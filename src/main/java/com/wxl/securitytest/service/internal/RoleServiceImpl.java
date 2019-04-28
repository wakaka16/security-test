package com.wxl.securitytest.service.internal;

import com.wxl.securitytest.entity.ResourceEntity;
import com.wxl.securitytest.entity.RoleEntity;
import com.wxl.securitytest.entity.UserEntity;
import com.wxl.securitytest.repository.RoleRepository;
import com.wxl.securitytest.service.RoleService;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;
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
  public RoleEntity getById(String id) {
    Validate.notBlank(id, "ID不能为空");
    return roleRepository.findOne(id);
  }

  @Override
  public Set<RoleEntity> findByUser(UserEntity user) {
    return roleRepository.findDistinctByUsersEquals(user);
  }

  @Override
  public List<RoleEntity> findByResource(ResourceEntity resource) {
    return roleRepository.findDistinctByResourcesEquals(resource);
  }

  @Override
  public List<RoleEntity> listAll() {
    return roleRepository.listAll();
  }
}
