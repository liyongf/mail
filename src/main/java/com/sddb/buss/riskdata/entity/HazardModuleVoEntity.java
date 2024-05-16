package com.sddb.buss.riskdata.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

public class HazardModuleVoEntity {

    @Excel(name="风险类型")
    private String riskTypeTemp;
    @Excel(name="危害因素")
    private String hazardFactorsTemp;
    @Excel(name="模块危害因素")
    private String moduleNameTemp;

    public String getRiskTypeTemp() {
        return riskTypeTemp;
    }

    public void setRiskTypeTemp(String riskTypeTemp) {
        this.riskTypeTemp = riskTypeTemp;
    }

    public String getHazardFactorsTemp() {
        return hazardFactorsTemp;
    }

    public void setHazardFactorsTemp(String hazardFactorsTemp) {
        this.hazardFactorsTemp = hazardFactorsTemp;
    }

    public String getModuleNameTemp() {
        return moduleNameTemp;
    }

    public void setModuleNameTemp(String moduleNameTemp) {
        this.moduleNameTemp = moduleNameTemp;
    }
}
