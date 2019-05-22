package com.wxl.securitytest.controller;


import com.wxl.securitytest.pojo.FilePojo;
import com.wxl.securitytest.pojo.ResponseModel;
import com.wxl.securitytest.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author wxl
 * @Date 2018/12/7
 **/
@Api(value = "文件接口（文件上传、下载、查看） FileController")
@RestController
@RequestMapping("/v1/file")
public class FileController extends BaseController {

  @Autowired
  private FileService fileservice;

  @ApiOperation(value = "图片查看")
  @RequestMapping(value = "/showImage/{subsystem}/{randomFolder}/{folder}/{imageFile}.{prefix}", method = RequestMethod.GET)
  public void imageQuery(HttpServletResponse response,
      @ApiParam(value = "subsystem", name = "文件存放目录1（图片：img，音频：audio，视频：video，其它：file）") @PathVariable("subsystem") String subsystem,
      @ApiParam(value = "randomFolder", name = "文件上传时返回的目录2") @PathVariable("randomFolder") String randomFolder,
      @ApiParam(value = "folder", name = "文件上传时返回的目录3") @PathVariable("folder") String folder,
      @ApiParam(value = "imageFile", name = "文件上传时返回的目录4") @PathVariable("imageFile") String imageFile,
      @ApiParam(value = "suffix", name = "文件上传时返回的文件后缀") @PathVariable("prefix") String prefix)
      throws IllegalArgumentException, IOException {
    byte[] imageBytes = fileservice
        .fileQuery(subsystem + "/" + randomFolder + "/" + folder, imageFile, prefix);
    if (imageBytes == null) {
      return;
    }
    // 显示到页面上
    this.writeResponseFile(response, 1, imageBytes);
  }

  @ApiOperation(value = "音频查看")
  @RequestMapping(value = "/showAudio/{subsystem}/{randomFolder}/{folder}/{imageFile}.{prefix}", method = RequestMethod.GET)
  public void audioQuery(HttpServletResponse response,
      @ApiParam(value = "subsystem", name = "文件存放目录1（图片：img，音频：audio，视频：video，其它：file）") @PathVariable("subsystem") String subsystem,
      @ApiParam(value = "randomFolder", name = "文件上传时返回的目录2") @PathVariable("randomFolder") String randomFolder,
      @ApiParam(value = "folder", name = "文件上传时返回的目录3") @PathVariable("folder") String folder,
      @ApiParam(value = "imageFile", name = "文件上传时返回的目录4") @PathVariable("imageFile") String imageFile,
      @ApiParam(value = "suffix", name = "文件上传时返回的文件后缀") @PathVariable("prefix") String prefix)
      throws IllegalArgumentException, IOException {
    byte[] imageBytes = fileservice
        .fileQuery(subsystem + "/" +subsystem + "/" + randomFolder + "/" + folder, imageFile, prefix);
    if (imageBytes == null) {
      return;
    }
    // 显示到页面上
    this.writeResponseFile(response, 2, imageBytes);
  }


  @ApiOperation(value = "转换音频查看")
  @RequestMapping(value = "/showAudio/convert/{subsystem}/{randomFolder}/{folder}/{imageFile}.{prefix}", method = RequestMethod.GET)
  public void convertAudioQuery(HttpServletResponse response,
      @ApiParam(value = "subsystem", name = "文件存放目录1（图片：img，音频：audio，视频：video，其它：file）") @PathVariable("subsystem") String subsystem,
      @ApiParam(value = "randomFolder", name = "文件上传时返回的目录2") @PathVariable("randomFolder") String randomFolder,
      @ApiParam(value = "folder", name = "文件上传时返回的目录3") @PathVariable("folder") String folder,
      @ApiParam(value = "imageFile", name = "文件上传时返回的目录4") @PathVariable("imageFile") String imageFile,
      @ApiParam(value = "suffix", name = "文件上传时返回的文件后缀") @PathVariable("prefix") String prefix)
      throws IllegalArgumentException, IOException {
    byte[] imageBytes = fileservice
        .fileQuery("convert/" +subsystem + "/" + randomFolder + "/" + folder, imageFile, prefix);
    if (imageBytes == null) {
      return;
    }
    // 显示到页面上
    this.writeResponseFile(response, 2, imageBytes);
  }


