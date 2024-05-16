package com.sdzk.buss.web.homePage.entity;

public class MajorRiskEntityVO {
    /**无效字段，必须有一个field=id*/
    private String id;
    /**风险名称*/
    private String riskName;
    /**风险等级*/
    private String riskLevel;
    /**关联隐患数量*/
    private int relHiddenDanger;
    /**关联的隐患ID*/
    private String relHiddenDangerIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public int getRelHiddenDanger() {
        return relHiddenDanger;
    }

    public void setRelHiddenDanger(int relHiddenDanger) {
        this.relHiddenDanger = relHiddenDanger;
    }

    public String getRelHiddenDangerIds() {
        return relHiddenDangerIds;
    }

    public void setRelHiddenDangerIds(String relHiddenDangerIds) {
        this.relHiddenDangerIds = relHiddenDangerIds;
    }
}
