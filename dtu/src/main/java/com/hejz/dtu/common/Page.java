package com.hejz.dtu.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotEmpty;

/**
 * page父类
 */
@Data
public class Page {
    //page=1&limit=20&sort=%2Bid
    @ApiModelProperty(value = "第几页",required = true,example = "0")
    @NotEmpty
    private Integer page;
    @ApiModelProperty(value = "每页多少行",required = true,example = "5")
    @NotEmpty
    private Integer limit;
    @ApiModelProperty(value = "排序",required = true,example = "")
    private String sort;

    public void setPage(Integer page) {
        this.page = page-1;
    }
}
