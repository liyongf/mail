package com.sddb.buss.riskmanage.entity;

import javax.persistence.*;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**   
 * @Title: Entity
 * @Description: t_b_danger_source
 * @author onlineGenerator
 * @date 2017-06-20 14:18:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_risk_manage", schema = "")
@SuppressWarnings("serial")
public class RiskManageEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;

	/**管控的风险id*/
	private RiskIdentificationEntity risk;


	/**管控类型*/
	private  String manageType;


	/**创建人登录名称*/
	private String createBy;
	/**创建人名称*/
	private String createName;
	/**创建日期*/
	private Date createDate;
	/**更新人登录名称*/
	private String updateBy;
	/**更新人名称*/
	private String updateName;
	/**更新日期*/
	private Date updateDate;

	/**我的管控清单 添加人Id*/
	private String myUserId;


	/**专业岗位班组*/
	private String majorId;

	private String hazardFactorsTemp;
	private String manageMeasureTemp;

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  唯一编号
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
	 *@param: java.lang.String  唯一编号
	 */
	public void setId(String id){
		this.id = id;
	}


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "risk_id")
	public RiskIdentificationEntity getRisk(){return this.risk;}

	public void setRisk(RiskIdentificationEntity risk){this.risk = risk;}

	@Column(name="manage_type",nullable=true,length=36)
	public String getManageType(){return this.manageType;}

	public void setManageType(String manageType){this.manageType = manageType;}

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
	@Column(name ="my_user_id",nullable=true,length=50)
	public String getMyUserId() {
		return myUserId;
	}

	public void setMyUserId(String myUserId) {
		this.myUserId = myUserId;
	}
	@Column(name ="major_id",nullable=true,length=50)
	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	@Transient
	public String getHazardFactorsTemp() {
		return hazardFactorsTemp;
	}

	public void setHazardFactorsTemp(String hazardFactorsTemp) {
		this.hazardFactorsTemp = hazardFactorsTemp;
	}
	@Transient
	public String getManageMeasureTemp() {
		return manageMeasureTemp;
	}

	public void setManageMeasureTemp(String manageMeasureTemp) {
		this.manageMeasureTemp = manageMeasureTemp;
	}

}
