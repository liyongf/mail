package com.sdzk.buss.web.hiddendanger.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/4/19.
 */
@Entity
@Table(name = "t_b_hidden_danger_exam_rel", schema = "")
public class TBHiddenDangerRelExamEntity implements java.io.Serializable  {
    /**主键*/
    private String id;
    private TBHiddenDangerExamEntity tBHiddenDangerExamEntity;
    /**违章人员ID*/
    private String duty_man_id;


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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id")
    public TBHiddenDangerExamEntity gettBHiddenDangerExamEntity() {
        return tBHiddenDangerExamEntity;
    }

    public void settBHiddenDangerExamEntity(TBHiddenDangerExamEntity tBHiddenDangerExamEntity) {
        this.tBHiddenDangerExamEntity = tBHiddenDangerExamEntity;
    }

    @Column(name ="duty_man_id",nullable=true)
    public String getDuty_man_id() {
        return duty_man_id;
    }

    public void setDuty_man_id(String duty_man_id) {
        this.duty_man_id = duty_man_id;
    }

}
