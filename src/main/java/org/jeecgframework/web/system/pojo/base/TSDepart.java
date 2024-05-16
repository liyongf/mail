package org.jeecgframework.web.system.pojo.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 部门机构表
 * @author  张代浩
 */
@Entity
@Table(name = "t_s_depart")
public class TSDepart extends IdEntity implements java.io.Serializable {
	private TSDepart TSPDepart;//上级部门
	@Excel(name = "部门名称")
	private String departname;//部门名称
	@Excel(name = "部门描述")
	private String description;//部门描述
	@Excel(name = "机构编码")
    private String orgCode;//机构编码
	@Excel(name = "机构类型编码")
    private String orgType;//机构编码
	@Excel(name = "电话")
	private String mobile;//电话
	@Excel(name = "传真")
	private String fax;//传真
	@Excel(name = "地址")
	private String address;//地址
    private String wx1;
    private String wx2;
    private String wx3;
    private String pho1;
    private String pho2;
    private String pho3;
    private String spelling;
    private String fullSpelling;
    @Column(name = "spelling")
    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }
    @Column(name = "full_spelling")
    public String getFullSpelling() {
        return fullSpelling;
    }

    public void setFullSpelling(String fullSpelling) {
        this.fullSpelling = fullSpelling;
    }
    @Column(name = "wx1", length = 200)
    public String getWx1() {
        return wx1;
    }

    public void setWx1(String wx1) {
        this.wx1 = wx1;
    }

    @Column(name = "wx2", length = 200)
    public String getWx2() {
        return wx2;
    }

    public void setWx2(String wx2) {
        this.wx2 = wx2;
    }

    @Column(name = "wx3", length = 200)
    public String getWx3() {
        return wx3;
    }

    public void setWx3(String wx3) {
        this.wx3 = wx3;
    }

    @Column(name = "pho1", length = 32)
    public String getPho1() {
        return pho1;
    }

    public void setPho1(String pho1) {
        this.pho1 = pho1;
    }

    @Column(name = "pho2", length = 32)
    public String getPho2() {
        return pho2;
    }

    public void setPho2(String pho2) {
        this.pho2 = pho2;
    }

    @Column(name = "pho3", length = 32)
    public String getPho3() {
        return pho3;
    }

    public void setPho3(String pho3) {
        this.pho3 = pho3;
    }

    private String departOrder;//排序
	
	private List<TSDepart> TSDeparts = new ArrayList<TSDepart>();//下属部门

    private List<TSDepart> subDepart = new ArrayList<>();

    @Transient
    public List<TSDepart> getSubDepart() {
        return subDepart;
    }

    public void setSubDepart(List<TSDepart> subDepart) {
        this.subDepart = subDepart;
    }

    private Short deleteFlag;// 状态: 0:不删除  1：删除
    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Column(name = "delete_flag",nullable = true, length = 6)
    public Short getDeleteFlag() {
        return deleteFlag;
    }

    @Excel(name = "机构类型")
    private String orgTypeTemp;

    @Transient
    public String getOrgTypeTemp() {
        return orgTypeTemp;
    }

    public void setOrgTypeTemp(String orgTypeTemp) {
        this.orgTypeTemp = orgTypeTemp;
    }

    @Excel(name = "上级部门")
    private String superiorDepart;

    @Transient
    public String getSuperiorDepart() {
        return superiorDepart;
    }

    public void setSuperiorDepart(String superiorDepart) {
        this.superiorDepart = superiorDepart;
    }

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentdepartid")
	public TSDepart getTSPDepart() {
		return this.TSPDepart;
	}

	public void setTSPDepart(TSDepart TSPDepart) {
		this.TSPDepart = TSPDepart;
	}

	@Column(name = "departname", nullable = false, length = 100)
	public String getDepartname() {
		return this.departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSPDepart")
	public List<TSDepart> getTSDeparts() {
		return TSDeparts;
	}

	public void setTSDeparts(List<TSDepart> tSDeparts) {
		TSDeparts = tSDeparts;
	}

    @Column(name = "org_code", length = 64)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "org_type", length = 1)
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

	@Column(name = "mobile", length = 32)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "fax", length = 32)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="depart_order")
	public String getDepartOrder() {
		return departOrder;
	}

	public void setDepartOrder(String departOrder) {
		this.departOrder = departOrder;
	}
	
	
	
}