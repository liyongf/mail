package com.sdzk.buss.web.hiddendanger.entity;

import com.sdzk.buss.web.common.Constants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lenovo on 2018/1/8.
 */
@Entity
@Table(name = "t_b_hidden_danger_apply")
public class TBHiddenDangerApplyEntity implements java.io.Serializable {

    /**主键*/
    private String id;
    /**关联隐患*/
    private TBHiddenDangerExamEntity hiddenDangerId;
    /**申请的延期日期*/
    private Date limitDateNew;
    /**新的延期日期*/
    private Date limitDateNewTrue;
    /**延期原因*/
    private String applyReason;
    /**申请人*/
    private TSBaseUser applyMan;
    /**处理状态*/
    private String dealStatus;
    /**延期次数*/
    private String dealTime;
    /**受理人*/
    private TSBaseUser acceptMan;
    /**驳回理由*/
    private String refuseReason;
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


     /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name="paymentableGenerator",strategy = "uuid")
    @Column(name = "ID",nullable = false,length = 36)
    public String getId() {
        return id;
    }

     /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  主键
     */
    public void setId(String id) {
        this.id = id;
    }


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "HIDDEN_DANGER_ID",nullable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public TBHiddenDangerExamEntity getHiddenDangerId() {
        return hiddenDangerId;
    }

    public void setHiddenDangerId(TBHiddenDangerExamEntity hiddenDangerId) {
        this.hiddenDangerId = hiddenDangerId;
    }

    @Column(name = "LIMIT_DATE_NEW",nullable = false)
    public Date getLimitDateNew() {
        return limitDateNew;
    }

    public void setLimitDateNew(Date limitDateNew) {
        this.limitDateNew = limitDateNew;
    }

    @Column(name = "LIMIT_DATE_NEW_TRUE",nullable = true)
    public Date getLimitDateNewTrue() {
        return limitDateNewTrue;
    }

    public void setLimitDateNewTrue(Date limitDateNewTrue) {
        this.limitDateNewTrue = limitDateNewTrue;
    }

    @Column(name = "APPLY_REASON",nullable = true)
    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "APPLY_MAN")
    @NotFound(action = NotFoundAction.IGNORE)
    public TSBaseUser getApplyMan() {
        return applyMan;
    }

    public void setApplyMan(TSBaseUser applyMan) {
        this.applyMan = applyMan;
    }

    @Column(name = "DEAl_STATUS",nullable = true)
    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    @Column(name = "DEAL_TIME",nullable = false)
    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ACCEPT_MAN")
    @NotFound(action = NotFoundAction.IGNORE)
    public TSBaseUser getAcceptMan() {
        return acceptMan;
    }

    public void setAcceptMan(TSBaseUser acceptMan) {
        this.acceptMan = acceptMan;
    }

    @Column(name = "REFUSE_REASON",nullable = true)
    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    @Column(name ="CREATE_NAME",nullable=true,length=50)
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(name ="CREATE_BY",nullable=true,length=50)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name ="CREATE_DATE",nullable=true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name ="UPDATE_NAME",nullable=true,length=50)
    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    @Column(name ="UPDATE_BY",nullable=true,length=50)
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name ="UPDATE_DATE",nullable=true)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
