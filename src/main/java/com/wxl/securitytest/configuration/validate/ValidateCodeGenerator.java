package com.wxl.securitytest.configuration.validate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangxiaolong
 * @date 2019/3/27 17:22
 */
public interface ValidateCodeGenerator {
  /**
   * 创建验证码
   */
  ImageCode createCode(HttpServletRequest request);
}
