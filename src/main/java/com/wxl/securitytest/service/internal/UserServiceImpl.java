package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.common.utils.StringValidateUtils;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.repository.UserRepository;
import com.wxl.securitytest.service.UserService;
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
  public UserEntity getByName(String name) {
    Validate.notBlank(name,"用户名不能为空");
    UserEntity byName = userRepository.getByName(name);
    return byName;
  }

  @Override
  public UserEntity getByEmail(String email) {
    Validate.notBlank(email,"邮箱不能为空");
    return userRepository.getByEmail(email);
  }

  @Override
  public UserEntity create(UserEntity user) {
    Validate.notNull(user, "用户不能为空");
    this.validateUser(user);
    // 密码需要加密
    String encodePassword = passwordEncoder.encodePassword(user.getPassword(), null);
    user.setPassword(encodePassword);
    this.userRepository.saveAndFlush(user);
    return user;
  }

  /**
   * 校验用户是否已存在
   *1、首先判断必须填写的信息是否都已经填写
   *2、检查数据库中是否存在重复信息
   * @param user
   */
  private void validateUser(UserEntity user){
    //1
    Validate.isTrue(user.getId()==null, "初始用户不应含有用户ID");
    Validate.notBlank(user.getName(),"用户名不能为空");
    String namePattern = "^[a-zA-Z\\u4E00-\\u9FA5\\uf900-\\ufa2d·s]{2,12}$";
    Validate.isTrue(StringValidateUtils.validStrByPattern(user.getName(),
        namePattern), "用户名长度必须为2-12个字符，大小写英文或汉字");
    Validate.notBlank(user.getPassword(),"密码不能为空");
    Validate.isTrue(
        StringValidateUtils.validStrByPattern(user.getPassword(), "[a-zA-Z\\d]{6,12}"),
        "密码长度必须为6-12个字符，大小写英文字符或数字！");
    Validate.notBlank(user.getEmail(),"邮箱不能为空");
    //2
    UserEntity existUserName = getByName(user.getName());
    Validate.isTrue(existUserName==null,"用户名已存在");
    UserEntity existEmail = getByEmail(user.getEmail());
    Validate.isTrue(existEmail==null,"邮箱已存在");
  }


}
