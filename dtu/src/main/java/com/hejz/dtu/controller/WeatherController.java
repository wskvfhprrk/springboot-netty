package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.Weather;
import com.hejz.dtu.service.WeatherService;
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
 * 天气网预报天气控制器
 * author: hejz
 * data: 2023-2-28
 */
@RestController
@RequestMapping("weather")
@Api(tags="天气网预报天气")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping()
    @ApiOperation("添加天气网预报天气")
    public Result createWeather(@Valid @RequestBody WeatherCreateDto dto){
        Weather weather=new Weather();
        BeanUtils.copyProperties(dto,weather);
        weatherService.save(weather);
        return Result.ok();

    }
    @PutMapping
    @ApiOperation("修改天气网预报天气")
    public Result updateWeather(@Valid @RequestBody WeatherUpdateDto dto){
        Weather weather=new Weather();
        BeanUtils.copyProperties(dto,weather);
        weatherService.update(weather);
        return Result.ok();
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除天气网预报天气")
    public Result DeleteWeather(@PathVariable Long id){
        weatherService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询天气网预报天气")
    public Result<PageResult<WeatherFindByPageVo>> findByPage(WeatherFindByPageDto dto){
        Weather weather=new Weather();
        BeanUtils.copyProperties(dto,weather);
        Page<Weather> weatherPage = weatherService.findPage(dto);
        List<WeatherFindByPageVo> list = weatherPage.getContent().stream().map(d -> {
            WeatherFindByPageVo vo = new WeatherFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<WeatherFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(weatherPage.getTotalPages());
        pages.setTotal(weatherPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @GetMapping("findAll")
    @ApiOperation("分布条件查询天气网预报天气所有的数据")
    public Result<List<WeatherAllVo>> findAll(WeatherAllDto dto){
        List<Weather> dictionaries = weatherService.findAll(dto);
        List<WeatherAllVo> list = dictionaries.stream().map(d -> {
            WeatherAllVo vo = new WeatherAllVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }

}
