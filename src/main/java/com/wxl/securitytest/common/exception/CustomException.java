package com.wxl.securitytest.common.exception;

/**
 * @author wangxiaolong
 * @date 2019/2/20 17:50
 * 自定义异常
 */
public class CustomException extends RuntimeException {

  private static final long serialVersionUID = 7305339081405812290L;

  public CustomException(String message){
    super(message);
  }
}
