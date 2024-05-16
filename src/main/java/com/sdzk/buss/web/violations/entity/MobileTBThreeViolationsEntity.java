package com.sdzk.buss.web.violations.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

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
@SuppressWarnings("serial")
public class MobileTBThreeViolationsEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**违章时间*/
	private Date vioDate;
	/**班次*/
	private String shift;
	/**违章地点*/
	private String vioAddress;
	/**违章单位*/
	private String vioUnits;
	/**工种*/
	private String workType;
	/**违章分类*/
	private String vioCategory;
	/**违章定性*/
	private String vioQualitative;
	/**三违级别*/
	private String vioLevel;
	/**制止人*/
	private String stopPeople;
	/**查出单位*/
	private String findUnits;
	/**三违事实描述*/
	private String vioFactDesc;
	/**备注*/
	private String remark;
	/**违章人员*/
	private String vioPeople;
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

	/**手机端上报id**/
	private String mobileId;

	@Column(name ="mobile_id",nullable=true)
	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
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
}
