package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.common.Const;
import com.wxl.securitytest.common.utils.StringValidateUtils;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.repository.UserRepository;
import com.wxl.securitytest.service.UserService;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private Md5PasswordEncoder passwordEncoder;

  @Override
  public UserEntity getById(String id) {
    Validate.notBlank(id,"ID不能为空");
    UserEntity one = userRepository.findOne(id);
    return one;
  }

  @Override
  public UserEntity getByName(String name) {
    Validate.isTrue(StringValidateUtils.isUserName(name), Const.ERROR_USERNAME);
    UserEntity byName = userRepository.getByName(name);
    return byName;
  }

  @Override
  public UserEntity getByEmail(String email) {
    Validate.isTrue(StringValidateUtils.isEmail(email), Const.ERROR_EMAIL);
    return userRepository.getByEmail(email);
  }

  @Override
  public UserEntity create(UserEntity user) {
    this.validateUser(user);
    // 密码需要加密
    String encodePassword = passwordEncoder.encodePassword(user.getPassword(), null);
    user.setPassword(encodePassword);
    this.userRepository.save(user);
    return user;
  }

  @Override
  public void modifyLoginTimeById(Date loginTime,String id) {
    Validate.notNull(loginTime,"登录时间不能为空");
    Validate.notBlank(id,"用户ID不能为空");
    userRepository.modifyLoginTimeById(id,loginTime);
  }

  @Override
  public List<UserEntity> findAll() {
    return userRepository.findAll();
  }


  /**
   * 校验用户(修改、新增都要通过此验证)
   *1、首先判断必须填写的信息是否都已经填写
   *2、检查数据库中是否存在重复信息
   * @param user
   */
  private void validateUser(UserEntity user){
    //1  非空and格式验证
    Validate.notNull(user,"用户信息不能为空");
    Validate.isTrue(user.getId()==null, "初始用户不应含有用户ID");
    Validate.isTrue(StringValidateUtils.isUserName(user.getName()), Const.ERROR_USERNAME);
    Validate.isTrue(StringValidateUtils.isPassword(user.getPassword()), Const.ERROR_PASSWORD);
    Validate.isTrue(StringValidateUtils.isEmail(user.getEmail()), Const.ERROR_EMAIL);
    //2  重复验证
    UserEntity existUserName = this.getByName(user.getName());
    Validate.isTrue(existUserName==null,"用户名已存在");
    UserEntity existEmail = this.getByEmail(user.getEmail());
    Validate.isTrue(existEmail==null,"邮箱已存在");
  }


}
