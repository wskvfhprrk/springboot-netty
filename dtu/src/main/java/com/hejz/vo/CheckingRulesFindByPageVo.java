package com.hejz.vo;

import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-03 21:10
 * @Description:
 */
@Data
public class CheckingRulesFindByPageVo {
    private Integer id;
    private String name;
    private Integer commonLength;
    private Integer AddressBitLength;
    private Integer functionCodeLength;
    private Integer DataBitsLength;
    private Integer DataValueLength;
    private Integer crc16CheckDigitLength;
}
