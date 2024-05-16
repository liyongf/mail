package com.sdzk.buss.web.hiddendanger.entity;


/**这里面的字段保持与excel导出的数据字典中的存在数据校验的字段一致。
 * 这里面需要注意的地方是，不能有excel列为空列，不赋t.xxxxx，否则会报错。
 * */
public class TBHiddenDangerExamExportDicVO {
    /**班次*/
    private String shift;
    /**地点*/
    private String address;
    /**责任单位*/
    private String dutyUnit;
    /**责任人*/
    private String dutyMan;
    /**隐患类别*/
    private String hiddenDangerCategory;
    /**隐患等级*/
    private String hiddenDangerLevel;
    /**隐患类型*/
    private String hiddenDangerType;
    /**处理方式*/
    private String processMode;
    /**复查人*/
    private String reviewMan;
    //检查人是其他里面的
    /**检查人*/
    private String inspector;
    //组别和组员是矿井安全大检查里面的
    /**组别*/
    private String group;
    /**组员*/
    private String member;
    /**信息来源*/
    private String manageType;
    /**风险类型修改为隐患类型*/
    private String riskType;

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDutyUnit() {
        return dutyUnit;
    }

    public void setDutyUnit(String dutyUnit) {
        this.dutyUnit = dutyUnit;
    }

    public String getDutyMan() {
        return dutyMan;
    }

    public void setDutyMan(String dutyMan) {
        this.dutyMan = dutyMan;
    }

    public String getHiddenDangerCategory() {
        return hiddenDangerCategory;
    }

    public void setHiddenDangerCategory(String hiddenDangerCategory) {
        this.hiddenDangerCategory = hiddenDangerCategory;
    }

    public String getHiddenDangerLevel() {
        return hiddenDangerLevel;
    }

    public void setHiddenDangerLevel(String hiddenDangerLevel) {
        this.hiddenDangerLevel = hiddenDangerLevel;
    }

    public String getHiddenDangerType() {
        return hiddenDangerType;
    }

    public void setHiddenDangerType(String hiddenDangerType) {
        this.hiddenDangerType = hiddenDangerType;
    }

    public String getProcessMode() {
        return processMode;
    }

    public void setProcessMode(String processMode) {
        this.processMode = processMode;
    }

    public String getReviewMan() {
        return reviewMan;
    }

    public void setReviewMan(String reviewMan) {
        this.reviewMan = reviewMan;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }
}
