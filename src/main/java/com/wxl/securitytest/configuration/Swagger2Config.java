package com.wxl.securitytest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API 配置信息
 * @author yinwenjie
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {
  @Value("${swagger.version}")
  private String version;
  
  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.vanda.alarm.manager"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("系统-restful在线文档说明")
        .description("系统-restful在线文档说明")
        .version(version)
        .license("车上码")
        .licenseUrl("www.vanda.com")
        .build();
  }
}
