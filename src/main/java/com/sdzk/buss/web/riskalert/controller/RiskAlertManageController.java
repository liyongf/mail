package com.sdzk.buss.web.riskalert.controller;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.riskmanage.entity.RiskManageEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamVO;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.layer.entity.TBLayerEntity;
import com.sdzk.buss.web.mapmanage.service.TBMapManageServiceI;
import com.sdzk.buss.web.riskalert.entity.*;
import com.sdzk.buss.web.riskalert.service.TBAlertLevelSettingServiceI;
import com.sdzk.buss.web.system.entity.TBShiftManageEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsVO;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 风险预警管理
 * @author hansf
 * @date 2016-04-20
 * @version
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/riskAlertManageController")
public class RiskAlertManageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(RiskAlertManageController.class);

	@Autowired
	private TBMapManageServiceI tbMapManageService;
	@Autowired
	private TBAlertLevelSettingServiceI tBAlertLevelSettingService;
	@Autowired
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 关联风险列表跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addressDangerSourceList")
	public ModelAndView addressDangerSourceList(HttpServletRequest request) {
		request.setAttribute("addressId", ResourceUtil.getParameter("addressId"));
		return new ModelAndView("com/sdzk/buss/web/riskalert/addressDangerSourceList");
	}

	/**
	 * 隐患列表跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addressHiddenDangerList")
	public ModelAndView addressHiddenDangerList(HttpServletRequest request) {
		request.setAttribute("addressId", ResourceUtil.getParameter("addressId"));
		return new ModelAndView("com/sdzk/buss/web/riskalert/addressHiddenDangerList");
	}

	/**
	 * 井下安全风险列表 超图页面跳转 动态风险等级（多煤层）
	 *
	 * @return
	 */
	@RequestMapping(params = "goUndergroundSafetyRiskAlert_supermap_multiLayer_dynamic")
	public ModelAndView goUndergroundSafetyRiskAlert_supermap_multiLayer_dynamic(HttpServletRequest request) {

		CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class);
		cq.eq("isShow",Constants.IS_SHOW_Y);
		cq.add();
		List<TBLayerEntity> layerList = this.systemService.getListByCriteriaQuery(cq, false);
		request.setAttribute("layerList",layerList);
		if(null!=layerList&&layerList.size()>0){
			request.setAttribute("initLayerCode",layerList.get(0).getId());
		}
		return new ModelAndView("com/sdzk/buss/web/riskalert/undergroundSafetyRiskAlert_supermap_multiLayer_dynamic");
	}

	/**
	 * 重大隐患列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addressMajorHiddenList")
	public ModelAndView addressMajorHiddenList(HttpServletRequest request) {
		request.setAttribute("addressId", ResourceUtil.getParameter("addressId"));
		return new ModelAndView("com/sdzk/buss/web/riskalert/addressMajorHiddenList");
	}

	/**
	 * 三违信息列表跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addressViolationsList")
	public ModelAndView addressViolationsList(HttpServletRequest request) {
		request.setAttribute("addressId", ResourceUtil.getParameter("addressId"));
		return new ModelAndView("com/sdzk/buss/web/riskalert/addressViolationsList");
	}

	/**
	 * 隐患三违分区域预警列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "hiddenDangerViolist")
	public ModelAndView hiddenDangerViolist(HttpServletRequest request) {
        String type = request.getParameter("type");
        type = StringUtils.isNotBlank(type)?type:"viohidden";
        request.setAttribute("type",type);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try{
            String startTime = sdf.format(date);
            startTime = startTime + "-01";
            request.setAttribute("startTime",startTime);
        }catch(Exception e){
            e.printStackTrace();
        }
		/*return new ModelAndView("com/yk/buss/web/riskalert/hiddenDangerVioList");*/

		String mapPath = tbMapManageService.getCurrentMapPath();
		request.setAttribute("mapPath",mapPath.substring(1));
		return new ModelAndView("com/sdzk/buss/web/riskalert/hiddenDangerVioList_withLevel");
	}

	/**
	 * 隐患三违分区域预警列表 超图页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "hiddenDangerViolist_supermap")
	public ModelAndView hiddenDangerViolist_supermap(HttpServletRequest request) {
		String type = request.getParameter("type");
		type = StringUtils.isNotBlank(type)?type:"viohidden";
		request.setAttribute("type",type);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try{
			String startTime = sdf.format(date);
			startTime = startTime + "-01";
			request.setAttribute("startTime",startTime);
		}catch(Exception e){
			e.printStackTrace();
		}
		String supermapMineUrl = ResourceUtil.getConfigByName("supermapMineUrl");
		request.setAttribute("supermapMineUrl",supermapMineUrl);
		String supermapMineCenter = ResourceUtil.getConfigByName("supermapMineCenter");
		request.setAttribute("supermapMineCenter",supermapMineCenter);
		String supermapCountyUrl = ResourceUtil.getConfigByName("supermapCountyUrl");
		request.setAttribute("supermapCountyUrl",supermapCountyUrl);
		String supermapCountyCenter = ResourceUtil.getConfigByName("supermapCountyCenter");
		request.setAttribute("supermapCountyCenter",supermapCountyCenter);

		return new ModelAndView("com/sdzk/buss/web/riskalert/hiddenDangerVioList_supermap");
	}


	/**
	 * 隐患三违分区域预警列表 超图页面跳转（多图层）
	 *
	 * @return
	 */
	@RequestMapping(params = "hiddenDangerViolist_supermap_multiLayer")
	public ModelAndView hiddenDangerViolist_supermap_multiLayer(HttpServletRequest request) {
		String type = request.getParameter("type");
		type = StringUtils.isNotBlank(type)?type:"viohidden";
		request.setAttribute("type",type);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try{
			String startTime = sdf.format(date);
			startTime = startTime + "-01";
			request.setAttribute("startTime",startTime);
		}catch(Exception e){
			e.printStackTrace();
		}
//		String supermapMineUrl = ResourceUtil.getConfigByName("supermapMineUrl");
//		request.setAttribute("supermapMineUrl",supermapMineUrl);
//		String supermapMineCenter = ResourceUtil.getConfigByName("supermapMineCenter");
//		request.setAttribute("supermapMineCenter",supermapMineCenter);
//		String supermapCountyUrl = ResourceUtil.getConfigByName("supermapCountyUrl");
//		request.setAttribute("supermapCountyUrl",supermapCountyUrl);
//		String supermapCountyCenter = ResourceUtil.getConfigByName("supermapCountyCenter");
//		request.setAttribute("supermapCountyCenter",supermapCountyCenter);

		CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class);
		cq.eq("isShow",Constants.IS_SHOW_Y);
		cq.add();
		List<TBLayerEntity> layerList = this.systemService.getListByCriteriaQuery(cq, false);
		request.setAttribute("layerList",layerList);
		if(null!=layerList&&layerList.size()>0){
			request.setAttribute("initLayerCode",layerList.get(0).getId());
		}
		return new ModelAndView("com/sdzk/buss/web/riskalert/hiddenDangerVioList_supermap_multiLayer");
	}


	/**
	 * 井下安全风险预警列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUndergroundSafetyRiskAlert")
	public ModelAndView goUndergroundSafetyRiskAlert(HttpServletRequest request) {
		String mapPath = tbMapManageService.getCurrentMapPath();
		request.setAttribute("mapPath",mapPath.substring(1));

		return new ModelAndView("com/sdzk/buss/web/riskalert/undergroundSafetyRiskAlert");
	}

	/**
	 * 井下安全风险列表 超图页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUndergroundSafetyRiskAlert_supermap")
	public ModelAndView goUndergroundSafetyRiskAlert_supermap(HttpServletRequest request) {
		String supermapMineUrl = ResourceUtil.getConfigByName("supermapMineUrl");
		request.setAttribute("supermapMineUrl",supermapMineUrl);
		String supermapMineCenter = ResourceUtil.getConfigByName("supermapMineCenter");
		request.setAttribute("supermapMineCenter",supermapMineCenter);
		String supermapCountyUrl = ResourceUtil.getConfigByName("supermapCountyUrl");
		request.setAttribute("supermapCountyUrl",supermapCountyUrl);
		String supermapCountyCenter = ResourceUtil.getConfigByName("supermapCountyCenter");
		request.setAttribute("supermapCountyCenter",supermapCountyCenter);

		return new ModelAndView("com/sdzk/buss/web/riskalert/undergroundSafetyRiskAlert_supermap");
	}

	/**
	 * 井下安全风险列表 超图页面跳转（多煤层）
	 *
	 * @return
	 */
	@RequestMapping(params = "goUndergroundSafetyRiskAlert_supermap_multiLayer")
	public ModelAndView goUndergroundSafetyRiskAlert_supermap_multiLayer(HttpServletRequest request) {
//		String supermapMineUrl = ResourceUtil.getConfigByName("supermapMineUrl");
//		request.setAttribute("supermapMineUrl",supermapMineUrl);
//		String supermapMineCenter = ResourceUtil.getConfigByName("supermapMineCenter");
//		request.setAttribute("supermapMineCenter",supermapMineCenter);
//		String supermapCountyUrl = ResourceUtil.getConfigByName("supermapCountyUrl");
//		request.setAttribute("supermapCountyUrl",supermapCountyUrl);
//		String supermapCountyCenter = ResourceUtil.getConfigByName("supermapCountyCenter");
//		request.setAttribute("supermapCountyCenter",supermapCountyCenter);

		CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class);
		cq.eq("isShow",Constants.IS_SHOW_Y);
		cq.add();
		List<TBLayerEntity> layerList = this.systemService.getListByCriteriaQuery(cq, false);
		request.setAttribute("layerList",layerList);
		if(null!=layerList&&layerList.size()>0){
			request.setAttribute("initLayerCode",layerList.get(0).getId());
		}
		return new ModelAndView("com/sdzk/buss/web/riskalert/undergroundSafetyRiskAlert_supermap_multiLayer");
	}
	/**
	 * 隐患三违分区域预警列表查询
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "hiddenDangerVioDataGrid")
	public void hiddenDangerVioDataGrid(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {

		String queryDateBegin = request.getParameter("queryDate_begin");
	    String queryDateEnd = request.getParameter("queryDate_end");

		String viowhere = " where 1=1 and tv.vio_address in (select id from t_b_address_info where is_delete=0 and isShowData = '1')  and VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ";
		String hiddenwhere = " where 1=1 and hdh.handlel_status <> '"+Constants.HANDELSTATUS_DRAFT+"' and hde.address in (select id from t_b_address_info where is_delete=0 and isShowData = '1' ) ";

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
			viowhere += " and id not in (select column_id from t_b_sunshine)";
			hiddenwhere += " and id not in (select column_id from t_b_sunshine)";
		}
		/*************************************************************/

		if(StringUtil.isNotEmpty(queryDateBegin)) {
           viowhere = viowhere + " and Date(tv.vio_date)>='" + queryDateBegin + "'";
           hiddenwhere = hiddenwhere + " and Date(hde.exam_date)>='" + queryDateBegin + "'";

        }
        if(StringUtil.isNotEmpty(queryDateEnd)){
            viowhere = viowhere + " and Date(tv.vio_date)<='" + queryDateEnd + "'";
            hiddenwhere = hiddenwhere + " and Date(hde.exam_date)>='" + queryDateBegin + "'";
        }

        /**
         * tempwork
         * */

		String querySql = "select temp.units,sum(vionum) vionum,sum(hiddennum) hiddennum,sum(vionum+hiddennum) sumnum from ( "
				+ "select tv.vio_units units,count(1) vionum,0 hiddennum from t_b_three_violations tv  " + viowhere + " group by tv.vio_units "
				+ "union ALL "
				+ "select hde.duty_unit units,0 vionum,count(1) hiddennum from t_b_hidden_danger_exam hde join t_b_hidden_danger_handle hdh on hde.id= hdh.hidden_danger_id " + hiddenwhere + " group by hde.duty_unit) temp group by temp.units order by sumnum desc";
		List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);


		List<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();
		Map<String, Object> resmap = new HashMap<String,Object>();
		if(maplist!=null&&maplist.size()>0){
			//三违总数
			int vionum = 0;
			//问题总数
			int hiddennum = 0;
			for (Map map : maplist) {
				// 根据单位ID获取单位名称
				if(StringUtil.isNotEmpty(map.get("units"))){
					TSDepart depart = systemService.getEntity(TSDepart.class, map.get("units").toString());
					if(depart!=null){
						map.put("unitname", depart.getDepartname());
					}
				}
				vionum+=Integer.valueOf(map.get("vionum").toString());
				hiddennum+=Integer.valueOf(map.get("hiddennum").toString());
			}
			resmap.put("units", "allmine");
			resmap.put("unitname", "全矿");
			resmap.put("vionum", vionum);
			resmap.put("hiddennum", hiddennum);
			reslist.add(resmap);
			reslist.addAll(maplist);
		}
		
		dataGrid.setResults(reslist);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 查询某单位各地点的问题及三违数
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getHiddenDangerVioListByUnit")
	@ResponseBody
	public AjaxJson getHiddenDangerVioListByUnit(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> resMap = new HashMap<String, Object>();
		String queryDateBegin = req.getParameter("queryDate_begin");
	    String queryDateEnd = req.getParameter("queryDate_end");
		String unitId = req.getParameter("unitId");
		String type = req.getParameter("type");
        String viowhere = " where 1=1 and tv.vio_address in (select id from t_b_address_info where is_delete=0) and tv.VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ";
        String hiddenwhere = " where 1=1 and hde.address in (select id from t_b_address_info where is_delete=0)  ";

	    if(StringUtil.isNotEmpty(queryDateBegin)) {
	    	viowhere=viowhere+ " and tv.vio_date >= '"+queryDateBegin+"' ";
	    	hiddenwhere=hiddenwhere+ " and hde.exam_date >= '"+queryDateBegin+"' ";
        }
        if(StringUtil.isNotEmpty(queryDateEnd)){
        	viowhere=viowhere+ " and tv.vio_date <= '"+queryDateEnd+"' ";
        	hiddenwhere=hiddenwhere+ " and hde.exam_date <= '"+queryDateEnd+"' ";
        }
        if(StringUtil.isNotEmpty(unitId)&&!unitId.trim().equals("allmine")){
        	viowhere=viowhere+ " and tv.vio_units = '"+unitId+"' ";
        	hiddenwhere=hiddenwhere+ " and hde.duty_unit = '"+unitId+"' ";
        }
		String querySql = "select temp.addressid,sum(vionum) vionum,sum(hiddennum) hiddennum,sum(vionum+hiddennum) sumnum from ( "
				+ "select tv.vio_address addressid,count(1) vionum,0 hiddennum from t_b_three_violations tv "+viowhere+" GROUP BY tv.vio_address "
				+ "union ALL "
				+ "select hde.address addressid,0 vionum,count(1) hiddennum from t_b_hidden_danger_exam hde "+hiddenwhere+" GROUP BY hde.address) temp group by temp.addressid";
		List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);
        List<Map<String,Object>> retMapList = new ArrayList<Map<String, Object>>();

		for (Map map : maplist) {
			// 根据地址ID获取地址信息
			if(StringUtil.isNotEmpty(map.get("addressid"))){
				TBAddressInfoEntity address = systemService.getEntity(TBAddressInfoEntity.class, map.get("addressid").toString());
				if(address!=null){
					map.put("addressName", address.getAddress());
					map.put("lon", address.getLon());
					map.put("lat", address.getLat());
				}
				//根据隐患三违个数获取预警等级
				if(StringUtils.isNotBlank(type) && "vio".equals(type)){
					//三违
					if(StringUtil.isNotEmpty(map.get("vionum"))){
						TBAlertLevelSettingEntity tBAlertLevelSettingEntity = null;
						if(unitId.trim().equals("allmine")){//矿井阀值
							tBAlertLevelSettingEntity= getAlertLevel(Constants.ALERT_MANAGE_UNIT_TYPE_1,Constants.ALERT_MANAGE_ALERT_INDEX_1,Integer.valueOf(map.get("vionum").toString()));
						}else{//单位阀值
							tBAlertLevelSettingEntity= getAlertLevel(Constants.ALERT_MANAGE_UNIT_TYPE_2,Constants.ALERT_MANAGE_ALERT_INDEX_1,Integer.valueOf(map.get("vionum").toString()));
						}

						if(tBAlertLevelSettingEntity != null){
							map.put("alertlevelName", tBAlertLevelSettingEntity.getAlertLevelName());
							map.put("alertLevelColor", tBAlertLevelSettingEntity.getAlertLevelColor());
						}
					}
				}else if(StringUtils.isNotBlank(type) && "hidden".equals(type)){
					//隐患
					if(StringUtil.isNotEmpty(map.get("hiddennum"))){
						TBAlertLevelSettingEntity tBAlertLevelSettingEntity = null;
						if(unitId.trim().equals("allmine")){//矿井阀值
							tBAlertLevelSettingEntity= getAlertLevel(Constants.ALERT_MANAGE_UNIT_TYPE_1,Constants.ALERT_MANAGE_ALERT_INDEX_1,Integer.valueOf(map.get("hiddennum").toString()));
						}else{//单位阀值
							tBAlertLevelSettingEntity= getAlertLevel(Constants.ALERT_MANAGE_UNIT_TYPE_2,Constants.ALERT_MANAGE_ALERT_INDEX_1,Integer.valueOf(map.get("hiddennum").toString()));
						}

						if(tBAlertLevelSettingEntity!=null){
							map.put("alertlevelName", tBAlertLevelSettingEntity.getAlertLevelName());
							map.put("alertLevelColor", tBAlertLevelSettingEntity.getAlertLevelColor());
						}
					}
				}else{
					continue;
				}

				if(address != null){
					if(!Constants.IS_SHOW_N.equals(address.getIsshow())){
						retMapList.add(map);
					}
				}

			}
		}
		resMap.put("resultList", retMapList);
		j.setAttributes(resMap);
		return j;
	}

	/**
	 * 查询某单位各地点的问题及三违数
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getHiddenDangerVioListByUnitAndLevel")
	@ResponseBody
	public AjaxJson getHiddenDangerVioListByUnitAndLevel(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> resMap = new HashMap<String, Object>();
		String queryDateBegin = req.getParameter("queryDate_begin");
		String queryDateEnd = req.getParameter("queryDate_end");
		String unitId = req.getParameter("unitId");
		String type = req.getParameter("type");

        String viowhere = " where 1=1 and tv.vio_address in (select id from t_b_address_info where is_delete=0) and tv.VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ";
        String hiddenwhere = " where 1=1 and hde.address in (select id from t_b_address_info where is_delete=0) and hde.hidden_nature in ('2','3','4')   ";
		if(StringUtil.isNotEmpty(queryDateBegin)) {
			viowhere=viowhere+ " and tv.vio_date >= '"+queryDateBegin+"' ";
			hiddenwhere=hiddenwhere+ " and hde.exam_date >= '"+queryDateBegin+"' ";
		}
		if(StringUtil.isNotEmpty(queryDateEnd)){
			viowhere=viowhere+ " and tv.vio_date <= '"+queryDateEnd+"' ";
			hiddenwhere=hiddenwhere+ " and hde.exam_date <= '"+queryDateEnd+"' ";
		}
		if(StringUtil.isNotEmpty(unitId)&&!unitId.trim().equals("allmine")){
			viowhere=viowhere+ " and tv.vio_units = '"+unitId+"' ";
			hiddenwhere=hiddenwhere+ " and hde.duty_unit = '"+unitId+"' ";
		}

		List<Map<String,Object>> retMapList = new ArrayList<Map<String, Object>>();

		/*CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
		cq.eq("isDelete","0");
		cq.add();
		List<TBAddressInfoEntity> addressList = systemService.getListByCriteriaQuery(cq,false);

		if(!addressList.isEmpty() && addressList.size()>0){
			for(TBAddressInfoEntity addressInfoEntity : addressList){
				String querySql = "select case tv.vio_level when '1' then 'A' when '2' then 'B' when '3' then 'C' when '4' then 'D' when '5' then 'E' end as alert_level,count(1) vionum,0 hiddennum from t_b_three_violations tv " + viowhere + " and tv.vio_address='" + addressInfoEntity.getId() + "' GROUP BY tv.vio_level "
						+ "union ALL "
						+ "select ( CASE hde.hidden_nature WHEN '2' THEN 'A' WHEN '3' THEN 'B' WHEN '4' THEN 'C' WHEN '5' THEN 'D' WHEN '6' THEN 'E' END ) AS alert_level,"
						+ "0 vionum,count(1) hiddennum from t_b_hidden_danger_exam hde " + hiddenwhere +" and hde.address='" + addressInfoEntity.getId() + "' GROUP BY hde.hidden_nature";
				List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);

				Map tempMap = new HashMap();
				tempMap.put("addressId", addressInfoEntity.getId());
				tempMap.put("addressName", addressInfoEntity.getAddress());
				tempMap.put("lon", addressInfoEntity.getLon());
				tempMap.put("lat", addressInfoEntity.getLat());
				tempMap.put("pointStr", addressInfoEntity.getPointStr());
				tempMap.put("belongLayer", addressInfoEntity.getBelongLayer());

				int sumall = 0;
				int sumDE_vio = 0;
				int sumDE_hidden = 0;
				for (Map map : maplist) {
					String alertLevel = (String)map.get("alert_level");
					if (StringUtil.isNotEmpty(alertLevel)) {
						if(alertLevel.equals("D") || alertLevel.equals("E")){
							alertLevel = "DE";
						}
						if(alertLevel.equals("DE")){
							sumDE_vio = sumDE_vio + Integer.valueOf(map.get("vionum").toString());
							sumDE_hidden = sumDE_hidden + Integer.valueOf(map.get("hiddennum").toString());
							sumall = sumall + sumDE_vio;
							sumall = sumall + sumDE_hidden;
						}else{
							if (StringUtil.isNotEmpty(map.get("vionum"))) {
								tempMap.put("vionum_"+alertLevel, map.get("vionum").toString());
								sumall = sumall + Integer.valueOf(map.get("vionum").toString());
							}
							if (StringUtil.isNotEmpty(map.get("hiddennum"))) {
								tempMap.put("hiddennum_"+alertLevel, map.get("hiddennum").toString());
								sumall = sumall + Integer.valueOf(map.get("hiddennum").toString());
							}
						}
					}
				}
				tempMap.put("vionum_DE", sumDE_vio);
				tempMap.put("hiddennum_DE", sumDE_hidden);

				if (!Constants.IS_SHOW_N.equals(addressInfoEntity.getIsshow()) && sumall != 0) {
					retMapList.add(tempMap);
				}
			}
		}*/

		String querySql = "select case tv.vio_level when '1' then 'A' when '2' then 'B' when '3' then 'C' when '4' then 'D' when '5' then 'E' end as alert_level,count(1) vionum,0 hiddennum,tv.vio_address addressId from t_b_three_violations tv " + viowhere + " GROUP BY tv.vio_level,tv.vio_address "
				+ "union ALL "
				+ "select ( CASE hde.hidden_nature WHEN '2' THEN 'A' WHEN '3' THEN 'B' WHEN '4' THEN 'C' WHEN '5' THEN 'D' WHEN '6' THEN 'E' END ) AS alert_level,"
				+ "0 vionum,count(1) hiddennum,hde.address from t_b_hidden_danger_exam hde " + hiddenwhere +"  GROUP BY hde.hidden_nature,hde.address";
		List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);



		for (Map map : maplist) {
			int sumall = 0;
			int sumDE_vio = 0;
			int sumDE_hidden = 0;
			Map tempMap = new HashMap();
			String addressId = (String)map.get("addressId");
			boolean addressIdIsExist = false;
			if(retMapList!=null&&retMapList.size()>0){
				for (Map retMap : retMapList) {
					String addressIdTemp = (String)retMap.get("addressId");
					if(addressId.equals(addressIdTemp)){
						addressIdIsExist = true;
						String alertLevel = (String)map.get("alert_level");
						if (StringUtil.isNotEmpty(alertLevel)) {
							if(alertLevel.equals("D") || alertLevel.equals("E")){
								alertLevel = "DE";
							}
							if(alertLevel.equals("DE")){
								sumDE_vio = sumDE_vio + Integer.valueOf(map.get("vionum").toString());
								sumDE_hidden = sumDE_hidden + Integer.valueOf(map.get("hiddennum").toString());
								sumall = sumall + sumDE_vio;
								sumall = sumall + sumDE_hidden;
							}else{
								if (StringUtil.isNotEmpty(map.get("vionum"))) {
									retMap.put("vionum_"+alertLevel, map.get("vionum").toString());
									sumall = sumall + Integer.valueOf(map.get("vionum").toString());
								}
								if (StringUtil.isNotEmpty(map.get("hiddennum"))) {
									retMap.put("hiddennum_"+alertLevel, map.get("hiddennum").toString());
									sumall = sumall + Integer.valueOf(map.get("hiddennum").toString());
								}
							}
						}
						retMap.put("vionum_DE", sumDE_vio);
						retMap.put("hiddennum_DE", sumDE_hidden);
					}
				}
				if(!addressIdIsExist){
					TBAddressInfoEntity addressInfoEntity = systemService.get(TBAddressInfoEntity.class,addressId);
					tempMap.put("addressId", addressInfoEntity.getId());
					tempMap.put("addressName", addressInfoEntity.getAddress());
					tempMap.put("lon", addressInfoEntity.getLon());
					tempMap.put("lat", addressInfoEntity.getLat());
					tempMap.put("pointStr", addressInfoEntity.getPointStr());
					tempMap.put("belongLayer", addressInfoEntity.getBelongLayer());
					String alertLevel = (String)map.get("alert_level");
					if (StringUtil.isNotEmpty(alertLevel)) {
						if(alertLevel.equals("D") || alertLevel.equals("E")){
							alertLevel = "DE";
						}
						if(alertLevel.equals("DE")){
							sumDE_vio = sumDE_vio + Integer.valueOf(map.get("vionum").toString());
							sumDE_hidden = sumDE_hidden + Integer.valueOf(map.get("hiddennum").toString());
							sumall = sumall + sumDE_vio;
							sumall = sumall + sumDE_hidden;
						}else{
							if (StringUtil.isNotEmpty(map.get("vionum"))) {
								tempMap.put("vionum_"+alertLevel, map.get("vionum").toString());
								sumall = sumall + Integer.valueOf(map.get("vionum").toString());
							}
							if (StringUtil.isNotEmpty(map.get("hiddennum"))) {
								tempMap.put("hiddennum_"+alertLevel, map.get("hiddennum").toString());
								sumall = sumall + Integer.valueOf(map.get("hiddennum").toString());
							}
						}
					}
					tempMap.put("vionum_DE", sumDE_vio);
					tempMap.put("hiddennum_DE", sumDE_hidden);
					if (!Constants.IS_SHOW_N.equals(addressInfoEntity.getIsshow()) && sumall != 0) {
						retMapList.add(tempMap);
					}
				}
			}else{
				TBAddressInfoEntity addressInfoEntity = systemService.get(TBAddressInfoEntity.class,addressId);
				tempMap.put("addressId", addressInfoEntity.getId());
				tempMap.put("addressName", addressInfoEntity.getAddress());
				tempMap.put("lon", addressInfoEntity.getLon());
				tempMap.put("lat", addressInfoEntity.getLat());
				tempMap.put("pointStr", addressInfoEntity.getPointStr());
				tempMap.put("belongLayer", addressInfoEntity.getBelongLayer());
				String alertLevel = (String)map.get("alert_level");
				if (StringUtil.isNotEmpty(alertLevel)) {
					if(alertLevel.equals("D") || alertLevel.equals("E")){
						alertLevel = "DE";
					}
					if(alertLevel.equals("DE")){
						sumDE_vio = sumDE_vio + Integer.valueOf(map.get("vionum").toString());
						sumDE_hidden = sumDE_hidden + Integer.valueOf(map.get("hiddennum").toString());
						sumall = sumall + sumDE_vio;
						sumall = sumall + sumDE_hidden;
					}else{
						if (StringUtil.isNotEmpty(map.get("vionum"))) {
							tempMap.put("vionum_"+alertLevel, map.get("vionum").toString());
							sumall = sumall + Integer.valueOf(map.get("vionum").toString());
						}
						if (StringUtil.isNotEmpty(map.get("hiddennum"))) {
							tempMap.put("hiddennum_"+alertLevel, map.get("hiddennum").toString());
							sumall = sumall + Integer.valueOf(map.get("hiddennum").toString());
						}
					}
				}
				tempMap.put("vionum_DE", sumDE_vio);
				tempMap.put("hiddennum_DE", sumDE_hidden);
				if (!Constants.IS_SHOW_N.equals(addressInfoEntity.getIsshow()) && sumall != 0) {
					retMapList.add(tempMap);
				}
			}

		}

		resMap.put("resultList", retMapList);
		j.setAttributes(resMap);
		return j;
	}
	
	/**
	 * 根据单位名称、预警指标、问题数获取预警等级
	 * @return
	 */
	private TBAlertLevelSettingEntity getAlertLevel(String unitType,String alertIndex,Integer issuenum){
		TBAlertLevelSettingEntity tBAlertLevelSettingEntity = null;
		CriteriaQuery cq = new CriteriaQuery(TBAlertLevelSettingEntity.class);
		try{
		//自定义追加查询条件
		String belongMine = (String) ContextHolderUtils.getSession()
				.getAttribute("belongMine");
		cq.eq("unitType", unitType);
		cq.eq("alertIndex", alertIndex);
		cq.le("beginThreshold", issuenum);
		cq.ge("endThreshold", issuenum);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		List<TBAlertLevelSettingEntity> list = tBAlertLevelSettingService.getListByCriteriaQuery(cq, false);
		if(list!=null&&list.size()>0){
			tBAlertLevelSettingEntity = list.get(0);
		}
		return tBAlertLevelSettingEntity;
	}

	/**
	 * 根据单位名称、预警指标、问题数获取预警等级
	 * @param //num
	 * @return
	 */
	private TBAlertLevelColorEntity getAlertLevelColorEntity(String alertLevelName){
		TBAlertLevelColorEntity tbAlertLevelColorEntity = null;
		CriteriaQuery cq = new CriteriaQuery(TBAlertLevelColorEntity.class);
		try{
			//自定义追加查询条件
			String belongMine = (String) ContextHolderUtils.getSession().getAttribute("belongMine");
			cq.eq("alertLevelName", alertLevelName);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		List<TBAlertLevelColorEntity> list = tBAlertLevelSettingService.getListByCriteriaQuery(cq, false);
		if(list!=null&&list.size()>0){
			tbAlertLevelColorEntity = list.get(0);
		}
		return tbAlertLevelColorEntity;
	}
	
	/**
	 * 查看三违信息列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "threeVioList")
	public ModelAndView threeVioList(HttpServletRequest request) {
		 String vioDateBegin = request.getParameter("queryDate_begin");//违章起始时间
	     String vioDateEnd = request.getParameter("queryDate_end");//违章截止时间
	     String vioAddressId = request.getParameter("vioAddressId");//地点
	     String vioUnits = request.getParameter("vioUnits");//违章单位
		 String vioLevel = request.getParameter("vioLevel");



	     Map<String,Object> paraMap = new HashMap<String,Object>();
	     paraMap.put("vioDate_begin", vioDateBegin);
	     paraMap.put("vioDate_end", vioDateEnd);
	     paraMap.put("vioAddressId", vioAddressId);
	     paraMap.put("vioUnits", vioUnits);
		 paraMap.put("vioLevel",vioLevel);
		return new ModelAndView("com/sdzk/buss/web/riskalert/threeViolationsList",paraMap);
	}
	/**
	 * 三违列表请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "threeViodatagrid")
	public void threeViodatagrid(TBThreeViolationsEntity tBThreeViolations,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBThreeViolationsEntity.class, dataGrid);

		//查询条件组装器
        String vioDateBegin = request.getParameter("vioDate_begin");//违章起始时间
        String vioDateEnd = request.getParameter("vioDate_end");//违章截止时间
        String vioAddressId = request.getParameter("vioAddressId");//地点
        String queryvioUnits = request.getParameter("vioUnits");//违章单位
		String vioLevel = request.getParameter("vioLevel");
		try{
		//自定义追加查询条件
            if(StringUtils.isNotBlank(vioDateBegin)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("vioDate", sdf.parse(vioDateBegin));
            }
            if(StringUtils.isNotBlank(vioDateEnd)){
                cq.le("vioDate", DateUtils.datetimeFormat.parse(vioDateEnd+" 23:23:59"));
            }
            if(StringUtils.isNotBlank(vioAddressId)){
                cq.eq("vioAddress" ,vioAddressId);
            }
            if(StringUtils.isNotBlank(queryvioUnits)&&!queryvioUnits.trim().equals("allmine")){
                cq.eq("vioUnits" ,queryvioUnits);
            }
			if(StringUtils.isNotBlank(vioLevel)){
				if(vioLevel.indexOf(",") > -1){
					cq.in("vioLevel",vioLevel.split(","));
				}else{
					cq.eq("vioLevel",vioLevel);
				}
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBThreeViolationsEntity> listTemp = dataGrid.getResults();
            for(TBThreeViolationsEntity t : listTemp){
                TBAddressInfoEntity address = this.systemService.getEntity(TBAddressInfoEntity.class, t.getVioAddress());
                if(address != null){
                    t.setAddressTemp(address.getAddress());
                }
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
            }
        }
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 查看隐患问题列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "hiddenDangerList")
	public ModelAndView hiddenDangerList(HttpServletRequest request) {
		 String queryDateBegin = request.getParameter("queryDate_begin");//违章起始时间
	     String queryDateEnd = request.getParameter("queryDate_end");//违章截止时间
	     String addressId = request.getParameter("addressId");//地点
	     String dutyunit = request.getParameter("dutyunit");//违章单位
	     String dangerId = request.getParameter("danger_id");//危险源ID
		 String hiddenNature = request.getParameter("hiddenNature");
	     Map<String,Object> paraMap = new HashMap<String,Object>();
	     paraMap.put("queryDate_begin", queryDateBegin);
	     paraMap.put("queryDate_end", queryDateEnd);
	     paraMap.put("addressId", addressId);
	     paraMap.put("dutyunit", dutyunit);
	     paraMap.put("danger_id", dangerId);
		 paraMap.put("hiddenNature",hiddenNature);
		return new ModelAndView("com/sdzk/buss/web/riskalert/hiddenDangerList",paraMap);
	}
	/**
	 * 隐患列表datagrid
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "hiddenDangerdatagrid")
	public void hiddenDangerdatagrid(TBHiddenDangerExamEntity tBHiddenDangerExam,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//检查类型
		String queryDateBegin = request.getParameter("queryDate_begin");//违章起始时间
	    String queryDateEnd = request.getParameter("queryDate_end");//违章截止时间
	    String addressId = request.getParameter("addressId");//地点
		String dutyunit = request.getParameter("dutyunit");//违章单位
		String dangerId = request.getParameter("danger_id");//危险源ID
		String queryDate = request.getParameter("queryDate");//用于现场检查回放
		String fillCardManId = request.getParameter("fillCardManId");//填卡人 用于现场检查回放
		String hiddenNature = request.getParameter("hiddenNature");
		CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class, dataGrid);

		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBHiddenDangerExam, request.getParameterMap());

		try{
			//自定义追加查询条件
			if(StringUtils.isNotBlank(queryDateBegin)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("examDate", sdf.parse(queryDateBegin));
            }
            if(StringUtils.isNotBlank(queryDateEnd)){
                cq.le("examDate", DateUtils.datetimeFormat.parse(queryDateEnd+" 23:23:59"));
            }
            if(StringUtils.isNotBlank(addressId)){
                cq.eq("address.id" ,addressId);
            }
            if(StringUtils.isNotBlank(dutyunit)&&!dutyunit.trim().equals("allmine")){
                cq.eq("dutyUnit.id" ,dutyunit);
            }
            if(StringUtils.isNotBlank(dangerId)){
                cq.eq("dangerId.id" ,dangerId);
            }
            if(StringUtils.isNotBlank(queryDate)){
                cq.eq("examDate", DateUtils.date_sdf.parse(queryDate));
            }
            if(StringUtils.isNotBlank(fillCardManId)){
                cq.eq("fillCardMan.id", fillCardManId);
            }
			if(StringUtils.isNotBlank(hiddenNature)){
				if(hiddenNature.indexOf(",") > -1){
					cq.in("hiddenNature",hiddenNature.split(","));
				}else{
					cq.eq("hiddenNature",hiddenNature);
				}
			}
			cq.addOrder("examDate", SortDirection.desc);
			//排除处于“草稿”处理状态的隐患
			String sql = "select hidden_danger_id from t_b_hidden_danger_handle where handlel_status = '00'";
			List<String> draftStatus = systemService.findListbySql(sql);
			if(draftStatus!=null && draftStatus.size()>0){
				String [] draftStatusIds = new String [draftStatus.size()];
				for(int i=0;i<draftStatus.size();i++){
					draftStatusIds[i] = draftStatus.get(i);
				}
				cq.notIn("id",draftStatusIds);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);


		if(dataGrid != null && dataGrid.getResults() != null){
			List<TBHiddenDangerExamEntity> list = dataGrid.getResults();
			for(TBHiddenDangerExamEntity t : list){
//				String dutyMan = t.getDutyMan();
//				StringBuffer sb = new StringBuffer();
//				if(StringUtils.isNotBlank(dutyMan)){
//					String ids [] = dutyMan.split(",");
//					for(String id : ids){
//						TSUser user = systemService.getEntity(TSUser.class,id);
//						if(user != null){
//							if(StringUtils.isNotBlank(sb.toString())){
//								sb.append(",");
//							}
//							sb.append(user.getRealName());
//						}
//					}
//				}
                if(t.getReviewMan() != null && t.getReviewMan().getId().length() >0){

                }else{
                    t.setReviewMan(null);
                }
				t.setDutyManTemp(t.getDutyMan());
				if(StringUtils.isNotBlank(t.getHiddenNature())){
					String hiddenNatureTemp = DicUtil.getTypeNameByCode("hiddenLevel",t.getHiddenNature());
					t.setHiddenNatureTemp(hiddenNatureTemp);
				}
				//检查人
				if (StringUtils.isNotBlank(t.getFillCardManId())){
					String[] ids = t.getFillCardManId().split(",");
					String name = "";

					for(String id : ids){
						TSUser user = systemService.getEntity(TSUser.class,id);
						if(user!=null){
							if(name==""){
								name = name + user.getUserName()+"-"+user.getRealName();
							}else{
								name = name + "," + user.getUserName()+"-"+user.getRealName();
							}
						}else if(StringUtil.isNotEmpty(id)){
							if (name == "") {
								name = name + id;
							} else {
								name = name + "," + id;
							}
						}
					}
					t.setFillCardManNames(name);
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	
	/**
	 * 查看隐患三违单位分布柱状图
	 * 
	 * @return
	 */
	@RequestMapping(params = "showHiddenVioUnitDisBarChart")
	public ModelAndView showHiddenVioUnitDisBarChart(HttpServletRequest request) {
        String type = request.getParameter("type");
        request.setAttribute("type",type);
        String queryDateBegin = request.getParameter("queryDate_begin");//违章起始时间
	     String queryDateEnd = request.getParameter("queryDate_end");//违章截止时间
	     Map<String,Object> paraMap = new HashMap<String,Object>();
	     paraMap.put("queryDate_begin", queryDateBegin);
	     paraMap.put("queryDate_end", queryDateEnd);
	     String belongMine = (String) ContextHolderUtils.getSession().getAttribute("belongMine");
		    String viowhere = " where 1=1 and VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ";
		    String hiddenwhere = " where 1=1  ";
		    if(StringUtil.isNotEmpty(queryDateBegin)) {
		    	viowhere=viowhere+ " and tv.vio_date >= '"+queryDateBegin+"' ";
		    	hiddenwhere=hiddenwhere+ " and hde.exam_date >= '"+queryDateBegin+"' ";
	        }
	        if(StringUtil.isNotEmpty(queryDateEnd)){
	        	viowhere=viowhere+ " and tv.vio_date <= '"+queryDateEnd+"' ";
	        	hiddenwhere=hiddenwhere+ " and hde.exam_date <= '"+queryDateEnd+"' ";
	        }
			String querySql = "select temp.units,sum(vionum) vionum,sum(hiddennum) hiddennum,sum(vionum+hiddennum) sumnum from ( "
					+ "select tv.vio_units units,count(1) vionum,0 hiddennum from t_b_three_violations tv "+viowhere+" GROUP BY tv.vio_units "
					+ "union ALL "
					+ "select hde.duty_unit units,0 vionum,count(1) hiddennum from t_b_hidden_danger_exam hde "+hiddenwhere+" GROUP BY hde.duty_unit) temp group by temp.units order by sumnum desc";
			List<Map<String, Object>> maplist = systemService.findForJdbc(querySql,null);
			List<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();
			Map<String, Object> resmap = new HashMap<String,Object>();
			if(maplist!=null&&maplist.size()>0){
				StringBuffer dataunits = new StringBuffer();
				StringBuffer datavionum = new StringBuffer();
				StringBuffer datahiddennum = new StringBuffer();
				for (Map map : maplist) {
					// 根据单位ID获取单位名称
					if(StringUtil.isNotEmpty(map.get("units"))){
						TSDepart depart = systemService.getEntity(TSDepart.class, map.get("units").toString());
						if(StringUtils.isNotBlank(dataunits.toString())){
							 dataunits.append(",");
			                }
						if(depart!=null){
							 dataunits.append("'").append(depart.getDepartname()).append("'");
						}else{
							dataunits.append(",");
						}
						if(StringUtils.isNotBlank(datavionum.toString())){
							datavionum.append(",");
			                }
						if(StringUtils.isNotBlank(datahiddennum.toString())){
							datahiddennum.append(",");
			                }
						datavionum.append(map.get("vionum"));
						datahiddennum.append(map.get("hiddennum"));
					}

				}
				paraMap.put("dataunits", dataunits);
				paraMap.put("datavionum", datavionum);
				paraMap.put("datahiddennum", datahiddennum);
			}

		return new ModelAndView("com/sdzk/buss/web/riskalert/hiddenVioUnitDisBarChart",paraMap);
	}









	/**
	 * 隐患检查次数分区域预警列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "hiddenCheckNumberlist")
	public ModelAndView hiddenCheckNumberlist(HttpServletRequest request) {
		// return new ModelAndView("com/yk/buss/web/riskalert/hiddenCheckNumberList");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try{
            String startTime = sdf.format(date);
            startTime = startTime + "-01";
            request.setAttribute("startTime",startTime);
        }catch(Exception e){
            e.printStackTrace();
        }

		String mapPath = tbMapManageService.getCurrentMapPath();
		request.setAttribute("mapPath",mapPath.substring(1));

		return new ModelAndView("com/sdzk/buss/web/riskalert/hiddenCheckNumberList-withLevel");
	}

	/**
	 * 隐患检查次数分区域预警列表 超图页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "hiddenCheckNumberlist_supermap")
	public ModelAndView hiddenCheckNumberlist_supermap(HttpServletRequest request) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try{
			String startTime = sdf.format(date);
			startTime = startTime + "-01";
			request.setAttribute("startTime",startTime);
		}catch(Exception e){
			e.printStackTrace();
		}
		String supermapMineUrl = ResourceUtil.getConfigByName("supermapMineUrl");
		request.setAttribute("supermapMineUrl",supermapMineUrl);
		String supermapMineCenter = ResourceUtil.getConfigByName("supermapMineCenter");
		request.setAttribute("supermapMineCenter",supermapMineCenter);
		String supermapCountyUrl = ResourceUtil.getConfigByName("supermapCountyUrl");
		request.setAttribute("supermapCountyUrl",supermapCountyUrl);
		String supermapCountyCenter = ResourceUtil.getConfigByName("supermapCountyCenter");
		request.setAttribute("supermapCountyCenter",supermapCountyCenter);

		return new ModelAndView("com/sdzk/buss/web/riskalert/hiddenCheckNumberList_supermap");
	}

	/**
	 * 隐患检查次数分区域预警列表 超图页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "hiddenCheckNumberlist_supermap_multiLayer")
	public ModelAndView hiddenCheckNumberlist_supermap_multiLayer(HttpServletRequest request) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try{
			String startTime = sdf.format(date);
			startTime = startTime + "-01";
			request.setAttribute("startTime",startTime);
		}catch(Exception e){
			e.printStackTrace();
		}
		CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class);
		cq.eq("isShow",Constants.IS_SHOW_Y);
		cq.add();
		List<TBLayerEntity> layerList = this.systemService.getListByCriteriaQuery(cq, false);
		request.setAttribute("layerList",layerList);
		if(null!=layerList&&layerList.size()>0){
			request.setAttribute("initLayerCode",layerList.get(0).getId());
		}

		return new ModelAndView("com/sdzk/buss/web/riskalert/hiddenCheckNumberList_supermap_multiLayer");
	}

	/**
	 * 隐患检查次数分区域预警列表查询
	 * 检查次数为（根据日期、班次、地点、填卡人相同的进行分组—去除干部下井问题）+干部下井表数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "hiddenCheckNumberDataGrid")
	public void hiddenCheckNumberDataGrid(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {

		String queryDateBegin = request.getParameter("queryDate_begin");
	    String queryDateEnd = request.getParameter("queryDate_end");
        String dutyUnitId = request.getParameter("dutyUnitId");

	    String hiddenwhere = " where 1=1   ";

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
			hiddenwhere += " AND hde.id not in (select hidden_danger_id from t_b_hidden_danger_handle where id in(select column_id from t_b_sunshine))";
		}
		/*************************************************************/

        if(StringUtils.isNotBlank(dutyUnitId)){
            hiddenwhere += " and hde.duty_unit='"+dutyUnitId+"' ";
        }

	    if(StringUtil.isNotEmpty(queryDateBegin)) {
	    	hiddenwhere=hiddenwhere+ " and hde.exam_date >= '"+queryDateBegin+"' ";
        }
        if(StringUtil.isNotEmpty(queryDateEnd)){
        	hiddenwhere=hiddenwhere+ " and hde.exam_date <= '"+queryDateEnd+"' ";
        }


		String querySql =  "SELECT temp.address,temp.addressname,temp.lon,temp.lat, temp.belongLayer,count(1) checknum FROM (SELECT hde.address,address.address addressname,address.lon,address.lat, address.belong_layer belongLayer FROM t_b_hidden_danger_exam hde join t_b_address_info address on hde.address=address.id and address.is_delete=0 and address.isShowData = '1' "+
				hiddenwhere+
					" GROUP BY hde.address,hde.exam_date,hde.shift,hde.fill_card_man) temp GROUP BY temp.address order by checknum desc";

		List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);
        if(maplist!=null&&maplist.size()>0){
			for (Map map : maplist) {
				// 根据单位ID获取单位名称
				if(StringUtil.isNotEmpty(map.get("address"))){
					//根据地点查询问题数
					String hiddenSql = "SELECT count(1) issuenum FROM t_b_hidden_danger_exam hde "+hiddenwhere+" and hde.address='"+map.get("address")+"'";
					Long hiddennum = systemService.getCountForJdbc(hiddenSql);
					map.put("hiddennum", hiddennum);
				}
			}
		}
		
		dataGrid.setResults(maplist);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 查询各地点隐患检查次数
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getHiddenCheckNumberList")
	@ResponseBody
	public AjaxJson getHiddenCheckNumberList(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> resMap = new HashMap<String, Object>();
		String queryDateBegin = req.getParameter("queryDate_begin");
	    String queryDateEnd = req.getParameter("queryDate_end");
        String dutyUnitId = req.getParameter("dutyUnitId");
        String addressId = req.getParameter("addressId");

	    String hiddenwhere = " where 1=1   ";
	    if(StringUtil.isNotEmpty(queryDateBegin)) {
	    	hiddenwhere=hiddenwhere+ " and hde.exam_date >= '"+queryDateBegin+"' ";
        }
        if(StringUtil.isNotEmpty(queryDateEnd)){
        	hiddenwhere=hiddenwhere+ " and hde.exam_date <= '"+queryDateEnd+"' ";
        }
        if(StringUtils.isNotBlank(dutyUnitId)){
            hiddenwhere += " and hde.duty_unit='"+dutyUnitId+"' ";
        }
        if(StringUtils.isNotBlank(addressId)&&!"allmine".equals(addressId.trim())){
            hiddenwhere += " and hde.address='"+addressId+"' ";
        }

		String querySql =  "SELECT addr.id as addressId,addr.address as addressName,addr.isShow as isShow,addr.lon as lon,addr.lat as lat,addr.point_str as pointStr, addr.belong_layer belongLayer, a.checknum as checknum FROM ( SELECT temp.address,count(1) checknum FROM (SELECT hde.address FROM t_b_hidden_danger_exam hde"+
				hiddenwhere+" GROUP BY hde.address,hde.exam_date,hde.shift,hde.fill_card_man) temp GROUP BY temp.address order by checknum desc ) a "+
				" JOIN t_b_address_info addr ON a.address = addr.id where addr.is_delete=0 and addr.isShowData = '1'";
		List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);


        List<Map<String,Object>> retMapList = new ArrayList<Map<String, Object>>();
		for (Map map : maplist) {
			// 根据地址ID获取地址信息
			if(StringUtil.isNotEmpty(map.get("addressId"))){
				map.put("addressName", map.get("addressName"));
				map.put("lon", map.get("lon"));
				map.put("lat", map.get("lat"));
				map.put("belongLayer", map.get("belongLayer"));
				map.put("checknum", map.get("checknum"));

				//根据地点查询问题数
				String hiddenSql = "select ( CASE hde.hidden_nature WHEN '2' THEN 'A' WHEN '3' THEN 'B' WHEN '4' THEN 'C' WHEN '5' THEN 'D' WHEN '6' THEN 'E' END ) AS alert_level,"
						+ "count(1) hiddennum from t_b_hidden_danger_exam hde " + hiddenwhere + " and hde.address='" + map.get("addressId").toString() + "' GROUP BY hde.hidden_nature";
				List<Map<String, Object>> hiddenMapList = systemService.findForJdbc(hiddenSql, null);
				int sumDE_hidden = 0;
				if(!hiddenMapList.isEmpty() && hiddenMapList.size()>0){
					for (Map hiddenMap : hiddenMapList) {
						String alertLevel = (String)hiddenMap.get("alert_level");
						if (StringUtil.isNotEmpty(alertLevel)) {
							if(alertLevel.equals("D") || alertLevel.equals("E")){
								alertLevel = "DE";
							}

							if(alertLevel.equals("DE")){
								sumDE_hidden = sumDE_hidden + Integer.valueOf(hiddenMap.get("hiddennum").toString());
							}else{
								if (StringUtil.isNotEmpty(hiddenMap.get("hiddennum"))) {
									map.put("hiddennum_"+alertLevel, hiddenMap.get("hiddennum").toString());
								}
							}
						}
					}
					map.put("hiddennum_DE", sumDE_hidden);
                    if(map.get("isShow") != null){
                        if(!Constants.IS_SHOW_N.equals(map.get("isShow").toString())){
                            retMapList.add(map);
                        }
                    }

				}
			}
		}
		resMap.put("resultList", retMapList);
		j.setAttributes(resMap);
		return j;
	}
    @RequestMapping(params = "exportXlsByAddress")
    public String exportXlsByAddress(TBDangerSourceEntity tBDangerSource,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {

        String queryDateBegin = request.getParameter("queryDate_begin");
        String queryDateEnd = request.getParameter("queryDate_end");
        String dutyUnitId = request.getParameter("dutyUnitId");
        String checkOrgName = "全部";

        String hiddenwhere = " where 1=1 ";

        if(StringUtils.isNotBlank(dutyUnitId)){
            hiddenwhere += " and hde.duty_unit='"+dutyUnitId+"' ";
            TSDepart depart = systemService.getEntity(TSDepart.class,dutyUnitId);
            checkOrgName = depart!=null?depart.getDepartname():"";
        }

        if(StringUtil.isNotEmpty(queryDateBegin)) {
            hiddenwhere=hiddenwhere+ " and hde.exam_date >= '"+queryDateBegin+"' ";
        }
        if(StringUtil.isNotEmpty(queryDateEnd)){
            hiddenwhere=hiddenwhere+ " and hde.exam_date <= '"+queryDateEnd+"' ";
        }


        String querySql =  "SELECT temp.address,temp.addressname,count(temp.address) checknum FROM (SELECT hde.address,address.address addressname FROM t_b_hidden_danger_exam hde join t_b_address_info address on hde.address=address.id and address.is_delete=0 "+
                hiddenwhere+
                " GROUP BY hde.address,hde.exam_date,hde.shift,hde.fill_card_man) temp GROUP BY temp.address order by checknum desc";

        List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);
        List<HiddenDangerVioVO> retList = new ArrayList<HiddenDangerVioVO>();
        if(maplist!=null&&maplist.size()>0){
            for (Map map : maplist) {
                HiddenDangerVioVO vo = new HiddenDangerVioVO();
                vo.setAddressname(map.get("addressname").toString());
                vo.setChecknum(map.get("checknum").toString());
                if(StringUtil.isNotEmpty(map.get("address"))){
                    //根据地点查询问题数
                    String hiddenSql = "SELECT count(1) issuenum FROM t_b_hidden_danger_exam hde "+hiddenwhere+" and hde.address='"+map.get("address")+"'";
                    Long hiddennum = systemService.getCountForJdbc(hiddenSql);
                    map.put("hiddennum", hiddennum);
                } else {
                    map.put("hiddennum", 0);
                }
                vo.setHiddennum(map.get("hiddennum").toString());
                retList.add(vo);
            }
        }

        //按隐患个数排序
        Collections.sort(retList, new Comparator() {
            public int compare(Object a, Object b) {
                String i = ((HiddenDangerVioVO) a).getHiddennum();
                String j = ((HiddenDangerVioVO) b).getHiddennum();
                return Integer.parseInt(j) - Integer.parseInt(i);
            }
        });

        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_hiddenDangerVioByAddress.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", retList);
        String start = "";
        if(StringUtils.isBlank(queryDateBegin) && StringUtils.isBlank(queryDateEnd)){
            start = "";
        }else if(StringUtils.isBlank(queryDateBegin) && StringUtils.isNotBlank(queryDateEnd)){
            start = "截止至 "+queryDateEnd;
        }else if(StringUtils.isNotBlank(queryDateBegin) && StringUtils.isNotBlank(queryDateEnd)){
            start = queryDateBegin + " 至 "+queryDateEnd;
        }else{
            start = "自 "+queryDateBegin+" 起";
        }
        map.put("checkOrgName",checkOrgName);
        map.put("start",start);
        modelMap.put(NormalExcelConstants.FILE_NAME,"检查次数分区域列表导出");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

	/**
	 * 安全风险四色图
	 *
	 * @return
	 */
	@RequestMapping(params = "goUndergroundSafetyRiskAlert_static")
	public ModelAndView goUndergroundSafetyRiskAlert_static(HttpServletRequest request) {

		CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class);
		cq.eq("isShow",Constants.IS_SHOW_Y);
		cq.add();
		List<TBLayerEntity> layerList = this.systemService.getListByCriteriaQuery(cq, false);
		request.setAttribute("layerList",layerList);
		if(null!=layerList&&layerList.size()>0){
			request.setAttribute("initLayerCode",layerList.get(0).getId());
		}
		return new ModelAndView("com/sdzk/buss/web/riskalert/undergroundSafetyRiskAlert_static");
	}


	/**
	 * 风险管控预警图
	 *
	 * @return
	 */
	@RequestMapping(params = "goUndergroundSafetyRiskAlert_dynamic")
	public ModelAndView goUndergroundSafetyRiskAlert_dynamic(HttpServletRequest request) {

		CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class);
		cq.eq("isShow",Constants.IS_SHOW_Y);
		cq.add();
		List<TBLayerEntity> layerList = this.systemService.getListByCriteriaQuery(cq, false);
		request.setAttribute("layerList",layerList);
		if(null!=layerList&&layerList.size()>0){
			request.setAttribute("initLayerCode",layerList.get(0).getId());
		}
		return new ModelAndView("com/sdzk/buss/web/riskalert/undergroundSafetyRiskAlert_dynamic");
	}

	@RequestMapping(params = "exportXls")
	public String exportXls( HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		String sql = "SELECT IFNULL(t.riskLevel,'') riskLevel,address from t_b_address_info ai LEFT JOIN (SELECT min(risk_level) riskLevel,address_id from t_b_risk_identification WHERE `status` ='3' and is_del ='0' and identifi_date <= NOW()  and (exp_date >= NOW() or exp_date is null) GROUP BY address_id) t on ai.id = t.address_id WHERE ai.is_delete = '0' ";
		List<Map<String,Object>> queryList = this.systemService.findForJdbc(sql.toString());
		if (queryList != null && queryList.size() > 0) {
			for (Map<String, Object> map: queryList) {
				map.put("riskLevel", DicUtil.getTypeNameByCode("factors_level", (String) map.get("riskLevel")));
			}
		}
		TemplateExportParams templateExportParams = new TemplateExportParams();
		templateExportParams.setSheetNum(0);
		templateExportParams.setTemplateUrl("export/template/exportTemp_addressLevel.xls");
		modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", queryList);
		modelMap.put(NormalExcelConstants.FILE_NAME,"风险点等级");
		modelMap.put(TemplateExcelConstants.MAP_DATA,map);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
}
