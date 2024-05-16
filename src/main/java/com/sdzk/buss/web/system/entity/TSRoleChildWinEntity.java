package com.sdzk.buss.web.system.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 子窗口权限管理
 * @author zhangdaihao
 * @date 2017-03-27 10:48:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_role_childWin", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TSRoleChildWinEntity implements java.io.Serializable {
	/**id*/
	private String id;
	/**role*/
	private org.jeecgframework.web.system.pojo.base.TSRole TSRole;
	/**childwinid*/
	private String childwinid;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=50)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(String id){
		this.id = id;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid")
	public org.jeecgframework.web.system.pojo.base.TSRole getTSRole() {
		return this.TSRole;
	}

	public void setTSRole(org.jeecgframework.web.system.pojo.base.TSRole TSRole) {
		this.TSRole = TSRole;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  childwinid
	 */
	@Column(name ="CHILDWINID",nullable=true,length=32)
	public String getChildwinid(){
		return this.childwinid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  childwinid
	 */
	public void setChildwinid(String childwinid){
		this.childwinid = childwinid;
	}
}
