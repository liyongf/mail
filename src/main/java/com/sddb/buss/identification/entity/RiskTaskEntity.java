package com.sddb.buss.identification.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 风险辨识任务
 * @author zhangdaihao
 * @date 2019-05-14 10:05:11
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_risk_task", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class RiskTaskEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;
	/**辨识活动类型*/
	private String taskType;
	/**辨识活动名称*/
	private String taskName;
	/**开始日期*/
	private Date startDate;
	/**结束日期*/
	private Date endDate;
	/**组织人员*/
	private String organizerMan;
	/**参与人员*/
	private String participantMan;
	/**任务状态*/
	private String status;
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

	private String participantManNames;


	private  String organizerOrCreateBy;
	private  String participant;
	private  String exportDoc;

	private  String isUpdate;

	private String speIdeType;

	@Column(name ="spe_ide_type",nullable=true)
	public String getSpeIdeType() {
		return speIdeType;
	}

	public void setSpeIdeType(String speIdeType) {
		this.speIdeType = speIdeType;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  唯一编号
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
	 *@param: java.lang.String  唯一编号
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识活动类型
	 */
	@Column(name ="TASK_TYPE",nullable=true,length=36)
	public String getTaskType(){
		return this.taskType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识活动类型
	 */
	public void setTaskType(String taskType){
		this.taskType = taskType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识活动名称
	 */
	@Column(name ="TASK_NAME",nullable=true,length=50)
	public String getTaskName(){
		return this.taskName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识活动名称
	 */
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始日期
	 */
	@Column(name ="START_DATE",nullable=true)
	public Date getStartDate(){
		return this.startDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始日期
	 */
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束日期
	 */
	@Column(name ="END_DATE",nullable=true)
	public Date getEndDate(){
		return this.endDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束日期
	 */
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组织人员
	 */
	@Column(name ="ORGANIZER_MAN",nullable=true,length=50)
	public String getOrganizerMan(){
		return this.organizerMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组织人员
	 */
	public void setOrganizerMan(String organizerMan){
		this.organizerMan = organizerMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参与人员
	 */
	@Column(name ="PARTICIPANT_MAN",nullable=true,length=1000)
	public String getParticipantMan(){
		return this.participantMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参与人员
	 */
	public void setParticipantMan(String participantMan){
		this.participantMan = participantMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务状态
	 */
	@Column(name ="STATUS",nullable=true,length=5)
	public String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务状态
	 */
	public void setStatus(String status){
		this.status = status;
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
	@Transient
	public String getParticipantManNames() {
		return participantManNames;
	}

	public void setParticipantManNames(String participantManNames) {
		this.participantManNames = participantManNames;
	}
	@Transient
	public String getOrganizerOrCreateBy() {
		return organizerOrCreateBy;
	}

	public void setOrganizerOrCreateBy(String organizerOrCreateBy) {
		this.organizerOrCreateBy = organizerOrCreateBy;
	}
	@Transient
	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}
	@Transient
	public String getExportDoc() {
		return exportDoc;
	}

	public void setExportDoc(String exportDoc) {
		this.exportDoc = exportDoc;
	}

	@Transient
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
}
