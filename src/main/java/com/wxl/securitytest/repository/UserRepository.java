package com.wxl.securitytest.repository;

import com.wxl.securitytest.entity.UserEntity;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends
    JpaRepository<UserEntity, String>,
    JpaSpecificationExecutor<UserEntity> {

  /**
   * 根据邮箱查询用户
   * @param email 邮箱 unique
   * @return 用户信息
   */
  UserEntity getByEmail(@Param("email")String email);

  @Modifying
  @Query(value = "update t_user set last_login_time = now() where id = :id",nativeQuery = true)
  void updateLastLoginTimeById(@Param("id")String id);

  /**
   * 根据账户查询用户
   * @param account 账户
   * @return
   */
  UserEntity getByAccount(@Param("account")String account);

}