package com.hejz.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private Integer page;
    private Integer limit;
    private Long total;
    private List<T> items;


    public void setPage(Integer page) {
        this.page = page+1;
    }

}
