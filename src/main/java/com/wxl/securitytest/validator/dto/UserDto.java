package com.wxl.securitytest.validator.dto;

import com.wxl.securitytest.entity.UserEntity;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * @author wangxiaolong
 * @date 2019/5/21 10:15
 * 用户数据传输对象
 * 将用户的数据传递给Entity
 */
public class UserDto {
  /**
   * 用户名
   */
  private String account;

  /**
   * 名称
   */
  private String name;

  /**
   * 用户邮箱
   */
  private String email;

  /**
   * 用户密码
   */
  private String password;

  /**
   * 登录时间
   */
  private Date lastLoginTime;

  /**
   * 创建人
   */
  private UserEntity creator;

  /**
   * 修改人
   */
  private UserEntity modifier;

  /**
   * 创建时间
   */
  private Date createDate = new Date();

  /**
   * 修改时间
   */
  private Date modifyDate;

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


  public UserEntity convertToUserEntity(UserDto userDto){
    UserEntity user = new UserEntity();
    BeanUtils.copyProperties(userDto,user);
    return user;
  }
}
