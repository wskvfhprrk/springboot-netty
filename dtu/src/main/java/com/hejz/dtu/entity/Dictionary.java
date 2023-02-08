package com.hejz.dtu.entity;

import com.hejz.dtu.enm.DictionaryTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * 数据字典实体类实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "dictionary")
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "dictionary", comment = "数据字典")
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
            name = "is_use",
            nullable = false,
            columnDefinition="bit"+" COMMENT '是否已用'"
    )
    private Boolean isUse;


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
            columnDefinition="int(2)"+" COMMENT '排序号'"
    )
    private Integer sortId;

    @Column(
            name = "type",
            nullable = false,
            columnDefinition="int(1)"+" COMMENT '字典类型'"
    )
    private DictionaryTypeEnum type;

    /**
     * 外键表——dictionary中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "parent_id",columnDefinition = "bigint"+" COMMENT '上一级ID'")
    private Dictionary dictionary;

    public Dictionary(String appModule, Date createTime, String description, Boolean isUse, String itemName, String itemValue, Integer sortId, DictionaryTypeEnum type, Dictionary dictionary) {
        this.appModule = appModule;
        this.createTime = createTime;
        this.description = description;
        this.isUse = isUse;
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.sortId = sortId;
        this.type = type;
        this.dictionary = dictionary;
    }

    public Dictionary(Long id) {
        this.id = id;
    }
}
