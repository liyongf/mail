package com.sdzk.buss.api.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "t_b_WexinOpenId", schema ="")
public class WexinOpenId {

    private  String id ;
    private  String openId;
    private  String nickname;
    private  String wexinStatus;
    private  String depart;
    private  String  user;
    private String userTemp;

    @javax.persistence.Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "openId")
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column(name = "wexinStatus")
    public String getWexinStatus() {
        return wexinStatus;
    }

    public void setWexinStatus(String wexinStatus) {
        this.wexinStatus = wexinStatus;
    }

    @Column(name = "departId")
    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }


    @Column(name = "userId")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Transient
    public String getUserTemp() {
        return userTemp;
    }

    public void setUserTemp(String userTemp) {
        this.userTemp = userTemp;
    }
}
