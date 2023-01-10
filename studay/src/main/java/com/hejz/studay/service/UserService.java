package com.hejz.studay.service;

import com.hejz.studay.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface UserService {
    User Save(User user);
    void delete(Integer id);
    User findById(Integer id);
    Page<User> findPage(User user, int pageNo,int pageSize);
    List<User> findAll(User user);
}
