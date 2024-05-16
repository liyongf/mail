package com.sdzk.buss.web.hiddendanger.entity;

import java.lang.String;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**   
 * @Title: Entity
 * @Description: 隐患操作日志
 * @author onlineGenerator
 * @date 2018-06-07 10:06:29
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_hidden_danger_handle_log", schema = "")
@SuppressWarnings("serial")
public class TBHiddenDangerHandleLogEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**隐患id*/
	@Excel(name="隐患id",width=15)
	private String examId;

	private TSUser TSUser;
	/**操作类型*/
	@Excel(name="操作类型",width=15)
	private Short handleType;
	/**操作内容*/
	@Excel(name="操作内容",width=15)
	private String handleContent;
	@Excel(name="操作时间",width=15)
	private Date operatetime;
	
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

	public void setId(String id){
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public TSUser getTSUser() {
		return this.TSUser;
	}

	public void setTSUser(TSUser TSUser) {
		this.TSUser = TSUser;
	}

	@Column(name ="EXAM_ID",nullable=true,length=36)
	public String getExamId(){
		return this.examId;
	}

	public void setExamId(String examId){
		this.examId = examId;
	}


	@Column(name ="HANDLE_TYPE",nullable=true,length=32)
	public Short getHandleType(){
		return this.handleType;
	}

	public void setHandleType(Short handleType){
		this.handleType = handleType;
	}


	@Column(name ="HANDLE_CONTENT",nullable=true,length=255)
	public String getHandleContent(){
		return this.handleContent;
	}

	public void setHandleContent(String handleContent){
		this.handleContent = handleContent;
	}

	@Column(name = "operatetime", nullable = false, length = 35)
	public Date getOperatetime() {
		return this.operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

}
