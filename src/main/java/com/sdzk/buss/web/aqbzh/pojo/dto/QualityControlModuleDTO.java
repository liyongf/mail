/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.pojo.dto;


/**
 * @Description Bean
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
@SuppressWarnings("serial")
public class QualityControlModuleDTO {

	/**
	 * @Description 主键
	 */
	private Integer id;

	/**
	 * @Description 模块名1
	 */
	private String moduleName1;
	/**
	 * 得分
	 */
	private String scoreTa;
	/**
	 * 汇总表id
	 */
	private String totalAssesId;
	/**
	 * 打分
	 */
	private String playScore;

	/**
	 * 是否打分
	 */
	private String status;

	/**
	 * @Description 模块名 3
	 */
	private String moduleName2;

	/**
	 * @Description 模块名 2
	 */
	private String moduleName;
	/**
	 * @Description 父id
	 */
	private Integer parentId;

	/**
	 * @Description 是否考核
	 */
	private Integer isAsses;
	/**
	 * 类型  通用  露天 井
	 */
	private String typeCode;
	/**
	 * 分数
	 */

	private Integer score;
	/**
	 * 权重
	 */
	private String theWeight;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * @Description 树id
	 */
	private String a1;
	/**
	 * @Description 备用
	 */
	private Integer a2;
	/**
	 * @Description 备用
	 */
	private String a3;
	/**
	 * @Description 备用
	 */
	private String a4;

	/**
	 * @Description 序号
	 */
	private Integer num;
	/**
	 * 表id
	 */
	private String idList;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleName1() {
		return moduleName1;
	}

	public void setModuleName1(String moduleName1) {
		this.moduleName1 = moduleName1;
	}

	public String getScoreTa() {
		return scoreTa;
	}

	public void setScoreTa(String scoreTa) {
		this.scoreTa = scoreTa;
	}

	public String getTotalAssesId() {
		return totalAssesId;
	}

	public void setTotalAssesId(String totalAssesId) {
		this.totalAssesId = totalAssesId;
	}

	public String getPlayScore() {
		return playScore;
	}

	public void setPlayScore(String playScore) {
		this.playScore = playScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModuleName2() {
		return moduleName2;
	}

	public void setModuleName2(String moduleName2) {
		this.moduleName2 = moduleName2;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getIsAsses() {
		return isAsses;
	}

	public void setIsAsses(Integer isAsses) {
		this.isAsses = isAsses;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getTheWeight() {
		return theWeight;
	}

	public void setTheWeight(String theWeight) {
		this.theWeight = theWeight;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public Integer getA2() {
		return a2;
	}

	public void setA2(Integer a2) {
		this.a2 = a2;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA4() {
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}
}
