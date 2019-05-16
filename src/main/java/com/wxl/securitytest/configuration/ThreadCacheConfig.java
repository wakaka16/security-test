package com.wxl.securitytest.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wangxiaolong
 * @date 2019/3/8 14:59
 */
@EnableAsync
@Configuration
public class ThreadCacheConfig {

  /**
   * 线程池可配可不配
   */
  @Bean(name = "threadCache")
  public ExecutorService ThreadCache() {
    ExecutorService executorService =
        new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());
    return executorService;
  }

}
