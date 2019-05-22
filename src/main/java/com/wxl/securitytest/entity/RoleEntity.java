package com.wxl.securitytest.entity;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
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

/**
 * 角色
 * 角色表（粗分资源）是权限的集合
 * @author wxl
 */
@Entity
@Table(name = "t_role")
public class RoleEntity extends BaseUuidEntity {

  private static final long serialVersionUID = -6450745644854563411L;

  /**
   * 角色名称
   */
  @Column(name = "name", length = 100,unique = true)
  private String name;

  /**
   * 角色描述
   */
  @Column(name = "description", length = 100)
  private String description;

  /**
   * 角色和用户的中间表
   */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "t_user_role", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
  @JsonIgnoreProperties("roles")
  private List<UserEntity> users;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
  @JsonIgnoreProperties("roles")
  private List<ResourceEntity> resources;

  /**
   * 创建人
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  @ApiModelProperty(hidden = true)
  private UserEntity creator;

  /**
   * 修改人
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modifier")
  @ApiModelProperty(hidden = true)
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
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<UserEntity> getUsers() {
    return users;
  }

  public void setUsers(List<UserEntity> users) {
    this.users = users;
  }

  public List<ResourceEntity> getResources() {
    return resources;
  }

  public void setResources(List<ResourceEntity> resources) {
    this.resources = resources;
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoleEntity that = (RoleEntity) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        Objects.equals(createDate, that.createDate) &&
        Objects.equals(modifyDate, that.modifyDate);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(name, description, createDate, modifyDate);
  }
}
