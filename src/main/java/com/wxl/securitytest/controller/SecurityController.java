package com.wxl.securitytest.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import java.security.Principal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.Session;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wxl
 * @Date 2018/12/13
 **/

/**
 * 安全控制器
 */
@RestController
@RequestMapping("/v1/security")
public class SecurityController {

  @RequestMapping(value = "/loginFail", method = {RequestMethod.GET, RequestMethod.POST})
  public String loginFail(HttpServletRequest request,Principal logUser){
    Object attribute = request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    String message = "用户认证异常";
    if(attribute instanceof InternalAuthenticationServiceException){
      InternalAuthenticationServiceException exception=(InternalAuthenticationServiceException)attribute;
      message=exception.getMessage();
    }else if(attribute instanceof UsernameNotFoundException){
      UsernameNotFoundException exception = (UsernameNotFoundException) attribute;
      message=exception.getMessage();
    }else if(attribute instanceof BadCredentialsException){
      message="密码错误";//提示用户名或密码错误
    }
    System.out.println(message);
    return message;
  }

  @RequestMapping(value = "/loginSuccess", method = RequestMethod.POST)
  public String loginSuccess(Principal logUser) {
    return logUser.getName()+": 登录成功";
  }

}
