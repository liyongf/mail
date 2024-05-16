package com.sdzk.buss.web.gjj.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 重大风险管控方案
 * @author gzy
 * @date 2023-10-30 17:02:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sf_plan_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SfPlanInfoEntity implements java.io.Serializable {
	/**重大风险管控方案编码*/
	private Integer id;
	/**矿端数据操作标识*/
	private String stateFlag;
	/**风险编码*/
	private String riskCode;
	/**报告日期*/
	private Date upDate;
	/**报告名称*/
	private String reportName;
	/**文件id*/
	private String fileId;
	/**报告附件名称*/
	private String fileName;
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
	/**是否删除*/
	private String isDelete;
	/**数据上报时间*/
	private Date uploadTime;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  重大风险管控方案编码
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=10,scale=0)
	public Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  重大风险管控方案编码
	 */
	public void setId(Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿端数据操作标识
	 */
	@Column(name ="STATE_FLAG",nullable=true,length=1)
	public String getStateFlag(){
		return this.stateFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿端数据操作标识
	 */
	public void setStateFlag(String stateFlag){
		this.stateFlag = stateFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险编码
	 */
	@Column(name ="RISK_CODE",nullable=true,length=50)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  报告日期
	 */
	@Column(name ="UP_DATE",nullable=true)
	public Date getUpDate(){
		return this.upDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  报告日期
	 */
	public void setUpDate(Date upDate){
		this.upDate = upDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报告名称
	 */
	@Column(name ="REPORT_NAME",nullable=true,length=100)
	public String getReportName(){
		return this.reportName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报告名称
	 */
	public void setReportName(String reportName){
		this.reportName = reportName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文件id
	 */
	@Column(name ="FILE_ID",nullable=true,length=36)
	public String getFileId(){
		return this.fileId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件id
	 */
	public void setFileId(String fileId){
		this.fileId = fileId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报告附件名称
	 */
	@Column(name ="FILE_NAME",nullable=true,length=100)
	public String getFileName(){
		return this.fileName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报告附件名称
	 */
	public void setFileName(String fileName){
		this.fileName = fileName;
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
	 *@return: java.lang.String  是否删除
	 */
	@Column(name ="IS_DELETE",nullable=true,length=4)
	public String getIsDelete(){
		return this.isDelete;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否删除
	 */
	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  数据上报时间
	 */
	@Column(name ="UPLOAD_TIME",nullable=true)
	public Date getUploadTime(){
		return this.uploadTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  数据上报时间
	 */
	public void setUploadTime(Date uploadTime){
		this.uploadTime = uploadTime;
	}
}
