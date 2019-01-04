package com.wxl.securitytest.controller;

/**
 * @Author wxl
 * @Date 2018/12/13
 **/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

  /**
   * 当加入security后，访问此url会进入http://localhost:8080/login
   * 并提示输入账号和密码
   *  通过在securityConfig中设置.authorizeRequests().antMatchers("/v1/user/hello").permitAll()
   *  可以取消访问权限控制，直接访问到资源
   * @return
   */
  @GetMapping("/hello")
  public String hello(){
    return "hello";
  }


  @GetMapping("/index")
  public String index(){
    return "index";
  }




}
