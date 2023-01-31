package com.hejz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-31 20:57
 * @Description:
 */
@Data
public class DtuInfoFindByPageVo {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "imei")
    private String imei;
    @ApiModelProperty(value = "感应器地址顺序")
    private String sensorAddressOrder;
    @ApiModelProperty(value = "dtu注册信息的长度")
    private Integer registrationLength = 89;
    @ApiModelProperty(value = "imei长度固定值")
    private Integer imeiLength = 15;
    @ApiModelProperty(value = "继电器返回值检测规则,多个以逗号隔开，首个为地址位，末位为规则id")
    private String relayCheckingRulesIds;
    @ApiModelProperty(value = "感应器返回值检测规则,多个以逗号隔开，首个为地址位，末位为规则id")
    private String sensorCheckingRulesIds;
    @ApiModelProperty(value = "心跳bates长度")
    private Integer heartbeatLength=2;
    @ApiModelProperty(value = "每组发送接收间隔时间(毫秒)——与dtu每组间隔时间要一致")
    private Integer intervalTime;
    @ApiModelProperty(value = "是否自动控制——1是自动0是手动控制")
    private Boolean automaticAdjustment = true;
    @ApiModelProperty(value = "返回值是否带imei:true带imei,false不带")
    private Boolean noImei = true;
}
