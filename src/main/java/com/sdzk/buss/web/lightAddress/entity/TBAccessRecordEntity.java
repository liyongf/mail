package com.sdzk.buss.web.lightAddress.entity;

import java.util.Date;
import java.lang.String;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
/**   
 * @Title: Entity
 * @Description: 访问信息
 * @author hanxudong
 * @date 2017-06-17 17:27:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_access_record", schema = "")
@SuppressWarnings("serial")
public class TBAccessRecordEntity implements java.io.Serializable {
	private String id;
	private String bussId;
	private String manId;
	private String addressId;
	private Date arrivalTime;
	private String lightId;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,length=36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(name ="buss_id",nullable=true,length=50)
	public String getBussId() {
		return bussId;
	}
	public void setBussId(String bussId) {
		this.bussId = bussId;
	}


	@Column(name ="man_id",nullable=true,length=50)
	public String getManId() {
		return manId;
	}
	public void setManId(String manId) {
		this.manId = manId;
	}

	@Column(name ="address_id",nullable=true,length=50)
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	@Column(name ="arrival_time",nullable=true)
	public Date getArrivalTime(){
		return this.arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime){
		this.arrivalTime = arrivalTime;
	}

	@Column(name ="light_id",nullable=true,length=50)
	public String getLightId() {
		return lightId;
	}
	public void setLightId(String lightId) {
		this.lightId = lightId;
	}

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


	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}
	public void setCreateName(String createName){
		this.createName = createName;
	}
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	@Column(name ="UPDATE_DATE",nullable=true)
	public Date getUpdateDate(){
		return this.updateDate;
	}
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

}
