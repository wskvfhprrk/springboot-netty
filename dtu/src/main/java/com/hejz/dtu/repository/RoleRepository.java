package com.hejz.dtu.repository;

import com.hejz.dtu.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 角色实体类 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface RoleRepository extends JpaRepository<Role,Integer>,JpaSpecificationExecutor<Role> {
}
