package com.sdzk.buss.web.lightAddress.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_b_light_address", schema = "")
public class TBLightAddressEntity implements java.io.Serializable {
    private String id;
    private String lightId;
    private String addressId;

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

    @Column(name ="light_id",nullable=true,length=50)
    public String getLightId() {
        return lightId;
    }

    public void setLightId(String lightId) {
        this.lightId = lightId;
    }

    @Column(name ="address_id",nullable=true,length=50)
    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }



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


    @Column(name ="CREATE_NAME",nullable=true,length=50)
    public String getCreateName(){
        return this.createName;
    }
    public void setCreateName(String createName){
        this.createName = createName;
    }
    @Column(name ="CREATE_BY",nullable=true,length=50)
    public String getCreateBy(){
        return this.createBy;
    }
    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }
    @Column(name ="CREATE_DATE",nullable=true)
    public Date getCreateDate(){
        return this.createDate;
    }
    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }

    @Column(name ="UPDATE_NAME",nullable=true,length=50)
    public String getUpdateName(){
        return this.updateName;
    }
    public void setUpdateName(String updateName){
        this.updateName = updateName;
    }
    @Column(name ="UPDATE_BY",nullable=true,length=50)
    public String getUpdateBy(){
        return this.updateBy;
    }
    public void setUpdateBy(String updateBy){
        this.updateBy = updateBy;
    }
    @Column(name ="UPDATE_DATE",nullable=true)
    public Date getUpdateDate(){
        return this.updateDate;
    }
    public void setUpdateDate(Date updateDate){
        this.updateDate = updateDate;
    }

    private String addressName;
    @Transient
    public String getAddressName() {
        return addressName;
    }
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}
