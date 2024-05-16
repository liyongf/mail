package com.sdzk.buss.web.quartz;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 17-9-20.
 */
@Entity
@Table(name = "t_b_quartz_log", schema = "")
public class TBQuartzLog implements java.io.Serializable {

    private String id;// '主键',
    private String busId;// '隐患主键',
    private String curHiddenNature;//'当前隐患级别',
    private String upHiddenNature;// '升级后隐患级别',
    private Date upDate;

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

    @Column(name ="bus_id",nullable=true,length=50)
    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    @Column(name ="cur_hidden_nature",length=50)
    public String getCurHiddenNature() {
        return curHiddenNature;
    }

    public void setCurHiddenNature(String curHiddenNature) {
        this.curHiddenNature = curHiddenNature;
    }

    @Column(name ="up_hidden_nature",length=50)
    public String getUpHiddenNature() {
        return upHiddenNature;
    }

    public void setUpHiddenNature(String upHiddenNature) {
        this.upHiddenNature = upHiddenNature;
    }

    @Column(name ="up_date")
    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }
}
