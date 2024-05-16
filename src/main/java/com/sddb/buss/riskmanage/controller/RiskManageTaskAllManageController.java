package com.sddb.buss.riskmanage.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.identification.entity.RiskTaskParticipantRelEntity;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.buss.riskmanage.entity.*;
import com.sddb.common.Constants;
import com.sdzk.buss.api.utils.WebChatUtil;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.utils.SMSSenderUtil;
import com.sdzk.buss.web.quartz.QuartzJob;
import com.sdzk.buss.web.quartz.QuartzJobTask;
import com.sdzk.buss.web.quartz.service.QrtzManagerServiceI;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.quartz.Scheduler;
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

import com.sddb.buss.riskmanage.service.RiskManageTaskAllManageServiceI;

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
 * @Description: 任务管理
 * @author zhangdaihao
 * @date 2019-05-20 09:56:11
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/riskManageTaskAllManageController")
public class RiskManageTaskAllManageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RiskManageTaskAllManageController.class);

	@Autowired
	private RiskManageTaskAllManageServiceI riskManageTaskAllManageService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	@Resource(name="quartzScheduler")
	private Scheduler scheduler;

	@Autowired
	private QrtzManagerServiceI qrtzManagerServiceI;



	/**
	 * 任务管理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageTaskAllManageList");
	}

	/**
	 *
	 * 任务管理
	 *
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(RiskManageTaskAllManageEntity riskManageTaskAllManage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RiskManageTaskAllManageEntity.class, dataGrid);
		try {
			TSUser sessionUser = ResourceUtil.getSessionUserName();
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
			String recordList = request.getParameter("recordList");
			if(StringUtil.isEmpty(recordList)){
				if(!isAdmin){
					cq.or(Restrictions.eq("createBy",sessionUser.getUserName()), Restrictions.eq("organizerMan",sessionUser.getId()));
				}
			}
			String manageType = request.getParameter("manageType");
			if (StringUtil.isNotEmpty(manageType)) {
				cq.eq("manageType", manageType);
			}
			String manageTimeStart = request.getParameter("manageTime_begin");
			String manageTimeEnd = request.getParameter("manageTime_end");
			if(StringUtils.isNotBlank(manageTimeStart)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.ge("manageTime",sdf.parse(manageTimeStart));
			}
			if(StringUtils.isNotBlank(manageTimeEnd)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.le("manageTime",sdf.parse(manageTimeEnd));
			}
			String status = request.getParameter("status");
			if(StringUtil.isNotEmpty(status)){
				if("0".equals(status)){
					cq.add(Restrictions.sqlRestriction("this_.id in (SELECT task_all_manage_id from t_b_risk_manage_task_all WHERE status= '0' and (task_all_manage_id is NOT NULL or task_all_manage_id!='')  GROUP BY task_all_manage_id)"));
				}else{
					cq.add(Restrictions.sqlRestriction("this_.id not in (SELECT task_all_manage_id from t_b_risk_manage_task_all WHERE status= '0' and (task_all_manage_id is NOT NULL or task_all_manage_id!='')  GROUP BY task_all_manage_id)"));
				}

			}

			String organizerMan = request.getParameter("organizerMan");
			if(StringUtil.isNotEmpty(organizerMan)){
				cq.eq("organizerMan",organizerMan);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cq.add();
		this.riskManageTaskAllManageService.getDataGridReturn(cq, true);
		if (dataGrid != null && dataGrid.getResults() != null) {
			List<RiskManageTaskAllManageEntity> listTemp = dataGrid.getResults();
			StringBuffer ids = new StringBuffer();
			for (RiskManageTaskAllManageEntity entity : listTemp ) {
				if (StringUtil.isNotEmpty(ids.toString())) {
					ids.append(",");
				}
				ids.append("'").append(entity.getId()).append("'") ;
			}
			if (StringUtil.isEmpty(ids.toString())) {
				ids.append("''");
			}
			String sql = "SELECT task_all_manage_id from t_b_risk_manage_task_all WHERE status= '0' and (task_all_manage_id is NOT NULL or task_all_manage_id!='') and task_all_manage_id in ("+ids.toString()+")  GROUP BY task_all_manage_id";
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
					"\t\tAND tam.id IS NOT NULL and tam.id  in ("+ids.toString()+")\n" +
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
					"\t\t\t\t)  and tam.id  in ("+ids.toString()+")\n" +
					"\t\t\tGROUP BY\n" +
					"\t\t\t\ttam.id\n" +
					"\t) temp where temp.id in ("+ids.toString()+")\n" +
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
					"WHERE ri.is_del = '0' and ri.status != '0' and tam.id in ("+ids.toString()+")\n" +
					"GROUP BY tam.id";
			List<Map<String, Object>> riskCountList = systemService.findForJdbc(riskCountSql);
			Map<String, String> riskCountMap = new HashMap<>();
			if (riskCountList != null && riskCountList.size() > 0) {
				for (Map<String, Object> obj : riskCountList) {
					riskCountMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}
			String implCountSql = "SELECT count(ta.id) count, tam.id id FROM t_b_risk_manage_task_all_manage tam LEFT JOIN t_b_risk_manage_task_all ta ON tam.id = ta.task_all_manage_id LEFT JOIN t_b_risk_manage_task t ON ta.id = t.task_all_id LEFT JOIN t_b_risk_manage_hazard_factor hf ON hf.risk_manage_task_id = t.id WHERE hf.impl_detail != '' AND hf.impl_detail IS NOT NULL and tam.id in ("+ids.toString()+") GROUP BY tam.id";
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

	//任务记录个人
	@RequestMapping(params = "datagridGR")
	public void datagrid(RiskManageTaskAllEntity riskManageTaskAllEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String manageType = request.getParameter("manageType");
		CriteriaQuery cq = new CriteriaQuery(RiskManageTaskAllEntity.class, dataGrid);
		try{
			if(StringUtils.isNotBlank(manageType)){
				cq.eq("manageType",manageType);
			}
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			String manageTime_begin = request.getParameter("manageTime_begin");
			if(StringUtil.isNotEmpty(manageTime_begin)){
				cq.ge("manageTime", sdFormat.parse(manageTime_begin));
			}
			String manageTime_end = request.getParameter("manageTime_end");
			if(StringUtil.isNotEmpty(manageTime_end)){
				cq.le("manageTime", sdFormat.parse(manageTime_end));
			}
			String manageShift =request.getParameter("manageShift");
			if(StringUtil.isNotEmpty(manageShift)){
				cq.eq("manageShift",manageShift);
			}
			String status =request.getParameter("status");
			if(StringUtil.isNotEmpty(status)){
				cq.eq("status",status);
			}
			String createMan =request.getParameter("createMan");
			if(StringUtil.isNotEmpty(createMan)){
				TSUser tsuser = systemService.get(TSUser.class,createMan);
				if(tsuser!=null){
					cq.eq("createBy",tsuser.getUserName());
				}
			}
			cq.add(Restrictions.sqlRestriction("this_.id not in (select id from t_b_risk_manage_task_all where task_all_manage_id is not null or task_all_manage_id != '') "));
		}catch(Exception e){
			e.printStackTrace();
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		if(dataGrid != null && dataGrid.getResults() != null){
			List<RiskManageTaskAllEntity> listTemp = dataGrid.getResults();
			StringBuffer ids = new StringBuffer();
			for (RiskManageTaskAllEntity entity : listTemp ) {
				if (StringUtil.isNotEmpty(ids.toString())) {
					ids.append(",");
				}
				ids.append("'").append(entity.getId()).append("'") ;
			}
			if (StringUtil.isEmpty(ids.toString())) {
				ids.append("''");
			}
			String hdCountSql = "SELECT sum(count) count, temp.id id FROM ( SELECT count(0) count, task_all_id id FROM t_b_hidden_danger_exam WHERE task_all_id IS NOT NULL AND task_all_id != '' and task_all_id in ("+ids.toString()+") GROUP BY task_all_id UNION ALL SELECT count(0) count, ta.id id FROM t_b_risk_manage_task_all ta LEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id LEFT JOIN t_b_risk_manage_hazard_factor rmhf ON t.id = rmhf.risk_manage_task_id LEFT JOIN t_b_risk_manage_rel_hd rmrh ON rmrh.risk_manage_hazard_factor_id = rmhf.id WHERE rmrh.hd_id IN ( SELECT id FROM t_b_hidden_danger_exam )  and ta.id in ("+ids.toString()+")  GROUP BY ta.id ) temp  where temp.id in ("+ids.toString()+") GROUP BY temp.id";
			List<Map<String, Object>> hdCountList = systemService.findForJdbc(hdCountSql);
			Map<String, String> hdCountMap = new HashMap<>();
			if (hdCountList != null && hdCountList.size() > 0) {
				for (Map<String, Object> obj : hdCountList) {
					hdCountMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}

			String hdPostCountSql = "SELECT sum(count)  count, temp.id id FROM ( SELECT count(0) count, task_all_id id FROM t_b_hidden_danger_exam WHERE task_all_id IS NOT NULL AND task_all_id != ''  and task_all_id in ("+ids.toString()+")  GROUP BY task_all_id UNION ALL SELECT count(0) count, ta.id id FROM t_b_risk_manage_task_all ta LEFT JOIN t_b_risk_manage_post_task t ON t.post_task_all_id = ta.id LEFT JOIN t_b_risk_manage_post_hazard_factor rmhf ON t.id = rmhf.risk_manage_post_task_id LEFT JOIN t_b_risk_manage_post_rel_hd rmrh ON rmrh.risk_manage_post_hazard_factor_id = rmhf.id WHERE rmrh.hd_id IN ( SELECT id FROM t_b_hidden_danger_exam )  and ta.id in ("+ids.toString()+") GROUP BY ta.id ) temp where temp.id in ("+ids.toString()+")  GROUP BY temp.id";
			List<Map<String, Object>> hdPostCountList = systemService.findForJdbc(hdPostCountSql);
			Map<String, String> hdPostCountMap = new HashMap<>();
			if (hdPostCountList != null && hdPostCountList.size() > 0) {
				for (Map<String, Object> obj : hdPostCountList) {
					hdPostCountMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}

			String hdYHCountSql = "SELECT count(0) count, task_all_id id from\n" +
					"t_b_hidden_danger_exam   WHERE task_all_id is not null and task_all_id != ''  and task_all_id in ("+ids.toString()+")  GROUP BY task_all_id";
			List<Map<String, Object>> hdYHCountList = systemService.findForJdbc(hdYHCountSql);
			Map<String, String> hdYHCountMap = new HashMap<>();
			if (hdYHCountList != null && hdYHCountList.size() > 0) {
				for (Map<String, Object> obj : hdYHCountList) {
					hdYHCountMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}

			String riskCountSql = "SELECT\n" +
					"\tcount(0) count,\n" +
					"\tta.id id\n" +
					"FROM\n" +
					"\tt_b_risk_manage_task_all ta\n" +
					"LEFT JOIN t_b_risk_manage_task t ON ta.id = t.task_all_id\n" +
					"LEFT JOIN t_b_risk_identification ri ON ri.risk_manage_task_id = t.id\n" +
					"WHERE\n" +
					"\tri.is_del = '0' and ri.status != '0' and ta.id in ("+ids.toString()+")\n" +
					"GROUP BY\n" +
					"\tta.id";
			List<Map<String, Object>> riskCountSqlList = systemService.findForJdbc(riskCountSql);
			Map<String, String> riskCountSqlMap = new HashMap<>();
			if (riskCountSqlList != null && riskCountSqlList.size() > 0) {
				for (Map<String, Object> obj : riskCountSqlList) {
					riskCountSqlMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}
			String implCountSql = "SELECT count(ta.id) count, ta.id id FROM t_b_risk_manage_task_all ta LEFT JOIN t_b_risk_manage_task t ON ta.id = t.task_all_id LEFT JOIN t_b_risk_manage_hazard_factor hf ON hf.risk_manage_task_id = t.id WHERE hf.impl_detail != '' AND hf.impl_detail IS NOT NULL  and ta.id in ("+ids.toString()+") GROUP BY ta.id";
			List<Map<String, Object>> implCountList = systemService.findForJdbc(implCountSql);
			Map<String, String> implCountMap = new HashMap<>();
			if (implCountList != null && implCountList.size() > 0) {
				for (Map<String, Object> obj : implCountList) {
					implCountMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}
			String riskPostCountSql = "SELECT\n" +
					"\tcount(0) count,\n" +
					"\tta.id id\n" +
					"FROM\n" +
					"\tt_b_risk_manage_task_all ta\n" +
					"LEFT JOIN t_b_risk_manage_post_task t ON ta.id = t.post_task_all_id\n" +
					"LEFT JOIN t_b_risk_identification ri ON ri.risk_manage_task_id = t.id\n" +
					"WHERE\n" +
					"\tri.is_del = '0' and  ri.status != '0'  and ta.id in ("+ids.toString()+")\n" +
					"GROUP BY\n" +
					"\tta.id";
			List<Map<String, Object>> riskPostCountSqlList = systemService.findForJdbc(riskPostCountSql);
			Map<String, String> riskPostCountSqlMap = new HashMap<>();
			if (riskPostCountSqlList != null && riskPostCountSqlList.size() > 0) {
				for (Map<String, Object> obj : riskPostCountSqlList) {
					riskPostCountSqlMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}
			String implCountPostSql = "SELECT count(ta.id) count, ta.id id FROM t_b_risk_manage_task_all ta LEFT JOIN t_b_risk_manage_post_task t ON ta.id = t.post_task_all_id LEFT JOIN t_b_risk_manage_post_hazard_factor hf ON hf.risk_manage_post_task_id = t.id WHERE hf.impl_detail != '' AND hf.impl_detail IS NOT NULL  and ta.id in ("+ids.toString()+") GROUP BY ta.id";
			List<Map<String, Object>> implCountPostList = systemService.findForJdbc(implCountPostSql);
			Map<String, String> implCountPostMap = new HashMap<>();
			if (implCountPostList != null && implCountPostList.size() > 0) {
				for (Map<String, Object> obj : implCountPostList) {
					implCountPostMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}
			String riskYHCountSql = "SELECT count(0) count,risk_manage_task_all_id id from t_b_risk_identification WHERE is_del = '0' and status != '0' and risk_manage_task_all_id != '' and risk_manage_task_all_id is not null  and risk_manage_task_all_id in ("+ids.toString()+") GROUP BY risk_manage_task_all_id";
			List<Map<String, Object>> riskYHCountList = systemService.findForJdbc(riskYHCountSql);
			Map<String, String> riskYHCountMap = new HashMap<>();
			if (riskYHCountList != null && riskYHCountList.size() > 0) {
				for (Map<String, Object> obj : riskYHCountList) {
					riskYHCountMap.put(obj.get("id").toString(), obj.get("count").toString());
				}
			}
			List<RiskManageTaskAllEntity> list = dataGrid.getResults();
			for(RiskManageTaskAllEntity t : list){
				if(StringUtil.isNotEmpty(t.getManageType())){
					if(t.getManageType().equals("comprehensive")||t.getManageType().equals("profession")||t.getManageType().equals("team")||t.getManageType().equals("group")){
						t.setIsExport("0");
						String hdCount = hdCountMap.get(t.getId());
						if(StringUtil.isNotEmpty(hdCount)){
							t.setHdCount(hdCount);
						}else {
							t.setHdCount("0");
						}
						String riskCount = riskCountSqlMap.get(t.getId());
						if(StringUtil.isNotEmpty(riskCount)){
							t.setRiskCount(riskCount);
						}else {
							t.setRiskCount("0");
						}
						String implCount = implCountMap.get(t.getId());
						if(StringUtil.isNotEmpty(implCount)){
							t.setImplCount(implCount);
						}else {
							t.setImplCount("0");
						}
					}else if(t.getManageType().equals("post")){
						t.setIsExport("0");
						String hdCount = hdPostCountMap.get(t.getId());
						if(StringUtil.isNotEmpty(hdCount)){
							t.setHdCount(hdCount);
						}else {
							t.setHdCount("0");
						}
						String riskCount = riskPostCountSqlMap.get(t.getId());
						if(StringUtil.isNotEmpty(riskCount)){
							t.setRiskCount(riskCount);
						}else {
							t.setRiskCount("0");
						}
						String implCount = implCountPostMap.get(t.getId());
						if(StringUtil.isNotEmpty(implCount)){
							t.setImplCount(implCount);
						}else {
							t.setImplCount("0");
						}
					}else{
						String hdCount = hdYHCountMap.get(t.getId());
						if(StringUtil.isNotEmpty(hdCount)){
							t.setHdCount(hdCount);
						}else {
							t.setHdCount("0");
						}
						String riskCount = riskYHCountMap.get(t.getId());
						if(StringUtil.isNotEmpty(riskCount)){
							t.setRiskCount(riskCount);
						}else {
							t.setRiskCount("0");
						}
						t.setIsExport("1");
					}
				}


				if(StringUtil.isNotEmpty(t.getTaskAllManage())){
					t.setEndTask("1");
					TSUser tsUser = systemService.get(TSUser.class,t.getTaskAllManage().getOrganizerMan());
					t.setCreateNameTemp(tsUser.getRealName());
				}else{
					t.setEndTask("0");
					t.setCreateNameTemp(t.getCreateName());
				}
			}

		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除任务管理
	 *
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(RiskManageTaskAllManageEntity riskManageTaskAllManage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		riskManageTaskAllManage = systemService.getEntity(RiskManageTaskAllManageEntity.class, riskManageTaskAllManage.getId());
		message = "任务管理删除成功";
		riskManageTaskAllManageService.delete(riskManageTaskAllManage);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}


	/**
	 * 批量删除任务
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDelAll")
	@ResponseBody
	public AjaxJson doBatchDelAll(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "删除成功";
		try{
			String taskAllManageId = request.getParameter("taskAllManageId");
			if(StringUtil.isNotEmpty(taskAllManageId)){
				String taskAllIdSql = "SELECT id from t_b_risk_manage_task_all WHERE task_all_manage_id = '"+taskAllManageId+"'";
				List<String> taskAllIdList = systemService.findListbySql(taskAllIdSql);
				if(taskAllIdList!=null&&taskAllIdList.size()>0){
					for(String taskAllId:taskAllIdList){
						RiskManageTaskAllEntity riskManageTaskAllEntity = this.systemService.getEntity(RiskManageTaskAllEntity.class,taskAllId);
						String sql="select id from t_b_risk_manage_task  where task_all_id='"+riskManageTaskAllEntity.getId()+"'";
						List <String> riskIds=systemService.findListbySql(sql);
						if(riskIds.size()>0) {
							for (String id : riskIds) {
								RiskManageTaskEntity riskManageTaskEntity = this.systemService.getEntity(RiskManageTaskEntity.class,id);
								String riskId = riskManageTaskEntity.getRisk().getId();
								//首先删除自动生成的对危害因素的管控
								CriteriaQuery hazardCq = new CriteriaQuery(RiskManageHazardFactorEntity.class);
								hazardCq.eq("taskId",riskManageTaskEntity.getTaskId());
								hazardCq.eq("risk.id",riskId);
								hazardCq.add();
								List<RiskManageHazardFactorEntity> hazardList = this.systemService.getListByCriteriaQuery(hazardCq,false);
								if(null!=hazardList && hazardList.size()>0){
									this.systemService.deleteAllEntitie(hazardList);
								}
								this.systemService.delete(riskManageTaskEntity);
							}
						}
						this.systemService.delete(riskManageTaskAllEntity);
					}
				}
				RiskManageTaskAllManageEntity riskManageTaskAllManage = systemService.getEntity(RiskManageTaskAllManageEntity.class, taskAllManageId);
				String sql ="DELETE from t_b_risk_manage_task_all_manage_check_rel WHERE task_all_manage_id = '"+taskAllManageId+"'";
				systemService.executeSql(sql);
				riskManageTaskAllManageService.delete(riskManageTaskAllManage);

			}

		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 隐患排查-任务管理-新增任务保存、追加人员保存
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(RiskManageTaskAllManageEntity riskManageTaskAllManage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		// 任务追加人员
		if (StringUtil.isNotEmpty(riskManageTaskAllManage.getId())) {
			message = "任务管理更新成功";
			RiskManageTaskAllManageEntity r = riskManageTaskAllManageService.get(RiskManageTaskAllManageEntity.class, riskManageTaskAllManage.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(riskManageTaskAllManage, r);
				riskManageTaskAllManageService.saveOrUpdate(r);
				message = "人员追加成功";
				TSUser organizerMan = systemService.get(TSUser.class,r.getOrganizerMan());
				String createTime = DateUtils.formatDate(r.getCreateDate(),"yyyy-MM-dd HH:mm");
				String content = "【双防平台】通知：尊敬的双防用户，您好！"+organizerMan.getRealName() +"组织了管控任务：" +createTime+
						" 管控名称："+r.getManageName()+"，管控类型：" +DicUtil.getTypeNameByCode("taskManageType",r.getManageType())+ ",请进行管控。";

				String checkData = request.getParameter("checkData");
				//解析JSON数据，然后将其保存到 t_b_risk_manage_task_all_manage_check_rel 表中
				if(StringUtils.isNotBlank(checkData)){
					JSONArray jsonArray = JSONArray.fromObject(checkData);
					List<RiskManageTaskAllManageCheckRelEntity> riskManageTaskAllManageCheckRelEntityList = new ArrayList<RiskManageTaskAllManageCheckRelEntity>();
					List<RiskManageTaskAllManageTempEntity> riskManageTaskAllManageTempEntityList = new ArrayList<RiskManageTaskAllManageTempEntity>();
					try {
						if (jsonArray.size()>0) {
							Map<String,String> checkManMap = new HashMap<>();
							for (int i=0; i<jsonArray.size(); i++) {
								RiskManageTaskAllManageTempEntity riskManageTaskAllManageTempEntity = new RiskManageTaskAllManageTempEntity();
								riskManageTaskAllManageTempEntity.setCheckAddress(jsonArray.getJSONObject(i).getString("address"));
								riskManageTaskAllManageTempEntity.setCheckMan(jsonArray.getJSONObject(i).getString("checkMan"));
								riskManageTaskAllManageTempEntity.setTaskAllManageId(r.getId());
								riskManageTaskAllManageTempEntityList.add(riskManageTaskAllManageTempEntity);
								for(String address : jsonArray.getJSONObject(i).getString("address").split(",")) {
									for(String checKMan : jsonArray.getJSONObject(i).getString("checkMan").split(",")) {
										String isExist = checkManMap.get(checKMan);
										if(StringUtil.isEmpty(isExist)){
											checkManMap.put(checKMan,"1");
											RiskManageTaskAllManageCheckRelEntity riskManageTaskAllManageCheckRelEntity = new RiskManageTaskAllManageCheckRelEntity();
											riskManageTaskAllManageCheckRelEntity.setCheckAddress(address);
											riskManageTaskAllManageCheckRelEntity.setCheckMan(checKMan);
											riskManageTaskAllManageCheckRelEntity.setTaskAllManageId(r.getId());
											riskManageTaskAllManageCheckRelEntityList.add(riskManageTaskAllManageCheckRelEntity);
											List<String> mobilePhone=systemService.findListbySql("select mobilePhone from t_s_user  where id = '"+checKMan+"'");
											if(mobilePhone != null && mobilePhone.size()>0 && StringUtils.isNotBlank(mobilePhone.get(0))){
												SMSSenderUtil.sendSMS(content,mobilePhone.get(0), com.sdzk.buss.web.common.Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
											}
											List<String> weChatPhones=systemService.findListbySql("select weChatPhone from t_s_user  where id = '"+checKMan+"'");
											if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
												WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
											}
										}else{
											for(RiskManageTaskAllManageCheckRelEntity bean:riskManageTaskAllManageCheckRelEntityList){
												if(StringUtil.isNotEmpty(checKMan)&&StringUtil.isNotEmpty(bean.getCheckMan())&&checKMan.equals(bean.getCheckMan())){
													List<String> addressList =new ArrayList<>();
													String addressIds = bean.getCheckAddress();
													if(StringUtil.isNotEmpty(addressIds)){
														String[] values = addressIds.split(",");
														addressList = Arrays.asList(values);
													}
													if(!addressList.contains(address)){
														bean.setCheckAddress(addressIds+","+address);
													}
												}
											}
										}
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					systemService.batchSave(riskManageTaskAllManageCheckRelEntityList);
					systemService.batchSave(riskManageTaskAllManageTempEntityList);
					//自动生成任务
					for(RiskManageTaskAllManageCheckRelEntity bean:riskManageTaskAllManageCheckRelEntityList){
						RiskManageTaskAllEntity riskManageTaskAllEntity = new RiskManageTaskAllEntity();
						riskManageTaskAllEntity.setStatus("0");
						riskManageTaskAllEntity.setManageType(r.getManageType());
						riskManageTaskAllEntity.setManageTime(r.getManageTime());
						riskManageTaskAllEntity.setTaskAllManage(r);
						TSUser checkUser = systemService.get(TSUser.class,bean.getCheckMan());
						riskManageTaskAllEntity.setCreateBy(checkUser.getUserName());
						riskManageTaskAllEntity.setCreateName(checkUser.getRealName());
						systemService.save(riskManageTaskAllEntity);
						bean.setTaskAllId(riskManageTaskAllEntity.getId());
						systemService.saveOrUpdate(bean);
						StringBuffer addressList = new StringBuffer();
						String addressIds = bean.getCheckAddress();
						for(String addressId:addressIds.split(",")){
							addressList.append("'"+addressId+"',");
						}
						addressList.append("''");
						//风险清单添加风险
						String riskIdSql = "SELECT id from t_b_risk_identification WHERE is_del='0' and status = '3' and address_id in ("+addressList.toString()+") and risk_type in (SELECT risk_type from t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and is_del = '0')" +
								" AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ) AND id NOT IN ( SELECT risk_id FROM t_b_risk_manage ) ";
						List<String> list = systemService.findListbySql(riskIdSql);
						if(list!=null&&list.size()>0) {
							for (String id : list) {
								RiskManageEntity riskManageEntity = null;
								riskManageEntity = new RiskManageEntity();
								RiskIdentificationEntity t = this.systemService.getEntity(RiskIdentificationEntity.class, id);
								riskManageEntity.setRisk(t);
								this.systemService.save(riskManageEntity);
								systemService.addLog("风险清单添加风险\"" + riskManageEntity.getId() + "\"成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
							}
						}
						riskIdSql = "SELECT id from t_b_risk_identification WHERE is_del='0' and status = '3' and address_id in ("+addressList.toString()+") and risk_type in (SELECT risk_type from t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and is_del = '0')" +
								" AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ) ";
						list = systemService.findListbySql(riskIdSql);
						if(list!=null&&list.size()>0) {
							//自动生成管控清单
							String taskId = UUIDGenerator.generate();
							String sql = "SELECT id FROM t_b_risk_manage WHERE risk_id IN ( SELECT id FROM t_b_risk_identification WHERE is_del = '0' AND STATUS = '3' and ( NOW() < exp_date or exp_date is null  ) and NOW() > identifi_date AND address_id in ("+addressList.toString()+") AND risk_type IN ( SELECT risk_type FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' ) AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ))";
							List<String> ids = systemService.findListbySql(sql);
							if(ids!=null&&ids.size()>0){
								RiskManageTaskEntity riskManageTaskEntity = null;
								for(String id : ids){
									RiskManageEntity t = this.systemService.getEntity(RiskManageEntity.class,id);
									String riskSql = "SELECT id FROM t_b_risk_identification WHERE id = (SELECT risk_id FROM t_b_risk_manage WHERE id ='"+id+"' )";
									List<String> tempList = systemService.findListbySql(riskSql);
									if(!tempList.isEmpty() && tempList.size()>0){
										riskManageTaskEntity = new RiskManageTaskEntity();
										riskManageTaskEntity.setTaskId(taskId);
										riskManageTaskEntity.setRiskManage(t);
										riskManageTaskEntity.setRisk(t.getRisk());
										riskManageTaskEntity.setCreateBy(checkUser.getUserName());
										riskManageTaskEntity.setCreateName(checkUser.getRealName());
										riskManageTaskEntity.setManageType(riskManageTaskAllEntity.getManageType());
										riskManageTaskEntity.setTaskAllId(riskManageTaskAllEntity.getId());
										List<RiskFactortsRel> riskFactorRelList = t.getRisk().getRelList();
										if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
											riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
										} else {
											riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
										}
										this.systemService.save(riskManageTaskEntity);

										//自动生成对危害因素的管控
										if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
											List<String> majorList =new ArrayList<>();
											String majorSql =  "SELECT major FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and major != '' and major is not null";
											List<String> major = systemService.findListbySql(majorSql);
											if(major!=null&&major.size()>0){
												String temp = major.get(0);
												String[] values = temp.split(",");
												majorList = Arrays.asList(values);
											}
											for(int i=0;i<riskFactorRelList.size();i++){
												String level = riskFactorRelList.get(i).getHfLevel();
												if(StringUtil.isNotEmpty(level)){
													if(level.equals("1")||level.equals("2")){
														if(majorList!=null){
															if(StringUtil.isNotEmpty(riskFactorRelList.get(i).getHazardFactorsEntity().getMajor())&&majorList.contains(riskFactorRelList.get(i).getHazardFactorsEntity().getMajor())){
																RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
																riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
																riskManageHazardFactorEntity.setTaskId(taskId);
																riskManageHazardFactorEntity.setRisk(t.getRisk());
																riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
																riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
																riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
																riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
																riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
																this.systemService.save(riskManageHazardFactorEntity);
															}
														}else{
															RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
															riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
															riskManageHazardFactorEntity.setTaskId(taskId);
															riskManageHazardFactorEntity.setRisk(t.getRisk());
															riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
															riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
															riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
															riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
															riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
															this.systemService.save(riskManageHazardFactorEntity);
														}
													}
												}
											}
											String manageNumSql = "SELECT manage_num FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and manage_num != '' and manage_num is not null";
											List<String> manageNumList = systemService.findListbySql(manageNumSql);
											int manageNum = 10;
											if(manageNumList!=null&&manageNumList.size()>0){
												manageNum = Integer.parseInt(manageNumList.get(0));
											}
											String hazardFactorSql ="";
											if(major!=null&&major.size()>0){
												hazardFactorSql = "SELECT hazard_factors_id from t_b_risk_factors_rel  LEFT JOIN t_b_hazard_factors hf on hf.id = hazard_factors_id WHERE   FIND_IN_SET(hf.major,'"+major.get(0)+"')  and hfLevel !='1' and hfLevel != '2' and risk_identification_id = '"+t.getRisk().getId()+"' ORDER BY RAND() LIMIT "+manageNum+"";
											}else{
												hazardFactorSql = "SELECT hazard_factors_id from t_b_risk_factors_rel  LEFT JOIN t_b_hazard_factors hf on hf.id = hazard_factors_id WHERE    hfLevel !='1' and hfLevel != '2' and risk_identification_id = '"+t.getRisk().getId()+"' ORDER BY RAND() LIMIT "+manageNum+"";
											}

											List<String> hazardFactorList = systemService.findListbySql(hazardFactorSql);
											for(String hazardFactorId : hazardFactorList){
												RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
												riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
												riskManageHazardFactorEntity.setTaskId(taskId);
												riskManageHazardFactorEntity.setRisk(t.getRisk());
												riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
												riskManageHazardFactorEntity.setHazardFactor(systemService.get(HazardFactorsEntity.class,hazardFactorId));
												riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
												riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
												riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
												this.systemService.save(riskManageHazardFactorEntity);
											}
										}
										//systemService.addLog("风险管控任务\""+ riskManageTaskEntity.getId()+"\"添加风险成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
										String riskManageHazardFactorSql = "SELECT id from t_b_risk_manage_hazard_factor WHERE risk_manage_task_id = '"+riskManageTaskEntity.getId()+"'";
										List<String> riskManageHazardFactorEntityList = systemService.findListbySql(riskManageHazardFactorSql);
										if(null==riskManageHazardFactorEntityList || riskManageHazardFactorEntityList.size()==0){
											riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
											this.systemService.delete(riskManageTaskEntity);
										}
									}
								}
							}
						}
				/*String sql = "SELECT id FROM t_b_risk_manage_task WHERE handle_status = '0' AND task_all_id = '"+riskManageTaskAllEntity.getId()+"'";
				List<String> temp = this.systemService.findListbySql(sql);
				if(temp==null||temp.size()==0){
					riskManageTaskAllEntity.setStatus("1");
					this.systemService.saveOrUpdate(riskManageTaskAllEntity);
				}*/
					}
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "任务管理更新失败";
			}
		} else
		{
			// 添加任务
			message = "任务添加成功";
			String hegang = ResourceUtil.getConfigByName("hegang");
			if(hegang.equals("true")){
				String manageNameTemp = request.getParameter("manageNameTemp");
				if(StringUtil.isNotEmpty(manageNameTemp)){
					riskManageTaskAllManage.setManageName(manageNameTemp);
				}
			}
			riskManageTaskAllManageService.save(riskManageTaskAllManage);
			String checkData = request.getParameter("checkData");
			TSUser organizerMan = systemService.get(TSUser.class,riskManageTaskAllManage.getOrganizerMan());
			String createTime = DateUtils.formatDate(riskManageTaskAllManage.getCreateDate(),"yyyy-MM-dd HH:mm");
			String content = "【双防平台】通知：尊敬的双防用户，您好！"+organizerMan.getRealName() +"组织了新的管控任务：" +createTime+
					" 管控名称："+riskManageTaskAllManage.getManageName()+"，管控类型：" +DicUtil.getTypeNameByCode("taskManageType",riskManageTaskAllManage.getManageType())+ ",请进行管控。";

			//解析JSON数据，然后将其保存到 t_b_risk_manage_task_all_manage_check_rel 表中
			if(StringUtils.isNotBlank(checkData)){
				JSONArray jsonArray = JSONArray.fromObject(checkData);
				List<RiskManageTaskAllManageCheckRelEntity> riskManageTaskAllManageCheckRelEntityList = new ArrayList<RiskManageTaskAllManageCheckRelEntity>();
				List<RiskManageTaskAllManageTempEntity> riskManageTaskAllManageTempEntityList = new ArrayList<RiskManageTaskAllManageTempEntity>();
				try {
					if (jsonArray.size()>0) {
						Map<String,String> checkManMap = new HashMap<>();
						for (int i=0; i<jsonArray.size(); i++) {
							RiskManageTaskAllManageTempEntity riskManageTaskAllManageTempEntity = new RiskManageTaskAllManageTempEntity();
							riskManageTaskAllManageTempEntity.setCheckAddress(jsonArray.getJSONObject(i).getString("address"));
							riskManageTaskAllManageTempEntity.setCheckMan(jsonArray.getJSONObject(i).getString("checkMan"));
							riskManageTaskAllManageTempEntity.setTaskAllManageId(riskManageTaskAllManage.getId());
							riskManageTaskAllManageTempEntityList.add(riskManageTaskAllManageTempEntity);
							for(String address : jsonArray.getJSONObject(i).getString("address").split(",")) {
								for(String checKMan : jsonArray.getJSONObject(i).getString("checkMan").split(",")) {
									String isExist = checkManMap.get(checKMan);
									if(StringUtil.isEmpty(isExist)){
										checkManMap.put(checKMan,"1");
										RiskManageTaskAllManageCheckRelEntity riskManageTaskAllManageCheckRelEntity = new RiskManageTaskAllManageCheckRelEntity();
										riskManageTaskAllManageCheckRelEntity.setCheckAddress(address);
										riskManageTaskAllManageCheckRelEntity.setCheckMan(checKMan);
										riskManageTaskAllManageCheckRelEntity.setTaskAllManageId(riskManageTaskAllManage.getId());
										riskManageTaskAllManageCheckRelEntityList.add(riskManageTaskAllManageCheckRelEntity);
										List<String> mobilePhone=systemService.findListbySql("select mobilePhone from t_s_user  where id = '"+checKMan+"'");
										if(mobilePhone != null && mobilePhone.size()>0 && StringUtils.isNotBlank(mobilePhone.get(0))){
											SMSSenderUtil.sendSMS(content,mobilePhone.get(0), com.sdzk.buss.web.common.Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
										}
										List<String> weChatPhones=systemService.findListbySql("select weChatPhone from t_s_user  where id = '"+checKMan+"'");
										if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
											WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
										}
									}else{
										for(RiskManageTaskAllManageCheckRelEntity bean:riskManageTaskAllManageCheckRelEntityList){
											if(StringUtil.isNotEmpty(checKMan)&&StringUtil.isNotEmpty(bean.getCheckMan())&&checKMan.equals(bean.getCheckMan())){
												List<String> addressList =new ArrayList<>();
												String addressIds = bean.getCheckAddress();
												if(StringUtil.isNotEmpty(addressIds)){
													String[] values = addressIds.split(",");
													addressList = Arrays.asList(values);
												}
												if(!addressList.contains(address)){
													bean.setCheckAddress(addressIds+","+address);
												}
											}
										}
									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				systemService.batchSave(riskManageTaskAllManageCheckRelEntityList);
				systemService.batchSave(riskManageTaskAllManageTempEntityList);
			}
			//自动生成任务
			CriteriaQuery cq = new CriteriaQuery(RiskManageTaskAllManageCheckRelEntity.class);
			cq.eq("taskAllManageId",riskManageTaskAllManage.getId());
			cq.add();
			List<RiskManageTaskAllManageCheckRelEntity> riskManageTaskAllManageCheckRelEntityList =systemService.getListByCriteriaQuery(cq,false);
			for(RiskManageTaskAllManageCheckRelEntity bean:riskManageTaskAllManageCheckRelEntityList){
				RiskManageTaskAllEntity riskManageTaskAllEntity = new RiskManageTaskAllEntity();
				riskManageTaskAllEntity.setStatus("0");
				riskManageTaskAllEntity.setManageType(riskManageTaskAllManage.getManageType());
				riskManageTaskAllEntity.setManageTime(riskManageTaskAllManage.getManageTime());
				riskManageTaskAllEntity.setTaskAllManage(riskManageTaskAllManage);
				TSUser checkUser = systemService.get(TSUser.class,bean.getCheckMan());
				riskManageTaskAllEntity.setCreateBy(checkUser.getUserName());
				riskManageTaskAllEntity.setCreateName(checkUser.getRealName());
				systemService.save(riskManageTaskAllEntity);
				bean.setTaskAllId(riskManageTaskAllEntity.getId());
				systemService.saveOrUpdate(bean);
				StringBuffer addressList = new StringBuffer();
				String addressIds = bean.getCheckAddress();
				for(String addressId:addressIds.split(",")){
					addressList.append("'"+addressId+"',");
				}
				addressList.append("''");
				//风险清单添加风险
				String riskIdSql = "SELECT id from t_b_risk_identification WHERE is_del='0' and status = '3' and address_id in ("+addressList.toString()+") and risk_type in (SELECT risk_type from t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and is_del = '0')" +
						" AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ) AND id NOT IN ( SELECT risk_id FROM t_b_risk_manage ) ";
				List<String> list = systemService.findListbySql(riskIdSql);
				if(list!=null&&list.size()>0) {
					for (String id : list) {
						RiskManageEntity riskManageEntity = null;
						riskManageEntity = new RiskManageEntity();
						RiskIdentificationEntity t = this.systemService.getEntity(RiskIdentificationEntity.class, id);
						riskManageEntity.setRisk(t);
						this.systemService.save(riskManageEntity);
						systemService.addLog("风险清单添加风险\"" + riskManageEntity.getId() + "\"成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
					}
				}
				riskIdSql = "SELECT id from t_b_risk_identification WHERE is_del='0' and status = '3' and address_id in ("+addressList.toString()+") and risk_type in (SELECT risk_type from t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and is_del = '0')" +
						" AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ) ";
				list = systemService.findListbySql(riskIdSql);
				if(list!=null&&list.size()>0) {
					//自动生成管控清单
					String taskId = UUIDGenerator.generate();
					String sql = "SELECT id FROM t_b_risk_manage WHERE risk_id IN ( SELECT id FROM t_b_risk_identification WHERE is_del = '0' AND STATUS = '3' and ( NOW() < exp_date or exp_date is null  ) and NOW() > identifi_date  AND address_id in ("+addressList.toString()+") AND risk_type IN ( SELECT risk_type FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' ) AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ))";
					List<String> ids = systemService.findListbySql(sql);
					if(ids!=null&&ids.size()>0){
						RiskManageTaskEntity riskManageTaskEntity = null;
						for(String id : ids){
							RiskManageEntity t = this.systemService.getEntity(RiskManageEntity.class,id);
							String riskSql = "SELECT id FROM t_b_risk_identification WHERE id = (SELECT risk_id FROM t_b_risk_manage WHERE id ='"+id+"' )";
							List<String> tempList = systemService.findListbySql(riskSql);
							if(!tempList.isEmpty() && tempList.size()>0){
								riskManageTaskEntity = new RiskManageTaskEntity();
								riskManageTaskEntity.setTaskId(taskId);
								riskManageTaskEntity.setRiskManage(t);
								riskManageTaskEntity.setRisk(t.getRisk());
								riskManageTaskEntity.setCreateBy(checkUser.getUserName());
								riskManageTaskEntity.setCreateName(checkUser.getRealName());
								riskManageTaskEntity.setManageType(riskManageTaskAllEntity.getManageType());
								riskManageTaskEntity.setTaskAllId(riskManageTaskAllEntity.getId());
								List<RiskFactortsRel> riskFactorRelList = t.getRisk().getRelList();
								if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
									riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
								} else {
									riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
								}
								this.systemService.save(riskManageTaskEntity);

								//自动生成对危害因素的管控
								if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
									List<String> majorList =new ArrayList<>();
									String majorSql =  "SELECT major FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and major != '' and major is not null";
									List<String> major = systemService.findListbySql(majorSql);
									if(major!=null&&major.size()>0){
										String temp = major.get(0);
										String[] values = temp.split(",");
										majorList = Arrays.asList(values);
									}
									for(int i=0;i<riskFactorRelList.size();i++){
										String level = riskFactorRelList.get(i).getHfLevel();
										if(StringUtil.isNotEmpty(level)){
											if(level.equals("1")||level.equals("2")){
												if(majorList!=null){
													if(StringUtil.isNotEmpty(riskFactorRelList.get(i).getHazardFactorsEntity().getMajor())&&majorList.contains(riskFactorRelList.get(i).getHazardFactorsEntity().getMajor())){
														RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
														riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
														riskManageHazardFactorEntity.setTaskId(taskId);
														riskManageHazardFactorEntity.setRisk(t.getRisk());
														riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
														riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
														riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
														riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
														riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
														this.systemService.save(riskManageHazardFactorEntity);
													}
												}else{
													RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
													riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
													riskManageHazardFactorEntity.setTaskId(taskId);
													riskManageHazardFactorEntity.setRisk(t.getRisk());
													riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
													riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
													riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
													riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
													riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
													this.systemService.save(riskManageHazardFactorEntity);
												}
											}
										}
									}
									String manageNumSql = "SELECT manage_num FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and manage_num != '' and manage_num is not null";
									List<String> manageNumList = systemService.findListbySql(manageNumSql);
									int manageNum = 10;
									if(manageNumList!=null&&manageNumList.size()>0){
										manageNum = Integer.parseInt(manageNumList.get(0));
									}
									String hazardFactorSql ="";
									if(major!=null&&major.size()>0){
										hazardFactorSql = "SELECT hazard_factors_id from t_b_risk_factors_rel  LEFT JOIN t_b_hazard_factors hf on hf.id = hazard_factors_id WHERE  FIND_IN_SET(hf.major,'"+major.get(0)+"')  and hfLevel !='1' and hfLevel != '2' and risk_identification_id = '"+t.getRisk().getId()+"' ORDER BY RAND() LIMIT "+manageNum+"";
									}else{
										hazardFactorSql = "SELECT hazard_factors_id from t_b_risk_factors_rel  LEFT JOIN t_b_hazard_factors hf on hf.id = hazard_factors_id WHERE   hfLevel !='1' and hfLevel != '2' and risk_identification_id = '"+t.getRisk().getId()+"' ORDER BY RAND() LIMIT "+manageNum+"";
									}

									List<String> hazardFactorList = systemService.findListbySql(hazardFactorSql);
									for(String hazardFactorId : hazardFactorList){
										RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
										riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
										riskManageHazardFactorEntity.setTaskId(taskId);
										riskManageHazardFactorEntity.setRisk(t.getRisk());
										riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
										riskManageHazardFactorEntity.setHazardFactor(systemService.get(HazardFactorsEntity.class,hazardFactorId));
										riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
										riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
										riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
										this.systemService.save(riskManageHazardFactorEntity);
									}
								}
								//systemService.addLog("风险管控任务\""+ riskManageTaskEntity.getId()+"\"添加风险成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
								String riskManageHazardFactorSql = "SELECT id from t_b_risk_manage_hazard_factor WHERE risk_manage_task_id = '"+riskManageTaskEntity.getId()+"'";
								List<String> riskManageHazardFactorEntityList = systemService.findListbySql(riskManageHazardFactorSql);
								if(null==riskManageHazardFactorEntityList || riskManageHazardFactorEntityList.size()==0){
									riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
									this.systemService.delete(riskManageTaskEntity);
								}
							}
						}
					}
				}
				/*String sql = "SELECT id FROM t_b_risk_manage_task WHERE handle_status = '0' AND task_all_id = '"+riskManageTaskAllEntity.getId()+"'";
				List<String> temp = this.systemService.findListbySql(sql);
				if(temp==null||temp.size()==0){
					riskManageTaskAllEntity.setStatus("1");
					this.systemService.saveOrUpdate(riskManageTaskAllEntity);
				}*/
			}

			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);


			// 定时任务
			String job_name = riskManageTaskAllManage.getId();

			Date endDate = riskManageTaskAllManage.getEndDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String endStr = sdf.format(endDate);
			endStr = endStr + " 23:59:59";
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				endDate = sdf.parse(endStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			int year = calendar.get(calendar.YEAR);
			int month = calendar.get(calendar.MONTH) + 1;
			int day = calendar.get(calendar.DATE);
			int hour = calendar.get(calendar.HOUR_OF_DAY);
			int minute = calendar.get(calendar.MINUTE);
			int second = calendar.get(calendar.SECOND);

			StringBuffer sb = new StringBuffer();
			sb.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month).append(" ").append("? ").append(year);
			try{
				qrtzManagerServiceI.addJob(scheduler,job_name, job_name, QuartzJobTask.class, sb.toString());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		j.setMsg(message);
		return j;
	}

	//星村个性化
	@RequestMapping(params = "saveXC")
	@ResponseBody
	public AjaxJson saveXC(RiskManageTaskAllManageEntity riskManageTaskAllManage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(riskManageTaskAllManage.getId())) {
			//追加人员
			message = "任务管理更新成功";
			RiskManageTaskAllManageEntity r = riskManageTaskAllManageService.get(RiskManageTaskAllManageEntity.class, riskManageTaskAllManage.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(riskManageTaskAllManage, r);
				riskManageTaskAllManageService.saveOrUpdate(r);
				message = "人员追加成功";
				TSUser organizerMan = systemService.get(TSUser.class,r.getOrganizerMan());
				String createTime = DateUtils.formatDate(r.getCreateDate(),"yyyy-MM-dd HH:mm");
				String content = "【双防平台】通知：尊敬的双防用户，您好！"+organizerMan.getRealName() +"组织了管控任务：" +createTime+
						" 管控名称："+r.getManageName()+"，管控类型：" +DicUtil.getTypeNameByCode("taskManageType",r.getManageType())+ ",请进行管控。";

				String checkData = request.getParameter("checkData");
				//解析JSON数据，然后将其保存到 t_b_risk_manage_task_all_manage_check_rel 表中
				if(StringUtils.isNotBlank(checkData)){
					JSONArray jsonArray = JSONArray.fromObject(checkData);
					List<RiskManageTaskAllManageCheckRelEntity> riskManageTaskAllManageCheckRelEntityList = new ArrayList<RiskManageTaskAllManageCheckRelEntity>();
					List<RiskManageTaskAllManageTempEntity> riskManageTaskAllManageTempEntityList = new ArrayList<RiskManageTaskAllManageTempEntity>();
					try {
						if (jsonArray.size()>0) {
							Map<String,String> checkManMap = new HashMap<>();
							for (int i=0; i<jsonArray.size(); i++) {
								RiskManageTaskAllManageTempEntity riskManageTaskAllManageTempEntity = new RiskManageTaskAllManageTempEntity();
								riskManageTaskAllManageTempEntity.setCheckAddress(jsonArray.getJSONObject(i).getString("address"));
								riskManageTaskAllManageTempEntity.setCheckMan(jsonArray.getJSONObject(i).getString("checkMan"));
								riskManageTaskAllManageTempEntity.setTaskAllManageId(r.getId());
								riskManageTaskAllManageTempEntityList.add(riskManageTaskAllManageTempEntity);
								for(String address : jsonArray.getJSONObject(i).getString("address").split(",")) {
									for(String checKMan : jsonArray.getJSONObject(i).getString("checkMan").split(",")) {
										String isExist = checkManMap.get(checKMan);
										if(StringUtil.isEmpty(isExist)){
											checkManMap.put(checKMan,"1");
											RiskManageTaskAllManageCheckRelEntity riskManageTaskAllManageCheckRelEntity = new RiskManageTaskAllManageCheckRelEntity();
											riskManageTaskAllManageCheckRelEntity.setCheckAddress(address);
											riskManageTaskAllManageCheckRelEntity.setCheckMan(checKMan);
											riskManageTaskAllManageCheckRelEntity.setTaskAllManageId(r.getId());
											riskManageTaskAllManageCheckRelEntityList.add(riskManageTaskAllManageCheckRelEntity);
											List<String> mobilePhone=systemService.findListbySql("select mobilePhone from t_s_user  where id = '"+checKMan+"'");
											if(mobilePhone != null && mobilePhone.size()>0 && StringUtils.isNotBlank(mobilePhone.get(0))){
												SMSSenderUtil.sendSMS(content,mobilePhone.get(0), com.sdzk.buss.web.common.Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
											}
											List<String> weChatPhones=systemService.findListbySql("select weChatPhone from t_s_user  where id = '"+checKMan+"'");
											if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
												WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
											}
										}else{
											for(RiskManageTaskAllManageCheckRelEntity bean:riskManageTaskAllManageCheckRelEntityList){
												if(StringUtil.isNotEmpty(checKMan)&&StringUtil.isNotEmpty(bean.getCheckMan())&&checKMan.equals(bean.getCheckMan())){
													List<String> addressList =new ArrayList<>();
													String addressIds = bean.getCheckAddress();
													if(StringUtil.isNotEmpty(addressIds)){
														String[] values = addressIds.split(",");
														addressList = Arrays.asList(values);
													}
													if(!addressList.contains(address)){
														bean.setCheckAddress(addressIds+","+address);
													}
												}
											}
										}
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					systemService.batchSave(riskManageTaskAllManageCheckRelEntityList);
					systemService.batchSave(riskManageTaskAllManageTempEntityList);
					//自动生成任务
					for(RiskManageTaskAllManageCheckRelEntity bean:riskManageTaskAllManageCheckRelEntityList){
						RiskManageTaskAllEntity riskManageTaskAllEntity = new RiskManageTaskAllEntity();
						riskManageTaskAllEntity.setStatus("0");
						riskManageTaskAllEntity.setManageType(r.getManageType());
						riskManageTaskAllEntity.setManageTime(r.getManageTime());
						riskManageTaskAllEntity.setTaskAllManage(r);
						TSUser checkUser = systemService.get(TSUser.class,bean.getCheckMan());
						riskManageTaskAllEntity.setCreateBy(checkUser.getUserName());
						riskManageTaskAllEntity.setCreateName(checkUser.getRealName());
						systemService.save(riskManageTaskAllEntity);
						bean.setTaskAllId(riskManageTaskAllEntity.getId());
						systemService.saveOrUpdate(bean);
						StringBuffer addressList = new StringBuffer();
						String addressIds = bean.getCheckAddress();
						for(String addressId:addressIds.split(",")){
							addressList.append("'"+addressId+"',");
						}
						addressList.append("''");
						//风险清单添加风险
						String riskIdSql = "SELECT id from t_b_risk_identification WHERE is_del='0' and status = '3' and address_id in ("+addressList.toString()+") and risk_type in (SELECT risk_type from t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and is_del = '0')" +
								" AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ) AND id NOT IN ( SELECT risk_id FROM t_b_risk_manage ) ";
						List<String> list = systemService.findListbySql(riskIdSql);
						if(list!=null&&list.size()>0) {
							for (String id : list) {
								RiskManageEntity riskManageEntity = null;
								riskManageEntity = new RiskManageEntity();
								RiskIdentificationEntity t = this.systemService.getEntity(RiskIdentificationEntity.class, id);
								riskManageEntity.setRisk(t);
								this.systemService.save(riskManageEntity);
								systemService.addLog("风险清单添加风险\"" + riskManageEntity.getId() + "\"成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
							}
						}
						riskIdSql = "SELECT id from t_b_risk_identification WHERE is_del='0' and status = '3' and address_id in ("+addressList.toString()+") and risk_type in (SELECT risk_type from t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and is_del = '0')" +
								" AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ) ";
						list = systemService.findListbySql(riskIdSql);
						if(list!=null&&list.size()>0) {
							//自动生成管控清单
							String taskId = UUIDGenerator.generate();
							String sql = "SELECT id FROM t_b_risk_manage WHERE risk_id IN ( SELECT id FROM t_b_risk_identification WHERE is_del = '0' AND STATUS = '3' and ( NOW() < exp_date or exp_date is null  ) and NOW() > identifi_date AND address_id in ("+addressList.toString()+") AND risk_type IN ( SELECT risk_type FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' ) AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ))";
							List<String> ids = systemService.findListbySql(sql);
							if(ids!=null&&ids.size()>0){
								RiskManageTaskEntity riskManageTaskEntity = null;
								for(String id : ids){
									RiskManageEntity t = this.systemService.getEntity(RiskManageEntity.class,id);
									String riskSql = "SELECT id FROM t_b_risk_identification WHERE id = (SELECT risk_id FROM t_b_risk_manage WHERE id ='"+id+"' )";
									List<String> tempList = systemService.findListbySql(riskSql);
									if(!tempList.isEmpty() && tempList.size()>0){
										riskManageTaskEntity = new RiskManageTaskEntity();
										riskManageTaskEntity.setTaskId(taskId);
										riskManageTaskEntity.setRiskManage(t);
										riskManageTaskEntity.setRisk(t.getRisk());
										riskManageTaskEntity.setCreateBy(checkUser.getUserName());
										riskManageTaskEntity.setCreateName(checkUser.getRealName());
										riskManageTaskEntity.setManageType(riskManageTaskAllEntity.getManageType());
										riskManageTaskEntity.setTaskAllId(riskManageTaskAllEntity.getId());
										List<RiskFactortsRel> riskFactorRelList = t.getRisk().getRelList();
										if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
											riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
										} else {
											riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
										}
										this.systemService.save(riskManageTaskEntity);

										//自动生成对危害因素的管控
										if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
											List<String> majorList =new ArrayList<>();
											//星村个性化提前选好
											String major = "";
											String[] majorTemp= request.getParameterValues("major");
											if(StringUtil.isNotEmpty(majorTemp)){
												majorList = Arrays.asList(majorTemp);
												for(String temp:majorList){
													if(StringUtil.isEmpty(major)){
														major = temp;
													}else{
														major = major + ',' + temp;
													}
												}
											}else{
												String majorSql =  "SELECT major FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and major != '' and major is not null";
												List<String> majors = systemService.findListbySql(majorSql);
												if(majors!=null&&majors.size()>0){
													String temp = majors.get(0);
													major = temp;
													String[] values = temp.split(",");
													majorList = Arrays.asList(values);
												}
											}
											for(int i=0;i<riskFactorRelList.size();i++){
												String level = riskFactorRelList.get(i).getHfLevel();
												if(StringUtil.isNotEmpty(level)){
													if(level.equals("1")||level.equals("2")){
														if(majorList!=null){
															if(StringUtil.isNotEmpty(riskFactorRelList.get(i).getHazardFactorsEntity().getMajor())&&majorList.contains(riskFactorRelList.get(i).getHazardFactorsEntity().getMajor())){
																RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
																riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
																riskManageHazardFactorEntity.setTaskId(taskId);
																riskManageHazardFactorEntity.setRisk(t.getRisk());
																riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
																riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
																riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
																riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
																riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
																this.systemService.save(riskManageHazardFactorEntity);
															}
														}else{
															RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
															riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
															riskManageHazardFactorEntity.setTaskId(taskId);
															riskManageHazardFactorEntity.setRisk(t.getRisk());
															riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
															riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
															riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
															riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
															riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
															this.systemService.save(riskManageHazardFactorEntity);
														}
													}
												}
											}
											String manageNumSql = "SELECT manage_num FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and manage_num != '' and manage_num is not null";
											List<String> manageNumList = systemService.findListbySql(manageNumSql);
											int manageNum = 10;
											if(manageNumList!=null&&manageNumList.size()>0){
												manageNum = Integer.parseInt(manageNumList.get(0));
											}
											String hazardFactorSql ="";
											if(StringUtil.isNotEmpty(major)){
												hazardFactorSql = "SELECT hazard_factors_id from t_b_risk_factors_rel  LEFT JOIN t_b_hazard_factors hf on hf.id = hazard_factors_id WHERE   FIND_IN_SET(hf.major,'"+major+"')  and hfLevel !='1' and hfLevel != '2' and risk_identification_id = '"+t.getRisk().getId()+"' ORDER BY RAND() LIMIT "+manageNum+"";
											}else{
												hazardFactorSql = "SELECT hazard_factors_id from t_b_risk_factors_rel  LEFT JOIN t_b_hazard_factors hf on hf.id = hazard_factors_id WHERE    hfLevel !='1' and hfLevel != '2' and risk_identification_id = '"+t.getRisk().getId()+"' ORDER BY RAND() LIMIT "+manageNum+"";
											}

											List<String> hazardFactorList = systemService.findListbySql(hazardFactorSql);
											for(String hazardFactorId : hazardFactorList){
												RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
												riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
												riskManageHazardFactorEntity.setTaskId(taskId);
												riskManageHazardFactorEntity.setRisk(t.getRisk());
												riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
												riskManageHazardFactorEntity.setHazardFactor(systemService.get(HazardFactorsEntity.class,hazardFactorId));
												riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
												riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
												riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
												this.systemService.save(riskManageHazardFactorEntity);
											}
										}
										//systemService.addLog("风险管控任务\""+ riskManageTaskEntity.getId()+"\"添加风险成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
										String riskManageHazardFactorSql = "SELECT id from t_b_risk_manage_hazard_factor WHERE risk_manage_task_id = '"+riskManageTaskEntity.getId()+"'";
										List<String> riskManageHazardFactorEntityList = systemService.findListbySql(riskManageHazardFactorSql);
										if(null==riskManageHazardFactorEntityList || riskManageHazardFactorEntityList.size()==0){
											riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
											this.systemService.delete(riskManageTaskEntity);
										}
									}
								}
							}
						}
				/*String sql = "SELECT id FROM t_b_risk_manage_task WHERE handle_status = '0' AND task_all_id = '"+riskManageTaskAllEntity.getId()+"'";
				List<String> temp = this.systemService.findListbySql(sql);
				if(temp==null||temp.size()==0){
					riskManageTaskAllEntity.setStatus("1");
					this.systemService.saveOrUpdate(riskManageTaskAllEntity);
				}*/
					}
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "任务管理更新失败";
			}
		} else {
			//创建任务
			message = "任务添加成功";
			riskManageTaskAllManageService.save(riskManageTaskAllManage);
			String checkData = request.getParameter("checkData");
			TSUser organizerMan = systemService.get(TSUser.class,riskManageTaskAllManage.getOrganizerMan());
			String createTime = DateUtils.formatDate(riskManageTaskAllManage.getCreateDate(),"yyyy-MM-dd HH:mm");
			String content = "【双防平台】通知：尊敬的双防用户，您好！"+organizerMan.getRealName() +"组织了新的管控任务：" +createTime+
					" 管控名称："+riskManageTaskAllManage.getManageName()+"，管控类型：" +DicUtil.getTypeNameByCode("taskManageType",riskManageTaskAllManage.getManageType())+ ",请进行管控。";

			//解析JSON数据，然后将其保存到 t_b_risk_manage_task_all_manage_check_rel 表中
			if(StringUtils.isNotBlank(checkData)){
				JSONArray jsonArray = JSONArray.fromObject(checkData);
				List<RiskManageTaskAllManageCheckRelEntity> riskManageTaskAllManageCheckRelEntityList = new ArrayList<RiskManageTaskAllManageCheckRelEntity>();
				List<RiskManageTaskAllManageTempEntity> riskManageTaskAllManageTempEntityList = new ArrayList<RiskManageTaskAllManageTempEntity>();
				try {
					if (jsonArray.size()>0) {
						Map<String,String> checkManMap = new HashMap<>();
						for (int i=0; i<jsonArray.size(); i++) {
							RiskManageTaskAllManageTempEntity riskManageTaskAllManageTempEntity = new RiskManageTaskAllManageTempEntity();
							riskManageTaskAllManageTempEntity.setCheckAddress(jsonArray.getJSONObject(i).getString("address"));
							riskManageTaskAllManageTempEntity.setCheckMan(jsonArray.getJSONObject(i).getString("checkMan"));
							riskManageTaskAllManageTempEntity.setTaskAllManageId(riskManageTaskAllManage.getId());
							riskManageTaskAllManageTempEntityList.add(riskManageTaskAllManageTempEntity);
							for(String address : jsonArray.getJSONObject(i).getString("address").split(",")) {
								for(String checKMan : jsonArray.getJSONObject(i).getString("checkMan").split(",")) {
									String isExist = checkManMap.get(checKMan);
									if(StringUtil.isEmpty(isExist)){
										checkManMap.put(checKMan,"1");
										RiskManageTaskAllManageCheckRelEntity riskManageTaskAllManageCheckRelEntity = new RiskManageTaskAllManageCheckRelEntity();
										riskManageTaskAllManageCheckRelEntity.setCheckAddress(address);
										riskManageTaskAllManageCheckRelEntity.setCheckMan(checKMan);
										riskManageTaskAllManageCheckRelEntity.setTaskAllManageId(riskManageTaskAllManage.getId());
										riskManageTaskAllManageCheckRelEntityList.add(riskManageTaskAllManageCheckRelEntity);
										List<String> mobilePhone=systemService.findListbySql("select mobilePhone from t_s_user  where id = '"+checKMan+"'");
										if(mobilePhone != null && mobilePhone.size()>0 && StringUtils.isNotBlank(mobilePhone.get(0))){
											SMSSenderUtil.sendSMS(content,mobilePhone.get(0), com.sdzk.buss.web.common.Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
										}
										List<String> weChatPhones=systemService.findListbySql("select weChatPhone from t_s_user  where id = '"+checKMan+"'");
										if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
											WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
										}
									}else{
										for(RiskManageTaskAllManageCheckRelEntity bean:riskManageTaskAllManageCheckRelEntityList){
											if(StringUtil.isNotEmpty(checKMan)&&StringUtil.isNotEmpty(bean.getCheckMan())&&checKMan.equals(bean.getCheckMan())){
												List<String> addressList =new ArrayList<>();
												String addressIds = bean.getCheckAddress();
												if(StringUtil.isNotEmpty(addressIds)){
													String[] values = addressIds.split(",");
													addressList = Arrays.asList(values);
												}
												if(!addressList.contains(address)){
													bean.setCheckAddress(addressIds+","+address);
												}
											}
										}
									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				systemService.batchSave(riskManageTaskAllManageCheckRelEntityList);
				systemService.batchSave(riskManageTaskAllManageTempEntityList);
			}
			//自动生成任务
			CriteriaQuery cq = new CriteriaQuery(RiskManageTaskAllManageCheckRelEntity.class);
			cq.eq("taskAllManageId",riskManageTaskAllManage.getId());
			cq.add();
			List<RiskManageTaskAllManageCheckRelEntity> riskManageTaskAllManageCheckRelEntityList =systemService.getListByCriteriaQuery(cq,false);
			for(RiskManageTaskAllManageCheckRelEntity bean:riskManageTaskAllManageCheckRelEntityList){
				RiskManageTaskAllEntity riskManageTaskAllEntity = new RiskManageTaskAllEntity();
				riskManageTaskAllEntity.setStatus("0");
				riskManageTaskAllEntity.setManageType(riskManageTaskAllManage.getManageType());
				riskManageTaskAllEntity.setManageTime(riskManageTaskAllManage.getManageTime());
				riskManageTaskAllEntity.setTaskAllManage(riskManageTaskAllManage);
				TSUser checkUser = systemService.get(TSUser.class,bean.getCheckMan());
				riskManageTaskAllEntity.setCreateBy(checkUser.getUserName());
				riskManageTaskAllEntity.setCreateName(checkUser.getRealName());
				systemService.save(riskManageTaskAllEntity);
				bean.setTaskAllId(riskManageTaskAllEntity.getId());
				systemService.saveOrUpdate(bean);
				StringBuffer addressList = new StringBuffer();
				String addressIds = bean.getCheckAddress();
				for(String addressId:addressIds.split(",")){
					addressList.append("'"+addressId+"',");
				}
				addressList.append("''");
				//风险清单添加风险
				String riskIdSql = "SELECT id from t_b_risk_identification WHERE is_del='0' and status = '3' and address_id in ("+addressList.toString()+") and risk_type in (SELECT risk_type from t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and is_del = '0')" +
						" AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ) AND id NOT IN ( SELECT risk_id FROM t_b_risk_manage ) ";
				List<String> list = systemService.findListbySql(riskIdSql);
				if(list!=null&&list.size()>0) {
					for (String id : list) {
						RiskManageEntity riskManageEntity = null;
						riskManageEntity = new RiskManageEntity();
						RiskIdentificationEntity t = this.systemService.getEntity(RiskIdentificationEntity.class, id);
						riskManageEntity.setRisk(t);
						this.systemService.save(riskManageEntity);
						systemService.addLog("风险清单添加风险\"" + riskManageEntity.getId() + "\"成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
					}
				}
				riskIdSql = "SELECT id from t_b_risk_identification WHERE is_del='0' and status = '3' and address_id in ("+addressList.toString()+") and risk_type in (SELECT risk_type from t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and is_del = '0')" +
						" AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ) ";
				list = systemService.findListbySql(riskIdSql);
				if(list!=null&&list.size()>0) {
					//自动生成管控清单
					String taskId = UUIDGenerator.generate();
					String sql = "SELECT id FROM t_b_risk_manage WHERE risk_id IN ( SELECT id FROM t_b_risk_identification WHERE is_del = '0' AND STATUS = '3' and ( NOW() < exp_date or exp_date is null  ) and NOW() > identifi_date  AND address_id in ("+addressList.toString()+") AND risk_type IN ( SELECT risk_type FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' ) AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' AND is_del = '0' GROUP BY manage_level ))";
					List<String> ids = systemService.findListbySql(sql);
					if(ids!=null&&ids.size()>0){
						RiskManageTaskEntity riskManageTaskEntity = null;
						for(String id : ids){
							RiskManageEntity t = this.systemService.getEntity(RiskManageEntity.class,id);
							String riskSql = "SELECT id FROM t_b_risk_identification WHERE id = (SELECT risk_id FROM t_b_risk_manage WHERE id ='"+id+"' )";
							List<String> tempList = systemService.findListbySql(riskSql);
							if(!tempList.isEmpty() && tempList.size()>0){
								riskManageTaskEntity = new RiskManageTaskEntity();
								riskManageTaskEntity.setTaskId(taskId);
								riskManageTaskEntity.setRiskManage(t);
								riskManageTaskEntity.setRisk(t.getRisk());
								riskManageTaskEntity.setCreateBy(checkUser.getUserName());
								riskManageTaskEntity.setCreateName(checkUser.getRealName());
								riskManageTaskEntity.setManageType(riskManageTaskAllEntity.getManageType());
								riskManageTaskEntity.setTaskAllId(riskManageTaskAllEntity.getId());
								List<RiskFactortsRel> riskFactorRelList = t.getRisk().getRelList();
								if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
									riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
								} else {
									riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
								}
								this.systemService.save(riskManageTaskEntity);

								//自动生成对危害因素的管控
								if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
									List<String> majorList =new ArrayList<>();
									//星村个性化提前选好
									String major = "";
									String[] majorTemp= request.getParameterValues("major");
									if(StringUtil.isNotEmpty(majorTemp)){
										majorList = Arrays.asList(majorTemp);
										for(String temp:majorList){
											if(StringUtil.isEmpty(major)){
												major = temp;
											}else{
												major = major + ',' + temp;
											}
										}
									}else{
										String majorSql =  "SELECT major FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and major != '' and major is not null";
										List<String> majors = systemService.findListbySql(majorSql);
										if(majors!=null&&majors.size()>0){
											String temp = majors.get(0);
											major = temp;
											String[] values = temp.split(",");
											majorList = Arrays.asList(values);
										}
									}
									for(int i=0;i<riskFactorRelList.size();i++){
										String level = riskFactorRelList.get(i).getHfLevel();
										if(StringUtil.isNotEmpty(level)){
											if(level.equals("1")||level.equals("2")){
												if(majorList!=null){
													if(StringUtil.isNotEmpty(riskFactorRelList.get(i).getHazardFactorsEntity().getMajor())&&majorList.contains(riskFactorRelList.get(i).getHazardFactorsEntity().getMajor())){
														RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
														riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
														riskManageHazardFactorEntity.setTaskId(taskId);
														riskManageHazardFactorEntity.setRisk(t.getRisk());
														riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
														riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
														riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
														riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
														riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
														this.systemService.save(riskManageHazardFactorEntity);
													}
												}else{
													RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
													riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
													riskManageHazardFactorEntity.setTaskId(taskId);
													riskManageHazardFactorEntity.setRisk(t.getRisk());
													riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
													riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
													riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
													riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
													riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
													this.systemService.save(riskManageHazardFactorEntity);
												}
											}
										}
									}
									String manageNumSql = "SELECT manage_num FROM t_b_my_manage WHERE my_user_id = '"+checkUser.getId()+"' and manage_num != '' and manage_num is not null";
									List<String> manageNumList = systemService.findListbySql(manageNumSql);
									int manageNum = 10;
									if(manageNumList!=null&&manageNumList.size()>0){
										manageNum = Integer.parseInt(manageNumList.get(0));
									}
									String hazardFactorSql ="";
									if(StringUtil.isNotEmpty(major)){
										hazardFactorSql = "SELECT hazard_factors_id from t_b_risk_factors_rel  LEFT JOIN t_b_hazard_factors hf on hf.id = hazard_factors_id WHERE  FIND_IN_SET(hf.major,'"+major+"')  and hfLevel !='1' and hfLevel != '2' and risk_identification_id = '"+t.getRisk().getId()+"' ORDER BY RAND() LIMIT "+manageNum+"";
									}else{
										hazardFactorSql = "SELECT hazard_factors_id from t_b_risk_factors_rel  LEFT JOIN t_b_hazard_factors hf on hf.id = hazard_factors_id WHERE   hfLevel !='1' and hfLevel != '2' and risk_identification_id = '"+t.getRisk().getId()+"' ORDER BY RAND() LIMIT "+manageNum+"";
									}

									List<String> hazardFactorList = systemService.findListbySql(hazardFactorSql);
									for(String hazardFactorId : hazardFactorList){
										RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
										riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
										riskManageHazardFactorEntity.setTaskId(taskId);
										riskManageHazardFactorEntity.setRisk(t.getRisk());
										riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
										riskManageHazardFactorEntity.setHazardFactor(systemService.get(HazardFactorsEntity.class,hazardFactorId));
										riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
										riskManageHazardFactorEntity.setCreateBy(checkUser.getUserName());
										riskManageHazardFactorEntity.setCreateName(checkUser.getRealName());
										this.systemService.save(riskManageHazardFactorEntity);
									}
								}
								//systemService.addLog("风险管控任务\""+ riskManageTaskEntity.getId()+"\"添加风险成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
								String riskManageHazardFactorSql = "SELECT id from t_b_risk_manage_hazard_factor WHERE risk_manage_task_id = '"+riskManageTaskEntity.getId()+"'";
								List<String> riskManageHazardFactorEntityList = systemService.findListbySql(riskManageHazardFactorSql);
								if(null==riskManageHazardFactorEntityList || riskManageHazardFactorEntityList.size()==0){
									riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
									this.systemService.delete(riskManageTaskEntity);
								}
							}
						}
					}
				}
				/*String sql = "SELECT id FROM t_b_risk_manage_task WHERE handle_status = '0' AND task_all_id = '"+riskManageTaskAllEntity.getId()+"'";
				List<String> temp = this.systemService.findListbySql(sql);
				if(temp==null||temp.size()==0){
					riskManageTaskAllEntity.setStatus("1");
					this.systemService.saveOrUpdate(riskManageTaskAllEntity);
				}*/
			}

			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);


			// 定时任务
			String job_name = riskManageTaskAllManage.getId();

			Date endDate = riskManageTaskAllManage.getEndDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String endStr = sdf.format(endDate);
			endStr = endStr + " 23:59:59";
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				endDate = sdf.parse(endStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			int year = calendar.get(calendar.YEAR);
			int month = calendar.get(calendar.MONTH) + 1;
			int day = calendar.get(calendar.DATE);
			int hour = calendar.get(calendar.HOUR_OF_DAY);
			int minute = calendar.get(calendar.MINUTE);
			int second = calendar.get(calendar.SECOND);

			StringBuffer sb = new StringBuffer();
			sb.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month).append(" ").append("? ").append(year);
			try{
				qrtzManagerServiceI.addJob(scheduler,job_name, job_name, QuartzJobTask.class, sb.toString());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 隐患排查-任务管理-录入按钮、追加人员操作
	 *
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(RiskManageTaskAllManageEntity riskManageTaskAllManage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(riskManageTaskAllManage.getId())) {
			riskManageTaskAllManage = riskManageTaskAllManageService.getEntity(RiskManageTaskAllManageEntity.class, riskManageTaskAllManage.getId());
			req.setAttribute("riskManageTaskAllManagePage", riskManageTaskAllManage);
			req.setAttribute("organizerMan", riskManageTaskAllManage.getOrganizerMan());
		}else{
			TSUser user = ResourceUtil.getSessionUserName();
			req.setAttribute("organizerMan", user.getId());
		}
		String hegang = ResourceUtil.getConfigByName("hegang");
		req.setAttribute("hegang",hegang);
		String addCheckMan = req.getParameter("addCheckMan");
		//星村个性化
		String xingcun = ResourceUtil.getConfigByName("xingcun");
		if(xingcun.equals("true")){
			String sql = "select typecode,typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='major')";
			List<Map<String, Object>> maplist = systemService.findForJdbc(sql);
			req.setAttribute("majorList",maplist);
			if(StringUtil.isNotEmpty(addCheckMan)){
				return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageTaskAllManageAddCheckManXC");
			}else{
				return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageTaskAllManageXC");
			}
		}
		if(StringUtil.isNotEmpty(addCheckMan)){
			return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageTaskAllManageAddCheckMan");
		}else{
			return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageTaskAllManage");
		}

	}


	/**
	 * 隐患排查-》任务记录-》
	 * 上级派发任务查看概况页面跳转
	 */
	@RequestMapping(params = "goSurvey")
	public ModelAndView goSurvey(RiskManageTaskAllManageEntity riskManageTaskAllManage, HttpServletRequest req) {
		String id = req.getParameter("riskManageTaskAllManageId");
		if (StringUtil.isNotEmpty(id)) {
			riskManageTaskAllManage = riskManageTaskAllManageService.getEntity(RiskManageTaskAllManageEntity.class,id);
			req.setAttribute("riskManageTaskAllManagePage", riskManageTaskAllManage);

			String taskManageType = DicUtil.getTypeNameByCode("taskManageType",riskManageTaskAllManage.getManageType());
			req.setAttribute("taskManageType", taskManageType);
			TSUser tsUser = systemService.get(TSUser.class,riskManageTaskAllManage.getOrganizerMan());
			if(tsUser!=null){
				req.setAttribute("organizerMan", tsUser.getRealName());
			}
		}

		String sql = "SELECT check_address address,check_man checkMan from t_b_risk_manage_task_all_manage_temp  WHERE task_all_manage_id = '"+id+"'";
		List<Map<String, Object>> resultList = systemService.findForJdbc(sql);
		if(resultList!=null&&resultList.size()>0){
			for (Map<String,Object> map:resultList){
				String address = String.valueOf(map.get("address"));
				String addressName = "";
				if(StringUtil.isNotEmpty(address)){
					for(String addressId:address.split(",")){
						TBAddressInfoEntity tbAddressInfoEntity = systemService.get(TBAddressInfoEntity.class,addressId);
						if(tbAddressInfoEntity!=null){
							addressName = addressName + tbAddressInfoEntity.getAddress() + ",";
						}
					}
					if(StringUtil.isNotEmpty(addressName)){
						addressName = addressName.substring(0,addressName.length()-1);
					}
				}
				map.put("address",addressName);
				String checkMan = String.valueOf(map.get("checkMan"));
				String checkManName = "";
				if(StringUtil.isNotEmpty(checkMan)){
					for(String userId:checkMan.split(",")){
						TSUser tsUser = systemService.get(TSUser.class,userId);
						if(tsUser!=null){
							checkManName = checkManName + tsUser.getRealName() + ",";
						}
					}
					if(StringUtil.isNotEmpty(checkManName)){
						checkManName = checkManName.substring(0,checkManName.length()-1);
					}
				}
				map.put("realname",checkManName);
			}
			req.setAttribute("resultList",resultList);
		}else{
			sql = "SELECT cr.check_address address,u.realname from t_b_risk_manage_task_all_manage_check_rel cr  LEFT JOIN t_s_base_user u on u.id = cr.check_man WHERE cr.task_all_manage_id = '"+id+"'";

			resultList = systemService.findForJdbc(sql);
			for (Map<String,Object> map:resultList){
				String address = String.valueOf(map.get("address"));
				String addressName = "";
				if(StringUtil.isNotEmpty(address)){
					for(String addressId:address.split(",")){
						TBAddressInfoEntity tbAddressInfoEntity = systemService.get(TBAddressInfoEntity.class,addressId);
						if(tbAddressInfoEntity!=null){
							addressName = addressName + tbAddressInfoEntity.getAddress() + ",";
						}
					}
					if(StringUtil.isNotEmpty(addressName)){
						addressName = addressName.substring(0,addressName.length()-1);
					}
				}
				map.put("address",addressName);
			}
			req.setAttribute("resultList",resultList);
		}


		String riskCount = req.getParameter("riskCount");
		req.setAttribute("riskCount",riskCount);
		String hdCount = req.getParameter("hdCount");
		req.setAttribute("hdCount",hdCount);
		String implCount = req.getParameter("implCount");
		req.setAttribute("implCount",implCount);
		return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageTaskAllManageSurvey");
	}

	/**
	 * 隐患排查-》任务记录-》
	 * 个人任务查看概况页面跳转
	 */
	@RequestMapping(params = "goSurveyGR")
	public ModelAndView goSurveyGR( HttpServletRequest req) {
		String id = req.getParameter("riskManageTaskAllId");
		if (StringUtil.isNotEmpty(id)) {
			RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,id);
			req.setAttribute("riskManageTaskAllPage", riskManageTaskAllEntity);

			String taskManageType = DicUtil.getTypeNameByCode("taskManageType",riskManageTaskAllEntity.getManageType());
			req.setAttribute("taskManageType", taskManageType);
			String manageShift = DicUtil.getTypeNameByCode("workShift",riskManageTaskAllEntity.getManageShift());
			req.setAttribute("manageShift", manageShift);
			if(StringUtil.isNotEmpty(riskManageTaskAllEntity.getManageType())){
				if(riskManageTaskAllEntity.getManageType().equals("comprehensive")||riskManageTaskAllEntity.getManageType().equals("profession")||riskManageTaskAllEntity.getManageType().equals("team")||riskManageTaskAllEntity.getManageType().equals("group")){
					req.setAttribute("manageTypeTemp","1");
					String sql = "SELECT\n" +
							"\ta.address\n" +
							"FROM\n" +
							"\tt_b_risk_manage_task_all ta\n" +
							"LEFT JOIN t_b_risk_manage_task t ON ta.id = t.task_all_id\n" +
							"LEFT JOIN t_b_risk_identification r on t.risk_id = r.id\n" +
							"LEFT JOIN t_b_address_info a on a.id = r.address_id\n" +
							"WHERE a.id is not null and ta.id = '"+id+"'\n" +
							"GROUP BY a.id \n";
					List<String> addressNameList = systemService.findListbySql(sql);
					String addressName = "";
					if(addressNameList!=null&&addressNameList.size()>0){
						for (String address:addressNameList){
							if(StringUtil.isNotEmpty(addressName)){
								addressName = addressName+","+address;
							}else{
								addressName = address;
							}
						}
					}
					req.setAttribute("addressName",addressName);
				}else if(riskManageTaskAllEntity.getManageType().equals("post")){
					req.setAttribute("manageTypeTemp","2");
					String sql = "SELECT\n" +
							"\ta.post_name\n" +
							"FROM\n" +
							"\tt_b_risk_manage_task_all ta\n" +
							"LEFT JOIN t_b_risk_manage_post_task t ON ta.id = t.post_task_all_id\n" +
							"LEFT JOIN t_b_risk_identification_post r on t.post_risk_id = r.id\n" +
							"LEFT JOIN t_b_post_manage a on a.id = r.post_id\n" +
							"WHERE a.id is not null and ta.id = '"+id+"'\n" +
							"GROUP BY a.id \n";
					List<String> postNameList = systemService.findListbySql(sql);
					String postName = "";
					if(postNameList!=null&&postNameList.size()>0){
						postName = postNameList.get(0);
					}
					req.setAttribute("postName",postName);
				}else{
					req.setAttribute("manageTypeTemp","3");
				}
			}
		}
		String riskCount = req.getParameter("riskCount");
		req.setAttribute("riskCount",riskCount);
		String hdCount = req.getParameter("hdCount");
		req.setAttribute("hdCount",hdCount);
		String implCount = req.getParameter("implCount");
		req.setAttribute("implCount",implCount);
		return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageTaskAllManageSurveyGR");
	}

	//查看进度
	@RequestMapping(params = "viewingProgressList")
	public ModelAndView viewingProgressList(HttpServletRequest request) {
		String riskManageTaskAllManageId = request.getParameter("riskManageTaskAllManageId");
		request.setAttribute("riskManageTaskAllManageId",riskManageTaskAllManageId);
		return new ModelAndView("com/sddb/buss/web/riskmanage/viewingProgressTaskList");
	}


	@RequestMapping(params = "checkManDatagrid")
	public void checkManDatagrid(RiskManageTaskAllManageCheckRelEntity riskManageTaskAllManageCheckRelEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RiskManageTaskAllManageCheckRelEntity.class, dataGrid);
		String riskManageTaskAllManageId = request.getParameter("riskManageTaskAllManageId");
		try {
			if(StringUtil.isNotEmpty(riskManageTaskAllManageId)){
				cq.eq("taskAllManageId",riskManageTaskAllManageId);
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		if (dataGrid != null && dataGrid.getResults() != null) {
			List<RiskManageTaskAllManageCheckRelEntity> entityList = dataGrid.getResults();
			if (null != entityList && entityList.size() > 0) {
				StringBuffer ids = new StringBuffer();
				for (RiskManageTaskAllManageCheckRelEntity entity : entityList ) {
					if (StringUtil.isNotEmpty(ids.toString())) {
						ids.append(",");
					}
					ids.append("'").append(entity.getTaskAllId()).append("'") ;
				}
				if (StringUtil.isEmpty(ids.toString())) {
					ids.append("''");
				}
				String hdCountSql = "SELECT sum(count) count, temp.id id FROM ( SELECT count(0) count, task_all_id id FROM t_b_hidden_danger_exam WHERE task_all_id IS NOT NULL AND task_all_id != '' and  task_all_id in ("+ids.toString()+")  GROUP BY task_all_id UNION ALL SELECT count(0) count, ta.id id FROM t_b_risk_manage_task_all ta LEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id LEFT JOIN t_b_risk_manage_hazard_factor rmhf ON t.id = rmhf.risk_manage_task_id LEFT JOIN t_b_risk_manage_rel_hd rmrh ON rmrh.risk_manage_hazard_factor_id = rmhf.id WHERE rmrh.hd_id IN ( SELECT id FROM t_b_hidden_danger_exam ) and  ta.id  in ("+ids.toString()+")   GROUP BY ta.id ) temp  where  temp.id in ("+ids.toString()+") GROUP BY temp.id";
				List<Map<String, Object>> hdCountList = systemService.findForJdbc(hdCountSql);
				Map<String, String> hdCountMap = new HashMap<>();
				if (hdCountList != null && hdCountList.size() > 0) {
					for (Map<String, Object> obj : hdCountList) {
						hdCountMap.put(obj.get("id").toString(), obj.get("count").toString());
					}
				}
				String riskCountSql = "SELECT\n" +
						"\tcount(0) count,\n" +
						"\tta.id id\n" +
						"FROM\n" +
						"\tt_b_risk_manage_task_all ta\n" +
						"LEFT JOIN t_b_risk_manage_task t ON ta.id = t.task_all_id\n" +
						"LEFT JOIN t_b_risk_identification ri ON ri.risk_manage_task_id = t.id\n" +
						"WHERE\n" +
						"\tri.is_del = '0'\n" +
						"\t and ri.status != '0' and  ta.id in ("+ids.toString()+") \n" +
						"GROUP BY\n" +
						"\tta.id";
				List<Map<String, Object>> riskCountList = systemService.findForJdbc(riskCountSql);
				Map<String, String> riskCountMap = new HashMap<>();
				if (riskCountList != null && riskCountList.size() > 0) {
					for (Map<String, Object> obj : riskCountList) {
						riskCountMap.put(obj.get("id").toString(), obj.get("count").toString());
					}
				}
				String implCountSql = "SELECT count(ta.id) count , ta.id  id from t_b_risk_manage_task_all ta LEFT JOIN t_b_risk_manage_task t on ta.id = t.task_all_id LEFT JOIN t_b_risk_manage_hazard_factor hf on hf.risk_manage_task_id = t.id WHERE hf.impl_detail != '' and hf.impl_detail is not null and  ta.id in ("+ids.toString()+") GROUP BY ta.id";
				List<Map<String, Object>> implCountList = systemService.findForJdbc(implCountSql);
				Map<String, String> implCountMap = new HashMap<>();
				if (implCountList != null && implCountList.size() > 0) {
					for (Map<String, Object> obj : implCountList) {
						implCountMap.put(obj.get("id").toString(), obj.get("count").toString());
					}
				}
				for (int i = 0; i < entityList.size(); i++) {
					RiskManageTaskAllManageCheckRelEntity entity = entityList.get(i);
					RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.get(RiskManageTaskAllEntity.class,entity.getTaskAllId());
					if(riskManageTaskAllEntity!=null){
						entity.setStatus(riskManageTaskAllEntity.getStatus());
						String hdCount = hdCountMap.get(riskManageTaskAllEntity.getId());
						if(StringUtil.isNotEmpty(hdCount)){
							entity.setHdCount(hdCount);
						}else {
							entity.setHdCount("0");
						}
						String riskCount = riskCountMap.get(riskManageTaskAllEntity.getId());
						if(StringUtil.isNotEmpty(riskCount)){
							entity.setRiskCount(riskCount);
						}else {
							entity.setRiskCount("0");
						}
						String implCount = implCountMap.get(riskManageTaskAllEntity.getId());
						if(StringUtil.isNotEmpty(implCount)){
							entity.setImplCount(implCount);
						}else {
							entity.setImplCount("0");
						}
					}else{
						entity.setStatus("-1");
						entity.setHdCount("0");
						entity.setRiskCount("0");
						entity.setImplCount("0");
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(RiskManageEntity riskManage, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(RiskManageTaskEntity.class, dataGrid);
		try {
			cq.add(Restrictions.sqlRestriction("this_.task_all_id in (SELECT task_all_id FROM t_b_risk_manage_task_all_manage_check_rel WHERE task_all_manage_id = '"+id+"')"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cq.add();
		List<RiskManageTaskEntity> riskManageTaskEntityList = this.systemService.getListByCriteriaQuery(cq,false);
		Map<String, String> relMap = new HashMap<>();
		Integer count = 0;
		String sql = "SELECT risk_identification_id,hazard_factors_id,IFNULL(d.departname,'') departname,IFNULL(u.realname,'') realname from t_b_risk_factors_rel rfr LEFT JOIN t_s_depart d on rfr.manage_depart = d.id LEFT JOIN t_s_base_user u on u.id = rfr.manage_user";
		List<Map<String, Object>> list = systemService.findForJdbc(sql);
		if (list !=null && list.size()>0) {
			for (Map<String, Object> obj : list) {
				String riskId = String.valueOf(obj.get("risk_identification_id"));
				String hazardId = String.valueOf(obj.get("hazard_factors_id"));
				String departname = String.valueOf(obj.get("departname"));
				String realname = String.valueOf(obj.get("realname"));
				if(StringUtil.isNotEmpty(departname)&&StringUtil.isNotEmpty(realname)){
					relMap.put(riskId+hazardId, departname+" - "+realname);
				}
			}
		}
		String riskId="";
		String hazardId ="";
		List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityListTemp = new ArrayList<>();
		if (riskManageTaskEntityList != null && riskManageTaskEntityList.size() > 0) {
			for (RiskManageTaskEntity entity : riskManageTaskEntityList) {
				List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityList = entity.getRiskManageHazardFactorEntityList();
				if (riskManageHazardFactorEntityList != null && riskManageHazardFactorEntityList.size() > 0) {
					for (RiskManageHazardFactorEntity riskManageHazardFactorEntity : riskManageHazardFactorEntityList) {
						if (riskManageHazardFactorEntity.getRisk() != null) {
							riskId = riskManageHazardFactorEntity.getRisk().getId();
							riskManageHazardFactorEntity.getRisk().setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type", riskManageHazardFactorEntity.getRisk().getRiskType()));
							riskManageHazardFactorEntity.getRisk().setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level", riskManageHazardFactorEntity.getRisk().getRiskLevel()));
							riskManageHazardFactorEntity.getRisk().setIdentificationTypeTemp(DicUtil.getTypeNameByCode("identifi_from", riskManageHazardFactorEntity.getRisk().getIdentificationType()));
							String manageLevelTemp = DicUtil.getTypeNameByCode("identifi_mange_level", riskManageHazardFactorEntity.getRisk().getManageLevel());
							if(StringUtil.isNotEmpty(manageLevelTemp)){
								riskManageHazardFactorEntity.getRisk().setManageLevelTemp(DicUtil.getTypeNameByCode("identifi_mange_level", riskManageHazardFactorEntity.getRisk().getManageLevel()));
							}else {
								riskManageHazardFactorEntity.getRisk().setManageLevelTemp(riskManageHazardFactorEntity.getRisk().getManageLevel());
							}
						}
						if (riskManageHazardFactorEntity.getHazardFactor() != null) {
							hazardId=riskManageHazardFactorEntity.getHazardFactor().getId();
							riskManageHazardFactorEntity.getHazardFactor().setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level", riskManageHazardFactorEntity.getHazardFactor().getRiskLevel()));
							riskManageHazardFactorEntity.getHazardFactor().setMajorTemp(DicUtil.getTypeNameByCode("major", riskManageHazardFactorEntity.getHazardFactor().getMajor()));
						}
						count++;
						riskManageHazardFactorEntity.setNumTemp(String.valueOf(count));
						String departorrealname = relMap.get(riskId+hazardId);
						if(StringUtil.isNotEmpty(departorrealname)&&departorrealname.contains(" - ")){
							riskManageHazardFactorEntity.setDepartNameTemp(departorrealname.split(" - ")[0]);
							riskManageHazardFactorEntity.setRealNameTemp(departorrealname.split(" - ")[1]);
						}
						riskManageHazardFactorEntityListTemp.add(riskManageHazardFactorEntity);
					}
				}
			}
		}
		TemplateExportParams templateExportParams = new TemplateExportParams();
		templateExportParams.setSheetNum(0);
		templateExportParams.setTemplateUrl("export/template/exportTemp_riskListTaskAll.xlsx");
		String xinan = ResourceUtil.getConfigByName("xinan");
		if(xinan.equals("true")){
			templateExportParams.setTemplateUrl("export/template/exportTemp_riskListTaskAllxinan.xlsx");
		}
		modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
		Map<String,List<RiskManageHazardFactorEntity>> map = new HashMap<String,List<RiskManageHazardFactorEntity>>();
		map.put("list", riskManageHazardFactorEntityListTemp);
		modelMap.put(NormalExcelConstants.FILE_NAME,"风险管控列表");
		modelMap.put(TemplateExcelConstants.MAP_DATA,map);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}

	@RequestMapping(params = "hdList")
	public ModelAndView hdList(HttpServletRequest request) {
		request.setAttribute("riskManageTaskAllId",ResourceUtil.getParameter("riskManageTaskAllId"));
		request.setAttribute("riskManageTaskAllManageId",ResourceUtil.getParameter("riskManageTaskAllManageId"));
		request.setAttribute("riskManageTaskAllGRId",ResourceUtil.getParameter("riskManageTaskAllGRId"));
		return new ModelAndView("com/sdzk/buss/web/hiddendanger/hdList");
	}

}
