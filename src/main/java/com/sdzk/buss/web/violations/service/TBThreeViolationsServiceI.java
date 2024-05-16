package com.sdzk.buss.web.violations.service;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.Map;

public interface TBThreeViolationsServiceI extends CommonService{
	
 	public void delete(TBThreeViolationsEntity entity) throws Exception;
 	
 	public Serializable save(TBThreeViolationsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBThreeViolationsEntity entity) throws Exception;

    Map<String,String> reportViolation(String ids, boolean isFromTask);

    Map<String,String> reportViolationToGroup(String ids, boolean isFromTask);
}
