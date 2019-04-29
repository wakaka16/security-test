package com.wxl.securitytest.service;

import com.wxl.securitytest.entity.UserEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  UserEntity getById(String id);

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
   * 更新用户最后登录时间
   * @param id
   * @return
   */
  void updateLoginTimeById(String id);

  /**
   * 获取用户列表
   * @return
   */
  List<UserEntity> listAll();

  /**
   * 根据账号查询
   * @param account 账号 unique
   * @return
   */
  UserEntity getByAccount(String account);

  /**
   * 分页条件查询用户
   * @param condition 条件
   * @param pageable 分页信息 如果有多个参数，spring jpa 建议放在最后
   * @return 分页用户信息
   */
  Page<UserEntity> findByCondition(Map<String, Object> condition, Pageable pageable);
}
