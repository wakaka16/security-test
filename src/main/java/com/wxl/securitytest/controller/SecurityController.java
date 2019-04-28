package com.wxl.securitytest.controller;

import com.wxl.securitytest.configuration.validate.ValidateCodeException;
import com.wxl.securitytest.pojo.ResponseCode;
import com.wxl.securitytest.pojo.ResponseModel;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.service.UserService;
import java.security.Principal;
import java.util.Date;
import javax.servlet.http.Cookie;
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

  /**
   * 这个登录失败会报出三种异常：
   * 用户信息不存在（用户名或密码错误）
   * 用户角色信息不存在
   * 您尚未登录，请先进行登录...
   * @param request
   * @return
   */
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
      e=new IllegalArgumentException("用户名或密码错误");//密码
    }else if(attribute instanceof ValidateCodeException){
      ValidateCodeException exception = (ValidateCodeException) attribute;
      e=new IllegalArgumentException(exception.getMessage());
    } else{
      e=new IllegalArgumentException("登录过期，请进行登录...");
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

  /**
   * 成功登出
   */
  @RequestMapping(value = "/logoutSuccess", method = RequestMethod.GET)
  public ResponseModel logoutSuccess(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    for(int i = 0;i<cookies.length;i++){
      if(cookies[i].getName().equals("remember_me")){
        cookies[i].setMaxAge(0);//立即清除cookie
      }
    }
    return this.buildHttpResult();
  }

  /**
   * 不能拦截的错误
   * 1、http://localhost:8080/admin/views/login/login.htmlklkk 登陆成功后访问错误地址（获取不到）
   * 2、crsftoken失效/未传递（不能获取）
   * 3、返回404，找不到资源
   */
  @RequestMapping(value = "/error",  method = {RequestMethod.GET, RequestMethod.POST})
  public ResponseModel error() {
    ResponseModel responseModel = new ResponseModel(ResponseCode._404);
    return responseModel;
  }


}
