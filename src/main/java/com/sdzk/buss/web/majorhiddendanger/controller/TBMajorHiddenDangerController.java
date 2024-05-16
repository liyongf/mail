package com.sdzk.buss.web.majorhiddendanger.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.address.service.TBAddressInfoServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendangerhistory.entity.TBHiddenDangerHistoryEntity;
import com.sdzk.buss.web.hiddendangerhistory.service.TBHiddenDangerHistoryServiceI;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import com.sdzk.buss.web.majorhiddendanger.entity.SFListedSupervisionInfoEntity;
import com.sdzk.buss.web.majorhiddendanger.service.MajorDangerReportRPC;
import com.sdzk.buss.web.majorhiddendanger.service.TBMajorHiddenDangerServiceI;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzk.buss.web.majorhiddendanger.service.TBMajorSuperviseServiceI;
import com.sdzk.buss.web.rectprogressreport.entity.TBRectProgressReportEntity;
import com.sdzk.buss.web.riskUpgrade.service.RiskUpgradeServiceI;
import com.sdzk.buss.web.system.entity.TBSunshineEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
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

import java.io.OutputStream;

import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
 * @Description: 重大隐患
 * @author onlineGenerator
 * @date 2017-06-22 10:21:57
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBMajorHiddenDangerController")
public class TBMajorHiddenDangerController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBMajorHiddenDangerController.class);

	@Autowired
	private TBMajorHiddenDangerServiceI tBMajorHiddenDangerService;
    @Autowired
    private UserService userService;

    @Autowired
    private TBAddressInfoServiceI tBAddressInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TBHiddenDangerHistoryServiceI tbHiddenDangerHistoryService;
    @Autowired
    private TBMajorSuperviseServiceI tbMajorSuperviseService;
    @Autowired
    private MajorDangerReportRPC majorDangerReportRPC;

    @Autowired
    private RiskUpgradeServiceI riskUpgradeService;

	/**
	 * 重大隐患列表 页面跳转
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

		return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDangerList");
	}
    private String dateToString(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        if(date != null){
            str=sdf.format(date);
        }
        return str;
    }

    /**
     * 上报到集团接口
     * */
    @RequestMapping(params = "reportGroup")
    @ResponseBody
    public AjaxJson reportGroup(HttpServletRequest request,String ids) {
        AjaxJson j = new AjaxJson();
        j.setMsg("上报成功");
        if (StringUtil.isNotEmpty(ids)) {
            j = majorDangerReportRPC.majorDangerReportToGroup(ids);
        } else {
            j.setMsg("请选择要上报的数据");
        }
        return j;
    }


	/**
	 * 重大隐患列表 整改页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "rectifyList")
	public ModelAndView rectifyList(HttpServletRequest request) {
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

		return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDangerList-rectify");
	}

	/**
	 * 重大隐患列表 验收页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "pendingAcceptList")
	public ModelAndView pendingAcceptList(HttpServletRequest request) {
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

		return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDangerList-accept");
	}

    /**
     * 撤回已验收待复查-隐患验收列表
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "toAcceptCallback")
    @ResponseBody
    public AjaxJson toAcceptCallback(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                for(String id : ids.split(",")){
                    TBMajorHiddenDangerEntity t = systemService.get(TBMajorHiddenDangerEntity.class, id);
                    System.out.println(t.getClStatus());
                    if (Constants.HIDDEN_DANGER_CLSTATUS_REVIEW.equals(t.getClStatus())) {

                        t.setClStatus(Constants.HIDDEN_DANGER_CLSTATUS_ACCEPT);
                        systemService.saveOrUpdate(t);
                    }
                    else{
                        message = "只能选择待复查的数据";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "撤回失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 撤回已整改待验收-隐患整改页面
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "toRectifyCallback")
    @ResponseBody
    public AjaxJson toRectifyCallback(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                for(String id : ids.split(",")){
                    TBMajorHiddenDangerEntity t = systemService.get(TBMajorHiddenDangerEntity.class, id);
                    if (Constants.HIDDEN_DANGER_CLSTATUS_ACCEPT.equals(t.getClStatus())) {
                        t.setClStatus(Constants.HIDDEN_DANGER_CLSTATUS_RECFITY);
                        systemService.saveOrUpdate(t);
                    }
                    else{
                        message = "只能选择未复查的数据";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "撤回失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 撤回待整改-综合查询界面
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "toCallback")
    @ResponseBody
    public AjaxJson toCallback(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                for(String id : ids.split(",")){
                    TBMajorHiddenDangerEntity t = systemService.get(TBMajorHiddenDangerEntity.class, id);
                    if (Constants.HIDDEN_DANGER_CLSTATUS_RECFITY.equals(t.getClStatus())) {
                        t.setClStatus(Constants.HIDDEN_DANGER_CLSTATUS_DRAFT);
                        systemService.saveOrUpdate(t);
                        riskUpgradeService.execute(id);
                    }
                    else{
                        message = "所选数据无需撤回";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "撤回失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

	/**
	 * 重大隐患列表 复查页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "pendingReviewList")
	public ModelAndView pendingReviewList(HttpServletRequest request) {
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

		return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDangerList-review");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBMajorHiddenDangerEntity tBMajorHiddenDanger,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBMajorHiddenDangerEntity.class, dataGrid);
        String clStatus = tBMajorHiddenDanger.getClStatus();
		//拼接条件
		String queryHandleStatusTem = ResourceUtil.getParameter("queryHandleStatus");
		if(StringUtil.isNotEmpty(queryHandleStatusTem)){
            tBMajorHiddenDanger.setClStatus(null);
			cq.in("clStatus", queryHandleStatusTem.split(","));
		}
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
		if (!isAdmin && Constants.HIDDEN_DANGER_CLSTATUS_RECFITY.equals(clStatus)) {
			cq.add(
                    Restrictions.eq("dutyUnit.id", sessionUser.getCurrentDepart().getId())
			);
		}
		cq.add(Restrictions.sqlRestriction(" this_.HD_LOCATION not in (select id from t_b_address_info where isShowData = '0' )"));
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBMajorHiddenDanger, request.getParameterMap());
		try{
		//自定义追加查询条件
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
			String sunSql = "select column_id from t_b_sunshine where table_name='t_b_major_hidden_danger'";
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
		this.tBMajorHiddenDangerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除重大隐患
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBMajorHiddenDangerEntity tBMajorHiddenDanger, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBMajorHiddenDanger = systemService.getEntity(TBMajorHiddenDangerEntity.class, tBMajorHiddenDanger.getId());
		message = "重大隐患删除成功";
		try{
			tBMajorHiddenDangerService.delete(tBMajorHiddenDanger);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "重大隐患删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除重大隐患
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "重大隐患删除成功";
		try{
			for(String id:ids.split(",")){
				TBMajorHiddenDangerEntity tBMajorHiddenDanger = systemService.getEntity(TBMajorHiddenDangerEntity.class, 
				id
				);
				tBMajorHiddenDangerService.delete(tBMajorHiddenDanger);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

				//删除阳光账号列表的关联数据
				List<TBSunshineEntity> tbSunshineEntityList = systemService.findByProperty(TBSunshineEntity.class, "columnId", id);
				if (tbSunshineEntityList!=null && tbSunshineEntityList.size()>0){
					systemService.deleteAllEntitie(tbSunshineEntityList);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "重大隐患删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加重大隐患
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBMajorHiddenDangerEntity tBMajorHiddenDanger, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "重大隐患添加成功";

//        /**保存关键字*/
//        String keywords = request.getParameter("keyWords");
//        tBMajorHiddenDanger.setKeyWords(keywords);
        /**自动关联危险源id*/
        /*String dangerId = getDangerIdByKeyword(tBMajorHiddenDanger.getKeyWords());
        if (StringUtil.isNotEmpty(dangerId)) {
            tBMajorHiddenDanger.setDangerId(dangerId);
        }*/
