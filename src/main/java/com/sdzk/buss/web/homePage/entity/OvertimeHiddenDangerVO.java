package com.sdzk.buss.web.homePage.entity;

public class OvertimeHiddenDangerVO {
    private String id;
    /**发现时间*/
    private String fineDate;
    /**责任部门*/
    private String dutyUnit;
    /**责任人*/
    private String dutyMan;
    /**描述*/
    private String desc;

    public String getFineDate() {
        return fineDate;
    }

    public void setFineDate(String fineDate) {
        this.fineDate = fineDate;
    }

    public String getDutyUnit() {
        return dutyUnit;
    }

    public void setDutyUnit(String dutyUnit) {
        this.dutyUnit = dutyUnit;
    }

    public String getDutyMan() {
        return dutyMan;
    }

    public void setDutyMan(String dutyMan) {
        this.dutyMan = dutyMan;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
