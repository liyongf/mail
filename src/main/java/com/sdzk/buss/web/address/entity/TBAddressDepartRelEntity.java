package com.sdzk.buss.web.address.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 风险点关联单位责任单位
 * @author zhangdaihao
 * @date 2017-09-19 09:51:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_address_depart_rel", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBAddressDepartRelEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**风险点id*/
	private String addressId;
	/**责任部门id*/
	private String departId;
	/**责任人名字*/
	@Excel(name="责任人",needMerge = true,width = 15)
	private String dutyMan;
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
	@Excel(name="责任单位",needMerge = true,width = 15)
    private String departName;

    @Transient
    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
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
	 *@return: java.lang.String  风险点id
	 */
	@Column(name ="ADDRESS_ID",nullable=true,length=36)
	public String getAddressId(){
		return this.addressId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险点id
	 */
	public void setAddressId(String addressId){
		this.addressId = addressId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任部门id
	 */
	@Column(name ="DEPART_ID",nullable=true,length=50)
	public String getDepartId(){
		return this.departId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任部门id
	 */
	public void setDepartId(String departId){
		this.departId = departId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任人名字
	 */
	@Column(name ="DUTY_MAN",nullable=true,length=50)
	public String getDutyMan(){
		return this.dutyMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任人名字
	 */
	public void setDutyMan(String dutyMan){
		this.dutyMan = dutyMan;
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
}
