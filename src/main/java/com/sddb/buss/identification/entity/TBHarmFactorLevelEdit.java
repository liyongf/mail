package com.sddb.buss.identification.entity;

public class TBHarmFactorLevelEdit {
    private String id;
    private String riskLevelTemp;
    //增加管控措施
    private String manageMeasureTemp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRiskLevelTemp() {
        return riskLevelTemp;
    }

    public void setRiskLevelTemp(String riskLevelTemp) {
        this.riskLevelTemp = riskLevelTemp;
    }

    public String getManageMeasureTemp() {
        return manageMeasureTemp;
    }

    public void setManageMeasureTemp(String manageMeasureTemp) {
        this.manageMeasureTemp = manageMeasureTemp;
    }
}
