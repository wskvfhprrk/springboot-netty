package com.hejz.studay.dto;

import lombok.Data;

@Data
public class DictionaryAllDto {
    private Long id;
    private Long parentId;
    private Long tenantId;
    private String appModule;
    private String type;
    private String itemName;
    private String itemValue;
    private String description;
    private String extdata;
    private Integer sortId;
    private Integer isEditable;
    private Integer isDeletable;
    private Integer isDeleted;
    private java.util.Date createTime;
}
