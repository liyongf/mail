package com.sdzk.buss.web.hiddendanger.entity;

import java.math.BigDecimal;

/**
 * Created by djs on 2017/3/23.
 */
public class TBHiddenDangerExamVO {
    //部门编号
    private String deptId;
    //部门名称
    private String deptName;
    //A级隐患
    private String countLevelA = "0";
    //B级隐患
    private String countLevelB = "0";
    //C级隐患
    private String countLevelC = "0";
    //D级隐患
    private String countLevelD = "0";
    //E级隐患
    private String countLevelE = "0";
    //上级检查
    private String countSjjc = "0";
    //未填写隐患性质
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

    public String getCountLevelA() {
        return countLevelA;
    }

    public void setCountLevelA(String countLevelA) {
        this.countLevelA = countLevelA;
    }

    public String getCountLevelB() {
        return countLevelB;
    }

    public void setCountLevelB(String countLevelB) {
        this.countLevelB = countLevelB;
    }

    public String getCountLevelC() {
        return countLevelC;
    }

    public void setCountLevelC(String countLevelC) {
        this.countLevelC = countLevelC;
    }

    public String getCountLevelD() {
        return countLevelD;
    }

    public void setCountLevelD(String countLevelD) {
        this.countLevelD = countLevelD;
    }

    public String getCountLevelE() {
        return countLevelE;
    }

    public void setCountLevelE(String countLevelE) {
        this.countLevelE = countLevelE;
    }

    public String getCountSjjc() {
        return countSjjc;
    }

    public void setCountSjjc(String countSjjc) {
        this.countSjjc = countSjjc;
    }

    public String getCountNull() {
        return countNull;
    }

    public void setCountNull(String countNull) {
        this.countNull = countNull;
    }

    public String getSum() {
        BigDecimal levelADecimal = new BigDecimal(this.getCountLevelA());
        BigDecimal levelBDecimal = new BigDecimal(this.getCountLevelB());
        BigDecimal levelCDecimal = new BigDecimal(this.getCountLevelC());
        BigDecimal levelDDecimal = new BigDecimal(this.getCountLevelD());
        BigDecimal levelEDecimal = new BigDecimal(this.getCountLevelE());
        BigDecimal sjjcDecimal = new BigDecimal(this.getCountSjjc());
        BigDecimal nullDecimal = new BigDecimal(this.getCountNull());

        BigDecimal sumDecimal = levelADecimal.add(levelBDecimal).add(levelCDecimal).add(levelDDecimal).add(levelEDecimal).add(sjjcDecimal).add(nullDecimal);

        return sumDecimal.toString();
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

}
