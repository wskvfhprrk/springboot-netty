package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class WeatherCreateDto {
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "抓取时间")
    private java.util.Date createTime;
    @ApiModelProperty(value = "黑夜")
    private String dn1;
    @ApiModelProperty(value = "黑夜")
    private String dn2;
    @ApiModelProperty(value = "描述")
    private String remarks;
    @ApiModelProperty(value = "温度")
    private String temperature1;
    @ApiModelProperty(value = "温度")
    private String temperature2;
    @ApiModelProperty(value = "天气")
    private String weather1;
    @ApiModelProperty(value = "天气")
    private String weather2;
    @ApiModelProperty(value = "风向")
    private String windDirection1;
    @ApiModelProperty(value = "风向")
    private String windDirection2;
    @ApiModelProperty(value = "风速")
    private String windSpeed1;
    @ApiModelProperty(value = "风速")
    private String windSpeed2;
}
