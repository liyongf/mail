package com.sdzk.buss.web.health.vo;


/**   
 * @Title: Entity
 * @Description: 职业健康疗养专项档案(用于导出)
 * @author hansf
 * @date 2016-03-03 16:44:40
 * @version V1.0   
 */
@SuppressWarnings("serial")
public class TBHealthRecuperateVO implements java.io.Serializable {
	/**职工基本信息ID*/
	private String employeeId;
	/**疗养时间*/
	private java.util.Date recupDate;
	/**疗养周期年*/
	private String recupCycle;
	/**疗养地点*/
	private String recupAddress;
	/**下次疗养时间*/
	private java.util.Date nextRecupDate;
	/**今年是否疗养*/
	private String thisYearIsRecup;
	
	/**档案号*/
	private String fileNo;
	/**在岗编号*/
	private String postNumber;
	/**退休编号*/
	private String retireNumber;
	/**姓名*/
	private String name;
	/**性别*/
	private String gender;
	/**身份证号*/
	private String cardNumber;
	/**参加工作时间*/
	private java.util.Date partiWorkDate;
	/**现工作单位*/
	private String currWorkUnits;
	/**现从事工种*/
	private String currTrade;
	/**接触职业危害种类*/
	private String jobHazardCategory;
	/**岗位类别*/
	private String postCategory;
	/**岗位状态*/
	private String postStatus;
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public java.util.Date getRecupDate() {
		return recupDate;
	}
	public void setRecupDate(java.util.Date recupDate) {
		this.recupDate = recupDate;
	}
	public String getRecupCycle() {
		return recupCycle;
	}
	public void setRecupCycle(String recupCycle) {
		this.recupCycle = recupCycle;
	}
	public String getRecupAddress() {
		return recupAddress;
	}
	public void setRecupAddress(String recupAddress) {
		this.recupAddress = recupAddress;
	}
	public java.util.Date getNextRecupDate() {
		return nextRecupDate;
	}
	public void setNextRecupDate(java.util.Date nextRecupDate) {
		this.nextRecupDate = nextRecupDate;
	}
	public String getThisYearIsRecup() {
		return thisYearIsRecup;
	}
	public void setThisYearIsRecup(String thisYearIsRecup) {
		this.thisYearIsRecup = thisYearIsRecup;
	}
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public String getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(String postNumber) {
		this.postNumber = postNumber;
	}
	public String getRetireNumber() {
		return retireNumber;
	}
	public void setRetireNumber(String retireNumber) {
		this.retireNumber = retireNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public java.util.Date getPartiWorkDate() {
		return partiWorkDate;
	}
	public void setPartiWorkDate(java.util.Date partiWorkDate) {
		this.partiWorkDate = partiWorkDate;
	}
	public String getCurrWorkUnits() {
		return currWorkUnits;
	}
	public void setCurrWorkUnits(String currWorkUnits) {
		this.currWorkUnits = currWorkUnits;
	}
	public String getCurrTrade() {
		return currTrade;
	}
	public void setCurrTrade(String currTrade) {
		this.currTrade = currTrade;
	}
	public String getJobHazardCategory() {
		return jobHazardCategory;
	}
	public void setJobHazardCategory(String jobHazardCategory) {
		this.jobHazardCategory = jobHazardCategory;
	}
	public String getPostCategory() {
		return postCategory;
	}
	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}
	public String getPostStatus() {
		return postStatus;
	}
	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}
	
	
}
