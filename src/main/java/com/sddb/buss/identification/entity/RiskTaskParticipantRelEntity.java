package com.sddb.buss.identification.entity;

import org.hibernate.annotations.*;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**   
 * @Title: Entity
 * @Description: 风险辨识任务
 * @author zhangdaihao
 * @date 2019-05-14 10:05:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_risk_task_participant_rel", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class RiskTaskParticipantRelEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;
	/**风险辨识任务*/
	private String riskTaskId;
	/**参与人员*/
	private TSUser participantManId;

	/**风险草稿数量*/
	private String risk0Num;

	/**风险待审核数量*/
	private String risk1Num;


	/**风险已退回数量*/
	private String risk2Num;

	/**风险已审核数量*/
	private String risk3Num;


	/**风险岗位*/
	private String riskPostNum;

	private String status;


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
	@Column(name ="risk_task_id",nullable=false,length=36)
	public String getRiskTaskId() {
		return riskTaskId;
	}

	public void setRiskTaskId(String riskTaskId) {
		this.riskTaskId = riskTaskId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "participant_man_id")
	@NotFound(action= NotFoundAction.IGNORE)
	public TSUser getParticipantManId() {
		return participantManId;
	}

	public void setParticipantManId(TSUser participantManId) {
		this.participantManId = participantManId;
	}


	@Transient
	public String getRisk0Num() {
		return risk0Num;
	}

	public void setRisk0Num(String risk0Num) {
		this.risk0Num = risk0Num;
	}
	@Transient
	public String getRisk1Num() {
		return risk1Num;
	}

	public void setRisk1Num(String risk1Num) {
		this.risk1Num = risk1Num;
	}
	@Transient
	public String getRisk2Num() {
		return risk2Num;
	}

	public void setRisk2Num(String risk2Num) {
		this.risk2Num = risk2Num;
	}

	@Transient
	public String getRisk3Num() {
		return risk3Num;
	}

	public void setRisk3Num(String risk3Num) {
		this.risk3Num = risk3Num;
	}

	@Column(name ="status",nullable=false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public String getRiskPostNum() {
		return riskPostNum;
	}

	public void setRiskPostNum(String riskPostNum) {
		this.riskPostNum = riskPostNum;
	}
}
