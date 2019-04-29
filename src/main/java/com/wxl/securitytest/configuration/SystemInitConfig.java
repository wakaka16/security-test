package com.wxl.securitytest.configuration;

import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.service.RoleService;
import com.wxl.securitytest.service.UserRoleService;
import com.wxl.securitytest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * 数据初始化配置，以保证整个系统在保证最小化数据完成性的基础上能够使用
 *
 * @author yinwenjie
 */
@Component
public class SystemInitConfig implements CommandLineRunner {

  @Autowired
  private RoleService roleService;
  @Autowired
  private UserService userService;
  @Autowired
  private UserRoleService userRoleService;

  /**
   * 初始化超级管理员角色
   * @return
   */
  private RoleEntity initRole() {
    RoleEntity admin = null;
    //初始化超级管理员角色
    admin = this.roleService.getByName("ADMIN");
    if (admin == null) {
      // 创建角色
      RoleEntity role = new RoleEntity();
      role.setDescription("超级管理员角色");
      role.setName("ADMIN");
      admin = this.roleService.create(role);
    }
    return admin;
  }

  /**
   * 初始化超级系统管理员
   */
  private void initAdmin() {
    RoleEntity adminRole = initRole();
    /*
     * 保证系统中至少有一个系统管理员
     */
    UserEntity admin = this.userService.getByAccount("admin");
    if (admin == null) {
      admin = new UserEntity();
      admin.setAccount("admin");
      admin.setEmail("582744719@qq.com");
      admin.setPassword("123456");
      admin.setName("超级管理员");
      admin = this.userService.create(admin);
      userRoleService.create(admin.getId(),adminRole.getId());
    }
  }

  @Override
  public void run(String... args) throws Exception {
    this.initAdmin();
  }
}