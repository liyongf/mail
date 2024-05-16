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
@Table(name = "quality_control_project", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class QualityControlProject implements java.io.Serializable{

	/**
	 * @Description 主键
	 */
	private Integer id;
	/**
	 * @Description 项目名称
	 */
	private String projectName;
	/**
	 * @Description 总分
	 */
	private Integer totalScore;
	/**
	 * @Description 排序
	 */
	private Integer sort;
	/**
	 * @Description 上级id
	 */
	private Integer parentId;
	/**
	 * @Description 树id
	 */
	private String treeId;
	/**
	 * @Description 类型 1项目  2项目内容
	 */
	private Integer type;
	/**
	 * @Description 模块id
	 */
	private Integer moduleId;
	/**
	 * @Description 状态 1 考核   2 停止考核
	 */
	private Integer status;
	/**
	 * @Description 备用
	 */
	private String a1;
	/**
	 * @Description 备用
	 */
	private String a2;
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
	@Column(name ="project_name",nullable=true)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Column(name ="total_score",nullable=true)
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	@Column(name ="sort",nullable=true)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Column(name ="parent_id",nullable=true)
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@Column(name ="tree_id",nullable=true)
	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	@Column(name ="type",nullable=true)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name ="module_id",nullable=true)
	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	@Column(name ="status",nullable=true)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name ="a1",nullable=true)
	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}
	@Column(name ="a2",nullable=true)
	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
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

	public QualityControlProject(Integer id, String projectName, Integer totalScore, Integer sort, Integer parentId, String treeId, Integer type, Integer moduleId, Integer status, String a1, String a2, String a3, String a4) {
		this.id = id;
		this.projectName = projectName;
		this.totalScore = totalScore;
		this.sort = sort;
		this.parentId = parentId;
		this.treeId = treeId;
		this.type = type;
		this.moduleId = moduleId;
		this.status = status;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
	}

	public QualityControlProject() {

	}
}