//        String dangerId = request.getParameter("dangerId.id");
//        System.out.println(dangerId);
//        TBDangerSourceEntity t = systemService.getEntity(TBDangerSourceEntity.class,dangerId);
//        tBMajorHiddenDanger.setDangerId(t);

		try{
			tBMajorHiddenDangerService.save(tBMajorHiddenDanger);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			tbHiddenDangerHistoryService.saveHistory(Constants.HIDDEN_DANGER_CLSTATUS_DRAFT, tBMajorHiddenDanger);

            riskUpgradeService.execute(tBMajorHiddenDanger.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "重大隐患添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新重大隐患
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBMajorHiddenDangerEntity tBMajorHiddenDanger, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "重大隐患更新成功";
		TBMajorHiddenDangerEntity t = tBMajorHiddenDangerService.get(TBMajorHiddenDangerEntity.class, tBMajorHiddenDanger.getId());
		String oldStatus = t.getClStatus();
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBMajorHiddenDanger, t);
			tBMajorHiddenDangerService.saveOrUpdate(t);
			if (!Constants.HIDDEN_DANGER_CLSTATUS_DRAFT.equals(oldStatus)) {
				tbHiddenDangerHistoryService.saveHistory(oldStatus, t);
			}
            riskUpgradeService.execute(tBMajorHiddenDanger.getId());
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "重大隐患更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

    /**
     * 更新五落实信息
     *
     * @return
     */
    @RequestMapping(params = "doUpdateFiveImpl")
    @ResponseBody
    public AjaxJson doUpdateFiveImpl(TBMajorHiddenDangerEntity tBMajorHiddenDanger, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "重大隐患五落实更新成功";
        TBMajorHiddenDangerEntity t = tBMajorHiddenDangerService.get(TBMajorHiddenDangerEntity.class, tBMajorHiddenDanger.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBMajorHiddenDanger, t);
            tBMajorHiddenDangerService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "重大隐患更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
	

	/**
	 * 重大隐患新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBMajorHiddenDangerEntity tBMajorHiddenDanger, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBMajorHiddenDanger.getId())) {
			tBMajorHiddenDanger = tBMajorHiddenDangerService.getEntity(TBMajorHiddenDangerEntity.class, tBMajorHiddenDanger.getId());
			req.setAttribute("tBMajorHiddenDangerPage", tBMajorHiddenDanger);
		}
		return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-add");
	}
	/**
	 * 重大隐患编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBMajorHiddenDangerEntity tBMajorHiddenDanger, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBMajorHiddenDanger.getId())) {
			tBMajorHiddenDanger = tBMajorHiddenDangerService.getEntity(TBMajorHiddenDangerEntity.class, tBMajorHiddenDanger.getId());

            if(tBMajorHiddenDanger.getHdLocation()!=null && !(tBMajorHiddenDanger.getHdLocation().equals(""))){
                TBAddressInfoEntity hl = tBAddressInfoService.getEntity(TBAddressInfoEntity.class,tBMajorHiddenDanger.getHdLocation());
                tBMajorHiddenDanger.setHdLocationName(hl.getAddress());
            }

//            if(tBMajorHiddenDanger.getDutyMan()!=null && !(tBMajorHiddenDanger.getDutyMan().equals(""))){
//                TSUser dm = userService.getEntity(TSUser.class,tBMajorHiddenDanger.getDutyMan().getId());
//                tBMajorHiddenDanger.setDutyManName(dm.getRealName());
//            }
            tBMajorHiddenDanger.setDutyManName(tBMajorHiddenDanger.getDutyMan());


            CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
            List<TSDepart> tempList = this.systemService.getListByCriteriaQuery(cq, false);
            for(TSDepart tsd : tempList){
                if(tBMajorHiddenDanger.getDutyUnit()!=null && !(tBMajorHiddenDanger.getDutyUnit().equals(""))){
                    if(tsd.getId().equals(tBMajorHiddenDanger.getDutyUnit().getId())){
                        tBMajorHiddenDanger.setDutyUnitName(tsd.getDepartname());
                    }
                }
                if(tBMajorHiddenDanger.getRectUnit()!=null && !(tBMajorHiddenDanger.getRectUnit().equals(""))){
                    if(tsd.getId().equals(tBMajorHiddenDanger.getRectUnit())){
                        tBMajorHiddenDanger.setRectUnitName(tsd.getDepartname());
                    }
                }

            }
            if(tBMajorHiddenDanger.getRectMan()!=null && !(tBMajorHiddenDanger.getRectMan().equals(""))){
				TSUser rm = userService.getEntity(TSUser.class,tBMajorHiddenDanger.getRectMan());
                if(rm != null && !(rm.equals(""))){
                    tBMajorHiddenDanger.setRectManName(rm.getRealName());
                }

            }

            if(tBMajorHiddenDanger.getAcceptor()!=null && !(tBMajorHiddenDanger.getAcceptor().equals(""))){
                TSUser an = userService.getEntity(TSUser.class,tBMajorHiddenDanger.getAcceptor());
				if (an != null) {
					tBMajorHiddenDanger.setAcceptorName(an.getRealName());
				}
            }

            if(tBMajorHiddenDanger.getReviewer()!=null && !(tBMajorHiddenDanger.getReviewer().equals(""))){
                TSUser rn = userService.getEntity(TSUser.class,tBMajorHiddenDanger.getReviewer());
				if (rn != null) {
					tBMajorHiddenDanger.setReviewerName(rn.getRealName());
				}
            }

			if(StringUtils.isNotBlank(tBMajorHiddenDanger.getHdInfoSource())){
				tBMajorHiddenDanger.setHdInfoSourceTemp(DicUtil.getTypeNameByCode("hiddenFrom", tBMajorHiddenDanger.getHdInfoSource()));
			}

			if(StringUtils.isNotBlank(tBMajorHiddenDanger.getHdCate())){
				tBMajorHiddenDanger.setHdCateTemp(DicUtil.getTypeNameByCode("hiddenCate", tBMajorHiddenDanger.getHdCate()));
			}
			if(StringUtils.isNotBlank(tBMajorHiddenDanger.getHdLevel())){
				tBMajorHiddenDanger.setHdLevelTemp(DicUtil.getTypeNameByCode("hiddenLevel", tBMajorHiddenDanger.getHdLevel()));
			}
			if(StringUtils.isNotBlank(tBMajorHiddenDanger.getHdMajor())){
				tBMajorHiddenDanger.setHdMajorTemp(DicUtil.getTypeNameByCode("proCate_gradeControl", tBMajorHiddenDanger.getHdMajor()));
			}
			if(StringUtils.isNotBlank(tBMajorHiddenDanger.getHiddenType())){
				tBMajorHiddenDanger.setHiddenTypeTemp(DicUtil.getTypeNameByCode("hiddenType", tBMajorHiddenDanger.getHiddenType()));
			}

			req.setAttribute("tBMajorHiddenDangerPage", tBMajorHiddenDanger);
		}
		String load = req.getParameter("load");

        /**
         * tempwork
         * */

		if ("detail".equals(load)) {
			if (Constants.HIDDEN_DANGER_CLSTATUS_REVIEW.equals(tBMajorHiddenDanger.getClStatus())){
				return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-detail-review");
			} else if (Constants.HIDDEN_DANGER_CLSTATUS_ACCEPT.equals(tBMajorHiddenDanger.getClStatus())){
				return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-detail-accept");
			} else if (Constants.HIDDEN_DANGER_CLSTATUS_FINISHED.equals(tBMajorHiddenDanger.getClStatus())){
				return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-detail-finished");
			}
			return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-detail");
		} else if ("accept".equals(load)){
			return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-accept");
		} else if ("review".equals(load)){
			return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-review");
		}
		return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-update");
	}


    /**
     * 重大隐患挂牌督办查看
     *
     * @return
     */
    @RequestMapping(params = "supervise")
    public ModelAndView supervise(String id, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(id)) {
            CriteriaQuery cq = new CriteriaQuery(SFListedSupervisionInfoEntity.class);
            cq.eq("fkHiddenInfoId", id);
            cq.add();

            List<SFListedSupervisionInfoEntity> list = systemService.getListByCriteriaQuery(cq, false);

            if (list != null && list.size()>0){
                for(SFListedSupervisionInfoEntity entity : list) {
                    if (Constants.LSILSLEVEL_BRANCH.equals(entity.getLsiIsLevel())){
                        req.setAttribute("subListedInfo", entity);
                    } else if (Constants.LSILSLEVEL_GENERAL_ADMINISTRATION.equals(entity.getLsiIsLevel())){
                        req.setAttribute("provListedInfo", entity);
                    }
                }
            }
        }
            return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-supervise");
    }

    /**
     * 隐患挂牌督办同步
     *
     * @return
     */
    @RequestMapping(params = "synSupervise")
    @ResponseBody
    public AjaxJson synSupervise(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String message = "隐患挂牌督办同步成功";
        Map<String,String> mapResult;

        //包括同步重大隐患和非重大隐患的督办信息
        mapResult = tbMajorSuperviseService.synMajorSupervise();

        //重大隐患督办同步成功
        if(null!=mapResult && mapResult.containsKey("code") && Constants.LOCAL_RESULT_CODE_SUCCESS.equals(mapResult.get("code"))){
            mapResult = tbMajorSuperviseService.synCommonSupervise();

            //非重大隐患督办同步成功
            if(null!=mapResult && mapResult.containsKey("code") && Constants.LOCAL_RESULT_CODE_SUCCESS.equals(mapResult.get("code"))){
                j.setMsg(message);
                return j;
            }

            //非重大隐患同步失败
            message = "非重大隐患督办信息同步失败";
            if(null!=mapResult && mapResult.containsKey("message")){
                message = mapResult.get("message");
            }
            j.setMsg(message);
            return j;
        }

        //重大隐患督办同步失败
        message = "重大隐患督办信息同步失败";
        if(null!=mapResult && mapResult.containsKey("message")){
            message = mapResult.get("message");
        }
        j.setMsg(message);
        return j;
    }

	/**
	 * 重大隐患五落实页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goFiveImpl")
	public ModelAndView goFiveImpl(TBMajorHiddenDangerEntity tBMajorHiddenDanger, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBMajorHiddenDanger.getId())) {
			tBMajorHiddenDanger = tBMajorHiddenDangerService.getEntity(TBMajorHiddenDangerEntity.class, tBMajorHiddenDanger.getId());
			req.setAttribute("tBMajorHiddenDangerPage", tBMajorHiddenDanger);
		}
        String load = req.getParameter("load");
        if ("detail".equals(load)) {
            return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-fiveImpl-detail");
        }
		return new ModelAndView("com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger-fiveImpl");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBMajorHiddenDangerController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBMajorHiddenDangerEntity tBMajorHiddenDanger,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBMajorHiddenDangerEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBMajorHiddenDanger, request.getParameterMap());
		List<TBMajorHiddenDangerEntity> tBMajorHiddenDangers = this.tBMajorHiddenDangerService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"重大隐患");
		modelMap.put(NormalExcelConstants.CLASS,TBMajorHiddenDangerEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("重大隐患列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBMajorHiddenDangers);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBMajorHiddenDangerEntity tBMajorHiddenDanger,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"重大隐患");
    	modelMap.put(NormalExcelConstants.CLASS,TBMajorHiddenDangerEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("重大隐患列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TBMajorHiddenDangerEntity> listTBMajorHiddenDangerEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBMajorHiddenDangerEntity.class,params);
				for (TBMajorHiddenDangerEntity tBMajorHiddenDanger : listTBMajorHiddenDangerEntitys) {
					tBMajorHiddenDangerService.save(tBMajorHiddenDanger);
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
	public List<TBMajorHiddenDangerEntity> list() {
		List<TBMajorHiddenDangerEntity> listTBMajorHiddenDangers=tBMajorHiddenDangerService.getList(TBMajorHiddenDangerEntity.class);
		return listTBMajorHiddenDangers;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TBMajorHiddenDangerEntity task = tBMajorHiddenDangerService.get(TBMajorHiddenDangerEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TBMajorHiddenDangerEntity tBMajorHiddenDanger, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBMajorHiddenDangerEntity>> failures = validator.validate(tBMajorHiddenDanger);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBMajorHiddenDangerService.save(tBMajorHiddenDanger);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tBMajorHiddenDanger.getId();
		URI uri = uriBuilder.path("/rest/tBMajorHiddenDangerController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TBMajorHiddenDangerEntity tBMajorHiddenDanger) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBMajorHiddenDangerEntity>> failures = validator.validate(tBMajorHiddenDanger);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBMajorHiddenDangerService.saveOrUpdate(tBMajorHiddenDanger);
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
		tBMajorHiddenDangerService.deleteEntityById(TBMajorHiddenDangerEntity.class, id);
	}

	/**
	 * 查看隐患状态
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getHdbiClStatus")
	@ResponseBody
	public AjaxJson getHdbiClStatus(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String status = "";
		if(StringUtil.isNotEmpty(id)){
			TBMajorHiddenDangerEntity entity = systemService.getEntity(TBMajorHiddenDangerEntity.class,id);
			status = entity.getClStatus();
		}
		j.setMsg(status);
		return j;
	}


    /**
     * 重大隐患上报
     * Author：张赛超
     * Date：2017.7.1
     * */
    @RequestMapping(params = "majorDangerReport")
    @ResponseBody
    public AjaxJson majorDangerReport(String ids,HttpServletRequest request){
            return majorDangerReportRPC.majorDangerReport(ids,false);
    }
    /**
     * QQ：1228310398
     * */


    /**
     * 根据关键字获取相关联的风险id
     * @param keywords
     * @return
     * Author：许冉
     * 复制：张赛超
     */
    private String getDangerIdByKeyword(String keywords) {
        String id = null;
        if (StringUtil.isNotEmpty(keywords)) {
            String[] keywordsArr = keywords.split(",");
            String sql = "select id, ye_mhazard_desc from t_b_danger_source where ( origin='1' or audit_status='4')";
            List<Map<String, Object>> list = systemService.findForJdbc(sql);
            if (list != null && list.size() >0) {
                int max = 0;
                for (Map<String, Object> entity : list) {
                    String name = (String) entity.get("ye_mhazard_desc");
                    if (StringUtil.isNotEmpty(name)) {
                        int count = 0;
                        for (String keyword : keywordsArr) {
                            if (name.contains(keyword)){
                                count++;
                            }
                        }
                        if (count > max) {
                            id = (String) entity.get("id");
                            max = count;
                        }
                    }
                }
            }

        }
        return id;
    }

	/**
	 * 获取待办任务
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkTask")
	@ResponseBody
	public AjaxJson checkTask(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try{
			TSUser sessionUser = ResourceUtil.getSessionUserName();
			StringBuffer sb = new StringBuffer();
			net.sf.json.JSONObject jo = new net.sf.json.JSONObject();
			//待整改
            sb.append("select count(1) count from t_b_major_hidden_danger hdh where 1=1 and hdh.cl_status='300'");
			//修改为管理员角色
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
            if(!isAdmin){
                sb.append(" and hdh.duty_unit in(")
                        .append(" select org_id from t_s_base_user tsbu,t_s_user_org WHERE")
                        .append(" tsbu.ID = t_s_user_org.user_id AND tsbu.username='").append(sessionUser.getUserName()).append("')");
            }
			List<BigInteger> dzgList = systemService.findListbySql(sb.toString());
			if(!dzgList.isEmpty() && dzgList.size()>0){
				jo.put("dzg",dzgList.get(0).intValue());
			}else{
				jo.put("dzg",0);
			}
			sb.setLength(0);
			//待复查
            sb.append("select count(1) count from t_b_major_hidden_danger hdh where 1=1 and hdh.cl_status='500'");
            if (!isAdmin) {
                sb.append(" and hdh.duty_unit in(")
                        .append(" select org_id from t_s_base_user tsbu,t_s_user_org WHERE")
                        .append(" tsbu.ID = t_s_user_org.user_id AND tsbu.username='").append(sessionUser.getUserName()).append("')");
            }
			List<BigInteger> dfcList = systemService.findListbySql(sb.toString());
			if(!dfcList.isEmpty() && dfcList.size()>0){
				jo.put("dfc",dfcList.get(0).intValue());
			}else{
				jo.put("dfc",0);
			}
			/*sb.setLength(0);
			//待验收
			sb.append("select count(1) count from t_b_major_hidden_danger hdh where 1=1 and hdh.cl_status='400' and hdh.create_by='").append(sessionUser.getUserName()).append("' and hdh.create_name='").append(sessionUser.getRealName()).append("'");
			List<BigInteger> dysList = systemService.findListbySql(sb.toString());
			if(!dysList.isEmpty() && dysList.size()>0){
				jo.put("dys",dysList.get(0).intValue());
			}else{
				jo.put("dys",0);
			}*/
			j.setObj(jo);
		}catch (Exception e) {
			j.setSuccess(false);
			e.printStackTrace();
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
					sunshineEntity.setTableName("t_b_major_hidden_danger");
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
