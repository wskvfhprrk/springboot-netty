package com.hejz.studay.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-04 06:50
 * @Description: 继电器
 */
@Data
@Entity(name = "relay")
public class Relay {
    @Id
    @SequenceGenerator(
            name = "relay_sequence",
            sequenceName = "relay_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "relay_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;
    private String imei;
    /**
     * 感应器编号地址——发出接收时指令地址位（每个感应器都有一个地址位的）
     */
    private Integer adrss;
    private String name;
    /**
     * 关联打开命令发出的指令
     */
    private String opneHex;
    /**
     * 关联关闭命令发出的指令
     */
    private String closeHex;
    /**
     * 开关通路的时间毫秒——dtu每次轮询上报时间要大于此时间，至少大于10秒钟
     */
    private Long accessTime;
    /**
     * 关联发出的链接
     */
    private String url;
    /**
     * 备注信息
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getAdrss() {
        return adrss;
    }

    public void setAdrss(Integer adrss) {
        this.adrss = adrss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpneHex() {
        return opneHex;
    }

    public void setOpneHex(String opneHex) {
        this.opneHex = opneHex;
    }

    public String getCloseHex() {
        return closeHex;
    }

    public void setCloseHex(String closeHex) {
        this.closeHex = closeHex;
    }

    public Long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Long accessTime) {
        this.accessTime = accessTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Relay() {

    }
    public Relay(Long id, String imei, Integer adrss, String name, String opneHex,String closeHex,Long accessTime, String url, String remark) {
        this.id = id;
        this.imei = imei;
        this.name = name;
        this.opneHex = opneHex;
        this.closeHex = closeHex;
        this.accessTime = accessTime;
        this.url = url;
        this.remark = remark;
        this.adrss = adrss;
    }

    @Override
    public String toString() {
        return "Control{" +
                "id=" + id +
                ", imei='" + imei + '\'' +
                ", adrss=" + adrss +
                ", name='" + name + '\'' +
                ", opneHex='" + opneHex + '\'' +
                ", closeHex='" + closeHex + '\'' +
                ", accessTime='" + accessTime + '\'' +
                ", url='" + url + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
