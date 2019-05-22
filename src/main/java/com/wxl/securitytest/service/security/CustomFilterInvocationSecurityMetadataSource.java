package com.wxl.securitytest.service.security;

import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.service.ResourceService;
import com.wxl.securitytest.service.RoleService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;


/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该**取到所有资源及其对应角色权限的定义**。
 *
 * @author wxl
 * @Date 2018/12/18 在CustomFilterSecurityInterceptor拦截器中调用此类， 查询访问该URL的所需角色
 **/
@Service
public class CustomFilterInvocationSecurityMetadataSource implements
    FilterInvocationSecurityMetadataSource {

  @Autowired
  private ResourceService resourceService;
  @Autowired
  private RoleService roleService;

  /**
   * 忽略权限判断的url（不需要登录）
   */
  @Value("${author.ignoreUrls}")
  private String[] ignoreUrls;


  /**
   * 将访问url所需要的所有角色添加到Collection<ConfigAttribute>
   */
  @Override
  public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
    FilterInvocation filterInvocation = (FilterInvocation) o;
    HttpServletRequest request = filterInvocation.getHttpRequest();
    //添加所需角色到config
    Collection<ConfigAttribute> needRoles = this.addRoleConfig(request);
    return needRoles;
  }

  /**
   * 查询访问该URL需要的所有的角色
   */
  private Collection<ConfigAttribute> addRoleConfig(HttpServletRequest request) {
    List<ConfigAttribute> configAttributes = new ArrayList<>();
    /*
    configAttributes==empty:忽略路径
    configAttributes!=empty:至少需要Anonymous
     */
    // 1、忽略路径不需要登录就可以访问
    boolean isIgnoreUrl = this.isIgnoreUrl(request, ignoreUrls);
    if (isIgnoreUrl) {
      return configAttributes;
    }
    //2、从数据匹配访问路径的资源
    List<ResourceEntity> currentResources = new ArrayList<>();
    this.matchUrlResources(request, currentResources);
    //2、从数据匹配访问方法的资源
    List<ResourceEntity> lastResourcesMatched = this
        .matchMethodResources(request, currentResources);
    //3、根据最终匹配到的资源，获取角色，并录入到configAttributes中
    this.completeConfig(lastResourcesMatched, configAttributes);
    //4、URL没有录入数据库，URL没有绑定角色，角色信息为空，又不是忽略路径（登录就能进行查看）
    if(configAttributes.isEmpty()){
      this.fillAnonymous(configAttributes);
    }
    return configAttributes;
  }

  /**
   * 判断请求的是否为忽略路径
   *
   * @param request 请求
   * @param ignoreUrls 忽略路径数组
   */
  private boolean isIgnoreUrl(HttpServletRequest request, String[] ignoreUrls) {
    for (String ignoreUrl : ignoreUrls) {
      AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(ignoreUrl);
      if (requestMatcher.matches(request)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 从数据匹配访问路径的资源
   *
   * @param request 请求
   * @param resourcesMatched 数据库匹配到的资源
   */
  private void matchUrlResources(HttpServletRequest request,
      List<ResourceEntity> resourcesMatched) {
    String url = request.getRequestURI();
    int index = 0;
    String matchUrl = "";
    try {
      URI currentUri = new URI(url);
      do {
        // 拼凑对比url用的表达式/v1/test/{param}
        matchUrl = currentUri.toString();
        for (int sum = 0; sum < index; sum++) {
          matchUrl += "/{param}";
        }
        // 查询数据库，找到可能存在的满足url格式的功能信息
        resourcesMatched = resourceService.findByUrl(matchUrl);
        // 如果条件成立，说明找到了那个要找的权限功能绑定信息
        if (!resourcesMatched.isEmpty()) {
          break;
        }
        currentUri = currentUri.resolve(".");
        // 去掉最后的“/”
        String currentUriValue = currentUri.toString();
        currentUriValue = currentUriValue.substring(0, currentUriValue.length() - 1);
        if (StringUtils.equals("", currentUriValue)) {
          break;
        }
        // 这是上一级uri
        currentUri = new URI(currentUriValue);
        index++;
      } while (true);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * 从数据匹配访问方法的资源
   *
   * @param request 请求
   * @param resourcesMatched 数据库匹配到的资源
   */
  private List<ResourceEntity> matchMethodResources(HttpServletRequest request,
      List<ResourceEntity> resourcesMatched) {
    String method = request.getMethod();
    List<ResourceEntity> lastResourcesMatched = new ArrayList<>();
    for (ResourceEntity resource : resourcesMatched) {
      String methods = resource.getMethods();
      if (methods.contains(method)) {
        lastResourcesMatched.add(resource);
      }
    }
    return lastResourcesMatched;
  }

  /**
   * 将管理资源的角色都配置进入config
   *
   * @param lastResourcesMatched 完全符合规定的资源
   * @param configAttributes 角色需求配置
   */
  private void completeConfig(List<ResourceEntity> lastResourcesMatched,
      List<ConfigAttribute> configAttributes) {
    for (ResourceEntity resource : lastResourcesMatched) {
      // 取得当前权限和角色的绑定关系
      List<RoleEntity> roles = roleService.findByResource(resource);
      if (!roles.isEmpty()) {
        for (RoleEntity role : roles) {
          SecurityConfig securityConfig = new SecurityConfig(role.getName());
          configAttributes.add(securityConfig);
        }
      }
    }
  }

  /**
   * 没有录入数据库，没有角色，又不是忽略路径（登录就能进行查看）
   * @param configs 角色需求配置
   * @return
   */
  private Collection<ConfigAttribute> fillAnonymous(Collection<ConfigAttribute> configs) {
    configs.add(new SecurityConfig("ROLE_ANONYMOUS"));
    return configs;
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}
