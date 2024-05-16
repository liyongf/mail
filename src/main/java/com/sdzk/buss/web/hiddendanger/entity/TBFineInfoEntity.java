package com.sdzk.buss.web.hiddendanger.entity;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
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
 * @Description: 罚款信息
 * @author onlineGenerator
 * @date 2016-03-31 13:12:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_fine_info", schema = "")
@SuppressWarnings("serial")
public class TBFineInfoEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**业务关联外键*/
	@Excel(name="业务关联外键")
	private String businessId;
	/**罚款单号*/
	@Excel(name="罚款单号")
	private String fineNo;
	/**罚款人*/
	@Excel(name="罚款人")
    private TSUser fineUser;
    private String fineMoneyCount;
	/**被罚人*/
	@Excel(name="被罚人")
    private TSUser finedUser;
    private TSDepart finedUnit;
	/**罚款金额*/
	@Excel(name="罚款金额")
	private String fineMoney;
	/**罚款性质*/
	@Excel(name="罚款性质")
	private String fineNature;
	/**扣分*/
	@Excel(name="扣分")
	private String deductScores;
	/**日期*/
	@Excel(name="日期",format = "yyyy-MM-dd")
	private Date fineDate;
	/**班次*/
	@Excel(name="班次")
	private String shift;
	/**填卡人*/
	@Excel(name="填卡人")
	private TSUser fillCardMan;
	/**罚款原因*/
	@Excel(name="罚款原因")
	private String fineReason;
	/**是否集体罚款*/
	@Excel(name="是否集体罚款")
	private String isGroupFine;
	/**罚款类型*/
	@Excel(name="罚款类型")
	private String fineType;
    private TBAddressInfoEntity fineAddress;
	/**所属煤矿*/
	@Excel(name="所属煤矿")
	private String belongMine;
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
    /**隐患标准*/
    @Excel(name="隐患标准")
    private TBDangerSourceEntity dangerId;
    /**隐患级别*/
    private String dangerLevel;

	private String shiftTemp;
	private String fineNatureTemp;
    private String fineTypeTemp;
    private String fineManTemp;
    private String finedManTemp;
	private String fineAddressTemp;
	private String dangerLevelTemp;

	@Transient
	public String getFineAddressTemp(){
		return fineAddressTemp;
	}

	public void setFineAddressTemp(String fineAddressTemp){
		this.fineAddressTemp = fineAddressTemp;
	}

	@Transient
	public String getDangerLevelTemp() {
		return dangerLevelTemp;
	}

	public void setDangerLevelTemp(String dangerLevelTemp) {
		this.dangerLevelTemp = dangerLevelTemp;
	}

    @Transient
    public String getFineManTemp() {
        return fineManTemp;
    }

    public void setFineManTemp(String fineManTemp) {
        this.fineManTemp = fineManTemp;
    }
    @Transient
    public String getFinedManTemp() {
        return finedManTemp;
    }

    public void setFinedManTemp(String finedManTemp) {
        this.finedManTemp = finedManTemp;
    }

    @Transient
    public String getFineMoneyCount() {
        return fineMoneyCount;
    }

    public void setFineMoneyCount(String fineMoneyCount) {
        this.fineMoneyCount = fineMoneyCount;
    }

    @Transient
    public String getFineTypeTemp() {
        return fineTypeTemp;
    }

    public void setFineTypeTemp(String fineTypeTemp) {
        this.fineTypeTemp = fineTypeTemp;
    }

    @Transient
    public String getFineNatureTemp() {
        return fineNatureTemp;
    }

    public void setFineNatureTemp(String fineNatureTemp) {
        this.fineNatureTemp = fineNatureTemp;
    }

    @Transient
    public String getShiftTemp() {
        return shiftTemp;
    }

    public void setShiftTemp(String shiftTemp) {
        this.shiftTemp = shiftTemp;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fine_address")
    public TBAddressInfoEntity getFineAddress() {
        return fineAddress;
    }

    public void setFineAddress(TBAddressInfoEntity fineAddress) {
        this.fineAddress = fineAddress;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务关联外键
	 */
	@Column(name ="BUSINESS_ID",nullable=true,length=36)
	public String getBusinessId(){
		return this.businessId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务关联外键
	 */
	public void setBusinessId(String businessId){
		this.businessId = businessId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  罚款单号
	 */
	@Column(name ="FINE_NO",nullable=true,length=20)
	public String getFineNo(){
		return this.fineNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  罚款单号
	 */
	public void setFineNo(String fineNo){
		this.fineNo = fineNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  罚款人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINE_MAN")
    @NotFound(action= NotFoundAction.IGNORE)
	public TSUser getFineUser(){
		return this.fineUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  罚款人
     */
	public void setFineUser(TSUser fineUser){
		this.fineUser = fineUser;
	}


    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  被罚人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINED_MAN")
    @NotFound(action= NotFoundAction.IGNORE)
	public TSUser getFinedUser(){
		return this.finedUser;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fined_unit")
    public TSDepart getFinedUnit() {
        return finedUnit;
    }

    public void setFinedUnit(TSDepart finedUnit) {
        this.finedUnit = finedUnit;
    }

    /**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  被罚人
     */
	public void setFinedUser(TSUser finedUser){
		this.finedUser = finedUser;
	}
    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  罚款金额
	 */
	@Column(name ="FINE_MONEY",nullable=true,length=20)
	public String getFineMoney(){
		return this.fineMoney;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  罚款金额
	 */
	public void setFineMoney(String fineMoney){
		this.fineMoney = fineMoney;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  罚款性质
	 */
	@Column(name ="FINE_NATURE",nullable=true,length=50)
	public String getFineNature(){
		return this.fineNature;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  罚款性质
	 */
	public void setFineNature(String fineNature){
		this.fineNature = fineNature;
	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  扣分
//	 */
//	@Column(name ="DEDUCT_SCORES",nullable=true,length=50)
//	public String getDeductScores(){
//		return this.deductScores;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  扣分
//	 */
//	public void setDeductScores(String deductScores){
//		this.deductScores = deductScores;
//	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  日期
	 */
	@Column(name ="FINE_DATE",nullable=true)
	public Date getFineDate(){
		return this.fineDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  日期
	 */
	public void setFineDate(Date fineDate){
		this.fineDate = fineDate;
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
	 *@return: java.lang.String  填卡人
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILL_CARD_MAN")
    @NotFound(action= NotFoundAction.IGNORE)
	public TSUser getFillCardMan(){
		return this.fillCardMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  填卡人
	 */
	public void setFillCardMan(TSUser fillCardMan){
		this.fillCardMan = fillCardMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  罚款原因
	 */
	@Column(name ="FINE_REASON",nullable=true,length=1000)
	public String getFineReason(){
		return this.fineReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  罚款原因
	 */
	public void setFineReason(String fineReason){
		this.fineReason = fineReason;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否集体罚款
	 */
	@Column(name ="IS_GROUP_FINE",nullable=true,length=50)
	public String getIsGroupFine(){
		return this.isGroupFine;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否集体罚款
	 */
	public void setIsGroupFine(String isGroupFine){
		this.isGroupFine = isGroupFine;
	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  罚款类型
//	 */
//	@Column(name ="FINE_TYPE",nullable=true,length=50)
//	public String getFineType(){
//		return this.fineType;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  罚款类型
//	 */
//	public void setFineType(String fineType){
//		this.fineType = fineType;
//	}

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

    @Column(name ="DANGER_LEVEL",nullable=true,length=50)
    public String getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DANGER_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    public TBDangerSourceEntity getDangerId() {
        return dangerId;
    }

    public void setDangerId(TBDangerSourceEntity dangerId) {
        this.dangerId = dangerId;
    }
}
