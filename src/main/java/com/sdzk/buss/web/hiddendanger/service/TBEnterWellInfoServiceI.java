package com.sdzk.buss.web.hiddendanger.service;
import com.sdzk.buss.web.hiddendanger.entity.TBEnterWellInfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.Date;

public interface TBEnterWellInfoServiceI extends CommonService {
	
 	<T> void delete(T entity);
 	
 	<T> Serializable save(T entity);
 	
 	<T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	boolean doAddSql(TBEnterWellInfoEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	boolean doUpdateSql(TBEnterWellInfoEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	boolean doDelSql(TBEnterWellInfoEntity t);

	/**
	 * 验证下井信息是否存在
	 * @param wellDate 日期
	 * @param shift 班次
	 * @param wellMan 填卡人
	 * @param beginWellDate 下井时间开始时间
	 * @return
	 */
	public boolean isEntityExist(Date wellDate, String shift, String wellMan, Date beginWellDate);
}
