package com.wxl.securitytest.controller;

import com.wxl.securitytest.pojo.ResponseCode;
import com.wxl.securitytest.pojo.ResponseModel;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.entity.UuidEntity;
import com.wxl.securitytest.service.UserService;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;


/**
 * 基础controller 其它controller要共用的一些方法
 */
public class BaseController {

  /**
   * 日志:this.getClass() 可以定位到具体的controller
   */
  protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserService userService;

  /**
   * 验证操作者是否登陆，是否是运营商平台操作者
   */
  protected UserEntity verifyOperatorLogin(Principal operator) {
    Validate.notNull(operator, "登录过期!");
    String name = operator.getName();
    // 验证是否是后台操作者
    UserEntity currentOp =
        this.userService.getByName(name);
    Validate.notNull(currentOp, "当前登录的操作者不存在!");
    return currentOp;
  }


  //mvc相应
  protected ModelAndView buildHttpReslutForException(Exception e, String viewName) {
    String errorMsg = "";
    if (e != null) {
      errorMsg = e.getMessage();
    }
    e.printStackTrace();
    ModelAndView result = new ModelAndView();
    if (StringUtils.isNotBlank(viewName)) {
      result.setViewName(viewName);
    } else {
      result.setViewName("/weChat/views/errorMsg");
    }
    result.addObject("errorMsg", errorMsg);
    return result;
  }

  //http数据相应
  /**
   * =========================开始============================
   */
  protected <T extends UuidEntity> ResponseModel buildHttpResult(Iterable<T> page,
      String... properties) {
    ResponseModel result = new ResponseModel();

    if (page == null || properties == null) {
      result.setData(page);
      return result;
    }
    page.forEach(item -> {
      filterProperties(item, properties);
    });
    result.setData(page);
    return result;
  }

  protected <T extends UuidEntity> ResponseModel buildHttpResult(Collection<T> entities,
      String... properties) {
    ResponseModel result = new ResponseModel();

    if (entities == null || entities.isEmpty() || properties == null) {
      result.setData(entities);
      return result;
    }

    for (T entity : entities) {
      filterProperties(entity, properties);
    }

    result.setData(entities);
    return result;
  }

  protected <T extends UuidEntity> ResponseModel buildHttpResult(T entity, String... properties) {
    ResponseModel result = new ResponseModel();
    if (entity == null || properties == null) {
      return result;
    }

    // 过滤
    this.filterProperties(entity, properties);

    result.setData(entity);
    return result;
  }

  /**
   * 该方法不返回任何信息，只是告诉调用者，调用业务成功了。
   */
  protected ResponseModel buildHttpResult() {
    return new ResponseModel();  }

  /**
   * 当异常状况时，使用该方法构造返回值
   *
   * @param e 错误的异常对象描述
   * @return 组装好的异常结果
   */
  protected ResponseModel buildHttpResultForException(Exception e) {
    String errorMsg = "";
    if (e != null) {
      errorMsg = e.getMessage();
      LOG.error(e.getMessage(), e);
    }
    ResponseModel result =
        new ResponseModel( null, ResponseCode._501.getCode(), errorMsg);
    return result;
  }
/**============================结束=========================*/

  //去关联的方法
  /**=========================开始============================*/
  /**
   * 该工具用来去除实体对象中的属性关联。支持集合内部的对象属性去除。<br> 注意，进行属性引用去除的对象必须是UuidEntity的子类示例对象
   *
   * @param entity 目标对象
   * @param properties 要去除的属性<br>
   *
   * <pre>
   * <example>
   *   // 以下代码可以去除currentRole中直接引用的5个属相
   *   this.filterProperty
   *     (currentRole, new String[]{"agents","competences","creator","merchants","modifier"});
   *     <br/>
   *   // 以下代码可以去除currentRole中直接引用的3个对象，以及merchants属性集合中的modifier属性和creator属性
   *   this.filterProperty
   *     (currentRole, new String[]{"agents","competences","creator","merchants.modifier","merchants.creator"});
   * </example>
   * </pre>
   */
  private <T extends UuidEntity> void filterProperties(T entity, String... properties) {
    if (entity == null || properties == null) {
      return;
    }

    /*
     * 首先要对初始输入的属性列表进行初步处理： 1、排序 2、确定这个属性是在第几层对象中
     */
    // 1、排序
    Arrays.sort(properties);

    // 2、递归排除指定的属性
    Stack<Class<?>> stackClasses = new Stack<>();
    try {
      for (String property : properties) {
        stackClasses.push(entity.getClass());
        filterProperty(property, entity, stackClasses);
        stackClasses.pop();
      }
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    }
  }

