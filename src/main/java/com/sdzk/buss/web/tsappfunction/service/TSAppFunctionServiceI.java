package com.sdzk.buss.web.tsappfunction.service;
import com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TSAppFunctionServiceI extends CommonService {
	
 	public void delete(TSAppFunctionEntity entity) throws Exception;
 	
 	public Serializable save(TSAppFunctionEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TSAppFunctionEntity entity) throws Exception;
 	
}
