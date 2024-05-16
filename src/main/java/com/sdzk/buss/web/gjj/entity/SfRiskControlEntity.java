package com.sdzk.buss.web.gjj.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 上报国家局管控记录表
 * @author gzy
 * @date 2023-11-02 13:34:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sf_risk_control", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SfRiskControlEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**t_b_risk_manage_task id*/
	private String riskControlCode;
	/**操作标识  0：已上报  1：新增  2：修改  3：删除*/
	private String stateFlag;
	/**风险编码*/
	private String riskCode;
	/**风险等级*/
	private String riskLevel;
	/**管控类型*/
	private String controlType;
	/**管控时间*/
	private Date controlDate;
	/**管控单位*/
	private String controlDepart;
	/**管控人*/
	private String controlMan;
	/**落实管控措施编码  &链接*/
	private String implemented;
	/**未落实管控措施编码  &链接*/
	private String unimplemented;
	/**创建时间*/
	private Date dataTime;
	/**删除标识*/
	private String isDelete;
	/**上报时间*/
	private Date uploadTime;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=10,scale=0)
	public Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  t_b_risk_manage_task id
	 */
	@Column(name ="RISK_CONTROL_CODE",nullable=true,length=255)
	public String getRiskControlCode(){
		return this.riskControlCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  t_b_risk_manage_task id
	 */
	public void setRiskControlCode(String riskControlCode){
		this.riskControlCode = riskControlCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作标识  0：已上报  1：新增  2：修改  3：删除
	 */
	@Column(name ="STATE_FLAG",nullable=true,length=255)
	public String getStateFlag(){
		return this.stateFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作标识  0：已上报  1：新增  2：修改  3：删除
	 */
	public void setStateFlag(String stateFlag){
		this.stateFlag = stateFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险编码
	 */
	@Column(name ="RISK_CODE",nullable=true,length=255)
	public String getRiskCode(){
		return this.riskCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险编码
	 */
	public void setRiskCode(String riskCode){
		this.riskCode = riskCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险等级
	 */
	@Column(name ="RISK_LEVEL",nullable=true,length=255)
	public String getRiskLevel(){
		return this.riskLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险等级
	 */
	public void setRiskLevel(String riskLevel){
		this.riskLevel = riskLevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控类型
	 */
	@Column(name ="CONTROL_TYPE",nullable=true,length=255)
	public String getControlType(){
		return this.controlType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控类型
	 */
	public void setControlType(String controlType){
		this.controlType = controlType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  管控时间
	 */
	@Column(name ="CONTROL_DATE",nullable=true)
	public Date getControlDate(){
		return this.controlDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  管控时间
	 */
	public void setControlDate(Date controlDate){
		this.controlDate = controlDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控单位
	 */
	@Column(name ="CONTROL_DEPART",nullable=true,length=255)
	public String getControlDepart(){
		return this.controlDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控单位
	 */
	public void setControlDepart(String controlDepart){
		this.controlDepart = controlDepart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控人
	 */
	@Column(name ="CONTROL_MAN",nullable=true,length=255)
	public String getControlMan(){
		return this.controlMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控人
	 */
	public void setControlMan(String controlMan){
		this.controlMan = controlMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  落实管控措施编码  &链接
	 */
	@Column(name ="IMPLEMENTED",nullable=true,length=2000)
	public String getImplemented(){
		return this.implemented;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  落实管控措施编码  &链接
	 */
	public void setImplemented(String implemented){
		this.implemented = implemented;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  未落实管控措施编码  &链接
	 */
	@Column(name ="UNIMPLEMENTED",nullable=true,length=2000)
	public String getUnimplemented(){
		return this.unimplemented;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  未落实管控措施编码  &链接
	 */
	public void setUnimplemented(String unimplemented){
		this.unimplemented = unimplemented;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="DATA_TIME",nullable=true)
	public Date getDataTime(){
		return this.dataTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setDataTime(Date dataTime){
		this.dataTime = dataTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  删除标识
	 */
	@Column(name ="IS_DELETE",nullable=true,length=10)
	public String getIsDelete(){
		return this.isDelete;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  删除标识
	 */
	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
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
}
