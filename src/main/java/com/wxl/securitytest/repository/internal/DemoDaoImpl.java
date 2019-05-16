package com.wxl.securitytest.repository.internal;

import com.wxl.securitytest.entity.DemoEntity;
import com.wxl.securitytest.repository.DemoRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

/**
 * @author wxl
 */
@Repository
public class DemoDaoImpl implements DemoDao {

  @Autowired
  private DemoRepository demoRepository;

  @Override
  public Page<DemoEntity> getByCondition(Map<String, Object> params, Pageable pageable) {
    Page<DemoEntity> list =
        demoRepository.findAll(new Specification<DemoEntity>() {
          @Override
          public Predicate toPredicate(Root<DemoEntity> root, CriteriaQuery<?> query,
              CriteriaBuilder cb) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            //将懒加载的对象加载
            Class<?> clazz = query.getResultType();
            if (clazz.equals(DemoEntity.class)) {
              root.fetch("modifier", JoinType.LEFT);
              root.fetch("reporter", JoinType.LEFT);
              root.fetch("police", JoinType.LEFT);
              root.fetch("handlers", JoinType.LEFT);
            }
            // 创建报案时间区间
            if (params.get("createDate") != null) {
              predicates.add(cb
                  .greaterThanOrEqualTo(root.get("createDate"), (Date) params.get("createDate")));
            }
            if (params.get("endDate") != null) {
              predicates.add(cb
                  .lessThanOrEqualTo(root.get("createDate"), (Date) params.get("endDate")));
            }

            // 创建归档时间区间
            if (params.get("createArchiveDate") != null) {
              predicates.add(cb
                  .greaterThanOrEqualTo(root.get("archiveDate"),
                      (Date) params.get("createArchiveDate")));
            }
            if (params.get("createArchiveDate") != null) {
              predicates.add(cb
                  .lessThanOrEqualTo(root.get("archiveDate"), (Date) params.get("endArchiveDate")));
            }
            if (params.get("unitId") != null) {
              predicates.add(cb.equal(root.get("police").get("unit").get("id").as(String.class),
                  (String)params.get("unitId")));
            }
            if (params.get("score") != null) {
              predicates.add(cb.equal(root.get("score").as(Integer.class),
                  (Integer)params.get("score")));
            }

            // 遍历查询条件，查询语句
            query.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
          }
        }, pageable);
    return list;
  }
}
