package com.sdzk.buss.web.majorhiddendanger.service.impl;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.majorhiddendanger.service.TBMajorHiddenDangerServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import org.jeecgframework.core.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import java.lang.String;
@Service("tBMajorHiddenDangerService")
@Transactional
public class TBMajorHiddenDangerServiceImpl extends CommonServiceImpl implements TBMajorHiddenDangerServiceI {

	
 	public void delete(TBMajorHiddenDangerEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TBMajorHiddenDangerEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TBMajorHiddenDangerEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TBMajorHiddenDangerEntity t) throws Exception{
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
	private void doUpdateBus(TBMajorHiddenDangerEntity t) throws Exception{
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
	private void doDelBus(TBMajorHiddenDangerEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(TBMajorHiddenDangerEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("hd_location", t.getHdLocation());
		map.put("inve_date", t.getInveDate());
		map.put("hd_info_source", t.getHdInfoSource());
		map.put("hd_level", t.getHdLevel());
		map.put("hd_cate", t.getHdCate());
		map.put("hd_major", t.getHdMajor());
		map.put("hd_desc", t.getHdDesc());
		map.put("verify_date", t.getVerifyDate());
		map.put("verify_unit", t.getVerifyUnit());
		map.put("verify_status", t.getVerifyStatus());
		map.put("verify_man", t.getVerifyMan());
		map.put("rect_unit", t.getRectUnit());
		map.put("rect_man", t.getRectMan());
		map.put("rect_period", t.getRectPeriod());
		map.put("rect_measures", t.getRectMeasures());
		map.put("rect_tagart_dt", t.getRectTagartDt());
		map.put("acceptor", t.getAcceptor());
		map.put("accep_time", t.getAccepTime());
		map.put("accep_report", t.getAccepReport());
		map.put("reviewer", t.getReviewer());
		map.put("review_time", t.getReviewTime());
		map.put("review_report", t.getReviewReport());
		map.put("is_ls_sub", t.getIsLsSub());
		map.put("is_ls_prov", t.getIsLsProv());
		map.put("cl_status", t.getClStatus());
		map.put("cancel_date", t.getCancelDate());
		map.put("goal_ach", t.getGoalAch());
		map.put("goal_ach_date", t.getGoalAchDate());
		map.put("is_goal_achieve", t.getIsGoalAchieve());
		map.put("resp_ach", t.getRespAch());
		map.put("resp_ach_date", t.getRespAchDate());
		map.put("is_resp_achieve", t.getIsRespAchieve());
		map.put("measure_ach", t.getMeasureAch());
		map.put("measure_ach_date", t.getMeasureAchDate());
		map.put("is_measure_achieve", t.getIsMeasureAchieve());
		map.put("fund_ach", t.getFundAch());
		map.put("fund_ach_date", t.getFundAchDate());
		map.put("is_fund_achieve", t.getIsFundAchieve());
		map.put("plan_ach", t.getPlanAch());
		map.put("plan_ach_date", t.getPlanAchDate());
		map.put("is_plan_achieve", t.getIsPlanAchieve());
		map.put("gove_plan", t.getGovePlan());
		map.put("gove_plan_date", t.getGovePlanDate());
		map.put("is_gove_plan_achieve", t.getIsGovePlanAchieve());
		map.put("create_by", t.getCreateBy());
		map.put("create_name", t.getCreateName());
		map.put("create_date", t.getCreateDate());
		map.put("update_by", t.getUpdateBy());
		map.put("update_name", t.getUpdateName());
		map.put("update_date", t.getUpdateDate());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TBMajorHiddenDangerEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{hd_location}",String.valueOf(t.getHdLocation()));
 		sql  = sql.replace("#{inve_date}",String.valueOf(t.getInveDate()));
 		sql  = sql.replace("#{hd_info_source}",String.valueOf(t.getHdInfoSource()));
 		sql  = sql.replace("#{hd_level}",String.valueOf(t.getHdLevel()));
 		sql  = sql.replace("#{hd_cate}",String.valueOf(t.getHdCate()));
 		sql  = sql.replace("#{hd_major}",String.valueOf(t.getHdMajor()));
 		sql  = sql.replace("#{hd_desc}",String.valueOf(t.getHdDesc()));
 		sql  = sql.replace("#{verify_date}",String.valueOf(t.getVerifyDate()));
 		sql  = sql.replace("#{verify_unit}",String.valueOf(t.getVerifyUnit()));
 		sql  = sql.replace("#{verify_status}",String.valueOf(t.getVerifyStatus()));
 		sql  = sql.replace("#{verify_man}",String.valueOf(t.getVerifyMan()));
 		sql  = sql.replace("#{rect_unit}",String.valueOf(t.getRectUnit()));
 		sql  = sql.replace("#{rect_man}",String.valueOf(t.getRectMan()));
 		sql  = sql.replace("#{rect_period}",String.valueOf(t.getRectPeriod()));
 		sql  = sql.replace("#{rect_measures}",String.valueOf(t.getRectMeasures()));
 		sql  = sql.replace("#{rect_tagart_dt}",String.valueOf(t.getRectTagartDt()));
 		sql  = sql.replace("#{acceptor}",String.valueOf(t.getAcceptor()));
 		sql  = sql.replace("#{accep_time}",String.valueOf(t.getAccepTime()));
 		sql  = sql.replace("#{accep_report}",String.valueOf(t.getAccepReport()));
 		sql  = sql.replace("#{reviewer}",String.valueOf(t.getReviewer()));
 		sql  = sql.replace("#{review_time}",String.valueOf(t.getReviewTime()));
 		sql  = sql.replace("#{review_report}",String.valueOf(t.getReviewReport()));
 		sql  = sql.replace("#{is_ls_sub}",String.valueOf(t.getIsLsSub()));
 		sql  = sql.replace("#{is_ls_prov}",String.valueOf(t.getIsLsProv()));
 		sql  = sql.replace("#{cl_status}",String.valueOf(t.getClStatus()));
 		sql  = sql.replace("#{cancel_date}",String.valueOf(t.getCancelDate()));
 		sql  = sql.replace("#{goal_ach}",String.valueOf(t.getGoalAch()));
 		sql  = sql.replace("#{goal_ach_date}",String.valueOf(t.getGoalAchDate()));
 		sql  = sql.replace("#{is_goal_achieve}",String.valueOf(t.getIsGoalAchieve()));
 		sql  = sql.replace("#{resp_ach}",String.valueOf(t.getRespAch()));
 		sql  = sql.replace("#{resp_ach_date}",String.valueOf(t.getRespAchDate()));
 		sql  = sql.replace("#{is_resp_achieve}",String.valueOf(t.getIsRespAchieve()));
 		sql  = sql.replace("#{measure_ach}",String.valueOf(t.getMeasureAch()));
 		sql  = sql.replace("#{measure_ach_date}",String.valueOf(t.getMeasureAchDate()));
 		sql  = sql.replace("#{is_measure_achieve}",String.valueOf(t.getIsMeasureAchieve()));
 		sql  = sql.replace("#{fund_ach}",String.valueOf(t.getFundAch()));
 		sql  = sql.replace("#{fund_ach_date}",String.valueOf(t.getFundAchDate()));
 		sql  = sql.replace("#{is_fund_achieve}",String.valueOf(t.getIsFundAchieve()));
 		sql  = sql.replace("#{plan_ach}",String.valueOf(t.getPlanAch()));
 		sql  = sql.replace("#{plan_ach_date}",String.valueOf(t.getPlanAchDate()));
 		sql  = sql.replace("#{is_plan_achieve}",String.valueOf(t.getIsPlanAchieve()));
 		sql  = sql.replace("#{gove_plan}",String.valueOf(t.getGovePlan()));
 		sql  = sql.replace("#{gove_plan_date}",String.valueOf(t.getGovePlanDate()));
 		sql  = sql.replace("#{is_gove_plan_achieve}",String.valueOf(t.getIsGovePlanAchieve()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
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
					javaInter.execute("t_b_major_hidden_danger",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}

    public Map<String,String> updateMajorDangerReport(String ids){
        Map<String,String> retMap = new HashMap<>();
        retMap.put("code", Constants.LOCAL_RESULT_CODE_SUCCESS);
        retMap.put("message","上报成功");
        try{
            if(StringUtil.isNotEmpty(ids)){
				String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
				updateBySqlString("update t_b_major_hidden_danger set report_date = '"+reportDate+"' , report_status = '"+Constants.REPORT_STATUS_Y+"' where id in ('"+ids.replace(",","','")+"')");
            }
        }catch(Exception e){
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message","本地数据库更新失败");
        }
        return retMap;
    }
}