package com.sddb.buss.riskmanage.controller;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.identification.entity.RiskIdentificationPostEntity;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.buss.riskdata.entity.HazardFactorsPostEntity;
import com.sddb.buss.riskmanage.entity.*;
import com.sddb.common.Constants;
import com.sdzk.buss.api.service.WeChartGetToken;
import com.sdzk.buss.api.utils.WebChatUtil;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.service.ReportToProvinceService;
import com.sdzk.buss.web.common.service.SfService;
import com.sdzk.buss.web.common.task.ReportToProvinceTask;
import com.sdzk.buss.web.common.utils.SMSSenderUtil;
import com.sdzk.buss.web.gjj.entity.SfRiskControlEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleStepEntity;
import com.sdzk.buss.web.quartz.QuartzJob;
import com.sdzk.buss.web.riskcontrol.entity.TBTaskManagerOrder;
import com.sdzk.buss.web.riskcontrol.entity.TBTaskOrderHidden;
import net.sf.json.JSONObject;
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
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
@RequestMapping("/riskManageResultController")
public class RiskManageResultController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RiskManageResultController.class);

	@Autowired
	private SystemService systemService;

    @Autowired
    private SfService sfService;

    @Autowired
    private WeChartGetToken weChartGetToken;


    /**
     * 管控的所有任务
     * @param request
     * @return
     */
    @RequestMapping(params = "taskList")
    public ModelAndView taskList(HttpServletRequest request, HttpServletResponse response) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageAllTaskList");
    }


    @RequestMapping(params = "datagrid")
    public void datagrid(RiskManageTaskAllEntity riskManageTaskAllEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String manageType = request.getParameter("manageType");
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        if (queryHandleStatus == null || queryHandleStatus.equals("")) {
            queryHandleStatus = "0";
        }
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
             // cq.eq("status","1");
            if(!queryHandleStatus.equals("all")){
                cq.add(Restrictions.sqlRestriction(" this_.id  in (SELECT task_all_id from t_b_risk_manage_task WHERE handle_status = '"+queryHandleStatus+"' AND  manage_type='" + manageType + "')"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 管控结果
     * @param request
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        String taskAllId = request.getParameter("taskAllId");
        request.setAttribute("taskAllId",taskAllId);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageResultList");
    }

    /**
     * 管控结果
     * @param request
     * @return
     */
    @RequestMapping(params = "listNew")
    public ModelAndView listNew(HttpServletRequest request, HttpServletResponse response) {
        String taskAllId = request.getParameter("taskAllId");
        request.setAttribute("taskAllId",taskAllId);
        String jinda = ResourceUtil.getConfigByName("jinda");
        request.setAttribute("jinda",jinda);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageResultListNew");
    }

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
            String addressName = request.getParameter("risk.address.address");
            if(StringUtil.isNotEmpty(addressName)){
                cq.like("risk_address.address","%"+addressName+"%");
            }
            String postName = request.getParameter("risk.post.postName");
            if(StringUtil.isNotEmpty(postName)){
                cq.like("risk_post.postName","%"+postName+"%");
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

            String queryHandleStatus = request.getParameter("queryHandleStatus");
            if(StringUtils.isEmpty(queryHandleStatus)){
                queryHandleStatus = Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED;
            } else if("all".equals(queryHandleStatus)){
                queryHandleStatus = "";
            }
            if (StringUtils.isNotBlank(queryHandleStatus)) {
                cq.eq("handleStatus", queryHandleStatus);
            }
            String taskAllId = request.getParameter("taskAllId");
            if(StringUtil.isNotEmpty(taskAllId)){
                cq.eq("taskAllId", taskAllId);
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
                riskManageTaskEntity.setHazardFactorNumFinished(numFinished + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 危害因素列表
     * @param request
     * @return
     */
    @RequestMapping(params = "hazardFactorList")
    public ModelAndView hazardFactorList(HttpServletRequest request, HttpServletResponse response) {
        String taskManageId = request.getParameter("taskManageId");
        request.setAttribute("taskManageId",taskManageId);
        String load = request.getParameter("load");
        request.setAttribute("load",load);
        String taskAllId = request.getParameter("taskAllId");
        request.setAttribute("taskAllId",taskAllId);
        return new ModelAndView("com/sddb/buss/web/riskmanage/hazardFactorsList");
    }

    //
    @RequestMapping(params = "hazardFactorDatagrid")
    public void hazardFactorDatagrid(RiskManageHazardFactorEntity riskManageHazardFactorEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskManageHazardFactorEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManageHazardFactorEntity, request.getParameterMap());

        try{
            String handleStatus = request.getParameter("queryHandleStatus2");
            if(StringUtil.isEmpty(handleStatus)){
                handleStatus = Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED;
            } else if ("all".equals(handleStatus)){
                handleStatus = "";
            }
            if(StringUtil.isNotEmpty(handleStatus)){
                cq.eq("handleStatus",handleStatus);
            }
            cq.createAlias("hazardFactor","hazardFactor");

        }catch(Exception e){
            e.printStackTrace();
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

                int riskNum = 0;
                List<RiskManageRelRisk> riskList = entity.getRiskList();
                if(null!=riskList && riskList.size()>0){
                    riskNum = riskList.size();
                }
                entity.setRiskCount(riskNum + "");

                int hdNum = 0;
                List<RiskManageRelHd> hdList = entity.getHdList();
                if(null!=hdList && hdList.size()>0){
                    hdNum = hdList.size();
                }
                entity.setHdCount(hdNum + "");
                entity.setHfManageMeasure(map.get(entity.getRisk().getId()+entity.getHazardFactor().getId()));
                entity.setHfLevel(levelMap.get(entity.getRisk().getId()+entity.getHazardFactor().getId()));
            }
        }

        TagUtil.datagrid(response, dataGrid);
    }


    //落实评估
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(RiskManageHazardFactorEntity riskManageHazardFactorEntity, HttpServletRequest request, HttpServletResponse response) {
        String id = riskManageHazardFactorEntity.getId();
        riskManageHazardFactorEntity = this.systemService.getEntity(RiskManageHazardFactorEntity.class,id);
        request.setAttribute("riskManageHazardFactor",riskManageHazardFactorEntity);
        request.setAttribute("hazardFactor",riskManageHazardFactorEntity.getHazardFactor());
        String sql = "SELECT hfManageMeasure from t_b_risk_factors_rel WHERE risk_identification_id = '"+riskManageHazardFactorEntity.getRisk().getId()+"' and  hazard_factors_id = '"+riskManageHazardFactorEntity.getHazardFactor().getId()+"'";
        List<String> hfManageMeasure = systemService.findListbySql(sql);
        request.setAttribute("hfManageMeasure",hfManageMeasure.get(0));
        String levelSql = "SELECT hfLevel from t_b_risk_factors_rel WHERE risk_identification_id = '"+riskManageHazardFactorEntity.getRisk().getId()+"' and  hazard_factors_id = '"+riskManageHazardFactorEntity.getHazardFactor().getId()+"'";
        List<String> hfLevel = systemService.findListbySql(levelSql);
        request.setAttribute("hfLevel",hfLevel.get(0));
        String load = request.getParameter("load");
        request.setAttribute("load",load);
        if(StringUtil.isNotEmpty(riskManageHazardFactorEntity.getImplDetail())){
            request.setAttribute("implDetail",riskManageHazardFactorEntity.getImplDetail());
        }else{
            request.setAttribute("implDetail","已落实");
            if(StringUtil.isNotEmpty(load)){
                if(load.equals("detail")){
                    request.setAttribute("implDetail","");
                }
            }
        }
        return new ModelAndView("com/sddb/buss/web/riskmanage/hazardFactorsUpdate");
    }

    //批量落实评估
    @RequestMapping(params = "goAllUpdate")
    public ModelAndView goAllUpdate(RiskManageHazardFactorEntity riskManageHazardFactorEntity, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        request.setAttribute("ids",ids);
        return new ModelAndView("com/sddb/buss/web/riskmanage/hazardFactorsUpdateAll");
    }



    //落实评估
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(RiskManageHazardFactorEntity riskManageHazardFactorEntity, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "管控任务结果更新成功";
        RiskManageHazardFactorEntity entity = this.systemService.getEntity(RiskManageHazardFactorEntity.class,riskManageHazardFactorEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(riskManageHazardFactorEntity, entity);
            entity.setHandleStatus("1");
        }catch (Exception e){
            e.printStackTrace();
        }
        this.systemService.saveOrUpdate(entity);


        RiskManageTaskEntity task = entity.getRiskManageTaskEntity();
        List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityList = task.getRiskManageHazardFactorEntityList();
        int numFinished = 0;
        if(null!=riskManageHazardFactorEntityList && riskManageHazardFactorEntityList.size()>0){

            for(int i=0;i<riskManageHazardFactorEntityList.size();i++){
                if(StringUtil.isNotEmpty(riskManageHazardFactorEntityList.get(i).getImplDetail())){
                    numFinished ++;
                }
            }
        }
        if(numFinished>=riskManageHazardFactorEntityList.size()){
            task.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
            this.systemService.saveOrUpdate(task);
            String sql = "SELECT id FROM t_b_risk_manage_task WHERE handle_status = '0' AND task_all_id = '"+task.getTaskAllId()+"'";

            //保存国家局上报信息
            sfService.saveSfRiskControl(task.getTaskAllId(),task.getRisk().getId());

            List<String> temp = this.systemService.findListbySql(sql);
            if(temp==null||temp.size()==0){
                RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,task.getTaskAllId());
                riskManageTaskAllEntity.setStatus("1");
                this.systemService.saveOrUpdate(riskManageTaskAllEntity);
            }
        }

        j.setMsg(message);
        return j;
    }


    //批量落实评估
    @RequestMapping(params = "doAllUpdate")
    @ResponseBody
    public AjaxJson doAllUpdate(RiskManageHazardFactorEntity riskManageHazardFactorEntity, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "管控任务结果更新成功";
        String ids= request.getParameter("ids");
        String implDetail = request.getParameter("implDetail");
        String handleStatus = request.getParameter("handleStatus");
        handleStatus = "1";
        String remark = request.getParameter("remark");
        for (String id:ids.split(",")){
            RiskManageHazardFactorEntity entity = this.systemService.getEntity(RiskManageHazardFactorEntity.class,id);
            entity.setImplDetail(implDetail);
            entity.setHandleStatus(handleStatus);
            entity.setRemark(remark);
            this.systemService.saveOrUpdate(entity);

            RiskManageTaskEntity task = entity.getRiskManageTaskEntity();
            List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityList = task.getRiskManageHazardFactorEntityList();
            int numFinished = 0;
            if(null!=riskManageHazardFactorEntityList && riskManageHazardFactorEntityList.size()>0){

                for(int i=0;i<riskManageHazardFactorEntityList.size();i++){
                    if(StringUtil.isNotEmpty(riskManageHazardFactorEntityList.get(i).getImplDetail())){
                        numFinished ++;
                    }
                }
            }
            if(numFinished>=riskManageHazardFactorEntityList.size()){
                task.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                this.systemService.saveOrUpdate(task);
                String sql = "SELECT id FROM t_b_risk_manage_task WHERE handle_status = '0' AND task_all_id = '"+task.getTaskAllId()+"'";

                //保存国家局上报信息
                sfService.saveSfRiskControl(task.getTaskAllId(),task.getRisk().getId());

                List<String> temp = this.systemService.findListbySql(sql);
                if(temp==null||temp.size()==0){
                    RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,task.getTaskAllId());
                    riskManageTaskAllEntity.setStatus("1");
                    this.systemService.saveOrUpdate(riskManageTaskAllEntity);
                }
            }

        }
        j.setMsg(message);
        return j;
    }


    //显示危害因素关联的风险列表
    @RequestMapping(params = "showriskList")
    public ModelAndView showriskList(HttpServletRequest request, HttpServletResponse response) {
//        String riskManageHazardFactorId = request.getParameter("riskManageHazardFactorId");
//        request.setAttribute("riskManageHazardFactorId",riskManageHazardFactorId);
//        return new ModelAndView("com/sddb/buss/web/riskmanage/riskIdentificationList");
//
        String riskManageHazardFactorId = request.getParameter("riskManageHazardFactorId");
        request.setAttribute("riskManageHazardFactorId",riskManageHazardFactorId);
        RiskManageHazardFactorEntity riskManageHazardFactorEntity = systemService.getEntity(RiskManageHazardFactorEntity.class,riskManageHazardFactorId);
        String manageType = riskManageHazardFactorEntity.getRiskManageTaskEntity().getManageType();

        String identificationType = "riskManage_" + manageType;
        request.setAttribute("identificationType",identificationType);
        String identificationTypeName = DicUtil.getTypeNameByCode("identifi_from", "riskManage_" + manageType);
        request.setAttribute("identificationTypeName",identificationTypeName);

        return new ModelAndView("com/sddb/buss/web/identification/riskIdentificationList");
    }


    @RequestMapping(params = "showriskListTaskAll")
    public ModelAndView showriskListTaskAll(HttpServletRequest request, HttpServletResponse response) {
//        String riskManageHazardFactorId = request.getParameter("riskManageHazardFactorId");
//        request.setAttribute("riskManageHazardFactorId",riskManageHazardFactorId);
//        return new ModelAndView("com/sddb/buss/web/riskmanage/riskIdentificationList");
//
        String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
        if(StringUtil.isNotEmpty(riskManageTaskAllId)){
            request.setAttribute("riskManageTaskAllId",riskManageTaskAllId);
            RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,riskManageTaskAllId);
            String manageType = riskManageTaskAllEntity.getManageType();

            String identificationType = "riskManage_" + manageType;
            request.setAttribute("identificationType",identificationType);
            String identificationTypeName = DicUtil.getTypeNameByCode("identifi_from", "riskManage_" + manageType);
            request.setAttribute("identificationTypeName",identificationTypeName);
        }
        String riskManageTaskAllIdYH = request.getParameter("riskManageTaskAllIdYH");
        if(StringUtil.isNotEmpty(riskManageTaskAllIdYH)){
            request.setAttribute("riskManageTaskAllIdYH",riskManageTaskAllIdYH);
            String identificationType = "2";
            request.setAttribute("identificationType",identificationType);
            String  identificationTypeName = DicUtil.getTypeNameByCode("identifi_from", identificationType);
            request.setAttribute("identificationTypeName",identificationTypeName);
        }
        return new ModelAndView("com/sddb/buss/web/identification/riskIdentificationList");
    }

    //
    @RequestMapping(params = "hazardFactorRelRiskDatagrid")
    public void hazardFactorRelRiskDatagrid(RiskManageRelRisk riskManageRelRisk, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskManageRelRisk.class,dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManageRelRisk, request.getParameterMap());
        cq.createAlias("risk","risk");
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "addRisk")
    public ModelAndView addRisk(HttpServletRequest req) {
        String riskManageHazardFactorId = req.getParameter("riskManageHazardFactorId");
        req.setAttribute("riskManageHazardFactorId",riskManageHazardFactorId);//管控危害因素id

        RiskManageHazardFactorEntity riskManageHazardFactorEntity = this.systemService.getEntity(RiskManageHazardFactorEntity.class,riskManageHazardFactorId);
        RiskManageTaskEntity riskManageTaskEntity = riskManageHazardFactorEntity.getRiskManageTaskEntity();
        RiskIdentificationEntity risk = riskManageTaskEntity.getRisk();
        req.setAttribute("risk",risk);
        req.setAttribute("riskManageTaskEntity",riskManageTaskEntity);

        String redirectUrl = "riskIdentificationController.do?goAdd&identificationType=riskManage_" + riskManageTaskEntity.getManageType();
        if(null!=risk.getAddress()) {
            redirectUrl = redirectUrl + "&addressId=" + risk.getAddress().getId();
        }
        if(null!=risk.getPost()) {
            redirectUrl = redirectUrl + "&postId=" + risk.getPost().getId();
        }
        //辨识人员默认当前登录人
        TSUser user = ResourceUtil.getSessionUserName();
        redirectUrl = redirectUrl + "&userId=" + user.getId();

        //风险类型默认当前危害因素的风险类型
        String riskType = riskManageHazardFactorEntity.getHazardFactor().getRiskType();
        redirectUrl = redirectUrl + "&riskType=" + riskType;

        redirectUrl = redirectUrl + "&riskManageHazardFactorId=" + riskManageHazardFactorId;

        //return new ModelAndView("redirect:com/sddb/buss/web/riskmanage/addHiddenDanger");
        return new ModelAndView(new RedirectView(redirectUrl));
    }

    @RequestMapping(params = "addRiskNew")
    public ModelAndView addRiskNew(HttpServletRequest req) {
        String riskManageTaskId = req.getParameter("riskManageTaskId");


        RiskManageTaskEntity riskManageTaskEntity = this.systemService.getEntity(RiskManageTaskEntity.class,riskManageTaskId);
        RiskIdentificationEntity risk = riskManageTaskEntity.getRisk();
        req.setAttribute("risk",risk);
        req.setAttribute("riskManageTaskEntity",riskManageTaskEntity);

        String redirectUrl = "riskIdentificationController.do?goAdd&identificationType=riskManage_" + riskManageTaskEntity.getManageType();
        if(null!=risk.getAddress()) {
            redirectUrl = redirectUrl + "&addressId=" + risk.getAddress().getId();
        }
        if(null!=risk.getPost()) {
            redirectUrl = redirectUrl + "&postId=" + risk.getPost().getId();
        }
        //辨识人员默认当前登录人
        TSUser user = ResourceUtil.getSessionUserName();
        redirectUrl = redirectUrl + "&userId=" + user.getId();

        //风险类型默认当前危害因素的风险类型
        String riskType = riskManageTaskEntity.getRisk().getRiskType();
        redirectUrl = redirectUrl + "&riskType=" + riskType;

        redirectUrl = redirectUrl + "&riskManageTaskId=" + riskManageTaskId;

        //return new ModelAndView("redirect:com/sddb/buss/web/riskmanage/addHiddenDanger");
        return new ModelAndView(new RedirectView(redirectUrl));
    }



    //显示危害因素关联的隐患列表
    @RequestMapping(params = "showHdList")
    public ModelAndView showHdList(HttpServletRequest request, HttpServletResponse response) {
        String riskManageHazardFactorId = request.getParameter("riskManageHazardFactorId");
        request.setAttribute("riskManageHazardFactorId",riskManageHazardFactorId);
        RiskManageHazardFactorEntity riskManageHazardFactorEntity = systemService.getEntity(RiskManageHazardFactorEntity.class,riskManageHazardFactorId);
        request.setAttribute("manageType",riskManageHazardFactorEntity.getManageType());
        String newPost = ResourceUtil.getConfigByName("newPost");
        request.setAttribute("newPost",newPost);
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        request.setAttribute("beixulou",beixulou);
        return new ModelAndView("com/sddb/buss/web/riskmanage/hiddenDangerList");
    }

    //显示危害因素关联的隐患列表
    @RequestMapping(params = "showHdListTaskAll")
    public ModelAndView showHdListTaskAll(HttpServletRequest request, HttpServletResponse response) {
        String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
        request.setAttribute("riskManageTaskAllId",riskManageTaskAllId);
        RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,riskManageTaskAllId);
        String manageType = riskManageTaskAllEntity.getManageType();
        request.setAttribute("manageType",manageType);
        String newPost = ResourceUtil.getConfigByName("newPost");
        request.setAttribute("newPost",newPost);
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        request.setAttribute("beixulou",beixulou);
        return new ModelAndView("com/sddb/buss/web/riskmanage/hiddenDangerListTaskAll");
    }

    @RequestMapping(params = "hazardFactorRelHdDatagrid")
    public void hazardFactorRelHdDatagrid(RiskManageRelHd riskManageRelHd, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskManageRelHd.class,dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManageRelHd, request.getParameterMap());
        try{
            cq.createAlias("hd","hd");
            String queryHandleStatus = request.getParameter("queryHandleStatus");
            if(StringUtils.isEmpty(queryHandleStatus)){
                queryHandleStatus = com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT;
            }
            if (com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT.equals(queryHandleStatus)) {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT};
                cq.createAlias("hd.handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            } else if (com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK.equals(queryHandleStatus)) {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK_REPORT};
                cq.createAlias("hd.handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            } else {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_REPORT, com.sdzk.buss.web.common.Constants.HANDELSTATUS_REVIEW, com.sdzk.buss.web.common.Constants.REVIEWSTATUS_PASS, com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK_CHECK};
                cq.createAlias("hd.handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            }

            String examBegin = request.getParameter("hd.examDate_begin");
            String examEnd = request.getParameter("hd.examDate_end");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtil.isNotEmpty(examBegin)){
                cq.ge("hd.examDate",sdf.parse(examBegin));
            }
            if(StringUtil.isNotEmpty(examEnd)){
                cq.le("hd.examDate", sdf.parse(examEnd));
            }
            String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
            if(StringUtil.isNotEmpty(riskManageTaskAllId)){
                cq.add(Restrictions.sqlRestriction("this_.hd_id in (  " +
                        "SELECT\n" +
                        "\trmrh.hd_id\n" +
                        "FROM\n" +
                        "\tt_b_risk_manage_task_all ta\n" +
                        "LEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id\n" +
                        "LEFT JOIN t_b_risk_manage_hazard_factor rmhf on t.id = rmhf.risk_manage_task_id\n" +
                        "LEFT JOIN t_b_risk_manage_rel_hd rmrh on rmrh.risk_manage_hazard_factor_id = rmhf.id\n" +
                        "WHERE ta.id = '"+riskManageTaskAllId+"' )"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<RiskManageRelHd> relHdList = dataGrid.getResults();
        if(null!=relHdList && relHdList.size()>0){
            for(int i=0;i<relHdList.size();i++) {
                TBHiddenDangerExamEntity e = relHdList.get(i).getHd();
                String fillCardManIds = e.getFillCardManId();
                if(StringUtil.isNotEmpty(fillCardManIds)){
                    String fillCardManNames = "";
                    String[] fillCardManIdArray = fillCardManIds.split(",");
                    for (String userid : fillCardManIdArray) {
                        TSUser user = systemService.getEntity(TSUser.class, userid);
                        fillCardManNames = fillCardManNames + user.getRealName() + ",";
                    }
                    if(StringUtil.isNotEmpty(fillCardManNames)){
                        fillCardManNames = fillCardManNames.substring(0,fillCardManNames.length()-1);
                    }
                    e.setFillCardManNames(fillCardManNames);
                }
            }
        }

        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "taskAllRelHdDatagrid")
    public void taskAllRelHdDatagrid(RiskManageRelHd riskManageRelHd, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class,dataGrid);
        try{
            String queryHandleStatus = request.getParameter("queryHandleStatus");
            if(StringUtils.isEmpty(queryHandleStatus)){
                queryHandleStatus = com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT;
            }
            if (com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT.equals(queryHandleStatus)) {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT};
                cq.createAlias("handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            } else if (com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK.equals(queryHandleStatus)) {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK_REPORT};
                cq.createAlias("handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            } else {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_REPORT, com.sdzk.buss.web.common.Constants.HANDELSTATUS_REVIEW, com.sdzk.buss.web.common.Constants.REVIEWSTATUS_PASS, com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK_CHECK};
                cq.createAlias("handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            }

            String examBegin = request.getParameter("examDate_begin");
            String examEnd = request.getParameter("examDate_end");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtil.isNotEmpty(examBegin)){
                cq.ge("examDate",sdf.parse(examBegin));
            }
            if(StringUtil.isNotEmpty(examEnd)){
                cq.le("examDate", sdf.parse(examEnd));
            }
            String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
            if(StringUtil.isNotEmpty(riskManageTaskAllId)){
                cq.add(Restrictions.sqlRestriction("this_.id in (SELECT temp.id id from (SELECT id FROM t_b_hidden_danger_exam WHERE task_all_id = '"+riskManageTaskAllId+"' UNION ALL   " +
                        "SELECT\n" +
                        "\trmrh.hd_id\n" +
                        "FROM\n" +
                        "\tt_b_risk_manage_task_all ta\n" +
                        "LEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id\n" +
                        "LEFT JOIN t_b_risk_manage_hazard_factor rmhf on t.id = rmhf.risk_manage_task_id\n" +
                        "LEFT JOIN t_b_risk_manage_rel_hd rmrh on rmrh.risk_manage_hazard_factor_id = rmhf.id\n" +
                        "WHERE ta.id = '"+riskManageTaskAllId+"' ) temp)"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<TBHiddenDangerExamEntity> tbHiddenDangerExamEntityList = dataGrid.getResults();
        if(null!=tbHiddenDangerExamEntityList && tbHiddenDangerExamEntityList.size()>0){
            for(int i=0;i<tbHiddenDangerExamEntityList.size();i++) {
                TBHiddenDangerExamEntity e = tbHiddenDangerExamEntityList.get(i);
                String fillCardManIds = e.getFillCardManId();
                if(StringUtil.isNotEmpty(fillCardManIds)){
                    String fillCardManNames = "";
                    String[] fillCardManIdArray = fillCardManIds.split(",");
                    for (String userid : fillCardManIdArray) {
                        TSUser user = systemService.getEntity(TSUser.class, userid);
                        fillCardManNames = fillCardManNames + user.getRealName() + ",";
                    }
                    if(StringUtil.isNotEmpty(fillCardManNames)){
                        fillCardManNames = fillCardManNames.substring(0,fillCardManNames.length()-1);
                    }
                    e.setFillCardManNames(fillCardManNames);
                }
            }
        }

        TagUtil.datagrid(response, dataGrid);
    }

    //addHd
    @RequestMapping(params = "addHd")
    public ModelAndView addHd(HttpServletRequest req) {
        String riskManageHazardFactorId = req.getParameter("riskManageHazardFactorId");
        req.setAttribute("riskManageHazardFactorId",riskManageHazardFactorId);//管控危害因素id

        RiskManageHazardFactorEntity riskManageHazardFactorEntity = this.systemService.getEntity(RiskManageHazardFactorEntity.class,riskManageHazardFactorId);
        RiskManageTaskEntity riskManageTaskEntity = riskManageHazardFactorEntity.getRiskManageTaskEntity();
        RiskIdentificationEntity risk = riskManageTaskEntity.getRisk();
        HazardFactorsEntity hazardFactor = riskManageHazardFactorEntity.getHazardFactor();
        req.setAttribute("risk",risk);
        req.setAttribute("riskManageTaskEntity",riskManageTaskEntity);
        req.setAttribute("hazardFactor",hazardFactor);
        String newPost = ResourceUtil.getConfigByName("newPost");
        req.setAttribute("newPost",newPost);
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        req.setAttribute("beixulou",beixulou);
        TSUser tsUser = ResourceUtil.getSessionUserName();
        req.setAttribute("tsUser",tsUser);
        String taiping = ResourceUtil.getConfigByName("taiping");
        req.setAttribute("taiping",taiping);
        String xinan = ResourceUtil.getConfigByName("xinan");
        req.setAttribute("xinan",xinan);
        String hegang = ResourceUtil.getConfigByName("hegang");
        req.setAttribute("hegang",hegang);
        return new ModelAndView("com/sddb/buss/web/riskmanage/addHiddenDanger");
    }

    @RequestMapping(params = "updateHd")
    public ModelAndView updateHd(HttpServletRequest req) {
        String id = req.getParameter("id");
        TBHiddenDangerExamEntity hde = this.systemService.getEntity(TBHiddenDangerExamEntity.class,id);
        req.setAttribute("hde",hde);
        String load = req.getParameter("load");
        req.setAttribute("load",load);
        String manageType = req.getParameter("manageType");
        req.setAttribute("manageType",manageType);
        String newPost = ResourceUtil.getConfigByName("newPost");
        req.setAttribute("newPost",newPost);
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        req.setAttribute("beixulou",beixulou);
        String taskAll = req.getParameter("taskAll");
        req.setAttribute("taskAll",taskAll);
        String taiping = ResourceUtil.getConfigByName("taiping");
        req.setAttribute("taiping",taiping);
        String hegang = ResourceUtil.getConfigByName("hegang");
        req.setAttribute("hegang",hegang);
        return new ModelAndView("com/sddb/buss/web/riskmanage/updateHiddenDanger");
    }



    @RequestMapping(params = "delHd")
    @ResponseBody
    public AjaxJson delHd(String ids,HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "隐患检查删除成功";
        try{
            for(String id:ids.split(",")){

                String sql = "delete from t_b_hidden_danger_handle where hidden_danger_id='" + id + "'";
                systemService.executeSql(sql);

                //2.清空责任人表
                sql = "delete from t_b_hidden_danger_exam_rel where bus_id='" + id + "'";
                systemService.executeSql(sql);

                sql = "delete from t_b_hidden_danger_exam where id='" + id + "'";
                systemService.executeSql(sql);

                sql = "delete from t_b_risk_manage_rel_hd where hd_id='" + id + "'";
                systemService.executeSql(sql);

                systemService.addLog("隐患检查\""+id+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "隐患检查删除失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_DEL);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    //岗位单独做一个
    /**
     * 管控结果
     * @param request
     * @return
     */
    @RequestMapping(params = "postList")
    public ModelAndView postList(HttpServletRequest request, HttpServletResponse response) {
        String postTaskAllId = request.getParameter("postTaskAllId");
        request.setAttribute("postTaskAllId",postTaskAllId);
        String jinda = ResourceUtil.getConfigByName("jinda");
        request.setAttribute("jinda",jinda);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManagePostResultList");
    }

    @RequestMapping(params = "relPostRiskDatagrid")
    public void relPostRiskDatagrid(RiskManagePostTaskEntity riskManagePostTask,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskManagePostTaskEntity.class,dataGrid);
        if(null!=riskManagePostTask && null!=riskManagePostTask.getPostRisk() && null!=riskManagePostTask.getPostRisk().getPost()){
            cq.createAlias("postRisk.post","risk_post");
            riskManagePostTask.getPostRisk().getPost().setPostName(null);
        }
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManagePostTask, request.getParameterMap());

        try{
            String queryHandleStatus = request.getParameter("queryHandleStatus");
            if(StringUtils.isEmpty(queryHandleStatus)){
                queryHandleStatus = Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED;
            } else if("all".equals(queryHandleStatus)){
                queryHandleStatus = "";
            }
            if (StringUtils.isNotBlank(queryHandleStatus)) {
                cq.eq("handleStatus", queryHandleStatus);
            }
            String postTaskAllId = request.getParameter("postTaskAllId");
            if(StringUtil.isNotEmpty(postTaskAllId)){
                cq.eq("postTaskAllId", postTaskAllId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.createAlias("postRisk", "postRisk");
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskManagePostTaskEntity> list = dataGrid.getResults();
            for (RiskManagePostTaskEntity riskManagePostTaskEntity : list) {
                int numTotal = 0;
                int numFinished = 0;
                List<RiskManagePostHazardFactorEntity> riskManagePostHazardFactorEntityList = riskManagePostTaskEntity.getRiskManagePostHazardFactorEntityList();
                if(null!=riskManagePostHazardFactorEntityList){
                    numTotal = riskManagePostHazardFactorEntityList.size();
                    for(int i=0;i<riskManagePostHazardFactorEntityList.size();i++){
                        if(StringUtil.isNotEmpty(riskManagePostHazardFactorEntityList.get(i).getImplDetail())){
                            numFinished ++;
                        }
                    }
                }
                riskManagePostTaskEntity.setHazardFactorNum(numTotal+"");
                riskManagePostTaskEntity.setHazardFactorNumFinished(numFinished + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 危害因素列表
     * @param request
     * @return
     */
    @RequestMapping(params = "hazardFactorPostList")
    public ModelAndView hazardFactorPostList(HttpServletRequest request, HttpServletResponse response) {
        String taskManagePostId = request.getParameter("taskManagePostId");
        request.setAttribute("taskManagePostId",taskManagePostId);
        String load = request.getParameter("load");
        request.setAttribute("load",load);
        String postTaskAllId = request.getParameter("postTaskAllId");
        request.setAttribute("postTaskAllId",postTaskAllId);
        return new ModelAndView("com/sddb/buss/web/riskmanage/hazardFactorsPostList");
    }

    //
    @RequestMapping(params = "hazardFactorPostDatagrid")
    public void hazardFactorPostDatagrid(RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskManagePostHazardFactorEntity.class, dataGrid);
        String hazardFactors = request.getParameter("hazardFactorPost.hazardFactors");
        if(StringUtil.isNotEmpty(hazardFactors)){
            cq.createAlias("hazardFactorPost","hazardFactorPost");
            riskManagePostHazardFactorEntity.getHazardFactorPost().setHazardFactors(null);
        }

        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManagePostHazardFactorEntity, request.getParameterMap());

        try{
            String handleStatus = request.getParameter("queryHandleStatus2");
            if(StringUtil.isEmpty(handleStatus)){
                handleStatus = Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED;
            } else if ("all".equals(handleStatus)){
                handleStatus = "";
            }
            if(StringUtil.isNotEmpty(handleStatus)){
                cq.eq("handleStatus",handleStatus);
            }
            //String hazardFactors = request.getParameter("hazardFactorPost.hazardFactors");
            if(StringUtil.isNotEmpty(hazardFactors)){
                cq.like("hazardFactorPost.hazardFactors", "%"+hazardFactors+"%");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0) {
            List<RiskManagePostHazardFactorEntity> tempList = dataGrid.getResults();
            for(RiskManagePostHazardFactorEntity entity : tempList){

                int riskNum = 0;
                List<RiskManagePostRelRisk> riskList = entity.getRiskList();
                if(null!=riskList && riskList.size()>0){
                    riskNum = riskList.size();
                }
                entity.setRiskCount(riskNum + "");

                int hdNum = 0;
                List<RiskManagePostRelHd> hdList = entity.getHdList();
                if(null!=hdList && hdList.size()>0){
                    hdNum = hdList.size();
                }
                entity.setHdCount(hdNum + "");
            }
        }

        TagUtil.datagrid(response, dataGrid);
    }

    //落实评估
    @RequestMapping(params = "goUpdatePost")
    public ModelAndView goUpdatePost(RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity, HttpServletRequest request, HttpServletResponse response) {
        String id = riskManagePostHazardFactorEntity.getId();
        riskManagePostHazardFactorEntity = this.systemService.getEntity(RiskManagePostHazardFactorEntity.class,id);
        request.setAttribute("riskManagePostHazardFactor",riskManagePostHazardFactorEntity);
        request.setAttribute("hazardFactorPost",riskManagePostHazardFactorEntity.getHazardFactorPost());
        String load = request.getParameter("load");
        request.setAttribute("load",load);
        return new ModelAndView("com/sddb/buss/web/riskmanage/hazardFactorsPostUpdate");
    }

    //批量落实评估
    @RequestMapping(params = "goAllUpdatePost")
    public ModelAndView goAllUpdatePost(RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        request.setAttribute("ids",ids);
        return new ModelAndView("com/sddb/buss/web/riskmanage/hazardFactorsPostUpdateAll");
    }



    //落实评估
    @RequestMapping(params = "doUpdatePost")
    @ResponseBody
    public AjaxJson doUpdatePost(RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "管控任务结果更新成功";
        RiskManagePostHazardFactorEntity entity = this.systemService.getEntity(RiskManagePostHazardFactorEntity.class,riskManagePostHazardFactorEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(riskManagePostHazardFactorEntity, entity);
            entity.setHandleStatus("1");
        }catch (Exception e){
            e.printStackTrace();
        }
        this.systemService.saveOrUpdate(entity);


        RiskManagePostTaskEntity postTask = entity.getRiskManagePostTaskEntity();
        List<RiskManagePostHazardFactorEntity> riskManagePostHazardFactorEntityList = postTask.getRiskManagePostHazardFactorEntityList();
        int numFinished = 0;
        if(null!=riskManagePostHazardFactorEntityList && riskManagePostHazardFactorEntityList.size()>0){

            for(int i=0;i<riskManagePostHazardFactorEntityList.size();i++){
                if(StringUtil.isNotEmpty(riskManagePostHazardFactorEntityList.get(i).getImplDetail())){
                    numFinished ++;
                }
            }
        }
        if(numFinished>=riskManagePostHazardFactorEntityList.size()){
            postTask.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
            this.systemService.saveOrUpdate(postTask);
            String sql = "SELECT id FROM t_b_risk_manage_post_task WHERE handle_status = '0' AND post_task_all_id = '"+postTask.getPostTaskAllId()+"'";
            List<String> temp = this.systemService.findListbySql(sql);
            if(temp==null||temp.size()==0){
                RiskManagePostTaskAllEntity riskManagePostTaskAllEntity = systemService.getEntity(RiskManagePostTaskAllEntity.class,postTask.getPostTaskAllId());
                if(riskManagePostTaskAllEntity!=null){
                    riskManagePostTaskAllEntity.setStatus("1");
                    this.systemService.saveOrUpdate(riskManagePostTaskAllEntity);
                }


                RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,postTask.getPostTaskAllId());
                if(riskManageTaskAllEntity!=null){
                    riskManageTaskAllEntity.setStatus("1");
                    this.systemService.saveOrUpdate(riskManageTaskAllEntity);
                }

            }
        }

        j.setMsg(message);
        return j;
    }


    //批量落实评估
    @RequestMapping(params = "doAllUpdatePost")
    @ResponseBody
    public AjaxJson doAllUpdatePost(RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "管控任务结果更新成功";
        String ids= request.getParameter("ids");
        String implDetail = request.getParameter("implDetail");
        String handleStatus = request.getParameter("handleStatus");
        handleStatus = "1";
        String remark = request.getParameter("remark");
        for (String id:ids.split(",")){
            RiskManagePostHazardFactorEntity entity = this.systemService.getEntity(RiskManagePostHazardFactorEntity.class,id);
            entity.setImplDetail(implDetail);
            entity.setHandleStatus(handleStatus);
            entity.setRemark(remark);
            this.systemService.saveOrUpdate(entity);

            RiskManagePostTaskEntity postTask = entity.getRiskManagePostTaskEntity();
            List<RiskManagePostHazardFactorEntity> riskManagePostHazardFactorEntityList = postTask.getRiskManagePostHazardFactorEntityList();
            int numFinished = 0;
            if(null!=riskManagePostHazardFactorEntityList && riskManagePostHazardFactorEntityList.size()>0){

                for(int i=0;i<riskManagePostHazardFactorEntityList.size();i++){
                    if(StringUtil.isNotEmpty(riskManagePostHazardFactorEntityList.get(i).getImplDetail())){
                        numFinished ++;
                    }
                }
            }
            if(numFinished>=riskManagePostHazardFactorEntityList.size()){
                postTask.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                this.systemService.saveOrUpdate(postTask);
                String sql = "SELECT id FROM t_b_risk_manage_post_task WHERE handle_status = '0' AND post_task_all_id = '"+postTask.getPostTaskAllId()+"'";
                List<String> temp = this.systemService.findListbySql(sql);
                if(temp==null||temp.size()==0){
                    RiskManagePostTaskAllEntity riskManagePostTaskAllEntity = systemService.getEntity(RiskManagePostTaskAllEntity.class,postTask.getPostTaskAllId());
                    if(riskManagePostTaskAllEntity!=null){
                        riskManagePostTaskAllEntity.setStatus("1");
                        this.systemService.saveOrUpdate(riskManagePostTaskAllEntity);
                    }


                    RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,postTask.getPostTaskAllId());
                    if(riskManageTaskAllEntity!=null){
                        riskManageTaskAllEntity.setStatus("1");
                        this.systemService.saveOrUpdate(riskManageTaskAllEntity);
                    }

                }
            }

        }
        j.setMsg(message);
        return j;
    }

    //addHd
    @RequestMapping(params = "addHdPost")
    public ModelAndView addHdPost(HttpServletRequest req) {
        String riskManagePostHazardFactorId = req.getParameter("riskManagePostHazardFactorId");
        req.setAttribute("riskManagePostHazardFactorId",riskManagePostHazardFactorId);//管控危害因素id

        RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity = this.systemService.getEntity(RiskManagePostHazardFactorEntity.class,riskManagePostHazardFactorId);
        RiskManagePostTaskEntity riskManagePostTaskEntity = riskManagePostHazardFactorEntity.getRiskManagePostTaskEntity();
        RiskIdentificationPostEntity postRisk = riskManagePostTaskEntity.getPostRisk();
        HazardFactorsPostEntity hazardFactorPost = riskManagePostHazardFactorEntity.getHazardFactorPost();
        req.setAttribute("postRisk",postRisk);
        req.setAttribute("riskManagePostTaskEntity",riskManagePostTaskEntity);
        req.setAttribute("hazardFactorPost",hazardFactorPost);
        TSUser tsUser = ResourceUtil.getSessionUserName();
        req.setAttribute("tsUser",tsUser);
        String taiping = ResourceUtil.getConfigByName("taiping");
        req.setAttribute("taiping",taiping);
        String hegang = ResourceUtil.getConfigByName("hegang");
        req.setAttribute("hegang",hegang);
        return new ModelAndView("com/sddb/buss/web/riskmanage/addHiddenDangerPost");
    }

    //显示危害因素关联的隐患列表
    @RequestMapping(params = "showHdPostList")
    public ModelAndView showHdPostList(HttpServletRequest request, HttpServletResponse response) {
        String riskManagePostHazardFactorId = request.getParameter("riskManagePostHazardFactorId");
        request.setAttribute("riskManagePostHazardFactorId",riskManagePostHazardFactorId);
        return new ModelAndView("com/sddb/buss/web/riskmanage/hiddenDangerPostList");
    }

    @RequestMapping(params = "showHdPostLisTaskAll")
    public ModelAndView showHdPostLisTaskAll(HttpServletRequest request, HttpServletResponse response) {
        String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
        request.setAttribute("riskManageTaskAllId",riskManageTaskAllId);
        return new ModelAndView("com/sddb/buss/web/riskmanage/hiddenDangerPostListTaskAll");
    }

    @RequestMapping(params = "hazardFactorPostRelHdDatagrid")
    public void hazardFactorPostRelHdDatagrid(RiskManagePostRelHd riskManagePostRelHd, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskManagePostRelHd.class,dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManagePostRelHd, request.getParameterMap());
        try{
            cq.createAlias("hd","hd");
            String queryHandleStatus = request.getParameter("queryHandleStatus");
            if(StringUtils.isEmpty(queryHandleStatus)){
                queryHandleStatus = com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT;
            }
            if (com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT.equals(queryHandleStatus)) {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT};
                cq.createAlias("hd.handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            } else if (com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK.equals(queryHandleStatus)) {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK_REPORT};
                cq.createAlias("hd.handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            } else {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_REPORT, com.sdzk.buss.web.common.Constants.HANDELSTATUS_REVIEW, com.sdzk.buss.web.common.Constants.REVIEWSTATUS_PASS, com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK_CHECK};
                cq.createAlias("hd.handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            }

            String examBegin = request.getParameter("hd.examDate_begin");
            String examEnd = request.getParameter("hd.examDate_end");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtil.isNotEmpty(examBegin)){
                cq.ge("hd.examDate",sdf.parse(examBegin));
            }
            if(StringUtil.isNotEmpty(examEnd)){
                cq.le("hd.examDate", sdf.parse(examEnd));
            }

            String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
            if(StringUtil.isNotEmpty(riskManageTaskAllId)){
                cq.add(Restrictions.sqlRestriction("this_.hd_id in (SELECT\n" +
                        "\trmrh.hd_id\n" +
                        "FROM\n" +
                        "\tt_b_risk_manage_task_all ta\n" +
                        "LEFT JOIN t_b_risk_manage_post_task t ON t.post_task_all_id = ta.id\n" +
                        "LEFT JOIN t_b_risk_manage_post_hazard_factor rmhf on t.id = rmhf.risk_manage_post_task_id\n" +
                        "LEFT JOIN t_b_risk_manage_post_rel_hd rmrh on rmrh.risk_manage_post_hazard_factor_id = rmhf.id\n" +
                        "WHERE ta.id = '"+riskManageTaskAllId+"')"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<RiskManagePostRelHd> relHdList = dataGrid.getResults();
        if(null!=relHdList && relHdList.size()>0){
            for(int i=0;i<relHdList.size();i++) {
                TBHiddenDangerExamEntity e = relHdList.get(i).getHd();
                String fillCardManIds = e.getFillCardManId();
                if(StringUtil.isNotEmpty(fillCardManIds)){
                    String fillCardManNames = "";
                    String[] fillCardManIdArray = fillCardManIds.split(",");
                    for (String userid : fillCardManIdArray) {
                        TSUser user = systemService.getEntity(TSUser.class, userid);
                        fillCardManNames = fillCardManNames + user.getRealName() + ",";
                    }
                    if(StringUtil.isNotEmpty(fillCardManNames)){
                        fillCardManNames = fillCardManNames.substring(0,fillCardManNames.length()-1);
                    }
                    e.setFillCardManNames(fillCardManNames);
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "taskAllPostRelHdDatagrid")
    public void taskAllPostRelHdDatagrid(RiskManagePostRelHd riskManagePostRelHd, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class,dataGrid);
        try{
            String queryHandleStatus = request.getParameter("queryHandleStatus");
            if(StringUtils.isEmpty(queryHandleStatus)){
                queryHandleStatus = com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT;
            }
            if (com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT.equals(queryHandleStatus)) {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_DRAFT};
                cq.createAlias("handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            } else if (com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK.equals(queryHandleStatus)) {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK_REPORT};
                cq.createAlias("handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            } else {
                String[] rollbackStatus = new String[]{com.sdzk.buss.web.common.Constants.HANDELSTATUS_REPORT, com.sdzk.buss.web.common.Constants.HANDELSTATUS_REVIEW, com.sdzk.buss.web.common.Constants.REVIEWSTATUS_PASS, com.sdzk.buss.web.common.Constants.HANDELSTATUS_ROLLBACK_CHECK};
                cq.createAlias("handleEntity", "handleEntity");
                cq.in("handleEntity.handlelStatus", rollbackStatus);
            }

            String examBegin = request.getParameter("examDate_begin");
            String examEnd = request.getParameter("examDate_end");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtil.isNotEmpty(examBegin)){
                cq.ge("examDate",sdf.parse(examBegin));
            }
            if(StringUtil.isNotEmpty(examEnd)){
                cq.le("examDate", sdf.parse(examEnd));
            }

            String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
            if(StringUtil.isNotEmpty(riskManageTaskAllId)){
                cq.add(Restrictions.sqlRestriction("this_.id in  (SELECT temp.id id from (SELECT id FROM t_b_hidden_danger_exam WHERE task_all_id = '"+riskManageTaskAllId+"' UNION ALL   " +
                        "SELECT\n" +
                        "\trmrh.hd_id\n" +
                        "FROM\n" +
                        "\tt_b_risk_manage_task_all ta\n" +
                        "LEFT JOIN t_b_risk_manage_post_task t ON t.post_task_all_id = ta.id\n" +
                        "LEFT JOIN t_b_risk_manage_post_hazard_factor rmhf on t.id = rmhf.risk_manage_post_task_id\n" +
                        "LEFT JOIN t_b_risk_manage_post_rel_hd rmrh on rmrh.risk_manage_post_hazard_factor_id = rmhf.id\n" +
                        "WHERE ta.id = '"+riskManageTaskAllId+"') temp )"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<TBHiddenDangerExamEntity> tbHiddenDangerExamEntityList = dataGrid.getResults();
        if(null!=tbHiddenDangerExamEntityList && tbHiddenDangerExamEntityList.size()>0){
            for(int i=0;i<tbHiddenDangerExamEntityList.size();i++) {
                TBHiddenDangerExamEntity e = tbHiddenDangerExamEntityList.get(i);
                String fillCardManIds = e.getFillCardManId();
                if(StringUtil.isNotEmpty(fillCardManIds)){
                    String fillCardManNames = "";
                    String[] fillCardManIdArray = fillCardManIds.split(",");
                    for (String userid : fillCardManIdArray) {
                        TSUser user = systemService.getEntity(TSUser.class, userid);
                        fillCardManNames = fillCardManNames + user.getRealName() + ",";
                    }
                    if(StringUtil.isNotEmpty(fillCardManNames)){
                        fillCardManNames = fillCardManNames.substring(0,fillCardManNames.length()-1);
                    }
                    e.setFillCardManNames(fillCardManNames);
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
    @RequestMapping(params = "addPostRisk")
    public ModelAndView addPostRisk(HttpServletRequest req) {
        String riskManagePostHazardFactorId = req.getParameter("riskManagePostHazardFactorId");
        req.setAttribute("riskManagePostHazardFactorId",riskManagePostHazardFactorId);//管控危害因素id

        RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity = this.systemService.getEntity(RiskManagePostHazardFactorEntity.class,riskManagePostHazardFactorId);
        RiskManagePostTaskEntity riskManagePostTaskEntity = riskManagePostHazardFactorEntity.getRiskManagePostTaskEntity();
        RiskIdentificationPostEntity risk = riskManagePostTaskEntity.getPostRisk();
        req.setAttribute("risk",risk);
        req.setAttribute("riskManagePostTaskEntity",riskManagePostTaskEntity);

        String redirectUrl = "riskIdentificationController.do?goAdd&identificationType=riskManage_" + riskManagePostTaskEntity.getManageType();
        //辨识人员默认当前登录人
        TSUser user = ResourceUtil.getSessionUserName();
        redirectUrl = redirectUrl + "&userId=" + user.getId();

        //风险类型默认当前危害因素的风险类型
        String riskType = riskManagePostHazardFactorEntity.getPostRisk().getRiskType();
        redirectUrl = redirectUrl + "&riskType=" + riskType;

        redirectUrl = redirectUrl + "&riskManagePostHazardFactorId=" + riskManagePostHazardFactorId;

        //return new ModelAndView("redirect:com/sddb/buss/web/riskmanage/addHiddenDanger");
        return new ModelAndView(new RedirectView(redirectUrl));
    }

    @RequestMapping(params = "addPostRiskNew")
    public ModelAndView addPostRiskNew(HttpServletRequest req) {
        String riskManageTaskId = req.getParameter("riskManageTaskId");
        RiskManagePostTaskEntity riskManagePostTaskEntity = this.systemService.getEntity(RiskManagePostTaskEntity.class,riskManageTaskId);
        RiskIdentificationPostEntity risk = riskManagePostTaskEntity.getPostRisk();
        req.setAttribute("risk",risk);
        req.setAttribute("riskManagePostTaskEntity",riskManagePostTaskEntity);

        String redirectUrl = "riskIdentificationController.do?goAdd&identificationType=riskManage_" + riskManagePostTaskEntity.getManageType();
        //辨识人员默认当前登录人
        TSUser user = ResourceUtil.getSessionUserName();
        redirectUrl = redirectUrl + "&userId=" + user.getId();

        //风险类型默认当前危害因素的风险类型
        String riskType = riskManagePostTaskEntity.getPostRisk().getRiskType();
        redirectUrl = redirectUrl + "&riskType=" + riskType;

        redirectUrl = redirectUrl + "&riskManageTaskId=" + riskManageTaskId;

        //return new ModelAndView("redirect:com/sddb/buss/web/riskmanage/addHiddenDanger");
        return new ModelAndView(new RedirectView(redirectUrl));
    }

    //显示危害因素关联的风险列表
    @RequestMapping(params = "showriskPostList")
    public ModelAndView showriskPostList(HttpServletRequest request, HttpServletResponse response) {
        String riskManagePostHazardFactorId = request.getParameter("riskManagePostHazardFactorId");
        request.setAttribute("riskManagePostHazardFactorId",riskManagePostHazardFactorId);
        RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity = systemService.getEntity(RiskManagePostHazardFactorEntity.class,riskManagePostHazardFactorId);
        String manageType = riskManagePostHazardFactorEntity.getRiskManagePostTaskEntity().getManageType();

        String identificationType = "riskManage_" + manageType;
        request.setAttribute("identificationType",identificationType);
        String identificationTypeName = DicUtil.getTypeNameByCode("identifi_from", "riskManage_" + manageType);
        request.setAttribute("identificationTypeName",identificationTypeName);

        return new ModelAndView("com/sddb/buss/web/identification/riskIdentificationList");
    }

    @RequestMapping(params = "isAddHidden")
    @ResponseBody
    public String isAddHidden(HttpServletRequest request, HttpServletResponse response){
        String isAddHidden = "1";
        String xinan = ResourceUtil.getConfigByName("xinan");
        if(xinan.equals("true")){
            String id =request.getParameter("taskAllId");
            RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.get(RiskManageTaskAllEntity.class,id);
            if(riskManageTaskAllEntity!=null){
                String manageType = riskManageTaskAllEntity.getManageType();
                Date manageTime = riskManageTaskAllEntity.getManageTime();
                String manageShit = riskManageTaskAllEntity.getManageShift();
                if(StringUtil.isNotEmpty(manageType)&&StringUtil.isNotEmpty(manageShit)&&StringUtil.isNotEmpty(manageTime)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdfTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Calendar c = Calendar.getInstance();
                    c.setTime(manageTime);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    Date outTime = null;
                    Date now = new Date();
                    String outTimeTemp = "";
                    try {
                        if(manageType.equals("team")){
                            outTimeTemp = sdf.format(c.getTime())+" 10:00";
                            outTime = sdfTemp.parse(outTimeTemp);
                            if(now.after(outTime)){
                                isAddHidden = "0";
                            }
                        }
                        if(manageType.equals("group")){
                            if(manageShit.equals("1")){
                                outTimeTemp = sdf.format(c.getTime())+" 12:00";
                                outTime = sdfTemp.parse(outTimeTemp);
                                if(now.after(outTime)){
                                    isAddHidden = "0";
                                }
                            }
                            if(manageShit.equals("2")){
                                outTimeTemp = sdf.format(c.getTime())+" 20:00";
                                outTime = sdfTemp.parse(outTimeTemp);
                                if(now.after(outTime)){
                                    isAddHidden = "0";
                                }
                            }
                            if(manageShit.equals("3")){
                                c.add(Calendar.DAY_OF_MONTH, 1);
                                outTimeTemp = sdf.format(c.getTime())+" 5:00";
                                outTime = sdfTemp.parse(outTimeTemp);
                                if(now.after(outTime)){
                                    isAddHidden = "0";
                                }
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return isAddHidden;
    }
}