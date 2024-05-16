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
@Component("reportDeleteId")
public class ReportDeleteId {
    @Autowired
    private SystemService systemService;
    @Autowired
    private GetDataService getDataService;

    protected static final Map<String,String> columnNeedInit = getColumnNeed();
    protected static final String tableName = getTableNeed();

    public void run(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报删除的信息开始===================");//调整
        try {
            String sql = "SELECT id from t_b_report_delete_id";//调整
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
                        AjaxJson j = reportDataToProvince(ids.toString());
                        if(j.getObj().equals("200")){
                            count+=tempList.size();
                        }
                    }
                }
            }
        } catch (Exception e) {
            org.jeecgframework.core.util.LogUtil.error("定时上报删除的信息失败", e);//调整
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报删除的信息结束===================");//调整
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报删除的信息总耗时"+times+"毫秒,共同步"+count+"条数据.");//调整
    }

    //上报
    public AjaxJson reportDataToProvince(String ids){
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(ids)){
            List<String> conditions = new ArrayList<>();
            String condition = "id in ('"+ids.replaceAll(",","','")+"')";
            conditions.add(condition);
            List<Map<String,Object>> retList = getDataService.getData(columnNeedInit,tableName,conditions);
            JSONArray jsonArray = JSONArray.fromObject(retList);
            if(jsonArray.size()>0){
                String reportUrl = ResourceUtil.getConfigByName("deleteIdReportToProvince");//调整
                j = getDataService.postData(jsonArray,reportUrl);
                //更新本地库
                if(j.getObj().equals("200")){
                    StringBuffer sqlUpdate = new StringBuffer("DELETE from t_b_report_delete_id b  \n");//调整
                    sqlUpdate.append("where 1=1 \n");
                    for(String tempCondition : conditions){
                        sqlUpdate.append(" and "+tempCondition+"\n");
                    }
                    systemService.executeSql(sqlUpdate.toString());
                    j.setMsg(retList.size()+"条删除信息上报成功");//调整
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
    private static Map<String,String> getColumnNeed(){//调整
        Map<String,String> columnNeed = new HashMap<>();
        columnNeed.put("id","id");
        columnNeed.put("delete_id","deleteId");
        columnNeed.put("type","type");
        return columnNeed;
    }

    private static String getTableNeed(){//调整
        String tableName = "t_b_report_delete_id b";
        return tableName;
    }
}
