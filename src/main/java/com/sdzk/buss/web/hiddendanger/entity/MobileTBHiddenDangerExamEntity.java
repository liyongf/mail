package com.sdzk.buss.web.hiddendanger.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 隐患检查
 * @author onlineGenerator
 * @date 2016-04-21 09:07:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_hidden_danger_exam", schema = "")
@SuppressWarnings("serial")
public class MobileTBHiddenDangerExamEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**日期*/
	private Date examDate;
	/**班次*/
	private String shift;
	/**地点*/
	private String address;

	private String addressName;
	/**填卡人*/
	private String fillCardMan;
	/**责任单位*/
	private String dutyUnit;

	/**责任人*/
	private String dutyMan;

	/**危险源ID*/
	private String dangerId;
	/**问题描述*/
	private String problemDesc;
	/**隐患类别*/
	private String hiddenCategory;
	// 隐患等级
	private String hiddenNature;

    private String hiddenNatureOriginal;
	/**下井时间开始*/
	private Date beginWellDate;
	/**下井时间结束*/
	private Date endWellDate;
	/**处理方式*/
	private String dealType;
	/**限期日期*/
	private Date limitDate;
	/**限期班次*/
	private String limitShift;
	/**复查人*/
	private String reviewMan;
	/**检查类型*/
	private String examType;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
    /**组别*/
	@Excel(name="组别")
    private String itemId;
    /**组员*/
	@Excel(name="组员")
    private String itemUserId;
    /**工作安排*/
    private String gzap;
	// 隐患等级
    private String hiddenLevel;

    private String deductScores;
	//上级检查部门
	private String sjjcDept;
	//上级检查-检查人
	private String sjjcCheckMan;
    //备注
    private String remark;
	//专业类型
	private String proType;
	//其他检查-检查类型
	private String checkType;

    //关键字
    private String keyWords;
	//上报人
	private String reportName;
	//上报时间
	private String reportDate;
	//上报状态
	private String reportStatus;
	//隐患类型
	private String hiddenType;

	//隐患类型
	private String riskType;

	//风险
	private String  riskId;

	/**手机端上报id**/
	private String mobileId;

	//信息来源
	private String manageType;
	//管控任务
	private String riskManageHazardFactorId;


	/**管控责任单位*/
	private String manageDutyUnit;
	/**管控责任人Id*/
	private String manageDutyManId;

	/**任务*/
	private String taskAllId;

	@Column(name ="mobile_id",nullable=true)
	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}


	@Column(name="hidden_type",nullable = true,length = 50)
	public String getHiddenType(){
		return this.hiddenType;
	}

	public void setHiddenType(String hiddenType){
		this.hiddenType = hiddenType;
	}

	@Column(name ="report_name",nullable=true,length=50)
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@Column(name ="report_date",nullable=true)
	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	@Column(name ="report_status",nullable=true,length=50)
	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	/**
     *方法: 取得java.lang.String
     *@return: java.lang.String  关键字
     */
    @Column(name ="KEY_WORDS",nullable=true,length=50)
    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    //是否分局挂牌督办
    private String isLsSub;
    //是否省局挂牌督办
    private String isLsProv;
    //销号日期
    private Date cancelDate;
    //前台显示的检查人
    private String showReviewManName;

    @Column(name ="hiddenLevel",nullable=true,length=50)
    public String getHiddenLevel() {
        return hiddenLevel;
    }

    public void setHiddenLevel(String hiddenLevel) {
        this.hiddenLevel = hiddenLevel;
    }


    @Column(name ="gzap",nullable=true,length=10)
    public String getGzap() {
        return gzap;
    }

    public void setGzap(String gzap) {
        this.gzap = gzap;
    }

    @Column(name ="itemId",nullable=true,length=500)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name ="itemUserId",nullable=true,length=500)
    public String getItemUserId() {
        return itemUserId;
    }

    public void setItemUserId(String itemUserId) {
        this.itemUserId = itemUserId;
    }

    /**更新日期*/
	private Date updateDate;
	private String dutyManTemp;
    private String itemUserNameTemp;
	private MobileTBHiddenDangerHandleEntity handleEntity;
	private String isWithClass;
	@Column(name ="is_withclass",nullable=true,length=500)
	public String getIsWithClass() {
		return isWithClass;
	}

	public void setIsWithClass(String isWithClass) {
		this.isWithClass = isWithClass;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hiddenDanger")
	public MobileTBHiddenDangerHandleEntity getHandleEntity() {
		return handleEntity;
	}

	public void setHandleEntity(MobileTBHiddenDangerHandleEntity handleEntity) {
		this.handleEntity = handleEntity;
	}

	@Transient
	public String getDutyManTemp() {
		return dutyManTemp;
	}

    @Transient
    public String getItemUserNameTemp() {
        return itemUserNameTemp;
    }

    public void setItemUserNameTemp(String itemUserNameTemp) {
        this.itemUserNameTemp = itemUserNameTemp;
    }

    public void setDutyManTemp(String dutyManTemp) {
		this.dutyManTemp = dutyManTemp;
	}

	@Column(name ="DUTY_MAN",nullable=true,length=500)
	public String getDutyMan() {
		return dutyMan;
	}

	public void setDutyMan(String dutyMan) {
		this.dutyMan = dutyMan;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	 *@return: java.lang.String  日期
	 */
	@Column(name ="EXAM_DATE",nullable=true,length=36)
	public Date getExamDate(){
		return this.examDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  日期
	 */
	public void setExamDate(Date examDate){
		this.examDate = examDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  班次
	 */
	@Column(name ="SHIFT",nullable=true,length=50)
	public String getShift(){
		return this.shift;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  班次
	 */
	public void setShift(String shift){
		this.shift = shift;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地点
	 */
	@Column(name ="ADDRESS",nullable=true,length=50)
	public String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地点
	 */
	public void setAddress(String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  填卡人
	 */
	@Column(name ="FILL_CARD_MANIDS",nullable=true,length=50)
	public String getFillCardMan(){
		return this.fillCardMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  填卡人
	 */
	public void setFillCardMan(String fillCardMan){
		this.fillCardMan = fillCardMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任单位
	 */
	@Column(name ="DUTY_UNIT",nullable=true,length=50)
	public String getDutyUnit(){
		return this.dutyUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任单位
	 */
	public void setDutyUnit(String dutyUnit){
		this.dutyUnit = dutyUnit;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  危险源ID
	 */
	@Column(name ="DANGER_ID",nullable=true,length=50)
	public String getDangerId(){
		return this.dangerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危险源ID
	 */
	public void setDangerId(String dangerId){
		this.dangerId = dangerId;
	}

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  扣分
     */
    @Column(name ="DEDUCT_SCORES",nullable=true,length=50)
    public String getDeductScores(){
        return this.deductScores;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  扣分
     */
    public void setDeductScores(String deductScores){
        this.deductScores = deductScores;
    }

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  问题描述
	 */
	@Column(name ="PROBLEM_DESC",nullable=true,length=1000)
	public String getProblemDesc(){
		return this.problemDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问题描述
	 */
	public void setProblemDesc(String problemDesc){
		this.problemDesc = problemDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患类别
	 */
	@Column(name ="HIDDEN_CATEGORY",nullable=true,length=50)
	public String getHiddenCategory(){
		return this.hiddenCategory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患类别
	 */
	public void setHiddenCategory(String hiddenCategory){
		this.hiddenCategory = hiddenCategory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患性质
	 */
	@Column(name ="HIDDEN_NATURE",nullable=true,length=50)
	public String getHiddenNature(){
		return this.hiddenNature;
	}

    @Column(name ="hidden_nature_original",nullable=true,length=50)
    public String getHiddenNatureOriginal() {
        return hiddenNatureOriginal;
    }

    public void setHiddenNatureOriginal(String hiddenNatureOriginal) {
        this.hiddenNatureOriginal = hiddenNatureOriginal;
    }

    /**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患性质
	 */
	public void setHiddenNature(String hiddenNature){
		this.hiddenNature = hiddenNature;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下井时间开始
	 */
	@Column(name ="BEGIN_WELL_DATE",nullable=true)
	public Date getBeginWellDate(){
		return this.beginWellDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下井时间开始
	 */
	public void setBeginWellDate(Date beginWellDate){
		this.beginWellDate = beginWellDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下井时间结束
	 */
	@Column(name ="END_WELL_DATE",nullable=true)
	public Date getEndWellDate(){
		return this.endWellDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下井时间结束
	 */
	public void setEndWellDate(Date endWellDate){
		this.endWellDate = endWellDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理类型
	 */
	@Column(name ="DEAL_TYPE",nullable=true,length=50)
	public String getDealType(){
		return this.dealType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理类型
	 */
	public void setDealType(String dealType){
		this.dealType = dealType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  限期日期
	 */
	@Column(name ="LIMIT_DATE",nullable=true)
	public Date getLimitDate(){
		return this.limitDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  限期日期
	 */
	public void setLimitDate(Date limitDate){
		this.limitDate = limitDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  限期班次
	 */
	@Column(name ="LIMIT_SHIFT",nullable=true,length=50)
	public String getLimitShift(){
		return this.limitShift;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  限期班次
	 */
	public void setLimitShift(String limitShift){
		this.limitShift = limitShift;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复查人
	 */
	@Column(name ="REVIEW_MAN",length=50)
	public String getReviewMan(){
		return this.reviewMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复查人
	 */
	public void setReviewMan(String reviewMan){
		this.reviewMan = reviewMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查类型
	 */
	@Column(name ="EXAM_TYPE",nullable=true,length=50)
	public String getExamType(){
		return this.examType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查类型
	 */
	public void setExamType(String examType){
		this.examType = examType;
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

    /**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	@Column(name ="sjjc_dept",nullable=true,length=360)
	public String getSjjcDept() {
		return sjjcDept;
	}

	public void setSjjcDept(String sjjcDept) {
		this.sjjcDept = sjjcDept;
	}

	@Column(name ="sjjc_check_man",nullable=true,length=360)
	public String getSjjcCheckMan() {
		return sjjcCheckMan;
	}

	public void setSjjcCheckMan(String sjjcCheckMan) {
		this.sjjcCheckMan = sjjcCheckMan;
	}
    @Column(name ="remark",nullable=true,length=200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	@Column(name ="pro_type",nullable=true,length=360)
	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	@Column(name ="check_type",nullable=true,length=360)
	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

    @Column(name ="is_ls_sub",nullable=true,length=2)
    public String getIsLsSub() {
        return isLsSub;
    }

    public void setIsLsSub(String isLsSub) {
        this.isLsSub = isLsSub;
    }

    @Column(name ="is_ls_prov",nullable=true,length=2)
    public String getIsLsProv() {
        return isLsProv;
    }

    public void setIsLsProv(String isLsProv) {
        this.isLsProv = isLsProv;
    }

    @Column(name ="cancel_date",nullable=true)
    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

	@Column(name ="risk_type",nullable=true)
	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	@Column(name ="risk_id",nullable=true)
	public String getRiskId() {
		return riskId;
	}

	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}
	@Column(name ="manage_type",nullable=true)
	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}
	@Column(name="risk_manage_hazard_factor_id",nullable = true)
	public String getRiskManageHazardFactorId() {
		return riskManageHazardFactorId;
	}

	public void setRiskManageHazardFactorId(String riskManageHazardFactorId) {
		this.riskManageHazardFactorId = riskManageHazardFactorId;
	}



	@Column(name ="manage_duty_unit",nullable=true)
	public String getManageDutyUnit() {
		return manageDutyUnit;
	}

	public void setManageDutyUnit(String manageDutyUnit) {
		this.manageDutyUnit = manageDutyUnit;
	}
	@Column(name ="manage_duty_man_id",nullable=true)
	public String getManageDutyManId() {
		return manageDutyManId;
	}

	public void setManageDutyManId(String manageDutyManId) {
		this.manageDutyManId = manageDutyManId;
	}

	@Column(name ="task_all_id",nullable=true)
	public String getTaskAllId() {
		return taskAllId;
	}

	public void setTaskAllId(String taskAllId) {
		this.taskAllId = taskAllId;
	}

	private String hiddenNumber;
	@Column(name ="hidden_number",nullable=true)
	public String getHiddenNumber() {
		return hiddenNumber;
	}

	public void setHiddenNumber(String hiddenNumber) {
		this.hiddenNumber = hiddenNumber;
	}
}
