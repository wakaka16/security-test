package com.wxl.securitytest.controller;

import com.wxl.securitytest.entity.DemoEntity;
import com.wxl.securitytest.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaolong
 * @date 2019/5/5 9:45
 * 路由的写法
 * @GetMapping（{"/add","/edit"}）
 */
@Api(value = "控制器模板restful接口")
@RestController
@RequestMapping(value = "/v1/demo")
public class DemoController {

  @Autowired
  private DemoService demoService;


//  public void  test(){
//    BeanUtils.copyProperties();
//  }

//  @ApiOperation(value = "模板列表")
//  @GetMapping(value = "/list")
//  public List<DemoEntity> list(
//      @ApiParam(value = "name",name = "模板名称",required = true)
//      @RequestParam(value = "name",name = "模板名称",required = true)
//          String name){
//    return demoService.findByName(name);
//  }



}
