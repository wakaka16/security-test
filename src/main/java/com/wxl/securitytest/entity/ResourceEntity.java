package com.wxl.securitytest.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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

  //权限URL串
  @Column(name = "name")
  private String name;
  //例如：POST或者POST|GET|DELETE
  @Column(name = "methods")
  private String methods;
  //权限对应的角色信息
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources")
  private Set<RoleEntity> roles;
  //备注
  @Column(name = "comment")
  private String comment;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
