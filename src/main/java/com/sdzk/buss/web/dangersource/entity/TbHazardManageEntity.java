package com.sdzk.buss.web.dangersource.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 第一类危险源名称管理
 * @author zhangdaihao
 * @date 2017-09-18 10:58:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_hazard_manage", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TbHazardManageEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**危险源名称*/
    @Excel(name="危险源名称")
	private String hazardName;
	/**危险源种类（装置/场所/设备/重大危险源）*/
    @Excel(name="种类")
	private String hazardType;
	/**危害类别（20种）*/
    @Excel(name="危害类别")
	private String damageType;
	/**所属专业*/
	private String professionType;
	/**事故类型*/
    @Excel(name="事故类别")
	private String accidentType;
	/**createName*/
	private String createName;
	/**createBy*/
	private String createBy;
	/**createDate*/
	private Date createDate;
	/**updateName*/
	private String updateName;
	/**updateBy*/
	private String updateBy;
	/**updateDate*/
	private Date updateDate;
	/**是否删除*/
	private String isDelete;


	@Column(name ="is_delete",nullable=true,length=2)
	public String getIsDelete(){
		return this.isDelete;
	}

	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
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
	 *@return: java.lang.String  危险源名称
	 */
	@Column(name ="HAZARD_NAME",nullable=true,length=120)
	public String getHazardName(){
		return this.hazardName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危险源名称
	 */
	public void setHazardName(String hazardName){
		this.hazardName = hazardName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  危险源种类（装置/场所/设备/重大危险源）
	 */
	@Column(name ="HAZARD_TYPE",nullable=true,length=50)
	public String getHazardType(){
		return this.hazardType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危险源种类（装置/场所/设备/重大危险源）
	 */
	public void setHazardType(String hazardType){
		this.hazardType = hazardType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  危害类别（20种）
	 */
	@Column(name ="DAMAGE_TYPE",nullable=true,length=50)
	public String getDamageType(){
		return this.damageType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危害类别（20种）
	 */
	public void setDamageType(String damageType){
		this.damageType = damageType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属专业
	 */
	@Column(name ="PROFESSION_TYPE",nullable=true,length=50)
	public String getProfessionType(){
		return this.professionType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属专业
	 */
	public void setProfessionType(String professionType){
		this.professionType = professionType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  事故类型
	 */
	@Column(name ="ACCIDENT_TYPE",nullable=true,length=50)
	public String getAccidentType(){
		return this.accidentType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  事故类型
	 */
	public void setAccidentType(String accidentType){
		this.accidentType = accidentType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createName
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createName
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createBy
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createBy
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createDate
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createDate
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  updateName
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  updateName
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  updateBy
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  updateBy
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  updateDate
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  updateDate
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

    private String tempAccidentType;
    private  String tempDamageType;

    @Transient
    public String getTempAccidentType() {
        return tempAccidentType;
    }

    public void setTempAccidentType(String tempAccidentType) {
        this.tempAccidentType = tempAccidentType;
    }

    @Transient
    public String getTempDamageType() {
        return tempDamageType;
    }

    public void setTempDamageType(String tempDamageType) {
        this.tempDamageType = tempDamageType;
    }
}
