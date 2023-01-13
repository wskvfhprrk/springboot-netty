package com.hejz.repository;

import com.hejz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface UserRepository extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {
}
