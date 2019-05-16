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
 * 全局异常捕获(业务错误，就是服务器错误了，相当于500)
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
  /**
   * 日志
   */
  protected final Logger LOG = LoggerFactory.getLogger(this.getClass());


  @ExceptionHandler(Exception.class)
  ResponseModel handleException(Exception e){
    LOG.error("【系统异常】",e.getMessage());
    ResponseModel responseModel = new ResponseModel(ResponseCode._502);
    return responseModel;
  }

  /**
   * spring文件上传配置的异常捕获
   * @return
   */
  @ExceptionHandler(MultipartException.class)
  ResponseModel handleMultipartException(MultipartException e){
    LOG.error(e.getMessage());
    ResponseModel responseModel = new ResponseModel(ResponseCode._501);
    responseModel.setErrorMsg("上传文件失败");
    return responseModel;
  }

  /**
   * 业务异常处理
   * @param e
   * @return
   */
  @ExceptionHandler(CustomException.class)
  ResponseModel handleCustomException(CustomException e){
    LOG.error(e.getMessage());
    ResponseModel responseModel = new ResponseModel(ResponseCode._501);
    responseModel.setErrorMsg(e.getMessage());
    return responseModel;
  }

  /**
   * 数据异常处理
   * @param e
   * @return
   */
  @ExceptionHandler(DataBaseException.class)
  ResponseModel handleDataBaseException(DataBaseException e){
    LOG.error(e.getMessage());
    ResponseModel responseModel = new ResponseModel(ResponseCode._501);
    responseModel.setErrorMsg("数据操作异常");
    return responseModel;
  }

}
