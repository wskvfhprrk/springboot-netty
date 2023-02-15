package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.SensorData;
import com.hejz.dtu.service.SensorDataService;
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
 * 传感器数据控制器
 * author: hejz
 * data: 2023-2-7
 */
@RestController
@RequestMapping("sensorData")
@Api(tags="传感器数据")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;

    @PostMapping()
    @ApiOperation("添加传感器数据")
    public Result createSensorData(@Valid @RequestBody SensorDataCreateDto dto){
        SensorData sensorData=new SensorData();
        BeanUtils.copyProperties(dto,sensorData);
        sensorData = sensorDataService.save(sensorData);
        return Result.ok(sensorData);

    }
    @PutMapping
    @ApiOperation("修改传感器数据")
    public Result updateSensorData(@Valid @RequestBody SensorDataUpdateDto dto){
        SensorData sensorData=new SensorData();
        BeanUtils.copyProperties(dto,sensorData);
        sensorData = sensorDataService.save(sensorData);
        return Result.ok(sensorData);
    }
    @DeleteMapping
    @ApiOperation("删除传感器数据")
    public Result DeleteSensorData(Long id){
        sensorDataService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询传感器数据")
    public Result<PageResult<SensorDataFindByPageVo>> findBypage( @Valid SensorDataFindByPageDto dto){
        SensorData sensorData=new SensorData();
        BeanUtils.copyProperties(dto,sensorData);
        Page<SensorData> sensorDataPage = sensorDataService.findPage(dto);
        List<SensorDataFindByPageVo> list = sensorDataPage.getContent().stream().map(d -> {
            SensorDataFindByPageVo vo = new SensorDataFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<SensorDataFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(sensorDataPage.getTotalPages());
        pages.setTotal(sensorDataPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

}
