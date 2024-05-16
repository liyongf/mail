package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;

/**
 * TRoleFunction entity. 
 *  @author  张代浩
 */
@Entity
@Table(name = "t_s_app_role_function")
public class TSAppRoleFunction extends IdEntity implements java.io.Serializable {
	private com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity TSAppFunctionEntity;
	private org.jeecgframework.web.system.pojo.base.TSRole TSRole;
	private String operation;
	private String dataRule;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity getTSAppFunctionEntity() {
		return this.TSAppFunctionEntity;
	}

	public void setTSAppFunctionEntity(com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity TSAppFunctionEntity) {
		this.TSAppFunctionEntity = TSAppFunctionEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid")
	public org.jeecgframework.web.system.pojo.base.TSRole getTSRole() {
		return this.TSRole;
	}

	public void setTSRole(org.jeecgframework.web.system.pojo.base.TSRole TSRole) {
		this.TSRole = TSRole;
	}

	@Column(name = "operation", length = 100)
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	@Column(name = "datarule", length = 100)
	public String getDataRule() {
		return dataRule;
	}

	public void setDataRule(String dataRule) {
		this.dataRule = dataRule;
	}

}