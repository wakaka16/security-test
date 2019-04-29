package com.wxl.securitytest.common;

/**
 * @author wangxiaolong
 * @date 2019/1/8 15:22
 */
public class Const {
  public static final String ERROR_ACCOUNT= "账号长度必须为2-12个字符，大小写英文或汉字!";
  public static final String ERROR_ACCOUNT_HAS_REGISTER= "account has register";
  public static final String ERROR_PASSWORD= "密码长度必须为6-12个字符，大小写英文字符或数字!";
  public static final String ERROR_EMAIL= "邮箱格式错误!";
  public static final String ERROR_EMAIL_HAS_REGISTER= "email has register";
  /**
   * 加密盐
   */
  public static final String PASSWORD_SALT= "SECURITY_TEST";
  public static final String ERROR_CAN_NOT_BE_NULL= " can not be null or []";
  public static final String ERROR_CAN_NOT_REPEAT_BIND= " can not repeat bind";

}
