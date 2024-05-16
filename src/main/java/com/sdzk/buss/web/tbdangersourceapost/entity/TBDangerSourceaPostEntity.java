package com.sdzk.buss.web.tbdangersourceapost.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: 岗位通用危险源
 * @author onlineGenerator
 * @date 2017-07-24 10:59:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_danger_source_post", schema = "")
@SuppressWarnings("serial")
public class TBDangerSourceaPostEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**风险名称*/
	@Excel(name="风险名称")
	private String dangerName;
	/**事故类型*/
	@Excel(name="事故类型")
	private String accidentType;
	/**风险类型*/
	@Excel(name="风险类型")
	private String riskType;
	/**风险可能性*/
	@Excel(name="风险可能性")
	private String riskPossibility;
	/**风险等级*/
	@Excel(name="风险等级")
	private String riskLevel;
	/**风险损失*/
	@Excel(name="风险损失")
	private String riskLoss;
	/**风险后果描述*/
	@Excel(name="风险后果描述")
	private String riskAffectDesc;
	/**风险值*/
	@Excel(name="风险值")
	private String riskValue;
	/**责任措施*/
	@Excel(name="责任措施")
	private String respMeasures;
	/**管控措施*/
	@Excel(name="管控措施")
	private String mangMeasures;
	/**管控标准*/
	@Excel(name="管控标准")
	private String mangStandards;
	/**内部市场价格*/
	@Excel(name="内部市场价格")
	private String internalMarketPrice;
	/**标准依据*/
	@Excel(name="标准依据")
	private String standardAccordance;
	/**依据条目*/
	@Excel(name="依据条目")
	private String basedEntry;
	/**专业分类*/
	@Excel(name="专业分类")
	private String professionaltype;
	/**是否重大风险*/
	@Excel(name="是否重大风险")
	private String ismajor;
	/**危险距离*/

	private String distance;
	/**周边情况及相互影响因素*/

	private String surrounding;
	/**监控措施*/

	private String monitor;
	/**应急措施*/

	private String emergency;
	/**责任部门*/
	@Excel(name="责任部门")
	private String resDepart;
	/**备案号*/
	@Excel(name="备案号")
	private String caseNum;
	/**辨识时间*/
	@Excel(name="辨识时间",format = "yyyy-MM-dd")
	private Date recognizeTime;
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

    /**新增加的LEC风险统计方法*/
    /**LEC风险可能性*/

    private Double lecRiskPossibility;
    /**LEC风险损失*/

    private Double lecRiskLoss;
    /**LEC人员暴露于危险环境中的频繁程度*/

    private Double lecExposure;
    /**LEC风险值*/
    @Excel(name="LEC风险值")
    private Double lecRiskValue;

    @Column(name ="LEC_risk_possibility",nullable=true)
    public Double getLecRiskPossibility() {
        return lecRiskPossibility;
    }

    public void setLecRiskPossibility(Double lecRiskPossibility) {
        this.lecRiskPossibility = lecRiskPossibility;
    }

    @Column(name ="LEC_risk_loss",nullable=true)
    public Double getLecRiskLoss() {
        return lecRiskLoss;
    }

    public void setLecRiskLoss(Double lecRiskLoss) {
        this.lecRiskLoss = lecRiskLoss;
    }

    @Column(name ="LEC_exposure",nullable=true)
    public Double getLecExposure() {
        return lecExposure;
    }

    public void setLecExposure(Double lecExposure) {
        this.lecExposure = lecExposure;
    }

    @Column(name ="LEC_risk_value",nullable=true)
    public Double getLecRiskValue() {
        return lecRiskValue;
    }

    public void setLecRiskValue(Double lecRiskValue) {
        this.lecRiskValue = lecRiskValue;
    }

    @Excel(name="LEC风险可能性")
    private String lecRiskPossibilityTemp;

    @Excel(name="LEC风险损失")
    private String lecRiskLossTemp;
    @Excel(name="LEC人员暴露于危险环境中的频繁程度")
    private String lecExposureTemp;
    @Transient
    public String getLecRiskPossibilityTemp() {
        return lecRiskPossibilityTemp;
    }
    public void setLecRiskPossibilityTemp(String lecRiskPossibilityTemp) {
        this.lecRiskPossibilityTemp = lecRiskPossibilityTemp;
    }
    @Transient
    public String getLecRiskLossTemp() {
        return lecRiskLossTemp;
    }
    public void setLecRiskLossTemp(String lecRiskLossTemp) {
        this.lecRiskLossTemp = lecRiskLossTemp;
    }
    @Transient
    public String getLecExposureTemp() {
        return lecExposureTemp;
    }
    public void setLecExposureTemp(String lecExposureTemp) {
        this.lecExposureTemp = lecExposureTemp;
    }

    private String postId;
	@Excel(name="工种")
	private String postName;

	private String accidentTypeTemp;
	private String professionaltypeTemp;
	private String riskTypeTemp;
	private String riskPossibilityTemp;
	private String riskLossTemp;
	private String riskLevelTemp;
	private String ismajorTemp;

	@Transient
	public String getIsmajorTemp() {
		return ismajorTemp;
	}

	public void setIsmajorTemp(String ismajorTemp) {
		this.ismajorTemp = ismajorTemp;
	}

	@Transient
	public String getProfessionaltypeTemp() {
		if (StringUtil.isNotEmpty(professionaltypeTemp)) {
			return  professionaltypeTemp;
		}
		return professionaltypeTemp = DicUtil.getTypeNameByCode("proCate_gradeControl", this.getProfessionaltype());
	}

	@Transient
	public String getRiskTypeTemp() {
		if (StringUtil.isNotEmpty(riskTypeTemp)) {
			return  riskTypeTemp;
		}
		return riskTypeTemp = DicUtil.getTypeNameByCode("hazardCate",this.getRiskType());
	}

	@Transient
	public String getRiskPossibilityTemp() {
		if (StringUtil.isNotEmpty(riskPossibilityTemp)) {
			return  riskPossibilityTemp;
		}
		return riskPossibilityTemp = DicUtil.getTypeNameByCode("probability", this.getRiskPossibility());
	}

	@Transient
	public String getRiskLossTemp() {
		if (StringUtil.isNotEmpty(riskLossTemp)) {
			return  riskLossTemp;
		}
		return riskLossTemp = DicUtil.getTypeNameByCode("hazard_fxss", this.getRiskLoss());
	}

	@Transient
	public String getRiskLevelTemp() {
		if (StringUtil.isNotEmpty(riskLevelTemp)) {
			return  riskLevelTemp;
		}
		return riskLevelTemp = DicUtil.getTypeNameByCode("hiddenLevel", this.getRiskLevel());
	}

	@Transient
	public String getAccidentTypeTemp() {
		if (StringUtil.isNotEmpty(accidentTypeTemp)) {
			return  accidentTypeTemp;
		}
		StringBuffer s = new StringBuffer();
		if (StringUtil.isNotEmpty(this.getAccidentType())) {
			for (String type : this.getAccidentType().split(",")){
				if (StringUtil.isNotEmpty(s.toString())) {
					s.append(",");
				}
				s.append(DicUtil.getTypeNameByCode("accidentCate", type));
			}
		}
		return accidentTypeTemp = s.toString();
	}

	@Transient
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	@Transient
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

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
	 *@return: java.lang.String  风险名称
	 */
	@Column(name ="DANGER_NAME",nullable=true,length=800)
	public String getDangerName(){
		return this.dangerName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险名称
	 */
	public void setDangerName(String dangerName){
		this.dangerName = dangerName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  事故类型
	 */
	@Column(name ="ACCIDENT_TYPE",nullable=true,length=500)
	public String getAccidentType(){
		return this.accidentType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  事故类型
	 */
	public void setAccidentType(String accidentType){
		this.accidentType = accidentType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险类型
	 */
	@Column(name ="RISK_TYPE",nullable=true,length=200)
	public String getRiskType(){
		return this.riskType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险类型
	 */
	public void setRiskType(String riskType){
		this.riskType = riskType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险可能性
	 */
	@Column(name ="RISK_POSSIBILITY",nullable=true,length=20)
	public String getRiskPossibility(){
		return this.riskPossibility;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险可能性
	 */
	public void setRiskPossibility(String riskPossibility){
		this.riskPossibility = riskPossibility;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险等级
	 */
	@Column(name ="RISK_LEVEL",nullable=true,length=200)
	public String getRiskLevel(){
		return this.riskLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险等级
	 */
	public void setRiskLevel(String riskLevel){
		this.riskLevel = riskLevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险损失
	 */
	@Column(name ="RISK_LOSS",nullable=true,length=20)
	public String getRiskLoss(){
		return this.riskLoss;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险损失
	 */
	public void setRiskLoss(String riskLoss){
		this.riskLoss = riskLoss;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险后果描述
	 */
	@Column(name ="RISK_AFFECT_DESC",nullable=true,length=1000)
	public String getRiskAffectDesc(){
		return this.riskAffectDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险后果描述
	 */
	public void setRiskAffectDesc(String riskAffectDesc){
		this.riskAffectDesc = riskAffectDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险值
	 */
	@Column(name ="RISK_VALUE",nullable=true,length=10)
	public String getRiskValue(){
		return this.riskValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险值
	 */
	public void setRiskValue(String riskValue){
		this.riskValue = riskValue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任措施
	 */
	@Column(name ="RESP_MEASURES",nullable=true,length=1000)
	public String getRespMeasures(){
		return this.respMeasures;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任措施
	 */
	public void setRespMeasures(String respMeasures){
		this.respMeasures = respMeasures;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管理措施
	 */
	@Column(name ="MANG_MEASURES",nullable=true,length=1000)
	public String getMangMeasures(){
		return this.mangMeasures;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管理措施
	 */
	public void setMangMeasures(String mangMeasures){
		this.mangMeasures = mangMeasures;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管理标准
	 */
	@Column(name ="MANG_STANDARDS",nullable=true,length=1000)
	public String getMangStandards(){
		return this.mangStandards;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管理标准
	 */
	public void setMangStandards(String mangStandards){
		this.mangStandards = mangStandards;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  市场内部价
	 */
	@Column(name ="INTERNAL_MARKET_PRICE",nullable=true,length=20)
	public String getInternalMarketPrice(){
		return this.internalMarketPrice;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  市场内部价
	 */
	public void setInternalMarketPrice(String internalMarketPrice){
		this.internalMarketPrice = internalMarketPrice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标准依据
	 */
	@Column(name ="STANDARD_ACCORDANCE",nullable=true,length=1000)
	public String getStandardAccordance(){
		return this.standardAccordance;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标准依据
	 */
	public void setStandardAccordance(String standardAccordance){
		this.standardAccordance = standardAccordance;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  依据条目
	 */
	@Column(name ="BASED_ENTRY",nullable=true,length=500)
	public String getBasedEntry(){
		return this.basedEntry;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  依据条目
	 */
	public void setBasedEntry(String basedEntry){
		this.basedEntry = basedEntry;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专业类型
	 */
	@Column(name ="PROFESSIONALTYPE",nullable=true,length=32)
	public String getProfessionaltype(){
		return this.professionaltype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专业类型
	 */
	public void setProfessionaltype(String professionaltype){
		this.professionaltype = professionaltype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否是重大隐患
	 */
	@Column(name ="ISMAJOR",nullable=true,length=2)
	public String getIsmajor(){
		return this.ismajor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否是重大隐患
	 */
	public void setIsmajor(String ismajor){
		this.ismajor = ismajor;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  危险距离
	 */
	@Column(name ="DISTANCE",nullable=true,length=200)
	public String getDistance(){
		return this.distance;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危险距离
	 */
	public void setDistance(String distance){
		this.distance = distance;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  周边情况及相互影响因素
	 */
	@Column(name ="SURROUNDING",nullable=true,length=1000)
	public String getSurrounding(){
		return this.surrounding;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  周边情况及相互影响因素
	 */
	public void setSurrounding(String surrounding){
		this.surrounding = surrounding;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  监控措施
	 */
	@Column(name ="MONITOR",nullable=true,length=1000)
	public String getMonitor(){
		return this.monitor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  监控措施
	 */
	public void setMonitor(String monitor){
		this.monitor = monitor;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应急措施
	 */
	@Column(name ="EMERGENCY",nullable=true,length=1000)
	public String getEmergency(){
		return this.emergency;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应急措施
	 */
	public void setEmergency(String emergency){
		this.emergency = emergency;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任部门
	 */
	@Column(name ="RES_DEPART",nullable=true,length=50)
	public String getResDepart(){
		return this.resDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任部门
	 */
	public void setResDepart(String resDepart){
		this.resDepart = resDepart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备案号
	 */
	@Column(name ="CASE_NUM",nullable=true,length=50)
	public String getCaseNum(){
		return this.caseNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备案号
	 */
	public void setCaseNum(String caseNum){
		this.caseNum = caseNum;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  辨识时间
	 */
	@Column(name ="RECOGNIZE_TIME",nullable=true)
	public Date getRecognizeTime(){
		return this.recognizeTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  辨识时间
	 */
	public void setRecognizeTime(Date recognizeTime){
		this.recognizeTime = recognizeTime;
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
