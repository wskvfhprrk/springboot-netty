package com.hejz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-03 12:39
 * @Description: dtu信息
 */
@Data
@Entity(name = "tb_dtu_info")
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "tb_dtu_info", comment = "dtu信息")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class DtuInfo implements Serializable {
    @Id
    @SequenceGenerator(
            name = "dtu_info_sequence",
            sequenceName = "dtu_info_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "dtu_info_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;
    @Column(
            name = "imei",
            nullable = false,
            columnDefinition="varchar(15)"+" COMMENT 'imei'"
    )
    private String imei;
    //感应器地址顺序
    @Column(
            name = "sensor_address_order",
            nullable = false,
            columnDefinition="varchar(60)"+" COMMENT '感应器地址顺序'"
    )
    private String sensorAddressOrder;
    /**
     * dtu注册信息的长度
     */
    @Column(
            name = "registration_length",
            columnDefinition="int(2) default 89"+" COMMENT 'dtu注册信息的长度'"
    )
    private Integer registrationLength = 89;
    /**
     * 每组发送接收间隔时间(毫秒)和心跳时间——略小于dtu每组间隔时间，大于每组中每个发送间隔时间
     */
    @Column(
            name = "interval_time",
            nullable = true,
            columnDefinition="int(10)"+" COMMENT '每组轮询指令隔时间(毫秒)——与dtu每组间隔时间要一致'"
    )
    private Integer intervalTime;
    /**
     * 是否自动控制——1是自动0是手动控制
     */
    @Column(
            name = "automatic_adjustment",
            nullable = true,
            columnDefinition = "bit(1)" + " COMMENT '是否自动控制——true是自动false是手动控制'"
    )
    private Boolean automaticAdjustment = true;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true)
    private User user;


    public DtuInfo(Long id) {
        this.id = id;
    }

    public DtuInfo(String imei, Integer registrationLength, Integer intervalTime, Boolean automaticAdjustment, String sensorAddressOrder) {
        this.imei = imei;
        this.registrationLength = registrationLength;
        this.intervalTime = intervalTime;
        this.automaticAdjustment = automaticAdjustment;
        this.sensorAddressOrder = sensorAddressOrder;
    }
}
