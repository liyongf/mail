package com.sdzk.buss.web.homePage.entity;

public class HiddenDangerByGroupVO {
    /**部门ID*/
    private String id;
    /**部门名称*/
    private String name;
    /**隐患总数*/
    private Double total;
    /**已闭环数量*/
    private Double close;
    /**未闭环数量*/
    private Double notClose;
    /**未闭环占比*/
    private Double percent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getNotClose() {
        return notClose;
    }

    public void setNotClose(Double notClose) {
        this.notClose = notClose;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
