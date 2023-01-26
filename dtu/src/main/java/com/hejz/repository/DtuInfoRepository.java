package com.hejz.repository;

import com.hejz.entity.DtuInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface DtuInfoRepository extends JpaRepository<DtuInfo,Long>,JpaSpecificationExecutor<DtuInfo> {

    DtuInfo findAllByImei(String imei);
}
