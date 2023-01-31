package com.hejz.dto;

import com.hejz.common.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
}
