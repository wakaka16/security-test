package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.entity.DemoEntity;
import com.wxl.securitytest.repository.DemoRepository;
import com.wxl.securitytest.service.DemoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangxiaolong
 * @date 2019/4/30 17:58
 */
@Service
public class DemoServiceImpl implements DemoService {

  /**
   * 业务层不进行参数校验
   */

  @Autowired
  private DemoRepository demoRepository;

  @Override
  public List<DemoEntity> findByName(String name){
    return demoRepository.findByName(name);
  }



}
