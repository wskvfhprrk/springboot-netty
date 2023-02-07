package com.hejz.dtu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DtuInfoAllVo {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "是否自动控制——true是自动false是手动控制")
    private Boolean automaticAdjustment;
    @ApiModelProperty(value = "imei")
    private String imei;
    @ApiModelProperty(value = "每组轮询指令隔时间(毫秒)——与dtu每组间隔时间要一致")
    private Integer intervalTime;
    @ApiModelProperty(value = "dtu注册信息的长度")
    private Integer registrationLength;
    @ApiModelProperty(value = "感应器地址顺序")
    private String sensorAddressOrder;
    @ApiModelProperty(value = "")
    private Integer userId;
}
