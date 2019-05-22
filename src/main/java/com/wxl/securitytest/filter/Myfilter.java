package com.wxl.securitytest.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author wangxiaolong
 * @date 2019/5/22 16:19
 * 直接继承OncePerRequestFilter，并注解上组件注解filter就直接可以使用了，
 * 但是filter的顺序默认是最先的（因为spring security中的order默认在100+）
 * 所以需要在security中设置过滤器的位置
 * */
//@Component
public class Myfilter extends OncePerRequestFilter implements InitializingBean {
  private AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    if (this.pathMatcher.match("/**", httpServletRequest.getRequestURI())) {
      System.out.println("hello");
    }


  }
}
