package com.hejz.controller;

import com.hejz.entity.CommandStatus;
import com.hejz.service.CommandStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: 继电器参数控制器
 */
@RestController
@RequestMapping("commandStatus")
@Api("继电器参数控制器")
public class CommandStatusController {

    @Autowired
    private CommandStatusService commandStatusService;

    @ApiOperation("根据imei查询所有感器信息")
    @GetMapping("all/{imei}")
    public List<CommandStatus> getCommandStatusByImei(@PathVariable String imei) {
        return commandStatusService.findByImei(imei);
    }

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public CommandStatus getCommandStatusById(@PathVariable("id") Long id) {
        return commandStatusService.findById(id);
    }

    @ApiOperation("添加感器信息")
    @PostMapping
    public CommandStatus save(@RequestBody CommandStatus commandStatus) {
        return commandStatusService.save(commandStatus);
    }

    @ApiOperation("更新继电器信息")
    @PutMapping
    public CommandStatus update(@RequestBody CommandStatus commandStatus) {
        return commandStatusService.update(commandStatus);
    }

    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        commandStatusService.delete(id);
    }

    @ApiOperation("根据imei删除所有感器信息")
    @DeleteMapping("deleteByImei/{imei}")
    public void deleteByImei(@PathVariable("imei") String imei) {
        commandStatusService.deleteByImei(imei);
    }
}
