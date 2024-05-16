package com.sdzk.buss.web.hiddendanger.entity;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.hiddendanger.service.impl.TBHiddenDangerExamServiceImpl;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 隐患检查
 * @author onlineGenerator
 * @date 2016-04-21 09:07:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_hidden_danger_exam", schema = "")
@SuppressWarnings("serial")
public class TBHiddenDangerExamEntity implements java.io.Serializable {
    TBHiddenDangerExamServiceImpl serviceimpl = new TBHiddenDangerExamServiceImpl();
	/**主键*/
	private String id;
	/**日期*/
	@Excel(name="日期")
	private Date examDate;
    private String examDateTemp;
	/**班次*/
	@Excel(name="班次")
	private String shift;
	/**地点*/
	private TBAddressInfoEntity address;
	@Excel(name="地点")
	private String addressName;
	/**填卡人*/
	private TSUser fillCardMan;

    private String fillCardManId;
	/**责任单位*/
	private TSDepart dutyUnit;
	@Excel(name="责任单位")
	private String dutyUnitName;

	/**责任人*/
	@Excel(name="责任人")
	private String dutyMan;

	/**督办单位**/
	@Excel(name="督办单位")
	private String superviseUnitId;

//	private List<TBHiddenDangerRelExamEntity> dutyManList;
	/**危险源ID*/
	private TBDangerSourceEntity dangerId;
	/**问题描述*/
	@Excel(name="问题描述")
	private String problemDesc;
	/**隐患类别*/
	@Excel(name="隐患类别")
	private String hiddenCategory;
	// 隐患等级
	@Excel(name="隐患等级")
	private String hiddenNature;

    private String hiddenNatureOriginal;
	/**下井时间开始*/
	@Excel(name="下井时间开始",format = "yyyy-MM-dd")
	private Date beginWellDate;
	/**下井时间结束*/
	@Excel(name="下井时间结束",format = "yyyy-MM-dd")
	private Date endWellDate;
	/**处理方式*/
	@Excel(name="处理方式")
	private String dealType;
	/**限期日期*/
	@Excel(name="限期日期")
	private Date limitDate;
    private String limitDateTemp;
	/**限期班次*/
	@Excel(name="限期班次")
	private String limitShift;
	/**复查人*/
	private TSUser reviewMan;
	/**检查类型*/
	@Excel(name="检查类型")
	private String examType;
    private String examTypeTemp;
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
    /**组别*/
	@Excel(name="组别")
    private String itemId;
    /**组员*/
	@Excel(name="组员")
    private String itemUserId;
    /**工作安排*/
    private String gzap;
    /**是否来源于井口信息办录入*/
    private String isFromJkxxb;
	// 隐患等级
    private String hiddenLevel;
    @Excel(name="扣分")
    private String deductScores;
	//上级检查部门
	@Excel(name="上级检查部门")
	private String sjjcDept;
	//上级检查-检查人
	@Excel(name="检查人")
	private String sjjcCheckMan;
    //备注
	@Excel(name="备注")
    private String remark;
    //延期申请关联
    private TBHiddenDangerApplyEntity applyEntity;
	//专业类型
	private String proType;
	//其他检查-检查类型
	private String checkType;

    //关键字
    private String keyWords;
	//上报人
	private String reportName;
	//上报时间
	private String reportDate;
	//上报状态
	private String reportStatus;
	//隐患类型
	//@Excel(name="隐患类型")
	private String hiddenType;

	//手机端ID
	private String mobileId;
	
	/**隐患来源(0集团，1本矿)*/
	private String origin;
	private String originTemp;

	//风险管控生成的隐患相关变量
	@Excel(name="信息来源")
	private String manageType;
	private String riskManageHazardFactorId;
	private String riskManagePostHazardFactorId;
	@Excel(name="隐患类型")
	private String riskType;
	private String manageTypeTemp;
	private String riskTypeTemp;

	//危害因素
	private String hazardFactorId;
	private String hazardFactorName;

	//风险
	private RiskIdentificationEntity riskId;
	private String riskDescTemp;

	private TBPostManageEntity post;

	/**管控责任单位*/
	private TSDepart manageDutyUnit;
	@Excel(name="管控责任单位")
	private String manageDutyUnitName;
	/**管控责任人Id*/
	private String manageDutyManId;
	/**管控责任人Id*/
	@Excel(name="管控责任人")
	private String manageDutyManTemp;
	/**责任人Id*/
	private String dutyManId;


