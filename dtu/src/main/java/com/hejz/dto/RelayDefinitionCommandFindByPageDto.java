package com.hejz.dto;

import com.hejz.common.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-31 21:29
 * @Description:
 */
@Data
public class RelayDefinitionCommandFindByPageDto extends Page {
    @ApiModelProperty(value = "dtuId")
    private Long dtuId;
    @ApiModelProperty(value = "名称")
    private String name;
}
