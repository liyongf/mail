package com.sdzk.buss.web.majorhiddendanger.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**   
 * @Title: Entity
 * @Description: 重大隐患
 * @author onlineGenerator
 * @date 2017-06-22 10:21:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_major_hidden_danger", schema = "")
@SuppressWarnings("serial")
public class TBMajorHiddenDangerEntity implements java.io.Serializable {
	/**唯一标识*/
	private String id;
	/**隐患地点*/
	@Excel(name="隐患地点")
	private String hdLocation;
	/**排查日期*/
	@Excel(name="排查日期",format = "yyyy-MM-dd")
	private Date inveDate;
	/**隐患信息来源*/
	@Excel(name="隐患信息来源")
	private String hdInfoSource;
	/**隐患等级*/
	@Excel(name="隐患等级")
	private String hdLevel;
	/**隐患类别*/
	@Excel(name="隐患类别")
	private String hdCate;
	/**隐患专业*/
	@Excel(name="隐患专业")
	private String hdMajor;
	/**隐患描述内容*/
	@Excel(name="隐患描述内容")
	private String hdDesc;
	/**核查日期*/
	@Excel(name="核查日期",format = "yyyy-MM-dd")
	private Date verifyDate;
	/**核查单位*/
	@Excel(name="核查单位")
	private String verifyUnit;
	/**核查情况*/
	@Excel(name="核查情况")
	private String verifyStatus;
	/**核查人员*/
	@Excel(name="核查人员")
	private String verifyMan;
	/**整改单位*/
	@Excel(name="整改单位")
	private String rectUnit;
	/**整改责任人*/
	@Excel(name="整改责任人")
	private String rectMan;
	/**整改时限*/
	@Excel(name="整改时限",format = "yyyy-MM-dd")
	private Date rectPeriod;
	/**整改措施*/
	@Excel(name="整改措施")
	private String rectMeasures;
	/**整改完成日期*/
	@Excel(name="整改完成日期",format = "yyyy-MM-dd")
	private Date rectTagartDt;
	/**验收人*/
	@Excel(name="验收人")
	private String acceptor;
	/**验收时间*/
	@Excel(name="验收时间",format = "yyyy-MM-dd")
	private Date accepTime;
	/**验收情况*/
	@Excel(name="验收情况")
	private String accepReport;
	/**复查人*/
	@Excel(name="复查人")
	private String reviewer;
	/**复查时间*/
	@Excel(name="复查时间",format = "yyyy-MM-dd")
	private Date reviewTime;
	/**复查情况*/
	@Excel(name="复查情况")
	private String reviewReport;
	/**是否分局挂牌督办*/
	@Excel(name="是否分局挂牌督办")
	private String isLsSub;
	/**是否省局挂牌督办*/
	@Excel(name="是否省局挂牌督办")
	private String isLsProv;
	/**闭环管理状态*/
	@Excel(name="闭环管理状态")
	private String clStatus;
	/**销号日期*/
	@Excel(name="销号日期",format = "yyyy-MM-dd")
	private Date cancelDate;
	/**整改目标到位情况*/
	@Excel(name="整改目标到位情况")
	private String goalAch;
	/**整改目标到位日期*/
	@Excel(name="整改目标到位日期",format = "yyyy-MM-dd")
	private Date goalAchDate;
	/**整改目标是否到位*/
	@Excel(name="整改目标是否到位")
	private String isGoalAchieve;
	/**整改责任到位说明*/
	@Excel(name="整改责任到位说明")
	private String respAch;
	/**整改责任到位日期*/
	@Excel(name="整改责任到位日期",format = "yyyy-MM-dd")
	private Date respAchDate;
	/**整改责任是否到位*/
	@Excel(name="整改责任是否到位")
	private String isRespAchieve;
	/**治理措施到位情况*/
	@Excel(name="治理措施到位情况")
	private String measureAch;
	/**治理措施到位日期*/
	@Excel(name="治理措施到位日期",format = "yyyy-MM-dd")
	private Date measureAchDate;
	/**治理措施是否到位*/
	@Excel(name="治理措施是否到位")
	private String isMeasureAchieve;
	/**整改资金到位情况*/
	@Excel(name="整改资金到位情况")
	private String fundAch;
	/**整改资金到位日期*/
	@Excel(name="整改资金到位日期",format = "yyyy-MM-dd")
	private Date fundAchDate;
	/**整改资金是否到位*/
	@Excel(name="整改资金是否到位")
	private String isFundAchieve;
	/**整改预案到位情况*/
	@Excel(name="整改预案到位情况")
	private String planAch;
	/**整改预案到位日期*/
	@Excel(name="整改预案到位日期",format = "yyyy-MM-dd")
	private Date planAchDate;
	/**整改预案是否到位*/
	@Excel(name="整改预案是否到位")
	private String isPlanAchieve;
	/**纳入治理计划情况*/
	@Excel(name="纳入治理计划情况")
	private String govePlan;
	/**纳入治理计划日期*/
	@Excel(name="纳入治理计划日期",format = "yyyy-MM-dd")
	private Date govePlanDate;
	/**纳入治理计划情况是否到位*/
	@Excel(name="纳入治理计划情况是否到位")
	private String isGovePlanAchieve;
	/**创建人登录名*/
	private String createBy;
	/**创建人名称*/
	private String createName;
	/**创建时间*/
	private Date createDate;
	/**更新人登陆名*/
	private String updateBy;
	/**更新人名称*/
	private String updateName;
	/**更新时间*/
	private Date updateDate;
    /**上报煤监局状态*/
    private String reportStatus;
    /**上报煤监局时间*/
    private Date reportDate;
	/**责任单位*/
	@Excel(name="责任单位")
	private TSDepart dutyUnit;
	/**责任人*/
	@Excel(name="责任人")
	private String dutyMan;
    /**关键字*/
    private String keyWords;
    /**危险源ID*/
    private TBDangerSourceEntity dangerId;
	//隐患类型
	private String hiddenType;

