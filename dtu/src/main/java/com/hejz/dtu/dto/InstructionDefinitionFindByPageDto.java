package com.hejz.dtu.dto;

import com.hejz.dtu.enm.InstructionTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.hejz.dtu.common.Page;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Data
public class InstructionDefinitionFindByPageDto extends Page {
    @ApiModelProperty(value = "指令类型")
    @Enumerated(value = EnumType.STRING)
    private InstructionTypeEnum instructionType;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "ID")
    private Long dtuId;
}
