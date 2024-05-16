package com.sdzk.buss.web.riskcontrol.entity;


import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_b_task_manager_order", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBTaskManagerOrder {

    /**
     * 主键
     */
    private String id;
    private TBTaskManagerControl taskManagerControl;
    private TBDangerSourceEntity dangerSourceEntity;
    private TBAddressInfoEntity addressInfoEntity;
    private String status;
    private String controlTimes;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建人登录名称
     */
    private String createBy;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新人名称
     */
    private String updateName;
    /**
     * 更新人登录名称
     */
    private String updateBy;
    /**
     * 更新日期
     */
    private Date updateDate;
    private String yeHazardCateTemp;
    private String yeRiskGradeTemp;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  主键
     */
    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public TBAddressInfoEntity getAddressInfoEntity() {
        return addressInfoEntity;
    }

    public void setAddressInfoEntity(TBAddressInfoEntity addressInfoEntity) {
        this.addressInfoEntity = addressInfoEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public TBTaskManagerControl getTaskManagerControl() {
        return taskManagerControl;
    }

    public void setTaskManagerControl(TBTaskManagerControl taskManagerControl) {
        this.taskManagerControl = taskManagerControl;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "danger_source_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public TBDangerSourceEntity getDangerSourceEntity() {
        return dangerSourceEntity;
    }

    public void setDangerSourceEntity(TBDangerSourceEntity dangerSourceEntity) {
        this.dangerSourceEntity = dangerSourceEntity;
    }

    @Column(name = "status", nullable = true, length = 50)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_DATE", nullable = true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "UPDATE_DATE", nullable = true)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Transient
    public String getYeHazardCateTemp() {
        return yeHazardCateTemp;
    }

    public void setYeHazardCateTemp(String yeHazardCateTemp) {
        this.yeHazardCateTemp = yeHazardCateTemp;
    }
    @Transient
    public String getYeRiskGradeTemp() {
        return yeRiskGradeTemp;
    }

    public void setYeRiskGradeTemp(String yeRiskGradeTemp) {
        this.yeRiskGradeTemp = yeRiskGradeTemp;
    }

    @Transient
    public String getControlTimes() {
        return controlTimes;
    }

    public void setControlTimes(String controlTimes) {
        this.controlTimes = controlTimes;
    }
}

