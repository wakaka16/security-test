package com.wxl.securitytest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxiaolong
 * @date 2019/1/14 17:57
 */
@Entity
@Table(name = "t_test")
public class Test extends UuidEntity{

  private static final long serialVersionUID = -8566548485291946469L;
  @Column(name = "name", length = 120,unique = true)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
