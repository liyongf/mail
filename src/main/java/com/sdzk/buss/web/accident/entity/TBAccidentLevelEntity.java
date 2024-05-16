package com.sdzk.buss.web.accident.entity;

import java.util.Date;
import java.lang.String;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 事故等级
 * @author onlineGenerator
 * @date 2016-05-06 08:53:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_accident_level", schema = "")
@SuppressWarnings("serial")
public class TBAccidentLevelEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**事故等级*/
	@Excel(name="事故等级")
	private String accidentlevel;
	/**认定标准*/
	@Excel(name="认定标准")
	private String standard;
	/**显示颜色*/
	@Excel(name="显示颜色")
	private String color;
	/**显示排序*/
	@Excel(name="显示排序")
	private String sortindex;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private String createBy;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称")
	private String updateBy;
	/**更新日期*/
	@Excel(name="更新日期",format = "yyyy-MM-dd")
	private Date updateDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  事故等级
	 */
	@Column(name ="ACCIDENTLEVEL",nullable=true,length=64)
	public String getAccidentlevel(){
		return this.accidentlevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  事故等级
	 */
	public void setAccidentlevel(String accidentlevel){
		this.accidentlevel = accidentlevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  认定标准
	 */
	@Column(name ="STANDARD",nullable=true,length=1000)
	public String getStandard(){
		return this.standard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  认定标准
	 */
	public void setStandard(String standard){
		this.standard = standard;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  显示颜色
	 */
	@Column(name ="COLOR",nullable=true,length=32)
	public String getColor(){
		return this.color;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  显示颜色
	 */
	public void setColor(String color){
		this.color = color;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  显示排序
	 */
	@Column(name ="SORTINDEX",nullable=true,length=32)
	public String getSortindex(){
		return this.sortindex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  显示排序
	 */
	public void setSortindex(String sortindex){
		this.sortindex = sortindex;
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
}
