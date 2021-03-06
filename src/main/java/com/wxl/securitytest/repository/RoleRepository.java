package com.wxl.securitytest.repository;

import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色Dao
 * @author wxl
 */
@Repository
public interface RoleRepository extends
    JpaRepository<RoleEntity, String>,
    JpaSpecificationExecutor<RoleEntity> {

  /**
   * 查询用户的角色
   * @param user
   * @return
   */
  List<RoleEntity> findDistinctByUsersEquals(@Param("user")UserEntity user);

  /**
   * 查询权限所属的角色
   * @param resource
   * @return
   */
  List<RoleEntity> findDistinctByResourcesEquals(@Param("resource")ResourceEntity resource);

  /**
   *列表所有角色
   * @return
   */
  @Query(value = "select re from RoleEntity re")
  List<RoleEntity> listAll();

  /**
   * 根据名称查询
   * @param name unique
   * @return
   */
  RoleEntity getByName(@Param("name")String name);

}