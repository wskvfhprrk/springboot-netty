package com.hejz.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-31 08:58
 * @Description:
 */
@Data
public class SensorDataDbFindByPageVo {
    private Long id;
    private Date createDate;
    private String names;
    private String data;
    private String units;
}
