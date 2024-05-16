package com.sdzk.buss.web.health.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 职业安全卫生教育培训专项档案
 * @author hansf
 * @date 2016-03-04 13:36:01
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_health_train", schema = "")
@SuppressWarnings("serial")
public class TBHealthTrainEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**职工基本信息ID*/
	private String employeeId;
	/**培训类型上岗前*/
	@Excel(name="培训类型1",replace={"新工人_1","转岗上岗_2","实习人员_3","外来施工_4"})
	private String prejobTrainType;
	/**培训时间上岗前*/
	@Excel(name="培训时间1",format = "yyyy/MM/dd")
	private java.util.Date prejobTrainDate;
	/**综合成绩上岗前*/
	@Excel(name="综合成绩1")
	private String prejobConResult;
	/**培训机构上岗前*/
	@Excel(name="培训机构1")
	private String prejobTrainOrg;
	/**培训时间在岗期间*/
	@Excel(name="培训时间2",format = "yyyy/MM/dd")
	private java.util.Date duringTrainDate;
	/**培训机构在岗期间*/
	@Excel(name="培训机构2")
	private String duringTrainOrg;
	/**综合成绩在岗期间*/
	@Excel(name="综合成绩2")
	private String duringConResult;
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
	//用于导入
	@Excel(name="档案号")
	private String fileNoTemp;
    @Excel(name="身份证号")
    private String cardNumberTemp;
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
	 *@return: java.lang.String  职工基本信息ID
	 */
	@Column(name ="EMPLOYEE_ID",nullable=true,length=36)
	public String getEmployeeId(){
		return this.employeeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职工基本信息ID
	 */
	public void setEmployeeId(String employeeId){
		this.employeeId = employeeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  培训类型上岗前
	 */
	@Column(name ="PREJOB_TRAIN_TYPE",nullable=true,length=50)
	public String getPrejobTrainType(){
		return this.prejobTrainType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  培训类型上岗前
	 */
	public void setPrejobTrainType(String prejobTrainType){
		this.prejobTrainType = prejobTrainType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  培训时间上岗前
	 */
	@Column(name ="PREJOB_TRAIN_DATE",nullable=true)
	public java.util.Date getPrejobTrainDate(){
		return this.prejobTrainDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  培训时间上岗前
	 */
	public void setPrejobTrainDate(java.util.Date prejobTrainDate){
		this.prejobTrainDate = prejobTrainDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  综合成绩上岗前
	 */
	@Column(name ="PREJOB_CON_RESULT",nullable=true,length=20)
	public String getPrejobConResult(){
		return this.prejobConResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  综合成绩上岗前
	 */
	public void setPrejobConResult(String prejobConResult){
		this.prejobConResult = prejobConResult;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  培训机构上岗前
	 */
	@Column(name ="PREJOB_TRAIN_ORG",nullable=true,length=100)
	public String getPrejobTrainOrg(){
		return this.prejobTrainOrg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  培训机构上岗前
	 */
	public void setPrejobTrainOrg(String prejobTrainOrg){
		this.prejobTrainOrg = prejobTrainOrg;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  培训时间在岗期间
	 */
	@Column(name ="DURING_TRAIN_DATE",nullable=true)
	public java.util.Date getDuringTrainDate(){
		return this.duringTrainDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  培训时间在岗期间
	 */
	public void setDuringTrainDate(java.util.Date duringTrainDate){
		this.duringTrainDate = duringTrainDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  培训机构在岗期间
	 */
	@Column(name ="DURING_TRAIN_ORG",nullable=true,length=200)
	public String getDuringTrainOrg(){
		return this.duringTrainOrg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  培训机构在岗期间
	 */
	public void setDuringTrainOrg(String duringTrainOrg){
		this.duringTrainOrg = duringTrainOrg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  综合成绩在岗期间
	 */
	@Column(name ="DURING_CON_RESULT",nullable=true,length=20)
	public String getDuringConResult(){
		return this.duringConResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  综合成绩在岗期间
	 */
	public void setDuringConResult(String duringConResult){
		this.duringConResult = duringConResult;
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
	@Column(name ="UPDATE_DATE",nullable=true)
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

	@Transient
	public String getFileNoTemp() {
		return fileNoTemp;
	}

	public void setFileNoTemp(String fileNoTemp) {
		this.fileNoTemp = fileNoTemp;
	}
    @Transient
    public String getCardNumberTemp() {
        return cardNumberTemp;
    }

    public void setCardNumberTemp(String cardNumberTemp) {
        this.cardNumberTemp = cardNumberTemp;
    }
}
