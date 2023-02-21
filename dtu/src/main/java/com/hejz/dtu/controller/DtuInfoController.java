package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.common.Result;
import com.hejz.dtu.dto.DtuInfoCreateDto;
import com.hejz.dtu.dto.DtuInfoFindAllDto;
import com.hejz.dtu.dto.DtuInfoFindByPageDto;
import com.hejz.dtu.dto.DtuInfoUpdateDto;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.User;
import com.hejz.dtu.service.DtuInfoService;
import com.hejz.dtu.service.UserService;
import com.hejz.dtu.vo.DtuInfoFindAllVo;
import com.hejz.dtu.vo.DtuInfoFindByPageVo;
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
 * dtu信息控制器
 * author: hejz
 * data: 2023-2-7
 */
@RestController
@RequestMapping("dtuInfo")
@Api(tags="dtu信息")
public class DtuInfoController {

    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    private UserService userService;

    @PostMapping()
    @ApiOperation("添加dtu信息")
    public Result createDtuInfo(@Valid @RequestBody DtuInfoCreateDto dto){
        DtuInfo dtuInfo=new DtuInfo();
        BeanUtils.copyProperties(dto,dtuInfo);
        if(dto.getUserId()!=null){
            User user=userService.getFindById(dto.getUserId());
            dtuInfo.setUser(user);
        }
        dtuInfo = dtuInfoService.save(dtuInfo);
        return Result.ok(dtuInfo);

    }
    @PutMapping
    @ApiOperation("修改dtu信息")
    public Result updateDtuInfo(@Valid @RequestBody DtuInfoUpdateDto dto){
        DtuInfo dtuInfo=new DtuInfo();
        BeanUtils.copyProperties(dto,dtuInfo);
        if(dto.getUserId()!=null){
            User user=userService.getFindById(dto.getUserId());
            dtuInfo.setUser(user);
        }
        dtuInfo = dtuInfoService.update(dtuInfo);
        return Result.ok(dtuInfo);
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除dtu信息")
    public Result DeleteDtuInfo(@PathVariable Long id){
        dtuInfoService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询dtu信息")
    public Result<PageResult<DtuInfoFindByPageVo>> findBypage( @Valid DtuInfoFindByPageDto dto){
        DtuInfo dtuInfo=new DtuInfo();
        BeanUtils.copyProperties(dto,dtuInfo);
        Page<DtuInfo> dtuInfoPage = dtuInfoService.findPage(dto);
        List<DtuInfoFindByPageVo> list = dtuInfoPage.getContent().stream().map(d -> {
            DtuInfoFindByPageVo vo = new DtuInfoFindByPageVo();
            BeanUtils.copyProperties(d, vo);
            if (d.getUser() != null) {
                vo.setUserId(d.getUser().getId());
            }
            return vo;
        }).collect(Collectors.toList());
        PageResult<DtuInfoFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(dtuInfoPage.getTotalPages());
        pages.setTotal(dtuInfoPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @GetMapping("findAll")
    @ApiOperation("条件查询dtu信息")
    public Result findBypage( @Valid DtuInfoFindAllDto dto){
        DtuInfo dtuInfo=new DtuInfo();
        BeanUtils.copyProperties(dto,dtuInfo);
        List<DtuInfo> dtuInfoList = dtuInfoService.findAll(dto);
        List<DtuInfoFindAllVo> list = dtuInfoList.stream().map(d -> {
            DtuInfoFindAllVo vo = new DtuInfoFindAllVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }

}
