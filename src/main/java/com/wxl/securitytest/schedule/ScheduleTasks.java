package com.wxl.securitytest.schedule;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * @author wxl
 * @EnableScheduling
 */
@Component
public class ScheduleTasks {
  /**
   * 日志
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTasks.class);


//  @Autowired
//  private FaceInfoService faceInfoService;
//
//  /**
//   * 人脸信息同步
//   */
//  @Scheduled(cron = "0 0 0 * * ?") // 凌晨同步
//  public void synchronousFaceInfo() {
//    List<FaceInfoEntity> faceInfoEntityList = new ArrayList<>();
//    try {
//      faceInfoEntityList = faceInfoService.synchronousFaceInfo(1);
//    } catch (Exception e) {
//      LOGGER.error(e.getMessage(), e);
//      throw new IllegalArgumentException(e);
//    }
//    LOGGER.info("定时器：人脸信息同步"+faceInfoEntityList);
//  }


}
