/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.pojo.bo;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description Bean
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
@Entity
@Table(name = "quality_control_module", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class QualityControlModule implements java.io.Serializable{

	/**
	 * @Description 主键
	 */
	private Integer id;

	/**
	 * 得分
	 */
	private String scoreTa;
	/**
	 * 打分
	 */
	private String playScore;
	/**
	 * 汇总表id
	 */
	private String totalAssesId;

	/**
	 * 是否打分
	 */
	private String status;

	/**
	 * @Description 模块名
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
	 * 表id
	 */
	private String idList;
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
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name ="ID",nullable=false,length=100)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Transient
	public String getScoreTa() {
		return scoreTa;
	}

	public void setScoreTa(String scoreTa) {
		this.scoreTa = scoreTa;
	}
	@Transient
	public String getPlayScore() {
		return playScore;
	}

	public void setPlayScore(String playScore) {
		this.playScore = playScore;
	}
	@Transient
	public String getTotalAssesId() {
		return totalAssesId;
	}

	public void setTotalAssesId(String totalAssesId) {
		this.totalAssesId = totalAssesId;
	}
	@Transient
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name ="module_name",nullable=true)
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	@Column(name ="parent_id",nullable=true)
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@Column(name ="is_asses",nullable=true)
	public Integer getIsAsses() {
		return isAsses;
	}

	public void setIsAsses(Integer isAsses) {
		this.isAsses = isAsses;
	}
	@Column(name ="type_code",nullable=true)
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	@Column(name ="score",nullable=true)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	@Column(name ="the_weight",nullable=true)
	public String getTheWeight() {
		return theWeight;
	}

	public void setTheWeight(String theWeight) {
		this.theWeight = theWeight;
	}
	@Column(name ="sort",nullable=true)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Transient
	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}
	@Column(name ="a1",nullable=true)
	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}
	@Column(name ="a2",nullable=true)
	public Integer getA2() {
		return a2;
	}

	public void setA2(Integer a2) {
		this.a2 = a2;
	}
	@Column(name ="a3",nullable=true)
	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}
	@Column(name ="a4",nullable=true)
	public String getA4() {
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public QualityControlModule(Integer id, String scoreTa, String playScore, String totalAssesId, String status, String moduleName, Integer parentId, Integer isAsses, String typeCode, Integer score, String theWeight, Integer sort, String idList, String a1, Integer a2, String a3, String a4) {
		this.id = id;
		this.scoreTa = scoreTa;
		this.playScore = playScore;
		this.totalAssesId = totalAssesId;
		this.status = status;
		this.moduleName = moduleName;
		this.parentId = parentId;
		this.isAsses = isAsses;
		this.typeCode = typeCode;
		this.score = score;
		this.theWeight = theWeight;
		this.sort = sort;
		this.idList = idList;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
	}

	public QualityControlModule() {
	}
}
