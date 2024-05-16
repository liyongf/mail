package org.jeecgframework.web.system.pojo.base;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 数据源配置
 * @author zhangdaihao
 * @date 2014-09-05 13:22:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_data_source", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class DynamicDataSourceEntity implements java.io.Serializable {
		
	/**id*/
	private String id;
	/**dbKey*/
	private String dbKey;
	/**description*/
	private String description;
	/**driverClass*/
	private String driverClass;
	/**url*/
	private String url;
	/**dbUser*/
	private String dbUser;
	/**dbPassword*/
	private String dbPassword;
	/**dbType*/
	private String dbType;
	/**dbName*/
	private String dbName;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,precision=36,length=36)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  dbKey
	 */
	@Column(name ="DB_KEY",nullable=false,precision=50,length=50)
	public String getDbKey(){
		return this.dbKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  dbKey
	 */
	public void setDbKey(String dbKey){
		this.dbKey = dbKey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  description
	 */
	@Column(name ="DESCRIPTION",nullable=false,precision=50,length=50)
	public String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  driverClass
	 */
	@Column(name ="DRIVER_CLASS",nullable=false,precision=50,length=50)
	public String getDriverClass(){
		return this.driverClass;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  driverClass
	 */
	public void setDriverClass(String driverClass){
		this.driverClass = driverClass;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  url
	 */
	@Column(name ="URL",nullable=false,precision=100,length=100)
	public String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  url
	 */
	public void setUrl(String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  dbUser
	 */
	@Column(name ="DB_USER",nullable=false,precision=50,length=50)
	public String getDbUser(){
		return this.dbUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  dbUser
	 */
	public void setDbUser(String dbUser){
		this.dbUser = dbUser;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  dbPassword
	 */
	@Column(name ="DB_PASSWORD",nullable=true,precision=50,length=50)
	public String getDbPassword(){
		return this.dbPassword;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  dbPassword
	 */
	public void setDbPassword(String dbPassword){
		this.dbPassword = dbPassword;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  dbType
	 */
	@Column(name ="DB_TYPE",nullable=true,precision=50,length=50)
	public String getDbType(){
		return this.dbType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  dbType
	 */
	public void setDbType(String dbType){
		this.dbType = dbType;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  dbName
	 */
	@Column(name ="DB_NAME",nullable=true,precision=50,length=50)
	public String getDbName() {
		return dbName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  dbName
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
