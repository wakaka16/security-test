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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
  @Temporal(TemporalType.DATE)
  //第一种：@Temporal(TemporalType.DATE)——》实体类会封装成日期“yyyy-MM-dd”的 Date类型。
  //第二种：@Temporal(TemporalType.TIME)——》实体类会封装成时间“hh-MM-ss”的 Date类型。
  //第三种：@Temporal(TemporalType.TIMESTAMP)——》实体类会封装成完整的时间“yyyy-MM-dd hh:MM:ss”的 Date类型。
  @Column(name = "dob", length = 10)
  private Date dob;//时间
  //角色和用户的关系.
  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
  private Set<RoleEntity> roles;

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

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public Set<RoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleEntity> roles) {
    this.roles = roles;
  }
}