package com.sdzk.buss.web.specialreport.controller;

import com.sddb.buss.riskmanage.entity.RiskManageTaskAllManageCheckRelEntity;
import com.sdzk.buss.web.specialreport.entity.TBSpecialReportEntity;
import com.sdzk.buss.web.specialreport.service.TBSpecialReportServiceI;
import com.sdzk.buss.web.yearreport.entity.TBYearReportEntity;
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
 * @Description: 专项辨识报告
 * @author zhangdaihao
 * @date 2019-06-04 08:42:26
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBSpecialReportController")
public class TBSpecialReportController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBSpecialReportController.class);

	@Autowired
	private TBSpecialReportServiceI tBSpecialReportService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 专项辨识报告列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/specialreport/tBSpecialReportList");
	}

	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HttpServletRequest request) {
		String id = request.getParameter("ids");
		request.setAttribute("taskId",id);
		TBSpecialReportEntity tBSpecialReportEntity = systemService.findUniqueByProperty(TBSpecialReportEntity.class,"taskId",id);
		if(StringUtil.isNotEmpty(tBSpecialReportEntity)){
			request.setAttribute("tBSpecialReportPage",tBSpecialReportEntity);
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
		return new ModelAndView("com/sdzk/buss/web/specialreport/tBSpecialReport");
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
	public void datagrid(TBSpecialReportEntity tBSpecialReport, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBSpecialReportEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBSpecialReport, request.getParameterMap());
		this.tBSpecialReportService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除专项辨识报告
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TBSpecialReportEntity tBSpecialReport, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBSpecialReport = systemService.getEntity(TBSpecialReportEntity.class, tBSpecialReport.getId());
		message = "专项辨识报告删除成功";
		tBSpecialReportService.delete(tBSpecialReport);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加专项辨识报告
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TBSpecialReportEntity tBSpecialReport, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String taskId = request.getParameter("taskId");
		if (StringUtil.isNotEmpty(tBSpecialReport.getId())) {
			message = "专项辨识报告更新成功";
			TBSpecialReportEntity t = tBSpecialReportService.get(TBSpecialReportEntity.class, tBSpecialReport.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tBSpecialReport, t);
				tBSpecialReportService.saveOrUpdate(t);
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
				message = "专项辨识报告更新失败";
			}
		} else {
			message = "专项辨识报告添加成功";
			tBSpecialReportService.save(tBSpecialReport);
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
	 * 专项辨识报告列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TBSpecialReportEntity tBSpecialReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBSpecialReport.getId())) {
			tBSpecialReport = tBSpecialReportService.getEntity(TBSpecialReportEntity.class, tBSpecialReport.getId());
			req.setAttribute("tBSpecialReportPage", tBSpecialReport);
		}
		return new ModelAndView("com/sdzk/buss/web/specialreport/tBSpecialReport");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TBSpecialReportEntity> list() {
		List<TBSpecialReportEntity> listTBSpecialReports=tBSpecialReportService.getList(TBSpecialReportEntity.class);
		return listTBSpecialReports;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TBSpecialReportEntity task = tBSpecialReportService.get(TBSpecialReportEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TBSpecialReportEntity tBSpecialReport, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBSpecialReportEntity>> failures = validator.validate(tBSpecialReport);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tBSpecialReportService.save(tBSpecialReport);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tBSpecialReport.getId();
		URI uri = uriBuilder.path("/rest/tBSpecialReportController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TBSpecialReportEntity tBSpecialReport) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBSpecialReportEntity>> failures = validator.validate(tBSpecialReport);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tBSpecialReportService.saveOrUpdate(tBSpecialReport);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tBSpecialReportService.deleteEntityById(TBSpecialReportEntity.class, id);
	}
}