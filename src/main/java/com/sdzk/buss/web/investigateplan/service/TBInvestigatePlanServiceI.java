package com.sdzk.buss.web.investigateplan.service;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBInvestigatePlanServiceI extends CommonService{
	
 	public void delete(TBInvestigatePlanEntity entity) throws Exception;
 	
 	public Serializable save(TBInvestigatePlanEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBInvestigatePlanEntity entity) throws Exception;

	void saveOrCommit(TBInvestigatePlanEntity tBInvestigatePlan, String saveOrCommit) throws Exception;

	void deleteById(String ids);
}