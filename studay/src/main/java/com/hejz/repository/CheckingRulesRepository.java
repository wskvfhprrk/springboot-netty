package com.hejz.repository;

import com.hejz.entity.CheckingRules;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface CheckingRulesRepository extends CrudRepository<CheckingRules,Integer>,JpaSpecificationExecutor<CheckingRules> {

    List<CheckingRules> findByCommonLength(Integer commonLength);

}