  /**
   * 过滤相关属性
   */
  private void filterProperty(String property, Object currentObject, Stack<Class<?>> stackClasses)
      throws NoSuchMethodException, SecurityException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    // 如果条件成立，说明还要进入下一级对象，否则就是操作本级对象
    int nodeIndex;
    if ((nodeIndex = property.indexOf(".")) != -1) {
      String currentFieldName = property.substring(0, nodeIndex);
      String nextFieldName = property.substring(nodeIndex + 1);
      Field currentField = null;
      Class<?> fieldClass = null;
      Class<?> currentClass = null;
      try {
        currentField = this.findField(currentFieldName, currentObject.getClass());
        if (currentField == null) {
          return;
        }
        fieldClass = currentField.getType();
        currentClass = currentObject.getClass();
      } catch (NoSuchFieldException | SecurityException e) {
        throw new IllegalArgumentException("not found property: " + currentFieldName + " in object "
            + currentObject.getClass().getName());
      }

      // 取得下一级对象
      char[] chars = currentFieldName.toCharArray();
      chars[0] -= 32;
      Method getMethod = currentClass.getMethod("get" + String.valueOf(chars));
      Object nextObject = getMethod.invoke(currentObject);

      /*
       * 那么是不是进入内部呢？还要以以下判断条件为准: 1、这个属性必须是UuidEntity的子类 2、这个属性本来不为null 3、这个属性所对应的类没有在已进入的递归列表中
       */
      // 如果条件成立，说明是单一对象
      if (nextObject != null && nextObject instanceof UuidEntity
          && !stackClasses.contains(fieldClass)) {
        stackClasses.push(fieldClass);
        filterProperty(nextFieldName, nextObject, stackClasses);
        stackClasses.pop();
      }
      // 如果条件成立，说明这个属性是一个集合
      else if (nextObject != null && nextObject instanceof Collection) {
        Collection<?> collections = (Collection<?>) nextObject;
        for (Object propertyObject : collections) {
          Class<?> propertyClass = propertyObject.getClass();
          if (!(propertyObject instanceof UuidEntity)) {
            break;
          }
          stackClasses.push(propertyClass);
          filterProperty(nextFieldName, propertyObject, stackClasses);
          stackClasses.pop();
        }
      }
    }
    // 就在本级对象进行属性排除
    else {
      String currentFieldName = property;
      Field currentField = null;
      Class<?> fieldClass = null;
      Class<?> currentClass = null;
      try {
        currentField = this.findField(currentFieldName, currentObject.getClass());
        if (currentField == null) {
          return;
        }
        fieldClass = currentField.getType();
        currentClass = currentObject.getClass();
      } catch (NoSuchFieldException | SecurityException e) {
        throw new IllegalArgumentException("not found property: " + currentFieldName + " in object "
            + currentObject.getClass().getName());
      }

      // 如果执行到这里，就可以将属性设置为null了
      char[] chars = currentFieldName.toCharArray();
      chars[0] -= 32;
      Method getMethod = currentClass.getMethod("set" + String.valueOf(chars), fieldClass);
      getMethod.invoke(currentObject, new Object[]{null});
    }
  }

  /**
   * 该私有方法查询指定类中的指定字段名
   */
  private Field findField(String currentFieldName, Class<?> targetClass)
      throws NoSuchFieldException {
    Field currentField = null;
    try {
      currentField = targetClass.getDeclaredField(currentFieldName);
    } catch (NoSuchFieldException | SecurityException e) {

    }

    if (currentField == null) {
      Class<?> superClass = targetClass.getSuperclass();
      if (superClass != null) {
        return this.findField(currentFieldName, superClass);
      } else {
        throw new NoSuchFieldException(
            "not found property " + currentFieldName + " in class " + targetClass.getSimpleName());
      }
    }

    return currentField;
  }
/**============================结束=========================*/

//文件相应
/**=========================开始============================*/
  /**
   * 下载文件
   */
  protected void writeResponseFile(HttpServletResponse response, String fileName, byte[] result) {
    response.setContentType("application/octet-stream;charset=utf-8");
    response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
    OutputStream out = null;
    try {
      out = response.getOutputStream();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    try {
      out.write(result);
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
    }
  }

  /**
   * @param response HttpServletResponse
   * @param httpType http信息的格式。根据不同的文件类型，http信息格式不一样<br> 1：表示是一张图片 2：表示是一段语音 3：表示是可播放的mpg4视频
   * @param result 写入的byte信息
   */
  protected void writeResponseFile(HttpServletResponse response, int httpType, byte[] result) {
    if (httpType == 1) {
      response.setContentType("image/jpeg;charset=utf-8");
    } else if (httpType == 2) {
      response.setContentType("audio/mp3;charset=utf-8");
    } else if (httpType == 3) {
      response.setContentType("video/mpeg4;charset=utf-8");
    } else {
      throw new IllegalArgumentException("未知文件类型");
    }

    OutputStream out = null;
    try {
      out = response.getOutputStream();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    try {
      out.write(result);
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
    }
  }
  /**============================结束=========================*/

//判断是否是微信扫码
  protected boolean isWeChat(HttpServletRequest request) {
    String userAgent = request.getHeader("user-agent");
    // 微信扫码
    if (userAgent.contains("MicroMessenger")) {
      return true;
    }
    return false;

  }
}
