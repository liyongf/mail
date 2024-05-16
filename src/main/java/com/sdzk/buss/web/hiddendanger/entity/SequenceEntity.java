package com.sdzk.buss.web.hiddendanger.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/4/27.
 */
@Entity
@Table(name = "sequence", schema = "")
public class SequenceEntity implements java.io.Serializable  {
    /**主键*/
    private String id;
    private String name;
    private String maxIndex;

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

    @Column(name ="name",nullable=true,length=36)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name ="maxIndex",nullable=true,length=36)
    public String getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(String maxIndex) {
        this.maxIndex = maxIndex;
    }
}

