package com.sdzk.buss.web.homePage.entity;

public class HiddenDangerByProfessionVO {
    /**隐患关联风险专业     存储数值*/
    private String profession;
    /**隐患数量*/
    private Double number;
    /**隐患关联风险的ID*/
    private String relRiskId;
    /**隐患关联风险的专业    实际文字*/
    private String professionName;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public String getRelRiskId() {
        return relRiskId;
    }

    public void setRelRiskId(String relRiskId) {
        this.relRiskId = relRiskId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }
}
