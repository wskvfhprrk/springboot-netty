package com.hejz.service.impl;

import com.hejz.enm.CommandTypeEnum;
import com.hejz.entity.CheckingRules;
import com.hejz.entity.Command;
import com.hejz.service.CommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CommandServiceImplTest {

    @Autowired
    private CommandService commandService;

    @Test
    void findAllByDtuId() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
//        Command command = commandService.save(new Command("普通厂商", "", "", "03 05 00 00 FF 00 8D D8",new CheckingRules(1), CommandTypeEnum.SENSOR_SENDS_COMMAND, "", "",null, null,true));
//        System.out.println(command.toString());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findPage() {
    }
}