package com.fxbank.cip.manager.entity;

import java.util.List;

public class BaseData <T>{
    private long pageTotal;//总页数
    private int limit=10;//每页多少行
    private int page=1;//当前第几页
    private int pageCountRows;//总行数

    private List<T> pageList;//分页实体集合

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getPageList() {
        return pageList;
    }
    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }
    public long getPageTotal() {
        return pageTotal;
    }
    public void setPageTotal(long pageTotal) {
        this.pageTotal = pageTotal;
    }
    public int getPageCountRows() {
        return pageCountRows;
    }
    public void setPageCountRows(int pageCountRows) {
        this.pageCountRows = pageCountRows;
    }
}
