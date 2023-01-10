package com.hejz.studay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-09 08:54
 * @Description: 传感器返回值-不是写入数据库中的
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {
    private Integer id;
    private String name;
    private Double data;

    /**
     * 接收到数据的单位
     */
    private String unit;

}
