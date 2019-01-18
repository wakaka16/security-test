package com.wxl.securitytest.controller;

/**
 * @Author wxl
 * @Date 2018/12/13
 **/

import com.wxl.securitytest.pojo.ResponseModel;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@RestController
@RequestMapping(value = "/v1/user")
public class UserController extends BaseController{

  @Autowired
  private UserService userService;

  /**
   * 当加入security后，访问此url会进入http://localhost:8080/login
   * 并提示输入账号和密码
   *  通过在securityConfig中设置.authorizeRequests().antMatchers("/v1/user/hello").permitAll()
   *  可以取消访问权限控制，直接访问到资源
   * @return
   */
  //资源一：忽略资源，任何人都可以访问
  @GetMapping("/hello")
  public String hello(){
    return "hello";
  }

  //资源二：没有入库的资源
  @GetMapping("/index")
  public String index(){
    return "index";
  }

  //资源三：限定admin角色访问
  @GetMapping("/get")
  public ResponseModel get(){
    UserEntity admin = userService.getByName("lizhiqiang");
    return this.buildHttpResult(admin,new String[]{"roles"});
  }

  @GetMapping(value = "/")
  public ResponseModel findAll(){
    List<UserEntity> userList = userService.findAll();
    return this.buildHttpResult(userList,new String[]{"roles"});
  }




}
