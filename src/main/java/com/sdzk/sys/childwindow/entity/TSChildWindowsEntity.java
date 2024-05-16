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
 * @Description: 首页子窗口
 * @author zhangdaihao
 * @date 2018-03-19 10:11:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_child_windows", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TSChildWindowsEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**子窗口frameId*/
	private String childWindowFrameId;
	/**子窗口标题*/
	private String childWindowTitle;
	/**子窗口地址*/
	private String childWindowUrl;
	/**是否显示全屏按钮*/
	private String isShowFullScreenBtn;
	/**是否中间窗口*/
	private String isCenter;
	/**是否可用*/
	private String isUsed;
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
	 *@return: java.lang.String  子窗口frameId
	 */
	@Column(name ="CHILD_WINDOW_FRAME_ID",nullable=true,length=50)
	public String getChildWindowFrameId(){
		return this.childWindowFrameId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  子窗口frameId
	 */
	public void setChildWindowFrameId(String childWindowFrameId){
		this.childWindowFrameId = childWindowFrameId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  子窗口标题
	 */
	@Column(name ="CHILD_WINDOW_TITLE",nullable=true,length=100)
	public String getChildWindowTitle(){
		return this.childWindowTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  子窗口标题
	 */
	public void setChildWindowTitle(String childWindowTitle){
		this.childWindowTitle = childWindowTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  子窗口地址
	 */
	@Column(name ="CHILD_WINDOW_URL",nullable=true,length=255)
	public String getChildWindowUrl(){
		return this.childWindowUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  子窗口地址
	 */
	public void setChildWindowUrl(String childWindowUrl){
		this.childWindowUrl = childWindowUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否显示全屏按钮
	 */
	@Column(name ="IS_SHOW_FULL_SCREEN_BTN",nullable=true,length=5)
	public String getIsShowFullScreenBtn(){
		return this.isShowFullScreenBtn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否显示全屏按钮
	 */
	public void setIsShowFullScreenBtn(String isShowFullScreenBtn){
		this.isShowFullScreenBtn = isShowFullScreenBtn;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否中间窗口
	 */
	@Column(name ="IS_CENTER",nullable=true,length=5)
	public String getIsCenter(){
		return this.isCenter;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否中间窗口
	 */
	public void setIsCenter(String isCenter){
		this.isCenter = isCenter;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否可用
	 */
	@Column(name ="IS_USED",nullable=true,length=5)
	public String getIsUsed(){
		return this.isUsed;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否可用
	 */
	public void setIsUsed(String isUsed){
		this.isUsed = isUsed;
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
