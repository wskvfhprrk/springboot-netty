package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.dto.*;
import com.hejz.entity.Relay;
import com.hejz.service.RelayService;
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
 * 继电器控制器
 * author: hejz
 * data: 2023-2-4
 */
@RestController
@RequestMapping("relay")
@Api(tags="继电器")
public class RelayController {

    @Autowired
    private RelayService relayService;

    @PostMapping()
    @ApiOperation("添加继电器")
    public Result createRelay(@Valid @RequestBody RelayCreateDto dto){
        Relay relay=new Relay();
        BeanUtils.copyProperties(dto,relay);
        relay = relayService.save(relay);
        return Result.ok(relay);

    }
    @PutMapping
    @ApiOperation("修改继电器")
    public Result updateRelay(@Valid @RequestBody RelayUpdateDto dto){
        Relay relay=new Relay();
        BeanUtils.copyProperties(dto,relay);
        relay = relayService.update(relay);
        return Result.ok(relay);
    }
    @DeleteMapping
    @ApiOperation("删除继电器")
    public Result DeleteRelay(Long id){
        relayService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询继电器")
    public Result<PageResult<RelayFindByPageVo>> findBypage( @Valid RelayFindByPageDto dto){
        Relay relay=new Relay();
        BeanUtils.copyProperties(dto,relay);
        Page<Relay> relayPage = relayService.findPage(dto);
        List<RelayFindByPageVo> list = relayPage.getContent().stream().map(d -> {
            RelayFindByPageVo vo = new RelayFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<RelayFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotal(relayPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @GetMapping("manualCommand")
    @ApiOperation("手动命令")
    public Result manualCommand(ManualCommandDto dto){
        return relayService.manualCommand(dto);
    }

//    @GetMapping
//    @ApiOperation("分布条件查询继电器所有的数据")
//    public Result<List<RelayAllVo>> findAll(@Valid RelayFindAllDto dto){
//        Relay relay=new Relay();
//        BeanUtils.copyProperties(dto,relay);
//        List<Relay> dictionaries = relayService.findAll(relay);
//        List<RelayAllVo> list = dictionaries.stream().map(d -> {
//            RelayAllVo vo = new RelayAllVo();
//            BeanUtils.copyProperties(d,vo);
//            return vo;
//        }).collect(Collectors.toList());
//        return Result.ok(list);
//    }
}
