package com.sdzk.buss.web.rectprogressreport.entity;

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
 * @Description: 整改进度汇报
 * @author onlineGenerator
 * @date 2017-06-22 10:22:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_rect_progress_report", schema = "")
@SuppressWarnings("serial")
public class TBRectProgressReportEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**隐患基础信息表关联键*/
	@Excel(name="隐患基础信息表关联键")
	private String fkHiddenInfoId;
	/**汇报日期*/
	@Excel(name="汇报日期",format = "yyyy-MM-dd")
	private Date reportDate;
	/**汇报人*/
	@Excel(name="汇报人")
	private String reportPerson;
	/**整改进展汇报*/
	@Excel(name="整改进展汇报")
	private String reportDesc;
	/**是否汇报完毕*/
	@Excel(name="是否汇报完毕")
	private String isComplete;
	/**创建人登录名*/
	private String createBy;
	/**创建人名称*/
	private String createName;
	/**创建时间*/
	private Date createDate;
	/**更新人登陆名*/
	private String updateBy;
	/**更新人名称*/
	private String updateName;
	/**更新时间*/
	private Date updateDate;

    private String reportPersonName;
    public void setReportPersonName(String reportPersonName){
        this.reportPersonName=reportPersonName;
    }
    @Transient
    public String getReportPersonName(){return this.reportPersonName;}
	
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
	 *@return: java.lang.String  隐患基础信息表关联键
	 */
	@Column(name ="FK_HIDDEN_INFO_ID",nullable=true,length=32)
	public String getFkHiddenInfoId(){
		return this.fkHiddenInfoId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患基础信息表关联键
	 */
	public void setFkHiddenInfoId(String fkHiddenInfoId){
		this.fkHiddenInfoId = fkHiddenInfoId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  汇报日期
	 */
	@Column(name ="REPORT_DATE",nullable=false)
	public Date getReportDate(){
		return this.reportDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  汇报日期
	 */
	public void setReportDate(Date reportDate){
		this.reportDate = reportDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  汇报人
	 */
	@Column(name ="REPORT_PERSON",nullable=true,length=40)
	public String getReportPerson(){
		return this.reportPerson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  汇报人
	 */
	public void setReportPerson(String reportPerson){
		this.reportPerson = reportPerson;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改进展汇报
	 */
	@Column(name ="REPORT_DESC",nullable=true,length=2000)
	public String getReportDesc(){
		return this.reportDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改进展汇报
	 */
	public void setReportDesc(String reportDesc){
		this.reportDesc = reportDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否汇报完毕
	 */
	@Column(name ="IS_COMPLETE",nullable=true,length=2)
	public String getIsComplete(){
		return this.isComplete;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否汇报完毕
	 */
	public void setIsComplete(String isComplete){
		this.isComplete = isComplete;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名
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
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=false)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登陆名
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登陆名
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
	@Column(name ="UPDATE_DATE",nullable=false)
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
