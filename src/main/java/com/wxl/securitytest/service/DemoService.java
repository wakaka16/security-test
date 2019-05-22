package com.wxl.securitytest.service;

import com.wxl.securitytest.entity.DemoEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangxiaolong
 * @date 2019/4/28 10:54
 * 1、接口的目的就是方便开发者对该接口实现的方法进行查看
 * 2、加入实现类名称变化，也不需要在控制器中重新注入
 * 3、如果同一个接口注入2个实现类会发生错误，此时第二个实现类应该加入注入条件，当没有才进行注入
 */
public interface DemoService {
  /**
   * findNameById
   * 根据id查询（本service对象的）某个字段
   * findById
   * 根据id查询 查询（本service对象）
   * find >0
   * get 0 ||1
   *
   * 创建create
   * 修改modify
   * 修改一个字段 updateXXX
   * 删除delete
   */

  /**
   * 根据模板名称获取模板列表
   * @param name
   * @return
   */
  List<DemoEntity> findByName(String name);

}
