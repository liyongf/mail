package com.sdzk.buss.web.health.service;
import com.sdzk.buss.web.health.entity.TBEmployeeInfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBEmployeeInfoServiceI extends CommonService {
	
 	<T> void delete(T entity);
 	
 	<T> Serializable save(T entity);
 	
 	<T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	boolean doAddSql(TBEmployeeInfoEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	boolean doUpdateSql(TBEmployeeInfoEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	boolean doDelSql(TBEmployeeInfoEntity t);
}
