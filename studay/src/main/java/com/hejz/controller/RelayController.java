package com.hejz.controller;

import com.hejz.entity.Relay;
import com.hejz.service.RelayService;
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
@RequestMapping("relay")
@Api("继电器参数控制器")
public class RelayController {

    @Autowired
    private RelayService relayService;

    @ApiOperation("根据imei查询所有感器信息")
    @GetMapping("all/{imei}")
    public List<Relay> getRelayByImei(@PathVariable String imei) {
        return relayService.findByImei(imei);
    }

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public Relay getRelayById(@PathVariable("id") Long id) {
        return relayService.findById(id);
    }

    @ApiOperation("添加感器信息")
    @PostMapping
    public Relay save(@RequestBody Relay relay) {
        return relayService.save(relay);
    }

    @ApiOperation("更新继电器信息")
    @PutMapping
    public Relay update(@RequestBody Relay relay) {
        return relayService.update(relay);
    }

    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        relayService.delete(id);
    }

    @ApiOperation("根据imei删除所有感器信息")
    @DeleteMapping("deleteByImei/{imei}")
    public void deleteByImei(@PathVariable("imei") String imei) {
        relayService.deleteByImei(imei);
    }
}
