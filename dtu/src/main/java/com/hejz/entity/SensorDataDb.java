package com.hejz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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
public class SensorDataDb implements Serializable {
    @Id
    @SequenceGenerator(
            name = "sensor_data_sequence",
            sequenceName = "sensor_data_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sensor_data_sequence"
    )
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
            name = "dtu_id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'dtuId'"
    )
    private Long dtuId;
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

    public SensorDataDb(Date createDate, Long dtuId, String names, String data, String units) {
        this.createDate=createDate;
        this.dtuId=dtuId;
        this.names=names;
        this.data=data;
        this.units=units;
    }
}
