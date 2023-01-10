package com.hejz.studay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleAllVo {
    @ApiModelProperty(value = "名称")
    private String name;
}
