package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class InstructionDefinitionCreateDto {
    @ApiModelProperty(value = "指令类型",example = "22")
    private Integer instructionType;
    @ApiModelProperty(value = "名称",required = true)
    @NotEmpty
    private String name;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "ID",required = true)
    @NotEmpty
    private Long dtuId;
}
