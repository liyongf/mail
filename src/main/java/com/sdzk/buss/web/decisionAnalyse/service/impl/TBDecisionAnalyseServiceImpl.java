package com.sdzk.buss.web.decisionAnalyse.service.impl;


import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.decisionAnalyse.service.TBDecisionAnalyseServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.UUID;

@Service("tBDecisionAnalyseService")
@Transactional
public class TBDecisionAnalyseServiceImpl extends CommonServiceImpl implements TBDecisionAnalyseServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBHiddenDangerExamEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBHiddenDangerExamEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBHiddenDangerExamEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBHiddenDangerExamEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBHiddenDangerExamEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBHiddenDangerExamEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBHiddenDangerExamEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{exam_date}",String.valueOf(t.getExamDate()));
 		sql  = sql.replace("#{shift}",String.valueOf(t.getShift()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{fill_card_man}",String.valueOf(t.getFillCardMan()));
 		sql  = sql.replace("#{duty_unit}",String.valueOf(t.getDutyUnit()));
 		sql  = sql.replace("#{duty_man}",String.valueOf(t.getDutyMan()));
        //TODO
 		//sql  = sql.replace("#{danger_id}",String.valueOf(t.getDangerId()));
 		sql  = sql.replace("#{problem_desc}",String.valueOf(t.getProblemDesc()));
 		sql  = sql.replace("#{hidden_category}",String.valueOf(t.getHiddenCategory()));
 		sql  = sql.replace("#{hidden_nature}",String.valueOf(t.getHiddenNature()));
 		sql  = sql.replace("#{begin_well_date}",String.valueOf(t.getBeginWellDate()));
 		sql  = sql.replace("#{end_well_date}",String.valueOf(t.getEndWellDate()));
 		sql  = sql.replace("#{deal_type}",String.valueOf(t.getDealType()));
 		sql  = sql.replace("#{limit_date}",String.valueOf(t.getLimitDate()));
 		sql  = sql.replace("#{limit_shift}",String.valueOf(t.getLimitShift()));
 		sql  = sql.replace("#{review_man}",String.valueOf(t.getReviewMan()));
 		sql  = sql.replace("#{exam_type}",String.valueOf(t.getExamType()));
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