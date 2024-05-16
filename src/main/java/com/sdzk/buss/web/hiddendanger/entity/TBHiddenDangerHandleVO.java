package com.sdzk.buss.web.hiddendanger.entity;
import java.util.Date;

/**
 * Created by Administrator on 17-6-2.
 */
public class TBHiddenDangerHandleVO {
    private Integer id;
    private String departname;
    private Date examDate ;
    private String address ;
    private String realName ;
    private String problemDesc ;
    private Date reviewDate ;
    private String reviewMan ;

    public String getHandlelStatusTemp() {
        return handlelStatusTemp;
    }

    public void setHandlelStatusTemp(String handlelStatusTemp) {
        this.handlelStatusTemp = handlelStatusTemp;
    }

    private String handlelStatusTemp ;

    public String getReviewMan() {
        return reviewMan;
    }

    public void setReviewMan(String reviewMan) {
        this.reviewMan = reviewMan;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }
    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
