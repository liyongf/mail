package com.sdzk.buss.web.lightAddress.controller;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.lightAddress.entity.TBLightAddressEntity;

import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

/**
 * @Title: Controller  
 * @Description: 灯和风险点对应信息
 * @author onlineGenerator
 * @date 2017-06-17 17:27:22
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBLightAddressController")
public class TBLightAddressController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBLightAddressController.class);

	@Autowired
	private SystemService systemService;

	/**
	 * 灯和风险点对应信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/lightAddress/tBLightAddressList");
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
	public void datagrid(TBLightAddressEntity tBLightAddress,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBLightAddressEntity.class, dataGrid);
		String lightId = request.getParameter("lightId");
		if(StringUtil.isNotEmpty(lightId)){
			cq.like("lightId","%"+lightId+"%");
		}
		cq.add(Restrictions.sqlRestriction(" this_.address_id not in (select id from t_b_address_info where isShowData = '0' )"));
		String addressId = request.getParameter("addressId");
		if(StringUtil.isNotEmpty(addressId)){
			cq.eq("addressId",addressId);
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		if (dataGrid != null && dataGrid.getResults() != null) {
			if (dataGrid.getResults().size() > 0) {
				List<TBLightAddressEntity> list = dataGrid.getResults();
				for (TBLightAddressEntity t : list){
					if(StringUtil.isNotEmpty(t.getAddressId())){
						TBAddressInfoEntity addressInfoEntity = this.systemService.getEntity(TBAddressInfoEntity.class,t.getAddressId());
						if(null!=addressInfoEntity){
							t.setAddressName(addressInfoEntity.getAddress());
						}
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
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
	 * 批量删除灯和风险点对应信息
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "灯和风险点对应信息删除成功";
		try{
			for(String id:ids.split(",")){
				TBLightAddressEntity tBLightAddress = systemService.getEntity(TBLightAddressEntity.class, id);
				this.systemService.delete(tBLightAddress);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "灯和风险点对应信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加灯和风险点对应信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBLightAddressEntity tBLightAddress, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "灯和风险点对应信息添加成功";
		try{
			this.systemService.save(tBLightAddress);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "灯和风险点对应信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新灯和风险点对应信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBLightAddressEntity tBLightAddress, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "灯和风险点对应信息更新成功";
		TBLightAddressEntity t = this.systemService.get(TBLightAddressEntity.class, tBLightAddress.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBLightAddress, t);
			this.systemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "灯和风险点对应信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 灯和风险点对应信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBLightAddressEntity tBLightAddress, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBLightAddress.getId())) {
			tBLightAddress = this.systemService.getEntity(TBLightAddressEntity.class, tBLightAddress.getId());
			req.setAttribute("tBLightAddressPage", tBLightAddress);
		}
		return new ModelAndView("com/sdzk/buss/web/lightAddress/tBLightAddress-add");
	}
	/**
	 * 灯和风险点对应信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBLightAddressEntity tBLightAddress, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBLightAddress.getId())) {
			tBLightAddress = this.systemService.getEntity(TBLightAddressEntity.class, tBLightAddress.getId());
            TBAddressInfoEntity va = this.systemService.getEntity(TBAddressInfoEntity.class,tBLightAddress.getAddressId());
            tBLightAddress.setAddressName(va.getAddress());
			req.setAttribute("tBLightAddressPage", tBLightAddress);
		}
		req.setAttribute("load", req.getParameter("load"));
        String load = req.getParameter("load");
        if ("detail".equals(load)) {
            return new ModelAndView("com/sdzk/buss/web/lightAddress/tBLightAddress-detail");
        }
		return new ModelAndView("com/sdzk/buss/web/lightAddress/tBLightAddress-update");
	}

	/**
	 * 轨迹跟踪
	 *
	 * @return
	 */
	@RequestMapping(params = "track")
	public ModelAndView track(HttpServletRequest request) {
		String supermapMineUrl = ResourceUtil.getConfigByName("supermapMineUrl");
		request.setAttribute("supermapMineUrl",supermapMineUrl);
		String supermapMineCenter = ResourceUtil.getConfigByName("supermapMineCenter");
		request.setAttribute("supermapMineCenter",supermapMineCenter);
		String supermapCountyUrl = ResourceUtil.getConfigByName("supermapCountyUrl");
		request.setAttribute("supermapCountyUrl",supermapCountyUrl);
		String supermapCountyCenter = ResourceUtil.getConfigByName("supermapCountyCenter");
		request.setAttribute("supermapCountyCenter",supermapCountyCenter);

		return new ModelAndView("com/sdzk/buss/web/lightAddress/tBLightAddressTrack");
	}

	/**
	 * 获取定位信息
	 *
	 * @return
	 */
	@RequestMapping(params = "getTrack")
	@ResponseBody
	public AjaxJson getTrack(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		JSONArray ja = new JSONArray();
		String sql = "select distinct r.man_id userId, u.realname userName from t_b_access_record r, t_s_base_user u where r.man_id=u.id";
		List<Map<String,Object>> userList = this.systemService.findForJdbc(sql);
		if(null!=userList && userList.size()>0){
			for(int i=0;i<userList.size();i++){
				String userId = (String)userList.get(i).get("userId");
				sql = "select r.address_id addressId, a.address addressName, a.lon lon, a.lat lat, r.arrival_time arrivalTime " +
						"from t_b_access_record r, t_b_address_info a where r.address_id=a.id " +
						"and r.man_id='"+userId+"' and a.is_delete!='1' and a.isShow='Y' order by r.arrival_time asc";
				List<Map<String,Object>> accessList = this.systemService.findForJdbc(sql);
				if(null!=accessList && accessList.size()>0){
					JSONObject jo = new JSONObject();
					jo.put("userId",userId);
					jo.put("userName", userList.get(i).get("userName"));
					jo.put("accessList",accessList);
					ja.add(jo);
				}
			}
			j.setObj(ja);
		}
		return j;
	}
}
