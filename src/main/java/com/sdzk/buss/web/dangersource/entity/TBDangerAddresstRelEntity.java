package com.sdzk.buss.web.dangersource.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 风险与风险点关联表
 * @author onlineGenerator
 * @date 2017-07-24 11:03:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_danger_address_rel", schema = "")
@SuppressWarnings("serial")
public class TBDangerAddresstRelEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**风险id*/
	@Excel(name="风险id")
	private String dangerId;
	/**风险点id*/
	@Excel(name="风险点id")
	private String addressId;
	/**风险等级*/
	private String riskLevel;

	private String upgradeCount;

	@Column(name ="RISK_LEVEL",nullable=true,length=20)
	public String getRiskLevel(){
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel){
		this.riskLevel = riskLevel;
	}

	@Column(name ="upgrade_count",nullable=true,length=20)
	public String getUpgradeCount() {
		return upgradeCount;
	}

	public void setUpgradeCount(String upgradeCount) {
		this.upgradeCount = upgradeCount;
	}

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
	 *@return: java.lang.String  风险id
	 */
	@Column(name ="DANGER_ID",nullable=true,length=32)
	public String getDangerId(){
		return this.dangerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险id
	 */
	public void setDangerId(String dangerId){
		this.dangerId = dangerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险点id
	 */
	@Column(name ="ADDRESS_ID",nullable=true,length=32)
	public String getAddressId(){
		return this.addressId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险点d
	 */
	public void setAddressId(String addressId){
		this.addressId = addressId;
	}
}
