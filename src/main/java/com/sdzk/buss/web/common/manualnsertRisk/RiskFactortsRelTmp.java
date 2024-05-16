package com.sdzk.buss.web.common.manualnsertRisk;


import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_b_risk_factors_rel", schema = "")
@SuppressWarnings("serial")
public class RiskFactortsRelTmp {

    private String id;
    private String hazardFactorsEntity;
    private String riskIdentificationEntity;
    private String hfLevel;
    private String hfManageMeasure;
    private String manageDepart;
    private String manageUser;

    /**创建人名称*/
    @Excel(name="创建人名称")
    private String createName;
    /**创建人登录名称*/
    @Excel(name="创建人登录名称")
    private String createBy;
    /**创建日期*/
    @Excel(name="创建日期",format = "yyyy-MM-dd")
    private Date createDate;
    /**更新人名称*/
    @Excel(name="更新人名称")
    private String updateName;
    /**更新人登录名称*/
    @Excel(name="更新人登录名称")
    private String updateBy;
    /**更新日期*/
    @Excel(name="更新日期",format = "yyyy-MM-dd")
    private Date updateDate;


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

    @Column(name ="hazard_factors_id",nullable=true)
    public String getHazardFactorsEntity() {
        return hazardFactorsEntity;
    }

    public void setHazardFactorsEntity(String hazardFactorsEntity) {
        this.hazardFactorsEntity = hazardFactorsEntity;
    }

    @Column(name ="risk_identification_id",nullable=true)
    public String getRiskIdentificationEntity() {
        return riskIdentificationEntity;
    }

    public void setRiskIdentificationEntity(String riskIdentificationEntity) {
        this.riskIdentificationEntity = riskIdentificationEntity;
    }

    @Column(name ="hfLevel",nullable=true)
    public String getHfLevel() {
        return hfLevel;
    }

    public void setHfLevel(String hfLevel) {
        this.hfLevel = hfLevel;
    }

    @Column(name ="manage_depart",nullable=true)
    public String getManageDepart() {
        return manageDepart;
    }

    public void setManageDepart(String manageDepart) {
        this.manageDepart = manageDepart;
    }

    @Column(name ="manage_user",nullable=true)
    public String getManageUser() {
        return manageUser;
    }

    public void setManageUser(String manageUser) {
        this.manageUser = manageUser;
    }
    @Column(name ="hfManageMeasure",nullable=true)
    public String getHfManageMeasure() {
        return hfManageMeasure;
    }

    public void setHfManageMeasure(String hfManageMeasure) {
        this.hfManageMeasure = hfManageMeasure;
    }
}
