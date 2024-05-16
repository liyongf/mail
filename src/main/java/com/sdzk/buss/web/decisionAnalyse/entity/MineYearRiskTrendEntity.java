package com.sdzk.buss.web.decisionAnalyse.entity;

/**
 * Created by Lenovo on 17-7-15.
 * Author:张赛超
 */
public class MineYearRiskTrendEntity {
    //年份
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    //月份
    private String month;
    //风险数
    private String risk;
    //隐患数
    private String danger;
    //重大隐患数
    private String majorDanger;
    //总数
    private String total;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getMajorDanger() {
        return majorDanger;
    }

    public void setMajorDanger(String majorDanger) {
        this.majorDanger = majorDanger;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
