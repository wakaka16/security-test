package com.wxl.securitytest.repository.internal;

import com.wxl.securitytest.entity.UserEntity;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author wangxiaolong
 * @date 2019/4/28 11:54
 */
public interface UserDao {

  /**
   * 分页条件查询用户
   * @param condition 条件
   * @param pageable 分页信息 如果有多个参数，spring jpa 建议放在最后
   * @return 分页用户信息
   */
  Page<UserEntity> findByCondition(Map<String, Object> condition, Pageable pageable);
}
