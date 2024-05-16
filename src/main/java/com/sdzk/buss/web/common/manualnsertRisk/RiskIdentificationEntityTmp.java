package com.sdzk.buss.web.common.manualnsertRisk;


import com.sddb.buss.identification.entity.RiskIdentifiUserRel;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_b_risk_identification", schema = "")
@SuppressWarnings("serial")
public class RiskIdentificationEntityTmp {

    private String id;
    private String identificationType;
    private String identificationTypeTemp;
    private String address;
    private String riskType;
    private String riskTypeTemp;
    private String riskDesc;
    private String riskLevel;
    private String riskLevelTemp;
    private String manageLevel;
    private String manageLevelTemp;
    private String dutyManager;
    private Date identifiDate;
    private Date expDate;
    private String hazardFactortsNum;
    private String identifiManId;
    private String status;
    private String post;
    private String modifyMan;
    private Date modifyDate;
    private String rollBackRemark;
    private String riskManageHazardFactorId;
    private String specificType;
    private String specificName;
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

    private String hazardFactorsTemp;
    private String manageMeasureTemp;
    private String manageUserTemp;
    private String manageDepartTemp;


    //审核人
    private String reviewMan;
    //是否删除
    private String isDel;

    private String stateFlag;
    /**上报时间*/
    private Date uploadTime;

    private String riskTaskId;

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
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  创建人登录名称
     */
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
    @Column(name ="modifyMan",nullable=true,length=50)
    public String getModifyMan() {
        return modifyMan;
    }

    public void setModifyMan(String modifyMan) {
        this.modifyMan = modifyMan;
    }

    @Column(name ="modifyDate",nullable=true,length=50)
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Column(name ="rollBackRemark",nullable=true,length=50)
    public String getRollBackRemark() {
        return rollBackRemark;
    }

    public void setRollBackRemark(String rollBackRemark) {
        this.rollBackRemark = rollBackRemark;
    }

    @Column(name ="risk_manage_hazard_factor_id",nullable=true,length=50)
    public String getRiskManageHazardFactorId() {
        return riskManageHazardFactorId;
    }
    public void setRiskManageHazardFactorId(String riskManageHazardFactorId) {
        this.riskManageHazardFactorId = riskManageHazardFactorId;
    }

    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  创建日期
     */
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

    @Column(name ="identification_type",nullable=true)
    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    @Column(name ="address_id",nullable=true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Column(name ="identifi_date",nullable=true)
    public Date getIdentifiDate() {
        return identifiDate;
    }

    public void setIdentifiDate(Date identifiDate) {
        this.identifiDate = identifiDate;
    }

    @Column(name ="exp_date",nullable=true)
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @Column(name ="status",nullable=true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name ="post_id",nullable=true)
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Transient
    public String getHazardFactortsNum() {
        return hazardFactortsNum;
    }

    public void setHazardFactortsNum(String hazardFactortsNum) {
        this.hazardFactortsNum = hazardFactortsNum;
    }

    @Transient
    public String getIdentifiManId() {
        return identifiManId;
    }

    public void setIdentifiManId(String identifiManId) {
        this.identifiManId = identifiManId;
    }
    @Transient
    public String getIdentificationTypeTemp() {
        return identificationTypeTemp;
    }

    public void setIdentificationTypeTemp(String identificationTypeTemp) {
        this.identificationTypeTemp = identificationTypeTemp;
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
    @Transient
    public String getManageLevelTemp() {
        return manageLevelTemp;
    }

    public void setManageLevelTemp(String manageLevelTemp) {
        this.manageLevelTemp = manageLevelTemp;
    }
    @Column(name ="specific_type",nullable=true)
    public String getSpecificType() {
        return specificType;
    }

    public void setSpecificType(String specificType) {
        this.specificType = specificType;
    }
    @Column(name ="specific_name",nullable=true)
    public String getSpecificName() {
        return specificName;
    }

    public void setSpecificName(String specificName) {
        this.specificName = specificName;
    }
    @Transient
    public String getHazardFactorsTemp() {
        return hazardFactorsTemp;
    }

    public void setHazardFactorsTemp(String hazardFactorsTemp) {
        this.hazardFactorsTemp = hazardFactorsTemp;
    }
    @Transient
    public String getManageMeasureTemp() {
        return manageMeasureTemp;
    }

    public void setManageMeasureTemp(String manageMeasureTemp) {
        this.manageMeasureTemp = manageMeasureTemp;
    }

    @Transient
    public String getManageUserTemp() {
        return manageUserTemp;
    }

    public void setManageUserTemp(String manageUserTemp) {
        this.manageUserTemp = manageUserTemp;
    }
    @Transient
    public String getManageDepartTemp() {
        return manageDepartTemp;
    }

    public void setManageDepartTemp(String manageDepartTemp) {
        this.manageDepartTemp = manageDepartTemp;
    }

    @Column(name ="review_man",nullable=true)
    public String getReviewMan() {
        return reviewMan;
    }

    public void setReviewMan(String reviewMan) {
        this.reviewMan = reviewMan;
    }

    @Column(name ="is_del",nullable=true)
    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  矿端数据操作标识
     */
    @Column(name ="STATE_FLAG",nullable=true,length=1)
    public String getStateFlag(){
        return this.stateFlag;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  矿端数据操作标识
     */
    public void setStateFlag(String stateFlag){
        this.stateFlag = stateFlag;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  上报时间
     */
    @Column(name ="UPLOAD_TIME",nullable=true)
    public Date getUploadTime(){
        return this.uploadTime;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  上报时间
     */
    public void setUploadTime(Date uploadTime){
        this.uploadTime = uploadTime;
    }

    @Column(name = "risk_task_id")
    public String getRiskTaskId() {
        return riskTaskId;
    }

    public void setRiskTaskId(String riskTaskId) {
        this.riskTaskId = riskTaskId;
    }
}
