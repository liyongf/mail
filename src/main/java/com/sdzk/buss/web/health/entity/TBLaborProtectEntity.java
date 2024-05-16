package com.sdzk.buss.web.health.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 职工劳动防护专项档案
 * @author hansf
 * @date 2016-03-02 08:52:54
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_labor_protect", schema = "")
@SuppressWarnings("serial")
public class TBLaborProtectEntity implements java.io.Serializable {
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
	/**单位名称*/
	@Excel(name="车间名称")
	private String unitName;
	/**接触职业病危害因素*/
	@Excel(name="接触职业病危害因素")
	private String hazardsFactor;
	/**个人防护用品名称*/
	@Excel(name="个人防护用品名称")
	private String protectEquipment;
	/**型号*/
	@Excel(name="型号")
	private String model;
	/**数量*/
	@Excel(name="数量")
	private String amount;
	/**领取人*/
//	@Excel(name="领取人")
//	private java.lang.String receiver;
	/**领取日期*/
	@Excel(name="领取日期",format = "yyyy-MM-dd")
	private java.util.Date receiveDate;
	//领取人
	private TBEmployeeInfoEntity tBEmployeeInfoEntity;

	//用于导入
	@Excel(name="领取人档案号")
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
	 *@return: java.lang.String  单位名称
	 */
	@Column(name ="UNIT_NAME",nullable=true,length=100)
	public String getUnitName(){
		return this.unitName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位名称
	 */
	public void setUnitName(String unitName){
		this.unitName = unitName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接触职业病危害因素
	 */
	@Column(name ="HAZARDS_FACTOR",nullable=true,length=200)
	public String getHazardsFactor(){
		return this.hazardsFactor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接触职业病危害因素
	 */
	public void setHazardsFactor(String hazardsFactor){
		this.hazardsFactor = hazardsFactor;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  个人防护用品名称
	 */
	@Column(name ="PROTECT_EQUIPMENT",nullable=false,length=200)
	public String getProtectEquipment(){
		return this.protectEquipment;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  个人防护用品名称
	 */
	public void setProtectEquipment(String protectEquipment){
		this.protectEquipment = protectEquipment;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  型号
	 */
	@Column(name ="MODEL",nullable=true,length=100)
	public String getModel(){
		return this.model;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  型号
	 */
	public void setModel(String model){
		this.model = model;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量
	 */
	@Column(name ="AMOUNT",nullable=false,length=100)
	public String getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setAmount(String amount){
		this.amount = amount;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  领取日期
	 */
	@Column(name ="RECEIVE_DATE",nullable=false,length=32)
	public java.util.Date getReceiveDate(){
		return this.receiveDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  领取日期
	 */
	public void setReceiveDate(java.util.Date receiveDate){
		this.receiveDate = receiveDate;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECEIVER")
	public TBEmployeeInfoEntity gettBEmployeeInfoEntity() {
		return tBEmployeeInfoEntity;
	}

	public void settBEmployeeInfoEntity(TBEmployeeInfoEntity tBEmployeeInfoEntity) {
		this.tBEmployeeInfoEntity = tBEmployeeInfoEntity;
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
