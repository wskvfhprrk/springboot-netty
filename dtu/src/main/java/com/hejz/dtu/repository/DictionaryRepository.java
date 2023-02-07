package com.hejz.dtu.repository;

import com.hejz.dtu.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据字典实体类 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface DictionaryRepository extends JpaRepository<Dictionary,Long>,JpaSpecificationExecutor<Dictionary> {
}
