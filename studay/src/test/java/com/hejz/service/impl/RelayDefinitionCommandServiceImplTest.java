package com.hejz.service.impl;

import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.service.RelayDefinitionCommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RelayDefinitionCommandServiceImplTest {
    
    @Autowired
    RelayDefinitionCommandService relayDefinitionCommandService;

    @Test
    void findByImei() {
        String imei="865328063321359";
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByImei(imei);
        System.out.println(relayDefinitionCommands.size());
    }

    @Test
    void findById() {
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