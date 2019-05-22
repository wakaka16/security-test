package com.wxl.securitytest.common.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wangxiaolong
 * @date 2019/2/20 17:50
 * 业务异常
 */
public class CustomException extends RuntimeException {

  private static final long serialVersionUID = 7305339081405812290L;

  public CustomException(String message){
    super(message);
  }

  public static void notNull(Object o,String errorInfo){
    if(null==o){
      throw new CustomException(errorInfo);
    }
  }

  public static void notBlank(String str,String errorInfo){
    if(StringUtils.isBlank(str)){
      throw new CustomException(errorInfo);
    }
  }

  public static void isTrue(boolean expression,String errorInfo){
    if(!expression){
      throw new CustomException(errorInfo);
    }
  }
}
