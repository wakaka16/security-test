package com.wxl.securitytest.controller;

import com.wxl.securitytest.pojo.Demo;
import com.wxl.securitytest.pojo.group.First;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaolong
 * @date 2019/5/6 11:11
 */
@RestController
@RequestMapping(value = "/v1/testParameter")
public class TestParameterController {
  @RequestMapping(value = "/1",method = {RequestMethod.GET,RequestMethod.POST})
  public void testParameter( Demo demo){
    System.out.println(demo.getName());
    System.out.println("执行完成");
  }
}
