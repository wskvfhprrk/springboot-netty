package com.hejz.dtu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictionaryFindByPageVo{
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "应用模块")
    private String appModule;
    @ApiModelProperty(value = "创建时间")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private java.util.Date createTime;
    @ApiModelProperty(value = "描述说明")
    private String description;
    @ApiModelProperty(value = "扩展JSON")
    private String extdata;
    @ApiModelProperty(value = "是否可删")
    private Integer isDeletable;
    @ApiModelProperty(value = "删除标记")
    private Integer isDeleted;
    @ApiModelProperty(value = "是否可改")
    private Integer isEditable;
    @ApiModelProperty(value = "显示名")
    private String itemName;
    @ApiModelProperty(value = "存储值")
    private String itemValue;
    @ApiModelProperty(value = "排序号")
    private Integer sortId;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;
    @ApiModelProperty(value = "字典类型")
    private String type;
    @ApiModelProperty(value = "ID")
    private Long parentId;
}
