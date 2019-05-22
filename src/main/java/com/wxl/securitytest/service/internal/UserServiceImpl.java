package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.common.Const;
import com.wxl.securitytest.common.exception.CustomException;
import com.wxl.securitytest.common.utils.StringValidateUtils;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.repository.UserRepository;
import com.wxl.securitytest.repository.internal.UserDao;
import com.wxl.securitytest.service.UserService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  @Autowired
  private UserDao userDao;

  @Override
  public UserEntity getById(String id) {
    CustomException.notBlank(id,"ID"+Const.ERROR_CAN_NOT_BE_NULL);
    UserEntity one = userRepository.findOne(id);
    return one;
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
    String encodePassword = passwordEncoder.encodePassword(user.getPassword(), Const.PASSWORD_SALT);
    user.setPassword(encodePassword);
    this.userRepository.save(user);
    return user;
  }

  @Transactional(value = TxType.REQUIRED,rollbackOn = Exception.class)
  @Override
  public void updateLoginTimeById(String id) {
    CustomException.notBlank(id,"ID"+Const.ERROR_CAN_NOT_BE_NULL);
    userRepository.updateLastLoginTimeById(id);
  }

  @Override
  public Page<UserEntity> findByCondition(Map<String, Object> condition, Pageable pageable) {
    Page<UserEntity> pageList = userDao.findByCondition(condition, pageable);
    return pageList;
  }

  @Override
  public UserEntity getByAccount(String account) {
    CustomException.notBlank(account,"ACCOUNT"+Const.ERROR_CAN_NOT_BE_NULL);
    return userRepository.getByAccount(account);
  }

  @Transactional(value = TxType.REQUIRED,rollbackOn = Exception.class)
  @Override
  public List<UserEntity> listAll() {
    return userRepository.findAll();
  }


  /**
   * 校验用户(新增都要通过此验证)
   *1、首先判断必须填写的信息是否都已经填写
   *2、检查数据库中是否存在重复信息
   *3、密码加密
   * @param user
   */
  private void validateUser(UserEntity user){
    //1  非空and格式验证
    CustomException.notNull(user,"USER"+Const.ERROR_CAN_NOT_BE_NULL);
    //防止篡改他人信息
    CustomException.isTrue(user.getId()==null, "初始用户不应含有用户ID");
    CustomException.isTrue(StringValidateUtils.isAccount(user.getAccount()), Const.ERROR_ACCOUNT);
    CustomException.isTrue(StringValidateUtils.isPassword(user.getPassword()), Const.ERROR_PASSWORD);
    CustomException.isTrue(StringValidateUtils.isEmail(user.getEmail()), Const.ERROR_EMAIL);
    //2  重复验证
    UserEntity existAccount = this.getByAccount(user.getAccount());
    CustomException.isTrue(existAccount==null,Const.ERROR_ACCOUNT_HAS_REGISTER);
    UserEntity existEmail = this.getByEmail(user.getEmail());
    CustomException.isTrue(existEmail==null,Const.ERROR_EMAIL_HAS_REGISTER);
  }


}
