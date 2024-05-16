package com.sddb.buss.riskmanage.controller;

import com.sddb.buss.identification.entity.*;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.buss.riskdata.entity.HazardModuleEntity;
import com.sddb.buss.riskmanage.entity.*;
import com.sddb.common.Constants;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.fine.entity.TBFineEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
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
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/riskManageTaskController")
public class RiskManageTaskController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RiskManageTaskController.class);

	@Autowired
	private SystemService systemService;


    /**
     * 管控任务
     * @param request
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        String manageType = request.getParameter("manageType");
        String taskAllId = request.getParameter("taskAllId");
        String checkRelId = request.getParameter("checkRelId");
        if(StringUtil.isNotEmpty(checkRelId)){
            RiskManageTaskAllManageCheckRelEntity riskManageTaskAllManageCheckRelEntity = systemService.get(RiskManageTaskAllManageCheckRelEntity.class,checkRelId);
            RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.get(RiskManageTaskAllEntity.class,riskManageTaskAllManageCheckRelEntity.getTaskAllId());
            manageType = riskManageTaskAllEntity.getManageType();
            taskAllId = riskManageTaskAllEntity.getId();
        }
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        request.setAttribute("taskAllId",taskAllId);
        //金达个人化处理
        String jinda = ResourceUtil.getConfigByName("jinda");
        request.setAttribute("jinda",jinda);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageTaskList");
    }

    /**
     * 管控任务
     *
     */
    @RequestMapping(params = "relRiskDatagrid")
    public void relRiskDatagrid(RiskManageTaskEntity riskManageTask,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskManageTaskEntity.class,dataGrid);
        if(null!=riskManageTask && null!=riskManageTask.getRisk() && null!=riskManageTask.getRisk().getAddress()){
            cq.createAlias("risk.address","risk_address");
            riskManageTask.getRisk().getAddress().setAddress(null);
        }
        if(null!=riskManageTask && null!=riskManageTask.getRisk() && null!=riskManageTask.getRisk().getPost()){
            cq.createAlias("risk.post","risk_post");
            riskManageTask.getRisk().getPost().setPostName(null);
        }
        if(null!=riskManageTask && null!=riskManageTask.getRisk()){
            if(StringUtil.isNotEmpty(riskManageTask.getRisk().getDutyManager())){
                riskManageTask.getRisk().setDutyManager("*"+riskManageTask.getRisk().getDutyManager()+"*");
            }
        }
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManageTask, request.getParameterMap());

        try{
            String taskAllId = request.getParameter("taskAllId");
            if(StringUtil.isNotEmpty(taskAllId)){
                cq.eq("taskAllId",taskAllId);
            }
            String addressName = request.getParameter("risk.address.address");
            if(StringUtil.isNotEmpty(addressName)){
                cq.like("risk_address.address","%"+addressName+"%");
            }
            String postName = request.getParameter("risk.post.postName");
            if(StringUtil.isNotEmpty(postName)){
                cq.like("risk_post.postName","%"+postName+"%");
            }
            String queryHandleStatus = request.getParameter("queryHandleStatus");
            if(StringUtils.isEmpty(queryHandleStatus)){
                queryHandleStatus = Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED;
            } else if("all".equals(queryHandleStatus)){
                queryHandleStatus = "";
            }
            if (StringUtils.isNotBlank(queryHandleStatus)) {
                cq.eq("handleStatus", queryHandleStatus);
            }

            String addressId = request.getParameter("addressId");
            if(StringUtil.isNotEmpty(addressId)){
                cq.createAlias("risk.address","risk_address");
                cq.eq("risk_address.id",addressId);
            }

            String postId = request.getParameter("postId");
            if(StringUtil.isNotEmpty(postId)){
                cq.createAlias("risk.post","risk_post");
                cq.eq("risk_post.id",postId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.createAlias("risk", "risk");
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskManageTaskEntity> list = dataGrid.getResults();
            for (RiskManageTaskEntity riskManageTaskEntity : list) {
                int numTotal = 0;
                int numFinished = 0;
                List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityList = riskManageTaskEntity.getRiskManageHazardFactorEntityList();
                if(null!=riskManageHazardFactorEntityList){
                    numTotal = riskManageHazardFactorEntityList.size();
                    for(int i=0;i<riskManageHazardFactorEntityList.size();i++){
                        if(StringUtil.isNotEmpty(riskManageHazardFactorEntityList.get(i).getImplDetail())){
                            numFinished ++;
                        }
                    }
                }
                riskManageTaskEntity.setHazardFactorNum(numTotal+"");
                riskManageTaskEntity.setHazardFactorNumFinished(numFinished+"");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }


    //获取风险清单列表，从中选取风险进入风险任务
    @RequestMapping(params = "selectRisk")
    public ModelAndView selectRisk(HttpServletRequest request) {
        request.setAttribute("year", DateUtils.getYear());
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String taskAllId = request.getParameter("taskAllId");
        request.setAttribute("taskAllId",taskAllId);
        return new ModelAndView("com/sddb/buss/web/riskmanage/selectManageTaskRisk");
    }


    @RequestMapping(params = "riskDatagrid")
    public void riskDatagrid(RiskManageEntity riskManage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskManageEntity.class,dataGrid);
        if(null!=riskManage && null!=riskManage.getRisk()){
            riskManage.getRisk().setAddress(null);
            riskManage.getRisk().setPost(null);
        }
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManage, request.getParameterMap());

        try{
            String addressName = request.getParameter("risk.address.address");
            if(StringUtil.isNotEmpty(addressName)) {
                cq.createAlias("risk.address", "risk_address");
                cq.like("risk_address.address", "%" + addressName + "%");
            }
            String postName = request.getParameter("risk.post.postName");
            if(StringUtil.isNotEmpty(postName)) {
                cq.createAlias("risk.post", "risk_post");
                cq.like("risk_post.postName", "%" + postName + "%");
            }

            String addressId = request.getParameter("addressId");
            if(StringUtil.isNotEmpty(addressId)){
                cq.createAlias("risk.address","risk_address");
                cq.eq("risk_address.id",addressId);
            }

            String postId = request.getParameter("postId");
            if(StringUtil.isNotEmpty(postId)){
                cq.createAlias("risk.post","risk_post");
                cq.eq("risk_post.id",postId);
            }

            TSUser user = ResourceUtil.getSessionUserName();
            cq.eq("myUserId",user.getId());
            cq.add(Restrictions.sqlRestriction(" this_.id not in (select risk_manage_id from t_b_risk_manage_task where create_by='" + user.getUserName()
                    + "' and handle_status!='"+Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED+"')"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.createAlias("risk","risk");
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskManageEntity> list = dataGrid.getResults();
            for (RiskManageEntity riskManageEntity : list) {
                RiskIdentificationEntity bean = riskManageEntity.getRisk();
                List<RiskFactortsRel> relList = bean.getRelList();
                if (relList == null) {
                    bean.setHazardFactortsNum("0");
                }
                bean.setHazardFactortsNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "saveRelRisk")
    @ResponseBody
    public AjaxJson saveRelRisk(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "风险管控任务添加成功";
        String ids = request.getParameter("ids");
        String manageType = request.getParameter("manageType");
        String taskAllId = request.getParameter("taskAllId");
        String taskId = UUIDGenerator.generate();
        try{
            if(org.apache.commons.lang.StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                RiskManageTaskEntity riskManageTaskEntity = null;
                for(String id : idArray){
                    riskManageTaskEntity = new RiskManageTaskEntity();
                    riskManageTaskEntity.setTaskId(taskId);
                    RiskManageEntity t = this.systemService.getEntity(RiskManageEntity.class,id);
                    riskManageTaskEntity.setRiskManage(t);
                    riskManageTaskEntity.setRisk(t.getRisk());
                    riskManageTaskEntity.setManageType(manageType);
                    riskManageTaskEntity.setTaskAllId(taskAllId);
                    List<RiskFactortsRel> riskFactorRelList = t.getRisk().getRelList();
                    if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
                        riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
                    } else {
                        riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                    }
                    this.systemService.save(riskManageTaskEntity);

                    //自动生成对危害因素的管控
                    if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
                        for(int i=0;i<riskFactorRelList.size();i++){
                            RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
                            riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
                            riskManageHazardFactorEntity.setTaskId(taskId);
                            riskManageHazardFactorEntity.setRisk(t.getRisk());
                            riskManageHazardFactorEntity.setManageType(manageType);
                            riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
                            riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
                            this.systemService.save(riskManageHazardFactorEntity);
                        }
                    }

                    //systemService.addLog("风险管控任务\""+ riskManageTaskEntity.getId()+"\"添加风险成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
                RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,taskAllId);
                riskManageTaskAllEntity.setStatus("0");
                systemService.saveOrUpdate(riskManageTaskAllEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "风险管控任务添加风险失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 批量删除事故
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        String taskAllId = request.getParameter("taskAllId");
        try{
            if(StringUtil.isNotEmpty(ids)){
                String idArr[] = ids.split(",");

                for(int i=0;i<idArr.length;i++){
                    RiskManageTaskEntity riskManageTaskEntity = this.systemService.getEntity(RiskManageTaskEntity.class,idArr[i]);
                    String riskId = riskManageTaskEntity.getRisk().getId();

                    //首先删除自动生成的对危害因素的管控
                    CriteriaQuery hazardCq = new CriteriaQuery(RiskManageHazardFactorEntity.class);
                    hazardCq.eq("taskId",riskManageTaskEntity.getTaskId());
                    hazardCq.eq("risk.id",riskId);
                    hazardCq.add();
                    List<RiskManageHazardFactorEntity> hazardList = this.systemService.getListByCriteriaQuery(hazardCq,false);
                    if(null!=hazardList && hazardList.size()>0){
                        this.systemService.deleteAllEntitie(hazardList);
                    }
                    this.systemService.delete(riskManageTaskEntity);
                }
                String sql = "SELECT id FROM t_b_risk_manage_task WHERE handle_status = '0' AND task_all_id = '"+taskAllId+"'";
                List<String> temp = this.systemService.findListbySql(sql);
                if(temp==null||temp.size()==0){
                    RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,taskAllId);
                    riskManageTaskAllEntity.setStatus("1");
                    this.systemService.saveOrUpdate(riskManageTaskAllEntity);
                }

//
//                CriteriaQuery cq = new CriteriaQuery(RiskManageTaskEntity.class);
//                cq.in("id", idArr);
//                cq.add();
//                List<RiskManageTaskEntity> riskManageTaskEntityList = this.systemService.getListByCriteriaQuery(cq,false);
//                if(null!=riskManageTaskEntityList && riskManageTaskEntityList.size()>0){
//                    this.systemService.deleteAllEntitie(riskManageTaskEntityList);
//                }
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "allList")
    public ModelAndView allList(HttpServletRequest request) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageAllList");
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(RiskManageTaskAllEntity riskManageTaskAllEntity , HttpServletRequest request) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String xiaogang = ResourceUtil.getConfigByName("xiaogang");
        request.setAttribute("xiaogang",xiaogang);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageAll-add");
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(RiskManageTaskAllEntity riskManageTaskAllEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "管控任务添加成功";
        try{
            riskManageTaskAllEntity.setStatus("0");
            systemService.save(riskManageTaskAllEntity);
            String xiaogang = ResourceUtil.getConfigByName("xiaogang");
            //小港去除自动生成管控清单
            if(!xiaogang.equals("true")){
                //自动生成管控清单
                String taskId = UUIDGenerator.generate();
                TSUser user = ResourceUtil.getSessionUserName();
                String sql = "SELECT id FROM t_b_risk_manage WHERE manage_type = '"+riskManageTaskAllEntity.getManageType()+"' AND my_user_id = '"+user.getId()+"' " +
                        "AND id NOT IN ( SELECT risk_manage_id FROM t_b_risk_manage_task WHERE create_by = '"+user.getUserName()+"' AND handle_status != '1' )";
                List<String> ids = systemService.findListbySql(sql);
                if(ids!=null&&ids.size()>0){
                    RiskManageTaskEntity riskManageTaskEntity = null;
                    for(String id : ids){
                        RiskManageEntity t = this.systemService.getEntity(RiskManageEntity.class,id);
                        String riskSql = "SELECT id FROM t_b_risk_identification WHERE id = (SELECT risk_id FROM t_b_risk_manage WHERE id ='"+id+"' )";
                        List<String> tempList = systemService.findListbySql(riskSql);
                        if(!tempList.isEmpty() && tempList.size()>0){
                            riskManageTaskEntity = new RiskManageTaskEntity();
                            riskManageTaskEntity.setTaskId(taskId);
                            riskManageTaskEntity.setRiskManage(t);
                            riskManageTaskEntity.setRisk(t.getRisk());
                            riskManageTaskEntity.setManageType(riskManageTaskAllEntity.getManageType());
                            riskManageTaskEntity.setTaskAllId(riskManageTaskAllEntity.getId());
                            List<RiskFactortsRel> riskFactorRelList = t.getRisk().getRelList();
                            if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
                                riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
                            } else {
                                riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                            }
                            this.systemService.save(riskManageTaskEntity);

                            //自动生成对危害因素的管控
                            if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
                                for(int i=0;i<riskFactorRelList.size();i++){
                                    RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
                                    riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
                                    riskManageHazardFactorEntity.setTaskId(taskId);
                                    riskManageHazardFactorEntity.setRisk(t.getRisk());
                                    riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
                                    riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
                                    riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
                                    this.systemService.save(riskManageHazardFactorEntity);
                                }
                            }
                            //systemService.addLog("风险管控任务\""+ riskManageTaskEntity.getId()+"\"添加风险成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                        }
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "管控任务添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(RiskManageTaskAllEntity riskManageTaskAllEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String manageType = request.getParameter("manageType");
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        if (queryHandleStatus == null || queryHandleStatus.equals("")) {
            //queryHandleStatus = "nodis";
            queryHandleStatus = "0";
        }
        CriteriaQuery cq = new CriteriaQuery(RiskManageTaskAllEntity.class, dataGrid);
        try{
            if(StringUtils.isNotBlank(manageType)){
                cq.eq("manageType",manageType);
            }
            cq.eq("createBy",sessionUser.getUserName());
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            String manageTime_begin = request.getParameter("manageTime_begin");
            if(StringUtil.isNotEmpty(manageTime_begin)){
                cq.ge("manageTime", sdFormat.parse(manageTime_begin));
            }
            String manageTime_end = request.getParameter("manageTime_end");
            if(StringUtil.isNotEmpty(manageTime_end)){
                cq.le("manageTime", sdFormat.parse(manageTime_end));
            }
            String manageShift =request.getParameter("manageShift");
            if(StringUtil.isNotEmpty(manageShift)){
                cq.eq("manageShift",manageShift);
            }
            if(!queryHandleStatus.equals("all")){
                cq.eq("status",queryHandleStatus);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if(dataGrid != null && dataGrid.getResults() != null){
            List<RiskManageTaskAllEntity> listTemp = dataGrid.getResults();
            StringBuffer ids = new StringBuffer();
            for (RiskManageTaskAllEntity entity : listTemp ) {
                if (StringUtil.isNotEmpty(ids.toString())) {
                    ids.append(",");
                }
                ids.append("'").append(entity.getId()).append("'") ;
            }
            if (StringUtil.isEmpty(ids.toString())) {
                ids.append("''");
            }
            String hdCountSql = "SELECT sum(count) count, temp.id id FROM ( SELECT count(0) count, task_all_id id FROM t_b_hidden_danger_exam WHERE task_all_id IS NOT NULL AND task_all_id != '' and task_all_id in ("+ids.toString()+") GROUP BY task_all_id UNION ALL SELECT count(0) count, ta.id id FROM t_b_risk_manage_task_all ta LEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id LEFT JOIN t_b_risk_manage_hazard_factor rmhf ON t.id = rmhf.risk_manage_task_id LEFT JOIN t_b_risk_manage_rel_hd rmrh ON rmrh.risk_manage_hazard_factor_id = rmhf.id WHERE rmrh.hd_id IN ( SELECT id FROM t_b_hidden_danger_exam ) and ta.id in ("+ids.toString()+") GROUP BY ta.id ) temp where temp.id in ("+ids.toString()+")  GROUP BY temp.id";
            List<Map<String, Object>> hdCountList = systemService.findForJdbc(hdCountSql);
            Map<String, String> hdCountMap = new HashMap<>();
            if (hdCountList != null && hdCountList.size() > 0) {
                for (Map<String, Object> obj : hdCountList) {
                    hdCountMap.put(obj.get("id").toString(), obj.get("count").toString());
                }
            }

            String hdPostCountSql = "SELECT sum(count)  count, temp.id id FROM ( SELECT count(0) count, task_all_id id FROM t_b_hidden_danger_exam WHERE task_all_id IS NOT NULL AND task_all_id != '' and task_all_id in ("+ids.toString()+") GROUP BY task_all_id UNION ALL SELECT count(0) count, ta.id id FROM t_b_risk_manage_task_all ta LEFT JOIN t_b_risk_manage_post_task t ON t.post_task_all_id = ta.id LEFT JOIN t_b_risk_manage_post_hazard_factor rmhf ON t.id = rmhf.risk_manage_post_task_id LEFT JOIN t_b_risk_manage_post_rel_hd rmrh ON rmrh.risk_manage_post_hazard_factor_id = rmhf.id WHERE rmrh.hd_id IN ( SELECT id FROM t_b_hidden_danger_exam ) and ta.id in ("+ids.toString()+") GROUP BY ta.id ) temp where temp.id in ("+ids.toString()+")  GROUP BY temp.id";
            List<Map<String, Object>> hdPostCountList = systemService.findForJdbc(hdPostCountSql);
            Map<String, String> hdPostCountMap = new HashMap<>();
            if (hdPostCountList != null && hdPostCountList.size() > 0) {
                for (Map<String, Object> obj : hdPostCountList) {
                    hdPostCountMap.put(obj.get("id").toString(), obj.get("count").toString());
                }
            }

            String hdYHCountSql = "SELECT count(0) count, task_all_id id from\n" +
                    "t_b_hidden_danger_exam   WHERE task_all_id is not null and task_all_id != '' and  task_all_id in ("+ids.toString()+")   GROUP BY task_all_id";
            List<Map<String, Object>> hdYHCountList = systemService.findForJdbc(hdYHCountSql);
            Map<String, String> hdYHCountMap = new HashMap<>();
            if (hdYHCountList != null && hdYHCountList.size() > 0) {
                for (Map<String, Object> obj : hdYHCountList) {
                    hdYHCountMap.put(obj.get("id").toString(), obj.get("count").toString());
                }
            }

            String riskCountSql = "SELECT\n" +
                    "\tcount(0) count,\n" +
                    "\tta.id id\n" +
                    "FROM\n" +
                    "\tt_b_risk_manage_task_all ta\n" +
                    "LEFT JOIN t_b_risk_manage_task t ON ta.id = t.task_all_id\n" +
                    "LEFT JOIN t_b_risk_identification ri ON ri.risk_manage_task_id = t.id\n" +
                    "WHERE\n" +
                    "\tri.is_del = '0' and ri.status != '0' and  ta.id in ("+ids.toString()+")\n" +
                    "GROUP BY\n" +
                    "\tta.id";
            List<Map<String, Object>> riskCountSqlList = systemService.findForJdbc(riskCountSql);
            Map<String, String> riskCountSqlMap = new HashMap<>();
            if (riskCountSqlList != null && riskCountSqlList.size() > 0) {
                for (Map<String, Object> obj : riskCountSqlList) {
                    riskCountSqlMap.put(obj.get("id").toString(), obj.get("count").toString());
                }
            }
            String riskPostCountSql = "SELECT\n" +
                    "\tcount(0) count,\n" +
                    "\tta.id id\n" +
                    "FROM\n" +
                    "\tt_b_risk_manage_task_all ta\n" +
                    "LEFT JOIN t_b_risk_manage_post_task t ON ta.id = t.post_task_all_id\n" +
                    "LEFT JOIN t_b_risk_identification ri ON ri.risk_manage_task_id = t.id\n" +
                    "WHERE\n" +
                    "\tri.is_del = '0' and  ri.status != '0' and  ta.id in ("+ids.toString()+")\n" +
                    "GROUP BY\n" +
                    "\tta.id";
            List<Map<String, Object>> riskPostCountSqlList = systemService.findForJdbc(riskPostCountSql);
            Map<String, String> riskPostCountSqlMap = new HashMap<>();
            if (riskPostCountSqlList != null && riskPostCountSqlList.size() > 0) {
                for (Map<String, Object> obj : riskPostCountSqlList) {
                    riskPostCountSqlMap.put(obj.get("id").toString(), obj.get("count").toString());
                }
            }
            String riskYHCountSql = "SELECT count(0) count,risk_manage_task_all_id id from t_b_risk_identification WHERE is_del = '0' and status != '0' and risk_manage_task_all_id != '' and risk_manage_task_all_id is not null and  risk_manage_task_all_id in ("+ids.toString()+") GROUP BY risk_manage_task_all_id";
            List<Map<String, Object>> riskYHCountList = systemService.findForJdbc(riskYHCountSql);
            Map<String, String> riskYHCountMap = new HashMap<>();
            if (riskYHCountList != null && riskYHCountList.size() > 0) {
                for (Map<String, Object> obj : riskYHCountList) {
                    riskYHCountMap.put(obj.get("id").toString(), obj.get("count").toString());
                }
            }
            List<RiskManageTaskAllEntity> list = dataGrid.getResults();
            for(RiskManageTaskAllEntity t : list){
                if(StringUtil.isNotEmpty(t.getManageType())){
                    if(t.getManageType().equals("comprehensive")||t.getManageType().equals("profession")||t.getManageType().equals("team")||t.getManageType().equals("group")){
                        t.setIsExport("0");
                        String hdCount = hdCountMap.get(t.getId());
                        if(StringUtil.isNotEmpty(hdCount)){
                            t.setHdCount(hdCount);
                        }else {
                            t.setHdCount("0");
                        }
                        String riskCount = riskCountSqlMap.get(t.getId());
                        if(StringUtil.isNotEmpty(riskCount)){
                            t.setRiskCount(riskCount);
                        }else {
                            t.setRiskCount("0");
                        }
                    }else if(t.getManageType().equals("post")){
                        t.setIsExport("0");
                        String hdCount = hdPostCountMap.get(t.getId());
                        if(StringUtil.isNotEmpty(hdCount)){
                            t.setHdCount(hdCount);
                        }else {
                            t.setHdCount("0");
                        }
                        String riskCount = riskPostCountSqlMap.get(t.getId());
                        if(StringUtil.isNotEmpty(riskCount)){
                            t.setRiskCount(riskCount);
                        }else {
                            t.setRiskCount("0");
                        }
                    }else{
                        String hdCount = hdYHCountMap.get(t.getId());
                        if(StringUtil.isNotEmpty(hdCount)){
                            t.setHdCount(hdCount);
                        }else {
                            t.setHdCount("0");
                        }
                        String riskCount = riskYHCountMap.get(t.getId());
                        if(StringUtil.isNotEmpty(riskCount)){
                            t.setRiskCount(riskCount);
                        }else {
                            t.setRiskCount("0");
                        }
                        t.setIsExport("1");
                    }
                }


                if(StringUtil.isNotEmpty(t.getTaskAllManage())){
                    t.setEndTask("1");
                    TSUser tsUser = systemService.get(TSUser.class,t.getTaskAllManage().getOrganizerMan());
                    t.setCreateNameTemp(tsUser.getRealName());
                }else{
                    t.setEndTask("0");
                    t.setCreateNameTemp(t.getCreateName());
                }
            }

        }
        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(RiskManageTaskAllEntity riskManageTaskAllEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskManageTaskAllEntity.getId())) {
            riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class, riskManageTaskAllEntity.getId());
            req.setAttribute("riskManageTaskAllPage", riskManageTaskAllEntity);
        }
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageAll-update");
    }
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(RiskManageTaskAllEntity riskManageTaskAllEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "管控任务更新成功";
        RiskManageTaskAllEntity t = systemService.get(RiskManageTaskAllEntity.class, riskManageTaskAllEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(riskManageTaskAllEntity, t);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "管控任务更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(RiskManageTaskAllEntity riskManageTaskAllEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskManageTaskAllEntity.getId())) {
            riskManageTaskAllEntity = systemService.getEntity( RiskManageTaskAllEntity.class, riskManageTaskAllEntity.getId());
            req.setAttribute("riskManageTaskAllPage", riskManageTaskAllEntity);
        }
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageAll-detail");
    }

    /**
     * 获取当前状态
     * @param id
     * @return
     */
    @RequestMapping(params = "getTaskAllStatus")
    @ResponseBody
    public String getTaskAllStatus(String id){
        RiskManageTaskAllEntity entity = systemService.get(RiskManageTaskAllEntity.class, id);
        if (entity != null){
            return entity.getStatus();
        }
        return null;
    }

    /**
     * 管控清单是否存在
     * @param id
     * @return
     */
    @RequestMapping(params = "getTasks")
    @ResponseBody
    public String getTasks(String id){
        String sql = "SELECT id from t_b_risk_manage_task WHERE task_all_id = '"+id+"'";
        List<String> tasks= systemService.findListbySql(sql);
        if(tasks==null||tasks.size()==0){
            return "0";
        }
        return null;
    }

    /**
     * 下发
     * @param id
     * @return
     */
    @RequestMapping(params = "doDis")
    @ResponseBody
    public AjaxJson doDis(String id){
        RiskManageTaskAllEntity entity = systemService.get(RiskManageTaskAllEntity.class, id);
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "所选任务下发成功";
        try {
            entity.setStatus("1");
            systemService.saveOrUpdate(entity);
        } catch (Exception e) {
            e.printStackTrace();
            message = "操作失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除任务
     *
     * @return
     */
    @RequestMapping(params = "doBatchDelAll")
    @ResponseBody
    public AjaxJson doBatchDelAll(String ids, HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        try{
            if(StringUtil.isNotEmpty(ids)){
                String idArr[] = ids.split(",");

                for(int i=0;i<idArr.length;i++){
                    RiskManageTaskAllEntity riskManageTaskAllEntity = this.systemService.getEntity(RiskManageTaskAllEntity.class,idArr[i]);
                    String sql="select id from t_b_risk_manage_task  where task_all_id='"+riskManageTaskAllEntity.getId()+"'";
                    List <String> riskIds=systemService.findListbySql(sql);
                    if(riskIds.size()>0) {
                        for (String id : riskIds) {
                            RiskManageTaskEntity riskManageTaskEntity = this.systemService.getEntity(RiskManageTaskEntity.class,id);
                            String riskId = riskManageTaskEntity.getRisk().getId();
                            //首先删除自动生成的对危害因素的管控
                            CriteriaQuery hazardCq = new CriteriaQuery(RiskManageHazardFactorEntity.class);
                            hazardCq.eq("taskId",riskManageTaskEntity.getTaskId());
                            hazardCq.eq("risk.id",riskId);
                            hazardCq.add();
                            List<RiskManageHazardFactorEntity> hazardList = this.systemService.getListByCriteriaQuery(hazardCq,false);
                            if(null!=hazardList && hazardList.size()>0){
                                this.systemService.deleteAllEntitie(hazardList);
                            }
                            this.systemService.delete(riskManageTaskEntity);
                        }
                    }
                    this.systemService.delete(riskManageTaskAllEntity);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    //岗位单独做一个
    @RequestMapping(params = "allPostListNew")
    public ModelAndView allListPost(HttpServletRequest request) {
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageAllPostListNew");
    }


    @RequestMapping(params = "datagridPost")
    public void datagridPost(RiskManagePostTaskAllEntity riskManagePostTaskAllEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        if (queryHandleStatus == null || queryHandleStatus.equals("")) {
            queryHandleStatus = "0";
        }
        CriteriaQuery cq = new CriteriaQuery(RiskManagePostTaskAllEntity.class, dataGrid);
        try{
            cq.eq("createBy",sessionUser.getUserName());
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            String manageTime_begin = request.getParameter("manageTime_begin");
            if(StringUtil.isNotEmpty(manageTime_begin)){
                cq.ge("manageTime", sdFormat.parse(manageTime_begin));
            }
            String manageTime_end = request.getParameter("manageTime_end");
            if(StringUtil.isNotEmpty(manageTime_end)){
                cq.le("manageTime", sdFormat.parse(manageTime_end));
            }
            String manageShift =request.getParameter("manageShift");
            if(StringUtil.isNotEmpty(manageShift)){
                cq.eq("manageShift",manageShift);
            }
            if(!queryHandleStatus.equals("all")){
                cq.eq("status",queryHandleStatus);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goAddPost")
    public ModelAndView goAddPost(RiskManagePostTaskAllEntity riskManagePostTaskAllEntity, HttpServletRequest request) {
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageAll-addPost");
    }

    @RequestMapping(params = "doAddPost")
    @ResponseBody
    public AjaxJson doAddPost(RiskManagePostTaskAllEntity riskManagePostTaskAllEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "管控任务添加成功";
        try{
            riskManagePostTaskAllEntity.setStatus("0");
            systemService.save(riskManagePostTaskAllEntity);
            String postTaskId = UUIDGenerator.generate();
            TSUser user = ResourceUtil.getSessionUserName();
            String sql = "SELECT id FROM t_b_risk_manage_post WHERE  my_user_id = '"+user.getId()+"' " +
                    "AND id NOT IN ( SELECT risk_manage_post_id FROM t_b_risk_manage_post_task WHERE create_by = '"+user.getUserName()+"' AND handle_status != '1' )";
            List<String> ids = systemService.findListbySql(sql);
            if(ids!=null&&ids.size()>0){
                RiskManagePostTaskEntity riskManagePostTaskEntity = null;
                for(String id : ids){
                    RiskManagePostEntity t = this.systemService.getEntity(RiskManagePostEntity.class,id);
                    String riskSql = "SELECT id FROM t_b_risk_identification_post WHERE id = (SELECT risk_id FROM t_b_risk_manage_post WHERE id ='"+id+"' )";
                    List<String> tempList = systemService.findListbySql(riskSql);
                    if(!tempList.isEmpty() && tempList.size()>0){
                        riskManagePostTaskEntity = new RiskManagePostTaskEntity();
                        riskManagePostTaskEntity.setPostTaskId(postTaskId);
                        riskManagePostTaskEntity.setRiskManagePost(t);
                        riskManagePostTaskEntity.setPostRisk(t.getRisk());
                        riskManagePostTaskEntity.setManageType(riskManagePostTaskAllEntity.getManageType());
                        riskManagePostTaskEntity.setPostTaskAllId(riskManagePostTaskAllEntity.getId());
                        List<RiskFactortsPostRel> riskFactorPostRelList = t.getRisk().getPostRelList();
                        if(null!=riskFactorPostRelList && riskFactorPostRelList.size()>0) {
                            riskManagePostTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
                        } else {
                            riskManagePostTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                        }
                        this.systemService.save(riskManagePostTaskEntity);

                        //自动生成对危害因素的管控
                        if(null!=riskFactorPostRelList && riskFactorPostRelList.size()>0) {
                            for(int i=0;i<riskFactorPostRelList.size();i++){
                                RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity = new RiskManagePostHazardFactorEntity();
                                riskManagePostHazardFactorEntity.setRiskManagePostTaskEntity(riskManagePostTaskEntity);
                                riskManagePostHazardFactorEntity.setPostTaskId(postTaskId);
                                riskManagePostHazardFactorEntity.setPostRisk(t.getRisk());
                                riskManagePostHazardFactorEntity.setManageType(riskManagePostTaskAllEntity.getManageType());
                                riskManagePostHazardFactorEntity.setHazardFactorPost(riskFactorPostRelList.get(i).getHazardFactorsPostEntity());
                                riskManagePostHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
                                this.systemService.save(riskManagePostHazardFactorEntity);
                            }
                        }
                        //systemService.addLog("风险管控任务\""+ riskManagePostTaskEntity.getId()+"\"添加风险成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "管控任务添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 批量删除任务
     *
     * @return
     */
    @RequestMapping(params = "doBatchDelAllPost")
    @ResponseBody
    public AjaxJson doBatchDelAllPost(String ids, HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        try{
            if(StringUtil.isNotEmpty(ids)){
                String idArr[] = ids.split(",");

                for(int i=0;i<idArr.length;i++){
                    RiskManagePostTaskAllEntity riskManagePostTaskAllEntity = this.systemService.getEntity(RiskManagePostTaskAllEntity.class,idArr[i]);
                    String sql="select id from t_b_risk_manage_post_task  where post_task_all_id='"+riskManagePostTaskAllEntity.getId()+"'";
                    List <String> riskIds=systemService.findListbySql(sql);
                    if(riskIds.size()>0) {
                        for (String id : riskIds) {
                            RiskManagePostTaskEntity riskManagePostTaskEntity = this.systemService.getEntity(RiskManagePostTaskEntity.class,id);
                            String riskId = riskManagePostTaskEntity.getPostRisk().getId();
                            //首先删除自动生成的对危害因素的管控
                            CriteriaQuery hazardCq = new CriteriaQuery(RiskManagePostHazardFactorEntity.class);
                            hazardCq.eq("postTaskId",riskManagePostTaskEntity.getPostTaskId());
                            hazardCq.eq("postRisk.id",riskId);
                            hazardCq.add();
                            List<RiskManagePostHazardFactorEntity> hazardList = this.systemService.getListByCriteriaQuery(hazardCq,false);
                            if(null!=hazardList && hazardList.size()>0){
                                this.systemService.deleteAllEntitie(hazardList);
                            }
                            this.systemService.delete(riskManagePostTaskEntity);
                        }
                    }
                    this.systemService.delete(riskManagePostTaskAllEntity);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goUpdatePost")
    public ModelAndView goUpdate(RiskManagePostTaskAllEntity riskManagePostTaskAllEntity,  HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskManagePostTaskAllEntity.getId())) {
            riskManagePostTaskAllEntity = systemService.getEntity(RiskManagePostTaskAllEntity.class, riskManagePostTaskAllEntity.getId());
            req.setAttribute("riskManagePostTaskAllPage", riskManagePostTaskAllEntity);
        }
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageAll-updatePost");
    }
    @RequestMapping(params = "doUpdatePost")
    @ResponseBody
    public AjaxJson doUpdate(RiskManagePostTaskAllEntity riskManagePostTaskAllEntity,  HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "管控任务更新成功";
        RiskManagePostTaskAllEntity t = systemService.get(RiskManagePostTaskAllEntity.class, riskManagePostTaskAllEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(riskManagePostTaskAllEntity, t);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "管控任务更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除
     *
     * @return
     */
    @RequestMapping(params = "doBatchDelPost")
    @ResponseBody
    public AjaxJson doBatchDelPost(String ids, HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        String postTaskAllId = request.getParameter("postTaskAllId");
        try{
            if(StringUtil.isNotEmpty(ids)){
                String idArr[] = ids.split(",");

                for(int i=0;i<idArr.length;i++){
                    RiskManagePostTaskEntity riskManagePostTaskEntity = this.systemService.getEntity(RiskManagePostTaskEntity.class,idArr[i]);
                    String postRiskId = riskManagePostTaskEntity.getPostRisk().getId();

                    //首先删除自动生成的对危害因素的管控
                    CriteriaQuery hazardCq = new CriteriaQuery(RiskManagePostHazardFactorEntity.class);
                    hazardCq.eq("postTaskId",riskManagePostTaskEntity.getPostTaskId());
                    hazardCq.eq("postRisk.id",postRiskId);
                    hazardCq.add();
                    List<RiskManagePostHazardFactorEntity> hazardList = this.systemService.getListByCriteriaQuery(hazardCq,false);
                    if(null!=hazardList && hazardList.size()>0){
                        this.systemService.deleteAllEntitie(hazardList);
                    }
                    this.systemService.delete(riskManagePostTaskEntity);
                }
                String sql = "SELECT id FROM t_b_risk_manage_post_task WHERE handle_status = '0' AND post_task_all_id = '"+postTaskAllId+"'";
                List<String> temp = this.systemService.findListbySql(sql);
                if(temp==null||temp.size()==0){
                    RiskManagePostTaskAllEntity riskManagePostTaskAllEntity = systemService.getEntity(RiskManagePostTaskAllEntity.class,postTaskAllId);
                    if(riskManagePostTaskAllEntity!=null){
                        riskManagePostTaskAllEntity.setStatus("1");
                        this.systemService.saveOrUpdate(riskManagePostTaskAllEntity);
                    }


                    RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,postTaskAllId);
                    if(riskManageTaskAllEntity!=null){
                        riskManageTaskAllEntity.setStatus("1");
                        this.systemService.saveOrUpdate(riskManageTaskAllEntity);
                    }

                }
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    //获取风险清单列表，从中选取风险进入风险任务
    @RequestMapping(params = "selectPostRisk")
    public ModelAndView selectPostRisk(HttpServletRequest request) {
        String postTaskAllId = request.getParameter("postTaskAllId");
        request.setAttribute("postTaskAllId",postTaskAllId);
        return new ModelAndView("com/sddb/buss/web/riskmanage/selectManageTaskPostRisk");
    }

    @RequestMapping(params = "postRiskDatagrid")
    public void postRiskDatagrid(RiskManagePostEntity riskManagePost,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskManagePostEntity.class,dataGrid);
        if(null!=riskManagePost && null!=riskManagePost.getRisk()){
            riskManagePost.getRisk().setPost(null);
        }
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManagePost, request.getParameterMap());
        try{
            TSUser user = ResourceUtil.getSessionUserName();
            cq.eq("myUserId",user.getId());
            cq.add(Restrictions.sqlRestriction(" this_.id not in (select risk_manage_post_id from t_b_risk_manage_post_task where create_by='" + user.getUserName()
                    + "' and handle_status!='"+Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED+"')"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.createAlias("risk","risk");
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskManagePostEntity> list = dataGrid.getResults();
            for (RiskManagePostEntity riskManagePostEntity : list) {
                RiskIdentificationPostEntity bean = riskManagePostEntity.getRisk();
                List<RiskFactortsPostRel> relList = bean.getPostRelList();
                if (relList == null) {
                    bean.setHazardFactortsPostNum("0");
                }
                bean.setHazardFactortsPostNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "saveRelPostRisk")
    @ResponseBody
    public AjaxJson saveRelPostRisk(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "风险管控任务添加成功";
        String ids = request.getParameter("ids");
        String postTaskAllId = request.getParameter("postTaskAllId");
        String postTaskId = UUIDGenerator.generate();
        try{
            if(org.apache.commons.lang.StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                RiskManagePostTaskEntity riskManagePostTaskEntity = null;
                for(String id : idArray){
                    riskManagePostTaskEntity = new RiskManagePostTaskEntity();
                    riskManagePostTaskEntity.setPostTaskId(postTaskId);
                    RiskManagePostEntity t = this.systemService.getEntity(RiskManagePostEntity.class,id);
                    riskManagePostTaskEntity.setRiskManagePost(t);
                    riskManagePostTaskEntity.setPostRisk(t.getRisk());
                    riskManagePostTaskEntity.setManageType("post");
                    riskManagePostTaskEntity.setPostTaskAllId(postTaskAllId);
                    List<RiskFactortsPostRel> riskFactorPostRelList = t.getRisk().getPostRelList();
                    if(null!=riskFactorPostRelList && riskFactorPostRelList.size()>0) {
                        riskManagePostTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
                    } else {
                        riskManagePostTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                    }
                    this.systemService.save(riskManagePostTaskEntity);

                    //自动生成对危害因素的管控
                    if(null!=riskFactorPostRelList && riskFactorPostRelList.size()>0) {
                        for(int i=0;i<riskFactorPostRelList.size();i++){
                            RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity = new RiskManagePostHazardFactorEntity();
                            riskManagePostHazardFactorEntity.setRiskManagePostTaskEntity(riskManagePostTaskEntity);
                            riskManagePostHazardFactorEntity.setPostTaskId(postTaskId);
                            riskManagePostHazardFactorEntity.setPostRisk(t.getRisk());
                            riskManagePostHazardFactorEntity.setManageType("post");
                            riskManagePostHazardFactorEntity.setHazardFactorPost(riskFactorPostRelList.get(i).getHazardFactorsPostEntity());
                            riskManagePostHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
                            this.systemService.save(riskManagePostHazardFactorEntity);
                        }
                    }

                    //systemService.addLog("风险管控任务\""+ riskManagePostTaskEntity.getId()+"\"添加风险成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
                RiskManagePostTaskAllEntity riskManagePostTaskAllEntity = systemService.getEntity(RiskManagePostTaskAllEntity.class,postTaskAllId);
                riskManagePostTaskAllEntity.setStatus("0");
                systemService.saveOrUpdate(riskManagePostTaskAllEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "风险管控任务添加风险失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 新增管控纪录
     * @param request
     * @return
     */
    @RequestMapping(params = "manageRecord")
    public ModelAndView manageRecord(HttpServletRequest request, HttpServletResponse response) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageRecordList");
    }


    @RequestMapping(params = "manageRecordNew")
    public ModelAndView manageRecordNew(HttpServletRequest request, HttpServletResponse response) {
        //return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageRecordListNew");

        String typeAll = request.getParameter("typeAll");
        if(StringUtil.isEmpty(typeAll)){
            typeAll = "1";
        }
        request.setAttribute("typeAll",typeAll);
        if(typeAll.equals("1")){
            return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageRecordListNewManage");
        }else{
            return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageRecordListNewManageGR");
        }
    }

    @RequestMapping(params = "recordDatagrid")
    public void recordDatagrid(RiskManageTaskEntity riskManageTask,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String manageType = request.getParameter("manageType");
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        if (queryHandleStatus == null || queryHandleStatus.equals("")) {
            //queryHandleStatus = "nodis";
            queryHandleStatus = "0";
        }
        String manageUser = request.getParameter("manageUser");
        String manageUserDepartUnit = request.getParameter("manageUserDepartUnit");
        CriteriaQuery cq = new CriteriaQuery(RiskManageTaskAllEntity.class, dataGrid);
        try{
            if(StringUtils.isNotBlank(manageType)){
                cq.eq("manageType",manageType);
            }
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            String manageTime_begin = request.getParameter("manageTime_begin");
            if(StringUtil.isNotEmpty(manageTime_begin)){
                cq.ge("manageTime", sdFormat.parse(manageTime_begin));
            }
            String manageTime_end = request.getParameter("manageTime_end");
            if(StringUtil.isNotEmpty(manageTime_end)){
                cq.le("manageTime", sdFormat.parse(manageTime_end));
            }
            String manageShift =request.getParameter("manageShift");
            if(StringUtil.isNotEmpty(manageShift)){
                cq.eq("manageShift",manageShift);
            }
            if(!queryHandleStatus.equals("all")){
                cq.eq("status",queryHandleStatus);
            }
            if(StringUtil.isNotEmpty(manageUser)){
                cq.eq("createName",manageUser);
            }
            if(StringUtil.isNotEmpty(manageUserDepartUnit)){
                TSDepart depart = systemService.getEntity(TSDepart.class,manageUserDepartUnit);
                cq.add(Restrictions.sqlRestriction("this_.create_by IN (SELECT baseUser.username from t_s_base_user baseUser WHERE baseUser.id in (select org.user_id from t_s_user_org org JOIN t_s_depart depart on org.org_id = depart.ID where depart.ID = '"+depart.getId()+"'))"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 新增管控纪录
     * @param request
     * @return
     */
    @RequestMapping(params = "manageRecordPostNew")
    public ModelAndView manageRecordPostNew(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageRecordPostList");
    }

    @RequestMapping(params = "recordPostDatagrid")
    public void recordPostDatagrid(RiskManagePostTaskEntity riskManagePostTask,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        if (queryHandleStatus == null || queryHandleStatus.equals("")) {
            queryHandleStatus = "0";
        }
        String manageUser = request.getParameter("manageUser");
        String manageUserDepartUnit = request.getParameter("manageUserDepartUnit");
        CriteriaQuery cq = new CriteriaQuery(RiskManagePostTaskAllEntity.class, dataGrid);
        try{
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            String manageTime_begin = request.getParameter("manageTime_begin");
            if(StringUtil.isNotEmpty(manageTime_begin)){
                cq.ge("manageTime", sdFormat.parse(manageTime_begin));
            }
            String manageTime_end = request.getParameter("manageTime_end");
            if(StringUtil.isNotEmpty(manageTime_end)){
                cq.le("manageTime", sdFormat.parse(manageTime_end));
            }
            String manageShift =request.getParameter("manageShift");
            if(StringUtil.isNotEmpty(manageShift)){
                cq.eq("manageShift",manageShift);
            }
            if(!queryHandleStatus.equals("all")){
                cq.eq("status",queryHandleStatus);
            }
            if(StringUtil.isNotEmpty(manageUser)){
                cq.eq("createName",manageUser);
            }
            if(StringUtil.isNotEmpty(manageUserDepartUnit)){
                TSDepart depart = systemService.getEntity(TSDepart.class,manageUserDepartUnit);
                cq.add(Restrictions.sqlRestriction("this_.create_by IN (SELECT baseUser.username from t_s_base_user baseUser WHERE baseUser.id in (select org.user_id from t_s_user_org org JOIN t_s_depart depart on org.org_id = depart.ID where depart.ID = '"+depart.getId()+"'))"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "postList")
    public ModelAndView postList(HttpServletRequest request, HttpServletResponse response) {
        String postTaskAllId = request.getParameter("postTaskAllId");
        request.setAttribute("postTaskAllId",postTaskAllId);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManagePostTaskList");
    }




    @RequestMapping(params = "allListNew")
    public ModelAndView allListNew(HttpServletRequest request) {
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageAllListNew");
    }

    @RequestMapping(params = "goAddNew")
    public ModelAndView goAddNew(RiskManageTaskAllEntity riskManageTaskAllEntity , HttpServletRequest request) {
        String sql = "select id,address from t_b_address_info WHERE is_delete = '0' ORDER BY CONVERT(address USING GBK) ASC";
        List<Map<String, Object>> maplist = systemService.findForJdbc(sql);
        request.setAttribute("addressList",maplist);

        String addressSql = "select address_id from t_b_my_manage where my_user_id ='"+ResourceUtil.getSessionUserName().getId()+"' and address_id is not null and address_id != '' ";
        List<String> addressList = systemService.findListbySql(addressSql);
        if(addressList!=null&&addressList.size()>0){
            request.setAttribute("addressId",addressList.get(0));
        }
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageAll-addNew");
    }

    @RequestMapping(params = "doAddNew")
    @ResponseBody
    public AjaxJson doAddNew(RiskManageTaskAllEntity riskManageTaskAllEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "管控任务添加成功";
        try{
            riskManageTaskAllEntity.setStatus("0");
            systemService.save(riskManageTaskAllEntity);
            /*String[] addressIds = request.getParameterValues("addressId");*/
            String manageType = request.getParameter("manageType");
            if(StringUtil.isNotEmpty(manageType)){
                if(manageType.equals("team")||manageType.equals("group")){
                    String addressIds = request.getParameter("addressId");
                    if(StringUtil.isNotEmpty(addressIds)){
                        String myAddressSql = "update t_b_my_manage SET address_id = '"+addressIds+"' WHERE my_user_id = '"+ResourceUtil.getSessionUserName().getId()+"' ";
                        systemService.executeSql(myAddressSql);
                        StringBuffer addressList = new StringBuffer();
               /* for(String addressId:addressIds){
                    addressList.append("'"+addressId+"',");
                }*/
                        for(String addressId:addressIds.split(",")){
                            addressList.append("'"+addressId+"',");
                        }
                        addressList.append("''");
                        TSUser user =ResourceUtil.getSessionUserName();
                        //风险清单添加风险
                        String riskIdSql = "SELECT id from t_b_risk_identification WHERE is_del='0' and status = '3' and address_id in ("+addressList.toString()+") and risk_type in (SELECT risk_type from t_b_my_manage WHERE my_user_id = '"+user.getId()+"' and is_del = '0')" +
                                " AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+user.getId()+"' AND is_del = '0' GROUP BY manage_level ) AND id NOT IN ( SELECT risk_id FROM t_b_risk_manage ) ";
                        List<String> list = systemService.findListbySql(riskIdSql);
                        if(list!=null&&list.size()>0) {
                            for (String id : list) {
                                RiskManageEntity riskManageEntity = null;
                                riskManageEntity = new RiskManageEntity();
                                RiskIdentificationEntity t = this.systemService.getEntity(RiskIdentificationEntity.class, id);
                                riskManageEntity.setRisk(t);
                                this.systemService.save(riskManageEntity);
                                systemService.addLog("风险清单添加风险\"" + riskManageEntity.getId() + "\"成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
                            }
                        }
                        riskIdSql = "SELECT id from t_b_risk_identification WHERE is_del='0' and status = '3' and address_id in ("+addressList.toString()+") and risk_type in (SELECT risk_type from t_b_my_manage WHERE my_user_id = '"+user.getId()+"' and is_del = '0')" +
                                " AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+user.getId()+"' AND is_del = '0' GROUP BY manage_level ) ";
                        list = systemService.findListbySql(riskIdSql);
                        if(list!=null&&list.size()>0) {
                            //自动生成管控清单
                            String taskId = UUIDGenerator.generate();
                            String sql = "SELECT id FROM t_b_risk_manage WHERE risk_id IN ( SELECT id FROM t_b_risk_identification WHERE is_del = '0' AND STATUS = '3' and ( NOW() < exp_date or exp_date is null ) and NOW() > identifi_date AND address_id IN ("+addressList.toString()+") AND risk_type IN ( SELECT risk_type FROM t_b_my_manage WHERE my_user_id = '"+user.getId()+"' AND is_del = '0' )AND risk_level <= ( SELECT manage_level FROM t_b_my_manage WHERE my_user_id = '"+user.getId()+"' AND is_del = '0' GROUP BY manage_level ))";
                            List<String> ids = systemService.findListbySql(sql);
                            if(ids!=null&&ids.size()>0){
                                RiskManageTaskEntity riskManageTaskEntity = null;
                                for(String id : ids){
                                    RiskManageEntity t = this.systemService.getEntity(RiskManageEntity.class,id);
                                    String riskSql = "SELECT id FROM t_b_risk_identification WHERE id = (SELECT risk_id FROM t_b_risk_manage WHERE id ='"+id+"' )";
                                    List<String> tempList = systemService.findListbySql(riskSql);
                                    if(!tempList.isEmpty() && tempList.size()>0){
                                        riskManageTaskEntity = new RiskManageTaskEntity();
                                        riskManageTaskEntity.setTaskId(taskId);
                                        riskManageTaskEntity.setRiskManage(t);
                                        riskManageTaskEntity.setRisk(t.getRisk());
                                        riskManageTaskEntity.setManageType(riskManageTaskAllEntity.getManageType());
                                        riskManageTaskEntity.setTaskAllId(riskManageTaskAllEntity.getId());
                                        List<RiskFactortsRel> riskFactorRelList = t.getRisk().getRelList();
                                        if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
                                            riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
                                        } else {
                                            riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                                        }
                                        this.systemService.save(riskManageTaskEntity);

                                        //自动生成对危害因素的管控
                                        if(null!=riskFactorRelList && riskFactorRelList.size()>0) {
                                            List<String> majorList =new ArrayList<>();
                                            String majorSql =  "SELECT major FROM t_b_my_manage WHERE my_user_id = '"+user.getId()+"' and major is not null and major != ''";
                                            List<String> major = systemService.findListbySql(majorSql);
                                            if(major!=null&&major.size()>0){
                                                String temp = major.get(0);
                                                String[] values = temp.split(",");
                                                majorList = Arrays.asList(values);
                                            }

                                            for(int i=0;i<riskFactorRelList.size();i++){
                                                String level = riskFactorRelList.get(i).getHfLevel();
                                                if(StringUtil.isNotEmpty(level)){
                                                    if(level.equals("1")||level.equals("2")){
                                                        if(majorList!=null){
                                                            if(StringUtil.isNotEmpty(riskFactorRelList.get(i).getHazardFactorsEntity().getMajor())&&majorList.contains(riskFactorRelList.get(i).getHazardFactorsEntity().getMajor())){
                                                                RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
                                                                riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
                                                                riskManageHazardFactorEntity.setTaskId(taskId);
                                                                riskManageHazardFactorEntity.setRisk(t.getRisk());
                                                                riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
                                                                riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
                                                                riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
                                                                this.systemService.save(riskManageHazardFactorEntity);
                                                            }
                                                        }else{
                                                            RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
                                                            riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
                                                            riskManageHazardFactorEntity.setTaskId(taskId);
                                                            riskManageHazardFactorEntity.setRisk(t.getRisk());
                                                            riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
                                                            riskManageHazardFactorEntity.setHazardFactor(riskFactorRelList.get(i).getHazardFactorsEntity());
                                                            riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
                                                            this.systemService.save(riskManageHazardFactorEntity);
                                                        }
                                                    }
                                                }
                                            }
                                            String manageNumSql = "SELECT manage_num FROM t_b_my_manage WHERE my_user_id = '"+user.getId()+"' and manage_num is not null and manage_num != ''";
                                            List<String> manageNumList = systemService.findListbySql(manageNumSql);
                                            int manageNum = 10;
                                            if(manageNumList!=null&&manageNumList.size()>0){
                                                manageNum = Integer.parseInt(manageNumList.get(0));
                                            }
                                            String hazardFactorSql ="";
                                            if(major!=null&&major.size()>0){
                                                hazardFactorSql = "SELECT hazard_factors_id from t_b_risk_factors_rel  LEFT JOIN t_b_hazard_factors hf on hf.id = hazard_factors_id WHERE FIND_IN_SET(hf.major,'"+major.get(0)+"')  and hfLevel !='1' and hfLevel != '2' and risk_identification_id = '"+t.getRisk().getId()+"' ORDER BY RAND() LIMIT "+manageNum+"";
                                            }else{
                                                hazardFactorSql = "SELECT hazard_factors_id from t_b_risk_factors_rel  LEFT JOIN t_b_hazard_factors hf on hf.id = hazard_factors_id WHERE hfLevel !='1' and hfLevel != '2' and risk_identification_id = '"+t.getRisk().getId()+"' ORDER BY RAND() LIMIT "+manageNum+"";
                                            }

                                            List<String> hazardFactorList = systemService.findListbySql(hazardFactorSql);
                                            for(String hazardFactorId : hazardFactorList){
                                                RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
                                                riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
                                                riskManageHazardFactorEntity.setTaskId(taskId);
                                                riskManageHazardFactorEntity.setRisk(t.getRisk());
                                                riskManageHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
                                                riskManageHazardFactorEntity.setHazardFactor(systemService.get(HazardFactorsEntity.class,hazardFactorId));
                                                riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
                                                this.systemService.save(riskManageHazardFactorEntity);
                                            }
                                        }
                                        //systemService.addLog("风险管控任务\""+ riskManageTaskEntity.getId()+"\"添加风险成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                                        String riskManageHazardFactorSql = "SELECT id from t_b_risk_manage_hazard_factor WHERE risk_manage_task_id = '"+riskManageTaskEntity.getId()+"'";
                                        List<String> riskManageHazardFactorEntityList = systemService.findListbySql(riskManageHazardFactorSql);
                                        if(null==riskManageHazardFactorEntityList || riskManageHazardFactorEntityList.size()==0){
                                            riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                                            this.systemService.delete(riskManageTaskEntity);
                                        }
                                    }
                                }
                            }
                        }
                        /*String sql = "SELECT id FROM t_b_risk_manage_task WHERE handle_status = '0' AND task_all_id = '"+riskManageTaskAllEntity.getId()+"'";
                        List<String> temp = this.systemService.findListbySql(sql);
                        if(temp==null||temp.size()==0){
                            riskManageTaskAllEntity.setStatus("1");
                            this.systemService.delete(riskManageTaskAllEntity);
                            message = "没有需要管控的危害因素";
                        }*/
                    }
                }else if(manageType.equals("post")){
                    String postId = request.getParameter("postId");
                    if(StringUtil.isNotEmpty(postId)){
                        TSUser user =ResourceUtil.getSessionUserName();
                        //风险清单添加风险
                        String riskIdSql = "SELECT id from t_b_risk_identification_post WHERE is_del='0'  and post_id = '"+postId+"' " +
                                " AND id NOT IN ( SELECT risk_id FROM t_b_risk_manage_post ) ";
                        String huafeng = ResourceUtil.getConfigByName("huafeng");
                        if(huafeng.equals("true")){
                            riskIdSql = "SELECT id from t_b_risk_identification_post WHERE is_del='0' and post_unit = '"+user.getCurrentDepart().getId()+"'  and post_id = '"+postId+"' " +
                                " AND id NOT IN ( SELECT risk_id FROM t_b_risk_manage_post ) ";
                        }
                        List<String> list = systemService.findListbySql(riskIdSql);
                        if(list!=null&&list.size()>0) {
                            for (String id : list) {
                                RiskManagePostEntity riskManagePostEntity = null;
                                riskManagePostEntity = new RiskManagePostEntity();
                                RiskIdentificationPostEntity t = this.systemService.getEntity(RiskIdentificationPostEntity.class, id);
                                riskManagePostEntity.setRisk(t);
                                this.systemService.save(riskManagePostEntity);
                                systemService.addLog("风险清单添加风险\"" + riskManagePostEntity.getId() + "\"成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
                            }
                        }
                        riskIdSql = "SELECT id from t_b_risk_identification_post WHERE is_del='0'  and post_id = '"+postId+"' ";
                        huafeng = ResourceUtil.getConfigByName("huafeng");
                        if(huafeng.equals("true")){
                            riskIdSql = "SELECT id from t_b_risk_identification_post WHERE is_del='0' and post_unit = '"+user.getCurrentDepart().getId()+"'   and post_id = '"+postId+"' ";
                        }
                        list = systemService.findListbySql(riskIdSql);
                        if(list!=null&&list.size()>0) {
                            //自动生成管控清单
                            String taskId = UUIDGenerator.generate();
                            String sql = "SELECT id FROM t_b_risk_manage_post WHERE risk_id IN ( SELECT id FROM t_b_risk_identification_post WHERE is_del = '0' and post_id = '"+postId+"')";
                            huafeng = ResourceUtil.getConfigByName("huafeng");
                            if(huafeng.equals("true")){
                                 sql = "SELECT id FROM t_b_risk_manage_post WHERE risk_id IN ( SELECT id FROM t_b_risk_identification_post WHERE is_del = '0' and post_unit = '"+user.getCurrentDepart().getId()+"' and post_id = '"+postId+"')";
                            }
                            List<String> ids = systemService.findListbySql(sql);
                            if(ids!=null&&ids.size()>0){
                                RiskManagePostTaskEntity riskManagePostTaskEntity = null;
                                for(String id : ids){
                                    RiskManagePostEntity t = this.systemService.getEntity(RiskManagePostEntity.class,id);
                                    String riskSql = "SELECT id FROM t_b_risk_identification_post WHERE id = (SELECT risk_id FROM t_b_risk_manage_post WHERE id ='"+id+"' )";
                                    List<String> tempList = systemService.findListbySql(riskSql);
                                    if(!tempList.isEmpty() && tempList.size()>0){
                                        riskManagePostTaskEntity = new RiskManagePostTaskEntity();
                                        riskManagePostTaskEntity.setPostTaskId(taskId);
                                        riskManagePostTaskEntity.setRiskManagePost(t);
                                        riskManagePostTaskEntity.setPostRisk(t.getRisk());
                                        riskManagePostTaskEntity.setManageType(riskManageTaskAllEntity.getManageType());
                                        riskManagePostTaskEntity.setPostTaskAllId(riskManageTaskAllEntity.getId());
                                        List<RiskFactortsPostRel> riskFactorPostRelList = t.getRisk().getPostRelList();
                                        if(null!=riskFactorPostRelList && riskFactorPostRelList.size()>0) {
                                            riskManagePostTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
                                        } else {
                                            riskManagePostTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                                        }
                                        this.systemService.save(riskManagePostTaskEntity);

                                        //自动生成对危害因素的管控
                                        if(null!=riskFactorPostRelList && riskFactorPostRelList.size()>0) {

                                            for(int i=0;i<riskFactorPostRelList.size();i++){
                                                RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity = new RiskManagePostHazardFactorEntity();
                                                riskManagePostHazardFactorEntity.setRiskManagePostTaskEntity(riskManagePostTaskEntity);
                                                riskManagePostHazardFactorEntity.setPostTaskId(taskId);
                                                riskManagePostHazardFactorEntity.setPostRisk(t.getRisk());
                                                riskManagePostHazardFactorEntity.setManageType(riskManageTaskAllEntity.getManageType());
                                                riskManagePostHazardFactorEntity.setHazardFactorPost(riskFactorPostRelList.get(i).getHazardFactorsPostEntity());
                                                riskManagePostHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
                                                this.systemService.save(riskManagePostHazardFactorEntity);
                                            }
                                        }
                                        //systemService.addLog("风险管控任务\""+ riskManagePostTaskEntity.getId()+"\"添加风险成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                                        String riskManagePostHazardFactorSql = "SELECT id from t_b_risk_manage_post_hazard_factor WHERE risk_manage_post_task_id = '"+riskManagePostTaskEntity.getId()+"'";
                                        List<String> riskManagePostHazardFactorEntityList = systemService.findListbySql(riskManagePostHazardFactorSql);
                                        if(null==riskManagePostHazardFactorEntityList || riskManagePostHazardFactorEntityList.size()==0){
                                            riskManagePostTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                                            this.systemService.delete(riskManagePostTaskEntity);
                                        }
                                    }
                                }
                            }
                        }
                        /*String sql = "SELECT id FROM t_b_risk_manage_post_task WHERE handle_status = '0' AND post_task_all_id = '"+riskManageTaskAllEntity.getId()+"'";
                        List<String> temp = this.systemService.findListbySql(sql);
                        if(temp==null||temp.size()==0){
                            riskManageTaskAllEntity.setStatus("1");
                            this.systemService.delete(riskManageTaskAllEntity);
                            message = "没有需要管控的危害因素";
                        }*/
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "管控任务添加失败";
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
        String id = request.getParameter("id");
        RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.get(RiskManageTaskAllEntity.class,id);
        CriteriaQuery cq = new CriteriaQuery(RiskManageTaskEntity.class, dataGrid);
        try {
            cq.eq("taskAllId",riskManageTaskAllEntity.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        List<RiskManageTaskEntity> riskManageTaskEntityList = this.systemService.getListByCriteriaQuery(cq,false);
        List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityListTemp = new ArrayList<>();
        Integer count = 0;
        String sql = "SELECT risk_identification_id,hazard_factors_id,IFNULL(d.departname,'') departname,IFNULL(u.realname,'') realname from t_b_risk_factors_rel rfr LEFT JOIN t_s_depart d on rfr.manage_depart = d.id LEFT JOIN t_s_base_user u on u.id = rfr.manage_user";
        List<Map<String, Object>> list = systemService.findForJdbc(sql);
        Map<String, String> relMap = new HashMap<>();
        if (list !=null && list.size()>0) {
            for (Map<String, Object> obj : list) {
                String riskId = String.valueOf(obj.get("risk_identification_id"));
                String hazardId = String.valueOf(obj.get("hazard_factors_id"));
                String departname = String.valueOf(obj.get("departname"));
                String realname = String.valueOf(obj.get("realname"));
                relMap.put(riskId+hazardId, departname+" - "+realname);
            }
        }
        String riskId="";
        String hazardId ="";
        if (riskManageTaskEntityList != null && riskManageTaskEntityList.size() > 0) {
            for (RiskManageTaskEntity entity : riskManageTaskEntityList) {
                List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityList = entity.getRiskManageHazardFactorEntityList();
                if (riskManageHazardFactorEntityList != null && riskManageHazardFactorEntityList.size() > 0) {
                    for (RiskManageHazardFactorEntity riskManageHazardFactorEntity : riskManageHazardFactorEntityList) {
                        if (riskManageHazardFactorEntity.getRisk() != null) {
                            riskId = riskManageHazardFactorEntity.getRisk().getId();
                            riskManageHazardFactorEntity.getRisk().setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type", riskManageHazardFactorEntity.getRisk().getRiskType()));
                            riskManageHazardFactorEntity.getRisk().setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level", riskManageHazardFactorEntity.getRisk().getRiskLevel()));
                            String manageLevelTemp = DicUtil.getTypeNameByCode("identifi_mange_level", riskManageHazardFactorEntity.getRisk().getManageLevel());
                            if(StringUtil.isNotEmpty(manageLevelTemp)){
                                riskManageHazardFactorEntity.getRisk().setManageLevelTemp(DicUtil.getTypeNameByCode("identifi_mange_level", riskManageHazardFactorEntity.getRisk().getManageLevel()));
                            }else {
                                riskManageHazardFactorEntity.getRisk().setManageLevelTemp(riskManageHazardFactorEntity.getRisk().getManageLevel());
                            }
                        }
                        if (riskManageHazardFactorEntity.getHazardFactor() != null) {
                            hazardId=riskManageHazardFactorEntity.getHazardFactor().getId();
                            riskManageHazardFactorEntity.getHazardFactor().setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level", riskManageHazardFactorEntity.getHazardFactor().getRiskLevel()));
                            riskManageHazardFactorEntity.getHazardFactor().setMajorTemp(DicUtil.getTypeNameByCode("major", riskManageHazardFactorEntity.getHazardFactor().getMajor()));
                        }
                        count++;
                        riskManageHazardFactorEntity.setNumTemp(String.valueOf(count));
                        String departorrealname = relMap.get(riskId+hazardId);
                        if(StringUtil.isNotEmpty(departorrealname)&&!departorrealname.equals(" - ")){
                            riskManageHazardFactorEntity.setDepartNameTemp(departorrealname.split(" - ")[0]);
                            riskManageHazardFactorEntity.setRealNameTemp(departorrealname.split(" - ")[1]);
                        }
                        riskManageHazardFactorEntityListTemp.add(riskManageHazardFactorEntity);
                    }
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_riskListTask.xlsx");
        String xinan = ResourceUtil.getConfigByName("xinan");
        if(xinan.equals("true")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_riskListTaskxinan.xlsx");
        }
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,List<RiskManageHazardFactorEntity>> map = new HashMap<String,List<RiskManageHazardFactorEntity>>();
        map.put("list", riskManageHazardFactorEntityListTemp);
        modelMap.put(NormalExcelConstants.FILE_NAME,"风险管控列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }


    @RequestMapping(params = "exportXlsPost")
    public String exportXlsPost(RiskManageEntity riskManage, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        String id = request.getParameter("id");
        RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.get(RiskManageTaskAllEntity.class,id);
        CriteriaQuery cq = new CriteriaQuery(RiskManagePostTaskEntity.class, dataGrid);
        try {
            cq.eq("postTaskAllId",riskManageTaskAllEntity.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        List<RiskManagePostTaskEntity> riskManagePostTaskEntityList = this.systemService.getListByCriteriaQuery(cq,false);
        List<RiskManagePostHazardFactorEntity> riskManagePostHazardFactorEntityListTemp = new ArrayList<>();
        if (riskManagePostTaskEntityList != null && riskManagePostTaskEntityList.size() > 0) {
            for (RiskManagePostTaskEntity entity : riskManagePostTaskEntityList) {
                List<RiskManagePostHazardFactorEntity> riskManagePostHazardFactorEntityList = entity.getRiskManagePostHazardFactorEntityList();
                if (riskManagePostHazardFactorEntityList != null && riskManagePostHazardFactorEntityList.size() > 0) {
                    for (RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity : riskManagePostHazardFactorEntityList) {
                        if (riskManagePostHazardFactorEntity.getPostRisk() != null) {
                            riskManagePostHazardFactorEntity.getPostRisk().setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type", riskManagePostHazardFactorEntity.getPostRisk().getRiskType()));
                            riskManagePostHazardFactorEntity.getPostRisk().setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level", riskManagePostHazardFactorEntity.getPostRisk().getRiskLevel()));
                        }
                        riskManagePostHazardFactorEntityListTemp.add(riskManagePostHazardFactorEntity);
                    }
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_riskListTaskPost.xlsx");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,List<RiskManagePostHazardFactorEntity>> map = new HashMap<String,List<RiskManagePostHazardFactorEntity>>();
        map.put("list", riskManagePostHazardFactorEntityListTemp);
        modelMap.put(NormalExcelConstants.FILE_NAME,"风险管控列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }


    @RequestMapping(params = "endTask")
    @ResponseBody
    public AjaxJson endTask(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "结束成功";
        try{
            String id = request.getParameter("id");
            RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.get(RiskManageTaskAllEntity.class,id);
            riskManageTaskAllEntity.setStatus("1");
            systemService.saveOrUpdate(riskManageTaskAllEntity);
        }catch(Exception e){
            e.printStackTrace();
            message = "结束失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


}



