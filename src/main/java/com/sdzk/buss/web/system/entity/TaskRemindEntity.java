package com.sdzk.buss.web.system.entity;

/**
 * Created by Administrator on 17-3-27.
 */

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "task_remind", schema = "")
public class TaskRemindEntity implements java.io.Serializable{
    private String id;
    private String bussId;

    private String bussType;// '1:隐患闭环中的待整改、2:超期待整改、4:安全任务闭环、3:复查、5:驳回'

    private String dutyUserId;
    private String status;// '0:未提醒  1：已经提醒',
    private Date createTime;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="id",nullable=true,length=32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name ="bussId",nullable=true,length=32)
    public String getBussId() {
        return bussId;
    }

    public void setBussId(String bussId) {
        this.bussId = bussId;
    }
    @Column(name ="bussType",nullable=true,length=16)
    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }
    @Column(name ="dutyUserId",nullable=true,length=36)
    public String getDutyUserId() {
        return dutyUserId;
    }

    public void setDutyUserId(String dutyUserId) {
        this.dutyUserId = dutyUserId;
    }
    @Column(name ="status",nullable=true,length=4)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name ="createTime",nullable=true)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
