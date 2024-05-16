package com.sdzk.buss.web.dangersource.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lenovo on 17-8-14.
 */
@Entity
@Table(name = "t_b_work_process_manage", schema = "")
@SuppressWarnings("serial")
public class TBWorkProcessManageEntity implements java.io.Serializable{
    /**唯一编号*/
    private String id;
    /**作业过程名称*/
    private String name;
    /**专业*/
    private String major;
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
    /**是否删除*/
    private String isDelete;

    private String majorTemp;
    @Transient
    public String getMajorTemp() {
        return majorTemp;
    }

    public void setMajorTemp(String majorTemp) {
        this.majorTemp = majorTemp;
    }

    private String count;
    @Transient
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="id",nullable=false,length=36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name ="name",nullable=true,length=40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name ="major",nullable=true,length=40)
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
    @Column(name ="create_by",nullable=true,length=50)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    @Column(name ="create_name",nullable=true,length=50)
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
    @Column(name ="update_name",nullable=true,length=50)
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
    @Column(name ="is_delete",nullable=true,length=50)
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
