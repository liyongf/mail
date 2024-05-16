package com.sdzk.buss.web.hiddendangerhistory.service.impl;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendangerhistory.service.TBHiddenDangerHistoryServiceI;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.hiddendangerhistory.entity.TBHiddenDangerHistoryEntity;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("tBHiddenDangerHistoryService")
@Transactional
public class TBHiddenDangerHistoryServiceImpl extends CommonServiceImpl implements TBHiddenDangerHistoryServiceI {

	@Autowired
	private SystemService systemService;
	
 	public void delete(TBHiddenDangerHistoryEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TBHiddenDangerHistoryEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TBHiddenDangerHistoryEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TBHiddenDangerHistoryEntity t) throws Exception{
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
	private void doUpdateBus(TBHiddenDangerHistoryEntity t) throws Exception{
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
	private void doDelBus(TBHiddenDangerHistoryEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(TBHiddenDangerHistoryEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("fk_hidden_info_id", t.getFkHiddenInfoId());
		map.put("deal_time", t.getDealTime());
		map.put("deal_step", t.getDealStep());
		map.put("deal_desc", t.getDealDesc());
		map.put("deal_user_name", t.getDealUserName());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TBHiddenDangerHistoryEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{fk_hidden_info_id}",String.valueOf(t.getFkHiddenInfoId()));
 		sql  = sql.replace("#{deal_time}",String.valueOf(t.getDealTime()));
 		sql  = sql.replace("#{deal_step}",String.valueOf(t.getDealStep()));
 		sql  = sql.replace("#{deal_desc}",String.valueOf(t.getDealDesc()));
 		sql  = sql.replace("#{deal_user_name}",String.valueOf(t.getDealUserName()));
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
					javaInter.execute("t_b_hidden_danger_history",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}

	/**
	 * 保存历史记录
	 * @param status 当前状态
	 * @param tBMajorHiddenDangerEntity
	 */
	public void saveHistory(String status, TBMajorHiddenDangerEntity tBMajorHiddenDangerEntity) throws Exception{
		TBHiddenDangerHistoryEntity historyEntity = new TBHiddenDangerHistoryEntity();
		historyEntity.setFkHiddenInfoId(tBMajorHiddenDangerEntity.getId());
		historyEntity.setDealStep(status);
		historyEntity.setDealDesc(getHistDesc(status, tBMajorHiddenDangerEntity));
		historyEntity.setDealTime(new Date());
		historyEntity.setDealUserName(ResourceUtil.getSessionUserName().getRealName());
		this.save(historyEntity);
	}
	/**
	 * 获取历史记录说明
	 * @param status  当前状态
	 * @param tBMajorHiddenDangerEntity
	 * @return
	 */
	private String getHistDesc(String status, TBMajorHiddenDangerEntity tBMajorHiddenDangerEntity){
		StringBuffer histDesc = new StringBuffer();
		if (Constants.HIDDEN_DANGER_CLSTATUS_DRAFT.equals(status)){
			histDesc.append("[创建人]:").append(ResourceUtil.getSessionUserName().getRealName()).append("<br>")
					.append("[创建时间]:").append(DateUtils.date2Str(new Date(), DateUtils.date_sdf)).append("<br>");
		} else if (Constants.HIDDEN_DANGER_CLSTATUS_VERIFY.equals(status)){
			if (Constants.HIDDEN_DANGER_CLSTATUS_DRAFT.equals(tBMajorHiddenDangerEntity.getClStatus())){
				histDesc.append("[审核状态:]驳回").append("<br>");
			} else if (Constants.HIDDEN_DANGER_CLSTATUS_RECFITY.equals(tBMajorHiddenDangerEntity.getClStatus())){
				histDesc.append("[审核状态:]通过").append("<br>");
			}
			histDesc.append("[审核人]:").append(getUsernameById(tBMajorHiddenDangerEntity.getVerifyMan())).append("<br>")
					.append("[审核时间]:").append(DateUtils.date2Str(tBMajorHiddenDangerEntity.getVerifyDate(), DateUtils.date_sdf)).append("<br>")
					.append("[审核单位]:").append(tBMajorHiddenDangerEntity.getVerifyUnit()).append("<br>")
					.append("[审核情况]:").append(tBMajorHiddenDangerEntity.getVerifyStatus()).append("<br>");
		} else if (Constants.HIDDEN_DANGER_CLSTATUS_RECFITY.equals(status)) {
			if (Constants.HIDDEN_DANGER_CLSTATUS_REVIEW.equals(tBMajorHiddenDangerEntity.getClStatus())){
				histDesc.append("[整改状态:]整改完成").append("<br>");
			}
			histDesc.append("[整改责任人]:").append(getUsernameById(tBMajorHiddenDangerEntity.getRectMan())).append("<br>")
					.append("[整改完成时间]:").append(DateUtils.date2Str(tBMajorHiddenDangerEntity.getRectPeriod(), DateUtils.date_sdf)).append("<br>")
					.append("[整改单位]:").append(getDepartnameById(tBMajorHiddenDangerEntity.getRectUnit())).append("<br>")
					.append("[整改情况]:").append(tBMajorHiddenDangerEntity.getRectMeasures()).append("<br>");
		}else if (Constants.HIDDEN_DANGER_CLSTATUS_ACCEPT.equals(status)){
			if (Constants.HIDDEN_DANGER_CLSTATUS_RECFITY.equals(tBMajorHiddenDangerEntity.getClStatus())){
				histDesc.append("[验收状态:]驳回").append("<br>");
			} else if (Constants.HIDDEN_DANGER_CLSTATUS_REVIEW.equals(tBMajorHiddenDangerEntity.getClStatus())){
				histDesc.append("[验收状态:]通过").append("<br>");
			}
			histDesc.append("[验收人]:").append(getUsernameById(tBMajorHiddenDangerEntity.getAcceptor())).append("<br>")
					.append("[验收时间]:").append(DateUtils.date2Str(tBMajorHiddenDangerEntity.getAccepTime(), DateUtils.date_sdf)).append("<br>")
					.append("[验收情况]:").append(tBMajorHiddenDangerEntity.getAccepReport()).append("<br>");
		} else if (Constants.HIDDEN_DANGER_CLSTATUS_REVIEW.contains(status)){
			if (Constants.HIDDEN_DANGER_CLSTATUS_ACCEPT.equals(tBMajorHiddenDangerEntity.getClStatus())){
				histDesc.append("[复查状态:]驳回").append("<br>");
			} else if (Constants.HIDDEN_DANGER_CLSTATUS_FINISHED.equals(tBMajorHiddenDangerEntity.getClStatus())){
				histDesc.append("[复查状态:]通过").append("<br>");
			}
			histDesc.append("[复查人]:").append(getUsernameById(tBMajorHiddenDangerEntity.getReviewer())).append("<br>")
					.append("[复查时间]:").append(DateUtils.date2Str(tBMajorHiddenDangerEntity.getReviewTime(), DateUtils.date_sdf)).append("<br>")
					.append("[复查情况]:").append(tBMajorHiddenDangerEntity.getReviewReport()).append("<br>");
		}
		return  histDesc.toString();
	}

	private String getUsernameById(String id){
		TSBaseUser user = systemService.get(TSBaseUser.class, id);
		if (user != null) {
			return user.getRealName();
		}
		return null;
	}

	private String getDepartnameById(String id) {
		TSDepart depart = systemService.get(TSDepart.class, id);
		if (depart != null) {
			return depart.getDepartname();
		}
		return null;
	}
}