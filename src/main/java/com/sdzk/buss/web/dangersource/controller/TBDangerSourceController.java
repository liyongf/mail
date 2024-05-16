package com.sdzk.buss.web.dangersource.controller;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.address.service.TBAddressInfoServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.DangerSourceExcelVerifyHandler;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.common.utils.SemanticSimilarityUtil;
import com.sdzk.buss.web.dangersource.entity.*;
import com.sdzk.buss.web.dangersource.service.SynchronizationDataRPC;
import com.sdzk.buss.web.dangersource.service.TBDangerSourceServiceI;
import com.sdzk.buss.web.specialevaluation.entity.TBSeDsRelationEntity;
import com.sdzk.buss.web.system.entity.TBSunshineEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import com.sdzk.sys.synctocloud.service.SyncToCloudService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**   
 * @Title: Controller  
 * @Description: t_b_danger_source
 * @author onlineGenerator
 * @date 2017-06-20 14:18:52
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBDangerSourceController")
public class TBDangerSourceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBDangerSourceController.class);

	@Autowired
	private TBDangerSourceServiceI tBDangerSourceService;
	@Autowired
	private SystemService systemService;
    @Autowired
    private SyncToCloudService syncToCloudService;
	@Autowired
	private Validator validator;
    @Autowired
    private SynchronizationDataRPC synchronizationDataRPC;
    @Autowired
    private TBAddressInfoServiceI tBAddressInfoService;

    /**
     * 未关联风险点危险源列表
     * @param request
     * @return
     */
    @RequestMapping(params = "unrelatedDangerList")
    public ModelAndView unrelatedDangerList(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/dangersource/unrelatedDangerList");
    }

    /**
     * 部门上报危险源
     * @param request
     * @return
     */
    @RequestMapping(params = "departReportDangerList")
    public ModelAndView departReportDangerList(HttpServletRequest request, HttpServletResponse response) {
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

        return new ModelAndView("com/sdzk/buss/web/dangersource/departReportDangerList");
    }

    /**
     * 矿年度危险源审核
     * @param request
     * @return
     */
    @RequestMapping(params = "reviewDangerList")
    public ModelAndView reviewDangerList(HttpServletRequest request, HttpServletResponse response) {
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

        return new ModelAndView("com/sdzk/buss/web/dangersource/reviewDangerList");
    }

    /**
     * 矿年度危险源上报
     * @param request
     * @return
     */
    @RequestMapping(params = "reportDangerList")
    public ModelAndView reportDangerList(HttpServletRequest request, HttpServletResponse response) {
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

        return new ModelAndView("com/sdzk/buss/web/dangersource/reportDangerList");
    }
    
    
    /**
     * 矿年度危险源上报
     * @param request
     * @return
     */
    @RequestMapping(params = "sddbYearDangerList")
    public ModelAndView sddbYearDangerList(HttpServletRequest request, HttpServletResponse response) {
       
        return new ModelAndView("com/sdzk/buss/web/dangersource/sddbYearDangerList");
    }
    
    

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
		try{
		//自定义追加查询条件
            //TODO 排除专项管理的危险源
            cq.notIn("origin", new String[]{Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION,Constants.DANGER_SOURCE_ORIGIN_NOMAL});
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBDangerSourceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

    /**
     * 矿上报危险源
     * @param tBDangerSource
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "reportDangerSourceDatagrid")
    public void reportDangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String sort = dataGrid.getSort();
        if (StringUtils.isNotBlank(sort)) {
            dataGrid.setSort(sort.replace("post.postName","post.id"));
        }

        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        String queryHandleStatus = request.getParameter("queryHandleStatus");

        try{

            cq.eq("isDelete",Constants.IS_DELETE_N);
            //默认查询待上报
            queryHandleStatus = StringUtils.isBlank(queryHandleStatus)?"0":queryHandleStatus;
            //自定义追加查询条件
            if(Constants.QUERY_STATUS_UNREPORT.equals(queryHandleStatus)){
                //待上报
                //cq.eq("reportStatus", Constants.DANGER_SOURCE_REPORT_UNREPORT);
                String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};      //Author：张赛超
                cq.in("auditStatus", status);
                cq.eq("reportStatus",Constants.DANGER_SOURCE_REPORT_UNREPORT);
            }else if(Constants.QUERY_STATUS_REPORT.equals(queryHandleStatus)){
                //已上报
                String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};      //Author：张赛超
                cq.in("auditStatus", status);
                cq.eq("reportStatus", Constants.DANGER_SOURCE_REPORT_REPORT);
            }else if(Constants.QUERY_STATUS_ALL.equals(queryHandleStatus)){
                String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};      //Author：张赛超
                cq.in("auditStatus", status);
            }


            //TODO 排除专项管理的危险源
            cq.notIn("origin", new String[]{Constants.DANGER_SOURCE_ORIGIN_NOMAL});
            //cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
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
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_danger_source'";
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
//        DataGridReturn dataGridReturn = this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }




                    String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", bean.getYeRiskGrade());

                    if("重大风险".equals(yeRiskGradeTemp)){
                        bean.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                    }else if("较大风险".equals(yeRiskGradeTemp)){
                        bean.setAlertColor(Constants.ALERT_COLOR_JDFX);
                    }else if("一般风险".equals(yeRiskGradeTemp)){
                        bean.setAlertColor(Constants.ALERT_COLOR_YBFX);
                    }else{
                        bean.setAlertColor(Constants.ALERT_COLOR_DFX);
                    }
                    bean.setYeRiskGradeTemp(yeRiskGradeTemp);


            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
    
    
    /**
     * 矿上报危险源
     * @param tBDangerSource
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "sddbDatagrid")
    public void sddbDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String sort = dataGrid.getSort();
        if (StringUtils.isNotBlank(sort)) {
            dataGrid.setSort(sort.replace("post.postName","post.id"));
        }

        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        String queryHandleStatus = request.getParameter("queryHandleStatus");

        try{

            cq.eq("isDelete",Constants.IS_DELETE_N);
            //默认查询待上报
            queryHandleStatus = StringUtils.isBlank(queryHandleStatus)?"0":queryHandleStatus;
            //自定义追加查询条件
            if(Constants.QUERY_STATUS_UNREPORT.equals(queryHandleStatus)){
                //待上报
                //cq.eq("reportStatus", Constants.DANGER_SOURCE_REPORT_UNREPORT);
                String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};      //Author：张赛超
                cq.in("auditStatus", status);
                cq.eq("reportStatus",Constants.DANGER_SOURCE_REPORT_UNREPORT);
            }else if(Constants.QUERY_STATUS_REPORT.equals(queryHandleStatus)){
                //已上报
                String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};      //Author：张赛超
                cq.in("auditStatus", status);
                cq.eq("reportStatus", Constants.DANGER_SOURCE_REPORT_REPORT);
            }else if(Constants.QUERY_STATUS_ALL.equals(queryHandleStatus)){
                String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};      //Author：张赛超
                cq.in("auditStatus", status);
            }


            //TODO 排除专项管理的危险源
            cq.notIn("origin", new String[]{Constants.DANGER_SOURCE_ORIGIN_NOMAL});
            //cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
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
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_danger_source'";
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
//        DataGridReturn dataGridReturn = this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

                switch (bean.getYeHazardCate()) {
				case "1":
					bean.setYeHazardCate("水灾");
					break;
				case "2":
					bean.setYeHazardCate("火灾");
					break;
				case "3":
					bean.setYeHazardCate("瓦斯");
					break;
				case "4":
					bean.setYeHazardCate("放炮");
					break;

				default:
					bean.setYeHazardCate("机电");
					break;
				}

                switch (bean.getYeRiskGrade()) {
				case "1":
					bean.setYeRiskGrade("重大风险");
					break;
				case "2":
					bean.setYeRiskGrade("一般风险A级");
					break;
				case "3":
					bean.setYeRiskGrade("一般风险B级");
					break;
				case "4":
					bean.setYeRiskGrade("一般风险C级");
					break;

				default:
					bean.setYeRiskGrade("一般风险D级");
					break;
				}

                    String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", bean.getYeRiskGrade());

                    if("重大风险".equals(yeRiskGradeTemp)){
                        bean.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                    }else if("较大风险".equals(yeRiskGradeTemp)){
                        bean.setAlertColor(Constants.ALERT_COLOR_JDFX);
                    }else if("一般风险".equals(yeRiskGradeTemp)){
                        bean.setAlertColor(Constants.ALERT_COLOR_YBFX);
                    }else{
                        bean.setAlertColor(Constants.ALERT_COLOR_DFX);
                    }
                    bean.setYeRiskGradeTemp(yeRiskGradeTemp);


            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 专业通用危险源查询
     * @param tBDangerSource
     * @param request
     * @param response
     * @param dataGrid
     * worktemp
     */
    @RequestMapping(params = "universalDangerSourceDatagrid")
    public void universalDangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        cq.eq("isDelete",Constants.IS_DELETE_N);
        try{
            cq.eq("origin",Constants.DANGER_SOURCE_ORIGIN_NOMAL);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
//        DataGridReturn dataGridReturn = this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 年度矿危险源审核
     * @param tBDangerSource
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "reviewDangerSourceDatagrid")
    public void reviewDangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        String queryHandleStatus = request.getParameter("queryHandleStatus");

        try{
            cq.eq("isDelete",Constants.IS_DELETE_N);
            //自定义追加查询条件
            if(Constants.QUERY_STATUS_UNREPORT.equals(queryHandleStatus) || StringUtil.isEmpty(queryHandleStatus)){
                //待审核
                cq.eq("auditStatus",Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW);
            }else if(Constants.QUERY_STATUS_REPORT.equals(queryHandleStatus)){
                //已审核
                cq.in("auditStatus",new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE,Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK });
            }else if(Constants.QUERY_STATUS_ALL.equals(queryHandleStatus)){
                //全部
                cq.in("auditStatus",new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE,Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW,Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK});
            }

            //TODO 排除专项管理的危险源
            cq.notIn("origin", new String[]{Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION,Constants.DANGER_SOURCE_ORIGIN_NOMAL});
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
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
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_danger_source'";
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
//        DataGridReturn dataGridReturn = this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

                String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", bean.getYeRiskGrade());

                if("重大风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                }else if("较大风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_JDFX);
                }else if("一般风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_YBFX);
                }else{
                    bean.setAlertColor(Constants.ALERT_COLOR_DFX);
                }
                bean.setYeRiskGradeTemp(yeRiskGradeTemp);

            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 部门关联危险源
     * @param tBDangerSource
     * @param request
     * @param response
     *
     * @param dataGrid
     */
    @RequestMapping(params = "departDangerSourceDatagrid")
    public void departDangerSourceDatagrid(TBDeptDangerRelEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        TSDepart userDepart = sessionUser.getCurrentDepart();
        CriteriaQuery cq = new CriteriaQuery(TBDeptDangerRelEntity.class, dataGrid);
        cq.createAlias("danger","danger");
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        String beginDate = request.getParameter("danger.yeRecognizeTime_begin");
        String endDate = request.getParameter("danger.yeRecognizeTime_end");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            cq.eq("danger.isDelete",Constants.IS_DELETE_N);
            cq.eq("dept.id",userDepart.getId());
            if(StringUtils.isNotBlank(beginDate)){
                cq.ge("danger.yeRecognizeTime", sdf.parse(beginDate+" 00:00:00"));
            }
            if(StringUtils.isNotBlank(endDate)){
                cq.le("danger.yeRecognizeTime", sdf.parse(endDate +" 23:59:59"));
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        DataGridReturn dataGridReturn = this.systemService.getDataGridReturn(cq, true);
        if(dataGridReturn != null && !dataGridReturn.getRows().isEmpty()){
            List<TBDeptDangerRelEntity> tempList = dataGridReturn.getRows();
            for(TBDeptDangerRelEntity relBean : tempList){
                //字典
                TBDangerSourceEntity bean = relBean.getDanger();
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }


            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "chooseDangerSource")
    public ModelAndView chooseDangerSource(HttpServletRequest request) {
        String from = request.getParameter("from");
        request.setAttribute("from",from);
        request.setAttribute("year", DateUtils.getYear());
        try {
            String keys = request.getParameter("keys");
            request.setAttribute("keys", URLDecoder.decode(StringUtils.isNotBlank(keys)?keys.replace(","," "):keys,"utf-8"));
        } catch (UnsupportedEncodingException e) {
        }
        request.setAttribute("addressId",request.getParameter("addressId"));
        return new ModelAndView("com/sdzk/buss/web/dangersource/chooseDangerSourceList");
    }

    @RequestMapping(params = "chooseDatagrid")
    public void chooseDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class,dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String recognizeYear = tBDangerSource.getYeRecognizeYear();
        tBDangerSource.setYeRecognizeYear(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        String addressId = request.getParameter("addressId");
        String hazardId = request.getParameter("hazardId");
        try{
            //获取已闭合未删除的风险
            cq.eq("auditStatus",Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            cq.eq("isDelete",Constants.IS_DELETE_N);
            cq.add(Restrictions.sqlRestriction(" this_.id in (select DANGER_ID from t_b_danger_address_rel where ADDRESS_ID = '"+addressId+"')"));
            if (StringUtils.isNotBlank(recognizeYear)) {
                cq.add(Restrictions.sqlRestriction(" this_.YE_RECOGNIZE_TIME like '"+recognizeYear+"%' "));
            }
            String ids = ResourceUtil.getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                cq.add(Restrictions.sqlRestriction(" this_.hazard_manage_id in ('"+ids.replace(",","','")+"')"));
            }
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }
            if(StringUtils.isNotBlank(hazardId)){
                cq.eq("hazard.id",hazardId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

                String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", bean.getYeRiskGrade());

                if("重大风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                }else if("较大风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_JDFX);
                }else if("一般风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_YBFX);
                }else{
                    bean.setAlertColor(Constants.ALERT_COLOR_DFX);
                }
                bean.setYeRiskGradeTemp(yeRiskGradeTemp);
            }
        }
        TagUtil.datagrid(response, dataGrid);

    }

    @RequestMapping(params = "unrelatedDatagrid")
    public void unrelatedDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class,dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT DISTINCT a.danger_id FROM (").append("SELECT DISTINCT danger_id FROM t_b_danger_address_rel").append(" UNION ALL (").append("SELECT DISTINCT danger_id FROM t_b_work_danger_rel").append(")) a");
            List<String> ids = systemService.findListbySql(sql.toString());
            cq.notIn("id",ids.toArray());
            cq.eq("isDelete",Constants.IS_DELETE_N);
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
//        systemService.getDataGridReturn(cq,true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 隐患排查关联列表
     * @param request
     * @return
     */
    @RequestMapping(params = "chooseInveDangerSource")
    public ModelAndView chooseInveDangerSource(HttpServletRequest request) {
        request.setAttribute("riskPointIds",ResourceUtil.getParameter("riskPointIds"));
        request.setAttribute("riskPointType",ResourceUtil.getParameter("riskPointType"));
        request.setAttribute("ids",ResourceUtil.getParameter("ids"));
        request.setAttribute("month", ResourceUtil.getParameter("month"));
        request.setAttribute("from", ResourceUtil.getParameter("from"));
        request.setAttribute("year", DateUtils.getYear());
        request.setAttribute("inveId", ResourceUtil.getParameter("inveId"));
        return new ModelAndView("com/sdzk/buss/web/dangersource/chooseInveDangerSourceList");
    }

    @RequestMapping(params = "chooseInveDangerSourceDatagrid")
    public void chooseInveDangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class,dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String recognizeYear = tBDangerSource.getYeRecognizeYear();
        tBDangerSource.setYeRecognizeYear(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        String inveId = request.getParameter("inveId");
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            if (StringUtils.isNotBlank(inveId)) {//查询排查计划已关联的危险源
                cq.add(Restrictions.sqlRestriction("id in (select obj_id from t_b_investigate_plan_rel where plan_id='"+inveId+"' and rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISK+"')"));
            } else {//排查计划未关联危险源列表
                String riskPointIds = ResourceUtil.getParameter("riskPointIds");
                String riskPointType = ResourceUtil.getParameter("riskPointType");
                String ids = ResourceUtil.getParameter("ids");
                if (StringUtil.isEmpty(riskPointIds)) {
                    cq.add(Restrictions.sqlRestriction("1=2"));
                } else if (Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_LOCATION.equals(riskPointType)) {
                    cq.add(Restrictions.sqlRestriction("id in (select danger_id from t_b_danger_address_rel where address_id in ('" + riskPointIds.replace(",", "','") + "'))"));
                } else if (Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_WORK.equals(riskPointType)) {
                    cq.add(Restrictions.sqlRestriction("activity_id in ('" + riskPointIds.replace(",", "','") + "')"));
                } else {
                    cq.add(Restrictions.sqlRestriction("1=2"));
                }
                if (StringUtil.isNotEmpty(ids)) {
                    cq.notIn("id", ids.split(","));
                }
                /** 从月度计划中选择 **/
                if ("plan".equals(ResourceUtil.getParameter("from"))) {
                    String month = ResourceUtil.getParameter("month");
                    if (StringUtil.isNotEmpty(month)) {
                        month = month.substring(0, 7) + "-01";
                        cq.add(Restrictions.sqlRestriction("id in (select obj_id from t_b_investigate_plan_rel rel join t_b_investigate_plan plan on rel.plan_id=plan.id where rel.rel_type='" + Constants.INVESTIGATEPLAN_REL_TYPE_RISK + "' and plan.start_time='" + month + "')"));
                    } else {
                        cq.add(Restrictions.sqlRestriction("id in (select obj_id from t_b_investigate_plan_rel where rel_type='" + Constants.INVESTIGATEPLAN_REL_TYPE_RISK + "')"));
                    }
                }
            }
            cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            if (StringUtils.isNotBlank(recognizeYear)) {
                cq.add(Restrictions.sqlRestriction(" this_.YE_RECOGNIZE_TIME like '"+recognizeYear+"%' "));
            }
            String rootIds = ResourceUtil.getParameter("rootIds");
            if (StringUtils.isNotBlank(rootIds)) {
                cq.add(Restrictions.sqlRestriction(" this_.hazard_manage_id in ('"+rootIds.replace(",","','")+"')"));
            }
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }
            cq.eq("isDelete",Constants.IS_DELETE_N);
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
//        this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 选择管理危险源
     * @param request
     * @return
     */
    @RequestMapping(params = "chooseDangerSource2Rel")
    public ModelAndView chooseDangerSource2Rel(HttpServletRequest request) {
        request.setAttribute("year", DateUtils.getYear());
        return new ModelAndView("com/sdzk/buss/web/dangersource/chooseDangerSource");
    }

    /**
     * 关联专业通用危险源库
     * @param request
     * @return
     */
    @RequestMapping(params = "associateMajorGeneralDangerSource")
    public ModelAndView associateMajorGeneralDangerSource(HttpServletRequest request) {
        request.setAttribute("year", DateUtils.getYear());
        //return new ModelAndView("com/sdzk/buss/web/dangersource/chooseMajorDangerSource");
        return new ModelAndView("com/sdzk/buss/web/dangersource/chooseGeneralDangerSource");
    }

    @RequestMapping(params = "initChooseMajorGeneralDanger")
    public void initChooseMajorGeneralDanger(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //CriteriaQuery cq = new CriteriaQuery(TBDeptDangerRelEntity.class);
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class,dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String recognizeYear = tBDangerSource.getYeRecognizeYear();
        tBDangerSource.setYeRecognizeYear(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            cq.eq("origin", Constants.DANGER_SOURCE_ORIGIN_NOMAL);
            cq.eq("isDelete",Constants.IS_DELETE_N);
            if (StringUtils.isNotBlank(recognizeYear)) {
                cq.add(Restrictions.sqlRestriction(" this_.YE_RECOGNIZE_TIME like '"+recognizeYear+"%' "));
            }
            String ids = ResourceUtil.getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                cq.add(Restrictions.sqlRestriction(" this_.hazard_manage_id in ('"+ids.replace(",","','")+"')"));
            }
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
//        this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);

    }

    @RequestMapping(params = "saveChooseMajorGeneralDanger")
    @ResponseBody
    public AjaxJson saveChooseMajorGeneralDanger(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "危险源添加成功";
        String ids = request.getParameter("ids");//选择的危险源
        TSUser user = ResourceUtil.getSessionUserName();
        TSDepart dept = user.getCurrentDepart();
        try{
            if(org.apache.commons.lang.StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                //TBDeptDangerRelEntity tbDeptDangerRelEntity = null;
                for(String id : idArray){
                    //tbDeptDangerRelEntity = new TBDeptDangerRelEntity();
                    //tbDeptDangerRelEntity.setDept(dept);
                    TBDangerSourceEntity t = this.systemService.getEntity(TBDangerSourceEntity.class,id);
                    TBDangerSourceEntity tBDangerSourceEntity = new TBDangerSourceEntity();
                    MyBeanUtils.copyBeanNotNull2Bean(t, tBDangerSourceEntity);
                    //tbDeptDangerRelEntity.setDanger(t);

                    tBDangerSourceEntity.setId(UUIDGenerator.generate());
                    tBDangerSourceEntity.setIsDelete(Constants.IS_DELETE_N);
                    tBDangerSourceEntity.setReportDepart(dept);
                    tBDangerSourceEntity.setOrigin(Constants.DANGER_SOURCE_ORIGIN_MINE);
                    tBDangerSourceEntity.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT);
                    tBDangerSourceEntity.setReportStatus(Constants.DANGER_SOURCE_REPORT_UNREPORT);

                    this.systemService.save(tBDangerSourceEntity);
                    systemService.addLog("部门关联危险源添加\""+tBDangerSourceEntity.getId()+"\"成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "部门关联危险源添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    @RequestMapping(params = "initChooseGeneralDanger")
    public void initChooseGeneralDanger(TBGeneralDangerSourceEntity tBGeneralDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBGeneralDangerSourceEntity.class,dataGrid);
        String dangerSourceName = tBGeneralDangerSource.getYeMhazardDesc();
        tBGeneralDangerSource.setYeMhazardDesc(null);
        String recognizeYear = tBGeneralDangerSource.getYeRecognizeYear();
        tBGeneralDangerSource.setYeRecognizeYear(null);
        String possiblyHazard = tBGeneralDangerSource.getYePossiblyHazard();
        tBGeneralDangerSource.setYePossiblyHazard(null);
        String docSource = tBGeneralDangerSource.getDocSource();
        tBGeneralDangerSource.setDocSource(null);
        String standard = tBGeneralDangerSource.getYeStandard();
        tBGeneralDangerSource.setYeStandard(null);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBGeneralDangerSource, request.getParameterMap());
        try{
            cq.eq("isDelete",Constants.IS_DELETE_N);
            if (StringUtils.isNotBlank(recognizeYear)) {
                cq.add(Restrictions.sqlRestriction(" this_.YE_RECOGNIZE_TIME like '"+recognizeYear+"%' "));
            }
            String ids = ResourceUtil.getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                cq.add(Restrictions.sqlRestriction(" this_.hazard_manage_id in ('"+ids.replace(",","','")+"')"));
            }
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
//        this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBGeneralDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBGeneralDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);

    }

    @RequestMapping(params = "saveChooseGeneralDanger")
    @ResponseBody
    public AjaxJson saveChooseGeneralDanger(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "危险源添加成功";
        String ids = request.getParameter("ids");//选择的危险源
        TSUser user = ResourceUtil.getSessionUserName();
        TSDepart dept = user.getCurrentDepart();
        try{
            if(org.apache.commons.lang.StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                for(String id : idArray){
                    TBGeneralDangerSourceEntity t = this.systemService.getEntity(TBGeneralDangerSourceEntity.class,id);
                    TBDangerSourceEntity tBDangerSourceEntity = new TBDangerSourceEntity();
                    MyBeanUtils.copyBeanNotNull2Bean(t, tBDangerSourceEntity);

                    tBDangerSourceEntity.setId(UUIDGenerator.generate());
                    tBDangerSourceEntity.setIsDelete(Constants.IS_DELETE_N);
                    tBDangerSourceEntity.setReportDepart(dept);
                    tBDangerSourceEntity.setOrigin(Constants.DANGER_SOURCE_ORIGIN_MINE);
                    tBDangerSourceEntity.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT);
                    tBDangerSourceEntity.setReportStatus(Constants.DANGER_SOURCE_REPORT_UNREPORT);
                    tBDangerSourceEntity.setYeRecognizeTime(new Date());
                    tBDangerSourceEntity.setHazard(null);
                    tBDangerSourceEntity.setActivity(null);
                    tBDangerSourceEntity.setPost(null);
                    tBDangerSourceService.save(tBDangerSourceEntity);
                    systemService.addLog("部门关联危险源添加\""+tBDangerSourceEntity.getId()+"\"成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "部门关联危险源添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "initChooseDanger")
    public void initChooseDanger(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDeptDangerRelEntity.class);
        TSUser user = ResourceUtil.getSessionUserName();
        TSDepart dept = user.getCurrentDepart();
        //1.查询出自己已经关联的危险源
        try{
            cq.eq("dept.id",dept.getId());

        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        List<TBDeptDangerRelEntity> choosedList = systemService.getListByCriteriaQuery(cq,false);
        List<String> choosedangerId = new ArrayList<String>();
        if(choosedList != null && !choosedList.isEmpty()){
            for(TBDeptDangerRelEntity temp : choosedList){
                choosedangerId.add(temp.getDanger().getId());
            }
        }

        cq = new CriteriaQuery(TBDangerSourceEntity.class,dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String recognizeYear = tBDangerSource.getYeRecognizeYear();
        tBDangerSource.setYeRecognizeYear(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            if(!choosedangerId.isEmpty()){
                cq.notIn("id",choosedangerId.toArray());
            }
            if (StringUtils.isNotBlank(recognizeYear)) {
                cq.add(Restrictions.sqlRestriction(" this_.YE_RECOGNIZE_TIME like '"+recognizeYear+"%' "));
            }
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }
            cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            cq.eq("isDelete",Constants.IS_DELETE_N);
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
//        this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);

    }



    @RequestMapping(params = "saveChooseDanger")
    @ResponseBody
    public AjaxJson saveChooseDanger(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "危险源添加成功";
        String ids = request.getParameter("ids");//选择的危险源
        TSUser user = ResourceUtil.getSessionUserName();
        TSDepart dept = user.getCurrentDepart();
        try{
            if(org.apache.commons.lang.StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                TBDeptDangerRelEntity tbDeptDangerRelEntity = null;
                for(String id : idArray){
                    tbDeptDangerRelEntity = new TBDeptDangerRelEntity();
                    tbDeptDangerRelEntity.setDept(dept);
                    TBDangerSourceEntity t = this.systemService.getEntity(TBDangerSourceEntity.class,id);
                    tbDeptDangerRelEntity.setDanger(t);

                    this.systemService.save(tbDeptDangerRelEntity);
                    systemService.addLog("部门关联危险源添加\""+tbDeptDangerRelEntity.getId()+"\"成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "部门关联危险源添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    /**
     * 部门年度危险源查询
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "departReportDangerSourceDatagrid")
    public void departReportDangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        TSDepart userDepart = sessionUser.getCurrentDepart();
        String sort = dataGrid.getSort();
        if (StringUtils.isNotBlank(sort)) {
            dataGrid.setSort(sort.replace("post.postName","post.id"));
        }

        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        String queryHandleStatus = request.getParameter("queryHandleStatus");

        try{
            cq.eq("isDelete",Constants.IS_DELETE_N);
            //自定义追加查询条件
            if(Constants.QUERY_STATUS_UNREPORT.equals(queryHandleStatus) || StringUtil.isEmpty(queryHandleStatus)){
                String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT,Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK};
                cq.in("auditStatus", status);
            }else if(Constants.QUERY_STATUS_REPORT.equals(queryHandleStatus)){
                String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW,Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};
                cq.in("auditStatus",status);
            }
            cq.eq("reportDepart.id",userDepart.getId());
            //TODO 排除专项管理的危险源
            cq.notIn("origin", new String[]{Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION,Constants.DANGER_SOURCE_ORIGIN_NOMAL});
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
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
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_danger_source'";
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
//        DataGridReturn dataGridReturn = this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
//        queryByDangerSourceName(hazardName, cq, dataGrid);

        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }
                String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", bean.getYeRiskGrade());

                if("重大风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                }else if("较大风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_JDFX);
                }else if("一般风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_YBFX);
                }else{
                    bean.setAlertColor(Constants.ALERT_COLOR_DFX);
                }
                bean.setYeRiskGradeTemp(yeRiskGradeTemp);

            }
        }
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 查看危险源是否可以审核
     * @param request
     * @return
     */
    @RequestMapping(params = "canReviewDangerSource")
    @ResponseBody
    public AjaxJson canReviewDangerSource(HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        String id = request.getParameter("id");
        String ids = request.getParameter("ids");
        String status = "";
        if(StringUtils.isNotBlank(id)){
            TBDangerSourceEntity entity = systemService.getEntity(TBDangerSourceEntity.class,id);
            if(entity != null){
                status = entity.getAuditStatus();
            }
        }else if(StringUtils.isNotBlank(ids)){
            String [] idArray = ids.split(",");
            if(idArray != null && idArray.length >0){
                status = Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW;
                for(String str : idArray){
                    TBDangerSourceEntity entity = systemService.getEntity(TBDangerSourceEntity.class,str);
                    if(entity != null){
                        if(!Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW.equals(entity.getAuditStatus())){
                            status = entity.getAuditStatus();
                            break;
                        }
                    }
                }
            }
        }
        j.setMsg(status);
        return j;
    }

    /**
     * 查看危险源审核状态
     *
     * @return
     */
    @RequestMapping(params = "canModifyDangerSource")
    @ResponseBody
    public AjaxJson canModifyDangerSource(HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        String id = request.getParameter("id");
        String ids = request.getParameter("ids");
        String status = "";
        if(StringUtils.isNotBlank(id)){
            TBDangerSourceEntity entity = systemService.getEntity(TBDangerSourceEntity.class,id);
            if(entity != null){
                    status = entity.getAuditStatus();
            }
        }else if(StringUtils.isNotBlank(ids)){
            String [] idArray = ids.split(",");
            if(idArray != null && idArray.length >0){
                status = Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT;
                for(String str : idArray){
                    TBDangerSourceEntity entity = systemService.getEntity(TBDangerSourceEntity.class,str);
                    if(entity != null){
                        if(!Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT.equals(entity.getAuditStatus()) && !Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK.equals(entity.getAuditStatus())){
                            status = entity.getAuditStatus();
                            break;
                        }
                    }
                }
            }
        }

        j.setMsg(status);
        return j;
    }

    /**
     * 校验是否可上报审核危险源
     *
     * @return
     */
    @RequestMapping(params = "checkForAudit")
    @ResponseBody
    public AjaxJson checkForAudit(HttpServletRequest request) {
        String message = "可以上报审核";
        boolean canAudit = true;
        AjaxJson j = new AjaxJson();
        String id = request.getParameter("id");
        String ids = request.getParameter("ids");
        if(StringUtils.isNotBlank(id)){
            TBDangerSourceEntity entity = systemService.getEntity(TBDangerSourceEntity.class,id);
            if(null==entity){
                message = "风险已被删除,请刷新页面";
                canAudit = false;
            }else if(null==entity.getActivity()){
                message = "风险未关联作业活动，请编辑后上报审核！";
                canAudit = false;
            }else if(null==entity.getHazard()){
                message = "风险未关联危险源，请编辑后上报审核！";
                canAudit = false;
            }else if(null==entity.getPost()){
                message = "风险未关联责任岗位，请编辑后上报审核！";
                canAudit = false;
            }
            j.setMsg(message);
        }else if(StringUtils.isNotBlank(ids)){
            String [] idArray = ids.split(",");
            if(idArray != null && idArray.length >0){
                for(String str : idArray){
                    TBDangerSourceEntity entity = systemService.getEntity(TBDangerSourceEntity.class,str);
                    if(null==entity){
                        message = "已选某个风险已被删除,请刷新页面";
                        canAudit = false;
                        break;
                    }else if(null==entity.getActivity()){
                        message = "已选某个风险未关联作业活动，请编辑后上报审核！";
                        canAudit = false;
                        break;
                    }else if(null==entity.getHazard()){
                        message = "已选某个风险未关联危险源，请编辑后上报审核！";
                        canAudit = false;
                        break;
                    }else if(null==entity.getPost()){
                        message = "已选某个风险未关联责任岗位，请编辑后上报审核！";
                        canAudit = false;
                        break;
                    }
                }
            }
        }

        j.setMsg(message);
        j.setSuccess(canAudit);
        return j;
    }

    @RequestMapping(params = "doReport2Mine")
    @ResponseBody
    public AjaxJson doReport2Mine(HttpServletRequest request) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        String message = null;
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        message = "上报成功";
        try{
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                for(String id : idArray){
                    TBDangerSourceEntity tBDangerSource = systemService.getEntity(TBDangerSourceEntity.class, id);
                    //上报待审核
                    tBDangerSource.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW);
                    tBDangerSource.setDepartReportTime(new Date());
                    tBDangerSource.setDepartReportMan(sessionUser);

                    systemService.saveOrUpdate(tBDangerSource);
                    //处理历史表添加一条记录
                    TBDangerSourceAuditHisEntity hisEntity = new TBDangerSourceAuditHisEntity();
                    hisEntity.setDanger(tBDangerSource);
                    hisEntity.setDealTime(new Date());
                    hisEntity.setDealStep(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_REPORT);
                    hisEntity.setDealDesc("上报");
                    hisEntity.setDealManName(sessionUser.getRealName());

                    systemService.save(hisEntity);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "上报失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doReportAll2Mine")
    @ResponseBody
    public AjaxJson doReportAll2Mine(HttpServletRequest request) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        TSDepart userDepart = sessionUser.getCurrentDepart();
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "全部上报审核成功";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sql = "";
            String sqlUpdate = "update t_b_danger_source ";
            String sqlWhere = " where is_delete='0' and audit_status in ('" + Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT + "','" + Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK + "')";
            sqlWhere = sqlWhere + " and report_depart_id='" + userDepart.getId() + "'";
            sqlWhere = sqlWhere + " and origin='" + Constants.DANGER_SOURCE_ORIGIN_MINE + "'";

            //处理历史表添加一条记录
            sql = "insert into t_b_danger_source_audit_his select REPLACE(UUID(),'-','') as id, id as danger_id, NOW() as deal_time,'"+Constants.DANGER_SOURCE_AUDIT_HIS_STEP_REPORT
                    + "' as deal_step, '上报' as deal_desc, '"+sessionUser.getRealName() + "' as deal_man_name from t_b_danger_source" + sqlWhere;
            this.systemService.executeSql(sql);

            //上报待审核
            sql = sqlUpdate + " set audit_status='" + Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW + "',depart_report_time='" + sdf.format(new Date())
                    + "', depart_report_man='" + sessionUser.getId() + "' " + sqlWhere;
            Integer count = this.systemService.executeSql(sql);
            if(null!=count && count.intValue()>=0){
                message = "成功上报至待审核"+count.toString()+"条风险";
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "全部上报至审核失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
	/**
	 * 删除t_b_danger_source
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBDangerSourceEntity tBDangerSource, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBDangerSource = systemService.getEntity(TBDangerSourceEntity.class, tBDangerSource.getId());
		message = "删除成功";
		try{
//			tBDangerSourceService.delete(tBDangerSource);
            tBDangerSource.setIsDelete(Constants.IS_DELETE_Y);
            tBDangerSourceService.saveOrUpdate(tBDangerSource);
            //如果是删除专项风险，则同时删除专项与风险关联关系
            if(Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION.equals(tBDangerSource.getOrigin())){
                List<TBSeDsRelationEntity> tbSeDsRelationEntityList = systemService.findByProperty(TBSeDsRelationEntity.class, "dangerSourceId", tBDangerSource.getId());
                systemService.deleteAllEntitie(tbSeDsRelationEntityList);
            }
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除t_b_danger_source
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "删除成功";
		try{
			for(String id:ids.split(",")){
				TBDangerSourceEntity tBDangerSource = systemService.getEntity(TBDangerSourceEntity.class,
				id
				);
                tBDangerSource.setIsDelete(Constants.IS_DELETE_Y);
				tBDangerSourceService.saveOrUpdate(tBDangerSource);
                //如果是删除专项风险，则同时删除专项与风险关联关系
                if(Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION.equals(tBDangerSource.getOrigin())){
                    List<TBSeDsRelationEntity> tbSeDsRelationEntityList = systemService.findByProperty(TBSeDsRelationEntity.class, "dangerSourceId", id);
                    systemService.deleteAllEntitie(tbSeDsRelationEntityList);
                }
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
    /**
     * 批量逻辑删除t_b_danger_source
     *
     * @return
     */
    @RequestMapping(params = "logicDoBatchDel")
    @ResponseBody
    public AjaxJson logicDoBatchDel(String ids,HttpServletRequest request){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "删除成功";
        try{
            for(String id:ids.split(",")){
                TBDangerSourceEntity tBDangerSource = systemService.getEntity(TBDangerSourceEntity.class,
                        id
                );
                tBDangerSource.setIsDelete(Constants.IS_DELETE_Y);
                tBDangerSourceService.saveOrUpdate(tBDangerSource);
                //如果是删除专项风险，则同时删除专项与风险关联关系
                if(Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION.equals(tBDangerSource.getOrigin())){
                    List<TBSeDsRelationEntity> tbSeDsRelationEntityList = systemService.findByProperty(TBSeDsRelationEntity.class, "dangerSourceId", id);
                    systemService.deleteAllEntitie(tbSeDsRelationEntityList);
                }
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 重大风险列表
     * @param request
     * @return
     */
    @RequestMapping(params = "majorDangerList")
    public ModelAndView majorDangerList(HttpServletRequest request) {
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

        return new ModelAndView("com/sdzk/buss/web/dangersource/majorDangerList");
    }

    /**
     * 异步获取重大风险数据
     * @param tBDangerSource
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "majorDangerSourceDatagrid")
    public void majorDangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());

        try{
            //自定义追加查询条件
            cq.eq("yeRiskGrade","1");
            // 排除专业通用危险源
//            cq.notEq("origin", Constants.DANGER_SOURCE_ORIGIN_NOMAL);
            cq.eq("isDelete",Constants.IS_DELETE_N);

            //仅显示闭环的重大风险
            cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
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
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_danger_source'";
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
//        DataGridReturn dataGridReturn = this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }
                String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", bean.getYeRiskGrade());

                if("重大风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                }else if("较大风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_JDFX);
                }else if("一般风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_YBFX);
                }else{
                    bean.setAlertColor(Constants.ALERT_COLOR_DFX);
                }
                bean.setYeRiskGradeTemp(yeRiskGradeTemp);
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
    /**
     * 重大风险标记
     *
     * @return
     */
    @RequestMapping(params = "doIsMajor")
    @ResponseBody
    public AjaxJson doIsMajor(String ids,String isMajor,HttpServletRequest request){
        String message = null;
        AjaxJson j = new AjaxJson();
        if ("1".equals(isMajor)){
            message = "设置重大风险标记成功";
        } else {
            message = "撤除重大风险标记成功";
        }
        try{
            StringBuffer idsb = new StringBuffer("(");
            for(String id:ids.split(",")){
                idsb.append("'").append(id).append("'").append(",");
            }
            idsb.append("'')");
            String sql = "update t_b_danger_source set ismajor ='"+isMajor+"' where id in "+idsb.toString();
            systemService.updateBySqlString(sql);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            if ("1".equals(isMajor)){
                message = "设置重大风险标记失败";
            } else {
                message = "撤除重大风险标记失败";
            }
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 克隆年度部门危险源
     * @param request
     * @return
     */
    @RequestMapping(params = "doCloneYearDanger")
    @ResponseBody
    public AjaxJson doCloneYearDanger(String from, String to, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        if(StringUtils.isNotBlank(from) && StringUtils.isNotBlank(to)){
            message = from + "年年度风险成功复制至" + to;
            //TODO 复制年度风险
            TSUser user = ResourceUtil.getSessionUserName();
            TSDepart dept = user.getCurrentDepart();
            try {
                List<String> dangerIds = systemService.findListbySql("select id from t_b_danger_source where is_delete!='1' and origin='2' and audit_status='4' and ye_recognize_time like '" + from + "%' ");
                if (!dangerIds.isEmpty() && dangerIds.size() > 0) {
                    for (String id : dangerIds) {
                        TBDangerSourceEntity t = this.systemService.getEntity(TBDangerSourceEntity.class, id);
                        TBDangerSourceEntity tBDangerSourceEntity = new TBDangerSourceEntity();
                        MyBeanUtils.copyBeanNotNull2Bean(t, tBDangerSourceEntity);

                        tBDangerSourceEntity.setId(UUIDGenerator.generate());
                        tBDangerSourceEntity.setIsDelete(Constants.IS_DELETE_N);
                        tBDangerSourceEntity.setReportDepart(dept);
                        tBDangerSourceEntity.setOrigin(Constants.DANGER_SOURCE_ORIGIN_MINE);
                        tBDangerSourceEntity.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT);
                        tBDangerSourceEntity.setReportStatus(Constants.DANGER_SOURCE_REPORT_UNREPORT);

                        tBDangerSourceEntity.setYeRecognizeTime(DateUtils.stringToDate(to));

                        tBDangerSourceService.save(tBDangerSourceEntity);
                        systemService.addLog("部门风险\"" + tBDangerSourceEntity.getId() + "\"复制成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = "年度风险复制失败";
                systemService.addLog(message + "：" + e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_INSERT);
                throw new BusinessException(e.getMessage());
            }
        }else{
            message = "年度风险复制失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加年度部门危险源
     * @param tBDangerSource
     * @param request
     * @return
     */
    @RequestMapping(params = "doAddDepartDangerSource")
    @ResponseBody
    public AjaxJson doAddDepartDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "添加成功";

        String reportStatus = ResourceUtil.getParameter("reportStatus");
        tBDangerSource.setIsDelete(Constants.IS_DELETE_N);


        try{
            tBDangerSource.setId(UUIDGenerator.generate());
            tBDangerSource.setOrigin(Constants.DANGER_SOURCE_ORIGIN_MINE);

            TSUser sessionUser = ResourceUtil.getSessionUserName();
            tBDangerSource.setReportDepart(sessionUser.getCurrentDepart());

            if ("1".equals(reportStatus)){
                //上报待审核
                tBDangerSource.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW);
                tBDangerSource.setDepartReportTime(new Date());
                tBDangerSource.setDepartReportMan(sessionUser);
                //处理历史表添加一条记录
                TBDangerSourceAuditHisEntity hisEntity = new TBDangerSourceAuditHisEntity();
                hisEntity.setDanger(tBDangerSource);
                hisEntity.setDealTime(new Date());
                hisEntity.setDealStep(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_REPORT);
                hisEntity.setDealDesc("上报");
                hisEntity.setDealManName(sessionUser.getRealName());

                systemService.save(hisEntity);
            } else {
                tBDangerSource.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT);
            }
            tBDangerSource.setReportStatus(Constants.DANGER_SOURCE_REPORT_UNREPORT);

            String riskGrade = DicUtil.getTypeCodeByName("riskLevel",tBDangerSource.getYeRiskGrade());
            tBDangerSource.setYeRiskGrade(riskGrade);
            if("重大风险".equals(tBDangerSource.getYeRiskGrade()) || "1".equals(tBDangerSource.getYeRiskGrade())){
                tBDangerSource.setIsMajor("1");
            }else{
                tBDangerSource.setIsMajor("0");
            }

            tBDangerSourceService.save(tBDangerSource);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 执行审核年度矿危险源
     * @param request
     * @return
     */
    @RequestMapping(params = "doReviewDangerSource")
    @ResponseBody
    public AjaxJson doReviewDangerSource(HttpServletRequest request) {
        String message = null;
        String ids = request.getParameter("ids");
        String reviewResult = request.getParameter("reviewResult");
        String remark = request.getParameter("remark");
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        AjaxJson j = new AjaxJson();
        message = "审核成功";
        try{

            String sql = "";
            String sqlUpdate = "update t_b_danger_source ";
            String sqlWhere = " where is_delete='0' and audit_status in ('" + Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW + "')";
            sqlWhere = sqlWhere + " and origin='" + Constants.DANGER_SOURCE_ORIGIN_MINE + "'";
            if(StringUtil.isNotEmpty(ids)) {
                sqlWhere = sqlWhere + " and id in ('" + ids.replaceAll(",","','") + "')";
            }

            if(Constants.REVIEW_RESULT_PASS.equals(reviewResult)) {

                //上报部门自动关联该危险源
                sql = "insert into t_b_dept_danger_rel select REPLACE(UUID(),'-','') as id, report_depart_id as dept_id, id as danger_id from t_b_danger_source" + sqlWhere;
                this.systemService.executeSql(sql);
                systemService.addLog("部门关联危险源添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);

                //处理历史表添加一条记录
                sql = "insert into t_b_danger_source_audit_his select REPLACE(UUID(),'-','') as id, id as danger_id, NOW() as deal_time,'"+Constants.DANGER_SOURCE_AUDIT_HIS_STEP_CLOSE
                        + "' as deal_step, '审核通过' as deal_desc, '"+sessionUser.getRealName() + "' as deal_man_name from t_b_danger_source" + sqlWhere;
                this.systemService.executeSql(sql);

                //审核通过
                sql = sqlUpdate + "set audit_status='" + Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE + "' " + sqlWhere;
                Integer count = this.systemService.executeSql(sql);
                if(null!=count && count.intValue()>=0){
                    message = "审核成功"+count.toString()+"条风险";
                }

                //添加风险的隐患描述分词缓存
                this.systemService.initAllDSParts();
            }else if(Constants.REVIEW_RESULT_ROLLBANK.equals(reviewResult)){

                //处理历史表添加一条记录
                sql = "insert into t_b_danger_source_audit_his select REPLACE(UUID(),'-','') as id, id as danger_id, NOW() as deal_time,'"+Constants.DANGER_SOURCE_AUDIT_HIS_STEP_ROLLBANK
                        + "' as deal_step,'" + remark + "' as deal_desc, '"+sessionUser.getRealName() + "' as deal_man_name from t_b_danger_source" + sqlWhere;
                this.systemService.executeSql(sql);

                //审核驳回
                sql = sqlUpdate + "set audit_status='" + Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK + "' " + sqlWhere;
                Integer count = this.systemService.executeSql(sql);
                if(null!=count && count.intValue()>=0){
                    message = "驳回成功"+count.toString()+"条风险";
                }
                message = "驳回成功";
            }
			if(StringUtils.isNotBlank(ids)){
				syncToCloudService.dangerSourceBatchReport(ids);
			}
        }catch(Exception e){
            e.printStackTrace();
            message = "审核失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 修改保存部门年度危险源
     * @param tBDangerSource
     * @param request
     * @return
     */
    @RequestMapping(params = "doUpdateDepartDangerSource")
    @ResponseBody
    public AjaxJson doUpdateDepartDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "修改成功";
        TBDangerSourceEntity t = tBDangerSourceService.get(TBDangerSourceEntity.class, tBDangerSource.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBDangerSource, t);
            TSUser sessionUser = ResourceUtil.getSessionUserName();
            String reportStatus = ResourceUtil.getParameter("reportStatus");
            t.setReportStatus(Constants.DANGER_SOURCE_REPORT_UNREPORT);
            if ("1".equals(reportStatus)){
                //上报待审核
                t.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW);
                t.setDepartReportTime(new Date());
                t.setDepartReportMan(sessionUser);
                //处理历史表添加一条记录
                TBDangerSourceAuditHisEntity hisEntity = new TBDangerSourceAuditHisEntity();
                hisEntity.setDanger(tBDangerSource);
                hisEntity.setDealTime(new Date());
                hisEntity.setDealStep(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_REPORT);
                hisEntity.setDealDesc("上报");
                hisEntity.setDealManName(sessionUser.getRealName());

                systemService.save(hisEntity);
            }

            String riskGrade = DicUtil.getTypeCodeByName("riskLevel",t.getYeRiskGrade());
            t.setYeRiskGrade(riskGrade);
            if("重大风险".equals(tBDangerSource.getYeRiskGrade()) || "1".equals(tBDangerSource.getYeRiskGrade())){
                t.setIsMajor("1");
            }else{
                t.setIsMajor("0");
            }

            tBDangerSourceService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "修改失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 修改保存部门年度危险源
     * @param tBDangerSource
     * @param request
     * @return
     */
    @RequestMapping(params = "doUpdateDangerSourceOnCheck")
    @ResponseBody
    public AjaxJson doUpdateDangerSourceOnCheck(TBDangerSourceEntity tBDangerSource, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "修改成功";
        TBDangerSourceEntity t = tBDangerSourceService.get(TBDangerSourceEntity.class, tBDangerSource.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBDangerSource, t);
            TSUser sessionUser = ResourceUtil.getSessionUserName();
            String reportStatus = ResourceUtil.getParameter("reportStatus");
            String riskGrade = DicUtil.getTypeCodeByName("riskLevel",t.getYeRiskGrade());
            t.setYeRiskGrade(riskGrade);
            if("重大风险".equals(tBDangerSource.getYeRiskGrade()) || "1".equals(tBDangerSource.getYeRiskGrade())){
                t.setIsMajor("1");
            }else{
                t.setIsMajor("0");
            }

            tBDangerSourceService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "修改失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    /**
     * 复制年度危险源
     * @param tBDangerSource
     * @param req
     * @return
     */
    @RequestMapping(params = "goCloneYearDanger")
    public ModelAndView goCloneYearDanger(TBDangerSourceEntity tBDangerSource, HttpServletRequest req) {
        return new ModelAndView("com/sdzk/buss/web/dangersource/cloneYearDanger");
    }

    /**
     * 跳转到审核页面
     * @param request
     * @return
     */
    @RequestMapping(params = "goReviewDangerSource")
    public ModelAndView goReviewDangerSource(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        request.setAttribute("ids",ids);
        return new ModelAndView("com/sdzk/buss/web/dangersource/goReviewDangerSource");
    }

    /**
     * 添加年度危险源
     * @param tBDangerSource
     * @param req
     * @return
     */
    @RequestMapping(params = "goAddDepartDangerSource")
    public ModelAndView goAddDepartDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBDangerSource.getId())) {
            tBDangerSource = tBDangerSourceService.getEntity(TBDangerSourceEntity.class, tBDangerSource.getId());
            req.setAttribute("tBDangerSourcePage", tBDangerSource);
        }

        TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
        List<TSType> tsTypeList = tsTypegroup.getTSTypes();
        req.setAttribute("tsTypeList",tsTypeList);

        List<TSType> damageTypeList = systemService.getTypeGroup("danger_Category","危险类别").getTSTypes();
        req.setAttribute("damageTypeList",damageTypeList);

        String threshold_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "重大风险阀值");
        String threshold_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "较大风险阀值");
        String threshold_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "一般风险阀值");

        String threshold_lec_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "重大风险阀值");
        String threshold_lec_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "较大风险阀值");
        String threshold_lec_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "一般风险阀值");

        req.setAttribute("threshold_major", StringUtils.isBlank(threshold_major)?"25":threshold_major);
        req.setAttribute("threshold_superior", StringUtils.isBlank(threshold_superior)?"16":threshold_superior);
        req.setAttribute("threshold_commonly", StringUtils.isBlank(threshold_commonly)?"8":threshold_commonly);
        req.setAttribute("threshold_lec_major", StringUtils.isBlank(threshold_lec_major)?"270":threshold_lec_major);
        req.setAttribute("threshold_lec_superior", StringUtils.isBlank(threshold_lec_superior)?"140":threshold_lec_superior);
        req.setAttribute("threshold_lec_commonly", StringUtils.isBlank(threshold_lec_commonly)?"70":threshold_lec_commonly);
        return new ModelAndView("com/sdzk/buss/web/dangersource/addDepartDangerSource_new");
    }


    /**
     * 部门年度危险源列表
     * @param request
     * @return
     */
    @RequestMapping(params = "departDangerList")
    public ModelAndView departDangerList( HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/dangersource/departDangerList");
    }

    /**
     * 专业通用危险源库
     * @param request
     * @return
     */
    @RequestMapping(params = "universalDangerList")
    public ModelAndView universalDangerList( HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/dangersource/universalDangerList");
    }

    /**
     * 更新年度危险源库
     * @param tBDangerSource
     * @param req
     * @return
     */
    @RequestMapping(params = "goUpdateDepartDangerSource")
    public ModelAndView goUpdateDepartDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBDangerSource.getId())) {
            tBDangerSource = tBDangerSourceService.getEntity(TBDangerSourceEntity.class, tBDangerSource.getId());

            String riskGrade = tBDangerSource.getYeRiskGrade();
            if(StringUtils.isNotBlank(riskGrade)){
                tBDangerSource.setYeRiskGradeTemp(DicUtil.getTypeNameByCode("riskLevel",riskGrade));
            }

            req.setAttribute("tBDangerSourcePage", tBDangerSource);
        }

        TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
        List<TSType> tsTypeList = tsTypegroup.getTSTypes();
        req.setAttribute("tsTypeList",tsTypeList);

        List<TSType> damageTypeList = systemService.getTypeGroup("danger_Category","危险类别").getTSTypes();
        req.setAttribute("damageTypeList",damageTypeList);


        String threshold_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "重大风险阀值");
        String threshold_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "较大风险阀值");
        String threshold_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "一般风险阀值");

        String threshold_lec_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "重大风险阀值");
        String threshold_lec_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "较大风险阀值");
        String threshold_lec_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "一般风险阀值");

        req.setAttribute("threshold_major", StringUtils.isBlank(threshold_major)?"25":threshold_major);
        req.setAttribute("threshold_superior", StringUtils.isBlank(threshold_superior)?"16":threshold_superior);
        req.setAttribute("threshold_commonly", StringUtils.isBlank(threshold_commonly)?"8":threshold_commonly);
        req.setAttribute("threshold_lec_major", StringUtils.isBlank(threshold_lec_major)?"270":threshold_lec_major);
        req.setAttribute("threshold_lec_superior", StringUtils.isBlank(threshold_lec_superior)?"140":threshold_lec_superior);
        req.setAttribute("threshold_lec_commonly", StringUtils.isBlank(threshold_lec_commonly)?"70":threshold_lec_commonly);

        return new ModelAndView("com/sdzk/buss/web/dangersource/updateDepartDangerSource_new");
    }
    /**
     * 审核页面更新年度危险源库
     * @param tBDangerSource
     * @param req
     * @return
     */
    @RequestMapping(params = "goUpdateDangerSourceOnCheck")
    public ModelAndView goUpdateDangerSourceOnCheck(TBDangerSourceEntity tBDangerSource, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBDangerSource.getId())) {
            tBDangerSource = tBDangerSourceService.getEntity(TBDangerSourceEntity.class, tBDangerSource.getId());

            String riskGrade = tBDangerSource.getYeRiskGrade();
            if(StringUtils.isNotBlank(riskGrade)){
                tBDangerSource.setYeRiskGradeTemp(DicUtil.getTypeNameByCode("riskLevel",riskGrade));
            }

            req.setAttribute("tBDangerSourcePage", tBDangerSource);
        }

        TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
        List<TSType> tsTypeList = tsTypegroup.getTSTypes();
        req.setAttribute("tsTypeList",tsTypeList);

        List<TSType> damageTypeList = systemService.getTypeGroup("danger_Category","危险类别").getTSTypes();
        req.setAttribute("damageTypeList",damageTypeList);


        String threshold_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "重大风险阀值");
        String threshold_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "较大风险阀值");
        String threshold_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "一般风险阀值");

        String threshold_lec_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "重大风险阀值");
        String threshold_lec_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "较大风险阀值");
        String threshold_lec_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "一般风险阀值");

        req.setAttribute("threshold_major", StringUtils.isBlank(threshold_major)?"25":threshold_major);
        req.setAttribute("threshold_superior", StringUtils.isBlank(threshold_superior)?"16":threshold_superior);
        req.setAttribute("threshold_commonly", StringUtils.isBlank(threshold_commonly)?"8":threshold_commonly);
        req.setAttribute("threshold_lec_major", StringUtils.isBlank(threshold_lec_major)?"270":threshold_lec_major);
        req.setAttribute("threshold_lec_superior", StringUtils.isBlank(threshold_lec_superior)?"140":threshold_lec_superior);
        req.setAttribute("threshold_lec_commonly", StringUtils.isBlank(threshold_lec_commonly)?"70":threshold_lec_commonly);

        return new ModelAndView("com/sdzk/buss/web/dangersource/updateDangerSourceOnCheck");
    }

    /**
     * 年度部门危险源查看
     * @param tBDangerSource
     * @param req
     * @return
     */
    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(TBDangerSourceEntity tBDangerSource, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBDangerSource.getId()) || StringUtils.isNotBlank(req.getParameter("dangerId"))) {
            String dangerId = StringUtils.isNotBlank(req.getParameter("dangerId")) ? req.getParameter("dangerId") : tBDangerSource.getId();
            tBDangerSource = tBDangerSourceService.getEntity(TBDangerSourceEntity.class, dangerId);
            //风险点类型
            String addressCate = tBDangerSource.getAddressCate();
            if(StringUtil.isNotEmpty(addressCate)){
                String addressCateTemp = DicUtil.getTypeNameByCode("addressCate", addressCate);
                tBDangerSource.setAddressCatetemp(addressCateTemp);
            }
            //专业类型
            String yeProfession = tBDangerSource.getYeProfession();
            if(StringUtils.isNotBlank(yeProfession)){
                String yeProfessiontemp = DicUtil.getTypeNameByCode("proCate_gradeControl", yeProfession);
                tBDangerSource.setYeProfessiontemp(yeProfessiontemp);
            }
            //事故类型
            String sgxlStr = tBDangerSource.getYeAccident();
            if(StringUtils.isNotBlank(sgxlStr)){
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    tBDangerSource.setYeAccidentTemp(sb.toString());
                }
            }
            //可能性
            String yeProbability = tBDangerSource.getYeProbability();
            if(StringUtils.isNotBlank(yeProbability)){
                String yeProbabilityTemp = DicUtil.getTypeNameByCode("probability", yeProbability);
                tBDangerSource.setYeProbabilityTemp(yeProbabilityTemp);
            }
            //损失
            String yeCost = tBDangerSource.getYeCost();
            if(StringUtils.isNotBlank(yeCost)){
                String yeCostTemp = DicUtil.getTypeNameByCode("hazard_fxss", yeCost);
                tBDangerSource.setYeCostTemp(yeCostTemp);
            }
            //风险等级
            String yeRiskGrade = tBDangerSource.getYeRiskGrade();
            if(StringUtils.isNotBlank(yeRiskGrade)){
                String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", yeRiskGrade);
                tBDangerSource.setYeRiskGradeTemp(yeRiskGradeTemp);
            }
            //风险类型
            String yeHazardCate = tBDangerSource.getYeHazardCate();
            if(StringUtils.isNotBlank(yeHazardCate)){
                String yeHazardCateTemp = DicUtil.getTypeNameByCode("hazardCate",yeHazardCate);
                tBDangerSource.setYeHazardCateTemp(yeHazardCateTemp);
            }
            //隐患等级
            String hiddenLevel = tBDangerSource.getHiddenLevel();
            if(StringUtils.isNotBlank(hiddenLevel)){
                String hiddenLevelTemp = DicUtil.getTypeNameByCode("hiddenLevel",hiddenLevel);
                tBDangerSource.setHiddenLevelTemp(hiddenLevelTemp);
            }

            //伤害类别
            /*String damageType = tBDangerSource.getDamageType();
            if(StringUtils.isNotBlank(damageType)){
                String damageTypeTemp = DicUtil.getTypeNameByCode("danger_Category",damageType);
                tBDangerSource.setDamageTypeTemp(damageTypeTemp);
            }*/
            String damageTypeStr = tBDangerSource.getDamageType();
            if(StringUtils.isNotBlank(damageTypeStr)){
                String [] damageTypeArray = damageTypeStr.split(",");
                StringBuffer sb = new StringBuffer();
                for(String str : damageTypeArray){
                    String retName = DicUtil.getTypeNameByCode("danger_Category", str);
                    if(StringUtils.isNotBlank(sb.toString())){
                        sb.append(",");
                    }
                    sb.append(retName);
                }
                tBDangerSource.setDamageTypeTemp(sb.toString());
            }

            //LEC风险可能性
            String lecRiskPossibility = "";
            if(tBDangerSource.getLecRiskPossibility() != null){
                lecRiskPossibility = tBDangerSource.getLecRiskPossibility().toString();
            }
            if(StringUtils.isNotBlank(lecRiskPossibility)){
                String lecRiskPossibilityTemp = DicUtil.getTypeNameByCode("lec_risk_probability",lecRiskPossibility);
                tBDangerSource.setLecRiskPossibilityTemp(lecRiskPossibilityTemp);
            }
            //LEC风险损失
            String lecRiskLoss = "";
            if(tBDangerSource.getLecRiskLoss() != null){
                lecRiskLoss = tBDangerSource.getLecRiskLoss().toString();
            }
            if(StringUtils.isNotBlank(lecRiskLoss)){
                String lecRiskLossTemp = DicUtil.getTypeNameByCode("lec_risk_loss",lecRiskLoss);
                tBDangerSource.setLecRiskLossTemp(lecRiskLossTemp);
            }
            //LEC人员暴露于危险环境中的频繁程度
            String lecExposure = "";
            if(tBDangerSource.getLecExposure() != null){
                lecExposure = tBDangerSource.getLecExposure().toString();
            }
            if(StringUtils.isNotBlank(lecExposure)){
                String lecExposureTemp = DicUtil.getTypeNameByCode("lec_exposure",lecExposure);
                tBDangerSource.setLecExposureTemp(lecExposureTemp);
            }
            req.setAttribute("tBDangerSourcePage", tBDangerSource);
        }
        String from = req.getParameter("from");
        req.setAttribute("from",from);
        if(!"universalDangerList".equals(from)){
            CriteriaQuery cq = new CriteriaQuery(TBDangerSourceAuditHisEntity.class);
            cq.eq("danger.id",tBDangerSource.getId());
            cq.addOrder("dealTime", SortDirection.desc);
            cq.add();
            List<TBDangerSourceAuditHisEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
            req.setAttribute("handleList",handleStepList);
        }
        return new ModelAndView("com/sdzk/buss/web/dangersource/tBDangerSourceDetail_new");
    }

    /**
     * 通用风险查看
     * @param tBGeneralDangerSource
     * @param req
     * @return
     */
    @RequestMapping(params = "goGeneralDangerDetail")
    public ModelAndView goGeneralDangerDetail(TBGeneralDangerSourceEntity tBGeneralDangerSource, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBGeneralDangerSource.getId())) {
            tBGeneralDangerSource = tBDangerSourceService.getEntity(TBGeneralDangerSourceEntity.class, tBGeneralDangerSource.getId());
            //专业类型
            String yeProfession = tBGeneralDangerSource.getYeProfession();
            if(StringUtils.isNotBlank(yeProfession)){
                String yeProfessiontemp = DicUtil.getTypeNameByCode("proCate_gradeControl", yeProfession);
                tBGeneralDangerSource.setYeProfessiontemp(yeProfessiontemp);
            }
            //事故类型
            String sgxlStr = tBGeneralDangerSource.getYeAccident();
            if(StringUtils.isNotBlank(sgxlStr)){
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    tBGeneralDangerSource.setYeAccidentTemp(sb.toString());
                }
            }
            //可能性
            String yeProbability = tBGeneralDangerSource.getYeProbability();
            if(StringUtils.isNotBlank(yeProbability)){
                String yeProbabilityTemp = DicUtil.getTypeNameByCode("probability", yeProbability);
                tBGeneralDangerSource.setYeProbabilityTemp(yeProbabilityTemp);
            }
            //损失
            String yeCost = tBGeneralDangerSource.getYeCost();
            if(StringUtils.isNotBlank(yeCost)){
                String yeCostTemp = DicUtil.getTypeNameByCode("hazard_fxss", yeCost);
                tBGeneralDangerSource.setYeCostTemp(yeCostTemp);
            }
            //风险等级
            String yeRiskGrade = tBGeneralDangerSource.getYeRiskGrade();
            if(StringUtils.isNotBlank(yeRiskGrade)){
                String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", yeRiskGrade);
                tBGeneralDangerSource.setYeRiskGradeTemp(yeRiskGradeTemp);
            }
            //风险类型
            String yeHazardCate = tBGeneralDangerSource.getYeHazardCate();
            if(StringUtils.isNotBlank(yeHazardCate)){
                String yeHazardCateTemp = DicUtil.getTypeNameByCode("hazardCate",yeHazardCate);
                tBGeneralDangerSource.setYeHazardCateTemp(yeHazardCateTemp);
            }
            //隐患等级
            String hiddenLevel = tBGeneralDangerSource.getHiddenLevel();
            if(StringUtils.isNotBlank(hiddenLevel)){
                String hiddenLevelTemp = DicUtil.getTypeNameByCode("hiddenLevel",hiddenLevel);
                tBGeneralDangerSource.setHiddenLevelTemp(hiddenLevelTemp);
            }

            //伤害类别
            /*String damageType = tBDangerSource.getDamageType();
            if(StringUtils.isNotBlank(damageType)){
                String damageTypeTemp = DicUtil.getTypeNameByCode("danger_Category",damageType);
                tBDangerSource.setDamageTypeTemp(damageTypeTemp);
            }*/
            String damageTypeStr = tBGeneralDangerSource.getDamageType();
            if(StringUtils.isNotBlank(damageTypeStr)){
                String [] damageTypeArray = damageTypeStr.split(",");
                StringBuffer sb = new StringBuffer();
                for(String str : damageTypeArray){
                    String retName = DicUtil.getTypeNameByCode("danger_Category", str);
                    if(StringUtils.isNotBlank(sb.toString())){
                        sb.append(",");
                    }
                    sb.append(retName);
                }
                tBGeneralDangerSource.setDamageTypeTemp(sb.toString());
            }

            //LEC风险可能性
            String lecRiskPossibility = "";
            if(tBGeneralDangerSource.getLecRiskPossibility() != null){
                lecRiskPossibility = tBGeneralDangerSource.getLecRiskPossibility().toString();
            }
            if(StringUtils.isNotBlank(lecRiskPossibility)){
                String lecRiskPossibilityTemp = DicUtil.getTypeNameByCode("lec_risk_probability",lecRiskPossibility);
                tBGeneralDangerSource.setLecRiskPossibilityTemp(lecRiskPossibilityTemp);
            }
            //LEC风险损失
            String lecRiskLoss = "";
            if(tBGeneralDangerSource.getLecRiskLoss() != null){
                lecRiskLoss = tBGeneralDangerSource.getLecRiskLoss().toString();
            }
            if(StringUtils.isNotBlank(lecRiskLoss)){
                String lecRiskLossTemp = DicUtil.getTypeNameByCode("lec_risk_loss",lecRiskLoss);
                tBGeneralDangerSource.setLecRiskLossTemp(lecRiskLossTemp);
            }
            //LEC人员暴露于危险环境中的频繁程度
            String lecExposure = "";
            if(tBGeneralDangerSource.getLecExposure() != null){
                lecExposure = tBGeneralDangerSource.getLecExposure().toString();
            }
            if(StringUtils.isNotBlank(lecExposure)){
                String lecExposureTemp = DicUtil.getTypeNameByCode("lec_exposure",lecExposure);
                tBGeneralDangerSource.setLecExposureTemp(lecExposureTemp);
            }
            req.setAttribute("tBDangerSourcePage", tBGeneralDangerSource);
        }
        String from = req.getParameter("from");
        req.setAttribute("from",from);
        if(!"universalDangerList".equals(from)){
            CriteriaQuery cq = new CriteriaQuery(TBDangerSourceAuditHisEntity.class);
            cq.eq("danger.id",tBGeneralDangerSource.getId());
            cq.addOrder("dealTime", SortDirection.desc);
            cq.add();
            List<TBDangerSourceAuditHisEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
            req.setAttribute("handleList",handleStepList);
        }
        return new ModelAndView("com/sdzk/buss/web/dangersource/tBGeneralDangerSourceDetail");
    }

    @RequestMapping(params = "getHandleStepList")
    @ResponseBody
    public JSONArray getHandleStepList(HttpServletRequest request) throws Exception {
        String dangerId = request.getParameter("dangerId");

        JSONArray ja = new JSONArray();
        if(StringUtils.isNotBlank(dangerId)){
            CriteriaQuery cq = new CriteriaQuery(TBDangerSourceAuditHisEntity.class);
            cq.eq("danger.id",dangerId);
            cq.addOrder("dealTime", SortDirection.asc);
            cq.add();
            List<TBDangerSourceAuditHisEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
            if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                //上报-审批，最短2条
                for (TBDangerSourceAuditHisEntity t : handleStepList) {

                    String status = t.getDealStep()+"";
                    StringBuffer sb = new StringBuffer();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    if (status.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_REPORT+"")) {
                        t.setHandleTypeTemp("上报");
                        sb.append("上报人：").append(t.getDealManName()).append("　上报时间：").append(sdf.format(t.getDealTime())).append("");
                    }
                    if (status.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_ROLLBANK+"")) {
                        t.setHandleTypeTemp("驳回上报");
                        sb.append("驳回人：").append(t.getDealManName()).append("　驳回时间：").append(sdf.format(t.getDealTime())).append("　");
                        sb.append("驳回备注：").append(t.getDealDesc());
                    }
                    if (status.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_CLOSE+"")) {
                        t.setHandleTypeTemp("审核通过");
                        sb.append("审核人：").append(t.getDealManName()).append("　审核时间：").append(sdf.format(t.getDealTime())).append("  ");
                        sb.append("审核备注：").append(t.getDealDesc());
                    }
                    if (status.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_SELFBACK+"")) {
                        t.setHandleTypeTemp("撤回");
                        sb.append("撤回人：").append(t.getDealManName()).append("　撤回时间：").append(sdf.format(t.getDealTime())).append("  ");
                        sb.append("撤回备注：").append(t.getDealDesc());
                    }
                    if (status.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_DRAWBACK+"")){
                        t.setHandleTypeTemp("去审");
                        sb.append("去审人：").append(t.getDealManName()).append("  去审时间：").append(sdf.format(t.getDealTime())).append("  ");
                        sb.append("去审备注：").append(t.getDealDesc());
                    }
                    JSONObject jo = new JSONObject();
                    jo.put("title", t.getHandleTypeTemp());
                    jo.put("content", sb.toString());
                    ja.add(jo);
                }
                    if (handleStepList != null && handleStepList.size() >=1 ) {
                        //list最后一条，写入最简的后续流程，前台以灰色显示
                        boolean flag = true;
                        String currentStatus = handleStepList.get(handleStepList.size() -1).getDealStep()+"";
                        while (flag){
                            if (currentStatus.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_REPORT+"")) {
                                //上报
                                JSONObject jo1 = new JSONObject();
                                jo1.put("title", "审核");
                                jo1.put("content", "未审核");
                                ja.add(jo1);
                                currentStatus = Constants.DANGER_SOURCE_AUDIT_HIS_STEP_CLOSE +"";
                            }
                            if (currentStatus.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_ROLLBANK+"")) {
                                //退回上报
                                JSONObject jo1 = new JSONObject();
                                jo1.put("title", "上报");
                                jo1.put("content", "重新编辑上报");
                                ja.add(jo1);
                                currentStatus = Constants.DANGER_SOURCE_AUDIT_HIS_STEP_REPORT +"";
                            }
                            if (currentStatus.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_CLOSE+"")) {
                                //审核通过
                                flag = false;
                            }
                            if (currentStatus.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_ROLLBANK+"")) {
                                //审核未通过
                                JSONObject jo1 = new JSONObject();
                                jo1.put("title", "审核");
                                jo1.put("content", "重新审核");
                                ja.add(jo1);
                                currentStatus = Constants.DANGER_SOURCE_AUDIT_HIS_STEP_REPORT+"";
                            }
                            if (currentStatus.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_SELFBACK+"")) {
                                //审核未通过
                                JSONObject jo1 = new JSONObject();
                                jo1.put("title", "撤回");
                                jo1.put("content", "重新撤回");
                                ja.add(jo1);
                                currentStatus = Constants.DANGER_SOURCE_AUDIT_HIS_STEP_REPORT+"";
                            }
                            if (currentStatus.equals(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_DRAWBACK+"")){
                                //去审
                                JSONObject jo1 = new JSONObject();
                                jo1.put("title", "审核");
                                jo1.put("content", "未审核");
                                ja.add(jo1);
                                currentStatus = Constants.DANGER_SOURCE_AUDIT_HIS_STEP_CLOSE +"";
                            }
                        }
                    }
            //    }
            }else{
                //上报
                JSONObject jo1 = new JSONObject();

                jo1.put("title", "未上报");
                jo1.put("content", "未上报");
                ja.add(jo1);

                jo1 = new JSONObject();
                jo1.put("title", "上报");
                jo1.put("content", "未上报");
                ja.add(jo1);

                jo1 = new JSONObject();
                jo1.put("title", "审核");
                jo1.put("content", "未审核");
                ja.add(jo1);


            }
        }
        return ja;
    }

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBDangerSourceController");
        String type = ResourceUtil.getParameter("type");
        String seId = ResourceUtil.getParameter("seId");
        if (Constants.TYPE_RISK_IMPORT_SPECIAL.equals(type) && StringUtil.isNotEmpty(seId)) {
            req.setAttribute("function_name", "importExcelT&seId="+seId+"&type="+type);
        }
        if (Constants.TYPE_RISK_IMPORT_DEPART_REPORT.equals(type)) {
            req.setAttribute("function_name", "importExcelT&type="+type);
        }
		return new ModelAndView("common/upload/pub_excel_upload");
	}

    @RequestMapping(params = "exportRelXls")
    public String exportRelXls(TBDeptDangerRelEntity tBDangerSource,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        TSDepart userDepart = sessionUser.getCurrentDepart();
        CriteriaQuery cq = new CriteriaQuery(TBDeptDangerRelEntity.class, dataGrid);
        cq.createAlias("danger","danger");
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        String beginDate = request.getParameter("danger.yeRecognizeTime_begin");
        String endDate = request.getParameter("danger.yeRecognizeTime_end");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            cq.eq("danger.isDelete",Constants.IS_DELETE_N);
            cq.eq("dept.id",userDepart.getId());
            if(StringUtils.isNotBlank(beginDate)){
                cq.ge("danger.yeRecognizeTime", sdf.parse(beginDate+" 00:00:00"));
            }
            if(StringUtils.isNotBlank(endDate)){
                cq.le("danger.yeRecognizeTime", sdf.parse(endDate +" 23:59:59"));
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        List<TBDeptDangerRelEntity> relList = this.systemService.getListByCriteriaQuery(cq, false);
        List<TBDangerSourceEntity> retList = new ArrayList<TBDangerSourceEntity>();
        if(relList != null && !relList.isEmpty()){
            for(TBDeptDangerRelEntity rel : relList){
                retList.add(rel.getDanger());
            }
        }
        if(retList != null && !retList.isEmpty()){
            for(TBDangerSourceEntity bean : retList){
                String auditStatus = bean.getAuditStatus();
                if(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT.equals(auditStatus)){
                    bean.setAuditStatusTemp("待上报");
                }else if(Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW.equals(auditStatus)){
                    bean.setAuditStatusTemp("审核中");
                }else if(Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK.equals(auditStatus)){
                    bean.setAuditStatusTemp("审核退回");
                }else if(Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE.equals(auditStatus)){
                    bean.setAuditStatusTemp("闭环");
                }
                //专业类型
                String yeProfession = bean.getYeProfession();
                if(StringUtils.isNotBlank(yeProfession)){
                    String yeProfessiontemp = DicUtil.getTypeNameByCode("proCate_gradeControl", yeProfession);
                    bean.setYeProfessiontemp(yeProfessiontemp);
                }
                //事故类型
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    if(StringUtils.isNotBlank(sgxlStr)){
                        String [] sgxlArray = sgxlStr.split(",");
                        StringBuffer sb = new StringBuffer();
                        for(String str : sgxlArray){
                            String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                            if(StringUtils.isNotBlank(sb.toString())){
                                sb.append(",");
                            }
                            sb.append(retName);
                        }
                        bean.setYeAccidentTemp(sb.toString());
                    }
                }
                //可能性
                /*String yeProbability = bean.getYeProbability();
                if(StringUtils.isNotBlank(yeProbability)){
                    String yeProbabilityTemp = DicUtil.getTypeNameByCode("probability", yeProbability);
                    bean.setYeProbabilityTemp(yeProbabilityTemp);
                }
                //损失
                String yeCost = bean.getYeCost();
                if(StringUtils.isNotBlank(yeCost)){
                    String yeCostTemp = DicUtil.getTypeNameByCode("hazard_fxss", yeProbability);
                    bean.setYeCostTemp(yeCostTemp);
                }*/
                //风险等级
                String yeRiskGrade = bean.getYeRiskGrade();
                if(StringUtils.isNotBlank(yeRiskGrade)){
                    String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", yeRiskGrade);
                    bean.setYeRiskGradeTemp(yeRiskGradeTemp);
                }
                //风险类型
                String yeHazardCate = bean.getYeHazardCate();
                if(StringUtils.isNotBlank(yeHazardCate)){
                    String yeHazardCateTemp = DicUtil.getTypeNameByCode("hazardCate",yeHazardCate);
                    bean.setYeHazardCateTemp(yeHazardCateTemp);
                }
                String origin = bean.getOrigin();
                if(StringUtils.isNotBlank(origin)){
                    if(Constants.DANGER_SOURCE_ORIGIN_NOMAL.equals(origin)){
                        bean.setOriginTemp("通用");
                    }else if(Constants.DANGER_SOURCE_ORIGIN_MINE.equals(origin)){
                        bean.setOriginTemp("年度");
                    }else if(Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION.equals(origin)){
                        bean.setOriginTemp("专项");
                    }
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_departDangerSource.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", retList);
        modelMap.put(NormalExcelConstants.FILE_NAME,"部门年度危险源导出列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBDangerSourceEntity tBDangerSource,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        TSDepart userDepart = sessionUser.getCurrentDepart();
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        String queryHandleStatus = request.getParameter("queryHandleStatus");

        try{
            cq.eq("isDelete",Constants.IS_DELETE_N);
            String type = ResourceUtil.getParameter("type");
            if ("departReport".equals(type)) {

                //自定义追加查询条件
                if(Constants.QUERY_STATUS_UNREPORT.equals(queryHandleStatus) || StringUtil.isEmpty(queryHandleStatus)){
                    String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT,Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK};      //Author：张赛超
                    cq.in("auditStatus", status);       //原版
                }else if(Constants.QUERY_STATUS_REPORT.equals(queryHandleStatus)){
                    String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW,Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};      //Author：张赛超
                    cq.in("auditStatus", status);    //原版
                }
                //根据部门查询
                cq.eq("reportDepart.id",userDepart.getId());

            } else if ("yearReport".equals(type)) {

                //自定义追加查询条件
                if(Constants.QUERY_STATUS_UNREPORT.equals(queryHandleStatus) || StringUtil.isEmpty(queryHandleStatus)){
                    String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};      //Author：张赛超
                    cq.in("auditStatus", status);
                    cq.eq("reportStatus",Constants.DANGER_SOURCE_REPORT_UNREPORT);
                }else if(Constants.QUERY_STATUS_REPORT.equals(queryHandleStatus)){
                    String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};      //Author：张赛超
                    cq.in("auditStatus",status);
                    cq.eq("reportStatus",Constants.DANGER_SOURCE_REPORT_REPORT);
                }

            } else if ("yearReview".equals(type)) {

                //自定义追加查询条件
                if(Constants.QUERY_STATUS_UNREPORT.equals(queryHandleStatus) || StringUtil.isEmpty(queryHandleStatus)){
                    String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW};      //Author：张赛超
                    cq.in("auditStatus", status);
                }else if(Constants.QUERY_STATUS_REPORT.equals(queryHandleStatus)){
                    String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK,Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};      //Author：张赛超
                    cq.in("auditStatus",status);
                    cq.notEq("auditStatus",Constants.DANGER_SOURCE_REPORT_REPORT);
                }else if (Constants.QUERY_STATUS_ALL.equals(queryHandleStatus)){
                    String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW,Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK,Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};
                    cq.in("auditStatus",status);
                }

            }

            String seId = ResourceUtil.getParameter("seId");
            if(StringUtil.isNotEmpty(seId)){
                List idList = systemService.findListbySql("select DANGER_SOURCE_ID from T_B_SE_DS_RELATION where SEPCIAL_EVALUATION_ID = '"+seId+"'");
                if (idList != null && idList.size() > 0) {
                    cq.in("id", idList.toArray());
                } else {
                    cq.eq("id","");
                }
            } else {
                //TODO 排除专项管理的危险源
                cq.notIn("origin", new String[]{Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION,Constants.DANGER_SOURCE_ORIGIN_NOMAL});
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        List<TBDangerSourceEntity> retList = this.tBDangerSourceService.getListByCriteriaQuery(cq, false);
        if(retList != null && !retList.isEmpty()){
            int orderNum = 0;
            for(TBDangerSourceEntity bean : retList){
                orderNum++;
                bean.setOrderNum(String.valueOf(orderNum));

                String auditStatus = bean.getAuditStatus();
                if(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT.equals(auditStatus)){
                    bean.setAuditStatusTemp("待上报");
                }else if(Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW.equals(auditStatus)){
                    bean.setAuditStatusTemp("审核中");
                }else if(Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK.equals(auditStatus)){
                    bean.setAuditStatusTemp("审核退回");
                }else if(Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE.equals(auditStatus)){
                    bean.setAuditStatusTemp("闭环");
                }
                //专业类型
                String yeProfession = bean.getYeProfession();
                if(StringUtils.isNotBlank(yeProfession)){
                    String yeProfessiontemp = DicUtil.getTypeNameByCode("proCate_gradeControl", yeProfession);
                    bean.setYeProfessiontemp(yeProfessiontemp);
                }
                //事故类型
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    if(StringUtils.isNotBlank(sgxlStr)){
                        String [] sgxlArray = sgxlStr.split(",");
                        StringBuffer sb = new StringBuffer();
                        for(String str : sgxlArray){
                            String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                            if(StringUtils.isNotBlank(sb.toString())){
                                sb.append(",");
                            }
                            sb.append(retName);
                        }
                        bean.setYeAccidentTemp(sb.toString());
                    }
                }
                //可能性
                String yeProbability = bean.getYeProbability();
                if(StringUtils.isNotBlank(yeProbability)){
                    String yeProbabilityTemp = DicUtil.getTypeNameByCode("probability", yeProbability);
                    bean.setYeProbabilityTemp(yeProbabilityTemp);
                }
                //损失
                String yeCost = bean.getYeCost();
                if(StringUtils.isNotBlank(yeCost)){
                    String yeCostTemp = DicUtil.getTypeNameByCode("hazard_fxss", yeCost);
                    bean.setYeCostTemp(yeCostTemp);
                }
                //风险等级
                String yeRiskGrade = bean.getYeRiskGrade();
                if(StringUtils.isNotBlank(yeRiskGrade)){
                    String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", yeRiskGrade);
                    bean.setYeRiskGradeTemp(yeRiskGradeTemp);
                }

                //风险类型
                String yeHazardCate = bean.getYeHazardCate();
                if(StringUtils.isNotBlank(yeHazardCate)){
                    String yeHazardCateTemp = DicUtil.getTypeNameByCode("hazardCate",yeHazardCate);
                    bean.setYeHazardCateTemp(yeHazardCateTemp);
                }
                //隐患等级
                String hiddenLevel = bean.getHiddenLevel();
                if(StringUtils.isNotBlank(hiddenLevel)){
                    String hiddenLevelTemp = DicUtil.getTypeNameByCode("hiddenLevel",hiddenLevel);
                    bean.setHiddenLevelTemp(hiddenLevelTemp);
                }

                //伤害类别
                /*String damageType = bean.getDamageType();
                if(StringUtils.isNotBlank(damageType)){
                    String damageTypeTemp = DicUtil.getTypeNameByCode("danger_Category",damageType);
                    bean.setDamageTypeTemp(damageTypeTemp);
                }*/
                String damageTypeStr = bean.getDamageType();
                if(StringUtils.isNotBlank(damageTypeStr)){
                    String [] damageTypeArray = damageTypeStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : damageTypeArray){
                        String retName = DicUtil.getTypeNameByCode("danger_Category", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setDamageTypeTemp(sb.toString());
                }
                String origin = bean.getOrigin();
                if(StringUtils.isNotBlank(origin)){
                    if(Constants.DANGER_SOURCE_ORIGIN_MINE.equals(origin)){
                        bean.setOriginTemp("本矿井");
                    }else if(Constants.DANGER_SOURCE_ORIGIN_NOMAL.equals(origin)){
                        bean.setOriginTemp("通用");
                    } else if(Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION.equals(origin)){
                        bean.setOriginTemp("专项风险评估");
                    }
                }

                //LEC风险可能性
                String lecRiskPossibility = String.valueOf(bean.getLecRiskPossibility());
                if(StringUtils.isNotBlank(lecRiskPossibility)){
                    String lecRiskPossibilityTemp = DicUtil.getTypeNameByCode("lec_risk_probability",lecRiskPossibility);
                    bean.setLecRiskPossibilityTemp(lecRiskPossibilityTemp);
                }

                //LEC风险损失
                String lecRiskLoss = String.valueOf(bean.getLecRiskLoss());
                if(StringUtils.isNotBlank(lecRiskLoss)){
                    String lecRiskLossTemp = DicUtil.getTypeNameByCode("lec_risk_loss",lecRiskLoss);
                    bean.setLecRiskLossTemp(lecRiskLossTemp);
                }
                //LEC人员暴露于危险环境中的频繁程度
                String lecExposure = String.valueOf(bean.getLecExposure());
                if(StringUtils.isNotBlank(lecExposure)){
                    String lecExposureTemp = DicUtil.getTypeNameByCode("lec_exposure",lecExposure);
                    bean.setLecExposureTemp(lecExposureTemp);
                }
                if (StringUtils.isBlank(bean.getFineMoney())) {
                    bean.setFineMoney(null);
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();

//        templateExportParams.setSheetName("这是测试");
        String type = ResourceUtil.getParameter("type");
        if ("departReport".equals(type)) {
            templateExportParams.setSheetName("部门年度风险上报列表");
        } else if ("yearReport".equals(type)) {
            templateExportParams.setSheetName("年度风险上报导出列表");
        } else if ("yearReview".equals(type)) {
            templateExportParams.setSheetName("年度风险审核导出列表");
        }else{
            templateExportParams.setSheetName("部门年度风险导出列表");
        }

        templateExportParams.setSheetNum(0);

        String lec = request.getParameter("lec");
        if(lec.equals("no")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_departDangerSource_new.xls");
        }else{
            templateExportParams.setTemplateUrl("export/template/exportTemp_departDangerSourceLec_new.xls");
        }

        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", retList);

//        String type = ResourceUtil.getParameter("type");
        if ("departReport".equals(type)) {
            modelMap.put(NormalExcelConstants.FILE_NAME,"部门年度风险上报列表");
        } else if ("yearReport".equals(type)) {
            modelMap.put(NormalExcelConstants.FILE_NAME,"年度风险上报导出列表");
        } else if ("yearReview".equals(type)) {
            modelMap.put(NormalExcelConstants.FILE_NAME,"年度风险审核导出列表");
        } else if ("special".equals(type)) {
            modelMap.put(NormalExcelConstants.FILE_NAME,"专项风险辨识结果导出列表");
        }else{
            modelMap.put(NormalExcelConstants.FILE_NAME,"部门年度风险导出列表");
        }

        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }


    /**
     * 导出excel 专业通用危险源数据导出
     *
     * Author:张赛超
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportCommonRiskXls")
    public String exportCommonRiskXls(TBDangerSourceEntity tBDangerSource,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        cq.eq("isDelete",Constants.IS_DELETE_N);
        try{
            cq.eq("origin",Constants.DANGER_SOURCE_ORIGIN_NOMAL);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        List<TBDangerSourceEntity> tbDangerSourceEntityList = tBDangerSourceService.getListByCriteriaQuery(cq, false);

        if(tbDangerSourceEntityList != null && tbDangerSourceEntityList.size() > 0){
            for(TBDangerSourceEntity tbDangerSourceEntity : tbDangerSourceEntityList){
                //字典
                String sgxlStr = tbDangerSourceEntity.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    tbDangerSourceEntity.setYeAccidentTemp(sb.toString());
                }

                //专业类型
                String yeProfession = tbDangerSourceEntity.getYeProfession();
                if(StringUtils.isNotBlank(yeProfession)){
                    String yeProfessiontemp = DicUtil.getTypeNameByCode("proCate_gradeControl", yeProfession);
                    tbDangerSourceEntity.setYeProfessiontemp(yeProfessiontemp);
                }

                //可能性
                String yeProbability = tbDangerSourceEntity.getYeProbability();
                if(StringUtils.isNotBlank(yeProbability)){
                    String yeProbabilityTemp = DicUtil.getTypeNameByCode("probability", yeProbability);
                    tbDangerSourceEntity.setYeProbabilityTemp(yeProbabilityTemp);
                }
                //损失
                String yeCost = tbDangerSourceEntity.getYeCost();
                if(StringUtils.isNotBlank(yeCost)){
                    String yeCostTemp = DicUtil.getTypeNameByCode("hazard_fxss", yeCost);
                    tbDangerSourceEntity.setYeCostTemp(yeCostTemp);
                }
                //风险等级
                String yeRiskGrade = tbDangerSourceEntity.getYeRiskGrade();
                if(StringUtils.isNotBlank(yeRiskGrade)){
                    String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", yeRiskGrade);
                    tbDangerSourceEntity.setYeRiskGradeTemp(yeRiskGradeTemp);
                }
                //风险类型
                String yeHazardCate = tbDangerSourceEntity.getYeHazardCate();
                if(StringUtils.isNotBlank(yeHazardCate)){
                    String yeHazardCateTemp = DicUtil.getTypeNameByCode("hazardCate",yeHazardCate);
                    tbDangerSourceEntity.setYeHazardCateTemp(yeHazardCateTemp);
                }
                String origin = tbDangerSourceEntity.getOrigin();
                if(StringUtils.isNotBlank(origin)){
                    if(Constants.DANGER_SOURCE_ORIGIN_MINE.equals(origin)){
                        tbDangerSourceEntity.setOriginTemp("本矿井");
                    }else if(Constants.DANGER_SOURCE_ORIGIN_NOMAL.equals(origin)){
                        tbDangerSourceEntity.setOriginTemp("通用");
                    } else if(Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION.equals(origin)){
                        tbDangerSourceEntity.setOriginTemp("专项风险评估");
                    }
                }
                //LEC风险可能性
                String lecRiskPossibility = String.valueOf(tbDangerSourceEntity.getLecRiskPossibility());
                if(StringUtils.isNotBlank(lecRiskPossibility)){
                    String lecRiskPossibilityTemp = DicUtil.getTypeNameByCode("lec_risk_probability",lecRiskPossibility);
                    tbDangerSourceEntity.setLecRiskPossibilityTemp(lecRiskPossibilityTemp);
                }

                //LEC风险损失
                String lecRiskLoss = String.valueOf(tbDangerSourceEntity.getLecRiskLoss());
                if(StringUtils.isNotBlank(lecRiskLoss)){
                    String lecRiskLossTemp = DicUtil.getTypeNameByCode("lec_risk_loss",lecRiskLoss);
                    tbDangerSourceEntity.setLecRiskLossTemp(lecRiskLossTemp);
                }
                //LEC人员暴露于危险环境中的频繁程度
                String lecExposure = String.valueOf(tbDangerSourceEntity.getLecExposure());
                if(StringUtils.isNotBlank(lecExposure)){
                    String lecExposureTemp = DicUtil.getTypeNameByCode("lec_exposure",lecExposure);
                    tbDangerSourceEntity.setLecExposureTemp(lecExposureTemp);
                }

            }
        }

        TemplateExportParams templateExportParams = new TemplateExportParams();

        templateExportParams.setSheetName("专业通用危险源库导出列表");

        templateExportParams.setSheetNum(0);
        String lec = request.getParameter("lec");
        if(lec.equals("no")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_commonDangerSource.xls");
        }else{
            templateExportParams.setTemplateUrl("export/template/exportTemp_commonDangerSourceLec.xls");
        }
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", tbDangerSourceEntityList);

        modelMap.put(NormalExcelConstants.FILE_NAME,"专业通用危险源库导出列表");
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
	public String exportXlsByT(TBDangerSourceEntity tBDangerSource,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"t_b_danger_source");
    	modelMap.put(NormalExcelConstants.CLASS,TBDangerSourceEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("t_b_danger_source列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
///**
// * 导出 地点关联风险excel
// */
//    @RequestMapping(params="riskexportXls")
//    public String riskexportXls (TBDangerSourceEntity tbDangerSourceEntity,HttpServletRequest request,HttpServletResponse response
//            , DataGrid dataGrid,ModelMap modelMap){
//        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
//        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tbDangerSourceEntity, request.getParameterMap());
//        List<TBDangerSourceEntity> tbDangerSources = this.tBDangerSourceService.getListByCriteriaQuery(cq,false);
//        modelMap.put(TemplateExcelConstants.FILE_NAME,"关联的风险");
//        modelMap.put(NormalExcelConstants.CLASS,TBDangerSourceEntity.class);
//        modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("关联的风险", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
//                "导出信息"));
//        modelMap.put(NormalExcelConstants.DATA_LIST,tbDangerSources);
//
//        return NormalExcelConstants.JEECG_EXCEL_VIEW;
//    }

    /**
     * 专项辨识导入模板下载
     * @param tBDangerSource
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     * @return
     */
    @RequestMapping(params = "exportXlsByTForSpecial")
    public String exportXlsByTForSpecial(TBDangerSourceEntity tBDangerSource,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME,"专项风险辨识结果导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        String lec = request.getParameter("lec");
        if(lec.equals("no")){
            templateExportParams.setTemplateUrl("export/template/importTemp_departDangerSource_new.xls");
        }else{
            templateExportParams.setTemplateUrl("export/template/importTemp_departDangerSourceLec_new.xls");
        }
        //templateExportParams.setTemplateUrl("export/template/importTemp_departDangerSource.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String, Object> param =new HashMap<String, Object>();
        param.put("title", "专项风险辨识结果列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,param);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    /**
     * 部门上报风险导入模板下载
     * @param tBDangerSource
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     * @return
     */
    @RequestMapping(params = "exportXlsByTForDepartReport")
    public String exportXlsByTForDepartReport(TBDangerSourceEntity tBDangerSource,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        String lec = request.getParameter("lec");

        modelMap.put(TemplateExcelConstants.FILE_NAME,"部门年度风险导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        Map<String, Object> map =new HashMap<String, Object>();

        if(lec.equals("no")){
            templateExportParams.setTemplateUrl("export/template/importTemp_departDangerSource_new.xls");
        }else{
            templateExportParams.setTemplateUrl("export/template/importTemp_departDangerSourceLec_new.xls");
        }

        List<TBDangerSourceExportDicVO> dicVOList = new ArrayList<TBDangerSourceExportDicVO>();

        //查询    风险点类型   0
        String addrCateSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='addressCate')";
        List<String> addressCateList = systemService.findListbySql(addrCateSql);

        //查询    专业  1
        String professionSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='proCate_gradeControl')";
        List<String> professionList = systemService.findListbySql(professionSql);

        //查询    危险源名称   2
        String dangerNameSql = "select hazard_name from t_b_hazard_manage where is_delete != '1'";
        List<String> dangerNameList = systemService.findListbySql(dangerNameSql);

        //查询    伤害类别    3
        String injuryCategorySql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='danger_Category')";
        List<String> injuryCategoryList = systemService.findListbySql(injuryCategorySql);

        //查询    事故类型    4
        String accidentTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='accidentCate')";
        List<String> accidentTypeList = systemService.findListbySql(accidentTypeSql);

        //查询    作业活动    5
        String activitySql = "select activity_name from t_b_activity_manage where is_delete != '1'";
        List<String> activityList = systemService.findListbySql(activitySql);

        //查询    可能性     6
        String possibilitySql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='probability')";
        List<String> possibilityList = systemService.findListbySql(possibilitySql);

        //查询    损失      7
        String lossSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='hazard_fxss')";
        List<String> lossList = systemService.findListbySql(lossSql);

        //查询    风险类型        8
        String riskTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='hazardCate')";
        List<String> riskTypeList = systemService.findListbySql(riskTypeSql);

        //查询    责任岗位        9
        String responsibilityPostSql = "select post_name from t_b_post_manage where is_delete='0'";
        List<String> responsibilityPostList = systemService.findListbySql(responsibilityPostSql);

        //查询    隐患等级        10
        String hiddenDangerGradeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='hiddenLevel')";
        List<String> hiddenDangerGradeList = systemService.findListbySql(hiddenDangerGradeSql);

        /**下面的三个内容是查询lec的风险可能性，风险损失和暴露在风险中的概率*/
        //查询    lec风险可能性        11
        String lecProbabilitySql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='lec_risk_probability')";
        List<String> lecProbabilityList = systemService.findListbySql(lecProbabilitySql);

        //查询    lec风险损失         12
        String lecLossSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='lec_risk_loss')";
        List<String> lecLossList = systemService.findListbySql(lecLossSql);

        //查询    lec暴露在危险环境中的概率
        String lecExposureSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='lec_exposure')";
        List<String> lecExposureList = systemService.findListbySql(lecExposureSql);

        //得到这几串数列的最长的一列，excel导出的行数即为最长一列的长度
        int[] listLength = {addressCateList.size(), professionList.size(), dangerNameList.size(), injuryCategoryList.size(), accidentTypeList.size(), activityList.size(), possibilityList.size(), lossList.size(), riskTypeList.size(), responsibilityPostList.size(), hiddenDangerGradeList.size(),
                lecProbabilityList.size(), lecLossList.size(), lecExposureList.size()};         /*后面这三个查询的是lec的风险可能性，风险损失和暴露在风险中的概率*/
        int maxLength = listLength[0];
        for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
            if (listLength[i] > maxLength) {  //循环判断数组元素
                maxLength = listLength[i]; }  //赋值给num，然后再次循环
        }

        for (int j=0; j<maxLength; j++){
            TBDangerSourceExportDicVO vo = new TBDangerSourceExportDicVO();
            if (j<addressCateList.size()){
                vo.setAddressCate(addressCateList.get(j));
            }
            if (j<professionList.size()){
                vo.setProfession(professionList.get(j));
            }
            if (j<dangerNameList.size()){
                vo.setDangerName(dangerNameList.get(j));
            }
            if (j<injuryCategoryList.size()){
                vo.setInjuryCategory(injuryCategoryList.get(j));
            }
            if (j<accidentTypeList.size()){
                vo.setAccidentType(accidentTypeList.get(j));
            }
            if (j<activityList.size()){
                vo.setActivity(activityList.get(j));
            }
            if (j<possibilityList.size()){
                vo.setPossibility(possibilityList.get(j));
            }
            if (j<lossList.size()){
                vo.setLoss(lossList.get(j));
            }
            if (j<riskTypeList.size()){
                vo.setRiskType(riskTypeList.get(j));
            }
            if (j<responsibilityPostList.size()){
                vo.setResponsibilityPost(responsibilityPostList.get(j));
            }
            if (j<hiddenDangerGradeList.size()){
                vo.setHiddenDangerGrade(hiddenDangerGradeList.get(j));
            }

            /**下面三个是lec的风险可能性，风险损失和暴露在风险中的概率*/
            if (j<lecProbabilityList.size()){
                vo.setLecProbability(lecProbabilityList.get(j));
            }
            if (j<lecLossList.size()){
                vo.setLecLoss(lecLossList.get(j));
            }
            if (j<lecExposureList.size()){
                vo.setLecExposure(lecExposureList.get(j));
            }

            dicVOList.add(vo);
        }

        //将字典赋值到map中，写到sheet2中
        map.put("dicVoList", dicVOList);

        templateExportParams.setSheetNum(1);
        templateExportParams.setScanAllsheet(true);
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        map.put("title", "部门年度风险列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
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
				List<TBDangerSourceEntity> listTBDangerSourceEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBDangerSourceEntity.class,params);
				for (TBDangerSourceEntity tBDangerSource : listTBDangerSourceEntitys) {
					tBDangerSourceService.save(tBDangerSource);
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
     * 根据模板导入部门风险或专项风险
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcelT", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcelT(HttpServletRequest request, HttpServletResponse response) {

        AjaxJson j = new AjaxJson();
        //判断其使用的风险值计算方法是LEC法还是矩阵法. yes 代表LEC法， no 代表矩阵法
        String lec = ResourceUtil.getInitParam("les");
        String seId = ResourceUtil.getParameter("seId");
        String type = ResourceUtil.getParameter("type");
        if (!(Constants.TYPE_RISK_IMPORT_SPECIAL.equals(type) && StringUtil.isNotEmpty(seId)) && !Constants.TYPE_RISK_IMPORT_DEPART_REPORT.equals(type)) {
            j.setSuccess(false);
            j.setMsg("导入数据校验失败, 请求参数不正确");
            return j;
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setNeedSave(false);
            params.setNeedVerfiy(true);
            params.setVerifyHanlder(new DangerSourceExcelVerifyHandler());
            try {
                HttpSession session = request.getSession();
                initSession(session,"post");
                initSession(session,"activity");
                initSession(session,"hazard");

                ExcelImportResult<TBDangerSourceEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),TBDangerSourceEntity.class,params);
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
                    tBDangerSourceService.importDataSava(result, seId, type);
                    j.setMsg("文件导入成功！");
                    systemService.addLog(j.getMsg(),Globals.Log_Leavel_INFO,Globals.Log_Type_UPLOAD);
                }

            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                systemService.addLog(j.getMsg()+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_UPLOAD);
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

    private void initSession(HttpSession session,String initType){
        List<Map<String,Object>> tempList = systemService.findForJdbc("select id,"+initType+"_name from t_b_"+initType+"_manage where is_delete!='1'");
        Map<String,String> resultMap = new HashMap<>();
        if(!tempList.isEmpty() && tempList.size()>0){
            for(Map<String,Object> map:tempList){
                String objId = (String)map.get("id");
                String objName = (String)map.get(initType+"_name");
                if(StringUtil.isNotEmpty(objId) && StringUtil.isNotEmpty(objName)){
                    resultMap.put(objName,objId);
                }
            }
        }
        session.setAttribute(initType + "Map", resultMap);
    }
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TBDangerSourceEntity> list() {
		List<TBDangerSourceEntity> listTBDangerSources=tBDangerSourceService.getList(TBDangerSourceEntity.class);
		return listTBDangerSources;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TBDangerSourceEntity task = tBDangerSourceService.get(TBDangerSourceEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TBDangerSourceEntity tBDangerSource, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBDangerSourceEntity>> failures = validator.validate(tBDangerSource);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBDangerSourceService.save(tBDangerSource);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tBDangerSource.getId();
		URI uri = uriBuilder.path("/rest/tBDangerSourceController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TBDangerSourceEntity tBDangerSource) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBDangerSourceEntity>> failures = validator.validate(tBDangerSource);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBDangerSourceService.saveOrUpdate(tBDangerSource);
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
		tBDangerSourceService.deleteEntityById(TBDangerSourceEntity.class, id);
	}

    /**
     * 上报煤监局
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "mineReport")
    @ResponseBody
    public AjaxJson mineReport(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "上报成功";

        //上报煤监局
        Map<String,String> retMap = tBDangerSourceService.reportYearRisk(ids,false);
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
     * 上报集团
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "reportYearRiskToGroup")
    @ResponseBody
    public AjaxJson reportYearRiskToGroup(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        //TODO 获取所有已闭合的年度风险辨识
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class);
        try{
            String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};
            cq.in("auditStatus", status);
            cq.eq("origin", Constants.DANGER_SOURCE_ORIGIN_MINE);
            if(StringUtil.isNotEmpty(ids)){
                String[] idArr = ids.split(",");
                cq.in("id", idArr);
            }
        }catch (Exception e) {
            j.setMsg("上报失败");
            return j;
        }
        cq.add();
        List<TBDangerSourceEntity> list = tBDangerSourceService.getListByCriteriaQuery(cq, false);

        //上报到集团
        return tBDangerSourceService.reportYearRiskToGroup(list);
    }

    /**
     * 上报煤监局-撤回
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "mineCallback")
    @ResponseBody
    public AjaxJson mineCallback(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                //调用煤监局接口删除已上报煤监局数据
                Map<String,String> retMap = tBDangerSourceService.callbackYearRisk(ids);
                if(null==retMap || !Constants.LOCAL_RESULT_CODE_SUCCESS.equals(retMap.get("code"))){
                    message = "撤回失败";
                    if(null!=retMap && retMap.containsKey("message")){
                        message = retMap.get("message");
                    }
                    j.setMsg(message);
                    return j;
                }
            }
        } catch (Exception e) {
            message = "撤回失败";
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 风险审核-已上报撤回
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "reviewCallback")
    @ResponseBody
    public AjaxJson reviewCallback(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                TSUser sessionUser = ResourceUtil.getSessionUserName();
                //统计撤回成功条数和未撤回（已上报煤监局）条数返回
                int success = 0 ;
                int failure = 0;
                for(String id : ids.split(",")){
                    TBDangerSourceEntity t = tBDangerSourceService.get(TBDangerSourceEntity.class, id);
                    if (!Constants.DANGER_SOURCE_REPORT_REPORT.equals(t.getReportStatus())) {
                        t.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW);
                        //删除风险和风险点的关联关系
                        String sql = "delete from t_b_danger_address_rel where danger_id ='"+id+"'";
                        systemService.executeSql(sql);

                        tBDangerSourceService.saveOrUpdate(t);
                        success++;
                        //处理历史表添加一条记录
                        TBDangerSourceAuditHisEntity hisEntity = new TBDangerSourceAuditHisEntity();
                        hisEntity.setDanger(t);
                        hisEntity.setDealTime(new Date());
                        hisEntity.setDealStep(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_DRAWBACK);
                        hisEntity.setDealDesc("去审");
                        hisEntity.setDealManName(sessionUser.getRealName());
                        systemService.save(hisEntity);
                    } else {
                        //TODO 已上报煤监局的的记录
                        logger.info(t.getId()+"已上报煤监局");
                        failure++;
                    }
                    message = "共撤回成功"+success+"条风险，"+failure+"条风险因已上报煤监局未撤回";
                }
                //删除风险的隐患描述分词缓存
                this.systemService.initAllDSParts();
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
     * 全部去审
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "undoReviewAll")
    @ResponseBody
    public AjaxJson undoReviewAll(String ids, HttpServletRequest request) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "去审成功";
        try {

            String sql = "";
            String sqlUpdate = "update t_b_danger_source ";
            String sqlWhere = " where is_delete='0' and audit_status in ('" + Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE + "')";
            sqlWhere = sqlWhere + " and origin='" + Constants.DANGER_SOURCE_ORIGIN_MINE + "'";
            sqlWhere = sqlWhere + " and report_status='" + Constants.DANGER_SOURCE_REPORT_UNREPORT + "'";

            if(StringUtil.isNotEmpty(ids)) {
                sqlWhere = sqlWhere + " and id in ('" + ids.replaceAll(",","','") + "')";
            }

            //删除风险和风险点的关联关系
            sql = "delete from t_b_danger_address_rel where danger_id in ( select id from t_b_danger_source " + sqlWhere + ")";
            this.systemService.executeSql(sql);

            //处理历史表添加一条记录
            sql = "insert into t_b_danger_source_audit_his select REPLACE(UUID(),'-','') as id, id as danger_id, NOW() as deal_time,'"+Constants.DANGER_SOURCE_AUDIT_HIS_STEP_DRAWBACK
                    + "' as deal_step, '去审' as deal_desc, '"+sessionUser.getRealName() + "' as deal_man_name from t_b_danger_source" + sqlWhere;
            this.systemService.executeSql(sql);

            //去审
            sql = sqlUpdate + "set audit_status='" + Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW + "' " + sqlWhere;
            Integer count = this.systemService.executeSql(sql);
            if(null!=count && count.intValue()>=0){
                message = "去审成功"+count.toString()+"条风险";
            }

            //刷新风险的隐患描述分词缓存
            this.systemService.initAllDSParts();

        } catch (Exception e) {
            e.printStackTrace();
            message = "去审失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 计算风险关联的风险点数量
     */
    @RequestMapping(params = "calculateAddressNum")
    @ResponseBody
    public AjaxJson calculateAddressNum(HttpServletRequest request){
        String sum = "0";
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        if(StringUtil.isNotEmpty(ids)){
            String sql = "select count(distinct address_id) from t_b_danger_address_rel where FIND_IN_SET(danger_id,'"+ids+"')";
            sum = systemService.findListbySql(sql).get(0).toString();
        }
        j.setMsg(sum);
        return  j;
    }

    /**
     * 待上报记录-撤回待审核的记录
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "toReportCallback")
    @ResponseBody
    public AjaxJson toReportCallback(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                TSUser sessionUser = ResourceUtil.getSessionUserName();
                for(String id : ids.split(",")){
                    TBDangerSourceEntity t = tBDangerSourceService.get(TBDangerSourceEntity.class, id);
                    if (Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW.equals(t.getAuditStatus())) {
                        t.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT);
                        tBDangerSourceService.saveOrUpdate(t);
                        //处理历史表添加一条记录
                        TBDangerSourceAuditHisEntity hisEntity = new TBDangerSourceAuditHisEntity();
                        hisEntity.setDanger(t);
                        hisEntity.setDealTime(new Date());
                        hisEntity.setDealStep(Constants.DANGER_SOURCE_AUDIT_HIS_STEP_SELFBACK);
                        hisEntity.setDealDesc("撤回");
                        hisEntity.setDealManName(sessionUser.getRealName());
                        systemService.save(hisEntity);
                    } else {
                        //TODO 已上报煤监局的的记录
                        logger.info(t.getId()+"已上报煤监局");
                    }
                    tBDangerSourceService.saveOrUpdate(t);
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
     * 撤回全部待审核的记录
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "undoReportAll")
    @ResponseBody
    public AjaxJson undoReportAll(String ids, HttpServletRequest request) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        TSDepart userDepart = sessionUser.getCurrentDepart();
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        try {

            String sql = "";
            String sqlUpdate = "update t_b_danger_source ";
            String sqlWhere = " where is_delete='0' and audit_status in ('" + Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW + "')";
            sqlWhere = sqlWhere + " and report_depart_id='" + userDepart.getId() + "'";
            sqlWhere = sqlWhere + " and origin='" + Constants.DANGER_SOURCE_ORIGIN_MINE + "'";

            //处理历史表添加一条记录
            sql = "insert into t_b_danger_source_audit_his select REPLACE(UUID(),'-','') as id, id as danger_id, NOW() as deal_time,'"+Constants.DANGER_SOURCE_AUDIT_HIS_STEP_SELFBACK
                    + "' as deal_step, '撤回' as deal_desc, '"+sessionUser.getRealName() + "' as deal_man_name from t_b_danger_source" + sqlWhere;
            this.systemService.executeSql(sql);

            //撤回
            sql = sqlUpdate + " set audit_status='" + Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT + "' " + sqlWhere;
            Integer count = this.systemService.executeSql(sql);
            if(null!=count && count.intValue()>=0){
                message = "撤回成功"+count.toString()+"条风险";
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "撤回失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /************************************************专项风险管控危险源开始*********************************************************************/
    /**
     * 专项风险管控危险源列表
     * @param request
     * @return
     */
    @RequestMapping(params = "specialEvaluationDangerList")
    public ModelAndView specialEvaluationDangerList(HttpServletRequest request) {
        request.setAttribute("seId", ResourceUtil.getParameter("seId"));
        request.setAttribute("type", ResourceUtil.getParameter("type"));
        return new ModelAndView("com/sdzk/buss/web/dangersource/specialEvaluationDangerList");
    }

    public boolean valDangerSource(TBDangerSourceEntity dangerSourceEntity, String[] dangerNames){
        boolean val = true;
        if(dangerNames!= null){
            for(String nameTemp:dangerNames){
                if(org.apache.commons.lang.StringUtils.isNotBlank(nameTemp)){
                    String dangerName = dangerSourceEntity.getYeMhazardDesc();
                    if(dangerName.indexOf(nameTemp) < 0){
                        val = false;
                    }
                }
            }
        }
        return val;
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "seDangerSourceDatagrid")
    public void seDangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            //自定义追加查询条件
            String seId = ResourceUtil.getParameter("seId");
            String sql = "select danger_source_id from t_b_se_ds_relation where sepcial_evaluation_id = '"+seId+"'";
            List<Map<String, Object>> result = systemService.findForJdbc(sql);

            if (result != null && result.size() >0) {
                String[] param = new String[result.size()];
                for (int i = 0 ; i < param.length; i++) {
                    param[i] = (String) result.get(i).get("danger_source_id");
                }
                cq.in("id", param);
            } else {
                cq.eq("id", "null");
            }
            cq.eq("isDelete",Constants.IS_DELETE_N);
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
//        DataGridReturn dataGridReturn = this.tBDangerSourceService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }


            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 添加专项辨识相关危险源
     * @param tBDangerSource
     * @param req
     * @return
     */
    @RequestMapping(params = "goAddSeDangerSource")
    public ModelAndView goAddSeDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBDangerSource.getId())) {
            tBDangerSource = tBDangerSourceService.getEntity(TBDangerSourceEntity.class, tBDangerSource.getId());
            req.setAttribute("tBDangerSourcePage", tBDangerSource);
        }

        req.setAttribute("seId", ResourceUtil.getParameter("seId"));
        TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
        List<TSType> tsTypeList = tsTypegroup.getTSTypes();
        req.setAttribute("tsTypeList",tsTypeList);

        List<TSType> damageTypeList = systemService.getTypeGroup("danger_Category","危险类别").getTSTypes();
        req.setAttribute("damageTypeList",damageTypeList);

        String threshold_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "重大风险阀值");
        String threshold_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "较大风险阀值");
        String threshold_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "一般风险阀值");

        String threshold_lec_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "重大风险阀值");
        String threshold_lec_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "较大风险阀值");
        String threshold_lec_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "一般风险阀值");

        req.setAttribute("threshold_major", StringUtils.isBlank(threshold_major)?"25":threshold_major);
        req.setAttribute("threshold_superior", StringUtils.isBlank(threshold_superior)?"16":threshold_superior);
        req.setAttribute("threshold_commonly", StringUtils.isBlank(threshold_commonly)?"8":threshold_commonly);
        req.setAttribute("threshold_lec_major", StringUtils.isBlank(threshold_lec_major)?"270":threshold_lec_major);
        req.setAttribute("threshold_lec_superior", StringUtils.isBlank(threshold_lec_superior)?"140":threshold_lec_superior);
        req.setAttribute("threshold_lec_commonly", StringUtils.isBlank(threshold_lec_commonly)?"70":threshold_lec_commonly);

        return new ModelAndView("com/sdzk/buss/web/dangersource/addSeDangerSource_new");
    }

    /**
     * 更新专项辨识相关危险源
     * @param tBDangerSource
     * @param req
     * @return
     */
    @RequestMapping(params = "goUpdateSeDangerSource")
    public ModelAndView goUpdateSeDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBDangerSource.getId())) {
            tBDangerSource = tBDangerSourceService.getEntity(TBDangerSourceEntity.class, tBDangerSource.getId());
            req.setAttribute("tBDangerSourcePage", tBDangerSource);
        }

        req.setAttribute("seId", ResourceUtil.getParameter("seId"));
        TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
        List<TSType> tsTypeList = tsTypegroup.getTSTypes();
        req.setAttribute("tsTypeList",tsTypeList);

        List<TSType> damageTypeList = systemService.getTypeGroup("danger_Category","危险类别").getTSTypes();
        req.setAttribute("damageTypeList",damageTypeList);

        String threshold_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "重大风险阀值");
        String threshold_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "较大风险阀值");
        String threshold_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "一般风险阀值");

        String threshold_lec_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "重大风险阀值");
        String threshold_lec_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "较大风险阀值");
        String threshold_lec_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "一般风险阀值");

        req.setAttribute("threshold_major", StringUtils.isBlank(threshold_major)?"25":threshold_major);
        req.setAttribute("threshold_superior", StringUtils.isBlank(threshold_superior)?"16":threshold_superior);
        req.setAttribute("threshold_commonly", StringUtils.isBlank(threshold_commonly)?"8":threshold_commonly);
        req.setAttribute("threshold_lec_major", StringUtils.isBlank(threshold_lec_major)?"270":threshold_lec_major);
        req.setAttribute("threshold_lec_superior", StringUtils.isBlank(threshold_lec_superior)?"140":threshold_lec_superior);
        req.setAttribute("threshold_lec_commonly", StringUtils.isBlank(threshold_lec_commonly)?"70":threshold_lec_commonly);

        return new ModelAndView("com/sdzk/buss/web/dangersource/updateSeDangerSource_new");
    }

    /**
     * 添加专项辨识相关危险源
     * @param tBDangerSource
     * @param request
     * @return
     */
    @RequestMapping(params = "doAddSeDangerSource")
    @ResponseBody
    public AjaxJson doAddSeDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "添加成功";
        String seId = ResourceUtil.getParameter("seId");
        try{
            tBDangerSource.setId(UUIDGenerator.generate());
            tBDangerSource.setOrigin(Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION);
            TSUser sessionUser = ResourceUtil.getSessionUserName();
            tBDangerSource.setReportDepart(sessionUser.getCurrentDepart());
            tBDangerSource.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT);
            tBDangerSource.setReportStatus(Constants.DANGER_SOURCE_REPORT_UNREPORT);
            tBDangerSource.setDepartReportTime(new Date());
            tBDangerSource.setDepartReportMan(sessionUser);
            tBDangerSource.setIsDelete(Constants.IS_DELETE_N);

            String riskGrade = DicUtil.getTypeCodeByName("riskLevel",tBDangerSource.getYeRiskGrade());
            tBDangerSource.setYeRiskGrade(riskGrade);
            if("重大风险".equals(tBDangerSource.getYeRiskGrade()) || "1".equals(tBDangerSource.getYeRiskGrade())){
                tBDangerSource.setIsMajor("1");
            }else{
                tBDangerSource.setIsMajor("0");
            }

            tBDangerSourceService.save(tBDangerSource);

            //保存专项风险与危险源关系
            TBSeDsRelationEntity tbSeDsRelationEntity = new TBSeDsRelationEntity();
            tbSeDsRelationEntity.setDangerSourceId(tBDangerSource.getId());
            tbSeDsRelationEntity.setSepcialEvaluationId(seId);
            systemService.save(tbSeDsRelationEntity);

            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }



    /**
     * 修改专项辨识相关危险源
     * @param tBDangerSource
     * @param request
     * @return
     */
    @RequestMapping(params = "doUpdateSeDangerSource")
    @ResponseBody
    public AjaxJson doUpdateSeDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "修改成功";
        TBDangerSourceEntity t = tBDangerSourceService.get(TBDangerSourceEntity.class, tBDangerSource.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBDangerSource, t);
            TSUser sessionUser = ResourceUtil.getSessionUserName();
            t.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT);
            t.setDepartReportTime(new Date());
            t.setDepartReportMan(sessionUser);

            String riskGrade = DicUtil.getTypeCodeByName("riskLevel",t.getYeRiskGrade());
            t.setYeRiskGrade(riskGrade);
            if("重大风险".equals(tBDangerSource.getYeRiskGrade()) || "1".equals(tBDangerSource.getYeRiskGrade())){
                t.setIsMajor("1");
            }else{
                t.setIsMajor("0");
            }

            tBDangerSourceService.saveOrUpdate(t);

            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "修改失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    /************************************************专项风险管控危险源结束*********************************************************************/

    /************************************************专业通用危险源库管控开始*****************************************************/
    /**
     * 添加专业通用危险源
     * @param tBDangerSource
     * @param req
     * @return
     */
    @RequestMapping(params = "goAddUniversalDangerSource")
    public ModelAndView goAddUniversalDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBDangerSource.getId())) {
            tBDangerSource = tBDangerSourceService.getEntity(TBDangerSourceEntity.class, tBDangerSource.getId());
            req.setAttribute("tBDangerSourcePage", tBDangerSource);
        }

        TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
        List<TSType> tsTypeList = tsTypegroup.getTSTypes();
        req.setAttribute("tsTypeList",tsTypeList);

        String threshold_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "重大风险阀值");
        String threshold_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "较大风险阀值");
        String threshold_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "一般风险阀值");

        String threshold_lec_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "重大风险阀值");
        String threshold_lec_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "较大风险阀值");
        String threshold_lec_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "一般风险阀值");

        req.setAttribute("threshold_major", StringUtils.isBlank(threshold_major)?"25":threshold_major);
        req.setAttribute("threshold_superior", StringUtils.isBlank(threshold_superior)?"16":threshold_superior);
        req.setAttribute("threshold_commonly", StringUtils.isBlank(threshold_commonly)?"8":threshold_commonly);
        req.setAttribute("threshold_lec_major", StringUtils.isBlank(threshold_lec_major)?"270":threshold_lec_major);
        req.setAttribute("threshold_lec_superior", StringUtils.isBlank(threshold_lec_superior)?"140":threshold_lec_superior);
        req.setAttribute("threshold_lec_commonly", StringUtils.isBlank(threshold_lec_commonly)?"70":threshold_lec_commonly);

        return new ModelAndView("com/sdzk/buss/web/dangersource/addUniversalDangerSource");
    }

    /**
     * 更新专业通用危险源
     * @param tBDangerSource
     * @param req
     * @return
     */
    @RequestMapping(params = "goUpdateUniversalDangerSource")
    public ModelAndView goUpdateUniversalDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBDangerSource.getId())) {
            tBDangerSource = tBDangerSourceService.getEntity(TBDangerSourceEntity.class, tBDangerSource.getId());
            req.setAttribute("tBDangerSourcePage", tBDangerSource);
        }

        TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
        List<TSType> tsTypeList = tsTypegroup.getTSTypes();
        req.setAttribute("tsTypeList",tsTypeList);

        List<TSType> damageTypeList = systemService.getTypeGroup("danger_Category","危险类别").getTSTypes();
        req.setAttribute("damageTypeList",damageTypeList);

        String threshold_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "重大风险阀值");
        String threshold_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "较大风险阀值");
        String threshold_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "一般风险阀值");

        String threshold_lec_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "重大风险阀值");
        String threshold_lec_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "较大风险阀值");
        String threshold_lec_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "一般风险阀值");

        req.setAttribute("threshold_major", StringUtils.isBlank(threshold_major)?"25":threshold_major);
        req.setAttribute("threshold_superior", StringUtils.isBlank(threshold_superior)?"16":threshold_superior);
        req.setAttribute("threshold_commonly", StringUtils.isBlank(threshold_commonly)?"8":threshold_commonly);
        req.setAttribute("threshold_lec_major", StringUtils.isBlank(threshold_lec_major)?"270":threshold_lec_major);
        req.setAttribute("threshold_lec_superior", StringUtils.isBlank(threshold_lec_superior)?"140":threshold_lec_superior);
        req.setAttribute("threshold_lec_commonly", StringUtils.isBlank(threshold_lec_commonly)?"70":threshold_lec_commonly);

        return new ModelAndView("com/sdzk/buss/web/dangersource/updateUniversalDangerSource");
    }

    /**
     * 添加专业通用危险源
     * @param tBDangerSource
     * @param request
     * @return
     */
    @RequestMapping(params = "doAddUniversalDangerSource")
    @ResponseBody
    public AjaxJson doAddUniversalDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "添加成功";
        String seId = ResourceUtil.getParameter("seId");
        try{
            tBDangerSource.setId(UUIDGenerator.generate());
            tBDangerSource.setOrigin(Constants.DANGER_SOURCE_ORIGIN_NOMAL);
            TSUser sessionUser = ResourceUtil.getSessionUserName();
            tBDangerSource.setReportDepart(sessionUser.getCurrentDepart());
            tBDangerSource.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            tBDangerSource.setReportStatus(Constants.DANGER_SOURCE_REPORT_UNREPORT);
            tBDangerSource.setDepartReportTime(new Date());
            tBDangerSource.setDepartReportMan(sessionUser);
            tBDangerSource.setIsDelete(Constants.IS_DELETE_N);

            String riskGrade = DicUtil.getTypeCodeByName("riskLevel",tBDangerSource.getYeRiskGrade());
            tBDangerSource.setYeRiskGrade(riskGrade);
            tBDangerSourceService.save(tBDangerSource);

            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }



    /**
     * 修改专业通用危险源
     * @param tBDangerSource
     * @param request
     * @return
     */
    @RequestMapping(params = "doUpdateUniversalDangerSource")
    @ResponseBody
    public AjaxJson doUpdateUniversalDangerSource(TBDangerSourceEntity tBDangerSource, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "修改成功";
        TBDangerSourceEntity t = tBDangerSourceService.get(TBDangerSourceEntity.class, tBDangerSource.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBDangerSource, t);
            TSUser sessionUser = ResourceUtil.getSessionUserName();
            t.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            t.setDepartReportTime(new Date());
            t.setDepartReportMan(sessionUser);

            String riskGrade = DicUtil.getTypeCodeByName("riskLevel",t.getYeRiskGrade());
            t.setYeRiskGrade(riskGrade);
            tBDangerSourceService.saveOrUpdate(t);

            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "修改失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    /************************************************专业通用危险源库管控结束*****************************************************/



    /**
     * 通用风险库同步
     * Author：张赛超
     *
     */

    @RequestMapping(params = "synchronizationDataRPC")
    @ResponseBody
    public AjaxJson synchronizationDataRPC() {
        String url = ResourceUtil.getConfigByName("synGenericRisk");
        String json = null;

        String msg = null;
        AjaxJson j = new AjaxJson();

        Map<String,String> paramMap = new HashMap<String,String>();


        /**
         * 加密过程
         * */
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");

         String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", ResourceUtil.getConfigByName("mine_code"));

        try {
            json = HttpClientUtils.get(url, paramMap);
        } catch (NetServiceException e) {
            msg = "连接失败！";
            e.printStackTrace();
        }

//        if (JSONObject.fromObject(json) != null){
//            msg = synchronizationDataRPC.save(json, msg);
//        } else {
//            msg = "网络连接或同步错误！";
//        }
        try {
            JSONObject object= JSONObject.fromObject(json);  //创建JsonObject对象
            msg = synchronizationDataRPC.save(json, msg);

        } catch (Exception e){
            msg = "网络连接或同步失败！";
        }

        j.setMsg(msg);
        return j;

    }


    /**
     *重大风险上报
     * @param ids
     * @param type all=全部上报
     * @return
     */
    @RequestMapping(params = "reportMajorYearRisk")
    @ResponseBody
    public AjaxJson reportMajorYearRisk(String ids, String type){
        if ("all".equals(type)) {//全部上报时,获取所有未上报的记录
            List<String> idList = systemService.findListbySql("select id from t_b_danger_source where ismajor='"+Constants.IS_MAJORDangerSource_Y+"' and (report_status='"+Constants.DANGER_SOURCE_REPORT_UNREPORT+"' or report_status is null) and origin !=  '"+Constants.DANGER_SOURCE_ORIGIN_NOMAL+"'");
            if (idList != null && idList.size()>0) {
                ids = StringUtil.joinString(idList, ",");
            }
        }
        return tBDangerSourceService.reportMajorYearRisk(ids);
    }

    /**
     *重大风险上报集团
     * @param ids
     * @param type all=全部上报
     * @return
     */
    @RequestMapping(params = "reportMajorYearRiskToGroup")
    @ResponseBody
    public AjaxJson reportMajorYearRiskToGroup(String ids, String type){
        if ("all".equals(type)) {//全部上报时,获取所有未上报的记录
            List<String> idList = systemService.findListbySql("select id from t_b_danger_source where ismajor='"+Constants.IS_MAJORDangerSource_Y+"' and (report_group_status='0' or report_group_status is null) and origin !=  '"+Constants.DANGER_SOURCE_ORIGIN_NOMAL+"'");
            if (idList != null && idList.size()>0) {
                ids = StringUtil.joinString(idList, ",");
            }
        }
        return tBDangerSourceService.reportMajorYearRiskToGroup(ids);
    }

    @RequestMapping(params = "addressDangerDatagrid")
    public void addressDangerDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);

        String sql = "select danger_id from (select * from t_b_danger_address_rel where risk_level in ("+Constants.RISK_LEVEL_HIDE_WHERE+")) t where address_id='"+ResourceUtil.getParameter("addressId")+"' and EXISTS(select * from t_b_danger_source ds where ds.id=danger_id and ds.is_delete='0' and ds.audit_status='4' and YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+")) ";
        List<String> ids = systemService.findListbySql(sql);
        if(!ids.isEmpty() && ids.size()>0){
            cq.in("id",ids.toArray());
        }else{
            cq.eq("id","TEST");
        }

