package com.hejz.studay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 17:09
 * @Description: 数据校检规则
 */
@Data
@Entity(name = "detection_rules")
@NoArgsConstructor
@AllArgsConstructor
public class DataCheckingRules {
    @Id
    @SequenceGenerator(
            name = "Crc6 Detection Rules Sequence",
            sequenceName = "crc6_detection_rules_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "crc6_detection_rules_sequence"
    )

    @Column(
            name = "id",
            nullable = false,
            columnDefinition="int"+" COMMENT 'ID'"
    )
    //id
    private Integer id;
    @Column(
            name = "name",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT 'name'"
    )
    private String name;
    //共有长度
    @Column(
            name = "common length",
            nullable = false,
            columnDefinition="int"+" COMMENT '共有长度'"
    )
    private Integer commonLength;
    //地址位
    @Column(
            name = "address_bit",
            nullable = false,
            columnDefinition="bit"+" COMMENT '地址位'"
    )
    private Integer AddressBit;
    //功能码
    @Column(
            name = "function_code",
            nullable = false,
            columnDefinition="bit"+" COMMENT '功能码'"
    )
    private Integer functionCode;
    //数据位数
    @Column(
            name = "data_bits",
            nullable = false,
            columnDefinition="bit"+" COMMENT '数据位数'"
    )
    private Integer DataBits;
    //16进制数据值
    @Column(
            name = "data_value",
            nullable = false,
            columnDefinition="bit"+" COMMENT '16进制数据值'"
    )
    private Integer DataValue;
    //crc16校验位数
    @Column(
            name = "crc16 check digit",
            nullable = false,
            columnDefinition="bit"+" COMMENT 'crc16校验位数'"
    )
    private Integer crc16CheckDigit;
}