package com.sdzk.buss.web.hiddendanger.service.impl;

import com.sdzk.buss.web.hiddendanger.entity.TBEnterWellInfoEntity;
import com.sdzk.buss.web.hiddendanger.service.TBEnterWellInfoServiceI;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Service("tBEnterWellInfoService")
@Transactional
public class TBEnterWellInfoServiceImpl extends CommonServiceImpl implements TBEnterWellInfoServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBEnterWellInfoEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBEnterWellInfoEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBEnterWellInfoEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBEnterWellInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBEnterWellInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBEnterWellInfoEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBEnterWellInfoEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{well_date}",String.valueOf(t.getWellDate()));
 		sql  = sql.replace("#{shift}",String.valueOf(t.getShift()));
 		sql  = sql.replace("#{well_nature}",String.valueOf(t.getWellNature()));
 		sql  = sql.replace("#{well_man}",String.valueOf(t.getWellMan()));
 		sql  = sql.replace("#{well_man_unit}",String.valueOf(t.getWellManUnit()));
 		sql  = sql.replace("#{post}",String.valueOf(t.getPost()));
 		sql  = sql.replace("#{well_address}",String.valueOf(t.getWellAddress()));
 		sql  = sql.replace("#{is_read_attendance}",String.valueOf(t.getIsReadAttendance()));
 		sql  = sql.replace("#{begin_well_date}",String.valueOf(t.getBeginWellDate()));
 		sql  = sql.replace("#{end_well_date}",String.valueOf(t.getEndWellDate()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 验证下井信息是否存在
	 * @param wellDate 日期
	 * @param shift 班次
	 * @param wellMan 填卡人
	 * @param beginWellDate 下井时间开始时间
     * @return
     */
	public boolean isEntityExist(Date wellDate, String shift, String wellMan, Date beginWellDate){
		CriteriaQuery cq = new CriteriaQuery(TBEnterWellInfoEntity.class);
		cq.createAlias("wellAddress","wellAddress");
		cq.createAlias("wellMan","wellMan");
		cq.eq("wellDate", wellDate);
		cq.eq("shift",shift);
		cq.eq("wellMan.id",wellMan);
		cq.add(Restrictions.sqlRestriction("date_format(begin_well_date, '%Y-%m-%d %H')='" + DateUtils.formatDate(beginWellDate, "yyyy-MM-dd HH") + "'"));
		cq.add();
		PageList pageList = this.getPageList(cq,false);
		if(pageList != null && pageList.getResultList() != null && pageList.getResultList().size() >0){
			//数据库中已经存在
			return true;
		}
		return false;
	}
}