package com.hejz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 17:09
 * @Description: 数据校检规则
 */
@Data
@Entity(name = "checking_rules")
@NoArgsConstructor
@AllArgsConstructor
public class CheckingRules implements Serializable {
    @Id
    @SequenceGenerator(
            name = "checking_rules_sequence",
            sequenceName = "checking_rules_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "checking_rules_sequence"
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
            name = "address_bit_length",
            nullable = false,
            columnDefinition="bit"+" COMMENT '地址位'"
    )
    private Integer AddressBitLength;
    //功能码
    @Column(
            name = "function_code_length",
            nullable = false,
            columnDefinition="bit"+" COMMENT '功能码'"
    )
    private Integer functionCodeLength;
    //数据位数
    @Column(
            name = "data_bits_length",
            nullable = false,
            columnDefinition="bit"+" COMMENT '数据位数'"
    )
    private Integer DataBitsLength;
    //16进制数据值
    @Column(
            name = "data_value_length",
            nullable = false,
            columnDefinition="int(2)"+" COMMENT '16进制数据值'"
    )
    private Integer DataValueLength;
    //crc16校验位数
    @Column(
            name = "crc16_check_digit_length",
            nullable = false,
            columnDefinition="int(2)"+" COMMENT 'crc16校验位数'"
    )
    private Integer crc16CheckDigitLength;

    public CheckingRules(String name, Integer commonLength, Integer addressBitLength, Integer functionCodeLength, Integer dataBitsLength, Integer dataValueLength, Integer crc16CheckDigitLength) {
        this.name = name;
        this.commonLength = commonLength;
        AddressBitLength = addressBitLength;
        this.functionCodeLength = functionCodeLength;
        DataBitsLength = dataBitsLength;
        DataValueLength = dataValueLength;
        this.crc16CheckDigitLength = crc16CheckDigitLength;
    }
}
