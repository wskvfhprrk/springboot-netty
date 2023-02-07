package com.hejz.dtu.repository;

import com.hejz.dtu.entity.CheckingRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据校检规则 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface CheckingRulesRepository extends JpaRepository<CheckingRules,Integer>,JpaSpecificationExecutor<CheckingRules> {
}
