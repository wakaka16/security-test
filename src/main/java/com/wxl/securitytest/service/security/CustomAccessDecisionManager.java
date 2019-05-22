package com.wxl.securitytest.service.security;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

/**
 * 权限决定的管理器 从登录用户的的角色和访问URL所需的角色进行比较
 *
 * @author wxl
 */
@Service
public class CustomAccessDecisionManager implements AccessDecisionManager {

  /**
   * 这里就是进行当前url请求需要的权限，和当前登录操作者所具备的权限进行对比
   *
   * 1、访问当前url需要的权限从CustomFilterInvocationSecurityMetadataSource的
   * getAttributes(Object object)这个方法返回就是这个方法中的configAttributes参数
   *
   * 2、当前登陆者所具有的权限为authentication，
   * 就是CustomUserSecurityDetailsService中循环添加到
   * GrantedAuthority 对象中的权限信息集合
   *
   * @param authentication 用户拥有的权限
   * @param object 包含客户端发起的请求的requset信息
   * @param configAttributes 问当前url需要的权限
   * @throws AccessDeniedException
   * @throws InsufficientAuthenticationException
   */
  @Override
  public void decide(Authentication authentication, Object object,
      Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException, InsufficientAuthenticationException {
    // 访问当前路由不需要权限（忽略路径）
    if (configAttributes.isEmpty()) {
      return;
    }
    // 登录用户的权限
    Collection<? extends GrantedAuthority> loginUserRoles = authentication.getAuthorities();
    for (ConfigAttribute securityConfig : configAttributes) {
      for (GrantedAuthority grantedAuthority : loginUserRoles) {
        // 如果条件成立，则说明当前登陆者具备访问这个url的权限
        if (StringUtils.equals(securityConfig.getAttribute(), grantedAuthority.getAuthority())) {
          return;
        }
      }
    }
    throw new AccessDeniedException("（not author）!");
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

}
