package com.sdzk.buss.web.specialevaluation.service;
import com.sdzk.buss.web.specialevaluation.entity.TBSpecialEvaluationEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBSpecialEvaluationServiceI extends CommonService{
	
 	public void delete(TBSpecialEvaluationEntity entity) throws Exception;
 	
 	public Serializable save(TBSpecialEvaluationEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBSpecialEvaluationEntity entity) throws Exception;
 	
}
