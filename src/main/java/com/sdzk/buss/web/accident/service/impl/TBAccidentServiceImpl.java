package com.sdzk.buss.web.accident.service.impl;
import com.sdzk.buss.web.accident.service.TBAccidentServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.accident.entity.TBAccidentEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tBAccidentService")
@Transactional
public class TBAccidentServiceImpl extends CommonServiceImpl implements TBAccidentServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBAccidentEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBAccidentEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBAccidentEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBAccidentEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAccidentEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAccidentEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBAccidentEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDept().getId()));
 		sql  = sql.replace("#{accidentcode}",String.valueOf(t.getAccidentcode()));
 		sql  = sql.replace("#{accidentname}",String.valueOf(t.getAccidentname()));
 		sql  = sql.replace("#{happentime}",String.valueOf(t.getHappentime()));
 		sql  = sql.replace("#{happenaddress}",String.valueOf(t.getHappenaddress()));
 		sql  = sql.replace("#{accidenttype}",String.valueOf(t.getAccidenttype()));
 		sql  = sql.replace("#{accidentlevel}",String.valueOf(t.getAccidentlevel()));
 		sql  = sql.replace("#{accidentdetail}",String.valueOf(t.getAccidentdetail()));
 		sql  = sql.replace("#{deathnum}",String.valueOf(t.getDeathnum()));
 		sql  = sql.replace("#{heavywoundnum}",String.valueOf(t.getHeavywoundnum()));
 		sql  = sql.replace("#{minorwoundnum}",String.valueOf(t.getMinorwoundnum()));
 		sql  = sql.replace("#{directdamage}",String.valueOf(t.getDirectdamage()));
 		sql  = sql.replace("#{consequentialloss}",String.valueOf(t.getConsequentialloss()));
 		sql  = sql.replace("#{cause}",String.valueOf(t.getCause()));
 		sql  = sql.replace("#{immediatecause}",String.valueOf(t.getImmediatecause()));
 		sql  = sql.replace("#{remotecause}",String.valueOf(t.getRemotecause()));
 		sql  = sql.replace("#{accidentlesson}",String.valueOf(t.getAccidentlesson()));
 		sql  = sql.replace("#{securityclampdown}",String.valueOf(t.getSecurityclampdown()));
 		sql  = sql.replace("#{attachmenturl}",String.valueOf(t.getAttachmenturl()));
 		sql  = sql.replace("#{attachmentname}",String.valueOf(t.getAttachmentname()));
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