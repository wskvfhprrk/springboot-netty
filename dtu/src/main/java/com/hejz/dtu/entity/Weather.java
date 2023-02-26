package com.hejz.dtu.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-25 19:00
 * @Description: 天气网爬虫
 */
@Data
@Entity(name = "sys_weather")
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "sys_weather", comment = "天气网预报天气")
public class Weather {
    //ID
    @Id
    @SequenceGenerator(
            name = "sys_weather_sequence",
            sequenceName = "sys_weather_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sys_weather_sequence"
    )
    @Column(
            name = "id",
            columnDefinition="bigint"+" COMMENT '天气网ID'"
    )
    private Long id;
    //抓取时间
    @Column(
            name = "create_time",
            columnDefinition="datetime"+" COMMENT '抓取时间'"
    )
    private Date createTime;
    //描述
    @Column(
            name = "remarks",
            columnDefinition="varchar(40)"+" COMMENT '描述'"
    )
    private String remarks;
    //城市
    @Column(
            name = "city",
            columnDefinition="varchar(20)"+" COMMENT '城市'"
    )
    private String city;
    //白天
    @Column(
            name = "dn1",
            columnDefinition="varchar(20)"+" COMMENT '黑夜'"
    )
    private String dn1;
    //天气
    @Column(
            name = "weather1",
            columnDefinition="varchar(20)"+" COMMENT '天气'"
    )
    private String weather1;
    //温度
    @Column(
            name = "temperature1",
            columnDefinition="varchar(20)"+" COMMENT '温度'"
    )
    private String temperature1;
    //风向
    @Column(
            name = "wind_direction1",
            columnDefinition="varchar(20)"+" COMMENT '风向'"
    )
    private String windDirection1;
    //风速
    @Column(
            name = "wind_speed1",
            columnDefinition="varchar(20)"+" COMMENT '风速'"
    )
    private String windSpeed1;
    //
    @Column(
            name = "dn2",
            columnDefinition="varchar(20)"+" COMMENT '黑夜'"
    )
    private String dn2;
    //天气
    @Column(
            name = "weather2",
            columnDefinition="varchar(20)"+" COMMENT '天气'"
    )
    private String weather2;
    //温度
    @Column(
            name = "temperature2",
            columnDefinition="varchar(20)"+" COMMENT '温度'"
    )
    private String temperature2;
    //风向
    @Column(
            name = "wind_direction2",
            columnDefinition="varchar(20)"+" COMMENT '风向'"
    )
    private String windDirection2;
    //风速
    @Column(
            name = "wind_speed2",
            columnDefinition="varchar(20)"+" COMMENT '风速'"
    )
    private String windSpeed2;
}
