package com.example.whitecommunity.result;

import java.util.List;

public class PageResult<T> {
    private List<T> list;
    private int total;

    public PageResult(List<T> list, int total) {
        this.list = list;
        this.total = total;
    }

    public List<T> getList() {
        return this.list;
    }

    public int getTotal() {
        return total;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
