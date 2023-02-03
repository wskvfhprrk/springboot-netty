package com.hejz.dto;

import com.hejz.common.Page;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-03 21:10
 * @Description:
 */
@Data
public class CheckingRulesFindByPageDto extends Page {
    private String name;
    private Integer commonLength;
}
