package org.jeecgframework.web.system.pojo.base;
// default package

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * TTypegroup entity.
 */
@Entity
@Table(name = "t_s_typegroup")
public class TSTypegroup extends IdEntity implements java.io.Serializable {
	private String typegroupname;
	private String typegroupcode;
	private String origin;

	@Column(name = "origin", length = 2)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	private Date createDate;//创建时间
	private String createName;//创建用户
    public static Map<String, List<TSType>> allTypes = new HashMap<String,List<TSType>>();
    public static Map<String, TSTypegroup> allTypeGroups = new HashMap<String,TSTypegroup>();
	private List<TSType> TSTypes = new ArrayList<TSType>();
	@Column(name = "typegroupname", length = 50)
	public String getTypegroupname() {
		return this.typegroupname;
	}

	public void setTypegroupname(String typegroupname) {
		this.typegroupname = typegroupname;
	}

	@Column(name = "typegroupcode", length = 50)
	public String getTypegroupcode() {
		return this.typegroupcode;
	}

	public void setTypegroupcode(String typegroupcode) {
		this.typegroupcode = typegroupcode;
	}

	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name="create_name",length=36)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSTypegroup")
	public List<TSType> getTSTypes() {
		return this.TSTypes;
	}

	public void setTSTypes(List<TSType> TSTypes) {
		this.TSTypes = TSTypes;
	}

}