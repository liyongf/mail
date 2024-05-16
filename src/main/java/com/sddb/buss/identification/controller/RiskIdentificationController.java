package com.sddb.buss.identification.controller;

import com.sddb.buss.identification.entity.*;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.buss.riskdata.entity.HazardFactorsPostEntity;
import com.sddb.buss.riskmanage.entity.*;
import com.sddb.common.Constants;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.excelverify.RiskIdentificationPostExcelVerifyHandler;
import com.sdzk.buss.web.common.service.SfService;
import com.sdzk.buss.web.common.taskProvince.TBReportDeleteIdEntity;
import com.sdzk.buss.web.gjj.entity.SfRiskMeasureEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.p3.core.util.MD5Util;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope("prototype")
@Controller
@RequestMapping("/riskIdentificationController")
public class RiskIdentificationController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private SfService sfService;

    private String message;

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        String identificationType = request.getParameter("identificationType");
        //辨识任务
        String riskTaskId = request.getParameter("riskTaskId");
        if(StringUtil.isNotEmpty(riskTaskId)){
            RiskTaskEntity riskTaskEntity = systemService.getEntity(RiskTaskEntity.class,riskTaskId);
            identificationType = riskTaskEntity.getTaskType();
            request.setAttribute("riskTaskId",riskTaskId);
        }
        request.setAttribute("identificationType",identificationType);
        String identificationTypeName = "";
        if(Constants.HAZARDFACTORS_FROM_YEAR.equals(identificationType)){
            identificationTypeName = "年度风险辨识";
        }else if(Constants.HAZARDFACTORS_FROM_MAJOR.equals(identificationType)){
            identificationTypeName = "专项风险辨识";
        }else if(Constants.HAZARDFACTORS_FROM_POST.equals(identificationType)){
            identificationTypeName = "岗位风险辨识";
        }else if(Constants.HAZARDFACTORS_FROM_TEMPWORK.equals(identificationType)){
            identificationTypeName = "临时施工风险辨识";
        }
        request.setAttribute("identificationTypeName",identificationTypeName);
        return new ModelAndView("com/sddb/buss/web/identification/riskIdentificationList");
    }

    @RequestMapping(params = "postListNew")
    public ModelAndView postListNew(HttpServletRequest request) {
//        String identificationType = request.getParameter("identificationType");
//        request.setAttribute("identificationType",identificationType);
//        String identificationTypeName = "";
//        if(Constants.HAZARDFACTORS_FROM_YEAR.equals(identificationType)){
//            identificationTypeName = "年度风险辨识";
//        }else if(Constants.HAZARDFACTORS_FROM_MAJOR.equals(identificationType)){
//            identificationTypeName = "专项风险辨识";
//        }else if(Constants.HAZARDFACTORS_FROM_POST.equals(identificationType)){
//            identificationTypeName = "岗位风险辨识";
//        }else if(Constants.HAZARDFACTORS_FROM_TEMPWORK.equals(identificationType)){
//            identificationTypeName = "临时施工风险辨识";
//        }
//        request.setAttribute("identificationTypeName",identificationTypeName);
        //辨识任务
        String riskTaskId = request.getParameter("riskTaskId");
        request.setAttribute("riskTaskId",riskTaskId);
        String detail = request.getParameter("detail");
        request.setAttribute("detail",detail);
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
        if (isAdmin) { //管理员可以查看所有的
            request.setAttribute("isAdmin","1");
        }
        return new ModelAndView("com/sddb/buss/web/identification/riskIdentificationPostListNew");
    }

    @RequestMapping(params = "querylist")
    public ModelAndView querylist(HttpServletRequest request) {
        String newPost = ResourceUtil.getConfigByName("newPost");
        request.setAttribute("newPost",newPost);
        String riskLevel = request.getParameter("riskLevel");
        request.setAttribute("riskLevel",riskLevel);
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
        if (isAdmin) { //管理员可以查看所有的
            request.setAttribute("isAdmin","1");
        }
        return new ModelAndView("com/sddb/buss/web/identification/riskList");
    }

    @RequestMapping(params = "reviewList")
    public ModelAndView reviewList(HttpServletRequest request) {
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
        if (isAdmin) { //管理员可以查看所有的
            request.setAttribute("isAdmin","1");
        }
        String newPost = ResourceUtil.getConfigByName("newPost");
        request.setAttribute("newPost",newPost);
        return new ModelAndView("com/sddb/buss/web/identification/reviewList");
    }

    @RequestMapping(params = "reviewListDatagrid")
    public void reviewListDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String queryHandleStatus= request.getParameter("queryHandleStatus");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
        try {
            cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
            if(StringUtils.isBlank(queryHandleStatus) || Constants.RISK_IDENTIFI_STATUS_TOVIEW.equals(queryHandleStatus)) {
                cq.eq("status", Constants.RISK_IDENTIFI_STATUS_TOVIEW);//待审核
            }else{
                cq.in("status",new String[]{Constants.RISK_IDENTIFI_STATUS_ROLLBACK,Constants.RISK_IDENTIFI_STATUS_REVIEW});
            }

            String addressId = request.getParameter("addressId");
            if(StringUtil.isNotEmpty(addressId)){
                cq.createAlias("address","address");
                cq.eq("address.id",addressId);
            }
            String postId = request.getParameter("postId");
            if(StringUtil.isNotEmpty(postId)){
                cq.createAlias("post","post");
                cq.eq("post.id",postId);
            }

            String identificationType = request.getParameter("identificationType");
            if(StringUtil.isNotEmpty(identificationType)){
                cq.eq("identificationType",identificationType);
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
            if (!isAdmin) { //管理员可以查看所有的
                cq.eq("reviewMan",sessionUser.getId());
            }
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

    @RequestMapping(params = "mainDatagrid")
    public void mainDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String identificationType = request.getParameter("identificationType");
        String queryHandleStatus= request.getParameter("queryHandleStatus");
        String riskType= request.getParameter("riskType");
        String addressName= request.getParameter("address.address");
        String addressId = request.getParameter("addressId");
        String postId = request.getParameter("postId");
        String postName= request.getParameter("post.postName");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
        try {
            cq.eq("identificationType", identificationType);
            cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
            if(StringUtils.isBlank(queryHandleStatus) || Constants.RISK_IDENTIFI_STATUS_DRAFT.equals(queryHandleStatus)){
                //默认查询草稿状态
                cq.eq("status",Constants.RISK_IDENTIFI_STATUS_DRAFT);
            }else if(Constants.HAZARDFACTORS_STATUS_TOVIEW.equals(queryHandleStatus)){
                //查询已提交状态
                cq.in("status",new String[]{Constants.RISK_IDENTIFI_STATUS_TOVIEW,Constants.RISK_IDENTIFI_STATUS_REVIEW});
            }else if(Constants.RISK_IDENTIFI_STATUS_ROLLBACK.equals(queryHandleStatus)){
                cq.eq("status",Constants.RISK_IDENTIFI_STATUS_ROLLBACK);
            }
            if(StringUtils.isNotBlank(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtils.isNotBlank(addressName)){
                cq.createAlias("address","address");
                cq.like("address.address","%"+addressName+"%");
            }
            if(StringUtils.isNotBlank(postName)){
                cq.createAlias("post","post");
                cq.like("post.postName","%"+postName+"%");
            }

            String riskManageHazardFactorId = request.getParameter("riskManageHazardFactorId");
            if(StringUtil.isNotEmpty(riskManageHazardFactorId)){
                cq.add(Restrictions.sqlRestriction("this_.id in (select risk_id from t_b_risk_manage_rel_risk where risk_manage_hazard_factor_id = '" + riskManageHazardFactorId + "')"));
            }


            String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
            if(StringUtil.isNotEmpty(riskManageTaskAllId)){
                RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,riskManageTaskAllId);
                String manageType = riskManageTaskAllEntity.getManageType();
                if(manageType.equals("post")){
                    cq.add(Restrictions.sqlRestriction("this_.id in (SELECT id from t_b_risk_identification WHERE risk_manage_task_id in (SELECT id from t_b_risk_manage_post_task WHERE post_task_all_id ='"+riskManageTaskAllId+"'))"));
                }else{
                    cq.add(Restrictions.sqlRestriction("this_.id in (SELECT id from t_b_risk_identification WHERE risk_manage_task_id in (SELECT id from t_b_risk_manage_task WHERE task_all_id ='"+riskManageTaskAllId+"'))"));
                }

            }

            String riskManageTaskAllIdYH = request.getParameter("riskManageTaskAllIdYH");
            if(StringUtil.isNotEmpty(riskManageTaskAllIdYH)){
                cq.eq("riskManageTaskAllId",riskManageTaskAllIdYH);

            }


            if(StringUtils.isNotBlank(addressId)){
                cq.createAlias("address","address");
                cq.eq("address.id", addressId);
            }
            if(StringUtils.isNotBlank(postId)){
                cq.createAlias("post","post");
                cq.eq("post.id", postId);
            }
            if(!identificationType.equals("4")){
                Map<String,Object> orderMap = new HashedMap();
                orderMap.put("address.id",SortDirection.asc);
                cq.setOrder(orderMap);
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
            if (!isAdmin) { //管理员可以查看所有的
                cq.eq("createBy",sessionUser.getUserName());
            }
            //辨识任务
            String riskTaskId = request.getParameter("riskTaskId");
            if(StringUtil.isNotEmpty(riskTaskId)){
                cq.eq("createBy",sessionUser.getUserName());
                cq.eq("riskTaskId",riskTaskId);
            }



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


    @RequestMapping(params = "queryListDatagrid")
    public void queryListDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String address = request.getParameter("address.address");
        String postName = request.getParameter("post.postName");
        String riskType = request.getParameter("riskType");
        String riskLevel = request.getParameter("riskLevel");
        String manageLevel = request.getParameter("manageLevel");
        String dutyManager = request.getParameter("dutyManager");
        String addressId = request.getParameter("addressId");
        String postId = request.getParameter("postId");
        String identificationTypeArr[] = request.getParameterValues("identificationType");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
        try {
            cq.eq("status",Constants.RISK_IDENTIFI_STATUS_REVIEW);
            cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
            if(StringUtils.isNotBlank(address)){
                cq.createAlias("address","address");
                cq.like("address.address","%"+address+"%");
            }
            if(StringUtils.isNotBlank(postName)){
                cq.createAlias("post","post");
                cq.like("post.postName","%"+postName+"%");
            }
            if(StringUtils.isNotBlank(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtils.isNotBlank(riskLevel)){
                cq.eq("riskLevel",riskLevel);
            }
            if(StringUtils.isNotBlank(manageLevel)){
                cq.eq("manageLevel",manageLevel);
            }
            if(StringUtils.isNotBlank(dutyManager)){
                cq.like("dutyManager","%"+dutyManager+"%");
            }

            if(StringUtils.isNotBlank(addressId)){
                cq.createAlias("address","address");
                cq.eq("address.id",addressId);
            }
            if(StringUtils.isNotBlank(postId)){
                cq.createAlias("post","post");
                cq.eq("post.id",postId);
            }
            if(identificationTypeArr.length>0){
                for(int i=0;i<identificationTypeArr.length;i++){
                    cq.eq("identificationType",identificationTypeArr[i]);
                }
            }
            String identifiDateStart = request.getParameter("identifiDate_begin");
            String identifiDateEnd = request.getParameter("identifiDate_end");
            if(StringUtils.isNotBlank(identifiDateStart)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("identifiDate",sdf.parse(identifiDateStart));
            }
            if(StringUtils.isNotBlank(identifiDateEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("identifiDate",sdf.parse(identifiDateEnd));
            }
            String expDateStart = request.getParameter("expDate_begin");
            String expDateEnd = request.getParameter("endDate_end");
            if(StringUtils.isNotBlank(expDateStart)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("expDate",sdf.parse(expDateStart));
            }
            if(StringUtils.isNotBlank(expDateEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("expDate",sdf.parse(expDateEnd));
            }
            String queryYear = request.getParameter("queryYear");
            if(StringUtil.isNotEmpty(queryYear)){
                cq.add(Restrictions.sqlRestriction("this_.id in (select id from  t_b_risk_identification  where  identifi_date <= '"+queryYear+"-12-31'  and (exp_date >= '"+queryYear+"-01-01' or exp_date is null))"));
            }else{
                cq.add(Restrictions.sqlRestriction("this_.id in (select id from  t_b_risk_identification  where  identifi_date <= now()  and (exp_date >= now() or exp_date is null))"));
            }
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

    @RequestMapping(params = "datagrid")
    public void datagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        boolean isAdmin = false;
        CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
        try{
            cqru.eq("TSUser.id",user.getId());
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
        String identificationType = request.getParameter("identificationType");
        String address = request.getParameter("addressId");
        String postId = request.getParameter("post.id");
        String load = request.getParameter("load");
        String id = request.getParameter("id");
        if(StringUtils.isNotBlank(address)) {
            CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
            try {
                cq.eq("identificationType", identificationType);
                cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
                if(StringUtil.isNotEmpty(load)){
                    cq.eq("id",id);
                    if (!isAdmin&&!load.equals("detail")) {
                        cq.eq("createBy", user.getUserName());
                    }
                }else if(!isAdmin){ //管理员可以查看所有的
                    cq.eq("createBy", user.getUserName());
                }
                cq.eq("address.id", address);
                String riskTaskId= request.getParameter("riskTaskId");
                if(StringUtil.isNotEmpty(riskTaskId)){
                    cq.eq("riskTaskId",riskTaskId);
                    cq.eq("createBy", user.getUserName());
                }
                String riskManageTaskId= request.getParameter("riskManageTaskId");
                if(StringUtil.isNotEmpty(riskManageTaskId)){
                    cq.eq("riskManageTaskId",riskManageTaskId);
                    cq.eq("createBy", user.getUserName());
                }
                String riskManageTaskAllId= request.getParameter("riskManageTaskAllId");
                if(StringUtil.isNotEmpty(riskManageTaskAllId)){
                    RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,riskManageTaskAllId);
                    String manageType = riskManageTaskAllEntity.getManageType();
                    if(manageType.equals("post")){
                        cq.add(Restrictions.sqlRestriction("this_.id in (SELECT id from t_b_risk_identification WHERE risk_manage_task_id in (SELECT id from t_b_risk_manage_post_task WHERE post_task_all_id ='"+riskManageTaskAllId+"'))"));
                    }else{
                        cq.add(Restrictions.sqlRestriction("this_.id in (SELECT id from t_b_risk_identification WHERE risk_manage_task_id in (SELECT id from t_b_risk_manage_task WHERE task_all_id ='"+riskManageTaskAllId+"'))"));
                    }
                    cq.eq("createBy", user.getUserName());
                }
                String riskManageTaskAllIdYH= request.getParameter("riskManageTaskAllIdYH");
                if(StringUtil.isNotEmpty(riskManageTaskAllIdYH)){
                    cq.eq("riskManageTaskAllId",riskManageTaskAllIdYH);
                    cq.eq("createBy", user.getUserName());
                }
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
        }else if(StringUtils.isNotBlank(postId)){
            CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
            try {
                if(StringUtil.isNotEmpty(id)){
                    cq.eq("id",id);
                }
                cq.eq("identificationType", identificationType);
                cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
                if (!isAdmin) { //管理员可以查看所有的
                    cq.eq("createBy", user.getUserName());
                }
                cq.eq("post.id",postId);
                String riskTaskId= request.getParameter("riskTaskId");
                if(StringUtil.isNotEmpty(riskTaskId)){
                    cq.eq("riskTaskId",riskTaskId);
                    cq.eq("createBy", user.getUserName());
                }
                String riskManageTaskId= request.getParameter("riskManageTaskId");
                if(StringUtil.isNotEmpty(riskManageTaskId)){
                    cq.eq("riskManageTaskId",riskManageTaskId);
                    cq.eq("createBy", user.getUserName());
                }
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
        }else{
            List<RiskIdentificationEntity> list = new ArrayList<RiskIdentificationEntity>();
            dataGrid.setResults(list);
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(RiskIdentificationEntity riskIdentificationEntity , HttpServletRequest request) {
        String identificationType = request.getParameter("identificationType");
        String address = request.getParameter("addressId");
        String postId = request.getParameter("postId");
        String load = request.getParameter("load");
        request.setAttribute("addressId",address);
        request.setAttribute("postId",postId);
        request.setAttribute("identificationType",identificationType);
        request.setAttribute("load",load);
        String id = request.getParameter("id");
        request.setAttribute("id",id);
        if(StringUtil.isNotEmpty(riskIdentificationEntity.getId())) {
            riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class, riskIdentificationEntity.getId());
            request.setAttribute("riskIdentificationPage", riskIdentificationEntity);
        }

        //
        List<TSType> types = ResourceUtil.allTypes.get("risk_type");
        request.setAttribute("riskTypes",types);

        String userId = request.getParameter("userId");
        request.setAttribute("userId",userId);
        String riskType = request.getParameter("riskType");
        request.setAttribute("riskType",riskType);
        String riskManageHazardFactorId = request.getParameter("riskManageHazardFactorId");
        request.setAttribute("riskManageHazardFactorId",riskManageHazardFactorId);//管控危害因素id
        String riskManagePostHazardFactorId = request.getParameter("riskManagePostHazardFactorId");
        request.setAttribute("riskManagePostHazardFactorId",riskManagePostHazardFactorId);//管控危害因素id

        //辨识任务Id
        String riskTaskId=request.getParameter("riskTaskId");
        request.setAttribute("riskTaskId",riskTaskId);

        String riskManageTaskId=request.getParameter("riskManageTaskId");
        request.setAttribute("riskManageTaskId",riskManageTaskId);

        String riskManageTaskAllId=request.getParameter("riskManageTaskAllId");
        request.setAttribute("riskManageTaskAllId",riskManageTaskAllId);

        String riskManageTaskAllIdYH=request.getParameter("riskManageTaskAllIdYH");
        request.setAttribute("riskManageTaskAllIdYH",riskManageTaskAllIdYH);
        if(StringUtil.isNotEmpty(riskManageTaskAllIdYH)){
            identificationType = "6";
            request.setAttribute("identificationType",identificationType);
        }


        // 专项风险获取辨识类型及专项辨识名称
        if ("3".equals(identificationType)) {
            String taskSql = "select spe_ide_type, task_name from t_b_risk_task where id=?";
            Map<String, Object> taskInfo = systemService.findOneForJdbc(taskSql, riskTaskId);
            if (taskInfo !=null && taskInfo.get("spe_ide_type") != null) {
                request.setAttribute("speIdeTypeName", DicUtil.getTypeNameByCode("specificType", taskInfo.get("spe_ide_type").toString()));
                request.setAttribute("taskName",taskInfo.get("task_name"));
            }

        }

        return new ModelAndView("com/sddb/buss/web/identification/riskIdentification-add");
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(RiskIdentificationEntity riskIdentificationEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "年度风险辨识添加成功";
        try{
            systemService.save(riskIdentificationEntity);
        }catch(Exception e){
            e.printStackTrace();
            message = "年度风险辨识添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(RiskIdentificationEntity riskIdentificationEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskIdentificationEntity.getId())) {
            riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class, riskIdentificationEntity.getId());
            req.setAttribute("riskIdentificationPage", riskIdentificationEntity);
        }
        return new ModelAndView("com/sddb/buss/web/identification/riskIdentification-update");
    }

    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(RiskIdentificationEntity riskIdentificationEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskIdentificationEntity.getId())) {
            riskIdentificationEntity = systemService.getEntity( RiskIdentificationEntity.class, riskIdentificationEntity.getId());
            req.setAttribute("riskIdentificationPage", riskIdentificationEntity);
        }
        return new ModelAndView("com/sddb/buss/web/identification/riskIdentification-detail");
    }

    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "删除成功";
        try{
            for(String id:ids.split(",")){
                RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class, id);
                riskIdentificationEntity.setIsDel(Constants.RISK_IS_DEL_TRUE);
                riskIdentificationEntity.setStateFlag(com.sdzk.buss.web.common.Constants.GJJ_STATE_FLAG_3);
                //systemService.executeSql("delete from t_b_risk_identifi_user_rel where hazard_factors_id = '"+id+"'") ;
                //systemService.executeSql("delete from t_b_risk_factors_rel where risk_identification_id = '"+id+"'") ;
                systemService.executeSql("delete from t_b_risk_manage_rel_risk where risk_id = '"+id+"'") ;
                systemService.executeSql("DELETE FROM t_b_risk_manage WHERE risk_id = '"+id+"'") ;
                //systemService.executeSql("DELETE FROM t_b_risk_manage_task where risk_id = '"+id+"'") ;
                //systemService.executeSql("DELETE FROM t_b_risk_manage_hazard_factor where risk_id = '"+id+"'") ;
                systemService.executeSql("delete from t_b_risk_manage_post_rel_risk where risk_id = '"+id+"'") ;
                //systemService.delete(riskIdentificationEntity);
                systemService.saveOrUpdate(riskIdentificationEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "saveRiskTemp")
    @ResponseBody
    public AjaxJson saveRiskTemp(HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        String address = request.getParameter("address");
        String identificationType = request.getParameter("identificationType");
        String identifiManId = request.getParameter("identifiManId");
        String riskType = request.getParameter("riskType");
        String postId = request.getParameter("postId");
        String specificType = request.getParameter("specificType");
        String specificName = request.getParameter("specificName");
        String riskManageHazardFactorId = request.getParameter("riskManageHazardFactorId");
        String riskManagePostHazardFactorId = request.getParameter("riskManagePostHazardFactorId");
        try{
            RiskIdentificationEntity riskIdentificationEntity = new RiskIdentificationEntity();
            if(StringUtils.isNotBlank(address)){
                TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,address);
                riskIdentificationEntity.setAddress(addressInfoEntity);
            }
            riskIdentificationEntity.setIdentificationType(identificationType);
            riskIdentificationEntity.setRiskType(riskType);

            riskIdentificationEntity.setStatus(Constants.RISK_IDENTIFI_STATUS_DRAFT);
            if(StringUtil.isNotEmpty(specificName)){
                riskIdentificationEntity.setSpecificName(specificName);
            }
            if(StringUtil.isNotEmpty(specificType)){
                riskIdentificationEntity.setSpecificType(specificType);
            }
            if(StringUtils.isNotBlank(postId)){
                TBPostManageEntity postManageEntity = systemService.getEntity(TBPostManageEntity.class,postId);
                riskIdentificationEntity.setPost(postManageEntity);
            }
            riskIdentificationEntity.setRiskManageHazardFactorId(riskManageHazardFactorId);
            riskIdentificationEntity.setRiskManagePostHazardFactorId(riskManagePostHazardFactorId);
            riskIdentificationEntity.setIsDel(Constants.RISK_IS_DEL_FALSE);
            //辨识任务
            String riskTaskId= request.getParameter("riskTaskId");
            if(StringUtil.isNotEmpty(riskTaskId)){
                riskIdentificationEntity.setRiskTaskId(riskTaskId);
            }
            String riskManageTaskId= request.getParameter("riskManageTaskId");
            if(StringUtil.isNotEmpty(riskManageTaskId)){
                riskIdentificationEntity.setRiskManageTaskId(riskManageTaskId);
            }
            String riskManageTaskAllIdYH= request.getParameter("riskManageTaskAllIdYH");
            if(StringUtil.isNotEmpty(riskManageTaskAllIdYH)){
                riskIdentificationEntity.setRiskManageTaskAllId(riskManageTaskAllIdYH);
            }

            //添加国家局上报标识
            riskIdentificationEntity.setStateFlag(com.sdzk.buss.web.common.Constants.GJJ_STATE_FLAG_1);
            systemService.save(riskIdentificationEntity);
            if(StringUtils.isNotBlank(identifiManId)){
                for(String userId : identifiManId.split(",")){
                    TSUser user = systemService.getEntity(TSUser.class,userId);
                    RiskIdentifiUserRel riskIdentifiUserRel = new RiskIdentifiUserRel();
                    riskIdentifiUserRel.setUser(user);
                    riskIdentifiUserRel.setRiskIdentificationEntity(riskIdentificationEntity);
                    systemService.save(riskIdentifiUserRel);
                }
            }

            if(StringUtil.isNotEmpty(riskManageHazardFactorId)){
                RiskManageRelRisk riskManageRelRisk = new RiskManageRelRisk();
                RiskManageHazardFactorEntity riskManageHazardFactorEntity = systemService.getEntity(RiskManageHazardFactorEntity.class, riskManageHazardFactorId);
                riskManageRelRisk.setRiskManageHazardFactor(riskManageHazardFactorEntity);
                riskManageRelRisk.setRisk(riskIdentificationEntity);
                systemService.save(riskManageRelRisk);
            }

            if(StringUtil.isNotEmpty(riskManagePostHazardFactorId)){
                RiskManagePostRelRisk riskManagePostRelRisk = new RiskManagePostRelRisk();
                RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity = systemService.getEntity(RiskManagePostHazardFactorEntity.class, riskManagePostHazardFactorId);
                riskManagePostRelRisk.setRiskManagePostHazardFactor(riskManagePostHazardFactorEntity);
                riskManagePostRelRisk.setRisk(riskIdentificationEntity);
                systemService.save(riskManagePostRelRisk);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        return j;
    }


    @RequestMapping(params = "toEditDetailInfo")
    public ModelAndView toEditDetailInfo(HttpServletRequest request) {
        String riskId = request.getParameter("riskId");
        String load = request.getParameter("load");
        request.setAttribute("load",load);
        request.setAttribute("riskId",riskId);
        if(StringUtils.isNotBlank(riskId)){
            RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class,riskId);
            if (riskIdentificationEntity == null) {
                riskIdentificationEntity = new RiskIdentificationEntity();
            }
            if (riskIdentificationEntity.getIdentifiDate()==null) {
                request.setAttribute("ideDate",new Date());
            } else {
                request.setAttribute("ideDate",riskIdentificationEntity.getIdentifiDate());
            }
            if(riskIdentificationEntity!=null){
                request.setAttribute("riskTypeTemp",riskIdentificationEntity.getRiskType());
            }
            request.setAttribute("riskIdentificationEntity",riskIdentificationEntity);
        }

        /*获取riskId对应的风险类型-开始*/
        StringBuffer hasQuery = new StringBuffer();
        hasQuery.append("SELECT risk_type FROM t_b_hazard_module WHERE risk_type = (SELECT risk_type FROM t_b_risk_identification WHERE id = '").append(riskId).append("') ");
        List<Map<String,Object>> hasQueryList = this.systemService.findForJdbc(hasQuery.toString());
        if(hasQueryList != null && hasQueryList.size()>0){
            request.setAttribute("riskType",String.valueOf(hasQueryList.get(0).get("risk_type")));
        }else{
            request.setAttribute("riskType","no");
        }
        /*获取riskId对应的风险类型-结束*/

        /*获取风险类型字典-开始*/
        request.setAttribute("dic",dicLevel());
        /*获取风险类型字典-结束*/
        /***判断是否已关联危害因素-开始**/
        String hazardSql = "select count(1) from t_b_risk_factors_rel where  risk_identification_id= ?";
        Long hazardCnt = systemService.getCountForJdbcParam(hazardSql, new String[]{riskId});
        if (hazardCnt > 0) {
            request.setAttribute("relStatus", "1");
        } else {
            request.setAttribute("relStatus", "0");
        }
        /***判断是否已关联危害因素-结束**/

        return new ModelAndView("com/sddb/buss/web/identification/riskIdentification-editInfo");
    }
    private String dicLevel() {
        //获取风险等级字典值用于编辑-开始
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT typecode,typename FROM t_s_type type LEFT JOIN t_s_typegroup typegroup ON type.typegroupid = typegroup.ID WHERE typegroup.typegroupcode='factors_level'");
        List<Map<String,Object>> queryList = this.systemService.findForJdbc(sb.toString());
        StringBuffer sbDic = new StringBuffer();
        if (queryList != null && queryList.size()>0) {
            for (Map<String,Object> map : queryList) {
                if (StringUtils.isNoneBlank(sbDic.toString())) {
                    sbDic.append(",");
                }
                sbDic.append("{'typecode':'").append(String.valueOf(map.get("typecode"))).append("','typename':'").append(String.valueOf(map.get("typename"))).append("'}");
            }
        }
        return sbDic.toString();
    }

    @RequestMapping(params = "majorTree")
    @ResponseBody
    public List<ComboTree> formTree(HttpServletRequest request, final ComboTree rootComboTree) {

        rootComboTree.setId("all");
        rootComboTree.setText("专业");
        List<ComboTree> comboTrees = new ArrayList<ComboTree>();
        List<TSType> typeList = ResourceUtil.allTypes.get("major".toLowerCase());
        if (typeList == null || typeList.size() == 0) {
            return new ArrayList<ComboTree>(){{add(rootComboTree);}};
        }
        try {
            for(TSType tsType : typeList){
                ComboTree combotree = new ComboTree();
                combotree.setId(tsType.getTypecode());
                combotree.setText(tsType.getTypename());
                comboTrees.add(combotree);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        rootComboTree.setChildren(comboTrees);
        return new ArrayList<ComboTree>(){{add(rootComboTree);}};
    }

    @RequestMapping(params = "majorFactList")
    public void majorFactList( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        String modular = request.getParameter("modular");
        String riskType= request.getParameter("riskType");
        String majors = request.getParameter("majors");
        String riskId = request.getParameter("riskId");
        String riskLevel = request.getParameter("riskLevelTemp");
        String postName = request.getParameter("postName");
        String relStatus = request.getParameter("relStatus");
        String keyword = request.getParameter("keyword");
        TSUser user = ResourceUtil.getSessionUserName();
//        String[] majorArray = new String[ majors.split(",").length];
//        int index = 0;
//        for(String major : majors.split(",")){
//            majorArray[index] = major;
//            index++;
//        }
        CriteriaQuery cq = new CriteriaQuery(HazardFactorsEntity.class, dataGrid);
        if(StringUtil.isNotEmpty(riskType)){
            cq.eq("riskType",riskType);
        }
        if(StringUtil.isNotEmpty(postName)){
            cq.like("postName","%"+postName+"%");
        }
        if(StringUtil.isNotEmpty(relStatus)){
            if(!relStatus.equals("1")){
                if(StringUtil.isNotEmpty(riskLevel)){
                    cq.eq("riskLevel",riskLevel);
                }
            }
        }
        if(StringUtil.isNotEmpty(keyword)){
            cq.or(Restrictions.like("hazardFactors","%"+keyword+"%"),Restrictions.like("manageMeasure","%"+keyword+"%"));
        }
        try {
            if(StringUtils.isNotBlank(majors)) {
                cq.in("major", majors.split(","));
            }
            if(StringUtil.isNotEmpty(relStatus)){
                if(relStatus.equals("1")&&StringUtil.isNotEmpty(riskLevel)){
                    cq.add(Restrictions.sqlRestriction(" this_.id  in (select hazard_factors_id from t_b_risk_factors_rel where  risk_identification_id= '"+riskId+"' and hfLevel = '"+riskLevel+"')"));
                }else if(relStatus.equals("1")){
                    cq.add(Restrictions.sqlRestriction(" this_.id  in (select hazard_factors_id from t_b_risk_factors_rel where  risk_identification_id= '"+riskId+"')"));
                }else{
                    cq.add(Restrictions.sqlRestriction(" this_.id not in (select hazard_factors_id from t_b_risk_factors_rel where  risk_identification_id= '"+riskId+"')"));
                }
            }else {
                cq.add(Restrictions.sqlRestriction(" this_.id not in (select hazard_factors_id from t_b_risk_factors_rel where  risk_identification_id= '"+riskId+"')"));
            }
            cq.add(Restrictions.sqlRestriction("( this_.status ='3' and this_.is_del != '1' or ( this_.create_By = '"+user.getUserName()+"' and this_.is_del !='1' ) )"));
            if(StringUtil.isNotEmpty(modular)){
                cq.add(Restrictions.sqlRestriction(" this_.id  in (SELECT hazard_id FROM t_b_hazard_module_rel WHERE modular_id = '"+modular+"')"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);

        if(StringUtil.isNotEmpty(relStatus)){
            if(relStatus.equals("1")){
                String levelSql = "SELECT hazard_factors_id,hfLevel FROM t_b_risk_factors_rel where  risk_identification_id= '"+riskId+"'";
                List<Map<String, Object>> hazardFactorLevel = systemService.findForJdbc(levelSql);
                Map<String, String> hazardFactorLevelMap = new HashMap<>();
                if (hazardFactorLevel !=null && hazardFactorLevel.size()>0) {
                    for (Map<String, Object> obj : hazardFactorLevel) {
                        String hfLevel = String.valueOf(obj.get("hfLevel"));
                        String id = String.valueOf(obj.get("hazard_factors_id"));
                        hazardFactorLevelMap.put(id, hfLevel);
                    }
                }
                String manageMeasureSql = "SELECT hazard_factors_id,hfManageMeasure FROM t_b_risk_factors_rel where  risk_identification_id= '"+riskId+"'";
                List<Map<String, Object>> hazardFactorManageMeasure = systemService.findForJdbc(manageMeasureSql);
                Map<String, String> hazardFactorManageMeasureMap = new HashMap<>();
                if (hazardFactorManageMeasure !=null && hazardFactorManageMeasure.size()>0) {
                    for (Map<String, Object> obj : hazardFactorManageMeasure) {
                        String hfManageMeasure = String.valueOf(obj.get("hfManageMeasure"));
                        String id = String.valueOf(obj.get("hazard_factors_id"));
                        hazardFactorManageMeasureMap.put(id, hfManageMeasure);
                    }
                }
                if(dataGrid != null && dataGrid.getResults() != null){
                    List<HazardFactorsEntity> hazardFactorsEntityList = dataGrid.getResults();
                    if(null!=hazardFactorsEntityList && hazardFactorsEntityList.size()>0){
                        for(int i=0;i<hazardFactorsEntityList.size();i++){
                            HazardFactorsEntity u =hazardFactorsEntityList.get(i);
                            u.setRiskLevelTemp(hazardFactorLevelMap.get(u.getId()));
                            u.setManageMeasureTemp(hazardFactorManageMeasureMap.get(u.getId()));
                        }
                    }
                }
            }else{
                if(dataGrid != null && dataGrid.getResults() != null){
                    List<HazardFactorsEntity> hazardFactorsEntityList = dataGrid.getResults();
                    if(null!=hazardFactorsEntityList && hazardFactorsEntityList.size()>0){
                        for(int i=0;i<hazardFactorsEntityList.size();i++){
                            HazardFactorsEntity u =hazardFactorsEntityList.get(i);
                            u.setRiskLevelTemp(u.getRiskLevel());
                            u.setManageMeasureTemp(u.getManageMeasure());
                        }
                    }
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "updateRiskTemp")
    @ResponseBody
    public AjaxJson updateRiskTemp(RiskIdentificationEntity riskIdentificationEntity,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "保存成功";
        RiskIdentificationEntity t = systemService.get(RiskIdentificationEntity.class, riskIdentificationEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(riskIdentificationEntity, t);
            t.setStatus(Constants.RISK_IDENTIFI_STATUS_DRAFT);
            t.setStateFlag(com.sdzk.buss.web.common.Constants.GJJ_STATE_FLAG_2);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "保存失败";
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "saveRekFactors")
    @ResponseBody
    public AjaxJson saveRekFactors(HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        String riskId = request.getParameter("riskId");
        String factortsIds = request.getParameter("ids");
        message = "关联成功";
        RiskIdentificationEntity t = systemService.get(RiskIdentificationEntity.class, riskId);
        try {
            for(String id : factortsIds.split(",")){
                HazardFactorsEntity hazardFactorsEntity = systemService.getEntity(HazardFactorsEntity.class,id);
                if(hazardFactorsEntity.getStatus().equals("0")){
                    hazardFactorsEntity.setStatus("1");
                    systemService.saveOrUpdate(hazardFactorsEntity);
                }
                RiskFactortsRel rel = new RiskFactortsRel();
                rel.setHazardFactorsEntity(hazardFactorsEntity);
                rel.setRiskIdentificationEntity(t);
                rel.setHfLevel(hazardFactorsEntity.getRiskLevel());
                rel.setHfManageMeasure(hazardFactorsEntity.getManageMeasure());
                systemService.save(rel);

                //保存上报国家局风险措施于风险关联表  审批后上传国家局
                sfService.saveOrUpdateSfRiskMeasureEntity(t.getId(),t.getAddress().getId(),"", hazardFactorsEntity.getId(), hazardFactorsEntity.getManageMeasure(), hazardFactorsEntity.getPostName(),"1");


                t.setStatus(Constants.RISK_IDENTIFI_STATUS_DRAFT);
                t.setStateFlag(com.sdzk.buss.web.common.Constants.GJJ_STATE_FLAG_2);
                systemService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "关联失败";
        }
        j.setMsg(message);
        return j;
    }
    /*辨识界面修改危害因素等级并添加关联*/
    @RequestMapping(params = "saveModifyLevel")
    @ResponseBody
    public AjaxJson saveModifyLevel(TBHarmFactorLevelEditPage page, HttpServletRequest request) {
        List<TBHarmFactorLevelEdit> issues = page.getIssues();
        AjaxJson j = new AjaxJson();
        message = "关联成功";
        if (CollectionUtils.isNotEmpty(issues)) {
            String riskId = request.getParameter("riskId");
            RiskIdentificationEntity t = systemService.get(RiskIdentificationEntity.class, riskId);
            try {
                for (TBHarmFactorLevelEdit assessIssue : issues) {
                    if (StringUtil.isNotEmpty(assessIssue.getId())) {
                        HazardFactorsEntity hazardFactorsEntity = systemService.getEntity(HazardFactorsEntity.class,assessIssue.getId());
                        if(hazardFactorsEntity.getStatus().equals("0")){
                            hazardFactorsEntity.setStatus("1");
                            systemService.saveOrUpdate(hazardFactorsEntity);
                        }
                        RiskFactortsRel rel = new RiskFactortsRel();
                        rel.setHazardFactorsEntity(hazardFactorsEntity);
                        rel.setRiskIdentificationEntity(t);
                        rel.setHfLevel(assessIssue.getRiskLevelTemp());
                        rel.setHfManageMeasure(assessIssue.getManageMeasureTemp());
                        systemService.save(rel);
                        t.setStatus(Constants.RISK_IDENTIFI_STATUS_DRAFT);
                        systemService.saveOrUpdate(t);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = "关联失败";
                throw new BusinessException(e.getMessage());
            }
        }
        j.setMsg(message);
        return j;
    }
    @RequestMapping(params = "wxysList")
    public ModelAndView wxysList(HttpServletRequest request) {
        String riskId = request.getParameter("id");
        String load = request.getParameter("load");
        //风险降级
        String isAdmin = request.getParameter("isAdmin");
        if(StringUtil.isNotEmpty(isAdmin)){
            if(isAdmin.equals("true")){
                TSUser tsUser = ResourceUtil.getSessionUserName();
                boolean isAdminTemp = false;
                CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
                try{
                    cqru.eq("TSUser.id",tsUser.getId());
                }catch(Exception e){
                    e.printStackTrace();
                }
                cqru.add();
                List<TSRoleUser> roleList = systemService.getListByCriteriaQuery(cqru,false);
                if(roleList != null && !roleList.isEmpty()){
                    for(TSRoleUser ru : roleList){
                        TSRole role = ru.getTSRole();
                        if(role != null && role.getRoleName().equals("管理员")){
                            isAdminTemp = true;
                            break;
                        }
                    }
                }
                if(isAdminTemp){
                    request.setAttribute("isAdmin",isAdmin);
                }
            }
        }
        request.setAttribute("load",load);
        request.setAttribute("riskId",riskId);
        String riskManageId = request.getParameter("riskManageId");
        if(StringUtil.isNotEmpty(riskManageId)){
            RiskManageEntity riskManageEntity = systemService.getEntity(RiskManageEntity.class,riskManageId);
            request.setAttribute("riskId",riskManageEntity.getRisk().getId());
        }
        return new ModelAndView("com/sddb/buss/web/identification/wxysList");
    }

    @RequestMapping(params = "wxysImplList")
    public ModelAndView wxysImplList(HttpServletRequest request) {
        String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
        request.setAttribute("riskManageTaskAllId",riskManageTaskAllId);
        String riskManageTaskAllManageId = request.getParameter("riskManageTaskAllManageId");
        request.setAttribute("riskManageTaskAllManageId",riskManageTaskAllManageId);
        return new ModelAndView("com/sddb/buss/web/identification/wxysImplList");
    }


    @RequestMapping(params = "fxList")
    public ModelAndView fxList(HttpServletRequest request) {
        String addressId = request.getParameter("addressId");
        if(StringUtil.isNotEmpty(addressId)){
            request.setAttribute("title","关联风险");
        }
        String expDate = request.getParameter("expDate");
        request.setAttribute("expDate",expDate);
        String load = request.getParameter("load");
        request.setAttribute("load",load);
        request.setAttribute("addressId",addressId);
        String riskTaskParticipantRelId = request.getParameter("riskTaskParticipantRelId");
        String status = request.getParameter("status");
        request.setAttribute("riskTaskParticipantRelId",riskTaskParticipantRelId);
        request.setAttribute("status",status);
        String yearMonth = request.getParameter("yearMonth");
        request.setAttribute("yearMonth",yearMonth);
        String riskStatisticsType = request.getParameter("riskStatisticsType");
        request.setAttribute("riskStatisticsType",riskStatisticsType);
        String riskStatisticsTypeId = request.getParameter("riskStatisticsTypeId");
        request.setAttribute("riskStatisticsTypeId",riskStatisticsTypeId);
        String riskManageTaskAllManageId = request.getParameter("riskManageTaskAllManageId");
        request.setAttribute("riskManageTaskAllManageId",riskManageTaskAllManageId);
        String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
        request.setAttribute("riskManageTaskAllId",riskManageTaskAllId);
        String riskManageTaskAllGRId = request.getParameter("riskManageTaskAllGRId");
        request.setAttribute("riskManageTaskAllGRId",riskManageTaskAllGRId);
        return new ModelAndView("com/sddb/buss/web/identification/fxList");
    }


    @RequestMapping(params = "fxPostList")
    public ModelAndView fxPostList(HttpServletRequest request) {
        String riskStatisticsTypeId = request.getParameter("riskStatisticsTypeId");
        request.setAttribute("riskStatisticsTypeId",riskStatisticsTypeId);
        return new ModelAndView("com/sddb/buss/web/identification/fxPostList");
    }

    @RequestMapping(params = "fxPostDatagrid")
    public void fxPostDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationPostEntity.class, dataGrid);
        try {
            //辨识任务
            String riskTaskParticipantRelId = request.getParameter("riskTaskParticipantRelId");
            if(StringUtil.isNotEmpty(riskTaskParticipantRelId)){
                RiskTaskParticipantRelEntity riskTaskParticipantRelEntity = systemService.getEntity(RiskTaskParticipantRelEntity.class,riskTaskParticipantRelId);
                if(riskTaskParticipantRelEntity!=null){
                    cq.eq("riskTaskId",riskTaskParticipantRelEntity.getRiskTaskId());
                    if(riskTaskParticipantRelEntity.getParticipantManId()!=null){
                        cq.eq("createBy",riskTaskParticipantRelEntity.getParticipantManId().getUserName());
                    }
                }
            }
            cq.eq("isDel","0" +
                    "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskIdentificationPostEntity> list = dataGrid.getResults();
            for (RiskIdentificationPostEntity bean : list) {
                List<RiskFactortsPostRel> relList = bean.getPostRelList();
                if (relList == null) {
                    bean.setHazardFactortsPostNum("0");
                }
                bean.setHazardFactortsPostNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "whysListDatagrid")
    public void whysListDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String riskId = request.getParameter("riskId");
        CriteriaQuery cq = new CriteriaQuery(RiskFactortsRel.class, dataGrid);
        try {
            cq.eq("riskIdentificationEntity.id",riskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "whysImplListDatagrid")
    public void hazardFactorDatagrid(RiskManageHazardFactorEntity riskManageHazardFactorEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskManageHazardFactorEntity.class, dataGrid);
        try{
            cq.createAlias("hazardFactor","hazardFactor");
        }catch(Exception e){
            e.printStackTrace();
        }
        String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
        if(StringUtil.isNotEmpty(riskManageTaskAllId)){
            cq.add(Restrictions.sqlRestriction("this_.id in (SELECT id from t_b_risk_manage_hazard_factor WHERE impl_detail != '' and impl_detail is not null and risk_manage_task_id in (SELECT id from t_b_risk_manage_task WHERE task_all_id = '"+riskManageTaskAllId+"'))"));
        }
        String riskManageTaskAllManageId = request.getParameter("riskManageTaskAllManageId");
        if(StringUtil.isNotEmpty(riskManageTaskAllManageId)){
            cq.add(Restrictions.sqlRestriction("this_.id in (SELECT id FROM t_b_risk_manage_hazard_factor WHERE impl_detail != '' AND impl_detail IS NOT NULL AND risk_manage_task_id IN ( SELECT id FROM t_b_risk_manage_task WHERE task_all_id IN ( SELECT id FROM t_b_risk_manage_task_all WHERE task_all_manage_id = '"+riskManageTaskAllManageId+"' )))"));
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        String sql = "SELECT risk_identification_id,hazard_factors_id,hfManageMeasure,hfLevel from t_b_risk_factors_rel";
        List<Map<String, Object>> list = systemService.findForJdbc(sql);
        Map<String, String> map = new HashMap<>();
        if (list !=null && list.size()>0) {
            for (Map<String, Object> obj : list) {
                String riskId = String.valueOf(obj.get("risk_identification_id"));
                String hazardId = String.valueOf(obj.get("hazard_factors_id"));
                String hfManageMeasure = String.valueOf(obj.get("hfManageMeasure"));
                map.put(riskId+hazardId, hfManageMeasure);
            }
        }
        Map<String, String> levelMap = new HashMap<>();
        if (list !=null && list.size()>0) {
            for (Map<String, Object> obj : list) {
                String riskId = String.valueOf(obj.get("risk_identification_id"));
                String hazardId = String.valueOf(obj.get("hazard_factors_id"));
                String hfLevel = String.valueOf(obj.get("hfLevel"));
                levelMap.put(riskId+hazardId, hfLevel);
            }
        }
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0) {
            List<RiskManageHazardFactorEntity> tempList = dataGrid.getResults();
            for(RiskManageHazardFactorEntity entity : tempList){
                entity.setHfManageMeasure(map.get(entity.getRisk().getId()+entity.getHazardFactor().getId()));
                entity.setHfLevel(levelMap.get(entity.getRisk().getId()+entity.getHazardFactor().getId()));
            }
        }

        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "queryListByAddressDatagrid")
    public void queryListByAddressDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String addressId = request.getParameter("addressId");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
        try {
            if(StringUtil.isNotEmpty(addressId)){
                cq.eq("address.id",addressId);
            }
            cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
            //辨识任务
            String riskTaskParticipantRelId = request.getParameter("riskTaskParticipantRelId");
            String status = request.getParameter("status");
            if(StringUtil.isNotEmpty(riskTaskParticipantRelId)){
                RiskTaskParticipantRelEntity riskTaskParticipantRelEntity = systemService.getEntity(RiskTaskParticipantRelEntity.class,riskTaskParticipantRelId);
                if(riskTaskParticipantRelEntity!=null){
                    cq.eq("riskTaskId",riskTaskParticipantRelEntity.getRiskTaskId());
                    if(riskTaskParticipantRelEntity.getParticipantManId()!=null){
                        cq.eq("createBy",riskTaskParticipantRelEntity.getParticipantManId().getUserName());
                    }
                }
            }
            //任务记录
            String riskManageTaskAllManageId = request.getParameter("riskManageTaskAllManageId");
            if(StringUtil.isNotEmpty(riskManageTaskAllManageId)){
                cq.add(Restrictions.sqlRestriction("this_.id in (SELECT ri.id FROM t_b_risk_manage_task_all_manage tam LEFT JOIN t_b_risk_manage_task_all ta ON tam.id = ta.task_all_manage_id LEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id LEFT JOIN t_b_risk_identification ri on ri.risk_manage_task_id = t.id WHERE tam.id = '"+riskManageTaskAllManageId+"' and ri.is_del = '0' and ri.status != '0')"));
            }
            String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
            if(StringUtil.isNotEmpty(riskManageTaskAllId)){
                cq.add(Restrictions.sqlRestriction("this_.id in (SELECT id from t_b_risk_identification WHERE is_del = '0' and status != '0' and   risk_manage_task_id in (SELECT id from t_b_risk_manage_task WHERE task_all_id ='"+riskManageTaskAllId+"'))"));
            }
            String riskManageTaskAllGRId = request.getParameter("riskManageTaskAllGRId");
            if(StringUtil.isNotEmpty(riskManageTaskAllGRId)){
                RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.get(RiskManageTaskAllEntity.class,riskManageTaskAllGRId);
                if(riskManageTaskAllEntity!=null){
                    String manage = riskManageTaskAllEntity.getManageType();
                    if(StringUtil.isNotEmpty(manage)){
                        if(manage.equals("comprehensive")||manage.equals("profession")||manage.equals("team")||manage.equals("group")){
                            String riskCountSql = "SELECT\n" +
                                    "\tri.id id\n" +
                                    "FROM\n" +
                                    "\tt_b_risk_manage_task_all ta\n" +
                                    "LEFT JOIN t_b_risk_manage_task t ON ta.id = t.task_all_id\n" +
                                    "LEFT JOIN t_b_risk_identification ri ON ri.risk_manage_task_id = t.id\n" +
                                    "WHERE\n" +
                                    "\tri.is_del = '0' and ri.status != '0'\n" +
                                    "\t and ta.id = '"+riskManageTaskAllGRId+"'";
                            cq.add(Restrictions.sqlRestriction("this_.id in ("+riskCountSql+")"));
                        }else if(manage.equals("post")){
                            String riskPostCountSql = "SELECT\n" +
                                    "\tri.id id\n" +
                                    "FROM\n" +
                                    "\tt_b_risk_manage_task_all ta\n" +
                                    "LEFT JOIN t_b_risk_manage_post_task t ON ta.id = t.post_task_all_id\n" +
                                    "LEFT JOIN t_b_risk_identification ri ON ri.risk_manage_task_id = t.id\n" +
                                    "WHERE\n" +
                                    "\tri.is_del = '0' and  ri.status != '0'\n" +
                                    "\t and  ta.id = '"+riskManageTaskAllGRId+"'";
                            cq.add(Restrictions.sqlRestriction("this_.id in ("+riskPostCountSql+")"));
                        }else{
                            String riskYHCountSql = "SELECT  id from t_b_risk_identification WHERE is_del = '0' and status != '0' and risk_manage_task_all_id = '"+riskManageTaskAllGRId+"'";
                            cq.add(Restrictions.sqlRestriction("this_.id in ("+riskYHCountSql+")"));
                        }
                    }
                }
            }
            if(StringUtil.isNotEmpty(status)){
                cq.eq("status",status);
            }else if(StringUtil.isNotEmpty(riskManageTaskAllId)||StringUtil.isNotEmpty(riskManageTaskAllManageId)||StringUtil.isNotEmpty(riskManageTaskAllGRId)){
                //记录不做默认完成处理
            }else{
                cq.eq("status",Constants.RISK_IDENTIFI_STATUS_REVIEW);
            }
            String riskStatisticsType = request.getParameter("riskStatisticsType");
            if(StringUtil.isNotEmpty(riskStatisticsType)){
                if(riskStatisticsType.equals("1")){
                    cq.eq("address.id",request.getParameter("riskStatisticsTypeId"));
                }else{
                    cq.eq("riskType",request.getParameter("riskStatisticsTypeId"));
                }

                String yearMonth = request.getParameter("yearMonth");
                if(StringUtil.isNotEmpty(yearMonth)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String startDate = yearMonth+"-01";
                    String endDate = yearMonth+"-31";
                    cq.ge("createDate",sdf.parse(startDate));
                    cq.le("createDate",sdf.parse(endDate));
                }
            }
            String expDate = request.getParameter("expDate");
            if(StringUtil.isNotEmpty(expDate)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.add(Restrictions.sqlRestriction("this_.id in (select id from t_b_risk_identification where status='3' and is_del = '0' and identifi_date <= '"+sdf.format(new Date())+"'  and (exp_date >= '"+sdf.format(new Date())+"' or exp_date is null) )"));
            }
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


    @RequestMapping(params = "doBatchDelRel")
    @ResponseBody
    public AjaxJson doBatchDelRel(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "取消关联成功";
        try{
            for(String id:ids.split(",")){
                RiskFactortsRel riskFactortsRel = systemService.getEntity(RiskFactortsRel.class, id);
                TBReportDeleteIdEntity reportDeleteIdEntity = new TBReportDeleteIdEntity();
                reportDeleteIdEntity.setDeleteId(id);
                reportDeleteIdEntity.setType("rfRel");
                systemService.save(reportDeleteIdEntity);
                RiskIdentificationEntity risk = riskFactortsRel.getRiskIdentificationEntity();
                systemService.delete(riskFactortsRel);
                risk.setStatus(Constants.RISK_IDENTIFI_STATUS_DRAFT);
                systemService.saveOrUpdate(risk);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "取消关联失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goUpdateRelInfo")
    public ModelAndView goUpdateRelInfo(RiskFactortsRel riskFactortsRel, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskFactortsRel.getId())) {
            riskFactortsRel = systemService.getEntity(RiskFactortsRel.class, riskFactortsRel.getId());
            req.setAttribute("riskFactortsRelPage", riskFactortsRel);
        }
        return new ModelAndView("com/sddb/buss/web/identification/updateRelInfo-update");
    }

    @RequestMapping(params = "goAllUpdateRelInfo")
    public ModelAndView goAllUpdateRelInfo (HttpServletRequest req) {
        String ids = req.getParameter("ids");
        req.setAttribute("ids",ids);
        return new ModelAndView("com/sddb/buss/web/identification/updateRelInfo-updateAll");
    }


    @RequestMapping(params = "doUpdateRelInfo")
    @ResponseBody
    public AjaxJson doUpdateRelInfo(RiskFactortsRel riskFactortsRel,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "修改成功";
        try{
            String id = riskFactortsRel.getId();
            RiskFactortsRel t= systemService.getEntity(RiskFactortsRel.class,id);
            MyBeanUtils.copyBeanNotNull2Bean(riskFactortsRel, t);
            systemService.saveOrUpdate(t);
        }catch(Exception e){
            e.printStackTrace();
            message = "修改失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "doAllUpdateRelInfo")
    @ResponseBody
    public AjaxJson doAllUpdateRelInfo(RiskFactortsRel riskFactortsRel,HttpServletRequest request, HttpServletResponse response) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "修改成功";
        String ids = request.getParameter("ids");
        if (StringUtil.isNotEmpty(ids)) {
            List<String> examIds = new ArrayList<>();
            try {
                for (String id : ids.split(",")) {
                    RiskFactortsRel t = systemService.getEntity(RiskFactortsRel.class, id);
                    MyBeanUtils.copyBeanNotNull2Bean(riskFactortsRel, t);
                    systemService.saveOrUpdate(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = "修改失败";
                throw new BusinessException(e.getMessage());
            }
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "isCanDel")
    @ResponseBody
    public AjaxJson isCanDel(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        try{
            for(String id : ids.split(",")){
                RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class,id);
                if(riskIdentificationEntity != null && !Constants.RISK_IDENTIFI_STATUS_DRAFT.equals(riskIdentificationEntity.getStatus())){
                    j.setSuccess(false);
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        return j;
    }

    @RequestMapping(params = "delRisk")
    @ResponseBody
    public AjaxJson delRisk(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "删除成功";
        try{
            for(String id : ids.split(",")){
                RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class,id);
                riskIdentificationEntity.setIsDel(Constants.RISK_IS_DEL_TRUE);
                //systemService.executeSql("delete from t_b_risk_identifi_user_rel where hazard_factors_id = '"+id+"'") ;
                //systemService.executeSql("delete from t_b_risk_factors_rel where risk_identification_id = '"+id+"'") ;
                systemService.executeSql("delete from t_b_risk_manage_rel_risk where risk_id = '"+id+"'") ;
                systemService.executeSql("DELETE FROM t_b_risk_manage WHERE risk_id = '"+id+"'") ;
                //systemService.executeSql("DELETE FROM t_b_risk_manage_task where risk_id = '"+id+"'") ;
                //systemService.executeSql("DELETE FROM t_b_risk_manage_hazard_factor where risk_id = '"+id+"'");
                systemService.executeSql("delete from t_b_risk_manage_post_rel_risk where risk_id = '"+id+"'") ;
                //systemService.delete(riskIdentificationEntity);
                systemService.saveOrUpdate(riskIdentificationEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "getLastIdentifiMan")
    @ResponseBody
    public String[] getLastIdentifiMan(HttpServletRequest request, HttpServletResponse response){
        String address = request.getParameter("address");
        String postId = request.getParameter("postId");
        String identificationType = request.getParameter("identificationType");
        String load = request.getParameter("load");
        TSUser user = ResourceUtil.getSessionUserName();
        String id = request.getParameter("id");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class);
        try{
            if(StringUtils.isNotBlank(id)){
                cq.eq("id",id);
            }
            if(StringUtils.isNotBlank(address)) {
                cq.eq("address.id", address);
            }
            if(StringUtils.isNotBlank(postId)){
                cq.eq("post.id",postId);
            }
            if(StringUtils.isNotBlank(load)){
                if(!load.equals("detail")){
                    cq.eq("createBy", user.getUserName());
                }
            }
            cq.eq("identificationType", identificationType);
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();

        List<RiskIdentificationEntity> list = systemService.getListByCriteriaQuery(cq,false);
        List<String> listTemp = new ArrayList<String>();
        if(StringUtils.isNotBlank(load)){
            if(!load.equals("detail")){
                listTemp.add(user.getId());
            }else{
                boolean contantCurrentUser = false;
                if(list != null){
                    for(RiskIdentificationEntity bean : list){
                        if(bean.getIdentifiUserList() != null){
                            List<RiskIdentifiUserRel> userRels = bean.getIdentifiUserList();
                            for(RiskIdentifiUserRel rel: userRels){
                                listTemp.add(rel.getUser().getId());
                                if (user.getId().equals(rel.getUser().getId())) {
                                    contantCurrentUser = true;
                                }
                            }
                            break;
                        }
                    }
                }
                if (!contantCurrentUser) {
                    listTemp.add(user.getId());
                }
            }
        }else{
            listTemp.add(user.getId());
        }
        return listTemp.toArray(new String[listTemp.size()]);
    }


    /**
     * 是否可以提交
     * @param ids
     * @return
     */
    @RequestMapping(params = "isReport")
    @ResponseBody
    public String isReport(String ids){
        String isReport = "2";
        String sql = "SELECT id from t_b_risk_identification WHERE (status = 1 or status = 3) and id in ('"+ids+"')";
        List<String> risks = systemService.findListbySql(sql);
        if(risks==null||risks.size()==0){
            isReport = "0";
            sql = "SELECT id from t_b_risk_identification WHERE (risk_desc = '' or risk_desc is NULL) and id in ('"+ids+"')";
            risks = systemService.findListbySql(sql);
            if(risks==null||risks.size()==0){
                sql = "SELECT id from t_b_hazard_factors WHERE STATUS != '3' and is_del != '1' and STATUS != '2'  and id in( SELECT hazard_factors_id from t_b_risk_factors_rel WHERE risk_identification_id in ('"+ids+"'))";
                List<String> hazardFactors = systemService.findListbySql(sql);
                if(hazardFactors==null||hazardFactors.size()==0){
                    isReport = "1";
                }else{
                    isReport = "-1";
                }
            }
        }
        return isReport;
    }

    @RequestMapping(params = "goReport")
    public ModelAndView goReport (HttpServletRequest req) {
        String ids = req.getParameter("ids");
        req.setAttribute("ids", ids);
        String identifiManId = req.getParameter("identifiManId");
        req.setAttribute("identifiManId", identifiManId);
        return new ModelAndView("com/sddb/buss/web/identification/riskIdentificationReport");
    }


    @RequestMapping(params = "doReport")
    @ResponseBody
    public AjaxJson doReport(String ids,HttpServletRequest request, HttpServletResponse response){
        String identifiManId = request.getParameter("identifiManId");
        String reviewMan = request.getParameter("reviewMan");
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "提交成功";
        try{
            if(StringUtils.isNotBlank(ids)){
                for(String id : ids.split(",")){
                    RiskIdentificationEntity bean = systemService.getEntity(RiskIdentificationEntity.class,id);
                    if(StringUtil.isNotEmpty(identifiManId)){
                        List<RiskIdentifiUserRel> identifiUserList = bean.getIdentifiUserList();
                        bean.setIdentifiUserList(null);
                        for(RiskIdentifiUserRel user : identifiUserList){
                            systemService.delete(user);
                        }
                        List<RiskIdentifiUserRel> newList = new ArrayList<RiskIdentifiUserRel>();
                        if(StringUtils.isNotBlank(identifiManId)) {
                            for (String userId : identifiManId.split(",")) {
                                TSUser user = systemService.getEntity(TSUser.class, userId);
                                RiskIdentifiUserRel rel = new RiskIdentifiUserRel();
                                rel.setRiskIdentificationEntity(bean);
                                rel.setUser(user);
                                systemService.save(rel);
                                newList.add(rel);
                            }
                        }
                        bean.setIdentifiUserList(newList);
                    }
                    bean.setStatus(Constants.RISK_IDENTIFI_STATUS_TOVIEW);
                    bean.setSubmitMan(ResourceUtil.getSessionUserName().getId());
                    bean.setReviewMan(reviewMan);
                    systemService.saveOrUpdate(bean);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "提交失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goClone")
    public ModelAndView goClone(String id,String identificationType, HttpServletRequest req) {
        String riskTaskId = req.getParameter("riskTaskId");
        req.setAttribute("identificationType",identificationType);
        req.setAttribute("toCloneAddressId",id);
        req.setAttribute("riskTaskId",riskTaskId);
        String riskManageTaskId = req.getParameter("riskManageTaskId");
        req.setAttribute("riskManageTaskId",riskManageTaskId);
        if ("5".equals(identificationType)) {
            return new ModelAndView("com/sddb/buss/web/identification/linshi-clone");
        } else if ("3".equals(identificationType)) {
            //星村个性化
            String xingcun = ResourceUtil.getConfigByName("xingcun");
            if(xingcun.equals("true")){
                return new ModelAndView("com/sddb/buss/web/identification/speIde-cloneXC");
            }
            return new ModelAndView("com/sddb/buss/web/identification/speIde-clone");
        } else if(Constants.HAZARDFACTORS_FROM_POST.equals(identificationType)){
            return new ModelAndView("com/sddb/buss/web/identification/post-clone");
        }else {
            //星村个性化
            String xingcun = ResourceUtil.getConfigByName("xingcun");
            if(xingcun.equals("true")){
                return new ModelAndView("com/sddb/buss/web/identification/tBAddressInfo-cloneXC");
            }
            return new ModelAndView("com/sddb/buss/web/identification/tBAddressInfo-clone");
        }
    }

    @RequestMapping(params = "goCloneZX")
    public ModelAndView goCloneZX(String toCloneAddressId,String identificationType, HttpServletRequest req) {
        String riskTaskId = req.getParameter("riskTaskId");
        req.setAttribute("identificationType",identificationType);
        req.setAttribute("toCloneAddressId",toCloneAddressId);
        req.setAttribute("riskTaskId",riskTaskId);
        String riskManageTaskId = req.getParameter("riskManageTaskId");
        req.setAttribute("riskManageTaskId",riskManageTaskId);
        String fl = req.getParameter("fl");
        if ("zx".equals(fl)) {
            return new ModelAndView("com/sddb/buss/web/identification/speIde-clone");
        } else {
            return new ModelAndView("com/sddb/buss/web/identification/speIde-cloneND");
        }
    }


    @RequestMapping(params = "doClone")
    @ResponseBody
    public AjaxJson doClone(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
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
        String from = request.getParameter("addressId");
        String postId = request.getParameter("postId");
        String speIdeTaskId =  request.getParameter("speIdeTaskId");
        String to = request.getParameter("toCloneAddressId");
        String identificationType = request.getParameter("identificationType");
        String riskTaskId = request.getParameter("riskTaskId");
        String riskManageTaskId = request.getParameter("riskManageTaskId");
        message = "克隆成功";
        try{
            CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class);
            try{
                if(StringUtils.isNotBlank(from)) {
                    cq.eq("address.id", from);
                }else if(StringUtils.isNotBlank(postId)){
                    cq.eq("post.id",postId);
                } else if (StringUtils.isNoneBlank(speIdeTaskId)) {//根据专项辨识活动id获取风险信息
                    cq.eq("riskTaskId",speIdeTaskId);
                }
                cq.eq("status",Constants.RISK_IDENTIFI_STATUS_REVIEW);
                if(!isAdmin){ //管理员可以克隆所有的
                    String yiqiao = ResourceUtil.getConfigByName("yiqiao");
                    if(yiqiao.equals("false")){
                        cq.eq("createBy", sessionUser.getUserName());
                    }
                }
                if(StringUtil.isNotEmpty(identificationType)){
                    String xingcun = ResourceUtil.getConfigByName("xingcun");
                    if(xingcun.equals("true")){
                        String queryHandleStatus = request.getParameter("queryHandleStatus");
                        if(StringUtil.isNotEmpty(queryHandleStatus)){
                            cq.eq("identificationType",queryHandleStatus);
                        }else{
                            cq.eq("identificationType",identificationType);
                        }
                    }else{
                        cq.eq("identificationType",identificationType);
                    }
                }
                cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
            }catch(Exception e){
                e.printStackTrace();
            }
            cq.add();

            List<RiskIdentificationEntity> list = systemService.getListByCriteriaQuery(cq,false);
            if(list != null && !list.isEmpty()){
                TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,to);
                TBPostManageEntity postManageEntity = systemService.getEntity(TBPostManageEntity.class,to);
                for(RiskIdentificationEntity bean : list){
                    //1.构建主表
                    RiskIdentificationEntity toBean = new RiskIdentificationEntity();
                    toBean.setIdentificationType(identificationType);
                    toBean.setAddress(addressInfoEntity);
                    toBean.setRiskDesc(bean.getRiskDesc());
                    toBean.setRiskType(bean.getRiskType());
                    toBean.setRiskLevel(bean.getRiskLevel());
                    toBean.setManageLevel(bean.getManageLevel());
                    toBean.setDutyManager(bean.getDutyManager());
                    toBean.setIdentifiDate(bean.getIdentifiDate());
                    toBean.setExpDate(bean.getExpDate());
                    toBean.setIdentifiManId(bean.getIdentifiManId());
                    toBean.setPost(postManageEntity);
                    toBean.setStatus(Constants.RISK_IDENTIFI_STATUS_DRAFT);
                    toBean.setIsDel(Constants.RISK_IS_DEL_FALSE);
                    toBean.setRiskTaskId(riskTaskId);
                    toBean.setRiskManageTaskId(riskManageTaskId);
                    systemService.save(toBean);
                    //2.保存关联辨识人员
                    List<RiskIdentifiUserRel> userRels = bean.getIdentifiUserList();
                    for(RiskIdentifiUserRel rel : userRels){
                        TSUser user = rel.getUser();
                        RiskIdentifiUserRel toRel = new RiskIdentifiUserRel();
                        toRel.setUser(user);
                        toRel.setRiskIdentificationEntity(toBean);
                        systemService.save(toRel);
                    }
                    //3.保存关联危害因素
                    List<RiskFactortsRel> riskFactortsRels = bean.getRelList();
                    for(RiskFactortsRel riskFactortsRel : riskFactortsRels){
                        RiskFactortsRel toRel = new RiskFactortsRel();
                        toRel.setRiskIdentificationEntity(toBean);
                        toRel.setHazardFactorsEntity(riskFactortsRel.getHazardFactorsEntity());
                        toRel.setHfLevel(riskFactortsRel.getHfLevel());
                        toRel.setManageDepart(riskFactortsRel.getManageDepart());
                        toRel.setManageUser(riskFactortsRel.getManageUser());
                        toRel.setHfManageMeasure(riskFactortsRel.getHfManageMeasure());
                        systemService.save(toRel);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "克隆失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "cloneDatagrid")
    public void cloneDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        int pageSize = dataGrid.getRows();
        String identificationType = request.getParameter("identificationType");
        String sql = "select DISTINCT t.address_id from t_b_risk_identification t where t.identification_type = '"+identificationType+"' and t.`status` = '3' and t.is_del = '0'";
        if(StringUtil.isNotEmpty(identificationType)){
            String xingcun = ResourceUtil.getConfigByName("xingcun");
            if(xingcun.equals("true")){
                String queryHandleStatus = request.getParameter("queryHandleStatus");
                if(StringUtil.isEmpty(queryHandleStatus)){
                    queryHandleStatus = "2";
                }
                sql = "select DISTINCT t.address_id from t_b_risk_identification t where t.identification_type = '"+queryHandleStatus+"' and t.`status` = '3' and t.is_del = '0'";
            }
        }
        String address = request.getParameter("address");
        if(StringUtil.isNotEmpty(address)){
            sql = sql + " and t.address_id in (SELECT ai.id FROM t_b_address_info ai where ai.address like '%"+address+"%')";
        }
        List<Map<String, Object>> listMap = systemService.findForJdbc(sql);
        List<Map<String, Object>> listMap2 = systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
        List<TBAddressInfoEntity> retList = new ArrayList<TBAddressInfoEntity>();
        if(listMap2 != null){
            for(Map<String,Object> map : listMap2){
                String addressId = (String)map.get("address_id");

                TBAddressInfoEntity bean = systemService.getEntity(TBAddressInfoEntity.class,addressId);
                retList.add(bean);
            }
        }
        dataGrid.setResults(retList);
        dataGrid.setTotal(listMap == null?0:listMap.size());
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "clonePostDatagrid")
    public void clonePostDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        int pageSize = dataGrid.getRows();
        String identificationType = request.getParameter("identificationType");
        String sql = "select DISTINCT t.post_id from t_b_risk_identification t where t.identification_type = '"+identificationType+"' and t.`status` = '3' and t.is_del = '0'";
        String postName = request.getParameter("postName");
        if(StringUtil.isNotEmpty(postName)){
            sql = sql + " and t.post_id in (SELECT pm.id FROM t_b_post_manage pm where pm.post_name like '%"+postName+"%')";
        }
        List<Map<String, Object>> listMap = systemService.findForJdbc(sql);
        List<Map<String, Object>> listMap2 = systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
        List<TBPostManageEntity> retList = new ArrayList<TBPostManageEntity>();
        if(listMap2 != null){
            for(Map<String,Object> map : listMap2){
                String postId = (String)map.get("post_id");

                TBPostManageEntity bean = systemService.getEntity(TBPostManageEntity.class,postId);
                retList.add(bean);
            }
        }
        dataGrid.setResults(retList);
        dataGrid.setTotal(listMap == null?0:listMap.size());
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "getIdentificationType")
    @ResponseBody
    public String getIdentificationType (HttpServletRequest req) {
        String identificationType = "";
        String id = req.getParameter("id");
        RiskIdentificationEntity riskIdentificationEntity =null;
        if (StringUtil.isNotEmpty(id)) {
            riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class, id);
            identificationType = riskIdentificationEntity.getIdentificationType();
        }
        return  identificationType;
    }

    @RequestMapping(params = "goApproval")
    public ModelAndView goApproval (HttpServletRequest req) {
        String id = req.getParameter("id");
        RiskIdentificationEntity riskIdentificationEntity =null;
        if (StringUtil.isNotEmpty(id)) {
            riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class, id);
            riskIdentificationEntity.setModifyDate(new Date());
            riskIdentificationEntity.setModifyMan(ResourceUtil.getSessionUserName().getId());
            req.setAttribute("riskIdentificationEntity", riskIdentificationEntity);
            req.setAttribute("modifyMan",riskIdentificationEntity.getModifyMan());
        }else{
            TSUser tsUser = ResourceUtil.getSessionUserName();
            req.setAttribute("modifyMan",tsUser.getId());
        }
        return new ModelAndView("com/sddb/buss/web/identification/reviewList-review");
    }

    @RequestMapping(params = "goAllApproval")
    public ModelAndView goAllApproval (HttpServletRequest req) {
        String ids = req.getParameter("ids");
        req.setAttribute("ids", ids);
        req.setAttribute("modifyDate",new Date());
        req.setAttribute("modifyMan",ResourceUtil.getSessionUserName().getId());
        return new ModelAndView("com/sddb/buss/web/identification/reviewList-reviewAll");
    }

    @RequestMapping(params = "saveApproval")
    @ResponseBody
    public AjaxJson saveApproval(RiskIdentificationEntity riskIdentificationEntity,HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        message = "风险辨识审批成功";
        RiskIdentificationEntity t = systemService.get(RiskIdentificationEntity.class, riskIdentificationEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(riskIdentificationEntity, t);
            if ("3".equals(t.getStatus())){
                //查询责任人更新 上报国家局措施 sf_risk_measure表
                String updateSql="UPDATE sf_risk_measure set depart=(select depart.departname  from t_s_base_user baseUser LEFT JOIN t_s_user_org userOrg on userOrg.user_id=baseUser.id left join t_s_depart depart on userOrg.org_id=depart.id  where 1=1 and baseUser.realname='"+t.getDutyManager()+"' limit 1),is_upload='0' where risk_code ='"+t.getId()+"'";
                systemService.executeSql(updateSql);
            }
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "风险辨识审批失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "saveAllApproval")
    @ResponseBody
    public AjaxJson saveAllApproval(RiskIdentificationEntity riskIdentificationEntity,HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        message = "风险辨识审批成功";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ids = request.getParameter("ids");
        String status = request.getParameter("status");
        String modifyMan = request.getParameter("modifyMan");
        String rollBackRemark = request.getParameter("rollBackRemark");
        String date = request.getParameter("modifyDate");
        Date modifyDate = new Date();
        try {
            if(StringUtil.isNotEmpty(date)){
                modifyDate = sdf.parse(request.getParameter("modifyDate"));
            }
            for(String id:ids.split(",")) {
                RiskIdentificationEntity t = systemService.get(RiskIdentificationEntity.class, id);
                if(status.equals("3")){
                    t.setStatus(status);
                    t.setModifyDate(modifyDate);
                    t.setModifyMan(modifyMan);
                    systemService.saveOrUpdate(t);

                    //查询责任人更新 上报国家局措施 sf_risk_measure表
                    String updateSql="UPDATE sf_risk_measure set depart=(select depart.departname  from t_s_base_user baseUser LEFT JOIN t_s_user_org userOrg on userOrg.user_id=baseUser.id left join t_s_depart depart on userOrg.org_id=depart.id  where 1=1 and baseUser.realname='"+t.getDutyManager()+"' limit 1),is_upload='0' where risk_code ='"+t.getId()+"'";
                    systemService.executeSql(updateSql);
                }else{
                    t.setStatus(status);
                    t.setRollBackRemark(rollBackRemark);
                    systemService.saveOrUpdate(t);
                }
            }
        } catch (ParseException e) {
            message = "风险辨识审批失败";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }
    @RequestMapping(params = "getRiskLevel")
    @ResponseBody
    public AjaxJson getRiskLevel(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String riskId = request.getParameter("riskId");
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT MIN(hfLevel) level FROM t_b_risk_factors_rel WHERE risk_identification_id = '").append(riskId).append("' ");
            List<Map<String,Object>> queryList = this.systemService.findForJdbc(sb.toString());
            if(queryList != null && queryList.size()>0){
                j.setObj(queryList.get(0).get("level"));
                if(StringUtil.isNotEmpty(riskId)){
                    RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class,riskId);
                    String riskLevel = riskIdentificationEntity.getRiskLevel();
                    if(StringUtil.isNotEmpty(riskLevel)){
                        Integer hfLevelTemp = Integer.valueOf(queryList.get(0).get("level").toString());
                        Integer riskLevelTemp = Integer.valueOf(riskLevel);
                        if(hfLevelTemp<riskLevelTemp){
                            riskIdentificationEntity.setRiskLevel(queryList.get(0).get("level").toString());
                            systemService.saveOrUpdate(riskIdentificationEntity);
                        }else{
                            j.setObj(riskLevel);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public String exportXls(RiskManageEntity riskManage, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {

        String address = request.getParameter("address.address");
        String postName = request.getParameter("post.postName");
        String riskType = request.getParameter("riskType");
        String riskLevel = request.getParameter("riskLevel");
        String manageLevel = request.getParameter("manageLevel");
        String dutyManager = request.getParameter("dutyManager");
        String addressId = request.getParameter("addressId");
        String postId = request.getParameter("postId");
        String identificationTypeArr[] = request.getParameterValues("identificationType");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
        try {
            String idTemp = request.getParameter("ids");
            if(StringUtils.isNotBlank(idTemp)&&idTemp!=null) {
                List<String> idList = new ArrayList<>();
                for (String id : idTemp.split(",")) {
                    idList.add(id);
                }
                cq.in("id", idList.toArray());
            }else{
                cq.eq("status",Constants.RISK_IDENTIFI_STATUS_REVIEW);
                cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
                if(StringUtils.isNotBlank(address)){
                    cq.createAlias("address","address");
                    cq.like("address.address","%"+address+"%");
                }
                if(StringUtils.isNotBlank(postName)){
                    cq.createAlias("post","post");
                    cq.like("post.postName","%"+postName+"%");
                }
                if(StringUtils.isNotBlank(riskType)){
                    cq.eq("riskType",riskType);
                }
                if(StringUtils.isNotBlank(riskLevel)){
                    cq.eq("riskLevel",riskLevel);
                }
                if(StringUtils.isNotBlank(manageLevel)){
                    cq.eq("manageLevel",manageLevel);
                }
                if(StringUtils.isNotBlank(dutyManager)){
                    cq.like("dutyManager","%"+dutyManager+"%");
                }

                if(StringUtils.isNotBlank(addressId)){
                    cq.createAlias("address","address");
                    cq.eq("address.id",addressId);
                }
                if(StringUtils.isNotBlank(postId)){
                    cq.createAlias("post","post");
                    cq.eq("post.id",postId);
                }
                if(identificationTypeArr.length>0){
                    for(int i=0;i<identificationTypeArr.length;i++){
                        cq.eq("identificationType",identificationTypeArr[i]);
                    }
                }
                String queryYear = request.getParameter("queryYear");
                if(StringUtil.isNotEmpty(queryYear)){
                    cq.add(Restrictions.sqlRestriction("this_.id in (select id from  t_b_risk_identification  where  identifi_date <= '"+queryYear+"-12-31'  and (exp_date >= '"+queryYear+"-01-01' or exp_date is null))"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        List<RiskIdentificationEntity> riskIdentificationEntityList = this.systemService.getListByCriteriaQuery(cq,false);
        List<RiskIdentificationEntity> riskIdentificationEntityListTemp = new ArrayList<>();
        if (riskIdentificationEntityList != null && riskIdentificationEntityList.size() > 0) {
            for (RiskIdentificationEntity entity : riskIdentificationEntityList) {
                entity.setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type",entity.getRiskType()));
                entity.setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level",entity.getRiskLevel()));
                String tempLevel = DicUtil.getTypeNameByCode("identifi_mange_level",entity.getManageLevel());
                if(StringUtil.isNotEmpty(tempLevel)){
                    entity.setManageLevelTemp(tempLevel);
                }else{
                    entity.setManageLevelTemp(entity.getManageLevel());
                }
                entity.setIdentificationTypeTemp(DicUtil.getTypeNameByCode("identifi_from",entity.getIdentificationType()));
                List<RiskFactortsRel> relList = entity.getRelList();
                if (relList != null&&relList.size()>0) {
                    for (RiskFactortsRel riskFactorts : relList) {
                        RiskIdentificationEntity temp = new RiskIdentificationEntity();
                        try {
                            MyBeanUtils.copyBeanNotNull2Bean(entity, temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        temp.setHazardFactorsTemp(riskFactorts.getHazardFactorsEntity().getHazardFactors());
                        temp.setManageMeasureTemp(riskFactorts.getHfManageMeasure());
                        temp.setHazardFactorsLevelTemp(DicUtil.getTypeNameByCode("factors_level",riskFactorts.getHfLevel()));
                        if(StringUtil.isNotEmpty(riskFactorts.getManageDepart())){
                            temp.setManageDepartTemp(riskFactorts.getManageDepart().getDepartname());
                        }
                        if(StringUtil.isNotEmpty(riskFactorts.getManageUser())){
                            temp.setManageUserTemp(riskFactorts.getManageUser().getRealName());
                        }
                        riskIdentificationEntityListTemp.add(temp);
                    }
                }else{
                    riskIdentificationEntityListTemp.add(entity);
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        String newPost = ResourceUtil.getConfigByName("newPost");
        if(newPost.equals("true")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_riskListNew.xlsx");
        }else{
            templateExportParams.setTemplateUrl("export/template/exportTemp_riskList.xlsx");
        }
        String xinan = ResourceUtil.getConfigByName("xinan");
        if(xinan.equals("true")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_riskListNewxinan.xlsx");
        }

        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,List<RiskIdentificationEntity>> map = new HashMap<String,List<RiskIdentificationEntity>>();
        map.put("list", riskIdentificationEntityListTemp);
        modelMap.put(NormalExcelConstants.FILE_NAME,"风险列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(RiskIdentificationEntity riskIdentificationEntity, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME,"岗位风险辨识导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(1);
        templateExportParams.setScanAllsheet(true);

        Map<String, Object> param =new HashMap<String, Object>();

        templateExportParams.setTemplateUrl("export/template/importTemp_riskIdentificationPost.xls");
        Map<String, List<String>> dicListMap = new HashMap();
        List<RiskIdentificationPostEntity> dicVOList = new ArrayList<RiskIdentificationPostEntity>();

        //单位
        String postUnitSql = "select departName from t_s_depart where delete_flag='0'";
        List<String> postUnitList = systemService.findListbySql(postUnitSql);

        //岗位
        String postNameSql = "select post_name from t_b_post_manage where is_delete != '1'";
        List<String> postNameListSql = systemService.findListbySql(postNameSql);

        //风险类型
        String riskTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='risk_type')";
        List<String> riskTypeList = systemService.findListbySql(riskTypeSql);

        //风险等级
        String riskLevelSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='factors_level')";
        List<String> riskLevelList = systemService.findListbySql(riskLevelSql);

        dicListMap.put("postUnitList", postUnitList);
        dicListMap.put("postNameListSql", postNameListSql);
        dicListMap.put("riskTypeList", riskTypeList);
        dicListMap.put("riskLevelList", riskLevelList);
        //得到这几串数列的最长的一列，excel导出的行数即为最长一列的长度
        int[] listLength = {dicListMap.get("postUnitList").size(), dicListMap.get("postNameListSql").size(),
                dicListMap.get("riskTypeList").size(),dicListMap.get("riskLevelList").size()};         /*后面这个查询的除了公有以外的私有的*/
        int maxLength = listLength[0];
        for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
            if (listLength[i] > maxLength) {  //循环判断数组元素
                maxLength = listLength[i]; }  //赋值给num，然后再次循环
        }

        for (int j=0; j<maxLength; j++) {
            RiskIdentificationPostEntity vo = new RiskIdentificationPostEntity();
            if (j < dicListMap.get("postUnitList").size()) {
                vo.setPostUnitTemp(dicListMap.get("postUnitList").get(j));
            }
            if (j < dicListMap.get("postNameListSql").size()) {
                vo.setPostTemp(dicListMap.get("postNameListSql").get(j));
            }
            if (j < dicListMap.get("riskTypeList").size()) {
                vo.setRiskTypeTemp(dicListMap.get("riskTypeList").get(j));
            }
            if (j < dicListMap.get("riskLevelList").size()) {
                vo.setRiskLevelTemp(dicListMap.get("riskLevelList").get(j));
            }
            dicVOList.add(vo);
        }

        //将字典赋值到param中，写到sheet2中
        param.put("dicVoList", dicVOList);



        modelMap.put(TemplateExcelConstants.PARAMS, templateExportParams);
        modelMap.put(TemplateExcelConstants.MAP_DATA, param);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    /**
     * 导入功能跳转
     *
     * @return
     */

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name","riskIdentificationController");
        String riskTaskId = req.getParameter("riskTaskId");
        req.setAttribute("function_name", "importExcelT&riskTaskId="+riskTaskId+"");
        req.setAttribute("riskTaskId", "riskTaskId");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    //导入
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcelT", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcelT(HttpServletRequest request, HttpServletResponse response) {
        String riskTaskId = request.getParameter("riskTaskId");
        AjaxJson j = new AjaxJson();

        List<TSDepart> postUnitList = systemService.findByProperty(TSDepart.class,"deleteFlag",new Short("0"));
        Map<String, TSDepart> postUnitMap = new HashedMap();
        if (postUnitList != null && postUnitList.size()>0) {
            for (TSDepart entity : postUnitList) {
                postUnitMap.put(entity.getDepartname(), entity);
            }
        }
        List<TBPostManageEntity> postList = systemService.findByProperty(TBPostManageEntity.class,"isDelete",new String("0"));
        Map<String, TBPostManageEntity> postMap = new HashedMap();
        if (postList != null && postList.size()>0) {
            for (TBPostManageEntity entity : postList) {
                postMap.put(entity.getPostName().replaceAll(" ",""), entity);
            }
        }

        String hazardSql = "select id,hazard_factors from t_b_hazard_factors_post";
        List<Map<String, Object>> HazardFactorsPostEntityListTemp = systemService.findForJdbc(hazardSql);
        Map<String, String> hazardFactorsPostEntityMap = new HashMap<>();
        for(Map<String, Object> bean : HazardFactorsPostEntityListTemp) {
            if (bean.get("hazard_factors") == null) {
                continue;
            }
            String hazardKey = MD5Util.MD5Encode(bean.get("hazard_factors").toString(), "UTF-8");
            hazardFactorsPostEntityMap.put(hazardKey,bean.get("id").toString());
        }
        String riskSql = "select id, post_unit, post_id, risk_type, risk_level from t_b_risk_identification_post where is_del = '0'";
        List<Map<String, Object>> riskIdentificationPostEntityListTemp =systemService.findForJdbc(riskSql);
        Map<String, String> riskIdentificationPostMap = new HashMap<>();
        for(Map<String, Object> bean : riskIdentificationPostEntityListTemp) {
            String id = bean.get("id").toString();
            String postUnit = "";
            if (bean.get("post_unit") != null) {
                postUnit = bean.get("post_unit").toString();
            }
            String post = "";
            if (bean.get("post_id") != null) {
                post = bean.get("post_id").toString();
            }
            String riskType = "";
            if (bean.get("risk_type") != null) {
                riskType = bean.get("risk_type").toString();
            }
            String riskLevel = "";
            if (bean.get("risk_level") != null) {
                riskLevel = bean.get("risk_level").toString();
            }
            String riskKey = MD5Util.MD5Encode(postUnit+post+riskType+riskLevel, "UTF-8");
            riskIdentificationPostMap.put(riskKey,id);
        }

        String hazardRiskSql = "SELECT id,hazard_factors_post_id,risk_identification_post_id FROM t_b_risk_factors_post_rel";
        List<Map<String, Object>> hazardRiskListTemp = systemService.findForJdbc(hazardRiskSql);
        Map<String, String> hazardRiskMap = new HashMap<>();
        for(Map<String, Object> bean : hazardRiskListTemp) {
            String hazardRiskRelKey = MD5Util.MD5Encode(bean.get("id").toString(), "UTF-8");
            String hazardKey = MD5Util.MD5Encode(bean.get("hazard_factors_post_id").toString(), "UTF-8");
            String rikKey = MD5Util.MD5Encode(bean.get("risk_identification_post_id").toString(), "UTF-8");
            hazardRiskMap.put(hazardKey+rikKey,hazardRiskRelKey);
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
            params.setVerifyHanlder(new RiskIdentificationPostExcelVerifyHandler(postUnitMap,postMap,riskIdentificationPostMap,hazardFactorsPostEntityMap,hazardRiskMap));
            try {
                ExcelImportResult<RiskIdentificationPostEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),RiskIdentificationPostEntity.class,params);
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
                    riskSql = "select id, post_unit, post_id, risk_type, risk_level from t_b_risk_identification_post where is_del = '0' and risk_task_id = '"+riskTaskId+"' and create_by = '"+ResourceUtil.getSessionUserName().getUserName()+"'";
                    riskIdentificationPostEntityListTemp =systemService.findForJdbc(riskSql);
                    riskIdentificationPostMap = new HashMap<>();
                    for(Map<String, Object> bean : riskIdentificationPostEntityListTemp) {
                        String id = bean.get("id").toString();
                        String postUnit = "";
                        if (bean.get("post_unit") != null) {
                            postUnit = bean.get("post_unit").toString();
                        }
                        String post = "";
                        if (bean.get("post_id") != null) {
                            post = bean.get("post_id").toString();
                        }
                        String riskType = "";
                        if (bean.get("risk_type") != null) {
                            riskType = bean.get("risk_type").toString();
                        }
                        String riskLevel = "";
                        if (bean.get("risk_level") != null) {
                            riskLevel = bean.get("risk_level").toString();
                        }
                        String riskKey = MD5Util.MD5Encode(postUnit+post+riskType+riskLevel, "UTF-8");
                        riskIdentificationPostMap.put(riskKey,id);
                    }

                    systemService.addLog("岗位风险开始导入", Globals.Log_Type_UPLOAD,Globals.Log_Type_UPLOAD);
                    for (int i = 0; i < result.getList().size(); i++) {
                        RiskIdentificationPostEntity riskIdentificationPostEntity =  result.getList().get(i);
                        String postUnit = riskIdentificationPostEntity.getPostUnit().getId();
                        String post = riskIdentificationPostEntity.getPost().getId();
                        String riskType = riskIdentificationPostEntity.getRiskType();
                        String riskLevel = riskIdentificationPostEntity.getRiskLevel();
                        String newRiskKey = MD5Util.MD5Encode(postUnit+post+riskType+riskLevel, "UTF-8");
                        String riskIdentificationPostId = riskIdentificationPostMap.get(newRiskKey);
                        if(StringUtil.isEmpty(riskIdentificationPostId)){
                            riskIdentificationPostEntity.setIsDel(Constants.RISK_IS_DEL_FALSE);
                            riskIdentificationPostEntity.setRiskTaskId(riskTaskId);
                            systemService.save(riskIdentificationPostEntity);
                            riskIdentificationPostId = riskIdentificationPostEntity.getId();
                            riskIdentificationPostMap.put(newRiskKey,riskIdentificationPostId);
                        }
                        String hazardFactors = riskIdentificationPostEntity.getHazardFactorsPostTemp();
                        if (StringUtil.isEmpty(hazardFactors)) {
                            continue;
                        }
                        String newHazardKey = MD5Util.MD5Encode(hazardFactors.trim(), "UTF-8");
                        String hazardFactorsPostId = hazardFactorsPostEntityMap.get(newHazardKey);
                        if(StringUtil.isEmpty(hazardFactorsPostId)){
                            HazardFactorsPostEntity hazardFactorsPostEntity = new HazardFactorsPostEntity();
                            hazardFactorsPostEntity.setHazardFactors(hazardFactors.trim());
                            hazardFactorsPostEntity.setIsDel("0");
                            systemService.save(hazardFactorsPostEntity);
                            hazardFactorsPostId=  hazardFactorsPostEntity.getId();
                            hazardFactorsPostEntityMap.put(newHazardKey,hazardFactorsPostId);
                        }

                        if (StringUtil.isNotEmpty(hazardFactorsPostId)) {
                            String insertSql = "insert into t_b_risk_factors_post_rel (id, hazard_factors_post_id, risk_identification_post_id) select " +
                                    "'"+UUIDGenerator.generate()+"'," +
                                    "'"+hazardFactorsPostId+"'," +
                                    "'"+riskIdentificationPostId+"' " +
                                    "from dual where not exists " +
                                    "(select 1 from t_b_risk_factors_post_rel where hazard_factors_post_id='"+hazardFactorsPostId+"' and risk_identification_post_id='"+riskIdentificationPostId+"')";
                            systemService.executeSql(insertSql);
                        }

                    }
                    systemService.addLog("岗位风险导入完成", Globals.Log_Type_UPLOAD,Globals.Log_Type_UPLOAD);
                    j.setMsg("文件导入成功！");
                }

            } catch (Exception e) {
                j.setMsg("文件导入失败！");
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


    @RequestMapping(params = "postDatagrid")
    public void postDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String riskType= request.getParameter("riskType");
        String riskLevel= request.getParameter("riskLevel");
        String postId = request.getParameter("postId");
        String postName= request.getParameter("post.postName");
        String riskTaskId= request.getParameter("riskTaskId");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationPostEntity.class, dataGrid);
        try {
            cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
            if(StringUtils.isNotBlank(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtils.isNotBlank(riskLevel)){
                cq.eq("riskLevel",riskLevel);
            }
            if(StringUtils.isNotBlank(postName)){
                cq.createAlias("post","post");
                cq.like("post.postName","%"+postName+"%");
            }
            if(StringUtils.isNotBlank(postId)){
                cq.createAlias("post","post");
                cq.eq("post.id", postId);
            }
            if(StringUtils.isNotBlank(riskTaskId)){
                cq.eq("riskTaskId", riskTaskId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskIdentificationPostEntity> list = dataGrid.getResults();
            for (RiskIdentificationPostEntity bean : list) {
                List<RiskFactortsPostRel> relList = bean.getPostRelList();
                if (relList == null) {
                    bean.setHazardFactortsPostNum("0");
                }
                bean.setHazardFactortsPostNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doBatchDelPost")
    @ResponseBody
    public AjaxJson doBatchDelPost(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "删除成功";
        try{
            for(String id:ids.split(",")){
                RiskIdentificationPostEntity riskIdentificationpPostEntity = systemService.getEntity(RiskIdentificationPostEntity.class, id);
                riskIdentificationpPostEntity.setIsDel(Constants.RISK_IS_DEL_TRUE);
                //systemService.executeSql("delete from t_b_risk_factors_post_rel where risk_identification_post_id = '"+id+"'") ;
                //systemService.executeSql("delete from t_b_risk_manage_post_rel_risk where risk_id = '"+id+"'") ;
                systemService.executeSql("DELETE FROM t_b_risk_manage_post WHERE risk_id = '"+id+"'") ;
                //systemService.executeSql("DELETE FROM t_b_risk_manage_post_task where post_risk_id = '"+id+"'") ;
                //systemService.executeSql("DELETE FROM t_b_risk_manage_post_hazard_factor where post_risk_id = '"+id+"'") ;
                //systemService.delete(riskIdentificationpPostEntity);
                systemService.saveOrUpdate(riskIdentificationpPostEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "wxysPostList")
    public ModelAndView wxysPostList(HttpServletRequest request) {
        String riskId = request.getParameter("id");
        request.setAttribute("riskId",riskId);
        String load = request.getParameter("load");
        request.setAttribute("load",load);
        String riskManageId = request.getParameter("riskManageId");
        if(StringUtil.isNotEmpty(riskManageId)){
            RiskManagePostEntity riskManagePostEntity = systemService.getEntity(RiskManagePostEntity.class,riskManageId);
            request.setAttribute("riskId",riskManagePostEntity.getRisk().getId());
        }
        return new ModelAndView("com/sddb/buss/web/identification/wxysPostList");
    }

    @RequestMapping(params = "whysPostListDatagrid")
    public void whysPostListDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String riskId = request.getParameter("riskId");
        CriteriaQuery cq = new CriteriaQuery(RiskFactortsPostRel.class, dataGrid);
        try {
            cq.eq("riskIdentificationPostEntity.id",riskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goAddPost")
    public ModelAndView goAddPost(RiskIdentificationPostEntity riskIdentificationPostEntity , HttpServletRequest request) {
        String identificationType = request.getParameter("identificationType");
        String postId = request.getParameter("postId");
        String load = request.getParameter("load");
        request.setAttribute("postId",postId);
        request.setAttribute("identificationType",identificationType);
        request.setAttribute("load",load);

        if(StringUtil.isNotEmpty(riskIdentificationPostEntity.getId())) {
            riskIdentificationPostEntity = systemService.getEntity(RiskIdentificationEntity.class, riskIdentificationPostEntity.getId());
            request.setAttribute("riskIdentificationPostPage", riskIdentificationPostEntity);
        }

        //
        List<TSType> types = ResourceUtil.allTypes.get("risk_type");
        request.setAttribute("riskTypes",types);

        String userId = request.getParameter("userId");
        request.setAttribute("userId",userId);
        String riskType = request.getParameter("riskType");
        request.setAttribute("riskType",riskType);
        String riskManageHazardFactorId = request.getParameter("riskManageHazardFactorId");
        request.setAttribute("riskManageHazardFactorId",riskManageHazardFactorId);//管控危害因素id
        return new ModelAndView("com/sddb/buss/web/identification/riskIdentification-addPost");
    }


    @RequestMapping(params = "datagridPost")
    public void datagridPost( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        boolean isAdmin = false;
        CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
        try{
            cqru.eq("TSUser.id",user.getId());
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
        String identificationType = request.getParameter("identificationType");
        String address = request.getParameter("addressId");
        String postId = request.getParameter("post.id");
        String load = request.getParameter("load");
        if(StringUtils.isNotBlank(address)) {
            CriteriaQuery cq = new CriteriaQuery(RiskIdentificationPostEntity.class, dataGrid);
            try {
                //cq.eq("identificationType", identificationType);
                cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
                if(StringUtil.isNotEmpty(load)){
                    if (!isAdmin&&!load.equals("detail")) {
                        cq.eq("createBy", user.getUserName());
                    }
                }else if(!isAdmin){ //管理员可以查看所有的
                    cq.eq("createBy", user.getUserName());
                }
                cq.eq("address.id", address);
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
        }else if(StringUtils.isNotBlank(postId)){
            CriteriaQuery cq = new CriteriaQuery(RiskIdentificationPostEntity.class, dataGrid);
            try {
                cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
                // cq.eq("identificationType", identificationType);
                if (!isAdmin) { //管理员可以查看所有的
                    cq.eq("createBy", user.getUserName());
                }
                cq.eq("post.id",postId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
                List<RiskIdentificationPostEntity> list = dataGrid.getResults();
                for (RiskIdentificationPostEntity bean : list) {
                    List<RiskFactortsPostRel> relList = bean.getPostRelList();
                    if (relList == null) {
                        bean.setHazardFactortsPostNum("0");
                    }
                    bean.setHazardFactortsPostNum(relList.size() + "");
                }
            }
        }else{
            List<RiskIdentificationEntity> list = new ArrayList<RiskIdentificationEntity>();
            dataGrid.setResults(list);
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsPost")
    public String exportXlsPost( HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        String riskType = request.getParameter("riskType");
        String postId = request.getParameter("postId");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationPostEntity.class, dataGrid);
        try {
            String idTemp = request.getParameter("ids");
            if(StringUtils.isNotBlank(idTemp)&&idTemp!=null) {
                List<String> idList = new ArrayList<>();
                for (String id : idTemp.split(",")) {
                    idList.add(id);
                }
                cq.in("id", idList.toArray());
            }else{
                if(StringUtils.isNotBlank(riskType)){
                    cq.eq("riskType",riskType);
                }
                if(StringUtils.isNotBlank(postId)){
                    cq.createAlias("post","post");
                    cq.eq("post.id",postId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
        cq.add();
        List<RiskIdentificationPostEntity> riskIdentificationPostEntityList = this.systemService.getListByCriteriaQuery(cq,false);
        List<RiskIdentificationPostEntity> riskIdentificationPostEntityListTemp = new ArrayList<>();
        if (riskIdentificationPostEntityList != null && riskIdentificationPostEntityList.size() > 0) {
            for (RiskIdentificationPostEntity entity : riskIdentificationPostEntityList) {
                entity.setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type",entity.getRiskType()));
                entity.setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level",entity.getRiskLevel()));
                List<RiskFactortsPostRel> relList = entity.getPostRelList();
                if (relList != null&&relList.size()>0) {
                    for (RiskFactortsPostRel riskFactortsPost : relList) {
                        RiskIdentificationPostEntity temp = new RiskIdentificationPostEntity();
                        try {
                            MyBeanUtils.copyBeanNotNull2Bean(entity, temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        temp.setHazardFactorsPostTemp(riskFactortsPost.getHazardFactorsPostEntity().getHazardFactors());
                        riskIdentificationPostEntityListTemp.add(temp);
                    }
                }else{
                    riskIdentificationPostEntityListTemp.add(entity);
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        String riskManage = request.getParameter("riskManage");
        if(StringUtil.isNotEmpty(riskManage)){
            templateExportParams.setTemplateUrl("export/template/exportTemp_riskManagePostList.xls");
        }else{
            templateExportParams.setTemplateUrl("export/template/exportTemp_riskPostList.xls");
        }

        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,List<RiskIdentificationPostEntity>> map = new HashMap<String,List<RiskIdentificationPostEntity>>();
        map.put("list", riskIdentificationPostEntityListTemp);
        if(StringUtil.isNotEmpty(riskManage)){
            modelMap.put(NormalExcelConstants.FILE_NAME,"管控清单");
        }else{
            modelMap.put(NormalExcelConstants.FILE_NAME,"风险列表");
        }

        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    @RequestMapping(params = "doBatchDelPostRel")
    @ResponseBody
    public AjaxJson doBatchDelPostRel(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "取消成功";
        try{
            for(String id:ids.split(",")){
                RiskFactortsPostRel riskFactortsPostRel = systemService.getEntity(RiskFactortsPostRel.class, id);
                systemService.delete(riskFactortsPostRel);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "取消失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "goAddHazardFactorsPost")
    public ModelAndView goAddHazardFactorsPost(RiskIdentificationPostEntity riskIdentificationPostEntity , HttpServletRequest request) {
        String identificationType = request.getParameter("identificationType");
        String postId = request.getParameter("postId");
        String load = request.getParameter("load");
        request.setAttribute("postId",postId);
        request.setAttribute("identificationType",identificationType);
        request.setAttribute("load",load);

        if(StringUtil.isNotEmpty(riskIdentificationPostEntity.getId())) {
            riskIdentificationPostEntity = systemService.getEntity(RiskIdentificationEntity.class, riskIdentificationPostEntity.getId());
            request.setAttribute("riskIdentificationPostPage", riskIdentificationPostEntity);
        }

        //
        List<TSType> types = ResourceUtil.allTypes.get("risk_type");
        request.setAttribute("riskTypes",types);

        String userId = request.getParameter("userId");
        request.setAttribute("userId",userId);
        String riskType = request.getParameter("riskType");
        request.setAttribute("riskType",riskType);
        String riskManageHazardFactorId = request.getParameter("riskManageHazardFactorId");
        request.setAttribute("riskManageHazardFactorId",riskManageHazardFactorId);//管控危害因素id
        return new ModelAndView("com/sddb/buss/web/identification/riskIdentification-add");
    }

    //风险降级
    @RequestMapping(params = "doDelRel")
    @ResponseBody
    public AjaxJson doDelRel(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "取消关联成功";
        try{
            RiskIdentificationEntity risk = new RiskIdentificationEntity();
            for(String id:ids.split(",")){
                RiskFactortsRel riskFactortsRel = systemService.getEntity(RiskFactortsRel.class, id);
                risk = riskFactortsRel.getRiskIdentificationEntity();
                TBReportDeleteIdEntity reportDeleteIdEntity = new TBReportDeleteIdEntity();
                reportDeleteIdEntity.setDeleteId(id);
                reportDeleteIdEntity.setType("rfRel");
                systemService.save(reportDeleteIdEntity);
                systemService.delete(riskFactortsRel);
            }
            String sql = "SELECT MIN(hfLevel) level FROM t_b_risk_factors_rel WHERE risk_identification_id = '"+risk.getId()+"'";
            List<Map<String,Object>> queryList = this.systemService.findForJdbc(sql);
            if(queryList != null && queryList.size()>0){
                String hfLevel = String.valueOf(queryList.get(0).get("level"));
                if(!hfLevel.equals("null")){
                    Integer hfLevelTemp = Integer.valueOf(queryList.get(0).get("level").toString());
                    Integer riskLevelTemp = Integer.valueOf(risk.getRiskLevel());
                    if(hfLevelTemp>riskLevelTemp){
                        risk.setRiskLevel(queryList.get(0).get("level").toString());
                        systemService.saveOrUpdate(risk);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "取消关联失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goEditLevel")
    public ModelAndView goEditLevel(RiskFactortsRel riskFactortsRel, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskFactortsRel.getId())) {
            riskFactortsRel = systemService.getEntity(RiskFactortsRel.class, riskFactortsRel.getId());
            req.setAttribute("riskFactortsRelPage", riskFactortsRel);
        }
        return new ModelAndView("com/sddb/buss/web/identification/updateRelInfo-editLevel");
    }

    @RequestMapping(params = "doUpdateRelInfoLevel")
    @ResponseBody
    public AjaxJson doUpdateRelInfoLevel(RiskFactortsRel riskFactortsRel,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "修改成功";
        try{
            String id = riskFactortsRel.getId();
            RiskFactortsRel t= systemService.getEntity(RiskFactortsRel.class,id);
            MyBeanUtils.copyBeanNotNull2Bean(riskFactortsRel, t);
            systemService.saveOrUpdate(t);
            RiskIdentificationEntity risk = t.getRiskIdentificationEntity();
            String sql = "SELECT MIN(hfLevel) level FROM t_b_risk_factors_rel WHERE risk_identification_id = '"+t.getRiskIdentificationEntity().getId()+"'";
            List<Map<String,Object>> queryList = this.systemService.findForJdbc(sql);
            if(queryList != null && queryList.size()>0){
                String hfLevel = String.valueOf(queryList.get(0).get("level"));
                if(!hfLevel.equals("null")){
                    risk.setRiskLevel(queryList.get(0).get("level").toString());
                    //risk_level字段在国家局上报信息中，变化时更新上报标志
                    risk.setStateFlag(com.sdzk.buss.web.common.Constants.GJJ_STATE_FLAG_2);
                    systemService.saveOrUpdate(risk);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "修改失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAllEdit")
    public ModelAndView goAllEdit (HttpServletRequest req) {
        String ids = req.getParameter("ids");
        req.setAttribute("ids", ids);
        return new ModelAndView("com/sddb/buss/web/identification/riskAllEdit");
    }

    @RequestMapping(params = "doAllEdit")
    @ResponseBody
    public AjaxJson doAllEdit(RiskIdentificationEntity riskIdentificationEntity,HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        message = "风险辨识编辑成功";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ids = request.getParameter("ids");
        String manageLevel = request.getParameter("manageLevel");
        String dutyManager = request.getParameter("dutyManager");
        String identifiDateTemp = request.getParameter("identifiDate");
        String expDateTemp = request.getParameter("expDate");
        Date identifiDate = new Date();
        Date expDate = new Date();
        try {
            if(StringUtil.isNotEmpty(identifiDateTemp)){
                identifiDate = sdf.parse(identifiDateTemp);
            }
            if(StringUtil.isNotEmpty(expDateTemp)){
                expDate = sdf.parse(expDateTemp);
            }
            for(String id:ids.split(",")) {
                RiskIdentificationEntity t = systemService.get(RiskIdentificationEntity.class, id);
                t.setManageLevel(manageLevel);
                t.setDutyManager(dutyManager);
                t.setIdentifiDate(identifiDate);
                t.setExpDate(expDate);
                systemService.saveOrUpdate(t);
            }
        } catch (ParseException e) {
            message = "风险辨识编辑失败";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }
}
