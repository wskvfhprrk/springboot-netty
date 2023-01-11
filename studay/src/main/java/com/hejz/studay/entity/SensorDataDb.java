package com.hejz.studay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-09 10:00
 * @Description: 传感器数据存放进数据库
 */
@Data
@Entity(name = "sensor_data")
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataDb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;
    @Column(
            name = "create_date",
            nullable = false,
            columnDefinition="datetime"+" COMMENT '接收到数据的单位'"
    )
    private Date createDate;
    @Column(
            name = "imei",
            nullable = false,
            columnDefinition="varchar(15)"+" COMMENT 'imei'"
    )
    private String imei;
    @Column(
            name = "names",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '接收到数据的names'"
    )
    private String names;
    @Column(
            name = "data",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '接收到数据'"
    )
    private String data;
    @Column(
            name = "units",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '接收到数据的单位'"
    )
    private String units;

    public SensorDataDb(Date createDate, String imei, String names, String data, String units) {
        this.createDate=createDate;
        this.imei=imei;
        this.names=names;
        this.data=data;
        this.units=units;
    }
}
