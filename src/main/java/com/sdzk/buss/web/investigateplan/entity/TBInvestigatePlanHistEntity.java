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
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: t_b_investigate_plan_hist
 * @author onlineGenerator
 * @date 2017-08-17 11:43:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_investigate_plan_hist", schema = "")
@SuppressWarnings("serial")
public class TBInvestigatePlanHistEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**排查计划主键*/
	@Excel(name="排查计划主键")
	private String planId;
	/**历史描述*/
	@Excel(name="历史描述")
	private String histDesc;
	/**排查状态*/
	@Excel(name="排查状态")
	private String planStatus;
	/**createName*/
	@Excel(name="createName")
	private String createName;
	/**createBy*/
	@Excel(name="createBy")
	private String createBy;
	/**createDate*/
	@Excel(name="createDate",format = "yyyy-MM-dd")
	private Date createDate;
	private String planStatusDesc;

	@Transient
	public String getPlanStatusDesc() {
		return planStatusDesc;
	}

	public void setPlanStatusDesc(String planStatusDesc) {
		this.planStatusDesc = planStatusDesc;
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
	 *@return: java.lang.String  排查计划主键
	 */
	@Column(name ="PLAN_ID",nullable=true,length=36)
	public String getPlanId(){
		return this.planId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排查计划主键
	 */
	public void setPlanId(String planId){
		this.planId = planId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  历史描述
	 */
	@Column(name ="HIST_DESC",nullable=true,length=1000)
	public String getHistDesc(){
		return this.histDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  历史描述
	 */
	public void setHistDesc(String histDesc){
		this.histDesc = histDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排查状态
	 */
	@Column(name ="PLAN_STATUS",nullable=true,length=2)
	public String getPlanStatus(){
		return this.planStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排查状态
	 */
	public void setPlanStatus(String planStatus){
		this.planStatus = planStatus;
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
}
