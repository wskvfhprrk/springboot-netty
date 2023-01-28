package com.hejz.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private Integer page;
//    private Integer pageSize;
    private Integer limit;
    private Long total;
    private List<T> items;
}
