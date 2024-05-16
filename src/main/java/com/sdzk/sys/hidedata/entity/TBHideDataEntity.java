package com.sdzk.sys.hidedata.entity;

import java.util.Date;
import java.lang.String;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: t_b_hide_data
 * @author onlineGenerator
 * @date 2018-03-23 14:50:19
 * @version V1.0
 */
@Entity
@Table(name = "t_b_hide_data", schema = "")
@SuppressWarnings("serial")
public class TBHideDataEntity implements java.io.Serializable {
	/**唯一标识*/
	private String id;
	/**标题*/
	@Excel(name="标题",width=15)
	private String title;
	/**是否显示*/
	@Excel(name="是否显示",width=15)
	private String isShow;
	/**页面div的id*/
	private String divId;
	/**创建人登录名*/
	@Excel(name="创建人登录名",width=15)
	private String createBy;
	/**创建人姓名*/
	@Excel(name="创建人姓名",width=15)
	private String createName;
	/**创建日期*/
	@Excel(name="创建日期",width=15,format = "yyyy-MM-dd")
	private Date createDate;
	/**更新人登录名*/
	@Excel(name="更新人登录名",width=15)
	private String updateBy;
	/**更新人名称*/
	@Excel(name="更新人名称",width=15)
	private String updateName;
	/**更新日期*/
	@Excel(name="更新日期",width=15,format = "yyyy-MM-dd")
	private Date updateDate;


	/**
	 *  将实体转为 JSONObject 对象
	 * */
	public JSONObject entityToString(){
		JSONObject jo = new JSONObject();
		jo.put("id", this.id);
		jo.put("title", this.title);
		jo.put("isShow", this.isShow);
		jo.put("divId", this.divId);
		jo.put("createBy", this.createBy);
		jo.put("createName", this.createName);
		jo.put("createDate", this.createDate);
		jo.put("updateBy", this.updateBy);
		jo.put("updateName", this.updateName);
		jo.put("updateDate", this.updateDate);

		return jo;
	}

	
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
	 *@return: java.lang.String  标题
	 */

	@Column(name ="TITLE",nullable=true,length=60)
	public String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否显示
	 */

	@Column(name ="IS_SHOW",nullable=true,length=2)
	public String getIsShow(){
		return this.isShow;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否显示
	 */
	public void setIsShow(String isShow){
		this.isShow = isShow;
	}

	@Column(name ="DIV_ID",nullable=true,length=20)
	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名
	 */

	@Column(name ="CREATE_BY",nullable=true,length=36)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人姓名
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=36)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人姓名
	 */
	public void setCreateName(String createName){
		this.createName = createName;
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
	 *@return: java.lang.String  更新人登录名
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=36)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=36)
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
}
