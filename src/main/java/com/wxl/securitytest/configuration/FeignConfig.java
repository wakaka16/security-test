package com.wxl.securitytest.configuration;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign客户端配置，最主要的是对IM服务器的访问
 */
@Configuration
@EnableFeignClients
public class FeignConfig {
  @Value("${im.url}")
  private String imUrl;

  @Bean
  public Request.Options feignOptions() {
    return new Request.Options(4 * 5000,  2 * 5000);
  }
}
