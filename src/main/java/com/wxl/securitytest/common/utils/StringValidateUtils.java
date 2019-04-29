package com.wxl.securitytest.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.Validate;


/**
 * 字符串的正则验证等
 *
 */

public class StringValidateUtils {

  /**
   * 计算GBK编码的字符串长度.
   * 
   * @param str String
   * @return int
   */
  public static int getGBKLength(String str) {
    try {
      byte[] b = str.getBytes("GBK");
      return b.length;
    } catch (Exception ex) {
      return 0;
    }
  }

  /**
   * 还原字符串中特殊字符.
   * 
   * @param strData String
   * @return 还原后的字符串
   */
  public static String decodeString(String strData) {
    if (strData == null) {
      return "";
    }
    return strData.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&apos;", "'")
        .replaceAll("&quot;", "\"").replaceAll("&amp;", "&");
  }

  /**
   * 判断是否在允许的长度范围内.
   * 
   * @param str String
   * @param min int
   * @param max int
   * @return boolean
   */
  public static boolean isGBKMatch(String str, int min, int max) {
    int strLength = getGBKLength(decodeString(str));
    if (strLength < min || strLength > max) {
      return false;
    }
    return true;

  }

  /**
   * 手机号格式验证.
   * @param mobile String
   * @return 验证通过返回true
   */
  public static boolean isMobile(String mobile) {
    Validate.notBlank(mobile,"手机号不能为空");
    Pattern p = null;
    Matcher m = null;
    boolean b = false;
    p = Pattern.compile("^[1][3-9][0-9]{9}$"); // 验证手机号
    m = p.matcher(mobile);
    b = m.matches();
    return b;
  }

  /**
   * 身份证号码格式验证.
   * @param idCard String
   * @return 验证通过返回true
   */
  public static boolean isIdCard(String idCard) {
    Validate.notBlank(idCard,"身份证号码不能为空");
    Pattern p = null;
    Matcher m = null;
    boolean b = false;
    p = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])"); // 验证身份证号码
    m = p.matcher(idCard);
    b = m.matches();
    return b;
  }

  /**
   * 邮箱格式验证
   * @param email
   * @return
   */
  public static boolean isEmail(String email) {
    Validate.notBlank(email,"邮箱不能为空");
    Pattern p = null;
    Matcher m = null;
    boolean b = false;
    p = Pattern.compile("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$"); // 验证邮箱
    m = p.matcher(email);
    b = m.matches();
    return b;
  }

  /**
   * 密码格式验证
   * @param password
   * @return
   */
  public static boolean isPassword(String password) {
    Validate.notBlank(password,"密码不能为空");
    Pattern p = null;
    Matcher m = null;
    boolean b = false;
    p = Pattern.compile("[a-zA-Z\\d]{6,12}"); // 验证密码
    m = p.matcher(password);
    b = m.matches();
    return b;
  }

  /**
   * 账号格式验证
   * @param account
   * @return
   */
  public static boolean isAccount(String account) {
    Validate.notBlank(account,"account can not be null or []");
    Pattern p = null;
    Matcher m = null;
    boolean b = false;
    p = Pattern.compile("^[a-zA-Z\\u4E00-\\u9FA5\\uf900-\\ufa2d·s]{2,12}$"); // 验证账号
    m = p.matcher(account);
    b = m.matches();
    return b;
  }

  /**
   * 校验字符串正则表达式.
   * 
   * @param str 要校验的字符串
   * @param pattern 正则表达式
   * @return boolean
   */
  public static boolean validStrByPattern(String str, String pattern) {
    if (str.matches(pattern)) {
      return true;
    }
    return false;
  }

  /**
   * 校验字符串长度.
   * 
   * @param str 要校验的字符串
   * @param minLen 最小长度
   * @param maxLen 最大长度
   * @return boolean
   */
  public static boolean validStrByLength(String str, int minLen, int maxLen) {
    int strLen = str.length();
    if (strLen >= minLen && strLen <= maxLen) {
      return true;
    }
    return false;
  }
}
