package com.wxl.securitytest.Pojo;

import java.util.Date;

/**
 * 统一返回数据对象（对本次请求执行的操作结果是否成功 进行描述 flag=1时执行成功）
 * 
 * @author yhy yinwenjie
 * 
 */
public class ResponseModel {
  /** 时间 */
  private Long timestemp = new Date().getTime();
  /**
   * 正常情况下返回的数据在这里进行记录和描述
   */
  private Object data;
  /**
   * 响应标记，正常情况下是200
   */
  private String code = ResponseCode._200.getCode();
  /** 异常信息描述 */
  private String errorMsg = ResponseCode._200.getMessage();
  
  public ResponseModel() {
  }
  public ResponseModel(Object data, String code, String errorMsg) {
    this.data = data;
    this.code = code;
    this.errorMsg = errorMsg;
  }

  /**
   * 用于构造异常信息
   * @param responseCode
   */
  public ResponseModel(ResponseCode responseCode) {
    this.code = responseCode.getCode();
    this.errorMsg = responseCode.getMessage();
  }

  /**
   * 用于构造200的响应
   * @param data
   */
  public ResponseModel(Object data) {
    this.data = data;
  }

  /**
   * 用于构造其它信息
   * @param responseCode
   * @param data
   */
  public ResponseModel(ResponseCode responseCode,Object data) {
    this.code = responseCode.getCode();
    this.errorMsg = responseCode.getMessage();
    this.data = data;
  }

  public Long getTimestemp() {
    return timestemp;
  }
  public void setTimestemp(Long timestemp) {
    this.timestemp = timestemp;
  }
  public Object getData() {
    return data;
  }
  public void setData(Object data) {
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getErrorMsg() {
    return errorMsg;
  }
  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
}