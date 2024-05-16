package com.sddb.buss.riskmanage.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 任务管理
 * @author zhangdaihao
 * @date 2019-05-20 09:56:12
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_risk_manage_task_all_manage", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class RiskManageTaskAllManageEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;
	/**管控类型*/
	private String manageType;
	/**管控时间*/
	private Date manageTime;
	/**管控名称*/
	private String manageName;
	/**主要内容*/
	private String mainContents;
	/**组织人员*/
	private String organizerMan;
	/**状态*/
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
	private String riskCount;
	private String hdCount;
	private String implCount;

	/**结束日期*/
	private Date endDate;

	
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
	 *@return: java.lang.String  管控类型
	 */
	@Column(name ="MANAGE_TYPE",nullable=true,length=36)
	public String getManageType(){
		return this.manageType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控类型
	 */
	public void setManageType(String manageType){
		this.manageType = manageType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  管控时间
	 */
	@Column(name ="MANAGE_TIME",nullable=true)
	public Date getManageTime(){
		return this.manageTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  管控时间
	 */
	public void setManageTime(Date manageTime){
		this.manageTime = manageTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控名称
	 */
	@Column(name ="MANAGE_NAME",nullable=true,length=36)
	public String getManageName(){
		return this.manageName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控名称
	 */
	public void setManageName(String manageName){
		this.manageName = manageName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主要内容
	 */
	@Column(name ="MAIN_CONTENTS",nullable=true,length=500)
	public String getMainContents(){
		return this.mainContents;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主要内容
	 */
	public void setMainContents(String mainContents){
		this.mainContents = mainContents;
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
	 *@return: java.lang.String  状态
	 */
	@Column(name ="STATUS",nullable=true,length=36)
	public String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
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
	public String getHdCount() {
		return hdCount;
	}

	public void setHdCount(String hdCount) {
		this.hdCount = hdCount;
	}
	@Transient
	public String getRiskCount() {
		return riskCount;
	}

	public void setRiskCount(String riskCount) {
		this.riskCount = riskCount;
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
	@Transient
	public String getImplCount() {
		return implCount;
	}

	public void setImplCount(String implCount) {
		this.implCount = implCount;
	}
}
