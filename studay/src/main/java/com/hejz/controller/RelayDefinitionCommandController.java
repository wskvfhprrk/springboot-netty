package com.hejz.controller;

import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.service.RelayDefinitionCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: 继电器定义命令参数控制器
 */
@RestController
@RequestMapping("relayDefinitionCommand")
@Api("继电器定义命令参数控制器")
public class RelayDefinitionCommandController {

    @Autowired
    private RelayDefinitionCommandService relayDefinitionCommandService;

    @ApiOperation("根据imei查询所有继电器定义命令信息")
    @GetMapping("all/{imei}")
    public List<RelayDefinitionCommand> getRelayDefinitionCommandByImei(@PathVariable String imei) {
        return relayDefinitionCommandService.findByImei(imei);
    }

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public RelayDefinitionCommand getRelayDefinitionCommandById(@PathVariable("id") Long id) {
        return relayDefinitionCommandService.findById(id);
    }

    @ApiOperation("添加感器信息")
    @PostMapping
    public RelayDefinitionCommand save(@RequestBody RelayDefinitionCommand relayDefinitionCommand) {
        return relayDefinitionCommandService.save(relayDefinitionCommand);
    }

    @ApiOperation("更新继电器定义命令信息")
    @PutMapping
    public RelayDefinitionCommand update(@RequestBody RelayDefinitionCommand relayDefinitionCommand) {
        return relayDefinitionCommandService.update(relayDefinitionCommand);
    }

    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        relayDefinitionCommandService.delete(id);
    }

    @ApiOperation("根据imei删除所有感器信息")
    @DeleteMapping("deleteAllByImei/{imei}")
    @Transactional
    public void deleteAllByImei(@PathVariable("imei") String imei) {
        relayDefinitionCommandService.deleteAllByImei(imei);
    }
}
