package com.sdzk.buss.web.standardassess.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 安全生产标准化
 * @author onlineGenerator
 * @date 2017-04-22 09:11:14
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_standard_assess", schema = "")
@SuppressWarnings("serial")
public class SfMineStandardAssessEntity implements java.io.Serializable {
	/**唯一标识*/
	private String id;
	/**考核月份*/
	@Excel(name="考核月份",format = "yyyy-MM")
	private String ssaMonth;
	/**得分*/
	@Excel(name="得分")
	private java.math.BigDecimal ssaSumScore;
	/**矿井类型*/
	private String ssaMineType;
	/**考核类型*/
	private String ssaAssessType;
	/**当前状态*/
	private String ssaCurrentStatus;
	/**创建人登录名*/
	private String createBy;
	/**创建人名称*/
	private String createName;
	/**创建时间*/
	private java.util.Date createDate;
	/**更新人登陆名*/
	private String updateBy;
	/**更新人名称*/
	private String updateName;
	/**更新时间*/
	private java.util.Date updateDate;

	private java.sql.Clob ssaScoreDetail;

	private java.util.Date ssaMonthDate;

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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  考核月份
	 */
	@Column(name ="MONTH",nullable=true)
	public String getSsaMonth(){
		return this.ssaMonth;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  考核月份
	 */
	public void setSsaMonth(String ssaMonth){
		this.ssaMonth = ssaMonth;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  得分
	 */
	@Column(name ="SUM_SCORE",nullable=true,scale=2,length=6)
	public java.math.BigDecimal getSsaSumScore(){
		return this.ssaSumScore;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  得分
	 */
	public void setSsaSumScore(java.math.BigDecimal ssaSumScore){
		this.ssaSumScore = ssaSumScore;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿井类型
	 */
	@Column(name ="MINE_TYPE",nullable=true,length=20)
	public String getSsaMineType(){
		return this.ssaMineType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿井类型
	 */
	public void setSsaMineType(String ssaMineType){
		this.ssaMineType = ssaMineType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  考核类型
	 */
	@Column(name ="ASSESS_TYPE",nullable=true,length=20)
	public String getSsaAssessType(){
		return this.ssaAssessType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  考核类型
	 */
	public void setSsaAssessType(String ssaAssessType){
		this.ssaAssessType = ssaAssessType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  当前状态
	 */
	@Column(name ="CURRENT_STATUS",nullable=true,length=20)
	public String getSsaCurrentStatus(){
		return this.ssaCurrentStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  当前状态
	 */
	public void setSsaCurrentStatus(String ssaCurrentStatus){
		this.ssaCurrentStatus = ssaCurrentStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=100)
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
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登陆名
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登陆名
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=100)
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
	 *@return: java.util.Date  更新时间
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	@Column(name ="score_detail")
	public java.sql.Clob getSsaScoreDetail() {
		return ssaScoreDetail;
	}

	public void setSsaScoreDetail(java.sql.Clob ssaScoreDetail) {
		this.ssaScoreDetail = ssaScoreDetail;
	}
	@Transient
	public java.util.Date getSsaMonthDate() {
		return ssaMonthDate;
	}

	public void setSsaMonthDate(java.util.Date ssaMonthDate) {
		this.ssaMonthDate = ssaMonthDate;
	}
	
	
}
