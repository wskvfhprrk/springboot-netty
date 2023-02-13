package com.hejz.dtu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * 传感器数据实体类
 * author: hejz
 * data: 2023-2-7
 */
@Getter
@Setter
@Entity(name = "sensor_data")
@org.hibernate.annotations.Table(appliesTo = "sensor_data", comment = "传感器数据")
@NoArgsConstructor
@AllArgsConstructor
public class SensorData implements Serializable{

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
            columnDefinition="bigint"+" COMMENT '传感器数据ID'"
    )
    private Long id;

    @Column(
            name = "create_date",
            nullable = false,
            columnDefinition="date"+" COMMENT '接收时间'"
    )
    private java.util.Date createDate;

    @Column(
            name = "data",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '接收到数据'"
    )
    private String data;

    @Column(
            name = "names",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '接收到数据的names'"
    )
    private String names;

    @Column(
            name = "units",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '接收到数据的单位'"
    )
    private String units;
    /**
     * 外键表——tb_dtu_info中的字段id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dtu_id",insertable = false,updatable = false)
    @JsonIgnoreProperties(value = {"sensorDatas"})
    private DtuInfo dtuInfo;

    @Transient
    private String address;

    public SensorData(Date createDate, DtuInfo dtuInfo, String names, String data, String units) {
        this.createDate=createDate;
        this.dtuInfo=dtuInfo;
        this.names=names;
        this.data=data;
        this.units=units;
    }


}
