package com.sdzk.buss.web.violations.entity;

import java.math.BigDecimal;

/**
 * Created by djs on 2017/3/23.
 */
public class TBThreeViolationsVO {
    //部门编号
    private String deptId;
    //部门名称
    private String deptName;
    //一般三违
    private String ybsw = "0";
    //典型三违
    private String dxsw = "0";
    //比较严重三违
    private String bjyzsw = "0";
    //严重三违
    private String yzsw = "0";
    //未填写违章定性
    private String countNull = "0";
    //合计
    private String sum = "0";

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getYbsw() {
        return ybsw;
    }

    public void setYbsw(String ybsw) {
        this.ybsw = ybsw;
    }

    public String getDxsw() {
        return dxsw;
    }

    public void setDxsw(String dxsw) {
        this.dxsw = dxsw;
    }

    public String getBjyzsw() {
        return bjyzsw;
    }

    public void setBjyzsw(String bjyzsw) {
        this.bjyzsw = bjyzsw;
    }

    public String getYzsw() {
        return yzsw;
    }

    public void setYzsw(String yzsw) {
        this.yzsw = yzsw;
    }

    public String getCountNull() {
        return countNull;
    }

    public void setCountNull(String countNull) {
        this.countNull = countNull;
    }

    public String getSum() {
        BigDecimal ybswDecimal = new BigDecimal(this.getYbsw());
        BigDecimal dxswDecimal = new BigDecimal(this.getDxsw());
        BigDecimal bjyzswDecimal = new BigDecimal(this.getBjyzsw());
        BigDecimal yzswDecimal = new BigDecimal(this.getYzsw());
        BigDecimal nullDecimal = new BigDecimal(this.getCountNull());

        BigDecimal sumDecimal = ybswDecimal.add(dxswDecimal).add(bjyzswDecimal).add(yzswDecimal).add(nullDecimal);
        return sumDecimal.toString();
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

}
