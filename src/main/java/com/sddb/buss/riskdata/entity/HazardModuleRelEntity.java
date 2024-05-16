package com.sddb.buss.riskdata.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_b_hazard_module_rel", schema = "")
@SuppressWarnings("serial")
public class HazardModuleRelEntity {

    private String id;
    private String modularId;
    private String hazardId;
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name ="modular_id",nullable=true,length=50)
    public String getModularId() {
        return modularId;
    }

    public void setModularId(String modularId) {
        this.modularId = modularId;
    }
    @Column(name ="hazard_id",nullable=true,length=50)
    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }
}
