package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.common.utils.media.MediaFactory;
import com.wxl.securitytest.common.utils.media.Multimedia;
import com.wxl.securitytest.pojo.FilePojo;
import com.wxl.securitytest.service.FileService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author wxl
 * @Date 2018/12/7
 **/
@Service
public class FileServiceImpl implements FileService {

  /**
   * 日志
   * */
  private static final Log LOGGER = LogFactory.getLog(FileServiceImpl.class);
  /**
   * 保存文件的根路径描述
   */
  @Value("${filePath.fileRoot}")
  private String fileRoot;

  /**
   * 最大允许的文件上传大小（单位MB）
   */
  @Value("${filePath.maxFileSize}")
  private Integer maxFileSize;

  @Autowired
  private MediaFactory mediaFactory;

  @Override
  public byte[] fileQuery(String folder, String file, String prefix) throws IllegalArgumentException, IOException {
    LOGGER.info("file service accept: " + folder + "/" + file + "." + prefix);
    // 1、========
    String relativePath = folder + "/" + file + "." + prefix;

    // 1、========
    File imFile = null;
    imFile = new File(fileRoot + "/" + relativePath);
    byte[] fileBytes = this.queryOriginalPicture(imFile);
    if(fileBytes == null) {
      return null;
    }

    return fileBytes;
  }

  /**
   * 这个私有方法用于在磁盘上查询原始文件
   * */
  private byte[] queryOriginalPicture(File imFile) throws IOException{
    // 如果不存在这个文件，就不需要处理咯
    // 生产环境下要显示一张默认的404图片
    if(!imFile.exists()) {
      return null;
    }
    InputStream in = new FileInputStream(imFile);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int len = 8192;
    int realLen;
    byte[] contents = new byte[8192];
    while((realLen = in.read(contents, 0, len)) != -1) {
      out.write(contents, 0, realLen);
    }
    in.close();
    byte[] imageBytes = out.toByteArray();
    out.close();

    return imageBytes;
  }



  /* (non-Javadoc)
   * @see com.vanda.alarm.manager.service.FileUpdateService#fileUpload(java.lang.String, org.springframework.web.multipart.MultipartFile)
   */
  @Override
  public FilePojo fileUpload(String subSystem , MultipartFile file) throws IllegalArgumentException {
    /*
     * 处理过程为：
     * 1、为了保证网络畅通，要控制文件大小在10MB以下，所以也要进行控制（当然也可以通过spring mvc的配置实现限制）
     *
     * 2、开始保存文件，注意，文件都要重命名。
     * 为了简单起见重命名使用java自带的UUID工具完成即可
     *
     * 3、正式写入文件，如果以上所有步骤都成功，则向上传者返回文件存储的提示信息
     *
     * 最后，本工程没有提供上传的测试页面，测试是使用postman等软件完成的
     * */
    // 1、都在这里=======
    String originalFilename = file.getOriginalFilename();
    long fileSize = file.getSize();
    String prefix = null;
    int prefixIndex = originalFilename.lastIndexOf(".");
    if(prefixIndex != -1) {
      prefix = originalFilename.substring(prefixIndex + 1);
      prefix = prefix.toLowerCase();
    }
    // 如果条件成立，说明大于10MB了
    if(fileSize > maxFileSize * 1024 * 1024) {
      throw new IllegalArgumentException("file should be less than 10MB!");
    }

    //2、======
    // 可以使用日期作为文件夹的名字
    Date nowDate = new Date();
    String folderName = new SimpleDateFormat("yyyyMMdd").format(nowDate);
    String renameImage = UUID.randomUUID().toString();
    String relativePath = null;
    String folderPath = null;
    if(!StringUtils.isBlank(subSystem)) {
      relativePath = subSystem + "/" + folderName + "/" + (new Random().nextInt(100) % 10);
    } else {
      relativePath =  folderName + "/" + (new Random().nextInt(100) % 10);
    }
    folderPath = fileRoot + "/" + relativePath;
    File folderFile = new File(folderPath);
    // 如果不存在这个目录则进行创建。
    // 为了保证高并发时不会重复创建目录，要进行线程锁定
    // 使用悲观锁就行了
    if(!folderFile.exists()) {
      synchronized (FileService.class) {
        while(!folderFile.exists()) {
          folderFile.mkdirs();
        }
      }
    }
    // 以下就是这个即将创建的文件的完整路径了
    relativePath += "/" + renameImage + "." + prefix;
    String fullImagePath = fileRoot + "/" + relativePath;

    // 4、====
    try {
      file.transferTo(new File(fullImagePath));
    } catch (IllegalStateException | IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    //5、视频/音频需要转码
    System.out.println(relativePath);
    if(prefix.toLowerCase().contains("mp4")){
      //创建转换文件的目录
      File convertFile = new File(fileRoot + "/convert/"+relativePath.substring(0,relativePath.lastIndexOf("/") ));
      if(!convertFile.exists()){
        convertFile.mkdirs();
      }

      Multimedia mediaHandler = mediaFactory.getMediaHandler(Multimedia.TYPE_VEDIO);
      mediaHandler.convert(fullImagePath, fileRoot + "/convert/" + relativePath);
      relativePath = "convert/" + relativePath;
    }else if(prefix.toLowerCase().contains("mp3")){
      //创建转换文件的目录
      File convertFile = new File(fileRoot + "/convert/"+relativePath.substring(0,relativePath.lastIndexOf("/") ));
      if(!convertFile.exists()){
        convertFile.mkdirs();
      }

      Multimedia mediaHandler = mediaFactory.getMediaHandler(Multimedia.TYPE_AUDIO);
      mediaHandler.convert(fullImagePath, fileRoot + "/convert/" + relativePath);
      relativePath = "convert/" + relativePath;
    }

    // 构造返回
    FilePojo result = new FilePojo();
    result.setRelativePath(relativePath);
    result.setFileName(renameImage + "." + prefix);
    result.setCreateTime(new Date());
    return result;
  }

}
