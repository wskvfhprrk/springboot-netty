package com.hejz.dtu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SensorDataFindByPageVo{
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "接收时间")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private java.util.Date createDate;
    @ApiModelProperty(value = "接收到数据")
    private String data;
    @ApiModelProperty(value = "接收到数据的names")
    private String names;
    @ApiModelProperty(value = "接收到数据的单位")
    private String units;
    @ApiModelProperty(value = "ID")
    private Long dtuId;
}
