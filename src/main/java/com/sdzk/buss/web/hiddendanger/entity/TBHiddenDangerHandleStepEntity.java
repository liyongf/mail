package com.sdzk.buss.web.hiddendanger.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 隐患处理步骤
 * @author zhangdaihao
 * @date 2017-04-05 11:20:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_hidden_danger_handle_step", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBHiddenDangerHandleStepEntity implements java.io.Serializable {
	/**id*/
	private String id;
	/**隐患ID*/
	private TBHiddenDangerExamEntity hiddenDanger;
	/**处理步骤*/
	private Integer handleStep;
	/**处理日期*/
	private Date handleDate;

	/**处理人*/
	private String handleMan;
	/**驳回备注*/
	private String remark;
	/**处理状态*/
	private String handleStatus;


	private String handleTypeTemp;
	private String shiftTemp;


	@Transient
	public String getShiftTemp() {
		return shiftTemp;
	}

	public void setShiftTemp(String shiftTemp) {
		this.shiftTemp = shiftTemp;
	}

	@Transient
	public String getHandleTypeTemp() {
		return handleTypeTemp;
	}

	public void setHandleTypeTemp(String handleTypeTemp) {
		this.handleTypeTemp = handleTypeTemp;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(String id){
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXAM_ID")
	public TBHiddenDangerExamEntity getHiddenDanger() {
		return hiddenDanger;
	}

	public void setHiddenDanger(TBHiddenDangerExamEntity hiddenDanger) {
		this.hiddenDanger = hiddenDanger;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  处理步骤
	 */
	@Column(name ="HANDLE_STEP",nullable=true,precision=10,scale=0)
	public Integer getHandleStep(){
		return this.handleStep;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  处理步骤
	 */
	public void setHandleStep(Integer handleStep){
		this.handleStep = handleStep;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  处理日期
	 */
	@Column(name ="HANDLE_DATE",nullable=true)
	public Date getHandleDate(){
		return this.handleDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  处理日期
	 */
	public void setHandleDate(Date handleDate){
		this.handleDate = handleDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理人
	 */
	@Column(name ="HANDLE_MAN",nullable=true,length=36)
	public String getHandleMan(){
		return this.handleMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理人
	 */
	public void setHandleMan(String handleMan){
		this.handleMan = handleMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  驳回备注
	 */
	@Column(name ="REMARK",nullable=true,length=5000)
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  驳回备注
	 */
	public void setRemark(String rollBackRemark){
		this.remark = rollBackRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理状态
	 */
	@Column(name ="HANDLE_STATUS",nullable=true,length=50)
	public String getHandleStatus(){
		return this.handleStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理状态
	 */
	public void setHandleStatus(String handleStatus){
		this.handleStatus = handleStatus;
	}

}
