package com.sdzk.buss.web.system.entity;

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
 * @Description: t_b_sunshine
 * @author onlineGenerator
 * @date 2017-11-30 09:20:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_sunshine", schema = "")
@SuppressWarnings("serial")
public class TBSunshineEntity implements java.io.Serializable {
	/**唯一标识*/
	private String id;
	/**表名*/
	@Excel(name="表名",width=15)
	private String tableName;
	/**对应表中隐藏列的id*/
	private String columnId;
	/**创建时间*/
	@Excel(name="创建时间",width=15,format = "yyyy-MM-dd")
	private Date createDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  唯一标识
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
	 *@param: java.lang.String  唯一标识
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表名
	 */

	@Column(name ="TABLE_NAME",nullable=true,length=60)
	public String getTableName(){
		return this.tableName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表名
	 */
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  列名
	 */

	/**
	 * 方法：设置java.lang.String
	 * @param:java.lang.String 列名
	 * */
	@Column(name ="COLUMN_ID",nullable=true,length=36)
	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */

	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
}
