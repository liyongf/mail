package com.sddb.buss.identification.entity;


import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_b_risk_identification_post", schema = "")
@SuppressWarnings("serial")
public class RiskIdentificationPostEntity {

    private String id;
    private TSDepart postUnit;
    @Excel(name="单位")
    private String  postUnitTemp;
    private TBPostManageEntity post;
    @Excel(name="岗位")
    private String  postTemp;
    private String riskType;
    @Excel(name="风险类型")
    private String riskTypeTemp;
    private String riskLevel;
    @Excel(name="风险等级")
    private String riskLevelTemp;
    @Excel(name="危害因素")
    private String hazardFactorsPostTemp;
    private List<RiskFactortsPostRel> postRelList;
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

    private String hazardFactortsPostNum;
    //是否删除
    private String isDel;

    private String riskTaskId;

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


    @Column(name ="risk_type",nullable=true)
    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }


    @Column(name ="risk_level",nullable=true)
    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_unit")
    @NotFound(action= NotFoundAction.IGNORE)
    public TSDepart getPostUnit() {
        return postUnit;
    }

    public void setPostUnit(TSDepart postUnit) {
        this.postUnit = postUnit;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskIdentificationPostEntity")
    public List<RiskFactortsPostRel> getPostRelList() {
        return postRelList;
    }

    public void setPostRelList(List<RiskFactortsPostRel> postRelList) {
        this.postRelList = postRelList;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    public TBPostManageEntity getPost() {
        return post;
    }

    public void setPost(TBPostManageEntity post) {
        this.post = post;
    }

    @Transient
    public String getRiskTypeTemp() {
        return riskTypeTemp;
    }

    public void setRiskTypeTemp(String riskTypeTemp) {
        this.riskTypeTemp = riskTypeTemp;
    }
    @Transient
    public String getRiskLevelTemp() {
        return riskLevelTemp;
    }

    public void setRiskLevelTemp(String riskLevelTemp) {
        this.riskLevelTemp = riskLevelTemp;
    }
    @Transient
    public String getPostUnitTemp() {
        return postUnitTemp;
    }

    public void setPostUnitTemp(String postUnitTemp) {
        this.postUnitTemp = postUnitTemp;
    }
    @Transient
    public String getPostTemp() {
        return postTemp;
    }

    public void setPostTemp(String postTemp) {
        this.postTemp = postTemp;
    }
    @Transient
    public String getHazardFactorsPostTemp() {
        return hazardFactorsPostTemp;
    }

    public void setHazardFactorsPostTemp(String hazardFactorsPostTemp) {
        this.hazardFactorsPostTemp = hazardFactorsPostTemp;
    }

    @Transient
    public String getHazardFactortsPostNum() {
        return hazardFactortsPostNum;
    }

    public void setHazardFactortsPostNum(String hazardFactortsPostNum) {
        this.hazardFactortsPostNum = hazardFactortsPostNum;
    }
    @Column(name ="is_del",nullable=true)
    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    @Column(name = "risk_task_id")
    public String getRiskTaskId() {
        return riskTaskId;
    }

    public void setRiskTaskId(String riskTaskId) {
        this.riskTaskId = riskTaskId;
    }
}
