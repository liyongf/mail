package com.sdzk.buss.web.hiddendanger.entity;

import com.sdzk.buss.web.common.Constants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: t_b_hidden_danger_handle
 * @author onlineGenerator
 * @date 2016-04-21 09:07:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_hidden_danger_handle", schema = "")
@SuppressWarnings("serial")
public class TBHiddenDangerHandleEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**隐患id*/
	@Excel(name="隐患id")
	private TBHiddenDangerExamEntity hiddenDanger;
	/**整改日期*/
	@Excel(name="整改日期",format = "yyyy-MM-dd")
	private Date modifyDate;
	/**整改班次*/
	@Excel(name="整改班次")
	private String modifyShift;
	/**整改人*/
	@Excel(name="整改人")
	private String modifyMan;
	/**复查日期*/
	@Excel(name="复查日期",format = "yyyy-MM-dd")
	private Date reviewDate;
	/**复查班次*/
	@Excel(name="复查班次")
	private String reviewShift;
	/**复查人*/
	@Excel(name="复查人")
	private String reviewMan;
	/**其他复查人*/
	private String reviewManOther;
	/**复查结果*/
	@Excel(name="复查结果")
	private String reviewResult;
	/**处理状态*/
	@Excel(name="处理状态")
	private String handlelStatus;
    private String handlelStatusTemp;
	/**扣分*/
	@Excel(name="扣分")
	private String deductScores;
	/**罚款类型*/
	@Excel(name="罚款类型")
	private String fineType;
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
	/**更新日期*/
	private Date updateDate;
	/**驳回备注**/
	private String rollBackRemark;
	/**整改措施**/
	private String rectMeasures;
	/**复查情况**/
	private String reviewReport;
	/**核查日期**/
	private String verifyDate;
	/**核查单位**/
	private String verifyUnit;
	/**核查情况**/
	private String verifyStatus;
	/**核查人**/
	private String verifyMan;
	/**验收日期**/
	private String accepDate;
	/**验收人**/
	private String acceptor;
	/**验收单位**/
	private String accepUnit;
	/**验收情况**/
	private String accepReport;
	private String tempString;
    //上报人
    private String reportName;
    //上报时间
    private Date reportDate;
    //上报状态
    private String reportStatus;

	/**上报集团时间*/
	private Date reportGroupTime;
	/**上报集团用户*/
	private TSUser reportGroupMan;
	/**上报集团状态*/
	private String reportGroupStatus;
	/**风险等级*/
	private String yeRiskGradeTemp;


	/**超期次数*/
	private String limitNum;
	@Transient
	public String getYeRiskGradeTemp() {
		return yeRiskGradeTemp;
	}

	public void setYeRiskGradeTemp(String yeRiskGradeTemp) {
		this.yeRiskGradeTemp = yeRiskGradeTemp;
	}
	@Transient
	public String getAlertColor() {
		return alertColor;
	}

	public void setAlertColor(String alertColor) {
		this.alertColor = alertColor;
	}

	/**风险颜色*/
	private String alertColor;
    @Column(name ="report_name",nullable=true,length=50)
    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    @Column(name ="report_date",nullable=true)
    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @Column(name ="report_status",nullable=true,length=50)
    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }


    @Column(name ="RECT_MEASURES",nullable=true,length=50)
	public String getRectMeasures() {
		return rectMeasures;
	}

	public void setRectMeasures(String rectMeasures) {
		this.rectMeasures = rectMeasures;
	}

	@Column(name ="REVIEW_REPORT",nullable=true,length=50)
	public String getReviewReport() {
		return reviewReport;
	}

	public void setReviewReport(String reviewReport) {
		this.reviewReport = reviewReport;
	}

	@Column(name ="VERIFY_DATE",nullable=true,length=50)
	public String getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(String verifyDate) {
		this.verifyDate = verifyDate;
	}

	@Column(name ="VERIFY_UNIT",nullable=true,length=50)
	public String getVerifyUnit() {
		return verifyUnit;
	}

	public void setVerifyUnit(String verifyUnit) {
		this.verifyUnit = verifyUnit;
	}

	@Column(name ="VERIFY_STATUS",nullable=true,length=50)
	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	@Column(name ="VERIFY_MAN",nullable=true,length=50)
	public String getVerifyMan() {
		return verifyMan;
	}

	public void setVerifyMan(String verifyMan) {
		this.verifyMan = verifyMan;
	}

	@Column(name ="ACCEP_DATE",nullable=true,length=50)
	public String getAccepDate() {
		return accepDate;
	}

	public void setAccepDate(String accepDate) {
		this.accepDate = accepDate;
	}

	@Column(name ="ACCEPTOR",nullable=true,length=50)
	public String getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(String acceptor) {
		this.acceptor = acceptor;
	}

	@Column(name ="ACCEP_UNIT",nullable=true,length=50)
	public String getAccepUnit() {
		return accepUnit;
	}

	public void setAccepUnit(String accepUnit) {
		this.accepUnit = accepUnit;
	}

	@Column(name ="ACCEP_REPORT",nullable=true,length=50)
	public String getAccepReport() {
		return accepReport;
	}

	public void setAccepReport(String accepReport) {
		this.accepReport = accepReport;
	}

	private String modifyShiftTemp;
    private String reviewShiftTemp;

    @Transient
    public String getHandlelStatusTemp() {
        return handlelStatusTemp;
    }

    public void setHandlelStatusTemp(String handlelStatusTemp) {
        this.handlelStatusTemp = handlelStatusTemp;
    }

    @Transient
    public String getReviewShiftTemp() {
        return reviewShiftTemp;
    }

    public void setReviewShiftTemp(String reviewShiftTemp) {
        this.reviewShiftTemp = reviewShiftTemp;
    }

    @Transient
    public String getModifyShiftTemp() {
        return modifyShiftTemp;
    }

    public void setModifyShiftTemp(String modifyShiftTemp) {
        this.modifyShiftTemp = modifyShiftTemp;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HIDDEN_DANGER_ID")
	public TBHiddenDangerExamEntity getHiddenDanger() {
		return hiddenDanger;
	}

	public void setHiddenDanger(TBHiddenDangerExamEntity hiddenDanger) {
		this.hiddenDanger = hiddenDanger;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  整改日期
	 */
	@Column(name ="MODIFY_DATE",nullable=true)
	public Date getModifyDate(){
		return this.modifyDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  整改日期
	 */
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改班次
	 */
	@Column(name ="MODIFY_SHIFT",nullable=true,length=50)
	public String getModifyShift(){
		return this.modifyShift;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改班次
	 */
	public void setModifyShift(String modifyShift){
		this.modifyShift = modifyShift;
	}

    @Column(name ="MODIFY_MAN",nullable=true,length=50)
	public String getModifyMan() {
		return modifyMan;
	}

	public void setModifyMan(String modifyMan) {
		this.modifyMan = modifyMan;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  复查日期
	 */
	@Column(name ="REVIEW_DATE",nullable=true)
	public Date getReviewDate(){
		return this.reviewDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  复查日期
	 */
	public void setReviewDate(Date reviewDate){
		this.reviewDate = reviewDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复查班次
	 */
	@Column(name ="REVIEW_SHIFT",nullable=true,length=50)
	public String getReviewShift(){
		return this.reviewShift;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复查班次
	 */
	public void setReviewShift(String reviewShift){
		this.reviewShift = reviewShift;
	}

    @Column(name ="REVIEW_MAN",nullable=true,length=50)
	public String getReviewMan() {
		return reviewMan;
	}

	public void setReviewMan(String reviewMan) {
		this.reviewMan = reviewMan;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复查结果
	 */
	@Column(name ="REVIEW_RESULT",nullable=true,length=50)
	public String getReviewResult(){
		return this.reviewResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复查结果
	 */
	public void setReviewResult(String reviewResult){
		this.reviewResult = reviewResult;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理状态
	 */
	@Column(name ="HANDLEL_STATUS",nullable=true,length=50)
	public String getHandlelStatus(){
		return this.handlelStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理状态
	 */
	public void setHandlelStatus(String handlelStatus){
		this.handlelStatus = handlelStatus;
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

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  驳回备注
	 */
	@Column(name ="ROLL_BACK_REMARK",nullable=true,length=5000)
	public String getRollBackRemark() {
		return this.rollBackRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  驳回备注
	 */
	public void setRollBackRemark(String rollBackRemark) {
		this.rollBackRemark = rollBackRemark;
	}

    /**
     * 临时存储list ids
     */
    private String ids;
    @Transient
    public String getIds(){return ids;}

    public  void setIds(String ids){this.ids = ids ; }

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
	public String getTempString() {
		return tempString;
	}

	public void setTempString(String tempString) {
		this.tempString = tempString;
	}


	@Column(name = "REVIEW_MAN_OTHER",nullable = true)
	public String getReviewManOther() {
		return reviewManOther;
	}

	public void setReviewManOther(String reviewManOther) {
		this.reviewManOther = reviewManOther;
	}
	@Column(name = "limit_num",nullable = true)
	public String getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(String limitNum) {
		this.limitNum = limitNum;
	}
}

