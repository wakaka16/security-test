package com.wxl.securitytest.service.internal;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @author wangxiaolong
 * @date 2019/5/20 11:57
 * spring 缓存测试
 * 使用spring cache
 * key=user::id
 */
@Service
@CacheConfig(cacheNames = "user")
public class CacheService {

//  @Cacheable(key = "#id")
//  public UserEntity getById(String id){
//
//  }
//
//  /**
//   * 1、定时清除缓存
//   * 2、删除时更新缓存
//   * @param id
//   */
//  @Scheduled(cron = "")
//  @CacheEvict(key = "#id")
//  public void deleteById(String id){
//
//  }
//
//  /**
//   * 修改时更新缓存
//   * @param user
//   * @return
//   */
//  @CachePut(key = "#user.id")
//  public UserEntity modify(UserEntity user){
//
//  }

}
