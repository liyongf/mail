package com.sdzk.buss.web.investigateplan.service.impl;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanHistEntity;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanRelEntity;
import com.sdzk.buss.web.investigateplan.service.TBInvestigatePlanServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanEntity;
import org.jeecgframework.core.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.io.Serializable;

import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("tBInvestigatePlanService")
@Transactional
public class TBInvestigatePlanServiceImpl extends CommonServiceImpl implements TBInvestigatePlanServiceI {

	
 	public void delete(TBInvestigatePlanEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TBInvestigatePlanEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TBInvestigatePlanEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}

	@Override
	public void saveOrCommit(TBInvestigatePlanEntity tBInvestigatePlan, String saveOrCommit) throws Exception{
		String histDesc = "创建成功";
		if (Constants.INVESTIGATEPLAN_TYPE_MONTH.equals(tBInvestigatePlan.getInvestigateType())) {
			if (StringUtil.isNotEmpty(tBInvestigatePlan.getMonth())) {
				String firstDate = tBInvestigatePlan.getMonth()+"-01";
				String endDate = DateUtils.getLastDay(firstDate);
				tBInvestigatePlan.setStartTime(DateUtils.str2Date(firstDate, DateUtils.date_sdf));
				tBInvestigatePlan.setEndTime(DateUtils.str2Date(endDate, DateUtils.date_sdf));
			}
		}
		if ("commit".equals(saveOrCommit)) {
			if (StringUtil.isNotEmpty(tBInvestigatePlan.getAcceptUser())) {
				tBInvestigatePlan.setAcceptTime(new Date());
				tBInvestigatePlan.setStatus(Constants.INVESTIGATEPLAN_STATUS_PENDINGINVESTIGATE);
			} else {
				tBInvestigatePlan.setStatus(Constants.INVESTIGATEPLAN_STATUS_PENDINGACCEPT);
			}
		} else {
			tBInvestigatePlan.setStatus(Constants.INVESTIGATEPLAN_STATUS_PENDINGCOMMIT);
		}
		if (StringUtil.isNotEmpty(tBInvestigatePlan.getId())) {
			TBInvestigatePlanEntity t = this.get(TBInvestigatePlanEntity.class, tBInvestigatePlan.getId());
			t.setStartTime(tBInvestigatePlan.getStartTime());
			t.setEndTime(tBInvestigatePlan.getEndTime());
			t.setCompleteTime(tBInvestigatePlan.getCompleteTime());
			t.setAcceptDepart(tBInvestigatePlan.getAcceptDepart());
			t.setAcceptUser(tBInvestigatePlan.getAcceptUser());
			t.setStatus(tBInvestigatePlan.getStatus());
			t.setRiskPointType(tBInvestigatePlan.getRiskPointType());
			this.saveOrUpdate(t);
			//清空关联信息
			this.executeSql("delete from t_b_investigate_plan_rel where plan_id='"+tBInvestigatePlan.getId()+"'");
			histDesc = "更新信息";
		} else {
			this.save(tBInvestigatePlan);
		}
		if (StringUtil.isNotEmpty(tBInvestigatePlan.getRiskPointIds())) {
			for (String id : tBInvestigatePlan.getRiskPointIds().split(",")) {
				TBInvestigatePlanRelEntity entity = new TBInvestigatePlanRelEntity();
				entity.setPlanId(tBInvestigatePlan.getId());
				entity.setObjId(id);
				entity.setPoitType(tBInvestigatePlan.getRiskPointType());
				entity.setRelType(Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT);
				save(entity);
			}
		}
		if (StringUtil.isNotEmpty(tBInvestigatePlan.getRiskIds())) {
			for (String id : tBInvestigatePlan.getRiskIds().split(",")) {
				TBInvestigatePlanRelEntity entity = new TBInvestigatePlanRelEntity();
				entity.setPlanId(tBInvestigatePlan.getId());
				entity.setObjId(id);
				entity.setRelType(Constants.INVESTIGATEPLAN_REL_TYPE_RISK);
				save(entity);
			}
		}
		//TODO 保存历史
		TBInvestigatePlanHistEntity history = new TBInvestigatePlanHistEntity();
		history.setPlanId(tBInvestigatePlan.getId());
		history.setPlanStatus(tBInvestigatePlan.getStatus());
		history.setHistDesc(histDesc);
		save(history);
	}

	//删除数据
	@Override
	public void deleteById(String ids) {
		if (StringUtil.isNotEmpty(ids)) {
			//获取所有当前状态为草稿及退回待处理的数据
			//List<String> idsList = findListbySql("select id from t_b_investigate_plan where status in ('1', '2') and id in ('"+ids.replace(",","','")+"')");
            List<String> idsList = findListbySql("select id from t_b_investigate_plan where id in ('"+ids.replace(",","','")+"')");
			StringBuffer idSb = new StringBuffer();
			if (idsList != null && idsList.size() > 0) {
				for (String id: idsList) {
					if (StringUtil.isNotEmpty(idSb.toString())) {
						idSb.append("','");
					}
					idSb.append(id);
				}
				//删除排查计划信息
				executeSql("delete from t_b_investigate_plan where id in ('"+idSb.toString()+"')");
				//删除关联风险点及危险源及隐患
				executeSql("delete from t_b_investigate_plan_rel where plan_id in ('"+idSb.toString()+"')");
				//删除历史信息
				executeSql("delete from t_b_investigate_plan_hist where plan_id in ('"+idSb.toString()+"')");
			}
		}
	}

	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TBInvestigatePlanEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TBInvestigatePlanEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(TBInvestigatePlanEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(TBInvestigatePlanEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("start_time", t.getStartTime());
		map.put("end_time", t.getEndTime());
		map.put("risk_point_type", t.getRiskPointType());
		map.put("complete_time", t.getCompleteTime());
		map.put("accept_depart", t.getAcceptDepart());
		map.put("accept_user", t.getAcceptUser());
		map.put("accept_time", t.getAcceptTime());
		map.put("investigate_time", t.getInvestigateTime());
		map.put("investigate_desc", t.getInvestigateDesc());
		map.put("status", t.getStatus());
		map.put("investigate_type", t.getInvestigateType());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("update_name", t.getUpdateName());
		map.put("update_by", t.getUpdateBy());
		map.put("update_date", t.getUpdateDate());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TBInvestigatePlanEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{start_time}",String.valueOf(t.getStartTime()));
 		sql  = sql.replace("#{end_time}",String.valueOf(t.getEndTime()));
 		sql  = sql.replace("#{risk_point_type}",String.valueOf(t.getRiskPointType()));
 		sql  = sql.replace("#{complete_time}",String.valueOf(t.getCompleteTime()));
 		sql  = sql.replace("#{accept_depart}",String.valueOf(t.getAcceptDepart()));
 		sql  = sql.replace("#{accept_user}",String.valueOf(t.getAcceptUser()));
 		sql  = sql.replace("#{accept_time}",String.valueOf(t.getAcceptTime()));
 		sql  = sql.replace("#{investigate_time}",String.valueOf(t.getInvestigateTime()));
 		sql  = sql.replace("#{investigate_desc}",String.valueOf(t.getInvestigateDesc()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{investigate_type}",String.valueOf(t.getInvestigateType()));
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
	 * 执行JAVA增强
	 */
 	private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceJavaInter){
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute("t_b_investigate_plan",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}