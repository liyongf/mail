package com.sdzk.buss.web.health.service.impl;
import com.sdzk.buss.web.health.service.TBEmployeeInfoServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.health.entity.TBEmployeeInfoEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tBEmployeeInfoService")
@Transactional
public class TBEmployeeInfoServiceImpl extends CommonServiceImpl implements TBEmployeeInfoServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBEmployeeInfoEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBEmployeeInfoEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBEmployeeInfoEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBEmployeeInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBEmployeeInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBEmployeeInfoEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBEmployeeInfoEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{file_no}",String.valueOf(t.getFileNo()));
 		sql  = sql.replace("#{post_number}",String.valueOf(t.getPostNumber()));
 		sql  = sql.replace("#{retire_number}",String.valueOf(t.getRetireNumber()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{gender}",String.valueOf(t.getGender()));
 		sql  = sql.replace("#{card_number}",String.valueOf(t.getCardNumber()));
 		sql  = sql.replace("#{parti_work_date}",String.valueOf(t.getPartiWorkDate()));
 		sql  = sql.replace("#{curr_work_units}",String.valueOf(t.getCurrWorkUnits()));
 		sql  = sql.replace("#{curr_trade}",String.valueOf(t.getCurrTrade()));
 		sql  = sql.replace("#{job_hazard_category}",String.valueOf(t.getJobHazardCategory()));
 		sql  = sql.replace("#{post_category}",String.valueOf(t.getPostCategory()));
 		sql  = sql.replace("#{post_status}",String.valueOf(t.getPostStatus()));
 		sql  = sql.replace("#{is_delete}",String.valueOf(t.getIsDelete()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}