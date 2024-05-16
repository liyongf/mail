package com.sdzk.buss.web.dsmanagerecord.service;
import com.sdzk.buss.web.dsmanagerecord.entity.TBDsManageRecordEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBDsManageRecordServiceI extends CommonService{
	
 	public void delete(TBDsManageRecordEntity entity) throws Exception;
 	
 	public Serializable save(TBDsManageRecordEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBDsManageRecordEntity entity) throws Exception;
 	
}
