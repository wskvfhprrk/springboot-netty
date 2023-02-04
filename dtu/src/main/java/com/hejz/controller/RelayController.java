package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.ManualCommandDto;
import com.hejz.dto.RelayFindByPageDto;
import com.hejz.entity.Relay;
import com.hejz.service.RelayService;
import com.hejz.vo.RelayFindByPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: 继电器参数控制器
 */
@RestController
@RequestMapping("relay")
@Api(tags ="继电器参数控制器")
public class RelayController {

    @Autowired
    private RelayService relayService;

    @ApiOperation("根据dtuId查询所有感器信息")
    @GetMapping("all/{dtuId}")
    public Result findAllByDtuId(@PathVariable Long dtuId) {
        return Result.ok(relayService.findAllByDtuId(dtuId));
    }

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public Result getRelayById(@PathVariable("id") Long id) {
        return Result.ok(relayService.findById(id));
    }

    @ApiOperation("添加感器信息")
    @PostMapping
    public Result save(@RequestBody Relay relay) {
        return Result.ok(relayService.save(relay));
    }

    @ApiOperation("手动指令")
    @PostMapping("manualCommand")
    public Result manualCommand(@RequestBody ManualCommandDto manualCommandDto) {
        return relayService.manualCommand(manualCommandDto);
    }

    @ApiOperation("更新继电器信息")
    @PutMapping
    public Result update(@RequestBody Relay relay) {
        return Result.ok(relayService.update(relay));
    }

    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") Long id) {
        relayService.delete(id);
        return Result.ok();
    }

    @ApiOperation("根据dtuId删除所有感器信息")
    @DeleteMapping("deleteAllByImei/{dtuId}")
    @Transactional
    public Result deleteAllByImei(@PathVariable("dtuId") Long dtuId) {
        relayService.deleteAlByDtuId(dtuId);
        return Result.ok();
    }

    @ApiOperation("分页条件查询")
    @GetMapping("page")
    public Result<PageResult<RelayFindByPageVo>> findBypage(@Valid RelayFindByPageDto dto) {
        Page<Relay> relayPage = relayService.findPage(dto);
        List<RelayFindByPageVo> list = relayPage.getContent().stream().map(d -> {
            RelayFindByPageVo vo = new RelayFindByPageVo();
            BeanUtils.copyProperties(d, vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<RelayFindByPageVo> pages=new PageResult<>();
        pages.setTotal(relayPage.getTotalElements());
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setItems(list);
        return Result.ok(pages);
    }
}
