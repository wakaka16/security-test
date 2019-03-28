package com.wxl.securitytest.configuration.validate;

import org.springframework.security.core.AuthenticationException;

/**
 * @author wangxiaolong
 * @date 2019/3/28 11:45
 */
public class ValidateCodeException extends AuthenticationException {

  public ValidateCodeException(String msg) {
    super(msg);
  }
}
