package com.sdzk.buss.web.hiddendanger.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 微信发送记录信息
 * @author onlineGenerator
 * @date 2018-08-06 
 *
 */
@Entity
@Table(name = "WeChatSend", schema = "")
@SuppressWarnings("serial")
public class WeChatSendEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**被通知人*/
	private String beNoticeMan;
	/**通知内容*/
	private String content;
	/**创建人名称*/
	private String createName;
	/**创建日期*/
	private Date createDate;
	/**是否发送成功*/
	private String isSuccess;

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
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=32)
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

	@Column(name ="BE_NOTICE_MAN",nullable=true,length=100)
	public String getBeNoticeMan() {
		return beNoticeMan;
	}

	public void setBeNoticeMan(String beNoticeMan) {
		this.beNoticeMan = beNoticeMan;
	}

	@Column(name ="CONTENT",nullable=true,length=1000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name ="IS_SUCCESS",nullable=true,length=1000)
    public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
}
