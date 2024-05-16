package com.sdzk.buss.web.dangersource.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;

import javax.persistence.*;

/**
 * Created by Lenovo on 17-8-14.
 */
@Entity
@Table(name = "t_b_work_danger_rel", schema = "")
@SuppressWarnings("serial")
public class TBWorkDangerRelEntity implements java.io.Serializable{
    /**主键*/
    private String id;
    /**危险源ID*/
    @Excel(name="危险源ID")
    private TBDangerSourceEntity danger;
    /**所属作业过程ID*/
    @Excel(name="所属作业过程ID")
    private TBWorkProcessManageEntity workprocess;

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
    @JoinColumn(name = "danger_id")
    public TBDangerSourceEntity getDanger() {
        return danger;
    }

    public void setDanger(TBDangerSourceEntity danger) {
        this.danger = danger;
    }
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workpro_id")
    public TBWorkProcessManageEntity getWorkprocess() {
        return workprocess;
    }

    public void setWorkprocess(TBWorkProcessManageEntity workprocess) {
        this.workprocess = workprocess;
    }
}
