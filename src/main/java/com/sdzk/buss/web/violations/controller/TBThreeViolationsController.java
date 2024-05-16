package com.sdzk.buss.web.violations.controller;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.address.service.TBAddressInfoServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.ThreeViolationsExcelVerifyHandler;
import com.sdzk.buss.web.common.service.SfService;
import com.sdzk.buss.web.common.taskProvince.TBReportDeleteIdEntity;
import com.sdzk.buss.web.fine.entity.TBFineEntity;
import com.sdzk.buss.web.system.entity.TBSunshineEntity;
import com.sdzk.buss.web.violations.entity.MobileTBThreeViolationsEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import com.sdzk.buss.web.violations.service.TBThreeViolationsServiceI;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzk.buss.web.violations.service.UploadThreeViolence;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @Description: 三违信息
 * @author onlineGenerator
 * @date 2017-06-17 17:27:22
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/tBThreeViolationsController")
public class TBThreeViolationsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBThreeViolationsController.class);

	@Autowired
	private TBThreeViolationsServiceI tBThreeViolationsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TBAddressInfoServiceI tBAddressInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private UploadThreeViolence uploadThreeViolence;

	@Autowired
	private SfService sfService;


	/**
	 * 三违信息列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
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
		//横河个性化处理
		String henghe = ResourceUtil.getConfigByName("henghe");
		if(StringUtil.isNotEmpty(henghe)){
			request.setAttribute("henghe",henghe);
		}
		//新查庄个性化处理
		String xinchazhuang = ResourceUtil.getConfigByName("xinchazhuang");
		if(StringUtil.isNotEmpty(xinchazhuang)){
			request.setAttribute("xinchazhuang",xinchazhuang);
		}
		String reporttype = request.getParameter("reporttype");
		if(StringUtils.isNotBlank(reporttype)){
			request.setAttribute("reporttype",reporttype);
			return new ModelAndView("com/sdzk/buss/web/violations/tBThreeViolationsReportList");
		}else{
			return new ModelAndView("com/sdzk/buss/web/violations/tBThreeViolationsList");
		}
	}

	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(HttpServletRequest request,HttpServletResponse response ,ModelMap modelMap) {
        /*modelMap.put(TemplateExcelConstants.FILE_NAME,"三违导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/importTemp_threeViolation.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String, Object> param =new HashMap<String, Object>();
        modelMap.put(TemplateExcelConstants.MAP_DATA,param);*/
		modelMap.put(TemplateExcelConstants.FILE_NAME,"三违导入模板");
		TemplateExportParams templateExportParams = new TemplateExportParams();
		templateExportParams.setSheetNum(1);
		templateExportParams.setScanAllsheet(true);
		Map<String, Object> param =new HashMap<String, Object>();
		templateExportParams.setTemplateUrl("export/template/importTemp_threeViolationNew.xls");
		String xinchazhuang = ResourceUtil.getConfigByName("xinchazhuang");
		if(StringUtil.isNotEmpty(xinchazhuang)){
			if(xinchazhuang.equals("true")){
				templateExportParams.setTemplateUrl("export/template/importTemp_threeViolationNewXCZ.xls");
			}
		}
		Map<String, List<String>> dicListMap = new HashMap<>();
		//班次
		String shiftSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='workShift')";
		List<String> shiftList = systemService.findListbySql(shiftSql);

		//地点
		String addressListSql = "select address from t_b_address_info where is_delete='0'";
		List<String> addressList = systemService.findListbySql(addressListSql);

		//单位
		String unitSql = "select departName from t_s_depart where delete_flag='0'";
		List<String> unitList = systemService.findListbySql(unitSql);

		//人员
		String manSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
		List<String> manList = systemService.findListbySql(manSql);

		//违章分类
		String vioCategorySql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='violaterule_wzfl')";
		List<String> vioCategoryList = systemService.findListbySql(vioCategorySql);

		//三违级别
		String vioLevelSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='vio_level')";
		List<String> vioLevelList = systemService.findListbySql(vioLevelSql);

		//违章定性
		String vioQualitativeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='violaterule_wzdx')";
		List<String> vioQualitativeList = systemService.findListbySql(vioQualitativeSql);

		//是否存在罚款
		List<String> isFineList = new ArrayList<String>();
		isFineList.add("是");
		isFineList.add("否");

		//罚款性质
		String finePropertySql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='fineProperty')";
		List<String> finePropertyList = systemService.findListbySql(finePropertySql);

		dicListMap.put("shiftList", shiftList);
		dicListMap.put("addressList", addressList);
		dicListMap.put("unitList", unitList);
		dicListMap.put("manList", manList);
		dicListMap.put("vioCategoryList", vioCategoryList);
		dicListMap.put("vioLevelList", vioLevelList);
		dicListMap.put("vioQualitativeList", vioQualitativeList);
		dicListMap.put("isFineList", isFineList);
		dicListMap.put("finePropertyList", finePropertyList);
		List<TBThreeViolationsEntity> dicVOList = new ArrayList<TBThreeViolationsEntity>();

		int[] listLength = {dicListMap.get("shiftList").size(), dicListMap.get("addressList").size(), dicListMap.get("unitList").size(),
				dicListMap.get("manList").size(), dicListMap.get("vioCategoryList").size(), dicListMap.get("vioLevelList").size(),
				dicListMap.get("vioQualitativeList").size(), dicListMap.get("isFineList").size(), dicListMap.get("finePropertyList").size()};
		int maxLength = listLength[0];
		for (int i = 0; i < listLength.length; i++) {
			if (listLength[i] > maxLength) {
				maxLength = listLength[i]; }
		}

		for (int j=0; j<maxLength; j++) {
			TBThreeViolationsEntity tbThreeViolationsEntity = new TBThreeViolationsEntity();
			if (j < dicListMap.get("shiftList").size()) {
				tbThreeViolationsEntity.setShiftTemp(dicListMap.get("shiftList").get(j));
			}
			if (j < dicListMap.get("addressList").size()) {
				tbThreeViolationsEntity.setAddressTemp(dicListMap.get("addressList").get(j));
			}
			if (j < dicListMap.get("unitList").size()) {
				tbThreeViolationsEntity.setVioUnitesNameTemp(dicListMap.get("unitList").get(j));
			}
			if (j < dicListMap.get("manList").size()) {
				tbThreeViolationsEntity.setVioPeopleTemp(dicListMap.get("manList").get(j));
			}
			if (j < dicListMap.get("unitList").size()) {
				tbThreeViolationsEntity.setFindUnitsTemp(dicListMap.get("unitList").get(j));
			}
			if (j < dicListMap.get("manList").size()) {
				tbThreeViolationsEntity.setStopPeopleTemp(dicListMap.get("manList").get(j));
			}
			if (j < dicListMap.get("vioCategoryList").size()) {
				tbThreeViolationsEntity.setVioCategoryTemp(dicListMap.get("vioCategoryList").get(j));
			}
			if (j < dicListMap.get("vioLevelList").size()) {
				tbThreeViolationsEntity.setVioLevelTemp(dicListMap.get("vioLevelList").get(j));
			}
			if (j < dicListMap.get("vioQualitativeList").size()) {
				tbThreeViolationsEntity.setVioQualitativeTemp(dicListMap.get("vioQualitativeList").get(j));
			}
			if (j < dicListMap.get("isFineList").size()) {
				tbThreeViolationsEntity.setIsFineTemp(dicListMap.get("isFineList").get(j));
			}
			if (j < dicListMap.get("finePropertyList").size()) {
				tbThreeViolationsEntity.setFinePropertyTemp(dicListMap.get("finePropertyList").get(j));
			}
			dicVOList.add(tbThreeViolationsEntity);
		}

		//将字典赋值到param中，写到sheet2中
		param.put("dicVoList", dicVOList);
		modelMap.put(TemplateExcelConstants.PARAMS, templateExportParams);
		modelMap.put(TemplateExcelConstants.MAP_DATA, param);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBThreeViolationsEntity tBThreeViolations,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBThreeViolationsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBThreeViolations, request.getParameterMap());
		String queryHandleStatus = request.getParameter("queryHandleStatus");
		try{
			//自定义追加查询条件
			cq.add(Restrictions.sqlRestriction(" this_.VIO_ADDRESS not in (select id from t_b_address_info where isShowData = '0' )"));
			if(StringUtils.isNotBlank(queryHandleStatus)){
				cq.eq("reportStatus",queryHandleStatus);
			}
			cq = initSearchCondition(cq);
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
			String sunSql = "select column_id from t_b_sunshine where table_name='t_b_three_violations'";
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
		this.tBThreeViolationsService.getDataGridReturn(cq, true);
		if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
			List<TBThreeViolationsEntity> listTemp = dataGrid.getResults();
			for(TBThreeViolationsEntity t : listTemp) {
				CriteriaQuery cqFine = new CriteriaQuery(TBFineEntity.class, dataGrid);
				try{
					cqFine.add(Restrictions.sqlRestriction(" this_.id in (select fine_id from t_b_hidden_vio_fine_rel where vio_id = '"+t.getId()+"')"));
				}catch(Exception e){
					e.printStackTrace();
				}
				cq.add();
				List<TBFineEntity> list = this.systemService.getListByCriteriaQuery(cqFine, false);
				String fineMoney = "";
				for(TBFineEntity bean : list){
					if(fineMoney==""){
						fineMoney = fineMoney + bean.getFineMoney();
					}else{
						fineMoney = fineMoney + "," + bean.getFineMoney();
					}
				}
				t.setFineMoneyTemp(fineMoney);
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * 上报煤监局
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "reportVoilations")
	@ResponseBody
	public AjaxJson reportVoilations(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "上报成功";

		//上报煤监局
		Map<String,String> retMap = tBThreeViolationsService.reportViolation(ids,false);
		if(null==retMap || !Constants.LOCAL_RESULT_CODE_SUCCESS.equals(retMap.get("code"))){
			message = "上报失败";
			if(null!=retMap && retMap.containsKey("message")){
				message = retMap.get("message");
			}
		}

		j.setMsg(message);
		return j;
	}


	/**
	 * 上报到集团
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "reportVoilationsToGroup")
	@ResponseBody
	public AjaxJson reportVoilationsToGroup(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "上报成功";

		//上报到集团
		Map<String,String> retMap = tBThreeViolationsService.reportViolationToGroup(ids,false);
		if(null==retMap || !Constants.LOCAL_RESULT_CODE_SUCCESS.equals(retMap.get("code"))){
			message = "上报失败";
			if(null!=retMap && retMap.containsKey("message")){
				message = retMap.get("message");
			}
		}

		j.setMsg(message);
		return j;
	}


	/**
	 *
	 * @param cq
	 * @throws ParseException
	 */
	private CriteriaQuery initSearchCondition (CriteriaQuery cq) {
		String isMonthQuery	= ResourceUtil.getParameter("isMonthQuery");//查询方式:0=按起止时间;1=按月份
		String vioDate_begin	= null;//开始时间
		String vioDate_end	= null;//结束时间
		String queryMonth	= ResourceUtil.getParameter("queryMonth");//月份
		String shift	= ResourceUtil.getParameter("shift");//班次
		String vioCategory	= ResourceUtil.getParameter("vioCategory");//违章种类
		String vioQualitative	= ResourceUtil.getParameter("vioQualitative");//违章定性
		String vioLevel	= ResourceUtil.getParameter("vioLevel");//违章级别
		String checkOrgIds	= ResourceUtil.getParameter("checkOrgIds");//违章单位
		String checkManIds	= ResourceUtil.getParameter("checkManIds");//违章人员
		String dutyOrgIds	= ResourceUtil.getParameter("dutyOrgIds");//查处单位
		String dutyManIds	= ResourceUtil.getParameter("dutyManIds");//制止人
		String tBAddressInfoEntityId	= ResourceUtil.getParameter("tBAddressInfoEntityId");//违章地点
		if("1".equals(isMonthQuery) && StringUtil.isNotEmpty(queryMonth)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			try {
				Date queryDate = sdf.parse(queryMonth);
				Calendar ca = Calendar.getInstance();
				ca.setTime(queryDate);
				//获取当前月第一天
				ca.add(Calendar.MONTH, 0);
				ca.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				vioDate_begin = sdf.format(ca.getTime());
				//获取当前月最后一天
				ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
				vioDate_end= sdf.format(ca.getTime());
			}catch (Exception e){
			}
		} else {
			vioDate_begin	= ResourceUtil.getParameter("vioDate_begin");//开始时间
			vioDate_end	= ResourceUtil.getParameter("vioDate_end");//结束时间
		}
		if (StringUtil.isNotEmpty(vioDate_begin)){
			try {
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				cq.ge("vioDate", sdFormat.parse(vioDate_begin));
			}catch (Exception e){
			}
		}
		if (StringUtil.isNotEmpty(vioDate_end)){
			try {
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				cq.le("vioDate", sdFormat.parse(vioDate_end));
			}catch (Exception e){
			}
		}
		if(StringUtil.isNotEmpty(shift)){
			cq.eq("shift",shift);
		}
		if(StringUtil.isNotEmpty(vioCategory)){
			cq.eq("vioCategory",vioCategory);
		}
		if(StringUtil.isNotEmpty(vioQualitative)){
			cq.eq("vioQualitative",vioQualitative);
		}
		if(StringUtil.isNotEmpty(checkOrgIds)){
			cq.eq("vioUnits",checkOrgIds);
		}
		if(StringUtil.isNotEmpty(checkManIds)){
			cq.like("vioPeople","%"+checkManIds+"%");
		}
		if(StringUtil.isNotEmpty(dutyOrgIds)){
			cq.eq("findUnits",dutyOrgIds);
		}
		if(StringUtil.isNotEmpty(dutyManIds)){
			cq.like("stopPeople", "%"+dutyManIds+"%");
		}
		if(StringUtil.isNotEmpty(tBAddressInfoEntityId)){
			cq.eq("vioAddress" ,tBAddressInfoEntityId);
		}
		if(StringUtil.isNotEmpty(vioLevel)){
			cq.eq("vioLevel",vioLevel);
		}
		cq.add();
		return cq;
	}

	/**
	 * 删除三违信息
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBThreeViolationsEntity tBThreeViolations, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBThreeViolations = systemService.getEntity(TBThreeViolationsEntity.class, tBThreeViolations.getId());
		message = "三违信息删除成功";
		try{
			//国家局标识
			sfService.deleteSfVioRel(tBThreeViolations.getId());
			tBThreeViolationsService.delete(tBThreeViolations);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "三违信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除三违信息
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "三违信息删除成功";
		try{
			for(String id:ids.split(",")){
				TBThreeViolationsEntity tBThreeViolations = systemService.getEntity(TBThreeViolationsEntity.class,
						id
				);
				TBReportDeleteIdEntity reportDeleteIdEntity = new TBReportDeleteIdEntity();
				reportDeleteIdEntity.setDeleteId(id);
				reportDeleteIdEntity.setType("vio");
				systemService.save(reportDeleteIdEntity);
				//国家局标识
				sfService.deleteSfVioRel(tBThreeViolations.getId());
				tBThreeViolationsService.delete(tBThreeViolations);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

				//删除阳光账号列表的关联数据
				List<TBSunshineEntity> tbSunshineEntityList = systemService.findByProperty(TBSunshineEntity.class, "columnId", id);
				if (tbSunshineEntityList!=null && tbSunshineEntityList.size()>0){
					systemService.deleteAllEntitie(tbSunshineEntityList);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "三违信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加三违信息
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBThreeViolationsEntity tBThreeViolations, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "三违信息添加成功";
		try{
			tBThreeViolations.setReportStatus("0");

			tBThreeViolationsService.save(tBThreeViolations);
			//国家局标识  新增
			sfService.saveOrUpdateSfVioRel(tBThreeViolations.getId());
			String isFine = request.getParameter("isFine");
			if(StringUtil.isNotEmpty(isFine)){
				if(isFine.equals("1")){
					TBFineEntity tbFineEntity = new TBFineEntity();
					//生成罚款单号
					if(StringUtil.isEmpty(tbFineEntity.getFineNum())){
						String strNum = "";
						Date strDate = DateUtils.getDate();
						SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");

						List<TBFineEntity> tbFineEntityList = systemService.getList(TBFineEntity.class);
						strNum = strNum + tbFineEntityList.size()%1000;

						while(strNum.length() < 4){
							strNum = "0" + strNum;
						}
						String temp = DateUtils.date2Str(strDate,sdf);
						temp = temp.substring(2, temp.length());
						tbFineEntity.setFineNum(temp + strNum);
					}
					tbFineEntity.setIsValidity("0");
					tbFineEntity.setFineDate(tBThreeViolations.getVioDate());
					TSDepart tsDepart = systemService.getEntity(TSDepart.class,tBThreeViolations.getVioUnits());
					tbFineEntity.setDutyUnit(tsDepart);
					tbFineEntity.setFineMan(tBThreeViolations.getStopPeople());
					tbFineEntity.setBeFinedMan(tBThreeViolations.getVioPeople());
					String fineProperty = request.getParameter("fineProperty");
					tbFineEntity.setFineProperty(fineProperty);
					tbFineEntity.setContent(tBThreeViolations.getVioFactDesc());
					String fineMoney = request.getParameter("fineMoney");
					tbFineEntity.setFineMoney(fineMoney);
					systemService.save(tbFineEntity);
					String key = UUIDGenerator.generate();
					String sql = "insert into t_b_hidden_vio_fine_rel (id,fine_id,vio_id) values('"+key+"','"+tbFineEntity.getId()+"','"+tBThreeViolations.getId()+"')";
					systemService.executeSql(sql);
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "三违信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新三违信息
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBThreeViolationsEntity tBThreeViolations, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "三违信息更新成功";
		TBThreeViolationsEntity t = tBThreeViolationsService.get(TBThreeViolationsEntity.class, tBThreeViolations.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBThreeViolations, t);
			t.setReportStatus("0");
			//国家局标识
			sfService.saveOrUpdateSfVioRel(t.getId());
			tBThreeViolationsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "三违信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 三违信息新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBThreeViolationsEntity tBThreeViolations, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBThreeViolations.getId())) {
			tBThreeViolations = tBThreeViolationsService.getEntity(TBThreeViolationsEntity.class, tBThreeViolations.getId());
			req.setAttribute("tBThreeViolationsPage", tBThreeViolations);
		}
		String hid = req.getParameter("hid");
		if(StringUtil.isNotEmpty(hid)){
			if(hid.equals("1")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date examDate = null;
				try {
					examDate = sdf.parse(req.getParameter("examDate"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String shift=req.getParameter("shift");
				String dutyUnitId=req.getParameter("dutyUnitId");
				String dutyMan= null;
				try {
					dutyMan = new String(req.getParameter("dutyMan").getBytes("ISO8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String addressId=req.getParameter("address");
				req.setAttribute("hid",hid);
				req.setAttribute("examDate",examDate);
				req.setAttribute("shift",shift);
				req.setAttribute("dutyUnitId",dutyUnitId);
				req.setAttribute("dutyMan",dutyMan);
				req.setAttribute("address",addressId);
			}
		}
		String henghe = ResourceUtil.getConfigByName("henghe");
		if(StringUtil.isNotEmpty(henghe)){
			req.setAttribute("henghe",henghe);
		}
		String xinchazhuang = ResourceUtil.getConfigByName("xinchazhuang");
		if(StringUtil.isNotEmpty(xinchazhuang)){
			req.setAttribute("xinchazhuang",xinchazhuang);
		}

		return new ModelAndView("com/sdzk/buss/web/violations/tBThreeViolations-add");
	}
	/**
	 * 三违信息编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBThreeViolationsEntity tBThreeViolations, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBThreeViolations.getId())) {
			tBThreeViolations = tBThreeViolationsService.getEntity(TBThreeViolationsEntity.class, tBThreeViolations.getId());
			tBThreeViolations.setShiftTemp(DicUtil.getTypeNameByCode("workShift",tBThreeViolations.getShift()));
			tBThreeViolations.setVioCategoryTemp(DicUtil.getTypeNameByCode("violaterule_wzfl",tBThreeViolations.getVioCategory()));
			tBThreeViolations.setVioLevelTemp(DicUtil.getTypeNameByCode("vio_level",tBThreeViolations.getVioLevel()));
			tBThreeViolations.setVioQualitativeTemp(DicUtil.getTypeNameByCode("violaterule_wzdx",tBThreeViolations.getVioQualitative()));

			TBAddressInfoEntity va = tBAddressInfoService.getEntity(TBAddressInfoEntity.class,tBThreeViolations.getVioAddress());
			tBThreeViolations.setAddressTemp(va.getAddress());

			CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
			List<TSDepart> tempList = this.systemService.getListByCriteriaQuery(cq, false);
			for(TSDepart tsd : tempList){
				if(tsd.getId().equals(tBThreeViolations.getVioUnits())){
					tBThreeViolations.setVioUnitesNameTemp(tsd.getDepartname());
				}
				if(tsd.getId().equals(tBThreeViolations.getFindUnits())){
					tBThreeViolations.setFindUnitsTemp(tsd.getDepartname());
				}
			}

//            TSUser vp = userService.getEntity(TSUser.class,tBThreeViolations.getVioPeople());
//            if(vp != null){
//                tBThreeViolations.setVioPeopleTemp(vp.getRealName());
//            }
			tBThreeViolations.setVioPeopleTemp(tBThreeViolations.getVioPeople());

//            TSUser sp = userService.getEntity(TSUser.class,tBThreeViolations.getStopPeople());
//            if(sp != null){
//                tBThreeViolations.setStopPeopleTemp(sp.getRealName());
//            }
			tBThreeViolations.setStopPeopleTemp(tBThreeViolations.getStopPeople());
			req.setAttribute("tBThreeViolationsPage", tBThreeViolations);

			//获取MobileTBThreeViolationsEntity
			MobileTBThreeViolationsEntity mobileTBThreeViolationsEntity = systemService.getEntity(MobileTBThreeViolationsEntity.class, tBThreeViolations.getId());
			req.setAttribute("mobileTBThreeViolationsPage", mobileTBThreeViolationsEntity);
			/**获取隐患对应图片*/
			if(StringUtil.isNotEmpty(mobileTBThreeViolationsEntity.getMobileId())) {
				String imgSql = "select img_path from t_b_three_violations_img_rel where mobile_threevio_id='" + mobileTBThreeViolationsEntity.getMobileId() + "'";
				List<String> imglist = systemService.findListbySql(imgSql);
				if (!imglist.isEmpty() && imglist.size() > 0) {
					req.setAttribute("imagelists", imglist);
				}
			}
			String stopPeoples = "";
			String stopPeople = tBThreeViolations.getStopPeople();
			if(StringUtils.isNotBlank(stopPeople)){
				String[] stopPeopleArry = stopPeople.split(",");



				for(String stopPeopleName : stopPeopleArry){
					if(stopPeoples == ""){
						stopPeoples = stopPeoples + '"' + stopPeopleName + '"';
					}else{
						stopPeoples = stopPeoples + ',' + '"'+ stopPeopleName + '"';
					}
				}
				req.setAttribute("stopPeoples",stopPeoples);
			}
		}
		req.setAttribute("load", req.getParameter("load"));
		String load = req.getParameter("load");
		String henghe = ResourceUtil.getConfigByName("henghe");
		if(StringUtil.isNotEmpty(henghe)){
			req.setAttribute("henghe",henghe);
		}
		String xinchazhuang = ResourceUtil.getConfigByName("xinchazhuang");
		if(StringUtil.isNotEmpty(xinchazhuang)){
			req.setAttribute("xinchazhuang",xinchazhuang);
		}
		if ("detail".equals(load)) {
			return new ModelAndView("com/sdzk/buss/web/violations/tBThreeViolations-detail");
		}
		return new ModelAndView("com/sdzk/buss/web/violations/tBThreeViolations-update");
	}

	/**
	 * 三违图片跳转到大图展示
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "goLargerimage")
	public ModelAndView goLargerimage(HttpServletRequest req) {

		req.setAttribute("path", req.getParameter("path"));
		return new ModelAndView("com/sdzk/buss/web/violations/tBThreeVioLargerImage");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBThreeViolationsController");
		return new ModelAndView("common/upload/pub_excel_upload");
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
			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setNeedSave(true);
			params.setVerifyHanlder(new ThreeViolationsExcelVerifyHandler());
			try {
				ExcelImportResult<TBThreeViolationsEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),TBThreeViolationsEntity.class,params);
				if(result.isVerfiyFail()){
					String uploadpathtemp = ResourceUtil.getConfigByName("uploadpathtemp");
					String realPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/" + uploadpathtemp+"/";// 文件的硬盘真实路径
					File fileTemp = new File(realPath);
					if (!fileTemp.exists()) {
						fileTemp.mkdirs();// 创建根目录
					}
					String name = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+".xls";
					realPath+=name;
					FileOutputStream fos = new FileOutputStream(realPath);
					result.getWorkbook().write(fos);
					fos.close();
					Map<String, Object> attributes = new HashMap<String, Object>();
					attributes.put("path", uploadpathtemp+"/"+name);
					j.setAttributes(attributes);
					j.setSuccess(false);
					j.setMsg("导入数据校验失败");
				}else{
					for (TBThreeViolationsEntity bean : result.getList()) {
						//保存用户信息
						bean.setReportStatus("0");
						tBThreeViolationsService.save(bean);
						String isFine = bean.getIsFineTemp();
						if(StringUtil.isNotEmpty(isFine)){
							if(isFine.equals("1")){
								TBFineEntity tbFineEntity = new TBFineEntity();
								//生成罚款单号
								if(StringUtil.isEmpty(tbFineEntity.getFineNum())){
									String strNum = "";
									Date strDate = DateUtils.getDate();
									SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");

									List<TBFineEntity> tbFineEntityList = systemService.getList(TBFineEntity.class);
									strNum = strNum + tbFineEntityList.size()%1000;

									while(strNum.length() < 4){
										strNum = "0" + strNum;
									}
									String temp = DateUtils.date2Str(strDate,sdf);
									temp = temp.substring(2, temp.length());
									tbFineEntity.setFineNum(temp + strNum);
								}
								tbFineEntity.setIsValidity("0");
								tbFineEntity.setFineDate(bean.getVioDate());
								TSDepart tsDepart = systemService.getEntity(TSDepart.class,bean.getVioUnits());
								tbFineEntity.setDutyUnit(tsDepart);
								tbFineEntity.setFineMan(bean.getStopPeople());
								tbFineEntity.setBeFinedMan(bean.getVioPeople());
								String fineProperty = bean.getFinePropertyTemp();
								tbFineEntity.setFineProperty(fineProperty);
								tbFineEntity.setContent(bean.getVioFactDesc());
								String fineMoney = bean.getFineMoneyTemp();
								tbFineEntity.setFineMoney(fineMoney);
								systemService.save(tbFineEntity);
								String key = UUIDGenerator.generate();
								String sql = "insert into t_b_hidden_vio_fine_rel (id,fine_id,vio_id) values('"+key+"','"+tbFineEntity.getId()+"','"+bean.getId()+"')";
								systemService.executeSql(sql);
							}
						}
						//添加三违上报国家局标识
						sfService.saveOrUpdateSfVioRel(bean.getId());
					}
					j.setSuccess(true);
					j.setMsg("文件导入成功！");
				}
			} catch (Exception e) {
				j.setSuccess(false);
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
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBThreeViolationsEntity tBThreeViolations,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {

		CriteriaQuery cq = new CriteriaQuery(TBThreeViolationsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBThreeViolations, request.getParameterMap());

		List<TBThreeViolationsEntity> tBThreeViolationss=new ArrayList<>();
		String idTemp = request.getParameter("ids");
		if(StringUtils.isNotBlank(idTemp)&&idTemp!=null){
			List<String> idList = new ArrayList<>();
			for(String id : idTemp.split(",")){
				idList.add(id);
			}

			cq.in("id",idList.toArray());
			cq.add();

			tBThreeViolationss = this.tBThreeViolationsService.getListByCriteriaQuery(cq,false);
		}else {
			try {
				//自定义追加查询条件
				cq = initSearchCondition(cq);
				cq.addOrder("createDate", SortDirection.desc);
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			tBThreeViolationss = this.tBThreeViolationsService.getListByCriteriaQuery(cq,false);
		}
		Map<String, String> category = initDicMap("violaterule_wzfl", "违章分类");
		Map<String, String> qualitative = initDicMap("violaterule_wzdx", "违章定性");
		Map<String, String> shift = initDicMap("workShift", "班次");
		List<Map<String, Object>> departs = systemService.findForJdbc("select id, departname from t_s_depart");
		Map<String, String> departMap = new HashMap<>();
		if (departs != null && departs.size() > 0) {
			for (Map<String, Object> depart : departs) {
				departMap.put((String) depart.get("id"), (String) depart.get("departname"));
			}
		}
		List<Map<String, Object>> users = systemService.findForJdbc("select id, realname from t_s_base_user");
		Map<String, String> userMap = new HashMap<>();
		if (users != null && users.size() > 0) {
			for (Map<String, Object> user : users) {
				userMap.put((String) user.get("id"), (String) user.get("realname"));
			}
		}
		List<Map<String, Object>> addresses = systemService.findForJdbc("select id, address from t_b_address_info");
		Map<String, String> addressMap = new HashMap<>();
		if (addresses != null && addresses.size() > 0) {
			for (Map<String, Object> address : addresses) {
				addressMap.put((String) address.get("id"), (String) address.get("address"));
			}
		}


		if (tBThreeViolationss != null && tBThreeViolationss.size() > 0) {
			for (TBThreeViolationsEntity entity : tBThreeViolationss) {
				entity.setShiftTemp(shift.get(entity.getShift()));
				entity.setFindUnitsTemp(departMap.get(entity.getFindUnits()));
				entity.setAddressTemp(addressMap.get(entity.getVioAddress()));
				entity.setVioUnitesNameTemp(departMap.get(entity.getVioUnits()));
				entity.setVioPeopleTemp(entity.getVioPeople());
				entity.setEmployeeNumTemp(entity.getEmployeeNum());
				entity.setVioCategoryTemp(category.get(entity.getVioCategory()));
				entity.setVioQualitativeTemp(qualitative.get(entity.getVioQualitative()));
				entity.setStopPeopleTemp(entity.getStopPeople());
				String vioLevelCode = entity.getVioLevel();
				String vioLevelName = DicUtil.getTypeNameByCode("vio_level", vioLevelCode);
				entity.setVioLevelTemp(vioLevelName);
				CriteriaQuery cqFine = new CriteriaQuery(TBFineEntity.class, dataGrid);
				try{
					cqFine.add(Restrictions.sqlRestriction(" this_.id in (select fine_id from t_b_hidden_vio_fine_rel where vio_id = '"+entity.getId()+"')"));
				}catch(Exception e){
					e.printStackTrace();
				}
				cq.add();
				List<TBFineEntity> list = this.systemService.getListByCriteriaQuery(cqFine, false);
				String fineMoney = "";
				for(TBFineEntity bean : list){
					if(fineMoney==""){
						fineMoney = fineMoney + bean.getFineMoney();
					}else{
						fineMoney = fineMoney + "," + bean.getFineMoney();
					}
				}
				entity.setFineMoneyTemp(fineMoney);
			}
		}
		TemplateExportParams templateExportParams = new TemplateExportParams();
		templateExportParams.setSheetNum(0);
		templateExportParams.setTemplateUrl("export/template/exportTemp_threeViolations.xls");
		String xinchazhuang = ResourceUtil.getConfigByName("xinchazhuang");
		if(StringUtil.isNotEmpty(xinchazhuang)){
			if(xinchazhuang.equals("true")){
				templateExportParams.setTemplateUrl("export/template/exportTemp_threeViolationsXCZ.xls");
			}
		}
		String xintaoyang = ResourceUtil.getConfigByName("xintaoyang");
		if(StringUtil.isNotEmpty(xintaoyang)){
			if(xintaoyang.equals("true")){
				templateExportParams.setTemplateUrl("export/template/exportTemp_threeViolationsXTY.xls");
			}
		}
		String liangzhuang = ResourceUtil.getConfigByName("liangzhuang");
		if(StringUtil.isNotEmpty(liangzhuang)){
			if(liangzhuang.equals("true")){
				templateExportParams.setTemplateUrl("export/template/exportTemp_threeViolationsLZ.xls");
			}
		}
		modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
		Map<String,List<TBThreeViolationsEntity>> map = new HashMap<String,List<TBThreeViolationsEntity>>();
		map.put("list", tBThreeViolationss);
		modelMap.put(NormalExcelConstants.FILE_NAME,"三违信息列表");
		modelMap.put(TemplateExcelConstants.MAP_DATA,map);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}

	/**
	 * 初始化字典值
	 * @return
	 */
	private Map<String, String> initDicMap(String code, String name){
		Map<String, String> map =  new HashMap<>();
		TSTypegroup group = systemService.getTypeGroup(code,name);
		if (group != null && group.getTSTypes()!=null && group.getTSTypes().size()>0) {
			for (TSType type : group.getTSTypes()) {
				map.put(type.getTypecode(), type.getTypename());
			}
		}
		return map;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TBThreeViolationsEntity> list() {
		List<TBThreeViolationsEntity> listTBThreeViolationss=tBThreeViolationsService.getList(TBThreeViolationsEntity.class);
		return listTBThreeViolationss;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TBThreeViolationsEntity task = tBThreeViolationsService.get(TBThreeViolationsEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TBThreeViolationsEntity tBThreeViolations, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBThreeViolationsEntity>> failures = validator.validate(tBThreeViolations);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBThreeViolationsService.save(tBThreeViolations);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tBThreeViolations.getId();
		URI uri = uriBuilder.path("/rest/tBThreeViolationsController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TBThreeViolationsEntity tBThreeViolations) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBThreeViolationsEntity>> failures = validator.validate(tBThreeViolations);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBThreeViolationsService.saveOrUpdate(tBThreeViolations);
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
		tBThreeViolationsService.deleteEntityById(TBThreeViolationsEntity.class, id);
	}


	/**
	 * 三违分布统计
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "distributionStatisticsMine")
	public ModelAndView distributionStatisticsMine(HttpServletRequest request) {
		//查看各个矿的三违人次柱图

        /*String belongMine = (String) ContextHolderUtils.getSession().getAttribute("belongMine");
        String mineName = DicUtil.getTypeNameByCode("belongmine",belongMine);
        request.setAttribute("mineName",mineName);*/
		StringBuffer sb = new StringBuffer();
		sb.append("select vio_units,count(1) count from t_b_three_violations  vio ,t_b_address_info address where 1=1 and vio.vio_address = address.id and address.isShowData = '1' and VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ");


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
			sb.append(" and id not in (select column_id from t_b_sunshine)");
		}
		/*************************************************************/

		String isMonthQuery = request.getParameter("isMonthQuery");
		String startDate = "";
		String endDate = "";
		if(StringUtils.isNotBlank(isMonthQuery)){
			request.setAttribute("isMonthQuery",isMonthQuery);
			if(isMonthQuery.equals("1")){
				String queryMonth = request.getParameter("queryMonth");
				request.setAttribute("queryMonth",queryMonth);
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
						startDate = sdf.format(ca.getTime());
						//获取当前月最后一天
						ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
						endDate= sdf.format(ca.getTime());

					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}else{
				startDate = request.getParameter("startDate");
				endDate = request.getParameter("endDate");
			}


		}

		if(StringUtils.isNotBlank(startDate)) {
			request.setAttribute("startDate", startDate);
			sb.append(" and  vio_date >='").append(startDate).append("'");
		}
		if(StringUtils.isNotBlank(endDate)){
			request.setAttribute("endDate",endDate);
			sb.append(" and vio_date <= '").append(endDate).append("'");
		}

		//默认本月
		if(startDate.equals("") && endDate.equals("")){

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			try {
				Date queryDate = sdf.parse(sdf.format(new Date()));
				Calendar ca = Calendar.getInstance();
				ca.setTime(queryDate);
				//获取当前月第一天
				ca.add(Calendar.MONTH, 0);
				ca.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				startDate = sdf.format(ca.getTime());
				//获取当前月最后一天
				ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate= sdf.format(ca.getTime());


				request.setAttribute("startDate",startDate);
				request.setAttribute("endDate",endDate);
				request.setAttribute("isMonthQuery",0);
			}catch (Exception e){
				e.printStackTrace();
			}


			sb.append(" and vio_date >= '").append(startDate).append("' ");
			sb.append(" and vio_date <='").append(endDate).append("' ");
		}


		sb.append(" group by vio_units ");
		List<Map<String, Object>> subCountResult =  tBThreeViolationsService.findForJdbc(sb.toString());
		List<Map<String,String>> retMap= new ArrayList<Map<String, String>>();
		List<String> retKey = new ArrayList<String>();
		StringBuffer datasb = new StringBuffer();

		if(subCountResult != null ){
			for(Map<String, Object> map :subCountResult){
				String unit = String.valueOf(map.get("vio_units"));
				String count = String.valueOf(map.get("count"));

				TSDepart tsDepart = this.systemService.getEntity(TSDepart.class,unit);
				if(tsDepart != null){
					unit = tsDepart.getDepartname();
				}
				Map unitsMap = new HashMap<String,String>();
				retKey.add(unit);
				unitsMap.put("unit",unit);
				unitsMap.put("count",count);
				retMap.add(unitsMap);
				if(StringUtils.isNotBlank(datasb.toString())){
					datasb.append(",");
				}
				datasb.append(count) ;
			}
			request.setAttribute("datasb",datasb);
		}
		request.setAttribute("unitsName",retKey);
		request.setAttribute("retMap",retMap);
		return new ModelAndView("com/sdzk/buss/web/violations/distributionStatisticsMine");
	}

	/**
	 * 各矿三违时间统计
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "violationTimeStatisticsMine")
	public ModelAndView violationTimeStatisticsMine(HttpServletRequest request) {
		//算出最近12个月的时间   select * from t_b_three_violations where vio_date >=  str_to_date('2016-3','%Y-%m')
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String nowStr = sdf.format(date);
		String minMonth = "";
		Calendar calendar = Calendar.getInstance();
		List<String> retX = new ArrayList<String>();
		Map<String,Map<String,String>> retDatemap = new HashMap<String,Map<String, String>>();
		for(int i =11 ;i>0;i--){
			calendar.setTime(date);
			calendar.add(Calendar.MONTH ,0-i);
			Date passMonth = calendar.getTime();
			String passMonthStr = sdf.format(passMonth);
			if(i == 11){
				minMonth = passMonthStr;
			}
			retX.add(passMonthStr);
		}
		retX.add(nowStr);
		request.setAttribute("retX",retX);
		String belongMine = (String) ContextHolderUtils.getSession().getAttribute("belongMine");
		StringBuffer sb = new StringBuffer();
		sb.append("select date_format(vio_date,'%Y-%m') vio_date, count(1) count from t_b_three_violations vio ,t_b_address_info address where 1=1 and vio.vio_address = address.id and address.isShowData = '1'");

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
			sb.append(" and id not in (select column_id from t_b_sunshine)");
		}
		/*************************************************************/

		sb.append(" and vio_date >=  str_to_date('").append(minMonth).append("','%Y-%m')").append(" GROUP BY date_format(vio_date,'%Y-%m') ");
		List<Map<String, Object>> subCountResult =  tBThreeViolationsService.findForJdbc(sb.toString());

		List<Map<String,String>> retMap= new ArrayList<Map<String, String>>();

		List<String> retKey = new ArrayList<String>();
		StringBuffer datasb = new StringBuffer();
		if(subCountResult != null ){
			for(Map<String, Object> map :subCountResult){
				String unit = String.valueOf(map.get("vio_date"));
				String count = String.valueOf(map.get("count"));

				Map unitsMap = new HashMap<String,String>();
				retKey.add(unit);
				unitsMap.put("unit",unit);
				unitsMap.put("count",count);
				retMap.add(unitsMap);

				Map<String,String> belongMineMap = retDatemap.get(unit);
				if(belongMineMap == null){
					belongMineMap = new HashMap<String, String>();
					belongMineMap.put(unit,count);
					retDatemap.put(unit,belongMineMap);
				}
			}
			for(String sd : retX){
				Map<String,String> belongMineMap = retDatemap.get(sd);
				if(belongMineMap == null){
					if(StringUtils.isNotBlank(datasb.toString())){
						datasb.append(",");
					}
					datasb.append("0") ;
				}else{
					if(StringUtils.isNotBlank(datasb.toString())){
						datasb.append(",");
					}
					datasb.append(belongMineMap.get(sd)) ;
				}

			}
			request.setAttribute("datasb",datasb);
		}

		request.setAttribute("unitsName",retKey);
		request.setAttribute("retMap",retMap);

		return new ModelAndView("com/sdzk/buss/web/violations/violationTimeStatisticsMine");
	}

	/**
	 * 三违性质统计
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "qualitativeAnalysis")
	public ModelAndView qualitativeAnalysis(HttpServletRequest request) {
		TSTypegroup tsTypegroup=systemService.getTypeGroup("violaterule_wzdx","违章定性");
		List<TSType> typeList = tsTypegroup.getTSTypes();
		request.setAttribute("list",typeList);
		return new ModelAndView("com/sdzk/buss/web/violations/qualitativeAnalysis");
	}

	/**
	 * 三违性质统计（矿）
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "qualitativeAnalysisDatagrid")
	public void qualitativeAnalysisDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//String belongMine = (String) ContextHolderUtils.getSession().getAttribute("belongMine");
		TSTypegroup tsTypegroup=systemService.getTypeGroup("violaterule_wzdx","违章定性");

		String isMonthQuery = request.getParameter("isMonthQuery");
		request.setAttribute("isMonthQuery",isMonthQuery);
		String startDate = "";
		String endDate = "";
		if(StringUtils.isNotBlank(isMonthQuery)){
			if(isMonthQuery.equals("1")) {
				String queryMonth = request.getParameter("queryMonth");
				if (StringUtils.isNotBlank(queryMonth)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					try {
						Date queryDate = sdf.parse(queryMonth);
						Calendar ca = Calendar.getInstance();
						ca.setTime(queryDate);
						//获取当前月第一天
						ca.add(Calendar.MONTH, 0);
						ca.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						startDate = sdf.format(ca.getTime());
						//获取当前月最后一天
						ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
						endDate = sdf.format(ca.getTime());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else{
				startDate = request.getParameter("startDate");
				endDate = request.getParameter("endDate");
			}
		}
		List<TSType> typeList = tsTypegroup.getTSTypes();
		StringBuffer sb = new StringBuffer();
		//sb.append("select vio_units,vio_qualitative,count(1) count from t_b_three_violations where VIO_QUALITATIVE is not null and VIO_QUALITATIVE !='' and belong_mine = '").append(belongMine).append("' ");
		sb.append("select vio_units,vio_qualitative,count(1) count from t_b_three_violations vio ,t_b_address_info address where VIO_QUALITATIVE is not null and vio.vio_address = address.id and address.isShowData = '1' and VIO_QUALITATIVE !='' and VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ");
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
			sb.append(" and id not in (select column_id from t_b_sunshine)");
		}
		/*************************************************************/

		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and vio_date >= '").append(startDate).append("' ");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append(" and vio_date <='").append(endDate).append("' ");
		}
		if(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)){
			//默认本月
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			try {
				Date queryDate = sdf.parse(sdf.format(new Date()));
				Calendar ca = Calendar.getInstance();
				ca.setTime(queryDate);
				//获取当前月第一天
				ca.add(Calendar.MONTH, 0);
				ca.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				startDate = sdf.format(ca.getTime());
				//获取当前月最后一天
				ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate= sdf.format(ca.getTime());
			}catch (Exception e){
				e.printStackTrace();
			}
			sb.append(" and vio_date >= '").append(startDate).append("' ");
			sb.append(" and vio_date <='").append(endDate).append("' ");
		}
		sb.append(" group by vio_units,vio_qualitative");
		List<Map<String, Object>> subCountResult =  tBThreeViolationsService.findForJdbc(sb.toString());
		Map<String,Map<String,String>> retMap = new HashMap<String, Map<String, String>>();
		List<Map<String,String>> retList = new ArrayList<Map<String, String>>();
		List<String> retKey = new ArrayList<String>();
		if(subCountResult != null ){
			for(Map<String, Object> map :subCountResult){
				String units = String.valueOf(map.get("vio_units"));
				String vioQualitative = String.valueOf(map.get("vio_qualitative"));
				String count = String.valueOf(map.get("count"));
				Map<String,String> unitMap = retMap.get(units);
				if(unitMap == null){
					unitMap = new HashMap<String,String>();
					unitMap.put("unitCode", units);
					TSDepart tsDepart = this.systemService.getEntity(TSDepart.class,units);
					if(tsDepart != null){
						unitMap.put("units", tsDepart.getDepartname());
					}
					retKey.add(units);
					for(TSType ts : typeList){
						unitMap.put("vio_qualitative"+ts.getTypecode(),"0");
						retMap.put(units,unitMap);
					}
				}
				String countTempStr = unitMap.get("count");
				countTempStr = StringUtils.isBlank(countTempStr)?"0":countTempStr;
				int total = Integer.parseInt(countTempStr);
				int countTemp = Integer.parseInt(count);
				unitMap.put("count",(total+countTemp)+"");
				unitMap.put("vio_qualitative"+vioQualitative,count);
			}
			int currentPage = dataGrid.getPage();
			int pageSize = dataGrid.getRows();
			int endIndex = pageSize *currentPage;
			if(pageSize *currentPage > retKey.size()){
				endIndex = retKey.size();
			}
			List<String> tempList = retKey.subList(pageSize *(currentPage -1), endIndex);
			for(String key : tempList){
				Map<String,String> belongMineMap = retMap.get(key);
				StringBuffer data = new StringBuffer();
				int totalTemp = 0;
				for(TSType ts : typeList){
					String num = belongMineMap.get("vio_qualitative"+ts.getTypecode());

					if(StringUtils.isNotBlank(data.toString())){
						data.append(",");
					}
					data.append(num);
					totalTemp = totalTemp + Integer.parseInt(num);
				}
				data.append(",").append(totalTemp);
				belongMineMap.put("charData",data.toString());

				retList.add(belongMineMap);
			}
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", retKey == null?0:retKey.size());
		jsonObject.put("rows", retList);
		request.getSession().setAttribute("retList", retList);
		responseDatagrid(response, jsonObject);
	}

	@RequestMapping(params = "goDetailList")
	public ModelAndView goDetailList(HttpServletRequest request) {
		String unitCode = request.getParameter("unitCode");
		request.setAttribute("unitCode",unitCode);
		String isMonthQuery = request.getParameter("isMonthQuery");
		request.setAttribute("isMonthQuery",isMonthQuery);
		String queryMonth = request.getParameter("queryMonth");
		request.setAttribute("queryMonth",queryMonth);
		String startDate = request.getParameter("startDate");
		request.setAttribute("startDate",startDate);
		String endDate = request.getParameter("endDate");
		request.setAttribute("endDate",endDate);
		return new ModelAndView("com/sdzk/buss/web/violations/goDetailList");
	}

	public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		try {
			PrintWriter pw=response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@RequestMapping(params = "datagridfinal")
	public void datagridfinal(TBThreeViolationsEntity tBThreeViolations,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		CriteriaQuery cq = new CriteriaQuery(TBThreeViolationsEntity.class, dataGrid);

		//查询条件组装器
		String isMonthQuery = request.getParameter("isMonthQuery");

		String vioDateBegin="";
		String vioDateEnd="";

		String shift = request.getParameter("shift");//班次
		String vioCategory = request.getParameter("vioCategory");//违章分类
		String vioQualitative = request.getParameter("vioQualitative");//违章定性
		String checkOrgIds = request.getParameter("checkOrgIds");//违章单位
		String checkManIds = request.getParameter("checkManIds");//违章人员
		String dutyOrgIds = request.getParameter("dutyOrgIds");//查出单位
		String dutyManIds = request.getParameter("dutyManIds");//制止人
		String workType = request.getParameter("workType");//工种
		String vioAddressId = request.getParameter("tBAddressInfoEntity_id");//地点
		String vioLevel = request.getParameter("vioLevel");//三违级别
		String type = request.getParameter("type");

		try{
			//自定义追加查询条件
			if(StringUtils.isNotBlank(isMonthQuery) && isMonthQuery.equals("0")){
				// goDetailList isMonthQuery=0
				vioDateBegin = request.getParameter("vioDate_begin");
				vioDateEnd = request.getParameter("vioDate_end");
			}else{
				String vioMonth = "";
				if(StringUtils.isNotBlank(isMonthQuery) && isMonthQuery.equals("1")){
					// goDetailList isMonthQuery=1
					vioMonth = request.getParameter("queryMonth");//违章时间
				}else{
					// tBThreeViolationsList
					vioMonth = request.getParameter("vioDate_begin");//违章时间
				}
				if(StringUtils.isNotBlank(vioMonth)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					try {
						Date queryDate = sdf.parse(vioMonth);
						Calendar ca = Calendar.getInstance();
						ca.setTime(queryDate);
						//获取当前月第一天
						ca.add(Calendar.MONTH, 0);
						ca.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						vioDateBegin = sdf.format(ca.getTime());
						//获取当前月最后一天
						ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
						vioDateEnd= sdf.format(ca.getTime());
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}

			if(StringUtils.isNotBlank(vioDateBegin)){
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				cq.ge("vioDate", sdFormat.parse(vioDateBegin));
			}
			if(StringUtils.isNotBlank(vioDateEnd)){
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				cq.le("vioDate", sdFormat.parse(vioDateEnd));
			}
			if(StringUtils.isNotBlank(shift)){
				cq.eq("shift",shift);
			}
			if(StringUtils.isNotBlank(vioCategory)){
				cq.eq("vioCategory",vioCategory);
			}
			if(StringUtils.isNotBlank(vioQualitative)){
				cq.eq("vioQualitative",vioQualitative);
			}
			if(StringUtils.isNotBlank(checkOrgIds)){
				cq.in("vioUnits",checkOrgIds.split(","));
			}
			if(StringUtils.isNotBlank(checkManIds)){
//                cq.in("vioPeople",checkManIds.split(","));
				cq.like("vioPeople", "%" + checkManIds + "%");
			}
			if(StringUtils.isNotBlank(dutyOrgIds)){
				cq.in("findUnits",dutyOrgIds.split(","));
			}
			if(StringUtils.isNotBlank(dutyManIds)){
				cq.like("stopPeople", "%" + dutyManIds + "%");
			}
			if(StringUtils.isNotBlank(workType)){
				cq.eq("workType",workType);
			}
			if(StringUtils.isNotBlank(vioAddressId)){
				cq.eq("vioAddressId" ,vioAddressId);
			}
			if(StringUtils.isNotBlank(vioLevel)){
				cq.eq("vioLevel",vioLevel);
			}


		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tBThreeViolationsService.getDataGridReturn(cq, true);
		if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
			List<TBThreeViolationsEntity> listTemp = dataGrid.getResults();
			for(TBThreeViolationsEntity t : listTemp){
				TBAddressInfoEntity address = this.systemService.getEntity(TBAddressInfoEntity.class,t.getVioAddress());
				if(address != null){
					t.setAddressTemp(address.getAddress());
				}

                /*cq = new CriteriaQuery(TBFineInfoEntity.class);
                try{
                    cq.eq("businessId",t.getId());
                }catch(Exception e){
                    e.printStackTrace();;
                }
                cq.addOrder("createDate", SortDirection.desc);
                cq.add();
                List<TBFineInfoEntity> list = this.systemService.getListByCriteriaQuery(cq,false);
                if(list != null && !list.isEmpty()){
                    t.setFineMoneyTemp(list.get(0).getFineMoney());
                }else{
                    t.setFineMoneyTemp("0");
                }*/

//                String stopMan = t.getStopPeople();
				t.setStopPeopleTemp(t.getStopPeople());
//                if(StringUtils.isNotBlank(stopMan)){
//                    StringBuffer sb = new StringBuffer();
//                    String[] stopIds = stopMan.split(",");
//                    for(String id : stopIds){
//                        TSUser user = systemService.getEntity(TSUser.class,id);
//                        if(user != null){
//                            if(StringUtils.isNotBlank(sb.toString())){
//                                sb.append(",");
//                            }
//                            sb.append(user.getRealName());
//                        }
//                    }
//                    t.setStopPeopleTemp(sb.toString());
//                }
				String vioUnits = t.getVioUnits();
				if(StringUtils.isNotBlank(vioUnits)){
					StringBuffer sb = new StringBuffer();
					String [] unitIdArray = vioUnits.split(",");
					for(String id : unitIdArray){
						TSDepart tsDepart = this.systemService.getEntity(TSDepart.class,id);
						if(tsDepart != null){
							if(StringUtils.isNotBlank(sb.toString())){
								sb.append(",");
							}
							sb.append(tsDepart.getDepartname());
						}
					}
					t.setVioUnitesNameTemp(sb.toString());
				}
				String vioPeople = t.getVioPeople();
				t.setVioPeopleTemp(vioPeople);
//                if(StringUtils.isNotBlank(vioPeople)){
//                    String[] ids = vioPeople.split(",");
//                    StringBuffer sb = new StringBuffer();
//                    for(String id : ids){
//                        TSUser user = this.systemService.getEntity(TSUser.class,id);
//                        if(user != null ){
//                            if(StringUtils.isNotBlank(sb.toString())){
//                                sb.append(",");
//                            }
//                            sb.append(user.getRealName());
//                        }
//                    }
//                    t.setVioPeopleTemp(sb.toString());
//                }
				String findUnits = t.getFindUnits();
				if(StringUtils.isNotBlank(findUnits)){
					TSDepart tsDepart = systemService.getEntity(TSDepart.class,findUnits);
					if(tsDepart != null){
						t.setFindUnitsTemp(tsDepart.getDepartname());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 违章分类统计
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "vioCategoryAnalysis")
	public ModelAndView vioCategoryAnalysis(HttpServletRequest request) {
		TSTypegroup tsTypegroup=systemService.getTypeGroup("violaterule_wzfl","违章分类");
		List<TSType> typeList = tsTypegroup.getTSTypes();
		request.setAttribute("list",typeList);
		return new ModelAndView("com/sdzk/buss/web/violations/vioCategoryAnalysis");
	}

	/**
	 * 违章分类统计（矿）
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "vioCategoryAnalysisDatagrid")
	public void vioCategoryAnalysisDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		TSTypegroup tsTypegroup=systemService.getTypeGroup("violaterule_wzfl","违章分类");

		String isMonthQuery = request.getParameter("isMonthQuery");
		request.setAttribute("isMonthQuery",isMonthQuery);
		String startDate = "";
		String endDate = "";
		if(StringUtils.isNotBlank(isMonthQuery)){
			if(isMonthQuery.equals("1")) {
				String queryMonth = request.getParameter("queryMonth");
				if (StringUtils.isNotBlank(queryMonth)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					try {
						Date queryDate = sdf.parse(queryMonth);
						Calendar ca = Calendar.getInstance();
						ca.setTime(queryDate);
						//获取当前月第一天
						ca.add(Calendar.MONTH, 0);
						ca.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						startDate = sdf.format(ca.getTime());
						//获取当前月最后一天
						ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
						endDate = sdf.format(ca.getTime());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else{
				startDate = request.getParameter("startDate");
				endDate = request.getParameter("endDate");
			}
		}
		List<TSType> typeList = tsTypegroup.getTSTypes();
		StringBuffer sb = new StringBuffer();
		sb.append("select vio_units,vio_category,count(1) count from t_b_three_violations vio ,t_b_address_info address where VIO_CATEGORY is not null and vio.vio_address = address.id and address.isShowData = '1' and VIO_CATEGORY !='' and VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ");
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
			sb.append(" and id not in (select column_id from t_b_sunshine)");
		}
		/*************************************************************/

		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and vio_date >= '").append(startDate).append("' ");
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append(" and vio_date <='").append(endDate).append("' ");
		}
		if(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)){
			//默认本月
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			try {
				Date queryDate = sdf.parse(sdf.format(new Date()));
				Calendar ca = Calendar.getInstance();
				ca.setTime(queryDate);
				//获取当前月第一天
				ca.add(Calendar.MONTH, 0);
				ca.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				startDate = sdf.format(ca.getTime());
				//获取当前月最后一天
				ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate= sdf.format(ca.getTime());
			}catch (Exception e){
				e.printStackTrace();
			}
			sb.append(" and vio_date >= '").append(startDate).append("' ");
			sb.append(" and vio_date <='").append(endDate).append("' ");
		}
		sb.append(" group by vio_units,vio_category");
		List<Map<String, Object>> subCountResult =  tBThreeViolationsService.findForJdbc(sb.toString());
		Map<String,Map<String,String>> retMap = new HashMap<String, Map<String, String>>();
		List<Map<String,String>> retList = new ArrayList<Map<String, String>>();
		List<String> retKey = new ArrayList<String>();
		if(subCountResult != null ){
			for(Map<String, Object> map :subCountResult){
				String units = String.valueOf(map.get("vio_units"));
				String vioCategory = String.valueOf(map.get("vio_category"));
				String count = String.valueOf(map.get("count"));
				Map<String,String> unitMap = retMap.get(units);
				if(unitMap == null){
					unitMap = new HashMap<String,String>();
					unitMap.put("unitCode", units);
					TSDepart tsDepart = this.systemService.getEntity(TSDepart.class,units);
					if(tsDepart != null){
						unitMap.put("units", tsDepart.getDepartname());
					}
					retKey.add(units);
					for(TSType ts : typeList){
						unitMap.put("vio_category"+ts.getTypecode(),"0");
						retMap.put(units,unitMap);
					}
				}
				String countTempStr = unitMap.get("count");
				countTempStr = StringUtils.isBlank(countTempStr)?"0":countTempStr;
				int total = Integer.parseInt(countTempStr);
				int countTemp = Integer.parseInt(count);
				unitMap.put("count",(total+countTemp)+"");
				unitMap.put("vio_category"+vioCategory,count);
			}
			int currentPage = dataGrid.getPage();
			int pageSize = dataGrid.getRows();
			int endIndex = pageSize *currentPage;
			if(pageSize *currentPage > retKey.size()){
				endIndex = retKey.size();
			}
			List<String> tempList = retKey.subList(pageSize *(currentPage -1), endIndex);
			for(String key : tempList){
				Map<String,String> belongMineMap = retMap.get(key);
				StringBuffer data = new StringBuffer();
				int totalTemp = 0;
				for(TSType ts : typeList){
					String num = belongMineMap.get("vio_category"+ts.getTypecode());

					if(StringUtils.isNotBlank(data.toString())){
						data.append(",");
					}
					data.append(num);
					totalTemp = totalTemp + Integer.parseInt(num);
				}
				data.append(",").append(totalTemp);
				belongMineMap.put("charData",data.toString());

				retList.add(belongMineMap);
			}
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", retKey == null?0:retKey.size());
		jsonObject.put("rows", retList);
		request.getSession().setAttribute("retList", retList);
		responseDatagrid(response, jsonObject);
	}

	/**
	 * 三违信息上报
	 * 张赛超
	 * */
	@RequestMapping(params = "uploadThreeViolence")
	@ResponseBody
	public AjaxJson uploadThreeViolence(String ids, HttpServletRequest request){
		return uploadThreeViolence.uploadThreeViolence(ids);
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
					sunshineEntity.setTableName("t_b_three_violations");
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
