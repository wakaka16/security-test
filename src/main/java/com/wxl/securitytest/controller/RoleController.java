package com.wxl.securitytest.controller;


import com.wxl.securitytest.common.aop.LoggerManage;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 */
@Api(value = "角色restful接口")
@RestController
@Controller
@RequestMapping(value = "/v1/role")
public class RoleController extends BaseController {

  @Autowired
  private RoleService roleService;


  @ApiOperation(value = "角色列表")
  @GetMapping("/")
  @LoggerManage(description = "【角色列表】")
  public List<RoleEntity> listAll(){
    List<RoleEntity> roleList = roleService.listAll();
    for(int i = 0;i<roleList.size();i++){
      RoleEntity role = roleList.get(i);
      System.out.println(role);
    }
    return roleList;
  }



}
