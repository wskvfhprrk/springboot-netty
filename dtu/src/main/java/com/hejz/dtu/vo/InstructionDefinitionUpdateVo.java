package com.hejz.dtu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InstructionDefinitionUpdateVo {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "指令类型")
    private Integer instructionType;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "ID")
    private Long dtuId;
}