//        if("riskAlert".equals(ResourceUtil.getParameter("from"))){
//            cq.addOrder("yeRiskGrade",SortDirection.asc);
//        }
        cq.add();
        this.tBDangerSourceService.getDataGridReturn(cq,false);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() >0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());

                    sql = "select risk_level from t_b_danger_address_rel where danger_id='"+bean.getId()+"' and address_id='"+ResourceUtil.getParameter("addressId")+"'";
                    List<String> risk_level = systemService.findListbySql(sql);
                    if(!risk_level.isEmpty() && risk_level.size()>0){
                        String alertLevel = DicUtil.getTypeNameByCode("riskLevel", risk_level.get(0));
                        bean.setAlertLevel(alertLevel);

                        if("重大风险".equals(alertLevel)){
                            bean.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                        }else if("较大风险".equals(alertLevel)){
                            bean.setAlertColor(Constants.ALERT_COLOR_JDFX);
                        }else if("一般风险".equals(alertLevel)){
                            bean.setAlertColor(Constants.ALERT_COLOR_YBFX);
                        }else{
                            bean.setAlertColor(Constants.ALERT_COLOR_DFX);
                        }
                    }

                }

            }
        }

        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "addressRiskDatagrid")
    public void addressRiskDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String addressId = request.getParameter("addressId");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
        try {
            cq.eq("address.id",addressId);
            cq.eq("status", com.sddb.common.Constants.RISK_IDENTIFI_STATUS_REVIEW);
            cq.eq("isDel", com.sddb.common.Constants.RISK_IS_DEL_FALSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskIdentificationEntity> list = dataGrid.getResults();
            for (RiskIdentificationEntity bean : list) {
                List<RiskFactortsRel> relList = bean.getRelList();
                if (relList == null) {
                    bean.setHazardFactortsNum("0");
                }
                bean.setHazardFactortsNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 风险点关联的风险, 获取通用或已闭合的风险
     *
     * @param request
     * @param response
     * @param dataGrid
     * worktemp
     */
    @RequestMapping(params = "addressDangerSourceDatagrid")
    public void addressDangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);

        String addressCate = request.getParameter("addressCate");

        String recognizeYear = tBDangerSource.getYeRecognizeYear();
        tBDangerSource.setYeRecognizeYear(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        //需要排除的id
        String excludeId = ResourceUtil.getParameter("excludeId");
        //已关联的风险
        String addressId = ResourceUtil.getParameter("addressId");
        String ids = ResourceUtil.getParameter("ids");
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            if (StringUtil.isNotEmpty(excludeId)) {
                //排除已关联的风险
                cq.add(Restrictions.sqlRestriction(" this_.id not in (select address.danger_id from t_b_danger_address_rel address where address.address_id='"+excludeId+"')"));
            } else  if (StringUtil.isNotEmpty(addressId)) {
                //获取已关联的风险
                cq.add(Restrictions.sqlRestriction(" this_.id in (select address.danger_id from t_b_danger_address_rel address where address.address_id='"+addressId+"')"));
            } else {
                cq.add(Restrictions.sqlRestriction(" 1=2 "));
            }
            if (StringUtils.isNotBlank(recognizeYear)) {
                cq.add(Restrictions.sqlRestriction(" this_.YE_RECOGNIZE_TIME like '"+recognizeYear+"%' "));
            }

            if (StringUtils.isNotBlank(ids)) {
                cq.add(Restrictions.sqlRestriction(" this_.hazard_manage_id in ('"+ids.replace(",","','")+"')"));
            }

            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }

            cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            cq.eq("isDelete",Constants.IS_DELETE_N);

            //根据风险点类型筛选
            if(StringUtil.isNotEmpty(addressCate)){
                cq.eq("addressCate", addressCate);
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if("riskAlert".equals(ResourceUtil.getParameter("from"))){
            cq.addOrder("yeRiskGrade",SortDirection.asc);
        }
        cq.add();
        //选择本矿的和专项风险，排除通用的危险源
        cq.add(Restrictions.or(Restrictions.eq("origin", "2"), Restrictions.eq("origin", "3")));
        //按危险源描述查询
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);

        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() >0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();

            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 导出风险点关联的风险
     */
    @RequestMapping(params = "exportRelatedRisks")
    public String exportRelatedRisks(HttpServletRequest request,ModelMap modelMap){
        String relatedIds = request.getParameter("id");
        List<String> ids=new ArrayList<>();
        for(String id: relatedIds.split(",")){
            ids.add(id);
        }

        if(ids.size()==1){
            StringBuffer sql = new StringBuffer();
            sql.append("select address from t_b_address_info  WHERE id  = '"+ids.get(0)+"'");
            List<String> address = systemService.findListbySql(sql.toString());
               if(!address.isEmpty() && address.size()>0){
                    String addressName =address.get(0).toString();
                    modelMap.put(NormalExcelConstants.FILE_NAME,addressName+"关联风险列表");
               }

            }else {
            modelMap.put(NormalExcelConstants.FILE_NAME, "多个地点关联风险列表");
        }
        List<TBDangerSourceEntity> entitylist=new ArrayList<>();
        String addressName="";
        List<TBDangerSourceEntity> retList =new ArrayList<>();
        for( String relatedids: ids) {

           CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class);
           if (StringUtil.isNotEmpty(relatedids)) {
               StringBuffer sql = new StringBuffer();
               sql.append("select address from t_b_address_info  WHERE id  = '"+relatedids+"'");
               List<String> address = systemService.findListbySql(sql.toString());
               addressName =address.get(0).toString();
               cq.add(Restrictions.sqlRestriction(" this_.id in (select address.danger_id from t_b_danger_address_rel address where address.address_id='" + relatedids + "')"));
           } else {
               cq.add(Restrictions.sqlRestriction(" 1=2 "));
           }
           String[] stringTemp = {"3", "2"};
           cq.in("origin", stringTemp);
           cq.add();
            retList = this.tBDangerSourceService.getListByCriteriaQuery(cq, false);


       }

            TemplateExportParams templateExportParams = new TemplateExportParams();

        if(ids.size()==1) {
            StringBuffer sql = new StringBuffer();
            sql.append("select address from t_b_address_info  WHERE id  = '"+ids.get(0)+"'");
            List<String> address = systemService.findListbySql(sql.toString());
            if (!address.isEmpty() && address.size() > 0) {
                addressName = address.get(0).toString();
                templateExportParams.setSheetName(addressName + "关联风险列表");
            }
               templateExportParams.setSheetNum(0);
        }else {
            templateExportParams.setSheetName("关联风险列表");
            templateExportParams.setSheetNum(0);
        }

            String lec = request.getParameter("lec");
            if (lec.equals("no")) {
                templateExportParams.setTemplateUrl("export/template/exportTemp_relatedRisk.xls");
            } else {
                templateExportParams.setTemplateUrl("export/template/exportTemp_relatedRisk_LEC.xls");
            }

            modelMap.put(TemplateExcelConstants.PARAMS, templateExportParams);

            Map<String, Object> map = new HashMap<String, Object>();

            map.put("list", entitylist);


            modelMap.put(TemplateExcelConstants.MAP_DATA, map);

        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    /**
     * 岗位关联的风险, 获取通用或已闭合的风险
     *
     * @param request
     * @param response
     * @param dataGrid
     * worktemp
     */
    @RequestMapping(params = "postDangerSourceDatagrid")
    public void postDangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);

        String recognizeYear = tBDangerSource.getYeRecognizeYear();
        tBDangerSource.setYeRecognizeYear(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        //需要排除的id
        //String excludeId = ResourceUtil.getParameter("excludeId");
        //已关联的风险
        String postid = ResourceUtil.getParameter("postid");

        String ids = ResourceUtil.getParameter("ids");
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            if (StringUtils.isNotBlank(recognizeYear)) {
                cq.add(Restrictions.sqlRestriction(" this_.YE_RECOGNIZE_TIME like '"+recognizeYear+"%' "));
            }

            if (StringUtils.isNotBlank(ids)) {
                cq.add(Restrictions.sqlRestriction(" this_.hazard_manage_id in ('"+ids.replace(",","','")+"')"));
            }
            cq.eq("post.id",postid);
            cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            cq.eq("isDelete",Constants.IS_DELETE_N);
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if("riskAlert".equals(ResourceUtil.getParameter("from"))){
            cq.addOrder("yeRiskGrade",SortDirection.asc);
        }
        cq.add();
        //按危险源描述查询
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);

        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() >0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 作业活动关联的风险, 获取通用或已闭合的风险
     *
     * @param request
     * @param response
     * @param dataGrid
     * worktemp
     */
    @RequestMapping(params = "activityDangerSourceDatagrid")
    public void activityDangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);

        String recognizeYear = tBDangerSource.getYeRecognizeYear();
        tBDangerSource.setYeRecognizeYear(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        //需要排除的id
        //String excludeId = ResourceUtil.getParameter("excludeId");
        //已关联的风险
        String activityid = ResourceUtil.getParameter("activityid");

        String ids = ResourceUtil.getParameter("ids");
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            if (StringUtils.isNotBlank(recognizeYear)) {
                cq.add(Restrictions.sqlRestriction(" this_.YE_RECOGNIZE_TIME like '"+recognizeYear+"%' "));
            }

            if (StringUtils.isNotBlank(ids)) {
                cq.add(Restrictions.sqlRestriction(" this_.hazard_manage_id in ('"+ids.replace(",","','")+"')"));
            }
            cq.eq("activity.id",activityid);
            cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            cq.eq("isDelete",Constants.IS_DELETE_N);
            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if("riskAlert".equals(ResourceUtil.getParameter("from"))){
            cq.addOrder("yeRiskGrade",SortDirection.asc);
        }
        cq.add();
        //按危险源描述查询
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);

        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() >0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    private void queryByDangerSourceName(String dangerSourceName, CriteriaQuery cq, DataGrid dataGrid) {
        if (StringUtils.isBlank(dangerSourceName)) {
            this.tBDangerSourceService.getDataGridReturn(cq, true);
        } else if (!dangerSourceName.contains(" ")){
            cq.like("yeMhazardDesc","%"+dangerSourceName+"%");
            cq.add();
            this.tBDangerSourceService.getDataGridReturn(cq, true);
        } else {
            cq.like("yeMhazardDesc","%"+dangerSourceName.split(" ")[0]+"%");
            cq.add();
            this.tBDangerSourceService.getDataGridReturn(cq, false);
            if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() >0){
                List<TBDangerSourceEntity> tempList= dataGrid.getResults();
                List<TBDangerSourceEntity> resultList = new ArrayList<TBDangerSourceEntity>();
                for(TBDangerSourceEntity t : tempList){
                    String dangerSourceNameTemp = dangerSourceName;

                    if(StringUtils.isNotBlank(dangerSourceNameTemp)){
                        String dangerNames[] = dangerSourceNameTemp.split(" ");
                        boolean val = valDangerSource(t,dangerNames);
                        if(val){
                            resultList.add(t);
                        }
                    }else{
                        resultList.add(t);
                    }
                }
                int currentPage = dataGrid.getPage();
                int pageSize = dataGrid.getRows();
                int endIndex = pageSize * currentPage;

                if(pageSize *currentPage > resultList.size()){
                    endIndex = resultList.size();
                }

                List<TBDangerSourceEntity> retList = resultList.subList(pageSize *(currentPage -1), endIndex);

                dataGrid.setResults(retList);
                dataGrid.setTotal(resultList.size());
            }
        }
    }

    /**
     * 选择管理危险源
     * @param request
     * @return
     */
    @RequestMapping(params = "goAddressDangerSourceList")
    public ModelAndView goAddressDangerSourceList(HttpServletRequest request) {
        request.setAttribute("excludeId",ResourceUtil.getParameter("excludeId"));
        request.setAttribute("year", DateUtils.getYear());

        CriteriaQuery cq = new CriteriaQuery(TSType.class);
        cq.createAlias("TSTypegroup","TSTypegroup");
        cq.eq("TSTypegroup.typegroupcode", "addressCate");
        cq.add();
        List<TSType> addressCateList = systemService.getListByCriteriaQuery(cq, false);
        request.setAttribute("addressCateList", addressCateList);

        return new ModelAndView("com/sdzk/buss/web/dangersource/chooseAddressDangerSource");
    }

    /**
     * 选择管理危险源
     * @param request
     * @return
     */
    @RequestMapping(params = "addressDangerSourceList")
    public ModelAndView addressDangerSourceList(HttpServletRequest request) {
        request.setAttribute("addressId",ResourceUtil.getParameter("addressId"));
        try{
            request.setAttribute("addressName", URLDecoder.decode(ResourceUtil.getParameter("addressName"),"utf-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        String anju = ResourceUtil.getConfigByName("anju");
        request.setAttribute("anju",anju);
        return new ModelAndView("com/sdzk/buss/web/dangersource/addressDangerSourceList");
    }

    /**
     * 隐患
     * @param request
     * @return
     */
    @RequestMapping(params = "dangerSourceList")
    public ModelAndView dangerSourceList(HttpServletRequest request) {
        request.setAttribute("addressId",ResourceUtil.getParameter("addressId"));
        return new ModelAndView("com/sdzk/buss/web/dangersource/dangerSourceList");
    }


    /**
     * 隐患地点
     * @param request
     * @return
     */
    @RequestMapping(params = "addressLevelList")
    public ModelAndView addressLevelList(HttpServletRequest request) {
        request.setAttribute("level",ResourceUtil.getParameter("level"));
        return new ModelAndView("com/sdzk/buss/web/dangersource/addressList");
    }

    @RequestMapping(params = "addressDatagrid")
    public void addressDatagrid(TBAddressInfoEntity tBAddressInfo, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
        try {

            String level = request.getParameter("level");
            Map<String, Object> dynamicLevel = this.tBAddressInfoService.getDynamicLevel();
            if (StringUtil.isNotEmpty(level)) {
                List<String> idList = new ArrayList<>();
                for (Map.Entry<String,Object> entry:dynamicLevel.entrySet()){
                    String levelTemp = String.valueOf(entry.getValue());
                    if(StringUtil.isNotEmpty(levelTemp)){
                        if(levelTemp.equals(level)){
                            idList.add(entry.getKey());
                        }
                    }
                }
                cq.in("id", idList.toArray());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null) {
            List<TBAddressInfoEntity> entityList = dataGrid.getResults();
            String sql = "SELECT exam.address address, count(1) count FROM t_b_hidden_danger_exam exam LEFT JOIN t_b_hidden_danger_handle handle ON handle.hidden_danger_id = exam.id WHERE handle.handlel_status IN ('1', '4') GROUP BY exam.address";

            List<Map<String, Object>> result = systemService.findForJdbc(sql);
            Map<String, String> countMap = new HashMap<>();
            if (result != null && result.size() > 0) {
                for (Map<String, Object> obj : result) {
                    if (obj.get("count") != null && obj.get("address") != null) {
                        countMap.put(obj.get("address").toString(), obj.get("count").toString());
                    }
                }
            }
            if (null != entityList && entityList.size() > 0) {
                for (int i = 0; i < entityList.size(); i++) {
                    TBAddressInfoEntity addressInfoEntity = entityList.get(i);
                    addressInfoEntity.setHiddenCount(countMap.get(addressInfoEntity.getId()));
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 批量删除t_b_danger_address_rel
     *
     * @return
     */
    @RequestMapping(params = "doBatchDelAddressRel")
    @ResponseBody
    public AjaxJson doBatchDelAddressRel(HttpServletRequest request){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "删除成功";
        String ids = ResourceUtil.getParameter("ids");
        String addressId = ResourceUtil.getParameter("addressId");
        try{
            if (StringUtil.isNotEmpty(ids) && StringUtil.isNotEmpty(addressId)) {
                CriteriaQuery cq = new CriteriaQuery(TBDangerAddresstRelEntity.class);
                cq.eq("addressId", addressId);
                cq.in("dangerId", ids.split(","));
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

    /**
     * 保存风险点有风险关系
     * @param request
     * @return
     */
    @RequestMapping(params = "saveAddressChooseDanger")
    @ResponseBody
    public AjaxJson saveAddressChooseDanger(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        String ids = request.getParameter("ids");//选择的危险源
        String addressId = ResourceUtil.getParameter("addressId");
        try{
            if(StringUtil.isNotEmpty(ids)){
                String[] idArray = ids.split(",");
                TBDangerAddresstRelEntity entity= null;
                for(String id : idArray){
                    entity = new TBDangerAddresstRelEntity();
                    entity.setDangerId(id);
                    entity.setAddressId(addressId);

                    TBDangerSourceEntity danger = systemService.getEntity(TBDangerSourceEntity.class,id);
                    if(danger!= null && StringUtils.isNotBlank(danger.getYeRiskGrade())){
                        entity.setRiskLevel(danger.getYeRiskGrade());
                    }

                    this.systemService.save(entity);
                    systemService.addLog("风险点关联风险添加\""+entity.getId()+"\"成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
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
                    sunshineEntity.setTableName("t_b_danger_source");
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

    /**
     * 危险源全部关联 全部关联
     * @return
     */
    @RequestMapping(params = "relAll")
    public void relAll(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class);

        String recognizeYear = request.getParameter("yeRecognizeYear");
        String yeProfession = request.getParameter("yeProfession");
        String yeRiskGrade = request.getParameter("yeRiskGrade");
        String yeMhazardDesc = request.getParameter("yeMhazardDesc");
        String possiblyHazard = request.getParameter("yePossiblyHazard");
        String addressCate = request.getParameter("addressCate");

        //需要排除的id
        String excludeId = ResourceUtil.getParameter("excludeId");
        //已关联的风险
        String addressId = ResourceUtil.getParameter("addressId");
        String ids = ResourceUtil.getParameter("ids");
        //查询条件组装器
//        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        StringBuffer sql = new StringBuffer();
        sql.append("select * from t_b_danger_source where 1=1");
        try{
            if (StringUtil.isNotEmpty(excludeId)) {
                //排除已关联的风险
//                cq.add(Restrictions.sqlRestriction(" this_.id not in (select address.danger_id from t_b_danger_address_rel address where address.address_id='"+excludeId+"')"));
                sql.append(" and id not in( SELECT address.danger_id from t_b_danger_address_rel address where address.address_id='").append(excludeId).append("')");
            } else  if (StringUtil.isNotEmpty(addressId)) {
                //获取已关联的风险
//                cq.add(Restrictions.sqlRestriction(" this_.id in (select address.danger_id from t_b_danger_address_rel address where address.address_id='"+addressId+"')"));
                sql.append(" and not id in (SELECT address.danger_id from t_b_danger_address_rel address where address.address_id='").append(addressId).append("')");
            } else {
//                cq.add(Restrictions.sqlRestriction(" 1=2 "));
                sql.append(" and 1=2");
            }
            if (StringUtils.isNotBlank(recognizeYear)) {
//                cq.add(Restrictions.sqlRestriction(" this_.YE_RECOGNIZE_TIME like '"+recognizeYear+"%' "));
                sql.append(" and ye_recognize_time like '").append(recognizeYear).append("%'");
            }

            if (StringUtils.isNotBlank(ids)) {
//                cq.add(Restrictions.sqlRestriction(" this_.hazard_manage_id in ('"+ids.replace(",","','")+"')"));
                sql.append(" and hazard_manage_id in('").append(ids.replace(",","','")).append("')");
            }

            if (StringUtils.isNotBlank(possiblyHazard)) {
//                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
                sql.append(" and ye_possibly_hazard like '").append(possiblyHazard).append("%'");
            }

//            cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
//            cq.eq("isDelete",Constants.IS_DELETE_N);
            sql.append(" and audit_status='4'");
            sql.append(" and is_delete='0'");

            //根据风险点类型筛选
            if(StringUtil.isNotEmpty(addressCate)){
//                cq.eq("addressCate", addressCate);
                sql.append(" and address_cate='").append(addressCate).append("'");
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        List<Object[]> tempList = systemService.findListbySql(sql.toString());
        List<TBDangerAddresstRelEntity> saveList = new ArrayList();
        for(Object[] t : tempList){
            TBDangerAddresstRelEntity temp = new TBDangerAddresstRelEntity();
            temp.setAddressId(addressId);
            temp.setDangerId(t[0].toString());
            temp.setRiskLevel(t[16].toString());
            saveList.add(temp);
        }
        systemService.batchSave(saveList);
    }


    /**
     *  一键复制跳转页面
     * */
    @RequestMapping(params = "goOneKeyCopy")
    public ModelAndView goOneKeyCopy(HttpServletResponse response, HttpServletRequest request){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date previousYear = c.getTime();
        Date thisYear = DateUtils.getDate();

        request.setAttribute("previousYear", previousYear);
        request.setAttribute("thisYear", thisYear);
        return new ModelAndView("com/sdzk/buss/web/dangersource/oneKeyCopy");
    }

    /**
     *  一键复制数据保存
     * */
    @RequestMapping(params = "doOneKeyCopy")
    @ResponseBody
    public AjaxJson saveOneKeyCopy(HttpServletRequest request, HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String message = "";

        String from = request.getParameter("from");
        String to = request.getParameter("to");

        if(StringUtils.isNotBlank(from) && StringUtils.isNotBlank(to)){
            message = "一键复制成功！";

            //TODO 复制年度风险
            TSUser user = ResourceUtil.getSessionUserName();
            TSDepart dept = user.getCurrentDepart();
            try {
                CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class);

                Date startFromYear = new Date();
                Date endFromYear = new Date();
                startFromYear = DateUtils.str2Date(from + "-01-01 00:00:00", DateUtils.datetimeFormat);
                endFromYear = DateUtils.str2Date(to + "-12-31 23:59:59", DateUtils.datetimeFormat);

                cq.ge("yeRecognizeTime", startFromYear);
                cq.le("yeRecognizeTime", endFromYear);
                cq.eq("auditStatus", "4");
                cq.eq("isDelete", "0");
                cq.add();

                List<TBDangerSourceEntity> list = systemService.getListByCriteriaQuery(cq, false);
                //判断今年是否有风险数据
                String isThisYearHaveDataSql = "select count(id) from t_b_danger_source where ye_recognize_time like '"+to+"%'";
                List<Object> flagList = systemService.findListbySql(isThisYearHaveDataSql);
                int flag = 0;
                if (flagList!=null && flagList.size()>0){
                    if (flagList.get(0)!=null){
                        flag = Integer.parseInt(flagList.get(0).toString());
                    }
                }

                //如果已经有今年的数据，那么不允许一键复制操作
                if (flag>0){
                    message = "今年已经有风险数据，不允许使用一键复制功能！";
                }else{
                    for (TBDangerSourceEntity e : list){
                        TBDangerSourceEntity newEntity = new TBDangerSourceEntity();
                        //id重新生成
                        newEntity.setId(UUIDGenerator.generate());
                        //默认辨识时间是复制当天的
                        newEntity.setYeRecognizeTime(new Date());
                        newEntity.setAlertColor(e.getAlertColor());
                        newEntity.setYeAccident(e.getYeAccident());
                        newEntity.setYeMhazardDesc(e.getYeMhazardDesc());
                        newEntity.setYeRiskGrade(e.getYeRiskGrade());
                        newEntity.setAuditStatus(e.getAuditStatus());
                        newEntity.setYePossiblyHazard(e.getYePossiblyHazard());
                        newEntity.setYeStandard(e.getYeStandard());
                        newEntity.setDocSource(e.getDocSource());
                        newEntity.setIsMajor(e.getIsMajor());
                        newEntity.setIsDelete(e.getIsDelete());
                        newEntity.setOrigin(e.getOrigin());
                        newEntity.setReportStatus(e.getReportStatus());
                        newEntity.setDepartReportMan(e.getDepartReportMan());
                        newEntity.setDepartReportTime(e.getDepartReportTime());
                        newEntity.setReportDepart(e.getReportDepart());
                        newEntity.setYeRecognizeYear(e.getYeRecognizeYear());
                        newEntity.setLecExposure(e.getLecExposure());
                        newEntity.setLecRiskLoss(e.getLecRiskLoss());
                        newEntity.setLecRiskPossibility(e.getLecRiskPossibility());
                        newEntity.setYeHazardCate(e.getYeHazardCate());
                        newEntity.setYeProfession(e.getYeProfession());
                        newEntity.setYeCost(e.getYeCost());
                        newEntity.setYeProbability(e.getYeProbability());
                        newEntity.setDamageType(e.getDamageType());
                        newEntity.setActivity(e.getActivity());
                        newEntity.setHazard(e.getHazard());
                        newEntity.setHiddenLevel(e.getHiddenLevel());
                        newEntity.setManageMeasure(e.getManageMeasure());
                        newEntity.setPost(e.getPost());
                        newEntity.setRiskValue(e.getRiskValue());
                        newEntity.setAddressCate(e.getAddressCate());
                        newEntity.setAlertLevel(e.getAlertLevel());
                        newEntity.setFineMoney(e.getFineMoney());
                        newEntity.setLecRiskValue(e.getLecRiskValue());
                        newEntity.setOrderNum(e.getOrderNum());
                        newEntity.setYeCaseNum(e.getYeCaseNum());
                        newEntity.setYeDistance(e.getYeDistance());
                        newEntity.setYeEmergency(e.getYeEmergency());
                        newEntity.setYeLocation(e.getYeLocation());
                        newEntity.setYeMonitor(e.getYeMonitor());
                        newEntity.setYeReference(e.getYeReference());
                        newEntity.setYeSurrounding(e.getYeSurrounding());
                        newEntity.setCreateBy(user.getUserName());
                        newEntity.setCreateDate(new Date());
                        newEntity.setCreateName(user.getRealName());
                        newEntity.setYeResDepart(e.getYeResDepart());
                        newEntity.setYeUploadTime(e.getYeUploadTime());

                        systemService.save(newEntity);
                        systemService.addLog("年度风险\"" + newEntity.getId() + "\"一键复制成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);

                        /************下面保存风险点关联风险的关联关系**********/
                        List<TBDangerAddresstRelEntity> relEntityList = systemService.findByProperty(TBDangerAddresstRelEntity.class, "dangerId", e.getId());
                        if (relEntityList!=null && relEntityList.size()>0){
                            for (TBDangerAddresstRelEntity relEntity : relEntityList){
                                relEntity.setDangerId(newEntity.getId());
                            }
                        }
                        systemService.batchSave(relEntityList);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                message = "一键复制失败";
                systemService.addLog(message + "：" + e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_INSERT);
                throw new BusinessException(e.getMessage());
            }
        }else{
            if (StringUtil.isEmpty(from)){
                message = "风险来源年份为空";
            }
            if (StringUtil.isEmpty(to)){
                message = "风险复制目标年份为空";
            }
        }

        System.out.println(message);

        j.setMsg(message);
        return j;
    }

    /**
     * 导出岗位关联风险列表
     * @param response
     * @param request
     * @param postId
     * @return
     */
    @RequestMapping(params = "postRelDangerExportXls")
    public String postRelDangerExportXls(HttpServletResponse response,HttpServletRequest request,String postId,ModelMap modelMap){
        String _yeRecognizeYear = request.getParameter("yeRecognizeYear");
        String _yeMhazardDesc = request.getParameter("yeMhazardDesc");
        String _yeProfession = request.getParameter("yeProfession");
        String _yeHazardCate = request.getParameter("yeHazardCate");
        String _yeRiskGrade = request.getParameter("yeRiskGrade");
        String _activityId = request.getParameter("activity.id");
        String _yePossiblyHazard = request.getParameter("yePossiblyHazard");
        String _docSource = request.getParameter("docSource");
        String _yeStandard = request.getParameter("yeStandard");

        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            if(StringUtils.isNotBlank(postId)){
                cq.in("post.id",postId.split(","));
            }
            if(StringUtils.isNotBlank(_yeRecognizeYear)){
                cq.le("yeRecognizeTime",sdf.parse(_yeRecognizeYear+"-12-31 23:59:59"));
                cq.ge("yeRecognizeTime",sdf.parse(_yeRecognizeYear+"-01-01 00:00:00"));
            }
            if(StringUtils.isNotBlank(_yeMhazardDesc)){
                cq.like("yeMhazardDesc","%"+_yeMhazardDesc+"%");
            }
            if(StringUtils.isNotBlank(_yeProfession)){
                cq.eq("yeProfession",_yeProfession);
            }
            if(StringUtils.isNotBlank(_yeHazardCate)){
                cq.eq("yeHazardCate",_yeHazardCate);
            }
            if(StringUtils.isNotBlank(_yeRiskGrade)){
                cq.eq("yeRiskGrade",_yeRiskGrade);
            }
            if(StringUtils.isNotBlank(_activityId)){
                cq.eq("activity.id",_activityId);
            }
            if(StringUtils.isNotBlank(_yePossiblyHazard)){
                cq.like("yePossiblyHazard","%"+_yePossiblyHazard+"%");
            }
            if(StringUtils.isNotBlank(_docSource)){
                cq.like("docSource","%"+_docSource+"%");
            }
            if(StringUtils.isNotBlank(_yeStandard)){
                cq.like("yeStandard","%"+_yeStandard+"%");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        cq.addOrder("yeRecognizeTime",SortDirection.desc);
        cq.add();
        List<TBDangerSourceEntity> retList = systemService.getListByCriteriaQuery(cq,false);

        if (retList != null && !retList.isEmpty()) {
            int orderNum = 0;
            for (TBDangerSourceEntity bean : retList) {
                orderNum++;
                bean.setOrderNum(String.valueOf(orderNum));

                String auditStatus = bean.getAuditStatus();
                if (Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT.equals(auditStatus)) {
                    bean.setAuditStatusTemp("待上报");
                } else if (Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW.equals(auditStatus)) {
                    bean.setAuditStatusTemp("审核中");
                } else if (Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK.equals(auditStatus)) {
                    bean.setAuditStatusTemp("审核退回");
                } else if (Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE.equals(auditStatus)) {
                    bean.setAuditStatusTemp("闭环");
                }

                //专业类型
                String yeProfession = bean.getYeProfession();
                if (StringUtils.isNotBlank(yeProfession)) {
                    String yeProfessiontemp = DicUtil.getTypeNameByCode("proCate_gradeControl", yeProfession);
                    bean.setYeProfessiontemp(yeProfessiontemp);
                }
                //事故类型
                String sgxlStr = bean.getYeAccident();
                if (StringUtils.isNotBlank(sgxlStr)) {
                    if (StringUtils.isNotBlank(sgxlStr)) {
                        String[] sgxlArray = sgxlStr.split(",");
                        StringBuffer sb = new StringBuffer();
                        for (String str : sgxlArray) {
                            String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                            if (StringUtils.isNotBlank(sb.toString())) {
                                sb.append(",");
                            }
                            sb.append(retName);
                        }
                        bean.setYeAccidentTemp(sb.toString());
                    }
                }

                String yeRiskGrade = bean.getYeRiskGrade();
                if (StringUtils.isNotBlank(yeRiskGrade)) {
                    String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", yeRiskGrade);
                    bean.setYeRiskGradeTemp(yeRiskGradeTemp);
                }

                //风险类型
                String yeHazardCate = bean.getYeHazardCate();
                if (StringUtils.isNotBlank(yeHazardCate)) {
                    String yeHazardCateTemp = DicUtil.getTypeNameByCode("hazardCate", yeHazardCate);
                    bean.setYeHazardCateTemp(yeHazardCateTemp);
                }
                //隐患等级
                String hiddenLevel = bean.getHiddenLevel();
                if (StringUtils.isNotBlank(hiddenLevel)) {
                    String hiddenLevelTemp = DicUtil.getTypeNameByCode("hiddenLevel", hiddenLevel);
                    bean.setHiddenLevelTemp(hiddenLevelTemp);
                }

                String damageTypeStr = bean.getDamageType();
                if (StringUtils.isNotBlank(damageTypeStr)) {
                    String[] damageTypeArray = damageTypeStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for (String str : damageTypeArray) {
                        String retName = DicUtil.getTypeNameByCode("danger_Category", str);
                        if (StringUtils.isNotBlank(sb.toString())) {
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setDamageTypeTemp(sb.toString());
                }
                String origin = bean.getOrigin();
                if (StringUtils.isNotBlank(origin)) {
                    if (Constants.DANGER_SOURCE_ORIGIN_MINE.equals(origin)) {
                        bean.setOriginTemp("本矿井");
                    } else if (Constants.DANGER_SOURCE_ORIGIN_NOMAL.equals(origin)) {
                        bean.setOriginTemp("通用");
                    } else if (Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION.equals(origin)) {
                        bean.setOriginTemp("专项风险评估");
                    }
                }

                //LEC风险可能性
                String lecRiskPossibility = String.valueOf(bean.getLecRiskPossibility());
                if(StringUtils.isNotBlank(lecRiskPossibility)){
                    String lecRiskPossibilityTemp = DicUtil.getTypeNameByCode("lec_risk_probability",lecRiskPossibility);
                    bean.setLecRiskPossibilityTemp(lecRiskPossibilityTemp);
                }

                //LEC风险损失
                String lecRiskLoss = String.valueOf(bean.getLecRiskLoss());
                if(StringUtils.isNotBlank(lecRiskLoss)){
                    String lecRiskLossTemp = DicUtil.getTypeNameByCode("lec_risk_loss",lecRiskLoss);
                    bean.setLecRiskLossTemp(lecRiskLossTemp);
                }
                //LEC人员暴露于危险环境中的频繁程度
                String lecExposure = String.valueOf(bean.getLecExposure());
                if(StringUtils.isNotBlank(lecExposure)){
                    String lecExposureTemp = DicUtil.getTypeNameByCode("lec_exposure",lecExposure);
                    bean.setLecExposureTemp(lecExposureTemp);
                }

                if (StringUtils.isBlank(bean.getFineMoney())) {
                    bean.setFineMoney(null);
                }
            }
        }

        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetName("关联风险列表");
        templateExportParams.setSheetNum(0);
        String lec = ResourceUtil.getInitParam("les");
        if (lec.equals("no")) {
            templateExportParams.setTemplateUrl("export/template/exportTemp_postRelRisk.xls");
        } else {
            templateExportParams.setTemplateUrl("export/template/exportTemp_postRelRisk_LEC.xls");
        }
        modelMap.put(NormalExcelConstants.FILE_NAME,"岗位关联风险列表");
        modelMap.put(TemplateExcelConstants.PARAMS, templateExportParams);
        Map<String, Object> map = new HashMap<>();
        map.put("list", retList);
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);

        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }



    /*********************************************************风险关联风险点 start**************************************************/


    /**
     * 保存风险与风险点关系
     * @param request
     * @return
     */
    @RequestMapping(params = "saveDangerChooseAddress")
    @ResponseBody
    public AjaxJson saveDangerChooseAddress(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        String ids = request.getParameter("ids");//选择的风险点
        String dangerId = ResourceUtil.getParameter("dangerId"); //风险
        try{
            if(StringUtil.isNotEmpty(ids)){
                String[] idArray = ids.split(",");
                TBDangerAddresstRelEntity entity= null;
                for(String id : idArray){
                    entity = new TBDangerAddresstRelEntity();
                    entity.setDangerId(dangerId);
                    entity.setAddressId(id);

                    TBDangerSourceEntity danger = systemService.getEntity(TBDangerSourceEntity.class,dangerId);
                    if(danger!= null && StringUtils.isNotBlank(danger.getYeRiskGrade())){
                        entity.setRiskLevel(danger.getYeRiskGrade());
                    }

                    this.systemService.save(entity);
                    systemService.addLog("风险关联风险点添加\""+entity.getId()+"\"成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /*********************************************************风险关联风险点 end*****************************************************/

    /*********************************************************风险管控状态清单 start*************************************************/

    /**
     * 风险管控状态清单列表
     * @param request
     * @return
     */
    @RequestMapping(params = "riskControlList")
    public ModelAndView riskControlList(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/dangersource/riskControlList");
    }


    @RequestMapping(params = "riskControlDatagrid")
    public void riskControlDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            // 查询未删除的
            cq.eq("isDelete",Constants.IS_DELETE_N);

            // 排除专项管理的危险源
            cq.notIn("origin", new String[]{Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION,Constants.DANGER_SOURCE_ORIGIN_NOMAL});

        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        List<TBDangerSourceEntity> tempList = systemService.getListByCriteriaQuery(cq,false);
        if(tempList != null  && tempList.size() > 0){
            setRiskNum(tempList);
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

                String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", bean.getYeRiskGrade());

                if("重大风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                }else if("较大风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_JDFX);
                }else if("一般风险".equals(yeRiskGradeTemp)){
                    bean.setAlertColor(Constants.ALERT_COLOR_YBFX);
                }else{
                    bean.setAlertColor(Constants.ALERT_COLOR_DFX);
                }
                bean.setYeRiskGradeTemp(yeRiskGradeTemp);
            }
        }
        this.tBDangerSourceService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    // 查询风险关联隐患个数
    private void setRiskNum(List<TBDangerSourceEntity> tBDangerInfos) {
        if (tBDangerInfos  != null && tBDangerInfos.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (TBDangerSourceEntity entity : tBDangerInfos ) {
                if (StringUtil.isNotEmpty(ids.toString())) {
                    ids.append(",");
                }
                ids.append("'").append(entity.getId()).append("'") ;
            }

            String sql = "select count(1) total, t.danger_id from t_b_hidden_danger_exam t where danger_id in ("+ids.toString()+") group by t.danger_id";
            List<Map<String, Object>> result = systemService.findForJdbc(sql);
            Map<String, String> countMap = new HashMap<>();
            if (result != null && result.size() > 0) {
                for (Map<String, Object> obj : result) {
                    if (obj.get("total") != null && obj.get("danger_id") != null) {
                        countMap.put(obj.get("danger_id").toString(), obj.get("total").toString());
                    }
                }
            }
            for (TBDangerSourceEntity entity : tBDangerInfos ) {
                entity.setRiskNum(countMap.get(entity.getId())!=null?countMap.get(entity.getId()):"0");
            }
        }
    }

    /*********************************************************风险管控状态清单 end***************************************************/


}



