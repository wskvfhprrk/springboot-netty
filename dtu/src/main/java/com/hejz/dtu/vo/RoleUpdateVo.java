package com.hejz.dtu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleUpdateVo {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
}
