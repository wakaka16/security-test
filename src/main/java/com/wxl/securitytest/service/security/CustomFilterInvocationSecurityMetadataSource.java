package com.wxl.securitytest.service.security;

import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.repository.ResourceRepository;
import com.wxl.securitytest.repository.RoleRepository;
import com.wxl.securitytest.service.ResourceService;
import com.wxl.securitytest.service.RoleService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
 * @Author wxl
 * @Date 2018/12/18
 **/
/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。
 * 此类在初始化时，应该**取到所有资源及其对应角色权限的定义**。
 */
@Service
public class CustomFilterInvocationSecurityMetadataSource implements
    FilterInvocationSecurityMetadataSource {

  @Autowired
  private ResourceService resourceService;
  @Autowired
  private RoleService roleService;

  /**
   * 忽略权限判断的url
   */
  @Value("${author.ignoreUrls}")
  private String[] ignoreUrls;


  /**
   * 将访问url所需要的所有角色添加到Collection<ConfigAttribute>
   * @param o
   * @return
   * @throws IllegalArgumentException
   */
  @Override
  public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
    FilterInvocation filterInvocation = (FilterInvocation) o;
    HttpServletRequest request = filterInvocation.getHttpRequest();
    //
    Collection<ConfigAttribute> rolesByUrl = findRolesByUrl(request);
    return rolesByUrl;
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
  /**
   * 该私有方法，将在角色集合为empty的时候，默认填充两个角色“匿名角色”
   * 情景：数据库已加入url信息，但是未给url绑定角色，说明并没有设定相关url的权限
   * 如果用户没打算管理url，则url可以任意访问
   * 当url被用户添加到数据库，即使没有绑定给角色，那也只能ADMIN、ROLE_ANONYMOUS（未登录）角色能够访问
   * @param configs
   * @return
   */
  private Collection<ConfigAttribute> fillAnonymous(Collection<ConfigAttribute> configs) {
    // 那么就只有两类角色可以访问，一类是ADMIN，另一类是匿名用户ROLE_ANONYMOUS
    //由于admin用户什么都可以访问的权限，所有不用再次添加权限
    if (configs.isEmpty()) {
      configs.add(new SecurityConfig("ROLE_ANONYMOUS"));
    }
    return configs;
  }

  /**
   * 查询所有的角色，并保存到List<ConfigAttribute>
   *   1、数据库匹配与requestUrl相同的url
   *   2、获取这些url的role
   *   3、添加到List<ConfigAttribute>
   * @param request 请求url
   * @return
   */
  private  Collection<ConfigAttribute> findRolesByUrl(HttpServletRequest request){
    /*
     * 确定功能路径绑定的角色过程如下：
     * 1、如果当前的路径符合配置的“排除权限”路径，那么直接返回不需要任何权限的空集合
     * 2、然后才开始正式的判断，因为很可能存在“{}”传参的形式，所以需要进行递归排除、
     * 3、比较Method，返回method设置匹配的功能权限绑定信息
     */
    String url = request.getRequestURI();
    String method = request.getMethod();
    List<ConfigAttribute> configAttributes = new ArrayList<>();
    // 1、====================
    for (String ignoreUrl : ignoreUrls) {
      AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(ignoreUrl);
      if (requestMatcher.matches(request)) {
        return this.fillAnonymous(configAttributes);
      }
    }

    // 2、获取当前访问路径匹配到的数据库录入的资源
    int index = 0;
    String matchUrl = "";
    List<ResourceEntity> currentResources = null;
    try {
      URI currentUri = new URI(url);
      do {
        // 拼凑对比url用的表达式
        matchUrl = currentUri.toString();
        for (int sum = 0; sum < index; sum++) {
          matchUrl += "/{param}";
        }
        // 查询数据库，找到可能存在的满足url格式的功能信息
        currentResources = this.resourceService.findByName(matchUrl);
        // 如果条件成立，说明找到了那个要找的权限功能绑定信息
        if (null!=currentResources&&!currentResources.isEmpty()) {
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

    // 3、获取当前访问路径方法匹配到的数据库录入的资源方法，获取角色，并录入到configAttributes中
    for (ResourceEntity resource : currentResources) {
      String methods = resource.getMethods();
      if (methods.indexOf(method) != -1) {
        // 取得当前权限和角色的绑定关系
        List<RoleEntity> roles = roleService.findByResource(resource);
        if (roles != null && !roles.isEmpty()) {
          for (RoleEntity role : roles) {
            SecurityConfig securityConfig = new SecurityConfig(role.getName());
            configAttributes.add(securityConfig);
          }
        }
      }
    }
    if (configAttributes.isEmpty()) {
      /**
       * 如果用户访问的资源不在库中，或在库中但是没有角色权限，那么就只有admin和匿名用户能看
       */
      return this.fillAnonymous(configAttributes);
    } else {
      return configAttributes;
    }
  }
}
