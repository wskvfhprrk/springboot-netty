package com.hejz.dtu.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 数据校检规则实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "checking_rules")
@org.hibernate.annotations.Table(appliesTo = "checking_rules", comment = "数据校检规则")
public class CheckingRules implements Serializable{

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
    private Integer id;

    @Column(
            name = "address_bit_length",
            nullable = false,
            columnDefinition="int"+" COMMENT '地址位'"
    )
    private Integer addressBitLength;

    @Column(
            name = "data_bits_length",
            nullable = false,
            columnDefinition="int"+" COMMENT '数据位数'"
    )
    private Integer dataBitsLength;

    @Column(
            name = "data_value_length",
            nullable = false,
            columnDefinition="int"+" COMMENT '16进制数据值'"
    )
    private Integer dataValueLength;

    @Column(
            name = "common_length",
            nullable = false,
            columnDefinition="int"+" COMMENT '共有长度'"
    )
    private Integer commonLength;

    @Column(
            name = "crc16_check_digit_length",
            nullable = false,
            columnDefinition="int"+" COMMENT 'crc16校验位数'"
    )
    private Integer crc16CheckDigitLength;

    @Column(
            name = "function_code_length",
            nullable = false,
            columnDefinition="int"+" COMMENT '功能码'"
    )
    private Integer functionCodeLength;

    @Column(
            name = "is_use",
            nullable = false,
            columnDefinition="date"+" COMMENT '是否正在使用——true正在使用，flase没有使用'"
    )
    private Boolean isUse;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT 'name'"
    )
    private String name;
}
