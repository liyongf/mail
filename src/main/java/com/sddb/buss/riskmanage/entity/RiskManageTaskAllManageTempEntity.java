package com.sddb.buss.riskmanage.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_b_risk_manage_task_all_manage_temp", schema = "")
@SuppressWarnings("serial")
public class RiskManageTaskAllManageTempEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;
	/**检查地点*/
	private String checkAddress;
	/**检查人*/
	private String checkMan;
	/**任务管理Id*/
	private String taskAllManageId;
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
	@Column(name ="check_address",nullable=true)
	public String getCheckAddress() {
		return checkAddress;
	}

	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	@Column(name ="check_man",nullable=true)
	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}
	@Column(name ="task_all_manage_id",nullable=true)
	public String getTaskAllManageId() {
		return taskAllManageId;
	}

	public void setTaskAllManageId(String taskAllManageId) {
		this.taskAllManageId = taskAllManageId;
	}
}
