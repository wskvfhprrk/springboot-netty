package com.hejz.controller;

import com.hejz.entity.DtuInfo;
import com.hejz.service.DtuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: dtu参数控制器
 */
@RestController
@RequestMapping("dtuInfo")
@Api("dtu参数控制器")
public class DtuInfoController {

    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据imei查询所有dtu信息")
    @GetMapping("all/{imei}")
    public DtuInfo getDtuInfoByImei(@PathVariable String imei){
        return dtuInfoService.findByImei(imei);
    }
    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public DtuInfo getDtuInfoById(@PathVariable("id") Long id){
        return dtuInfoService.findById(id);
    }
    @ApiOperation("添加感器信息")
    @PostMapping
    public DtuInfo save(@RequestBody DtuInfo dtuInfo){
        return dtuInfoService.save(dtuInfo);
    }
    @ApiOperation("更新dtu信息")
    @PutMapping
    public DtuInfo update(@RequestBody DtuInfo dtuInfo){
        return dtuInfoService.update(dtuInfo);
    }
    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id){
        dtuInfoService.delete(id);
    }
    @ApiOperation("根据imei删除所有感器信息")
    @DeleteMapping("deleteAllByImei/{imei}")
    public void deleteAllByImei(@PathVariable("imei") String imei){
        dtuInfoService.deleteAllByImei(imei);
    }
}
