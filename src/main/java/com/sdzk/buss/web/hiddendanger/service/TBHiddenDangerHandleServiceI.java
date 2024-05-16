package com.sdzk.buss.web.hiddendanger.service;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface TBHiddenDangerHandleServiceI extends CommonService {
	
 	<T> void delete(T entity);
 	
 	<T> Serializable save(T entity);
 	
 	<T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @return
	 */
 	boolean doAddSql(TBHiddenDangerHandleEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @return
	 */
 	boolean doUpdateSql(TBHiddenDangerHandleEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @return
	 */
 	boolean doDelSql(TBHiddenDangerHandleEntity t);

    void deleteHidden(TBHiddenDangerHandleEntity tBHiddenDangerHandle);

    /**
	 *  上报日常隐患到集团
	 * */
	AjaxJson hiddenDangerReportToGroup(String ids);

	AjaxJson loginCountReportToGroup(List<Map<String, Object>> ids);

	AjaxJson hiddenDangerReport(String ids, boolean isFromTask);

    Map<String,String> toReportHiddenDangerCallback(String ids);
}
