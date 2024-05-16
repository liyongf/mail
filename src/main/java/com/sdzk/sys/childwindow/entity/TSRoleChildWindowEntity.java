package com.sdzk.sys.childwindow.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 角色-子窗口关联
 * @author zhangdaihao
 * @date 2018-03-19 10:14:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_role_child_window", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TSRoleChildWindowEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**子窗口ID*/
	private String childWindowId;
	/**角色ID*/
	private String roleId;
	/**位置*/
	private String position;
	/**创建人,录入人*/
	private String createBy;
	/**创建人名称*/
	private String createName;
	/**创建日期*/
	private Date createDate;
	/**更新人登录名*/
	private String updateBy;
	/**更新人名称*/
	private String updateName;
	/**更新时间*/
	private Date updateDate;

	public TSRoleChildWindowEntity(){}

	public TSRoleChildWindowEntity(String roleId,String position,String childWindowId){
		this.roleId = roleId;
		this.position = position;
		this.childWindowId = childWindowId;
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
	 *@return: java.lang.String  子窗口ID
	 */
	@Column(name ="CHILD_WINDOW_ID",nullable=true,length=36)
	public String getChildWindowId(){
		return this.childWindowId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  子窗口ID
	 */
	public void setChildWindowId(String childWindowId){
		this.childWindowId = childWindowId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  角色ID
	 */
	@Column(name ="ROLE_ID",nullable=true,length=36)
	public String getRoleId(){
		return this.roleId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  角色ID
	 */
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  位置
	 */
	@Column(name ="POSITION",nullable=true,length=50)
	public String getPosition(){
		return this.position;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  位置
	 */
	public void setPosition(String position){
		this.position = position;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人,录入人
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人,录入人
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=100)
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
	 *@return: java.lang.String  更新人登录名
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=100)
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
	 *@return: java.util.Date  更新时间
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
}
