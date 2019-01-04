package com.wxl.securitytest.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/
@MappedSuperclass//
//基于代码复用和模型分离的思想，
// 在项目开发中使用JPA的@MappedSuperclass注解将实体类的多个属性分别封装到不同的非实体类中。
public abstract class UuidEntity implements Serializable {

  private static final long serialVersionUID = 2592506859768286804L;

  //抽象实体层模型（MySQL主键）的编号信息ID.
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

}
