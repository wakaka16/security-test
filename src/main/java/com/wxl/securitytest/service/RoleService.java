package com.wxl.securitytest.service;

import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import java.util.List;

public interface RoleService {

  /**
   * 根据用户获取角色信息
   * @param user
   * @return
   */
  List<RoleEntity> findByUser(UserEntity user);

  /**
   * 根据权限获取角色信息
   * @param resource
   * @return
   */
  List<RoleEntity> findByResource(ResourceEntity resource);

}
