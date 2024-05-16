package com.sdzk.buss.web.common.taskProvince;

import com.sdzk.buss.web.common.taskProvince.service.GetDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * Created by lenovo on 2019/1/4.
 */
@Component("reportRiskTask")
public class ReportRiskTask {
    @Autowired
    private SystemService systemService;
    @Autowired
    private GetDataService getDataService;
    @Autowired
    private ReportHazardFactorsTask reportHazardFactorsTask;
    @Autowired
    private ReportRiskFactorRel reportRiskFactorRel;
    @Autowired
    private ReportAddressInfo reportAddressInfo;
    @Autowired
    private ReportLayerInfo reportLayerInfo;

    private static final Map<String,String> columnNeedInit = getColumnNeed();
    private static final String tableName = getTableNeed();

    public void run(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报风险开始===================");
        try {
            String sql = "select id from t_b_risk_identification where report_date_province is null or update_date > report_date_province ";
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
                        AjaxJson j = reportRiskToProvince(ids.toString(), Constants.REPORT_BY_TIME_TASK);
                        if(j.getObj().equals("200")){
                            count+=tempList.size();
                        }
                    }
                }
            }
        } catch (Exception e) {
            org.jeecgframework.core.util.LogUtil.error("定时上报风险失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报风险结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报风险总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    //上报
    public AjaxJson reportRiskToProvince(String ids,String reportBy){
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(ids)){
            List<String> conditions = new ArrayList<>();
            String condition = "b.id in ('"+ids.replaceAll(",","','")+"')";
            conditions.add(condition);
            List<Map<String,Object>> retList = getDataService.getData(columnNeedInit,tableName,conditions);
            JSONArray jsonArray = JSONArray.fromObject(retList);
            if(jsonArray.size()>0){
                String reportUrl = ResourceUtil.getConfigByName("riskReportToProvince");
                j = getDataService.postData(jsonArray,reportUrl);
                //更新本地库
                if(j.getObj().equals("200")){
                    StringBuffer sqlUpdate = new StringBuffer("update t_b_risk_identification b set \n");
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

    //上报风险及关联的危害因素
    public AjaxJson reportRiskWithRelatedDataToProvince(String ids,String reportBy){
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(ids)){
            JSONObject sendObject = new JSONObject();
            String idsArray = "'"+ids.replaceAll(",","','")+"'";
            //风险数据
            List<String> conditions = new ArrayList<>();
            String condition = "b.id in ("+idsArray+")";
            conditions.add(condition);
            List<Map<String,Object>> retList = getDataService.getData(columnNeedInit,tableName,conditions);
            JSONArray jsonArray = JSONArray.fromObject(retList);
            sendObject.put("riskJsonArray",jsonArray);

            //关联关系
            Map<String,String> relColumn = reportRiskFactorRel.columnNeedInit;
            String relTableName = reportRiskFactorRel.tableName;
            List<String> relConditions = new ArrayList<>();
            String relCondition = "(report_date_province is null or update_date > report_date_province) and  risk_identification_id in ("+idsArray+")";
            relConditions.add(relCondition);
            List<Map<String,Object>> relList = getDataService.getData(relColumn,relTableName,relConditions);
            JSONArray relArray = JSONArray.fromObject(relList);
            sendObject.put("relJsonArray",relArray);

            //危害因素
            Map<String,String> factorColumn = reportHazardFactorsTask.columnNeedInit;
            String factorTableName = reportHazardFactorsTask.tableName;
            List<String> factorConditions = new ArrayList<>();
            String factorCondition = "id in (select hazard_factors_id from t_b_risk_factors_rel r where r.risk_identification_id in ("+idsArray+") )";
            factorConditions.add(factorCondition);
            factorConditions.add("(b.report_date_province is null or b.update_date > b.report_date_province)");
            List<Map<String,Object>> factorList = getDataService.getData(factorColumn,factorTableName,factorConditions);
            JSONArray factorArray = JSONArray.fromObject(factorList);
            sendObject.put("factorsJsonArray",factorArray);

            //关联地点
            Map<String,String> addressColumn = reportAddressInfo.columnNeedInit;
            String addressTableName = reportAddressInfo.tableName;
            List<String> addressConditions = new ArrayList<>();
            String addressCondition = "id in (select address_id from t_b_risk_identification r where r.address_id is not null and r.id in ("+idsArray+") )";
            addressConditions.add(addressCondition);
            addressConditions.add("(b.report_date_province is null or b.update_date > b.report_date_province)");
            List<Map<String,Object>> addressList = getDataService.getData(addressColumn,addressTableName,addressConditions);
            JSONArray addressArray = JSONArray.fromObject(addressList);
            sendObject.put("addressJsonArray",addressArray);

            //关联地点关联煤层
            Map<String,String> layerColumn = reportLayerInfo.columnNeedInit;
            String layerTableName = reportLayerInfo.tableName;
            List<String> layerConditions = new ArrayList<>();
            String layerCondition = "id in ( select belong_layer from t_b_address_info where belong_layer is not null and id in \n( select address_id from t_b_risk_identification r where r.address_id is not null and r.id in \n("+idsArray+")\n )\n)";
            layerConditions.add(layerCondition);
            layerConditions.add("(b.report_date_province is null or b.update_date > b.report_date_province)");
            List<Map<String,Object>> layerList = getDataService.getData(layerColumn,layerTableName,layerConditions);
            JSONArray layerArray = JSONArray.fromObject(layerList);
            sendObject.put("layerJsonArray",layerArray);

            if(sendObject.size()>0){
                String reportUrl = ResourceUtil.getConfigByName("riskWithRelatedReportToProvince");
                j = getDataService.postData(sendObject,reportUrl);
                //更新本地库
                if(j.getObj().equals("200")){
                    //更新风险
                    StringBuffer sqlUpdate = new StringBuffer("update t_b_risk_identification b set \n");
                    if(reportBy.equals(Constants.REPORT_BY_PEOPLE)){
                        String userName = ResourceUtil.getSessionUserName().getRealName();
                        sqlUpdate.append("b.report_status_province = '"+Constants.REPORTED_ALREADY+"'\n");
                        sqlUpdate.append(",b.report_name_province = '"+userName+"'\n");
                    }else{
                        sqlUpdate.append("b.report_date_province = NOW()\n");
                    }
                    sqlUpdate.append("where 1=1\n");
                    for(String tempCondition : conditions){
                        sqlUpdate.append("and "+tempCondition+"\n");
                    }
                    systemService.executeSql(sqlUpdate.toString());
                    //更新关联关系
                    StringBuffer sqlUpdateRel = new StringBuffer("update t_b_risk_factors_rel b  set \n");//单独调整
                    if(reportBy.equals(Constants.REPORT_BY_PEOPLE)){
                        String userName = ResourceUtil.getSessionUserName().getRealName();
                        sqlUpdateRel.append("report_status_province = '"+Constants.REPORTED_ALREADY+"'\n");
                        sqlUpdateRel.append(",report_name_province = '"+userName+"'\n");
                    }else{
                        sqlUpdateRel.append("report_date_province = NOW()\n");
                    }
                    sqlUpdateRel.append("where 1=1\n");
                    for(String tempCondition : relConditions){
                        sqlUpdateRel.append("and "+tempCondition+"\n");
                    }
                    systemService.executeSql(sqlUpdateRel.toString());
                    //更新危害因素
                    StringBuffer sqlUpdateFactors = new StringBuffer("update t_b_hazard_factors b set \n");
                    if(reportBy.equals(Constants.REPORT_BY_PEOPLE)){
                        String userName = ResourceUtil.getSessionUserName().getRealName();
                        sqlUpdateFactors.append("b.report_status_province = '"+Constants.REPORTED_ALREADY+"'\n");
                        sqlUpdateFactors.append(",b.report_name_province = '"+userName+"'\n");
                    }else{
                        sqlUpdateFactors.append("b.report_date_province = NOW()\n");
                    }
                    sqlUpdateFactors.append("where 1=1\n");
                    for(String tempCondition : factorConditions){
                        sqlUpdateFactors.append("and "+tempCondition+"\n");
                    }
                    systemService.executeSql(sqlUpdateFactors.toString());

                    //更新风险点
                    StringBuffer sqlUpdateAddress = new StringBuffer("update t_b_address_info b set \n");
                    if(reportBy.equals(Constants.REPORT_BY_PEOPLE)){
                        String userName = ResourceUtil.getSessionUserName().getRealName();
                        sqlUpdateAddress.append("b.report_status_province = '"+Constants.REPORTED_ALREADY+"'\n");
                        sqlUpdateAddress.append(",b.report_name_province = '"+userName+"'\n");
                    }else{
                        sqlUpdateAddress.append("b.report_date_province = NOW()\n");
                    }
                    sqlUpdateAddress.append("where 1=1\n");
                    for(String tempCondition : addressConditions){
                        sqlUpdateAddress.append("and "+tempCondition+"\n");
                    }
                    systemService.executeSql(sqlUpdateAddress.toString());

                    //更新煤层信息
                    StringBuffer sqlUpdateLayer = new StringBuffer("update t_b_layer b set \n");
                    if(reportBy.equals(Constants.REPORT_BY_PEOPLE)){
                        String userName = ResourceUtil.getSessionUserName().getRealName();
                        sqlUpdateLayer.append("b.report_status_province = '"+Constants.REPORTED_ALREADY+"'\n");
                        sqlUpdateLayer.append(",b.report_name_province = '"+userName+"'\n");
                    }else{
                        sqlUpdateLayer.append("b.report_date_province = NOW()\n");
                    }
                    sqlUpdateLayer.append("where 1=1\n");
                    for(String tempCondition : layerConditions){
                        sqlUpdateLayer.append("and "+tempCondition+"\n");
                    }
                    systemService.executeSql(sqlUpdateLayer.toString());
                    j.setMsg(retList.size()+"条风险上报成功");
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

    //撤回风险
    public AjaxJson removeRiskDataFromProvince(String ids){
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(ids)){
            JSONObject sendObject = new JSONObject();
            String idsArray = "'"+ids.replaceAll(",","','")+"'";
            List<String> conditions = new ArrayList<>();
            String condition = "b.id in ("+idsArray+")";
            conditions.add(condition);
            sendObject.put("ids",ids);
            if(sendObject.size()>0){
                String reportUrl = ResourceUtil.getConfigByName("removeRiskDataFromProvince");
                j = getDataService.postData(sendObject,reportUrl);
                //更新本地库
                if(j.getObj().equals("200")){
                    //更新风险
                    StringBuffer sqlUpdate = new StringBuffer("update t_b_risk_identification b set \n");
                    sqlUpdate.append("b.report_status_province = '" + Constants.REPORTED_NOT + "'\n");
                    sqlUpdate.append(",b.report_name_province = NULL\n");
                    sqlUpdate.append(",b.report_date_province = NULL\n");
                    sqlUpdate.append("where 1=1\n");
                    for(String tempCondition : conditions){
                        sqlUpdate.append("and "+tempCondition+"\n");
                    }
                    systemService.executeSql(sqlUpdate.toString());
                    j.setMsg(ids.split(",").length+"条风险撤回成功");
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
        columnNeed.put("b.id","id");
        columnNeed.put("b.identification_type","identificationType");
        columnNeed.put("b.address_id","addressId");
        columnNeed.put("a.address","addressName");
        columnNeed.put("b.post_id","postId");
        columnNeed.put("b.risk_type","riskType");
        columnNeed.put("b.risk_desc","riskDesc");
        columnNeed.put("b.risk_level","riskLevel");
        columnNeed.put("b.manage_level","manageLevel");
        columnNeed.put("b.duty_manager","dutyManager");
        columnNeed.put("DATE_FORMAT(b.identifi_date,'%Y-%m-%d %H:%i:%s')","identifiDate");
        columnNeed.put("DATE_FORMAT(b.exp_date,'%Y-%m-%d %H:%i:%s')","expDate");
        columnNeed.put("b.risk_manage_hazard_factor_id","riskManageHazardFactorsId");
        columnNeed.put("b.specific_type","specificType");
        columnNeed.put("b.specific_name","specificName");
        columnNeed.put("IF(b.is_del=1,1,0)","isDel");
        return columnNeed;
    }

    private static String getTableNeed(){
        String tableName = "t_b_risk_identification b left join t_b_address_info a on b.address_id = a.id";
        return tableName;
    }
}
