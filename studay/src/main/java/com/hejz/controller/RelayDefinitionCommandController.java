package com.hejz.controller;

import com.hejz.common.Constant;
import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.repository.RelayDefinitionCommandRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: 继电器定义命令参数控制器
 */
@RestController
@RequestMapping(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY)
@Api("继电器定义命令参数控制器")
public class RelayDefinitionCommandController {

    @Autowired
    private RelayDefinitionCommandRepository relayDefinitionCommandRepository;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据imei查询所有继电器定义命令信息")
    @GetMapping("all/{imei}")
    @Cacheable(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#imei", unless = "#result == null")
    public List<RelayDefinitionCommand> getRelayDefinitionCommandByImei(@PathVariable String imei) {
        return relayDefinitionCommandRepository.getByImei(imei);
    }

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public RelayDefinitionCommand getRelayDefinitionCommandById(@PathVariable("id") Long id) {
        return relayDefinitionCommandRepository.getById(id);
    }

    @ApiOperation("添加感器信息")
    @PostMapping
    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#result.imei")
    public RelayDefinitionCommand save(@RequestBody RelayDefinitionCommand relayDefinitionCommand) {
        return relayDefinitionCommandRepository.save(relayDefinitionCommand);
    }

    @ApiOperation("更新继电器定义命令信息")
    @PutMapping
    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#result.imei")
    public RelayDefinitionCommand update(@RequestBody RelayDefinitionCommand relayDefinitionCommand) {
        return relayDefinitionCommandRepository.save(relayDefinitionCommand);
    }

    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        RelayDefinitionCommand relayDefinitionCommand = getRelayDefinitionCommandById(id);
        redisTemplate.delete(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + relayDefinitionCommand.getImei());
        relayDefinitionCommandRepository.deleteById(id);
    }

    @ApiOperation("根据imei删除所有感器信息")
    @DeleteMapping("deleteByImei/{imei}")
    public void deleteByImei(@PathVariable("imei") String imei) {
        relayDefinitionCommandRepository.deleteByImei(imei);
    }
}
