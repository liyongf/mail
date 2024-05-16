package com.sddb.buss.identification.entity;

public class MonthRiskExportDicVO {
    /**风险等级*/
    private String riskLevel;
    /**地点*/
    private String address;
    /**风险类型*/
    private String riskType;
    /**信息来源*/
    private String infoSources;
    /**季度*/
    private String quarter;

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getInfoSources() {
        return infoSources;
    }

    public void setInfoSources(String infoSources) {
        this.infoSources = infoSources;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

}
