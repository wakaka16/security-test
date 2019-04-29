package com.wxl.securitytest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/

/**
 * 后台url资源
 * 权限表（细分资源）
 */
@Entity
@Table(name = "t_resource")
public class ResourceEntity extends UuidEntity {

  private static final long serialVersionUID = -2478353142674157920L;

  @Column(name = "url")
  private String url;//权限URL串

  @Column(name = "description", length = 100)
  private String description;//权限描述

  @Column(name = "methods")
  private String methods;//例如：POST或者POST|GET|DELETE

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "t_role_resource", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "resource_id")})
  @JsonIgnoreProperties("resources")
  private Set<RoleEntity> roles;//角色和资源的中间表

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

  //  getter setter
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getMethods() {
    return methods;
  }

  public void setMethods(String methods) {
    this.methods = methods;
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
}
