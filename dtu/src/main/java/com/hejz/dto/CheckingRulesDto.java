package com.hejz.dto;

import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-03 21:08
 * @Description:
 */
@Data
public class CheckingRulesDto {
    private String name;
    private Integer commonLength;
    private Integer AddressBitLength;
    private Integer functionCodeLength;
    private Integer DataBitsLength;
    private Integer DataValueLength;
    private Integer crc16CheckDigitLength;
}
