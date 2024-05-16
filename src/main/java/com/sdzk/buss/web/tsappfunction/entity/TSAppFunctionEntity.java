package com.sdzk.buss.web.tsappfunction.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: APP菜单管理
 * @author onlineGenerator
 * @date 2018-08-24 09:05:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_app_function", schema = "")
@SuppressWarnings("serial")
public class TSAppFunctionEntity implements java.io.Serializable {
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
	/**流程状态*/
	private String bpmStatus;
	/**菜单等级*/
	@Excel(name="菜单等级")
	private String functionlevel;
	/**菜单顺序*/
	@Excel(name="菜单顺序")
	private String functionorder;
	/**菜单编码*/
	@Excel(name="菜单编码")
	private String functioncode;
	/**父级菜单id*/
	@Excel(name="父级菜单id")
	private com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity TSAppFunctionEntity;
	/**菜单名称*/
	@Excel(name="菜单名称")
	private String functionname;

	private List<com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity> TSAppFunctionEntitys = new ArrayList<com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentfunctionid")
	public com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity getTSAppFunctionEntity() {
		return this.TSAppFunctionEntity;
	}

	public void setTSAppFunctionEntity(com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity TSAppFunctionEntitys) {
		this.TSAppFunctionEntity = TSAppFunctionEntitys;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSAppFunctionEntity")
	public List<com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity> getTSAppFunctionEntitys() {
		return TSAppFunctionEntitys;
	}
	public void setTSAppFunctionEntitys(List<com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity> TSAppFunctionEntitys) {
		this.TSAppFunctionEntitys = TSAppFunctionEntitys;
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
	 *@return: java.lang.String  流程状态
	 */
	@Column(name ="BPM_STATUS",nullable=true,length=32)
	public String getBpmStatus(){
		return this.bpmStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流程状态
	 */
	public void setBpmStatus(String bpmStatus){
		this.bpmStatus = bpmStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  菜单等级
	 */
	@Column(name ="FUNCTIONLEVEL",nullable=true,length=32)
	public String getFunctionlevel(){
		return this.functionlevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  菜单等级
	 */
	public void setFunctionlevel(String functionlevel){
		this.functionlevel = functionlevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  菜单顺序
	 */
	@Column(name ="FUNCTIONORDER",nullable=true,length=32)
	public String getFunctionorder(){
		return this.functionorder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  菜单顺序
	 */
	public void setFunctionorder(String functionorder){
		this.functionorder = functionorder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  菜单编码
	 */
	@Column(name ="FUNCTIONCODE",nullable=true,length=32)
	public String getFunctioncode(){
		return this.functioncode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  菜单编码
	 */
	public void setFunctioncode(String functioncode){
		this.functioncode = functioncode;
	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  父级菜单id
//	 */
//	@Column(name ="PARENTFUNCTIONID",nullable=true,length=36)
//	public java.lang.String getParentfunctionid(){
//		return this.parentfunctionid;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  父级菜单id
//	 */
//	public void setParentfunctionid(java.lang.String parentfunctionid){
//		this.parentfunctionid = parentfunctionid;
//	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  菜单名称
	 */
	@Column(name ="FUNCTIONNAME",nullable=true,length=32)
	public String getFunctionname(){
		return this.functionname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  菜单名称
	 */
	public void setFunctionname(String functionname){
		this.functionname = functionname;
	}
}
