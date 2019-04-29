package com.wxl.securitytest.service;

/**
 * @author wangxiaolong
 * @date 2019/4/28 15:04
 */
public interface UserRoleService {

  /**
   * 绑定用户的角色的关系
   * @param userId
   * @param roleId
   */
  void create(String userId, String roleId);
}
