package com.sdzk.buss.web.hiddendanger.service.impl;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.DataMapServiceI;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleStepEntity;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("tBHiddenDangerHandleService")
@Transactional
public class TBHiddenDangerHandleServiceImpl extends CommonServiceImpl implements TBHiddenDangerHandleServiceI {

    @Autowired
    private SystemService systemService;
    @Autowired
	private DataMapServiceI dataMapService;

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBHiddenDangerHandleEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBHiddenDangerHandleEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBHiddenDangerHandleEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @return
	 */
 	public boolean doAddSql(TBHiddenDangerHandleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @return
	 */
 	public boolean doUpdateSql(TBHiddenDangerHandleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @return
	 */
 	public boolean doDelSql(TBHiddenDangerHandleEntity t){
	 	return true;
 	}


 	/**
	 *  将多个逗号连接起来的人名id转为名字并用逗号连接起来
	 *  userIdsToNames
	 * */
 	private String userIdsToNames(String userIds){
 		String ret = "";
 		Map<String, String> userMap = dataMapService.getUserMap();

		if(userIds == null || userIds.trim().length() == 0){
			//ret = "";
		}else{
			StringBuffer manNames = new StringBuffer();
			for (String manId : userIds.trim().split(",")){
				if (StringUtils.isNoneBlank(manNames.toString())) {
					manNames.append(",");
				}
				if(StringUtil.isNotEmpty(userMap.get(manId))){
					manNames.append(userMap.get(manId));
				}else{
					manNames.append(manId);
				}

			}
			ret = manNames.toString();
		}

 		return ret;
	}


    @Override
    public void deleteHidden(TBHiddenDangerHandleEntity tBHiddenDangerHandle) {
        TBHiddenDangerExamEntity hiddenDangerExamEntity = tBHiddenDangerHandle.getHiddenDanger();

        String sql = "delete from t_b_hidden_danger_handle where hidden_danger_id='" + hiddenDangerExamEntity.getId() + "'";
        systemService.executeSql(sql);

        //2.清空责任人表
        sql = "delete from t_b_hidden_danger_exam_rel where bus_id='" + hiddenDangerExamEntity.getId() + "'";
        systemService.executeSql(sql);

        sql = "delete from t_b_hidden_danger_exam where id='" + hiddenDangerExamEntity.getId() + "'";
        systemService.executeSql(sql);
    }

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBHiddenDangerHandleEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{hidden_danger_id}",String.valueOf(t.getHiddenDanger().getId()));
 		sql  = sql.replace("#{modify_date}",String.valueOf(t.getModifyDate()));
 		sql  = sql.replace("#{modify_shift}",String.valueOf(t.getModifyShift()));
 		sql  = sql.replace("#{modify_man}",String.valueOf(t.getModifyMan()));
 		sql  = sql.replace("#{review_date}",String.valueOf(t.getReviewDate()));
 		sql  = sql.replace("#{review_shift}",String.valueOf(t.getReviewShift()));
 		sql  = sql.replace("#{review_man}",String.valueOf(t.getReviewMan()));
 		sql  = sql.replace("#{review_result}",String.valueOf(t.getReviewResult()));
 		sql  = sql.replace("#{handlel_status}",String.valueOf(t.getHandlelStatus()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

 	@Override
	public AjaxJson hiddenDangerReportToGroup(String ids){
		AjaxJson j = new AjaxJson();

		JSONArray jsonArray = new JSONArray();
		Map<String, String> userMap = dataMapService.getUserMap();
		Map<String, String> departMap = dataMapService.getDepartMap();

		for (String id : ids.split(",")) {
			JSONObject data = new JSONObject();         //这里是包括 TBHiddenDangerHandleEntity 以及 TBHiddenDangerExamEntity 中的数据
			TBHiddenDangerHandleEntity handleEntity = systemService.get(TBHiddenDangerHandleEntity.class, id);
			if (handleEntity != null){
				JSONObject object = new JSONObject();       //这里组装的是 TBHiddenDangerExamEntity 中的数据

				/**组装TBHiddenDangerExamEntity    dangerExam**/
				TBHiddenDangerExamEntity examEntity = handleEntity.getHiddenDanger();
				object.put("id", examEntity.getId());
				object.put("examDate", DateUtils.date2Str(examEntity.getExamDate(), DateUtils.date_sdf));
				object.put("shift", examEntity.getShift());
				object.put("address", StringUtil.isNotEmpty(examEntity.getAddress()) ? examEntity.getAddress().getId() : "");
				object.put("addressName", StringUtil.isNotEmpty(examEntity.getAddress()) ? examEntity.getAddress().getId() : "");
				object.put("fillCardMan", examEntity.getFillCardManId());
				object.put("fillCardManName", userIdsToNames(examEntity.getFillCardManId()));
				object.put("dutyUnit", examEntity.getDutyUnit()==null ? "" : examEntity.getDutyUnit().getId());
				object.put("dutyUnitName", examEntity.getDutyUnit()==null ? "" : examEntity.getDutyUnit().getDepartname());
				object.put("dutyMan", examEntity.getDutyMan());		 //矿版的dutyMan现在直接存名字了，所以dutyManName必然为空，这里先保留此设计
				object.put("dutyManName", userMap.get(examEntity.getDutyMan()));		 //dutyManName必然为空，这里暂时保留此设计，防止以后有变动
				object.put("dangerSourceName", examEntity.getDangerId()==null ? "" : examEntity.getDangerId().getYeMhazardDesc());
				object.put("problemDesc", examEntity.getProblemDesc());
				object.put("itemUserId", examEntity.getItemUserId());		//此字段已经废弃
				object.put("itemUserName", "");										//itemUserId已经废弃，这里为了便于理解，暂时保留
				object.put("itemId", examEntity.getItemId());						//此字段已废弃
				object.put("itemName", "");												//itemId已经废弃，为了便于理解，暂时保留itemName
				object.put("hiddenCategory", examEntity.getHiddenCategory());
				object.put("hiddenNature", examEntity.getHiddenNature());
				object.put("beginWellDate", examEntity.getBeginWellDate()==null ? "" : DateUtils.date2Str(examEntity.getBeginWellDate(), DateUtils.datetimeFormat));
				object.put("endWellDate", examEntity.getEndWellDate()==null ? "" : DateUtils.date2Str(examEntity.getEndWellDate(), DateUtils.datetimeFormat));
				object.put("dealType",examEntity.getDealType());
				object.put("limitDate", DateUtils.date2Str(examEntity.getLimitDate(), DateUtils.date_sdf));
				object.put("limitShift", DicUtil.getTypeNameByCode("workShift", examEntity.getLimitShift()));
				object.put("reviewMan", examEntity.getReviewMan()==null ? "" : examEntity.getReviewMan().getId());
				object.put("reviewManName", examEntity.getReviewMan()==null ? "" : examEntity.getReviewMan().getRealName());
				object.put("examType",examEntity.getExamType());
				object.put("sjjcDept", examEntity.getSjjcDept());
				object.put("sjjcCheckMan", examEntity.getSjjcCheckMan());
				object.put("proType", StringUtils.isNotBlank(examEntity.getProType()) ? DicUtil.getTypeNameByCode("proType", examEntity.getProType()) : "");
				object.put("checkType",examEntity.getCheckType());
				object.put("remark",examEntity.getRemark());
				object.put("createName",examEntity.getCreateName());
				object.put("createDate", DateUtils.date2Str(examEntity.getCreateDate(), DateUtils.datetimeFormat));
				object.put("isLsSub", examEntity.getIsLsSub());
				object.put("isLsProv", examEntity.getIsLsProv());
				object.put("reportName", examEntity.getReportName());
				object.put("reportStatus", examEntity.getReportStatus());
				object.put("dangerId", examEntity.getDangerId()!=null ? examEntity.getDangerId().getId() : "");
				object.put("gzap", examEntity.getGzap());
				object.put("hiddenNatureOriginal", examEntity.getHiddenNatureOriginal());
				object.put("deductScores", examEntity.getDeductScores());
				object.put("cancelDate", examEntity.getCancelDate());
				object.put("hiddenType", examEntity.getHiddenType());
				object.put("manageType", examEntity.getManageType());
				object.put("riskType", examEntity.getRiskType());
				object.put("riskId", StringUtil.isNotEmpty(examEntity.getRiskId()) ? examEntity.getRiskId().getId() : "");
				object.put("manageDutyUnitName", examEntity.getManageDutyUnit()==null ? "" : examEntity.getManageDutyUnit().getDepartname());
				object.put("manageDutyManName", userMap.get(examEntity.getManageDutyManId()));
				/**至此，hiddendangerexamentity中的数据组装完成**/
				data.put("object",object );

				data.put("id", handleEntity.getId());
				data.put("hiddenDangerId", handleEntity.getHiddenDanger()!=null ? handleEntity.getHiddenDanger().getId() : "");
				data.put("modifyDate",DateUtils.date2Str(handleEntity.getModifyDate(), DateUtils.datetimeFormat));
				data.put("modifyShift", handleEntity.getModifyShift());
				data.put("modifyMan", handleEntity.getModifyMan());
				data.put("modifyManName", StringUtil.isNotEmpty(userMap.get(handleEntity.getModifyMan()))?userMap.get(handleEntity.getModifyMan()):handleEntity.getModifyMan());
				data.put("reviewDate", DateUtils.date2Str(handleEntity.getReviewDate(), DateUtils.datetimeFormat));
				data.put("reviewShift", handleEntity.getReviewShift());
				data.put("reviewMan", handleEntity.getReviewMan());
				data.put("reviewManName", userMap.get(handleEntity.getReviewMan()));
				data.put("reviewResult", handleEntity.getReviewResult());
				data.put("handlelStatus", handleEntity.getHandlelStatus());
				data.put("rollBackRemark", handleEntity.getRollBackRemark());
				data.put("createName", handleEntity.getCreateName());
				data.put("createBy", handleEntity.getCreateBy());
				data.put("createDate", DateUtils.date2Str(handleEntity.getCreateDate(), DateUtils.datetimeFormat));
				data.put("rectMeasures", handleEntity.getRectMeasures());
				data.put("reviewReport", handleEntity.getReviewReport());
				data.put("verifyDate", handleEntity.getVerifyDate());
				data.put("verifyUnit", handleEntity.getVerifyDate());
				data.put("verifyUnitName", departMap.get(handleEntity.getVerifyUnit()));
				data.put("verifyStatus", handleEntity.getVerifyUnit());
				data.put("verifyMan", handleEntity.getVerifyMan());
				data.put("verifyManName", departMap.get(handleEntity.getVerifyMan()));
				data.put("accepDate", handleEntity.getAccepDate());
				data.put("acceptor", handleEntity.getAcceptor());
				data.put("acceptorName", userMap.get(handleEntity.getAcceptor()));
				data.put("accepUnit", handleEntity.getAccepUnit());
				data.put("accepUnitName", departMap.get(handleEntity.getAccepUnit()));
				data.put("accepReport", handleEntity.getAccepReport());

				jsonArray.add(data);
			}
		}

		if (jsonArray.size() > 0){
			//上传
			String mineCode = ResourceUtil.getConfigByName("mine_code");
			String token = ResourceUtil.getConfigByName("token_group");
			String reportContent = jsonArray.toString();
			String hiddenDangerReport = ResourceUtil.getConfigByName("hiddenDangerReport_group");
			Map<String, Object> paramMap = new HashMap<>();

			/**
			 * 加密过程
			 * */
			String tempToken = "token=" + token + "&mineCode=" + mineCode;
			String ciphertext = null;
			try {
				ciphertext = AesUtil.encryptWithIV(tempToken, token);
			} catch (Exception e) {
				e.printStackTrace();
			}
			paramMap.put("token", ciphertext);
			paramMap.put("mineCode", mineCode);
			paramMap.put("reportContent", reportContent);
			String response = null;
			LogUtil.info("hiddenDangerReport=" + hiddenDangerReport);
			LogUtil.info("jsonArray.size()=" + jsonArray.size() + ",reportContent="+reportContent.length());
			try {
				response = HttpClientUtils.post(hiddenDangerReport, paramMap, "UTF-8");
				LogUtil.info("上报接口调用成功返回：" + response);
			} catch (NetServiceException e) {
				j.setSuccess(false);
				j.setMsg("上报接口调用失败, 网络连接错误");
				LogUtil.error("上报接口调用异常",e);
				return j;
			}
			JSONObject jsonObject = JSONObject.fromObject(response);

			String retCode = jsonObject.getString("code");
			if("200".equals(retCode)){
				j.setMsg("上报成功");
				try{
					String curUser;
					try {
						//定时任务无法获取session
						curUser = ResourceUtil.getSessionUserName().getRealName();
					} catch (Exception e) {
						curUser = "定时任务";
					}
					systemService.executeSql("update t_b_hidden_danger_handle set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
				}catch (Exception e){
					j.setMsg("上报成功但是本地数据库操作失败！");
				}
			}else{
				j.setMsg(jsonObject.optString("message"));
			}
		} else {
			j.setMsg("所选中记录不存在或状态已改变");
		}
 		return j;
	}

	@Override
	public AjaxJson loginCountReportToGroup(List<Map<String, Object>>  ids){
		AjaxJson j = new AjaxJson();

		JSONArray jsonArray = new JSONArray();
		Map<String, String> userMap = dataMapService.getUserMap();
		Map<String, String> departMap = dataMapService.getDepartMap();

		for (Map map : ids) {
			JSONObject data = new JSONObject();         //这里是包括 TBHiddenDangerHandleEntity 以及 TBHiddenDangerExamEntity 中的数据
			data.put("minecode", map.get("minecode"));
			data.put("username", map.get("username"));
			data.put("realname", map.get("realname"));
			data.put("userid", map.get("userid"));
			data.put("operatetime", DateUtils.date2Str((Date) map.get("operatetime"), DateUtils.datetimeFormat));
			data.put("id",map.get("id"));
			jsonArray.add(data);

		}

		if (jsonArray.size() > 0){
			//上传
			String mineCode = ResourceUtil.getConfigByName("mine_code");
			String token = ResourceUtil.getConfigByName("token_group");
			String reportContent = jsonArray.toString();
			String loginCountReport = ResourceUtil.getConfigByName("loginCountReport_group");
			Map<String, Object> paramMap = new HashMap<>();

			/**
			 * 加密过程
			 * */
			String tempToken = "token=" + token + "&mineCode=" + mineCode;
			String ciphertext = null;
			try {
				ciphertext = AesUtil.encryptWithIV(tempToken, token);
			} catch (Exception e) {
				e.printStackTrace();
			}
			paramMap.put("token", ciphertext);
			paramMap.put("mineCode", mineCode);
			paramMap.put("reportContent", reportContent);
			String response = null;
			LogUtil.info("loginCountReport=" + loginCountReport);
			LogUtil.info("jsonArray.size()=" + jsonArray.size() + ",reportContent="+reportContent.length());
			try {
				response = HttpClientUtils.post(loginCountReport, paramMap, "UTF-8");
				LogUtil.info("上报接口调用成功返回：" + response);
			} catch (NetServiceException e) {
				j.setSuccess(false);
				j.setMsg("上报接口调用失败, 网络连接错误");
				LogUtil.error("上报接口调用异常",e);
			}
			JSONObject jsonObject = JSONObject.fromObject(response);

			String retCode = jsonObject.getString("code");
			if("200".equals(retCode)){
				j.setMsg("上报成功");
				try{
					String curUser;
					try {
						//定时任务无法获取session
						curUser = ResourceUtil.getSessionUserName().getRealName();
					} catch (Exception e) {
						curUser = "定时任务";
					}
//					systemService.executeSql("update t_b_hidden_danger_handle set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
				}catch (Exception e){
					j.setMsg("上报成功但是本地数据库操作失败！");
				}
			}else{
				j.setMsg(jsonObject.optString("message"));
			}
		} else {
			j.setMsg("所选中记录不存在或状态已改变");
		}
		return j;
	}


	@Override
	public AjaxJson hiddenDangerReport(String ids,boolean isFromTask) {
		AjaxJson j = new AjaxJson();
		j.setMsg("上报成功");
		if (StringUtil.isNotEmpty(ids)) {
			JSONArray jsonArray = new JSONArray();
			StringBuffer examIds = new StringBuffer();
			for (String id : ids.split(",")) {
				TBHiddenDangerHandleEntity handleEntity = systemService.get(TBHiddenDangerHandleEntity.class, id);
				if (handleEntity != null){
					if (StringUtil.isNotEmpty(examIds.toString())) {
						examIds.append(",");
					}
					examIds.append(handleEntity.getHiddenDanger().getId());
					JSONObject object = new JSONObject();
					TBHiddenDangerExamEntity examEntity = handleEntity.getHiddenDanger();
					object.put("id", handleEntity.getId());
					if (StringUtil.isNotEmpty(examEntity.getAddress())) {
						object.put("hdbiHdLocation", examEntity.getAddress().getAddress());
					}
					object.put("hdbiInveDate", DateUtils.date2Str(examEntity.getExamDate(), DateUtils.date_sdf));
					object.put("hdbiHdLevel", examEntity.getHiddenNature());//TODO 矿版不存在隐患等级
//					object.put("hdbiHdCate", examEntity.getHiddenCategory());
					object.put("hdbiHdCate", examEntity.getRiskType());
					object.put("hdbiHdMajor", examEntity.getProType()); //TODO 隐患专业, 除专业科室日常检查外不需填写
					object.put("hdbiDesc", examEntity.getProblemDesc());
					object.put("hdbiClStatus", getCloseLoopStatus(handleEntity.getHandlelStatus()));
					if (StringUtil.isNotEmpty(examEntity.getDutyUnit())) {
						object.put("hdbiRectUnit", examEntity.getDutyUnit().getDepartname());
					}
					object.put("hdbiRectMan", getNameById("t_s_base_user", "realname", handleEntity.getModifyMan()));
					object.put("hdbiRectPeriod", DateUtils.date2Str(examEntity.getLimitDate(), DateUtils.date_sdf));
					object.put("hdbiRectMeasures", handleEntity.getRectMeasures());
					object.put("hdbiRectTagartDt", DateUtils.date2Str(handleEntity.getModifyDate(), DateUtils.date_sdf));
					object.put("hdbiReviewer", getNameById("t_s_base_user", "realname", handleEntity.getReviewMan()));
					object.put("hdbiReviewTime", DateUtils.date2Str(handleEntity.getReviewDate(), DateUtils.date_sdf));
					object.put("hdbiReviewReport", handleEntity.getReviewReport());
					List<TBHiddenDangerHandleStepEntity> stepEntitys = systemService.findByProperty(TBHiddenDangerHandleStepEntity.class, "hiddenDanger.id", examEntity.getId());
					JSONArray stepJsonArray = new JSONArray();
					if (stepEntitys != null && stepEntitys.size() > 0) {
						for (TBHiddenDangerHandleStepEntity stepEntity : stepEntitys) {
							JSONObject stepObject = new JSONObject();
							stepObject.put("hdhDealTime", DateUtils.date2Str(stepEntity.getHandleDate(), DateUtils.datetimeFormat));
							stepObject.put("hdhDealStep", getCloseLoopStatus(stepEntity.getHandleStatus()));
							stepObject.put("hdhDealDesc", getHistDesc(stepEntity));
							stepObject.put("hdhDealUserName", stepEntity.getHandleMan());
							stepJsonArray.add(stepObject);
						}
					}
					object.put("histInfo", stepJsonArray);
					jsonArray.add(object);
				}
			}
			if (jsonArray.size() > 0){
				//上传
				String mineCode = ResourceUtil.getConfigByName("mine_code");
				String token = ResourceUtil.getConfigByName("token");
				String reportContent = jsonArray.toString();
				String hiddenDangerReport = ResourceUtil.getConfigByName("hiddenDangerReport");
				Map<String, Object> paramMap = new HashMap<>();

                /**
                 * 加密过程
                 * */
                String tempToken = "token=" + token + "&mineCode=" + mineCode;
                String ciphertext = null;
                try {
                    ciphertext = AesUtil.encryptWithIV(tempToken, token);
                } catch (Exception e) {
                    e.printStackTrace();
                }

				paramMap.put("token", ciphertext);
				paramMap.put("mineCode", mineCode);
				paramMap.put("reportContent", reportContent);
				String response = null;
				try {
					response = HttpClientUtils.post(hiddenDangerReport, paramMap, "UTF-8");
				} catch (NetServiceException e) {
					j.setSuccess(false);
					j.setMsg("上报接口调用失败, 网络连接错误");
				}
				if (StringUtil.isNotEmpty(response)) {
					JSONObject result = JSONHelper.jsonstr2json(response);
					String code = (String) result.get("code");
					//根据请求返回状态码更新数据库状态
					if ("200".equals(code)) {
						String userName;
						try {
							userName = ResourceUtil.getSessionUserName().getRealName();
						} catch (Exception e) {
							userName = "定时任务";
						}
						String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
//                        if(!isFromTask){
                            List<String> idExamTempList = new ArrayList<>();
                            for(String id : examIds.toString().split(",")){
                                idExamTempList.add(id);
                            }
                            CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class);
                            cq.in("id",idExamTempList.toArray());
                            cq.add();
                            List<TBHiddenDangerExamEntity> hiddenDangerExamEntities =systemService.getListByCriteriaQuery(cq,false);
                            for(TBHiddenDangerExamEntity tempExamEntity : hiddenDangerExamEntities){
                                if(tempExamEntity.getHandleEntity().getHandlelStatus().equals("5")){
                                    tempExamEntity.setReportStatus("3");
                                    tempExamEntity.getHandleEntity().setReportStatus("3");
                                }else{
                                    tempExamEntity.setReportStatus("2");
                                    tempExamEntity.getHandleEntity().setReportStatus("2");
                                }
                                tempExamEntity.setReportName(userName);
                                tempExamEntity.getHandleEntity().setReportName(userName);
                                tempExamEntity.setReportDate(reportDate);
                                tempExamEntity.getHandleEntity().setReportDate(new Date());
                                systemService.saveOrUpdate(tempExamEntity);
                            }
//                            this.updateBySqlString("update t_b_hidden_danger_exam set report_status='"+Constants.DANGER_SOURCE_REPORT_REPORT
//                                    +"', report_name='"+userName+"', report_date='"+reportDate+"' where id in ('"+examIds.toString().replace(",","','")+"')");
//                        }

//                        this.updateBySqlString("update t_b_hidden_danger_handle set report_status='"+Constants.DANGER_SOURCE_REPORT_REPORT
//                                +"', report_name='"+userName+"', report_date='"+reportDate+"' where hidden_danger_id in ('"+examIds.toString().replace(",","','")+"')");

					} else {
						j.setMsg("上报失败,"+result.get("message"));
					}
				}else{
                    j.setMsg("上报失败");
                }
			} else {
				j.setMsg("所选中记录不存在或状态已改变");
			}
		} else {
			j.setMsg("上报成功");
		}
		return j;
	}

    /**
     * 日常隐患撤回
     */
    @Override
    public Map<String,String> toReportHiddenDangerCallback(String ids){
        Map<String,String> retMap =new HashMap<>();
        retMap.put("code", Constants.LOCAL_RESULT_CODE_SUCCESS);
        retMap.put("message", "上报成功");
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("callbackHiddenDanger");
        String result = null;
        /**
         * 加密过程
         * */
        String tempToken = "token=" + token + "&mineCode=" + mineCode + "&riskIds=" + ids;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(StringUtil.isNotEmpty(ids)) {
            try {
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("token", ciphertext);
                paramMap.put("mineCode", mineCode);
//                paramMap.put("riskIds", ids);

                result = HttpClientUtils.post(url, paramMap, "UTF-8");
            } catch (NetServiceException e) {
                retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                retMap.put("message", "撤回失败：" + e.getMessage());
                return retMap;
            }

            //解析rpc返回的json
            try {
                if (result != null) {
                    JSONObject resultJson = JSONHelper.jsonstr2json(result);
                    String code = resultJson.getString("code");
                    if (!code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {//请求成功
                        retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                        retMap.put("message", "撤回失败");
                        return retMap;
                    }
                } else {
                    retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                    retMap.put("message", "撤回失败");
                    return retMap;
                }
            } catch (Exception e) {
                retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                retMap.put("message", "撤回失败");
                return retMap;
            }
            //更新本地数据库
            try {
                List<String> idExamTempList = new ArrayList<>();
                for(String id : ids.split(",")){
                    idExamTempList.add(id);
                }
                CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class);
                cq.createAlias("handleEntity","handleEntity");
                cq.in("handleEntity.id",idExamTempList.toArray());
                cq.add();
                List<TBHiddenDangerExamEntity> hiddenDangerExamEntities =systemService.getListByCriteriaQuery(cq,false);
                for(TBHiddenDangerExamEntity tempExamEntity : hiddenDangerExamEntities){
                        tempExamEntity.setReportStatus("1");
                        tempExamEntity.getHandleEntity().setReportStatus("1");
                        systemService.saveOrUpdate(tempExamEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
                retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                retMap.put("message", "撤回成功，但本地数据库操作失败");
                return retMap;
            }
        }
            return retMap;
    }
	/**
	 * 根据id获取用户名称, 部门名称
	 * @param table
	 * @param field
	 * @param id
     * @return
     */
	private String getNameById(String table,String field, String id) {
		List<String> name = systemService.findListbySql("select "+field+" from "+table+" where id = '"+id+"'");
		if (name != null && name.size() > 0) {
			return name.get(0);
		}
		return null;
	}

	/**
	 * 根据日常隐患状态获取对应的上报状态
	 * @param hiddenDangerStatus
	 * @return
     */
	private String getCloseLoopStatus(String hiddenDangerStatus){
		if (Constants.HANDELSTATUS_REPORT.equals(hiddenDangerStatus)) {
			return Constants.HIDDEN_DANGER_CLSTATUS_RECFITY;
		} else if (Constants.HANDELSTATUS_ROLLBACK_REPORT.equals(hiddenDangerStatus)) {
			return Constants.HIDDEN_DANGER_CLSTATUS_DRAFT;
		} else if (Constants.HANDELSTATUS_REVIEW.equals(hiddenDangerStatus)) {
			return Constants.HIDDEN_DANGER_CLSTATUS_REVIEW;
		} else if (Constants.HANDELSTATUS_ROLLBACK_CHECK.equals(hiddenDangerStatus)) {
			return Constants.HIDDEN_DANGER_CLSTATUS_RECFITY;
		} else if (Constants.REVIEWSTATUS_PASS.equals(hiddenDangerStatus)) {
			return Constants.HIDDEN_DANGER_CLSTATUS_FINISHED;
		}
		return null;
	}

	private String getHistDesc(TBHiddenDangerHandleStepEntity stepEntity){
		StringBuffer desc = new StringBuffer();
		String userName = stepEntity.getHandleMan();
		String date = DateUtils.date2Str(stepEntity.getHandleDate(), DateUtils.date_sdf);
		if (Constants.HANDELSTATUS_REPORT.equals(stepEntity.getHandleStatus())) {
			desc.append("[当前状态]:").append("已上报待整改").append("<br>");
			desc.append("[上报人]:").append(userName).append("<br>");
			desc.append("[上报时间]:").append(date).append("<br>");
		} else if (Constants.HANDELSTATUS_ROLLBACK_REPORT.equals(stepEntity.getHandleStatus())) {
			desc.append("[当前状态]:").append("退回上报").append("<br>");
			desc.append("[退回人]:").append(userName).append("<br>");
			desc.append("[退回时间]:").append(date).append("<br>");
			if (StringUtil.isNotEmpty(stepEntity.getRemark())) {
				desc.append("[退回备注]:").append(stepEntity.getRemark()).append("<br>");
			} else {
				desc.append("[退回备注]:").append("<br>");
			}
		} else if (Constants.HANDELSTATUS_REVIEW.equals(stepEntity.getHandleStatus())) {
			desc.append("[当前状态]:").append("已整改待复查").append("<br>");
			desc.append("[整改人]:").append(userName).append("<br>");
			desc.append("[整改时间]:").append(date).append("<br>");
			if (StringUtil.isNotEmpty(stepEntity.getRemark())){
				desc.append("[整改备注]:").append(stepEntity.getRemark()).append("<br>");
			} else {
				desc.append("[整改备注]:").append("<br>");
			}
		} else if (Constants.HANDELSTATUS_ROLLBACK_CHECK.equals(stepEntity.getHandleStatus())) {
			desc.append("[当前状态]:").append("复查未通过").append("<br>");
			desc.append("[复查人]:").append(userName).append("<br>");
			desc.append("[复查时间]:").append(date).append("<br>");
			if (StringUtil.isNotEmpty(stepEntity.getRemark())) {
				desc.append("[复查备注]:").append(stepEntity.getRemark()).append("<br>");
			} else {
				desc.append("[复查备注]:").append("<br>");
			}
		} else if (Constants.REVIEWSTATUS_PASS.equals(stepEntity.getHandleStatus())) {
			desc.append("[当前状态]:").append("复查通过").append("<br>");
			desc.append("[复查人]:").append(userName).append("<br>");
			desc.append("[复查时间]:").append(date).append("<br>");
			if (StringUtil.isNotEmpty(stepEntity.getRemark())) {
				desc.append("[复查备注]:").append(stepEntity.getRemark()).append("<br>");
			} else {
				desc.append("[复查备注]:").append("<br>");
			}
		}
		return desc.toString();
	}
}