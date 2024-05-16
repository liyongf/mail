package com.sdzk.buss.web.health.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 职业健康疗养专项档案
 * @author onlineGenerator
 * @date 2016-03-03 16:44:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_health_recuperate", schema = "")
@SuppressWarnings("serial")
public class TBHealthRecuperateEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**职工基本信息ID*/
	@Excel(name="职工基本信息ID")
	private String employeeId;
	/**疗养时间*/
	@Excel(name="疗养时间",format = "yyyy-MM-dd")
	private java.util.Date recupDate;
	/**疗养周期年*/
	@Excel(name="疗养周期（年）")
	private String recupCycle;
	/**疗养地点*/
	@Excel(name="疗养地点")
	private String recupAddress;
	/**下次疗养时间*/
	@Excel(name="下次疗养时间",format = "yyyy-MM-dd")
	private java.util.Date nextRecupDate;
	/**今年是否疗养*/
	@Excel(name="今年是否疗养")
	private String thisYearIsRecup;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private String createBy;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private java.util.Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称")
	private String updateBy;
	/**更新日期*/
	@Excel(name="更新日期",format = "yyyy-MM-dd")
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  疗养时间
	 */
	@Column(name ="RECUP_DATE",nullable=true)
	public java.util.Date getRecupDate(){
		return this.recupDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  疗养时间
	 */
	public void setRecupDate(java.util.Date recupDate){
		this.recupDate = recupDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  疗养周期年
	 */
	@Column(name ="RECUP_CYCLE",nullable=true,length=20)
	public String getRecupCycle(){
		return this.recupCycle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  疗养周期年
	 */
	public void setRecupCycle(String recupCycle){
		this.recupCycle = recupCycle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  疗养地点
	 */
	@Column(name ="RECUP_ADDRESS",nullable=true,length=200)
	public String getRecupAddress(){
		return this.recupAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  疗养地点
	 */
	public void setRecupAddress(String recupAddress){
		this.recupAddress = recupAddress;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下次疗养时间
	 */
	@Column(name ="NEXT_RECUP_DATE",nullable=true)
	public java.util.Date getNextRecupDate(){
		return this.nextRecupDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下次疗养时间
	 */
	public void setNextRecupDate(java.util.Date nextRecupDate){
		this.nextRecupDate = nextRecupDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  今年是否疗养
	 */
	@Column(name ="THIS_YEAR_IS_RECUP",nullable=true,length=50)
	public String getThisYearIsRecup(){
		return this.thisYearIsRecup;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  今年是否疗养
	 */
	public void setThisYearIsRecup(String thisYearIsRecup){
		this.thisYearIsRecup = thisYearIsRecup;
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
