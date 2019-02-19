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
