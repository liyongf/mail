package com.sdzk.buss.web.dangersource.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.*;
import com.sdzk.buss.web.dangersource.service.TBDangerSourceServiceI;
import com.sdzk.buss.web.dangersource.service.UploadWorkProcess;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 17-8-14.
 */
@Controller
@RequestMapping("/tBWorkProcessManageController")
public class TBWorkProcessManageController extends BaseController {

    private static final Logger logger = Logger.getLogger(TBWorkProcessManageController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private TBDangerSourceServiceI tBDangerSourceService;
    @Autowired
    private UploadWorkProcess uploadWorkProcess;

    /**
     * 作业过程列表页面跳转
     */
    @RequestMapping(params = "list")
    public ModelAndView role() {
        return new ModelAndView("com/sdzk/buss/web/dangersource/workProcessManageList");
    }
    /**
     * 作业过程添加页面跳转
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView addorupdate() {
        return new ModelAndView("com/sdzk/buss/web/dangersource/workProcessManage-add");
    }
    /**
     * 作业过程添加
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson saveRole(TBWorkProcessManageEntity tBWorkProcessManageEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "作业过程添加成功";
        try{

            TSUser sessionUser = ResourceUtil.getSessionUserName();
            tBWorkProcessManageEntity.setId(UUIDGenerator.generate());
            tBWorkProcessManageEntity.setCreateBy(sessionUser.getUserName());
            tBWorkProcessManageEntity.setCreateName(sessionUser.getRealName());
            tBWorkProcessManageEntity.setCreateDate(new Date());
            tBWorkProcessManageEntity.setIsDelete("no");
            systemService.save(tBWorkProcessManageEntity);
            systemService.addLog(message, Globals.Log_Type_INSERT,
                    Globals.Log_Leavel_INFO);
            logger.info(message);
        }catch(Exception e){
            e.printStackTrace();
            message = "作业过程添加失败";
            throw new BusinessException(e.getMessage());
        }
        return j;
    }
    /**
     * 作业过程列表页面 加载数据
     */
    @RequestMapping(params = "workGrid")
    public void roleGrid(TBWorkProcessManageEntity tBWorkProcessManageEntity, HttpServletRequest request,
                         HttpServletResponse response, DataGrid dataGrid) {
        String name = tBWorkProcessManageEntity.getName();
        tBWorkProcessManageEntity.setName(null);
        CriteriaQuery cq = new CriteriaQuery(TBWorkProcessManageEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                tBWorkProcessManageEntity);
        cq.eq("isDelete","no");
        if(StringUtil.isNotEmpty(name)) {
            cq.like("name", "%"+name+"%");
        }
        cq.add();
        DataGridReturn dataGridReturn = this.systemService.getDataGridReturn(cq, true);
        if(dataGridReturn != null && !dataGridReturn.getRows().isEmpty()){
            List<TBWorkProcessManageEntity> tempList = dataGridReturn.getRows();
            for(TBWorkProcessManageEntity relBean : tempList){
                Map<String,Object> count = systemService.findOneForJdbc("select count(1) total from t_b_work_danger_rel where danger_id in (select id from t_b_danger_source where is_delete=0 and YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+")) and workpro_id='"+relBean.getId()+"'");
                relBean.setCount(String.valueOf(count.get("total")));
            }
        }
        TagUtil.datagrid(response, dataGrid);
        ;
    }
    /**
     * 逻辑删除作业过程
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson delRole(String ids,TBWorkProcessManageEntity tBWorkProcessManageEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        for(String id:ids.split(",")){
            tBWorkProcessManageEntity = systemService.getEntity(TBWorkProcessManageEntity.class,id);
            if(tBWorkProcessManageEntity != null){
                tBWorkProcessManageEntity.setIsDelete("yes");
                systemService.saveOrUpdate(tBWorkProcessManageEntity);
                message = "删除成功";
                systemService.addLog(message, Globals.Log_Type_DEL,
                        Globals.Log_Leavel_INFO);
            }
        }

        j.setMsg(message);
        logger.info(message);
        return j;
    }
   /* @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson delRole(TBWorkProcessManageEntity tBWorkProcessManageEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tBWorkProcessManageEntity = systemService.getEntity(TBWorkProcessManageEntity.class,tBWorkProcessManageEntity.getId());
        if(tBWorkProcessManageEntity != null){
            tBWorkProcessManageEntity.setIsDelete("yes");
            systemService.saveOrUpdate(tBWorkProcessManageEntity);
            message = "删除成功";
            systemService.addLog(message, Globals.Log_Type_DEL,
                    Globals.Log_Leavel_INFO);
        }
        j.setMsg(message);
        logger.info(message);
        return j;
    }*/
    /**
     * 跳转至编辑界面
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView addorupdate(TBWorkProcessManageEntity tBWorkProcessManageEntity, HttpServletRequest req) {
        if (tBWorkProcessManageEntity.getId() != null) {
            tBWorkProcessManageEntity = systemService.getEntity(TBWorkProcessManageEntity.class, tBWorkProcessManageEntity.getId());
            req.setAttribute("tBWorkProcessManageEntity",tBWorkProcessManageEntity);
        }
        return new ModelAndView("com/sdzk/buss/web/dangersource/workProcessManage-update");
    }
    /**
     * 保存编辑
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBWorkProcessManageEntity tBWorkProcessManageEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(tBWorkProcessManageEntity.getId())) {
            TBWorkProcessManageEntity t = systemService.getEntity(TBWorkProcessManageEntity.class, tBWorkProcessManageEntity.getId());
            t.setName(tBWorkProcessManageEntity.getName());
            t.setMajor(tBWorkProcessManageEntity.getMajor());
            message = "作业过程: " + tBWorkProcessManageEntity.getName() + "被更新成功";
            systemService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE,
                    Globals.Log_Leavel_INFO);
        } else {
            message = "作业过程: " + tBWorkProcessManageEntity.getName() + "被添加成功";
            systemService.save(tBWorkProcessManageEntity);
            systemService.addLog(message, Globals.Log_Type_INSERT,
                    Globals.Log_Leavel_INFO);
        }
        logger.info(message);
        return j;
    }

    /**
     * 加载已关联的危险源
     */
    @RequestMapping(params = "workProcessDangerSourceDatagrid")
    public void departDangerSourceDatagrid(TBWorkDangerRelEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBWorkDangerRelEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        String id = request.getParameter("workid");
        try{
            cq.eq("workprocess.id",id);
            cq.add(Restrictions.sqlRestriction(" this_.danger_id in (select id from t_b_danger_source where is_delete=0 and YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+")) "));
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        DataGridReturn dataGridReturn = this.systemService.getDataGridReturn(cq, true);
        if(dataGridReturn != null && !dataGridReturn.getRows().isEmpty()){
            List<TBWorkDangerRelEntity> tempList = dataGridReturn.getRows();
            if(tempList != null && tempList.size() > 0){
                for(TBWorkDangerRelEntity relBean : tempList){
                    //字典
                    if(relBean != null){
                        TBDangerSourceEntity bean = relBean.getDanger();
                        String sgxlStr = bean.getYeAccident();
                        if(StringUtils.isNotBlank(sgxlStr)){
                            String[] sgxlArray = sgxlStr.split(",");
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
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }


    //查询已关联的危险源  已不用
    @RequestMapping(params = "workDangerDatagrid")
    public void roleUserDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery( TBWorkDangerRelEntity.class);
        String id = request.getParameter("workid");

        //1.查询出自己已经关联的危险源
        try{
            cq.eq("workprocess.id",id);

        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        List<TBWorkDangerRelEntity> choosedList = systemService.getListByCriteriaQuery(cq,false);
        List<String> choosedangerId = new ArrayList<String>();
        if(choosedList != null && !choosedList.isEmpty()){
            for(TBWorkDangerRelEntity temp : choosedList){
                choosedangerId.add(temp.getDanger().getId());
            }
        }
        cq = new CriteriaQuery(TBDangerSourceEntity.class,dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            if(!choosedangerId.isEmpty()){
                cq.in("id",choosedangerId.toArray());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.tBDangerSourceService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }








    /**
     * 跳转关联危险源界面
     */
    @RequestMapping(params = "riskList")
    public ModelAndView userList(HttpServletRequest request) {
        request.setAttribute("id", request.getParameter("id"));
        return new ModelAndView("com/sdzk/buss/web/dangersource/processRiskList");
    }







    /**
     * 跳转危险源列表
     */
    @RequestMapping(params = "chooseDangerSource2Rel")
    public ModelAndView chooseDangerSource2Rel(HttpServletRequest request) {
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("year", DateUtils.getYear());
        return new ModelAndView("com/sdzk/buss/web/dangersource/chooseDangerSourceForPro");
    }
    /**
     * 危险源列表加载数据  方法1
     */
    @RequestMapping(params = "initChooseDangers")
    public void initChooseDangers(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery( TBWorkDangerRelEntity.class);
        String id = request.getParameter("workid");

        //1.查询出自己已经关联的危险源
        try{
            cq.eq("workprocess.id",id);
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        List<TBWorkDangerRelEntity> choosedList = systemService.getListByCriteriaQuery(cq,false);
        List<String> choosedangerId = new ArrayList<String>();
        if(choosedList != null && !choosedList.isEmpty()){
            for(TBWorkDangerRelEntity temp : choosedList){
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
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        TagUtil.datagrid(response, dataGrid);

    }
    public void queryByDangerSourceName(String dangerSourceName, CriteriaQuery cq, DataGrid dataGrid) {
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
     * 危险源列表加载数据  已不用
     */
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
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        try{
            if(!choosedangerId.isEmpty()){
                cq.notIn("id",choosedangerId.toArray());
            }

            //查询专业通用危险源，年度闭环风险，专项闭环风险
            cq.add(Restrictions.or(
                    Restrictions.eq("origin", Constants.DANGER_SOURCE_ORIGIN_NOMAL),
                    Restrictions.or(
                            Restrictions.and(
                                    Restrictions.eq("origin", Constants.DANGER_SOURCE_ORIGIN_MINE),
                                    Restrictions.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE)
                            ),
                            Restrictions.and(
                                    Restrictions.eq("origin", Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION),
                                    Restrictions.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE)
                            )
                    )
            ));

        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.tBDangerSourceService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);

    }
    /**
     * 执行关联操作
     */
    @RequestMapping(params = "saveChooseDanger")
    @ResponseBody
    public AjaxJson saveChooseDanger(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "危险源添加成功";
        String workid = request.getParameter("workId");
        String ids = request.getParameter("ids");//选择的危险源

        TBWorkProcessManageEntity twork = this.systemService.getEntity(TBWorkProcessManageEntity.class,workid);

        try{
            if(org.apache.commons.lang.StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                TBWorkDangerRelEntity tBWorkDangerRelEntity = null;
                for(String id : idArray){
                    tBWorkDangerRelEntity = new TBWorkDangerRelEntity();
                    tBWorkDangerRelEntity.setId(UUIDGenerator.generate());
                    tBWorkDangerRelEntity.setWorkprocess(twork);
                    TBDangerSourceEntity t = this.systemService.getEntity(TBDangerSourceEntity.class,id);
                    tBWorkDangerRelEntity.setDanger(t);

                    this.systemService.save(tBWorkDangerRelEntity);
                    systemService.addLog("作业过程关联危险源添加\""+tBWorkDangerRelEntity.getId()+"\"成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "作业过程危险源添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 查看危险源信息
     */
    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(TBDangerSourceEntity tBDangerSource, HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtil.isNotEmpty(id)) {
            TBWorkDangerRelEntity tBWorkDangerRelEntity = tBDangerSourceService.getEntity(TBWorkDangerRelEntity.class,id);
            tBDangerSource = tBDangerSourceService.getEntity(TBDangerSourceEntity.class, tBWorkDangerRelEntity.getDanger().getId());
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
                String yeCostTemp = DicUtil.getTypeNameByCode("hazard_fxss", yeProbability);
                tBDangerSource.setYeCostTemp(yeCostTemp);
            }
            //风险等级
            String yeRiskGrade = tBDangerSource.getYeRiskGrade();
            if(StringUtils.isNotBlank(yeRiskGrade)){
                String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", yeProbability);
                tBDangerSource.setYeRiskGradeTemp(yeRiskGradeTemp);
            }
            //风险类型
            String yeHazardCate = tBDangerSource.getYeHazardCate();
            if(StringUtils.isNotBlank(yeHazardCate)){
                String yeHazardCateTemp = DicUtil.getTypeNameByCode("hazardCate",yeHazardCate);
                tBDangerSource.setYeHazardCateTemp(yeHazardCateTemp);
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
        return new ModelAndView("com/sdzk/buss/web/dangersource/tBDangerSourceDetail");
    }

    //删除关联危险源
    @RequestMapping(params = "delProcessRisk")
    @ResponseBody
    public AjaxJson delUserRole(@RequestParam String id) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            TBWorkDangerRelEntity tBWorkDangerRelEntity = tBDangerSourceService.getEntity(TBWorkDangerRelEntity.class, id);
            systemService.delete(tBWorkDangerRelEntity);
            ajaxJson.setMsg("成功删除");
        } catch (Exception e) {
            LogUtil.log("删除失败", e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(e.getMessage());
        }
        return ajaxJson;
    }


    /**
     * 作业过程列表页面跳转
     */
    @RequestMapping(params = "chooseInveRiskPoint")
    public ModelAndView chooseInveRiskPoint(HttpServletRequest request) {
        request.setAttribute("ids", ResourceUtil.getParameter("ids"));
        request.setAttribute("month", ResourceUtil.getParameter("month"));
        request.setAttribute("from", ResourceUtil.getParameter("from"));
        return new ModelAndView("com/sdzk/buss/web/dangersource/chooseInveRiskPointList");
    }

    /**
     * 作业过程列表页面 加载数据
     */
    @RequestMapping(params = "chooseInveRiskPointDatagrid")
    public void chooseInveRiskPointDatagrid(TBWorkProcessManageEntity tBWorkProcessManageEntity, HttpServletRequest request,
                         HttpServletResponse response, DataGrid dataGrid) {
        String name = tBWorkProcessManageEntity.getName();
        tBWorkProcessManageEntity.setName(null);
        CriteriaQuery cq = new CriteriaQuery(TBWorkProcessManageEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                tBWorkProcessManageEntity);
        request.setAttribute("ids", ResourceUtil.getParameter("ids"));
        String ids = ResourceUtil.getParameter("ids");
        if (StringUtil.isNotEmpty(ids)) {
            cq.notIn("id", ids.split(","));
        }
        if (StringUtil.isNotEmpty(name)) {
            cq.like("name", "%"+name+"%");
        }
        cq.eq("isDelete","no");
        /** 从月度计划中选择 **/
        if ("plan".equals(ResourceUtil.getParameter("from"))) {
            String month = ResourceUtil.getParameter("month");
            if (StringUtil.isNotEmpty(month)) {
                month = month.substring(0, 7)+"-01";
                cq.add(Restrictions.sqlRestriction("id in (select obj_id from t_b_investigate_plan_rel rel join t_b_investigate_plan plan on rel.plan_id=plan.id where rel.rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT+"' and rel.poit_type='"+Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_WORK+"' and plan.start_time='"+month+"')"));
            } else {
                cq.add(Restrictions.sqlRestriction("id in (select obj_id from t_b_investigate_plan_rel where rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT+"' and poit_type='"+Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_WORK+"')"));
            }
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<TBWorkProcessManageEntity> tempList = dataGrid.getResults();
        if(tempList != null && !tempList.isEmpty()){
            for(TBWorkProcessManageEntity relBean : tempList){
                Map<String,Object> count = systemService.findOneForJdbc("select count(1) total from t_b_work_danger_rel where danger_id in (select id from t_b_danger_source where is_delete=0 and YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+")) and workpro_id='"+relBean.getId()+"'");
                relBean.setCount(String.valueOf(count.get("total")));
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 作业过程上报
     * 张赛超
     * */
    @RequestMapping(params = "uploadWorkProcess")
    @ResponseBody
    public AjaxJson uploadWorkProcess(String ids, HttpServletRequest request){
        return uploadWorkProcess.uploadWorkProcess(ids);
    }

}





