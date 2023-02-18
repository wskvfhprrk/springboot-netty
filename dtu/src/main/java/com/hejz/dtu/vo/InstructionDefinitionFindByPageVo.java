package com.hejz.dtu.vo;

import com.hejz.dtu.enm.InstructionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class InstructionDefinitionFindByPageVo{
    @ApiModelProperty(value = "ID")
    private Long id;
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
