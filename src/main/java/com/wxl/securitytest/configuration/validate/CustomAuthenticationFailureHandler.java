package com.wxl.securitytest.configuration.validate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author wangxiaolong
 * @date 2019/3/28 11:41
 * 验证码过滤器异常处理器
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException e)
      throws IOException, ServletException {
    request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,e);
    request.getRequestDispatcher("/v1/security/loginFail").forward(request, response);
  }
}
