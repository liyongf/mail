package com.sdzk.buss.web.yearreport.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 年度辨识报告
 * @author zhangdaihao
 * @date 2019-06-01 16:09:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_year_report_rel", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBYearReportRelEntity implements java.io.Serializable {

	/**唯一编号*/
	private String id;

	private String professGroup;

	private String chargeLeader;

	private String leadDepart;

	private String dutyDepart;

	private String reportId;

	//序号
	private  String orderNumber;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  唯一编号
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
	 *@param: java.lang.String  唯一编号
	 */
	public void setId(String id){
		this.id = id;
	}

	@Column(name ="report_id",nullable=true,length=300)
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	@Column(name ="profess_group",nullable=true)
	public String getProfessGroup() {
		return professGroup;
	}

	public void setProfessGroup(String professGroup) {
		this.professGroup = professGroup;
	}

	@Column(name ="charge_leader",nullable=true)
	public String getChargeLeader() {
		return chargeLeader;
	}

	public void setChargeLeader(String chargeLeader) {
		this.chargeLeader = chargeLeader;
	}

	@Column(name ="lead_depart",nullable=true)
	public String getLeadDepart() {
		return leadDepart;
	}

	public void setLeadDepart(String leadDepart) {
		this.leadDepart = leadDepart;
	}

	@Column(name ="duty_depart",nullable=true)
	public String getDutyDepart() {
		return dutyDepart;
	}

	public void setDutyDepart(String dutyDepart) {
		this.dutyDepart = dutyDepart;
	}

	@Transient
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
}
