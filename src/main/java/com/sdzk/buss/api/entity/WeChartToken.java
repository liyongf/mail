package com.sdzk.buss.api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "t_b_token", schema = "")
public class WeChartToken {
    //id
    private String Id;

    //保存的Token，定时两小时更换一次
    private String accessToken;
    //token开始时间
    private String createTime;

    @javax.persistence.Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @Column(name = "accessToken")
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Column(name = "createTime")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}
