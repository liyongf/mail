package com.sddb.buss.riskmanage.entity;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: t_b_danger_source
 * @author onlineGenerator
 * @date 2017-06-20 14:18:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_risk_manage_task", schema = "")
@SuppressWarnings("serial")
public class RiskManageTaskEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;

	/**管控风险id*/
	private RiskManageEntity riskManage;

	/**任务编号*/
	private String taskId;

	/**管控的风险id*/
	private RiskIdentificationEntity risk;

	/**管控类型*/
	private  String manageType;

	/**处理状态*/
	private  String handleStatus;

	/**总任务*/
	private  String taskAllId;

	/**危害因素管控列表*/
	private List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityList;

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

	//辅助字段
	private String hazardFactorNum;
	private String hazardFactorNumFinished;

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
	public void setId(String id){
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "risk_manage_id")
	public RiskManageEntity getRiskManage(){return this.riskManage;}
	public void setRiskManage(RiskManageEntity riskManage){this.riskManage = riskManage;}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "risk_id")
	public RiskIdentificationEntity getRisk(){return this.risk;}
	public void setRisk(RiskIdentificationEntity risk){this.risk = risk;}

	@Column(name="manage_type",nullable=true,length=36)
	public String getManageType(){return this.manageType;}
	public void setManageType(String manageType){this.manageType = manageType;}

	@Column(name="task_id",nullable=true,length=36)
	public String getTaskId(){return this.taskId;}
	public void setTaskId(String taskId){this.taskId = taskId;}

	@Column(name="handle_status",nullable=true,length=36)
	public String getHandleStatus(){return this.handleStatus;}
	public void setHandleStatus(String handleStatus){this.handleStatus = handleStatus;}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskManageTaskEntity")
	public List<RiskManageHazardFactorEntity> getRiskManageHazardFactorEntityList() {
		return riskManageHazardFactorEntityList;
	}

	public void setRiskManageHazardFactorEntityList(List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityList) {
		this.riskManageHazardFactorEntityList = riskManageHazardFactorEntityList;
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

	@Transient
	public String getHazardFactorNum(){
		return this.hazardFactorNum;
	}
	public void setHazardFactorNum(String hazardFactorNum){
		this.hazardFactorNum = hazardFactorNum;
	}

	@Transient
	public String getHazardFactorNumFinished(){
		return this.hazardFactorNumFinished;
	}
	public void setHazardFactorNumFinished(String hazardFactorNumFinished){
		this.hazardFactorNumFinished = hazardFactorNumFinished;
	}
	@Column(name ="task_all_id",nullable=true)
	public String getTaskAllId() {
		return taskAllId;
	}

	public void setTaskAllId(String taskAllId) {
		this.taskAllId = taskAllId;
	}
}
