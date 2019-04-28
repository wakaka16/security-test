package com.wxl.securitytest.service;

import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import java.util.List;
import java.util.Set;

public interface RoleService {

  RoleEntity getById(String id);

  /**
   * 根据用户获取角色信息
   * @param user
   * @return
   */
  Set<RoleEntity> findByUser(UserEntity user);

  /**
   * 根据权限获取角色信息
   * @param resource
   * @return
   */
  List<RoleEntity> findByResource(ResourceEntity resource);

  /**
   * 列表所有角色信息
   * @return
   */
  List<RoleEntity> listAll();
}
