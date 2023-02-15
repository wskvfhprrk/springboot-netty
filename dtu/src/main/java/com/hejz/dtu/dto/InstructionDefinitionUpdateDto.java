package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 更新入参——必须带主键
 */
@Data
public class InstructionDefinitionUpdateDto {
    @ApiModelProperty(value = "ID",required = true)
    @NotEmpty
    private Long id;
    @ApiModelProperty(value = "指令类型",example = "1")
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
