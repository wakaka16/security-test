package com.wxl.securitytest;

import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.service.UserService;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xulin
 * @date 2019/1/4 16:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
  @Autowired
  UserService userService;

  @Test
  public void create(){
    UserEntity user = new UserEntity();
    user.setId("111111111111111111");
    user.setName("lizhiqiang2");
    user.setPassword("1234562");
    user.setEmail("lizhiqiang2@sina.com");
    UserEntity userEntity = userService.create(user);
    System.out.println(userEntity);
  }
//  public T findOne(ID id) {
//    Assert.notNull(id, "The given id must not be null!");
//    Class<T> domainType = this.getDomainClass();
//    if (this.metadata == null) {
//      return this.em.find(domainType, id);
//    } else {
//      LockModeType type = this.metadata.getLockModeType();
//      Map<String, Object> hints = this.getQueryHints();
//      return type == null ? this.em.find(domainType, id, hints) : this.em.find(domainType, id, type, hints);
//    }
//  }
  /**
   * getOne通过session，缓存中获取
   */
//  public T getOne(ID id) {
//    Assert.notNull(id, "The given id must not be null!");
//    return this.em.getReference(this.getDomainClass(), id);
//    //调用
//    public <T> T getReference(Class<T> entityClass, Object primaryKey) {
//      this.checkOpen();
//
//      try {
//        return this.internalGetSession().load(entityClass, (Serializable)primaryKey);
//  }
}
