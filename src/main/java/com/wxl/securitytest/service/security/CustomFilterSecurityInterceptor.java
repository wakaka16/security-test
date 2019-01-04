package com.wxl.securitytest.service.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/
@Service
public class CustomFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }
  @Autowired
  private FilterInvocationSecurityMetadataSource securityMetadataSource;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    FilterInvocation fi = new FilterInvocation(request, response, chain);
    //beforeInvocation：调用CustomFilterInvocationSecurityMetadataSource
    //获取访问这个路径需要的权限（role）
    InterceptorStatusToken token = super.beforeInvocation(fi);
    try {
      // 执行下一个拦截器
      fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
    } finally {
      //afterInvocation：调用CustomAccessDecisionManager
      //进行比对用户的权限和访问url的权限
      super.afterInvocation(token, null);
    }
  }

  @Override
  public void destroy() {}



  @Autowired
  public void setMyAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
    super.setAccessDecisionManager(accessDecisionManager);
  }



  @Override
  public Class<?> getSecureObjectClass() {
    return FilterInvocation.class;
  }

  @Override
  public SecurityMetadataSource obtainSecurityMetadataSource() {
    return this.securityMetadataSource;
  }


}
