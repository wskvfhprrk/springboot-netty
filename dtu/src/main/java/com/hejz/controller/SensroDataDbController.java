package com.hejz.controller;

import com.hejz.entity.SensorDataDb;
import com.hejz.service.SensorDataDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: dtu参数控制器
 */
@RestController
@RequestMapping("densorDataDb")
@Api(tags ="dtu参数控制器")
public class SensroDataDbController {

    @Autowired
    private SensorDataDbService densorDataDbService;

    @ApiOperation("根据dtuId查询所有dtu信息")
    @GetMapping("all/{dtuId}")
    public List<SensorDataDb> findAllByDtuId(@PathVariable Long dtuId) {
        return densorDataDbService.findAllByDtuId(dtuId);
    }

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public SensorDataDb getSensorDataDbById(@PathVariable("id") Long id) {
        return densorDataDbService.getById(id);
    }

    @ApiOperation("添加感器信息")
    @PostMapping
    public SensorDataDb save(@RequestBody SensorDataDb densorDataDb) {
        return densorDataDbService.save(densorDataDb);
    }

    @ApiOperation("更新dtu信息")
    @PutMapping
    public SensorDataDb update(@RequestBody SensorDataDb densorDataDb) {
        return densorDataDbService.update(densorDataDb);
    }

    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        densorDataDbService.delete(id);
    }

    @ApiOperation("根据dtuId删除所有感器信息")
    @DeleteMapping("deleteAllByImei/{dtuId}")
    public void deleteAllByImei(@PathVariable("dtuId") Long dtuId) {
        densorDataDbService.deleteAllByDtuId(dtuId);
    }
}
