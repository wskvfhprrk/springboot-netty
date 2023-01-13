package com.hejz.repository;

import com.hejz.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据字典 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface DictionaryRepository extends JpaRepository<Dictionary,Long>,JpaSpecificationExecutor<Dictionary> {
}
