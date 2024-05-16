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

import com.sdzk.buss.web.common.Constants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: t_b_danger_source_audit_his
 * @author onlineGenerator
 * @date 2017-06-20 14:24:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_danger_source_audit_his", schema = "")
@SuppressWarnings("serial")
public class TBDangerSourceAuditHisEntity implements java.io.Serializable {
	/**唯一标识*/
	private String id;
	/**隐患基础信息表关联键*/
	@Excel(name="隐患基础信息表关联键")
	private TBDangerSourceEntity danger;
	/**处理时间*/
	@Excel(name="处理时间",format = "yyyy-MM-dd")
	private Date dealTime;
	/**处理环节*/
	@Excel(name="处理环节")
	private Integer dealStep;
	/**说明*/
	@Excel(name="说明")
	private String dealDesc;
	/**处理人*/
	@Excel(name="处理人")
	private String dealManName;

    private String handleTypeTemp;
    private String dealStepName;

    @Transient
    public String getDealStepName() {
        if (dealStep == Constants.DANGER_SOURCE_AUDIT_HIS_STEP_REPORT ) {
            return "上报";
        }else if (dealStep == Constants.DANGER_SOURCE_AUDIT_HIS_STEP_ROLLBANK) {
            return "驳回上报";
        }else if (dealStep == Constants.DANGER_SOURCE_AUDIT_HIS_STEP_CLOSE) {
            return "审核通过";
        }else{
            return "";
        }
    }

    public void setDealStepName(String dealStepName) {
        this.dealStepName = dealStepName;
    }

    @Transient
    public String getHandleTypeTemp() {
        return handleTypeTemp;
    }

    public void setHandleTypeTemp(String handleTypeTemp) {
        this.handleTypeTemp = handleTypeTemp;
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
	 *@return: java.lang.String  隐患基础信息表关联键
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DANGER_ID")
    @NotFound(action= NotFoundAction.IGNORE)
	public TBDangerSourceEntity getDanger(){
		return this.danger;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隐患基础信息表关联键
	 */
	public void setDanger(TBDangerSourceEntity danger){
		this.danger = danger;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  处理时间
	 */
	@Column(name ="DEAL_TIME",nullable=true)
	public Date getDealTime(){
		return this.dealTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  处理时间
	 */
	public void setDealTime(Date dealTime){
		this.dealTime = dealTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理环节
	 */
	@Column(name ="DEAL_STEP",nullable=true,length=50)
	public Integer getDealStep(){
		return this.dealStep;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理环节
	 */
	public void setDealStep(Integer dealStep){
		this.dealStep = dealStep;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  说明
	 */
	@Column(name ="DEAL_DESC",nullable=true,length=1000)
	public String getDealDesc(){
		return this.dealDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  说明
	 */
	public void setDealDesc(String dealDesc){
		this.dealDesc = dealDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理人
	 */
	@Column(name ="DEAL_MAN_NAME",nullable=true,length=100)
	public String getDealManName(){
		return this.dealManName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理人
	 */
	public void setDealManName(String dealManName){
		this.dealManName = dealManName;
	}
}
