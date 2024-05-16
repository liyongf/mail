package com.sdzk.buss.web.gjj.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 风险辨识任务上报国家局关联表
 * @author gzy
 * @date 2023-11-07 09:43:01
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sf_risk_task_rel", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SfRiskTaskRelEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**风险辨识id*/
	private String riskTaskId;
	/**国家局上报标识*/
	private String stateFlag;
	/**删除标识*/
	private String isDelete;
	/**上报时间*/
	private Date uploadTime;
	/**附件id*/
	private String fileId;
	/**报告附件名称*/
	private String fileName;
	/**报告日期*/
	private Date upDate;
	/**报告名称*/
	private String reportName;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=10,scale=0)
	public Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险辨识id
	 */
	@Column(name ="RISK_TASK_ID",nullable=true,length=36)
	public String getRiskTaskId(){
		return this.riskTaskId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险辨识id
	 */
	public void setRiskTaskId(String riskTaskId){
		this.riskTaskId = riskTaskId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国家局上报标识
	 */
	@Column(name ="STATE_FLAG",nullable=true,length=10)
	public String getStateFlag(){
		return this.stateFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国家局上报标识
	 */
	public void setStateFlag(String stateFlag){
		this.stateFlag = stateFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  删除标识
	 */
	@Column(name ="IS_DELETE",nullable=true,length=10)
	public String getIsDelete(){
		return this.isDelete;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  删除标识
	 */
	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  上报时间
	 */
	@Column(name ="UPLOAD_TIME",nullable=true)
	public Date getUploadTime(){
		return this.uploadTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  上报时间
	 */
	public void setUploadTime(Date uploadTime){
		this.uploadTime = uploadTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件id
	 */
	@Column(name ="file_id",nullable=true,length=36)
	public String getFileId(){
		return this.fileId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件id
	 */
	public void setFileId(String fileId){
		this.fileId = fileId;
	}
	/**
	 *方法: 取得java.util.String
	 *@return: java.util.String  报告附件名称
	 */
	@Column(name ="file_name",nullable=true,length=100)
	public String getFileName(){
		return this.fileName;
	}

	/**
	 *方法: 设置java.util.String
	 *@param: java.util.String  报告附件名称
	 */
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件id
	 */
	@Column(name ="up_date",nullable=true)
	public Date getUpDate(){
		return this.upDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件id
	 */
	public void setUpDate(Date upDate){
		this.upDate = upDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  报告附件名称
	 */
	@Column(name ="report_name",nullable=true,length=100)
	public String getReportName(){
		return this.reportName;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  报告附件名称
	 */
	public void setReportName(String reportName){
		this.reportName = reportName;
	}
}
