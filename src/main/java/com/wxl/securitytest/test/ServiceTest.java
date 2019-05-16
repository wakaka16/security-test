package com.wxl.securitytest.test;

import com.wxl.securitytest.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangxiaolong
 * @date 2019/5/5 9:41
 */
public class ServiceTest {
  @Autowired
  private DemoService demoService;


  public void test(){
    demoService.testTranstation();
  }

}
