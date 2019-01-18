package com.wxl.securitytest.common.exception;

import com.wxl.securitytest.pojo.ResponseCode;
import com.wxl.securitytest.pojo.ResponseModel;
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
@ResponseBody
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
  ResponseModel handleException(Exception e){
    LOG.error(e.getMessage());
    ResponseModel responseModel = new ResponseModel(ResponseCode._501);
    responseModel.setErrorMsg("服务器内部错误，请联系管理员");
    return responseModel;
  }


  @ExceptionHandler(MultipartException.class)
  ResponseModel handleMultipartException(MultipartException e){
    LOG.error(e.getMessage());
    ResponseModel responseModel = new ResponseModel(ResponseCode._501);
    responseModel.setErrorMsg("上传文件失败");
    return responseModel;
  }
}
