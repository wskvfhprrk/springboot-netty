package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class SensorCreateDto {
    @ApiModelProperty(value = "获取值参考最大值",example = "22")
    private Integer max;
    @ApiModelProperty(value = "获取值参考最小值",example = "22")
    private Integer min;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "接收感应器数据排序")
    private String sensorSort;
    @ApiModelProperty(value = "接收到数据的单位")
    private String unit;
    @ApiModelProperty(value = "指令ID")
    private Long commandId;
    @ApiModelProperty(value = "dtuID")
    private Long equDtuId;
    @ApiModelProperty(value = "继电器定义指令ID")
    private Long maxInstructionDefinitionId;
    @ApiModelProperty(value = "继电器定义指令ID")
    private Long minInstructionDefinitionId;
}
