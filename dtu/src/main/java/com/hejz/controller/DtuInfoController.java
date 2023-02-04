package com.hejz.controller;
import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.DtuInfoDto;
import com.hejz.dto.DtuInfoFindByPageDto;
import com.hejz.dto.DtuInfoUpdateDto;
import com.hejz.entity.DtuInfo;
import com.hejz.vo.DtuInfoFindByPageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.hejz.service.DtuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: dtu参数控制器
 */
@RestController
@RequestMapping("dtuInfo")
@Api(tags ="dtu参数控制器")
public class DtuInfoController {

    @Autowired
    private DtuInfoService dtuInfoService;

    @ApiOperation("根据imei查询所有dtu信息")
    @GetMapping("all/{imei}")
    public Result findAllByDtuId(@PathVariable String imei){
        return Result.ok(dtuInfoService.findByImei(imei));
    }
    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public Result getDtuInfoById(@PathVariable("id") Long id){
        return Result.ok(dtuInfoService.findById(id));
    }
    @ApiOperation("添加感器信息")
    @PostMapping
    public Result save(@RequestBody DtuInfoDto dtuInfo){
        return Result.ok(dtuInfoService.save(dtuInfo));
    }
    @ApiOperation("更新dtu信息")
    @PutMapping
    public Result update(@RequestBody DtuInfoUpdateDto dtuInfo){
        return Result.ok(dtuInfoService.update(dtuInfo));
    }
    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") Long id){
        dtuInfoService.delete(id);
        return Result.ok();
    }
    @ApiOperation("分页条件查询")
    @GetMapping("findPage")
    public Result<PageResult<DtuInfoFindByPageVo>> findBypage( DtuInfoFindByPageDto dto){
        Page<DtuInfo> dtuInfoPage = dtuInfoService.findPage(dto);
        List<DtuInfoFindByPageVo> list = dtuInfoPage.getContent().stream().map(d -> {
            DtuInfoFindByPageVo vo = new DtuInfoFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<DtuInfoFindByPageVo> pages=new PageResult<>();
        pages.setTotal(dtuInfoPage.getTotalElements());
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @ApiOperation("切换大棚自动调整模式")
    @GetMapping("automaticAdjustment/{dtuId}")
    public Result automaticAdjustment(@PathVariable Long dtuId){
        return dtuInfoService.changeAutomaticAdjustment(dtuId);
    }
}
