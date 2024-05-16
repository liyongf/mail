package com.sdzk.buss.web.health.vo;


/**   
 * @Title: Entity
 * @Description: 职业健康检查专项档案VO(用于导出)
 * @author hansf
 * @date 2016-03-03 10:28:51
 * @version V1.0   
 *
 */

@SuppressWarnings("serial")
public class TBHealthExamVO implements java.io.Serializable {


	/**检查时间*/
	private java.util.Date prejobChkDate;
	/**检查机构*/
	private String prejobChkOrg;
	/**检查类别*/
	private String prejobChkCategory;
	/**检查结果未见异常*/
	private String prejobNoAbnormal;
	/**检查结果职业禁忌*/
	private String prejobBan;
	/**检查结果其他疾病*/
	private String prejobOtherDiseases;
	/**处置情况上岗前*/
	private String prejobDisposition;
	/**是否书面告知上岗前*/
	private String prejobIsNotify;
	/**检查时间在岗期间*/
	private java.util.Date duringChkDate;
	/**检查机构在岗期间*/
	private String duringChkOrg;
	/**检查结果未见异常在岗期间*/
	private String duringNoAbnormal;
	/**检查结果疑是职业病在岗期间*/
	private String duringOccupationDiseases;
	/**检查结果职业禁忌在岗期间*/
	private String duringBan;
	/**检查结果其他疾病在岗期间*/
	private String duringOtherDiseases;
	/**处置情况在岗期间*/
	private String duringDisposition;
	/**是否书面告知在岗期间*/
	private String duringIsNotify;
	/**检查时间离岗时*/
	private java.util.Date leavingChkDate;
	/**检查机构离岗时*/
	private String leavingChkOrg;
	/**检查结果离岗时*/
	private String leavingChkResult;
	/**是否书面告知离岗时*/
	private String leavingIsNotify;
	/**离岗类型离岗后*/
	private String leavedType;
	/**检查时间离岗后*/
	private java.util.Date leavedChkDate;
	/**检查结果离岗后*/
	private String leavedChkResult;
	/**检查时间应急检查*/
	private java.util.Date emergChkDate;
	/**检查种类应急检查*/
	private String emergChkCategory;
	/**检查机构应急检查*/
	private String emergChkOrg;
	/**处置情况应急检查*/
	private String emergDisposition;

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
	public java.util.Date getPrejobChkDate() {
		return prejobChkDate;
	}
	public void setPrejobChkDate(java.util.Date prejobChkDate) {
		this.prejobChkDate = prejobChkDate;
	}
	public String getPrejobChkOrg() {
		return prejobChkOrg;
	}
	public void setPrejobChkOrg(String prejobChkOrg) {
		this.prejobChkOrg = prejobChkOrg;
	}
	public String getPrejobChkCategory() {
		return prejobChkCategory;
	}
	public void setPrejobChkCategory(String prejobChkCategory) {
		this.prejobChkCategory = prejobChkCategory;
	}
	public String getPrejobNoAbnormal() {
		return prejobNoAbnormal;
	}
	public void setPrejobNoAbnormal(String prejobNoAbnormal) {
		this.prejobNoAbnormal = prejobNoAbnormal;
	}
	public String getPrejobBan() {
		return prejobBan;
	}
	public void setPrejobBan(String prejobBan) {
		this.prejobBan = prejobBan;
	}
	public String getPrejobOtherDiseases() {
		return prejobOtherDiseases;
	}
	public void setPrejobOtherDiseases(String prejobOtherDiseases) {
		this.prejobOtherDiseases = prejobOtherDiseases;
	}
	public String getPrejobDisposition() {
		return prejobDisposition;
	}
	public void setPrejobDisposition(String prejobDisposition) {
		this.prejobDisposition = prejobDisposition;
	}
	public String getPrejobIsNotify() {
		return prejobIsNotify;
	}
	public void setPrejobIsNotify(String prejobIsNotify) {
		this.prejobIsNotify = prejobIsNotify;
	}
	public java.util.Date getDuringChkDate() {
		return duringChkDate;
	}
	public void setDuringChkDate(java.util.Date duringChkDate) {
		this.duringChkDate = duringChkDate;
	}
	public String getDuringChkOrg() {
		return duringChkOrg;
	}
	public void setDuringChkOrg(String duringChkOrg) {
		this.duringChkOrg = duringChkOrg;
	}
	public String getDuringNoAbnormal() {
		return duringNoAbnormal;
	}
	public void setDuringNoAbnormal(String duringNoAbnormal) {
		this.duringNoAbnormal = duringNoAbnormal;
	}
	public String getDuringOccupationDiseases() {
		return duringOccupationDiseases;
	}
	public void setDuringOccupationDiseases(
			String duringOccupationDiseases) {
		this.duringOccupationDiseases = duringOccupationDiseases;
	}
	public String getDuringBan() {
		return duringBan;
	}
	public void setDuringBan(String duringBan) {
		this.duringBan = duringBan;
	}
	public String getDuringOtherDiseases() {
		return duringOtherDiseases;
	}
	public void setDuringOtherDiseases(String duringOtherDiseases) {
		this.duringOtherDiseases = duringOtherDiseases;
	}
	public String getDuringDisposition() {
		return duringDisposition;
	}
	public void setDuringDisposition(String duringDisposition) {
		this.duringDisposition = duringDisposition;
	}
	public String getDuringIsNotify() {
		return duringIsNotify;
	}
	public void setDuringIsNotify(String duringIsNotify) {
		this.duringIsNotify = duringIsNotify;
	}
	public java.util.Date getLeavingChkDate() {
		return leavingChkDate;
	}
	public void setLeavingChkDate(java.util.Date leavingChkDate) {
		this.leavingChkDate = leavingChkDate;
	}
	public String getLeavingChkOrg() {
		return leavingChkOrg;
	}
	public void setLeavingChkOrg(String leavingChkOrg) {
		this.leavingChkOrg = leavingChkOrg;
	}
	public String getLeavingChkResult() {
		return leavingChkResult;
	}
	public void setLeavingChkResult(String leavingChkResult) {
		this.leavingChkResult = leavingChkResult;
	}
	public String getLeavingIsNotify() {
		return leavingIsNotify;
	}
	public void setLeavingIsNotify(String leavingIsNotify) {
		this.leavingIsNotify = leavingIsNotify;
	}
	public String getLeavedType() {
		return leavedType;
	}
	public void setLeavedType(String leavedType) {
		this.leavedType = leavedType;
	}
	public java.util.Date getLeavedChkDate() {
		return leavedChkDate;
	}
	public void setLeavedChkDate(java.util.Date leavedChkDate) {
		this.leavedChkDate = leavedChkDate;
	}
	public String getLeavedChkResult() {
		return leavedChkResult;
	}
	public void setLeavedChkResult(String leavedChkResult) {
		this.leavedChkResult = leavedChkResult;
	}
	public java.util.Date getEmergChkDate() {
		return emergChkDate;
	}
	public void setEmergChkDate(java.util.Date emergChkDate) {
		this.emergChkDate = emergChkDate;
	}
	public String getEmergChkCategory() {
		return emergChkCategory;
	}
	public void setEmergChkCategory(String emergChkCategory) {
		this.emergChkCategory = emergChkCategory;
	}
	public String getEmergChkOrg() {
		return emergChkOrg;
	}
	public void setEmergChkOrg(String emergChkOrg) {
		this.emergChkOrg = emergChkOrg;
	}
	public String getEmergDisposition() {
		return emergDisposition;
	}
	public void setEmergDisposition(String emergDisposition) {
		this.emergDisposition = emergDisposition;
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
