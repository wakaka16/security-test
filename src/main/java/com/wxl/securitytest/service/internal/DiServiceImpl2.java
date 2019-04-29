package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.service.DiService;
import org.springframework.stereotype.Service;

/**
 * @author wangxiaolong
 * @date 2019/3/6 14:13
 */
@Service
public class DiServiceImpl2 implements DiService {

  @Override
  public void method() {
    System.out.println("DiServiceImpl2");
  }
}
