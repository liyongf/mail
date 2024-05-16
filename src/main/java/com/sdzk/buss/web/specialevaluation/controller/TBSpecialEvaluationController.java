package com.sdzk.buss.web.specialevaluation.controller;

import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.address.service.TBAddressInfoServiceI;
import com.sdzk.buss.web.address.service.impl.TBAddressInfoServiceImpl;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerAddresstRelEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.specialevaluation.entity.TBSeDsRelationEntity;
import com.sdzk.buss.web.specialevaluation.entity.TBSpecialEvaluationEntity;
import com.sdzk.buss.web.specialevaluation.service.SpecialEvaluationRPC;
import com.sdzk.buss.web.specialevaluation.service.TBSpecialEvaluationServiceI;
import com.sdzk.buss.web.system.entity.TBSunshineEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import com.sdzk.buss.web.common.utils.SemanticSimilarityUtil;
/**   
 * @Title: Controller  
 * @Description: 专项风险辨识
 * @author onlineGenerator
 * @date 2017-07-01 10:55:00
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBSpecialEvaluationController")
public class TBSpecialEvaluationController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBSpecialEvaluationController.class);

	@Autowired
	private TBSpecialEvaluationServiceI tBSpecialEvaluationService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
    @Autowired
    private SpecialEvaluationRPC specialEvaluationRPC;
    @Autowired
    private TBAddressInfoServiceI tbAddressInfoService;
	


	/**
	 * 专项风险辨识列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		/******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
		Boolean isSunRole = false;

		TSUser user = ResourceUtil.getSessionUserName();
		String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
		List<String> userRoleList = systemService.findListbySql(userRoleSql);
		for (String userRole : userRoleList){
			if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
				isSunRole = true;
			}
		}
		if(isSunRole){
			request.setAttribute("isSunAdmin", "YGADMIN");
		}else{
			request.setAttribute("isSunAdmin", "common");
		}
		/***************************************************************************************************************/

		request.setAttribute("type", ResourceUtil.getParameter("type"));
		request.setAttribute("reportType", "depart");
		return new ModelAndView("com/sdzk/buss/web/specialevaluation/tBSpecialEvaluationList");
	}

	/**
	 * 专项风险辨识列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "reportList")
	public ModelAndView reportList(HttpServletRequest request, HttpServletResponse response) {
		/******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
		Boolean isSunRole = false;

		TSUser user = ResourceUtil.getSessionUserName();
		String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
		List<String> userRoleList = systemService.findListbySql(userRoleSql);
		for (String userRole : userRoleList){
			if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
				isSunRole = true;
			}
		}
		if(isSunRole){
			request.setAttribute("isSunAdmin", "YGADMIN");
		}else{
			request.setAttribute("isSunAdmin", "common");
		}
		/***************************************************************************************************************/

		request.setAttribute("type", ResourceUtil.getParameter("type"));
		request.setAttribute("reportType", "mine");
		return new ModelAndView("com/sdzk/buss/web/specialevaluation/tBSpecialEvaluationList-report");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

    /**
     * workTemp
     * */
	@RequestMapping(params = "datagrid")
	public void datagrid(TBSpecialEvaluationEntity tBSpecialEvaluation,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBSpecialEvaluationEntity.class, dataGrid);
		String name = tBSpecialEvaluation.getName();
		tBSpecialEvaluation.setName(null);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBSpecialEvaluation, request.getParameterMap());
		try{
		//自定义追加查询条件
            cq.eq("isDelete",Constants.IS_DELETE_N);
			cq.add(Restrictions.sqlRestriction(" this_.LOCATION not in (select id from t_b_address_info where isShowData = '0' )"));
			String status = ResourceUtil.getParameter("status");
			String reportType = ResourceUtil.getParameter("reportType");
			if (StringUtil.isNotEmpty(status)){
				if (status.contains(",")){
					cq.in("reportStatus", status.split(","));
				} else {
					cq.eq("reportStatus", status);
				}
			} else {
				if ("mine".equals(reportType)) {
					cq.eq("reportStatus", "1");
				} else if ("depart".equals(reportType)) {
					cq.eq("reportStatus", "0");
				}
			}
			//辨识名称查询
			if (StringUtil.isNotEmpty(name)) {
				cq.like("name" , "%"+name+"%");
			}

		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		/*******************阳光账号排除部分内容*******************/
		//判断用户的角色是否是阳光账号
		Boolean isSunRole = false;

		TSUser user = ResourceUtil.getSessionUserName();
		String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
		List<String> userRoleList = systemService.findListbySql(userRoleSql);
		for (String userRole : userRoleList){
			if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
				isSunRole = true;
			}
			if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
				isSunRole = true;
			}
		}
		if (isSunRole){
			String sunSql = "select column_id from t_b_sunshine where table_name='t_b_special_evaluation'";
			List<String> sunList = systemService.findListbySql(sunSql);
			if (sunList!=null && sunList.size()>0){
				String[] sunString = new String[sunList.size()];
				for (int i=0; i<sunList.size(); i++){
					sunString[i] = sunList.get(i);
				}
				cq.notIn("id", sunString);
			}
		}
		/*************************************************************/

		cq.add();
		this.tBSpecialEvaluationService.getDataGridReturn(cq, true);
		List<TBSpecialEvaluationEntity> result = dataGrid.getResults();
		if ( result != null && result.size() >0 ){
			String sql = "select count(1) from t_b_se_ds_relation where sepcial_evaluation_id = '?'";
			for (TBSpecialEvaluationEntity entity : result) {
				entity.setDangerSourceCount(systemService.getCountForJdbc(sql.replace("?", entity.getId())));
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}
    /**
     * 待上报记录-撤回待审核的记录
     * @author qichong
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "toReportCallback")
    @ResponseBody
    public AjaxJson toReportCallback(String ids, HttpServletRequest request) {
    	AjaxJson j = new AjaxJson();
    	Boolean success = true;
		String message = "专项辨识撤回成功";

		String token = ResourceUtil.getConfigByName("token");
		String mineCode = ResourceUtil.getConfigByName("mine_code");
		String url = ResourceUtil.getConfigByName("specialEvaluateReportCallback");
		String result = null;
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

		if(StringUtil.isNotEmpty(ids)) {
			try {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("token", ciphertext);
				paramMap.put("mineCode", mineCode);
                paramMap.put("reportContent", ids);

				result = HttpClientUtils.post(url, paramMap, "UTF-8");
			} catch (NetServiceException e) {
				success = false;
				message = "撤回失败：" + e.getMessage();
			}

			//解析rpc返回的json
			try {
				if (result != null) {
					net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
					String code = resultJson.getString("code");
					if (!code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {//请求成功
						success = false;
						message = "专项辨识撤回失败！";
					}
				} else {
					success = false;
					message = "专项辨识撤回失败！";
				}
			} catch (Exception e) {
				success = false;
				message = "专项辨识撤回失败！";
			}
			//更新本地数据库
			try {
				if (result != null) {
					net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
					String code = resultJson.getString("code");
					if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {//请求成功
						List<String> specialEvaluationTempIdList = new ArrayList<>();
						for(String id : ids.split(",")){
							specialEvaluationTempIdList.add(id);
						}
						CriteriaQuery cq = new CriteriaQuery(TBSpecialEvaluationEntity.class);
						cq.in("id",specialEvaluationTempIdList.toArray());
						cq.add();
						List<TBSpecialEvaluationEntity> specialEvaluationEntityList =systemService.getListByCriteriaQuery(cq,false);
						for(TBSpecialEvaluationEntity specialEvaluationEntity : specialEvaluationEntityList){
							//撤回之后本地状态改为未上报
							specialEvaluationEntity.setReportStatus(Constants.SE_REPORT_STATUS_DEPART_REPORT);
							systemService.saveOrUpdate(specialEvaluationEntity);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				success = false;
				message = "撤回成功，但本地数据库操作失败";
			}
		}

		j.setSuccess(success);
		j.setMsg(message);
		return j;
    }
	/**
	 * 删除专项风险辨识
	 * 
	 * @return
	 */
//	@RequestMapping(params = "doDel")
//	@ResponseBody
//	public AjaxJson doDel(TBSpecialEvaluationEntity tBSpecialEvaluation, HttpServletRequest request) {
//		String message = null;
//		AjaxJson j = new AjaxJson();
//		tBSpecialEvaluation = systemService.getEntity(TBSpecialEvaluationEntity.class, tBSpecialEvaluation.getId());
//		message = "专项风险辨识删除成功";
//		try{
//			tBSpecialEvaluationService.delete(tBSpecialEvaluation);
//			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "专项风险辨识删除失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}

	/**
	 * 批量删除专项风险辨识
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "专项风险辨识删除成功";
		try{
			for(String id:ids.split(",")){
				TBSpecialEvaluationEntity tBSpecialEvaluation = systemService.getEntity(TBSpecialEvaluationEntity.class, 
				id
				);
				tBSpecialEvaluationService.delete(tBSpecialEvaluation);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
			String idss = ids.replace(",","','");
			//删除关联的风险
			String delYeEva = "update t_b_danger_source set is_delete='1' where id in (select DANGER_SOURCE_ID from t_b_se_ds_relation where SEPCIAL_EVALUATION_ID in ('"+idss+"'))";
			systemService.executeSql(delYeEva);
			//删除关联关系
			String delRelation = "delete from t_b_se_ds_relation where SEPCIAL_EVALUATION_ID in ('"+idss+"')";
			systemService.executeSql(delRelation);
		}catch(Exception e){
			e.printStackTrace();
			message = "专项风险辨识删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


    /**
     * 批量删除专项风险辨识
     *
     * @return
     */
    @RequestMapping(params = "LogicDoBatchDel")
    @ResponseBody
    public AjaxJson LogicDoBatchDel(String ids,HttpServletRequest request){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "专项风险辨识删除成功";
        try{
            for(String id:ids.split(",")){
                TBSpecialEvaluationEntity tBSpecialEvaluation = systemService.getEntity(TBSpecialEvaluationEntity.class,
                        id
                );
//                tBSpecialEvaluationService.delete(tBSpecialEvaluation);
                tBSpecialEvaluation.setIsDelete("1");
                tBSpecialEvaluationService.saveOrUpdate(tBSpecialEvaluation);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
            String idss = ids.replace(",","','");
            //删除关联的风险
            String delYeEva = "update t_b_danger_source set is_delete='1' where id in (select DANGER_SOURCE_ID from t_b_se_ds_relation where SEPCIAL_EVALUATION_ID in ('"+idss+"'))";
            systemService.executeSql(delYeEva);
//            //删除关联关系
//            String delRelation = "delete from t_b_se_ds_relation where SEPCIAL_EVALUATION_ID in ('"+idss+"')";
//            systemService.executeSql(delRelation);
        }catch(Exception e){
            e.printStackTrace();
            message = "专项风险辨识删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

	/**
	 * 添加专项风险辨识
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBSpecialEvaluationEntity tBSpecialEvaluation, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "专项风险辨识添加成功";
		try{
            tBSpecialEvaluation.setIsDelete(Constants.IS_DELETE_N);
			tBSpecialEvaluationService.save(tBSpecialEvaluation);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "专项风险辨识添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("id",tBSpecialEvaluation.getId());
		j.setAttributes(attributes);
		return j;
	}
	
	/**
	 * 更新专项风险辨识
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBSpecialEvaluationEntity tBSpecialEvaluation, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "专项风险辨识更新成功";
		TBSpecialEvaluationEntity t = tBSpecialEvaluationService.get(TBSpecialEvaluationEntity.class, tBSpecialEvaluation.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBSpecialEvaluation, t);
			tBSpecialEvaluationService.saveOrUpdate(t);
			if (Constants.SE_REPORT_STATUS_DEPART_REPORT.equals(tBSpecialEvaluation.getReportStatus())) {
				//更新相关危险源为上报
				String sql = "update t_b_danger_source set audit_status= '"+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"', report_status='"+Constants.DANGER_SOURCE_REPORT_UNREPORT+"' where id in " +
						"(select danger_source_id from t_b_se_ds_relation where sepcial_evaluation_id = '"+tBSpecialEvaluation.getId()+"')";
				systemService.updateBySqlString(sql);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "专项风险辨识更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


    /**
     * 更新专项风险辨识
     *
     * @return
     */
    @RequestMapping(params = "doUpdateOnReport")
    @ResponseBody
    public AjaxJson doUpdateOnReport(TBSpecialEvaluationEntity tBSpecialEvaluation, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "专项风险辨识更新成功";
        TBSpecialEvaluationEntity t = tBSpecialEvaluationService.get(TBSpecialEvaluationEntity.class, tBSpecialEvaluation.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBSpecialEvaluation, t);
            tBSpecialEvaluationService.saveOrUpdate(t);
//            if (Constants.SE_REPORT_STATUS_DEPART_REPORT.equals(tBSpecialEvaluation.getReportStatus())) {
//                //更新相关危险源为上报
//                String sql = "update t_b_danger_source set audit_status= '"+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"', report_status='"+Constants.DANGER_SOURCE_REPORT_UNREPORT+"' where id in " +
//                        "(select danger_source_id from t_b_se_ds_relation where sepcial_evaluation_id = '"+tBSpecialEvaluation.getId()+"')";
//                systemService.updateBySqlString(sql);
//            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "专项风险辨识更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
	/**
	 * 部门上报
	 * @param ids
	 * @param request
     * @return
     */
	@RequestMapping(params = "departReport")
	@ResponseBody
	public AjaxJson departReport(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "专项风险辨识上报成功";
		try {
			if (StringUtil.isNotEmpty(ids)){
				for (String id: ids.split(",")) {
					TBSpecialEvaluationEntity t = tBSpecialEvaluationService.get(TBSpecialEvaluationEntity.class, id);
					if(t!= null && Constants.SE_REPORT_STATUS_PENDING_REPORT.equals(t.getReportStatus())){
						t.setReportStatus(Constants.SE_REPORT_STATUS_DEPART_REPORT);
						tBSpecialEvaluationService.saveOrUpdate(t);
						//更新相关危险源为上报
						String sql1 = "update t_b_danger_source set audit_status= '"+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"', report_status='"+Constants.DANGER_SOURCE_REPORT_UNREPORT+"' where id in " +
								"(select danger_source_id from t_b_se_ds_relation where sepcial_evaluation_id = '"+id+"')";
						systemService.updateBySqlString(sql1);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
						//TODO houbin 添加地点绑定风险
					//	TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,t.getLocation());
						CriteriaQuery cq = new CriteriaQuery(TBSeDsRelationEntity.class);
						try{
							cq.eq("sepcialEvaluationId",t.getId());
						}catch (Exception e){
							e.printStackTrace();
						}
						cq.add();
						List<TBSeDsRelationEntity> list = systemService.getListByCriteriaQuery(cq,false);
						if(list != null && !list.isEmpty()){
							TBDangerAddresstRelEntity addressRel = new TBDangerAddresstRelEntity();
							for(TBSeDsRelationEntity bean : list){
								String dangerSourceId = bean.getDangerSourceId();
								TBDangerSourceEntity dangerSourceEntity = systemService.getEntity(TBDangerSourceEntity.class,dangerSourceId);
								if(dangerSourceEntity != null) {
									addressRel.setAddressId(t.getLocation());
									addressRel.setDangerId(dangerSourceId);
									addressRel.setRiskLevel(dangerSourceEntity.getYeRiskGrade());
									systemService.save(addressRel);
								}
							}
						}
					}
				}
				//刷新风险的隐患描述分词缓存
				this.systemService.initAllDSParts();
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "专项风险辨识上报失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 部门撤回
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "unDepartReport")
	@ResponseBody
	public AjaxJson unDepartReport(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "专项风险辨识撤回成功";
		try {
			if (StringUtil.isNotEmpty(ids)){
				for (String id: ids.split(",")) {
					TBSpecialEvaluationEntity t = tBSpecialEvaluationService.get(TBSpecialEvaluationEntity.class, id);
					if(t!= null && Constants.SE_REPORT_STATUS_DEPART_REPORT.equals(t.getReportStatus())){
						t.setReportStatus(Constants.SE_REPORT_STATUS_PENDING_REPORT);
						tBSpecialEvaluationService.saveOrUpdate(t);
						//更新相关危险源为未上报
						String sql1 = "update t_b_danger_source set audit_status= '"+Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT+"', report_status='"+Constants.DANGER_SOURCE_REPORT_UNREPORT+"' where id in " +
								"(select danger_source_id from t_b_se_ds_relation where sepcial_evaluation_id = '"+id+"')";
						systemService.updateBySqlString(sql1);

						//删除风险点和危险源的关联关系
						String sql2 = "delete from t_b_danger_address_rel where danger_id in " +
								"(select danger_source_id from t_b_se_ds_relation where sepcial_evaluation_id = '"+id+"')";
						systemService.updateBySqlString(sql2);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					}
				}

				//刷新风险的隐患描述分词缓存
				this.systemService.initAllDSParts();
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "专项风险辨识撤回失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 矿上报
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "mineReport")
	@ResponseBody
	public AjaxJson mineReport(String ids, HttpServletRequest request) {
		return  specialEvaluationRPC.mineReport(ids,false);
	}


	/**
	 * 上报集团
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "specialEvaluateReportToGroup")
	@ResponseBody
	public AjaxJson specialEvaluateReportToGroup(String ids, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isEmpty(ids)) {
			j.setMsg("请选择要上报的专项风险辨识");
			return j;
		}
		CriteriaQuery cq = new CriteriaQuery(TBSpecialEvaluationEntity.class);
		cq.in("id", ids.split(","));
		cq.add();
		List<TBSpecialEvaluationEntity> list = systemService.getListByCriteriaQuery(cq,false);
		if (list == null || list.size() == 0) {
			j.setMsg("所选专项风险辨识数据库中不存在");
			return j;
		}

		return  specialEvaluationRPC.specialEvaluateReportToGroup(list);
	}

	/**
	 * 专项风险辨识新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBSpecialEvaluationEntity tBSpecialEvaluation, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBSpecialEvaluation.getId())) {
			tBSpecialEvaluation = tBSpecialEvaluationService.getEntity(TBSpecialEvaluationEntity.class, tBSpecialEvaluation.getId());
			req.setAttribute("tBSpecialEvaluationPage", tBSpecialEvaluation);
		}
		req.setAttribute("type", ResourceUtil.getParameter("type"));
		return new ModelAndView("com/sdzk/buss/web/specialevaluation/tBSpecialEvaluation-add");
	}

	/**
	 * 组合组织机构
	 *
	 * @return
	 */
	@RequestMapping(params = "departmentTree")
	@ResponseBody
	public List<ComboTree> departmentTree(HttpServletRequest request,final ComboTree rootComboTree) {


		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
        Short flag = 0;
		List<TSDepart> departList = systemService.findByProperty(TSDepart.class,"deleteFlag",flag);
        List<TSDepart> existDepart = new ArrayList<>(departList);
        Map<TSDepart,TSDepart> departMap = new HashMap<>();
        for(TSDepart tsDepart : departList){
            departMap.put(tsDepart,tsDepart.getTSPDepart());
        }
        Map<TSDepart,ComboTree> tempMap = new HashMap<>();

        //所有组织机构被遍历
        while(!departMap.isEmpty()) {
            //一次遍历，筛选叶子节点
            Iterator<Map.Entry<TSDepart, TSDepart>> tempIterator = departMap.entrySet().iterator();
            while(tempIterator.hasNext()) {
                Map.Entry<TSDepart, TSDepart> mapEntry = tempIterator.next();
                TSDepart tsDepart = mapEntry.getKey();
                TSDepart tsDepartFather = mapEntry.getValue();
                //非要处理叶子节点
                if (!departMap.containsValue(tsDepart)) {
                    //存在根节点
                    if (tsDepartFather != null && existDepart.contains(tsDepartFather)) {
                        //是否初始化
                        if (tempMap.containsKey(tsDepart)) {
                            ComboTree children = tempMap.get(tsDepart);
                            //子节点父节点已存在
                            if (tempMap.containsKey(tsDepartFather)) {
                                //添加此次叶子节点
                                tempMap.get(tsDepartFather).getChildren().add(children);
                            } else {
                                //父节点未生成，新建
                                ComboTree father = turnComboTree(tsDepartFather);
                                if (father.getChildren() == null) {
                                    father.setChildren(new ArrayList<ComboTree>());
                                }
                                //添加此次叶子节点
                                father.getChildren().add(children);
                                //父节点加入已有节点map
                                tempMap.put(tsDepartFather, father);
                            }
                            //节点处理后删除
                            tempMap.remove(tsDepart);
                            tempIterator.remove();
                        } else {
                            //新建此叶子节点
                            ComboTree children = turnComboTree(tsDepart);
                            if (tempMap.containsKey(tsDepartFather)) {
                                tempMap.get(tsDepartFather).getChildren().add(children);
                            } else {
                                ComboTree father = turnComboTree(tsDepartFather);
                                if (father.getChildren() == null) {
                                    father.setChildren(new ArrayList<ComboTree>());
                                }
                                father.getChildren().add(children);
                                tempMap.put(tsDepartFather, father);
                            }
                            tempIterator.remove();
                        }
                        //父亲节点为空
                    } else {
                        if (tempMap.containsKey(tsDepart)) {
                            ComboTree children = tempMap.get(tsDepart);
                            if (rootComboTree.getChildren() == null) {
                                rootComboTree.setChildren(new ArrayList<ComboTree>());
                            }
                            rootComboTree.getChildren().add(children);
                            tempMap.remove(tsDepart);
                        } else {
                            ComboTree children = turnComboTree(tsDepart);
                            if (rootComboTree.getChildren() == null) {
                                rootComboTree.setChildren(comboTrees);
                            }
                            rootComboTree.getChildren().add(children);
                        }
                        tempIterator.remove();
                    }
                }
            }
        }
        if(rootComboTree.getChildren().size()==1){
            rootComboTree.getChildren().get(0).setChecked(true);
            return new ArrayList<ComboTree>(){{add(rootComboTree.getChildren().get(0));}};
        }else{
            rootComboTree.setChecked(true);
            return new ArrayList<ComboTree>(){{add(rootComboTree);}};
        }
	}

    private ComboTree turnComboTree(TSDepart tsDepart){
        ComboTree departTree = new ComboTree();
        departTree.setId(tsDepart.getId());
        departTree.setText(tsDepart.getDepartname());
        return departTree;
    }

	/**
	 * 专项风险辨识编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBSpecialEvaluationEntity tBSpecialEvaluation, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBSpecialEvaluation.getId())) {
			tBSpecialEvaluation = tBSpecialEvaluationService.getEntity(TBSpecialEvaluationEntity.class, tBSpecialEvaluation.getId());
			if (tBSpecialEvaluation != null && StringUtil.isNotEmpty(tBSpecialEvaluation.getParticipant())) {
				StringBuffer participantName = new StringBuffer();
				for (String userId: tBSpecialEvaluation.getParticipant().split(",")) {
					TSBaseUser user = systemService.get(TSBaseUser.class, userId);
					if (user != null) {
						if (StringUtil.isNotEmpty(participantName.toString())) {
							participantName.append(",");
						}
						participantName.append(user.getRealName());
					}
				}
				if (StringUtil.isNotEmpty(participantName.toString())) {
					tBSpecialEvaluation.setParticipantName(participantName.toString());
				}
			}
			req.setAttribute("tBSpecialEvaluationPage", tBSpecialEvaluation);
		}
		if ("detail".equals(ResourceUtil.getParameter("load"))) {
			if (StringUtil.isNotEmpty(tBSpecialEvaluation.getLeader())){
				TSBaseUser user = systemService.get(TSBaseUser.class, tBSpecialEvaluation.getLeader());
				if (user != null) {
					req.setAttribute("leader", user.getRealName());
				}
			}
			if (StringUtil.isNotEmpty(tBSpecialEvaluation.getLocation())){
				TBAddressInfoEntity address = systemService.get(TBAddressInfoEntity.class, tBSpecialEvaluation.getLocation());
				if (address != null) {
					req.setAttribute("location", address.getAddress());
				}
			}
			if (StringUtil.isNotEmpty(tBSpecialEvaluation.getLeaderType())){
				req.setAttribute("leaderType", DicUtil.getTypeNameByCode("evaluationLeaderType",tBSpecialEvaluation.getLeaderType()));
			}
			if (StringUtil.isNotEmpty(tBSpecialEvaluation.getParticipant())){
				StringBuffer participant = new StringBuffer();
				for (String p : tBSpecialEvaluation.getParticipant().split(",")) {
					TSBaseUser user = systemService.get(TSBaseUser.class, p);
					if (user != null) {
						if (StringUtil.isNotEmpty(participant.toString())) {
							participant.append(",");
						}
						participant.append(user.getRealName());
					}
				}
				if (StringUtil.isNotEmpty(participant.toString())) {
					req.setAttribute("participant", participant.toString());
				}
			}
			return new ModelAndView("com/sdzk/buss/web/specialevaluation/tBSpecialEvaluation-detail");
		}
		return new ModelAndView("com/sdzk/buss/web/specialevaluation/tBSpecialEvaluation-update");
	}

    /**
     * 专项风险辨识编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdateOnReport")
    public ModelAndView goUpdateOnReport(TBSpecialEvaluationEntity tBSpecialEvaluation, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBSpecialEvaluation.getId())) {
            tBSpecialEvaluation = tBSpecialEvaluationService.getEntity(TBSpecialEvaluationEntity.class, tBSpecialEvaluation.getId());
            if (tBSpecialEvaluation != null && StringUtil.isNotEmpty(tBSpecialEvaluation.getParticipant())) {
                StringBuffer participantName = new StringBuffer();
                for (String userId: tBSpecialEvaluation.getParticipant().split(",")) {
                    TSBaseUser user = systemService.get(TSBaseUser.class, userId);
                    if (user != null) {
                        if (StringUtil.isNotEmpty(participantName.toString())) {
                            participantName.append(",");
                        }
                        participantName.append(user.getRealName());
                    }
                }
                if (StringUtil.isNotEmpty(participantName.toString())) {
                    tBSpecialEvaluation.setParticipantName(participantName.toString());
                }
            }
            req.setAttribute("tBSpecialEvaluationPage", tBSpecialEvaluation);
        }
        if ("detail".equals(ResourceUtil.getParameter("load"))) {
            if (StringUtil.isNotEmpty(tBSpecialEvaluation.getLeader())){
                TSBaseUser user = systemService.get(TSBaseUser.class, tBSpecialEvaluation.getLeader());
                if (user != null) {
                    req.setAttribute("leader", user.getRealName());
                }
            }
            if (StringUtil.isNotEmpty(tBSpecialEvaluation.getLocation())){
                TBAddressInfoEntity address = systemService.get(TBAddressInfoEntity.class, tBSpecialEvaluation.getLocation());
                if (address != null) {
                    req.setAttribute("location", address.getAddress());
                }
            }
            if (StringUtil.isNotEmpty(tBSpecialEvaluation.getLeaderType())){
                req.setAttribute("leaderType", DicUtil.getTypeNameByCode("evaluationLeaderType",tBSpecialEvaluation.getLeaderType()));
            }
            if (StringUtil.isNotEmpty(tBSpecialEvaluation.getParticipant())){
                StringBuffer participant = new StringBuffer();
                for (String p : tBSpecialEvaluation.getParticipant().split(",")) {
                    TSBaseUser user = systemService.get(TSBaseUser.class, p);
                    if (user != null) {
                        if (StringUtil.isNotEmpty(participant.toString())) {
                            participant.append(",");
                        }
                        participant.append(user.getRealName());
                    }
                }
                if (StringUtil.isNotEmpty(participant.toString())) {
                    req.setAttribute("participant", participant.toString());
                }
            }
            return new ModelAndView("com/sdzk/buss/web/specialevaluation/tBSpecialEvaluation-detail");
        }
        return new ModelAndView("com/sdzk/buss/web/specialevaluation/tBSpecialEvaluationUpdateOnReport");
    }
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBSpecialEvaluationController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}




    /**
     * 导出excel
     *
     * Author： 张赛超
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBSpecialEvaluationEntity tBSpecialEvaluation,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        String name = tBSpecialEvaluation.getName();
        tBSpecialEvaluation.setName(null);

        CriteriaQuery cq = new CriteriaQuery(TBSpecialEvaluationEntity.class, dataGrid);    //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBSpecialEvaluation, request.getParameterMap());

        String status = request.getParameter("status");
        String type = request.getParameter("type");

        try{
            cq.eq("isDelete",Constants.IS_DELETE_N);
            //自定义追加查询条件
            String reportType = ResourceUtil.getParameter("reportType");
            if (StringUtil.isNotEmpty(status)){
                if (status.contains(",")){
                    cq.in("reportStatus", status.split(","));
                } else {
                    cq.eq("reportStatus", status);
                }
            } else {
                if ("mine".equals(reportType)) {
                    cq.eq("reportStatus", "1");
                } else if ("depart".equals(reportType)) {
                    cq.eq("reportStatus", "0");
                }
            }
            //辨识名称查询
            if (StringUtil.isNotEmpty(name)) {
                cq.like("name" , "%"+name+"%");
            }
            if (StringUtil.isNotEmpty(type)) {
                cq.eq("type", type);
            }

        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();

        List<TBSpecialEvaluationEntity> tBSpecialEvaluations = this.tBSpecialEvaluationService.getListByCriteriaQuery(cq,false);
        if ( tBSpecialEvaluations != null && tBSpecialEvaluations.size() >0 ){
            String sql = "select count(1) from t_b_se_ds_relation where sepcial_evaluation_id = '?'";
            for (TBSpecialEvaluationEntity entity : tBSpecialEvaluations) {
                entity.setDangerSourceCount(systemService.getCountForJdbc(sql.replace("?", entity.getId())));

                //转换对应的字段
//                TBSpecialEvaluationEntity majorHiddenDangerEntity = systemService.get(TBSpecialEvaluationEntity.class, id);
                //将辨识负责人从id转换成汉字
                TSBaseUser tsBaseUser = systemService.get(TSBaseUser.class, entity.getLeader() );
                if (tsBaseUser != null){
                    entity.setLeaderTemp(tsBaseUser.getRealName());
                }
                //将辨识参与人从id转换为汉字
                String participantMembers = entity.getParticipant();
                String[] pm = participantMembers.split(",");
                String pmTemp = "";
                for(int i=0; i<pm.length; i++){
                    if(pm[i] != null || pm[i] != ""){
                        pmTemp = pmTemp + systemService.get(TSBaseUser.class, pm[i]).getRealName() + ",";
                    }
                }
                pmTemp = pmTemp.substring(0, pmTemp.length()-1);
                entity.setParticipantTemp(pmTemp);
                //将专项辨识类型转换为汉字
                /**
                下面这段代码用来把数据转换成对应的字典值
                tBHiddenDangerExam.setShiftTemp(DicUtil.getTypeNameByCode("workShift", tBHiddenDangerExam.getShift()));
                 */
                entity.setTypeTemp(DicUtil.getTypeNameByCode("specialEvaluationType", entity.getType()));
                //将上报状态根据字段值转换成对应的汉字
                entity.setReportStatusTemp(DicUtil.getTypeNameByCode("seReportStatus", entity.getReportStatus()));
                //将辨识地点从id转化为对应的汉字
                /**
                 * 根据id从数据库中查找数据
                 * 最初的方法是冉哥给的查找方法
                 * */
                String locationTemp = entity.getLocation();
                TBAddressInfoEntity tbAddressInfoEntity = tbAddressInfoService.getEntity(TBAddressInfoEntity.class, entity.getLocation());
                if(null!=tbAddressInfoEntity){
                    locationTemp = tbAddressInfoEntity.getAddress();
                }
                entity.setLocationTemp(locationTemp);

            }
        }

        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetName("专项风险列表");

        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_specialRisk.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", tBSpecialEvaluations);

        modelMap.put(NormalExcelConstants.FILE_NAME,"专项风险列表");

        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }
    /**
     * QQ：1228310398
     * */




	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBSpecialEvaluationEntity tBSpecialEvaluation,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"专项风险辨识");
    	modelMap.put(NormalExcelConstants.CLASS,TBSpecialEvaluationEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("专项风险辨识列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TBSpecialEvaluationEntity> listTBSpecialEvaluationEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBSpecialEvaluationEntity.class,params);
				for (TBSpecialEvaluationEntity tBSpecialEvaluation : listTBSpecialEvaluationEntitys) {
					tBSpecialEvaluationService.save(tBSpecialEvaluation);
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
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TBSpecialEvaluationEntity> list() {
		List<TBSpecialEvaluationEntity> listTBSpecialEvaluations=tBSpecialEvaluationService.getList(TBSpecialEvaluationEntity.class);
		return listTBSpecialEvaluations;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TBSpecialEvaluationEntity task = tBSpecialEvaluationService.get(TBSpecialEvaluationEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TBSpecialEvaluationEntity tBSpecialEvaluation, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBSpecialEvaluationEntity>> failures = validator.validate(tBSpecialEvaluation);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBSpecialEvaluationService.save(tBSpecialEvaluation);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tBSpecialEvaluation.getId();
		URI uri = uriBuilder.path("/rest/tBSpecialEvaluationController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TBSpecialEvaluationEntity tBSpecialEvaluation) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBSpecialEvaluationEntity>> failures = validator.validate(tBSpecialEvaluation);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBSpecialEvaluationService.saveOrUpdate(tBSpecialEvaluation);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tBSpecialEvaluationService.deleteEntityById(TBSpecialEvaluationEntity.class, id);
	}

	/**
	 * 专项风险辨识克隆页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goClone")
	public ModelAndView goClone(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			TBSpecialEvaluationEntity entity = systemService.getEntity(TBSpecialEvaluationEntity.class, id);
			String tempName = "";

			if(StringUtil.isNotEmpty(entity.getParticipant())){
				for(String i : entity.getParticipant().split(",")){
					TSUser tsUser = systemService.getEntity(TSUser.class, i);
					tempName += tsUser.getRealName() + ",";
				}
			}
			entity.setParticipantName(tempName.substring(0, tempName.length()-1));
			request.setAttribute("tBSpecialEvaluationPage", entity);
		}
		return new ModelAndView("com/sdzk/buss/web/specialevaluation/tBSpecialEvaluation-clone");
	}

	/**
	 * 添加专项风险辨识克隆
	 *
	 * @return
	 */
	@RequestMapping(params = "doClone")
	@ResponseBody
	public AjaxJson doClone(HttpServletRequest request,HttpServletResponse response, TBSpecialEvaluationEntity tbSpecialEvaluationEntity) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "专项风险辨识克隆成功";
		try{
			tbSpecialEvaluationEntity.setIsDelete(Constants.IS_DELETE_N);
			tbSpecialEvaluationEntity.setReportStatus("1");

			List<TBSeDsRelationEntity> relationEntityList = systemService.findByProperty(TBSeDsRelationEntity.class, "sepcialEvaluationId", tbSpecialEvaluationEntity.getId());
			List<TBSeDsRelationEntity> resultList = new ArrayList<TBSeDsRelationEntity>();
			tbSpecialEvaluationEntity.setId(UUIDGenerator.generate());
			for (TBSeDsRelationEntity e : relationEntityList){
				TBSeDsRelationEntity temp = new TBSeDsRelationEntity();
				temp.setSepcialEvaluationId(tbSpecialEvaluationEntity.getId());
				temp.setDangerSourceId(e.getDangerSourceId());

				resultList.add(temp);
			}
			systemService.batchSave(resultList);

			tBSpecialEvaluationService.save(tbSpecialEvaluationEntity);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "专项风险辨识克隆失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
//		Map<String, Object> attributes = new HashMap<>();
//		attributes.put("id",tbSpecialEvaluationEntity.getId());
//		j.setAttributes(attributes);
		return j;
	}


	/**
	 *  阳光账号隐藏数据操作
	 *  @return
	 * */
	@RequestMapping(params = "sunshine")
	@ResponseBody
	public AjaxJson sunShine(HttpServletRequest request, HttpServletResponse response){
		String message = "隐藏成功！";
		AjaxJson j = new AjaxJson();
		String ids = request.getParameter("ids");

		try{
			if(StringUtil.isNotEmpty(ids)){
				for (String id : ids.split(",")){
					//这个表用来保存哪些是隐藏的
					List<TBSunshineEntity> sunshineEntityList = systemService.findByProperty(TBSunshineEntity.class, "columnId", id);
					//如果需要隐藏的数据已经存在，那么删除该数据
					if (sunshineEntityList!=null && sunshineEntityList.size()>0){
						systemService.deleteAllEntitie(sunshineEntityList);
					}

					TBSunshineEntity sunshineEntity = new TBSunshineEntity();
					sunshineEntity.setTableName("t_b_special_evaluation");
					sunshineEntity.setColumnId(id);

					systemService.save(sunshineEntity);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			message = "隐藏失败";
			throw new BusinessException(e.getMessage());
		}

		j.setMsg(message);
		return j;
	}

}
