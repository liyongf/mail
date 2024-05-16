package com.sdzk.buss.web.common.taskProvince;

import com.sdzk.buss.web.common.taskProvince.service.GetDataService;
import net.sf.json.JSONArray;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2019/1/9.
 */
@Component("reportTaskAllManage")
public class ReportTaskAllManage {
    @Autowired
    private SystemService systemService;
    @Autowired
    private GetDataService getDataService;

    protected static final Map<String,String> columnNeedInit = getColumnNeed();
    protected static final String tableName = getTableNeed();

    public void run(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报任务管理开始===================");//调整
        try {
            String sql = "select id from t_b_risk_manage_task_all_manage  ";//调整
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                count = 0;
                /**
                 * 步数
                 */
                int distance = 500;
                List<String> tempList;
                for(int i = idList.size();i>0;i=i-distance){
                    if(i>distance){
                        tempList = idList.subList(i-distance,i);
                    }else{
                        tempList = idList.subList(0,i);
                    }
                    if(!tempList.isEmpty() && tempList.size()>0){
                        StringBuffer ids = new StringBuffer();
                        for(String id:tempList){
                            if(ids.length()>0){
                                ids.append(",");
                            }
                            ids.append(id);
                        }
                        AjaxJson j = reportDataToProvince(ids.toString(),Constants.REPORT_BY_TIME_TASK);
                        if(j.getObj().equals("200")){
                            count+=tempList.size();
                        }
                    }
                }
            }
        } catch (Exception e) {
            org.jeecgframework.core.util.LogUtil.error("定时上报任务管理失败", e);//调整
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报任务管理结束===================");//调整
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报任务管理总耗时"+times+"毫秒,共同步"+count+"条数据.");//调整
    }

