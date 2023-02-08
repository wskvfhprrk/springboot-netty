package com.hejz.dtu.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 数据字典实体类实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "dictionary")
@org.hibernate.annotations.Table(appliesTo = "dictionary", comment = "数据字典实体类")
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
            columnDefinition="bigint"+" COMMENT '数据字典ID'"
    )
    private Long id;

    @Column(
            name = "app_module",
            nullable = true,
            columnDefinition="varchar(50)"+" COMMENT '应用模块'"
    )
    private String appModule;

    @Column(
            name = "create_time",
            nullable = false,
            columnDefinition="date"+" COMMENT '创建时间'"
    )
    private java.util.Date createTime;

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
            name = "is_editable",
            nullable = false,
            columnDefinition="int"+" COMMENT '是否可改'"
    )
    private Integer isEditable;

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
            name = "sort_id",
            nullable = false,
            columnDefinition="int"+" COMMENT '排序号'"
    )
    private Integer sortId;

    @Column(
            name = "tenant_id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT '租户ID'"
    )
    private Long tenantId;

    @Column(
            name = "type",
            nullable = false,
            columnDefinition="varchar(50)"+" COMMENT '字典类型'"
    )
    private String type;

    @Column(
            name = "parent_id",
            nullable = true,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long parentId;
    /**
     * 外键表——dictionary中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "parent_id",insertable = false,updatable = false)
    private Dictionary dictionary;
}
