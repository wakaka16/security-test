package com.wxl.securitytest.repository;

import com.wxl.securitytest.entity.DemoEntity;
import com.wxl.securitytest.entity.ResourceEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wxl
 * 数据库查询如果都出现了异常，
 * 那一定是逻辑有问题
 * 表设计不合理导致的
 */
@Repository
public interface DemoRepository extends
    JpaRepository<DemoEntity, String>,
    JpaSpecificationExecutor<DemoEntity> {

  /**
   * 0、1个用get 返回值entity
   * 1、多个用find 返回值list<entity>
   * @param name
   * @return
   */
  List<DemoEntity> findByName(String name);
}
