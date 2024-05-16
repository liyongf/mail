package com.sdzk.buss.web.aqbzh.util;


import javax.validation.constraints.Min;

public class PageQuery {


    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;


    @Min(value = 1, message = "每页展示数据不合法")
    private int pageSize;


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
