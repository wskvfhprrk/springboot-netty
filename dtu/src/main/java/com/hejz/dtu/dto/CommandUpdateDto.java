package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 更新入参——必须带主键
 */
@Data
public class CommandUpdateDto {
    @ApiModelProperty(value = "ID",required = true)
    @NotEmpty
    private Long id;
    @ApiModelProperty(value = "校正数据计算公式——D是测得数据，如果实际数据是原来的小10倍加上5，公式为D/10+5,用测得的结果带入计算公式得到最后实际结果")
    private String calculationFormula;
    @ApiModelProperty(value = "指令类型",example = "1")
    private Integer commandType;
    @ApiModelProperty(value = "指令",required = true)
    @NotEmpty
    private String instructions;
    @ApiModelProperty(value = "是否正在使用——true正在使用，flase没有使用",required = true)
    @NotEmpty
    private Boolean isUse;
    @ApiModelProperty(value = "生产厂商",required = true)
    @NotEmpty
    private String manufacturer;
    @ApiModelProperty(value = "指令名称",required = true)
    @NotEmpty
    private String name;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "接收到数据的单位")
    private String unit;
    @ApiModelProperty(value = "等待时间下一指令（单位：秒）")
    private String waitTimeNextCommand;
    @ApiModelProperty(value = "ID",example = "1")
    private Integer checkingRulesId;
    @ApiModelProperty(value = "ID")
    private Long nextLevelInstruction;
}
