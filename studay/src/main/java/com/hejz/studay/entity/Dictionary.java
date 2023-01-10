package com.hejz.studay.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 数据字典实体类
 * author: hejz
 * data: 2022-5-9
 */
@Data
@Entity(name = "dictionary")
public class Dictionary implements Serializable{

    @Id
    @SequenceGenerator(
            name = "dictionary_sequence",
            sequenceName = "dictionary_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "dictionary_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;

    @Column(
            name = "parent_id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT '父ID'"
    )
    private Long parentId;

    @Column(
            name = "tenant_id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT '租户ID'"
    )
    private Long tenantId;

    @Column(
            name = "app_module",
            nullable = true,
            columnDefinition="varchar(50)"+" COMMENT '应用模块'"
    )
    private String appModule;

    @Column(
            name = "type",
            nullable = false,
            columnDefinition="varchar(50)"+" COMMENT '字典类型'"
    )
    private String type;

    @Column(
            name = "item_name",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '显示名'"
    )
    private String itemName;

    @Column(
            name = "item_value",
            nullable = true,
            columnDefinition="varchar(100)"+" COMMENT '存储值'"
    )
    private String itemValue;

    @Column(
            name = "description",
            nullable = true,
            columnDefinition="varchar(100)"+" COMMENT '描述说明'"
    )
    private String description;

    @Column(
            name = "extdata",
            nullable = true,
            columnDefinition="varchar(200)"+" COMMENT '扩展JSON'"
    )
    private String extdata;

    @Column(
            name = "sort_id",
            nullable = false,
            columnDefinition="int"+" COMMENT '排序号'"
    )
    private Integer sortId;

    @Column(
            name = "is_editable",
            nullable = false,
            columnDefinition="int"+" COMMENT '是否可改'"
    )
    private Integer isEditable;

    @Column(
            name = "is_deletable",
            nullable = false,
            columnDefinition="int"+" COMMENT '是否可删'"
    )
    private Integer isDeletable;

    @Column(
            name = "is_deleted",
            nullable = false,
            columnDefinition="int"+" COMMENT '删除标记'"
    )
    private Integer isDeleted;

    @Column(
            name = "create_time",
            nullable = false,
            columnDefinition="date"+" COMMENT '创建时间'"
    )
    private java.util.Date createTime;
}
