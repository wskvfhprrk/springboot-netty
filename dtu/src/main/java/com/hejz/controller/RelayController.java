package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.RelayFindByPageDto;
import com.hejz.entity.Relay;
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
    public List<Relay> findAllByDtuId(@PathVariable Long dtuId) {
        return relayService.findAllByDtuId(dtuId);
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

    @ApiOperation("手动模式关闭大棚")
    @GetMapping("closeTheCanopyInManualMode/{dtuId}")
    public Result closeTheCanopyInManualMode(@PathVariable Long dtuId){
        return relayService.closeTheCanopyInManualMode(dtuId);
    }
    @ApiOperation("手动模式开启大棚")
    @GetMapping("openTheCanopyInManualMode/{dtuId}")
    public Result openTheCanopyInManualMode(@PathVariable Long dtuId){
        return relayService.openTheCanopyInManualMode(dtuId);
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

    @ApiOperation("根据dtuId删除所有感器信息")
    @DeleteMapping("deleteAllByImei/{dtuId}")
    @Transactional
    public void deleteAllByImei(@PathVariable("dtuId") Long dtuId) {
        relayService.deleteAlByDtuId(dtuId);
    }
    @ApiOperation("分页条件查询")
    @GetMapping("page")
    public Result<PageResult<RelayFindByPageVo>> findBypage(@Valid RelayFindByPageDto dto){
        Page<Relay> relayPage = relayService.findPage(dto);
        List<RelayFindByPageVo> list = relayPage.getContent().stream().map(d -> {
            RelayFindByPageVo vo = new RelayFindByPageVo();
            BeanUtils.copyProperties(d,vo);
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
