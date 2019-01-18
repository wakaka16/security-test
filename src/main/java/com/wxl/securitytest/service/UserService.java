package com.wxl.securitytest.service;

import com.wxl.securitytest.entity.UserEntity;
import java.util.Date;

public interface UserService {

  UserEntity getById(String id);

  UserEntity getByName(String name);

  UserEntity getByEmail(String email);

  /**
   * 创建一个用户
   * 1、校验用户
   * 2、立即创建
   * @param user
   * @return
   */
  UserEntity create(UserEntity user);

  /**
   * 修改用户登录时间
   * @param loginTime
   * @param id
   * @return
   */
  void modifyLoginTimeById(Date loginTime,String id);
}
