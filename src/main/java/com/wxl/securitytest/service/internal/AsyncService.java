package com.wxl.securitytest.service.internal;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author wangxiaolong
 * @date 2019/3/8 15:03
 */
@Service
public class AsyncService {

  @Async(value = "threadCache")
  public void async(){
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("异步执行");
  }

  /**
   *  只能在自身之外调用，在本类调用是无效的
   * 因此下面的是无效的
   * 详见：TestController
   */
  public void test(){
    System.out.println("11111111111111111111111");
    async();
    System.out.println("test结束");
  }
}
