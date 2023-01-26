package com.hejz.repository;

import com.hejz.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * 角色 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface RoleRepository extends CrudRepository<Role,Integer>,JpaSpecificationExecutor<Role> {
}
