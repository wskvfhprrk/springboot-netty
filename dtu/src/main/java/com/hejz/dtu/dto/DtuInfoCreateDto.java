package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class DtuInfoCreateDto {
    @ApiModelProperty(value = "是否自动控制——true是自动false是手动控制")
    private Boolean automaticAdjustment;
    @ApiModelProperty(value = "imei",required = true)
    @NotEmpty
    private String imei;
    @ApiModelProperty(value = "每组轮询指令隔时间(毫秒)——与dtu每组间隔时间要一致",example = "22")
    private Integer intervalTime;
    @ApiModelProperty(value = "dtu注册信息的长度",example = "22")
    private Integer registrationLength;
    @ApiModelProperty(value = "感应器地址顺序",required = true)
    @NotEmpty
    private String sensorAddressOrder;
    @ApiModelProperty(value = "",example = "22")
    private Integer userId;
}
