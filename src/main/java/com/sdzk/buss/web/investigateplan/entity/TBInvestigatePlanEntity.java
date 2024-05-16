package com.sdzk.buss.web.investigateplan.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**   
 * @Title: Entity
 * @Description: t_b_investigate_plan
 * @author onlineGenerator
 * @date 2017-08-17 11:41:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_investigate_plan", schema = "")
@SuppressWarnings("serial")
public class TBInvestigatePlanEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**计划开始时间*/
	@Excel(name="计划开始时间",format = "yyyy-MM-dd")
	private Date startTime;
	/**计划结束时间*/
	@Excel(name="计划结束时间",format = "yyyy-MM-dd")
	private Date endTime;
	/**风险点类型(1=区域;2=作业)*/
	@Excel(name="风险点类型(1=区域;2=作业)")
	private String riskPointType;
	/**要求完成时间*/
	@Excel(name="要求完成时间",format = "yyyy-MM-dd")
	private Date completeTime;
	/**受理单位*/
	@Excel(name="受理单位")
	private String acceptDepart;
	/**受理人*/
	@Excel(name="受理人")
	private String acceptUser;
	/**受理时间*/
	@Excel(name="受理时间",format = "yyyy-MM-dd")
	private Date acceptTime;
	/**排查时间*/
	@Excel(name="排查时间",format = "yyyy-MM-dd")
	private Date investigateTime;
	/**排查描述*/
	@Excel(name="排查描述")
	private String investigateDesc;
	/**状态(1=草稿;2=退回待处理;3=待受理;4=待回复;5已回复)*/
	@Excel(name="状态(1=草稿;2=退回待处理;3=待受理;4=待回复;5已回复)")
	private String status;
	/**排查类型(1=月排查;2=旬排查;3=周排查)*/
	@Excel(name="排查类型(1=月排查;2=旬排查;3=周排查)")
	private String investigateType;
	/**驳回原因*/
	@Excel(name="rollBackReason")
	private String rollBackReason;
	/**createName*/
	@Excel(name="createName")
	private String createName;
	/**createBy*/
	@Excel(name="createBy")
	private String createBy;
	/**createDate*/
	@Excel(name="createDate",format = "yyyy-MM-dd")
	private Date createDate;
	/**updateName*/
	@Excel(name="updateName")
	private String updateName;
	/**updateBy*/
	@Excel(name="updateBy")
	private String updateBy;
	/**updateDate*/
	@Excel(name="updateDate",format = "yyyy-MM-dd")
	private Date updateDate;

	/**上报集团时间*/
	private Date reportGroupTime;
	/**上报集团用户*/
	private TSUser reportGroupMan;
	/**上报集团状态*/
	private String reportGroupStatus;

	private String acceptDepartName;
	private String acceptUserRealName;
	private String riskPointTypeDesc;

	@Transient
	public String getRiskPointTypeDesc() {
		return riskPointTypeDesc;
	}

	public void setRiskPointTypeDesc(String riskPointTypeDesc) {
		this.riskPointTypeDesc = riskPointTypeDesc;
	}

	@Transient
	public String getAcceptDepartName(){
		return this.acceptDepartName;
	}

	public void setAcceptDepartName(String acceptDepartName){
		this.acceptDepartName=acceptDepartName;
	}

	@Transient
	public String getAcceptUserRealName(){
		return this.acceptUserRealName;
	}

	public void setAcceptUserRealName(String acceptUserRealName){
		this.acceptUserRealName = acceptUserRealName;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  计划开始时间
	 */
	@Column(name ="START_TIME",nullable=true)
	public Date getStartTime(){
		return this.startTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  计划开始时间
	 */
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  计划结束时间
	 */
	@Column(name ="END_TIME",nullable=true)
	public Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  计划结束时间
	 */
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险点类型(1=区域;2=作业)
	 */
	@Column(name ="RISK_POINT_TYPE",nullable=true,length=2)
	public String getRiskPointType(){
		return this.riskPointType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险点类型(1=区域;2=作业)
	 */
	public void setRiskPointType(String riskPointType){
		this.riskPointType = riskPointType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  要求完成时间
	 */
	@Column(name ="COMPLETE_TIME",nullable=true)
	public Date getCompleteTime(){
		return this.completeTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  要求完成时间
	 */
	public void setCompleteTime(Date completeTime){
		this.completeTime = completeTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  受理单位
	 */
	@Column(name ="ACCEPT_DEPART",nullable=true,length=36)
	public String getAcceptDepart(){
		return this.acceptDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  受理单位
	 */
	public void setAcceptDepart(String acceptDepart){
		this.acceptDepart = acceptDepart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  受理人
	 */
	@Column(name ="ACCEPT_USER",nullable=true,length=36)
	public String getAcceptUser(){
		return this.acceptUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  受理人
	 */
	public void setAcceptUser(String acceptUser){
		this.acceptUser = acceptUser;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  受理时间
	 */
	@Column(name ="ACCEPT_TIME",nullable=true)
	public Date getAcceptTime(){
		return this.acceptTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  受理时间
	 */
	public void setAcceptTime(Date acceptTime){
		this.acceptTime = acceptTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  排查时间
	 */
	@Column(name ="INVESTIGATE_TIME",nullable=true)
	public Date getInvestigateTime(){
		return this.investigateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  排查时间
	 */
	public void setInvestigateTime(Date investigateTime){
		this.investigateTime = investigateTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排查描述
	 */
	@Column(name ="INVESTIGATE_DESC",nullable=true,length=1000)
	public String getInvestigateDesc(){
		return this.investigateDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排查描述
	 */
	public void setInvestigateDesc(String investigateDesc){
		this.investigateDesc = investigateDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态(1=草稿;2=退回待处理;3=待受理;4=待回复;5已回复)
	 */
	@Column(name ="STATUS",nullable=true,length=2)
	public String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态(1=草稿;2=退回待处理;3=待受理;4=待回复;5已回复)
	 */
	public void setStatus(String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排查类型(1=月排查;2=旬排查;3=周排查)
	 */
	@Column(name ="INVESTIGATE_TYPE",nullable=true,length=2)
	public String getInvestigateType(){
		return this.investigateType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排查类型(1=月排查;2=旬排查;3=周排查)
	 */
	public void setInvestigateType(String investigateType){
		this.investigateType = investigateType;
	}

	@Column(name ="ROLLBACK_REASON",nullable=true,length=300)
	public String getRollBackReason(){
		return this.rollBackReason;
	}

	public void setRollBackReason(String rollBackReason){
		this.rollBackReason = rollBackReason;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createName
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createName
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createBy
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createBy
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createDate
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createDate
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  updateName
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  updateName
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  updateBy
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  updateBy
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  updateDate
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  updateDate
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	/**前台显示*/
	/**关联风险点ids**/
	private String riskPointIds;
	/**关联风险点名称**/
	private String riskPointName;
	/**关联危险源ids**/
	private String riskIds;
	/**关联危险名称**/
	private String riskName;
	private String month;

	@Transient
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Transient
	public String getRiskPointIds() {
		return riskPointIds;
	}

	public void setRiskPointIds(String riskPointIds) {
		this.riskPointIds = riskPointIds;
	}

	@Transient
	public String getRiskPointName() {
		return riskPointName;
	}

	public void setRiskPointName(String riskPointName) {
		this.riskPointName = riskPointName;
	}

	@Transient
	public String getRiskIds() {
		return riskIds;
	}

	public void setRiskIds(String riskIds) {
		this.riskIds = riskIds;
	}

	@Transient
	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
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
}
