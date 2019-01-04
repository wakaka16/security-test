package com.wxl.securitytest.service;

import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import java.util.List;

public interface ResourceService {

  /**
   * 根据url查询resource信息
   * @param name
   * @return
   */
  List<ResourceEntity> findByName(String name);

}
