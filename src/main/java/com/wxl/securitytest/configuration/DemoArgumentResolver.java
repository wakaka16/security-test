package com.wxl.securitytest.configuration;

import com.wxl.securitytest.pojo.Demo;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author wangxiaolong
 * @date 2019/5/5 16:01 *处理器*方法参数解析器
 * 学习控制器的参数来源
 */
@Component
public class DemoArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(Demo.class);
  }

  /**
   * 通过request中的参数获取，
   * 提前把对象封装到参数中，
   * 然后反射调用controller进行参数填入，
   * 被解析对象不能含有setter方法
   * @param methodParameter
   * @param modelAndViewContainer
   * @param nativeWebRequest
   * @param webDataBinderFactory
   * @return
   * @throws Exception
   */
  @Override
  public Object resolveArgument(MethodParameter methodParameter,
      ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory) throws Exception {
    String name = nativeWebRequest.getParameter("name");
    return new Demo(name);
  }
}
