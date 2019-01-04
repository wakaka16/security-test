package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.repository.ResourceRepository;
import com.wxl.securitytest.service.ResourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/
@Service
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceRepository resourceRepository;

  @Override
  public List<ResourceEntity> findByName(String name) {
    return resourceRepository.findByName(name);
  }
}
