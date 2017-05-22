package com.sipuang.entwebsit.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lj on 2017/2/14.
 * 分页显示器
 */
public class PageVo {
    /**
     * 总的记录数
     */
    private long total;
    /**
     * 记录
     */
    private List<?> rows = new ArrayList<>();

    public PageVo(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
