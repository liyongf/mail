package com.sdzk.buss.web.health.service.impl;
import com.sdzk.buss.web.health.service.TBHealthTrainServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.health.entity.TBHealthTrainEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tBHealthTrainService")
@Transactional
public class TBHealthTrainServiceImpl extends CommonServiceImpl implements TBHealthTrainServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBHealthTrainEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBHealthTrainEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBHealthTrainEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBHealthTrainEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBHealthTrainEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBHealthTrainEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBHealthTrainEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{employee_id}",String.valueOf(t.getEmployeeId()));
 		sql  = sql.replace("#{prejob_train_type}",String.valueOf(t.getPrejobTrainType()));
 		sql  = sql.replace("#{prejob_train_date}",String.valueOf(t.getPrejobTrainDate()));
 		sql  = sql.replace("#{prejob_con_result}",String.valueOf(t.getPrejobConResult()));
 		sql  = sql.replace("#{prejob_train_org}",String.valueOf(t.getPrejobTrainOrg()));
 		sql  = sql.replace("#{during_train_date}",String.valueOf(t.getDuringTrainDate()));
 		sql  = sql.replace("#{during_train_org}",String.valueOf(t.getDuringTrainOrg()));
 		sql  = sql.replace("#{during_con_result}",String.valueOf(t.getDuringConResult()));
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