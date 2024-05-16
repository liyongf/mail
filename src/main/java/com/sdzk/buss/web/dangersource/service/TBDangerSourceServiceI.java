package com.sdzk.buss.web.dangersource.service;

import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;

import java.io.Serializable;
import java.util.List;

public interface TBDangerSourceServiceI extends CommonService{
	
 	public void delete(TBDangerSourceEntity entity) throws Exception;
 	
 	public Serializable save(TBDangerSourceEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBDangerSourceEntity entity) throws Exception;

    public java.util.Map<String,String> reportYearRisk(String ids, boolean isFromTask);
    public java.util.Map<String,String> callbackYearRisk(String ids);

	public AjaxJson reportMajorYearRisk(String ids);
	public AjaxJson reportMajorYearRiskToGroup(String ids);

	public AjaxJson reportYearRiskExToGroup(List<String> idList);
	public AjaxJson reportYearRiskToGroup(List<TBDangerSourceEntity> list);

	void importDataSava(ExcelImportResult<TBDangerSourceEntity> result, String seId, String type);
}