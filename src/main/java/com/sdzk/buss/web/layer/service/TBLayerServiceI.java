package com.sdzk.buss.web.layer.service;
import com.sdzk.buss.web.layer.entity.TBLayerEntity;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBLayerServiceI extends CommonService{
	
 	public void delete(TBLayerEntity entity) throws Exception;
 	
 	public Serializable save(TBLayerEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBLayerEntity entity) throws Exception;

	AjaxJson tBLayerReportToGroup(String ids);
 	
}
