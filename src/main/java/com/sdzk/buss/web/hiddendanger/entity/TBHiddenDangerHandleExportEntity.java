package com.sdzk.buss.web.hiddendanger.entity;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

import java.util.Date;

public class TBHiddenDangerHandleExportEntity implements java.io.Serializable {
	@Excel(name="检查类型")
	private String examType;
	@Excel(name="日期")
	private Date examDate;
	@Excel(name="班次")
	private String shift;
	@Excel(name="地点")
	private String address;
	@Excel(name="上级检查部门")
	private String sjjcDept;
	@Excel(name="检查人（上级检查）")
	private String sjjcCheckMan;
	@Excel(name="组别")
	private String itemId;
	@Excel(name="组员")
	private String itemUserId;
	@Excel(name="检查人")
    private String fillCardManId;
	@Excel(name="责任单位")
	private String dutyUnit;
	@Excel(name="责任人")
	private String dutyMan;
	@Excel(name="督办单位")
	private String superviseUnit;
	@Excel(name="隐患等级")
	private String hiddenDangerLevel;
	@Excel(name="隐患类别")
	private String hiddenCategory;
	@Excel(name="隐患类型")
	private String hiddenDangerType;
	@Excel(name="问题描述")
	private String problemDesc;
	@Excel(name="备注")
	private String remark;
	@Excel(name="处理方式")
	private String dealType;
	@Excel(name="限期日期")
	private Date limitDate;
	@Excel(name="整改人")
	private String modifyMan;
	@Excel(name="整改日期")
	private Date modifyDate;
	@Excel(name="整改班次")
	private String modifyShift;
	@Excel(name="整改措施")
	private String rectMeasures;
	@Excel(name="复查人")
	private String reviewMan;
	@Excel(name="复查日期")
	private Date reviewDate;
	@Excel(name="复查班次")
	private String reviewShift;
	@Excel(name="复查情况")
	private String reviewReport;

	/**地点*/
	private TBAddressInfoEntity addressId;
	/**责任单位*/
	private TSDepart dutyUnitId;
	/**督办单位*/
	private TSDepart superviseUnitId;
    /**复查人*/
    private TSUser reviewManId;
    /**检查人*/
    private String fillCardManName;

    public String getFillCardManName() {
        return fillCardManName;
    }

    public void setFillCardManName(String fillCardManName) {
        this.fillCardManName = fillCardManName;
    }

    public TSUser getReviewManId() {
        return reviewManId;
    }

    public void setReviewManId(TSUser reviewManId) {
        this.reviewManId = reviewManId;
    }

    public TSDepart getSuperviseUnitId() {
		return superviseUnitId;
	}

	public void setSuperviseUnitId(TSDepart superviseUnitId) {
		this.superviseUnitId = superviseUnitId;
	}

	public TSDepart getDutyUnitId() {
		return dutyUnitId;
	}

	public void setDutyUnitId(TSDepart dutyUnitId) {
		this.dutyUnitId = dutyUnitId;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
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

	public String getSjjcDept() {
		return sjjcDept;
	}

	public void setSjjcDept(String sjjcDept) {
		this.sjjcDept = sjjcDept;
	}

	public String getSjjcCheckMan() {
		return sjjcCheckMan;
	}

	public void setSjjcCheckMan(String sjjcCheckMan) {
		this.sjjcCheckMan = sjjcCheckMan;
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

	public String getFillCardManId() {
		return fillCardManId;
	}

	public void setFillCardManId(String fillCardManId) {
		this.fillCardManId = fillCardManId;
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

	public String getHiddenDangerLevel() {
		return hiddenDangerLevel;
	}

	public void setHiddenDangerLevel(String hiddenDangerLevel) {
		this.hiddenDangerLevel = hiddenDangerLevel;
	}

	public String getHiddenCategory() {
		return hiddenCategory;
	}

	public void setHiddenCategory(String hiddenCategory) {
		this.hiddenCategory = hiddenCategory;
	}

	public String getHiddenDangerType() {
		return hiddenDangerType;
	}

	public void setHiddenDangerType(String hiddenDangerType) {
		this.hiddenDangerType = hiddenDangerType;
	}

	public String getProblemDesc() {
		return problemDesc;
	}

	public void setProblemDesc(String problemDesc) {
		this.problemDesc = problemDesc;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	public String getModifyMan() {
		return modifyMan;
	}

	public void setModifyMan(String modifyMan) {
		this.modifyMan = modifyMan;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyShift() {
		return modifyShift;
	}

	public void setModifyShift(String modifyShift) {
		this.modifyShift = modifyShift;
	}

	public String getRectMeasures() {
		return rectMeasures;
	}

	public void setRectMeasures(String rectMeasures) {
		this.rectMeasures = rectMeasures;
	}

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

	public String getReviewShift() {
		return reviewShift;
	}

	public void setReviewShift(String reviewShift) {
		this.reviewShift = reviewShift;
	}

	public String getReviewReport() {
		return reviewReport;
	}

	public void setReviewReport(String reviewReport) {
		this.reviewReport = reviewReport;
	}

	public TBAddressInfoEntity getAddressId() {
		return addressId;
	}

	public void setAddressId(TBAddressInfoEntity addressId) {
		this.addressId = addressId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
