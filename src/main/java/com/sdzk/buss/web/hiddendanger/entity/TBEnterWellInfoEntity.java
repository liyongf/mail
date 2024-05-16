package com.sdzk.buss.web.hiddendanger.entity;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 下井信息表
 * @author onlineGenerator
 * @date 2016-04-28 09:36:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_enter_well_info", schema = "")
@SuppressWarnings("serial")
public class TBEnterWellInfoEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**下井日期*/
	@Excel(name="下井日期",format = "yyyy-MM-dd")
	private Date wellDate;
	/**班次*/
	@Excel(name="班次")
	private String shift;
	/**下井性质*/
	@Excel(name="下井性质")
	private String wellNature;
	/**姓名*/
	@Excel(name="姓名")
	private TSUser wellMan;
	/**单位*/
	@Excel(name="单位")
	private TSDepart wellManUnit;
	/**职务*/
	@Excel(name="职务")
	private String post;
	/**下井地点*/
	@Excel(name="下井地点")
	private TBAddressInfoEntity wellAddress;
	/**是否读取考勤信息*/
	@Excel(name="是否读取考勤信息")
	private String isReadAttendance;
    private String isReadAttendanceTemp;
	/**下井开始时间*/
	@Excel(name="下井开始时间",format = "yyyy-MM-dd")
	private Date beginWellDate;
	/**下井结束时间*/
	@Excel(name="下井结束时间",format = "yyyy-MM-dd")
	private Date endWellDate;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private String createBy;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称")
	private String updateBy;
	/**更新日期*/
	@Excel(name="更新日期",format = "yyyy-MM-dd")
	private Date updateDate;
    //下井持续时间
	private String duration;
    private String wellNatureTemp;

    @Transient
    public String getIsReadAttendanceTemp() {
        return isReadAttendanceTemp;
    }

    public void setIsReadAttendanceTemp(String isReadAttendanceTemp) {
        this.isReadAttendanceTemp = isReadAttendanceTemp;
    }

    @Transient
    public String getWellNatureTemp() {
        return wellNatureTemp;
    }

    public void setWellNatureTemp(String wellNatureTemp) {
        this.wellNatureTemp = wellNatureTemp;
    }

    @Transient
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
	 *@return: java.util.Date  下井日期
	 */
	@Column(name ="WELL_DATE",nullable=true)
	public Date getWellDate(){
		return this.wellDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下井日期
	 */
	public void setWellDate(Date wellDate){
		this.wellDate = wellDate;
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
	 *@return: java.lang.String  下井性质
	 */
	@Column(name ="WELL_NATURE",nullable=true,length=50)
	public String getWellNature(){
		return this.wellNature;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下井性质
	 */
	public void setWellNature(String wellNature){
		this.wellNature = wellNature;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WELL_MAN")
    @NotFound(action= NotFoundAction.IGNORE)
	public TSUser getWellMan(){
		return this.wellMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setWellMan(TSUser wellMan){
		this.wellMan = wellMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WELL_MAN_UNIT")
    @NotFound(action= NotFoundAction.IGNORE)
	public TSDepart getWellManUnit(){
		return this.wellManUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setWellManUnit(TSDepart wellManUnit){
		this.wellManUnit = wellManUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职务
	 */
	@Column(name ="POST",nullable=true,length=50)
	public String getPost(){
		return this.post;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职务
	 */
	public void setPost(String post){
		this.post = post;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  下井地点
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WELL_ADDRESS")
    @NotFound(action= NotFoundAction.IGNORE)
	public TBAddressInfoEntity getWellAddress(){
		return this.wellAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下井地点
	 */
	public void setWellAddress(TBAddressInfoEntity wellAddress){
		this.wellAddress = wellAddress;
	}


    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否读取考勤信息
	 */
	@Column(name ="IS_READ_ATTENDANCE",nullable=true,length=50)
	public String getIsReadAttendance(){
		return this.isReadAttendance;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否读取考勤信息
	 */
	public void setIsReadAttendance(String isReadAttendance){
		this.isReadAttendance = isReadAttendance;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下井开始时间
	 */
	@Column(name ="BEGIN_WELL_DATE",nullable=true)
	public Date getBeginWellDate(){
		return this.beginWellDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下井开始时间
	 */
	public void setBeginWellDate(Date beginWellDate){
		this.beginWellDate = beginWellDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下井结束时间
	 */
	@Column(name ="END_WELL_DATE",nullable=true)
	public Date getEndWellDate(){
		return this.endWellDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下井结束时间
	 */
	public void setEndWellDate(Date endWellDate){
		this.endWellDate = endWellDate;
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
