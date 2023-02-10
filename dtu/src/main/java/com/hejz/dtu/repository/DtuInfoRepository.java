package com.hejz.dtu.repository;

import com.hejz.dtu.entity.DtuInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * dtu信息 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface DtuInfoRepository extends JpaRepository<DtuInfo,Long>,JpaSpecificationExecutor<DtuInfo> {
    DtuInfo findByImei(String imei);
}
