package com.wxl.securitytest.service.provider;

/**
 * @author wangxiaolong
 * @date 2019/5/9 11:54
 * 当其它service需要使用到DemoService的方法，
 * 不能出现serviceA调用serviceB，serviceB调用serviceA的情况，
 * 就创建一个XxHelper类来提供给其它service调用，XxHelper本身不调用其它service
 */
public class DemoHelperImpl {

}
