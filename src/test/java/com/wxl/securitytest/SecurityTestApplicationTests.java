package com.wxl.securitytest;

import com.alibaba.fastjson.JSONObject;
import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.repository.ResourceRepository;
import com.wxl.securitytest.repository.RoleRepository;
import com.wxl.securitytest.repository.UserRepository;
import com.wxl.securitytest.service.UserService;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 在测试时，由于没有在主配置中配置数据源 因此选择在主配置激活test配置
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityTestApplicationTests {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private ResourceRepository resourceRepository;

  @Autowired
  private UserService userService;

  @Test
  public void userTest() {
    UserEntity user = new UserEntity();
    user.setName("18398031356");
    user.setEmail("wxl@163.com");
    user.setLoginTime(new Date());
    user.setPassword("wxl");
    //未给此用户设置角色role
    userRepository.save(user);
  }

  @Test
  public void RoleTest() {
    //===============定义管理员===============
    RoleEntity admin = new RoleEntity();
    admin.setName("ADMIN");
    admin.setDescription("管理员");
    //未把角色给任何人，也没有给角色资源
    roleRepository.save(admin);
    //===============定义超级管理员===============
    RoleEntity superAdmin = new RoleEntity();
    admin.setName("SUPER");
    admin.setDescription("超级管理员");
    roleRepository.save(superAdmin);
    //===============定义用户===============
    RoleEntity common = new RoleEntity();
    admin.setName("COMMON");
    admin.setDescription("普通");
    roleRepository.save(common);
  }


  @Test
  public void ResourceTest() {
    //  /admin的查看
    ResourceEntity resource1 = new ResourceEntity();
    resource1.setName("/admin");
    resource1.setMethods("GET");
    resource1.setComment("admin资源的查看");
    resourceRepository.save(resource1);
    //  /admin的POST|PATCH
    ResourceEntity resource2 = new ResourceEntity();
    resource2.setName("/admin");
    resource2.setMethods("PATCH|POST");
    resource2.setComment("admin资源的增加、修改");
    resourceRepository.save(resource2);
    //  /admin的DELETE
    ResourceEntity resource3 = new ResourceEntity();
    resource3.setName("/admin");
    resource3.setMethods("DELETE");
    resource3.setComment("admin资源的删除");
    resourceRepository.save(resource3);
  }

  /**
   * 由于更高级的角色，拥有resource越多
   * 如：COMMON 拥有 /admin GET
   *     ADMIN 拥有 /admin GET、/admin POST、/admin PATCH...
   * 因此：一个用户拥有的角色，其实是在取resource的并集
   */

  //获取用户的角色
  @Test
  public void findDistinctByUsersEquals(){
    UserEntity one = userRepository.findOne("56326ea8-6a12-4684-8c81-955836928c54");
    List<RoleEntity> roleList = roleRepository.findDistinctByUsersEquals(one);
    System.out.println(roleList.size());
  }


  @Test
  public void findOne(){
    UserEntity one = userRepository.findOne("9053854b-0fba-46bb-bbed-c126079c0407");
    Object o = JSONObject.toJSON(one);
    String s = JSONObject.toJSONString(o);
    System.out.println(s);
//    {"password":"e10adc3949ba59abbe56e057f20f883e",
// "dob":1546531200000,
// "roles":[],"name":"lizhiqiang",
// "id":"7b04c524-da71-4fc5-9dea-68aabc73d70c",
// "email":"lizhiqiang@sina.com"}


  }

  /**
   * 查询不到会抛出错误，建议使用findOne
   */
  @Test
  @Transactional
  public void getOne(){
    UserEntity one = userRepository.getOne("9053854b-0fba-46bb-bbed-c126079c0407");
    Object o = JSONObject.toJSON(one);
    String s = JSONObject.toJSONString(o);
    System.out.println(s);
  }

  @Test
  public void modifyLoginTimeById(){
    userService.modifyLoginTimeById(new Date(),"9053854b-0fba-46bb-bbed-c126079c0407");
  }

}

