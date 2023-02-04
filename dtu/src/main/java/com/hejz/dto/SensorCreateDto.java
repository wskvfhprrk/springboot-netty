package com.hejz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class SensorCreateDto {
    @ApiModelProperty(value = "感应器编号地址——发出接收时指令地址位（每个感应器都有一个地址位的）",example = "22")
    private Integer adrss;
    @ApiModelProperty(value = "为了校正上报数据与实际数据间的差距，使用到的计算公式——D是测得数据，如果实际数据是原来的小10倍加上5，公式为D/10+5,用测得的结果带入计算公式得到最后实际结果。")
    private String calculationFormula;
    @ApiModelProperty(value = "dtuId",required = true)
    @NotEmpty
    private Long dtuId;
    @ApiModelProperty(value = "发送查询16进制指令字符串——含有crc16验证码")
    private String hex;
    @ApiModelProperty(value = "获取值参考最大值",example = "22")
    private Integer max;
    @ApiModelProperty(value = "超过最大值时的指令")
    private Long maxRelayDefinitionCommandId;
    @ApiModelProperty(value = "获取值参考最小值",example = "22")
    private Integer min;
    @ApiModelProperty(value = "小于最小值时发出的指令")
    private Long minRelayDefinitionCommandId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "接收到数据的单位")
    private String unit;
}
