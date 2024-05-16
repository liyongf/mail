package com.sdzk.buss.web.riskalert.service;
import com.sdzk.buss.web.riskalert.entity.TBAlertLevelSettingEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBAlertLevelSettingServiceI extends CommonService {
	
 	<T> void delete(T entity);
 	
 	<T> Serializable save(T entity);
 	
 	<T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	boolean doAddSql(TBAlertLevelSettingEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	boolean doUpdateSql(TBAlertLevelSettingEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	boolean doDelSql(TBAlertLevelSettingEntity t);
}
