package com.wxl.securitytest.entity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author wangxiaolong
 * @date 2019/4/28 10:26
 */
public class DemoEntity {

//  一个部门管辖范围
//  @ApiModelProperty(hidden = true)
//  @OneToOne(fetch = FetchType.LAZY,mappedBy="unit")
//  private JurisdictionEntity jurisdictions;
//
//  //所属部门
//  @ApiModelProperty(hidden = true)
//  @OneToOne
//  @JoinColumn(name="unit_id")
//  private UnitEntity unit;

//  @ManyToOne(fetch = FetchType.EAGER)
//  @JoinColumn(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
//  private Account account;

  /**
   * 关于懒加载和积极加载：
   * LAZY:只有在@Tansational注解中通过get方法调用才会产生 如：user.getRoles()
   * EAGER:在查询user时，直接就查询出了roles
   */
}
