package com.wxl.securitytest.controller;


import com.wxl.securitytest.pojo.ResponseModel;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.repository.UserRepository;
import com.wxl.securitytest.service.RoleService;
import com.wxl.securitytest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaolong
 * @date 2019/1/14 14:13
 * 1. PUT /users/{id} 更新用户信息
 * 2. PATCH /users/role/{id} 更新用户角色
 * 3. PATCH /users/status/{id} 更新用户状态
 *  @ApiOperation("更新用户状态")
 *     @PatchMapping("/status/{id}")
 *     @PreAuthorize("hasAnyRole('SYS_ADMIN','ADMIN')")
 *     @SysLog(resource = "用户状态",type = SysLogTypeEnum.UPDATE)
 *     public Result updateUserStatus(@ApiParam("角色ID") @PathVariable("id") Integer id,
 *                                    @ApiParam("状态ID") @Valid @RequestBody UpdateStatus update)
 *
 *
 *     @PutMapping("/{id}")
 *     @ApiOperation("更新用户信息")
 *     @PreAuthorize("hasAnyRole('SYS_ADMIN','ADMIN') or #id == authentication.principal.id")
 *     @SysLog(resource = RESOURCE_USER,type = SysLogTypeEnum.UPDATE)
 *     public Result<UserDTO> updateUser(@ApiParam("用户ID") @PathVariable("id") @P("id") Integer id,
 *                                       @ApiParam("用户对象") @Valid @RequestBody UserDO user)
 */
@RestController
@RequestMapping(value = "/v1/test")
public class TestController extends BaseController {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private RoleService roleService;

  //findOne
//  @GetMapping("/findOne/{id}")
//  public ResponseModel findOne(@PathVariable(value = "id") String id){
//    UserEntity one = userService.getById(id);
//    return one;//this.buildHttpResult(one);
//  }

  @GetMapping("/hello")
  public void hello(){
    System.out.println("hello");
  }

  //findOne
  @GetMapping("/findOne/{id}")
  public ResponseModel findOne(@PathVariable(value = "id") String id){
    RoleEntity roleEntity = roleService.getById(id);
    return this.buildHttpResult(roleEntity);
  }

  //save
  @GetMapping("/save")
  public ResponseModel save(){
    UserEntity one = new UserEntity();
    one.setName("wakakaFlush");
    one.setPassword("123456");
    one.setEmail("wakakaFlush@qq.com");
    userService.create(one);
    return  this.buildHttpResult(one);
  }


}
