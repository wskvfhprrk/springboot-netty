package com.hejz.studay.controller;

import com.hejz.studay.common.PageResult;
import com.hejz.studay.dto.*;
import com.hejz.studay.entity.User;
import com.hejz.studay.service.UserService;
import com.hejz.studay.common.Result;
import com.hejz.studay.vo.*;
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
 * 用户信息控制器
 * author: hejz
 * data: 2022-5-9
 */
@RestController
@RequestMapping("user")
@Api(tags="用户信息")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    @ApiOperation("添加用户信息")
    public Result createUser(@Valid @RequestBody UserCreateDto dto){
        User user=new User();
        BeanUtils.copyProperties(dto,user);
        user = userService.Save(user);
        return Result.ok(user);

    }
    @PutMapping
    @ApiOperation("修改用户信息")
    public Result updateUser(@Valid @RequestBody UserUpdateDto dto){
        User user=new User();
        BeanUtils.copyProperties(dto,user);
        user = userService.Save(user);
        return Result.ok(user);
    }
    @DeleteMapping
    @ApiOperation("删除用户信息")
    public Result DeleteUser(Integer id){
        userService.delete(id);
        return Result.ok();
    }

    @GetMapping("fingPage")
    @ApiOperation("条件查询用户信息")
    public Result<PageResult<UserFindByPageVo>> findBypage( @Valid UserFindByPageDto dto){
        User user=new User();
        BeanUtils.copyProperties(dto,user);
        Page<User> userPage = userService.findPage(user, dto.getPageNo(), dto.getPageSize());
        List<UserFindByPageVo> list = userPage.getContent().stream().map(d -> {
            UserFindByPageVo vo = new UserFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<UserFindByPageVo> pages=new PageResult<>();
        pages.setPageNo(dto.getPageNo());
        pages.setPageSize(dto.getPageSize());
        pages.setTotalPage(userPage.getTotalPages());
        pages.setTotalElements(userPage.getTotalElements());
        pages.setContent(list);
        return Result.ok(pages);
    }

    @GetMapping
    @ApiOperation("分布条件查询用户信息所有的数据")
    public Result<List<UserAllVo>> findAll(@Valid UserFindAllDto dto){
        User user=new User();
        BeanUtils.copyProperties(dto,user);
        List<User> dictionaries = userService.findAll(user);
        List<UserAllVo> list = dictionaries.stream().map(d -> {
            UserAllVo vo = new UserAllVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }
}
