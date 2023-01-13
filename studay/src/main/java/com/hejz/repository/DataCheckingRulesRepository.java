package com.hejz.repository;

import com.hejz.entity.DataCheckingRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface DataCheckingRulesRepository extends JpaRepository<DataCheckingRules,Integer> ,JpaSpecificationExecutor<DataCheckingRules> {

    List<DataCheckingRules> findByCommonLength(Integer commonLength);

}