package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class CheckingRulesAllDto {
    @ApiModelProperty(value = "数据校检规则ID")
    private Integer id;
    @ApiModelProperty(value = "地址位")
    private Integer addressBitLength;
    @ApiModelProperty(value = "共有长度")
    private Integer commonLength;
    @ApiModelProperty(value = "crc16校验位数")
    private Integer crc16CheckDigitLength;
    @ApiModelProperty(value = "数据位数")
    private Integer dataBitsLength;
    @ApiModelProperty(value = "16进制数据值")
    private Integer dataValueLength;
    @ApiModelProperty(value = "功能码")
    private Integer functionCodeLength;
    @ApiModelProperty(value = "是否正在使用——true正在使用，flase没有使用")
    private Boolean isUse;
    @ApiModelProperty(value = "name")
    private String name;
}
