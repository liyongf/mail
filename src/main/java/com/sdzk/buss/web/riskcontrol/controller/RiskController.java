package com.sdzk.buss.web.riskcontrol.controller;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.riskcontrol.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Scope("prototype")
@Controller
@RequestMapping("/riskController")
public class RiskController {

    @Autowired
    private SystemService systemService;



    @RequestMapping(params = "addressControl")
    public ModelAndView addressControl(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/riskcontrol/addressControl");
    }

    @RequestMapping(params = "dangerSourceControl")
    public ModelAndView dangerSourceControl(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/riskcontrol/dangerSourceControl");
    }

    @RequestMapping(params = "taskManager")
    public ModelAndView taskManager(HttpServletRequest request) {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar calstr = Calendar.getInstance();
        //本月
        calstr.add(Calendar.MONTH, 0);
        //设置为1号为本月第一天
        calstr.set(Calendar.DAY_OF_MONTH,1);
        String first = sm.format(calstr.getTime());

        //获取当前月最后一天
        Calendar calast = Calendar.getInstance();
        //设置当月为最后一天
        calast.set(Calendar.DAY_OF_MONTH, calast.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = sm.format(calast.getTime());

        request.setAttribute("start",first);
        request.setAttribute("end",last);
        return new ModelAndView("com/sdzk/buss/web/riskcontrol/taskManager");
    }


    @RequestMapping(params = "taskManagerCheck")
    public ModelAndView taskManagerCheck(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/riskcontrol/taskManagerCheck");
    }


    @RequestMapping(params = "taskManagerCheckDatagrid")
    public void taskManagerCheckDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TBTaskManagerOrder.class, dataGrid);
//        String inveDate_begin = request.getParameter("inveDate_begin");
//        String inveDate_end	 = request.getParameter("inveDate_end");
//        String inveType	 = request.getParameter("inveType");
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        if(StringUtils.isBlank(queryHandleStatus)){
            queryHandleStatus = "1";
        }
        //查询条件组装器
        try{
            cq.createAlias("taskManagerControl","taskManagerControl");
            cq.eq("taskManagerControl.belongUser.id",user.getId());
            cq.eq("status",queryHandleStatus);
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
//            if(StringUtils.isNotBlank(inveDate_begin)) {
//                cq.ge("inveDate", sm.parse(inveDate_begin));
//            }
//            if(StringUtils.isNotBlank(inveDate_end)) {
//                cq.le("inveDate", sm.parse(inveDate_end));
//            }
//            if(StringUtils.isNotBlank(inveType)){
//                cq.eq("inveType",inveType);
//            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;


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
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "taskManagerDatagrid")
    public void taskManagerDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TBTaskManagerControl.class, dataGrid);
        String inveDate_begin = request.getParameter("inveDate_begin");
        String inveDate_end	 = request.getParameter("inveDate_end");
        String inveType	 = request.getParameter("inveType");
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        if(StringUtils.isBlank(queryHandleStatus)){
            queryHandleStatus = "0";
        }
        //查询条件组装器
        try{
            cq.eq("belongUser.id",user.getId());
            if(queryHandleStatus.equals("1")){
                cq.notEq("status","0");
            }else if(queryHandleStatus.equals("2") || queryHandleStatus.equals("3")){
                cq.eq("status",queryHandleStatus);
            }else{
                cq.eq("status",queryHandleStatus);
            }

            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
            //获取当前月第一天：
            Calendar calstr = Calendar.getInstance();
            //本月
            calstr.add(Calendar.MONTH, 0);
            //设置为1号为本月第一天
            calstr.set(Calendar.DAY_OF_MONTH,1);
            String first = sm.format(calstr.getTime());

            //获取当前月最后一天
            Calendar calast = Calendar.getInstance();
            //设置当月为最后一天
            calast.set(Calendar.DAY_OF_MONTH, calast.getActualMaximum(Calendar.DAY_OF_MONTH));
            String last = sm.format(calast.getTime());

            if(StringUtils.isBlank(inveDate_begin)){
                inveDate_begin = first;
            }

            if(StringUtils.isBlank(inveDate_end)){
                inveDate_end = last;
            }

            cq.ge("inveDate",sm.parse(inveDate_begin));
            cq.le("inveDate",sm.parse(inveDate_end));
            if(StringUtils.isNotBlank(inveType)){
                cq.eq("inveType",inveType);
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;


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
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "dangerSourceControlDatagrid")
    public void dangerSourceControlDatagrid(TBDangerSourceControl dangerSourceControl, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();

        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceControl.class, dataGrid);

        String hazardName = request.getParameter("dangerSourceEntity.hazard.hazardName");
        String yeHazardCate = request.getParameter("dangerSourceEntity.yeHazardCate");
        String yePossiblyHazard = request.getParameter("dangerSourceEntity.yePossiblyHazard");
        String yeRiskGrade = request.getParameter("dangerSourceEntity.yeRiskGrade");
        cq.eq("belongUser.id",user.getId());
        //查询条件组装器
        try{
            cq.createAlias("dangerSourceEntity","dangerSourceEntity");
            cq.createAlias("dangerSourceEntity.hazard","hazard");
            if(StringUtils.isNotBlank(hazardName)){
                cq.like("hazard.hazardName","%"+hazardName+"%");
            }
            if(StringUtils.isNotBlank(yeHazardCate)){
                cq.eq("dangerSourceEntity.yeHazardCate",yeHazardCate);
            }
            if(StringUtils.isNotBlank(yePossiblyHazard)){
                cq.like("dangerSourceEntity.yePossiblyHazard","%"+yePossiblyHazard+"%");
            }
            if(StringUtils.isNotBlank(yeRiskGrade)){
                cq.eq("dangerSourceEntity.yeRiskGrade",yeRiskGrade);
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;


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
        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "datagrid")
    public void datagrid(TBAddressControl tBAddressInfo, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBAddressControl.class, dataGrid);
        String addressName = request.getParameter("address.address");
        TSUser user = ResourceUtil.getSessionUserName();
        //查询条件组装器
        try{
            cq.eq("belongUser.id",user.getId());
            if(StringUtils.isNotBlank(addressName)){
                cq.createAlias("address","address");
                cq.like("address.address","%"+addressName+"%");
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;


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
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goRelAddressList")
    public ModelAndView goRelAddressList(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/riskcontrol/addressList");
    }

    @RequestMapping(params = "goOrderAddressList")
    public ModelAndView goOrderAddressList(HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        request.setAttribute("taskId",taskId);

        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");

        //获取当前月第一天：
        Calendar calstr = Calendar.getInstance();
        //本月
        calstr.add(Calendar.MONTH, 0);
        //设置为1号为本月第一天
        calstr.set(Calendar.DAY_OF_MONTH,1);
        String first = sm.format(calstr.getTime());

        //获取当前月最后一天
        Calendar calast = Calendar.getInstance();
        //设置当月为最后一天
        calast.set(Calendar.DAY_OF_MONTH, calast.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = sm.format(calast.getTime());

        request.setAttribute("start",first);
        request.setAttribute("end",last);

        return new ModelAndView("com/sdzk/buss/web/riskcontrol/orderAddressList");
    }

    @RequestMapping(params = "goRelDangerSourceList")
    public ModelAndView goRelDangerSourceList(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/riskcontrol/dangerSourceList");
    }


    @RequestMapping(params = "getDangerSourceDatagrid")
    public void getDangerSourceDatagrid(TBDangerSourceControl dangerSourceControl, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String hazardName = request.getParameter("hazard.hazardName");
        String yeHazardCate = request.getParameter("yeHazardCate");
        String yePossiblyHazard = request.getParameter("yePossiblyHazard");
        String yeRiskGrade = request.getParameter("yeRiskGrade");


        //查询条件组装器
        try{
            cq.add(Restrictions.sqlRestriction(" this_.id not in (select dangersource_id from t_b_dangersource_cnotrol where user_id = '"+user.getId()+"' )"));
            cq.notIn("origin", new String[]{Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION,Constants.DANGER_SOURCE_ORIGIN_NOMAL});

            cq.createAlias("hazard","hazard");
            if(StringUtils.isNotBlank(hazardName)){
                cq.like("hazard.hazardName","%"+hazardName+"%");
            }
            if(StringUtils.isNotBlank(yeHazardCate)){
                cq.eq("yeHazardCate",yeHazardCate);
            }
            if(StringUtils.isNotBlank(yePossiblyHazard)){
                cq.like("yePossiblyHazard","%"+yePossiblyHazard+"%");
            }
            if(StringUtils.isNotBlank(yeRiskGrade)){
                cq.eq("yeRiskGrade",yeRiskGrade);
            }

        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

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
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "getAddressDatagrid")
    public void getAddressDatagrid(TBAddressInfoEntity tBAddressInfo, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        String addressName = request.getParameter("address");
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
        tBAddressInfo.setAddress(null);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAddressInfo, request.getParameterMap());
        try{
            cq.add(Restrictions.sqlRestriction(" this_.id not in (select address_id from t_b_address_cnotrol where user_id = '"+user.getId()+"' )"));
            cq.eq("isDelete", Constants.IS_DELETE_N);
            cq.eq("isShowData", Constants.IS_SHOWDATA_Y);
            if(StringUtils.isNotBlank(addressName)){
                cq.like("address","%"+addressName+"%");
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

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
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "saveRelChooseAddress")
    @ResponseBody
    public AjaxJson saveRelChooseAddress(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        String ids = request.getParameter("ids");//选择的风险点
        TSUser user = ResourceUtil.getSessionUserName();
        try{
            if(StringUtil.isNotEmpty(ids)){
                String[] idArray = ids.split(",");
                for(String id : idArray){
                    TBAddressControl bean = new TBAddressControl();
                    TBAddressInfoEntity entity= systemService.getEntity(TBAddressInfoEntity.class,id);
                    bean.setAddress(entity);
                    bean.setBelongUser(user);
                    bean.setCreateBy(user.getUserName());
                    bean.setCreateName(user.getRealName());
                    bean.setCreateDate(new Date());
                    systemService.save(bean);
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


    @RequestMapping(params = "saveRelChooseDangerSource")
    @ResponseBody
    public AjaxJson saveRelChooseDangerSource(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        String ids = request.getParameter("ids");//选择的风险
        TSUser user = ResourceUtil.getSessionUserName();
        try{
            if(StringUtil.isNotEmpty(ids)){
                String[] idArray = ids.split(",");
                for(String id : idArray){
                    TBDangerSourceControl bean = new TBDangerSourceControl();
                    TBDangerSourceEntity entity= systemService.getEntity(TBDangerSourceEntity.class,id);
                    bean.setDangerSourceEntity(entity);
                    bean.setBelongUser(user);
                    bean.setCreateBy(user.getUserName());
                    bean.setCreateName(user.getRealName());
                    bean.setCreateDate(new Date());
                    systemService.save(bean);
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


    @RequestMapping(params = "closeTaskOrder")
    @ResponseBody
    public AjaxJson closeTaskOrder(String taskId, HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "闭合成功";
        try{
            TBTaskManagerOrder order = systemService.getEntity(TBTaskManagerOrder.class,taskId);
            order.setStatus("2");
            systemService.saveOrUpdate(order);
        }catch(Exception e){
            e.printStackTrace();
            message = "闭合失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "closeAllTaskOrder")
    @ResponseBody
    public AjaxJson closeAllTaskOrder(String ids, HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "闭合成功";
        try{
            if(StringUtils.isNotBlank(ids)) {
                for(String id : ids.split(",")) {
                    TBTaskManagerOrder order = systemService.getEntity(TBTaskManagerOrder.class, id);
                    order.setStatus("2");
                    systemService.saveOrUpdate(order);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "闭合失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "风险点列表删除成功";
        try{
            for(String id:ids.split(",")){
                TBAddressControl tBAddressInfo = systemService.getEntity(TBAddressControl.class, id);
                systemService.delete(tBAddressInfo);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "风险点列表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "deleteDanger")
    @ResponseBody
    public AjaxJson deleteDanger(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "风险删除成功";
        String ids = request.getParameter("ids");//选择的风险
        try{
            for(String id:ids.split(",")){
                TBDangerSourceControl tBAddressInfo = systemService.getEntity(TBDangerSourceControl.class, id);
                systemService.delete(tBAddressInfo);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "风险删除成功";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAddTaskManager")
    public ModelAndView goAddTaskManager(TBTaskManagerControl taskManagerControl, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(taskManagerControl.getId())) {
            taskManagerControl = systemService.getEntity(TBTaskManagerControl.class, taskManagerControl.getId());
            req.setAttribute("taskManagerControl", taskManagerControl);
        }
        return new ModelAndView("com/sdzk/buss/web/riskcontrol/taskManager-add");
    }

    @RequestMapping(params = "goUpdateTaskManager")
    public ModelAndView goUpdateTaskManager(TBTaskManagerControl taskManagerControl, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(taskManagerControl.getId())) {
            taskManagerControl = systemService.getEntity(TBTaskManagerControl.class, taskManagerControl.getId());
            req.setAttribute("taskManagerControl", taskManagerControl);
        }
        return new ModelAndView("com/sdzk/buss/web/riskcontrol/taskManager-update");
    }

    @RequestMapping(params = "doAddTaskManager")
    @ResponseBody
    public AjaxJson doAddTaskManager(TBTaskManagerControl taskManagerControl, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        TSUser user = ResourceUtil.getSessionUserName();
        message = "管控任务添加成功";
        try{
            taskManagerControl.setStatus("0");
            taskManagerControl.setBelongUser(user);
            systemService.save(taskManagerControl);
        }catch(Exception e){
            e.printStackTrace();
            message = "管控任务添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBTaskManagerControl taskManagerControl, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "三违信息更新成功";
        TBTaskManagerControl t = systemService.get(TBTaskManagerControl.class, taskManagerControl.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(taskManagerControl, t);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "三违信息更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "taskManagerOrderList")
    public ModelAndView taskManagerOrderList(HttpServletRequest request, String taskId) {
        request.setAttribute("taskId", taskId);
        return new ModelAndView("com/sdzk/buss/web/riskcontrol/taskManagerOrderList");
    }

    @RequestMapping(params = "taskManagerOrderStatistics")
    public ModelAndView taskManagerOrderStatistics(HttpServletRequest request, String taskId) {
        return new ModelAndView("com/sdzk/buss/web/riskcontrol/taskManagerOrderStatistics");
    }

    @RequestMapping(params = "statisticsDataGrid")
    public void statisticsDataGrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TBTaskManagerOrder.class, dataGrid);
        String address = request.getParameter("addressInfoEntity.address");
        String hazardName = request.getParameter("dangerSourceEntity.hazard.hazardName");
        String yeHazardCate = request.getParameter("dangerSourceEntity.yeHazardCate");
        String yeRiskGrade = request.getParameter("dangerSourceEntity.yeRiskGrade");
        String inveDate_end = request.getParameter("taskManagerControl.inveDate_end");
        String inveDate_begin = request.getParameter("taskManagerControl.inveDate_begin");
        //查询条件组装器
        try{
            cq.createAlias("taskManagerControl","taskManagerControl");
            cq.createAlias("dangerSourceEntity","dangerSourceEntity");
            cq.createAlias("dangerSourceEntity.hazard","hazard");
            cq.createAlias("addressInfoEntity","addressInfoEntity");
            cq.eq("taskManagerControl.belongUser.id",user.getId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtils.isNotBlank(inveDate_begin)){
                cq.ge("taskManagerControl.inveDate",sdf.parse(inveDate_begin));
            }
            if(StringUtils.isNotBlank(inveDate_end)){
                cq.le("taskManagerControl.inveDate",sdf.parse(inveDate_end));
            }
            if(StringUtils.isNotBlank(address)){
                cq.like("addressInfoEntity.address","%"+address+"%");
            }
            if(StringUtils.isNotBlank(hazardName)){
                cq.like("hazard.hazardName","%"+hazardName+"%");
            }
            if(StringUtils.isNotBlank(yeHazardCate)){
                cq.eq("dangerSourceEntity.yeHazardCate",yeHazardCate);
            }
            if(StringUtils.isNotBlank(yeRiskGrade)){
                cq.eq("dangerSourceEntity.yeRiskGrade",yeRiskGrade);
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;


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

        if(dataGrid != null && dataGrid.getResults() !=null && !dataGrid.getResults().isEmpty()){
            List<TBTaskManagerOrder> list = dataGrid.getResults();
            for(TBTaskManagerOrder bean : list){
                cq = new CriteriaQuery(TBTaskOrderHidden.class);
                cq.eq("taskManagerOrder.id",bean.getId());
                cq.add();
                List<TBTaskOrderHidden> retList = systemService.getListByCriteriaQuery(cq,false);
                if(retList == null || retList.isEmpty()){
                    bean.setControlTimes("0");
                }else{
                    bean.setControlTimes(retList.size()+"");
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "taskOrderDategrid")
    public void taskOrderDategrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TBTaskManagerOrder.class, dataGrid);
        String taskId = request.getParameter("taskId");
        String address = request.getParameter("addressInfoEntity.address");
        String hazardName = request.getParameter("dangerSourceEntity.hazard.hazardName");
        String yeHazardCate = request.getParameter("dangerSourceEntity.yeHazardCate");
        String yeRiskGrade = request.getParameter("dangerSourceEntity.yeRiskGrade");
        //查询条件组装器
        try{
            cq.createAlias("taskManagerControl","taskManagerControl");
            cq.createAlias("dangerSourceEntity","dangerSourceEntity");
            cq.createAlias("dangerSourceEntity.hazard","hazard");
            cq.createAlias("addressInfoEntity","addressInfoEntity");
            cq.eq("taskManagerControl.belongUser.id",user.getId());
            cq.eq("taskManagerControl.id",taskId);
            if(StringUtils.isNotBlank(address)){
                cq.like("addressInfoEntity.address","%"+address+"%");
            }
            if(StringUtils.isNotBlank(hazardName)){
                cq.like("hazard.hazardName","%"+hazardName+"%");
            }
            if(StringUtils.isNotBlank(yeHazardCate)){
                cq.eq("dangerSourceEntity.yeHazardCate",yeHazardCate);
            }
            if(StringUtils.isNotBlank(yeRiskGrade)){
                cq.eq("dangerSourceEntity.yeRiskGrade",yeRiskGrade);
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;


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
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "saveTaskOrder")
    @ResponseBody
    public AjaxJson saveTaskOrder(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        String addressIds = request.getParameter("addressIds");//选择的风险点
        String taskId = request.getParameter("taskId");
        TSUser user = ResourceUtil.getSessionUserName();
        boolean hasSave = false;
        try{

            if(StringUtil.isNotEmpty(addressIds)){
                String[] idArray = addressIds.split(",");
                TBTaskManagerControl taskManagerControl = systemService.getEntity(TBTaskManagerControl.class,taskId);
                for(String id : idArray){
                    CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class);

                    cq.add(Restrictions.sqlRestriction(" this_.id in (select address.danger_id from t_b_danger_address_rel address where address.address_id = '"
                            +id+"' and this_.id in ( select dangersource_id from  t_b_dangersource_cnotrol where user_id = '"+user.getId()+"') )"));
                    cq.add();
                    List<TBDangerSourceEntity> list = systemService.getListByCriteriaQuery(cq,false);

                    if(list != null && !list.isEmpty()){
                        TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,id);
                        TBTaskManagerOrder order = new TBTaskManagerOrder();
                        order.setAddressInfoEntity(addressInfoEntity);
                        order.setDangerSourceEntity(list.get(0));
                        order.setTaskManagerControl(taskManagerControl);
                        order.setCreateBy(user.getUserName());
                        order.setCreateName(user.getRealName());
                        order.setCreateDate(new Date());
                        order.setStatus("0");
                        systemService.save(order);
                        hasSave = true;
                    }
                }
            }
            if(!hasSave){
                message = "该风险点未关联风险";
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


    @RequestMapping(params = "delTaskOrder")
    @ResponseBody
    public AjaxJson delTaskOrder(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        String ids = request.getParameter("ids");

        try{
            if(StringUtils.isNotBlank(ids)){
                String [] idArray = ids.split(",");
                for(String id : idArray){
                    systemService.deleteEntityById(TBTaskManagerOrder.class,id);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "deleteALLSelect")
    @ResponseBody
    public AjaxJson deleteALLSelect(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        String ids = request.getParameter("ids");

        try{
            if(StringUtils.isNotBlank(ids)){
                String [] idArray = ids.split(",");
                for(String id : idArray){
                    systemService.deleteEntityById(TBTaskManagerControl.class,id);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "doDistribution")
    @ResponseBody
    public AjaxJson doDistribution(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "派发成功";
        String ids = request.getParameter("ids");

        try{
            if(StringUtils.isNotBlank(ids)){
                String [] idArray = ids.split(",");
                for(String id : idArray){
                    TBTaskManagerControl bean = systemService.getEntity(TBTaskManagerControl.class,id);
                    bean.setStatus("1");
                    systemService.saveOrUpdate(bean);
                    CriteriaQuery cq = new CriteriaQuery(TBTaskManagerOrder.class);
                    cq.eq("taskManagerControl.id",bean.getId());
                    cq.add();
                    List<TBTaskManagerOrder> list = systemService.getListByCriteriaQuery(cq,false);
                    if(list != null){
                        for(TBTaskManagerOrder order : list){
                            order.setStatus("1");
                            systemService.saveOrUpdate(order);
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "派发失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBTaskManagerOrder taskManagerOrder, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        Map<String,Object> map = new HashMap<String, Object>();
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TBTaskManagerOrder.class, dataGrid);

        cq.createAlias("taskManagerControl","taskManagerControl");
        cq.eq("taskManagerControl.belongUser.id",user.getId());

        cq.add();

        List<TBTaskManagerOrder> taskManagerOrderListk = this.systemService.getListByCriteriaQuery(cq,false);
        modelMap.put(TemplateExcelConstants.FILE_NAME,"管控清单");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_taskOrder.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        try {
            if(taskManagerOrderListk!=null&&taskManagerOrderListk.size()>0){
                for(TBTaskManagerOrder t:taskManagerOrderListk){
                    t.setYeHazardCateTemp( DicUtil.getTypeNameByCode("hazardCate", t.getDangerSourceEntity().getYeHazardCate()));
                    t.setYeRiskGradeTemp( DicUtil.getTypeNameByCode("riskLevel", t.getDangerSourceEntity().getYeRiskGrade()));
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        map.put("list", taskManagerOrderListk);
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    @RequestMapping(params = "chooseDatagrid")
    public void chooseDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBAddressControl.class, dataGrid);
        String taskId = request.getParameter("taskId");
        String addressName = request.getParameter("address.address");
        TSUser user = ResourceUtil.getSessionUserName();

        //查询条件组装器
        try{
            cq.eq("belongUser.id",user.getId());
            cq.add(Restrictions.sqlRestriction(" this_.address_id not in (select address_id from t_b_task_manager_order where task_id = '"+taskId+"' )"));
            if(StringUtils.isNotBlank(addressName)){
                cq.createAlias("address","address");
                cq.like("address.address","%"+addressName+"%");
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;


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

        String controlTime_begin = request.getParameter("controlTime_begin");
        String controlTime_end = request.getParameter("controlTime_end");

        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar calstr = Calendar.getInstance();
        //本月
        calstr.add(Calendar.MONTH, 0);
        //设置为1号为本月第一天
        calstr.set(Calendar.DAY_OF_MONTH,1);
        String first = sm.format(calstr.getTime());

        //获取当前月最后一天
        Calendar calast = Calendar.getInstance();
        //设置当月为最后一天
        calast.set(Calendar.DAY_OF_MONTH, calast.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = sm.format(calast.getTime());

        if(StringUtils.isBlank(controlTime_begin)){
            controlTime_begin = first;
        }

        if(StringUtils.isBlank(controlTime_end)){
            controlTime_end = last;
        }

        if(dataGrid != null && dataGrid.getResults() !=null && !dataGrid.getResults().isEmpty()){
            List<TBAddressControl> list = dataGrid.getResults();
            for(TBAddressControl bean : list){
                cq = new CriteriaQuery(TBTaskManagerOrder.class);
                cq.eq("addressInfoEntity.id",bean.getAddress().getId());
                cq.createAlias("taskManagerControl","taskManagerControl");
                try {
                    cq.ge("taskManagerControl.inveDate",sm.parse(controlTime_begin));
                    cq.le("taskManagerControl.inveDate",sm.parse(controlTime_end));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                cq.le("taskManagerControl.belongUser.id",user.getId());
                cq.add();
                List<TBTaskManagerOrder> retList = systemService.getListByCriteriaQuery(cq,false);
                if(retList == null || retList.isEmpty()){
                    bean.setControlTimes("0");
                }else{
                    bean.setControlTimes(retList.size()+"");
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
}
