package com.wxl.securitytest.configuration;

import com.wxl.securitytest.service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @Author wxl
 * @Date 2018/12/13
 **/

/**
 * 安全配置：
 * 每一次访问url都会经过这里，
 * 不需要被保护的URL是可以直接访问
 * 被保护的任何URL在访问时，都会进入accessFail
 * 登陆点/login提供唯一入口
 */
@Configuration
@EnableWebSecurity(debug = true)//这个注解的作用等同于
@EnableGlobalMethodSecurity(prePostEnabled=true)//结合@PreAuthorize("hasRole(‘admin‘)")使用，
//
//@EnableGlobalMethodSecurity(prePostEnabled=true)
//         使用表达式时间方法级别的安全性 4个注解可用
//
//@PreAuthorize 在方法调用之前,基于表达式的计算结果来限制对方法的访问
//@PostAuthorize 允许方法调用,但是如果表达式计算结果为false,将抛出一个安全性异常
//@PostFilter 允许方法调用,但必须按照表达式来过滤方法的结果
//@PreFilter 允许方法调用,但必须在进入方法之前过滤输入值
public class SecurityConfig extends WebSecurityConfigurerAdapter{

  /**
   * 忽略权限判断的url
   */
  @Value("${author.ignoreUrls}")
  private String[] ignoreUrls;

  /**
   * 配置自己的登录访问接口
   * @param http
   * @throws Exception
   */
  protected void configure(HttpSecurity http) throws Exception {
    /**
     * ignoreUrl：不需要登录即可访问（任何人都可以访问）
     * 非ignoreUrl：必须用户登录
     * ----登陆之后：拥有对应的角色权限才能访问（根据CustomAccessDecisionManager中的decide）
     * ---------入库了没有绑定角色或者未入库的url会默认带有admin和ROLE_ANONYMOUS匿名角色权限
     * 我个人认为：如果没有对url绑定角色，那么所有的登录用户角色就应该都能访问()
     */
    http
        /**==================设定url的访问权限(请求授权)==================**/
        .authorizeRequests()
        //以下URL不需要被保护的资源(1、后台url 2、静态资源路径)
       .antMatchers(ignoreUrls).permitAll()
        //任何请求,都要拦截
        .anyRequest().authenticated()
        .and()

        /**==================设定登录后的url地址==================**/
        .formLogin()
        //  定义当需要用户登录时候，转到的登录页面。(未登录前，访问未授权的url都会重定向到此页面)
        .loginPage("/admin/views/login/login.html")
        //usernameParameter和passwordParameter来自页面，不配置默认为username、passWord
        .usernameParameter("username")
        .passwordParameter("password")
        // 提供给前端登录请求点在form表单中请求，会进入securityService
        .loginProcessingUrl("/login")
        // 登录失败，返回到这里
        .failureForwardUrl("/v1/security/loginFail")
        // 登录成功后，默认到这个URL，返回登录成功后的信息
        .successForwardUrl("/v1/security/loginSuccess")
        .and()

        /**==================设定登出后的url地址==================**/
        .logout()
        // 登出页面
        .logoutUrl("/v1/security/logout")
        // 登录成功后
        .logoutSuccessUrl("/v1/security/logoutSuccess")
        .and()

        /**==================关闭/开启csrf==================**/
        .csrf().csrfTokenRepository(this.tokenRepository())
//        .csrf()
//        .disable()
        .and()
        /**==================开启rememberMe==================**/
        .rememberMe()
        // 持续化登录，登录时间为100天
        .tokenValiditySeconds(100*24*60*60)
        .rememberMeCookieName("persistence")
        .alwaysRemember(true)
        ;
  }

  /**
   * CSRF漏洞:第3方服务获取本服务用户的cookie，再进行攻击本服务用户的信息
   * CSRF不对GET请求进行拦截
   * 动态口令
   * @return
   */
  @Bean
  public CookieCsrfTokenRepository tokenRepository(){
    CookieCsrfTokenRepository tokenRepository = new CookieCsrfTokenRepository();
    tokenRepository.setCookieHttpOnly(false);
    tokenRepository.setCookieName("X-XSRF-TOKEN");//项目名-SXRF-TOKEN
    tokenRepository.setHeaderName("X-XSRF-TOKEN");//项目名-SXRF-TOKEN
    return tokenRepository;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  /**
   * 用户密码的加密方式为MD5加密(spring boot 版本1.4.5)
   * @return
   */
  @Bean
  public Md5PasswordEncoder passwordEncoder() {
    return new Md5PasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider(){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setHideUserNotFoundExceptions(false);
    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(passwordEncoder());
    return  provider;
  }

  @Bean
  public UserDetailsService userDetailsService(){
    return new CustomUserDetailsService();
  }
}
