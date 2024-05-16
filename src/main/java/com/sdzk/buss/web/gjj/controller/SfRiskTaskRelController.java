package com.sdzk.buss.web.gjj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sddb.buss.identification.entity.RiskTaskEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.gjj.entity.SfRiskTaskRelEntity;
import com.sdzk.buss.web.gjj.service.SfRiskTaskRelServiceI;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.LayDataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 辨识报告上传
 * @author xa-zhanglong
 * @date 2023-11-29 16:18:45
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/sfRiskTaskRelController")
public class SfRiskTaskRelController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(SfPlanInfoController.class);

    @Autowired
    private SfRiskTaskRelServiceI sfRiskTaskRelService;
    @Autowired
    private SystemService systemService;

    /**
     * 辨识报告上传列表 页面跳转
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        String loginName = ResourceUtil.getSessionUserName().getUserName();
        if("admin".equals(loginName)){
            request.setAttribute("roleOpt","");
        }else{
            String opt = "";
            //获取用户菜单按钮权限
            Set<String> optList = (Set<String>) request.getAttribute(Globals.OPERATIONCODESTRS);
            if(optList != null && optList.size() > 0){
                for(String str : optList){
                    if("".equals(opt)){
                        opt = str;
                    }else{
                        opt += "," + str;
                    }
                }
            }
            request.setAttribute("roleOpt",opt);
        }

        String fileTypeSql="SELECT typecode,typename FROM t_s_type type LEFT JOIN t_s_typegroup typegroup ON type.typegroupid = typegroup.ID WHERE typegroup.typegroupcode='risk_task_type'";
        List<Map<String, Object>> taskTypeList = systemService.findForJdbc(fileTypeSql);
        request.setAttribute("taskTypeList",taskTypeList);

        return new ModelAndView("com/sdzk/buss/web/gjj/sfRiskTaskRelList");
    }

    /**
     * 辨识报告上传列表
     */
    @RequestMapping(params = "datagrid")
    @ResponseBody
    public LayDataGrid LayDataGrid(HttpServletRequest request){
        Map<String, String[]> obj = request.getParameterMap();
        Integer page =Integer.parseInt(obj.get("page")[0]) ;//页码
        Integer rows = Integer.parseInt(obj.get("limit")[0]);//每页行数
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT rel.id,rel.risk_task_id,rel.file_id,rel.file_name,risk.task_type,risk.task_name,");
        sql.append("risk.start_date,risk.end_date,risk.organizer_man,risk.participant_man,risk.status ");
        sql.append("FROM sf_risk_task_rel rel ");
        sql.append("left join t_b_risk_task risk on rel.risk_task_id = risk.id where risk.status = '1' ");
        //查询条件
        String sql1 = null;
        String sql2 = null;
        if(obj.get("searchParams") != null){
            String searchParams = obj.get("searchParams")[0];
            if(StringUtils.isNotEmpty(searchParams)){
                JSONObject json = JSON.parseObject(searchParams);
                String taskType = json.getString("taskType");
                String taskName = json.getString("taskName");
                if(StringUtils.isNotEmpty(taskType)){
                    sql1 = " and risk.task_type = '"+taskType+"' ";
                    sql.append(sql1);
                }
                if(StringUtils.isNotEmpty(taskName)){
                    sql2 = " and risk.task_name like '%"+taskName+"%' ";
                    sql.append(sql2);
                }
            }
        }
        sql.append("ORDER BY risk.create_date DESC ");
        List<Map<String,Object>> list = systemService.findForJdbc(sql.toString(),page,rows);
        StringBuffer countSql = new StringBuffer();
        countSql.append("select COUNT(*) as count from sf_risk_task_rel rel left join t_b_risk_task risk on rel.risk_task_id = risk.id where risk.status = '1' ");
        if(sql1 != null){
            countSql.append(sql1);
        }
        if(sql2 != null){
            countSql.append(sql2);
        }
        Map<String,Object> count = systemService.findOneForJdbc(countSql.toString());

        List<Map<String,Object>> result=new ArrayList<>();
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        for (Map<String,Object> entity : list) {
            Map<String,Object> resultMap=new HashMap<>();
            resultMap.put("id",entity.get("id"));
            resultMap.put("riskTaskId",entity.get("risk_task_id"));
            resultMap.put("fileId",entity.get("file_id"));
            resultMap.put("fileName",entity.get("file_name"));
            resultMap.put("taskTypeName","");
            if(entity.get("task_type") != null){
                resultMap.put("taskTypeName",DicUtil.getTypeNameByCode("risk_task_type",entity.get("task_type").toString()));
            }
            resultMap.put("taskName",entity.get("task_name"));
            resultMap.put("startDate",entity.get("start_date"));
            resultMap.put("endDate",entity.get("end_date"));
            resultMap.put("organizerMan","");
            if(entity.get("organizer_man") != null){
                TSUser organizerMan = systemService.get(TSUser.class,entity.get("organizer_man").toString());
                resultMap.put("organizerMan", organizerMan.getRealName());
            }
            if (entity.get("participant_man") != null){
                String[] ids = entity.get("participant_man").toString().split(",");
                String name = "";

                for(String id : ids){
                    TSUser user = systemService.getEntity(TSUser.class,id);
                    if(user.getId().equals(sessionUser.getId())){
                        entity.put("participant","0");
                    }
                    if(user!=null){
                        if(name==""){
                            name = name +user.getRealName();
                        }else{
                            name = name + "," +user.getRealName();
                        }
                    }
                }
                resultMap.put("participantManNames", name);
            }
            resultMap.put("status",entity.get("status"));
            result.add(resultMap);
        }
        LayDataGrid lay = new LayDataGrid(Integer.parseInt(count.get("count").toString()), result);
        return lay;
    }

    /**
     * 批量删除辨识报告上传
     */
    @RequestMapping(params = "batchDel")
    @ResponseBody
    public AjaxJson batchDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        if(StringUtils.isNotBlank(ids)){
            String[] idArray = ids.split(",");
            for(String id : idArray){
                SfRiskTaskRelEntity sfRiskTaskRelEntity = systemService.getEntity(SfRiskTaskRelEntity.class,Integer.parseInt(id));
                if (Constants.GJJ_STATE_FLAG_0.equals(sfRiskTaskRelEntity.getStateFlag())){
                    sfRiskTaskRelEntity.setIsDelete(Constants.IS_DELETE_Y);
                    sfRiskTaskRelEntity.setStateFlag(Constants.GJJ_STATE_FLAG_3);//国家局上报标识
                    systemService.updateEntitie(sfRiskTaskRelEntity);
                }else {
                    sfRiskTaskRelEntity.setStateFlag(Constants.GJJ_STATE_FLAG_0);//国家局上报标识
                    systemService.delete(sfRiskTaskRelEntity);
                }
            }
        }
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 添加辨识报告文件
     */
    @RequestMapping(params = "save")
    @ResponseBody
    public AjaxJson save(SfRiskTaskRelEntity sfRiskTaskRelEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(sfRiskTaskRelEntity.getId())) {
            message = "辨识报告文件更新成功";
            SfRiskTaskRelEntity t = sfRiskTaskRelService.get(SfRiskTaskRelEntity.class, sfRiskTaskRelEntity.getId());
            try {
                MyBeanUtils.copyBeanNotNull2Bean(sfRiskTaskRelEntity, t);
                if (!Constants.GJJ_STATE_FLAG_1.equals(t.getStateFlag())){
                    t.setStateFlag(Constants.GJJ_STATE_FLAG_2);//国家局上报标识
                }
                sfRiskTaskRelService.saveOrUpdate(t);
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
                message = "辨识报告文件案更新失败";
            }
        } else {
            message = "辨识报告文件添加成功";
            sfRiskTaskRelEntity.setIsDelete(Constants.IS_DELETE_N);
            sfRiskTaskRelEntity.setStateFlag(Constants.GJJ_STATE_FLAG_1);
            sfRiskTaskRelService.save(sfRiskTaskRelEntity);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 辨识报告上传列表页面跳转
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(SfRiskTaskRelEntity sfRiskTaskRelEntity, HttpServletRequest req) {
//		String riskId = req.getParameter("riskId");
//		req.setAttribute("riskId",riskId);
        if (StringUtil.isNotEmpty(sfRiskTaskRelEntity.getId())) {
            sfRiskTaskRelEntity = sfRiskTaskRelService.getEntity(SfRiskTaskRelEntity.class, sfRiskTaskRelEntity.getId());
        }
        req.setAttribute("sfRiskTaskRelPage", sfRiskTaskRelEntity);
        return new ModelAndView("com/sdzk/buss/web/gjj/sfRiskTaskRelAdd");
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson uploadFile(HttpServletRequest request, TSAttachment attachment) {
        String message = "上传成功";
        AjaxJson j = new AjaxJson();
        try {
            attachment.setSubclassname(MyClassLoader.getPackPath(attachment));
            attachment.setCreatedate(DateUtils.gettimestamp());
            attachment.setTSUser(ResourceUtil.getSessionUserName());
            UploadFile uploadFile = new UploadFile(request, attachment);
            uploadFile.setCusPath("files");
            uploadFile.setSwfpath("swfpath");
            attachment = systemService.uploadFile(uploadFile);
            Map<String ,Object> map=new HashMap<>();
            map.put("id",attachment.getId());
            map.put("reportName",attachment.getAttachmenttitle());
            map.put("fileName",attachment.getAttachmenttitle()+"."+attachment.getExtend());
            j.setObj(map);
        } catch (Exception e) {
            message = "上传失败";
            j.setSuccess(false);
            LogUtil.error(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
}
