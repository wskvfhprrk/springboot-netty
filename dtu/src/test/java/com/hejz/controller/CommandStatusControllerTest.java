package com.hejz.controller;

import com.hejz.entity.CommandStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommandStatusControllerTest {

    @Autowired
    CommandStatusController commandStatusController;

    @Test
    void getCommandStatusByImei() {
        List<CommandStatus> commandStatusByImei = commandStatusController.findAllByDtuId(1L);
        Assert.notEmpty(commandStatusByImei,"结果为空值！");
    }

    @Test
    void getCommandStatusById() {
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
    void deleteAllByImei() {
    }
}