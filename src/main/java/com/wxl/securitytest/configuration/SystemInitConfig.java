//package com.wxl.securitytest.configuration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.wxl.securitytest.entity.RoleEntity;
//import com.wxl.securitytest.entity.UserEntity;
//import com.wxl.securitytest.service.RoleService;
//import com.wxl.securitytest.service.UserService;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.stereotype.Component;
//
//
///**
// * 数据初始化配置，以保证整个系统在保证最小化数据完成性的基础上能够使用
// *
// * @author yinwenjie
// */
//@Component
//public class SystemInitConfig implements CommandLineRunner {
//
//  @Autowired
//  private RoleService roleService;
//  @Autowired
//  private UserService userService;
//
//  /**
//   * 初始化角色
//   * ADMIN 超级管理员
//   */
//  private void initRole() {
//    /*
//     * 向系统初始化role角色信息
//     * 保证系统中至少固定持有ADMIN角色
//     * */
//    if (this.roleService.findByName("ADMIN") == null) {
//      //创建角色
//      RoleEntity role = new RoleEntity();
//      role.setComment("管理员角色");
//      role.setCreateDate(new Date());
//      role.setName("ADMIN");
//      role.setStatus(UseStatus.STATUS_NORMAL);
//      this.roleService.addRole(role);
//    }
//
//  }
//
//  /**
//   * 初始化系统管理员
//   */
//  private void initAdmin() {
//    /*
//     * 保证系统中至少有一个系统管理员
//     * */
//    UserEntity admin = this.userService.getByName("admin");
//    if (admin == null) {
//      admin = new UserEntity();
//      admin.setName("admin");
//      admin.setLoginTime(new Date());
//      admin.setPassword("123456");
//      admin.setName("admin(后台超级管理员)");
//      RoleEntity role = this.roleService.getById("ADMIN");
//      if (role != null) {
//        Set<RoleEntity> roles = new HashSet<>();
//        roles.add(role);
//        admin.setRoles(roles);
//      }
//      this.userService.create(admin);
//    }
//  }
//
//  @Override
//  public void run(String... args) throws Exception {
//    this.initAdmin();
//    this.initRole();
//  }
//}