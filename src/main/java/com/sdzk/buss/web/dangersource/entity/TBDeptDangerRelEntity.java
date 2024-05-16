package com.sdzk.buss.web.dangersource.entity;

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
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;

/**   
 * @Title: Entity
 * @Description: 部门危险源关联表
 * @author onlineGenerator
 * @date 2017-06-23 10:18:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_dept_danger_rel", schema = "")
@SuppressWarnings("serial")
public class TBDeptDangerRelEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**危险源ID*/
	@Excel(name="危险源ID")
	private TBDangerSourceEntity danger;
	/**所属部门ID*/
	@Excel(name="所属部门ID")
	private TSDepart dept;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  危险源ID
	 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DANGER_ID")
	public TBDangerSourceEntity getDanger(){
		return this.danger;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  危险源ID
	 */
	public void setDanger(TBDangerSourceEntity danger){
		this.danger = danger;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门ID
	 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_ID")
	public TSDepart getDept(){
		return this.dept;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门ID
	 */
	public void setDept(TSDepart dept){
		this.dept = dept;
	}
}
