package com.sdzk.buss.web.hiddendanger.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_b_hidden_danger_img_rel", schema = "")
@SuppressWarnings("serial")
public class TBHiddenDangerImgRelEntity implements java.io.Serializable {
    private String id;
    private String mobileHiddenId;
    private String imgPath;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name ="mobile_hidden_id",nullable=true,length=36)
    public String getMobileHiddenId() {
        return mobileHiddenId;
    }

    public void setMobileHiddenId(String mobileHiddenId) {
        this.mobileHiddenId = mobileHiddenId;
    }

    @Column(name ="img_path",nullable=true,length=36)
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
