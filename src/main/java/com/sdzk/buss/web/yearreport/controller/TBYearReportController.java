package com.sdzk.buss.web.yearreport.controller;


import com.sddb.buss.riskmanage.entity.RiskManageTaskAllManageCheckRelEntity;
import com.sdzk.buss.web.yearreport.entity.TBYearReportEntity;
import com.sdzk.buss.web.yearreport.service.TBYearReportServiceI;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**   
 * @Title: Controller
 * @Description: 年度辨识报告
 * @author zhangdaihao
 * @date 2019-06-01 16:09:06
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBYearReportController")
public class TBYearReportController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBYearReportController.class);

	@Autowired
	private TBYearReportServiceI tBYearReportService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 年度辨识报告列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/yearreport/tBYearReportList");
	}

	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HttpServletRequest request) {
		String id = request.getParameter("ids");
		request.setAttribute("taskId",id);
		TBYearReportEntity tbYearReportEntity = systemService.findUniqueByProperty(TBYearReportEntity.class,"taskId",id);
		if(StringUtil.isNotEmpty(tbYearReportEntity)){
			request.setAttribute("tBYearReportPage",tbYearReportEntity);
		}
		String sql1 = "select profess_group,charge_leader,lead_depart,duty_depart from t_b_year_report_rel where report_id = '"+id+"'";
		List<Map<String,Object>> mapList = systemService.findForJdbc(sql1);
		if(StringUtil.isNotEmpty(mapList)&&mapList.size()>0){
		    String count = String.valueOf(mapList.size()-1);
		    request.setAttribute("count",count);
		    request.setAttribute("mapList",mapList);
        }else{
            request.setAttribute("count","0");
        }
		return new ModelAndView("com/sdzk/buss/web/yearreport/tBYearReport");
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
	public void datagrid(TBYearReportEntity tBYearReport, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBYearReportEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBYearReport, request.getParameterMap());
		this.tBYearReportService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除年度辨识报告
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TBYearReportEntity tBYearReport, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBYearReport = systemService.getEntity(TBYearReportEntity.class, tBYearReport.getId());
		message = "年度辨识报告删除成功";
		tBYearReportService.delete(tBYearReport);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加年度辨识报告
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TBYearReportEntity tBYearReport, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String taskId = request.getParameter("taskId");
		if (StringUtil.isNotEmpty(tBYearReport.getId())) {
			message = "年度辨识报告更新成功";
			TBYearReportEntity t = tBYearReportService.get(TBYearReportEntity.class, tBYearReport.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tBYearReport, t);
				t.setTaskId(taskId);
				tBYearReportService.saveOrUpdate(t);
				String sql = "delete from t_b_year_report_rel where report_id = '"+taskId+"'";
				systemService.executeSql(sql);
                String checkData = request.getParameter("checkData");
                //解析JSON数据，然后将其保存到 t_b_risk_manage_task_all_manage_check_rel 表中
                if(StringUtils.isNotBlank(checkData)){
                    JSONArray jsonArray = JSONArray.fromObject(checkData);
                    List<RiskManageTaskAllManageCheckRelEntity> riskManageTaskAllManageCheckRelEntityList = new ArrayList<RiskManageTaskAllManageCheckRelEntity>();
                    if (jsonArray.size()>0) {
                        for (int i=0; i<jsonArray.size(); i++) {
							String id = UUIDGenerator.generate();
							String professGroup="";
                            professGroup = jsonArray.getJSONObject(i).getString("professGroup");
                            String chargeLeader="";
                            chargeLeader = jsonArray.getJSONObject(i).getString("chargeLeader");
                            String leadDepart="";
                            leadDepart = jsonArray.getJSONObject(i).getString("leadDepart");
                            String dutyDepart="";
                            dutyDepart = jsonArray.getJSONObject(i).getString("dutyDepart");
                            String sql1 = "insert into t_b_year_report_rel (id,profess_group,charge_leader,lead_depart,duty_depart,report_id) values ('"+id+"','"+professGroup+"','"+chargeLeader+"','"+leadDepart+"','"+dutyDepart+"','"+taskId+"')";
                            systemService.executeSql(sql1);
                        }
                    }
                }
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "年度辨识报告更新失败";
			}
		} else {
			message = "年度辨识报告添加成功";
			tBYearReport.setTaskId(taskId);
			tBYearReportService.save(tBYearReport);
            String sql = "delete from t_b_year_report_rel where report_id = '"+taskId+"'";
            systemService.executeSql(sql);
			String checkData = request.getParameter("checkData");
			//解析JSON数据，然后将其保存到 t_b_risk_manage_task_all_manage_check_rel 表中
			if(StringUtils.isNotBlank(checkData)){
				JSONArray jsonArray = JSONArray.fromObject(checkData);
				List<RiskManageTaskAllManageCheckRelEntity> riskManageTaskAllManageCheckRelEntityList = new ArrayList<RiskManageTaskAllManageCheckRelEntity>();
				if (jsonArray.size()>0) {
					for (int i=0; i<jsonArray.size(); i++) {
						String id = UUIDGenerator.generate();
						String professGroup="";
						professGroup = jsonArray.getJSONObject(i).getString("professGroup");
						String chargeLeader="";
						chargeLeader = jsonArray.getJSONObject(i).getString("chargeLeader");
						String leadDepart="";
						leadDepart = jsonArray.getJSONObject(i).getString("leadDepart");
						String dutyDepart="";
						dutyDepart = jsonArray.getJSONObject(i).getString("dutyDepart");
						String sql1 = "insert into t_b_year_report_rel (id,profess_group,charge_leader,lead_depart,duty_depart,report_id) values ('"+id+"','"+professGroup+"','"+chargeLeader+"','"+leadDepart+"','"+dutyDepart+"','"+taskId+"')";
						systemService.executeSql(sql1);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 年度辨识报告列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TBYearReportEntity tBYearReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBYearReport.getId())) {
			tBYearReport = tBYearReportService.getEntity(TBYearReportEntity.class, tBYearReport.getId());
			req.setAttribute("tBYearReportPage", tBYearReport);
		}
		return new ModelAndView("com/sdzk/buss/web/yearreport/tBYearReport");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TBYearReportEntity> list() {
		List<TBYearReportEntity> listTBYearReports=tBYearReportService.getList(TBYearReportEntity.class);
		return listTBYearReports;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TBYearReportEntity task = tBYearReportService.get(TBYearReportEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TBYearReportEntity tBYearReport, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBYearReportEntity>> failures = validator.validate(tBYearReport);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tBYearReportService.save(tBYearReport);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tBYearReport.getId();
		URI uri = uriBuilder.path("/rest/tBYearReportController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TBYearReportEntity tBYearReport) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBYearReportEntity>> failures = validator.validate(tBYearReport);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tBYearReportService.saveOrUpdate(tBYearReport);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tBYearReportService.deleteEntityById(TBYearReportEntity.class, id);
	}
}
