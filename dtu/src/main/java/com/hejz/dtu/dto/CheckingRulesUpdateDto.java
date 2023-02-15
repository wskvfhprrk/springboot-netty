package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 更新入参——必须带主键
 */
@Data
public class CheckingRulesUpdateDto {
    @ApiModelProperty(value = "ID",required = true,example = "1")
    @NotEmpty
    private Integer id;
    @ApiModelProperty(value = "地址位",required = true,example = "1")
    @NotEmpty
    private Integer addressBitLength;
    @ApiModelProperty(value = "数据位数",required = true,example = "1")
    @NotEmpty
    private Integer dataBitsLength;
    @ApiModelProperty(value = "16进制数据值",required = true,example = "1")
    @NotEmpty
    private Integer dataValueLength;
    @ApiModelProperty(value = "共有长度",required = true,example = "1")
    @NotEmpty
    private Integer commonLength;
    @ApiModelProperty(value = "crc16校验位数",required = true,example = "1")
    @NotEmpty
    private Integer crc16CheckDigitLength;
    @ApiModelProperty(value = "功能码",required = true,example = "1")
    @NotEmpty
    private Integer functionCodeLength;
    @ApiModelProperty(value = "是否正在使用——true正在使用，flase没有使用",required = true)
    @NotEmpty
    private Boolean isUse;
    @ApiModelProperty(value = "name",required = true)
    @NotEmpty
    private String name;
}
