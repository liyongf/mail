package com.sdzk.buss.web.address.controller;

import com.sdzk.buss.web.address.entity.TBAddressDepartRelEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.address.service.TBAddressInfoServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.SfService;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.common.utils.QrCodeCreateUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerAddresstRelEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceReportVO;
import com.sdzk.buss.web.gjj.entity.SFPictureInfoEntity;
import com.sdzk.buss.web.mapmanage.service.TBMapManageServiceI;
import com.sdzk.buss.web.system.entity.TBSunshineEntity;
import com.sdzk.sys.synctocloud.service.SyncToCloudService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
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
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.web.system.pojo.base.*;
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
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import com.sdzk.buss.web.layer.entity.TBLayerEntity;

/**
 * @Title: Controller
 * @Description: 风险点列表
 * @author onlineGenerator
 * @date 2017-06-19 15:18:39
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/tBAddressInfoController")
public class TBAddressInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBAddressInfoController.class);

	@Autowired
	private TBAddressInfoServiceI tBAddressInfoService;
	@Autowired
	private TBMapManageServiceI tbMapManageService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SyncToCloudService syncToCloudService;
	@Autowired
	private Validator validator;

	@Autowired
	private SfService sfService;

	@RequestMapping(params = "dynamicRiskAlertDatagrid")
	public void dynamicRiskAlertDatagrid(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		/*查询风险点-开始*/
		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
		tBAddressInfo.setAddress(null);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAddressInfo, request.getParameterMap());
		try{
			//自定义追加查询条件
			String address = request.getParameter("address");
			if (StringUtil.isNotEmpty(address)) {
				cq.like("address", "%"+address+"%");
			}
			cq.eq("isDelete", Constants.IS_DELETE_N);
			/*地标版本发生变动，下面代码注释*/
			/*//将未关联风险的地点从表格中去掉
			String sql = "SELECT a.address_id FROM t_b_danger_address_rel a inner join t_b_danger_source b on a.danger_id=b.id ";
			List<String> ids = systemService.findListbySql(sql);
			if(!ids.isEmpty() && ids.size()>0){
				cq.in("id",ids.toArray());
			}else{
				cq.eq("id","TEST");
			}*/
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBAddressInfoService.getDataGridReturn(cq, true);
		List<TBAddressInfoEntity> list = dataGrid.getResults();

		/*查询风险点-结束*/
		/*获取风险点的动态等级-开始*/
		Map<String, Object> dynamicLevel = this.tBAddressInfoService.getDynamicLevel();
		/*获取风险点的动态等级-结束*/

		if (list != null && list.size() > 0) {
			for (TBAddressInfoEntity entity : list) {
				//动态风险等级，根据未整改隐患情况决定
				String levelTemp = String.valueOf(dynamicLevel.get(entity.getId()));
				if(StringUtil.isNotEmpty(levelTemp) && levelTemp != "null"){
					if("1".equals(levelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_ZDFX);
					}else if("2".equals(levelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_JDFX);
					}else if("3".equals(levelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_YBFX);
					}else{
						entity.setAlertColor(Constants.ALERT_COLOR_DFX);
					}
					entity.setRiskLevel(levelTemp);
				}
				/*旧的动态等级算法-开始*/
				//查询该地点未整改隐患数量，根据隐患等级分组
				/*String sql = "select count(t.id) total,t.hidden_nature from t_b_hidden_danger_exam t inner join t_b_hidden_danger_handle handle on t.id = handle.hidden_danger_id where handle.handlel_status in ('1','4') and t.address = '"+entity.getId()+"' GROUP BY t.hidden_nature";
				List<Map<String, Object>> totalList = systemService.findForJdbc(sql);
				if(totalList != null && !totalList.isEmpty()){
					String total = totalList.get(0).get("total").toString();
					String hidden_nature = totalList.get(0).get("hidden_nature").toString();

					//根据隐患等级，查询分值配置
					cq = new CriteriaQuery(TBRiskRuleScoreManagerEntity.class);
					try{
						cq.eq("riskType",hidden_nature);
					}catch(Exception e){
						e.printStackTrace();
					}
					cq.add();
					List<TBRiskRuleScoreManagerEntity> listScore = systemService.getListByCriteriaQuery(cq,false);
					int score = 0;
					if(listScore != null && !listScore.isEmpty()){
						score = Integer.parseInt(listScore.get(0).getScore());
					}

					int totalScore = score * Integer.parseInt(total);
					//根据总分值，到规则表中匹配等级
					sql = "select a.* from ( select t.* ,(CASE t.score_end WHEN '' THEN '9999999' ELSE t.score_end END) maxScore from t_b_risk_rule_manager t  )a where a.score_between <= "+totalScore+" and a.maxScore > "+totalScore;//"select t.risk_type from t_b_risk_rule_manager t where t.score_between <= "+totalScore+" and t.score_end > "+totalScore;
					List<Map<String, Object>> riskTypeList = systemService.findForJdbc(sql);
					String riskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", riskTypeList.get(0).get("risk_type").toString());

					if("重大风险".equals(riskLevelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_ZDFX);
					}else if("较大风险".equals(riskLevelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_JDFX);
					}else if("一般风险".equals(riskLevelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_YBFX);
					}else{
						entity.setAlertColor(Constants.ALERT_COLOR_DFX);
					}
					entity.setRiskLevel(riskLevelTemp);
				}*/
				/*旧的动态等级算法-结束*/
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//风险点静态等级
				String staticSql = "SELECT b.risk_level FROM t_b_address_info a LEFT JOIN ( SELECT address_id, risk_level FROM t_b_risk_identification c where c.is_del = '0' and c.status = '3' and  identifi_date <= '"+sdf.format(new Date())+"'  and (exp_date >= '"+sdf.format(new Date())+"' or exp_date is null)) b ON a.id = b.address_id WHERE a.id = '"+entity.getId()+"' ORDER BY b.risk_level ASC LIMIT 1";
				List<String> staticList = systemService.findListbySql(staticSql);
				if(!staticList.isEmpty() && staticList.size()>0){
					String staticRiskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", staticList.get(0));

					if("重大风险".equals(staticRiskLevelTemp)){
						entity.setStaticAlertColor(Constants.ALERT_COLOR_ZDFX);
					}else if("较大风险".equals(staticRiskLevelTemp)){
						entity.setStaticAlertColor(Constants.ALERT_COLOR_JDFX);
					}else if("一般风险".equals(staticRiskLevelTemp)){
						entity.setStaticAlertColor(Constants.ALERT_COLOR_YBFX);
					}else{
						entity.setStaticAlertColor(Constants.ALERT_COLOR_DFX);
					}
					entity.setStaticRiskLevel(staticRiskLevelTemp);
				}else {
					entity.setStaticAlertColor(Constants.ALERT_COLOR_NULL);
					entity.setStaticRiskLevel("");
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "hiddenAddressData")
	@ResponseBody
	public AjaxJson hiddenAddressData(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "风险点列表更新成功";
		if(StringUtils.isNotBlank(ids)){
			String[] idArray = ids.split(",");
			for(String id : idArray){
				TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,id);
				addressInfoEntity.setIsShowData(Constants.IS_SHOWDATA_N);
				systemService.saveOrUpdate(addressInfoEntity);
			}
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "showAddressData")
	@ResponseBody
	public AjaxJson showAddressData(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "风险点列表更新成功";
		if(StringUtils.isNotBlank(ids)){
			String[] idArray = ids.split(",");
			for(String id : idArray){
				TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,id);
				addressInfoEntity.setIsShowData(Constants.IS_SHOWDATA_Y);
				systemService.saveOrUpdate(addressInfoEntity);
			}
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "dataManageDatagrid")
	public void dataManageDatagrid(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
		tBAddressInfo.setAddress(null);
		String isShow = request.getParameter("isShowData");
		if(StringUtils.isBlank(isShow)){
			isShow = "1";
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAddressInfo, request.getParameterMap());
		try{
			//自定义追加查询条件
			String address = request.getParameter("address");
			if (StringUtil.isNotEmpty(address)) {
				cq.like("address", "%"+address+"%");
			}
			cq.eq("isDelete", Constants.IS_DELETE_N);
			cq.eq("isShowData", isShow);
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
			String sunSql = "select column_id from t_b_sunshine where table_name='t_b_address_info'";
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
		this.tBAddressInfoService.getDataGridReturn(cq, true);
		List<TBAddressInfoEntity> list = dataGrid.getResults();
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "datamanagelist")
	public ModelAndView datamanagelist(HttpServletRequest request, HttpServletResponse response) {
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

		String mapPath = tbMapManageService.getCurrentMapPath();
		request.setAttribute("mapPath",mapPath.substring(1));

		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfoDataManageList");

	}

	@RequestMapping(params = "getAddressList")
	@ResponseBody
	public JSONArray getAddressList(HttpServletRequest request)
			throws Exception {

		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
		cq.eq("isDelete", Constants.IS_DELETE_N);
		cq.add();
		PageList pageList = this.systemService.getPageList(cq, false);
		List<TBAddressInfoEntity> addressInfoEntityList = pageList
				.getResultList();
		List<TSAttachmentTemp> tempList = new ArrayList<TSAttachmentTemp>();
		if (addressInfoEntityList == null) {
			tempList = new ArrayList<TSAttachmentTemp>();
		} else {
			for (TBAddressInfoEntity address : addressInfoEntityList) {
				String id = address.getId();
				String addressStr = address.getAddress();
				TSAttachmentTemp temp = new TSAttachmentTemp();
				temp.setId(id);
				temp.setAttachmenttitle(addressStr);
				tempList.add(temp);
			}
		}
		JSONArray jsonArray = JSONArray.fromObject(tempList);
		return jsonArray;
	}


	/**
	 * 风险点列表列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfoList");
	}

	/**
	 * 风险点信息管理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "managelist")
	public ModelAndView managelist(HttpServletRequest request, HttpServletResponse response) {
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

		String mapPath = tbMapManageService.getCurrentMapPath();
		request.setAttribute("mapPath",mapPath.substring(1));

		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfoManageList");

	}

	/**
	 * 风险点信息管理列表 超图页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "managelist_supermap")
	public ModelAndView managelist_supermap(HttpServletRequest request, HttpServletResponse response) {

		String ewmgn=ResourceUtil.getConfigByName("ewmgn");
		if(StringUtils.isNotBlank(ewmgn)){
			request.setAttribute("ewmgn",ewmgn);
		}
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

		/*String supermapMineUrl = ResourceUtil.getConfigByName("supermapMineUrl");
		request.setAttribute("supermapMineUrl",supermapMineUrl);
		String supermapMineCenter = ResourceUtil.getConfigByName("supermapMineCenter");
		request.setAttribute("supermapMineCenter",supermapMineCenter);
		String supermapCountyUrl = ResourceUtil.getConfigByName("supermapCountyUrl");
		request.setAttribute("supermapCountyUrl",supermapCountyUrl);
		String supermapCountyCenter = ResourceUtil.getConfigByName("supermapCountyCenter");
		request.setAttribute("supermapCountyCenter",supermapCountyCenter);*/
		CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class);
		cq.eq("isShow","Y");
		cq.add();
		List<TBLayerEntity> layerList = this.systemService.getListByCriteriaQuery(cq,false);
		request.setAttribute("layerList",layerList);
		if(layerList != null && layerList.size()>0){
			request.setAttribute("initLayerCode",layerList.get(0).getId());
		}

		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfoManageList_supermap");

	}

	/**
	 * 风险点信息管理列表 超图多边形页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "managelist_supermap_polygon")
	public ModelAndView managelist_supermap_polygon(HttpServletRequest request, HttpServletResponse response) {
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

		String supermapMineUrl = ResourceUtil.getConfigByName("supermapMineUrl");
		request.setAttribute("supermapMineUrl",supermapMineUrl);
		String supermapMineCenter = ResourceUtil.getConfigByName("supermapMineCenter");
		request.setAttribute("supermapMineCenter",supermapMineCenter);
		String supermapCountyUrl = ResourceUtil.getConfigByName("supermapCountyUrl");
		request.setAttribute("supermapCountyUrl",supermapCountyUrl);
		String supermapCountyCenter = ResourceUtil.getConfigByName("supermapCountyCenter");
		request.setAttribute("supermapCountyCenter",supermapCountyCenter);

		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfoManageList_supermap_polygon");

	}

	/**
	 * 风险点信息管理列表 超图多图层跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "managelist_supermap_multiLayer")
	public ModelAndView managelist_supermap_multiLayer(HttpServletRequest request, HttpServletResponse response) {
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

/*		String supermapMineUrl = ResourceUtil.getConfigByName("supermapMineUrl");
		request.setAttribute("supermapMineUrl",supermapMineUrl);
		String supermapMineCenter = ResourceUtil.getConfigByName("supermapMineCenter");
		request.setAttribute("supermapMineCenter",supermapMineCenter);
		String supermapCountyUrl = ResourceUtil.getConfigByName("supermapCountyUrl");
		request.setAttribute("supermapCountyUrl",supermapCountyUrl);
		String supermapCountyCenter = ResourceUtil.getConfigByName("supermapCountyCenter");
		request.setAttribute("supermapCountyCenter",supermapCountyCenter);*/

		CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class);
		cq.eq("isShow",Constants.IS_SHOW_Y);
		cq.add();
		List<TBLayerEntity> layerList = this.systemService.getListByCriteriaQuery(cq, false);
		request.setAttribute("layerList",layerList);
		if(null!=layerList&&layerList.size()>0){
			request.setAttribute("initLayerCode",layerList.get(0).getLayerCode());
		}
		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfoManageList_supermap_multiLayer");
	}


	/**
	 * 获取全部地址
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getAllAddressList")
	@ResponseBody
	public AjaxJson getAllAddressList(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> resMap = new HashMap<String, Object>();
		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
		cq.eq("isDelete", Constants.IS_DELETE_N);
		cq.eq("isshow",Constants.IS_SHOW_Y);
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
			String sunSql = "select column_id from t_b_sunshine where table_name='t_b_address_info'";
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
		List<TBAddressInfoEntity> addressList = this.systemService.getListByCriteriaQuery(cq, false);

		resMap.put("addressList", addressList);
		j.setAttributes(resMap);
		return j;
	}
	/**
	 * 获取全部地址 静态风险等级
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getAllAddressListWithLevelStatic")
	@ResponseBody
	public AjaxJson getAllAddressListWithLevelStatic(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		/*String sql = "select a.*,min(c.ye_risk_grade) risk_level from (select address.address, address.lon, address.lat, address.point_str pointStr, address.belong_layer belongLayer, address.id from t_b_address_info address " +
				"left join (select * from t_b_danger_address_rel where risk_level in (select t.typecode from t_s_type t where t.typegroupid = (select g.id from t_s_typegroup g where g.typegroupcode = 'riskLevel') and t.is_hide = 0)) rel " +
				"on address.id=rel.address_id " +
				"inner join (select * from t_b_danger_source where YE_RISK_GRADE in (select t.typecode from t_s_type t where t.typegroupid = (select g.id from t_s_typegroup g where g.typegroupcode = 'riskLevel') and t.is_hide = 0)) ds " +
				"on rel.danger_id=ds.id " +
				"and ds.is_delete='0' " +
				"where address.is_delete='0' and address.isShow='Y'" +
				"group by address.address, address.lon, address.lat, address.id) a left join t_b_danger_address_rel b on a.id = b.address_id left join (SELECT * from  t_b_danger_source ) c on b.danger_id=c.id  GROUP BY a.id";
*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "SELECT address.address, address.lon, address.lat, address.point_str pointStr, address.belong_layer belongLayer, address.id, min(b.risk_level) risk_level FROM t_b_address_info address INNER JOIN t_b_risk_identification b ON b.address_id = address.id AND b.is_del = '0' AND b. STATUS = '3'  and  b.identifi_date <= '"+sdf.format(new Date())+"'  and (b.exp_date >= '"+sdf.format(new Date())+"' or b.exp_date is null) WHERE address.is_delete = '0' AND address.isShow = 'Y' GROUP BY address.id";
		List<Map<String,Object>> addressList = this.systemService.findForJdbc(sql);
		if(null!=addressList && addressList.size()>0){
			for(int i=0;i<addressList.size();i++){
				Map<String,Object> addressMap = addressList.get(i);
				String addressRiskLevel = (String)addressMap.get("risk_level");
				if(StringUtil.isEmpty(addressRiskLevel)){
					addressMap.put("risk_level","");
					addressMap.put("staticAlertColor",Constants.ALERT_COLOR_NULL);
				} else {
					String riskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", addressRiskLevel);
					if(StringUtil.isEmpty(riskLevelTemp)){
						addressMap.put("risk_level","riskLevelTemp");
						addressMap.put("staticAlertColor",Constants.ALERT_COLOR_NULL);
					} else {
						addressMap.put("risk_level",addressRiskLevel);
						if("重大风险".equals(riskLevelTemp)){
							addressMap.put("staticAlertColor",Constants.ALERT_COLOR_ZDFX);
						}else if("较大风险".equals(riskLevelTemp)){
							addressMap.put("staticAlertColor",Constants.ALERT_COLOR_JDFX);
						}else if("一般风险".equals(riskLevelTemp)){
							addressMap.put("staticAlertColor",Constants.ALERT_COLOR_YBFX);
						}else{
							addressMap.put("staticAlertColor",Constants.ALERT_COLOR_DFX);
						}
					}
				}

			}
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("addressList", addressList);
		j.setAttributes(resMap);
		return j;
	}
	/**
	 * 获取全部地址  动态风险预警
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getAllAddressListWithLevel_new")
	@ResponseBody
	public AjaxJson getAllAddressListWithLevel_new(HttpServletRequest req) {
		Map<String, Object> dynamicLevel = this.tBAddressInfoService.getDynamicLevel();

		AjaxJson j = new AjaxJson();
		String proCate_gradeControl = req.getParameter("proCate_gradeControl");//专业查询条件，未加
		String riskLevel = req.getParameter("riskLevel");//等级查询条件，未加
		//动态风险
		/*String sql = "select address.address, address.lon, address.lat, address.point_str pointStr, address.belong_layer belongLayer, address.id, min(rel.risk_level) risk_level from t_b_address_info address \n" +
				"left join (select * from t_b_danger_address_rel where danger_id in (SELECT id from t_b_danger_source ) and risk_level in ("+Constants.RISK_LEVEL_HIDE_WHERE+")) rel\n" +
				"on address.id=rel.address_id\n" +
				"inner join (select * from t_b_danger_source where YE_RISK_GRADE in ("+Constants.RISK_LEVEL_HIDE_WHERE+")) ds\n" +
				"on rel.danger_id=ds.id\n" +
				"and ds.is_delete='0' " +
				"where address.is_delete='0' and address.isShow='Y'\n" +
				"group by address.address, address.lon, address.lat, address.id";*/
		String sql = "SELECT id,address,lon,lat,belong_layer belongLayer FROM t_b_address_info WHERE is_delete = '0' AND isShow = 'Y'";

		List<Map<String,Object>> addressList = this.systemService.findForJdbc(sql);
		if(null!=addressList && addressList.size()>0){
			for(int i=0;i<addressList.size();i++){
				Map<String,Object> addressMap = addressList.get(i);
				String addressid = (String)addressMap.get("id");
				String levelTemp = String.valueOf(dynamicLevel.get(addressid));
				if(StringUtil.isNotEmpty(levelTemp) && levelTemp != "null"){
					addressMap.put("risk_level",levelTemp);
					if("1".equals(levelTemp)){
						addressMap.put("alertColor",Constants.ALERT_COLOR_ZDFX);
					}else if("2".equals(levelTemp)){
						addressMap.put("alertColor",Constants.ALERT_COLOR_JDFX);
					}else if("3".equals(levelTemp)){
						addressMap.put("alertColor",Constants.ALERT_COLOR_YBFX);
					}else{
						addressMap.put("alertColor",Constants.ALERT_COLOR_DFX);
					}
				}

				//查询该地点未整改隐患数量，根据隐患等级分组
				/*sql = "select count(t.id) total,t.hidden_nature from t_b_hidden_danger_exam t inner join t_b_hidden_danger_handle handle on t.id = handle.hidden_danger_id inner join t_b_danger_source danger on t.danger_id = danger.id where 1=1 ";
				if(StringUtils.isNotBlank(riskLevel)){
					sql = sql + " and danger.ye_risk_grade = '"+riskLevel+"' ";
				}
				if(StringUtils.isNotBlank(proCate_gradeControl)){
					sql = sql + " and danger.ye_profession = '"+proCate_gradeControl+"' ";
				}
				sql = sql + " and handle.handlel_status in ('1','4') and t.address = '"+addressid+"' GROUP BY t.hidden_nature";
				List<Map<String, Object>> totalList = systemService.findForJdbc(sql);
				if(totalList != null && !totalList.isEmpty()){
					String total = totalList.get(0).get("total").toString();
					String hidden_nature = totalList.get(0).get("hidden_nature").toString();

					//根据隐患等级，查询分值配置
					CriteriaQuery cq = new CriteriaQuery(TBRiskRuleScoreManagerEntity.class);
					try{
						cq.eq("riskType",hidden_nature);
					}catch(Exception e){
						e.printStackTrace();
					}
					cq.add();
					List<TBRiskRuleScoreManagerEntity> list = systemService.getListByCriteriaQuery(cq,false);
					int score = 0;
					if(list != null && !list.isEmpty()){
						score = Integer.parseInt(list.get(0).getScore());
					}

					int totalScore = score * Integer.parseInt(total);
					//根据总分值，到规则表中匹配等级
					sql = "select a.* from ( select t.* ,(CASE t.score_end WHEN '' THEN '9999999' ELSE t.score_end END) maxScore from t_b_risk_rule_manager t  )a where a.score_between <= "+totalScore+" and a.maxScore > "+totalScore;//"select t.risk_type from t_b_risk_rule_manager t where t.score_between <= "+totalScore+" and t.score_end > "+totalScore;
					List<Map<String, Object>> riskTypeList = systemService.findForJdbc(sql);
					String riskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", riskTypeList.get(0).get("risk_type").toString());
					if(StringUtil.isEmpty(riskLevelTemp)){
						addressMap.put("risk_level","riskLevelTemp");
						addressMap.put("alertColor",Constants.ALERT_COLOR_NULL);
					} else {
						addressMap.put("risk_level",riskTypeList.get(0).get("risk_type").toString());
						if("重大风险".equals(riskLevelTemp)){
							addressMap.put("alertColor",Constants.ALERT_COLOR_ZDFX);
						}else if("较大风险".equals(riskLevelTemp)){
							addressMap.put("alertColor",Constants.ALERT_COLOR_JDFX);
						}else if("一般风险".equals(riskLevelTemp)){
							addressMap.put("alertColor",Constants.ALERT_COLOR_YBFX);
						}else{
							addressMap.put("alertColor",Constants.ALERT_COLOR_DFX);
						}
					}
				}*/

			}
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("addressList", addressList);
		j.setAttributes(resMap);
		return j;
	}

	/**
	 * 获取全部地址
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getAllAddressListWithLevel")
	@ResponseBody
	public AjaxJson getAllAddressListWithLevel(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
       /* String sql = "select address.address, address.lon, address.lat, address.point_str pointStr, address.belong_layer belongLayer, address.id, min(rel.risk_level) risk_level from t_b_address_info address \n" +
                "left join (select * from t_b_danger_address_rel where risk_level in ("+Constants.RISK_LEVEL_HIDE_WHERE+")) rel\n" +
                "on address.id=rel.address_id\n" +
                "left join (select * from t_b_danger_source where YE_RISK_GRADE in ("+Constants.RISK_LEVEL_HIDE_WHERE+")) ds\n" +
                "on rel.danger_id=ds.id\n" +
                "and ds.is_delete='0' and ds.audit_status='4'\n" +
                "where address.is_delete='0' and address.isShow='Y'\n" +
                "group by address.address, address.lon, address.lat, address.id";*/
		String sql = "SELECT address.address, address.lon, address.lat, address.point_str pointStr, address.belong_layer belongLayer, address.id, min(rel.risk_level) risk_level FROM t_b_address_info address LEFT JOIN ( SELECT * FROM t_b_risk_identification WHERE risk_level IN ( SELECT t.typecode FROM t_s_type t WHERE t.typegroupid = ( SELECT g.id FROM t_s_typegroup g WHERE g.typegroupcode = 'riskLevel' ) AND t.is_hide = 0 )) rel ON address.id = rel.address_id INNER JOIN ( SELECT * FROM t_b_risk_identification WHERE risk_level IN ( SELECT t.typecode FROM t_s_type t WHERE t.typegroupid = ( SELECT g.id FROM t_s_typegroup g WHERE g.typegroupcode = 'riskLevel' ) AND t.is_hide = 0 )) ds ON rel.id = ds.id AND ds.STATUS = '3' and ds.is_del = '0' WHERE address.is_delete = '0' AND address.isShow = 'Y' GROUP BY address.address, address.lon, address.lat, address.id";

		List<Map<String,Object>> addressList = this.systemService.findForJdbc(sql);
		if(null!=addressList && addressList.size()>0){
			for(int i=0;i<addressList.size();i++){
				Map<String,Object> addressMap = addressList.get(i);
				String addressRiskLevel = (String)addressMap.get("risk_level");
				if(StringUtil.isEmpty(addressRiskLevel)){
					addressMap.put("risk_level","");
					addressMap.put("alertColor",Constants.ALERT_COLOR_NULL);
				} else {
					String riskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", addressRiskLevel);
					if(StringUtil.isEmpty(riskLevelTemp)){
						addressMap.put("risk_level","riskLevelTemp");
						addressMap.put("alertColor",Constants.ALERT_COLOR_NULL);
					} else {
						addressMap.put("risk_level","");
						if("重大风险".equals(riskLevelTemp)){
							addressMap.put("alertColor",Constants.ALERT_COLOR_ZDFX);
						}else if("较大风险".equals(riskLevelTemp)){
							addressMap.put("alertColor",Constants.ALERT_COLOR_JDFX);
						}else if("一般风险".equals(riskLevelTemp)){
							addressMap.put("alertColor",Constants.ALERT_COLOR_YBFX);
						}else{
							addressMap.put("alertColor",Constants.ALERT_COLOR_DFX);
						}
					}
				}

			}
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("addressList", addressList);
		j.setAttributes(resMap);
		return j;
	}

	/**
	 * 获取全部地址
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getQueryAddressListWithLevel")
	@ResponseBody
	public AjaxJson getQueryAddressListWithLevel(HttpServletRequest req) {
		StringBuffer riskLevelSql = new StringBuffer();
		String riskLevel1 = req.getParameter("riskLevel1");
		if(StringUtil.isNotEmpty(riskLevel1)){
			riskLevelSql.append("and (al.risklevel='1'");
			riskLevelSql.append("or al.risklevel='"+riskLevel1+"')");
		}else{
			riskLevelSql.append("and (al.risklevel='no')");
		}
		String riskLevel2 = req.getParameter("riskLevel2");
		if(StringUtil.isNotEmpty(riskLevel2)){
			riskLevelSql.deleteCharAt(riskLevelSql.length() - 1);
			riskLevelSql.append("or al.risklevel='"+riskLevel2+"')");
		}
		String riskLevel3 = req.getParameter("riskLevel3");
		if(StringUtil.isNotEmpty(riskLevel3)){
			riskLevelSql.deleteCharAt(riskLevelSql.length() - 1);
			riskLevelSql.append("or al.risklevel='"+riskLevel3+"')");
		}
		String riskLevel4 = req.getParameter("riskLevel4");
		if(StringUtil.isNotEmpty(riskLevel4)){
			riskLevelSql.deleteCharAt(riskLevelSql.length() - 1);
			riskLevelSql.append("or al.risklevel='"+riskLevel4+"')");
		}

		AjaxJson j = new AjaxJson();
		String sql = "select a.address address,a.lon lon, a.lat lat,a.point_str pointStr,  a.id, al.riskLevel risk_level from t_b_address_info a left join (\n" +
				"\tselect ar.address_id addressId, min(ar.risk_level) riskLevel from t_b_danger_address_rel ar, t_b_danger_source ds\n" +
				"\twhere ar.danger_id=ds.id and ds.is_delete!='1' and ds.audit_status='4' group by ar.address_id\n" +
				") al on a.id=al.addressId\n" +
				"where a.is_delete!='1' and a.isShowData = '1' " + riskLevelSql;

		List<Map<String,Object>> addressList = this.systemService.findForJdbc(sql);
		if(null!=addressList && addressList.size()>0){
			for(int i=0;i<addressList.size();i++){
				Map<String,Object> addressMap = addressList.get(i);
				String addressRiskLevel = (String)addressMap.get("risk_level");
				if(StringUtil.isEmpty(addressRiskLevel)){
					addressMap.put("risk_level","");
					addressMap.put("alertColor",Constants.ALERT_COLOR_NULL);
				} else {
					String riskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", addressRiskLevel);
					if(StringUtil.isEmpty(riskLevelTemp)){
						addressMap.put("risk_level","riskLevelTemp");
						addressMap.put("alertColor",Constants.ALERT_COLOR_NULL);
					} else {
						addressMap.put("risk_level","");
						if("重大风险".equals(riskLevelTemp)){
							addressMap.put("alertColor",Constants.ALERT_COLOR_ZDFX);
						}else if("较大风险".equals(riskLevelTemp)){
							addressMap.put("alertColor",Constants.ALERT_COLOR_JDFX);
						}else if("一般风险".equals(riskLevelTemp)){
							addressMap.put("alertColor",Constants.ALERT_COLOR_YBFX);
						}else{
							addressMap.put("alertColor",Constants.ALERT_COLOR_DFX);
						}
					}
				}

			}
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("addressList", addressList);
		j.setAttributes(resMap);
		return j;
	}


	/**
	 * 获取全部地址
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getAllAddressListWithLevel2")
	@ResponseBody
	public AjaxJson getAllAddressListWithLevel2(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> resMap = new HashMap<String, Object>();
		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
		cq.eq("isDelete", Constants.IS_DELETE_N);
		cq.eq("isshow",Constants.IS_SHOW_Y);
		cq.add();
		List<TBAddressInfoEntity> addressList = this.tBAddressInfoService
				.getListByCriteriaQuery(cq, false);
		if (addressList != null && addressList.size() > 0) {
			for (TBAddressInfoEntity entity : addressList) {
				//取关联风险的最高风险等级，根据字典，typecode越小风险等级越高
				String sql = "select risk_level from (select * from t_b_danger_address_rel where risk_level in ("+Constants.RISK_LEVEL_HIDE_WHERE+")) rel where address_id='"+entity.getId()+"' and risk_level is not null "+" and EXISTS(select * from t_b_danger_source ds where ds.id=danger_id and ds.is_delete='0' and ds.audit_status='4' and YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+"))  ORDER BY risk_level asc limit 1";
				List<String> tempList = systemService.findListbySql(sql);
				if(!tempList.isEmpty() && tempList.size()>0){
					String riskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", tempList.get(0));

					if("重大风险".equals(riskLevelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_ZDFX);
					}else if("较大风险".equals(riskLevelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_JDFX);
					}else if("一般风险".equals(riskLevelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_YBFX);
					}else{
						entity.setAlertColor(Constants.ALERT_COLOR_DFX);
					}
					entity.setRiskLevel(riskLevelTemp);
				}else{
					entity.setAlertColor(Constants.ALERT_COLOR_NULL);
					entity.setRiskLevel("");
				}
			}
		}
		resMap.put("addressList", addressList);
		j.setAttributes(resMap);
		return j;
	}
	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
		tBAddressInfo.setAddress(null);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAddressInfo, request.getParameterMap());
		try{
			//自定义追加查询条件
			String address = request.getParameter("address");
			if (StringUtil.isNotEmpty(address)) {
				cq.like("address", "%"+address+"%");
			}
			cq.eq("isDelete", Constants.IS_DELETE_N);
			cq.eq("isShowData", Constants.IS_SHOWDATA_Y);
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
			String sunSql = "select column_id from t_b_sunshine where table_name='t_b_address_info'";
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
		this.systemService.getDataGridReturn(cq, true);
		List<TBAddressInfoEntity> list = dataGrid.getResults();
		setDangerSourceCountData(list);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "riskAlertDatagrid")
	public void riskAlertDatagrid(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
		tBAddressInfo.setAddress(null);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAddressInfo, request.getParameterMap());
		try{
			//自定义追加查询条件
			String address = request.getParameter("address");
			cq.eq("isShowData","1");
			if (StringUtil.isNotEmpty(address)) {
				cq.like("address", "%"+address+"%");
			}
			cq.eq("isDelete", Constants.IS_DELETE_N);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBAddressInfoService.getDataGridReturn(cq, true);
		List<TBAddressInfoEntity> list = dataGrid.getResults();
		if (list != null && list.size() > 0) {
			for (TBAddressInfoEntity entity : list) {
				//取关联风险的最高风险等级，根据字典，typecode越小风险等级越高
				String sql = "SELECT b.risk_level FROM t_b_address_info a LEFT JOIN ( SELECT address_id, risk_level FROM t_b_risk_identification c where c.status = '3' and c.is_del = '0') b ON a.id = b.address_id WHERE a.id = '"+entity.getId()+"'";

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
					sql += " and ds.id not in (select column_id from t_b_sunshine where table_name = 't_b_danger_source') ";
				}
				/*************************************************************/

				sql += " ORDER BY risk_level asc limit 1";
				List<String> tempList = systemService.findListbySql(sql);
				if(!tempList.isEmpty() && tempList.size()>0){
					String riskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", tempList.get(0));

					if("重大风险".equals(riskLevelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_ZDFX);
					}else if("较大风险".equals(riskLevelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_JDFX);
					}else if("一般风险".equals(riskLevelTemp)){
						entity.setAlertColor(Constants.ALERT_COLOR_YBFX);
					}else{
						entity.setAlertColor(Constants.ALERT_COLOR_DFX);
					}
					entity.setRiskLevel(riskLevelTemp);
				}else {
					entity.setAlertColor(Constants.ALERT_COLOR_NULL);
					entity.setRiskLevel("");
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "riskQueryAlertDatagrid")
	public void riskQueryAlertDatagrid(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sql = "select a.id, a.address, al.riskLevel riskLevel from t_b_address_info a left join (\n" +
				"\tselect ar.address_id addressId, min(ar.risk_level) riskLevel from t_b_danger_address_rel ar, t_b_danger_source ds\n" +
				"\twhere ar.danger_id=ds.id and ds.is_delete!='1' and ds.audit_status='4' group by ar.address_id\n" +
				") al on a.id=al.addressId\n" +
				"where a.is_delete!='1' and a.isShowData = '1'";
		String address = request.getParameter("address");
		if(StringUtil.isNotEmpty(address)){
			sql = sql + " and a.address like '%" + address + "%'" ;
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
			sql += " and ds.id not in (select column_id from t_b_sunshine where table_name = 't_b_danger_source') ";
		}
		/*************************************************************/
		List<Map<String,Object>> list = this.systemService.findForJdbc(sql);
		if (list != null && list.size() > 0) {
			for (Map<String,Object> map : list) {
				String riskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", (String)map.get("riskLevel"));
				if("重大风险".equals(riskLevelTemp)){
					map.put("alertColor", Constants.ALERT_COLOR_ZDFX);
				}else if("较大风险".equals(riskLevelTemp)){
					map.put("alertColor",Constants.ALERT_COLOR_JDFX);
				}else if("一般风险".equals(riskLevelTemp)){
					map.put("alertColor",Constants.ALERT_COLOR_YBFX);
				}else if("低风险".equals(riskLevelTemp)){
					map.put("alertColor",Constants.ALERT_COLOR_DFX);
				} else {
					map.put("alertColor",Constants.ALERT_COLOR_NULL);
				}
				map.put("riskLevel",riskLevelTemp);
			}
		}

		dataGrid.setTotal(list.size());
		int pageSize = dataGrid.getRows();
		int curPage = dataGrid.getPage();
		int endIndex = pageSize*curPage;
		if(endIndex>list.size()){
			endIndex=list.size();
		}
		dataGrid.setResults(list.subList(pageSize*(curPage-1),endIndex));
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * 删除风险点列表
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBAddressInfoEntity tBAddressInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBAddressInfo = systemService.getEntity(TBAddressInfoEntity.class, tBAddressInfo.getId());
		message = "风险点列表删除成功";
		try{
			tBAddressInfo.setIsDelete(Constants.IS_DELETE_Y);
			tBAddressInfo.setStateFlag(Constants.GJJ_STATE_FLAG_3);
			tBAddressInfoService.updateEntitie(tBAddressInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "风险点列表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除风险点列表
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request, HttpServletResponse response){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "风险点列表删除成功";
		try{
			String ezhuang = ResourceUtil.getConfigByName("ezhuang");
			for(String id:ids.split(",")){
				TBAddressInfoEntity tBAddressInfo = systemService.getEntity(TBAddressInfoEntity.class, id);
				tBAddressInfo.setIsDelete(Constants.IS_DELETE_Y);
				tBAddressInfo.setStateFlag(Constants.GJJ_STATE_FLAG_3);
				tBAddressInfoService.updateEntitie(tBAddressInfo);
				if(StringUtil.isNotEmpty(ezhuang)){
					if (ezhuang.equals("true")){
						String sql = "UPDATE t_b_risk_identification set is_del = '1'  WHERE address_id = '"+id+"'";
						systemService.executeSql(sql);
					}
				}
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

				//删除阳光账号列表的关联数据
				List<TBSunshineEntity> tbSunshineEntityList = systemService.findByProperty(TBSunshineEntity.class, "columnId", id);
				if (tbSunshineEntityList!=null && tbSunshineEntityList.size()>0){
					systemService.deleteAllEntitie(tbSunshineEntityList);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "风险点列表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加风险点列表
	 *
	 * @param tBAddressInfo
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBAddressInfoEntity tBAddressInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "风险点列表添加成功";
		try{
			CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
			cq.eq("address", tBAddressInfo.getAddress());
			cq.notEq("isDelete", Constants.IS_DELETE_Y);
			cq.add();
			List<TBAddressInfoEntity>  list = tBAddressInfoService.getListByCriteriaQuery(cq, false);

			if (list != null && list.size()>0) {
				if (Constants.IS_DELETE_Y.equals(tBAddressInfo.getIsDelete())) {
					tBAddressInfo.setId(list.get(0).getId());
					tBAddressInfo.setIsDelete(Constants.IS_DELETE_N);
					tBAddressInfoService.updateEntitie(tBAddressInfo);
				} else {
					message = "此风险点已存在";
				}
			} else {
				tBAddressInfo.setIsDelete(Constants.IS_DELETE_N);
				tBAddressInfo.setIsShowData(Constants.IS_SHOWDATA_Y);

				tBAddressInfo.setStateFlag(Constants.GJJ_STATE_FLAG_1);
				//判断该图层是否有图形文件，没有则新增，有则保存id
				String existPicture = sfService.isExistPicture(tBAddressInfo.getBelongLayer().getId());
				if (StringUtils.isNotBlank(existPicture)){
					tBAddressInfo.setPictureCode(Integer.parseInt(existPicture));
				}else {
					SFPictureInfoEntity sfPictureInfoEntity = sfService.savePictureInfo(tBAddressInfo.getBelongLayer());
					tBAddressInfo.setPictureCode(sfPictureInfoEntity.getId());
				}
				tBAddressInfoService.save(tBAddressInfo);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				//如果添加一个之前删除的风险点 风险地点ID更新为新添加的风险点id
				String sql = "SELECT id FROM t_b_address_info WHERE is_delete = '1' and address = '"+tBAddressInfo.getAddress()+"'";
				List<String> addressList = systemService.findListbySql(sql);
				StringBuffer idSb = new StringBuffer();
				if (addressList != null && addressList.size() > 0) {
					for (String id: addressList) {
						if (StringUtil.isNotEmpty(idSb.toString())) {
							idSb.append("','");
						}
						idSb.append(id);
					}
				}
				String updateRiskSql = "update t_b_risk_identification SET address_id = '"+tBAddressInfo.getId()+"' WHERE address_id in ('"+idSb.toString()+"');";
				systemService.executeSql(updateRiskSql);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "风险点列表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新风险点列表
	 *
	 * @param tBAddressInfo
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBAddressInfoEntity tBAddressInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "风险点列表更新成功";
		TBAddressInfoEntity t = tBAddressInfoService.get(TBAddressInfoEntity.class, tBAddressInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBAddressInfo, t);

			if (Constants.GJJ_STATE_FLAG_0.equals(t.getStateFlag())){
				t.setStateFlag(Constants.GJJ_STATE_FLAG_2);
			}
			//判断该图层是否有图形文件，没有则新增，有则保存id
			String existPicture = sfService.isExistPicture(t.getBelongLayer().getId());
			if (StringUtils.isNotBlank(existPicture)){
				t.setPictureCode(Integer.parseInt(existPicture));
			}else {
				SFPictureInfoEntity sfPictureInfoEntity = sfService.savePictureInfo(t.getBelongLayer());
				t.setPictureCode(sfPictureInfoEntity.getId());
			}
			tBAddressInfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "风险点列表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 风险点列表新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBAddressInfoEntity tBAddressInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAddressInfo.getId())) {
			tBAddressInfo = tBAddressInfoService.getEntity(TBAddressInfoEntity.class, tBAddressInfo.getId());
		}
		req.setAttribute("tBAddressInfoPage", tBAddressInfo);
		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfo-add");
	}

	/**
	 * 风险点列表编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBAddressInfoEntity tBAddressInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAddressInfo.getId())) {
			tBAddressInfo = tBAddressInfoService.getEntity(TBAddressInfoEntity.class, tBAddressInfo.getId());
			req.setAttribute("tBAddressInfoPage", tBAddressInfo);
		}
		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfo-update");
	}
	/**
	 * 风险点台账批量 编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goBatchUpdate")
	public ModelAndView goBatchUpdate(TBAddressInfoEntity tBAddressInfo, HttpServletRequest req) {
		String ids =req.getParameter("ids");
		req.setAttribute("ids", ids);
		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfo-doBatchUpdate");
	}
	/**
	 * 批量编辑风险点台账
	 *
	 * @param tBAddressInfo
	 * @return
	 */
	@RequestMapping(params = "doBatchUpdate")
	@ResponseBody
	public AjaxJson doBatchUpdate(TBAddressInfoEntity tBAddressInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		String ids=request.getParameter("ids");

		try {
			for(String id:ids.split(",")){
				TBAddressInfoEntity t=systemService.getEntity(TBAddressInfoEntity.class,id);
				if(StringUtils.isNotBlank(tBAddressInfo.getManageUnit())){
					t.setManageUnit(tBAddressInfo.getManageUnit());
				}
				if(StringUtils.isNotBlank(tBAddressInfo.getManageMan())){
					t.setManageMan(tBAddressInfo.getManageMan());
				}
				if(StringUtils.isNotBlank(tBAddressInfo.getRiskType())){
					t.setRiskType(tBAddressInfo.getRiskType());
				}
				if(tBAddressInfo.getInvestigationDate()!=null){
					t.setInvestigationDate(tBAddressInfo.getInvestigationDate());
				}
				if(tBAddressInfo.getStartDate()!=null){
					t.setStartDate(tBAddressInfo.getStartDate());
				}
				if(tBAddressInfo.getEndDate()!=null){
					t.setEndDate(tBAddressInfo.getEndDate());
				}
				if(StringUtils.isNotBlank(tBAddressInfo.getRemark())){
					t.setRemark(tBAddressInfo.getRemark());
				}
				tBAddressInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}


		} catch (Exception e) {
			e.printStackTrace();
			message = "风险点列表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBAddressInfoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 风险点预警详情页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "detailWithRiskHiddenVio_new")
	public ModelAndView detailWithRiskHiddenVio_new(TBAddressInfoEntity tBAddressInfo, String addressId, HttpServletRequest req) {
		String type = req.getParameter("type");
		if (StringUtil.isNotEmpty(tBAddressInfo.getId())) {
			tBAddressInfo = tBAddressInfoService.getEntity(TBAddressInfoEntity.class, tBAddressInfo.getId());
		}
		if (StringUtil.isNotEmpty(addressId)) {
			tBAddressInfo = tBAddressInfoService.getEntity(TBAddressInfoEntity.class, addressId);
		}
		/*//修改为取只有隐患关联月度风险的关联数据2018-08-15
		String sql = "select risk_level from t_b_danger_address_rel a where address_id='"+tBAddressInfo.getId()+"' and risk_level is not null " + " and a.danger_id in " +
				"(SELECT b.id FROM t_b_hidden_danger_exam d LEFT JOIN t_b_danger_source b on d.danger_id = b.id where  d.address = '"+tBAddressInfo.getId()+"'  and b.is_delete='0') order by risk_level asc LIMIT 1";

		List<String> tempList = systemService.findListbySql(sql);
		//edit by linsen 如果是静态风险，则等级直接从t_b_danger_source取
		if ("static".equals(type)) {
			sql = "SELECT b.ye_risk_grade from t_b_danger_address_rel a left join (SELECT * from t_b_danger_source c ) b on a.danger_id=b.id WHERE a.address_id='"+tBAddressInfo.getId()+"' ORDER BY b.ye_risk_grade asc LIMIT 1";
			tempList = systemService.findListbySql(sql);
		}*/
		String sql = "";
		List<String> tempList = new ArrayList<>();
		//动态风险等级

		Map<String, Object> dynamicLevel = this.tBAddressInfoService.getDynamicLevel();
		String riskLevel = String.valueOf(dynamicLevel.get(tBAddressInfo.getId()));
		if(StringUtil.isNotEmpty(riskLevel)){
			tempList.add(riskLevel);
		}
		//edit by linsen 如果是静态风险，则等级直接从t_b_danger_source取
		if ("static".equals(type)) {
			sql = "SELECT b.risk_level FROM t_b_address_info a LEFT JOIN ( SELECT address_id, risk_level FROM t_b_risk_identification c where c.is_del = '0' and c.status = '3' ) b ON a.id = b.address_id WHERE a.id = '"+tBAddressInfo.getId()+"' ORDER BY b.risk_level ASC LIMIT 1";
			tempList = systemService.findListbySql(sql);
		}
		if (!tempList.isEmpty() && tempList.size() > 0) {
			String riskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", tempList.get(0));
			tBAddressInfo.setRiskLevel(riskLevelTemp);

			if("重大风险".equals(riskLevelTemp)){
				req.setAttribute("riskColor", Constants.ALERT_COLOR_ZDFX);
			}else if("较大风险".equals(riskLevelTemp)){
				req.setAttribute("riskColor", Constants.ALERT_COLOR_JDFX);
			}else if("一般风险".equals(riskLevelTemp)){
				req.setAttribute("riskColor", Constants.ALERT_COLOR_YBFX);
			}else{
				req.setAttribute("riskColor", Constants.ALERT_COLOR_DFX);
			}
		}else{
			//没关联就显示低风险
			req.setAttribute("riskColor", Constants.ALERT_COLOR_DFX);
		}

		/*sql = "SELECT dept.departname from t_b_address_depart_rel rel LEFT JOIN t_s_depart dept on dept.id=rel.depart_id where rel.address_id='" + tBAddressInfo.getId() + "'";
		List<String> deptNameList = systemService.findListbySql(sql);
		StringBuffer sb = new StringBuffer();
		if (!deptNameList.isEmpty() && deptNameList.size() > 0) {
			for (String deptName : deptNameList) {
				if (sb.toString().length() > 0) {
					sb.append(",");
				}
				sb.append(deptName);
			}
		}
		req.setAttribute("dutyDeptNames", sb.toString());*/

		/*sql = "select a.count countRisk,b.count countVio,c.count countHidden,d.count countMajor from ";
		sql += " (select count(distinct(danger_id)) count from (select * from t_b_danger_address_rel where risk_level in("+Constants.RISK_LEVEL_HIDE_WHERE+")) t where address_id='" + tBAddressInfo.getId() + "'  and EXISTS(select * from t_b_danger_source ds where ds.id=danger_id and ds.is_delete='0' and ds.YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+"))) a,";
		sql += " (select count(id) count from " +
				//edit by linsen 三违数量对应表格
				//"t_b_three_violations where vio_address='" + tBAddressInfo.getId() + "' and VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ) b,";
				"t_b_three_violations where vio_address='" + tBAddressInfo.getId() + "' ) b,";
		//sql += " (select count(hdh.id) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hde.id=hdh.hidden_danger_id ";
		//sql += " where hdh.handlel_status <> ('"+Constants.HANDELSTATUS_DRAFT+"') and hde.address='" + tBAddressInfo.getId() + "') c,";
		//过滤掉年度的隐患--20180815
		sql += " (select count(hdh.id) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hde.id=hdh.hidden_danger_id left join t_b_danger_source sou on hde.danger_id=sou.id ";
		sql += " where hdh.handlel_status <> ('"+Constants.HANDELSTATUS_DRAFT+"')  and hde.address='" + tBAddressInfo.getId() + "') c,";

		sql += " (select count(id) count from t_b_major_hidden_danger where hd_location='"+tBAddressInfo.getId()+"' and cl_status in ('200','300','400','500') ) d";*/
		sql = "select a.count countRisk,c.count countHidden  from ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sql += " (SELECT count(id) count from t_b_risk_identification WHERE address_id = '"+tBAddressInfo.getId()+"' and status= '3' and is_del = '0' and  identifi_date <= '"+sdf.format(new Date())+"'  and (exp_date >= '"+sdf.format(new Date())+"' or exp_date is null))  a,";
		sql += " (select count(hdh.id) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hde.id=hdh.hidden_danger_id ";
		sql += " where hdh.handlel_status IN ('1', '4')  and hde.address='" + tBAddressInfo.getId() + "') c";
		List<Map<String, Object>> countList = systemService.findForJdbc(sql);
		if (!countList.isEmpty() && countList.size() > 0) {
			Map map = countList.get(0);
			req.setAttribute("countRisk", map.get("countRisk"));
			req.setAttribute("countHidden", map.get("countHidden"));
			req.setAttribute("countVio", map.get("countVio"));
			req.setAttribute("countMajor", map.get("countMajor"));
		}

		req.setAttribute("tBAddressInfoPage", tBAddressInfo);
		if ("static".equals(type)) {
			return new ModelAndView("com/sdzk/buss/web/riskalert/addressRiskStaticDetail");
		}
		if ("all".equals(type)) {
			return new ModelAndView("com/sdzk/buss/web/riskalert/addressRiskHiddenDetail");
		}
		return new ModelAndView("com/sdzk/buss/web/riskalert/addressRiskDetail_new");
	}


	/**
	 * 风险点预警详情页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "detailWithRiskHiddenVio")
	public ModelAndView detailWithRiskHiddenVio(TBAddressInfoEntity tBAddressInfo, String addressId, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAddressInfo.getId())) {
			tBAddressInfo = tBAddressInfoService.getEntity(TBAddressInfoEntity.class, tBAddressInfo.getId());
		}
		if (StringUtil.isNotEmpty(addressId)) {
			tBAddressInfo = tBAddressInfoService.getEntity(TBAddressInfoEntity.class, addressId);
		}
		String sql = "select risk_level from (select * from t_b_danger_address_rel where risk_level in("+Constants.RISK_LEVEL_HIDE_WHERE+")) t where address_id='" + tBAddressInfo.getId() + "'  and EXISTS(select * from t_b_danger_source ds where ds.id=danger_id and ds.is_delete='0' and ds.audit_status='4') ORDER BY risk_level asc limit 1 ";
		List<String> tempList = systemService.findListbySql(sql);
		if (!tempList.isEmpty() && tempList.size() > 0) {
			String riskLevelTemp = DicUtil.getTypeNameByCode("riskLevel", tempList.get(0));
			tBAddressInfo.setRiskLevel(riskLevelTemp);

			if("重大风险".equals(riskLevelTemp)){
				req.setAttribute("riskColor", Constants.ALERT_COLOR_ZDFX);
			}else if("较大风险".equals(riskLevelTemp)){
				req.setAttribute("riskColor", Constants.ALERT_COLOR_JDFX);
			}else if("一般风险".equals(riskLevelTemp)){
				req.setAttribute("riskColor", Constants.ALERT_COLOR_YBFX);
			}else{
				req.setAttribute("riskColor", Constants.ALERT_COLOR_DFX);
			}
		}

		sql = "SELECT dept.departname from t_b_address_depart_rel rel LEFT JOIN t_s_depart dept on dept.id=rel.depart_id where rel.address_id='" + tBAddressInfo.getId() + "'";
		List<String> deptNameList = systemService.findListbySql(sql);
		StringBuffer sb = new StringBuffer();
		if (!deptNameList.isEmpty() && deptNameList.size() > 0) {
			for (String deptName : deptNameList) {
				if (sb.toString().length() > 0) {
					sb.append(",");
				}
				sb.append(deptName);
			}
		}
		req.setAttribute("dutyDeptNames", sb.toString());

		sql = "select a.count countRisk,b.count countVio,c.count countHidden,d.count countMajor from ";
		sql += " (select count(distinct(danger_id)) count from (select * from t_b_danger_address_rel where risk_level in("+Constants.RISK_LEVEL_HIDE_WHERE+")) t where address_id='" + tBAddressInfo.getId() + "'  and EXISTS(select * from t_b_danger_source ds where ds.id=danger_id and ds.is_delete='0' and ds.audit_status='4' and ds.YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+"))) a,";
		sql += " (select count(id) count from " +
				"t_b_three_violations where vio_address='" + tBAddressInfo.getId() + "' and VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ) b,";
		sql += " (select count(hdh.id) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hde.id=hdh.hidden_danger_id ";
		sql += " where hdh.handlel_status in ('" + Constants.HANDELSTATUS_REPORT + "','" + Constants.HANDELSTATUS_REVIEW + "','" + Constants.HANDELSTATUS_ROLLBACK_CHECK + "') and hde.address='" + tBAddressInfo.getId() + "') c,";
		sql += " (select count(id) count from t_b_major_hidden_danger where hd_location='"+tBAddressInfo.getId()+"' and cl_status in ('200','300','400','500') ) d";
		List<Map<String, Object>> countList = systemService.findForJdbc(sql);
		if (!countList.isEmpty() && countList.size() > 0) {
			Map map = countList.get(0);
			req.setAttribute("countRisk", map.get("countRisk"));
			req.setAttribute("countHidden", map.get("countHidden"));
			req.setAttribute("countVio", map.get("countVio"));
			req.setAttribute("countMajor", map.get("countMajor"));
		}

		req.setAttribute("tBAddressInfoPage", tBAddressInfo);
		return new ModelAndView("com/sdzk/buss/web/riskalert/addressRiskDetail");
	}

	/**
	 * 风险点克隆页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goClone")
	public ModelAndView goClone(TBAddressInfoEntity tBAddressInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAddressInfo.getId())) {
			tBAddressInfo = tBAddressInfoService.getEntity(TBAddressInfoEntity.class, tBAddressInfo.getId());
			req.setAttribute("tBAddressInfoPage", tBAddressInfo);
		}
		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfo-clone");
	}

	/**
	 * 克隆风险点
	 *
	 * @param tBAddressInfo
	 * @return
	 */
	@RequestMapping(params = "doClone")
	@ResponseBody
	public AjaxJson doClone(TBAddressInfoEntity tBAddressInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "风险点克隆成功";
		String id=tBAddressInfo.getId();
		String newAddress=tBAddressInfo.getAddress();
		String idOld = request.getParameter("idOld");
		tBAddressInfo = systemService.getEntity(TBAddressInfoEntity.class, idOld);

		TBAddressInfoEntity newAddressInfo = new TBAddressInfoEntity();
		try{
			CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
			cq.eq("address", newAddress);
			cq.notEq("isDelete", Constants.IS_DELETE_Y);
			cq.add();
			List<TBAddressInfoEntity>  list = tBAddressInfoService.getListByCriteriaQuery(cq, false);
			if (list != null && list.size()>0) {
				message = "此风险点已存在";
			} else {
				newAddressInfo.setId(id);
				newAddressInfo.setIsDelete(Constants.IS_DELETE_N);
				newAddressInfo.setAddress(newAddress);
				newAddressInfo.setCate(tBAddressInfo.getCate());
				newAddressInfo.setDangerSourceCount(tBAddressInfo.getDangerSourceCount());
				newAddressInfo.setDescription("");
				newAddressInfo.setIsshow(tBAddressInfo.getIsshow());
				newAddressInfo.setLat(tBAddressInfo.getLat());
				newAddressInfo.setLon(tBAddressInfo.getLon());
				newAddressInfo.setPointStr(tBAddressInfo.getPointStr());
				newAddressInfo.setBelongLayer(tBAddressInfo.getBelongLayer());
				tBAddressInfoService.save(newAddressInfo);

				//复制风险点关联风险
				List<TBDangerAddresstRelEntity> addressRelList = tBAddressInfoService.findByProperty(TBDangerAddresstRelEntity.class, "addressId", tBAddressInfo.getId());
				if(null!=addressRelList && addressRelList.size()>0){
					TBDangerAddresstRelEntity entity = null;
					List<TBDangerAddresstRelEntity> newAddressRelList = new ArrayList<TBDangerAddresstRelEntity>();
					for(int i = 0 ; i < addressRelList.size(); i++) {
						TBDangerAddresstRelEntity addressRel = addressRelList.get(i);
						entity = new TBDangerAddresstRelEntity();
						entity.setAddressId(newAddressInfo.getId());
						entity.setDangerId(addressRel.getDangerId());
						entity.setRiskLevel(addressRel.getRiskLevel());
						newAddressRelList.add(entity);
					}
					this.systemService.batchSave(newAddressRelList);
				}

				//同步到云平台
				syncToCloudService.addressRiskRelReport(newAddressInfo.getId());

				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "风险点克隆失败";
			throw new BusinessException(e.getMessage());
		}

		j.setMsg(message);
		return j;
	}
	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		String ids = request.getParameter("ids");

		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
		if(StringUtils.isNotBlank(ids)){
			cq.in("id",ids.split(","));
		}else {
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAddressInfo, request.getParameterMap());
			cq.eq("isDelete", Constants.IS_DELETE_N);
		}
		cq.add();
		List<TBAddressInfoEntity> tBAddressInfos = this.tBAddressInfoService.getListByCriteriaQuery(cq,false);
		for(TBAddressInfoEntity t : tBAddressInfos){
			if(StringUtil.isNotEmpty(t.getRiskType())){
				StringBuffer riskTypeName = new StringBuffer();
				for (String code:t.getRiskType().split(",")){
					if(StringUtils.isNotBlank(riskTypeName.toString())) {
						riskTypeName.append(",");
						riskTypeName.append(DicUtil.getTypeNameByCode("risk_type", code));
					}else{
						riskTypeName.append(DicUtil.getTypeNameByCode("risk_type", code));
					}
				}
				t.setRiskTypeTemp(riskTypeName.toString());
			}

		}

		TemplateExportParams templateExportParams = new TemplateExportParams();
		templateExportParams.setSheetNum(0);
		templateExportParams.setSheetName("风险点台账");
		templateExportParams.setTemplateUrl("export/template/exportTemp_address.xls");
		modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
		Map<String,List<TBAddressInfoEntity>> map = new HashMap<String,List<TBAddressInfoEntity>>();
		map.put("list", tBAddressInfos);
		modelMap.put(NormalExcelConstants.FILE_NAME,"风险点台账");
		modelMap.put(TemplateExcelConstants.MAP_DATA,map);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
		/*//导出前设置危险源数量
		setDangerSourceCountData(tBAddressInfos);
		//导出前设置责任单位和责任人
		if(tBAddressInfos!=null && tBAddressInfos.size()>0){
			for(int i=0;i<tBAddressInfos.size();i++){
				List<TBAddressDepartRelEntity> departRelEntities = systemService.findByProperty(TBAddressDepartRelEntity.class,"addressId",tBAddressInfos.get(i).getId());
				for(int j=0;j<departRelEntities.size();j++){
					TBAddressDepartRelEntity entity = departRelEntities.get(j);
					TSDepart d = systemService.findUniqueByProperty(TSDepart.class,"id",entity.getDepartId());
					if(d!=null && StringUtils.isNotBlank(d.getDepartname())){
						entity.setDepartName(d.getDepartname());
					}
				}
				tBAddressInfos.get(i).setDepartRelEntityList(departRelEntities);
			}
		}

		modelMap.put(NormalExcelConstants.FILE_NAME,"风险点列表");
		modelMap.put(NormalExcelConstants.CLASS,TBAddressInfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("风险点列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));

		modelMap.put(NormalExcelConstants.DATA_LIST,tBAddressInfos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;*/
	}

	private void setDangerSourceCountData(List<TBAddressInfoEntity> tBAddressInfos) {
		if (tBAddressInfos  != null && tBAddressInfos.size() > 0) {
			StringBuffer ids = new StringBuffer();
			for (TBAddressInfoEntity entity : tBAddressInfos ) {
				if (StringUtil.isNotEmpty(ids.toString())) {
					ids.append(",");
				}
				ids.append("'").append(entity.getId()).append("'") ;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String sql = "select count(1) total, address_id from t_b_risk_identification where address_id in ("+ids.toString()+")  and status = '"+ com.sddb.common.Constants.RISK_IDENTIFI_STATUS_REVIEW +"' and is_del = '"+ com.sddb.common.Constants.RISK_IS_DEL_FALSE +"' and identifi_date <= '"+sdf.format(new Date())+"'  and (exp_date >= '"+sdf.format(new Date())+"' or exp_date is null)  GROUP BY address_id";

			List<Map<String, Object>> result = systemService.findForJdbc(sql);
			Map<String, String> countMap = new HashMap<>();
			if (result != null && result.size() > 0) {
				for (Map<String, Object> obj : result) {
					if (obj.get("total") != null && obj.get("address_id") != null) {
						countMap.put(obj.get("address_id").toString(), obj.get("total").toString());
					}
				}
			}
			for (TBAddressInfoEntity entity : tBAddressInfos ) {
				entity.setDangerSourceCount(countMap.get(entity.getId())!=null?countMap.get(entity.getId()):"0");
				if(StringUtil.isNotEmpty(entity.getInvestigationDate())&&StringUtil.isNotEmpty(entity.getStartDate())
						&&StringUtil.isNotEmpty(entity.getEndDate())){
					entity.setIsAll("0");
				}else{
					entity.setIsAll("1");
				}
			}
		}
	}

	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME,"风险点列表");
		modelMap.put(NormalExcelConstants.CLASS,TBAddressInfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("风险点列表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TBAddressInfoEntity> listTBAddressInfoEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBAddressInfoEntity.class,params);
				for (TBAddressInfoEntity tBAddressInfo : listTBAddressInfoEntitys) {
					tBAddressInfoService.save(tBAddressInfo);
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
	 * 更改风险点坐标
	 *
	 * @param tBAddressInfo
	 * @return
	 */
	@RequestMapping(params = "doUpdateCoordinate")
	@ResponseBody
	public AjaxJson doUpdateCoordinate(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "坐标更新成功";
		String addressId = request.getParameter("addressId");
		String lon = request.getParameter("lng");
		String lat = request.getParameter("lat");
		if(StringUtil.isNotEmpty(lon) && StringUtil.isNotEmpty(lat)){
			TBAddressInfoEntity t = tBAddressInfoService.get(TBAddressInfoEntity.class, addressId);
			tBAddressInfo.setLon(lon);
			tBAddressInfo.setLat(lat);
			String smLayer = request.getParameter("layerID");
			if(StringUtil.isNotEmpty(smLayer)){
				TBLayerEntity tbLayerEntity = this.systemService.getEntity(TBLayerEntity.class,smLayer);
				if(tbLayerEntity != null){
					tBAddressInfo.setBelongLayer(tbLayerEntity);
				}
			}
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tBAddressInfo, t);
				tBAddressInfoService.saveOrUpdate(t);
				systemService.addLog("风险点\""+t.getId()+"\"坐标更新成功", Globals.Log_Leavel_INFO,Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "坐标更新失败";
				systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_UPDATE);
				throw new BusinessException(e.getMessage());
			}
		}else{
			message = "坐标更新失败";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 *
	 * @Description: 表单验证
	 * @author guoteng
	 * @date 2017-07-26
	 * @version V1.0
	 *
	 */
	@RequestMapping(params = "addressExists")
	@ResponseBody
	public Map<String, String> addressExists(HttpServletRequest request){
		Map<String, String> result = new HashMap();
		String address = ResourceUtil.getParameter("param");
		String id = ResourceUtil.getParameter("id");
		if (checkAddressExists(address,id)){
			result.put("status", "n");
			result.put("info","此风险点已存在！");
		} else {
			result.put("status", "y");
			result.put("info","通过信息验证！");
		}
		return result;
	}
	private boolean checkAddressExists(String address,String id){
		List<TBAddressInfoEntity> entitys = systemService.findByProperty(TBAddressInfoEntity.class, "address", address);
		if (entitys != null && entitys.size() > 0 && !Constants.IS_DELETE_Y.equals(entitys.get(0).getIsDelete()) && !entitys.get(0).getId().equals(id)) {
			return true;
		}
		return false;
	}


	/**
	 * 风险点信息查看页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goDetail")
	public ModelAndView goDetail(TBAddressInfoEntity tBAddressInfo,
								 HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAddressInfo.getId())) {
			tBAddressInfo = tBAddressInfoService.getEntity(
					TBAddressInfoEntity.class, tBAddressInfo.getId());
			if (StringUtil.isNotEmpty(tBAddressInfo.getCate())) {
				tBAddressInfo.setCate(DicUtil.getTypeNameByCode("locationCate", tBAddressInfo.getCate()));
			}
			if (StringUtil.isNotEmpty(tBAddressInfo.getIsshow())) {
				//tBAddressInfo.setIsshow(DicUtil.getTypeNameByCode("sf_yn", tBAddressInfo.getIsshow()));
			}
			if(StringUtil.isNotEmpty(tBAddressInfo.getRiskType())){
				StringBuffer riskTypeName = new StringBuffer();
				for (String code:tBAddressInfo.getRiskType().split(",")){
					if(StringUtils.isNotBlank(riskTypeName.toString())) {
						riskTypeName.append(",");
						riskTypeName.append(DicUtil.getTypeNameByCode("risk_type", code));
					}else{
						riskTypeName.append(DicUtil.getTypeNameByCode("risk_type", code));
					}
				}
				tBAddressInfo.setRiskTypeTemp(riskTypeName.toString());
			}
			req.setAttribute("tBAddressInfoPage", tBAddressInfo);


		}
		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfo-detail");
	}


	/**
	 * 隐患排查关联危险点 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "chooseInveRiskPoint")
	public ModelAndView chooseInveRiskPoint(HttpServletRequest request) {
		request.setAttribute("ids", ResourceUtil.getParameter("ids"));
		request.setAttribute("month", ResourceUtil.getParameter("month"));
		request.setAttribute("from", ResourceUtil.getParameter("from"));
		return new ModelAndView("com/sdzk/buss/web/address/chooseInveRiskPointList");
	}

	@RequestMapping(params = "reportGroup")
	@ResponseBody
	public AjaxJson reportGroup(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ids = request.getParameter("ids");
		AjaxJson j = new AjaxJson();
		List<Map<String,Object>> reportList = new ArrayList<Map<String,Object>>();
		if(StringUtils.isNotBlank(ids)){
			String idArray[] = ids.split(",");

			for(String id : idArray){
				TBAddressInfoEntity bean = systemService.getEntity(TBAddressInfoEntity.class,id);
				List<TBDangerSourceReportVO> dangerList = new ArrayList<TBDangerSourceReportVO>();
				CriteriaQuery cq = new CriteriaQuery(TBDangerAddresstRelEntity.class);
				try{
					cq.eq("addressId",id);
				}catch(Exception e){
					e.printStackTrace();
				}
				cq.add();
				List<TBDangerAddresstRelEntity> relList = systemService.getListByCriteriaQuery(cq,false);
				if(relList != null){


					for(TBDangerAddresstRelEntity rel: relList ){
						String dangerId = rel.getDangerId();
						TBDangerSourceEntity danger = systemService.getEntity(TBDangerSourceEntity.class,dangerId);
						danger = danger == null? new TBDangerSourceEntity():danger;
						TBDangerSourceReportVO vo = new TBDangerSourceReportVO();
						vo.setId(danger.getId());
						vo.setIsMajor(danger.getIsMajor());
						String yeAccident = danger.getYeAccident();
						if(StringUtils.isNotBlank(yeAccident)){
							String array[] = yeAccident.split(",");
							StringBuffer sb = new StringBuffer();
							for(String str : array){
								String nameTemp = DicUtil.getTypeNameByCode("accidentCate",str);
								if(StringUtils.isNotBlank(sb.toString())){
									sb.append(",");
								}
								sb.append(nameTemp);
							}
							vo.setYeAccident(sb.toString());
						}else{
							vo.setYeAccident("");
						}
						String yeCost = danger.getYeCost();
						if(StringUtils.isNotBlank(yeCost)){
							String nameTemp = DicUtil.getTypeNameByCode("hazard_fxss",yeCost);
							vo.setYeCost(nameTemp);
						}else{
							vo.setYeCost("");
						}
						vo.setYeCost(danger.getYeCost());
						vo.setYeMhazardDesc(danger.getYeMhazardDesc());
						String yeProfession = danger.getYeProfession();
						if(StringUtils.isNotBlank(yeProfession)){
							String nameTemp = DicUtil.getTypeNameByCode("proCate_gradeControl",yeProfession);
							vo.setYeProfession(nameTemp);
						}else{
							vo.setYeProfession("");
						}
						vo.setYeReference(danger.getYeReference());
						vo.setYeLocation(danger.getYeLocation());
						vo.setYeDistance(danger.getYeDistance());
						vo.setYeSurrounding(danger.getYeSurrounding());
						vo.setYeStandard(danger.getYeStandard());
						vo.setYeMonitor(danger.getYeMonitor());
						vo.setYeEmergency(danger.getYeEmergency());
						vo.setYeResDepart(danger.getYeResDepart());
						vo.setYePossiblyHazard(danger.getYePossiblyHazard());
						String yeProbability = danger.getYeProbability();
						if(StringUtils.isNotBlank(yeProbability)){
							//取字典
							DicUtil.getTypeNameByCode("probability",yeProbability);
							vo.setYeProbability(danger.getYeProbability());
						}else{
							vo.setYeProbability("");
						}
						String yeHazardCate = danger.getYeHazardCate();
						if(StringUtils.isNotBlank(yeHazardCate)){
							String nameTemp = DicUtil.getTypeNameByCode("hazardCate",yeHazardCate);
							vo.setYeHazardCate(nameTemp);
						}else{
							vo.setYeHazardCate("");
						}
						String yeRiskGrade = danger.getYeRiskGrade();
						if(StringUtils.isNotBlank(yeRiskGrade)){
							String nameTemp = DicUtil.getTypeNameByCode("riskLevel",yeRiskGrade);
							vo.setYeRiskGrade(nameTemp);
						}else{
							vo.setYeRiskGrade("");
						}
						vo.setYeCaseNum(danger.getYeCaseNum());
						Date date = danger.getYeRecognizeTime();
						if(date == null){
							vo.setYeRecognizeTime("");
						}else{
							vo.setYeRecognizeTime(sdf.format(date));
						}

//                            lecRiskPossibility      字典:
						Double lecRiskPossibility = danger.getLecRiskPossibility();
						if(lecRiskPossibility != null){
							String lecRiskPossibilityStr = lecRiskPossibility.toString();
							String nameTemp = DicUtil.getTypeNameByCode("lec_risk_probability",lecRiskPossibilityStr);
							vo.setLecRiskPossibility(nameTemp);
						}else{
							vo.setLecRiskPossibility("");
						}
//                            private String lecRiskLoss;   字典： lec_risk_loss
						Double lecRiskLoss = danger.getLecRiskLoss();
						if(lecRiskLoss != null){
							String lecRiskLossStr = lecRiskLoss.toString();
							String nameTemp = DicUtil.getTypeNameByCode("lec_risk_loss",lecRiskLossStr);
							vo.setLecRiskLoss(nameTemp);
						}else{
							vo.setLecRiskLoss("");
						}
//                            private String lecExposure; 字典：  LEC暴露在危险中的概率
						Double lecExposure = danger.getLecExposure();
						if(lecExposure != null){
							String str = lecExposure.toString();
							String nameTemp = DicUtil.getTypeNameByCode("lec_exposure",str);
							vo.setLecExposure(nameTemp);
						}else{
							vo.setLecExposure("");
						}
//                            private String lecRiskValue;   不用转

						vo.setRiskValue(danger.getRiskValue());
						if(danger.getLecRiskValue() == null){
							vo.setLecRiskValue("");
						}else{
							vo.setLecRiskValue(String.valueOf(danger.getLecRiskValue()));
						}

						dangerList.add(vo);
					}
				}

				Map<String,Object> reportMap = new HashMap<String,Object>();
				reportMap.put("id",bean.getId());
				reportMap.put("address",bean.getAddress());
				reportMap.put("lon",bean.getLon());
				reportMap.put("lat",bean.getLat());
				reportMap.put("isshow",bean.getIsshow());
				reportMap.put("isDelete",bean.getIsDelete());
				Date date = bean.getCreateDate();
				reportMap.put("createDate",sdf.format(date));
				reportMap.put("riskdatas",dangerList);

				reportList.add(reportMap);
			}
		}

		Map<String,Object> postMap = new HashMap<String,Object>();
		String mineCode = ResourceUtil.getConfigByName("mine_code");
		String token = ResourceUtil.getConfigByName("token_group");

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
		postMap.put("token", ciphertext);

		postMap.put("mineCode",mineCode);
		postMap.put("reportContent",JSONArray.fromObject(reportList).toString());
		String url = ResourceUtil.getConfigByName("addressInfoReportUrl");
		try {
			String msg = HttpClientUtils.post(url,postMap,"UTF-8");
			JSONObject jsonObject = JSONObject.fromObject(msg);
			String retCode = jsonObject.optString("code");
			if("200".equals(retCode)){
				j.setMsg("上报成功");
			}else{
				j.setMsg(jsonObject.optString("message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping(params = "chooseInveRiskPointDatagrid")
	public void chooseInveRiskPointDatagrid(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
		tBAddressInfo.setAddress(null);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAddressInfo, request.getParameterMap());
		try{
			String ids = ResourceUtil.getParameter("ids");
			if (StringUtil.isNotEmpty(ids)) {
				cq.notIn("id", ids.split(","));
			}
			//自定义追加查询条件
			cq.eq("isDelete", Constants.IS_DELETE_N);
			String address = request.getParameter("address");
			if (StringUtil.isNotEmpty(address)) {
				cq.like("address", "%"+address+"%");
			}
			/** 从月度计划中选择 **/
			if ("plan".equals(ResourceUtil.getParameter("from"))) {
				String month = ResourceUtil.getParameter("month");
				if (StringUtil.isNotEmpty(month)) {
					month = month.substring(0, 7)+"-01";
					cq.add(Restrictions.sqlRestriction("id in (select obj_id from t_b_investigate_plan_rel rel join t_b_investigate_plan plan on rel.plan_id=plan.id where rel.rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT+"' and rel.poit_type='"+Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_LOCATION+"' and plan.start_time='"+month+"')"));
				} else {
					cq.add(Restrictions.sqlRestriction("id in (select obj_id from t_b_investigate_plan_rel where rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT+"' and poit_type='"+Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_LOCATION+"')"));
				}
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBAddressInfoService.getDataGridReturn(cq, true);
		List<TBAddressInfoEntity> list = dataGrid.getResults();
		if (list != null && list.size() > 0) {
			StringBuffer ids = new StringBuffer();
			for (TBAddressInfoEntity entity : list) {
				if (StringUtil.isNotEmpty(ids.toString())) {
					ids.append(",");
				}
				ids.append("'").append(entity.getId()).append("'") ;
			}
			String sql = "select count(1) total, t.address_id  from (select DISTINCT address_id,danger_id from t_b_danger_address_rel where address_id in ("+ids.toString()+")) t GROUP BY t.address_id";
			List<Map<String, Object>> result = systemService.findForJdbc(sql);
			Map<String, String> countMap = new HashMap<>();
			if (result != null && result.size() > 0) {
				for (Map<String, Object> obj : result) {
					if (obj.get("total") != null && obj.get("address_id") != null) {
						countMap.put(obj.get("address_id").toString(), obj.get("total").toString());
					}
				}
			}
			for (TBAddressInfoEntity entity : list) {
				entity.setDangerSourceCount(countMap.get(entity.getId())!=null?countMap.get(entity.getId()):"0");
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 组合一类危险源树
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "dangerSourceTree")
	@ResponseBody
	public List<ComboTree> formTree(HttpServletRequest request,final ComboTree rootComboTree) {
		String addressCate = request.getParameter("addressCate");

		rootComboTree.setId("all");
		rootComboTree.setText("危险源");
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		List<TSType> typeList = ResourceUtil.allTypes.get("dangerSource_type".toLowerCase());
		if (typeList == null || typeList.size() == 0) {
			return new ArrayList<ComboTree>(){{add(rootComboTree);}};
		}
		try {
			for(TSType tsType : typeList){
				ComboTree combotree = new ComboTree();
				combotree.setId(tsType.getTypecode());
				combotree.setText(tsType.getTypename());
				comboTrees.add(combotree);
				List<ComboTree> childComboTrees = new ArrayList<ComboTree>();
//                List<TbHazardManageEntity> hazardList = systemService.findByProperty(TbHazardManageEntity.class, "hazardType", tsType.getTypecode());
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT `id`, `hazard_name`, `hazard_type`, `damage_type`, `profession_type`, `accident_type`, `create_name`, `create_by`, `create_date`, `update_name`, `update_by`, `update_date`, `is_delete` from t_b_hazard_manage where 1=1")
						.append(" and hazard_type='").append(tsType.getTypecode()).append("'");
				if(StringUtil.isNotEmpty(addressCate)){
					sql.append(" and id in(")
							.append("SELECT hazard_manage_id from t_b_danger_source where address_cate='").append(addressCate).append("')");
				}
				List<Object[]> hazardList = systemService.findListbySql(sql.toString());

				if (hazardList != null && hazardList.size() > 0) {
					for (Object[] entity : hazardList) {
						if ("0".equals(entity[12].toString())) {
							ComboTree childCombotree = new ComboTree();
							childCombotree.setId(entity[0].toString());
							childCombotree.setText(entity[1].toString());
							//childCombotree.setChecked(true);
							childComboTrees.add(childCombotree);
						}
					}
				}
				combotree.setChildren(childComboTrees);
			}
		} catch (Exception e) {

		}
		rootComboTree.setChildren(comboTrees);
		return new ArrayList<ComboTree>(){{add(rootComboTree);}};
	}

	/***********************************************************关联部门*************************************************/

	/**
	 * 获取已关联的责任部门
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addressDepartRelList")
	public ModelAndView addressDepartRelList(HttpServletRequest request) {
		request.setAttribute("addressId",ResourceUtil.getParameter("addressId"));
		try{
			TBAddressInfoEntity addressInfo = systemService.get(TBAddressInfoEntity.class, ResourceUtil.getParameter("addressId"));
			if (addressInfo != null) {
				request.setAttribute("addressName", addressInfo.getAddress());
				request.setAttribute("manageMan", addressInfo.getManageMan());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ModelAndView("com/sdzk/buss/web/address/addressDepartRelList");
	}


	/**
	 * 获取已关联的责任部门
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "addressDepartRelDatagrid")
	public void addressDepartRelDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAddressDepartRelEntity.class, dataGrid);
		//已关联的风险
		String addressId = ResourceUtil.getParameter("addressId");
		try{
			cq.eq("addressId",addressId);
			cq.add(Restrictions.sqlRestriction(" this_.depart_id in (select id from t_s_depart where delete_flag = '0')"));
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		//按危险源描述查询
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 获取部门列表
	 * @param request
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "setDepartRelList")
	@ResponseBody
	public List<ComboTree> setDepartRelList(HttpServletRequest request, ComboTree comboTree) {
		String addressId = ResourceUtil.getParameter("addressId");
		request.setAttribute("addressId",addressId);
		//获取已关联的部门
		List<TSDepart> relDeparts = systemService.findByQueryString("from TSDepart where deleteFlag = '0' and id in (select departId from TBAddressDepartRelEntity where addressId = '"+addressId+"')");
		//获取组织树
		List<TSDepart> departsList = systemService.findByQueryString("from TSDepart where TSPDepart.id is null and deleteFlag = '0'");
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		if (departsList != null && departsList.size()>0){
			ComboTreeModel comboTreeModel = new ComboTreeModel("id", "departname", "TSDeparts");
			comboTrees = systemService.ComboTree(departsList, comboTreeModel, relDeparts, true);
//            if (comboTrees!=null && comboTrees.size()>0) {
//                for (ComboTree tree : comboTrees) {
//                    if (!tree.getChecked()){
//                        tree.setChecked(setParentChecked(tree.getChildren()));;
//                    }
//                }
//            }
		}

		return comboTrees;
	}

//    private boolean setParentChecked(List<ComboTree> comboTrees){
//        boolean checked = false;
//        if (comboTrees!=null && comboTrees.size()>0){
//            for (ComboTree comboTree : comboTrees) {
//                List<ComboTree> childComboTrees = comboTree.getChildren();
//                if (!comboTree.getChecked()){
//                    comboTree.setChecked(setParentChecked(childComboTrees));
//                }
//                if (comboTree.getChecked()){
//                    checked = comboTree.getChecked();
//                }
//            }
//        }
//        return checked;
//    }

	/**
	 * 保存责任部门关联关系
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveAddressDepartRel")
	@ResponseBody
	public AjaxJson saveAddressDepartRel(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String addressId = request.getParameter("addressId");
			String departIds = request.getParameter("departIds");
			tBAddressInfoService.saveAddressDepartRel(addressId,departIds);
			j.setMsg("责任部门关联成功");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("责任部门关联失败");
		}
		return j;
	}

	/**
	 * 删除关联关系
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doBatchDelAddressDepartRel")
	@ResponseBody
	public AjaxJson doBatchDelAddressDepartRel(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String ids = request.getParameter("ids");
			if (StringUtils.isNotBlank(ids)) {
				systemService.executeSql("delete from t_b_address_depart_rel where id in ('"+ids.replace(",","','")+"')");
			}
			j.setMsg("责任部门关联删除成功");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("责任部门关联删除失败");
		}
		return j;
	}

	/**
	 * 更新责任人
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveDutyMan")
	@ResponseBody
	public AjaxJson saveDutyMan(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String saveData = request.getParameter("saveData");
		try {
			JSONArray jsonArray = JSONArray.fromObject(saveData);
			if (jsonArray != null && jsonArray.size()>0){
				for (int i = 0; i<jsonArray.size();i++)
					systemService.executeSql("update t_b_address_depart_rel set duty_man='" + jsonArray.getJSONObject(i).optString("dutyMan") + "' where id='" + jsonArray.getJSONObject(i).optString("id") + "'");
			}
			//更新分管领导
			String manageMan = request.getParameter("manageMan");
			String addressId = request.getParameter("addressId");
			if (StringUtils.isNotBlank(manageMan)&&StringUtils.isNotBlank(addressId)){
				systemService.executeSql("update t_b_address_info set manage_man='"+manageMan+"' where id = ('"+addressId+"')");
			}
			j.setMsg("责任人更新成功");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("责任人更新失败");
		}
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
					sunshineEntity.setTableName("t_b_address_info");
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



	/*********************************************************风险关联风险点 start**************************************************/
	/**
	 * 风险关联风险点 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addresslist")
	public ModelAndView addresslist(HttpServletRequest request, HttpServletResponse response) {
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
		request.setAttribute("dangerId",ResourceUtil.getParameter("dangerId"));

		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfoAddressList");
	}

	/**
	 * 风险关联风险点 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addressAddlist")
	public ModelAndView addressAddlist(HttpServletRequest request, HttpServletResponse response) {
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
		request.setAttribute("excludeId",ResourceUtil.getParameter("excludeId"));

		return new ModelAndView("com/sdzk/buss/web/address/tBAddressInfoAddressAdd");
	}

	/**
	 * 查询风险已关联和或未关联的风险点
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 * worktemp
	 */
	@RequestMapping(params = "dangerSourceAddressDatagrid")
	public void dangerSourceAddressDatagrid(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
		//需要排除的id
		String excludeId = ResourceUtil.getParameter("excludeId");
		//已关联的风险
		String dangerId = ResourceUtil.getParameter("dangerId");
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAddressInfo, request.getParameterMap());
		try{
			//自定义追加查询条件
			if (StringUtil.isNotEmpty(excludeId)) {
				//排除已关联的风险点
				cq.add(Restrictions.sqlRestriction(" this_.id not in (select danger.address_id from t_b_danger_address_rel danger where danger.danger_id='"+excludeId+"')"));
			} else  if (StringUtil.isNotEmpty(dangerId)) {
				//获取已关联的风险点
				cq.add(Restrictions.sqlRestriction(" this_.id in (select danger.address_id from t_b_danger_address_rel danger where danger.danger_id='"+dangerId+"')"));
			} else {
				cq.add(Restrictions.sqlRestriction(" 1=2 "));
			}
			cq.eq("isDelete", Constants.IS_DELETE_N);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBAddressInfoService.getDataGridReturn(cq, true);
		List<TBAddressInfoEntity> list = dataGrid.getResults();
		setDangerSourceCountData(list);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 批量删除
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDelDangerRel")
	@ResponseBody
	public AjaxJson doBatchDelDangerRel(HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "删除成功";
		String ids = ResourceUtil.getParameter("ids");
		String dangerId = ResourceUtil.getParameter("dangerId");
		try{
			if (StringUtil.isNotEmpty(ids) && StringUtil.isNotEmpty(dangerId)) {
				CriteriaQuery cq = new CriteriaQuery(TBDangerAddresstRelEntity.class);
				cq.eq("dangerId", dangerId);
				cq.in("addressId", ids.split(","));
				cq.add();
				List<TBDangerAddresstRelEntity> list = systemService.getListByCriteriaQuery(cq, false);
				systemService.deleteAllEntitie(list);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			} else {
				message = "删除失败";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/*********************************************************风险关联风险点 end**************************************************/


	@RequestMapping(params = "addressInfoList")
	public ModelAndView addressInfoList(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/address/addressInfoList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "addressInfoDatagrid")
	public void addressInfoDatagrid(TBAddressInfoEntity tBAddressInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
		try{
			String addressId = request.getParameter("addressId");
			if (StringUtil.isNotEmpty(addressId)) {
				cq.eq("id", addressId);
			}
			String investigationDateStart = request.getParameter("investigationDate_begin");
			String investigationDateEnd = request.getParameter("investigationDate_end");
			if(StringUtils.isNotBlank(investigationDateStart)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.ge("investigationDate",sdf.parse(investigationDateStart));
			}
			if(StringUtils.isNotBlank(investigationDateEnd)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cq.le("investigationDate",sdf.parse(investigationDateEnd));
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
			String addressStatus = request.getParameter("addressStatus");
			if(StringUtil.isNotEmpty(addressStatus)){
				if (addressStatus.equals("1")){
					cq.add(Restrictions.sqlRestriction("this_.id in (SELECT id FROM t_b_address_info WHERE (start_date < NOW() or start_date is null or start_date = '') and (end_date > NOW() or end_date is null or end_date = ''))"));
				}
			}
			cq.eq("isDelete", Constants.IS_DELETE_N);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		List<TBAddressInfoEntity> list = dataGrid.getResults();
		setDangerSourceCountData(list);
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params="showOrDownByurl",method = RequestMethod.GET)
	public void showOrDownByurl(HttpServletResponse response,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		TBAddressInfoEntity entity = tBAddressInfoService.get(TBAddressInfoEntity.class, id);
		String fileName = entity.getAddress()+".jpg";

		response.setContentType("application/x-msdownload;charset=utf-8");
		response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));

//		InputStream inputStream = null;
		OutputStream outputStream=null;
//		InputStream fileInputStream = null;

		try {
//			File file = new File(dir + "//reduce_" + fileName);
//			if (!file.exists() || "y".equals(large)) {
//				file = new File(dir + "//" + fileName);
//			}

			outputStream = response.getOutputStream();
			QrCodeCreateUtil.createQrCode(outputStream,id,900,"JPEG");
//			fileInputStream = new FileInputStream(file);
//			inputStream = new BufferedInputStream(fileInputStream);
//			byte[] buf = new byte[1024];
//			int len;
//			while ((len = inputStream.read(buf)) > 0) {
//				outputStream.write(buf, 0, len);
//			}
			response.flushBuffer();
		} catch (Exception e) {
			logger.info("--通过流的方式获取文件异常--"+e.getMessage());
		}finally{
//			if(fileInputStream!=null){
//				fileInputStream.close();
//			}
//			if(inputStream!=null){
//				inputStream.close();
//			}
			if(outputStream!=null){
				outputStream.close();
			}
		}
	}
}
