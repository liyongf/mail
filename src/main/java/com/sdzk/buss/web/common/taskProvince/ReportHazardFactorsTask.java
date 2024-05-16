package com.sdzk.buss.web.common.taskProvince;

import com.sdzk.buss.web.common.taskProvince.service.GetDataService;
import com.sdzk.buss.web.common.utils.AesUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.HttpClientUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2019/1/3.
 */
@Component("reportHazardFactorsTask")
public class ReportHazardFactorsTask {
    @Autowired
    private SystemService systemService;
    @Autowired
    private GetDataService getDataService;

    protected static final Map<String,String> columnNeedInit = getColumnNeed();
    protected static final String tableName = getTableNeed();

    public void run(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报危害因素开始===================");
        try {
            String sql = "select id from t_b_hazard_factors where report_date_province is null or update_date > report_date_province ";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                count = 0;
                /**
                 * 步数
                 */
                int distance = 5000;
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
                        AjaxJson j = reportHazardFactorsToProvince(ids.toString(),Constants.REPORT_BY_TIME_TASK);
                        if(j.getObj().equals("200")){
                            count+=tempList.size();
                        }
                    }
                }
            }
        } catch (Exception e) {
            org.jeecgframework.core.util.LogUtil.error("定时上报危害因素失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报危害因素结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报危害因素总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    //上报
    public AjaxJson reportHazardFactorsToProvince(String ids,String reportBy){
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(ids)){
            List<String> conditions = new ArrayList<>();
            String condition = "id in ('"+ids.replaceAll(",","','")+"')";
            conditions.add(condition);
            List<Map<String,Object>> retList = getDataService.getData(columnNeedInit,tableName,conditions);
            JSONArray jsonArray = JSONArray.fromObject(retList);
            if(jsonArray.size()>0){
                String reportUrl = ResourceUtil.getConfigByName("hazardFactorsReportToProvince");
                j = getDataService.postData(jsonArray,reportUrl);
                //更新本地库
                if(j.getObj().equals("200")){
                    StringBuffer sqlUpdate = new StringBuffer("update t_b_hazard_factors b set \n");
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
                    j.setMsg(retList.size()+"条危害因素上报成功");
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

    //撤回危害因素
    public AjaxJson removeHazardFactorFromProvince(String ids){
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(ids)){
            JSONObject sendObject = new JSONObject();
            String idsArray = "'"+ids.replaceAll(",","','")+"'";
            List<String> conditions = new ArrayList<>();
            String condition = "b.id in ("+idsArray+")";
            conditions.add(condition);
            sendObject.put("ids",ids);
            if(sendObject.size()>0){
                String reportUrl = ResourceUtil.getConfigByName("removeHazardFactorFromProvince");
                j = getDataService.postData(sendObject,reportUrl);
                //更新本地库
                if(j.getObj().equals("200")){
                    //更新风险
                    StringBuffer sqlUpdate = new StringBuffer("update t_b_hazard_factors b set \n");
                    sqlUpdate.append("b.report_status_province = '" + Constants.REPORTED_NOT + "'\n");
                    sqlUpdate.append(",b.report_name_province = NULL\n");
                    sqlUpdate.append(",b.report_date_province = NULL\n");
                    sqlUpdate.append("where 1=1\n");
                    for(String tempCondition : conditions){
                        sqlUpdate.append("and "+tempCondition+"\n");
                    }
                    systemService.executeSql(sqlUpdate.toString());
                    j.setMsg(ids.split(",").length+"条危害因素撤回成功");
                }else{
                    j.setMsg("请求失败！");
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
        columnNeed.put("id","id");
        columnNeed.put("risk_type","riskType");
        columnNeed.put("major","major");
        columnNeed.put("hazard_factors","hazardFactors");
        columnNeed.put("IF(is_Del=1,1,0)","isDel");
        columnNeed.put("source_from","'from'");
        columnNeed.put("riskLevel","riskLevel");
        columnNeed.put("manage_measure","manageMeasure");
        return columnNeed;
    }

    private static String getTableNeed(){
        String tableName = "t_b_hazard_factors b";
        return tableName;
    }
}
