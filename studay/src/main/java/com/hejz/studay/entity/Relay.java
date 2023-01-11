package com.hejz.studay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-04 06:50
 * @Description: 继电器
 */
@Data
@Entity(name = "relay")
@NoArgsConstructor
@AllArgsConstructor
public class Relay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;

    @Column(
            name = "imei",
            nullable = false,
            columnDefinition="varchar(15)"+" COMMENT 'imei'"
    )
    private String imei;
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
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '关联打开命令发出的指令'"
    )
    private String opneHex;
    /**
     * 关联关闭命令发出的指令
     */
    @Column(
            name = "close_hex",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '关联关闭命令发出的指令'"
    )
    private String closeHex;
    /**
     * 开关通路的时间毫秒——dtu每次轮询上报时间要大于此时间，至少大于10秒钟
     */
    @Column(
            name = "access_time",
            nullable = false,
            columnDefinition="bigint"+" COMMENT '开关通路的时间毫秒——dtu每次轮询上报时间要大于此时间，至少大于10秒钟'"
    )
    private Long accessTime;
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

}
