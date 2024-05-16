package com.sdzk.buss.web.riskUpgrade.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 17-9-23.
 * 隐患等级和风险等级映射关系
 */
@Entity
@Table(name = "t_b_hidden_dangerSource_level_mapping", schema = "")
public class TBHiddenDangerSourceLevelMapping implements java.io.Serializable{
    /**主键*/
    private String id;
    /**隐患等级*/
    private String hiddenLevel;
    /**危险源等级*/
    private String dangerSourceLevel;

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

    @Column(name ="hiddenLevel",nullable=false,length=16)
    public String getHiddenLevel() {
        return hiddenLevel;
    }

    public void setHiddenLevel(String hiddenLevel) {
        this.hiddenLevel = hiddenLevel;
    }

    @Column(name ="dangerSourceLevel",nullable=false,length=16)
    public String getDangerSourceLevel() {
        return dangerSourceLevel;
    }

    public void setDangerSourceLevel(String dangerSourceLevel) {
        this.dangerSourceLevel = dangerSourceLevel;
    }
}
