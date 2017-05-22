package com.sipuang.entwebsit.domain;

/**
 * Created by lj on 2017/2/22.
 */
public class PageQueryVo {
    /**
     * 当前页总数
     */
    private int rows = 20;
    /**
     * 当前
     */
    private int page = 1;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
