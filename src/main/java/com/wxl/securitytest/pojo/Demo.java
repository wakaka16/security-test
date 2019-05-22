package com.wxl.securitytest.pojo;

import com.wxl.securitytest.pojo.group.First;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author wangxiaolong
 * @date 2019/5/5 16:36
 * 1、测试参数封装
 * 2、测试参数验证
 */
public class Demo {
  @NotEmpty(groups = {First.class},message = "name can not be null")
  @Size(min=3,max=8,message = "3-8个字符")
  @Pattern(regexp = "[0-9]*",message = "只能为数字")
  @Max(value = 15165156L,message = "最大值")
  @Min(value = 0L)
  @Email(message = "邮件格式")
  private String name;

  public Demo(){
  }
  public Demo(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
