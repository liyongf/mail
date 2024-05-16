package com.sdzk.buss.web.health.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 职工个人信息
 * @author hansf
 * @date 2016-02-29 15:39:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_employee_info", schema = "")
@SuppressWarnings("serial")
public class TBEmployeeInfoEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**档案号*/
	@Excel(name="档案号")
	private String fileNo;
	/**在岗编号*/
	@Excel(name="在岗编号")
	private String postNumber;
	/**退休编号*/
	@Excel(name="退休编号")
	private String retireNumber;
	/**姓名*/
	@Excel(name="姓名")
	private String name;
	/**性别*/
	@Excel(name="性别",replace={"男_0","女_1"})
	private String gender;
	/**身份证号*/
	@Excel(name="身份证号",type=1)
	private String cardNumber;
	/**参加工作时间*/
	@Excel(name="参加工作时间",format = "yyyy-MM-dd")
	private java.util.Date partiWorkDate;
	/**现工作单位*/
	@Excel(name="（现）工作单位")
	private String currWorkUnits;
	/**现从事工种*/
	@Excel(name="（现）从事工种")
	private String currTrade;
	/**接触职业危害种类*/
	@Excel(name="接触职业危害种类")
	private String jobHazardCategory;
	/**岗位类别*/
	@Excel(name="岗位类别",replace={"管理岗_1","工人岗_2"})
	private String postCategory;
	/**岗位状态*/
	@Excel(name="岗位状态",replace={"在岗_1","在职离岗_2","离岗_3"})
	private String postStatus;
	/**是否删除*/
	private String isDelete;

	/**岗位变更简历*/
	@Excel(name="岗位变更简历")
	private String postChangeResume;

	/**健康检查专项档案*/
	private TBHealthExamEntity tBHealthExamEntity;
	/**健康疗养专项档案*/
	private TBHealthRecuperateEntity tBHealthRecuperateEntity;
	/**教育培训专项档案*/
	private TBHealthTrainEntity tBHealthTrainEntity;

	//临时字段用于导出
	private String postStatusTemp;
    //劳动防护用品
	private String laborProtectTemp;
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
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
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
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  档案号
	 */
	@Column(name ="FILE_NO",nullable=true,length=20)
	public String getFileNo(){
		return this.fileNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  档案号
	 */
	public void setFileNo(String fileNo){
		this.fileNo = fileNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  在岗编号
	 */
	@Column(name ="POST_NUMBER",nullable=true,length=20)
	public String getPostNumber(){
		return this.postNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  在岗编号
	 */
	public void setPostNumber(String postNumber){
		this.postNumber = postNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  退休编号
	 */
	@Column(name ="RETIRE_NUMBER",nullable=true,length=20)
	public String getRetireNumber(){
		return this.retireNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  退休编号
	 */
	public void setRetireNumber(String retireNumber){
		this.retireNumber = retireNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	@Column(name ="NAME",nullable=true,length=100)
	public String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="GENDER",nullable=true,length=50)
	public String getGender(){
		return this.gender;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setGender(String gender){
		this.gender = gender;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证号
	 */
	@Column(name ="CARD_NUMBER",nullable=true,length=50)
	public String getCardNumber(){
		return this.cardNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号
	 */
	public void setCardNumber(String cardNumber){
		this.cardNumber = cardNumber;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  参加工作时间
	 */
	@Column(name ="PARTI_WORK_DATE",nullable=true)
	public java.util.Date getPartiWorkDate(){
		return this.partiWorkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  参加工作时间
	 */
	public void setPartiWorkDate(java.util.Date partiWorkDate){
		this.partiWorkDate = partiWorkDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  现工作单位
	 */
	@Column(name ="CURR_WORK_UNITS",nullable=true,length=50)
	public String getCurrWorkUnits(){
		return this.currWorkUnits;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  现工作单位
	 */
	public void setCurrWorkUnits(String currWorkUnits){
		this.currWorkUnits = currWorkUnits;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  现从事工种
	 */
	@Column(name ="CURR_TRADE",nullable=true,length=50)
	public String getCurrTrade(){
		return this.currTrade;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  现从事工种
	 */
	public void setCurrTrade(String currTrade){
		this.currTrade = currTrade;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接触职业危害种类
	 */
	@Column(name ="JOB_HAZARD_CATEGORY",nullable=true,length=50)
	public String getJobHazardCategory(){
		return this.jobHazardCategory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接触职业危害种类
	 */
	public void setJobHazardCategory(String jobHazardCategory){
		this.jobHazardCategory = jobHazardCategory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  岗位类别
	 */
	@Column(name ="POST_CATEGORY",nullable=true,length=50)
	public String getPostCategory(){
		return this.postCategory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  岗位类别
	 */
	public void setPostCategory(String postCategory){
		this.postCategory = postCategory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  岗位状态
	 */
	@Column(name ="POST_STATUS",nullable=true,length=50)
	public String getPostStatus(){
		return this.postStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  岗位状态
	 */
	public void setPostStatus(String postStatus){
		this.postStatus = postStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否删除
	 */
	@Column(name ="IS_DELETE",nullable=true,length=50)
	public String getIsDelete(){
		return this.isDelete;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否删除
	 */
	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  岗位变更简历
	 */
	@Column(name ="POST_CHANGE_RESUME",nullable=true,length=5000)
	public String getPostChangeResume(){
		return this.postChangeResume;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  岗位变更简历
	 */
	public void setPostChangeResume(String postChangeResume){
		this.postChangeResume = postChangeResume;
	}

	public void setPostStatusTemp(String postStatusTemp) {
		this.postStatusTemp = postStatusTemp;
	}

	@Transient
	public String getPostStatusTemp() {
		return postStatusTemp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HEALTH_EXAM_ID")
	@NotFound(action= NotFoundAction.IGNORE)
	public TBHealthExamEntity gettBHealthExamEntity() {
		return tBHealthExamEntity;
	}

	public void settBHealthExamEntity(TBHealthExamEntity tBHealthExamEntity) {
		this.tBHealthExamEntity = tBHealthExamEntity;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HEALTH_RECUPERATE_ID")
	@NotFound(action= NotFoundAction.IGNORE)
	public TBHealthRecuperateEntity gettBHealthRecuperateEntity() {
		return tBHealthRecuperateEntity;
	}

	public void settBHealthRecuperateEntity(
			TBHealthRecuperateEntity tBHealthRecuperateEntity) {
		this.tBHealthRecuperateEntity = tBHealthRecuperateEntity;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HEALTH_TRAIN_ID")
	@NotFound(action= NotFoundAction.IGNORE)
	public TBHealthTrainEntity gettBHealthTrainEntity() {
		return tBHealthTrainEntity;
	}

	public void settBHealthTrainEntity(TBHealthTrainEntity tBHealthTrainEntity) {
		this.tBHealthTrainEntity = tBHealthTrainEntity;
	}
	@Transient
	public String getLaborProtectTemp() {
		return laborProtectTemp;
	}

	public void setLaborProtectTemp(String laborProtectTemp) {
		this.laborProtectTemp = laborProtectTemp;
	}
	
	
	
	
}
