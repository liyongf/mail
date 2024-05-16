package com.sdzk.buss.web.dangersource.entity;

import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: 文档来源管理
 * @author zhangdaihao
 * @date 2017-07-31 10:59:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_document_source", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBDocumentSourceEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**文档来源名称*/
	private String docSourceName;
	/**上级分类*/
	private TBDocumentSourceEntity parentDocSource;
	/**是否删除*/
	private String isDelete;
	/**创建人登录名称*/
	private String createBy;
	/**创建人名称*/
	private String createName;
	/**创建日期*/
	private Date createDate;
	/**更新人登录名称*/
	private String updateBy;
	/**更新人名称*/
	private String updateName;
	/**更新日期*/
	private Date updateDate;

    /**下级分类*/
    private List<TBDocumentSourceEntity> childDocSources = new ArrayList<TBDocumentSourceEntity>();
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
	 *@return: java.lang.String  文档来源名称
	 */
	@Column(name ="DOC_SOURCE_NAME",nullable=false,length=200)
	public String getDocSourceName(){
		return this.docSourceName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文档来源名称
	 */
	public void setDocSourceName(String docSourceName){
		this.docSourceName = docSourceName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父节点ID
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARENT_SOURCE_ID")
	@NotFound(action= NotFoundAction.IGNORE)
	public TBDocumentSourceEntity getParentDocSource(){
		return parentDocSource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父节点ID
	 */
	public void setParentDocSource(TBDocumentSourceEntity parentDocSource){
		this.parentDocSource = parentDocSource;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否删除
	 */
	@Column(name ="IS_DELETE",nullable=true,length=32)
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentDocSource")
    public List<TBDocumentSourceEntity> getChildDocSources(){
        return childDocSources;
    }

    public void setChildDocSources(List<TBDocumentSourceEntity> childDocSources){
        this.childDocSources = childDocSources;
    }


}
