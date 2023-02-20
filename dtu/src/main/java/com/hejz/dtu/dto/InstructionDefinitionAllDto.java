package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class InstructionDefinitionAllDto {
    @ApiModelProperty(value = "继电器定义指令ID")
    private Long id;
    @ApiModelProperty(value = "指令类型")
    private String instructionType;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "dtuID")
    private Long dtuId;
}
