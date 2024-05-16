package com.sdzk.buss.web.tbpostmanage.service;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBPostManageServiceI extends CommonService{
	
 	public void delete(TBPostManageEntity entity) throws Exception;
 	
 	public Serializable save(TBPostManageEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBPostManageEntity entity) throws Exception;

	AjaxJson tBPostManageReportToGroup(String ids);
 	
}
