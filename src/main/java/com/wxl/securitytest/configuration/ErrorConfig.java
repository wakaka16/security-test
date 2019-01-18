package com.wxl.securitytest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author wangxiaolong
 * @date 2019/1/9 11:26
 *不可处理错误处理
 */
@Configuration
public class ErrorConfig implements ErrorPageRegistrar {

  @Override
  public void registerErrorPages(ErrorPageRegistry registry) {
    ErrorPage errorPage = new ErrorPage("/v1/security/error");
    registry.addErrorPages(errorPage);

  }


}
