package com.wxl.securitytest.common.aop;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wxl
 * 日志增强切面 =  切入点    +   增强器
 *      |             |              |
 * 这个类      =   方法注解  +   方法内代码（操作）
 *
 * 连接点（JoinPoint）：被增强类中可以被增强的方法（所有方法）
 * 切入点（PointCut）：被增强类中可以已经被增强的方法（需要增强的方法）
 * 增强/通知（Advance）：增强器（各种增强器）
 * 切面（Aspect）：PointCut+Advance
 *
 */
@Aspect
@Component
public class LoggerAdvice {

  private final static Logger LOG = LoggerFactory.getLogger(LoggerAdvice.class);

  private String parseParams(Object[] params) {
    if (null == params || params.length <= 0) {
      return "";
    }
    StringBuffer param = new StringBuffer("传入参数[{}] ");
    for (Object obj : params) {
      param.append(ToStringBuilder.reflectionToString(obj)).append("  ");
    }
    return param.toString();
  }

  /**
   * 在com.wxl.securitytest..*内，并且注解有loggerManage的进行通知增强
   * @param joinPoint
   * @param loggerManage
   */
  @Before(value = "within(com.wxl.securitytest..*) && @annotation(loggerManage)")
  public void addBeforeLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
    LOG.info("执行 " + loggerManage.description() + " 开始");
    LOG.info(joinPoint.getSignature().toString());
    LOG.info(parseParams(joinPoint.getArgs()));
  }

  @AfterReturning("within(com.wxl.securitytest..*) && @annotation(loggerManage)")
  public void addAfterReturningLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
    LOG.info("执行 " + loggerManage.description() + " 结束");
  }

  @AfterThrowing(pointcut = "within(com.wxl.securitytest..*) && @annotation(loggerManage)", throwing = "ex")
  public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManage loggerManage, Exception ex) {
    LOG.error("执行 " + loggerManage.description() + " 异常", ex);
  }
}
