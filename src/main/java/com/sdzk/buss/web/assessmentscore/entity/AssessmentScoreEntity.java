package com.sdzk.buss.web.assessmentscore.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_b_assessment_score", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AssessmentScoreEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;
	/**风险*/
	private String risk;
	/**任务*/
	private String task;
	/**管控单位重大*/
	private String hdManageDuty1;
	/**管控人重大*/
	private String hdManageMan1;
	/**管控单位较大*/
	private String hdManageDuty2;
	/**管控人较大*/
	private String hdManageMan2;
	/**管控单位一般*/
	private String hdManageDuty3;
	/**管控人一般*/
	private String hdManageMan3;
	/**管控单位低*/
	private String hdManageDuty4;
	/**管控人低*/
	private String hdManageMan4;
	/**责任单位重大*/
	private String hdDutyUnit1;
	/**责任人重大*/
	private String hdDutyMan1;
	/**责任单位较大*/
	private String hdDutyUnit2;
	/**责任人较大*/
	private String hdDutyMan2;
	/**责任单位一般*/
	private String hdDutyUnit3;
	/**责任人一般*/
	private String hdDutyMan3;
	/**责任单位低*/
	private String hdDutyUnit4;
	/**责任人低*/
	private String hdDutyMan4;
	/**辨识管理人员*/
	private String riskMan;
	/**分值*/
	private String riskManScore;
	/**任务管理人员*/
	private String taskMan;
	/**分值*/
	private String taskManScore;
	/**任务管理人员*/
	private String taskMan2;
	/**分值*/
	private String taskManScore2;

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
	 *@return: java.lang.String  风险
	 */
	@Column(name ="risk",nullable=true,length=3)
	public String getRisk(){
		return this.risk;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务
	 */
	public void setRisk(String risk){
		this.risk = risk;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务
	 */
	@Column(name ="TASK",nullable=true,length=3)
	public String getTask(){
		return this.task;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务
	 */
	public void setTask(String task){
		this.task = task;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控单位重大
	 */
	@Column(name ="HD_MANAGE_DUTY1",nullable=true,length=3)
	public String getHdManageDuty1(){
		return this.hdManageDuty1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控单位重大
	 */
	public void setHdManageDuty1(String hdManageDuty1){
		this.hdManageDuty1 = hdManageDuty1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控人重大
	 */
	@Column(name ="HD_MANAGE_MAN1",nullable=true,length=3)
	public String getHdManageMan1(){
		return this.hdManageMan1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控人重大
	 */
	public void setHdManageMan1(String hdManageMan1){
		this.hdManageMan1 = hdManageMan1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控单位较大
	 */
	@Column(name ="HD_MANAGE_DUTY2",nullable=true,length=3)
	public String getHdManageDuty2(){
		return this.hdManageDuty2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控单位较大
	 */
	public void setHdManageDuty2(String hdManageDuty2){
		this.hdManageDuty2 = hdManageDuty2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控人较大
	 */
	@Column(name ="HD_MANAGE_MAN2",nullable=true,length=3)
	public String getHdManageMan2(){
		return this.hdManageMan2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控人较大
	 */
	public void setHdManageMan2(String hdManageMan2){
		this.hdManageMan2 = hdManageMan2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控单位一般
	 */
	@Column(name ="HD_MANAGE_DUTY3",nullable=true,length=3)
	public String getHdManageDuty3(){
		return this.hdManageDuty3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控单位一般
	 */
	public void setHdManageDuty3(String hdManageDuty3){
		this.hdManageDuty3 = hdManageDuty3;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控人一般
	 */
	@Column(name ="HD_MANAGE_MAN3",nullable=true,length=3)
	public String getHdManageMan3(){
		return this.hdManageMan3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控人一般
	 */
	public void setHdManageMan3(String hdManageMan3){
		this.hdManageMan3 = hdManageMan3;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控单位低
	 */
	@Column(name ="HD_MANAGE_DUTY4",nullable=true,length=3)
	public String getHdManageDuty4(){
		return this.hdManageDuty4;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控单位低
	 */
	public void setHdManageDuty4(String hdManageDuty4){
		this.hdManageDuty4 = hdManageDuty4;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控人低
	 */
	@Column(name ="HD_MANAGE_MAN4",nullable=true,length=3)
	public String getHdManageMan4(){
		return this.hdManageMan4;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控人低
	 */
	public void setHdManageMan4(String hdManageMan4){
		this.hdManageMan4 = hdManageMan4;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任单位重大
	 */
	@Column(name ="HD_DUTY_UNIT1",nullable=true,length=3)
	public String getHdDutyUnit1(){
		return this.hdDutyUnit1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任单位重大
	 */
	public void setHdDutyUnit1(String hdDutyUnit1){
		this.hdDutyUnit1 = hdDutyUnit1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任人重大
	 */
	@Column(name ="HD_DUTY_MAN1",nullable=true,length=3)
	public String getHdDutyMan1(){
		return this.hdDutyMan1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任人重大
	 */
	public void setHdDutyMan1(String hdDutyMan1){
		this.hdDutyMan1 = hdDutyMan1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任单位较大
	 */
	@Column(name ="HD_DUTY_UNIT2",nullable=true,length=3)
	public String getHdDutyUnit2(){
		return this.hdDutyUnit2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任单位较大
	 */
	public void setHdDutyUnit2(String hdDutyUnit2){
		this.hdDutyUnit2 = hdDutyUnit2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任人较大
	 */
	@Column(name ="HD_DUTY_MAN2",nullable=true,length=3)
	public String getHdDutyMan2(){
		return this.hdDutyMan2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任人较大
	 */
	public void setHdDutyMan2(String hdDutyMan2){
		this.hdDutyMan2 = hdDutyMan2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任单位一般
	 */
	@Column(name ="HD_DUTY_UNIT3",nullable=true,length=3)
	public String getHdDutyUnit3(){
		return this.hdDutyUnit3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任单位一般
	 */
	public void setHdDutyUnit3(String hdDutyUnit3){
		this.hdDutyUnit3 = hdDutyUnit3;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任人一般
	 */
	@Column(name ="HD_DUTY_MAN3",nullable=true,length=3)
	public String getHdDutyMan3(){
		return this.hdDutyMan3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任人一般
	 */
	public void setHdDutyMan3(String hdDutyMan3){
		this.hdDutyMan3 = hdDutyMan3;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任单位低
	 */
	@Column(name ="HD_DUTY_UNIT4",nullable=true,length=3)
	public String getHdDutyUnit4(){
		return this.hdDutyUnit4;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任单位低
	 */
	public void setHdDutyUnit4(String hdDutyUnit4){
		this.hdDutyUnit4 = hdDutyUnit4;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任人低
	 */
	@Column(name ="HD_DUTY_MAN4",nullable=true,length=3)
	public String getHdDutyMan4(){
		return this.hdDutyMan4;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任人低
	 */
	public void setHdDutyMan4(String hdDutyMan4){
		this.hdDutyMan4 = hdDutyMan4;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识管理人员
	 */
	@Column(name ="RISK_MAN",nullable=true,length=1000)
	public String getRiskMan(){
		return this.riskMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识管理人员
	 */
	public void setRiskMan(String riskMan){
		this.riskMan = riskMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分值
	 */
	@Column(name ="RISK_MAN_SCORE",nullable=true,length=3)
	public String getRiskManScore(){
		return this.riskManScore;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分值
	 */
	public void setRiskManScore(String riskManScore){
		this.riskManScore = riskManScore;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务管理人员
	 */
	@Column(name ="TASK_MAN",nullable=true,length=1000)
	public String getTaskMan(){
		return this.taskMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务管理人员
	 */
	public void setTaskMan(String taskMan){
		this.taskMan = taskMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分值
	 */
	@Column(name ="TASK_MAN_SCORE",nullable=true,length=3)
	public String getTaskManScore(){
		return this.taskManScore;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分值
	 */
	public void setTaskManScore(String taskManScore){
		this.taskManScore = taskManScore;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务管理人员
	 */
	@Column(name ="TASK_MAN2",nullable=true,length=1000)
	public String getTaskMan2(){
		return this.taskMan2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务管理人员
	 */
	public void setTaskMan2(String taskMan2){
		this.taskMan2 = taskMan2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分值
	 */
	@Column(name ="TASK_MAN_SCORE2",nullable=true,length=3)
	public String getTaskManScore2(){
		return this.taskManScore2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分值
	 */
	public void setTaskManScore2(String taskManScore2){
		this.taskManScore2 = taskManScore2;
	}




}
