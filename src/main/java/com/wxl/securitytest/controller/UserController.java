package com.wxl.securitytest.controller;

/**
 * @Author wxl
 * @Date 2018/12/13
 **/

import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.pojo.ResponseModel;
import com.wxl.securitytest.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@RestController
@RequestMapping(value = "/v1/user")
public class UserController extends BaseController {

  @Autowired
  private UserService userService;

  @ApiOperation(value = "列表查询用户信息")
  @GetMapping(value = "/list")
  public List<UserEntity> listAll() {
    List<UserEntity> userList = userService.listAll();
    return userList;
  }

  @ApiOperation(value = "分页条件查询用户信息")
  @GetMapping(value = "/")
  public ResponseModel findCondition(
      @ApiParam(value = "account", name = "账号") String account,
      @ApiParam(value = "name", name = "名称") String name,
      @ApiParam(value = "email", name = "邮箱") String email,
      @PageableDefault(value = 20, sort = "createDate", direction = Direction.DESC) Pageable pageable,
      Principal operator) {
    //验证必须登录
//    this.verifyOperatorLogin(operator);
    //输入条件
    Map<String, Object> condition = new HashMap<>();
    if (!StringUtils.isBlank(account)) {
      condition.put("account", account);
    }
    if (!StringUtils.isBlank(name)) {
      condition.put("name", name);
    }
    if (!StringUtils.isBlank(email)) {
      condition.put("email", email);
    }
    Page<UserEntity> result = userService.findByCondition(condition, pageable);
    return this.buildHttpResult(result);
  }


}
