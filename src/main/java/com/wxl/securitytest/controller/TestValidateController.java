package com.wxl.securitytest.controller;

import com.wxl.securitytest.pojo.Demo;
import com.wxl.securitytest.pojo.group.First;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wangxiaolong
 * @date 2019/5/6 11:11
 */
public class TestValidateController {

  /**
   * 验证方式可以分组
   * 也可以按组先后（组时序）
   * @param demo
   */
  @ApiOperation(value = "验证时，name可以为空")
  @RequestMapping(value = "/1",method = {RequestMethod.GET,RequestMethod.POST})
  public void testParameter(@Validated Demo demo){

  }

  @ApiOperation(value = "验证时，name不可以为空")
  @RequestMapping(value = "/2",method = {RequestMethod.GET,RequestMethod.POST})
  public void testParameter2(@Validated({First.class}) Demo demo, BindingResult result){
    //参数验证结果
    //1、直接返回400
    //2、经过保证返回
    if(result.hasErrors()){
      //参数错误
    }

  }
}
