package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.service.DiService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

/**
 * @author wangxiaolong
 * @date 2019/3/6 14:12
 * 如果同一个接口注入2个实现类会发生错误，此时第二个实现类应该加入注入条件，当没有才进行注入
 */
@Service
@ConditionalOnMissingBean(DiService.class)
public class DiServiceImpl1 implements DiService {

  @Override
  public void method() {
    System.out.println("DiServiceImpl1");
  }
}
