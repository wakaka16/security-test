package com.wxl.securitytest.repository.internal;

import com.wxl.securitytest.entity.DemoEntity;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DemoDao {

  /**
   * 分页条件查询模板
   * @param params
   * @param pageable
   * @return
   */
  Page<DemoEntity> getByCondition(Map<String, Object> params, Pageable pageable);

}
