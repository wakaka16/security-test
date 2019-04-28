package com.wxl.securitytest.entity;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 角色
 * 角色表（粗分资源）是权限的集合
 */
@Entity
@Table(name = "t_role")
public class RoleEntity extends UuidEntity {

  private static final long serialVersionUID = -6450745644854563411L;

  //角色名称
  @Column(name = "name", length = 100,unique = true)
  private String name;
  @Column(name = "description", length = 100)
  //角色描述
  private String description;
  //角色和用户的中间表
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "t_user_role", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private Set<UserEntity> users;

  //角色和资源的中间表
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "t_role_resource", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "resource_id")})
  private Set<ResourceEntity> resources;

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

  public Set<UserEntity> getUsers() {
    return users;
  }

  public void setUsers(Set<UserEntity> users) {
    this.users = users;
  }

  public Set<ResourceEntity> getResources() {
    return resources;
  }

  public void setResources(Set<ResourceEntity> resources) {
    this.resources = resources;
  }
}
