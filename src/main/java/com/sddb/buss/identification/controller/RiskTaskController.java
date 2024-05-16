package com.sddb.buss.identification.controller;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sddb.buss.identification.entity.*;
import com.sddb.buss.riskmanage.entity.RiskManageTaskAllManageCheckRelEntity;
import com.sdzk.buss.api.utils.WebChatUtil;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.SfService;
import com.sdzk.buss.web.common.utils.SMSSenderUtil;
import com.sdzk.buss.web.gjj.entity.SfRiskTaskRelEntity;
import com.sdzk.buss.web.health.entity.TBLaborProtectEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.specialreport.entity.TBSpecialReportEntity;
import com.sdzk.buss.web.yearreport.entity.TBYearReportEntity;
import com.sdzk.buss.web.yearreport.entity.TBYearReportRelEntity;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;

import com.sddb.buss.identification.service.RiskTaskServiceI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @Title: Controller
 * @Description: 风险辨识任务
 * @author zhangdaihao
 * @date 2019-05-14 10:05:10
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/riskTaskController")
public class RiskTaskController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RiskTaskController.class);

	@Autowired
	private RiskTaskServiceI riskTaskService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private SfService sfService;



	/**
	 * 风险辨识任务列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sddb/buss/web/identification/riskTaskList");
	}

	/**
	 * 风险辨识报告 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "listReport")
	public ModelAndView listReport(HttpServletRequest request) {
		return new ModelAndView("com/sddb/buss/web/identification/riskTaskListReport");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(RiskTaskEntity riskTask,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RiskTaskEntity.class, dataGrid);
		TSUser sessionUser = ResourceUtil.getSessionUserName();
		try {
			boolean isAdmin = false;
			CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
			try{
				cqru.eq("TSUser.id",sessionUser.getId());
			}catch(Exception e){
				e.printStackTrace();
			}
			cqru.add();
			List<TSRoleUser> roleList = systemService.getListByCriteriaQuery(cqru,false);
			if(roleList != null && !roleList.isEmpty()){
				for(TSRoleUser ru : roleList){
					TSRole role = ru.getTSRole();
					if(role != null && role.getRoleName().equals("管理员")){
						isAdmin = true;
						break;
					}
				}
			}
			if(!isAdmin){
				String sql = "SELECT id from t_b_risk_task WHERE organizer_man = '"+sessionUser.getId()+"' or FIND_IN_SET('"+sessionUser.getId()+"',participant_man) or create_by = '"+sessionUser.getUserName()+"'";
				List<String> idList = systemService.findListbySql(sql);
				if(idList.size()>0&&idList!=null){
					cq.in("id",idList.toArray());
				}else{
					cq.isNull("id");
				}
			}
			String taskType = request.getParameter("taskType");
			if (StringUtil.isNotEmpty(taskType)) {
				cq.eq("taskType", taskType);
			}
			String startDateStart = request.getParameter("startDate_begin");
			String startDateEnd = request.getParameter("startDate_end");
			if(StringUtils.isNotBlank(startDateStart)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.ge("startDate",sdf.parse(startDateStart));
			}
			if(StringUtils.isNotBlank(startDateEnd)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.le("startDate",sdf.parse(startDateEnd));
			}
			String endDateStart = request.getParameter("endDate_begin");
			String endDateEnd = request.getParameter("endDate_end");
			if(StringUtils.isNotBlank(endDateStart)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.ge("endDate",sdf.parse(endDateStart));
			}
			if(StringUtils.isNotBlank(endDateEnd)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.le("endDate",sdf.parse(endDateEnd));
			}
			String status = request.getParameter("status");
			if (StringUtil.isNotEmpty(status)) {
				cq.eq("status", status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cq.add();
		this.riskTaskService.getDataGridReturn(cq, true);
		if(dataGrid != null && dataGrid.getResults() != null){
			List<RiskTaskEntity> list = dataGrid.getResults();
			for(RiskTaskEntity t : list){
				if (StringUtils.isNotBlank(t.getParticipantMan())){
					String[] ids = t.getParticipantMan().split(",");
					String name = "";

					for(String id : ids){
						TSUser user = systemService.getEntity(TSUser.class,id);
						if(user.getId().equals(sessionUser.getId())){
							t.setParticipant("0");
						}
						if(user!=null){
							if(name==""){
								name = name +user.getRealName();
							}else{
								name = name + "," +user.getRealName();
							}
						}
					}
					t.setParticipantManNames(name);
				}
				if(StringUtil.isNotEmpty(t.getCreateBy())&&StringUtil.isNotEmpty(t.getOrganizerMan())){
					if(t.getCreateBy().equals(sessionUser.getUserName())||t.getOrganizerMan().equals(sessionUser.getId())){
						t.setOrganizerOrCreateBy("0");
					}else{
						t.setOrganizerOrCreateBy("1");
					}
				}
				if(StringUtil.isNotEmpty(t.getTaskType())){
					if(t.getTaskType().equals("2")||t.getTaskType().equals("3")){
						t.setExportDoc("0");
					}else{
						t.setExportDoc("1");
					}
				}
			}

		}
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "datagridReport")
	public void datagridReport(RiskTaskEntity riskTask,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RiskTaskEntity.class, dataGrid);
		TSUser sessionUser = ResourceUtil.getSessionUserName();
		try {
			String sql = "SELECT id from t_b_risk_task WHERE organizer_man = '"+sessionUser.getId()+"' or FIND_IN_SET('"+sessionUser.getId()+"',participant_man) or create_by = '"+sessionUser.getUserName()+"'";
			List<String> idList = systemService.findListbySql(sql);
			if(idList.size()>0&&idList!=null){
				cq.in("id",idList.toArray());
			}else{
				cq.isNull("id");
			}
			cq.eq("status","1");
			String taskType = request.getParameter("taskType");
			if (StringUtil.isNotEmpty(taskType)) {
				cq.eq("taskType", taskType);
			}else{
				cq.or(Restrictions.eq("taskType","2"),Restrictions.eq("taskType","3"));
			}
			String startDateStart = request.getParameter("startDate_begin");
			String startDateEnd = request.getParameter("startDate_end");
			if(StringUtils.isNotBlank(startDateStart)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.ge("startDate",sdf.parse(startDateStart));
			}
			if(StringUtils.isNotBlank(startDateEnd)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.le("startDate",sdf.parse(startDateEnd));
			}
			String endDateStart = request.getParameter("endDate_begin");
			String endDateEnd = request.getParameter("endDate_end");
			if(StringUtils.isNotBlank(endDateStart)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.ge("endDate",sdf.parse(endDateStart));
			}
			if(StringUtils.isNotBlank(endDateEnd)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.le("endDate",sdf.parse(endDateEnd));
			}
			String status = request.getParameter("status");
			if (StringUtil.isNotEmpty(status)) {
				cq.eq("status", status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cq.add();
		this.riskTaskService.getDataGridReturn(cq, true);
		if(dataGrid != null && dataGrid.getResults() != null){
			List<RiskTaskEntity> list = dataGrid.getResults();
			for(RiskTaskEntity t : list){
				if (StringUtils.isNotBlank(t.getParticipantMan())){
					String[] ids = t.getParticipantMan().split(",");
					String name = "";

					for(String id : ids){
						TSUser user = systemService.getEntity(TSUser.class,id);
						if(user.getId().equals(sessionUser.getId())){
							t.setParticipant("0");
						}
						if(user!=null){
							if(name==""){
								name = name +user.getRealName();
							}else{
								name = name + "," +user.getRealName();
							}
						}
					}
					t.setParticipantManNames(name);
				}
				if(StringUtil.isNotEmpty(t.getCreateBy())&&StringUtil.isNotEmpty(t.getOrganizerMan())){
					if(t.getCreateBy().equals(sessionUser.getUserName())||t.getOrganizerMan().equals(sessionUser.getId())){
						t.setOrganizerOrCreateBy("0");
					}else{
						t.setOrganizerOrCreateBy("1");
					}
				}
				if(StringUtil.isNotEmpty(t.getTaskType())){
					if(t.getTaskType().equals("2")||t.getTaskType().equals("3")){
						t.setExportDoc("0");
					}else{
						t.setExportDoc("1");
					}
				}
				TBYearReportEntity tby = systemService.findUniqueByProperty(TBYearReportEntity.class,"taskId",t.getId());
				TBSpecialReportEntity tbs = systemService.findUniqueByProperty(TBSpecialReportEntity.class,"taskId",t.getId());
				if(StringUtil.isNotEmpty(tby) || StringUtil.isNotEmpty(tbs)){
					t.setIsUpdate("1");
				}else{
					t.setIsUpdate("0");
				}
			}

		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除风险辨识任务
	 *
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(RiskTaskEntity riskTask, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		riskTask = systemService.getEntity(RiskTaskEntity.class, riskTask.getId());
		message = "风险辨识任务删除成功";
		String sql = "DELETE FROM t_b_risk_task_participant_rel WHERE risk_task_id = '"+riskTask.getId()+"'";
		systemService.executeSql(sql);
		//国家局上报关联表
		sfService.saveOrUpdateRiskTask(riskTask,Constants.GJJ_STATE_FLAG_3);
		riskTaskService.delete(riskTask);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}


	/**
	 * 添加风险辨识任务
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(RiskTaskEntity riskTask, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(riskTask.getId())) {
			message = "风险辨识任务更新成功";
			RiskTaskEntity t = riskTaskService.get(RiskTaskEntity.class, riskTask.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(riskTask, t);
				message = "人员追加成功";
				String checkData = request.getParameter("checkData");
				Map<String, String> idMap = new HashMap<>();
				String idTemp = "";
				String[] participantMans = t.getParticipantMan().split(",");
				for (String participantMan : participantMans) {
					/**参与人数不符合效验*/
					String sql="select count(1) from t_b_risk_task_participant_rel where participant_man_id='"+participantMan+"' and risk_task_id='"+t.getId()+"'";
					long num=systemService.getCountForJdbc(sql);
					if(num>0){
						idMap.put(participantMan,"1");
						idTemp+=participantMan+",";
					}
				}
				String addIdTemp ="";
				if(StringUtils.isNotBlank(checkData)){
					JSONArray jsonArray = JSONArray.fromObject(checkData);
					try {
						if (jsonArray.size()>0) {
							for (int i=0; i<jsonArray.size(); i++) {
								for(String participantMan : jsonArray.getJSONObject(i).getString("participantMan").split(",")) {
									if(StringUtil.isEmpty(idMap.get(participantMan))){
										idMap.put(participantMan,"1");
										idTemp+=participantMan+",";
										addIdTemp+=participantMan+",";
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				idTemp = idTemp.substring(0,idTemp.length() - 1);
				t.setParticipantMan(idTemp);
				riskTaskService.saveOrUpdate(t);
				//国家局上报关联表
				sfService.saveOrUpdateRiskTask(t,Constants.GJJ_STATE_FLAG_2);
				TSUser organizerMan = systemService.get(TSUser.class,t.getOrganizerMan());
				String createTime = DateUtils.formatDate(t.getCreateDate(),"yyyy-MM-dd HH:mm");
				String content = "【双防平台】通知：尊敬的双防用户，您好！"+organizerMan.getRealName() +"组织了辨识任务：" +createTime+
						" 辨识活动名称："+t.getTaskName()+"，辨识活动类型：" +DicUtil.getTypeNameByCode("risk_task_type",t.getTaskType())+ "。";
				if(StringUtil.isNotEmpty(addIdTemp)){
					addIdTemp = addIdTemp.substring(0,addIdTemp.length() - 1);
					String[] ids = addIdTemp.split(",");
					for(String id : ids){
						RiskTaskParticipantRelEntity riskTaskParticipantRelEntity = new RiskTaskParticipantRelEntity();
						TSUser tsUser = systemService.getEntity(TSUser.class,id);
						riskTaskParticipantRelEntity.setParticipantManId(tsUser);
						riskTaskParticipantRelEntity.setRiskTaskId(t.getId());
						riskTaskParticipantRelEntity.setStatus("0");
						systemService.save(riskTaskParticipantRelEntity);
						List<String> mobilePhone=systemService.findListbySql("select mobilePhone from t_s_user  where id = '"+id+"'");
						if(mobilePhone != null && mobilePhone.size()>0 && StringUtils.isNotBlank(mobilePhone.get(0))){
							SMSSenderUtil.sendSMS(content,mobilePhone.get(0), Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
						}
						List<String> weChatPhones=systemService.findListbySql("select weChatPhone from t_s_user  where id = '"+id+"'");
						if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
							WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
						}
					}
				}
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "风险辨识任务更新失败";
			}
		} else {
			message = "风险辨识任务添加成功";
			riskTask.setStatus("0");
			String checkData = request.getParameter("checkData");
			Map<String,String> idMap = new HashMap<>();
			String idTemp = "";
			if(StringUtils.isNotBlank(checkData)){
				JSONArray jsonArray = JSONArray.fromObject(checkData);
				try {
					if (jsonArray.size()>0) {
						for (int i=0; i<jsonArray.size(); i++) {
							for(String participantMan : jsonArray.getJSONObject(i).getString("participantMan").split(",")) {
								if(StringUtil.isEmpty(idMap.get(participantMan))){
									idMap.put(participantMan,"1");
									idTemp+=participantMan+",";
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			idTemp = idTemp.substring(0,idTemp.length() - 1);
			riskTask.setParticipantMan(idTemp);
			riskTaskService.save(riskTask);
			//国家局上报关联表
			sfService.saveOrUpdateRiskTask(riskTask,Constants.GJJ_STATE_FLAG_1);
			TSUser organizerMan = systemService.get(TSUser.class,riskTask.getOrganizerMan());
			String createTime = DateUtils.formatDate(riskTask.getCreateDate(),"yyyy-MM-dd HH:mm");
			String content = "【双防平台】通知：尊敬的双防用户，您好！"+organizerMan.getRealName() +"组织了新的辨识任务：" +createTime+
					" 辨识活动名称："+riskTask.getTaskName()+"，辨识活动类型：" +DicUtil.getTypeNameByCode("risk_task_type",riskTask.getTaskType())+ ",请辨识风险。";

			String[] ids = idTemp.split(",");
			for(String id : ids){
				RiskTaskParticipantRelEntity riskTaskParticipantRelEntity = new RiskTaskParticipantRelEntity();
				TSUser tsUser = systemService.getEntity(TSUser.class,id);
				riskTaskParticipantRelEntity.setParticipantManId(tsUser);
				riskTaskParticipantRelEntity.setRiskTaskId(riskTask.getId());
				riskTaskParticipantRelEntity.setStatus("0");
				systemService.save(riskTaskParticipantRelEntity);
				List<String> mobilePhone=systemService.findListbySql("select mobilePhone from t_s_user  where id = '"+id+"'");
				if(mobilePhone != null && mobilePhone.size()>0 && StringUtils.isNotBlank(mobilePhone.get(0))){
					SMSSenderUtil.sendSMS(content,mobilePhone.get(0), Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
				}
				List<String> weChatPhones=systemService.findListbySql("select weChatPhone from t_s_user  where id = '"+id+"'");
				if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
					WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 风险辨识任务列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(RiskTaskEntity riskTask, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(riskTask.getId())) {
			riskTask = riskTaskService.getEntity(RiskTaskEntity.class, riskTask.getId());
			req.setAttribute("riskTaskPage", riskTask);
			req.setAttribute("organizerMan", riskTask.getOrganizerMan());
		}else{
			TSUser user = ResourceUtil.getSessionUserName();
			req.setAttribute("organizerMan", user.getId());
		}
		if (riskTask.getStartDate() == null) {
			req.setAttribute("startDate", new Date());
		} else {
			req.setAttribute("startDate", riskTask.getStartDate());
		}
		String detail = req.getParameter("detail");
		req.setAttribute("detail",detail);
		String participantMan = req.getParameter("participantMan");
		if(StringUtil.isNotEmpty(participantMan)){
			return new ModelAndView("com/sddb/buss/web/identification/riskTaskAddParticipantMan");
		}else{
			return new ModelAndView("com/sddb/buss/web/identification/riskTask");
		}

	}
	//查看进度
	@RequestMapping(params = "viewingProgressList")
	public ModelAndView viewingProgressList(HttpServletRequest request) {
		String riskTaskId = request.getParameter("riskTaskId");
		request.setAttribute("riskTaskId",riskTaskId);
		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType",taskType);
		return new ModelAndView("com/sddb/buss/web/identification/viewingProgressList");
	}


	@RequestMapping(params = "participantManDatagrid")
	public void participantManDatagrid(RiskTaskParticipantRelEntity riskTaskParticipantRelEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RiskTaskParticipantRelEntity.class, dataGrid);
		String riskTaskId = request.getParameter("riskTaskId");
		try {
			if(StringUtil.isNotEmpty(riskTaskId)){
				cq.eq("riskTaskId",riskTaskId);
				String sql = "SELECT id from t_b_risk_task_participant_rel WHERE FIND_IN_SET(participant_man_id,(SELECT participant_man from t_b_risk_task WHERE id = '"+riskTaskId+"'))";
				List<String> idList = systemService.findListbySql(sql);
				if(idList.size()>0&&idList!=null){
					cq.in("id",idList.toArray());
				}else{
					cq.isNull("id");
				}
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		if (dataGrid != null && dataGrid.getResults() != null) {
			List<RiskTaskParticipantRelEntity> entityList = dataGrid.getResults();
			//草稿
			String sql0 = "SELECT create_by userName,count(1) count FROM t_b_risk_identification ri WHERE risk_task_id = '"+riskTaskId+"' AND is_del = '0' AND status = '0' GROUP BY create_by";

			List<Map<String, Object>> result0 = systemService.findForJdbc(sql0);
			Map<String, String> count0Map = new HashMap<>();
			if (result0 != null && result0.size() > 0) {
				for (Map<String, Object> obj : result0) {
					if (obj.get("count") != null && obj.get("userName") != null) {
						count0Map.put(obj.get("userName").toString(), obj.get("count").toString());
					}
				}
			}
			//待审核
			String sql1 = "SELECT create_by userName,count(1) count FROM t_b_risk_identification ri WHERE risk_task_id = '"+riskTaskId+"' AND is_del = '0' AND status = '1' GROUP BY create_by";

			List<Map<String, Object>> result1 = systemService.findForJdbc(sql1);
			Map<String, String> count1Map = new HashMap<>();
			if (result1 != null && result1.size() > 0) {
				for (Map<String, Object> obj : result1) {
					if (obj.get("count") != null && obj.get("userName") != null) {
						count1Map.put(obj.get("userName").toString(), obj.get("count").toString());
					}
				}
			}
			//已退回
			String sql2 = "SELECT create_by userName,count(1) count FROM t_b_risk_identification ri WHERE risk_task_id = '"+riskTaskId+"' AND is_del = '0' AND status = '2' GROUP BY create_by";

			List<Map<String, Object>> result2 = systemService.findForJdbc(sql2);
			Map<String, String> count2Map = new HashMap<>();
			if (result2 != null && result2.size() > 0) {
				for (Map<String, Object> obj : result2) {
					if (obj.get("count") != null && obj.get("userName") != null) {
						count2Map.put(obj.get("userName").toString(), obj.get("count").toString());
					}
				}
			}
			//已审核
			String sql3 = "SELECT create_by userName,count(1) count FROM t_b_risk_identification ri WHERE risk_task_id = '"+riskTaskId+"' AND is_del = '0' AND status = '3' GROUP BY create_by";

			List<Map<String, Object>> result3 = systemService.findForJdbc(sql3);
			Map<String, String> count3Map = new HashMap<>();
			if (result3 != null && result3.size() > 0) {
				for (Map<String, Object> obj : result3) {
					if (obj.get("count") != null && obj.get("userName") != null) {
						count3Map.put(obj.get("userName").toString(), obj.get("count").toString());
					}
				}
			}

			//已审核
			String sqlPost = "SELECT create_by userName,count(1) count FROM t_b_risk_identification_post ri WHERE risk_task_id = '"+riskTaskId+"' GROUP BY create_by";

			List<Map<String, Object>> resultPost = systemService.findForJdbc(sqlPost);
			Map<String, String> countPostMap = new HashMap<>();
			if (resultPost != null && resultPost.size() > 0) {
				for (Map<String, Object> obj : resultPost) {
					if (obj.get("count") != null && obj.get("userName") != null) {
						countPostMap.put(obj.get("userName").toString(), obj.get("count").toString());
					}
				}
			}
			if (null != entityList && entityList.size() > 0) {
				for (int i = 0; i < entityList.size(); i++) {
					RiskTaskParticipantRelEntity entity = entityList.get(i);
					if(entity.getParticipantManId()!=null){
						entity.setRisk0Num((count0Map.get(entity.getParticipantManId().getUserName())!=null?count0Map.get(entity.getParticipantManId().getUserName()):"0"));
						entity.setRisk1Num((count1Map.get(entity.getParticipantManId().getUserName())!=null?count1Map.get(entity.getParticipantManId().getUserName()):"0"));
						entity.setRisk2Num((count2Map.get(entity.getParticipantManId().getUserName())!=null?count2Map.get(entity.getParticipantManId().getUserName()):"0"));
						entity.setRisk3Num((count3Map.get(entity.getParticipantManId().getUserName())!=null?count3Map.get(entity.getParticipantManId().getUserName()):"0"));
						entity.setRiskPostNum((countPostMap.get(entity.getParticipantManId().getUserName())!=null?countPostMap.get(entity.getParticipantManId().getUserName()):"0"));
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * 导出Word 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportDoc")
	public String exportDoc(HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		String riskTaskId = request.getParameter("riskTaskId");
		RiskTaskEntity riskTaskEntity = systemService.getEntity(RiskTaskEntity.class,riskTaskId);
		Map<String,Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
		String year = sdfY.format(riskTaskEntity.getStartDate());
		map.put("A0",  year);
		map.put("A1",  (Integer.parseInt(year)+1));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		map.put("A2",  sdf.format(riskTaskEntity.getEndDate()));
		map.put("A3",  sdf.format(riskTaskEntity.getStartDate()));
		SimpleDateFormat sdfM = new SimpleDateFormat("MM月dd日");
		map.put("A4",  sdfM.format(riskTaskEntity.getEndDate()));
		TSUser organizerMan = systemService.getEntity(TSUser.class,riskTaskEntity.getOrganizerMan());
		map.put("A5",  organizerMan.getRealName());
		String[] ids = riskTaskEntity.getParticipantMan().split(",");
		String participantManNames = "";
		for(String id : ids){
			TSUser participantMan = systemService.getEntity(TSUser.class,id);
			if(participantMan!=null){
				if(participantManNames==""){
					participantManNames = participantManNames +participantMan.getRealName();
				}else{
					participantManNames = participantManNames + "," +participantMan.getRealName();
				}
			}
		}
		map.put("A6",  participantManNames);
		CriteriaQuery addressCq = new CriteriaQuery(TBAddressInfoEntity.class);
		addressCq.eq("isDelete","0");
		addressCq.add(Restrictions.sqlRestriction("this_.id in (SELECT address_id  from t_b_risk_identification WHERE is_del = '0' AND risk_task_id = '"+riskTaskId+"' GROUP BY address_id)"));
		addressCq.add();
		List<TBAddressInfoEntity> addressList =systemService.getListByCriteriaQuery(addressCq,false);
		int count=1;
		for (TBAddressInfoEntity tbAddressInfoEntity:addressList){
			tbAddressInfoEntity.setOrderNumber(String.valueOf(count));
			count++;
		}
		map.put("addressList", addressList);

		List<TBYearReportRelEntity> yearRelList =systemService.findByProperty(TBYearReportRelEntity.class,"reportId",riskTaskId);
		int counter=1;
		for (TBYearReportRelEntity tBYearReportRelEntity:yearRelList){
			tBYearReportRelEntity.setOrderNumber(String.valueOf(counter));
			counter++;
		}
		map.put("yearRelList", yearRelList);

		TBYearReportEntity tby = systemService.findUniqueByProperty(TBYearReportEntity.class,"taskId",riskTaskId);
		if(StringUtil.isNotEmpty(tby)){
			map.put("A14",tby.getChargeMan()==null?"":tby.getChargeMan());
			map.put("A15",tby.getMineName()==null?"":tby.getMineName());
			map.put("A16",tby.getMineStatus()==null?"":tby.getMineStatus());
			map.put("A17",tby.getMainRefer()==null?"":tby.getMainRefer());
			map.put("A18",tby.getMainDanger()==null?"":tby.getMainDanger());
			map.put("A19",tby.getMainSystem()==null?"":tby.getMainSystem());
			map.put("A20",tby.getIdentifyRange()==null?"":tby.getIdentifyRange());
			map.put("A21",tby.getIdentifyMethod()==null?"":tby.getIdentifyMethod());
			map.put("A24",tby.getIdentifyGroup()==null?"":tby.getIdentifyGroup());
		}else{
			map.put("A14","");
			map.put("A15","");
			map.put("A16","");
			map.put("A17","");
			map.put("A18","");
			map.put("A19","");
			map.put("A20","");
			map.put("A21","");
			map.put("A24","");
		}
		String major = "SELECT t.typename FROM t_b_risk_identification r LEFT JOIN t_b_risk_factors_rel f ON r.id = f.risk_identification_id LEFT JOIN t_b_hazard_factors h ON f.hazard_factors_id = h.id  LEFT JOIN t_s_type t ON t.typecode = h.major LEFT JOIN t_s_typegroup tg ON t.typegroupid = tg.id WHERE r.risk_task_id = '"+riskTaskId+"' AND r.is_del = '0' AND r.STATUS = '3' AND tg.typegroupcode = 'risk_type' GROUP BY h.major";
		List<String> majorList = systemService.findListbySql(major);
		map.put("A23",majorList.size());
		String majorNames= "";
		for (String majorName:majorList){
			if(majorNames==""){
				majorNames = majorNames +majorName;
			}else{
				majorNames = majorNames + "、" +majorName;
			}
		}
		map.put("A22",majorNames);


		map.put("A7",  addressList.size());
		String riskCount="SELECT COUNT(id) from t_b_risk_identification WHERE risk_task_id = '"+riskTaskId+"' and is_del = '0' AND status = '3'";
		map.put("A8",systemService.getCountForJdbc(riskCount));
		CriteriaQuery riskCq = new CriteriaQuery(RiskIdentificationEntity.class);
		String jinqiao = ResourceUtil.getConfigByName("jinqiao");
		if(jinqiao.equals("false")){
			riskCq.eq("riskLevel","1");
		}
		riskCq.eq("isDel","0");
		riskCq.eq("status","3");
		riskCq.eq("riskTaskId",riskTaskId);
		riskCq.add();
		List<RiskIdentificationEntity> riskList =systemService.getListByCriteriaQuery(riskCq,false);
		List<RiskIdentificationEntity> riskIdentificationEntityListTemp = new ArrayList<>();
		if(jinqiao.equals("true")){
			if (riskList != null && riskList.size() > 0) {
				for (RiskIdentificationEntity entity : riskList) {
					entity.setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type",entity.getRiskType()));
					entity.setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level",entity.getRiskLevel()));
					entity.setManageLevelTemp(DicUtil.getTypeNameByCode("identifi_mange_level",entity.getManageLevel()));
					List<RiskFactortsRel> relList = entity.getRelList();
					if (relList != null&&relList.size()>0) {
						for (RiskFactortsRel riskFactorts : relList) {
							RiskIdentificationEntity temp = new RiskIdentificationEntity();
							try {
								MyBeanUtils.copyBeanNotNull2Bean(entity, temp);
							} catch (Exception e) {
								e.printStackTrace();
							}
							temp.setHazardFactorsTemp(riskFactorts.getHazardFactorsEntity().getHazardFactors());
							temp.setManageMeasureTemp(riskFactorts.getHfManageMeasure());
							riskIdentificationEntityListTemp.add(temp);
						}
					}else{
						riskIdentificationEntityListTemp.add(entity);
					}
				}
			}
		}else{
			for (RiskIdentificationEntity entity : riskList) {
				entity.setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type",entity.getRiskType()));
				entity.setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level",entity.getRiskLevel()));
				entity.setManageLevelTemp(DicUtil.getTypeNameByCode("identifi_mange_level",entity.getManageLevel()));
			}
		}
		map.put("A9",riskList.size());
		if(jinqiao.equals("true")){
			String riskLevel1="SELECT COUNT(id) from t_b_risk_identification WHERE risk_task_id = '"+riskTaskId+"' and is_del = '0' AND status = '3' and risk_level = '1'";
			map.put("A9",systemService.getCountForJdbc(riskLevel1));
			map.put("riskList",riskIdentificationEntityListTemp);
		}else{
			map.put("riskList",riskList);
		}
		String riskLevel2="SELECT COUNT(id) from t_b_risk_identification WHERE risk_task_id = '"+riskTaskId+"' and is_del = '0' AND status = '3' and risk_level = '2'";
		map.put("A10",systemService.getCountForJdbc(riskLevel2));
		String riskLevel3="SELECT COUNT(id) from t_b_risk_identification WHERE risk_task_id = '"+riskTaskId+"' and is_del = '0' AND status = '3' and risk_level = '3'";
		map.put("A11",systemService.getCountForJdbc(riskLevel3));
		String riskLevel4="SELECT COUNT(id) from t_b_risk_identification WHERE risk_task_id = '"+riskTaskId+"' and is_del = '0' AND status = '3' and risk_level = '4'";
		map.put("A12",systemService.getCountForJdbc(riskLevel4));
		String riskType = "SELECT t.typename FROM t_b_risk_identification LEFT JOIN t_s_type t ON t.typecode = risk_type LEFT JOIN t_s_typegroup tg ON t.typegroupid = tg.id WHERE risk_task_id = '"+riskTaskId+"' AND is_del = '0' AND STATUS = '3' AND tg.typegroupcode = 'risk_type' GROUP BY risk_type";
		List<String> riskTypeNameList = systemService.findListbySql(riskType);
		String riskTypeNames= "";
		for (String riskTypeName:riskTypeNameList){
			if(riskTypeNames==""){
				riskTypeNames = riskTypeNames +riskTypeName;
			}else{
				riskTypeNames = riskTypeNames + "、" +riskTypeName;
			}
		}
		map.put("A13",riskTypeNames);
		modelMap.put(TemplateWordConstants.FILE_NAME,""+sdfY.format(riskTaskEntity.getStartDate())+"年度辨识报告");
		modelMap.put(TemplateWordConstants.MAP_DATA,map);
		if(jinqiao.equals("true")){
			modelMap.put(TemplateWordConstants.URL,"export/template/exportTemp_riskTaskDocJQ.docx");
		}else{
			modelMap.put(TemplateWordConstants.URL,"export/template/exportTemp_riskTaskDoc.docx");
		}

		return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;
	}


	@RequestMapping(params = "exportDoc3")
	public String exportDoc3(HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		String riskTaskId = request.getParameter("riskTaskId");
		RiskTaskEntity riskTaskEntity = systemService.getEntity(RiskTaskEntity.class,riskTaskId);
		Map<String,Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
		map.put("A1",  riskTaskEntity.getTaskName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		map.put("A3",  sdf.format(riskTaskEntity.getStartDate()));
		SimpleDateFormat sdfM = new SimpleDateFormat("MM月dd日");
		map.put("A4",  sdfM.format(riskTaskEntity.getEndDate()));
		TSUser organizerMan = systemService.getEntity(TSUser.class,riskTaskEntity.getOrganizerMan());
		map.put("A5",  organizerMan.getRealName());
		String[] ids = riskTaskEntity.getParticipantMan().split(",");
		String participantManNames = "";
		for(String id : ids){
			TSUser participantMan = systemService.getEntity(TSUser.class,id);
			if(participantMan!=null){
				if(participantManNames==""){
					participantManNames = participantManNames +participantMan.getRealName();
				}else{
					participantManNames = participantManNames + "," +participantMan.getRealName();
				}
			}
		}
		map.put("A6",  participantManNames);

		List<TBYearReportRelEntity> yearRelList =systemService.findByProperty(TBYearReportRelEntity.class,"reportId",riskTaskId);
		int counter=1;
		for (TBYearReportRelEntity tBYearReportRelEntity:yearRelList){
			tBYearReportRelEntity.setOrderNumber(String.valueOf(counter));
			counter++;
		}
		map.put("yearRelList", yearRelList);
		TBSpecialReportEntity tbs = systemService.findUniqueByProperty(TBSpecialReportEntity.class,"taskId",riskTaskId);
		if(StringUtil.isNotEmpty(tbs)){
			map.put("A7",tbs.getBaseCondition()==null?"":tbs.getBaseCondition());
			map.put("A8",tbs.getChargeMan()==null?"":tbs.getChargeMan());
			map.put("A9",tbs.getIdentifyGroup()==null?"":tbs.getIdentifyGroup());
			map.put("A10",tbs.getIdentifyMethod()==null?"":tbs.getIdentifyMethod());
			map.put("A11",tbs.getIdentifyResult()==null?"":tbs.getIdentifyResult());

		}else{
			map.put("A7","");
			map.put("A8","");
			map.put("A9","");
			map.put("A10","");
			map.put("A11","");

		}

		CriteriaQuery riskCq = new CriteriaQuery(RiskIdentificationEntity.class);
		riskCq.eq("isDel","0");
		riskCq.eq("status","3");
		riskCq.eq("riskTaskId",riskTaskId);
		riskCq.add();
		List<RiskIdentificationEntity> riskList =systemService.getListByCriteriaQuery(riskCq,false);
		for (RiskIdentificationEntity entity : riskList) {
			entity.setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type",entity.getRiskType()));
			entity.setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level",entity.getRiskLevel()));
			entity.setManageLevelTemp(DicUtil.getTypeNameByCode("identifi_mange_level",entity.getManageLevel()));
		}
		map.put("riskList",riskList);
		modelMap.put(TemplateWordConstants.FILE_NAME,""+riskTaskEntity.getTaskName()+"报告");
		modelMap.put(TemplateWordConstants.MAP_DATA,map);
		modelMap.put(TemplateWordConstants.URL,"export/template/exportTemp_riskTaskDoc3.docx");
		return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;
	}




	@RequestMapping(params = "isEndRisk")
	@ResponseBody
	public String isEndRisk(String id){
		String isEnd = "1";
		TSUser user = ResourceUtil.getSessionUserName();
		String sql = "SELECT id from t_b_risk_identification WHERE create_by = '"+user.getUserName()+"' and risk_task_id = '"+id+"' and is_del = '0' and (status = '0' or status = '1')";
		List<String> risks = systemService.findListbySql(sql);
		if(risks==null||risks.size()==0){
			isEnd = "0";
		}
		return isEnd;
	}

	@RequestMapping(params = "endRisk")
	@ResponseBody
	public AjaxJson endRisk(String id,HttpServletRequest request, HttpServletResponse response){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "结束辨识成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			String sql = "SELECT id from t_b_risk_task_participant_rel WHERE risk_task_id = '"+id+"' and participant_man_id = '"+user.getId()+"'";
			List<String> rel = systemService.findListbySql(sql);
			if(rel!=null&&rel.size()>0){
				RiskTaskParticipantRelEntity riskTaskParticipantRelEntity = systemService.getEntity(RiskTaskParticipantRelEntity.class,rel.get(0));
				if(riskTaskParticipantRelEntity!=null){
					riskTaskParticipantRelEntity.setStatus("1");
					systemService.saveOrUpdate(riskTaskParticipantRelEntity);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "结束辨识成功";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "isEndTask")
	@ResponseBody
	public String isEndTask(String id){
		String isEnd = "1";
		TSUser user = ResourceUtil.getSessionUserName();
		String sql = "SELECT id from t_b_risk_task_participant_rel WHERE risk_task_id = '"+id+"' and status = '0' ";
		List<String> temp = systemService.findListbySql(sql);
		if(temp==null||temp.size()==0){
			isEnd = "0";
		}
		return isEnd;
	}

	@RequestMapping(params = "endTask")
	@ResponseBody
	public AjaxJson endTask(String id,HttpServletRequest request, HttpServletResponse response){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "结束任务成功";
		try{
			RiskTaskEntity riskTaskEntity = systemService.getEntity(RiskTaskEntity.class,id);
			riskTaskEntity.setStatus("1");
			systemService.saveOrUpdate(riskTaskEntity);
		}catch(Exception e){
			e.printStackTrace();
			message = "结束任务失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "isAddRisk")
	@ResponseBody
	public String isAddRisk(String id){
		String isAdd = "1";
		TSUser user = ResourceUtil.getSessionUserName();
		String sql = "SELECT id from t_b_risk_task_participant_rel WHERE risk_task_id = '"+id+"' and participant_man_id = '"+user.getId()+"'";
		List<String> rel = systemService.findListbySql(sql);
		if(rel!=null&&rel.size()>0){
			RiskTaskParticipantRelEntity riskTaskParticipantRelEntity = systemService.getEntity(RiskTaskParticipantRelEntity.class,rel.get(0));
			if(riskTaskParticipantRelEntity!=null){
				if(riskTaskParticipantRelEntity.getStatus().equals("1")){
					isAdd = "0";
				}
			}
		}
		return isAdd;
	}


	@RequestMapping(params = "isExportDoc")
	@ResponseBody
	public String isExportDoc(String id){
		String isExportDoc = "1";
		RiskTaskEntity riskTaskEntity = systemService.getEntity(RiskTaskEntity.class,id);
		if(riskTaskEntity.getStatus().equals("1")){
			isExportDoc = "0";
		}
		return isExportDoc;
	}

	@RequestMapping(params = "isDeleteSelect")
	@ResponseBody
	public String isDeleteSelect(String id){
		String isDeleteSelect = "0";
		RiskTaskEntity riskTaskEntity = systemService.get(RiskTaskEntity.class,id);
		String sql = "SELECT id from t_b_risk_identification WHERE risk_task_id = '"+id+"' and is_del = '0'";
		if(riskTaskEntity.getTaskType().equals("4")){
			sql  = "SELECT id from t_b_risk_identification_post WHERE risk_task_id = '"+id+"' and is_del = '0'";
		}
		List<String> ids = systemService.findListbySql(sql);
		if(ids!=null&&ids.size()>0){
			isDeleteSelect = "1";
		}
		return isDeleteSelect;
	}


}
