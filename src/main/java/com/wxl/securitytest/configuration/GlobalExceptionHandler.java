package com.wxl.securitytest.configuration;

import com.vanda.alarm.controller.mvc.ResponseCode;
import com.vanda.alarm.controller.mvc.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

/**
 * @Author wxl
 * @Date 2018/12/14
 **/

/**
 * 全局异常捕获
 */
@ControllerAdvice
public class GlobalExceptionHandler {
  /**
   * 日志
   */
  protected static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * spring文件上传配置的异常捕获
   * @return
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  ResponseModel handleException(Exception e){
    ResponseModel responseModel = new ResponseModel();
    responseModel.setTimestemp(System.currentTimeMillis());
    responseModel.setResponseCode(ResponseCode._501);
    if(e instanceof MultipartException){
      responseModel.setErrorMsg("上传文件失败");
    }else{
      LOG.error(e.getMessage(),e );
      responseModel.setErrorMsg("服务器内部错误，请联系管理员");
    }
    return responseModel;
  }
}
