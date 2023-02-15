package com.hejz.dtu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictionaryUpdateVo {
    @ApiModelProperty(value = "数据字典ID")
    private Long id;
    @ApiModelProperty(value = "应用模块")
    private String appModule;
    @ApiModelProperty(value = "创建时间")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private java.util.Date createTime;
    @ApiModelProperty(value = "描述说明")
    private String description;
    @ApiModelProperty(value = "是否已用")
    private Boolean isUse;
    @ApiModelProperty(value = "显示名")
    private String itemName;
    @ApiModelProperty(value = "存储值")
    private String itemValue;
    @ApiModelProperty(value = "排序号")
    private Integer sortId;
    @ApiModelProperty(value = "字典类型")
    private Integer type;
    @ApiModelProperty(value = "上一级ID")
    private Long parentId;
}
