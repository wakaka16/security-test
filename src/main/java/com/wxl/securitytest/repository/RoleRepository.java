package com.wxl.securitytest.repository;

import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends
    JpaRepository<RoleEntity, String>,
    JpaSpecificationExecutor<RoleEntity> {

  /**
   * 查询用户的角色
   */
  List<RoleEntity> findDistinctByUsersEquals(@Param("user")UserEntity user);

  /**
   * 查询权限所属的角色
   */
  List<RoleEntity> findDistinctByResourcesEquals(@Param("resource")ResourceEntity resource);

}