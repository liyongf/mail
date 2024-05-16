package com.sdzk.buss.web.majorhiddendanger.service;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import org.jeecgframework.core.common.service.CommonService;
import java.util.Map;
import java.io.Serializable;

public interface TBMajorHiddenDangerServiceI extends CommonService{
	
 	public void delete(TBMajorHiddenDangerEntity entity) throws Exception;
 	
 	public Serializable save(TBMajorHiddenDangerEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBMajorHiddenDangerEntity entity) throws Exception;

    public Map<String,String> updateMajorDangerReport(String ids);
}
