package com.sddb.buss.riskmanage.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "t_b_risk_manage_task_all_manage_check_rel", schema = "")
@SuppressWarnings("serial")
public class RiskManageTaskAllManageCheckRelEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;
	/**检查地点*/
	private String checkAddress;
	/**检查人*/
	private String checkMan;
	/**任务管理Id*/
	private String taskAllManageId;
	/**任务Id*/
	private String taskAllId;
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

	private String status;

	private String checkAddressTemp;

	private String checkManTemp;

	private String riskCount;

	private String hdCount;

	private String implCount;

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
	@Column(name ="check_address",nullable=true)
	public String getCheckAddress() {
		return checkAddress;
	}

	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	@Column(name ="check_man",nullable=true)
	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}
	@Column(name ="task_all_manage_id",nullable=true)
	public String getTaskAllManageId() {
		return taskAllManageId;
	}

	public void setTaskAllManageId(String taskAllManageId) {
		this.taskAllManageId = taskAllManageId;
	}
	@Column(name ="task_all_id",nullable=true)
	public String getTaskAllId() {
		return taskAllId;
	}

	public void setTaskAllId(String taskAllId) {
		this.taskAllId = taskAllId;
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
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Transient
	public String getCheckAddressTemp() {
		return checkAddressTemp;
	}

	public void setCheckAddressTemp(String checkAddressTemp) {
		this.checkAddressTemp = checkAddressTemp;
	}
	@Transient
	public String getCheckManTemp() {
		return checkManTemp;
	}

	public void setCheckManTemp(String checkManTemp) {
		this.checkManTemp = checkManTemp;
	}

	@Transient
	public String getRiskCount() {
		return riskCount;
	}

	public void setRiskCount(String riskCount) {
		this.riskCount = riskCount;
	}
	@Transient
	public String getHdCount() {
		return hdCount;
	}

	public void setHdCount(String hdCount) {
		this.hdCount = hdCount;
	}
	@Transient
	public String getImplCount() {
		return implCount;
	}

	public void setImplCount(String implCount) {
		this.implCount = implCount;
	}
}
