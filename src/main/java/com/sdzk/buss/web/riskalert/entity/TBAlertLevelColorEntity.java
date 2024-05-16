package com.sdzk.buss.web.riskalert.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 预警等级颜色
 * @author zhangdaihao
 * @date 2017-05-17 10:29:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_alert_level_color", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBAlertLevelColorEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**预警等级名称*/
	private String alertLevelName;
	/**预警等级颜色*/
	private String alertLevelColor;
	/**所属煤矿*/
	private String belongMine;
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
}