	/**上报集团时间*/
	private Date reportGroupTime;
	/**上报集团用户*/
	private TSUser reportGroupMan;
	/**上报集团状态*/
	private String reportGroupStatus;

	private String hdInfoSourceTemp;
	private String hdCateTemp;
	private String hdLevelTemp;
	private String hdMajorTemp;
	private String hiddenTypeTemp;

	@Transient
	public String getHdInfoSourceTemp(){
		return  this.hdInfoSourceTemp;
	}

	public  void setHdInfoSourceTemp(String hdInfoSourceTemp){
		this.hdInfoSourceTemp = hdInfoSourceTemp;
	}

	@Transient
	public  String getHdCateTemp(){
		return this.hdCateTemp;
	}

	public void  setHdCateTemp(String hdCateTemp){
		this.hdCateTemp = hdCateTemp;
	}


	@Transient
	public String getHdLevelTemp(){
		return this.hdLevelTemp;
	}

	public  void setHdLevelTemp(String hdLevelTemp){
		this.hdLevelTemp = hdLevelTemp;
	}

	@Transient
	public String getHdMajorTemp(){
		return this.hdMajorTemp;
	}

	public void setHdMajorTemp(String str){
		this.hdMajorTemp = str;
	}

	@Transient
	public String getHiddenTypeTemp(){
		return  this.hiddenTypeTemp;
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

    @Column(name ="key_words",nullable=true,length=50)
    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "danger_id")
    @NotFound(action= NotFoundAction.IGNORE)
    public TBDangerSourceEntity getDangerId() {
        return dangerId;
    }

    public void setDangerId(TBDangerSourceEntity dangerId) {
        this.dangerId = dangerId;
    }

    private String hdLocationName;
    public void setHdLocationName(String hdLocationName){
        this.hdLocationName = hdLocationName;
    }
    @Transient
    public String getHdLocationName(){
        return this.hdLocationName;
    }

    private String dutyUnitName;
    public void setDutyUnitName(String dutyUnitName){this.dutyUnitName = dutyUnitName;}
    @Transient
    public String getDutyUnitName(){return this.dutyUnitName;}

    private String dutyManName;
    public void setDutyManName(String dutyManName){this.dutyManName = dutyManName;}
    @Transient
    public String getDutyManName(){return this.dutyManName;}

    private String rectManName;
    public void setRectManName(String rectManName){
        this.rectManName=rectManName;
    }
    @Transient
    public String getRectManName(){return this.rectManName;}

    private String rectUnitName;
    public void setRectUnitName(String rectUnitName){this.rectUnitName=rectUnitName;}
    @Transient
    public String getRectUnitName(){return this.rectUnitName;}

    private String acceptorName;
    public void setAcceptorName(String acceptorName){this.acceptorName=acceptorName;}
    @Transient
    public String getAcceptorName(){return this.acceptorName;}

    private String reviewerName;
    public void setReviewerName(String reviewerName){this.reviewerName=reviewerName;}
    @Transient
    public String getReviewerName(){return this.reviewerName;}

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

