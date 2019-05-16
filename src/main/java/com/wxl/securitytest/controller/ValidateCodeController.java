package com.wxl.securitytest.controller;

import com.wxl.securitytest.configuration.validate.ImageCode;
import com.wxl.securitytest.configuration.validate.ValidateCodeGenerator;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaolong
 * @date 2019/3/27 17:19
 */
@RestController
@RequestMapping(value = "v1/validateCode")
public class ValidateCodeController extends BaseController {

  public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
  @Autowired
  private ValidateCodeGenerator imageCodeGenerator;

  /**
   * 图片验证码
   * @param request
   * @param response
   * @throws IOException
   */
  @GetMapping("/image")
  public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ImageCode imageCode = imageCodeGenerator.createCode(request);
    //将随机数 放到Session中
    request.getSession().setAttribute(SESSION_KEY,imageCode);
    //写给response 响应
    ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
  }

}
