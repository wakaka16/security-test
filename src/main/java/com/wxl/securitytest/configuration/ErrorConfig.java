package com.wxl.securitytest.configuration;

import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangxiaolong
 * @date 2019/1/9 11:26
 *不可处理错误处理（用户访问未知路由等引起的）
 * tomcat找不到对应的资源
 */
@Configuration
public class ErrorConfig implements ErrorPageRegistrar {

  @Override
  public void registerErrorPages(ErrorPageRegistry registry) {
    ErrorPage errorPage = new ErrorPage("/v1/security/error");
    registry.addErrorPages(errorPage);

  }


}
