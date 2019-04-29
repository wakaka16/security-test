package com.wxl.securitytest.repository.internal;

import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.repository.UserRepository;
import java.util.ArrayList;
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
 * @author wangxiaolong
 * @date 2019/4/28 11:54
 */
@Repository
public class UserDaoImpl implements UserDao {
  @Autowired
  private UserRepository userRepository;

  @Override
  public Page<UserEntity> findByCondition(Map<String, Object> condition, Pageable pageable) {
    Page<UserEntity> list = userRepository.findAll(new Specification<UserEntity>() {
      @Override
      public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        // 查询用户账号
        if (condition.get("account") != null) {
          predicates.add(cb.equal(root.get("account").as(String.class),String.valueOf(condition.get("account"))));
        }
        // 模糊查询用户名称
        if (condition.get("name") != null) {
          predicates.add(cb.like(root.get("name").as(String.class),
              "%" + String.valueOf(condition.get("name") + "%")));
        }
        if (condition.get("email") != null) {
          predicates.add(cb.equal(root.get("email").as(String.class),String.valueOf(condition.get("email"))));
        }
        // 遍历查询条件，查询语句
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return null;
      }
    }, pageable);
    return list;
  }
}
