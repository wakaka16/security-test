package com.wxl.securitytest;

import com.wxl.starters.validatecodestarter.annotion.EnableValidateCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author wxl
 * 开启异步功能
 * EnableCaching
 *
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableSwagger2
@EnableValidateCode
public class SecurityTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityTestApplication.class, args);
  }

}

