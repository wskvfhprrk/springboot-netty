package com.hejz.controller;

import com.hejz.entity.Sensor;
import com.hejz.service.SensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: 感应器参数控制器
 */
@RestController
@RequestMapping("sensor")
@Api("感应器参数控制器")
public class SensorController {

    @Autowired
    private SensorService sensorService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据imei查询所有感器信息")
    @GetMapping("all/{imei}")
    public List<Sensor> getSensorByImei(@PathVariable String imei) {
        return sensorService.findByImei(imei);
    }

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public Sensor getSensorById(@PathVariable("id") Long id) {
        return sensorService.findById(id);
    }

    @ApiOperation("添加感器信息")
    @PostMapping
    public Sensor save(@RequestBody Sensor sensor) {
        return sensorService.save(sensor);
    }

    @ApiOperation("更新感应器信息")
    @PutMapping
    public Sensor update(@RequestBody Sensor sensor) {
        return sensorService.update(sensor);
    }

    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        sensorService.delete(id);
    }

    @ApiOperation("根据imei删除所有感器信息")
    @DeleteMapping("deleteAllByImei/{imei}")
    public void deleteAllByImei(@PathVariable("imei") String imei) {
        sensorService.deleteAllByImei(imei);
    }
}
