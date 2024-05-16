package com.sdzk.buss.web.address.entity;


import com.sdzk.buss.web.layer.entity.TBLayerEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: 地点列表
 * @author onlineGenerator
 * @date 2017-06-19 15:18:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_address_info", schema = "")
@SuppressWarnings("serial")
public class TBAddressInfoEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**地点名称*/
	@Excel(name="地点名称",width = 25,needMerge = true)
	private String address;
	/**经度*/
	private String lon;
	/**纬度*/
	private String lat;
	/**是否显示*/
	private String isshow;
	/**是否删除*/
	private String isDelete;
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
	/**类型**/
	private String cate;
	/**描述**/
	private String description;
	/**分管领导**/
	@Excel(name="分管领导",needMerge = true)
	private String manageMan;


	private String manageUnit;

	private String riskType;

	private String riskTypeTemp;

	/**多边形区域点集*/
	private String pointStr;
	/**所属煤层*/
	private TBLayerEntity belongLayer;
	/**风险个数**/
	@Excel(name="关联风险个数",width = 15,needMerge = true)
	private  String dangerSourceCount;

    /***责任关系实体*/
	@ExcelCollection(name = "责任人与责任单位")
	private List<TBAddressDepartRelEntity> departRelEntityList;

	/**风险等级*/
	private String riskLevel;
	/**预警颜色*/
	private String alertColor;
	private String isShowData;
	/**静态风险等级*/
	private String staticRiskLevel;
	/**静态预警颜色*/
	private String staticAlertColor;

	/**上报集团时间*/
	private Date reportGroupTime;
	/**上报集团用户*/
	private TSUser reportGroupMan;
	/**上报集团状态*/
	private String reportGroupStatus;

	/**排查日期*/
	private Date investigationDate;

	/**开始日期*/
	private Date startDate;

	/**结束日期*/
	private Date endDate;
	//隐患个数
	private  String hiddenCount;


	//序号
	private  String orderNumber;

	//备注
	private  String remark;

	private  String isAll;

	//国家局上报标识
	private  String stateFlag;
	//图形文件id
	private  Integer pictureCode;
	//上报时间
	private  Date uploadTime;


	@Column(name ="isShowData")
	public String getIsShowData() {
		return isShowData;
	}

	public void setIsShowData(String isShowData) {
		this.isShowData = isShowData;
	}
	@Transient
	public String getAlertColor(){
		return alertColor;
	}

	public void setAlertColor(String alertColor){
		this.alertColor = alertColor;
	}

	@Transient
	public String getRiskLevel(){
		return this.riskLevel;
	}

	public void setRiskLevel(String riskLevel){
		this.riskLevel = riskLevel;
	}

    @Column(name ="manage_man",length=20)
    public String getManageMan() {
        return manageMan;
    }

    public void setManageMan(String manageMan) {
        this.manageMan = manageMan;
    }

	@Column(name ="point_str",length=2000)
	public String getPointStr() {
		return pointStr;
	}

	public void setPointStr(String pointStr) {
		this.pointStr = pointStr;
	}

	/*@Column(name ="",length=100)*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "belong_layer")
	@NotFound(action = NotFoundAction.IGNORE)
	public TBLayerEntity getBelongLayer() {
		return belongLayer;
	}

	public void setBelongLayer(TBLayerEntity belongLayer) {
		this.belongLayer = belongLayer;
	}

	@Transient
	public String getDangerSourceCount() {
		return dangerSourceCount;
	}

	public void setDangerSourceCount(String dangerSourceCount) {
		this.dangerSourceCount = dangerSourceCount;
	}

	@Column(name ="cate",nullable=true,length=10)
	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	@Column(name ="description",nullable=true,length=500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		if (StringUtil.isEmpty(id)){
			id = UUIDGenerator.generate();
		}
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地点名称
	 */
	@Column(name ="ADDRESS",nullable=true,length=36)
	public String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地点名称
	 */
	public void setAddress(String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经度
	 */
	@Column(name ="LON",nullable=true,length=15)
	public String getLon(){
		return this.lon;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经度
	 */
	public void setLon(String lon){
		this.lon = lon;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  纬度
	 */
	@Column(name ="LAT",nullable=true,length=15)
	public String getLat(){
		return this.lat;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  纬度
	 */
	public void setLat(String lat){
		this.lat = lat;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否显示
	 */
	@Column(name ="ISSHOW",nullable=true,length=16)
	public String getIsshow(){
		return this.isshow;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否显示
	 */
	public void setIsshow(String isshow){
		this.isshow = isshow;
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

	@Transient
	public String getStaticRiskLevel() {
		return staticRiskLevel;
	}

	public void setStaticRiskLevel(String staticRiskLevel) {
		this.staticRiskLevel = staticRiskLevel;
	}

	@Transient
	public String getStaticAlertColor() {
		return staticAlertColor;
	}

	public void setStaticAlertColor(String staticAlertColor) {
		this.staticAlertColor = staticAlertColor;
	}

	@Transient
	public List<TBAddressDepartRelEntity> getDepartRelEntityList(){return this.departRelEntityList;}

	public void setDepartRelEntityList(List<TBAddressDepartRelEntity> departRelEntityList){this.departRelEntityList = departRelEntityList;}


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
	@Column(name ="investigation_date",nullable=true)
	public Date getInvestigationDate() {
		return investigationDate;
	}

	public void setInvestigationDate(Date investigationDate) {
		this.investigationDate = investigationDate;
	}
	@Column(name ="start_date",nullable=true)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Column(name ="end_date",nullable=true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Transient
	public String getHiddenCount() {
		return hiddenCount;
	}

	public void setHiddenCount(String hiddenCount) {
		this.hiddenCount = hiddenCount;
	}
	@Transient
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name ="remark",nullable=true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name ="manage_unit",nullable=true)
	public String getManageUnit() {
		return manageUnit;
	}

	public void setManageUnit(String manageUnit) {
		this.manageUnit = manageUnit;
	}
	@Column(name ="risk_type",nullable=true)
	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

    @Transient
	public String getRiskTypeTemp() {
		return riskTypeTemp;
	}

	public void setRiskTypeTemp(String riskTypeTemp) {
		this.riskTypeTemp = riskTypeTemp;
	}

    @Transient
	public String getIsAll() {
		return isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}

	@Column(name ="state_flag",nullable=true)
	public String getStateFlag() {
		return stateFlag;
	}

	public void setStateFlag(String stateFlag) {
		this.stateFlag = stateFlag;
	}

	@Column(name ="picture_code",nullable=true)
	public Integer getPictureCode() {
		return pictureCode;
	}

	public void setPictureCode(Integer pictureCode) {
		this.pictureCode = pictureCode;
	}

	@Column(name ="upload_time",nullable=true)
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}
