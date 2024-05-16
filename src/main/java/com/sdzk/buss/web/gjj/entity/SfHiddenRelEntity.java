package com.sdzk.buss.web.gjj.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 国家局上报隐患关联表
 * @author gzy
 * @date 2023-11-05 18:35:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sf_hidden_rel", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SfHiddenRelEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**隐患id*/
	private String hiddenId;
	/**国家局上报标识*/
	private String stateFlag;
	/**删除标识*/
	private String isDelete;
	/**上报时间*/
	private Date uploadTime;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=10,scale=0)
	public Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患id
	 */
	@Column(name ="HIDDEN_ID",nullable=true,length=36)
	public String getHiddenId(){
		return this.hiddenId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患id
	 */
	public void setHiddenId(String hiddenId){
		this.hiddenId = hiddenId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国家局上报标识
	 */
	@Column(name ="STATE_FLAG",nullable=true,length=10)
	public String getStateFlag(){
		return this.stateFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国家局上报标识
	 */
	public void setStateFlag(String stateFlag){
		this.stateFlag = stateFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  删除标识
	 */
	@Column(name ="IS_DELETE",nullable=true,length=10)
	public String getIsDelete(){
		return this.isDelete;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  删除标识
	 */
	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  上报时间
	 */
	@Column(name ="UPLOAD_TIME",nullable=true)
	public Date getUploadTime(){
		return this.uploadTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  上报时间
	 */
	public void setUploadTime(Date uploadTime){
		this.uploadTime = uploadTime;
	}
}
