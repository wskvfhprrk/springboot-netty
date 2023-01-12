package com.hejz.studay.controller;

import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.service.DtuInfoService;
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
    @Cacheable(value = "dtuInfo",key = "#imei")
    public DtuInfo getDtuInfoByImei(@PathVariable String imei){
        return dtuInfoService.getByImei(imei);
    }
    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public DtuInfo getDtuInfoById(@PathVariable("id") Long id){
        return dtuInfoService.getById(id);
    }
    @ApiOperation("添加感器信息")
    @PostMapping
    @CacheEvict(value = "dtuInfo",key = "#result.imei")
    public DtuInfo save(@RequestBody DtuInfo dtuInfo){
        return dtuInfoService.save(dtuInfo);
    }
    @ApiOperation("更新dtu信息")
    @PutMapping
    @CacheEvict(value = "dtuInfo",key = "#result.imei")
    public DtuInfo update(@RequestBody DtuInfo dtuInfo){
        return dtuInfoService.update(dtuInfo);
    }
    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id){
        DtuInfo dtuInfo = dtuInfoService.getById(id);
        dtuInfoService.delete(id);
        //删除缓存，缓存要同步
        redisTemplate.opsForValue().decrement("dtuInfo::"+dtuInfo.getImei());
    }
    @ApiOperation("根据imei删除所有感器信息")
    @DeleteMapping("deleteByImei/{imei}")
    public void deleteByImei(@PathVariable("imei") String imei){
        dtuInfoService.deleteByImei(imei);
    }
}
