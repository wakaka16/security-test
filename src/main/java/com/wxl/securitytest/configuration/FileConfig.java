package com.wxl.securitytest.configuration;

import java.io.File;
import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wxl
 * @Date 2018/12/7
 * 文件上传配置
 **/
@Configuration
public class FileConfig {

  /**
   * 最大允许的文件上传大小（单位MB）
   */
  @Value("${filePath.maxFileSize}")
  private Integer maxFileSize;


  /**
   * 文件上传路径（单位MB）
   */
  @Value("${filePath.fileRoot}")
  private String fileRoot;

  /**
   * @return 多文件上传配置
   */
  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    //文件最大
    factory.setMaxFileSize(maxFileSize * 1024 + "KB");
    /// 设置总上传数据总大小
    factory.setMaxRequestSize(maxFileSize * 1024 + "KB");

    String location = fileRoot + "/tmp";
    factory.setLocation(location);
    // 注意，没有文件夹，就需要进行创建
    File locationFile = new File(location);
    if (!locationFile.exists()) {
      locationFile.mkdirs();
    }
    return factory.createMultipartConfig();
  }

}
