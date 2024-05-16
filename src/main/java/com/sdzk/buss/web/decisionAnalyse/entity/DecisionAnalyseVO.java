package com.sdzk.buss.web.decisionAnalyse.entity;

/**
 * Created by Administrator on 17-5-15.
 */
public class DecisionAnalyseVO {
    //煤矿名称
    private String mineName;
    //单位id
    private String unitId;
    //单位名称
    private String unitName;
    //隐患统计
    private String hiddenTotal;
    //重大隐患统计
    private String zdTotal;
    //风险统计
    private String fxTotal;
    //月份
    private String month;
    //年
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMineName() {
        return mineName;
    }

    public void setMineName(String mineName) {
        this.mineName = mineName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getHiddenTotal() {
        return hiddenTotal;
    }

    public void setHiddenTotal(String hiddenTotal) {
        this.hiddenTotal = hiddenTotal;
    }

    public String getZdTotal() {
        return zdTotal;
    }

    public void setZdTotal(String zdTotal) {
        this.zdTotal = zdTotal;
    }

    public String getFxTotal() {
        return fxTotal;
    }

    public void setFxTotal(String fxTotal) {
        this.fxTotal = fxTotal;
    }
}
