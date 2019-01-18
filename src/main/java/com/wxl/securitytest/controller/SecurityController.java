package com.wxl.securitytest.controller;

import com.wxl.securitytest.pojo.ResponseModel;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.service.UserService;
import java.security.Principal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
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
public class SecurityController extends BaseController{
  @Autowired
  private UserService userService;

  @RequestMapping(value = "/loginFail", method = {RequestMethod.GET, RequestMethod.POST})
  public ResponseModel loginFail(HttpServletRequest request){
    Object attribute = request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    Exception e = null;
    if(attribute instanceof InternalAuthenticationServiceException){
      InternalAuthenticationServiceException exception=(InternalAuthenticationServiceException)attribute;
      e=new IllegalArgumentException(exception.getMessage());
    }else if(attribute instanceof UsernameNotFoundException){
      UsernameNotFoundException exception = (UsernameNotFoundException) attribute;
      e=new IllegalArgumentException(exception.getMessage());
    }else if(attribute instanceof BadCredentialsException){
      e=new IllegalArgumentException("用户名或密码错误");
    }
    return this.buildHttpResultForException(e);
  }

  @RequestMapping(value = "/loginSuccess", method = RequestMethod.POST)
  public ResponseModel loginSuccess(Principal logUser) {
    UserEntity userEntity = this.verifyOperatorLogin(logUser);
    userEntity.setLoginTime(new Date());
    userService.modifyLoginTimeById(new Date(),userEntity.getId());
    userEntity.setPassword(null);
    return this.buildHttpResult(userEntity,new String[]{"roles"});
  }

  @RequestMapping("/error")
  public String error(){
    return "error";
  }

  @RequestMapping("/error2")
  public String error2(){
    return "error2";
  }

}
