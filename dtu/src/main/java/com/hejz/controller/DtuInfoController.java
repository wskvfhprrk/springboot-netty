package com.hejz.controller;
import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.DtuInfoFindByPageDto;
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
    public DtuInfo findAllByDtuId(@PathVariable String imei){
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
    @ApiOperation("分页条件查询")
    @GetMapping("page")
    public Result<PageResult<DtuInfoFindByPageVo>> findBypage(@Valid DtuInfoFindByPageDto dto){
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
    @ApiOperation("手动模式关闭大棚")
    @GetMapping("closeTheCanopyInManualMode/{dtuId}")
    public Result closeTheCanopyInManualMode(@PathVariable Long dtuId){
        return dtuInfoService.closeTheCanopyInManualMode(dtuId);
    }
    @ApiOperation("手动模式开启大棚")
    @GetMapping("openTheCanopyInManualMode/{dtuId}")
    public Result openTheCanopyInManualMode(@PathVariable Long dtuId){
        return dtuInfoService.openTheCanopyInManualMode(dtuId);
    }
    @ApiOperation("切换大棚自动调整模式")
    @GetMapping("automaticAdjustment/{dtuId}")
    public Result automaticAdjustment(@PathVariable Long dtuId){
        return dtuInfoService.changeAutomaticAdjustment(dtuId);
    }
}
