package com.sddb.buss.riskmanage.entity;

import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: t_b_danger_source
 * @author onlineGenerator
 * @date 2017-06-20 14:18:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_risk_manage_post_rel", schema = "")
@SuppressWarnings("serial")
public class RiskManagePostRelEntity implements java.io.Serializable {
	/**唯一编号*/
	private String id;

	/**岗位Id*/
	private TBPostManageEntity post;

	/**个人Id*/
	private  String userId;





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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	public TBPostManageEntity getPost() {
		return post;
	}

	public void setPost(TBPostManageEntity post) {
		this.post = post;
	}
	@Column(name ="user_id",nullable=false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
