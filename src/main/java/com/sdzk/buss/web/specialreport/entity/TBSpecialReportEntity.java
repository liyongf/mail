package com.sdzk.buss.web.specialreport.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 专项辨识报告
 * @author zhangdaihao
 * @date 2019-06-04 08:42:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_special_report", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBSpecialReportEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;
	/**专项工作基本情况*/
	private String baseCondition;
	/**单位负责人*/
	private String chargeMan;
	/**辨识组织*/
	private String identifyGroup;
	/**辨识评估方法*/
	private String identifyMethod;
	/**辨识结论*/
	private String identifyResult;
	/**任务id*/
	private String taskId;

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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专项工作基本情况
	 */
	@Column(name ="BASE_CONDITION",nullable=true,length=3000)
	public String getBaseCondition(){
		return this.baseCondition;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专项工作基本情况
	 */
	public void setBaseCondition(String baseCondition){
		this.baseCondition = baseCondition;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位负责人
	 */
	@Column(name ="CHARGE_MAN",nullable=true,length=100)
	public String getChargeMan(){
		return this.chargeMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位负责人
	 */
	public void setChargeMan(String chargeMan){
		this.chargeMan = chargeMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识组织
	 */
	@Column(name ="IDENTIFY_GROUP",nullable=true,length=3000)
	public String getIdentifyGroup(){
		return this.identifyGroup;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识组织
	 */
	public void setIdentifyGroup(String identifyGroup){
		this.identifyGroup = identifyGroup;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识评估方法
	 */
	@Column(name ="IDENTIFY_METHOD",nullable=true,length=3000)
	public String getIdentifyMethod(){
		return this.identifyMethod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识评估方法
	 */
	public void setIdentifyMethod(String identifyMethod){
		this.identifyMethod = identifyMethod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识结论
	 */
	@Column(name ="IDENTIFY_RESULT",nullable=true,length=3000)
	public String getIdentifyResult(){
		return this.identifyResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识结论
	 */
	public void setIdentifyResult(String identifyResult){
		this.identifyResult = identifyResult;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务id
	 */
	@Column(name ="TASK_ID",nullable=true,length=300)
	public String getTaskId(){
		return this.taskId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务id
	 */
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}
}
