package com.hejz.dtu.vo;

import com.hejz.dtu.enm.CommandTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class CommandFindByPageVo{
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "校正数据计算公式——D是测得数据，如果实际数据是原来的小10倍加上5，公式为D/10+5,用测得的结果带入计算公式得到最后实际结果")
    private String calculationFormula;
    @ApiModelProperty(value = "指令类型")
    @Enumerated(value = EnumType.STRING)
    private CommandTypeEnum commandType;
    @ApiModelProperty(value = "指令")
    private String instructions;
    @ApiModelProperty(value = "是否正在使用——true正在使用，flase没有使用")
    private Boolean isUse;
    @ApiModelProperty(value = "生产厂商")
    private String manufacturer;
    @ApiModelProperty(value = "指令名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "等待时间下一指令（单位：秒）")
    private Integer waitTimeNextCommand;
    @ApiModelProperty(value = "ID")
    private Integer checkingRulesId;
    @ApiModelProperty(value = "ID")
    private Long nextLevelInstructionId;
}
