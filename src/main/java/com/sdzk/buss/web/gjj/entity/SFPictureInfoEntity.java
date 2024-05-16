package com.sdzk.buss.web.gjj.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 图形文件
 * @author zhangdaihao
 * @date 2023-10-25 17:15:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sf_picture_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SFPictureInfoEntity implements java.io.Serializable {
	/**图形文件编码*/
	private Integer id;
	/**矿端数据操作标识*/
	private String stateFlag;
	/**图层id*/
	private String layerId;
	private String layerName;
	/**文件id*/
	private String fileId;
	/**图形文件类型*/
	private String fileType;
	private String fileTypeName;
	/**图形文件名称*/
	private String fileName;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**是否删除*/
	private String isDelete;
	/**数据上报时间*/
	private Date uploadTime;

	//文件真实路径名称
	private String fileRealPath;

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  图形文件编码
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=10,scale=0)
	public Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  图形文件编码
	 */
	public void setId(Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  矿端数据操作标识
	 */
	@Column(name ="STATE_FLAG",nullable=true,length=1)
	public String getStateFlag(){
		return this.stateFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  矿端数据操作标识
	 */
	public void setStateFlag(String stateFlag){
		this.stateFlag = stateFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图层id
	 */
	@Column(name ="LAYER_ID",nullable=true,length=32)
	public String getLayerId(){
		return this.layerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图层id
	 */
	public void setLayerId(String layerId){
		this.layerId = layerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文件id
	 */
	@Column(name ="FILE_ID",nullable=true,length=32)
	public String getFileId(){
		return this.fileId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件id
	 */
	public void setFileId(String fileId){
		this.fileId = fileId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图形文件类型
	 */
	@Column(name ="FILE_TYPE",nullable=true,length=1)
	public String getFileType(){
		return this.fileType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图形文件类型
	 */
	public void setFileType(String fileType){
		this.fileType = fileType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图形文件名称
	 */
	@Column(name ="FILE_NAME",nullable=true,length=200)
	public String getFileName(){
		return this.fileName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图形文件名称
	 */
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
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
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否删除
	 */
	@Column(name ="IS_DELETE",nullable=true,length=4)
	public String getIsDelete(){
		return this.isDelete;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否删除
	 */
	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
	}

	@Transient
	public String getFileRealPath() {
		return fileRealPath;
	}

	public void setFileRealPath(String fileRealPath) {
		this.fileRealPath = fileRealPath;
	}

	@Transient
	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	@Transient
	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  数据上报时间
	 */
	@Column(name ="UPLOAD_TIME",nullable=true)
	public Date getUploadTime(){
		return this.uploadTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  数据上报时间
	 */
	public void setUploadTime(Date uploadTime){
		this.uploadTime = uploadTime;
	}
}
