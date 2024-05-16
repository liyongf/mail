package com.sdzk.buss.web.standardassess.service;
import com.sdzk.buss.web.standardassess.entity.SfMineStandardAssessEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface SfMineStandardAssessServiceI extends CommonService {
	
 	public void delete(SfMineStandardAssessEntity entity) throws Exception;
 	
 	public Serializable save(SfMineStandardAssessEntity entity) throws Exception;
 	
 	public void saveOrUpdate(SfMineStandardAssessEntity entity) throws Exception;
 	
}
