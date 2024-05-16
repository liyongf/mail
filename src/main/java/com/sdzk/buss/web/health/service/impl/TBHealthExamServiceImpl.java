package com.sdzk.buss.web.health.service.impl;
import com.sdzk.buss.web.health.service.TBHealthExamServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.health.entity.TBHealthExamEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tBHealthExamService")
@Transactional
public class TBHealthExamServiceImpl extends CommonServiceImpl implements TBHealthExamServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBHealthExamEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBHealthExamEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBHealthExamEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBHealthExamEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBHealthExamEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBHealthExamEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBHealthExamEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{employee_id}",String.valueOf(t.getEmployeeId()));
 		sql  = sql.replace("#{prejob_chk_date}",String.valueOf(t.getPrejobChkDate()));
 		sql  = sql.replace("#{prejob_chk_org}",String.valueOf(t.getPrejobChkOrg()));
 		sql  = sql.replace("#{prejob_chk_category}",String.valueOf(t.getPrejobChkCategory()));
 		sql  = sql.replace("#{prejob_no_abnormal}",String.valueOf(t.getPrejobNoAbnormal()));
 		sql  = sql.replace("#{prejob_ban}",String.valueOf(t.getPrejobBan()));
 		sql  = sql.replace("#{prejob_other_diseases}",String.valueOf(t.getPrejobOtherDiseases()));
 		sql  = sql.replace("#{prejob_disposition}",String.valueOf(t.getPrejobDisposition()));
 		sql  = sql.replace("#{prejob_is_notify}",String.valueOf(t.getPrejobIsNotify()));
 		sql  = sql.replace("#{during_chk_date}",String.valueOf(t.getDuringChkDate()));
 		sql  = sql.replace("#{during_chk_org}",String.valueOf(t.getDuringChkOrg()));
 		sql  = sql.replace("#{during_no_abnormal}",String.valueOf(t.getDuringNoAbnormal()));
 		sql  = sql.replace("#{during_occupation_diseases}",String.valueOf(t.getDuringOccupationDiseases()));
 		sql  = sql.replace("#{during_ban}",String.valueOf(t.getDuringBan()));
 		sql  = sql.replace("#{during_other_diseases}",String.valueOf(t.getDuringOtherDiseases()));
 		sql  = sql.replace("#{during_disposition}",String.valueOf(t.getDuringDisposition()));
 		sql  = sql.replace("#{during_is_notify}",String.valueOf(t.getDuringIsNotify()));
 		sql  = sql.replace("#{leaving_chk_date}",String.valueOf(t.getLeavingChkDate()));
 		sql  = sql.replace("#{leaving_chk_org}",String.valueOf(t.getLeavingChkOrg()));
 		sql  = sql.replace("#{leaving_chk_result}",String.valueOf(t.getLeavingChkResult()));
 		sql  = sql.replace("#{leaving_is_notify}",String.valueOf(t.getLeavingIsNotify()));
 		sql  = sql.replace("#{leaved_type}",String.valueOf(t.getLeavedType()));
 		sql  = sql.replace("#{leaved_chk_date}",String.valueOf(t.getLeavedChkDate()));
 		sql  = sql.replace("#{leaved_chk_result}",String.valueOf(t.getLeavedChkResult()));
 		sql  = sql.replace("#{emerg_chk_date}",String.valueOf(t.getEmergChkDate()));
 		sql  = sql.replace("#{emerg_chk_category}",String.valueOf(t.getEmergChkCategory()));
 		sql  = sql.replace("#{emerg_chk_org}",String.valueOf(t.getEmergChkOrg()));
 		sql  = sql.replace("#{emerg_disposition}",String.valueOf(t.getEmergDisposition()));
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