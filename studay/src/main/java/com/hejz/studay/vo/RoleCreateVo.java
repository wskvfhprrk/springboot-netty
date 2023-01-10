package com.hejz.studay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleCreateVo {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
}
