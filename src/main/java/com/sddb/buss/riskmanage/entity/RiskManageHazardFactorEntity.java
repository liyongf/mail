package com.sddb.buss.riskmanage.entity;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: t_b_danger_source
 * @author onlineGenerator
 * @date 2017-06-20 14:18:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_risk_manage_hazard_factor", schema = "")
@SuppressWarnings("serial")
public class RiskManageHazardFactorEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;

	/**任务编号*/
	private String taskId;

	/**管控的风险id*/
	private RiskIdentificationEntity risk;

	/**管控类型*/
	private  String manageType;

	/**危险因素id*/
	private HazardFactorsEntity hazardFactor;

	/**处理状态*/
	private  String handleStatus;

	/**管控措施落实情况*/
	private  String implDetail;

	/**备注*/
	private  String remark;

	/**新录入风险列表*/
	private List<RiskManageRelRisk> riskList;

	/**新录入隐患列表*/
	private List<RiskManageRelHd> hdList;

	private RiskManageTaskEntity riskManageTaskEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "risk_manage_task_id")
	@NotFound(action= NotFoundAction.IGNORE)
	public RiskManageTaskEntity getRiskManageTaskEntity(){return this.riskManageTaskEntity;}
	public void setRiskManageTaskEntity(RiskManageTaskEntity riskManageTaskEntity){this.riskManageTaskEntity = riskManageTaskEntity;}

	/**创建人登录名称*/
	private String createBy;
	/**创建人名称*/
	private String createName;
	/**创建日期*/
	private Date createDate;
	/**更新人登录名称*/
	private String updateBy;
	/**更新人名称*/
	private String updateName;
	/**更新日期*/
	private Date updateDate;

	//辅助字段
	private String riskCount;
	private String hdCount;
	private  String hazardFactorId;

	private String hfManageMeasure;
	private String hfLevel;

	private String numTemp;
	private String departNameTemp;
	private String realNameTemp;
    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  唯一编号
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "risk_id")
	public RiskIdentificationEntity getRisk(){return this.risk;}
	public void setRisk(RiskIdentificationEntity risk){this.risk = risk;}

	@Column(name="manage_type",nullable=true,length=36)
	public String getManageType(){return this.manageType;}
	public void setManageType(String manageType){this.manageType = manageType;}

	@Column(name="task_id",nullable=true,length=36)
	public String getTaskId(){return this.taskId;}
	public void setTaskId(String taskId){this.taskId = taskId;}

	@Column(name="handle_status",nullable=true,length=36)
	public String getHandleStatus(){return this.handleStatus;}
	public void setHandleStatus(String handleStatus){this.handleStatus = handleStatus;}

	@Column(name="impl_detail",nullable=true,length=36)
	public String getImplDetail(){return this.implDetail;}
	public void setImplDetail(String implDetail){this.implDetail = implDetail;}

	@Column(name="remark",nullable=true,length=36)
	public String getRemark(){return this.remark;}
	public void setRemark(String remark){this.remark = remark;}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hazard_factor_id")
	public HazardFactorsEntity getHazardFactor(){return this.hazardFactor;}
	public void setHazardFactor(HazardFactorsEntity hazardFactor){this.hazardFactor = hazardFactor;}

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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskManageHazardFactor")
	public List<RiskManageRelRisk> getRiskList() {
		return riskList;
	}

	public void setRiskList(List<RiskManageRelRisk> riskList) {
		this.riskList = riskList;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskManageHazardFactor")
	public List<RiskManageRelHd> getHdList() {
		return hdList;
	}

	public void setHdList(List<RiskManageRelHd> hdList) {
		this.hdList = hdList;
	}

	@Transient
	public String getRiskCount() {
		return riskCount;
	}
	public void setRiskCount(String riskCount) {
		this.riskCount = riskCount;
	}

	@Transient
	public String getHdCount() {
		return hdCount;
	}
	public void setHdCount(String hdCount) {
		this.hdCount = hdCount;
	}

	@Transient
	public String getHfManageMeasure() {
		return hfManageMeasure;
	}

	public void setHfManageMeasure(String hfManageMeasure) {
		this.hfManageMeasure = hfManageMeasure;
	}
	@Transient
	public String getHfLevel() {
		return hfLevel;
	}

	public void setHfLevel(String hfLevel) {
		this.hfLevel = hfLevel;
	}
	@Transient
	public String getNumTemp() {
		return numTemp;
	}

	public void setNumTemp(String numTemp) {
		this.numTemp = numTemp;
	}
	@Transient
	public String getDepartNameTemp() {
		return departNameTemp;
	}

	public void setDepartNameTemp(String departNameTemp) {
		this.departNameTemp = departNameTemp;
	}
	@Transient
	public String getRealNameTemp() {
		return realNameTemp;
	}

	public void setRealNameTemp(String realNameTemp) {
		this.realNameTemp = realNameTemp;
	}
}