    @Column(name ="DUTY_MAN",nullable=true,length=32)
	public String getDutyMan() {
		return dutyMan;
	}

	public void setDutyMan(String dutyMan) {
		this.dutyMan = dutyMan;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  唯一标识
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
	 *@param: java.lang.String  唯一标识
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患地点
	 */
	@Column(name ="HD_LOCATION",nullable=true,length=200)
	public String getHdLocation(){
		return this.hdLocation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患地点
	 */
	public void setHdLocation(String hdLocation){
		this.hdLocation = hdLocation;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  排查日期
	 */
	@Column(name ="INVE_DATE",nullable=false)
	public Date getInveDate(){
		return this.inveDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  排查日期
	 */
	public void setInveDate(Date inveDate){
		this.inveDate = inveDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患信息来源
	 */
	@Column(name ="HD_INFO_SOURCE",nullable=true,length=50)
	public String getHdInfoSource(){
		return this.hdInfoSource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患信息来源
	 */
	public void setHdInfoSource(String hdInfoSource){
		this.hdInfoSource = hdInfoSource;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患等级
	 */
	@Column(name ="HD_LEVEL",nullable=true,length=40)
	public String getHdLevel(){
		return this.hdLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患等级
	 */
	public void setHdLevel(String hdLevel){
		this.hdLevel = hdLevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患类别
	 */
	@Column(name ="HD_CATE",nullable=true,length=40)
	public String getHdCate(){
		return this.hdCate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患类别
	 */
	public void setHdCate(String hdCate){
		this.hdCate = hdCate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患专业
	 */
	@Column(name ="HD_MAJOR",nullable=true,length=40)
	public String getHdMajor(){
		return this.hdMajor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患专业
	 */
	public void setHdMajor(String hdMajor){
		this.hdMajor = hdMajor;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隐患描述内容
	 */
	@Column(name ="HD_DESC",nullable=true,length=2000)
	public String getHdDesc(){
		return this.hdDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患描述内容
	 */
	public void setHdDesc(String hdDesc){
		this.hdDesc = hdDesc;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  核查日期
	 */
	@Column(name ="VERIFY_DATE",nullable=false)
	public Date getVerifyDate(){
		return this.verifyDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  核查日期
	 */
	public void setVerifyDate(Date verifyDate){
		this.verifyDate = verifyDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  核查单位
	 */
	@Column(name ="VERIFY_UNIT",nullable=true,length=100)
	public String getVerifyUnit(){
		return this.verifyUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  核查单位
	 */
	public void setVerifyUnit(String verifyUnit){
		this.verifyUnit = verifyUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  核查情况
	 */
	@Column(name ="VERIFY_STATUS",nullable=true,length=2000)
	public String getVerifyStatus(){
		return this.verifyStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  核查情况
	 */
	public void setVerifyStatus(String verifyStatus){
		this.verifyStatus = verifyStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  核查人员
	 */
	@Column(name ="VERIFY_MAN",nullable=true,length=20)
	public String getVerifyMan(){
		return this.verifyMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  核查人员
	 */
	public void setVerifyMan(String verifyMan){
		this.verifyMan = verifyMan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改单位
	 */
	@Column(name ="RECT_UNIT",nullable=true,length=100)
	public String getRectUnit(){
		return this.rectUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改单位
	 */
	public void setRectUnit(String rectUnit){
		this.rectUnit = rectUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改责任人
	 */
	@Column(name ="RECT_MAN",nullable=true,length=20)
	public String getRectMan(){
		return this.rectMan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改责任人
	 */
	public void setRectMan(String rectMan){
		this.rectMan = rectMan;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  整改时限
	 */
	@Column(name ="RECT_PERIOD",nullable=false)
	public Date getRectPeriod(){
		return this.rectPeriod;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  整改时限
	 */
	public void setRectPeriod(Date rectPeriod){
		this.rectPeriod = rectPeriod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改措施
	 */
	@Column(name ="RECT_MEASURES",nullable=true,length=2000)
	public String getRectMeasures(){
		return this.rectMeasures;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改措施
	 */
	public void setRectMeasures(String rectMeasures){
		this.rectMeasures = rectMeasures;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  整改完成日期
	 */
	@Column(name ="RECT_TAGART_DT",nullable=false)
	public Date getRectTagartDt(){
		return this.rectTagartDt;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  整改完成日期
	 */
	public void setRectTagartDt(Date rectTagartDt){
		this.rectTagartDt = rectTagartDt;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  验收人
	 */
	@Column(name ="ACCEPTOR",nullable=true,length=20)
	public String getAcceptor(){
		return this.acceptor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  验收人
	 */
	public void setAcceptor(String acceptor){
		this.acceptor = acceptor;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  验收时间
	 */
	@Column(name ="ACCEP_TIME",nullable=false)
	public Date getAccepTime(){
		return this.accepTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  验收时间
	 */
	public void setAccepTime(Date accepTime){
		this.accepTime = accepTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  验收情况
	 */
	@Column(name ="ACCEP_REPORT",nullable=true,length=2000)
	public String getAccepReport(){
		return this.accepReport;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  验收情况
	 */
	public void setAccepReport(String accepReport){
		this.accepReport = accepReport;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复查人
	 */
	@Column(name ="REVIEWER",nullable=true,length=20)
	public String getReviewer(){
		return this.reviewer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复查人
	 */
	public void setReviewer(String reviewer){
		this.reviewer = reviewer;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  复查时间
	 */
	@Column(name ="REVIEW_TIME",nullable=false)
	public Date getReviewTime(){
		return this.reviewTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  复查时间
	 */
	public void setReviewTime(Date reviewTime){
		this.reviewTime = reviewTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复查情况
	 */
	@Column(name ="REVIEW_REPORT",nullable=true,length=2000)
	public String getReviewReport(){
		return this.reviewReport;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复查情况
	 */
	public void setReviewReport(String reviewReport){
		this.reviewReport = reviewReport;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否分局挂牌督办
	 */
	@Column(name ="IS_LS_SUB",nullable=true,length=2)
	public String getIsLsSub(){
		return this.isLsSub;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否分局挂牌督办
	 */
	public void setIsLsSub(String isLsSub){
		this.isLsSub = isLsSub;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否省局挂牌督办
	 */
	@Column(name ="IS_LS_PROV",nullable=true,length=2)
	public String getIsLsProv(){
		return this.isLsProv;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否省局挂牌督办
	 */
	public void setIsLsProv(String isLsProv){
		this.isLsProv = isLsProv;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  闭环管理状态
	 */
	@Column(name ="CL_STATUS",nullable=true,length=20)
	public String getClStatus(){
		return this.clStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  闭环管理状态
	 */
	public void setClStatus(String clStatus){
		this.clStatus = clStatus;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  销号日期
	 */
	@Column(name ="CANCEL_DATE",nullable=false)
	public Date getCancelDate(){
		return this.cancelDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  销号日期
	 */
	public void setCancelDate(Date cancelDate){
		this.cancelDate = cancelDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改目标到位情况
	 */
	@Column(name ="GOAL_ACH",nullable=true,length=500)
	public String getGoalAch(){
		return this.goalAch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改目标到位情况
	 */
	public void setGoalAch(String goalAch){
		this.goalAch = goalAch;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  整改目标到位日期
	 */
	@Column(name ="GOAL_ACH_DATE",nullable=false)
	public Date getGoalAchDate(){
		return this.goalAchDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  整改目标到位日期
	 */
	public void setGoalAchDate(Date goalAchDate){
		this.goalAchDate = goalAchDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改目标是否到位
	 */
	@Column(name ="IS_GOAL_ACHIEVE",nullable=true,length=2)
	public String getIsGoalAchieve(){
		return this.isGoalAchieve;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改目标是否到位
	 */
	public void setIsGoalAchieve(String isGoalAchieve){
		this.isGoalAchieve = isGoalAchieve;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改责任到位说明
	 */
	@Column(name ="RESP_ACH",nullable=true,length=500)
	public String getRespAch(){
		return this.respAch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改责任到位说明
	 */
	public void setRespAch(String respAch){
		this.respAch = respAch;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  整改责任到位日期
	 */
	@Column(name ="RESP_ACH_DATE",nullable=false)
	public Date getRespAchDate(){
		return this.respAchDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  整改责任到位日期
	 */
	public void setRespAchDate(Date respAchDate){
		this.respAchDate = respAchDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改责任是否到位
	 */
	@Column(name ="IS_RESP_ACHIEVE",nullable=true,length=2)
	public String getIsRespAchieve(){
		return this.isRespAchieve;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改责任是否到位
	 */
	public void setIsRespAchieve(String isRespAchieve){
		this.isRespAchieve = isRespAchieve;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  治理措施到位情况
	 */
	@Column(name ="MEASURE_ACH",nullable=true,length=500)
	public String getMeasureAch(){
		return this.measureAch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  治理措施到位情况
	 */
	public void setMeasureAch(String measureAch){
		this.measureAch = measureAch;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  治理措施到位日期
	 */
	@Column(name ="MEASURE_ACH_DATE",nullable=false)
	public Date getMeasureAchDate(){
		return this.measureAchDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  治理措施到位日期
	 */
	public void setMeasureAchDate(Date measureAchDate){
		this.measureAchDate = measureAchDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  治理措施是否到位
	 */
	@Column(name ="IS_MEASURE_ACHIEVE",nullable=true,length=2)
	public String getIsMeasureAchieve(){
		return this.isMeasureAchieve;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  治理措施是否到位
	 */
	public void setIsMeasureAchieve(String isMeasureAchieve){
		this.isMeasureAchieve = isMeasureAchieve;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改资金到位情况
	 */
	@Column(name ="FUND_ACH",nullable=true,length=500)
	public String getFundAch(){
		return this.fundAch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改资金到位情况
	 */
	public void setFundAch(String fundAch){
		this.fundAch = fundAch;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  整改资金到位日期
	 */
	@Column(name ="FUND_ACH_DATE",nullable=false)
	public Date getFundAchDate(){
		return this.fundAchDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  整改资金到位日期
	 */
	public void setFundAchDate(Date fundAchDate){
		this.fundAchDate = fundAchDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改资金是否到位
	 */
	@Column(name ="IS_FUND_ACHIEVE",nullable=true,length=2)
	public String getIsFundAchieve(){
		return this.isFundAchieve;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改资金是否到位
	 */
	public void setIsFundAchieve(String isFundAchieve){
		this.isFundAchieve = isFundAchieve;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改预案到位情况
	 */
	@Column(name ="PLAN_ACH",nullable=true,length=500)
	public String getPlanAch(){
		return this.planAch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改预案到位情况
	 */
	public void setPlanAch(String planAch){
		this.planAch = planAch;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  整改预案到位日期
	 */
	@Column(name ="PLAN_ACH_DATE",nullable=false)
	public Date getPlanAchDate(){
		return this.planAchDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  整改预案到位日期
	 */
	public void setPlanAchDate(Date planAchDate){
		this.planAchDate = planAchDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  整改预案是否到位
	 */
	@Column(name ="IS_PLAN_ACHIEVE",nullable=true,length=2)
	public String getIsPlanAchieve(){
		return this.isPlanAchieve;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  整改预案是否到位
	 */
	public void setIsPlanAchieve(String isPlanAchieve){
		this.isPlanAchieve = isPlanAchieve;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  纳入治理计划情况
	 */
	@Column(name ="GOVE_PLAN",nullable=true,length=500)
	public String getGovePlan(){
		return this.govePlan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  纳入治理计划情况
	 */
	public void setGovePlan(String govePlan){
		this.govePlan = govePlan;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  纳入治理计划日期
	 */
	@Column(name ="GOVE_PLAN_DATE",nullable=false)
	public Date getGovePlanDate(){
		return this.govePlanDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  纳入治理计划日期
	 */
	public void setGovePlanDate(Date govePlanDate){
		this.govePlanDate = govePlanDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  纳入治理计划情况是否到位
	 */
	@Column(name ="IS_GOVE_PLAN_ACHIEVE",nullable=true,length=2)
	public String getIsGovePlanAchieve(){
		return this.isGovePlanAchieve;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  纳入治理计划情况是否到位
	 */
	public void setIsGovePlanAchieve(String isGovePlanAchieve){
		this.isGovePlanAchieve = isGovePlanAchieve;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=100)
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
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=false)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登陆名
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登陆名
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=100)
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
	 *@return: java.util.Date  更新时间
	 */
	@Column(name ="UPDATE_DATE",nullable=false)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}


    /**
     *方法: 取得java.util.String
     *@return: java.util.String  设置上报状态
     */
    @Column(name ="REPORT_STATUS",nullable=false)
    public String getReportStatus(){
        return this.reportStatus;
    }

    /**
     *方法: 设置java.util.String
     *@param: java.util.String  设置上报状态
     */
    public void setReportStatus(String reportStatus){
        this.reportStatus = reportStatus;
    }

    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  上报时间
     */
    @Column(name ="REPORT_DATE",nullable=false)
    public Date getReportDate(){
        return this.reportDate;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  设置上报时间
     */
    public void setReportDate(Date reportDate){
        this.reportDate = reportDate;
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

}
