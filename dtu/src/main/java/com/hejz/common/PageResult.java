package com.hejz.common;

import lombok.Data;

import java.util.List;

//@Data
public class PageResult<T> {
    private Integer page;
    private Integer limit;
    private Long total;
    private List<T> items;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page+1;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
