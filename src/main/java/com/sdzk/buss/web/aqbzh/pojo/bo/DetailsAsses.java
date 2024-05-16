/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月05日
 * 修改日期：2020年06月05日
 */
package com.sdzk.buss.web.aqbzh.pojo.bo;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description Bean
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月05日
 */
@Entity
@Table(name = "details_asses", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class DetailsAsses implements java.io.Serializable{

	/**
	 * @Description 主键
	 */
	private Integer id;
	/**
	 * @Description 表名id
	 */
	private Integer moduleId;
	/**
	 * @Description 项目内容treeId
	 */
	private String projectTreeId;
	/**
	 * @Description 基本要求Id
	 */
	private Integer requirementsId;
	/**
	 * @Description 分数
	 */
	private String scoreDetails;
	/**
	 * @Description 打分点code
	 */
	private String placeCode;
	/**
	 * @Description 打分人Id
	 */
	private String createBy;
	/**
	 * @Description 打分时间
	 */
	private Date createTime;
	/**
	 * @Description 修改人Id
	 */
	private String updateBy;
	/**
	 * @Description 修改时间
	 */
	private Date updateTime;
	/**
	 * @Description 考核汇总表Id
	 */
	private Integer totalAssesId;
	/**
	 * @Description
	 */
	private Integer a1;
	/**
	 * @Description 
	 */
	private Integer a2;
	/**
	 * @Description 
	 */
	private String a3;
	/**
	 * @Description 
	 */
	private String a4;
	/**
	 * @Description 
	 */
	private String a5;
	/**
	 * @Description 
	 */
	private String a6;
	/**
	 * @Description 
	 */
	private String a7;
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
	@Column(name ="module_id",nullable=true)
	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	@Column(name ="project_tree_id",nullable=true)
	public String getProjectTreeId() {
		return projectTreeId;
	}

	public void setProjectTreeId(String projectTreeId) {
		this.projectTreeId = projectTreeId;
	}
	@Column(name ="requirements_id",nullable=true)
	public Integer getRequirementsId() {
		return requirementsId;
	}

	public void setRequirementsId(Integer requirementsId) {
		this.requirementsId = requirementsId;
	}
	@Column(name ="score_details",nullable=true)
	public String getScoreDetails() {
		return scoreDetails;
	}

	public void setScoreDetails(String scoreDetails) {
		this.scoreDetails = scoreDetails;
	}
	@Column(name ="place_code",nullable=true)
	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}
	@Column(name ="create_by",nullable=true)
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	@Column(name ="create_time",nullable=true)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name ="update_by",nullable=true)
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	@Column(name ="update_time",nullable=true)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name ="total_asses_id",nullable=true)
	public Integer getTotalAssesId() {
		return totalAssesId;
	}

	public void setTotalAssesId(Integer totalAssesId) {
		this.totalAssesId = totalAssesId;
	}
	@Column(name ="a1",nullable=true)
	public Integer getA1() {
		return a1;
	}

	public void setA1(Integer a1) {
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
	@Column(name ="a5",nullable=true)
	public String getA5() {
		return a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}
	@Column(name ="a6",nullable=true)
	public String getA6() {
		return a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}
	@Column(name ="a7",nullable=true)
	public String getA7() {
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public DetailsAsses(Integer id, Integer moduleId, String projectTreeId, Integer requirementsId, String scoreDetails, String placeCode, String createBy, Date createTime, String updateBy, Date updateTime, Integer totalAssesId, Integer a1, Integer a2, String a3, String a4, String a5, String a6, String a7) {
		this.id = id;
		this.moduleId = moduleId;
		this.projectTreeId = projectTreeId;
		this.requirementsId = requirementsId;
		this.scoreDetails = scoreDetails;
		this.placeCode = placeCode;
		this.createBy = createBy;
		this.createTime = createTime;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
		this.totalAssesId = totalAssesId;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.a5 = a5;
		this.a6 = a6;
		this.a7 = a7;
	}

	public DetailsAsses() {
	}
}
