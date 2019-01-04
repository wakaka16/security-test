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
    user.setName("lizhiqiang");
    user.setPassword("123456");
    user.setEmail("lizhiqiang@sina.com");
    user.setDob(new Date());
    UserEntity userEntity = userService.create(user);
    System.out.println(userEntity);
  }

}
