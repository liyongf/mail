package com.sdzk.buss.web.violations.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_b_three_violations_img_rel", schema = "")
public class TBThreeViolationsImgRelEntity implements java.io.Serializable {
    private String id;
    private String mobileThreevioId;
    private String imgPath;

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

    @Column(name ="mobile_threevio_id",nullable=true,length=36)
    public String getMobileThreevioId() {
        return mobileThreevioId;
    }

    public void setMobileThreevioId(String mobileThreevioId) {
        this.mobileThreevioId = mobileThreevioId;
    }

    @Column(name ="img_path",nullable=true,length=200)
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

}
