package com.hejz.studay.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalElements;
    private List<T> content;
}