  @ApiOperation(value = "视频查看")
  @RequestMapping(value = "/showVideo/{subsystem}/{randomFolder}/{folder}/{imageFile}.{prefix}", method = RequestMethod.GET)
  public void videoQuery(HttpServletResponse response,
      @ApiParam(value = "subsystem", name = "文件存放目录1（图片：img，音频：audio，视频：video，其它：file）") @PathVariable("subsystem") String subsystem,
      @ApiParam(value = "randomFolder", name = "文件上传时返回的目录2") @PathVariable("randomFolder") String randomFolder,
      @ApiParam(value = "folder", name = "文件上传时返回的目录3") @PathVariable("folder") String folder,
      @ApiParam(value = "imageFile", name = "文件上传时返回的目录4") @PathVariable("imageFile") String imageFile,
      @ApiParam(value = "suffix", name = "文件上传时返回的文件后缀") @PathVariable("prefix") String prefix)
      throws IllegalArgumentException, IOException {
    byte[] imageBytes = fileservice
        .fileQuery(subsystem + "/" + randomFolder + "/" + folder, imageFile, prefix);
    if (imageBytes == null) {
      return;
    }
    // 显示到页面上
    this.writeResponseFile(response, 3, imageBytes);
  }

  @ApiOperation(value = "转换视频查看")
  @RequestMapping(value = "/showVideo/convert/{subsystem}/{randomFolder}/{folder}/{imageFile}.{prefix}", method = RequestMethod.GET)
  public void convertVideoQuery(HttpServletResponse response,
      @ApiParam(value = "subsystem", name = "文件存放目录1（图片：img，音频：audio，视频：video，其它：file）") @PathVariable("subsystem") String subsystem,
      @ApiParam(value = "randomFolder", name = "文件上传时返回的目录2") @PathVariable("randomFolder") String randomFolder,
      @ApiParam(value = "folder", name = "文件上传时返回的目录3") @PathVariable("folder") String folder,
      @ApiParam(value = "imageFile", name = "文件上传时返回的目录4") @PathVariable("imageFile") String imageFile,
      @ApiParam(value = "suffix", name = "文件上传时返回的文件后缀") @PathVariable("prefix") String prefix)
      throws IllegalArgumentException, IOException {
    byte[] imageBytes = fileservice
        .fileQuery("convert/" +subsystem + "/" + randomFolder + "/" + folder, imageFile, prefix);
    if (imageBytes == null) {
      return;
    }
    // 显示到页面上
    this.writeResponseFile(response, 3, imageBytes);
  }

  @ApiOperation(value = "这个方法可用于一次上传多个文件", notes = ""
      + "1、只能上传大小不超过10*1024KB的文件，也就是说如果上传的图片文件较大，那么客户端需要自行压缩一下<br>"
      + "2、上传成功后，这个请求将返回文件在服务器端重命名后的相对存储路径，"
      + "使用http://zuulservice:port/imageServer/v1/images/文件相对路径的方式就可以进行图片访问（还可以加相应特效哦）")
  @ApiParam(required = true, name = "file", value = "上传的文件对象，提交的表单信息中，请命名为file")
  @RequestMapping(value = "/{subsystem}", method = RequestMethod.POST)
  public ResponseModel fileUpload(
      @ApiParam(value = "subsystem", name = "文件存放目录（前端定义的路径，图片：img，音频：audio，视频：video，其它：file）") @PathVariable("subsystem") String subsystem,
      @ApiParam(value = "file", name = "文件") @RequestParam("file") MultipartFile[] file) {
    try{
      Validate.notEmpty(file, "you must upload least one file !");
      // 依次处理每个文件
      List<FilePojo> results = new ArrayList<>();
      for (MultipartFile multipartFile : file) {
        FilePojo result = null;
        result = this.fileservice.fileUpload(subsystem, multipartFile);
        results.add(result);
      }
      ResponseModel responseModel = this.buildHttpResult();
      responseModel.setData(results);
      return responseModel;
    } catch (Exception e) {
      return this.buildHttpResultForException(e);
    }
  }

  //=============================文件下载=============================//
  @ApiOperation(value = "文件下载")
  @RequestMapping(path = "/{subsystem}/{randomFolder}/{folder}/{imageFile}.{suffix}", method = RequestMethod.GET)
  public void fileDownload(
      HttpServletResponse response,
      @ApiParam(value = "subsystem", name = "文件上传时返回的目录1（由上传时定义，图片：img，音频：audio，视频：video，其它：file）") @PathVariable("subsystem") String subsystem,
      @ApiParam(value = "randomFolder", name = "文件上传时返回的目录2") @PathVariable("randomFolder") String randomFolder,
      @ApiParam(value = "folder", name = "文件上传时返回的目录3") @PathVariable("folder") String folder,
      @ApiParam(value = "imageFile", name = "文件上传时返回的目录4") @PathVariable("imageFile") String imageFile,
      @ApiParam(value = "suffix", name = "文件上传时返回的文件后缀") @PathVariable("suffix") String suffix)
      throws IllegalArgumentException, IOException {
    byte[] result = fileservice
        .fileQuery(subsystem + "/" + randomFolder + "/" + folder, imageFile, suffix);
    if (result == null) {
      return;
    }
    this.writeResponseFile(response, imageFile + "." + suffix, result);
  }
}
