package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.common.Const;
import com.wxl.securitytest.common.exception.CustomException;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.repository.UserRepository;
import com.wxl.securitytest.repository.UserRoleRepository;
import com.wxl.securitytest.service.RoleService;
import com.wxl.securitytest.service.UserRoleService;
import com.wxl.securitytest.service.UserService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangxiaolong
 * @date 2019/4/28 15:04
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

  @Autowired
  private UserRoleRepository userRoleRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private RoleService roleService;

  @Transactional
  @Override
  public void create(String userId, String roleId) {
    CustomException.notBlank(userId,"USERID"+ Const.ERROR_CAN_NOT_BE_NULL);
    CustomException.notBlank(roleId,"ROLEID"+ Const.ERROR_CAN_NOT_BE_NULL);
    UserEntity user = userService.getById(userId);
    RoleEntity role = roleService.getById(roleId);
    CustomException.notNull(user,"USER"+ Const.ERROR_CAN_NOT_BE_NULL);
    CustomException.notNull(role,"ROLE"+ Const.ERROR_CAN_NOT_BE_NULL);
    int count = userRoleRepository.countByUserIdAndRoleId(userId, roleId);
    CustomException.isTrue(count==0,Const.ERROR_CAN_NOT_REPEAT_BIND);
    userRoleRepository.create(userId,roleId);
  }
}
