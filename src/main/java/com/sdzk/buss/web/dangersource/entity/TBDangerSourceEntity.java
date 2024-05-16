package com.sdzk.buss.web.dangersource.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**   
 * @Title: Entity
 * @Description: t_b_danger_source
 * @author onlineGenerator
 * @date 2017-06-20 14:18:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_danger_source", schema = "")
@Where(clause = " EXISTS ("+ Constants.RISK_LEVEL_HIDE_WHERE+" and t.typecode = YE_RISK_GRADE)")
@SuppressWarnings("serial")
public class TBDangerSourceEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;
	/**风险点类型*/
	private  String addressCate;


	/**关联地点名称*/

	private  String addreddRick;
	@Transient
	public String getAddreddRick() {
		return addreddRick;
	}

	public void setAddreddRick(String addreddRick) {
		this.addreddRick = addreddRick;
	}
	/**危险源描述*/
	@Excel(name="隐患描述")
	private String yeMhazardDesc;
	/**专业属性*/
	@Excel(name="专业")
	private String yeProfession;
	/**事故类型*/
	@Excel(name="事故类型")
	private String yeAccident;
	/**确定依据*/

	private String yeReference;
	/**具体位置*/

	private String yeLocation;
	/**危险距离*/

	private String yeDistance;
	/**周边情况及相互影响因素*/

	private String yeSurrounding;
	/**标准内容*/
	@Excel(name="标准内容")
	private String yeStandard;
	/**监控措施*/

	private String yeMonitor;
	/**应急措施*/

	private String yeEmergency;
	/**责任部门*/
	@Excel(name="责任部门")
	private String yeResDepart;
	/**风险描述*/
	@Excel(name="风险描述")
	private String yePossiblyHazard;
	/**可能性*/
	@Excel(name="可能性")
	private String yeProbability;
	/**损失*/
	@Excel(name="损失")
	private String yeCost;
	/**风险类型*/
	@Excel(name="风险类型")
	private String yeHazardCate;
	/**风险等级*/
	@Excel(name="风险等级")
	private String yeRiskGrade;
	/**备案号*/
	@Excel(name="备案号")
	private String yeCaseNum;
	/**辨识时间*/
	@Excel(name="辨识时间",format = "yyyy-MM-dd")
	private Date yeRecognizeTime;
	/**风险值*/
	@Excel(name="风险值")
	private String riskValue;
	/**管控措施*/
	@Excel(name="管控措施")
	private String manageMeasure;
	/**危险源来源（通用、本矿井）*/
	private String origin;
	/**部门上报人ID*/
	private TSUser departReportMan;
	/**部门上报时间*/
	private Date departReportTime;
	/**上报部门ID*/
	private TSDepart reportDepart;
	/**审核状态（待上报、审核中、审核退回、闭环）*/
	private String auditStatus;
	/**上报人ID（煤监局）*/
	private String mjReportMan;
	/**上传时间*/
	private Date yeUploadTime;
	/**上报煤监局状态（未上报、已上报）*/
	private String reportStatus;
	/**创建人登录名称*/
	private String createBy;
	/**创建人名称*/
	private String createName;
	/**创建日期*/
	private Date createDate;
	/**更新人登录名称*/
	private String updateBy;
	/**更新人名称*/
	private String updateName;
	/**更新日期*/
	private Date updateDate;
    /** 是否删除*/
    private String isDelete;

	/**是否是重大隐患**/
	private String isMajor;
    /**上报人*/
    private String report_name;
    /**上报时间*/
    private Date report_date;

    /**新增加的LEC风险统计方法*/
    /**LEC风险可能性*/
    private Double lecRiskPossibility;
    /**LEC风险损失*/
    private Double lecRiskLoss;
    /**LEC人员暴露于危险环境中的频繁程度*/
    private Double lecExposure;
    /**LEC风险值*/
    private Double lecRiskValue;

	/**管控标准来源*/
	@Excel(name="管控标准来源")
	private String docSource;
	/**章节*/
	@Excel(name="章节条款")
	private String sectionName;
	/**伤害类别*/
	private String damageType;
	/**责任岗位*/
	private TBPostManageEntity post;
	/**作业活动*/
	private TbActivityManageEntity activity;
	/**第一类危险源*/
	private TbHazardManageEntity hazard;
	/**隐患等级*/
	private String hiddenLevel;
	/**罚款金额*/
	@Excel(name="罚款金额")
	private String fineMoney;


    /**辨识年度**/
    private String yeRecognizeYear;
    @Excel(name="伤害类别")
	private String damageTypeTemp;
	@Excel(name="隐患等级")
	private String hiddenLevelTemp;
	private String orderNum;//导出序号

	/**以下属性用于导入*/
	@Excel(name="责任岗位")
	private String postTemp;
	@Excel(name="作业活动")
	private String activityTemp;
	@Excel(name="危险源名称")
	private String hazardTemp;
	@Excel(name="风险点类型")
	private String addressCatetemp;

	/**预警颜色*/
	private String alertColor;
	/**预警等级，依据隐患计算*/
	private String alertLevel;

	/**上报集团时间*/
	private Date reportGroupTime;
	/**上报集团用户*/
	private TSUser reportGroupMan;
	/**上报集团状态*/
	private String reportGroupStatus;
    // 关联隐患数量
	private String riskNum;

	@Column(name="risk_num",nullable=true,length=10)
	public String getRiskNum() {
		return riskNum;
	}

	public void setRiskNum(String riskNum) {
		this.riskNum = riskNum;
	}

	@Transient
	public String getAddressCatetemp() {
		return addressCatetemp;
	}

	public void setAddressCatetemp(String addressCatetemp) {
		this.addressCatetemp = addressCatetemp;
	}

	@Transient
	public String getAlertLevel(){
		return this.alertLevel;
	}

	public void setAlertLevel(String alertLevel){
		this.alertLevel = alertLevel;
	}

	@Transient
	public String getAlertColor(){
		return alertColor;
	}

	public void setAlertColor(String alertColor){
		this.alertColor = alertColor;
	}

	@Transient
	public String getPostTemp(){
		return postTemp;
	}

	public void setPostTemp(String postTemp){
		this.postTemp = postTemp;
	}

	@Transient
	public String getActivityTemp(){
		return activityTemp;
	}

	public void setActivityTemp(String activityTemp){
		this.activityTemp = activityTemp;
	}

	@Transient
	public String getHazardTemp(){
		return hazardTemp;
	}

	public void setHazardTemp(String hazardTemp){
		this.hazardTemp = hazardTemp;
	}




	@Transient
	public String getOrderNum(){
		return this.orderNum;
	}

	public void setOrderNum(String orderNum){
		this.orderNum = orderNum;
	}

	@Transient
	public String getDamageTypeTemp(){
		return damageTypeTemp;
	}

	public void setDamageTypeTemp(String damageTypeTemp){
		this.damageTypeTemp = damageTypeTemp;
	}

    @Transient
    public String getYeRecognizeYear() {
        return yeRecognizeYear;
    }

    public void setYeRecognizeYear(String yeRecognizeYear) {
        this.yeRecognizeYear = yeRecognizeYear;
    }

    @Column(name ="LEC_risk_possibility",nullable=true)
    public Double getLecRiskPossibility() {
        return lecRiskPossibility;
    }

    public void setLecRiskPossibility(Double lecRiskPossibility) {
        this.lecRiskPossibility = lecRiskPossibility;
    }

    @Column(name ="LEC_risk_loss",nullable=true)
    public Double getLecRiskLoss() {
        return lecRiskLoss;
    }

    public void setLecRiskLoss(Double lecRiskLoss) {
        this.lecRiskLoss = lecRiskLoss;
    }

    @Column(name ="LEC_exposure",nullable=true)
    public Double getLecExposure() {
        return lecExposure;
    }

    public void setLecExposure(Double lecExposure) {
        this.lecExposure = lecExposure;
    }

    @Column(name ="LEC_risk_value",nullable=true)
    public Double getLecRiskValue() {
        return lecRiskValue;
    }

    public void setLecRiskValue(Double lecRiskValue) {
        this.lecRiskValue = lecRiskValue;
    }

    @Excel(name="风险可能性")
    private String lecRiskPossibilityTemp;
    @Excel(name="风险损失")
    private String lecRiskLossTemp;
    @Excel(name="人员暴露于危险环境中的频繁程度")
    private String lecExposureTemp;
    @Transient
    public String getLecRiskPossibilityTemp() {
        return lecRiskPossibilityTemp;
    }
    public void setLecRiskPossibilityTemp(String lecRiskPossibilityTemp) {
        this.lecRiskPossibilityTemp = lecRiskPossibilityTemp;
    }
    @Transient
    public String getLecRiskLossTemp() {
        return lecRiskLossTemp;
    }
    public void setLecRiskLossTemp(String lecRiskLossTemp) {
        this.lecRiskLossTemp = lecRiskLossTemp;
    }
    @Transient
    public String getLecExposureTemp() {
        return lecExposureTemp;
    }
    public void setLecExposureTemp(String lecExposureTemp) {
        this.lecExposureTemp = lecExposureTemp;
    }

    @Column(name ="report_name",nullable=true,length=50)
    public String getReportName() {
        return report_name;
    }

    public void setReportName(String report_name) {
        this.report_name = report_name;
    }

    @Column(name ="report_date",nullable=true)
    public Date getReportDate() {
        return report_date;
    }

    public void setReportDate(Date report_date) {
        this.report_date = report_date;
    }

    @Column(name ="ismajor",nullable=true,length=1000)
	public String getIsMajor() {
		return isMajor;
	}

    @Column(name ="is_delete",nullable=true,length=4)
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public void setIsMajor(String isMajor) {
		this.isMajor = isMajor;
	}

	private String yeAccidentTemp;
    private String yeProfessiontemp;
    private String yeProbabilityTemp;
    private String yeCostTemp;
    private String yeRiskGradeTemp;
    private String yeHazardCateTemp;
    private String originTemp;
    private String auditStatusTemp;

    @Transient
    public String getAuditStatusTemp() {
        return auditStatusTemp;
    }

    public void setAuditStatusTemp(String auditStatusTemp) {
        this.auditStatusTemp = auditStatusTemp;
    }

    @Transient
    public String getOriginTemp() {
        return originTemp;
    }

    public void setOriginTemp(String originTemp) {
        this.originTemp = originTemp;
    }

    @Transient
    public String getYeHazardCateTemp() {
        return yeHazardCateTemp;
    }

    public void setYeHazardCateTemp(String yeHazardCateTemp) {
        this.yeHazardCateTemp = yeHazardCateTemp;
    }

    @Transient
    public String getYeRiskGradeTemp() {
        return yeRiskGradeTemp;
    }

    public void setYeRiskGradeTemp(String yeRiskGradeTemp) {
        this.yeRiskGradeTemp = yeRiskGradeTemp;
    }

    @Transient
    public String getYeCostTemp() {
        return yeCostTemp;
    }

    public void setYeCostTemp(String yeCostTemp) {
        this.yeCostTemp = yeCostTemp;
    }

    @Transient
    public String getYeProbabilityTemp() {
        return yeProbabilityTemp;
    }

    public void setYeProbabilityTemp(String yeProbabilityTemp) {
        this.yeProbabilityTemp = yeProbabilityTemp;
    }

    @Transient
    public String getYeProfessiontemp() {
        return yeProfessiontemp;
    }

    public void setYeProfessiontemp(String yeProfessiontemp) {
        this.yeProfessiontemp = yeProfessiontemp;
    }

    @Transient
    public String getYeAccidentTemp() {
        return yeAccidentTemp;
    }

    public void setYeAccidentTemp(String yeAccidentTemp) {
        this.yeAccidentTemp = yeAccidentTemp;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  唯一编号
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  唯一编号
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * 方法：设置java.lang.String
	 * @return：java.lang.String 风险点类型
	 * */
	@Column(name="address_cate",nullable=true,length=36)
	public String getAddressCate(){return this.addressCate;}

	public void setAddressCate(String addressCate){this.addressCate = addressCate;}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  危险源描述
	 */
	@Column(name ="YE_MHAZARD_DESC",nullable=true,length=1000)
	public String getYeMhazardDesc(){
		return this.yeMhazardDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危险源描述
	 */
	public void setYeMhazardDesc(String yeMhazardDesc){
		this.yeMhazardDesc = yeMhazardDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专业分类
	 */
	@Column(name ="YE_PROFESSION",nullable=true,length=40)
	public String getYeProfession(){
		return this.yeProfession;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专业分类
	 */
	public void setYeProfession(String yeProfession){
		this.yeProfession = yeProfession;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  事故类型
	 */
	@Column(name ="YE_ACCIDENT",nullable=true,length=40)
	public String getYeAccident(){
		return this.yeAccident;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  事故类型
	 */
	public void setYeAccident(String yeAccident){
		this.yeAccident = yeAccident;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  确定依据
	 */
	@Column(name ="YE_REFERENCE",nullable=true,length=40)
	public String getYeReference(){
		return this.yeReference;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  确定依据
	 */
	public void setYeReference(String yeReference){
		this.yeReference = yeReference;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  具体位置
	 */
	@Column(name ="YE_LOCATION",nullable=true,length=200)
	public String getYeLocation(){
		return this.yeLocation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  具体位置
	 */
	public void setYeLocation(String yeLocation){
		this.yeLocation = yeLocation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  危险距离
	 */
	@Column(name ="YE_DISTANCE",nullable=true,length=200)
	public String getYeDistance(){
		return this.yeDistance;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危险距离
	 */
	public void setYeDistance(String yeDistance){
		this.yeDistance = yeDistance;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  周边情况及相互影响因素
	 */
	@Column(name ="YE_SURROUNDING",nullable=true,length=1000)
	public String getYeSurrounding(){
		return this.yeSurrounding;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  周边情况及相互影响因素
	 */
	public void setYeSurrounding(String yeSurrounding){
		this.yeSurrounding = yeSurrounding;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管理标准
	 */
	@Column(name ="YE_STANDARD",nullable=true,length=1000)
	public String getYeStandard(){
		return this.yeStandard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管理标准
	 */
	public void setYeStandard(String yeStandard){
		this.yeStandard = yeStandard;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  监控措施
	 */
	@Column(name ="YE_MONITOR",nullable=true,length=1000)
	public String getYeMonitor(){
		return this.yeMonitor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  监控措施
	 */
	public void setYeMonitor(String yeMonitor){
		this.yeMonitor = yeMonitor;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应急措施
	 */
	@Column(name ="YE_EMERGENCY",nullable=true,length=1000)
	public String getYeEmergency(){
		return this.yeEmergency;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应急措施
	 */
	public void setYeEmergency(String yeEmergency){
		this.yeEmergency = yeEmergency;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任部门
	 */
	@Column(name ="YE_RES_DEPART",nullable=true,length=36)
	public String getYeResDepart(){
		return this.yeResDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任部门
	 */
	public void setYeResDepart(String yeResDepart){
		this.yeResDepart = yeResDepart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  可能造成的风险及后果描述
	 */
	@Column(name ="YE_POSSIBLY_HAZARD",nullable=true,length=1000)
	public String getYePossiblyHazard(){
		return this.yePossiblyHazard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  可能造成的风险及后果描述
	 */
	public void setYePossiblyHazard(String yePossiblyHazard){
		this.yePossiblyHazard = yePossiblyHazard;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  可能性
	 */
	@Column(name ="YE_PROBABILITY",nullable=true,length=40)
	public String getYeProbability(){
		return this.yeProbability;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  可能性
	 */
	public void setYeProbability(String yeProbability){
		this.yeProbability = yeProbability;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  损失
	 */
	@Column(name ="YE_COST",nullable=true,length=40)
	public String getYeCost(){
		return this.yeCost;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  损失
	 */
	public void setYeCost(String yeCost){
		this.yeCost = yeCost;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险类型
	 */
	@Column(name ="YE_HAZARD_CATE",nullable=true,length=40)
	public String getYeHazardCate(){
		return this.yeHazardCate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险类型
	 */
	public void setYeHazardCate(String yeHazardCate){
		this.yeHazardCate = yeHazardCate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险等级
	 */
	@Column(name ="YE_RISK_GRADE",nullable=true,length=40)
	public String getYeRiskGrade(){
		return this.yeRiskGrade;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险等级
	 */
	public void setYeRiskGrade(String yeRiskGrade){
		this.yeRiskGrade = yeRiskGrade;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备案号
	 */
	@Column(name ="YE_CASE_NUM",nullable=true,length=50)
	public String getYeCaseNum(){
		return this.yeCaseNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备案号
	 */
	public void setYeCaseNum(String yeCaseNum){
		this.yeCaseNum = yeCaseNum;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  辨识时间
	 */
	@Column(name ="YE_RECOGNIZE_TIME",nullable=true)
	public Date getYeRecognizeTime(){
		return this.yeRecognizeTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  辨识时间
	 */
	public void setYeRecognizeTime(Date yeRecognizeTime){
		this.yeRecognizeTime = yeRecognizeTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险值
	 */
	@Column(name ="RISK_VALUE",nullable=true,length=20)
	public String getRiskValue(){
		return this.riskValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险值
	 */
	public void setRiskValue(String riskValue){
		this.riskValue = riskValue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管理措施
	 */
	@Column(name ="MANAGE_MEASURE",nullable=true,length=1000)
	public String getManageMeasure(){
		return this.manageMeasure;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管理措施
	 */
	public void setManageMeasure(String manageMeasure){
		this.manageMeasure = manageMeasure;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  危险源来源（通用、本矿井）
	 */
	@Column(name ="ORIGIN",nullable=true,length=50)
	public String getOrigin(){
		return this.origin;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危险源来源（通用、本矿井）
	 */
	public void setOrigin(String origin){
		this.origin = origin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门上报人ID
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPART_REPORT_MAN")
    @NotFound(action= NotFoundAction.IGNORE)
	public TSUser getDepartReportMan(){
		return this.departReportMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门上报人ID
	 */
	public void setDepartReportMan(TSUser departReportMan){
		this.departReportMan = departReportMan;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  部门上报时间
	 */
	@Column(name ="DEPART_REPORT_TIME",nullable=true)
	public Date getDepartReportTime(){
		return this.departReportTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  部门上报时间
	 */
	public void setDepartReportTime(Date departReportTime){
		this.departReportTime = departReportTime;
	}


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORT_DEPART_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    public TSDepart getReportDepart() {
        return reportDepart;
    }

    public void setReportDepart(TSDepart reportDepart) {
        this.reportDepart = reportDepart;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核状态（待上报、审核中、审核退回、闭环）
	 */
	@Column(name ="AUDIT_STATUS",nullable=true,length=50)
	public String getAuditStatus(){
		return this.auditStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核状态（待上报、审核中、审核退回、闭环）
	 */
	public void setAuditStatus(String auditStatus){
		this.auditStatus = auditStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上报人ID（煤监局）
	 */
	@Column(name ="MJ_REPORT_MAN",nullable=true,length=36)
	public String getMjReportMan(){
		return this.mjReportMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上报人ID（煤监局）
	 */
	public void setMjReportMan(String mjReportMan){
		this.mjReportMan = mjReportMan;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  上传时间
	 */
	@Column(name ="YE_UPLOAD_TIME",nullable=true)
	public Date getYeUploadTime(){
		return this.yeUploadTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  上传时间
	 */
	public void setYeUploadTime(Date yeUploadTime){
		this.yeUploadTime = yeUploadTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上报煤监局状态（未上报、已上报）
	 */
	@Column(name ="REPORT_STATUS",nullable=true,length=50)
	public String getReportStatus(){
		return this.reportStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上报煤监局状态（未上报、已上报）
	 */
	public void setReportStatus(String reportStatus){
		this.reportStatus = reportStatus;
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

	@Column(name ="doc_source",nullable=true,length=150)
	public String getDocSource() {
		return docSource;
	}

	public void setDocSource(String docSource) {
		this.docSource = docSource;
	}

	@Column(name ="section_name",nullable=true,length=150)
	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	@Column(name ="damage_type",nullable=true,length=50)
	public String getDamageType() {
		return damageType;
	}

	public void setDamageType(String damageType) {
		this.damageType = damageType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	public TBPostManageEntity getPost() {
		return post;
	}

	public void setPost(TBPostManageEntity post) {
		this.post = post;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "activity_id")
	public TbActivityManageEntity getActivity() {
		return activity;
	}

	public void setActivity(TbActivityManageEntity activity) {
		this.activity = activity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hazard_manage_id")
	public TbHazardManageEntity getHazard() {
		return hazard;
	}

	public void setHazard(TbHazardManageEntity hazard) {
		this.hazard = hazard;
	}

	@Column(name ="hidden_level",nullable=true,length=50)
	public String getHiddenLevel() {
		return hiddenLevel;
	}

	public void setHiddenLevel(String hiddenLevel) {
		this.hiddenLevel = hiddenLevel;
	}

	@Column(name ="fine_money",nullable=true,length=50)
	public String getFineMoney() {
		return fineMoney;
	}

	public void setFineMoney(String fineMoney) {
		this.fineMoney = fineMoney;
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

	@Transient
	public String getHiddenLevelTemp() {
		return hiddenLevelTemp;
	}

	public void setHiddenLevelTemp(String hiddenLevelTemp) {
		this.hiddenLevelTemp = hiddenLevelTemp;
	}

}
