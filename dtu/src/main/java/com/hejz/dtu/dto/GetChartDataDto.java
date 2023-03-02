package com.hejz.dtu.dto;

import com.hejz.dtu.common.Page;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-03-02 06:52
 * @Description:
 */
@Data
public class GetChartDataDto extends Page {
    private Long dtuId;
    private Integer orderNumber;
    private String type;
    private String backgroundColor;
}
