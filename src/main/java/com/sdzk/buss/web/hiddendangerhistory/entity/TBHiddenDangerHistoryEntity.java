package com.sdzk.buss.web.hiddendangerhistory.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 隐患闭环流程处理历史
 * @author onlineGenerator
 * @date 2017-06-22 10:21:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_hidden_danger_history", schema = "")
@SuppressWarnings("serial")
public class TBHiddenDangerHistoryEntity implements java.io.Serializable {
	/**唯一标识*/
	private String id;
	/**隐患基础信息表关联键*/
	private String fkHiddenInfoId;
	/**处理时间*/
	@Excel(name="处理时间",format = "yyyy-MM-dd HH:mm:ss")
	private Date dealTime;
	/**处理环节*/
	@Excel(name="处理环节")
	private String dealStep;
	/**说明*/
	@Excel(name="说明")
	private String dealDesc;
	/**处理人名称*/
	@Excel(name="处理人名称")
	private String dealUserName;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  唯一标识
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
	 *@param: java.lang.String  唯一标识
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患基础信息表关联键
	 */
	@Column(name ="FK_HIDDEN_INFO_ID",nullable=true,length=32)
	public String getFkHiddenInfoId(){
		return this.fkHiddenInfoId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患基础信息表关联键
	 */
	public void setFkHiddenInfoId(String fkHiddenInfoId){
		this.fkHiddenInfoId = fkHiddenInfoId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  处理时间
	 */
	@Column(name ="DEAL_TIME",nullable=false)
	public Date getDealTime(){
		return this.dealTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  处理时间
	 */
	public void setDealTime(Date dealTime){
		this.dealTime = dealTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理环节
	 */
	@Column(name ="DEAL_STEP",nullable=true,length=50)
	public String getDealStep(){
		return this.dealStep;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理环节
	 */
	public void setDealStep(String dealStep){
		this.dealStep = dealStep;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  说明
	 */
	@Column(name ="DEAL_DESC",nullable=true,length=1000)
	public String getDealDesc(){
		return this.dealDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  说明
	 */
	public void setDealDesc(String dealDesc){
		this.dealDesc = dealDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理人名称
	 */
	@Column(name ="DEAL_USER_NAME",nullable=true,length=100)
	public String getDealUserName(){
		return this.dealUserName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理人名称
	 */
	public void setDealUserName(String dealUserName){
		this.dealUserName = dealUserName;
	}
}
