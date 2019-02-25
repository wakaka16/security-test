package com.wxl.securitytest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaolong
 * @date 2019/2/20 17:12
 */
@RestController
@RequestMapping(value = "/v1/log")
public class LogTestController extends BaseController{

  /**
   * 日志的输出与log中的clazz有关
   */
//  private final Logger LOG = LoggerFactory.getLogger(LogTestController.class);
//  private final Logger LOG = LoggerFactory.getLogger(SecurityController.class);

  @GetMapping(value = "/test/")
  public String test(){
//    2019-02-20 17:44:22.205  INFO 8728 --- [nio-8080-exec-9] c.w.s.controller.（LogTestController）
//   2019-02-20 17:45:18.175  INFO 8036 --- [nio-8080-exec-1] c.w.s.controller.（SecurityController）
    LOG.info("*******************************************");
    return "test";
  }

}
