package com.hejz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class RelayCreateDto {
    @ApiModelProperty(value = "感应器编号地址——发出接收时指令地址位（每个感应器都有一个地址位的）",required = true)
    @NotEmpty
    private String adrss;
    @ApiModelProperty(value = "关联关闭命令发出的指令")
    private String closeHex;
    @ApiModelProperty(value = "dtuId",required = true)
    @NotEmpty
    private Long dtuId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "关联打开命令发出的指令")
    private String opneHex;
    @ApiModelProperty(value = "备注信息")
    private String remark;
    @ApiModelProperty(value = "关联发出的链接")
    private String url;
}
