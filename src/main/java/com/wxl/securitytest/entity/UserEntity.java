package com.wxl.securitytest.entity;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 管理员用户
 */
@Entity
@Table(name = "t_user")
public class UserEntity extends UuidEntity {

  private static final long serialVersionUID = -6450745644854563411L;

  @Column(name = "name", length = 120,unique = true)
  private String name; //用户名
  @Column(name = "email", length = 50,unique = true)
  private String email;//用户邮箱
  @Column(name = "password", length = 120)
  private String password;//用户密码
  @Column(name = "login_time", length = 10)
  private Date loginTime;//登录时间
  //角色和用户的关系.
  /**
   * 关于懒加载和积极加载：
   * LAZY:只有在@Tansational注解中通过get方法调用才会产生 如：user.getRoles()
   * EAGER:在查询user时，直接就查询出了roles
   */
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
  private Set<RoleEntity> roles;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "test_id")
  private Test test;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }

  public Set<RoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleEntity> roles) {
    this.roles = roles;
  }

  public Test getTest() {
    return test;
  }

  public void setTest(Test test) {
    this.test = test;
  }
}