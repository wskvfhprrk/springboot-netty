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
 * @Description: 传感器数据
 */
@Data
@Entity(name = "sensor_data")
@org.hibernate.annotations.Table(appliesTo = "sensor_data", comment = "传感器数据")
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
            columnDefinition="datetime"+" COMMENT '接收时间'"
    )
    private Date createDate;
    @ManyToOne
    @JoinColumn(name = "dtu_id",nullable = false)
    private DtuInfo dtuInfo;
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

    public SensorDataDb(Date createDate, DtuInfo dtuInfo, String names, String data, String units) {
        this.createDate=createDate;
        this.dtuInfo=dtuInfo;
        this.names=names;
        this.data=data;
        this.units=units;
    }
}
