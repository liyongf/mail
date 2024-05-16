package com.sdzk.buss.web.gjj.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 上报国家局管控措施表
 * @author gzy
 * @date 2023-11-02 13:35:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sf_risk_measure", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SfRiskMeasureEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**t_b_risk_factors_rel    id    用于上报国家局关联风险管控措施*/
	private String riskFactorsId;
	/**操作标识  0：已上报  1：新增  2：修改  3：删除*/
	private String stateFlag;
	/**风险编码*/
	private String riskCode;
	/**风险点编码*/
	private String riskPointCode;
	/**措施详情*/
	private String measures;
	/**责任岗位*/
	private String post;
	/**责任部门*/
	private String depart;
	/**创建时间*/
	private Date dateTime;
	/**上报时间*/
	private Date uploadTime;

	/**0:代表需要上报   1：不需要上报  默认 1   辨识结束后为0*/
	private String isUpload;
	
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
	 *@return: java.lang.String  t_b_risk_factors_rel    id    用于上报国家局关联风险管控措施
	 */
	@Column(name ="RISK_FACTORS_ID",nullable=true,length=36)
	public String getRiskFactorsId(){
		return this.riskFactorsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  t_b_risk_factors_rel    id    用于上报国家局关联风险管控措施
	 */
	public void setRiskFactorsId(String riskFactorsId){
		this.riskFactorsId = riskFactorsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作标识  0：已上报  1：新增  2：修改  3：删除
	 */
	@Column(name ="STATE_FLAG",nullable=true,length=10)
	public String getStateFlag(){
		return this.stateFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作标识  0：已上报  1：新增  2：修改  3：删除
	 */
	public void setStateFlag(String stateFlag){
		this.stateFlag = stateFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险编码
	 */
	@Column(name ="RISK_CODE",nullable=true,length=36)
	public String getRiskCode(){
		return this.riskCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险编码
	 */
	public void setRiskCode(String riskCode){
		this.riskCode = riskCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险点编码
	 */
	@Column(name ="RISK_POINT_CODE",nullable=true,length=36)
	public String getRiskPointCode(){
		return this.riskPointCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险点编码
	 */
	public void setRiskPointCode(String riskPointCode){
		this.riskPointCode = riskPointCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  措施详情
	 */
	@Column(name ="MEASURES",nullable=true,length=2000)
	public String getMeasures(){
		return this.measures;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  措施详情
	 */
	public void setMeasures(String measures){
		this.measures = measures;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任岗位
	 */
	@Column(name ="POST",nullable=true,length=50)
	public String getPost(){
		return this.post;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任岗位
	 */
	public void setPost(String post){
		this.post = post;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任部门
	 */
	@Column(name ="DEPART",nullable=true,length=200)
	public String getDepart(){
		return this.depart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任部门
	 */
	public void setDepart(String depart){
		this.depart = depart;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="DATE_TIME",nullable=true)
	public Date getDateTime(){
		return this.dateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setDateTime(Date dateTime){
		this.dateTime = dateTime;
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

	@Column(name ="is_upload",nullable=true)
	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}
}
