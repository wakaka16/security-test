package com.wxl.securitytest.entity;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  @ApiModelProperty(hidden = true)
  private UserEntity creator;//创建人
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modifier")
  @ApiModelProperty(hidden = true)
  private UserEntity modifier;//修改人
  @Column(name = "create_date", nullable = false)
  private Date createDate = new Date();//创建时间
  @Column(name = "modify_date")
  private Date modifyDate;//修改时间


  /**
   * 关于懒加载和积极加载：
   * LAZY:只有在@Tansational注解中通过get方法调用才会产生 如：user.getRoles()
   * EAGER:在查询user时，直接就查询出了roles
   */
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
  private Set<RoleEntity> roles;//角色和用户的关系.


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
  //一个部门管辖范围
//  @ApiModelProperty(hidden = true)
//  @OneToOne(fetch = FetchType.LAZY,mappedBy="unit")
//  private JurisdictionEntity jurisdictions;
//
//  //所属部门
//  @ApiModelProperty(hidden = true)
//  @OneToOne
//  @JoinColumn(name="unit_id")
//  private UnitEntity unit;
}