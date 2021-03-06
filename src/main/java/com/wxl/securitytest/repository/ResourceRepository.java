package com.wxl.securitytest.repository;

import com.wxl.securitytest.entity.ResourceEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wxl
 */
@Repository
public interface ResourceRepository extends
    JpaRepository<ResourceEntity, String>,
    JpaSpecificationExecutor<ResourceEntity> {

  /**
   *
   * @param url
   * @return
   */
  List<ResourceEntity> findByUrl(@Param("url")String url);
}
