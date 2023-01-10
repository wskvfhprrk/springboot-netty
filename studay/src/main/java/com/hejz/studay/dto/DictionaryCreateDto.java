package com.hejz.studay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class DictionaryCreateDto {
    @ApiModelProperty(value = "父ID",required = true)
    @NotEmpty
    private Long parentId;
    @ApiModelProperty(value = "租户ID",required = true)
    @NotEmpty
    private Long tenantId;
    @ApiModelProperty(value = "应用模块")
    private String appModule;
    @ApiModelProperty(value = "字典类型",required = true)
    @NotEmpty
    private String type;
    @ApiModelProperty(value = "显示名",required = true)
    @NotEmpty
    private String itemName;
    @ApiModelProperty(value = "存储值")
    private String itemValue;
    @ApiModelProperty(value = "描述说明")
    private String description;
    @ApiModelProperty(value = "扩展JSON")
    private String extdata;
    @ApiModelProperty(value = "排序号",required = true,example = "22")
    @NotEmpty
    private Integer sortId;
    @ApiModelProperty(value = "是否可改",required = true,example = "22")
    @NotEmpty
    private Integer isEditable;
    @ApiModelProperty(value = "是否可删",required = true,example = "22")
    @NotEmpty
    private Integer isDeletable;
    @ApiModelProperty(value = "删除标记",required = true,example = "22")
    @NotEmpty
    private Integer isDeleted;
    @ApiModelProperty(value = "创建时间",required = true)
    @NotEmpty
    private java.util.Date createTime;
}
