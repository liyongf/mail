package com.sdzk.buss.web.tbpostmanage.entity;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "t_b_post_user")
public class TBPostUser extends IdEntity implements java.io.Serializable {
	private TSUser TSUser;
	private TBPostManageEntity TBPostManage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public TSUser getTSUser() {
		return this.TSUser;
	}

	public void setTSUser(TSUser TSUser) {
		this.TSUser = TSUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	public TBPostManageEntity getTBPostManage() {
		return this.TBPostManage;
	}

	public void setTBPostManage(TBPostManageEntity TBPostManage) {
		this.TBPostManage = TBPostManage;
	}

}