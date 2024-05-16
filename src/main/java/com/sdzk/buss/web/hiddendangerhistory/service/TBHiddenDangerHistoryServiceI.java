package com.sdzk.buss.web.hiddendangerhistory.service;
import com.sdzk.buss.web.hiddendangerhistory.entity.TBHiddenDangerHistoryEntity;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBHiddenDangerHistoryServiceI extends CommonService{
	
 	public void delete(TBHiddenDangerHistoryEntity entity) throws Exception;
 	
 	public Serializable save(TBHiddenDangerHistoryEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBHiddenDangerHistoryEntity entity) throws Exception;

	public void saveHistory(String status, TBMajorHiddenDangerEntity tBMajorHiddenDangerEntity) throws Exception;
}
