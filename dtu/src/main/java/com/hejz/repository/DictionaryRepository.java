package com.hejz.repository;

import com.hejz.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * 数据字典 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface DictionaryRepository extends CrudRepository<Dictionary,Long>,JpaSpecificationExecutor<Dictionary> {
}
