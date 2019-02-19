package com.wxl.securitytest.common.utils.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author wxl
 * @Date 2018/12/21
 **/
@Component
public class MediaFactory {
  @Autowired
  private  AudioSh audioSh;
  @Autowired
  private  VideoSh videoSh;

  public Multimedia getMediaHandler(Integer type){
    switch (type){
      case Multimedia.TYPE_AUDIO:
        return audioSh;
      case Multimedia.TYPE_VEDIO:
        return videoSh;
        default:
          throw new IllegalArgumentException("media type is not define");
    }
  }
}
