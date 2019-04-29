package com.wxl.securitytest.configuration;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

/**
 * @Author wxl
 * @Date 2018/12/17
 **/

/**
 * ssl应用的tomcat和应用配置
 */
//@Configuration
public class SSLConfig {
  /**
   * http重定向到https
   * 等同于web.xml配置
   * @return
   */
  @Bean
  public EmbeddedServletContainerFactory servletContainer() {
    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
      @Override
      protected void postProcessContext(Context context) {
        SecurityConstraint constraint = new SecurityConstraint();
        constraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        constraint.addCollection(collection);
        context.addConstraint(constraint);
      }
    };
    tomcat.addAdditionalTomcatConnectors(httpConnector());
    return tomcat;
  }

  /**
   * 给服务开通一个http的端口8081
   * 当监听到8081的端口访问后，重定向到8899的ssl端口
   * tomcat server.xml设置
   * @return
   */
  @Bean
  public Connector httpConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    //Connector监听的http的端口号
    connector.setPort(8081);
    connector.setSecure(false);
    //监听到http的端口号后转向到的https的端口号
    connector.setRedirectPort(8899);
    return connector;
  }
}
