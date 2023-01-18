package com.hejz.service.impl;

import com.hejz.entity.DtuInfo;
import com.hejz.service.DtuInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DtuInfoServiceImplTest {

    @Autowired
    private DtuInfoService dtuInfoService;
    @Test
    void findByImei() {
    }

    @Test
    void findById() {
        DtuInfo dtuInfo = dtuInfoService.findById(1L);
        System.out.println(dtuInfo);
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteByImei() {
    }
}