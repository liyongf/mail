package com.sdzk.buss.web.layer.entity;

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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**   
 * @Title: Entity
 * @Description: 超图图层
 * @author onlineGenerator
 * @date 2018-06-09 10:42:08
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_layer", schema = "")
@SuppressWarnings("serial")
public class TBLayerEntity implements java.io.Serializable {
	/**ID*/
	private String id;
	/**图层编码*/
	@Excel(name="图层编码",width=15)
	private String layerCode;
	/**矿图名称*/
	@Excel(name="矿图名称",width=15)
	private String layerDetailName;
	/**超图路径*/
	@Excel(name="超图路径",width=15)
	private String url;
	/**超图中心点*/
	@Excel(name="超图中心点",width=15)
	private String center;
	/**图层是否显示*/
	@Excel(name="图层是否显示",width=15)
	private String isShow;
	/**备注*/
	@Excel(name="备注",width=15)
	private String remark;
	/**创建人id*/
	private String createBy;
	/**创建人*/
	private String createName;
	/**创建时间*/
	private Date createDate;
	/**修改人id*/
	private String updateBy;
	/**修改人*/
	private String updateName;
	/**修改时间*/
	private Date updateDate;

	/**上报集团时间*/
	private Date reportGroupTime;
	/**上报集团用户*/
	private TSUser reportGroupMan;
	/**上报集团状态*/
	private String reportGroupStatus;
	
	/**矿图文件名*/
	private String dwgName;
	/**矿图文件路径*/
	private String dwgPath;
	/**发布类型*/
	private String publishType;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")

	@Column(name ="ID",nullable=false,length=32)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ID
	 */
	public void setId(String id){
		if (StringUtil.isEmpty(id)){
			id = UUIDGenerator.generate();
		}
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图层编码
	 */

	@Column(name ="LAYER_CODE",nullable=true,length=32)
	public String getLayerCode(){
		return this.layerCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图层编码
	 */
	public void setLayerCode(String layerCode){
		this.layerCode = layerCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿图名称
	 */

	@Column(name ="LAYER_DETAIL_NAME",nullable=true,length=50)
	public String getLayerDetailName(){
		return this.layerDetailName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿图名称
	 */
	public void setLayerDetailName(String layerDetailName){
		this.layerDetailName = layerDetailName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  超图路径
	 */

	@Column(name ="URL",nullable=true,length=100)
	public String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  超图路径
	 */
	public void setUrl(String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  超图中心点
	 */

	@Column(name ="center",nullable=true,length=100)
	public String getCenter(){
		return this.center;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  超图中心点
	 */
	public void setCenter(String center){
		this.center = center;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图层是否显示
	 */

	@Column(name ="IS_SHOW",nullable=true,length=10)
	public String getIsShow(){
		return this.isShow;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图层是否显示
	 */
	public void setIsShow(String isShow){
		this.isShow = isShow;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="REMARK",nullable=true,length=100)
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人id
	 */

	@Column(name ="CREATE_BY",nullable=true,length=32)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人id
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=32)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateName(String createName){
		this.createName = createName;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人id
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=32)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人id
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=32)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */

	@Column(name ="UPDATE_DATE",nullable=true)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}


	@Column(name ="report_group_time",nullable=true)
	public Date getReportGroupTime() {
		return reportGroupTime;
	}

	public void setReportGroupTime(Date reportGroupTime) {
		this.reportGroupTime = reportGroupTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_group_man")
	@NotFound(action= NotFoundAction.IGNORE)
	public TSUser getReportGroupMan() {
		return reportGroupMan;
	}

	public void setReportGroupMan(TSUser reportGroupMan) {
		this.reportGroupMan = reportGroupMan;
	}

	@Column(name ="report_group_status",nullable=true,length=4)
	public String getReportGroupStatus() {
		return reportGroupStatus;
	}

	public void setReportGroupStatus(String reportGroupStatus) {
		this.reportGroupStatus = reportGroupStatus;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿图文件名
	 */

	@Column(name ="DWG_NAME",nullable=true,length=50)
	public String getDwgName(){
		return this.dwgName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿图文件名
	 */
	public void setDwgName(String dwgName){
		this.dwgName = dwgName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿图文件路径
	 */

	@Column(name ="DWG_PATH",nullable=true,length=100)
	public String getDwgPath(){
		return this.dwgPath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿图文件路径
	 */
	public void setDwgPath(String dwgPath){
		this.dwgPath = dwgPath;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿图发布类型
	 */

	@Column(name ="publish_type",nullable=true,length=100)
	public String getPublishType(){
		return this.publishType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿图发布类型
	 */
	public void setPublishType(String publishType){
		this.publishType = publishType;
	}
}
