package com.wxl.securitytest.service;

import com.wxl.securitytest.pojo.FilePojo;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传下载
 */
public interface FileService {

  /**
   * 文件上传服务
   * 处理过程为：
   * 1、为了保证网络畅通，要控制文件大小在10MB以下，所以也要进行控制（当然也可以通过spring mvc的配置实现限制）
   * 2、开始保存文件，注意，文件都要重命名。为了简单起见重命名使用java自带的UUID工具完成即可
   * 3、正式写入文件，如果以上所有步骤都成功，则向上传者返回文件存储的提示信息
   * 最后，本工程没有提供上传的测试页面，测试是使用postman等软件完成的
   * @param subsystem 指代进行文件上传的子系统信息，子系统将单独生成一个文件夹。利于管理
   * @param file 文件系统
   * @return
   * @throws IllegalArgumentException
   */
  public FilePojo fileUpload(String subsystem, MultipartFile file) throws IllegalArgumentException;

  /**
   *将文件转byte[]
   */
  public byte[] fileQuery(String folder, String file, String prefix) throws IllegalArgumentException , IOException;

}
