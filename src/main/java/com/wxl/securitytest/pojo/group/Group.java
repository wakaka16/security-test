package com.wxl.securitytest.pojo.group;

import javax.validation.GroupSequence;

/**
 * @author wangxiaolong
 * @date 2019/5/6 11:37
 * 按组先后（组时序）
 */
@GroupSequence(value = {First.class,Second.class})
public class Group {

}
