package com.wxl.securitytest.repository;

import com.wxl.securitytest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends
    JpaRepository<UserEntity, String>,
    JpaSpecificationExecutor<UserEntity> {

  /**
   * 根据用户名查询用户
   * @param name 用户名（唯一）
   * @return 用户信息
   */
  UserEntity getByName(@Param("name")String name);

  /**
   * 根据邮箱查询用户
   * @param email 邮箱（唯一）
   * @return 用户信息
   */
  UserEntity getByEmail(@Param("email")String email);

}