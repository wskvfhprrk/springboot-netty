package com.hejz.studay.repository;

import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.entity.Relay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface DtuInfoRepository extends JpaRepository<DtuInfo,Long>,JpaSpecificationExecutor<DtuInfo> {
    DtuInfo getDtuInfoByImei(String imei);
}
