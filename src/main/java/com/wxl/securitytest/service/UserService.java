package com.wxl.securitytest.service;

import com.wxl.securitytest.entity.UserEntity;

public interface UserService {

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

}
