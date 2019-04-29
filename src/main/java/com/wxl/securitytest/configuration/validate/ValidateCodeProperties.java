package com.wxl.securitytest.configuration.validate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangxiaolong
 * @date 2019/3/27 17:40
 * 各种验证码的配置
 */
@Component
@ConfigurationProperties(prefix = "validate.code")
public class ValidateCodeProperties {
  /**
   * 图形验证码 配置属性
   */
  private ImageCodeProperties image = new ImageCodeProperties();

  /**
   * 其它验证码
   *
   */

  public ImageCodeProperties getImage() {
    return image;
  }

  public void setImage(ImageCodeProperties image) {
    this.image = image;
  }
}
