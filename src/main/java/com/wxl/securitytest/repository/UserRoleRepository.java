package com.wxl.securitytest.repository;

import com.wxl.securitytest.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wangxiaolong
 * @date 2019/4/28 15:06
 */
@Repository
public interface UserRoleRepository extends JpaRepository<RoleEntity, String> {

  /**
   * 创建用户和角色的绑定
   * @param userId 用户ID
   * @param roleId 角色ID
   * @return
   */
  @Modifying
  @Query(value = "insert into t_user_role (user_id,role_id) values (:userId , :roleId)",nativeQuery = true)
  void create(@Param(value = "userId") String userId,@Param(value = "roleId") String roleId);

  /**
   * 用户与角色是否绑定
   * @param userId 用户ID
   * @param roleId 角色ID
   * @return 0 未绑定，>0已经绑定
   */
  @Query(value = "select count(user_id) from t_user_role where user_id=:userId and role_id=:roleId",nativeQuery = true)
  int countByUserIdAndRoleId(@Param(value = "userId") String userId,@Param(value = "roleId") String roleId);
}
