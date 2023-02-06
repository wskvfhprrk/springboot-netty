package com.hejz.service;

import com.hejz.common.Constant;
import com.hejz.dto.CheckingRulesDto;
import com.hejz.dto.CheckingRulesUpdateDto;
import com.hejz.entity.CheckingRules;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CheckingRulesServiceTest {

    @Autowired
    CheckingRulesService checkingRulesService;
    @Autowired
    RedisTemplate redisTemplate;

    @Order(1)
    @Test
    void findById() {
        CheckingRules byId = checkingRulesService.findById(1);
        Assert.notNull(byId, "不为空值");
    }

    @Test
    void getAll() {
    }

    @Order(3)
    @Test
    void getByCommonLength() {
        List<CheckingRules> checkingRules = checkingRulesService.getByCommonLength(1);
        Assert.isTrue(checkingRules.size() > 0, "查询到结果");
        Object o = redisTemplate.opsForValue().get(Constant.CHECKING_RULES_CACHE_KEY + "::" + 1);
        Assert.isTrue(((ArrayList) o).size() > 0, "查询到结果");

    }

    @Order(2)
    @Test
    void save() {
        CheckingRules checkingRules = new CheckingRules( "1", 1, 1, 1, 1, 1, 1);
        CheckingRulesDto dto=new CheckingRulesDto();
        BeanUtils.copyProperties(checkingRules,dto);
        CheckingRules save = checkingRulesService.save(dto);
        Assert.notNull(save, "数据已经保存");
        Object o = redisTemplate.opsForValue().get(Constant.CHECKING_RULES_CACHE_KEY + "::" + save.getCommonLength());
        Assert.isNull(o, "缓存中为空值");

    }

    @Order(4)
    @Test
    void update() {
        CheckingRules checkingRules = checkingRulesService.findById(1);
        List<CheckingRules> checkingRuless = checkingRulesService.getByCommonLength(checkingRules.getCommonLength());
        Assert.isTrue(checkingRuless.size() > 0, "查询到结果");
        checkingRules.setCommonLength(5);
        CheckingRulesUpdateDto dto=new CheckingRulesUpdateDto();
        BeanUtils.copyProperties(checkingRules,dto);
        CheckingRules update = checkingRulesService.update(dto);
        Assert.isTrue(update.getCommonLength()==5, "修改成功");
        Object o1 = redisTemplate.opsForValue().get(Constant.CHECKING_RULES_CACHE_KEY + "::" + update.getCommonLength());
        Assert.isNull(o1, "缓存中为空值");
    }

    @Order(5)
    @Test
    void delete() {
        CheckingRules checkingRules = checkingRulesService.findById(1);
        List<CheckingRules> checkingRuless = checkingRulesService.getByCommonLength(checkingRules.getCommonLength());
        Assert.isTrue(checkingRuless.size() > 0, "查询到结果");
        checkingRulesService.delete(1);
        CheckingRules byId = checkingRulesService.findById(1);
        Assert.isNull(byId, "查不到结果，已经删除");
        Object o1 = redisTemplate.opsForValue().get(Constant.CHECKING_RULES_CACHE_KEY + "::" + checkingRules.getCommonLength());
        Assert.isNull(o1, "缓存中为空值");
    }
}