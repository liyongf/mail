package com.sddb.buss.riskdata.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_b_hazard_factors", schema = "")
@SuppressWarnings("serial")
public class HazardFactorsEntity {

    private String id;
    private String riskType;
    @Excel(name="风险类型")
    private String riskTypeTemp;
    private String major;
    @Excel(name="专业")
    private String majorTemp;
    @Excel(name="危害因素")
    private String hazardFactors;
    @Excel(name="管控措施")
    private String manageMeasure;
    private String isDel;
    private String from;
    private String riskLevel;
    @Excel(name="危害因素等级")
    private String riskLevelTemp;
    private String status;
    private String statusTemp;
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
    private String modifyMan;
    private Date modifyDate;
    private String rollBackRemark;
    @Excel(name="岗位")
    private String postName;

    //审核人
    private String reviewMan;


    private String manageMeasureTemp;

    //审核人
    private String submitMan;

    //上报煤监矿端字段
    private Integer reportStatusProvince;
    private String reportNameProvince;
    private Date reportDateProvince;


    /**上报集团时间*/
    private Date reportGroupTime;
    /**上报集团用户*/
    private TSUser reportGroupMan;
    /**上报集团状态*/
    private String reportGroupStatus;
    //管控标准来源
    @Excel(name="管控标准来源")
    private String docSource;
    //章节条款
    @Excel(name="章节条款")
    private String sectionName;
    //作业活动
    @Excel(name="作业活动")
    private String activity;
    //设备
    @Excel(name="设备")
    private String equipment;

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

    @Column(name ="risk_type",nullable=true,length=50)
    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    @Column(name ="major",nullable=true,length=50)
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Column(name ="hazard_factors",nullable=true,length=200)
    public String getHazardFactors() {
        return hazardFactors;
    }

    public void setHazardFactors(String hazardFactors) {
        this.hazardFactors = hazardFactors;
    }

    @Column(name ="manage_measure",nullable=true,length=1000)
    public String getManageMeasure() {
        return manageMeasure;
    }

    public void setManageMeasure(String manageMeasure) {
        this.manageMeasure = manageMeasure;
    }

    @Column(name ="is_del",nullable=true,length=4)
    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    @Column(name ="review_man",nullable=true)
    public String getReviewMan() {
        return reviewMan;
    }

    public void setReviewMan(String reviewMan) {
        this.reviewMan = reviewMan;
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

    @Column(name ="source_from",nullable=true)
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Column(name ="riskLevel",nullable=true)
    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Column(name ="status",nullable=true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  更新日期
     */
    public void setUpdateDate(Date updateDate){
        this.updateDate = updateDate;
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

    @Transient
    public String getRiskTypeTemp() {
        return riskTypeTemp;
    }

    public void setRiskTypeTemp(String riskTypeTemp) {
        this.riskTypeTemp = riskTypeTemp;
    }

    @Transient
    public String getMajorTemp() {
        return majorTemp;
    }

    public void setMajorTemp(String majorTemp) {
        this.majorTemp = majorTemp;
    }

    @Transient
    public String getRiskLevelTemp() {
        return riskLevelTemp;
    }

    public void setRiskLevelTemp(String riskLevelTemp) {
        this.riskLevelTemp = riskLevelTemp;
    }
    @Transient
    public String getStatusTemp() {
        return status;
    }

    public void setStatusTemp(String statusTemp) {
        this.statusTemp = statusTemp;
    }
    @Column(name ="post_name",nullable=true,length=50)
    public String getPostName() {
        return postName;
    }

    public void setPostName(String post_name) {
        this.postName = post_name;
    }

    @Transient
    public String getManageMeasureTemp() {
        return manageMeasureTemp;
    }

    public void setManageMeasureTemp(String manageMeasureTemp) {
        this.manageMeasureTemp = manageMeasureTemp;
    }


    @Column(name ="submit_man",nullable=true,length=50)
    public String getSubmitMan() {
        return submitMan;
    }

    public void setSubmitMan(String submitMan) {
        this.submitMan = submitMan;
    }

    @Column(name = "report_date_province")
    public Date getReportDateProvince() {
        return reportDateProvince;
    }

    public void setReportDateProvince(Date reportDateProvince) {
        this.reportDateProvince = reportDateProvince;
    }

    @Column(name = "report_name_province")
    public String getReportNameProvince() {
        return reportNameProvince;
    }

    public void setReportNameProvince(String reportNameProvince) {
        this.reportNameProvince = reportNameProvince;
    }

    @Column(name = "report_status_province")
    public Integer getReportStatusProvince() {
        return reportStatusProvince;
    }

    public void setReportStatusProvince(Integer reportStatusProvince) {
        this.reportStatusProvince = reportStatusProvince;
    }

    @Column(name ="report_group_time",nullable=true)
    public Date getReportGroupTime() {
        return reportGroupTime;
    }

    public void setReportGroupTime(Date reportGroupTime) {
        this.reportGroupTime = reportGroupTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_group_man")
    @NotFound(action= NotFoundAction.IGNORE)
    public TSUser getReportGroupMan() {
        return reportGroupMan;
    }

    public void setReportGroupMan(TSUser reportGroupMan) {
        this.reportGroupMan = reportGroupMan;
    }

    @Column(name ="report_group_status",nullable=true,length=4)
    public String getReportGroupStatus() {
        return reportGroupStatus;
    }

    public void setReportGroupStatus(String reportGroupStatus) {
        this.reportGroupStatus = reportGroupStatus;
    }

    @Column(name ="doc_source",nullable=true,length=4)
    public String getDocSource() {
        return docSource;
    }

    public void setDocSource(String docSource) {
        this.docSource = docSource;
    }
    @Column(name ="section_name",nullable=true,length=4)
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
    @Column(name ="activity",nullable=true,length=4)
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
    @Column(name ="equipment",nullable=true,length=4)
    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
