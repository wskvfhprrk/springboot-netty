package com.hejz.controller;

import com.hejz.entity.SensorDataDb;
import com.hejz.service.SensorDataDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: dtu参数控制器
 */
@RestController
@RequestMapping("densorDataDb")
@Api("dtu参数控制器")
public class SensroDataDbController {

    @Autowired
    private SensorDataDbService densorDataDbService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据imei查询所有dtu信息")
    @GetMapping("all/{imei}")
    public List<SensorDataDb> getSensorDataDbByImei(@PathVariable String imei){
        return densorDataDbService.getSensorDataDbByImei(imei);
    }
    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public SensorDataDb getSensorDataDbById(@PathVariable("id") Long id){
        return densorDataDbService.getSensorDataDbById(id);
    }
    @ApiOperation("添加感器信息")
    @PostMapping
    public SensorDataDb save(@RequestBody SensorDataDb densorDataDb){
        return densorDataDbService.save(densorDataDb);
    }
    @ApiOperation("更新dtu信息")
    @PutMapping
    public SensorDataDb update(@RequestBody SensorDataDb densorDataDb){
        return densorDataDbService.update(densorDataDb);
    }
    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id){
        densorDataDbService.delete(id);
    }
    @ApiOperation("根据imei删除所有感器信息")
    @DeleteMapping("deleteByImei/{imei}")
    public void deleteByImei(@PathVariable("imei") String imei){
        densorDataDbService.deleteByImei(imei);
    }
}