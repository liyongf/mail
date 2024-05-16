package com.sdzk.buss.web.health.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 职业健康检查专项档案
 * @author hansf
 * @date 2016-03-03 10:28:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_health_exam", schema = "")
@SuppressWarnings("serial")
public class TBHealthExamEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**职工基本信息ID*/
	@Excel(name="职工基本信息ID")
	private String employeeId;
	/**检查时间*/
	@Excel(name="检查时间1",format = "yyyy-MM-dd")
	private java.util.Date prejobChkDate;
	/**检查机构*/
	@Excel(name="检查机构1")
	private String prejobChkOrg;
	/**检查类别*/
	@Excel(name="检查类别1",replace={"新工人_1","转岗上岗_2","实习人员_3","外来施工_4"})
	private String prejobChkCategory;
	/**检查结果未见异常*/
	@Excel(name="未见异常1")
	private String prejobNoAbnormal;
	/**检查结果职业禁忌*/
	@Excel(name="职业禁忌1")
	private String prejobBan;
	/**检查结果其他疾病*/
	@Excel(name="其他疾病1")
	private String prejobOtherDiseases;
	/**处置情况上岗前*/
	@Excel(name="处置情况1")
	private String prejobDisposition;
	/**是否书面告知上岗前*/
	@Excel(name="是否书面告知1")
	private String prejobIsNotify;
	/**检查时间在岗期间*/
	@Excel(name="检查时间2",format = "yyyy-MM-dd")
	private java.util.Date duringChkDate;
	/**检查机构在岗期间*/
	@Excel(name="检查机构2")
	private String duringChkOrg;
	/**检查结果未见异常在岗期间*/
	@Excel(name="未见异常2")
	private String duringNoAbnormal;
	/**检查结果疑是职业病在岗期间*/
	@Excel(name="疑是职业病2")
	private String duringOccupationDiseases;
	/**检查结果职业禁忌在岗期间*/
	@Excel(name="职业禁忌2")
	private String duringBan;
	/**检查结果其他疾病在岗期间*/
	@Excel(name="其他疾病2")
	private String duringOtherDiseases;
	/**处置情况在岗期间*/
	@Excel(name="处置情况2")
	private String duringDisposition;
	/**是否书面告知在岗期间*/
	@Excel(name="是否书面告知2")
	private String duringIsNotify;
	/**检查时间离岗时*/
	@Excel(name="检查时间3",format = "yyyy-MM-dd")
	private java.util.Date leavingChkDate;
	/**检查机构离岗时*/
	@Excel(name="检查机构3")
	private String leavingChkOrg;
	/**检查结果离岗时*/
	@Excel(name="检查结果3")
	private String leavingChkResult;
	/**是否书面告知离岗时*/
	@Excel(name="是否书面告知3")
	private String leavingIsNotify;
	/**离岗类型离岗后*/
	@Excel(name="离岗类型4",replace={"在职离岗_1","退休离岗_2"})
	private String leavedType;
	/**检查时间离岗后*/
	@Excel(name="检查时间4",format = "yyyy-MM-dd")
	private java.util.Date leavedChkDate;
	/**检查结果离岗后*/
	@Excel(name="检查结果4")
	private String leavedChkResult;
	/**检查时间应急检查*/
	@Excel(name="检查时间5",format = "yyyy-MM-dd")
	private java.util.Date emergChkDate;
	/**检查种类应急检查*/
	@Excel(name="检查种类5")
	private String emergChkCategory;
	/**检查机构应急检查*/
	@Excel(name="检查机构5")
	private String emergChkOrg;
	/**处置情况应急检查*/
	@Excel(name="处置情况5")
	private String emergDisposition;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private String createBy;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private java.util.Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称")
	private String updateBy;
	/**更新日期*/
	@Excel(name="更新日期",format = "yyyy-MM-dd")
	private java.util.Date updateDate;
	//用于导入
	@Excel(name="档案号")
	private String fileNoTemp;
    @Excel(name="身份证号")
    private String cardNumberTemp;
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
	 *@return: java.lang.String  职工基本信息ID
	 */
	@Column(name ="EMPLOYEE_ID",nullable=true,length=36)
	public String getEmployeeId(){
		return this.employeeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职工基本信息ID
	 */
	public void setEmployeeId(String employeeId){
		this.employeeId = employeeId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  检查时间
	 */
	@Column(name ="PREJOB_CHK_DATE",nullable=true)
	public java.util.Date getPrejobChkDate(){
		return this.prejobChkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  检查时间
	 */
	public void setPrejobChkDate(java.util.Date prejobChkDate){
		this.prejobChkDate = prejobChkDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查机构
	 */
	@Column(name ="PREJOB_CHK_ORG",nullable=true,length=100)
	public String getPrejobChkOrg(){
		return this.prejobChkOrg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查机构
	 */
	public void setPrejobChkOrg(String prejobChkOrg){
		this.prejobChkOrg = prejobChkOrg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查类别
	 */
	@Column(name ="PREJOB_CHK_CATEGORY",nullable=true,length=50)
	public String getPrejobChkCategory(){
		return this.prejobChkCategory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查类别
	 */
	public void setPrejobChkCategory(String prejobChkCategory){
		this.prejobChkCategory = prejobChkCategory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查结果未见异常
	 */
	@Column(name ="PREJOB_NO_ABNORMAL",nullable=true,length=50)
	public String getPrejobNoAbnormal(){
		return this.prejobNoAbnormal;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查结果未见异常
	 */
	public void setPrejobNoAbnormal(String prejobNoAbnormal){
		this.prejobNoAbnormal = prejobNoAbnormal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查结果职业禁忌
	 */
	@Column(name ="PREJOB_BAN",nullable=true,length=50)
	public String getPrejobBan(){
		return this.prejobBan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查结果职业禁忌
	 */
	public void setPrejobBan(String prejobBan){
		this.prejobBan = prejobBan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查结果其他疾病
	 */
	@Column(name ="PREJOB_OTHER_DISEASES",nullable=true,length=50)
	public String getPrejobOtherDiseases(){
		return this.prejobOtherDiseases;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查结果其他疾病
	 */
	public void setPrejobOtherDiseases(String prejobOtherDiseases){
		this.prejobOtherDiseases = prejobOtherDiseases;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处置情况上岗前
	 */
	@Column(name ="PREJOB_DISPOSITION",nullable=true,length=50)
	public String getPrejobDisposition(){
		return this.prejobDisposition;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处置情况上岗前
	 */
	public void setPrejobDisposition(String prejobDisposition){
		this.prejobDisposition = prejobDisposition;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否书面告知上岗前
	 */
	@Column(name ="PREJOB_IS_NOTIFY",nullable=true,length=50)
	public String getPrejobIsNotify(){
		return this.prejobIsNotify;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否书面告知上岗前
	 */
	public void setPrejobIsNotify(String prejobIsNotify){
		this.prejobIsNotify = prejobIsNotify;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  检查时间在岗期间
	 */
	@Column(name ="DURING_CHK_DATE",nullable=true)
	public java.util.Date getDuringChkDate(){
		return this.duringChkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  检查时间在岗期间
	 */
	public void setDuringChkDate(java.util.Date duringChkDate){
		this.duringChkDate = duringChkDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查机构在岗期间
	 */
	@Column(name ="DURING_CHK_ORG",nullable=true,length=100)
	public String getDuringChkOrg(){
		return this.duringChkOrg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查机构在岗期间
	 */
	public void setDuringChkOrg(String duringChkOrg){
		this.duringChkOrg = duringChkOrg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查结果未见异常在岗期间
	 */
	@Column(name ="DURING_NO_ABNORMAL",nullable=true,length=50)
	public String getDuringNoAbnormal(){
		return this.duringNoAbnormal;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查结果未见异常在岗期间
	 */
	public void setDuringNoAbnormal(String duringNoAbnormal){
		this.duringNoAbnormal = duringNoAbnormal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查结果疑是职业病在岗期间
	 */
	@Column(name ="DURING_OCCUPATION_DISEASES",nullable=true,length=50)
	public String getDuringOccupationDiseases(){
		return this.duringOccupationDiseases;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查结果疑是职业病在岗期间
	 */
	public void setDuringOccupationDiseases(String duringOccupationDiseases){
		this.duringOccupationDiseases = duringOccupationDiseases;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查结果职业禁忌在岗期间
	 */
	@Column(name ="DURING_BAN",nullable=true,length=50)
	public String getDuringBan(){
		return this.duringBan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查结果职业禁忌在岗期间
	 */
	public void setDuringBan(String duringBan){
		this.duringBan = duringBan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查结果其他疾病在岗期间
	 */
	@Column(name ="DURING_OTHER_DISEASES",nullable=true,length=50)
	public String getDuringOtherDiseases(){
		return this.duringOtherDiseases;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查结果其他疾病在岗期间
	 */
	public void setDuringOtherDiseases(String duringOtherDiseases){
		this.duringOtherDiseases = duringOtherDiseases;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处置情况在岗期间
	 */
	@Column(name ="DURING_DISPOSITION",nullable=true,length=200)
	public String getDuringDisposition(){
		return this.duringDisposition;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处置情况在岗期间
	 */
	public void setDuringDisposition(String duringDisposition){
		this.duringDisposition = duringDisposition;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否书面告知在岗期间
	 */
	@Column(name ="DURING_IS_NOTIFY",nullable=true,length=50)
	public String getDuringIsNotify(){
		return this.duringIsNotify;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否书面告知在岗期间
	 */
	public void setDuringIsNotify(String duringIsNotify){
		this.duringIsNotify = duringIsNotify;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  检查时间离岗时
	 */
	@Column(name ="LEAVING_CHK_DATE",nullable=true)
	public java.util.Date getLeavingChkDate(){
		return this.leavingChkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  检查时间离岗时
	 */
	public void setLeavingChkDate(java.util.Date leavingChkDate){
		this.leavingChkDate = leavingChkDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查机构离岗时
	 */
	@Column(name ="LEAVING_CHK_ORG",nullable=true,length=100)
	public String getLeavingChkOrg(){
		return this.leavingChkOrg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查机构离岗时
	 */
	public void setLeavingChkOrg(String leavingChkOrg){
		this.leavingChkOrg = leavingChkOrg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查结果离岗时
	 */
	@Column(name ="LEAVING_CHK_RESULT",nullable=true,length=200)
	public String getLeavingChkResult(){
		return this.leavingChkResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查结果离岗时
	 */
	public void setLeavingChkResult(String leavingChkResult){
		this.leavingChkResult = leavingChkResult;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否书面告知离岗时
	 */
	@Column(name ="LEAVING_IS_NOTIFY",nullable=true,length=50)
	public String getLeavingIsNotify(){
		return this.leavingIsNotify;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否书面告知离岗时
	 */
	public void setLeavingIsNotify(String leavingIsNotify){
		this.leavingIsNotify = leavingIsNotify;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  离岗类型离岗后
	 */
	@Column(name ="LEAVED_TYPE",nullable=true,length=50)
	public String getLeavedType(){
		return this.leavedType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  离岗类型离岗后
	 */
	public void setLeavedType(String leavedType){
		this.leavedType = leavedType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  检查时间离岗后
	 */
	@Column(name ="LEAVED_CHK_DATE",nullable=true)
	public java.util.Date getLeavedChkDate(){
		return this.leavedChkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  检查时间离岗后
	 */
	public void setLeavedChkDate(java.util.Date leavedChkDate){
		this.leavedChkDate = leavedChkDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查结果离岗后
	 */
	@Column(name ="LEAVED_CHK_RESULT",nullable=true,length=200)
	public String getLeavedChkResult(){
		return this.leavedChkResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查结果离岗后
	 */
	public void setLeavedChkResult(String leavedChkResult){
		this.leavedChkResult = leavedChkResult;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  检查时间应急检查
	 */
	@Column(name ="EMERG_CHK_DATE",nullable=true)
	public java.util.Date getEmergChkDate(){
		return this.emergChkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  检查时间应急检查
	 */
	public void setEmergChkDate(java.util.Date emergChkDate){
		this.emergChkDate = emergChkDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查种类应急检查
	 */
	@Column(name ="EMERG_CHK_CATEGORY",nullable=true,length=50)
	public String getEmergChkCategory(){
		return this.emergChkCategory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查种类应急检查
	 */
	public void setEmergChkCategory(String emergChkCategory){
		this.emergChkCategory = emergChkCategory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查机构应急检查
	 */
	@Column(name ="EMERG_CHK_ORG",nullable=true,length=100)
	public String getEmergChkOrg(){
		return this.emergChkOrg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查机构应急检查
	 */
	public void setEmergChkOrg(String emergChkOrg){
		this.emergChkOrg = emergChkOrg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处置情况应急检查
	 */
	@Column(name ="EMERG_DISPOSITION",nullable=true,length=200)
	public String getEmergDisposition(){
		return this.emergDisposition;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处置情况应急检查
	 */
	public void setEmergDisposition(String emergDisposition){
		this.emergDisposition = emergDisposition;
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
	@Column(name ="UPDATE_DATE",nullable=true)
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

	@Transient
	public String getFileNoTemp() {
		return fileNoTemp;
	}

	public void setFileNoTemp(String fileNoTemp) {
		this.fileNoTemp = fileNoTemp;
	}

    @Transient
    public String getCardNumberTemp() {
        return cardNumberTemp;
    }

    public void setCardNumberTemp(String cardNumberTemp) {
        this.cardNumberTemp = cardNumberTemp;
    }
}