    //上报
    public AjaxJson reportDataToProvince(String ids,String reportBy){
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(ids)){
            String sql = "SELECT task_all_manage_id from t_b_risk_manage_task_all WHERE status= '0' and (task_all_manage_id is NOT NULL or task_all_manage_id!='')  GROUP BY task_all_manage_id";
            List<String> tempList = systemService.findListbySql(sql);
            Map<String,String> map = new HashMap<>();
            for(String temp:tempList){
                map.put(temp,"0");
            }
            String hdCountSql = "SELECT\n" +
                    "\tsum(count) count,\n" +
                    "\ttemp.id id\n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tcount(0) count,\n" +
                    "\t\t\ttam.id id\n" +
                    "\t\tFROM\n" +
                    "\t\t\tt_b_hidden_danger_exam hde\n" +
                    "\t\tLEFT JOIN t_b_risk_manage_task_all ta ON ta.id = hde.task_all_id\n" +
                    "\t\tLEFT JOIN t_b_risk_manage_task_all_manage tam ON tam.id = ta.task_all_manage_id\n" +
                    "\t\tWHERE\n" +
                    "\t\t\thde.task_all_id IS NOT NULL\n" +
                    "\t\tAND hde.task_all_id != ''\n" +
                    "\t\tAND tam.id != ''\n" +
                    "\t\tAND tam.id IS NOT NULL\n" +
                    "\t\tGROUP BY\n" +
                    "\t\t\ttask_all_id\n" +
                    "\t\tUNION ALL\n" +
                    "\t\t\tSELECT\n" +
                    "\t\t\t\tcount(0) count,\n" +
                    "\t\t\t\ttam.id id\n" +
                    "\t\t\tFROM\n" +
                    "\t\t\t\tt_b_risk_manage_task_all_manage tam\n" +
                    "\t\t\tLEFT JOIN t_b_risk_manage_task_all ta ON tam.id = ta.task_all_manage_id\n" +
                    "\t\t\tLEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id\n" +
                    "\t\t\tLEFT JOIN t_b_risk_manage_hazard_factor rmhf ON t.id = rmhf.risk_manage_task_id\n" +
                    "\t\t\tLEFT JOIN t_b_risk_manage_rel_hd rmrh ON rmrh.risk_manage_hazard_factor_id = rmhf.id\n" +
                    "\t\t\tWHERE\n" +
                    "\t\t\t\trmrh.hd_id IN (\n" +
                    "\t\t\t\t\tSELECT\n" +
                    "\t\t\t\t\t\tid\n" +
                    "\t\t\t\t\tFROM\n" +
                    "\t\t\t\t\t\tt_b_hidden_danger_exam\n" +
                    "\t\t\t\t)\n" +
                    "\t\t\tGROUP BY\n" +
                    "\t\t\t\ttam.id\n" +
                    "\t) temp\n" +
                    "GROUP BY\n" +
                    "\ttemp.id";
            List<Map<String, Object>> hdCountList = systemService.findForJdbc(hdCountSql);
            Map<String, String> hdCountMap = new HashMap<>();
            if (hdCountList != null && hdCountList.size() > 0) {
                for (Map<String, Object> obj : hdCountList) {
                    hdCountMap.put(obj.get("id").toString(), obj.get("count").toString());
                }
            }
            String riskCountSql = "SELECT\n" +
                    "\tcount(0) count,tam.id id\n" +
                    "FROM\n" +
                    "\tt_b_risk_manage_task_all_manage tam\n" +
                    "LEFT JOIN t_b_risk_manage_task_all ta ON tam.id = ta.task_all_manage_id\n" +
                    "LEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id\n" +
                    "LEFT JOIN t_b_risk_identification ri on ri.risk_manage_task_id = t.id\n" +
                    "WHERE ri.is_del = '0' and ri.status = '3'\n" +
                    "GROUP BY tam.id";
            List<Map<String, Object>> riskCountList = systemService.findForJdbc(riskCountSql);
            Map<String, String> riskCountMap = new HashMap<>();
            if (riskCountList != null && riskCountList.size() > 0) {
                for (Map<String, Object> obj : riskCountList) {
                    riskCountMap.put(obj.get("id").toString(), obj.get("count").toString());
                }
            }String implCountSql = "SELECT count(ta.id) count, tam.id id FROM t_b_risk_manage_task_all_manage tam LEFT JOIN t_b_risk_manage_task_all ta ON tam.id = ta.task_all_manage_id LEFT JOIN t_b_risk_manage_task t ON ta.id = t.task_all_id LEFT JOIN t_b_risk_manage_hazard_factor hf ON hf.risk_manage_task_id = t.id WHERE hf.impl_detail != '' AND hf.impl_detail IS NOT NULL GROUP BY tam.id";
            List<Map<String, Object>> implCountList = systemService.findForJdbc(implCountSql);
            Map<String, String> implCountMap = new HashMap<>();
            if (implCountList != null && implCountList.size() > 0) {
                for (Map<String, Object> obj : implCountList) {
                    implCountMap.put(obj.get("id").toString(), obj.get("count").toString());
                }
            }
            List<String> conditions = new ArrayList<>();
            String condition = "b.id in ('"+ids.replaceAll(",","','")+"')";
            conditions.add(condition);
            List<Map<String,Object>> retList = getDataService.getData(columnNeedInit,tableName,conditions);
            for (Map<String,Object> temp:retList){
                String id = String.valueOf(temp.get("id"));
                if(StringUtil.isNotEmpty(id)){
                    String status = String.valueOf(map.get(id));
                    if(StringUtil.isNotEmpty(status)&&!status.equals("null")){
                        temp.put("status",status);
                    }else {
                        temp.put("status","1");
                    }
                    String hdCount = hdCountMap.get(id);
                    if(StringUtil.isNotEmpty(hdCount)){
                        temp.put("hdCount",hdCount);
                    }else {
                        temp.put("hdCount","0");
                    }
                    String riskCount = riskCountMap.get(id);
                    if(StringUtil.isNotEmpty(riskCount)){
                        temp.put("riskCount",riskCount);
                    }else {
                        temp.put("riskCount","0");
                    }
                    String implCount = implCountMap.get(id);
                    if(StringUtil.isNotEmpty(implCount)){
                        temp.put("implCount",implCount);
                    }else {
                        temp.put("implCount","0");
                    }
                }
            }
            JSONArray jsonArray = JSONArray.fromObject(retList);
            if(jsonArray.size()>0){
                String reportUrl = ResourceUtil.getConfigByName("taskAllManageReportToProvince");//调整
                j = getDataService.postData(jsonArray,reportUrl);
                //更新本地库
                if(j.getObj().equals("200")){
                    StringBuffer sqlUpdate = new StringBuffer("update t_b_risk_manage_task_all_manage b set \n");//调整
                    if(reportBy.equals(Constants.REPORT_BY_PEOPLE)){
                        String userName = ResourceUtil.getSessionUserName().getRealName();
                        sqlUpdate.append("report_status_province = '"+Constants.REPORTED_ALREADY+"'\n");
                        sqlUpdate.append(",report_name_province = '"+userName+"'\n");
                    }else {
                        sqlUpdate.append("report_date_province = NOW()\n");
                    }
                    sqlUpdate.append("where 1=1\n");
                    for(String tempCondition : conditions){
                        sqlUpdate.append("and "+tempCondition+"\n");
                    }
                    systemService.executeSql(sqlUpdate.toString());
                    j.setMsg(retList.size()+"条任务管理上报成功");//调整
                }else{
                    if(StringUtil.isEmpty(j.getMsg())){
                        j.setMsg("请求失败！");
                    }
                }
            }else{
                j.setMsg("未查找到对应的数据！");
            }
        }else{
            j.setMsg("无需要操作的对象！");
        }
        return j;
    }

    //定义需要的列
    private static Map<String,String> getColumnNeed(){
        Map<String,String> columnNeed = new HashMap<>();
        columnNeed.put("b.id","id");
        columnNeed.put("b.manage_type","manageType");
        columnNeed.put("DATE_FORMAT(b.manage_time,'%Y-%m-%d')","manageTime");
        columnNeed.put("DATE_FORMAT(b.end_date,'%Y-%m-%d')","endDate");
        columnNeed.put("b.manage_name","manageName");
        columnNeed.put("b.main_contents","mainContents");
        columnNeed.put("IFNULL(u.realname,b.organizer_man)","organizerMan");
        return columnNeed;
    }

    //定义需要的表
    private static String getTableNeed(){
        String tableName = "t_b_risk_manage_task_all_manage b\n" +
                "LEFT JOIN t_s_base_user u on b.organizer_man = u.ID";
        return tableName;
    }
}
