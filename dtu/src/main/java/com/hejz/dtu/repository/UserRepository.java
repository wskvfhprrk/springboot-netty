package com.hejz.dtu.repository;

import com.hejz.dtu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface UserRepository extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {
}
