package com.sdzk.buss.web.assessmentscore.controller;

import com.sddb.buss.identification.entity.RiskTaskEntity;
import com.sddb.buss.riskmanage.entity.RiskManageTaskAllManageEntity;
import com.sdzk.buss.web.assessmentscore.entity.AssessmentScoreEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/assessmentScoreController")
public class AssessmentScoreController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AssessmentScoreController.class);


	@Autowired
	private SystemService systemService;


	@RequestMapping(params = "checkConfig")
	public ModelAndView checkConfig(HttpServletRequest request) {
		List<AssessmentScoreEntity> assessmentScoreEntityList = systemService.getList(AssessmentScoreEntity.class);
		if(assessmentScoreEntityList!=null&&assessmentScoreEntityList.size()>0){
			request.setAttribute("assessMentScore",assessmentScoreEntityList.get(0));
		}
		return new ModelAndView("com/sdzk/buss/web/assessmentscore/checkConfig");
	}

	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(AssessmentScoreEntity assessmentScoreEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(assessmentScoreEntity.getId())) {
			message = "保存成功";
			AssessmentScoreEntity t = systemService.get(AssessmentScoreEntity.class, assessmentScoreEntity.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(assessmentScoreEntity, t);
				systemService.saveOrUpdate(t);
			} catch (Exception e) {
				e.printStackTrace();
				message = "保存失败";
			}
		} else {
			message = "保存成功";
			try {
				systemService.save(assessmentScoreEntity);
			} catch (Exception e) {
				message = "保存失败";
				e.printStackTrace();
			}
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "statisticsList")
	public ModelAndView statisticsList(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/assessmentscore/statisticsList");
	}


	@RequestMapping(params = "statisticsDatagrid")
	public void statisticsDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		List<Map<String,Object>> queryList = new ArrayList<>();
		String queryType = request.getParameter("queryType");
		if(StringUtil.isEmpty(queryType)){
			queryType = "user";
		}
		StringBuffer sql = new StringBuffer();
		String userId= request.getParameter("userId");
		String departId = request.getParameter("departId");
		if(queryType.equals("user")){
			sql.append("select u.id,concat(realName, ' - ' ,userName) realName,userName from t_s_base_user u LEFT JOIN t_s_user_org uo on uo.user_id = u.id  WHERE delete_flag = '0' ");
			if(StringUtil.isNotEmpty(userId)){
				sql.append( "and u.id = '"+userId+"' ");
			}
			if(StringUtil.isNotEmpty(departId)){
				sql.append(" and uo.org_id = '"+departId+"'");
			}
		}else{
			sql.append("SELECT id,departname from t_s_depart  WHERE delete_flag = '0'");
			if(StringUtil.isNotEmpty(departId)){
				sql.append(" and id = '"+departId+"'");
			}
		}

		queryList = this.systemService.findForJdbc(sql.toString());
		int currentPage = dataGrid.getPage();
		int pageSize = dataGrid.getRows();
		int endIndex = pageSize * currentPage;

		if(endIndex > queryList.size()){
			endIndex = queryList.size();
		}
		List<Map<String,Object>> resultList = queryList.subList(pageSize * (currentPage - 1), endIndex);
		dataGrid.setResults(resultList);
		dataGrid.setTotal(queryList.size());
		String yearMonth = request.getParameter("yearMonth");
		SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
		if(yearMonth==null){
			yearMonth = date_sdf.format(new Date());
		}
		Date yearMonthDate = null;
		try {
            if(StringUtil.isNotEmpty(yearMonth)){
                yearMonthDate = date_sdf.parse(yearMonth);
            }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
			List<AssessmentScoreEntity> assessmentScoreEntityList = systemService.getList(AssessmentScoreEntity.class);
			if(assessmentScoreEntityList!=null&&assessmentScoreEntityList.size()>0){
				AssessmentScoreEntity assessmentScoreEntity = assessmentScoreEntityList.get(0);
				//辨识组织考核

				/*StringBuffer riskManNumSql = new StringBuffer();
				riskManNumSql.append("SELECT organizer_man id, count(a. YEAR) count FROM ( SELECT organizer_man, LEFT (create_date, 4) YEAR FROM t_b_risk_task WHERE FIND_IN_SET( organizer_man, ( SELECT risk_man FROM t_b_assessment_score )) ");
				riskManNumSql.append(" GROUP BY organizer_man, LEFT (create_date, 4)) a GROUP BY organizer_man");
				List<Map<String, Object>> riskManNumList = systemService.findForJdbc(riskManNumSql.toString());
				Map<String, String> riskManNumMap = new HashMap<>();
				if (riskManNumList !=null && riskManNumList.size()>0) {
					for (Map<String, Object> obj : riskManNumList) {
						String id = String.valueOf(obj.get("id"));
						String count = String.valueOf(obj.get("count"));
						riskManNumMap.put(id, count);
					}
				}*/
				//辨识组织考核
				StringBuffer riskManNumSql = new StringBuffer();
				riskManNumSql.append("SELECT p.participant_man_id id, count(0) count FROM t_b_risk_task_participant_rel p LEFT JOIN t_s_base_user u ON p.participant_man_id = u.id LEFT JOIN t_b_risk_task t ON t.id = p.risk_task_id WHERE u.username NOT IN ( SELECT create_by FROM t_b_risk_identification WHERE create_date <= t.end_date AND risk_task_id = t.id ) AND p.risk_task_id IN ( SELECT id FROM t_b_risk_task WHERE task_type != '4' ) ") ;
				if(StringUtil.isNotEmpty(yearMonth)){
					riskManNumSql.append(" and DATE_FORMAT(t.start_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				riskManNumSql.append(" GROUP BY p.participant_man_id ");
				List<Map<String, Object>> riskManNumList = systemService.findForJdbc(riskManNumSql.toString());
				Map<String, String> riskManNumMap = new HashMap<>();
				if (riskManNumList !=null && riskManNumList.size()>0) {
					for (Map<String, Object> obj : riskManNumList) {
						String id = String.valueOf(obj.get("id"));
						String count = String.valueOf(obj.get("count"));
						riskManNumMap.put(id, count);
					}
				}
				StringBuffer riskManNumSql4 = new StringBuffer();
				riskManNumSql4.append("SELECT p.participant_man_id id, count(0) count FROM t_b_risk_task_participant_rel p LEFT JOIN t_s_base_user u ON p.participant_man_id = u.id LEFT JOIN t_b_risk_task t ON t.id = p.risk_task_id WHERE u.username NOT IN ( SELECT create_by FROM t_b_risk_identification_post WHERE create_date <= t.end_date AND risk_task_id = t.id ) AND p.risk_task_id IN ( SELECT id FROM t_b_risk_task WHERE task_type = '4' ) ");
				if(StringUtil.isNotEmpty(yearMonth)){
					riskManNumSql4.append(" and DATE_FORMAT(t.start_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				riskManNumSql4.append(" GROUP BY p.participant_man_id ");
				List<Map<String, Object>> riskManNumList4 = systemService.findForJdbc(riskManNumSql4.toString());
				Map<String, String> riskManNumMap4 = new HashMap<>();
				if (riskManNumList4 !=null && riskManNumList4.size()>0) {
					for (Map<String, Object> obj : riskManNumList4) {
						String id = String.valueOf(obj.get("id"));
						String count = String.valueOf(obj.get("count"));
						riskManNumMap4.put(id, count);
					}
				}
				//管控措施考核
				StringBuffer taskNumSql = new StringBuffer();
				taskNumSql.append("SELECT count(DISTINCT task_all_manage_id) count, create_by userName FROM t_b_risk_manage_task_all WHERE STATUS = '1' AND task_all_manage_id IS NOT NULL AND task_all_manage_id != '' AND id IN ( SELECT task_all_id FROM t_b_risk_manage_task WHERE handle_status = '0' ) ");
				if(StringUtil.isNotEmpty(yearMonth)){
					taskNumSql.append(" and DATE_FORMAT(create_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				taskNumSql.append(" GROUP BY create_by ");
				List<Map<String, Object>> taskNumList = systemService.findForJdbc(taskNumSql.toString());
				Map<String, String> taskNumMap = new HashMap<>();
				if (taskNumList !=null && taskNumList.size()>0) {
					for (Map<String, Object> obj : taskNumList) {
						String userName = String.valueOf(obj.get("userName"));
						String count = String.valueOf(obj.get("count"));
						taskNumMap.put(userName, count);
					}
				}
				//管控情况考核
				StringBuffer hiddenManageManNumSql1 = new StringBuffer();
				hiddenManageManNumSql1.append("SELECT COUNT(id) hiddenNum , manage_duty_man_id manageMan from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '1' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManageManNumSql1.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManageManNumSql1.append(" GROUP BY manage_duty_man_id ");
				List<Map<String, Object>> hiddenManageManNumList1 = systemService.findForJdbc(hiddenManageManNumSql1.toString());
				Map<String, String> hiddenManageManNumMap1 = new HashMap<>();
				if (hiddenManageManNumList1 !=null && hiddenManageManNumList1.size()>0) {
					for (Map<String, Object> obj : hiddenManageManNumList1) {
						String id = String.valueOf(obj.get("manageMan"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenManageManNumMap1.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenManageManNumSql2 = new StringBuffer();
				hiddenManageManNumSql2.append("SELECT COUNT(id) hiddenNum , manage_duty_man_id manageMan from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '2' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManageManNumSql2.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManageManNumSql2.append(" GROUP BY manage_duty_man_id ");
				List<Map<String, Object>> hiddenManageManNumList2 = systemService.findForJdbc(hiddenManageManNumSql2.toString());
				Map<String, String> hiddenManageManNumMap2 = new HashMap<>();
				if (hiddenManageManNumList2 !=null && hiddenManageManNumList2.size()>0) {
					for (Map<String, Object> obj : hiddenManageManNumList2) {
						String id = String.valueOf(obj.get("manageMan"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenManageManNumMap2.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenManageManNumSql3 = new StringBuffer();
				hiddenManageManNumSql3.append("SELECT COUNT(id) hiddenNum , manage_duty_man_id manageMan from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '3' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManageManNumSql3.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManageManNumSql3.append(" GROUP BY manage_duty_man_id ");
				List<Map<String, Object>> hiddenManageManNumList3 = systemService.findForJdbc(hiddenManageManNumSql3.toString());
				Map<String, String> hiddenManageManNumMap3 = new HashMap<>();
				if (hiddenManageManNumList3 !=null && hiddenManageManNumList3.size()>0) {
					for (Map<String, Object> obj : hiddenManageManNumList3) {
						String id = String.valueOf(obj.get("manageMan"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenManageManNumMap3.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenManageManNumSql4 = new StringBuffer();
				hiddenManageManNumSql4.append("SELECT COUNT(id) hiddenNum , manage_duty_man_id manageMan from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '4' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManageManNumSql4.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManageManNumSql4.append(" GROUP BY manage_duty_man_id ");
				List<Map<String, Object>> hiddenManageManNumList4 = systemService.findForJdbc(hiddenManageManNumSql4.toString());
				Map<String, String> hiddenManageManNumMap4 = new HashMap<>();
				if (hiddenManageManNumList4 !=null && hiddenManageManNumList4.size()>0) {
					for (Map<String, Object> obj : hiddenManageManNumList4) {
						String id = String.valueOf(obj.get("manageMan"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenManageManNumMap4.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenManageDutyNumSql1 = new StringBuffer();
				hiddenManageDutyNumSql1.append("SELECT COUNT(id) hiddenNum , manage_duty_unit manageDuty from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '1' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManageDutyNumSql1.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManageDutyNumSql1.append(" GROUP BY manage_duty_unit ");
				List<Map<String, Object>> hiddenManageDutyNumList1 = systemService.findForJdbc(hiddenManageDutyNumSql1.toString());
				Map<String, String> hiddenManageDutyNumMap1 = new HashMap<>();
				if (hiddenManageDutyNumList1 !=null && hiddenManageDutyNumList1.size()>0) {
					for (Map<String, Object> obj : hiddenManageDutyNumList1) {
						String id = String.valueOf(obj.get("manageDuty"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenManageDutyNumMap1.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenManageDutyNumSql2 = new StringBuffer();
				hiddenManageDutyNumSql2.append("SELECT COUNT(id) hiddenNum , manage_duty_unit manageDuty from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '2' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManageDutyNumSql2.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManageDutyNumSql2.append(" GROUP BY manage_duty_unit ");
				List<Map<String, Object>> hiddenManageDutyNumList2 = systemService.findForJdbc(hiddenManageDutyNumSql2.toString());
				Map<String, String> hiddenManageDutyNumMap2 = new HashMap<>();
				if (hiddenManageDutyNumList2 !=null && hiddenManageDutyNumList2.size()>0) {
					for (Map<String, Object> obj : hiddenManageDutyNumList2) {
						String id = String.valueOf(obj.get("manageDuty"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenManageDutyNumMap2.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenManageDutyNumSql3 = new StringBuffer();
				hiddenManageDutyNumSql3.append("SELECT COUNT(id) hiddenNum , manage_duty_unit manageDuty from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '3' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManageDutyNumSql3.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManageDutyNumSql3.append(" GROUP BY manage_duty_unit ");
				List<Map<String, Object>> hiddenManageDutyNumList3 = systemService.findForJdbc(hiddenManageDutyNumSql3.toString());
				Map<String, String> hiddenManageDutyNumMap3 = new HashMap<>();
				if (hiddenManageDutyNumList3 !=null && hiddenManageDutyNumList3.size()>0) {
					for (Map<String, Object> obj : hiddenManageDutyNumList3) {
						String id = String.valueOf(obj.get("manageDuty"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenManageDutyNumMap3.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenManageDutyNumSql4 = new StringBuffer();
				hiddenManageDutyNumSql4.append("SELECT COUNT(id) hiddenNum , manage_duty_unit manageDuty from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '4' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManageDutyNumSql4.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManageDutyNumSql4.append(" GROUP BY manage_duty_unit ");
				List<Map<String, Object>> hiddenManageDutyNumList4 = systemService.findForJdbc(hiddenManageDutyNumSql4.toString());
				Map<String, String> hiddenManageDutyNumMap4 = new HashMap<>();
				if (hiddenManageDutyNumList4 !=null && hiddenManageDutyNumList4.size()>0) {
					for (Map<String, Object> obj : hiddenManageDutyNumList4) {
						String id = String.valueOf(obj.get("manageDuty"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenManageDutyNumMap4.put(id, hiddenNum);
					}
				}
				//隐患治理考核
				StringBuffer hiddenDutyManNumSql1 = new StringBuffer();
				hiddenDutyManNumSql1.append("select COUNT(h.id) hiddenNum , duty_man_id dutyMan from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '1' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenDutyManNumSql1.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenDutyManNumSql1.append(" GROUP BY duty_man_id ");
				List<Map<String, Object>> hiddenDutyManNumList1 = systemService.findForJdbc(hiddenDutyManNumSql1.toString());
				Map<String, String> hiddenDutyManNumMap1 = new HashMap<>();
				if (hiddenDutyManNumList1 !=null && hiddenDutyManNumList1.size()>0) {
					for (Map<String, Object> obj : hiddenDutyManNumList1) {
						String id = String.valueOf(obj.get("dutyMan"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenDutyManNumMap1.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenDutyManNumSql2 = new StringBuffer();
				hiddenDutyManNumSql2.append("select COUNT(h.id) hiddenNum , duty_man_id dutyMan from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '2' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenDutyManNumSql2.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenDutyManNumSql2.append(" GROUP BY duty_man_id ");
				List<Map<String, Object>> hiddenDutyManNumList2 = systemService.findForJdbc(hiddenDutyManNumSql2.toString());
				Map<String, String> hiddenDutyManNumMap2 = new HashMap<>();
				if (hiddenDutyManNumList2 !=null && hiddenDutyManNumList2.size()>0) {
					for (Map<String, Object> obj : hiddenDutyManNumList2) {
						String id = String.valueOf(obj.get("dutyMan"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenDutyManNumMap2.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenDutyManNumSql3 = new StringBuffer();
				hiddenDutyManNumSql3.append("select COUNT(h.id) hiddenNum , duty_man_id dutyMan from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '3' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenDutyManNumSql3.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenDutyManNumSql3.append(" GROUP BY duty_man_id ");
				List<Map<String, Object>> hiddenDutyManNumList3 = systemService.findForJdbc(hiddenDutyManNumSql3.toString());
				Map<String, String> hiddenDutyManNumMap3 = new HashMap<>();
				if (hiddenDutyManNumList3 !=null && hiddenDutyManNumList3.size()>0) {
					for (Map<String, Object> obj : hiddenDutyManNumList3) {
						String id = String.valueOf(obj.get("dutyMan"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenDutyManNumMap3.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenDutyManNumSql4 = new StringBuffer();
				hiddenDutyManNumSql4.append("select COUNT(h.id) hiddenNum , duty_man_id dutyMan from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '4' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenDutyManNumSql4.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenDutyManNumSql4.append(" GROUP BY duty_man_id ");
				List<Map<String, Object>> hiddenDutyManNumList4 = systemService.findForJdbc(hiddenDutyManNumSql4.toString());
				Map<String, String> hiddenDutyManNumMap4 = new HashMap<>();
				if (hiddenDutyManNumList4 !=null && hiddenDutyManNumList4.size()>0) {
					for (Map<String, Object> obj : hiddenDutyManNumList4) {
						String id = String.valueOf(obj.get("dutyMan"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenDutyManNumMap4.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenDutyUnitNumSql1 = new StringBuffer();
				hiddenDutyUnitNumSql1.append("select COUNT(h.id) hiddenNum , duty_unit DutyUnit from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '1' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenDutyUnitNumSql1.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenDutyUnitNumSql1.append(" GROUP BY duty_unit ");
				List<Map<String, Object>> hiddenDutyUnitNumList1 = systemService.findForJdbc(hiddenDutyUnitNumSql1.toString());
				Map<String, String> hiddenDutyUnitNumMap1 = new HashMap<>();
				if (hiddenDutyUnitNumList1 !=null && hiddenDutyUnitNumList1.size()>0) {
					for (Map<String, Object> obj : hiddenDutyUnitNumList1) {
						String id = String.valueOf(obj.get("DutyUnit"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenDutyUnitNumMap1.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenDutyUnitNumSql2 = new StringBuffer();
				hiddenDutyUnitNumSql2.append("select COUNT(h.id) hiddenNum , duty_unit DutyUnit from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '2' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenDutyUnitNumSql2.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenDutyUnitNumSql2.append(" GROUP BY duty_unit ");
				List<Map<String, Object>> hiddenDutyUnitNumList2 = systemService.findForJdbc(hiddenDutyUnitNumSql2.toString());
				Map<String, String> hiddenDutyUnitNumMap2 = new HashMap<>();
				if (hiddenDutyUnitNumList2 !=null && hiddenDutyUnitNumList2.size()>0) {
					for (Map<String, Object> obj : hiddenDutyUnitNumList2) {
						String id = String.valueOf(obj.get("DutyUnit"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenDutyUnitNumMap2.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenDutyUnitNumSql3 = new StringBuffer();
				hiddenDutyUnitNumSql3.append("select COUNT(h.id) hiddenNum , duty_unit DutyUnit from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '3' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenDutyUnitNumSql3.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenDutyUnitNumSql3.append(" GROUP BY duty_unit ");
				List<Map<String, Object>> hiddenDutyUnitNumList3 = systemService.findForJdbc(hiddenDutyUnitNumSql3.toString());
				Map<String, String> hiddenDutyUnitNumMap3 = new HashMap<>();
				if (hiddenDutyUnitNumList3 !=null && hiddenDutyUnitNumList3.size()>0) {
					for (Map<String, Object> obj : hiddenDutyUnitNumList3) {
						String id = String.valueOf(obj.get("DutyUnit"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenDutyUnitNumMap3.put(id, hiddenNum);
					}
				}
				StringBuffer hiddenDutyUnitNumSql4 = new StringBuffer();
				hiddenDutyUnitNumSql4.append("select COUNT(h.id) hiddenNum , duty_unit DutyUnit from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '4' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenDutyUnitNumSql4.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenDutyUnitNumSql4.append(" GROUP BY duty_unit ");
				List<Map<String, Object>> hiddenDutyUnitNumList4 = systemService.findForJdbc(hiddenDutyUnitNumSql4.toString());
				Map<String, String> hiddenDutyUnitNumMap4 = new HashMap<>();
				if (hiddenDutyUnitNumList4 !=null && hiddenDutyUnitNumList4.size()>0) {
					for (Map<String, Object> obj : hiddenDutyUnitNumList4) {
						String id = String.valueOf(obj.get("DutyUnit"));
						String hiddenNum = String.valueOf(obj.get("hiddenNum"));
						hiddenDutyUnitNumMap4.put(id, hiddenNum);
					}
				}
				//辨识组织考核
				StringBuffer taskManNumSql = new StringBuffer();
				taskManNumSql.append("SELECT organizer_man id, count(a.mon) count  FROM ( SELECT organizer_man, LEFT (create_date, 7) mon FROM t_b_risk_manage_task_all_manage y WHERE FIND_IN_SET( organizer_man, ( SELECT task_man FROM t_b_assessment_score ))   and manage_type = 'comprehensive' and LEFT (create_date, 7) < LEFT (now(), 7) ");
				if(StringUtil.isNotEmpty(yearMonth)){
					taskManNumSql.append("  and LEFT (create_date, 7) = '"+yearMonth+"' ");
				}
				taskManNumSql.append(" GROUP BY organizer_man, LEFT (create_date, 7)) a GROUP BY organizer_man");
				List<Map<String, Object>> taskManNumList = systemService.findForJdbc(taskManNumSql.toString());
				Map<String, String> taskManNumMap = new HashMap<>();
				if (taskManNumList !=null && taskManNumList.size()>0) {
					for (Map<String, Object> obj : taskManNumList) {
						String id = String.valueOf(obj.get("id"));
						String count = String.valueOf(obj.get("count"));
						taskManNumMap.put(id, count);
					}
				}
				StringBuffer taskManNumSql2 = new StringBuffer();
				taskManNumSql2.append("SELECT organizer_man id, count(a.xun) count FROM ( SELECT organizer_man, LEFT (create_date, 7) mon, (DAY(create_date) - 1) DIV 10 AS xun FROM t_b_risk_manage_task_all_manage y WHERE FIND_IN_SET( organizer_man, ( SELECT task_man2 FROM t_b_assessment_score )) AND manage_type = 'profession' and (DAY(create_date) - 1) DIV 10 < (DAY(now()) - 1) DIV 10 ");
				if(StringUtil.isNotEmpty(yearMonth)){
					taskManNumSql2.append("  and LEFT (create_date, 7) = '"+yearMonth+"' ");
				}
				taskManNumSql2.append(" GROUP BY organizer_man, mon, xun ) a GROUP BY organizer_man");
				List<Map<String, Object>> taskManNumList2 = systemService.findForJdbc(taskManNumSql2.toString());
				Map<String, String> taskManNumMap2 = new HashMap<>();
				if (taskManNumList2 !=null && taskManNumList2.size()>0) {
					for (Map<String, Object> obj : taskManNumList2) {
						String id = String.valueOf(obj.get("id"));
						String count = String.valueOf(obj.get("count"));
						taskManNumMap2.put(id, count);
					}
				}

				//分值
				Integer risk = 0;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getRisk())){
					risk=Integer.parseInt(assessmentScoreEntity.getRisk());
				}
				Integer task = 0;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getTask())){
					task=Integer.parseInt(assessmentScoreEntity.getTask());
				}
				Integer hdManageMan1 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageMan1())){
					hdManageMan1=Integer.parseInt(assessmentScoreEntity.getHdManageMan1());
				}
				Integer hdManageMan2 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageMan2())){
					hdManageMan2=Integer.parseInt(assessmentScoreEntity.getHdManageMan2());
				}
				Integer hdManageMan3 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageMan3())){
					hdManageMan3=Integer.parseInt(assessmentScoreEntity.getHdManageMan3());
				}
				Integer hdManageMan4 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageMan4())){
					hdManageMan4=Integer.parseInt(assessmentScoreEntity.getHdManageMan4());
				}
				Integer hdDutyMan1 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyMan1())){
					hdDutyMan1=Integer.parseInt(assessmentScoreEntity.getHdDutyMan1());
				}
				Integer hdDutyMan2 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyMan2())){
					hdDutyMan2=Integer.parseInt(assessmentScoreEntity.getHdDutyMan2());
				}
				Integer hdDutyMan3 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyMan3())){
					hdDutyMan3=Integer.parseInt(assessmentScoreEntity.getHdDutyMan3());
				}
				Integer hdDutyMan4 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyMan4())){
					hdDutyMan4=Integer.parseInt(assessmentScoreEntity.getHdDutyMan4());
				}
				Integer hdManageDuty1 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageDuty1())){
					hdManageDuty1=Integer.parseInt(assessmentScoreEntity.getHdManageDuty1());
				}
				Integer hdManageDuty2 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageDuty2())){
					hdManageDuty2=Integer.parseInt(assessmentScoreEntity.getHdManageDuty2());
				}
				Integer hdManageDuty3 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageDuty3())){
					hdManageDuty3=Integer.parseInt(assessmentScoreEntity.getHdManageDuty3());
				}
				Integer hdManageDuty4 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageDuty4())){
					hdManageDuty4=Integer.parseInt(assessmentScoreEntity.getHdManageDuty4());
				}
				Integer hdDutyUnit1 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyUnit1())){
					hdDutyUnit1=Integer.parseInt(assessmentScoreEntity.getHdDutyUnit1());
				}
				Integer hdDutyUnit2 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyUnit2())){
					hdDutyUnit2=Integer.parseInt(assessmentScoreEntity.getHdDutyUnit2());
				}
				Integer hdDutyUnit3 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyUnit3())){
					hdDutyUnit3=Integer.parseInt(assessmentScoreEntity.getHdDutyUnit3());
				}
				Integer hdDutyUnit4 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyUnit4())){
					hdDutyUnit4=Integer.parseInt(assessmentScoreEntity.getHdDutyUnit4());
				}
				Map<String, String> riskManMap = new HashMap<>();
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getRiskMan())){
					for(String riskMan : assessmentScoreEntity.getRiskMan().split(",")){
						riskManMap.put(riskMan,"1");
					}
				}
				Integer riskManScore = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getRiskManScore())){
					riskManScore=Integer.parseInt(assessmentScoreEntity.getRiskManScore());
				}
				Map<String, String> taskManMap = new HashMap<>();
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getTaskMan())){
					for(String taskMan : assessmentScoreEntity.getTaskMan().split(",")){
						taskManMap.put(taskMan,"1");
					}
				}
				Integer taskManScore = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getTaskManScore())){
					taskManScore=Integer.parseInt(assessmentScoreEntity.getTaskManScore());
				}
				Map<String, String> taskManMap2 = new HashMap<>();
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getTaskMan2())){
					for(String taskMan2 : assessmentScoreEntity.getTaskMan2().split(",")){
						taskManMap2.put(taskMan2,"1");
					}
				}
				Integer taskManScore2 = 0 ;
				if(StringUtil.isNotEmpty(assessmentScoreEntity.getTaskManScore2())){
					taskManScore2=Integer.parseInt(assessmentScoreEntity.getTaskManScore2());
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String yearTemp = "2019-07";
				Date fromDate = null;
				try {
					fromDate = sdf.parse(yearTemp);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date toDate = new Date();

				Calendar  from  =  Calendar.getInstance();
				from.setTime(fromDate);
				Calendar  to  =  Calendar.getInstance();
				to.setTime(toDate);
				//只要年月
				int fromYear = from.get(Calendar.YEAR);
				int fromMonth = from.get(Calendar.MONTH);

				int toYear = to.get(Calendar.YEAR);
				int toMonth = to.get(Calendar.MONTH);
				int toDay  = to.get(Calendar.DAY_OF_MONTH);
				int year = toYear  -  fromYear;
				int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
				List<Map<String,Object>>  list = dataGrid.getResults();
				for ( Map<String, Object>  bean : list) {
					//年度辨识考核
					/*if(StringUtil.isNotEmpty(riskManMap.get(bean.get("id")))){
						Integer score1 = 0;
						if(StringUtil.isNotEmpty(riskManNumMap.get(bean.get("id")))){
							Integer count = year-Integer.valueOf(riskManNumMap.get(bean.get("id")));
							if(count>0){
								score1 = count * riskManScore;
							}else{
								bean.put("score1","0.0");
							}
						}else{
							score1 = year * riskManScore;
						}
						bean.put("score1",score1);
					}else{
						bean.put("score1","0.0");
					}*/
					String riskNumTemp = riskManNumMap.get(bean.get("id"))==null?"0":riskManNumMap.get(bean.get("id"));
					Integer riskNum = Integer.valueOf(riskNumTemp);

					String riskNumTemp4 = riskManNumMap4.get(bean.get("id"))==null?"0":riskManNumMap4.get(bean.get("id"));
					Integer riskNum4 = Integer.valueOf(riskNumTemp4);
					Integer score1 = (riskNum+riskNum4) * risk;
					bean.put("score1",score1);
					//管控情况考核
					String taskNumTemp = taskNumMap.get(bean.get("userName"))==null?"0":taskNumMap.get(bean.get("userName"));
					Integer taskNum = Integer.valueOf(taskNumTemp);
					Integer score2 = taskNum * task;
					bean.put("score2",score2);
					//管控情况考核
					if(queryType.equals("user")){
						String hiddenManageNumTemp1 = hiddenManageManNumMap1.get(bean.get("id"))==null?"0":hiddenManageManNumMap1.get(bean.get("id"));
						Integer hiddenManageManNum1 = Integer.valueOf(hiddenManageNumTemp1);
						Integer hiddenManageManScore1 = hiddenManageManNum1 * hdManageMan1;
						String hiddenManageNumTemp2 = hiddenManageManNumMap2.get(bean.get("id"))==null?"0":hiddenManageManNumMap2.get(bean.get("id"));
						Integer hiddenManageManNum2 = Integer.valueOf(hiddenManageNumTemp2);
						Integer hiddenManageManScore2 = hiddenManageManNum2 * hdManageMan2;
						String hiddenManageNumTemp3 = hiddenManageManNumMap3.get(bean.get("id"))==null?"0":hiddenManageManNumMap3.get(bean.get("id"));
						Integer hiddenManageManNum3 = Integer.valueOf(hiddenManageNumTemp3);
						Integer hiddenManageManScore3 = hiddenManageManNum3 * hdManageMan3;
						String hiddenManageNumTemp4 = hiddenManageManNumMap4.get(bean.get("id"))==null?"0":hiddenManageManNumMap4.get(bean.get("id"));
						Integer hiddenManageManNum4 = Integer.valueOf(hiddenManageNumTemp4);
						Integer hiddenManageManScore4= hiddenManageManNum4 * hdManageMan4;
						Integer score3 = hiddenManageManScore1+hiddenManageManScore2+hiddenManageManScore3+hiddenManageManScore4;
						bean.put("score3",score3);
					}else{
						String hiddenManageNumTemp1 = hiddenManageDutyNumMap1.get(bean.get("id"))==null?"0":hiddenManageDutyNumMap1.get(bean.get("id"));
						Integer hiddenManageDutyNum1 = Integer.valueOf(hiddenManageNumTemp1);
						Integer hiddenManageDutyScore1 = hiddenManageDutyNum1 * hdManageDuty1;
						String hiddenManageNumTemp2 = hiddenManageDutyNumMap2.get(bean.get("id"))==null?"0":hiddenManageDutyNumMap2.get(bean.get("id"));
						Integer hiddenManageDutyNum2 = Integer.valueOf(hiddenManageNumTemp2);
						Integer hiddenManageDutyScore2 = hiddenManageDutyNum2 * hdManageDuty2;
						String hiddenManageNumTemp3 = hiddenManageDutyNumMap3.get(bean.get("id"))==null?"0":hiddenManageDutyNumMap3.get(bean.get("id"));
						Integer hiddenManageDutyNum3 = Integer.valueOf(hiddenManageNumTemp3);
						Integer hiddenManageDutyScore3 = hiddenManageDutyNum3 * hdManageDuty3;
						String hiddenManageNumTemp4 = hiddenManageDutyNumMap4.get(bean.get("id"))==null?"0":hiddenManageDutyNumMap4.get(bean.get("id"));
						Integer hiddenManageDutyNum4 = Integer.valueOf(hiddenManageNumTemp4);
						Integer hiddenManageDutyScore4= hiddenManageDutyNum4 * hdManageDuty4;
						Integer score3 = hiddenManageDutyScore1+hiddenManageDutyScore2+hiddenManageDutyScore3+hiddenManageDutyScore4;
						bean.put("score3",score3);
					}

					//隐患治理考核
					if(queryType.equals("user")){
						String hiddenDutyManNumTemp1 = hiddenDutyManNumMap1.get(bean.get("id"))==null?"0":hiddenDutyManNumMap1.get(bean.get("id"));
						Integer hiddenDutyManNum1 = Integer.valueOf(hiddenDutyManNumTemp1);
						Integer hiddenDutyManScore1 = hiddenDutyManNum1 * hdDutyMan1;
						String hiddenDutyManNumTemp2 = hiddenDutyManNumMap2.get(bean.get("id"))==null?"0":hiddenDutyManNumMap2.get(bean.get("id"));
						Integer hiddenDutyManNum2 = Integer.valueOf(hiddenDutyManNumTemp2);
						Integer hiddenDutyManScore2 = hiddenDutyManNum2 * hdDutyMan2;
						String hiddenDutyManNumTemp3 = hiddenDutyManNumMap3.get(bean.get("id"))==null?"0":hiddenDutyManNumMap3.get(bean.get("id"));
						Integer hiddenDutyManNum3 = Integer.valueOf(hiddenDutyManNumTemp3);
						Integer hiddenDutyManScore3 = hiddenDutyManNum3 * hdDutyMan3;
						String hiddenDutyManNumTemp4 = hiddenDutyManNumMap4.get(bean.get("id"))==null?"0":hiddenDutyManNumMap4.get(bean.get("id"));
						Integer hiddenDutyManNum4 = Integer.valueOf(hiddenDutyManNumTemp4);
						Integer hiddenDutyManScore4= hiddenDutyManNum4 * hdDutyMan4;
						Integer score4 = hiddenDutyManScore1+hiddenDutyManScore2+hiddenDutyManScore3+hiddenDutyManScore4;
						bean.put("score4",score4);
					}else{
						String hiddenDutyUnitNumTemp1 = hiddenDutyUnitNumMap1.get(bean.get("id"))==null?"0":hiddenDutyUnitNumMap1.get(bean.get("id"));
						Integer hiddenDutyUnitNum1 = Integer.valueOf(hiddenDutyUnitNumTemp1);
						Integer hiddenDutyUnitScore1 = hiddenDutyUnitNum1 * hdDutyUnit1;
						String hiddenDutyUnitNumTemp2 = hiddenDutyUnitNumMap2.get(bean.get("id"))==null?"0":hiddenDutyUnitNumMap2.get(bean.get("id"));
						Integer hiddenDutyUnitNum2 = Integer.valueOf(hiddenDutyUnitNumTemp2);
						Integer hiddenDutyUnitScore2 = hiddenDutyUnitNum2 * hdDutyUnit2;
						String hiddenDutyUnitNumTemp3 = hiddenDutyUnitNumMap3.get(bean.get("id"))==null?"0":hiddenDutyUnitNumMap3.get(bean.get("id"));
						Integer hiddenDutyUnitNum3 = Integer.valueOf(hiddenDutyUnitNumTemp3);
						Integer hiddenDutyUnitScore3 = hiddenDutyUnitNum3 * hdDutyUnit3;
						String hiddenDutyUnitNumTemp4 = hiddenDutyUnitNumMap4.get(bean.get("id"))==null?"0":hiddenDutyUnitNumMap4.get(bean.get("id"));
						Integer hiddenDutyUnitNum4 = Integer.valueOf(hiddenDutyUnitNumTemp4);
						Integer hiddenDutyUnitScore4= hiddenDutyUnitNum4 * hdDutyUnit4;
						Integer score4 = hiddenDutyUnitScore1+hiddenDutyUnitScore2+hiddenDutyUnitScore3+hiddenDutyUnitScore4;
						bean.put("score4",score4);
					}

					//管控组织考核
					Integer score5 = 0;
					if(StringUtil.isNotEmpty(taskManMap.get(bean.get("id")))){
						if(StringUtil.isNotEmpty(taskManNumMap.get(bean.get("id")))){
							Integer count = month-Integer.valueOf(taskManNumMap.get(bean.get("id")));
							if(StringUtil.isNotEmpty(yearMonth)){
								count = 1-Integer.valueOf(taskManNumMap.get(bean.get("id")));
							}
							score5 = count * taskManScore;
						}else{
							if(StringUtil.isNotEmpty(yearMonth)){
								score5 = 1 * taskManScore;
							}else{
								score5 = month * taskManScore;
							}
						}
					}
					if(score5<0){
						score5=0;
					}
                    if(StringUtil.isNotEmpty(yearMonth)){
                        if(yearMonthDate.before(fromDate)){
                            score5=0;
                        }
                    }
					if(StringUtil.isNotEmpty(taskManMap2.get(bean.get("id")))){
						Integer xun = toDay/10;
						if(StringUtil.isNotEmpty(taskManNumMap2.get(bean.get("id")))){
							Integer count = month*3+xun-Integer.valueOf(taskManNumMap2.get(bean.get("id")));
							if(StringUtil.isNotEmpty(yearMonth)){
								String dateTemp = sdf.format(new Date());
								if(dateTemp.equals(yearMonth)){
									count = xun;
								}else{
									count = 3;
								}
							}
							score5 = score5+count * taskManScore2;
						}else{
							if(StringUtil.isNotEmpty(yearMonth)){
								String dateTemp = sdf.format(new Date());
								if(dateTemp.equals(yearMonth)){
									score5 = score5+xun * taskManScore2;
								}else{
									score5 = score5+3 * taskManScore2;
								}
							}else{
								score5 = score5+(month*3+xun) * taskManScore2;
							}
						}
					}
					if(score5<0){
						score5=0;
					}
                    if(StringUtil.isNotEmpty(yearMonth)){
                        if(yearMonthDate.before(fromDate)){
                            score5=0;
                        }
                    }
					bean.put("score5",score5);
				}
			}else{
				List<Map<String,Object>>  list = dataGrid.getResults();
				for ( Map<String, Object>  bean : list) {
					bean.put("score1","0");
					bean.put("score2","0");
					bean.put("score3","0");
					bean.put("score4","0");
					bean.put("score5","0");
					}
				}
			}
		TagUtil.datagrid(response, dataGrid);
		}

	/**
	 * 是否可以提交
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "isGoDetail")
	@ResponseBody
	public String isGoDetail(){
		String isReport = "0";
		List<AssessmentScoreEntity> assessmentScoreEntityList = systemService.getList(AssessmentScoreEntity.class);
		if(assessmentScoreEntityList!=null&&assessmentScoreEntityList.size()>0) {
			isReport = "1";
		}
		return isReport;
	}

	@RequestMapping(params = "goDetail")
	public ModelAndView goDetail(HttpServletRequest request) {
		String selectId = request.getParameter("id");
		String selectedvalue = request.getParameter("selectedvalue");
		String yearMonth = request.getParameter("yearMonth");
		SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
		if(yearMonth==null){
			yearMonth = date_sdf.format(new Date());
		}
		Date yearMonthDate = null;
		try {
            if(StringUtil.isNotEmpty(yearMonth)){
                yearMonthDate = date_sdf.parse(yearMonth);
            }
			yearMonthDate = date_sdf.parse(yearMonth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<AssessmentScoreEntity> assessmentScoreEntityList = systemService.getList(AssessmentScoreEntity.class);
		AssessmentScoreEntity assessmentScoreEntity = assessmentScoreEntityList.get(0);
		request.setAttribute("assessMentScore",assessmentScoreEntity);
        request.setAttribute("yearMonth",yearMonth);
		request.setAttribute("selectId",selectId);
		Integer risk = 0;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getRisk())){
			risk=Integer.parseInt(assessmentScoreEntity.getRisk());
		}
		Integer task = 0;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getTask())){
			task=Integer.parseInt(assessmentScoreEntity.getTask());
		}
		Integer hdManageMan1 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageMan1())){
			hdManageMan1=Integer.parseInt(assessmentScoreEntity.getHdManageMan1());
		}
		Integer hdManageMan2 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageMan2())){
			hdManageMan2=Integer.parseInt(assessmentScoreEntity.getHdManageMan2());
		}
		Integer hdManageMan3 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageMan3())){
			hdManageMan3=Integer.parseInt(assessmentScoreEntity.getHdManageMan3());
		}
		Integer hdManageMan4 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageMan4())){
			hdManageMan4=Integer.parseInt(assessmentScoreEntity.getHdManageMan4());
		}
		Integer hdDutyMan1 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyMan1())){
			hdDutyMan1=Integer.parseInt(assessmentScoreEntity.getHdDutyMan1());
		}
		Integer hdDutyMan2 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyMan2())){
			hdDutyMan2=Integer.parseInt(assessmentScoreEntity.getHdDutyMan2());
		}
		Integer hdDutyMan3 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyMan3())){
			hdDutyMan3=Integer.parseInt(assessmentScoreEntity.getHdDutyMan3());
		}
		Integer hdDutyMan4 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyMan4())){
			hdDutyMan4=Integer.parseInt(assessmentScoreEntity.getHdDutyMan4());
		}
		Integer hdManageDuty1 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageDuty1())){
			hdManageDuty1=Integer.parseInt(assessmentScoreEntity.getHdManageDuty1());
		}
		Integer hdManageDuty2 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageDuty2())){
			hdManageDuty2=Integer.parseInt(assessmentScoreEntity.getHdManageDuty2());
		}
		Integer hdManageDuty3 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageDuty3())){
			hdManageDuty3=Integer.parseInt(assessmentScoreEntity.getHdManageDuty3());
		}
		Integer hdManageDuty4 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdManageDuty4())){
			hdManageDuty4=Integer.parseInt(assessmentScoreEntity.getHdManageDuty4());
		}
		Integer hdDutyUnit1 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyUnit1())){
			hdDutyUnit1=Integer.parseInt(assessmentScoreEntity.getHdDutyUnit1());
		}
		Integer hdDutyUnit2 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyUnit2())){
			hdDutyUnit2=Integer.parseInt(assessmentScoreEntity.getHdDutyUnit2());
		}
		Integer hdDutyUnit3 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyUnit3())){
			hdDutyUnit3=Integer.parseInt(assessmentScoreEntity.getHdDutyUnit3());
		}
		Integer hdDutyUnit4 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getHdDutyUnit4())){
			hdDutyUnit4=Integer.parseInt(assessmentScoreEntity.getHdDutyUnit4());
		}
		Map<String, String> riskManMap = new HashMap<>();
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getRiskMan())){
			for(String riskMan : assessmentScoreEntity.getRiskMan().split(",")){
				riskManMap.put(riskMan,"1");
			}
		}
		Integer riskManScore = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getRiskManScore())){
			riskManScore=Integer.parseInt(assessmentScoreEntity.getRiskManScore());
		}
		Map<String, String> taskManMap = new HashMap<>();
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getTaskMan())){
			for(String taskMan : assessmentScoreEntity.getTaskMan().split(",")){
				taskManMap.put(taskMan,"1");
			}
		}
		Integer taskManScore = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getTaskManScore())){
			taskManScore=Integer.parseInt(assessmentScoreEntity.getTaskManScore());
		}
		Map<String, String> taskManMap2 = new HashMap<>();
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getTaskMan2())){
			for(String taskMan2 : assessmentScoreEntity.getTaskMan2().split(",")){
				taskManMap2.put(taskMan2,"1");
			}
		}
		Integer taskManScore2 = 0 ;
		if(StringUtil.isNotEmpty(assessmentScoreEntity.getTaskManScore2())){
			taskManScore2=Integer.parseInt(assessmentScoreEntity.getTaskManScore2());
		}
		if(selectedvalue.equals("user")){
			TSUser selectUser = systemService.get(TSUser.class,selectId);
			//辨识组织考核
			Integer riskTaskNum = 0;
			StringBuffer riskTask1IdSql = new StringBuffer();
			riskTask1IdSql.append("SELECT t.id riskTaskId FROM t_b_risk_task_participant_rel p LEFT JOIN t_s_base_user u ON p.participant_man_id = u.id LEFT JOIN t_b_risk_task t ON t.id = p.risk_task_id WHERE u.username NOT IN ( SELECT create_by FROM t_b_risk_identification WHERE create_date <= t.end_date  AND risk_task_id = t.id ) AND p.risk_task_id IN ( SELECT id FROM t_b_risk_task WHERE task_type != '4' ) ") ;
			if(StringUtil.isNotEmpty(yearMonth)){
				riskTask1IdSql.append(" and DATE_FORMAT(t.start_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			riskTask1IdSql.append(" AND p.participant_man_id = '"+selectUser.getId()+"'");
			List<String> riskTask1IdList = systemService.findListbySql(riskTask1IdSql.toString());
			if(riskTask1IdList!=null&&riskTask1IdList.size()>0){
				riskTaskNum+=riskTask1IdList.size();
			}

			StringBuffer riskTask4IdSql = new StringBuffer();
			riskTask4IdSql.append("SELECT t.id riskTaskId FROM t_b_risk_task_participant_rel p LEFT JOIN t_s_base_user u ON p.participant_man_id = u.id LEFT JOIN t_b_risk_task t ON t.id = p.risk_task_id WHERE u.username NOT IN ( SELECT create_by FROM t_b_risk_identification_post WHERE create_date <= t.end_date  AND risk_task_id = t.id ) AND p.risk_task_id IN ( SELECT id FROM t_b_risk_task WHERE task_type  = '4' ) ") ;
			if(StringUtil.isNotEmpty(yearMonth)){
				riskTask4IdSql.append(" and DATE_FORMAT(t.start_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			riskTask4IdSql.append(" AND p.participant_man_id = '"+selectUser.getId()+"'");
			List<String> riskTask4IdList = systemService.findListbySql(riskTask4IdSql.toString());
			if(riskTask4IdList!=null&&riskTask4IdList.size()>0){
				riskTaskNum+=riskTask4IdList.size();
			}
			request.setAttribute("riskNum",riskTaskNum);
			request.setAttribute("riskScore",riskTaskNum*risk);
			//管控措施考核
			Integer taskAllManageNum = 0;
			StringBuffer taskAllManageIdSql = new StringBuffer();
			taskAllManageIdSql.append("SELECT DISTINCT(task_all_manage_id) taskAllManageId  FROM t_b_risk_manage_task_all WHERE STATUS = '1' AND task_all_manage_id IS NOT NULL AND task_all_manage_id != '' AND id IN ( SELECT task_all_id FROM t_b_risk_manage_task WHERE handle_status = '0' ) ");
			if(StringUtil.isNotEmpty(yearMonth)){
				taskAllManageIdSql.append(" and DATE_FORMAT(create_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			taskAllManageIdSql.append(" and create_by = '"+selectUser.getUserName()+"'");
			List<String> taskAllManageIdList = systemService.findListbySql(taskAllManageIdSql.toString());
			if(taskAllManageIdList!=null&&taskAllManageIdList.size()>0){
				taskAllManageNum+=taskAllManageIdList.size();
			}
			request.setAttribute("taskAllManageNum",taskAllManageNum);
			request.setAttribute("taskAllManageScore",taskAllManageNum*task);
			//管控情况考核
			Integer hiddenManage1Num = 0;
			StringBuffer hiddenManage1IdSql = new StringBuffer();
			hiddenManage1IdSql.append("SELECT id  from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '1' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hiddenManage1IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hiddenManage1IdSql.append(" and manage_duty_man_id = '"+selectUser.getId()+"'");
			List<String> hiddenManage1IdList = systemService.findListbySql(hiddenManage1IdSql.toString());
			if(hiddenManage1IdList!=null&&hiddenManage1IdList.size()>0){
				hiddenManage1Num+=hiddenManage1IdList.size();
			}
			request.setAttribute("hiddenManage1Num",hiddenManage1Num);
			request.setAttribute("hiddenManage1Score",hiddenManage1Num*hdManageMan1);
			Integer hiddenManage2Num = 0;
			StringBuffer hiddenManage2IdSql = new StringBuffer();
			hiddenManage2IdSql.append("SELECT id  from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '2' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hiddenManage2IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hiddenManage2IdSql.append(" and manage_duty_man_id = '"+selectUser.getId()+"'");
			List<String> hiddenManage2IdList = systemService.findListbySql(hiddenManage2IdSql.toString());
			if(hiddenManage2IdList!=null&&hiddenManage2IdList.size()>0){
				hiddenManage2Num+=hiddenManage2IdList.size();
			}
			request.setAttribute("hiddenManage2Num",hiddenManage2Num);
			request.setAttribute("hiddenManage2Score",hiddenManage2Num*hdManageMan2);
			Integer hiddenManage3Num = 0;
			StringBuffer hiddenManage3IdSql = new StringBuffer();
			hiddenManage3IdSql.append("SELECT id  from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '3' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hiddenManage3IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hiddenManage3IdSql.append(" and manage_duty_man_id = '"+selectUser.getId()+"'");
			List<String> hiddenManage3IdList = systemService.findListbySql(hiddenManage3IdSql.toString());
			if(hiddenManage3IdList!=null&&hiddenManage3IdList.size()>0){
				hiddenManage3Num+=hiddenManage3IdList.size();
			}
			request.setAttribute("hiddenManage3Num",hiddenManage3Num);
			request.setAttribute("hiddenManage3Score",hiddenManage3Num*hdManageMan3);
			Integer hiddenManage4Num = 0;
			StringBuffer hiddenManage4IdSql = new StringBuffer();
			hiddenManage4IdSql.append("SELECT id  from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '4' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hiddenManage4IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hiddenManage4IdSql.append(" and manage_duty_man_id = '"+selectUser.getId()+"'");
			List<String> hiddenManage4IdList = systemService.findListbySql(hiddenManage4IdSql.toString());
			if(hiddenManage4IdList!=null&&hiddenManage4IdList.size()>0){
				hiddenManage4Num+=hiddenManage4IdList.size();
			}
			request.setAttribute("hiddenManage4Num",hiddenManage4Num);
			request.setAttribute("hiddenManage4Score",hiddenManage4Num*hdManageMan4);
			request.setAttribute("hiddenManageScore",hiddenManage1Num*hdManageMan1+hiddenManage2Num*hdManageMan2+hiddenManage3Num*hdManageMan3+hiddenManage4Num*hdManageMan4);
			//隐患治理考核
			Integer hidden1Num = 0;
			StringBuffer hidden1IdSql= new StringBuffer();
			hidden1IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '1' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hidden1IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hidden1IdSql.append(" and duty_man_id = '"+selectUser.getId()+"'");
			List<String> hidden1IdList = systemService.findListbySql(hidden1IdSql.toString());
			if(hidden1IdList!=null&&hidden1IdList.size()>0){
				hidden1Num+=hidden1IdList.size();
			}
			request.setAttribute("hidden1Num",hidden1Num);
			request.setAttribute("hidden1Score",hidden1Num*hdDutyMan1);
			Integer hidden2Num = 0;
			StringBuffer hidden2IdSql= new StringBuffer();
			hidden2IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '2' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hidden2IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hidden2IdSql.append(" and duty_man_id = '"+selectUser.getId()+"'");
			List<String> hidden2IdList = systemService.findListbySql(hidden2IdSql.toString());
			if(hidden2IdList!=null&&hidden2IdList.size()>0){
				hidden2Num+=hidden2IdList.size();
			}
			request.setAttribute("hidden2Num",hidden2Num);
			request.setAttribute("hidden2Score",hidden2Num*hdDutyMan2);
			Integer hidden3Num = 0;
			StringBuffer hidden3IdSql= new StringBuffer();
			hidden3IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '3' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hidden3IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hidden3IdSql.append(" and duty_man_id = '"+selectUser.getId()+"'");
			List<String> hidden3IdList = systemService.findListbySql(hidden3IdSql.toString());
			if(hidden3IdList!=null&&hidden3IdList.size()>0){
				hidden3Num+=hidden3IdList.size();
			}
			request.setAttribute("hidden3Num",hidden3Num);
			request.setAttribute("hidden3Score",hidden3Num*hdDutyMan3);
			Integer hidden4Num = 0;
			StringBuffer hidden4IdSql= new StringBuffer();
			hidden4IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '4' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hidden4IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hidden4IdSql.append(" and duty_man_id = '"+selectUser.getId()+"'");
			List<String> hidden4IdList = systemService.findListbySql(hidden4IdSql.toString());
			if(hidden4IdList!=null&&hidden4IdList.size()>0){
				hidden4Num+=hidden4IdList.size();
			}
			request.setAttribute("hidden4Num",hidden4Num);
			request.setAttribute("hidden4Score",hidden4Num*hdDutyMan4);
			request.setAttribute("hiddenScore",hidden1Num*hdDutyMan1+hidden2Num*hdDutyMan2+hidden3Num*hdDutyMan3+hidden4Num*hdDutyMan4);
			//辨识组织考核
			StringBuffer taskManNumSql = new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String yearTemp = "2019-07";
			Date fromDate = null;
			try {
				fromDate = sdf.parse(yearTemp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date toDate = new Date();

			Calendar  from  =  Calendar.getInstance();
			from.setTime(fromDate);
			Calendar  to  =  Calendar.getInstance();
			to.setTime(toDate);
			//只要年月
			int fromYear = from.get(Calendar.YEAR);
			int fromMonth = from.get(Calendar.MONTH);

			int toYear = to.get(Calendar.YEAR);
			int toMonth = to.get(Calendar.MONTH);
			int toDay  = to.get(Calendar.DAY_OF_MONTH);
			int year = toYear  -  fromYear;
			int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
			taskManNumSql.append("SELECT  count(a.mon) count  FROM ( SELECT organizer_man, LEFT (create_date, 7) mon FROM t_b_risk_manage_task_all_manage y WHERE FIND_IN_SET( organizer_man, ( SELECT task_man FROM t_b_assessment_score ))   and manage_type = 'comprehensive' and LEFT (create_date, 7) < LEFT (now(), 7)  ");
			if(StringUtil.isNotEmpty(yearMonth)){
				taskManNumSql.append("  and LEFT (create_date, 7) = '"+yearMonth+"' ");
			}
			taskManNumSql.append(" and organizer_man = '"+selectUser.getId()+"'  GROUP BY LEFT (create_date, 7)) a  ");
			Long taskManNum = systemService.getCountForJdbc(taskManNumSql.toString());
			Long count = Long.valueOf(0);
			if(StringUtil.isNotEmpty(yearMonth)){
				count = 1-taskManNum;
			}else{
				count = month - taskManNum;
			}
			if(count<0){
				count = Long.valueOf(0);
			}
			request.setAttribute("taskManNum",count);
			request.setAttribute("taskMan1Score",count*taskManScore);
			String isTaskMan = taskManMap.get(selectUser.getId());
			if(StringUtil.isNotEmpty(isTaskMan)){
				request.setAttribute("isTaskMan","1");
			}else{
				request.setAttribute("isTaskMan","0");
			}
			StringBuffer taskManNumSql2 = new StringBuffer();
			taskManNumSql2.append("SELECT  count(a.xun) count FROM ( SELECT organizer_man, LEFT (create_date, 7) mon, (DAY(create_date) - 1) DIV 10 AS xun FROM t_b_risk_manage_task_all_manage y WHERE FIND_IN_SET( organizer_man, ( SELECT task_man2 FROM t_b_assessment_score )) AND manage_type = 'profession' and (DAY(create_date) - 1) DIV 10 < (DAY(now()) - 1) DIV 10  ");
			if(StringUtil.isNotEmpty(yearMonth)){
				taskManNumSql2.append("  and LEFT (create_date, 7) = '"+yearMonth+"' ");
			}
			taskManNumSql2.append(" and organizer_man = '"+selectUser.getId()+"' GROUP BY   mon, xun ) a  ");
			Long taskMan2Num = systemService.getCountForJdbc(taskManNumSql2.toString());
			Long count2 = Long.valueOf(0);
			Integer xun = toDay/10;
			if(StringUtil.isNotEmpty(yearMonth)){
				String dateTemp = sdf.format(new Date());
				if(dateTemp.equals(yearMonth)){
					count2 = Long.valueOf(xun*1);
				}else{
					count2 = Long.valueOf(3);
				}
			}else{
				count2 = month*3+xun-taskMan2Num;
			}
			request.setAttribute("taskMan2Num",count2);
			request.setAttribute("taskMan2Score",count2*taskManScore2);
			String isTaskMan2 = taskManMap2.get(selectUser.getId());
			if(StringUtil.isNotEmpty(isTaskMan2)){
				request.setAttribute("isTaskMan2","1");
			}else{
				request.setAttribute("isTaskMan2","0");
			}
			if(StringUtil.isNotEmpty(isTaskMan)&&StringUtil.isNotEmpty(isTaskMan2)){
				request.setAttribute("taskManScore",count*taskManScore+count2*taskManScore2);
			}else if(StringUtil.isNotEmpty(isTaskMan)){
				request.setAttribute("taskManScore",count*taskManScore);
			}else if (StringUtil.isNotEmpty(isTaskMan2)){
				request.setAttribute("taskManScore",count*taskManScore);
			}else{
				request.setAttribute("taskManScore",0);
			}
            if(StringUtil.isNotEmpty(yearMonth)){
                if(yearMonthDate.before(fromDate)){
                    request.setAttribute("taskManScore",0);
                    request.setAttribute("taskMan2Score",0);
                    request.setAttribute("taskMan1Score",0);
                    request.setAttribute("taskMan2Num",0);
                    request.setAttribute("taskManNum",0);
                }
            }
			return new ModelAndView("com/sdzk/buss/web/assessmentscore/scoreDetailUser");
		}else{
			TSDepart selectDepart = systemService.get(TSDepart.class,selectId);
			Integer hiddenManage1Num = 0;
			StringBuffer hiddenManage1IdSql = new StringBuffer();
			hiddenManage1IdSql.append("SELECT id from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '1' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hiddenManage1IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hiddenManage1IdSql.append(" and manage_duty_unit  = '"+selectDepart.getId()+"'");
			List<String> hiddenManage1IdList = systemService.findListbySql(hiddenManage1IdSql.toString());
			if(hiddenManage1IdList!=null&&hiddenManage1IdList.size()>0){
				hiddenManage1Num+=hiddenManage1IdList.size();
			}
			request.setAttribute("hiddenManage1Num",hiddenManage1Num);
			request.setAttribute("hiddenManage1Score",hiddenManage1Num*hdManageDuty1);
			Integer hiddenManage2Num = 0;
			StringBuffer hiddenManage2IdSql = new StringBuffer();
			hiddenManage2IdSql.append("SELECT id from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '2' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hiddenManage2IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hiddenManage2IdSql.append(" and manage_duty_unit  = '"+selectDepart.getId()+"'");
			List<String> hiddenManage2IdList = systemService.findListbySql(hiddenManage2IdSql.toString());
			if(hiddenManage2IdList!=null&&hiddenManage2IdList.size()>0){
				hiddenManage2Num+=hiddenManage2IdList.size();
			}
			request.setAttribute("hiddenManage2Num",hiddenManage2Num);
			request.setAttribute("hiddenManage2Score",hiddenManage2Num*hdManageDuty2);
			Integer hiddenManage3Num = 0;
			StringBuffer hiddenManage3IdSql = new StringBuffer();
			hiddenManage3IdSql.append("SELECT id from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '3' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hiddenManage3IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hiddenManage3IdSql.append(" and manage_duty_unit  = '"+selectDepart.getId()+"'");
			List<String> hiddenManage3IdList = systemService.findListbySql(hiddenManage3IdSql.toString());
			if(hiddenManage3IdList!=null&&hiddenManage3IdList.size()>0){
				hiddenManage3Num+=hiddenManage3IdList.size();
			}
			request.setAttribute("hiddenManage3Num",hiddenManage3Num);
			request.setAttribute("hiddenManage3Score",hiddenManage3Num*hdManageDuty3);
			Integer hiddenManage4Num = 0;
			StringBuffer hiddenManage4IdSql = new StringBuffer();
			hiddenManage4IdSql.append("SELECT id from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '4' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hiddenManage4IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hiddenManage4IdSql.append(" and manage_duty_unit  = '"+selectDepart.getId()+"'");
			List<String> hiddenManage4IdList = systemService.findListbySql(hiddenManage4IdSql.toString());
			if(hiddenManage4IdList!=null&&hiddenManage4IdList.size()>0){
				hiddenManage4Num+=hiddenManage4IdList.size();
			}
			request.setAttribute("hiddenManage4Num",hiddenManage4Num);
			request.setAttribute("hiddenManage4Score",hiddenManage4Num*hdManageDuty4);
			request.setAttribute("hiddenManageScore",hiddenManage1Num*hdManageDuty1+hiddenManage2Num*hdManageDuty2+hiddenManage3Num*hdManageDuty3+hiddenManage4Num*hdManageDuty4);
			Integer hidden1Num = 0;
			StringBuffer hidden1IdSql = new StringBuffer();
			hidden1IdSql.append("select e.id  from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '1' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hidden1IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hidden1IdSql.append(" and  duty_unit  = '"+selectDepart.getId()+"'");
			List<String> hidden1IdList = systemService.findListbySql(hidden1IdSql.toString());
			if(hidden1IdList!=null&&hidden1IdList.size()>0){
				hidden1Num+=hidden1IdList.size();
			}
			request.setAttribute("hidden1Num",hidden1Num);
			request.setAttribute("hidden1Score",hidden1Num*hdDutyUnit1);
			Integer hidden2Num = 0;
			StringBuffer hidden2IdSql = new StringBuffer();
			hidden2IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '2' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hidden2IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hidden2IdSql.append(" and  duty_unit  = '"+selectDepart.getId()+"'");
			List<String> hidden2IdList = systemService.findListbySql(hidden2IdSql.toString());
			if(hidden2IdList!=null&&hidden2IdList.size()>0){
				hidden2Num+=hidden2IdList.size();
			}
			request.setAttribute("hidden2Num",hidden2Num);
			request.setAttribute("hidden2Score",hidden2Num*hdDutyUnit2);
			Integer hidden3Num = 0;
			StringBuffer hidden3IdSql = new StringBuffer();
			hidden3IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '3' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hidden3IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hidden3IdSql.append(" and  duty_unit  = '"+selectDepart.getId()+"'");
			List<String> hidden3IdList = systemService.findListbySql(hidden3IdSql.toString());
			if(hidden3IdList!=null&&hidden3IdList.size()>0){
				hidden3Num+=hidden3IdList.size();
			}
			request.setAttribute("hidden3Num",hidden3Num);
			request.setAttribute("hidden3Score",hidden3Num*hdDutyUnit3);
			Integer hidden4Num = 0;
			StringBuffer hidden4IdSql = new StringBuffer();
			hidden4IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '4' ");
			if(StringUtil.isNotEmpty(yearMonth)){
				hidden4IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			hidden4IdSql.append(" and  duty_unit  = '"+selectDepart.getId()+"'");
			List<String> hidden4IdList = systemService.findListbySql(hidden4IdSql.toString());
			if(hidden4IdList!=null&&hidden4IdList.size()>0){
				hidden4Num+=hidden4IdList.size();
			}
			request.setAttribute("hidden4Num",hidden4Num);
			request.setAttribute("hidden4Score",hidden4Num*hdDutyUnit4);
			request.setAttribute("hiddenScore",hidden1Num*hdDutyUnit1+hidden2Num*hdDutyUnit2+hidden3Num*hdDutyUnit3+hidden4Num*hdDutyUnit4);
		}
		return new ModelAndView("com/sdzk/buss/web/assessmentscore/scoreDetailDepart");
	}

	@RequestMapping(params = "detailList")
	public ModelAndView detailList(HttpServletRequest request) {
		String type = request.getParameter("type");
		request.setAttribute("type",type);
        String yearMonth = request.getParameter("yearMonth");
        request.setAttribute("yearMonth",yearMonth);
		String selectId = request.getParameter("selectId");
		request.setAttribute("selectId",selectId);
		String selectType = request.getParameter("selectType");
		request.setAttribute("selectType",selectType);
		if(type.equals("1")){
			return new ModelAndView("com/sdzk/buss/web/assessmentscore/socreFxTaskList");
		}else if(type.equals("2")){
			return new ModelAndView("com/sdzk/buss/web/assessmentscore/socreGkTaskList");
		}else{
			return new ModelAndView("com/sdzk/buss/web/assessmentscore/socreHdList");
		}
	}


	@RequestMapping(params = "hdListDatagrid")
	public void hdListDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class, dataGrid);
		String yearMonth = request.getParameter("yearMonth");
		String selectId = request.getParameter("selectId");
		String selectType = request.getParameter("selectType");
		String type = request.getParameter("type");
		try {
			if(selectType.equals("user")){
				//管控情况考核
				StringBuffer hiddenManage1IdSql = new StringBuffer();
				hiddenManage1IdSql.append("SELECT id  from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '1' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManage1IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManage1IdSql.append(" and manage_duty_man_id = '"+selectId+"'");
				StringBuffer hiddenManage2IdSql = new StringBuffer();
				hiddenManage2IdSql.append("SELECT id  from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '2' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManage2IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManage2IdSql.append(" and manage_duty_man_id = '"+selectId+"'");
				StringBuffer hiddenManage3IdSql = new StringBuffer();
				hiddenManage3IdSql.append("SELECT id  from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '3' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManage3IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManage3IdSql.append(" and manage_duty_man_id = '"+selectId+"'");
				StringBuffer hiddenManage4IdSql = new StringBuffer();
				hiddenManage4IdSql.append("SELECT id  from t_b_hidden_danger_exam where manage_duty_man_id != '' and manage_duty_man_id is not null and hidden_nature = '4' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManage4IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManage4IdSql.append(" and manage_duty_man_id = '"+selectId+"'");
				//隐患治理考核
				StringBuffer hidden1IdSql= new StringBuffer();
				hidden1IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '1' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hidden1IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hidden1IdSql.append(" and duty_man_id = '"+selectId+"'");
				StringBuffer hidden2IdSql= new StringBuffer();
				hidden2IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '2' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hidden2IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hidden2IdSql.append(" and duty_man_id = '"+selectId+"'");
				StringBuffer hidden3IdSql= new StringBuffer();
				hidden3IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '3' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hidden3IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hidden3IdSql.append(" and duty_man_id = '"+selectId+"'");
				StringBuffer hidden4IdSql= new StringBuffer();
				hidden4IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_man_id != '' and duty_man_id is not null  and hidden_nature = '4' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hidden4IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hidden4IdSql.append(" and duty_man_id = '"+selectId+"'");
				if(type.equals("3.1")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hiddenManage1IdSql.toString()+")"));
				}else if (type.equals("3.2")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hiddenManage2IdSql.toString()+")"));
				}else if (type.equals("3.3")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hiddenManage3IdSql.toString()+")"));
				}else if (type.equals("3.4")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hiddenManage4IdSql.toString()+")"));
				}else if (type.equals("4.1")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hidden1IdSql.toString()+")"));
				}else if (type.equals("4.2")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hidden2IdSql.toString()+")"));
				}else if (type.equals("4.3")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hidden3IdSql.toString()+")"));
				}else if (type.equals("4.4")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hidden4IdSql.toString()+")"));
				}
			}
			if(selectType.equals("depart")){
				StringBuffer hiddenManage1IdSql = new StringBuffer();
				hiddenManage1IdSql.append("SELECT id from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '1' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManage1IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManage1IdSql.append(" and manage_duty_unit  = '"+selectId+"'");
				StringBuffer hiddenManage2IdSql = new StringBuffer();
				hiddenManage2IdSql.append("SELECT id from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '2' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManage2IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManage2IdSql.append(" and manage_duty_unit  = '"+selectId+"'");
				StringBuffer hiddenManage3IdSql = new StringBuffer();
				hiddenManage3IdSql.append("SELECT id from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '3' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManage3IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManage3IdSql.append(" and manage_duty_unit  = '"+selectId+"'");
				StringBuffer hiddenManage4IdSql = new StringBuffer();
				hiddenManage4IdSql.append("SELECT id from t_b_hidden_danger_exam where manage_duty_unit != '' and manage_duty_unit is not null and hidden_nature = '4' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hiddenManage4IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hiddenManage4IdSql.append(" and manage_duty_unit  = '"+selectId+"'");
				StringBuffer hidden1IdSql = new StringBuffer();
				hidden1IdSql.append("select e.id  from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '1' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hidden1IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hidden1IdSql.append(" and  duty_unit  = '"+selectId+"'");
				StringBuffer hidden2IdSql = new StringBuffer();
				hidden2IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '2' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hidden2IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hidden2IdSql.append(" and  duty_unit  = '"+selectId+"'");
				StringBuffer hidden3IdSql = new StringBuffer();
				hidden3IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '3' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hidden3IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hidden3IdSql.append(" and  duty_unit  = '"+selectId+"'");
				StringBuffer hidden4IdSql = new StringBuffer();
				hidden4IdSql.append("select e.id from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id WHERE (modify_date > limit_date or ( now() > limit_date and h.handlel_status = '1'))  and deal_type = '1' AND duty_unit != '' and duty_unit is not null and hidden_nature = '4' ");
				if(StringUtil.isNotEmpty(yearMonth)){
					hidden4IdSql.append(" and DATE_FORMAT(exam_date,'%Y-%m') = '"+yearMonth+"' ");
				}
				hidden4IdSql.append(" and  duty_unit  = '"+selectId+"'");
				if(type.equals("3.1")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hiddenManage1IdSql.toString()+")"));
				}else if (type.equals("3.2")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hiddenManage2IdSql.toString()+")"));
				}else if (type.equals("3.3")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hiddenManage3IdSql.toString()+")"));
				}else if (type.equals("3.4")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hiddenManage4IdSql.toString()+")"));
				}else if (type.equals("4.1")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hidden1IdSql.toString()+")"));
				}else if (type.equals("4.2")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hidden2IdSql.toString()+")"));
				}else if (type.equals("4.3")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hidden3IdSql.toString()+")"));
				}else if (type.equals("4.4")){
					cq.add(Restrictions.sqlRestriction("this_.id in ("+hidden4IdSql.toString()+")"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		if (dataGrid != null && dataGrid.getResults() != null) {
			if (dataGrid.getResults().size() > 0) {
				List<TBHiddenDangerExamEntity> list = dataGrid.getResults();
				for (TBHiddenDangerExamEntity t : list) {
					String names = "";
					String querySql = "select fill_card_manids man from t_b_hidden_danger_exam where id = '" + String.valueOf(t.getId()) + "'";
					List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);
					for (Map map : maplist) {
						String mans = String.valueOf(map.get("man"));
						if (StringUtils.isNotBlank(mans)) {
							String[] userIdArray = mans.split(",");

							for (String userid : userIdArray) {
								TSUser user = systemService.getEntity(TSUser.class, userid);
								if (user != null) {
									if (names == "") {
										names = names + user.getRealName() + "-" + user.getUserName();
									} else {
										names = names + "," + user.getRealName() + "-" + user.getUserName();
									}
								} else if (StringUtil.isNotEmpty(userid)) {
									if (names == "") {
										names = names + userid;
									} else {
										names = names + "," + userid;
									}
								}
							}
						}
					}
					t.setFillCardManNames(names);
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "fxTaskdatagrid")
	public void fxTaskdatagrid(RiskTaskEntity riskTask, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RiskTaskEntity.class, dataGrid);
		String yearMonth = request.getParameter("yearMonth");
		String selectId = request.getParameter("selectId");
		try {
			TSUser selectUser = systemService.get(TSUser.class,selectId);
			//辨识组织考核
			StringBuffer riskTask1IdSql = new StringBuffer();
			riskTask1IdSql.append("SELECT t.id riskTaskId FROM t_b_risk_task_participant_rel p LEFT JOIN t_s_base_user u ON p.participant_man_id = u.id LEFT JOIN t_b_risk_task t ON t.id = p.risk_task_id WHERE u.username NOT IN ( SELECT create_by FROM t_b_risk_identification WHERE create_date <= t.end_date  AND risk_task_id = t.id ) AND p.risk_task_id IN ( SELECT id FROM t_b_risk_task WHERE task_type != '4' ) ") ;
			if(StringUtil.isNotEmpty(yearMonth)){
				riskTask1IdSql.append(" and DATE_FORMAT(t.start_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			riskTask1IdSql.append(" AND p.participant_man_id = '"+selectUser.getId()+"'");
			StringBuffer riskTask4IdSql = new StringBuffer();
			riskTask4IdSql.append("SELECT t.id riskTaskId FROM t_b_risk_task_participant_rel p LEFT JOIN t_s_base_user u ON p.participant_man_id = u.id LEFT JOIN t_b_risk_task t ON t.id = p.risk_task_id WHERE u.username NOT IN ( SELECT create_by FROM t_b_risk_identification_post WHERE create_date <= t.end_date  AND risk_task_id = t.id ) AND p.risk_task_id IN ( SELECT id FROM t_b_risk_task WHERE task_type  = '4' ) ") ;
			if(StringUtil.isNotEmpty(yearMonth)){
				riskTask4IdSql.append(" and DATE_FORMAT(t.start_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			riskTask4IdSql.append(" AND p.participant_man_id = '"+selectUser.getId()+"'");
			cq.add(Restrictions.sqlRestriction("this_.id in ("+riskTask1IdSql+" union "+riskTask4IdSql+")"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		if(dataGrid != null && dataGrid.getResults() != null){
			List<RiskTaskEntity> list = dataGrid.getResults();
			for(RiskTaskEntity t : list){
				if (StringUtils.isNotBlank(t.getParticipantMan())){
					String[] ids = t.getParticipantMan().split(",");
					String name = "";
					for(String id : ids){
						TSUser user = systemService.getEntity(TSUser.class,id);
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
			}

		}
		TagUtil.datagrid(response, dataGrid);
	}


	@RequestMapping(params = "gkTaskdatagrid")
	public void gkTaskdatagrid(RiskManageTaskAllManageEntity riskManageTaskAllManage, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RiskManageTaskAllManageEntity.class, dataGrid);
		String yearMonth = request.getParameter("yearMonth");
		String selectId = request.getParameter("selectId");
		try {
			TSUser selectUser = systemService.get(TSUser.class,selectId);
			StringBuffer taskAllManageIdSql = new StringBuffer();
			taskAllManageIdSql.append("SELECT task_all_manage_id taskAllManageId  FROM t_b_risk_manage_task_all WHERE STATUS = '1' AND task_all_manage_id IS NOT NULL AND task_all_manage_id != '' AND id IN ( SELECT task_all_id FROM t_b_risk_manage_task WHERE handle_status = '0' ) ");
			if(StringUtil.isNotEmpty(yearMonth)){
				taskAllManageIdSql.append(" and DATE_FORMAT(create_date,'%Y-%m') = '"+yearMonth+"' ");
			}
			taskAllManageIdSql.append(" and create_by = '"+selectUser.getUserName()+"'");
			cq.add(Restrictions.sqlRestriction("this_.id in ("+taskAllManageIdSql+")"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		if (dataGrid != null && dataGrid.getResults() != null) {
			String sql = "SELECT task_all_manage_id from t_b_risk_manage_task_all WHERE status= '0' and (task_all_manage_id is NOT NULL or task_all_manage_id!='')  GROUP BY task_all_manage_id";
			List<String> tempList = systemService.findListbySql(sql);
			Map<String,String> map = new HashMap<>();
			for(String temp:tempList){
				map.put(temp,"0");
			}
			String hdCountSql = "SELECT\n" +
					"\tsum(count) count,\n" +
					"\ttemp.id id\n" +
					"FROM\n" +
					"\t(\n" +
					"\t\tSELECT\n" +
					"\t\t\tcount(0) count,\n" +
					"\t\t\ttam.id id\n" +
					"\t\tFROM\n" +
					"\t\t\tt_b_hidden_danger_exam hde\n" +
					"\t\tLEFT JOIN t_b_risk_manage_task_all ta ON ta.id = hde.task_all_id\n" +
					"\t\tLEFT JOIN t_b_risk_manage_task_all_manage tam ON tam.id = ta.task_all_manage_id\n" +
					"\t\tWHERE\n" +
					"\t\t\thde.task_all_id IS NOT NULL\n" +
					"\t\tAND hde.task_all_id != ''\n" +
					"\t\tAND tam.id != ''\n" +
					"\t\tAND tam.id IS NOT NULL\n" +
					"\t\tGROUP BY\n" +
					"\t\t\ttask_all_id\n" +
					"\t\tUNION ALL\n" +
					"\t\t\tSELECT\n" +
					"\t\t\t\tcount(0) count,\n" +
					"\t\t\t\ttam.id id\n" +
					"\t\t\tFROM\n" +
					"\t\t\t\tt_b_risk_manage_task_all_manage tam\n" +
					"\t\t\tLEFT JOIN t_b_risk_manage_task_all ta ON tam.id = ta.task_all_manage_id\n" +
					"\t\t\tLEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id\n" +
					"\t\t\tLEFT JOIN t_b_risk_manage_hazard_factor rmhf ON t.id = rmhf.risk_manage_task_id\n" +
					"\t\t\tLEFT JOIN t_b_risk_manage_rel_hd rmrh ON rmrh.risk_manage_hazard_factor_id = rmhf.id\n" +
					"\t\t\tWHERE\n" +
					"\t\t\t\trmrh.hd_id IN (\n" +
					"\t\t\t\t\tSELECT\n" +
					"\t\t\t\t\t\tid\n" +
					"\t\t\t\t\tFROM\n" +
					"\t\t\t\t\t\tt_b_hidden_danger_exam\n" +
					"\t\t\t\t)\n" +
					"\t\t\tGROUP BY\n" +
					"\t\t\t\ttam.id\n" +
					"\t) temp\n" +
					"GROUP BY\n" +
					"\ttemp.id";
			List<Map<String, Object>> hdCountList = systemService.findForJdbc(hdCountSql);
			Map<String, String> hdCountMap = new HashMap<>();
			if (hdCountList != null && hdCountList.size() > 0) {
				for (Map<String, Object> obj : hdCountList) {
					hdCountMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}
			String riskCountSql = "SELECT\n" +
					"\tcount(0) count,tam.id id\n" +
					"FROM\n" +
					"\tt_b_risk_manage_task_all_manage tam\n" +
					"LEFT JOIN t_b_risk_manage_task_all ta ON tam.id = ta.task_all_manage_id\n" +
					"LEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id\n" +
					"LEFT JOIN t_b_risk_identification ri on ri.risk_manage_task_id = t.id\n" +
					"WHERE ri.is_del = '0' and ri.status = '3'\n" +
					"GROUP BY tam.id";
			List<Map<String, Object>> riskCountList = systemService.findForJdbc(riskCountSql);
			Map<String, String> riskCountMap = new HashMap<>();
			if (riskCountList != null && riskCountList.size() > 0) {
				for (Map<String, Object> obj : riskCountList) {
					riskCountMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}String implCountSql = "SELECT count(ta.id) count, tam.id id FROM t_b_risk_manage_task_all_manage tam LEFT JOIN t_b_risk_manage_task_all ta ON tam.id = ta.task_all_manage_id LEFT JOIN t_b_risk_manage_task t ON ta.id = t.task_all_id LEFT JOIN t_b_risk_manage_hazard_factor hf ON hf.risk_manage_task_id = t.id WHERE hf.impl_detail != '' AND hf.impl_detail IS NOT NULL GROUP BY tam.id";
			List<Map<String, Object>> implCountList = systemService.findForJdbc(implCountSql);
			Map<String, String> implCountMap = new HashMap<>();
			if (implCountList != null && implCountList.size() > 0) {
				for (Map<String, Object> obj : implCountList) {
					implCountMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}
			List<RiskManageTaskAllManageEntity> entityList = dataGrid.getResults();
			if (null != entityList && entityList.size() > 0) {
				for (int i = 0; i < entityList.size(); i++) {
					RiskManageTaskAllManageEntity entity = entityList.get(i);
					String status = map.get(entity.getId());
					if(StringUtil.isNotEmpty(status)){
						entity.setStatus(status);
					}else {
						entity.setStatus("1");
					}
					String hdCount = hdCountMap.get(entity.getId());
					if(StringUtil.isNotEmpty(hdCount)){
						entity.setHdCount(hdCount);
					}else {
						entity.setHdCount("0");
					}
					String riskCount = riskCountMap.get(entity.getId());
					if(StringUtil.isNotEmpty(riskCount)){
						entity.setRiskCount(riskCount);
					}else {
						entity.setRiskCount("0");
					}
					String implCount = implCountMap.get(entity.getId());
					if(StringUtil.isNotEmpty(implCount)){
						entity.setImplCount(implCount);
					}else {
						entity.setImplCount("0");
					}

				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	//查看进度
	@RequestMapping(params = "viewingProgressList")
	public ModelAndView viewingProgressList(HttpServletRequest request) {
		String riskManageTaskAllManageId = request.getParameter("riskManageTaskAllManageId");
		request.setAttribute("riskManageTaskAllManageId",riskManageTaskAllManageId);
		return new ModelAndView("com/sdzk/buss/web/assessmentscore/viewingProgressTaskList");
	}
}
