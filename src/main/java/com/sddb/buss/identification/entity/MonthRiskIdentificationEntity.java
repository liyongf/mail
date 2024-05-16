package com.sddb.buss.identification.entity;


import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_b_month_risk_identification", schema = "")
@SuppressWarnings("serial")
public class MonthRiskIdentificationEntity {

    private String id;
    @Excel(name="风险地点")
    private String address;
    private String addressNameTemp;
    @Excel(name="风险类型")
    private String riskType;
    private String riskTypeTemp;
    @Excel(name="风险描述")
    private String riskDesc;
    @Excel(name="风险等级")
    private String riskLevel;
    private String riskLevelTemp;
    @Excel(name="最高管控层级及责任人")
    private String manageLevel;
    @Excel(name="管控单位及责任人")
    private String dutyManager;

    @Excel(name="技术指导部门及负责人")
    private String technical;
    @Excel(name="监管部门及责任人")
    private String supervision;
    @Excel(name="单位专业")
    private String unitSpecialty;
    @Excel(name="管控措施")
    private String controlMeasures;
    @Excel(name="落实资金(万元)")
    private String fund;
    @Excel(name="预计解决时间")
    private String solveTime;
    @Excel(name="预案名称")
    private String planName;
    private String isDel;
    @Excel(name="备注")
    private String remark;
    @Excel(name="月份")
    private String month;
    @Excel(name="年份")
    private String year;
    @Excel(name="危害因素")
    private String hazardFactors;
    @Excel(name="信息来源")
    private String identificationType;
    private String identificationTypeTemp;
    private String type;
    @Excel(name="季度")
    private String quarter;
    private String quarterTemp;
    @Column(name ="technical",nullable=true)
    public String getTechnical() {
        return technical;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }
    @Column(name ="supervision",nullable=true)
    public String getSupervision() {
        return supervision;
    }

    public void setSupervision(String supervision) {
        this.supervision = supervision;
    }

    @Column(name ="address",nullable=true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name ="unit_specialty",nullable=true)
    public String getUnitSpecialty() {
        return unitSpecialty;
    }

    public void setUnitSpecialty(String unitSpecialty) {
        this.unitSpecialty = unitSpecialty;
    }
    @Column(name ="control_measures",nullable=true)
    public String getControlMeasures() {
        return controlMeasures;
    }

    public void setControlMeasures(String controlMeasures) {
        this.controlMeasures = controlMeasures;
    }
    @Column(name ="fund",nullable=true)
    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }
    @Column(name ="solve_time",nullable=true)
    public String getSolveTime() {
        return solveTime;
    }

    public void setSolveTime(String solveTime) {
        this.solveTime = solveTime;
    }
    @Column(name ="plan_name",nullable=true)
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
    @Column(name ="remark",nullable=true)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name ="month",nullable=true)
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    /**创建人名称*/
    @Excel(name="创建人名称")
    private String createName;
    /**创建人登录名称*/
    @Excel(name="创建人登录名称")
    private String createBy;
    /**创建日期*/
    @Excel(name="创建日期",format = "yyyy-MM-dd")
    private Date createDate;
    /**更新人名称*/
    @Excel(name="更新人名称")
    private String updateName;
    /**更新人登录名称*/
    @Excel(name="更新人登录名称")
    private String updateBy;
    /**更新日期*/
    @Excel(name="更新日期",format = "yyyy-MM-dd")
    private Date updateDate;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=32)
    public String getId(){
        return this.id;
    }
    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  主键
     */
    public void setId(String id){
        this.id = id;
    }


    @Column(name ="CREATE_BY",nullable=true,length=50)
    public String getCreateBy(){
        return this.createBy;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  创建人登录名称
     */
    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  创建人名称
     */
    @Column(name ="CREATE_NAME",nullable=true,length=50)
    public String getCreateName(){
        return this.createName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  创建人名称
     */
    public void setCreateName(String createName){
        this.createName = createName;
    }
    @Column(name ="CREATE_DATE",nullable=true)
    public Date getCreateDate(){
        return this.createDate;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  创建日期
     */
    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  更新人名称
     */
    @Column(name ="UPDATE_NAME",nullable=true,length=50)
    public String getUpdateName(){
        return this.updateName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  更新人名称
     */
    public void setUpdateName(String updateName){
        this.updateName = updateName;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  更新人登录名称
     */
    @Column(name ="UPDATE_BY",nullable=true,length=50)
    public String getUpdateBy(){
        return this.updateBy;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  更新人登录名称
     */
    public void setUpdateBy(String updateBy){
        this.updateBy = updateBy;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  更新日期
     */
    @Column(name ="UPDATE_DATE",nullable=true)
    public Date getUpdateDate(){
        return this.updateDate;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  更新日期
     */
    public void setUpdateDate(Date updateDate){
        this.updateDate = updateDate;
    }

    @Column(name ="risk_type",nullable=true)
    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    @Column(name ="risk_desc",nullable=true)
    public String getRiskDesc() {
        return riskDesc;
    }

    public void setRiskDesc(String riskDesc) {
        this.riskDesc = riskDesc;
    }

    @Column(name ="risk_level",nullable=true)
    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }


    @Column(name ="manage_level",nullable=true)
    public String getManageLevel() {
        return manageLevel;
    }

    public void setManageLevel(String manageLevel) {
        this.manageLevel = manageLevel;
    }

    @Column(name ="duty_manager",nullable=true)
    public String getDutyManager() {
        return dutyManager;
    }

    public void setDutyManager(String dutyManager) {
        this.dutyManager = dutyManager;
    }

    @Transient
    public String getRiskTypeTemp() {
        return riskTypeTemp;
    }

    public void setRiskTypeTemp(String riskTypeTemp) {
        this.riskTypeTemp = riskTypeTemp;
    }
    @Transient
    public String getRiskLevelTemp() {
        return riskLevelTemp;
    }

    public void setRiskLevelTemp(String riskLevelTemp) {
        this.riskLevelTemp = riskLevelTemp;
    }

    @Column(name ="is_del",nullable=true)
    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    @Transient
    public String getAddressNameTemp() {
        return addressNameTemp;
    }

    public void setAddressNameTemp(String addressNameTemp) {
        this.addressNameTemp = addressNameTemp;
    }

    @Transient
    public String getIdentificationTypeTemp() {
        return identificationTypeTemp;
    }

    public void setIdentificationTypeTemp(String identificationTypeTemp) {
        this.identificationTypeTemp = identificationTypeTemp;
    }

    @Column(name ="hazard_factors",nullable=true,length=1000)
    public String getHazardFactors() {
        return hazardFactors;
    }

    public void setHazardFactors(String hazardFactors) {
        this.hazardFactors = hazardFactors;
    }

    @Column(name ="identification_type",nullable=true)
    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    @Column(name ="type",nullable=true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name ="quarter",nullable=true)
    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }
    @Transient
    public String getQuarterTemp() {
        return quarterTemp;
    }

    public void setQuarterTemp(String quarterTemp) {
        this.quarterTemp = quarterTemp;
    }
    @Transient
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
