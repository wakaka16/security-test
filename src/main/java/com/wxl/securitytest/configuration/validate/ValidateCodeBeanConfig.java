package com.wxl.securitytest.configuration.validate;

/**
 * @author wangxiaolong
 * @date 2019/3/27 17:38
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

  @Autowired
  private ValidateCodeProperties validateCodeProperties;


  @Bean
  @ConditionalOnMissingBean(name = "imageCodeGenerator")
  public ValidateCodeGenerator imageCodeGenerator(){
    ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
    codeGenerator.setValidateCodeProperties(validateCodeProperties);
    return codeGenerator;
  }

}