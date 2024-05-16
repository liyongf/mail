package com.sdzk.buss.web.riskalert.service.impl;

import com.sdzk.buss.web.riskalert.entity.TBAlertLevelSettingEntity;
import com.sdzk.buss.web.riskalert.service.TBAlertLevelSettingServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.UUID;

@Service("tBAlertLevelSettingService")
@Transactional
public class TBAlertLevelSettingServiceImpl extends CommonServiceImpl implements TBAlertLevelSettingServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBAlertLevelSettingEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBAlertLevelSettingEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBAlertLevelSettingEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBAlertLevelSettingEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAlertLevelSettingEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAlertLevelSettingEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBAlertLevelSettingEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{unit_type}",String.valueOf(t.getUnitType()));
 		sql  = sql.replace("#{alert_index}",String.valueOf(t.getAlertIndex()));
 		sql  = sql.replace("#{begin_threshold}",String.valueOf(t.getBeginThreshold()));
 		sql  = sql.replace("#{end_threshold}",String.valueOf(t.getEndThreshold()));
 		sql  = sql.replace("#{alert_level_name}",String.valueOf(t.getAlertLevelName()));
 		sql  = sql.replace("#{alert_level_color}",String.valueOf(t.getAlertLevelColor()));
 		sql  = sql.replace("#{belong_mine}",String.valueOf(t.getBelongMine()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}