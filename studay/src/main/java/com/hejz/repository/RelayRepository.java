package com.hejz.repository;

import com.hejz.entity.Relay;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface RelayRepository extends CrudRepository<Relay, Long>, JpaSpecificationExecutor<Relay> {

    List<Relay> findAlByDtuId(Long dtuId);

    void deleteAllByDtuId(Long dtuId);
}
