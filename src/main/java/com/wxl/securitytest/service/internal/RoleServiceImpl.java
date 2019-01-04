package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.repository.RoleRepository;
import com.wxl.securitytest.repository.UserRepository;
import com.wxl.securitytest.service.RoleService;
import com.wxl.securitytest.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wxl
 * @Date 2018/12/18
 **/
@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public List<RoleEntity> findByUser(UserEntity user) {
    return roleRepository.findDistinctByUsersEquals(user);
  }

  @Override
  public List<RoleEntity> findByResource(ResourceEntity resource) {
    return roleRepository.findDistinctByResourcesEquals(resource);
  }
}