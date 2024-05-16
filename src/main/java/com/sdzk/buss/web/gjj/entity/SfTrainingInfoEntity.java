package com.sdzk.buss.web.gjj.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 培训档案
 * @author gzy
 * @date 2023-10-31 15:59:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sf_training_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SfTrainingInfoEntity implements java.io.Serializable {
	/**培训档案编码*/
	private Integer id;
	/**矿端数据操作标识*/
	private String stateFlag;
	/**培训类型*/
	private String trainingType;
	/**培训日期*/
	private Date trainingDate;
	/**培训主题*/
	private String trainingTheme;
	/**培训人*/
	private String trainingPerson;
	/**组织部门*/
	private String trainingDepart;
	/**文件id*/
	private String fileId;
	/**培训课件、签到表、现场图片等记录材料*/
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
	 *@return: java.lang.Integer  培训档案编码
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=10,scale=0)
	public Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  培训档案编码
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
	 *@return: java.lang.String  培训类型
	 */
	@Column(name ="TRAINING_TYPE",nullable=true,length=1)
	public String getTrainingType(){
		return this.trainingType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  培训类型
	 */
	public void setTrainingType(String trainingType){
		this.trainingType = trainingType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  培训日期
	 */
	@Column(name ="TRAINING_DATE",nullable=true)
	public Date getTrainingDate(){
		return this.trainingDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  培训日期
	 */
	public void setTrainingDate(Date trainingDate){
		this.trainingDate = trainingDate;
	}
	/**
	 *方法: 取得java.lang.Object
	 *@return: java.lang.Object  培训主题
	 */
	@Column(name ="TRAINING_THEME",nullable=true,length=65535)
	public String getTrainingTheme(){
		return this.trainingTheme;
	}

	/**
	 *方法: 设置java.lang.Object
	 *@param: java.lang.Object  培训主题
	 */
	public void setTrainingTheme(String trainingTheme){
		this.trainingTheme = trainingTheme;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  培训人
	 */
	@Column(name ="TRAINING_PERSON",nullable=true,length=100)
	public String getTrainingPerson(){
		return this.trainingPerson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  培训人
	 */
	public void setTrainingPerson(String trainingPerson){
		this.trainingPerson = trainingPerson;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组织部门
	 */
	@Column(name ="TRAINING_DEPART",nullable=true,length=200)
	public String getTrainingDepart(){
		return this.trainingDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组织部门
	 */
	public void setTrainingDepart(String trainingDepart){
		this.trainingDepart = trainingDepart;
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
	 *方法: 取得java.lang.Object
	 *@return: java.lang.Object  培训课件、签到表、现场图片等记录材料
	 */
	@Column(name ="FILE_NAME",nullable=true,length=65535)
	public String getFileName(){
		return this.fileName;
	}

	/**
	 *方法: 设置java.lang.Object
	 *@param: java.lang.Object  培训课件、签到表、现场图片等记录材料
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