	/**任务*/
	private String taskAllId;

	@Column(name="origin",nullable = true,length = 2)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Transient
	public String getOriginTemp() {
		return originTemp;
	}

	public void setOriginTemp(String originTemp) {
		this.originTemp = originTemp;
	}
	
	private String hiddenTypeTemp;

    private String fillCardManNames;

	@Column(name="supervise_unit",nullable = true)
	public String getSuperviseUnitId() {
		return superviseUnitId;
	}

	public void setSuperviseUnitId(String superviseUnitId) {
		this.superviseUnitId = superviseUnitId;
	}

    @Transient
    public String getFillCardManNames() {
        return fillCardManNames;
    }

    public void setFillCardManNames(String fillCardManNames) {
        this.fillCardManNames = fillCardManNames;
    }

    @Transient
	public String getDutyUnitName() {
		return dutyUnitName;
	}

	public void setDutyUnitName(String dutyUnitName) {
		this.dutyUnitName = dutyUnitName;
	}

	@Transient
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	@Transient
	public String getHiddenTypeTemp(){
		return this.hiddenTypeTemp;
	}

	public void setHiddenTypeTemp(String str){
		this.hiddenTypeTemp = str;
	}

	@Column(name="hidden_type",nullable = true,length = 50)
	public String getHiddenType(){
		return this.hiddenType;
	}

	public void setHiddenType(String hiddenType){
		this.hiddenType = hiddenType;
	}

	@Column(name ="report_name",nullable=true,length=50)
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@Column(name ="report_date",nullable=true)
	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	@Column(name ="report_status",nullable=true,length=50)
	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	/**
     *方法: 取得java.lang.String
     *@return: java.lang.String  关键字
     */
    @Column(name ="KEY_WORDS",nullable=true,length=50)
    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    private String proTypeTemp;
    private String shiftTemp;
    private String dealTypeTemp;
    private String limitShiftTemp;
    private String handleStatusTemp;
	private String hiddenCategoryTemp;
	private String hiddenNatureTemp;
	private String hiddenLevelTemp;
	private String rollBackRemarkTemp;


    private String sumFineMoneyTemp;
	private int hiddenOrderTemp;//日报表序号

	/*****整改和复查信息*****/
	//整改人
	private String modifyManTemp;
	//整改班次
	private String modifyShiftTemp;
	//整改日期
	private String modifyDateTemp;

    //整改措施
    private String modifyRemarkTemp;
	//复查人
	@Excel(name="复查人")
	private String reviewManTemp;
	//复查日期
	private String reviewDateTemp;
	//复查班次
	private String reviewShiftTemp;
	//复查结果
	private String reviewResultTemp;

    //复查结果
    private String reviewDetailTemp;
    private String riskLevelNameTemp;
    //是否分局挂牌督办
    private String isLsSub;
    //是否省局挂牌督办
    private String isLsProv;
    //销号日期
    private Date cancelDate;
    //前台显示的检查人
    private String showReviewManName;

    @Transient
    public String getReviewDetailTemp() {
        return reviewDetailTemp;
    }

    public void setReviewDetailTemp(String reviewDetailTemp) {
        this.reviewDetailTemp = reviewDetailTemp;
    }
    @Transient
    public String getModifyRemarkTemp() {
        return modifyRemarkTemp;
    }

    public void setModifyRemarkTemp(String modifyRemarkTemp) {
        this.modifyRemarkTemp = modifyRemarkTemp;
    }
    @Transient
    public String getShowReviewManName() {
        String names = "";
        if (Constants.HIDDENCHECK_EXAMTYPE_SHANGJI.equals(examType)) {
            return getSjjcCheckMan();
        }else{
            if(StringUtil.isNotEmpty(getFillCardManId())){
                String[] ids = getFillCardManId().split(",");
                System.out.println(ids.length);
                for(String id : ids){
                    System.out.println(id);
                    TSUser t = serviceimpl.getEntity(TSUser.class,id);
                    if(t != null){
                        if(names == ""){
                            names = names + t.getUserName();
                        }else{
                            names = names + ","+ t.getUserName();
                        }
                    }

                }
            }

        }
        return names;
    }

    public void setShowReviewManName(String showReviewManName) {
        this.showReviewManName = showReviewManName;
    }

    @Transient
	public String getProTypeTemp() {
		return proTypeTemp;
	}

	public void setProTypeTemp(String proTypeTemp) {
		this.proTypeTemp = proTypeTemp;
	}

