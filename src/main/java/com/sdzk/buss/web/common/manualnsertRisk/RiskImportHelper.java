package com.sdzk.buss.web.common.manualnsertRisk;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;


/**
 * Created by lenovo on 2018/12/5.
 */
public class RiskImportHelper {
    @Excel(name = "风险点")
    private String addressName;
    @Excel(name = "风险岗位")
    private String postName;
    @Excel(name = "风险类型")
    private String riskCate;
    @Excel(name = "风险等级")
    private String riskLevel;
    @Excel(name = "风险描述")
    private String riskDescription;
    @Excel(name = "专业")
    private String profession;
    @Excel(name = "危害因素岗位")
    private String hPostName;
    @Excel(name = "危害因素")
    private String hazard;
    @Excel(name = "管控措施")
    private String controlMeasure;
    @Excel(name = "危害因素等级")
    private String hRiskLevel;
    @Excel(name = "管控单位")
    private String controlUnit;
    @Excel(name = "管控责任人")
    private String controller;
    @Excel(name = "最高管控层级")
    private String topControlLevel;
    @Excel(name = "最高管控责任人")
    private String topController;
    @Excel(name = "评估日期")
    private Date assessDate;
    @Excel(name = "解除日期")
    private Date terminateDate;
    @Excel(name = "信息来源")
    private String informationSource;
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "风险辨识任务Id")
    private String riskTaskId;

    public String gethPostName() {
        return hPostName==null?null:hPostName.trim().replace(" ","");
    }

    public void sethPostName(String hPostName) {
        this.hPostName = hPostName;
    }

    public String gethRiskLevel() {
        return hRiskLevel==null?null:hRiskLevel.trim().replace(" ","");
    }

    public void sethRiskLevel(String hRiskLevel) {
        this.hRiskLevel = hRiskLevel;
    }

    public String getAddressName() {
        return addressName==null?null:addressName.trim().replace(" ","");
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getPostName() {
        return postName==null?null:postName.trim().replace(" ","");
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getRiskCate() {
        return riskCate==null?null:riskCate.trim().replace(" ","");
    }

    public void setRiskCate(String riskCate) {
        this.riskCate = riskCate;
    }

    public String getRiskLevel() {
        return riskLevel==null?null:riskLevel.trim().replace(" ","");
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRiskDescription() {
        return riskDescription==null?null:riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
    }

    public String getProfession() {
        return profession==null?null:profession.trim().replace(" ","");
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getHazard() {
        return hazard==null?null:hazard.trim().replace(" ","");
    }

    public void setHazard(String hazard) {
        this.hazard = hazard;
    }

    public String getControlMeasure() {
        return controlMeasure==null?null:controlMeasure.trim().replace(" ","");
    }

    public void setControlMeasure(String controlMeasure) {
        this.controlMeasure = controlMeasure;
    }

    public String getControlUnit() {
        return controlUnit==null?null:controlUnit.trim().replace(" ","");
    }

    public void setControlUnit(String controlUnit) {
        this.controlUnit = controlUnit;
    }

    public String getController() {
        return controller==null?null:controller.trim().replace(" ","");
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getTopControlLevel() {
        return topControlLevel==null?null:topControlLevel.trim().replace(" ","");
    }

    public void setTopControlLevel(String topControlLevel) {
        this.topControlLevel = topControlLevel;
    }

    public String getTopController() {
        return topController==null?null:topController.trim().replace(" ","");
    }

    public void setTopController(String topController) {
        this.topController = topController;
    }

    public Date getAssessDate() {
        return assessDate;
    }

    public void setAssessDate(Date assessDate) {
        this.assessDate = assessDate;
    }

    public Date getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate(Date terminateDate) {
        this.terminateDate = terminateDate;
    }

    public String getInformationSource() {
        return informationSource==null?null:informationSource.trim().replace(" ","");
    }

    public void setInformationSource(String informationSource) {
        this.informationSource = informationSource;
    }

    public String getRemark() {
        return remark==null?null:remark.trim().replace(" ","");
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRiskTaskId() {
        return riskTaskId==null?null:riskTaskId.trim().replace(" ","");
    }

    public void setRiskTaskId(String riskTaskId) {
        this.riskTaskId = riskTaskId;
    }
}
