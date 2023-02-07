package com.hejz.dtu.service.impl;

import com.hejz.dtu.service.CommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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