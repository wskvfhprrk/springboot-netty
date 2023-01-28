package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.dto.*;
import com.hejz.entity.User;
import com.hejz.service.UserService;
import com.hejz.common.Result;
import com.hejz.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    String token = "admin-token";

    @ApiOperation("登陆")
    @PostMapping("login")
    public Result login(@RequestBody LoginUserDto loginUserDto,HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        map.put("\"token\"", token);
        //操作cookies
        Cookie cookie = new Cookie("Admin-Token",token);
        response.addCookie(cookie);
//        return Result.ok("'token':'admin-token");
        return Result.ok(map);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public Result<UserInfoVo> getUserInfo(String token) {
        return Result.ok(new UserInfoVo("zs", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif", "zs", Arrays.asList("admin")));
    }

    @ApiOperation("登陆")
    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }

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
