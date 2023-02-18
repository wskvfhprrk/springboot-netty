package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.Sensor;
import com.hejz.dtu.service.SensorService;
import com.hejz.dtu.common.Result;
import com.hejz.dtu.vo.*;
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
 * data: 2023-2-7
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
            vo.setDtuId(d.getDtuInfo().getId());
            return vo;
        }).collect(Collectors.toList());
        PageResult<SensorFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(sensorPage.getTotalPages());
        pages.setTotal(sensorPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

}