	@Transient
	public int getHiddenOrderTemp() {
		return hiddenOrderTemp;
	}

	public void setHiddenOrderTemp(int hiddenOrderTemp) {
		this.hiddenOrderTemp = hiddenOrderTemp;
	}

	@Transient
	public String getSumFineMoneyTemp() {
		return sumFineMoneyTemp;
	}

	public void setSumFineMoneyTemp(String sumFineMoneyTemp) {
		this.sumFineMoneyTemp = sumFineMoneyTemp;
	}

    @Transient
    public String getLimitDateTemp() {
        return limitDateTemp;
    }

    public void setLimitDateTemp(String limitDateTemp) {
        this.limitDateTemp = limitDateTemp;
    }

    @Transient
    public String getRiskLevelNameTemp() {
        return riskLevelNameTemp;
    }

    public void setRiskLevelNameTemp(String riskLevelNameTemp) {
        this.riskLevelNameTemp = riskLevelNameTemp;
    }

    @Transient
    public String getExamDateTemp() {
        return examDateTemp;
    }

    public void setExamDateTemp(String examDateTemp) {
        this.examDateTemp = examDateTemp;
    }

    @Transient
	public String getReviewDateTemp() {
		return reviewDateTemp;
	}

	public void setReviewDateTemp(String reviewDateTemp) {
		this.reviewDateTemp = reviewDateTemp;
	}

	@Transient
	public String getReviewShiftTemp() {
		return reviewShiftTemp;
	}

	public void setReviewShiftTemp(String reviewShiftTemp) {
		this.reviewShiftTemp = reviewShiftTemp;
	}

	@Transient
	public String getModifyManTemp() {
		return modifyManTemp;
	}

	public void setModifyManTemp(String modifyManTemp) {
		this.modifyManTemp = modifyManTemp;
	}

	@Transient
	public String getModifyShiftTemp() {
		return modifyShiftTemp;
	}

	public void setModifyShiftTemp(String modifyShiftTemp) {
		this.modifyShiftTemp = modifyShiftTemp;
	}

	@Transient
	public String getModifyDateTemp() {
		return modifyDateTemp;
	}

	public void setModifyDateTemp(String modifyDateTemp) {
		this.modifyDateTemp = modifyDateTemp;
	}

	@Transient
	public String getReviewManTemp() {
		return reviewManTemp;
	}

	public void setReviewManTemp(String reviewManTemp) {
		this.reviewManTemp = reviewManTemp;
	}

	@Transient
	public String getReviewResultTemp() {
		return reviewResultTemp;
	}

	public void setReviewResultTemp(String reviewResultTemp) {
		this.reviewResultTemp = reviewResultTemp;
	}

	@Transient
	public String getRollBackRemarkTemp() {
		return rollBackRemarkTemp;
	}

	public void setRollBackRemarkTemp(String rollBackRemarkTemp) {
		this.rollBackRemarkTemp = rollBackRemarkTemp;
	}

    @Transient
    public String getExamTypeTemp() {
        return examTypeTemp;
    }

    public void setExamTypeTemp(String examTypeTemp) {
        this.examTypeTemp = examTypeTemp;
    }


    @Transient
	public String getHiddenLevelTemp() {
		return hiddenLevelTemp;
	}

	public void setHiddenLevelTemp(String hiddenLevelTemp) {
		this.hiddenLevelTemp = hiddenLevelTemp;
	}


	@Transient
	public String getHiddenNatureTemp() {
		return hiddenNatureTemp;
	}

	public void setHiddenNatureTemp(String hiddenNatureTemp) {
		this.hiddenNatureTemp = hiddenNatureTemp;
	}

	@Transient
	public String getHiddenCategoryTemp() {
		return hiddenCategoryTemp;
	}

	public void setHiddenCategoryTemp(String hiddenCategoryTemp) {
		this.hiddenCategoryTemp = hiddenCategoryTemp;
	}


    @Transient
    public String getHandleStatusTemp() {
        return handleStatusTemp;
    }

    public void setHandleStatusTemp(String handleStatusTemp) {
        this.handleStatusTemp = handleStatusTemp;
    }

    @Transient
    public String getLimitShiftTemp() {
        return limitShiftTemp;
    }

    public void setLimitShiftTemp(String limitShiftTemp) {
        this.limitShiftTemp = limitShiftTemp;
    }

