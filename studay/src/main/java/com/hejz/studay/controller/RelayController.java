package com.hejz.studay.controller;

import com.hejz.studay.entity.Relay;
import com.hejz.studay.service.RelayService;
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
 * @Description: 继电器参数控制器
 */
@RestController
@RequestMapping("relay")
@Api("继电器参数控制器")
public class RelayController {

    @Autowired
    private RelayService relayService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据imei查询所有感器信息")
    @GetMapping("all/{imei}")
    @Cacheable(value = "relay",key = "#imei")
    public List<Relay> getRelayByImei(@PathVariable String imei){
        return relayService.getByImei(imei);
    }
    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public Relay getRelayById(@PathVariable("id") Long id){
        return relayService.getById(id);
    }
    @ApiOperation("添加感器信息")
    @PostMapping
    @CacheEvict(value = "relay",key = "#result.imei")
    public Relay save(@RequestBody Relay relay){
        return relayService.save(relay);
    }
    @ApiOperation("更新继电器信息")
    @PutMapping
    @CacheEvict(value = "relay",key = "#result.imei")
    public Relay update(@RequestBody Relay relay){
        return relayService.update(relay);
    }
    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id){
        Relay relay = relayService.getById(id);
        relayService.delete(id);
        //删除缓存，缓存要同步
        redisTemplate.opsForValue().decrement("relay::"+relay.getName());
    }
    @ApiOperation("根据imei删除所有感器信息")
    @DeleteMapping("deleteByImei/{imei}")
    public void deleteByImei(@PathVariable("imei") String imei){
        relayService.deleteByImei(imei);
    }
}
