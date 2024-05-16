package org.jeecgframework.web.cgdynamgraph.entity.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 移动报表配置参数
 * @author onlineGenerator
 * @date 2015-12-01 11:22:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "jform_cgdynamgraph_param", schema = "")
@SuppressWarnings("serial")
public class CgDynamGraphConfigParamEntity implements java.io.Serializable {
	/**主键*/
	private String id;
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
	/**所属部门*/
	private String sysOrgCode;
	/**所属公司*/
	private String sysCompanyCode;
	/**参数名称*/
	@Excel(name="参数名称")
	private String paramName;
	/**参数说明*/
	@Excel(name="参数说明")
	private String paramDesc;
	/**数值*/
	@Excel(name="数值")
	private String paramValue;
	/**排序*/
	@Excel(name="排序")
	private Integer seq;
	/**数据源ID*/
	@Excel(name="动态报表ID")
	private String cgrheadId;
	
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
	@Column(name ="CREATE_DATE",nullable=true,length=20)
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
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
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
	 *@return: java.lang.String  所属部门
	 */
	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门
	 */
	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属公司
	 */
	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属公司
	 */
	public void setSysCompanyCode(String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参数名称
	 */
	@Column(name ="PARAM_NAME",nullable=false,length=32)
	public String getParamName(){
		return this.paramName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参数名称
	 */
	public void setParamName(String paramName){
		this.paramName = paramName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参数说明
	 */
	@Column(name ="PARAM_DESC",nullable=true,length=32)
	public String getParamDesc(){
		return this.paramDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参数说明
	 */
	public void setParamDesc(String paramDesc){
		this.paramDesc = paramDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数值
	 */
	@Column(name ="PARAM_VALUE",nullable=true,length=32)
	public String getParamValue(){
		return this.paramValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数值
	 */
	public void setParamValue(String paramValue){
		this.paramValue = paramValue;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SEQ",nullable=true,length=32)
	public Integer getSeq(){
		return this.seq;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSeq(Integer seq){
		this.seq = seq;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  动态报表ID
	 */
	@Column(name ="CGRHEAD_ID",nullable=true,length=36)
	public String getCgrheadId() {
		return cgrheadId;
	}

	public void setCgrheadId(String cgrheadId) {
		this.cgrheadId = cgrheadId;
	}
	
	
}
