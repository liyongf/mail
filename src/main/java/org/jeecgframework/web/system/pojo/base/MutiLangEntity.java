package org.jeecgframework.web.system.pojo.base;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 多语言
 * @author Rocky
 * @date 2014-06-28 00:09:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_muti_lang", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MutiLangEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**语言主键*/
	private String langKey;
	/**内容*/
	private String langContext;
	/**语言*/
	private String langCode;
	/**创建时间*/
	private java.util.Date createDate;
	/**创建人编号*/
	private String createBy;
	/**创建人姓名*/
	private String createName;
	/**更新日期*/
	private java.util.Date updateDate;
	/**更新人编号*/
	private String updateBy;
	/**更新人姓名*/
	private String updateName;

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
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
	 *@return: java.lang.String  语言主键
	 */
	@Column(name ="LANG_KEY",nullable=false,length=50)
	public String getLangKey(){
		return this.langKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  语言主键
	 */
	public void setLangKey(String langKey){
		this.langKey = langKey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="LANG_CONTEXT",nullable=false,length=500)
	public String getLangContext(){
		return this.langContext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容
	 */
	public void setLangContext(String langContext){
		this.langContext = langContext;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  语言
	 */
	@Column(name ="LANG_CODE",nullable=false,length=50)
	public String getLangCode(){
		return this.langCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  语言
	 */
	public void setLangCode(String langCode){
		this.langCode = langCode;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=false)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人编号
	 */
	@Column(name ="CREATE_BY",nullable=false,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人编号
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人姓名
	 */
	@Column(name ="CREATE_NAME",nullable=false,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人姓名
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=false)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人编号
	 */
	@Column(name ="UPDATE_BY",nullable=false,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人编号
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人姓名
	 */
	@Column(name ="UPDATE_NAME",nullable=false,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人姓名
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
}
