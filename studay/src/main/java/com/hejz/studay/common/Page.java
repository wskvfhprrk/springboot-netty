package com.hejz.studay.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * page父类
 */
@Data
public class Page {
    @ApiModelProperty(value = "第几页",required = true,example = "0")
    @NotEmpty
    private Integer pageNo;
    @ApiModelProperty(value = "每页多少行",required = true,example = "5")
    @NotEmpty
    private Integer pageSize;
}
