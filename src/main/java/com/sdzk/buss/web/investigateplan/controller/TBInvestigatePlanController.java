package com.sdzk.buss.web.investigateplan.controller;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.dangersource.entity.TBWorkProcessManageEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanEntity;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanHistEntity;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanRelEntity;
import com.sdzk.buss.web.investigateplan.service.TBInvestigatePlanServiceI;
import com.sdzk.buss.web.investigateplan.service.UploadInvestigationPlan;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**   
 * @Title: Controller  
 * @Description: 隐患排查
 * @author onlineGenerator
 * @date 2017-08-17 11:41:50
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBInvestigatePlanController")
public class TBInvestigatePlanController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBInvestigatePlanController.class);

	@Autowired
	private TBInvestigatePlanServiceI tBInvestigatePlanService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
    private UploadInvestigationPlan uploadInvestigationPlan;


	/**
	 * 隐患排查列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlanList");
	}

	/**
	 * 排查计划受理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "acceptList")
	public ModelAndView acceptList(HttpServletRequest request) {
		String investType = request.getParameter("investType");
		request.setAttribute("investType",investType);
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlanAcceptList");
	}

	/**
	 * 月排查列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "monthList")
	public ModelAndView monthList(HttpServletRequest request) {
		request.setAttribute("investigateType", Constants.INVESTIGATEPLAN_TYPE_MONTH);
		request.setAttribute("title", "月排查列表");
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlanList");
	}
	/**
	 * 旬排查列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "xunList")
	public ModelAndView xunList(HttpServletRequest request) {
		request.setAttribute("investigateType", Constants.INVESTIGATEPLAN_TYPE_XUN);
		request.setAttribute("title", "旬排查列表");
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlanList");
	}
	/**
	 * 周排查列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "weekList")
	public ModelAndView weekList(HttpServletRequest request) {
		request.setAttribute("investigateType", Constants.INVESTIGATEPLAN_TYPE_WEEK);
		request.setAttribute("title", "周排查列表");
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlanList");
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
	public void datagrid(TBInvestigatePlanEntity tBInvestigatePlan,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanEntity.class, dataGrid);
		String type = ResourceUtil.getParameter("investigateType");
		String month_begin = ResourceUtil.getParameter("month_begin");
		String month_end = ResourceUtil.getParameter("month_end");
		String queryHandleStatus = ResourceUtil.getParameter("queryHandleStatus");
		Map<String, String[]> paramMap = new HashMap<>();
		for (String key : request.getParameterMap().keySet()) {
			if (!"month_begin".equals(key) && !"month_end".equals(key)) {
				paramMap.put(key, request.getParameterMap().get(key));
			}
		}
		cq.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBInvestigatePlan, paramMap);
		try{
		//自定义追加查询条件
			if(Constants.INVESTIGATEPLAN_TYPE_MONTH.equals(type) ||
					Constants.INVESTIGATEPLAN_TYPE_XUN.equals(type) ||
					Constants.INVESTIGATEPLAN_TYPE_WEEK.equals(type)){
				cq.eq("investigateType", type);
			} else {
				cq.add(Restrictions.sqlRestriction("1=2"));
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtil.isNotEmpty(month_begin)) {
				cq.ge("startTime", sdf.parse(month_begin+"-01"));
			}
			if (StringUtil.isNotEmpty(month_end)) {
				cq.le("startTime", sdf.parse(month_end+"-01"));
			}
			if ("true".equals(queryHandleStatus)) {
				cq.notIn("status", new String[]{Constants.INVESTIGATEPLAN_STATUS_PENDINGCOMMIT, Constants.INVESTIGATEPLAN_STATUS_BACK});
			} else {
				cq.in("status", new String[]{Constants.INVESTIGATEPLAN_STATUS_PENDINGCOMMIT, Constants.INVESTIGATEPLAN_STATUS_BACK});
			}

		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBInvestigatePlanService.getDataGridReturn(cq, true);
		List<TBInvestigatePlanEntity> list = dataGrid.getResults();
		if (list != null && list.size() > 0) {
			for (TBInvestigatePlanEntity entity : list) {
				initRelObjectInfo(entity);
				if (StringUtil.isNotEmpty(entity.getRiskName())) {
					entity.setRiskName(entity.getRiskName().replace("\r\n", "<br>"));
				}
				if (StringUtils.isNoneBlank(entity.getRiskPointName())) {
					entity.setRiskPointName(entity.getRiskPointName().replace("\r\n", ","));
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 获取关联的风险点和危险源信息
	 * @param entity
     */
	private void initRelObjectInfo(TBInvestigatePlanEntity entity){
		//获取计划月份
		if (entity.getStartTime() != null) {
			entity.setMonth(DateUtils.date2Str(entity.getStartTime(), new SimpleDateFormat("yyyy-MM")));
		}
		//获取关联风险点
		String sql;
		StringBuffer riskPointIds = new StringBuffer();
		StringBuffer riskPointName = new StringBuffer();
		if (Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_LOCATION.equals(entity.getRiskPointType())) {
			sql = "select id, address name from t_b_address_info";
		} else {
			sql = "select id, ACTIVITY_NAME name from t_b_activity_manage";
		}
		List<Map<String, Object>> riskPointList = systemService.findForJdbc(sql+" where id in (select obj_id from t_b_investigate_plan_rel where plan_id='"+entity.getId()+"' and rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT+"' and poit_type='"+entity.getRiskPointType()+"')");
		if (riskPointList != null && riskPointList.size() > 0) {
			for (Map<String, Object> obj : riskPointList) {
				if (StringUtil.isNotEmpty(riskPointIds.toString())) {
					riskPointIds.append(",");
				}
				riskPointIds.append(obj.get("id"));
				if (StringUtil.isNotEmpty(riskPointName.toString())) {
					riskPointName.append("\r\n");
				}
				riskPointName.append(obj.get("name"));
			}
			entity.setRiskPointIds(riskPointIds.toString());
			entity.setRiskPointName(riskPointName.toString());
		}
		//获取关联风险
		StringBuffer riskIds = new StringBuffer();
		StringBuffer riskName = new StringBuffer();
		List<Map<String, Object>> riskList = systemService.findForJdbc("select id, ye_mhazard_desc name from t_b_danger_source where id in (select obj_id from t_b_investigate_plan_rel where plan_id='"+entity.getId()+"' and rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISK+"')");
		if (riskList != null && riskList.size() > 0) {
			for (Map<String, Object> obj : riskList){
				if (StringUtil.isNotEmpty(riskIds.toString())) {
					riskIds.append(",");
				}
				riskIds.append(obj.get("id"));
				if (StringUtil.isNotEmpty(riskName.toString())) {
					riskName.append("\r\n");
				}
				riskName.append(obj.get("name"));
			}
			entity.setRiskIds(riskIds.toString());
			entity.setRiskName(riskName.toString());
		}
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "acceptDatagrid")
	public void acceptDatagrid(TBInvestigatePlanEntity tBInvestigatePlan,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanEntity.class, dataGrid);
		String investType = request.getParameter("investType");
		String status = request.getParameter("status");
		String acceptDeptId = request.getParameter("acceptDept_id");
		String queryMonth = request.getParameter("queryMonth");
		String startTime_begin = request.getParameter("startTime_begin");
		String startTime_end = request.getParameter("startTime_end");
		//查询条件组装器
		try{
			cq.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
			//自定义追加查询条件
			if(StringUtils.isNotBlank(investType)){
				cq.eq("investigateType",investType);
			}
			if(StringUtils.isNotBlank(status)){
				cq.eq("status",status);
			}else{
				cq.eq("status", Constants.INVESTIGATEPLAN_STATUS_PENDINGACCEPT);
			}
			if(StringUtils.isNotBlank(acceptDeptId)){
				cq.eq("acceptDepart",acceptDeptId);
			}else{
				TSUser user = ResourceUtil.getSessionUserName();
				cq.eq("acceptDepart",user.getCurrentDepart().getId());
			}
			if(StringUtils.isNotBlank(queryMonth)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				try {
					Date queryDate = sdf.parse(queryMonth);
					Calendar ca = Calendar.getInstance();
					ca.setTime(queryDate);
					//获取当前月第一天
					ca.add(Calendar.MONTH, 0);
					ca.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					String startDate = sdf.format(ca.getTime());
					//获取当前月最后一天
					ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
					String endDate= sdf.format(ca.getTime());
					cq.eq("startTime", sdf.parse(startDate));
					cq.eq("endTime", sdf.parse(endDate));
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isNotBlank(startTime_begin)){
				cq.ge("startTime",sdf.parse(startTime_begin));
			}
			if(StringUtils.isNotBlank(startTime_end)){
				cq.le("startTime",sdf.parse(startTime_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBInvestigatePlanService.getDataGridReturn(cq, true);
		if(dataGrid != null && dataGrid.getResults() != null){
			List<TBInvestigatePlanEntity> resultList = dataGrid.getResults();
			for(TBInvestigatePlanEntity t:resultList){
				String acceptDept = t.getAcceptDepart();
				String acceptMan = t.getAcceptUser();
				if(StringUtils.isNotBlank(acceptDept)){
					TSDepart dept = systemService.getEntity(TSDepart.class,acceptDept);
					if(dept!= null){
						t.setAcceptDepartName(dept.getDepartname());
					}
				}
				if(StringUtils.isNotBlank(acceptMan)){
					TSUser acceptor = systemService.getEntity(TSUser.class,acceptMan);
					if(acceptor != null){
						t.setAcceptUserRealName(acceptor.getRealName());
					}
				}

				StringBuffer riskPointName = new StringBuffer();
				StringBuffer riskName = new StringBuffer();

				cq = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
				cq.eq("planId",t.getId());
				cq.notEq("relType", Constants.INVESTIGATEPLAN_REL_TYPE_HIDDENDANGER);//剔除关联隐患信息，页面不做显示
				cq.add();
				List<TBInvestigatePlanRelEntity> investigatePlanRelList = systemService.getListByCriteriaQuery(cq,false);
				if(!investigatePlanRelList.isEmpty() && investigatePlanRelList.size()>0){
					for(TBInvestigatePlanRelEntity rel : investigatePlanRelList){
						String relType = rel.getRelType();
						if(Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT.equals(relType) && Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_LOCATION.equals(rel.getPoitType())){
							//关联风险点(区域)
							if(StringUtils.isNotBlank(rel.getObjId())){
								TBAddressInfoEntity address = systemService.getEntity(TBAddressInfoEntity.class,rel.getObjId());
								if(address != null){
									riskPointName.append(address.getAddress()).append(";");
								}
							}
						}
						if(Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT.equals(relType) && Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_WORK.equals(rel.getPoitType())){
							//关联风险点(工作过程)
							if(StringUtils.isNotBlank(rel.getObjId())){
								TBWorkProcessManageEntity workProcess = systemService.getEntity(TBWorkProcessManageEntity.class,rel.getObjId());
								if(workProcess != null){
									riskPointName.append(workProcess.getName()).append(";");
								}
							}
						}
						if(Constants.INVESTIGATEPLAN_REL_TYPE_RISK.equals(relType)){
							//关联危险源
							if(StringUtils.isNotBlank(rel.getObjId())){
								TBDangerSourceEntity dangerSource = systemService.getEntity(TBDangerSourceEntity.class,rel.getObjId());
								if(dangerSource != null){
									riskName.append(dangerSource.getYeMhazardDesc()).append("<br />");
								}
							}
						}
					}
				}

				t.setRiskName(riskName.toString());
				if(StringUtils.isNotBlank(riskPointName.toString())){
					t.setRiskPointName(riskPointName.toString().substring(0, riskPointName.toString().length()-1));
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 受理排查计划
	 */
	@RequestMapping(params = "doAccept")
	@ResponseBody
	public AjaxJson doAccept(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "排查任务受理成功";
		try{
			for(String id:ids.split(",")) {
				TBInvestigatePlanEntity tBInvestigatePlan = systemService.getEntity(TBInvestigatePlanEntity.class, id);
				TSUser sessionUser = ResourceUtil.getSessionUserName();
				tBInvestigatePlan.setAcceptTime(new Date());
				tBInvestigatePlan.setAcceptUser(sessionUser.getId());
				tBInvestigatePlan.setStatus(Constants.INVESTIGATEPLAN_STATUS_PENDINGINVESTIGATE);
				tBInvestigatePlanService.saveOrUpdate(tBInvestigatePlan);

				//保存处理历史记录
				TBInvestigatePlanHistEntity history = new TBInvestigatePlanHistEntity();
				history.setPlanId(tBInvestigatePlan.getId());
				history.setPlanStatus(tBInvestigatePlan.getStatus());
				StringBuffer histDesc = new StringBuffer();
				TSUser user = ResourceUtil.getSessionUserName();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				histDesc.append("任务受理成功[处理人：").append(user.getRealName()).append("][处理时间：").append(sdf.format(new Date())).append("]");
				history.setHistDesc(histDesc.toString());
				systemService.save(history);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch (Exception e){
			e.printStackTrace();
			message = "排查任务受理失败";
			throw new BusinessException(e.getMessage());
		}
		return j;
	}

	/**
	 * 驳回排查计划
	 */
	@RequestMapping(params = "doRollback")
	@ResponseBody
	public AjaxJson doRollback(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "排查任务驳回成功";
		try{
			String ids = request.getParameter("ids");
			String rollBackRemark = request.getParameter("rollBackReason");
			if(StringUtils.isNotBlank(ids)) {
				for (String id : ids.split(",")) {
					TBInvestigatePlanEntity t = tBInvestigatePlanService.get(TBInvestigatePlanEntity.class, id);
					if(t != null){
						if(StringUtils.isNotBlank(rollBackRemark)){
							t.setRollBackReason(rollBackRemark);
						}
						t.setStatus(Constants.INVESTIGATEPLAN_STATUS_BACK);
						tBInvestigatePlanService.saveOrUpdate(t);

						//保存处理历史记录
						TBInvestigatePlanHistEntity history = new TBInvestigatePlanHistEntity();
						history.setPlanId(t.getId());
						history.setPlanStatus(t.getStatus());

						StringBuffer histDesc = new StringBuffer();
						TSUser user = ResourceUtil.getSessionUserName();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						histDesc.append("任务驳回成功[处理人：").append(user.getRealName()).append("][处理时间：").append(sdf.format(new Date())).append("][驳回原因：").append(t.getRollBackReason()).append("]");
						history.setHistDesc(histDesc.toString());

						systemService.save(history);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch (Exception e){
			e.printStackTrace();
			message = "排查任务驳回失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 排查计划驳回 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goRollback")
	public ModelAndView goRollback(HttpServletRequest request) {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			TBInvestigatePlanEntity investigatePlanEntity = systemService.getEntity(TBInvestigatePlanEntity.class,id);
			request.setAttribute("tBInvestigatePlanPage",investigatePlanEntity);
		}
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlan-rollback");
	}

	/**
	 * 回复排查计划
	 */
	@RequestMapping(params = "doReply")
	@ResponseBody
	public AjaxJson doReply(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "排查任务回复成功";
		try{
			String ids = request.getParameter("ids");
			String investigateTime = request.getParameter("investigateTime");
			String investigateDesc = request.getParameter("investigateDesc");
			if(StringUtils.isNotBlank(ids)) {
				for (String id : ids.split(",")) {
					TBInvestigatePlanEntity t = tBInvestigatePlanService.get(TBInvestigatePlanEntity.class, id);
					if(t != null){
						if(StringUtils.isNotBlank(investigateTime)){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							t.setInvestigateTime(sdf.parse(investigateTime));
						}
						if(StringUtils.isNotBlank(investigateDesc)){
							t.setInvestigateDesc(investigateDesc);
						}
						t.setStatus(Constants.INVESTIGATEPLAN_STATUS_INVESTIGATE);
						tBInvestigatePlanService.saveOrUpdate(t);

						//保存处理历史记录
						TBInvestigatePlanHistEntity history = new TBInvestigatePlanHistEntity();
						history.setPlanId(t.getId());
						history.setPlanStatus(t.getStatus());

						StringBuffer histDesc = new StringBuffer();
						TSUser user = ResourceUtil.getSessionUserName();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						histDesc.append("任务回复成功[处理人：").append(user.getRealName()).append("][处理时间：").append(sdf.format(new Date())).append("]");
						history.setHistDesc(histDesc.toString());

						systemService.save(history);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch (Exception e){
			e.printStackTrace();
			message = "排查任务回复失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 排查计划回复 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goReply")
	public ModelAndView goReply(HttpServletRequest request) {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			TBInvestigatePlanEntity t = systemService.getEntity(TBInvestigatePlanEntity.class,id);
			String acceptDept = t.getAcceptDepart();
			String acceptMan = t.getAcceptUser();
			if(StringUtils.isNotBlank(acceptDept)){
				TSDepart dept = systemService.getEntity(TSDepart.class,acceptDept);
				if(dept!= null){
					t.setAcceptDepartName(dept.getDepartname());
				}
			}
			if(StringUtils.isNotBlank(acceptMan)){
				TSUser acceptor = systemService.getEntity(TSUser.class,acceptMan);
				if(acceptor != null){
					t.setAcceptUserRealName(acceptor.getRealName());
				}
			}

			StringBuffer riskPointName = new StringBuffer();
			StringBuffer riskName = new StringBuffer();

			CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
			cq.eq("planId",t.getId());
			cq.notEq("relType", Constants.INVESTIGATEPLAN_REL_TYPE_HIDDENDANGER);//剔除关联隐患信息，页面不做显示
			cq.add();
			List<TBInvestigatePlanRelEntity> investigatePlanRelList = systemService.getListByCriteriaQuery(cq,false);
			if(!investigatePlanRelList.isEmpty() && investigatePlanRelList.size()>0){
				for(TBInvestigatePlanRelEntity rel : investigatePlanRelList){
					String relType = rel.getRelType();
					if(Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT.equals(relType) && Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_LOCATION.equals(rel.getPoitType())){
						//关联风险点(区域)
						if(StringUtils.isNotBlank(rel.getObjId())){
							TBAddressInfoEntity address = systemService.getEntity(TBAddressInfoEntity.class,rel.getObjId());
							if(address != null){
								riskPointName.append(address.getAddress()).append("\r\n");
							}
						}
					}
					if(Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT.equals(relType) && Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_WORK.equals(rel.getPoitType())){
						//关联风险点(工作过程)
						if(StringUtils.isNotBlank(rel.getObjId())){
							TBWorkProcessManageEntity workProcess = systemService.getEntity(TBWorkProcessManageEntity.class,rel.getObjId());
							if(workProcess != null){
								riskPointName.append(workProcess.getName()).append("\r\n");
							}
						}
					}
					if(Constants.INVESTIGATEPLAN_REL_TYPE_RISK.equals(relType)){
						//关联危险源
						if(StringUtils.isNotBlank(rel.getObjId())){
							TBDangerSourceEntity dangerSource = systemService.getEntity(TBDangerSourceEntity.class,rel.getObjId());
							if(dangerSource != null){
								riskName.append(dangerSource.getYeMhazardDesc()).append("\r\n");
							}
						}
					}
				}
			}

			t.setRiskName(riskName.toString());
			t.setRiskPointName(riskPointName.toString());

			request.setAttribute("tBInvestigatePlanPage",t);
		}
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlan-reply");
	}

	/**
	 * 关联隐患列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goHiddenList")
	public ModelAndView goHiddenList(HttpServletRequest request) {
		String planId = request.getParameter("planId");
		request.setAttribute("planId",planId);
		request.setAttribute("type", ResourceUtil.getParameter("type"));
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlan-hiddenList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "hiddenListDatagrid")
	public void hiddenListDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String planId = request.getParameter("planId");
		CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
		cq.eq("planId",planId);
		cq.eq("relType",Constants.INVESTIGATEPLAN_REL_TYPE_HIDDENDANGER);
//		try{
//			cq.createAlias("hiddenDanger","hiddenDanger");
//			String examDateBegin = request.getParameter("hiddenDanger.examDate_begin");
//			if(StringUtil.isNotEmpty(examDateBegin)){
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				cq.ge("hiddenDanger.examDate",sdf.parse(examDateBegin));
//			}
//
//			String examDateEnd = request.getParameter("hiddenDanger.examDate_end");
//			if(StringUtil.isNotEmpty(examDateEnd)){
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				cq.le("hiddenDanger.examDate", sdf.parse(examDateEnd));
//			}
//
//			String manageType = request.getParameter("hiddenDanger.manageType");
//			if(StringUtils.isNotBlank(manageType)){
//				cq.eq("hiddenDanger.manageType", manageType);
//			}
//
//			String handlelStatus = request.getParameter("handlelStatus");
//			if(StringUtils.isNotBlank(handlelStatus)){
//				cq.eq("handlelStatus", handlelStatus);
//			}
//
//
//		}catch (Exception e){
//			e.printStackTrace();
//		}


		cq.add();
		List<TBInvestigatePlanRelEntity> relList = systemService.getListByCriteriaQuery(cq,false);

		List<TBHiddenDangerHandleEntity> hiddenList = new ArrayList<>();
		if(!relList.isEmpty() && relList.size()>0){
			for(TBInvestigatePlanRelEntity rel:relList){
				String hiddenId = rel.getObjId();
				TBHiddenDangerHandleEntity hidden = systemService.getEntity(TBHiddenDangerHandleEntity.class,hiddenId);
				if(hidden != null){
					hiddenList.add(hidden);
				}
			}
		}
		dataGrid.setTotal(hiddenList.size());

		int currentPage = dataGrid.getPage();
		int pageSize = dataGrid.getRows();
		int endIndex = pageSize *currentPage;
		if(pageSize *currentPage > hiddenList.size()){
			endIndex = hiddenList.size();
		}
		List<TBHiddenDangerHandleEntity> resultList = hiddenList.subList(pageSize *(currentPage -1), endIndex);
		dataGrid.setResults(resultList);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 添加关联隐患 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAddHiddenRel")
	public ModelAndView goAddHiddenRel(HttpServletRequest request) {
		String planId = request.getParameter("planId");
		request.setAttribute("planId", planId);
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlan-addHiddenRel");
	}

	/**
	 * 添加关联隐患
	 */
	@RequestMapping(params = "doAddHidden")
	@ResponseBody
	public AjaxJson doAddHidden(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "关联隐患添加成功";
		try{
			String planId = request.getParameter("planId");
			String ids = request.getParameter("hiddenIds");
			TBInvestigatePlanEntity investigatePlan = tBInvestigatePlanService.get(TBInvestigatePlanEntity.class, planId);
			if(investigatePlan != null && StringUtils.isNotBlank(ids)){
				for (String hiddenId : ids.split(",")) {
					CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
					cq.eq("planId",planId);
					cq.eq("objId",hiddenId);
					cq.eq("relType",Constants.INVESTIGATEPLAN_REL_TYPE_HIDDENDANGER);
					cq.add();
					List<TBInvestigatePlanRelEntity> tempList = systemService.getListByCriteriaQuery(cq,false);
					if(tempList.size()<1){
						TBInvestigatePlanRelEntity t = new TBInvestigatePlanRelEntity();
						TBHiddenDangerHandleEntity hidden = systemService.getEntity(TBHiddenDangerHandleEntity.class,hiddenId);
						if(hidden != null){
							t.setObjId(hiddenId);
							t.setPlanId(planId);
							t.setRelType(Constants.INVESTIGATEPLAN_REL_TYPE_HIDDENDANGER);
							systemService.save(t);
						}
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch (Exception e){
			e.printStackTrace();
			message = "关联隐患添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除关联隐患
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDelHidden")
	@ResponseBody
	public AjaxJson doBatchDelHidden(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String planId = request.getParameter("planId");
		String message = "关联隐患移除成功";
		if(StringUtils.isNotBlank(planId)) {
			try {
				for (String id : ids.split(",")) {
					String sql = "delete from t_b_investigate_plan_rel where plan_id='" + planId + "' and obj_id='" + id + "' and rel_type='" + Constants.INVESTIGATEPLAN_REL_TYPE_HIDDENDANGER + "' ";
					systemService.executeSql(sql);
					systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = "关联隐患移除失败";
				systemService.addLog(message + "：" + e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
				throw new BusinessException(e.getMessage());
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除隐患排查
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String id, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "隐患排查删除成功";
		try{
			tBInvestigatePlanService.deleteById(id);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "隐患排查删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除隐患排查
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "隐患排查删除成功";
		try{
			tBInvestigatePlanService.deleteById(ids);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "隐患排查删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加隐患排查
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBInvestigatePlanEntity tBInvestigatePlan, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "隐患排查添加成功";
		try{
			String saveOrCommit = ResourceUtil.getParameter("saveOrCommit");
			tBInvestigatePlanService.saveOrCommit(tBInvestigatePlan, saveOrCommit);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "隐患排查添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新隐患排查
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBInvestigatePlanEntity tBInvestigatePlan, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "隐患排查更新成功";
		try {
			String saveOrCommit = ResourceUtil.getParameter("saveOrCommit");
			tBInvestigatePlanService.saveOrCommit(tBInvestigatePlan, saveOrCommit);
		} catch (Exception e) {
			e.printStackTrace();
			message = "隐患排查更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 派发排查假话
	 *
	 * @return
	 */
	@RequestMapping(params = "doAssign")
	@ResponseBody
	public AjaxJson doAssign(TBInvestigatePlanEntity tBInvestigatePlan, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "隐患排查派发成功";
		TBInvestigatePlanEntity t = tBInvestigatePlanService.get(TBInvestigatePlanEntity.class, tBInvestigatePlan.getId());
		try {
			if (StringUtil.isNotEmpty(tBInvestigatePlan.getAcceptUser())) {
				t.setAcceptDepart(tBInvestigatePlan.getAcceptDepart());
				t.setAcceptUser(tBInvestigatePlan.getAcceptUser());
				t.setInvestigateTime(tBInvestigatePlan.getInvestigateTime());
				t.setStatus(Constants.INVESTIGATEPLAN_STATUS_PENDINGINVESTIGATE);
			} else {
				t.setAcceptDepart(tBInvestigatePlan.getAcceptDepart());
				t.setStatus(Constants.INVESTIGATEPLAN_STATUS_PENDINGACCEPT);
			}
			tBInvestigatePlanService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "隐患排查派发失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 隐患排查新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBInvestigatePlanEntity tBInvestigatePlan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBInvestigatePlan.getId())) {
			tBInvestigatePlan = tBInvestigatePlanService.getEntity(TBInvestigatePlanEntity.class, tBInvestigatePlan.getId());
			req.setAttribute("tBInvestigatePlanPage", tBInvestigatePlan);
		}
		req.setAttribute("investigateType", ResourceUtil.getParameter("investigateType"));
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlan-add");
	}
	/**
	 * 隐患排查编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBInvestigatePlanEntity tBInvestigatePlan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBInvestigatePlan.getId())) {
			tBInvestigatePlan = tBInvestigatePlanService.getEntity(TBInvestigatePlanEntity.class, tBInvestigatePlan.getId());
			initRelObjectInfo(tBInvestigatePlan);
			req.setAttribute("tBInvestigatePlanPage", tBInvestigatePlan);
		}
		req.setAttribute("investigateType", ResourceUtil.getParameter("investigateType"));
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlan-update");
	}

	/**
	 * 隐患排查编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goDetail")
	public ModelAndView goDetail(TBInvestigatePlanEntity tBInvestigatePlan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBInvestigatePlan.getId())) {
			tBInvestigatePlan = tBInvestigatePlanService.getEntity(TBInvestigatePlanEntity.class, tBInvestigatePlan.getId());
			initRelObjectInfo(tBInvestigatePlan);
			tBInvestigatePlan.setRiskPointTypeDesc(DicUtil.getTypeNameByCode("investPlan_riskPoint_type", tBInvestigatePlan.getRiskPointType()));
			if(StringUtils.isNotBlank(tBInvestigatePlan.getAcceptUser())){
				TSUser acceptor = systemService.get(TSUser.class,tBInvestigatePlan.getAcceptUser());
				if(acceptor != null){
					tBInvestigatePlan.setAcceptUserRealName(acceptor.getRealName());
				}
			}
			if (StringUtils.isNotBlank(tBInvestigatePlan.getAcceptDepart())){
				TSDepart depart = systemService.get(TSDepart.class, tBInvestigatePlan.getAcceptDepart());
				if (depart != null) {
					tBInvestigatePlan.setAcceptDepartName(depart.getDepartname());
				}
			}
			req.setAttribute("tBInvestigatePlanPage", tBInvestigatePlan);
			if (StringUtil.isNotEmpty(tBInvestigatePlan.getRiskName())) {
				tBInvestigatePlan.setRiskName(tBInvestigatePlan.getRiskName().replace("\r\n", "<br>"));
			}
			if (StringUtils.isNoneBlank(tBInvestigatePlan.getRiskPointName())) {
				tBInvestigatePlan.setRiskPointName(tBInvestigatePlan.getRiskPointName().replace("\r\n", "<br>"));
			}
			CriteriaQuery histCq = new CriteriaQuery(TBInvestigatePlanHistEntity.class);
			histCq.eq("planId", tBInvestigatePlan.getId());
			histCq.addOrder("createDate", SortDirection.desc);
			histCq.add();
			List<TBInvestigatePlanHistEntity> histList = systemService.getListByCriteriaQuery(histCq, false);
			if (histList != null && histList.size() > 0) {
				for (TBInvestigatePlanHistEntity entity : histList) {
					entity.setPlanStatusDesc(DicUtil.getTypeNameByCode("investigatePlan_status", entity.getPlanStatus()));
				}
			}
			req.setAttribute("histList", histList);
		}
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlan-detail");
	}
	/**
	 * 隐患排查编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAssign")
	public ModelAndView goAssign(TBInvestigatePlanEntity tBInvestigatePlan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBInvestigatePlan.getId())) {
			tBInvestigatePlan = tBInvestigatePlanService.getEntity(TBInvestigatePlanEntity.class, tBInvestigatePlan.getId());
			req.setAttribute("tBInvestigatePlanPage", tBInvestigatePlan);
		}
		req.setAttribute("investigateType", ResourceUtil.getParameter("investigateType"));
		return new ModelAndView("com/sdzk/buss/web/investigateplan/tBInvestigatePlan-assign");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBInvestigatePlanController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBInvestigatePlanEntity tBInvestigatePlan,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBInvestigatePlan, request.getParameterMap());
		List<TBInvestigatePlanEntity> tBInvestigatePlans = this.tBInvestigatePlanService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"隐患排查");
		modelMap.put(NormalExcelConstants.CLASS,TBInvestigatePlanEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("隐患排查列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBInvestigatePlans);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBInvestigatePlanEntity tBInvestigatePlan,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"隐患排查");
    	modelMap.put(NormalExcelConstants.CLASS,TBInvestigatePlanEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("隐患排查列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TBInvestigatePlanEntity> listTBInvestigatePlanEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBInvestigatePlanEntity.class,params);
				for (TBInvestigatePlanEntity tBInvestigatePlan : listTBInvestigatePlanEntitys) {
					tBInvestigatePlanService.save(tBInvestigatePlan);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}

    /**
     * 排查计划上报集团
     * */
    @RequestMapping(params = "uploadInvestigationPlan")
    @ResponseBody
    public AjaxJson uploadInvestigationPlan(String ids, HttpServletRequest request){
        return uploadInvestigationPlan.uploadInvestigationPlan(ids);
    }

}
