package com.sdzk.buss.web.tbdangersourceapost.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 风险与工种关联表
 * @author onlineGenerator
 * @date 2017-07-24 11:03:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_danger_post_rel", schema = "")
@SuppressWarnings("serial")
public class TBDangerPostRelEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**风险id*/
	@Excel(name="风险id")
	private String dangerId;
	/**工种id*/
	@Excel(name="工种id")
	private String postId;
	
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险id
	 */
	@Column(name ="DANGER_ID",nullable=true,length=32)
	public String getDangerId(){
		return this.dangerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险id
	 */
	public void setDangerId(String dangerId){
		this.dangerId = dangerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工种id
	 */
	@Column(name ="POST_ID",nullable=true,length=32)
	public String getPostId(){
		return this.postId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工种id
	 */
	public void setPostId(String postId){
		this.postId = postId;
	}
}
