package com.wxl.securitytest.service.security;

import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.service.RoleService;
import com.wxl.securitytest.service.UserService;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author wxl
 * @Date 2018/12/13
 **/

/**
 * 实现自己的用户登录的业务
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
    UserEntity user = this.userService.getByName(username);
    Validate.notNull(user, "用户名 " + username + " 未找到");//提示用户名或密码错误
    // 查询用户角色信息
    List<RoleEntity> roles = this.roleService.findByUser(user);
    Validate.notEmpty(roles, "用户没有角色信息，请联系客服人员！");
    //收集角色信息形成authorities集合对象
    List<SimpleGrantedAuthority> authorities = new LinkedList<>();
    for (RoleEntity role : roles) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
      authorities.add(authority);
    }
    //创建安全用户
    UserDetails securityDetails = new User(username, user.getPassword(), authorities);
    return securityDetails;
  }
}