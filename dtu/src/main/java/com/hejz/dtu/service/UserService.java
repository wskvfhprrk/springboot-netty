package com.hejz.dtu.service;

import com.hejz.dtu.dto.UserFindAllDto;
import com.hejz.dtu.dto.UserFindByPageDto;
import com.hejz.dtu.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface UserService {
    User save(User user);
    User update(User user);
    void delete(Integer id);
    User findById(Integer id);
    Page<User> findPage(UserFindByPageDto dto);

    List<User> findAll(UserFindAllDto dto);

    User getFindById(Integer userId);
}
