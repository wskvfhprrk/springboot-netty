package com.hejz.entity;

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
            name = "detection_rules_sequence",
            sequenceName = "detection_rules_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "detection_rules_sequence"
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
            name = "common_length",
            nullable = false,
            columnDefinition="int(2)"+" COMMENT '共有长度'"
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
            columnDefinition="int(2)"+" COMMENT '16进制数据值'"
    )
    private Integer DataValue;
    //crc16校验位数
    @Column(
            name = "crc16_check_digit_length",
            nullable = false,
            columnDefinition="int(2)"+" COMMENT 'crc16校验位数'"
    )
    private Integer crc16CheckDigitLength;

    public DataCheckingRules(String name, Integer commonLength, Integer addressBit, Integer functionCode, Integer dataBits, Integer dataValue, Integer crc16CheckDigitLength) {
        this.name = name;
        this.commonLength = commonLength;
        AddressBit = addressBit;
        this.functionCode = functionCode;
        DataBits = dataBits;
        DataValue = dataValue;
        this.crc16CheckDigitLength = crc16CheckDigitLength;
    }
}