    @Transient
    public String getDealTypeTemp() {
        return dealTypeTemp;
    }

    public void setDealTypeTemp(String dealTypeTemp) {
        this.dealTypeTemp = dealTypeTemp;
    }

    @Transient
    public String getShiftTemp() {
        return shiftTemp;
    }

    public void setShiftTemp(String shiftTemp) {
        this.shiftTemp = shiftTemp;
    }



    @Column(name ="hiddenLevel",nullable=true,length=50)
    public String getHiddenLevel() {
        return hiddenLevel;
    }

    public void setHiddenLevel(String hiddenLevel) {
        this.hiddenLevel = hiddenLevel;
    }


    @Column(name ="gzap",nullable=true,length=10)
    public String getGzap() {
        return gzap;
    }

    public void setGzap(String gzap) {
        this.gzap = gzap;
    }

    @Column(name ="itemId",nullable=true,length=500)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name ="itemUserId",nullable=true,length=500)
    public String getItemUserId() {
        return itemUserId;
    }

    public void setItemUserId(String itemUserId) {
        this.itemUserId = itemUserId;
    }

    /**更新日期*/
	private Date updateDate;
	private String dutyManTemp;
    private String itemUserNameTemp;
	private TBHiddenDangerHandleEntity handleEntity;
	private String isWithClass;
	@Column(name ="is_withclass",nullable=true,length=500)
	public String getIsWithClass() {
		return isWithClass;
	}

	public void setIsWithClass(String isWithClass) {
		this.isWithClass = isWithClass;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hiddenDanger")
	public TBHiddenDangerHandleEntity getHandleEntity() {
		return handleEntity;
	}

	public void setHandleEntity(TBHiddenDangerHandleEntity handleEntity) {
		this.handleEntity = handleEntity;
	}


    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "APPLY_DELAY",nullable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public TBHiddenDangerApplyEntity getApplyEntity() {
        return applyEntity;
    }

    public void setApplyEntity(TBHiddenDangerApplyEntity applyEntity) {
        this.applyEntity = applyEntity;
    }

    @Transient
	public String getDutyManTemp() {
		return dutyManTemp;
	}

    @Transient
    public String getItemUserNameTemp() {
        return itemUserNameTemp;
    }

    public void setItemUserNameTemp(String itemUserNameTemp) {
        this.itemUserNameTemp = itemUserNameTemp;
    }

    public void setDutyManTemp(String dutyManTemp) {
		this.dutyManTemp = dutyManTemp;
	}

	@Column(name ="DUTY_MAN",nullable=true,length=500)
	public String getDutyMan() {
		return dutyMan;
	}

	public void setDutyMan(String dutyMan) {
		this.dutyMan = dutyMan;
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
	 *@return: java.lang.String  日期
	 */
	@Column(name ="EXAM_DATE",nullable=true,length=36)
	public Date getExamDate(){
		return this.examDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  日期
	 */
	public void setExamDate(Date examDate){
		this.examDate = examDate;
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
	 *@return: java.lang.String  地点
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESS")
    @NotFound(action= NotFoundAction.IGNORE)
	public TBAddressInfoEntity getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地点
	 */
	public void setAddress(TBAddressInfoEntity address){
		this.address = address;
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
    @Column(name = "FILL_CARD_MANIDS")
    public String getFillCardManId() {
        return fillCardManId;
    }

    public void setFillCardManId(String fillCardManId) {
        this.fillCardManId = fillCardManId;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任单位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DUTY_UNIT")
    @NotFound(action= NotFoundAction.IGNORE)
	public TSDepart getDutyUnit(){
		return this.dutyUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任单位
	 */
	public void setDutyUnit(TSDepart dutyUnit){
		this.dutyUnit = dutyUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任人
	 */
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tBHiddenDangerExamEntity")
//    @NotFound(action= NotFoundAction.IGNORE)
//	public List<TBHiddenDangerRelExamEntity> getDutyManList() {
//		return dutyManList;
//	}
//
//	public void setDutyManList(List<TBHiddenDangerRelExamEntity> dutyManList) {
//		this.dutyManList = dutyManList;
//	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  危险源ID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DANGER_ID")
    @NotFound(action= NotFoundAction.IGNORE)
	public TBDangerSourceEntity getDangerId(){
		return this.dangerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危险源ID
	 */
	public void setDangerId(TBDangerSourceEntity dangerId){
		this.dangerId = dangerId;
	}

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  扣分
     */
    @Column(name ="DEDUCT_SCORES",nullable=true,length=50)
    public String getDeductScores(){
        return this.deductScores;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  扣分
     */
    public void setDeductScores(String deductScores){
        this.deductScores = deductScores;
    }

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  问题描述
	 */
	@Column(name ="PROBLEM_DESC",nullable=true,length=1000)
	public String getProblemDesc(){
		return this.problemDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问题描述
	 */
	public void setProblemDesc(String problemDesc){
		this.problemDesc = problemDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患类别
	 */
	@Column(name ="HIDDEN_CATEGORY",nullable=true,length=50)
	public String getHiddenCategory(){
		return this.hiddenCategory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患类别
	 */
	public void setHiddenCategory(String hiddenCategory){
		this.hiddenCategory = hiddenCategory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患性质
	 */
	@Column(name ="HIDDEN_NATURE",nullable=true,length=50)
	public String getHiddenNature(){
		return this.hiddenNature;
	}

    @Column(name ="hidden_nature_original",nullable=true,length=50)
    public String getHiddenNatureOriginal() {
        return hiddenNatureOriginal;
    }

    public void setHiddenNatureOriginal(String hiddenNatureOriginal) {
        this.hiddenNatureOriginal = hiddenNatureOriginal;
    }

    /**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患性质
	 */
	public void setHiddenNature(String hiddenNature){
		this.hiddenNature = hiddenNature;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下井时间开始
	 */
	@Column(name ="BEGIN_WELL_DATE",nullable=true)
	public Date getBeginWellDate(){
		return this.beginWellDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下井时间开始
	 */
	public void setBeginWellDate(Date beginWellDate){
		this.beginWellDate = beginWellDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下井时间结束
	 */
	@Column(name ="END_WELL_DATE",nullable=true)
	public Date getEndWellDate(){
		return this.endWellDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下井时间结束
	 */
	public void setEndWellDate(Date endWellDate){
		this.endWellDate = endWellDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理类型
	 */
	@Column(name ="DEAL_TYPE",nullable=true,length=50)
	public String getDealType(){
		return this.dealType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理类型
	 */
	public void setDealType(String dealType){
		this.dealType = dealType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  限期日期
	 */
	@Column(name ="LIMIT_DATE",nullable=true)
	public Date getLimitDate(){
		return this.limitDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  限期日期
	 */
	public void setLimitDate(Date limitDate){
		this.limitDate = limitDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  限期班次
	 */
	@Column(name ="LIMIT_SHIFT",nullable=true,length=50)
	public String getLimitShift(){
		return this.limitShift;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  限期班次
	 */
	public void setLimitShift(String limitShift){
		this.limitShift = limitShift;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复查人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REVIEW_MAN")
    @NotFound(action= NotFoundAction.IGNORE)
	public TSUser getReviewMan(){
		return this.reviewMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复查人
	 */
	public void setReviewMan(TSUser reviewMan){
		this.reviewMan = reviewMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  检查类型
	 */
	@Column(name ="EXAM_TYPE",nullable=true,length=50)
	public String getExamType(){
		return this.examType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  检查类型
	 */
	public void setExamType(String examType){
		this.examType = examType;
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

    @Column(name ="isFromJkxxb",nullable=true)
    public String getIsFromJkxxb() {
        return isFromJkxxb;
    }

    public void setIsFromJkxxb(String isFromJkxxb) {
        this.isFromJkxxb = isFromJkxxb;
    }

    /**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	@Column(name ="sjjc_dept",nullable=true,length=360)
	public String getSjjcDept() {
		return sjjcDept;
	}

	public void setSjjcDept(String sjjcDept) {
		this.sjjcDept = sjjcDept;
	}

	@Column(name ="sjjc_check_man",nullable=true,length=360)
	public String getSjjcCheckMan() {
		return sjjcCheckMan;
	}

	public void setSjjcCheckMan(String sjjcCheckMan) {
		this.sjjcCheckMan = sjjcCheckMan;
	}
    @Column(name ="remark",nullable=true,length=200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	@Column(name ="pro_type",nullable=true,length=360)
	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	@Column(name ="check_type",nullable=true,length=360)
	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

    @Column(name ="is_ls_sub",nullable=true,length=2)
    public String getIsLsSub() {
        return isLsSub;
    }

    public void setIsLsSub(String isLsSub) {
        this.isLsSub = isLsSub;
    }

    @Column(name ="is_ls_prov",nullable=true,length=2)
    public String getIsLsProv() {
        return isLsProv;
    }

    public void setIsLsProv(String isLsProv) {
        this.isLsProv = isLsProv;
    }

    @Column(name ="cancel_date",nullable=true)
    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

	@Column(name="mobile_id",nullable = true,length = 36)
	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}

	@Column(name="manage_type",nullable = true,length = 36)
	public String getManageType() {
		return manageType;
	}
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	@Column(name="risk_manage_hazard_factor_id",nullable = true,length = 36)
	public String getRiskManageHazardFactorId() {
		return riskManageHazardFactorId;
	}
	public void setRiskManageHazardFactorId(String riskManageHazardFactorId) {
		this.riskManageHazardFactorId = riskManageHazardFactorId;
	}

	@Column(name="risk_manage_post_hazard_factor_id",nullable = true,length = 36)
	public String getRiskManagePostHazardFactorId() {
		return riskManagePostHazardFactorId;
	}

	public void setRiskManagePostHazardFactorId(String riskManagePostHazardFactorId) {
		this.riskManagePostHazardFactorId = riskManagePostHazardFactorId;
	}

	@Column(name="risk_type",nullable = true,length = 36)
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	@Transient
	public String getManageTypeTemp(){
		return manageTypeTemp;
	}
	public void setManageTypeTemp(String manageTypeTemp){
		this.manageTypeTemp = manageTypeTemp;
	}

	@Transient
	public String getRiskTypeTemp(){
		return riskTypeTemp;
	}
	public void setRiskTypeTemp(String riskTypeTemp){
		this.riskTypeTemp = riskTypeTemp;
	}
	@Column(name="hazard_factor_id",nullable = true)
	public String getHazardFactorId() {
		return hazardFactorId;
	}

	public void setHazardFactorId(String hazardFactorId) {
		this.hazardFactorId = hazardFactorId;
	}
	@Column(name="hazard_factor_name",nullable = true)
	public String getHazardFactorName() {
		return hazardFactorName;
	}

	public void setHazardFactorName(String hazardFactorName) {
		this.hazardFactorName = hazardFactorName;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "risk_id")
	@NotFound(action= NotFoundAction.IGNORE)
	public RiskIdentificationEntity getRiskId() {
		return riskId;
	}

	public void setRiskId(RiskIdentificationEntity riskId) {
		this.riskId = riskId;
	}

	@Transient
	public String getRiskDescTemp() {
		return riskDescTemp;
	}

	public void setRiskDescTemp(String riskDescTemp) {
		this.riskDescTemp = riskDescTemp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	public TBPostManageEntity getPost() {
		return post;
	}

	public void setPost(TBPostManageEntity post) {
		this.post = post;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manage_duty_unit")
	@NotFound(action= NotFoundAction.IGNORE)
	public TSDepart getManageDutyUnit() {
		return manageDutyUnit;
	}

	public void setManageDutyUnit(TSDepart manageDutyUnit) {
		this.manageDutyUnit = manageDutyUnit;
	}
	@Column(name ="manage_duty_man_id",nullable=true,length=360)
	public String getManageDutyManId() {
		return manageDutyManId;
	}

	public void setManageDutyManId(String manageDutyManId) {
		this.manageDutyManId = manageDutyManId;
	}
	@Column(name ="duty_man_id",nullable=true,length=360)
	public String getDutyManId() {
		return dutyManId;
	}

	public void setDutyManId(String dutyManId) {
		this.dutyManId = dutyManId;
	}
	@Transient
	public String getManageDutyManTemp() {
		return manageDutyManTemp;
	}

	public void setManageDutyManTemp(String manageDutyManTemp) {
		this.manageDutyManTemp = manageDutyManTemp;
	}

	@Column(name ="task_all_id",nullable=true)
	public String getTaskAllId() {
		return taskAllId;
	}

	public void setTaskAllId(String taskAllId) {
		this.taskAllId = taskAllId;
	}
	@Transient
	public String getManageDutyUnitName() {
		return manageDutyUnitName;
	}

	public void setManageDutyUnitName(String manageDutyUnitName) {
		this.manageDutyUnitName = manageDutyUnitName;
	}

	private String hiddenNumber;
	@Column(name ="hidden_number",nullable=true)
	public String getHiddenNumber() {
		return hiddenNumber;
	}

	public void setHiddenNumber(String hiddenNumber) {
		this.hiddenNumber = hiddenNumber;
	}
}
