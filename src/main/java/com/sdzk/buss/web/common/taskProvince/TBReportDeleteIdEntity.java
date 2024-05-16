package com.sdzk.buss.web.common.taskProvince;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 删除
 * @author zhangdaihao
 * @date 2020-06-03 14:24:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_report_delete_id", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBReportDeleteIdEntity implements java.io.Serializable {
	/**唯一标识*/
	private String id;
	/**删除Id*/
	private String deleteId;
	/**type*/
	private String type;
	
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
	 *@return: java.lang.String  删除Id
	 */
	@Column(name ="DELETE_ID",nullable=true,length=32)
	public String getDeleteId(){
		return this.deleteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  删除Id
	 */
	public void setDeleteId(String deleteId){
		this.deleteId = deleteId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  type
	 */
	@Column(name ="TYPE",nullable=true,length=10)
	public String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  type
	 */
	public void setType(String type){
		this.type = type;
	}
}
