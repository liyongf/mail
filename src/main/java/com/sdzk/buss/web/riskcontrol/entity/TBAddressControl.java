package com.sdzk.buss.web.riskcontrol.entity;


import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import org.hibernate.annotations.*;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_b_address_cnotrol", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBAddressControl {

    /**主键*/
    private String id;
    private TBAddressInfoEntity address;
    private TSUser belongUser;
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

    private String controlTimes;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @NotFound(action= NotFoundAction.IGNORE)
    public TBAddressInfoEntity getAddress() {
        return address;
    }

    public void setAddress(TBAddressInfoEntity address) {
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotFound(action= NotFoundAction.IGNORE)
    public TSUser getBelongUser() {
        return belongUser;
    }

    public void setBelongUser(TSUser belongUser) {
        this.belongUser = belongUser;
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

    @Transient
    public String getControlTimes() {
        return controlTimes;
    }

    public void setControlTimes(String controlTimes) {
        this.controlTimes = controlTimes;
    }
}
