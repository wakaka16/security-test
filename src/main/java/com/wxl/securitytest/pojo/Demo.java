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
 */
public class Demo {
  @NotEmpty(groups = {First.class},message = "name can not be null")
  @Size(min=3,max=8)
  @Pattern(regexp = "[0-9]*")
  @Max(value = 15165156L)
  @Min(value = 0L)
  @Email
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
