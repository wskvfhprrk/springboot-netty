package com.hejz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SensorDataAllVo {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "接收到数据的单位")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private java.util.Date createDate;
    @ApiModelProperty(value = "接收到数据")
    private String data;
    @ApiModelProperty(value = "dtuId")
    private Long dtuId;
    @ApiModelProperty(value = "接收到数据的names")
    private String names;
    @ApiModelProperty(value = "接收到数据的单位")
    private String units;
}
