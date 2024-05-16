package com.sddb.buss.identification.controller;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.identification.service.ReportServiceI;
import com.sdzk.buss.web.common.taskProvince.ReportHazardFactorsTask;
import com.sdzk.buss.web.common.taskProvince.ReportRiskTask;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by lenovo on 2019/1/8.
 */
@Controller
@RequestMapping("/reportController")
public class ReportController {
    @Autowired
    private ReportRiskTask reportRiskTask;
    @Autowired
    private ReportHazardFactorsTask reportHazardFactorsTask;
    @Autowired
    private ReportServiceI reportService;
    @Autowired
    private SystemService systemService;

    @RequestMapping(params = "reportRiskToProvince")
    @ResponseBody
    public AjaxJson reportRiskToProvince(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        if(StringUtil.isNotEmpty(ids)){
           j = reportRiskTask.reportRiskWithRelatedDataToProvince(ids, com.sdzk.buss.web.common.taskProvince.Constants.REPORT_BY_PEOPLE);
        }else{
            j.setMsg("未选中任何风险！");
        }
        return j;
    }

    @RequestMapping(params = "removeRiskFromProvince")
    @ResponseBody
    public AjaxJson removeRiskFromProvince(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        if(StringUtil.isNotEmpty(ids)){
            j = reportRiskTask.removeRiskDataFromProvince(ids);
        }else{
            j.setMsg("未选中任何风险！");
        }
        return j;
    }

    @RequestMapping(params = "removeHazardFactorFromProvince")
    @ResponseBody
    public AjaxJson removeHazardFactorFromProvince(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        if(StringUtil.isNotEmpty(ids)){
            j = reportHazardFactorsTask.removeHazardFactorFromProvince(ids);
        }else{
            j.setMsg("未选中任何危害因素！");
        }
        return j;
    }

    @RequestMapping(params = "reportHazardFactorToProvince")
    @ResponseBody
    public AjaxJson reportHazardFactorToProvince(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        if(StringUtil.isNotEmpty(ids)){
            j = reportHazardFactorsTask.reportHazardFactorsToProvince(ids, com.sdzk.buss.web.common.taskProvince.Constants.REPORT_BY_PEOPLE);
        }else{
            j.setMsg("未选中任何危害因素！");
        }
        return j;
    }

    @RequestMapping(params = "goReportRiskList")
    public ModelAndView goReportRiskList(HttpServletRequest request) {
        String newPost = ResourceUtil.getConfigByName("newPost");
        request.setAttribute("newPost",newPost);
        return new ModelAndView("com/sddb/buss/web/identification/reportRiskList");
    }

    @RequestMapping(params = "reportRiskListDataGrid")
    public void reportRiskListDataGrid( HttpServletRequest request,HttpServletResponse response,DataGrid dataGrid) {
        CriteriaQuery query = reportService.queryRiskReportList(request,dataGrid);
        this.systemService.getDataGridReturn(query, true);
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


    @RequestMapping(params = "goReportHazardFactorsList")
    public ModelAndView goReportHazardFactorsList(){
        return new ModelAndView("com/sddb/buss/web/riskdata/reportHazardFactorsList");
    }

    @RequestMapping(params = "reportHazardFactorsDataGrid")
    public void reportHazardFactorsDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery query = reportService.queryHazardFactorReportList(request,dataGrid);
        this.systemService.getDataGridReturn(query, true);
        TagUtil.datagrid(response, dataGrid);
    }
}
