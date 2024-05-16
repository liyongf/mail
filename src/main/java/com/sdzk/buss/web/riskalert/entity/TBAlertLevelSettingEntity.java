package com.sdzk.buss.web.riskalert.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 预警等级管理
 * @author onlineGenerator
 * @date 2016-04-22 14:33:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_alert_level_setting", schema = "")
@SuppressWarnings("serial")
public class TBAlertLevelSettingEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**单位*/
	@Excel(name="单位")
	private String unitType;
	/**预警指标*/
	@Excel(name="预警指标")
	private String alertIndex;
	/**开始阀值*/
	@Excel(name="开始阀值")
	private Integer beginThreshold;
	/**结束阀值*/
	@Excel(name="结束阀值")
	private Integer endThreshold;
	/**预警等级名称*/
	@Excel(name="预警等级名称")
	private String alertLevelName;
	/**预警等级颜色*/
	@Excel(name="预警等级颜色")
	private String alertLevelColor;
	/**所属煤矿*/
	private String belongMine;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;

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
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNIT_TYPE",nullable=false,length=50)
	public String getUnitType(){
		return this.unitType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitType(String unitType){
		this.unitType = unitType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预警指标
	 */
	@Column(name ="ALERT_INDEX",nullable=false,length=50)
	public String getAlertIndex(){
		return this.alertIndex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预警指标
	 */
	public void setAlertIndex(String alertIndex){
		this.alertIndex = alertIndex;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  开始阀值
	 */
	@Column(name ="BEGIN_THRESHOLD",nullable=false,length=10)
	public Integer getBeginThreshold(){
		return this.beginThreshold;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  开始阀值
	 */
	public void setBeginThreshold(Integer beginThreshold){
		this.beginThreshold = beginThreshold;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  结束阀值
	 */
	@Column(name ="END_THRESHOLD",nullable=false,length=10)
	public Integer getEndThreshold(){
		return this.endThreshold;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  结束阀值
	 */
	public void setEndThreshold(Integer endThreshold){
		this.endThreshold = endThreshold;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预警等级名称
	 */
	@Column(name ="ALERT_LEVEL_NAME",nullable=false,length=200)
	public String getAlertLevelName(){
		return this.alertLevelName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预警等级名称
	 */
	public void setAlertLevelName(String alertLevelName){
		this.alertLevelName = alertLevelName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预警等级颜色
	 */
	@Column(name ="ALERT_LEVEL_COLOR",nullable=false,length=100)
	public String getAlertLevelColor(){
		return this.alertLevelColor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预警等级颜色
	 */
	public void setAlertLevelColor(String alertLevelColor){
		this.alertLevelColor = alertLevelColor;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属煤矿
	 */
	@Column(name ="BELONG_MINE",nullable=true,length=50)
	public String getBelongMine(){
		return this.belongMine;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属煤矿
	 */
	public void setBelongMine(String belongMine){
		this.belongMine = belongMine;
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
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
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
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
}
