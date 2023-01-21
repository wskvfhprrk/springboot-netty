package com.hejz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-04 06:50
 * @Description: 继电器
 */
@Data
@Entity(name = "relay")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Relay implements Serializable {
    @Id
    @SequenceGenerator(
            name = "relay_sequence",
            sequenceName = "relay_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "relay_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;

    @Column(
            name = "dtu_id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'dtuId'"
    )
    private Long dtuId;
    /**
     * 感应器编号地址——发出接收时指令地址位（每个感应器都有一个地址位的）
     */
    @Column(
            name = "adrss",
            nullable = false,
            columnDefinition="varchar(15)"+" COMMENT '感应器编号地址——发出接收时指令地址位（每个感应器都有一个地址位的）'"
    )
    private Integer adrss;
    @Column(
            name = "name",
            nullable = true,
            columnDefinition="varchar(15)"+" COMMENT '名称'"
    )
    private String name;
    /**
     * 关联打开命令发出的指令
     */
    @Column(
            name = "opne_hex",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '关联打开命令发出的指令'"
    )
    private String opneHex;
    /**
     * 关联关闭命令发出的指令
     */
    @Column(
            name = "close_hex",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '关联关闭命令发出的指令'"
    )
    private String closeHex;
    /**
     * 关联发出的链接
     */
    @Column(
            name = "url",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '关联发出的链接'"
    )
    private String url;
    /**
     * 备注信息
     */
    @Column(
            name = "remark",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '备注信息'"
    )
    private String remark;

    public Relay( Long dtuId, Integer adrss, String name, String opneHex, String closeHex, String url, String remark) {
        this.dtuId = dtuId;
        this.adrss = adrss;
        this.name = name;
        this.opneHex = opneHex;
        this.closeHex = closeHex;
        this.url = url;
        this.remark = remark;
    }
}
