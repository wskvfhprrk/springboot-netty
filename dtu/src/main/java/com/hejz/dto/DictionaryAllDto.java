package com.hejz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
}
