package com.hejz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserFindAllDto {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}
