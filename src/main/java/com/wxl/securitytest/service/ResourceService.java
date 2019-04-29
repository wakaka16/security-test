package com.wxl.securitytest.service;

import com.wxl.securitytest.entity.ResourceEntity;
import java.util.List;

public interface ResourceService {

  /**
   * 根据url查询resource信息
   * @param url
   * @return
   */
  List<ResourceEntity> findByUrl(String url);

}
