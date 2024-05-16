package com.sdzk.buss.web.violations.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import com.sdzk.buss.web.common.Constants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**   
 * @Title: Entity
 * @Description: 三违信息
 * @author onlineGenerator
 * @date 2017-06-17 17:27:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_three_violations", schema = "")
@Where(clause = "VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+")")
@SuppressWarnings("serial")
public class TBThreeViolationsEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**违章时间*/
	@Excel(name="违章时间")
	private Date vioDate;
	private String vioDateTemp;
	/**班次*/
	@Excel(name="班次")
	private String shift;
	/**违章地点*/
	@Excel(name="违章地点")
	private String vioAddress;
	/**违章单位*/
	@Excel(name="违章单位")
	private String vioUnits;
	/**工种*/
	@Excel(name="工种")
	private String workType;
	/**违章分类*/
	@Excel(name="违章分类")
	private String vioCategory;
	/**违章定性*/
	@Excel(name="违章定性")
	private String vioQualitative;
	/**三违级别*/
	@Excel(name="三违级别")
	private String vioLevel;
	/**制止人*/
	@Excel(name="制止人")
	private String stopPeople;
	/**查出单位*/
	@Excel(name="查处单位")
	private String findUnits;
	/**三违事实描述*/
	@Excel(name="三违事实描述")
	private String vioFactDesc;
	/**备注*/
	@Excel(name="备注")
	private String remark;
	/**违章人员*/
	@Excel(name="违章人员")
	private String vioPeople;
	/**职工编号*/
	@Excel(name="职工编号")
	private String employeeNum;

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

    private String reportStatus;

	/**上报集团时间*/
	private Date reportGroupTime;
	/**上报集团用户*/
	private TSUser reportGroupMan;
	/**上报集团状态*/
	private String reportGroupStatus;

	private String shiftTemp;
	private String findUnitsTemp;
	private String addressTemp;
	private String vioUnitesNameTemp;
	private String vioPeopleTemp;
	private String employeeNumTemp;
	private String vioCategoryTemp;
	private String vioQualitativeTemp;
	private String stopPeopleTemp;
    private String vioLevelTemp;
	@Excel(name="罚款金额")
    private String fineMoneyTemp;
	@Excel(name="是否存在罚款")
	private String isFineTemp;
	@Excel(name="罚款性质")
	private String finePropertyTemp;
    @Transient
    public String getFineMoneyTemp() {
        return fineMoneyTemp;
    }

    public void setFineMoneyTemp(String fineMoneyTemp) {
        this.fineMoneyTemp = fineMoneyTemp;
    }

    @Transient
    public String getVioLevelTemp() {
        return vioLevelTemp;
    }

    public void setVioLevelTemp(String vioLevelTemp) {
        this.vioLevelTemp = vioLevelTemp;
    }
	@Transient
	public String getShiftTemp() {
		return shiftTemp;
	}

	public void setShiftTemp(String shiftTemp) {
		this.shiftTemp = shiftTemp;
	}

	@Transient
	public String getFindUnitsTemp() {
		return findUnitsTemp;
	}

	public void setFindUnitsTemp(String findUnitsTemp) {
		this.findUnitsTemp = findUnitsTemp;
	}

	@Transient
	public String getAddressTemp() {
		return addressTemp;
	}

	public void setAddressTemp(String addressTemp) {
		this.addressTemp = addressTemp;
	}

	@Transient
	public String getVioUnitesNameTemp() {
		return vioUnitesNameTemp;
	}

	public void setVioUnitesNameTemp(String vioUnitesNameTemp) {
		this.vioUnitesNameTemp = vioUnitesNameTemp;
	}

	@Transient
	public String getVioPeopleTemp() {
		return vioPeopleTemp;
	}

	public void setVioPeopleTemp(String vioPeopleTemp) {
		this.vioPeopleTemp = vioPeopleTemp;
	}

	@Transient
	public String getVioCategoryTemp() {
		return vioCategoryTemp;
	}

	public void setVioCategoryTemp(String vioCategoryTemp) {
		this.vioCategoryTemp = vioCategoryTemp;
	}

	@Transient
	public String getVioQualitativeTemp() {
		return vioQualitativeTemp;
	}

	public void setVioQualitativeTemp(String vioQualitativeTemp) {
		this.vioQualitativeTemp = vioQualitativeTemp;
	}

	@Transient
	public String getStopPeopleTemp() {
		return stopPeopleTemp;
	}

	public void setStopPeopleTemp(String stopPeopleTemp) {
		this.stopPeopleTemp = stopPeopleTemp;
	}

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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  违章时间
	 */
	@Column(name ="VIO_DATE",nullable=true)
	public Date getVioDate(){
		return this.vioDate;
	}

    @Column(name ="report_status",nullable=true)
    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    /**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  违章时间
	 */
	public void setVioDate(Date vioDate){
		this.vioDate = vioDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  班次
	 */
	@Column(name ="SHIFT",nullable=true,length=50)
	public String getShift(){
		return this.shift;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  班次
	 */
	public void setShift(String shift){
		this.shift = shift;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  违章地点
	 */
	@Column(name ="VIO_ADDRESS",nullable=true,length=100)
	public String getVioAddress(){
		return this.vioAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  违章地点
	 */
	public void setVioAddress(String vioAddress){
		this.vioAddress = vioAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  违章单位
	 */
	@Column(name ="VIO_UNITS",nullable=true,length=36)
	public String getVioUnits(){
		return this.vioUnits;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  违章单位
	 */
	public void setVioUnits(String vioUnits){
		this.vioUnits = vioUnits;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工种
	 */
	@Column(name ="WORK_TYPE",nullable=true,length=50)
	public String getWorkType(){
		return this.workType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工种
	 */
	public void setWorkType(String workType){
		this.workType = workType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  违章分类
	 */
	@Column(name ="VIO_CATEGORY",nullable=true,length=50)
	public String getVioCategory(){
		return this.vioCategory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  违章分类
	 */
	public void setVioCategory(String vioCategory){
		this.vioCategory = vioCategory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  违章定性
	 */
	@Column(name ="VIO_QUALITATIVE",nullable=true,length=50)
	public String getVioQualitative(){
		return this.vioQualitative;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  违章定性
	 */
	public void setVioQualitative(String vioQualitative){
		this.vioQualitative = vioQualitative;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三违级别
	 */
	@Column(name ="VIO_LEVEL",nullable=true,length=50)
	public String getVioLevel(){
		return this.vioLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三违级别
	 */
	public void setVioLevel(String vioLevel){
		this.vioLevel = vioLevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制止人
	 */
	@Column(name ="STOP_PEOPLE",nullable=true,length=1000)
	public String getStopPeople(){
		return this.stopPeople;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制止人
	 */
	public void setStopPeople(String stopPeople){
		this.stopPeople = stopPeople;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  查出单位
	 */
	@Column(name ="FIND_UNITS",nullable=true,length=36)
	public String getFindUnits(){
		return this.findUnits;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  查出单位
	 */
	public void setFindUnits(String findUnits){
		this.findUnits = findUnits;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三违事实描述
	 */
	@Column(name ="VIO_FACT_DESC",nullable=true,length=2000)
	public String getVioFactDesc(){
		return this.vioFactDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三违事实描述
	 */
	public void setVioFactDesc(String vioFactDesc){
		this.vioFactDesc = vioFactDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  违章人员
	 */
	@Column(name ="VIO_PEOPLE",nullable=true,length=1000)
	public String getVioPeople(){
		return this.vioPeople;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  违章人员
	 */
	public void setVioPeople(String vioPeople){
		this.vioPeople = vioPeople;
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

	@Column(name ="report_group_time",nullable=true)
	public Date getReportGroupTime() {
		return reportGroupTime;
	}

	public void setReportGroupTime(Date reportGroupTime) {
		this.reportGroupTime = reportGroupTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_group_man")
	@NotFound(action= NotFoundAction.IGNORE)
	public TSUser getReportGroupMan() {
		return reportGroupMan;
	}

	public void setReportGroupMan(TSUser reportGroupMan) {
		this.reportGroupMan = reportGroupMan;
	}

	@Column(name ="report_group_status",nullable=true,length=4)
	public String getReportGroupStatus() {
		return reportGroupStatus;
	}

	public void setReportGroupStatus(String reportGroupStatus) {
		this.reportGroupStatus = reportGroupStatus;
	}
	@Transient
	public String getIsFineTemp() {
		return isFineTemp;
	}

	public void setIsFineTemp(String isFineTemp) {
		this.isFineTemp = isFineTemp;
	}
	@Transient
	public String getFinePropertyTemp() {
		return finePropertyTemp;
	}

	public void setFinePropertyTemp(String finePropertyTemp) {
		this.finePropertyTemp = finePropertyTemp;
	}

	@Column(name ="employee_num",nullable=true)
	public String getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}
	@Transient
	public String getEmployeeNumTemp() {
		return employeeNumTemp;
	}

	public void setEmployeeNumTemp(String employeeNumTemp) {
		this.employeeNumTemp = employeeNumTemp;
	}
	@Transient
	public String getVioDateTemp() {
		return vioDateTemp;
	}

	public void setVioDateTemp(String vioDateTemp) {
		this.vioDateTemp = vioDateTemp;
	}
}
