package com.sdzk.buss.web.fine.service;

import com.sdzk.buss.web.fine.entity.TBFineEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBFineServiceI extends CommonService{
	
 	public void delete(TBFineEntity entity) throws Exception;
 	
 	public Serializable save(TBFineEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBFineEntity entity) throws Exception;

}
