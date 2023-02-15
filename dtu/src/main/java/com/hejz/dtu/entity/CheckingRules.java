package com.hejz.dtu.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 数据校检规则实体类
 * author: hejz
 * data: 2023-2-7
 */
@Getter
@Setter
@Entity(name = "equ_checking_rules")
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "equ_checking_rules", comment = "数据校检规则")
public class CheckingRules implements Serializable{

    @Id
    @SequenceGenerator(
            name = "equ_checking_rules_sequence",
            sequenceName = "equ_checking_rules_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "equ_checking_rules_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="int"+" COMMENT '数据校检规则ID'"
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
            columnDefinition = "bit" + " COMMENT '是否正在使用——true正在使用，flase没有使用'"
    )
    private Boolean isUse;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "varchar(255)" + " COMMENT 'name'"
    )
    private String name;


    public CheckingRules(String name, Integer commonLength, Integer functionCodeLength, Integer addressBitLength, Integer dataBitsLength, Integer dataValueLength, Integer crc16CheckDigitLength, Boolean isUse) {
        this.addressBitLength = addressBitLength;
        this.dataBitsLength = dataBitsLength;
        this.dataValueLength = dataValueLength;
        this.commonLength = commonLength;
        this.crc16CheckDigitLength = crc16CheckDigitLength;
        this.functionCodeLength = functionCodeLength;
        this.isUse = isUse;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckingRules that = (CheckingRules) o;
        return Objects.equals(id, that.id) && Objects.equals(addressBitLength, that.addressBitLength) && Objects.equals(dataBitsLength, that.dataBitsLength) && Objects.equals(dataValueLength, that.dataValueLength) && Objects.equals(commonLength, that.commonLength) && Objects.equals(crc16CheckDigitLength, that.crc16CheckDigitLength) && Objects.equals(functionCodeLength, that.functionCodeLength) && Objects.equals(isUse, that.isUse) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addressBitLength, dataBitsLength, dataValueLength, commonLength, crc16CheckDigitLength, functionCodeLength, isUse, name);
    }
}
