package com.sdzk.buss.web.standardassess.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: SF_MINE_STANDARD_ASSESS_SCORE
 * @author onlineGenerator
 * @date 2017-04-26 17:11:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_standard_assess_score", schema = "")
@SuppressWarnings("serial")
public class SfMineStandardAssessScoreEntity implements java.io.Serializable {
	/**创建时间*/
	@Excel(name="创建时间",format = "yyyy-MM-dd")
	private Date createDate;
	/**更新人登陆名*/
	@Excel(name="更新人登陆名")
	private String updateBy;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private String updateName;
	/**更新时间*/
	@Excel(name="更新时间",format = "yyyy-MM-dd")
	private Date updateDate;
	/**评估等级*/
	@Excel(name="评估等级")
	private String ssasAssessLevel;
	/**矿井类型(1:井工煤矿 2:露天煤矿)*/
	@Excel(name="矿井类型(1:井工煤矿 2:露天煤矿)")
	private String ssasMineType;
	/**创建人登录名*/
	@Excel(name="创建人登录名")
	private String createBy;
	/**唯一标识*/
	private String id;
	/**考核月份*/
	@Excel(name="考核月份",format = "yyyy-MM")
	private String ssasMonth;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private String createName;
	/**得分*/
	@Excel(name="得分")
	private BigDecimal ssasSumScore;
	/**当前状态*/
	private String ssasCurrentStatus;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  当前状态
	 */
	@Column(name ="CURRENT_STATUS",nullable=true,length=20)
	public String getSsasCurrentStatus(){
		return this.ssasCurrentStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  当前状态
	 */
	public void setSsasCurrentStatus(String ssasCurrentStatus){
		this.ssasCurrentStatus = ssasCurrentStatus;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(Date createDate){
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
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评估等级
	 */
	@Column(name ="ASSESS_LEVEL",nullable=true,length=50)
	public String getSsasAssessLevel(){
		return this.ssasAssessLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评估等级
	 */
	public void setSsasAssessLevel(String ssasAssessLevel){
		this.ssasAssessLevel = ssasAssessLevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿井类型(1:井工煤矿 2:露天煤矿)
	 */
	@Column(name ="MINE_TYPE",nullable=true,length=20)
	public String getSsasMineType(){
		return this.ssasMineType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿井类型(1:井工煤矿 2:露天煤矿)
	 */
	public void setSsasMineType(String ssasMineType){
		this.ssasMineType = ssasMineType;
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
	 *@return: java.lang.String  考核月份
	 */
	@Column(name ="MONTH",nullable=true,length=10)
	public String getSsasMonth(){
		return this.ssasMonth;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  考核月份
	 */
	public void setSsasMonth(String ssasMonth){
		this.ssasMonth = ssasMonth;
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  得分
	 */
	@Column(name ="SUM_SCORE",nullable=true,scale=2,length=6)
	public BigDecimal getSsasSumScore(){
		return this.ssasSumScore;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  得分
	 */
	public void setSsasSumScore(BigDecimal ssasSumScore){
		this.ssasSumScore = ssasSumScore;
	}
}
