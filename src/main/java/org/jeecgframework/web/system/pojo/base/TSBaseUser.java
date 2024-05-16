package org.jeecgframework.web.system.pojo.base;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户父类表
 * @author  张代浩
 */
@Entity
@Table(name = "t_s_base_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class TSBaseUser extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Excel(name = "用户帐号")
	private String userName;// 用户名
	@Excel(name = "真实姓名")
	private String realName;// 真实姓名
	private String browser;// 用户使用浏览器类型
	@Excel(name = "角色编码(多个角色编码用逗号分隔，非必填)")
	private String userKey;// 用户验证唯一标示
	private String password;//用户密码
	private Short activitiSync;//是否同步工作流引擎
	/*@Excel(name = "状态")*/
	private Short status;// 状态1：在线,2：离线,0：禁用
	
	private Short deleteFlag;// 状态: 0:不删除  1：删除
	
	private byte[] signature;// 签名文件
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
	@Excel(name = "组织机构编码(多个组织机构编码用逗号分隔，非必填)")
	private String departid;

    @Excel(name="角色(可多选，但是导入须单选)")
    private String roleTemp;
    @Excel(name="组织机构")
    private String departTemp;

    @Transient
    public String getRoleTemp() {
        return roleTemp;
    }

    public void setRoleTemp(String roleTemp) {
        this.roleTemp = roleTemp;
    }

    @Transient
    public String getDepartTemp() {
        return departTemp;
    }

    public void setDepartTemp(String departTemp) {
        this.departTemp = departTemp;
    }

    public void setDepartid(String departid){
		this.departid = departid;
	}
	@Column(name = "departid",length=32)
	public String getDepartid(){
		return departid;
	}

    	private TSDepart TSDepart = new TSDepart(); //部门
    private List<TSUserOrg> userOrgList = new ArrayList<TSUserOrg>();
	private TSDepart currentDepart = new TSDepart();// 当前部门
    private List<TSRoleUser> userRoleList;

    @Transient
    public List<TSRoleUser> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<TSRoleUser> userRoleList) {
        this.userRoleList = userRoleList;
    }

    @Column(name = "signature",length=3000)
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	@Column(name = "browser", length = 20)
	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	@Column(name = "userkey", length = 200)
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	public Short getActivitiSync() {
		return activitiSync;
	}
	@Column(name = "activitisync")
	public void setActivitiSync(Short activitiSync) {
		this.activitiSync = activitiSync;
	}
	
	
	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
//	@JsonIgnore    //getList查询转换为列表时处理json转换异常
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "departid")
//	public TSDepart getTSDepart() {
//		return this.TSDepart;
//	}
//
//	public void setTSDepart(TSDepart TSDepart) {
//		this.TSDepart = TSDepart;
//	}
	@Column(name = "username", nullable = false, length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "realname", length = 50)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

    @Transient
    public TSDepart getCurrentDepart() {
        return currentDepart;
    }

    public void setCurrentDepart(TSDepart currentDepart) {
        this.currentDepart = currentDepart;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "tsUser")
    public List<TSUserOrg> getUserOrgList() {
        return userOrgList;
    }

    public void setUserOrgList(List<TSUserOrg> userOrgList) {
        this.userOrgList = userOrgList;
    }

	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@Column(name = "delete_flag")
	public Short getDeleteFlag() {
		return deleteFlag;
	}

    @Transient
    public TSDepart getTSDepart() {
        if(userOrgList!=null&&userOrgList.size()>0){
            this.TSDepart = userOrgList.get(0).getTsDepart();
        }
        return this.TSDepart;
    }

    public void setTSDepart(TSDepart TSDepart) {
        this.TSDepart = TSDepart;
    }

}