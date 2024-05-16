package com.sdzk.buss.web.accident.entity;

import java.util.Date;
import java.lang.String;
import java.lang.Integer;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;

/**   
 * @Title: Entity
 * @Description: 事故
 * @author onlineGenerator
 * @date 2016-05-06 10:09:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_accident", schema = "")
@SuppressWarnings("serial")
public class TBAccidentEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**事故单位*/
	@Excel(name="事故单位")
	private TSDepart dept;
	/**事故编号*/
	@Excel(name="事故编号")
	private String accidentcode;
	/**事故名称*/
	@Excel(name="事故名称")
	private String accidentname;
	/**发生时间*/
	@Excel(name="发生时间")
	private Date happentime;
	/**发生地点*/
	@Excel(name="发生地点")
	private String happenaddress;
	/**事故类型*/
	@Excel(name="事故类型")
	private String accidenttype;
	/**事故等级*/
	@Excel(name="事故等级")
	private TBAccidentLevelEntity accidentlevel;
	/**事故经过*/
	@Excel(name="事故经过")
	private String accidentdetail;
	/**死亡人数*/
	@Excel(name="死亡人数")
	private Integer deathnum;
	/**重伤人数*/
	@Excel(name="重伤人数")
	private Integer heavywoundnum;
	/**轻伤人数*/
	@Excel(name="轻伤人数")
	private Integer minorwoundnum;
	/**直接损失*/
	@Excel(name="直接损失")
	private String directdamage;
	/**间接损失*/
	@Excel(name="间接损失")
	private String consequentialloss;
	/**起因物*/
	@Excel(name="起因物")
	private String cause;
	/**直接原因*/
	@Excel(name="直接原因")
	private String immediatecause;
	/**间接原因*/
	@Excel(name="间接原因")
	private String remotecause;
	/**事故教训*/
	@Excel(name="事故教训")
	private String accidentlesson;
	/**防御措施*/
	@Excel(name="防御措施")
	private String securityclampdown;
	/**attachmenturl*/
	@Excel(name="attachmenturl")
	private String attachmenturl;
	/**attachmentname*/
	@Excel(name="attachmentname")
	private String attachmentname;
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
    private String accidentTypeTemp;

    @Transient
    public String getAccidentTypeTemp() {
        return accidentTypeTemp;
    }

    public void setAccidentTypeTemp(String accidentTypeTemp) {
        this.accidentTypeTemp=accidentTypeTemp;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPTID")
    public TSDepart getDept() {
        return dept;
    }

    public void setDept(TSDepart dept) {
        this.dept = dept;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  事故编号
	 */
	@Column(name ="ACCIDENTCODE",nullable=true,length=255)
	public String getAccidentcode(){
		return this.accidentcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  事故编号
	 */
	public void setAccidentcode(String accidentcode){
		this.accidentcode = accidentcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  事故名称
	 */
	@Column(name ="ACCIDENTNAME",nullable=true,length=255)
	public String getAccidentname(){
		return this.accidentname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  事故名称
	 */
	public void setAccidentname(String accidentname){
		this.accidentname = accidentname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发生时间
	 */
	@Column(name ="HAPPENTIME",nullable=true,length=20)
	public Date getHappentime(){
		return this.happentime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发生时间
	 */
	public void setHappentime(Date happentime){
		this.happentime = happentime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发生地点
	 */
	@Column(name ="HAPPENADDRESS",nullable=true,length=255)
	public String getHappenaddress(){
		return this.happenaddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发生地点
	 */
	public void setHappenaddress(String happenaddress){
		this.happenaddress = happenaddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  事故类型
	 */
	@Column(name ="ACCIDENTTYPE",nullable=true,length=64)
	public String getAccidenttype(){
		return this.accidenttype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  事故类型
	 */
	public void setAccidenttype(String accidenttype){
		this.accidenttype = accidenttype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  事故等级
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCIDENTLEVEL")
	public TBAccidentLevelEntity getAccidentlevel(){
		return this.accidentlevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  事故等级
	 */
	public void setAccidentlevel(TBAccidentLevelEntity accidentlevel){
		this.accidentlevel = accidentlevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  事故经过
	 */
	@Column(name ="ACCIDENTDETAIL",nullable=true,length=1000)
	public String getAccidentdetail(){
		return this.accidentdetail;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  事故经过
	 */
	public void setAccidentdetail(String accidentdetail){
		this.accidentdetail = accidentdetail;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  死亡人数
	 */
	@Column(name ="DEATHNUM",nullable=true,length=10)
	public Integer getDeathnum(){
		return this.deathnum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  死亡人数
	 */
	public void setDeathnum(Integer deathnum){
		this.deathnum = deathnum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  重伤人数
	 */
	@Column(name ="HEAVYWOUNDNUM",nullable=true,length=10)
	public Integer getHeavywoundnum(){
		return this.heavywoundnum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  重伤人数
	 */
	public void setHeavywoundnum(Integer heavywoundnum){
		this.heavywoundnum = heavywoundnum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  轻伤人数
	 */
	@Column(name ="MINORWOUNDNUM",nullable=true,length=10)
	public Integer getMinorwoundnum(){
		return this.minorwoundnum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  轻伤人数
	 */
	public void setMinorwoundnum(Integer minorwoundnum){
		this.minorwoundnum = minorwoundnum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  直接损失
	 */
	@Column(name ="DIRECTDAMAGE",nullable=true,scale=6,length=20)
	public String getDirectdamage(){
		return this.directdamage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  直接损失
	 */
	public void setDirectdamage(String directdamage){
		this.directdamage = directdamage;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  间接损失
	 */
	@Column(name ="CONSEQUENTIALLOSS",nullable=true,scale=6,length=20)
	public String getConsequentialloss(){
		return this.consequentialloss;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  间接损失
	 */
	public void setConsequentialloss(String consequentialloss){
		this.consequentialloss = consequentialloss;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  起因物
	 */
	@Column(name ="CAUSE",nullable=true,length=255)
	public String getCause(){
		return this.cause;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  起因物
	 */
	public void setCause(String cause){
		this.cause = cause;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  直接原因
	 */
	@Column(name ="IMMEDIATECAUSE",nullable=true,length=1000)
	public String getImmediatecause(){
		return this.immediatecause;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  直接原因
	 */
	public void setImmediatecause(String immediatecause){
		this.immediatecause = immediatecause;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  间接原因
	 */
	@Column(name ="REMOTECAUSE",nullable=true,length=1000)
	public String getRemotecause(){
		return this.remotecause;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  间接原因
	 */
	public void setRemotecause(String remotecause){
		this.remotecause = remotecause;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  事故教训
	 */
	@Column(name ="ACCIDENTLESSON",nullable=true,length=1000)
	public String getAccidentlesson(){
		return this.accidentlesson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  事故教训
	 */
	public void setAccidentlesson(String accidentlesson){
		this.accidentlesson = accidentlesson;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  防御措施
	 */
	@Column(name ="SECURITYCLAMPDOWN",nullable=true,length=1000)
	public String getSecurityclampdown(){
		return this.securityclampdown;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  防御措施
	 */
	public void setSecurityclampdown(String securityclampdown){
		this.securityclampdown = securityclampdown;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  attachmenturl
	 */
	@Column(name ="ATTACHMENTURL",nullable=true,length=255)
	public String getAttachmenturl(){
		return this.attachmenturl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  attachmenturl
	 */
	public void setAttachmenturl(String attachmenturl){
		this.attachmenturl = attachmenturl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  attachmentname
	 */
	@Column(name ="ATTACHMENTNAME",nullable=true,length=255)
	public String getAttachmentname(){
		return this.attachmentname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  attachmentname
	 */
	public void setAttachmentname(String attachmentname){
		this.attachmentname = attachmentname;
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
