package com.sddb.buss.riskmanage.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: t_b_danger_source
 * @author onlineGenerator
 * @date 2017-06-20 14:18:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_risk_manage_major_rel", schema = "")
@SuppressWarnings("serial")
public class RiskManageMajorRelEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;

	/**专业Id*/
	private  String majorId;
	private  String majorNameTemp;

	/**个人Id*/
	private  String userId;
	/**管控类型*/
	private  String manageType;




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
	@Column(name ="major_id",nullable=false)
	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
    @Transient
	public String getMajorNameTemp() {
		return majorNameTemp;
	}

	public void setMajorNameTemp(String majorNameTemp) {
		this.majorNameTemp = majorNameTemp;
	}
	@Column(name ="user_id",nullable=false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name ="manageType",nullable=false)
	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}
}
