package com.sdzk.buss.web.hiddendanger.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_b_count", schema = "")
public class TBCount implements java.io.Serializable  {
    /**主键*/
    private String id;
    private String count;

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

    @Column(name ="count",nullable=true)
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
