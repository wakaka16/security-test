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

  @Modifying
  @Transactional
  @Query(value = "update t_user set login_time = :loginTime where id = :id",nativeQuery = true)
  void modifyLoginTimeById(@Param("id")String id,@Param("loginTime")Date loginTime);


//  @Query("select distinct e from EventEntity e "
//      + " left join fetch e.handlers"
//      + " left join fetch e.police"
//      + " left join fetch e.reporter"
//      + " where e.id = :id")
//  EventEntity findById(@Param("id") String id);
}