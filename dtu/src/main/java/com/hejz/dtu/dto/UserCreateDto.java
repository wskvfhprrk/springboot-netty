package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class UserCreateDto {
    @ApiModelProperty(value = "年龄",example = "22")
    private Integer age;
    @ApiModelProperty(value = "用户名",required = true)
    @NotEmpty
    private String username;
}
