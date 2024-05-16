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
 * Created by lenovo on 2019/1/4.
 */
@Component("reportRiskFactorRel")
public class ReportRiskFactorRel {
    @Autowired
    private SystemService systemService;
    @Autowired
    private GetDataService getDataService;

    protected static final Map<String,String> columnNeedInit = getColumnNeed();
    protected static final String tableName = getTableNeed();

    public void run(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报风险危害因素关联关系开始===================");//单独调整
        try {
            String sql = "select id from t_b_risk_factors_rel where report_date_province is null or update_date > report_date_province ";//单独调整
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
                        AjaxJson j = reportRiskFactorsRelToProvince(ids.toString(),Constants.REPORT_BY_TIME_TASK);//单独调整
                        if(j.getObj().equals("200")){
                            count+=tempList.size();
                        }
                    }
                }
            }
        } catch (Exception e) {
            org.jeecgframework.core.util.LogUtil.error("定时上报风险危害因素关联关系失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报风险危害因素关联关系结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报风险危害因素关联关系总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    //上报
    public AjaxJson reportRiskFactorsRelToProvince(String ids,String reportBy){//单独调整
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(ids)){
            List<String> conditions = new ArrayList<>();
            String condition = "b.id in ('"+ids.replaceAll(",","','")+"')";//单独调整
            conditions.add(condition);
            List<Map<String,Object>> retList = getDataService.getData(columnNeedInit,tableName,conditions);
            JSONArray jsonArray = JSONArray.fromObject(retList);
            if(jsonArray.size()>0){
                String reportUrl = ResourceUtil.getConfigByName("riskFactorRelReportedToProvince");//单独调整
                j = getDataService.postData(jsonArray,reportUrl);
                //更新本地库
                if(j.getObj().equals("200")){
                    StringBuffer sqlUpdate = new StringBuffer("update t_b_risk_factors_rel b set \n");//单独调整
                    if(reportBy.equals(Constants.REPORT_BY_PEOPLE)){
                        String userName = ResourceUtil.getSessionUserName().getRealName();
                        sqlUpdate.append("report_status_province = '"+Constants.REPORTED_ALREADY+"'\n");
                        sqlUpdate.append(",report_name_province = '"+userName+"'\n");
                    }else{
                        sqlUpdate.append("report_date_province = NOW()\n");
                    }
                    sqlUpdate.append("where 1=1\n");
                    for(String tempCondition : conditions){
                        sqlUpdate.append("and "+tempCondition+"\n");
                    }
                    systemService.executeSql(sqlUpdate.toString());
                    j.setMsg(retList.size()+"条危害因素风险关联关系上报成功");
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
        columnNeed.put("b.hazard_factors_id","hazardFactorsId");
        columnNeed.put("b.risk_identification_id","riskIdentificationId");
        columnNeed.put("b.hfLevel","hfLevel");
        columnNeed.put("IFNULL(d.departname,b.manage_depart)","manageDepart");
        columnNeed.put("IFNULL(u.realname,b.manage_user)","manageUser");
        columnNeed.put("b.hfManageMeasure","hfManageMeasure");
        return columnNeed;
    }

    //定义需要的表
    private static String getTableNeed(){
        String tableName = "t_b_risk_factors_rel b\n" +
                "LEFT JOIN t_s_depart d on b.manage_depart = d.id\n" +
                "LEFT JOIN t_s_base_user u on b.manage_user = u.ID";
        return tableName;
    }

}
