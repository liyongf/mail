package com.sdzk.buss.web.health.vo;


/**   
 * @Title: Entity
 * @Description: 职业安全卫生教育培训专项档案VO(用于导出)
 * @author hansf
 * @date 2016-03-04 13:36:01
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class TBHealthTrainVO implements java.io.Serializable {

	/**培训类型上岗前*/
	private String prejobTrainType;
	/**培训时间上岗前*/
	private java.util.Date prejobTrainDate;
	/**综合成绩上岗前*/
	private String prejobConResult;
	/**培训机构上岗前*/
	private String prejobTrainOrg;
	/**培训时间在岗期间*/
	private java.util.Date duringTrainDate;
	/**培训机构在岗期间*/
	private String duringTrainOrg;
	/**综合成绩在岗期间*/
	private String duringConResult;
	
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
	public String getPrejobTrainType() {
		return prejobTrainType;
	}
	public void setPrejobTrainType(String prejobTrainType) {
		this.prejobTrainType = prejobTrainType;
	}
	public java.util.Date getPrejobTrainDate() {
		return prejobTrainDate;
	}
	public void setPrejobTrainDate(java.util.Date prejobTrainDate) {
		this.prejobTrainDate = prejobTrainDate;
	}
	public String getPrejobConResult() {
		return prejobConResult;
	}
	public void setPrejobConResult(String prejobConResult) {
		this.prejobConResult = prejobConResult;
	}
	public String getPrejobTrainOrg() {
		return prejobTrainOrg;
	}
	public void setPrejobTrainOrg(String prejobTrainOrg) {
		this.prejobTrainOrg = prejobTrainOrg;
	}
	public java.util.Date getDuringTrainDate() {
		return duringTrainDate;
	}
	public void setDuringTrainDate(java.util.Date duringTrainDate) {
		this.duringTrainDate = duringTrainDate;
	}
	public String getDuringTrainOrg() {
		return duringTrainOrg;
	}
	public void setDuringTrainOrg(String duringTrainOrg) {
		this.duringTrainOrg = duringTrainOrg;
	}
	public String getDuringConResult() {
		return duringConResult;
	}
	public void setDuringConResult(String duringConResult) {
		this.duringConResult = duringConResult;
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
