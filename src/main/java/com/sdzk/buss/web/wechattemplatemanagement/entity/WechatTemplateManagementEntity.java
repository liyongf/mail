package com.sdzk.buss.web.wechattemplatemanagement.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 微信模板管理表
 * @author onlineGenerator
 * @date 2018-08-09 10:23:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wechat_template_management", schema = "")
@SuppressWarnings("serial")
public class WechatTemplateManagementEntity implements java.io.Serializable {
	/**id*/
	private String id;
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
	/**父模块名称*/
	@Excel(name="父模块名称",width=15)
	private String parentModelName;
	/**模块名称*/
	@Excel(name="模块名称",width=15)
	private String modelName;
	/**模块内容*/
	@Excel(name="模块内容",width=15)
	private String modelContent;
	/**模块类型*/
	private String modelType;
	/**备用*/
	private String remark;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(String id){
		this.id = id;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父模块名称
	 */

	@Column(name ="PARENT_MODEL_NAME",nullable=true,length=32)
	public String getParentModelName(){
		return this.parentModelName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父模块名称
	 */
	public void setParentModelName(String parentModelName){
		this.parentModelName = parentModelName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模块名称
	 */

	@Column(name ="MODEL_NAME",nullable=true,length=50)
	public String getModelName(){
		return this.modelName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模块名称
	 */
	public void setModelName(String modelName){
		this.modelName = modelName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模块内容
	 */

	@Column(name ="MODEL_CONTENT",nullable=true)
	public String getModelContent(){
		return this.modelContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模块内容
	 */
	public void setModelContent(String modelContent){
		this.modelContent = modelContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模块类型
	 */

	@Column(name ="MODEL_TYPE",nullable=true,length=32)
	public String getModelType(){
		return this.modelType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模块类型
	 */
	public void setModelType(String modelType){
		this.modelType = modelType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用
	 */

	@Column(name ="REMARK",nullable=true,length=32)
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
}
