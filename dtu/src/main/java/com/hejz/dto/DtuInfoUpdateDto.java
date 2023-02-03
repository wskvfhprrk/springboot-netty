package com.hejz.dto;

import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-03 16:44
 * @Description:
 */
@Data
public class DtuInfoUpdateDto {
    private Long id;
    private String imei;
    private String sensorAddressOrder;
    private Integer registrationLength = 89;
    private Integer imeiLength = 15;
    private String relayCheckingRulesIds;
    private String sensorCheckingRulesIds;
    private Integer heartbeatLength = 2;
    private Integer intervalTime;
    private Boolean automaticAdjustment;
    private Boolean noImei;
}
