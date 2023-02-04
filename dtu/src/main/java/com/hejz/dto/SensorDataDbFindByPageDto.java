package com.hejz.dto;

import com.hejz.common.Page;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-31 09:00
 * @Description:
 */
@Data
public class SensorDataDbFindByPageDto extends Page {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Long dtuId;
}
