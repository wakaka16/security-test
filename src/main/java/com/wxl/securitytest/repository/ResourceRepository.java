package com.wxl.securitytest.repository;

import com.wxl.securitytest.entity.ResourceEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends
    JpaRepository<ResourceEntity, String>,
    JpaSpecificationExecutor<ResourceEntity> {

  public List<ResourceEntity> findByName(@Param("name")String name);

}
