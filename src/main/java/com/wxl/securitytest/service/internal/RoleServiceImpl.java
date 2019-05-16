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
    Validate.notBlank(id, "ID can not be null or []");
    return roleRepository.findOne(id);
  }

  @Override
  public List<RoleEntity> findByUser(UserEntity user) {
    Validate.notNull(user, "user can not be null");
    return roleRepository.findDistinctByUsersEquals(user);
  }

  @Override
  public List<RoleEntity> findByResource(ResourceEntity resource) {
    Validate.notNull(resource, "resource can not be null");
    return roleRepository.findDistinctByResourcesEquals(resource);
  }

  @Override
  public RoleEntity create(RoleEntity role) {
    return  roleRepository.save(role);
  }

  @Override
  public RoleEntity getByName(String name) {
    Validate.notBlank(name, "name can not be null or []");
    return roleRepository.getByName(name);
  }

  @Override
  public List<RoleEntity> listAll() {
    return roleRepository.listAll();
  }
}
