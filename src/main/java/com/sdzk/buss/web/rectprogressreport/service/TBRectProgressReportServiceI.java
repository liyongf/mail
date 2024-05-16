package com.sdzk.buss.web.rectprogressreport.service;
import com.sdzk.buss.web.rectprogressreport.entity.TBRectProgressReportEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBRectProgressReportServiceI extends CommonService{
	
 	public void delete(TBRectProgressReportEntity entity) throws Exception;
 	
 	public Serializable save(TBRectProgressReportEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBRectProgressReportEntity entity) throws Exception;
 	
}
