package com.wxl.securitytest.controller;

import com.wxl.securitytest.Pojo.ResponseModel;
import com.wxl.securitytest.common.utils.DateUtils;
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
  public String loginFail(HttpServletRequest request){
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
