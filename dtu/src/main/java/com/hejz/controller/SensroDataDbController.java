package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.SensorDataDbFindByPageDto;
import com.hejz.entity.SensorDataDb;
import com.hejz.service.SensorDataDbService;
import com.hejz.vo.SensorDataDbFindByPageVo;
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
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: 感应器上报数据
 */
@RestController
@RequestMapping("densorDataDb")
@Api(tags ="感应器上报数据")
public class SensroDataDbController {

    @Autowired
    private SensorDataDbService sensorDataDbService;

    @ApiOperation("根据dtuId查询所有dtu信息")
    @GetMapping("all/{dtuId}")
    public List<SensorDataDb> findAllByDtuId(@PathVariable Long dtuId) {
        return sensorDataDbService.findAllByDtuId(dtuId);
    }

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public SensorDataDb getSensorDataDbById(@PathVariable("id") Long id) {
        return sensorDataDbService.getById(id);
    }

    @ApiOperation("添加感器信息")
    @PostMapping
    public SensorDataDb save(@RequestBody SensorDataDb densorDataDb) {
        return sensorDataDbService.save(densorDataDb);
    }

    @ApiOperation("更新dtu信息")
    @PutMapping
    public SensorDataDb update(@RequestBody SensorDataDb densorDataDb) {
        return sensorDataDbService.update(densorDataDb);
    }

    @ApiOperation("分页条件查询")
    @GetMapping("findPage")
    public Result<PageResult<SensorDataDbFindByPageVo>> findBypage(@Valid SensorDataDbFindByPageDto dto){
        Page<SensorDataDb> sensorDataDbPage = sensorDataDbService.findPage(dto);
        List<SensorDataDbFindByPageVo> list = sensorDataDbPage.getContent().stream().map(d -> {
            SensorDataDbFindByPageVo vo = new SensorDataDbFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<SensorDataDbFindByPageVo> pages=new PageResult<>();
        pages.setTotal(sensorDataDbPage.getTotalElements());
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        sensorDataDbService.delete(id);
    }

    @ApiOperation("根据dtuId删除所有感器信息")
    @DeleteMapping("deleteAllByImei/{dtuId}")
    public void deleteAllByImei(@PathVariable("dtuId") Long dtuId) {
        sensorDataDbService.deleteAllByDtuId(dtuId);
    }
}
