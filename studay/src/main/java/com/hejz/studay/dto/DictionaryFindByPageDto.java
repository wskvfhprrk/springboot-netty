package com.hejz.studay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.hejz.studay.common.Page;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class DictionaryFindByPageDto extends Page {
    @ApiModelProperty(value = "父ID")
    private Long parentId;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;
    @ApiModelProperty(value = "应用模块")
    private String appModule;
    @ApiModelProperty(value = "字典类型")
    private String type;
    @ApiModelProperty(value = "显示名")
    private String itemName;
    @ApiModelProperty(value = "存储值")
    private String itemValue;
    @ApiModelProperty(value = "描述说明")
    private String description;
    @ApiModelProperty(value = "扩展JSON")
    private String extdata;
    @ApiModelProperty(value = "排序号")
    private Integer sortId;
    @ApiModelProperty(value = "是否可改")
    private Integer isEditable;
    @ApiModelProperty(value = "是否可删")
    private Integer isDeletable;
    @ApiModelProperty(value = "删除标记")
    private Integer isDeleted;
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
}
