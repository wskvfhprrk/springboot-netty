package com.hejz.dtu.service;

import com.hejz.dtu.dto.RoleFindByPageDto;
import com.hejz.dtu.entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface RoleService {
    Role Save(Role role);
    void delete(Integer id);
    Role findById(Integer id);
    Page<Role> findPage(RoleFindByPageDto dto);
}
