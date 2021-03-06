package com.wxl.securitytest.service.security;

import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.service.RoleService;
import com.wxl.securitytest.service.UserService;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 实现自己的用户登录的业务
 * 获取登录用户的角色权限
 * @author wxl
 * @date 2018/12/13
 */
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;
  @Autowired
  private RoleService roleService;

  /**
   * @param username 通过form表单提交，username==input的name="username"
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = this.userService.getByAccount(username);
    //(用户名)
    Validate.notNull(user, "account or password error");
    // 查询用户角色信息
    List<RoleEntity> roles =  this.roleService.findByUser(user);
    //收集角色信息形成authorities集合对象
    List<SimpleGrantedAuthority> authorities = new LinkedList<>();
    for (RoleEntity role : roles) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
      authorities.add(authority);
    }
    //添加匿名角色（可以访问所有未加入数据库、加入了但是没有分配角色的资源）
    authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
    //创建安全用户
    return new User(username, user.getPassword(), authorities);
  }
}
