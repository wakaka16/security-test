//package com.wxl.securitytest.repository.internal;
//
//import com.vanda.alarm.common.enums.AlarmType;
//import com.vanda.alarm.entity.EventEntity;
//import com.vanda.alarm.entity.PoliceEntity;
//import com.vanda.alarm.entity.ReporterEntity;
//import com.vanda.alarm.repository.EventRepository;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.JoinType;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class EventDaoImpl implements EventDao {
//
//  @Autowired
//  private EventRepository eventRepository;
//
//  @Override
//  public Page<EventEntity> getByConditions(Map<String, Object> params, Pageable pageable) {
//    Page<EventEntity> list =
//        eventRepository.findAll(new Specification<EventEntity>() {
//          @Override
//          public Predicate toPredicate(Root<EventEntity> root, CriteriaQuery<?> query,
//              CriteriaBuilder cb) {
//            List<Predicate> predicates = new ArrayList<Predicate>();
//            //将懒加载的对象加载
//            Class<?> clazz = query.getResultType();
//            if (clazz.equals(EventEntity.class)) {
//              root.fetch("modifier", JoinType.LEFT);
//              root.fetch("reporter", JoinType.LEFT);
//              root.fetch("police", JoinType.LEFT);
//              root.fetch("handlers", JoinType.LEFT);
//            }
//            if (params.get("alarmType") != null) {
//              predicates.add(cb
//                  .equal(root.get("alarmType").as(AlarmType.class),
//                      (AlarmType) params.get("alarmType")));
//            }
//            // 创建报案时间区间
//            if (params.get("createDate") != null) {
//              predicates.add(cb
//                  .greaterThanOrEqualTo(root.get("createDate"), (Date) params.get("createDate")));
//            }
//            if (params.get("endDate") != null) {
//              predicates.add(cb
//                  .lessThanOrEqualTo(root.get("createDate"), (Date) params.get("endDate")));
//            }
//
//            // 创建归档时间区间
//            if (params.get("createArchiveDate") != null) {
//              predicates.add(cb
//                  .greaterThanOrEqualTo(root.get("archiveDate"),
//                      (Date) params.get("createArchiveDate")));
//            }
//            if (params.get("createArchiveDate") != null) {
//              predicates.add(cb
//                  .lessThanOrEqualTo(root.get("archiveDate"), (Date) params.get("endArchiveDate")));
//            }
//
//            // 查询条件-报警人
//            if (params.get("reporters") != null) {
//              List<ReporterEntity> reporters = (List<ReporterEntity>) params.get("reporters");
//              for (ReporterEntity reporter : reporters) {
//                predicates.add(cb.equal(root.get("reporter").as(ReporterEntity.class),
//                    reporter));
//              }
//            }
//            // 查询条件-接警人
//            if (params.get("receiver") != null) {
//              predicates.add(cb.equal(root.get("police").as(PoliceEntity.class),
//                  (PoliceEntity)params.get("receiver")));
//            }
//            if (params.get("unitId") != null) {
//              predicates.add(cb.equal(root.get("police").get("unit").get("id").as(String.class),
//                  (String)params.get("unitId")));
//            }
//            if (params.get("score") != null) {
//              predicates.add(cb.equal(root.get("score").as(Integer.class),
//                  (Integer)params.get("score")));
//            }
//
//            // 遍历查询条件，查询语句
//            query.where(predicates.toArray(new Predicate[predicates.size()]));
//            return null;
//          }
//        }, pageable);
//    return list;
//  }
//}
