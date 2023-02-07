package com.hejz.dtu.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

/**
 * dtu信息实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "tb_dtu_info")
@org.hibernate.annotations.Table(appliesTo = "tb_dtu_info", comment = "dtu信息")
public class DtuInfo implements Serializable{

    @Id
    @SequenceGenerator(
            name = "tb_dtu_info_sequence",
            sequenceName = "tb_dtu_info_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_dtu_info_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;

    @Column(
            name = "automatic_adjustment",
            nullable = true,
            columnDefinition="date"+" COMMENT '是否自动控制——true是自动false是手动控制'"
    )
    private Boolean automaticAdjustment;

    @Column(
            name = "imei",
            nullable = false,
            columnDefinition="varchar(15)"+" COMMENT 'imei'"
    )
    private String imei;

    @Column(
            name = "interval_time",
            nullable = true,
            columnDefinition="int"+" COMMENT '每组轮询指令隔时间(毫秒)——与dtu每组间隔时间要一致'"
    )
    private Integer intervalTime;

    @Column(
            name = "registration_length",
            nullable = true,
            columnDefinition="int"+" COMMENT 'dtu注册信息的长度'"
    )
    private Integer registrationLength;

    @Column(
            name = "sensor_address_order",
            nullable = false,
            columnDefinition="varchar(60)"+" COMMENT '感应器地址顺序'"
    )
    private String sensorAddressOrder;

    @Column(
            name = "user_id",
            nullable = true,
            columnDefinition="int"+" COMMENT ''"
    )
    private Integer userId;
    /**
     * 外键表——tb_user中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User User;
}
