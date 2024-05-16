package com.sdzk.buss.web.investigateplan.entity;

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

import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;

/**   
 * @Title: Entity
 * @Description: t_b_investigate_plan_rel
 * @author onlineGenerator
 * @date 2017-08-17 11:42:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_investigate_plan_rel", schema = "")
@SuppressWarnings("serial")
public class TBInvestigatePlanRelEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**排查计划主键*/
	@Excel(name="排查计划主键")
	private String planId;
	/**关联对象主键*/
	@Excel(name="关联对象主键")
	private String objId;
	/**关联类型(1=风险点;2=危险源;3=隐患)*/
	@Excel(name="关联类型(1=风险点;2=危险源;3=隐患)")
	private String relType;
	/**风险点类型(1=区域;2=作业过程)*/
	@Excel(name="风险点类型(1=区域;2=作业过程)")
	private String poitType;

    private String planName;
    private String objName;
    private String relTypeName;
    private String pointTypeName;

    private TBInvestigatePlanEntity tBInvestigatePlanEntity;
    private TBHiddenDangerHandleEntity tBHiddenDangerHandleEntity;
    @Transient
    public TBInvestigatePlanEntity gettBInvestigatePlanEntity() {
        return tBInvestigatePlanEntity;
    }

    public void settBInvestigatePlanEntity(TBInvestigatePlanEntity tBInvestigatePlanEntity) {
        this.tBInvestigatePlanEntity = tBInvestigatePlanEntity;
    }
    @Transient
    public TBHiddenDangerHandleEntity gettBHiddenDangerHandleEntity() {
        return tBHiddenDangerHandleEntity;
    }

    public void settBHiddenDangerHandleEntity(TBHiddenDangerHandleEntity tBHiddenDangerHandleEntity) {
        this.tBHiddenDangerHandleEntity = tBHiddenDangerHandleEntity;
    }

    @Transient
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
    @Transient
    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }
    @Transient
    public String getRelTypeName() {
        return relTypeName;
    }

    public void setRelTypeName(String relTypeName) {
        this.relTypeName = relTypeName;
    }
    @Transient
    public String getPointTypeName() {
        return pointTypeName;
    }

    public void setPointTypeName(String pointTypeName) {
        this.pointTypeName = pointTypeName;
    }

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
	 *@return: java.lang.String  排查计划主键
	 */
	@Column(name ="PLAN_ID",nullable=true,length=36)
	public String getPlanId(){
		return this.planId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排查计划主键
	 */
	public void setPlanId(String planId){
		this.planId = planId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联对象主键
	 */
	@Column(name ="OBJ_ID",nullable=true,length=36)
	public String getObjId(){
		return this.objId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联对象主键
	 */
	public void setObjId(String objId){
		this.objId = objId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联类型(1=风险点;2=危险源;3=隐患)
	 */
	@Column(name ="REL_TYPE",nullable=true,length=2)
	public String getRelType(){
		return this.relType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联类型(1=风险点;2=危险源;3=隐患)
	 */
	public void setRelType(String relType){
		this.relType = relType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险点类型(1=区域;2=作业过程)
	 */
	@Column(name ="POIT_TYPE",nullable=true,length=2)
	public String getPoitType(){
		return this.poitType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险点类型(1=区域;2=作业过程)
	 */
	public void setPoitType(String poitType){
		this.poitType = poitType;
	}
}
