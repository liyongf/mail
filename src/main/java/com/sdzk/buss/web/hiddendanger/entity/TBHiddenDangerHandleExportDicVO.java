package com.sdzk.buss.web.hiddendanger.entity;


/**这里面的字段保持与excel导出的数据字典中的存在数据校验的字段一致。
 * 这里面需要注意的地方是，不能有excel列为空列，不赋t.xxxxx，否则会报错。
 * */
public class TBHiddenDangerHandleExportDicVO {
    /*检查类型*/
    private String examType;
    /**班次*/
    private String shift;
    /**地点*/
    private String address;
    /**组别*/
    private String itemId;
    /**组员*/
    private String itemUserId;
    /**检查人*/
    private String fillCardMan;
    /**责任单位*/
    private String dutyUnit;
    /**责任人*/
    private String dutyMan;
    /*督办单位*/
    private String superviseUnit;
    /**隐患类别*/
    private String hiddenDangerCategory;
    /**隐患等级*/
    private String hiddenDangerLevel;
    /**隐患类型*/
    private String hiddenDangerType;
    /**处理方式*/
    private String processMode;
    /**整改人*/
    private String modifyMan;
    /*整改班次*/
    private String modifyShift;
    /**复查人*/
    private String reviewMan;
    /*复查班次*/
    private String reviewShift;

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemUserId() {
        return itemUserId;
    }

    public void setItemUserId(String itemUserId) {
        this.itemUserId = itemUserId;
    }

    public String getFillCardMan() {
        return fillCardMan;
    }

    public void setFillCardMan(String fillCardMan) {
        this.fillCardMan = fillCardMan;
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

    public String getSuperviseUnit() {
        return superviseUnit;
    }

    public void setSuperviseUnit(String superviseUnit) {
        this.superviseUnit = superviseUnit;
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

    public String getModifyMan() {
        return modifyMan;
    }

    public void setModifyMan(String modifyMan) {
        this.modifyMan = modifyMan;
    }

    public String getModifyShift() {
        return modifyShift;
    }

    public void setModifyShift(String modifyShift) {
        this.modifyShift = modifyShift;
    }

    public String getReviewMan() {
        return reviewMan;
    }

    public void setReviewMan(String reviewMan) {
        this.reviewMan = reviewMan;
    }

    public String getReviewShift() {
        return reviewShift;
    }

    public void setReviewShift(String reviewShift) {
        this.reviewShift = reviewShift;
    }
}
