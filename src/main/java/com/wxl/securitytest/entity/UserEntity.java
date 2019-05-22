package com.wxl.securitytest.entity;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 管理员用户
 * @author Client
 */
@Entity
@Table(name = "t_user")
public class UserEntity extends BaseUuidEntity {

  private static final long serialVersionUID = -6450745644854563411L;

  /**
   * 用户名
   */
  @Column(name = "account", length = 120,unique = true)
  private String account;

  /**
   * 名称
   */
  @Column(name = "name", length = 50)
  private String name;

  /**
   * 用户邮箱
   */
  @Column(name = "email", length = 50,unique = true)
  private String email;

  /**
   * 用户密码
   */
  @JsonIgnore
  @Column(name = "password", length = 120)
  private String password;

  /**
   * 登录时间
   */
  @Column(name = "last_login_time", length = 10)
  private Date lastLoginTime;

  /**
   * 角色和用户的关系
   */
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
  @JsonIgnoreProperties("users")
  private List<RoleEntity> roles;

  /**
   * 创建人
   */
  @ApiModelProperty(hidden = true)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  private UserEntity creator;

  /**
   * 修改人
   */
  @ApiModelProperty(hidden = true)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modifier")
  private UserEntity modifier;

  /**
   * 创建时间
   */
  @Column(name = "create_date", nullable = false)
  private Date createDate = new Date();

  /**
   * 修改时间
   */
  @Column(name = "modify_date")
  private Date modifyDate;

  /**
   * getter setter
   */
  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

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

  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public void setRoles(List<RoleEntity> roles) {
    this.roles = roles;
  }

  public UserEntity getCreator() {
    return creator;
  }

  public void setCreator(UserEntity creator) {
    this.creator = creator;
  }

  public UserEntity getModifier() {
    return modifier;
  }

  public void setModifier(UserEntity modifier) {
    this.modifier = modifier;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  @Override
  public String toString() {
    return "UserEntity{" +
        "account='" + account + '\'' +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", lastLoginTime=" + lastLoginTime +
        ", roles=" + roles +
        ", creator=" + creator +
        ", modifier=" + modifier +
        ", createDate=" + createDate +
        ", modifyDate=" + modifyDate +
        '}';
  }
}