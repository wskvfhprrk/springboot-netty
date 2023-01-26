package com.hejz.repository;

import com.hejz.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface UserRepository extends CrudRepository<User,Integer>,JpaSpecificationExecutor<User> {
}
