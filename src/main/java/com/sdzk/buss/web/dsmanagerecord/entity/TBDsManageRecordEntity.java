package com.sdzk.buss.web.dsmanagerecord.entity;

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
 * @Description: 重大风险管控记录
 * @author onlineGenerator
 * @date 2017-06-29 10:24:12
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_ds_manage_record", schema = "")
@SuppressWarnings("serial")
public class TBDsManageRecordEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**危险源id*/
	private String dangerId;
	/**管控人*/
	@Excel(name="管控人")
	private String controller;
	/**管控时间*/
	@Excel(name="管控时间",format = "yyyy-MM-dd")
	private Date controlleDate;
	/**工作内容*/
	@Excel(name="工作内容")
	private String workContent;
	/**取得效果*/
	@Excel(name="取得效果")
	private String achieveEffect;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  危险源id
	 */
	@Column(name ="DANGER_ID",nullable=true,length=36)
	public String getDangerId(){
		return this.dangerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危险源id
	 */
	public void setDangerId(String dangerId){
		this.dangerId = dangerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管控人
	 */
	@Column(name ="CONTROLLER",nullable=true,length=36)
	public String getController(){
		return this.controller;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管控人
	 */
	public void setController(String controller){
		this.controller = controller;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  管控时间
	 */
	@Column(name ="CONTROLLE_DATE",nullable=true)
	public Date getControlleDate(){
		return this.controlleDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  管控时间
	 */
	public void setControlleDate(Date controlleDate){
		this.controlleDate = controlleDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作内容
	 */
	@Column(name ="WORK_CONTENT",nullable=true,length=1000)
	public String getWorkContent(){
		return this.workContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作内容
	 */
	public void setWorkContent(String workContent){
		this.workContent = workContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  取得效果
	 */
	@Column(name ="ACHIEVE_EFFECT",nullable=true,length=1000)
	public String getAchieveEffect(){
		return this.achieveEffect;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  取得效果
	 */
	public void setAchieveEffect(String achieveEffect){
		this.achieveEffect = achieveEffect;
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
