package com.sdzk.buss.web.yearreport.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 年度辨识报告
 * @author zhangdaihao
 * @date 2019-06-01 16:09:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_year_report", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBYearReportEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;
	/**单位负责人*/
	private String chargeMan;
	/**矿井名称*/
	private String mineName;
	/**矿井概况*/
	private String mineStatus;
	/**主要参考依据*/
	private String mainRefer;
	/**矿井主要灾害*/
	private String mainDanger;
	/**矿井主要生产系统*/
	private String mainSystem;
	/**辨识范围*/
	private String identifyRange;
	/**辨识评估方法*/
	private String identifyMethod;

	private String identifyGroup;

	private String taskId;
	@Column(name ="TASK_ID",nullable=true,length=300)
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

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
	 *@return: java.lang.String  矿井名称
	 */
	@Column(name ="MINE_NAME",nullable=true,length=255)
	public String getMineName(){
		return this.mineName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿井名称
	 */
	public void setMineName(String mineName){
		this.mineName = mineName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿井概况
	 */
	@Column(name ="MINE_STATUS",nullable=true,length=3000)
	public String getMineStatus(){
		return this.mineStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿井概况
	 */
	public void setMineStatus(String mineStatus){
		this.mineStatus = mineStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主要参考依据
	 */
	@Column(name ="MAIN_REFER",nullable=true,length=3000)
	public String getMainRefer(){
		return this.mainRefer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主要参考依据
	 */
	public void setMainRefer(String mainRefer){
		this.mainRefer = mainRefer;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿井主要灾害
	 */
	@Column(name ="MAIN_DANGER",nullable=true,length=3000)
	public String getMainDanger(){
		return this.mainDanger;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿井主要灾害
	 */
	public void setMainDanger(String mainDanger){
		this.mainDanger = mainDanger;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿井主要生产系统
	 */
	@Column(name ="MAIN_SYSTEM",nullable=true,length=3000)
	public String getMainSystem(){
		return this.mainSystem;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿井主要生产系统
	 */
	public void setMainSystem(String mainSystem){
		this.mainSystem = mainSystem;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识范围
	 */
	@Column(name ="IDENTIFY_RANGE",nullable=true,length=3000)
	public String getIdentifyRange(){
		return this.identifyRange;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识范围
	 */
	public void setIdentifyRange(String identifyRange){
		this.identifyRange = identifyRange;
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

	@Column(name ="IDENTIFY_GROUP",nullable=true,length=3000)
	public String getIdentifyGroup() {
		return identifyGroup;
	}

	public void setIdentifyGroup(String identifyGroup) {
		this.identifyGroup = identifyGroup;
	}
}
