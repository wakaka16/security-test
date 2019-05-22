package com.wxl.securitytest.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wangxiaolong
 * @date 2019/5/5 16:58
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  private DemoArgumentResolver demoArgumentResolver;


  @Override
  public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

  }

  @Override
  public void configureContentNegotiation(
      ContentNegotiationConfigurer contentNegotiationConfigurer) {

  }

  @Override
  public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

  }

  @Override
  public void configureDefaultServletHandling(
      DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

  }

  @Override
  public void addFormatters(FormatterRegistry formatterRegistry) {

  }

  @Override
  public void addInterceptors(InterceptorRegistry interceptorRegistry) {

  }

  /**
   * 添加静态资源--过滤swagger-api (开源的在线API文档)
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //过滤swagger
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");

    registry.addResourceHandler("/swagger-resources/**")
        .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");

    registry.addResourceHandler("/swagger/**")
        .addResourceLocations("classpath:/META-INF/resources/swagger*");

    registry.addResourceHandler("/v2/api-docs/**")
        .addResourceLocations("classpath:/META-INF/resources/v2/api-docs/");

  }

  /**
   * 支持跨域
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        //允许cookie跨域
        .allowCredentials(true)
        .allowedOrigins("*")
        .allowedMethods("*")
        .maxAge(3600 * 24);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

  }

  /**
   * 添加参数解析器
   */
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(demoArgumentResolver);
  }

  @Override
  public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

  }

  /**
   * 配置消息转换器--这里我用的是alibaba 开源的 fastjson
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    //1.需要定义一个convert转换消息的对象;
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    //2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteNullListAsEmpty,
        SerializerFeature.WriteDateUseDateFormat);
    //3处理中文乱码问题
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    //4.在convert中添加配置信息.
    fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
    fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
    //5.将convert添加到converters当中.
    converters.add(fastJsonHttpMessageConverter);
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

  }

  @Override
  public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

  }

  @Override
  public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

  }

  @Override
  public Validator getValidator() {
    return null;
  }

  @Override
  public MessageCodesResolver getMessageCodesResolver() {
    return null;
  }
}
