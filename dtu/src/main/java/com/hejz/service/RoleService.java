package com.hejz.service;

import com.hejz.entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface RoleService {
    Role Save(Role role);
    void delete(Integer id);
    Role findById(Integer id);
    Page<Role> findPage(Role role, int pageNo,int pageSize);
    List<Role> findAll(Role role);
}
