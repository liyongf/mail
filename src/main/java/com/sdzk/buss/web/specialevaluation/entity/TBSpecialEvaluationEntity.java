package com.sdzk.buss.web.specialevaluation.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**   
 * @Title: Entity
 * @Description: t_b_special_evaluation
 * @author onlineGenerator
 * @date 2017-07-01 10:55:00
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_special_evaluation", schema = "")
@SuppressWarnings("serial")
public class TBSpecialEvaluationEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**专项辨识名称*/
	@Excel(name="专项辨识名称")
	private String name;
	/**专项辨识负责人*/
	@Excel(name="专项辨识负责人")
	private String leader;
	/**辨识人类型*/
	@Excel(name="辨识人类型")
	private String leaderType;
	/**辨识时间*/
	@Excel(name="辨识时间",format = "yyyy-MM-dd")
	private Date time;
	/**辨识参加人员*/
	@Excel(name="辨识参加人员")
	private String participant;
	/**辨识地点*/
	@Excel(name="辨识地点")
	private String location;
	/**辨识结论*/
	@Excel(name="辨识结论")
	private String remark;
	/**专项辨识类型*/
	@Excel(name="专项辨识类型")
	private String type;
	/**上报状态:0=未上报;1=部门上报;2=矿上报*/
	@Excel(name="上报状态:0=未上报;1=部门上报;2=矿上报")
	private String reportStatus;
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
	/**
	 * 辨识参加人员姓名
	 */
	private String participantName;
	private long dangerSourceCount;
    /**是否删除**/
    private String isDelete;
    /**上报人*/
    private String report_name;
    /**上报时间*/
    private Date report_date;

	/**上报集团时间*/
	private Date reportGroupTime;
	/**上报集团用户*/
	private TSUser reportGroupMan;
	/**上报集团状态*/
	private String reportGroupStatus;


    @Column(name ="report_name",nullable=true,length=50)
    public String getReportName() {
        return report_name;
    }

    public void setReportName(String report_name) {
        this.report_name = report_name;
    }

    @Column(name ="report_date",nullable=true)
    public Date getReportDate() {
        return report_date;
    }

    public void setReportDate(Date report_date) {
        this.report_date = report_date;
    }

    /**
     * Temp字段
     * */

    private String leaderTemp;
    private String leaderTypeTemp;
    private String participantTemp;
    private String typeTemp;
    private String reportStatusTemp;
    private String locationTemp;

    @Transient
    public String getLocationTemp() {
        return locationTemp;
    }

    public void setLocationTemp(String locationTemp) {
        this.locationTemp = locationTemp;
    }

    @Transient
    public String getReportStatusTemp() {
        return reportStatusTemp;
    }

    public void setReportStatusTemp(String reportStatusTemp) {
        this.reportStatusTemp = reportStatusTemp;
    }

    @Transient
    public String getLeaderTemp() {
        return leaderTemp;
    }

    public void setLeaderTemp(String leaderTemp) {
        this.leaderTemp = leaderTemp;
    }

    @Transient
    public String getLeaderTypeTemp() {
        return leaderTypeTemp;
    }

    public void setLeaderTypeTemp(String leaderTypeTemp) {
        this.leaderTypeTemp = leaderTypeTemp;
    }

    @Transient
    public String getParticipantTemp() {
        return participantTemp;
    }

    public void setParticipantTemp(String participantTemp) {
        this.participantTemp = participantTemp;
    }

    @Transient
    public String getTypeTemp() {
        return typeTemp;
    }

    public void setTypeTemp(String typeTemp) {
        this.typeTemp = typeTemp;
    }

    @Transient
	public long getDangerSourceCount() {
		return dangerSourceCount;
	}

	public void setDangerSourceCount(long dangerSourceCount) {
		this.dangerSourceCount = dangerSourceCount;
	}

	@Transient
	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
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
		if(StringUtil.isEmpty(id)){
			id = UUIDGenerator.generate();
		}
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专项辨识名称
	 */
	@Column(name ="NAME",nullable=true,length=200)
	public String getName(){
		return this.name!=null?this.name.trim():this.name;
	}

    @Column(name ="is_delete",nullable=true,length=200)
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    /**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专项辨识名称
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专项辨识负责人
	 */
	@Column(name ="LEADER",nullable=true,length=36)
	public String getLeader(){
		return this.leader;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专项辨识负责人
	 */
	public void setLeader(String leader){
		this.leader = leader;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识人类型
	 */
	@Column(name ="LEADER_TYPE",nullable=true,length=20)
	public String getLeaderType(){
		return this.leaderType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识人类型
	 */
	public void setLeaderType(String leaderType){
		this.leaderType = leaderType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  辨识时间
	 */
	@Column(name ="eval_time",nullable=true)
	public Date getTime(){
		return this.time;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  辨识时间
	 */
	public void setTime(Date time){
		this.time = time;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识参加人员
	 */
	@Column(name ="PARTICIPANT",nullable=true,length=500)
	public String getParticipant(){
		return this.participant;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识参加人员
	 */
	public void setParticipant(String participant){
		this.participant = participant;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识地点
	 */
	@Column(name ="LOCATION",nullable=true,length=100)
	public String getLocation(){
		return this.location;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识地点
	 */
	public void setLocation(String location){
		this.location = location;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辨识结论
	 */
	@Column(name ="REMARK",nullable=true,length=1000)
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辨识结论
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专项辨识类型
	 */
	@Column(name ="TYPE",nullable=true,length=20)
	public String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专项辨识类型
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上报状态:0=未上报;1=部门上报;2=矿上报
	 */
	@Column(name ="REPORT_STATUS",nullable=true,length=2)
	public String getReportStatus(){
		return this.reportStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上报状态:0=未上报;1=部门上报;2=矿上报
	 */
	public void setReportStatus(String reportStatus){
		this.reportStatus = reportStatus;
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
}
