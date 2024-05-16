package org.jeecgframework.web.system.sms.entity;

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
 * @Description: 消息发送记录表
 * @author onlineGenerator
 * @date 2014-09-18 00:01:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_sms", schema = "")
@SuppressWarnings("serial")
public class TSSmsEntity implements java.io.Serializable {
	/**主键*/
	private String id;
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
	/**消息标题*/
	@Excel(name="消息标题")
	private String esTitle;
	/**消息类型*/
	@Excel(name="消息类型")
	private String esType;
	/**发送人*/
	@Excel(name="发送人")
	private String esSender;
	/**接收人*/
	@Excel(name="接收人")
	private String esReceiver;
	/**内容*/
	@Excel(name="内容")
	private String esContent;
	/**发送时间*/
	@Excel(name="发送时间")
	private Date esSendtime;
	/**发送状态*/
	@Excel(name="发送状态")
	private String esStatus;
	/**备注*/
	@Excel(name="备注")
	private String remark;
	
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
	@Column(name ="CREATE_DATE",nullable=true,length=20)
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
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息标题
	 */
	@Column(name ="ES_TITLE",nullable=true,length=32)
	public String getEsTitle(){
		return this.esTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息标题
	 */
	public void setEsTitle(String esTitle){
		this.esTitle = esTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息类型
	 */
	@Column(name ="ES_TYPE",nullable=true,length=1)
	public String getEsType(){
		return this.esType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息类型
	 */
	public void setEsType(String esType){
		this.esType = esType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送人
	 */
	@Column(name ="ES_SENDER",nullable=true,length=50)
	public String getEsSender(){
		return this.esSender;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送人
	 */
	public void setEsSender(String esSender){
		this.esSender = esSender;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接收人
	 */
	@Column(name ="ES_RECEIVER",nullable=true,length=50)
	public String getEsReceiver(){
		return this.esReceiver;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接收人
	 */
	public void setEsReceiver(String esReceiver){
		this.esReceiver = esReceiver;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="ES_CONTENT",nullable=true,length=1000)
	public String getEsContent(){
		return this.esContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容
	 */
	public void setEsContent(String esContent){
		this.esContent = esContent;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发送时间
	 */
	@Column(name ="ES_SENDTIME",nullable=true,length=32)
	public Date getEsSendtime(){
		return this.esSendtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发送时间
	 */
	public void setEsSendtime(Date esSendtime){
		this.esSendtime = esSendtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送状态
	 */
	@Column(name ="ES_STATUS",nullable=true,length=1)
	public String getEsStatus(){
		return this.esStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送状态
	 */
	public void setEsStatus(String esStatus){
		this.esStatus = esStatus;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="remark",nullable=true,length=1)
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
}
