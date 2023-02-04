package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.dto.*;
import com.hejz.entity.Sensor;
import com.hejz.service.SensorService;
import com.hejz.common.Result;
import com.hejz.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 传感器控制器
 * author: hejz
 * data: 2023-2-4
 */
@RestController
@RequestMapping("sensor")
@Api(tags="传感器")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping()
    @ApiOperation("添加传感器")
    public Result createSensor(@Valid @RequestBody SensorCreateDto dto){
        Sensor sensor=new Sensor();
        BeanUtils.copyProperties(dto,sensor);
        sensor = sensorService.save(sensor);
        return Result.ok(sensor);

    }
    @PutMapping
    @ApiOperation("修改传感器")
    public Result updateSensor(@Valid @RequestBody SensorUpdateDto dto){
        Sensor sensor=new Sensor();
        BeanUtils.copyProperties(dto,sensor);
        sensor = sensorService.update(sensor);
        return Result.ok(sensor);
    }
    @DeleteMapping
    @ApiOperation("删除传感器")
    public Result DeleteSensor(Long id){
        sensorService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询传感器")
    public Result<PageResult<SensorFindByPageVo>> findBypage( @Valid SensorFindByPageDto dto){
        Sensor sensor=new Sensor();
        BeanUtils.copyProperties(dto,sensor);
        Page<Sensor> sensorPage = sensorService.findPage(dto);
        List<SensorFindByPageVo> list = sensorPage.getContent().stream().map(d -> {
            SensorFindByPageVo vo = new SensorFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<SensorFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotal(sensorPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

//    @GetMapping
//    @ApiOperation("分布条件查询传感器所有的数据")
//    public Result<List<SensorAllVo>> findAll(@Valid SensorFindAllDto dto){
//        Sensor sensor=new Sensor();
//        BeanUtils.copyProperties(dto,sensor);
//        List<Sensor> dictionaries = sensorService.findAll(sensor);
//        List<SensorAllVo> list = dictionaries.stream().map(d -> {
//            SensorAllVo vo = new SensorAllVo();
//            BeanUtils.copyProperties(d,vo);
//            return vo;
//        }).collect(Collectors.toList());
//        return Result.ok(list);
//    }
}
