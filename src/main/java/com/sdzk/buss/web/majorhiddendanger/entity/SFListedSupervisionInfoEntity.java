package com.sdzk.buss.web.majorhiddendanger.entity;

/**
 * @Title: Entity
 * @Description: 重大隐患挂牌督办
 * @author 张赛超
 * QQ:1228310398
 * @version V1.0
 *
 */

import java.beans.Transient;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "SF_LISTED_SUPERVISION_INFO", schema = "")
public class SFListedSupervisionInfoEntity implements java.io.Serializable {

    /**唯一标示*/
    private String id;
    /**隐患基础信息表关联键*/
    private String fkHiddenInfoId;
    /**挂牌督办时间*/
    private Date lsiLsTime;
    /**挂牌督办级别*/
    private String lsiIsLevel;
    /**挂牌督办单位*/
    private String lsiLsUnit;
    /**挂牌督办文号*/
    private String lsiShNum;
    /**创建人登录名*/
    private String createBy;
    /**创建人名称*/
    private String createName;
    /**创建时间*/
    private Date createDate;
    /**更新人登录名*/
    private String updateBy;
    /**更新人名称*/
    private String updateName;
    /**更新时间*/
    private Date updateDate;
    /**数据来源（1：内部、2：接口）*/
    private String dateSource;

    /*lsType（1：挂牌督办，2取消挂牌督办）*/
    private String lsType;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="id",nullable=false,length=32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name ="fk_hidden_info_id",nullable=true,length=32)
    public String getFkHiddenInfoId() {
        return fkHiddenInfoId;
    }

    public void setFkHiddenInfoId(String fkHiddenInfoId) {
        this.fkHiddenInfoId = fkHiddenInfoId;
    }

    @Column(name ="lsi_ls_time",nullable=true,length=6)
    public Date getLsiLsTime() {
        return lsiLsTime;
    }

    public void setLsiLsTime(Date lsiLsTime) {
        this.lsiLsTime = lsiLsTime;
    }

    @Column(name ="lsi_ls_level",nullable=true,length=50)
    public String getLsiIsLevel() {
        return lsiIsLevel;
    }

    public void setLsiIsLevel(String lsiIsLevel) {
        this.lsiIsLevel = lsiIsLevel;
    }

    @Column(name ="lsi_ls_unit",nullable=true,length=50)
    public String getLsiLsUnit() {
        return lsiLsUnit;
    }

    public void setLsiLsUnit(String lsiLsUnit) {
        this.lsiLsUnit = lsiLsUnit;
    }

    @Column(name ="lsi_sh_num",nullable=true,length=50)
    public String getLsiShNum() {
        return lsiShNum;
    }

    public void setLsiShNum(String lsiShNum) {
        this.lsiShNum = lsiShNum;
    }

    @Column(name ="create_by",nullable=true,length=50)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name ="create_name",nullable=true,length=100)
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(name ="create_date",nullable=true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name ="update_by",nullable=true,length=50)
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name ="update_name",nullable=true,length=100)
    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    @Column(name ="update_date",nullable=true)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name ="data_source",nullable=true,length=2)
    public String getDateSource() {
        return dateSource;
    }

    public void setDateSource(String dateSource) {
        this.dateSource = dateSource;
    }

    @javax.persistence.Transient
    public String getLsType() {
        return lsType;
    }

    public void setLsType(String lsType) {
        this.lsType = lsType;
    }
}
