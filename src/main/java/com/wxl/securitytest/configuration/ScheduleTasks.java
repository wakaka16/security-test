package com.wxl.securitytest.configuration;

import com.vanda.alarm.service.FormIdService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author wxl
 * @Date 2018/12/25
 **/
@Component
@EnableScheduling
public class ScheduleTasks {
  /**
   * 日志
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTasks.class);

  @Autowired
  private FormIdService formIdService;
  /**
   * 过期formId清理
   */
  @Scheduled(cron = "0 */30 * * * ? ") // 每半小时运行一次
  public void deleteFormId() {
    Date createDate = new Date(System.currentTimeMillis() - 518400 * 1000);//6天
    try {
      // 删除数据库
      formIdService.deleteByCreateDateBefore(createDate);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new IllegalArgumentException(e);
    }
    LOGGER.info("定时器：清理过期FormId");
  }
}
