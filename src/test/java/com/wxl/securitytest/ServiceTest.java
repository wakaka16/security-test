package com.wxl.securitytest;


import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.repository.UserRepository;
import com.wxl.securitytest.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxl
 * @date 2019/1/4 16:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = {"test"})
public class ServiceTest {

  /**
   * 桩模块：加入dao层已经完成的情况下测试service层
   */
  @MockBean
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

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


  /**
   * 测试service或者工具类可以使用这种桩模型进行测试
   */
  @Test
  public void findAll(){
    //准备数据（桩模型测试）
    List<UserEntity> userList = new ArrayList<>();
    UserEntity user = new UserEntity();
    user.setAccount("3433344");
    userList.add(user);

    //当userRepository被调用时返回，已准备到的数据
    Mockito.when( userRepository.findAll()).thenReturn(userList);

    //userService调用
    List<UserEntity> userList2 = userService.listAll();

    //断言测试
    Assert.assertTrue(userList.size()==userList2.size());
    Assert.assertTrue(userList.size()==1);

  }




}
