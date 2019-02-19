package com.wxl.securitytest.service.feign;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 微信小程序接口调用开发， 详细信息请参考：https://developers.weixin.qq.com/miniprogram/dev/api/sendUniformMessage.html
 */
@FeignClient(url = "https://api.weixin.qq.com", name = "weChat")
public interface WeChatApiService {

  /**
   * 获取接口调用凭证access_token
   */

  @RequestMapping(value = "/cgi-bin/token", method = RequestMethod.GET)
  public String getToken(@RequestParam("grant_type") String grant_type,
      @RequestParam("appid") String appid, @RequestParam("secret") String secret);

  /**
   * 获取openId和sessionkey
   */
  @RequestMapping(value = "/sns/jscode2session", method = RequestMethod.GET)
  public String getOpenIdAndSessionKey(
      @RequestParam("appid") String appid,
      @RequestParam("secret") String secret,
      @RequestParam("js_code") String js_code,
      @RequestParam("grant_type") String grant_type);


  /**
   * 发送信息给微信用户
   * @param access_token
   * @param context
   * @return
   */
  @RequestMapping(value = "/cgi-bin/message/wxopen/template/uniform_send?access_token={access_token}", method = RequestMethod.POST)
  public String sendMsg(
      @PathVariable("access_token") String access_token,
      @RequestBody JSONObject context);


}
