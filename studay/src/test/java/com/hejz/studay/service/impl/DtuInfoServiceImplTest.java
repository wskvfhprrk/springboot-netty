package com.hejz.studay.service.impl;

import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.service.DtuInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DtuInfoServiceImplTest {

    @Autowired
    private DtuInfoService dtuInfoService;
    @Test
    void getByImei() {
    }

    @Test
    void getById() {
        DtuInfo dtuInfo = dtuInfoService.getById(1L);
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