package com.sdzk.buss.web.common.task;

/**
 * Created by Administrator on 17-9-27.
 */

import com.sddb.buss.identification.entity.MonthRiskIdentificationEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.utils.AesUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时上报月度风险至 新矿集团
 */
@Service("monthRiskReportGroupTask")
public class MonthRiskReportGroupTask {

    @Autowired
    private SystemService systemService;

    public void reportMonthRisk(){
        long start = System.currentTimeMillis();
        long count = 0;
        LogUtil.info("===================定时上报月度风险开始===================");
        try {
            String sql = "SELECT id FROM t_b_month_risk_identification WHERE report_group_status='0' or ISNULL(report_group_status) or report_group_status = '' or update_date>=report_group_time";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                int step = 100;
                int times = (idList.size()-1)/step;
                for(int i=0;i<=times;i++){
                    String ids = "";
                    int startIndex = i*step;
                    int endIndex = (i+1)*step > idList.size()?idList.size(): (i+1)*step;
                    for(int k=startIndex;k<endIndex;k++){
                        if(ids.length()>0){
                            ids += ",";
                        }
                        ids += idList.get(k);
                    }

                    count = count + (endIndex-startIndex);
                    monthRiskReportToGroup(ids);
                }
            }
        } catch (Exception e) {
            count = 0;
            LogUtil.error("定时上报月度失败", e);
        }
        LogUtil.info("===================定时上报月度结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info("定时上报月度总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public AjaxJson monthRiskReportToGroup(String ids){
        AjaxJson j = new AjaxJson();
        JSONArray jsonArray = new JSONArray();
        for (String id : ids.split(",")) {
            JSONObject data = new JSONObject();
            MonthRiskIdentificationEntity riskIdentificationEntity = systemService.get(MonthRiskIdentificationEntity.class, id);
            if (riskIdentificationEntity != null){
                /**地点*/
                if (StringUtils.isNotEmpty(riskIdentificationEntity.getAddress())) {
                    TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class, riskIdentificationEntity.getAddress());
                    if (addressInfoEntity != null) {
                        riskIdentificationEntity.setAddressNameTemp(addressInfoEntity.getAddress());
                    }
                }

                data.put("id", riskIdentificationEntity.getId());
                data.put("month", riskIdentificationEntity.getMonth());
                data.put("identificationType", riskIdentificationEntity.getIdentificationType());
                data.put("address", StringUtil.isNotEmpty(riskIdentificationEntity.getAddressNameTemp()) ? riskIdentificationEntity.getAddressNameTemp() : "");
                data.put("unitSpecialty", riskIdentificationEntity.getUnitSpecialty());
                data.put("riskType", riskIdentificationEntity.getRiskType());
                data.put("riskLevel", riskIdentificationEntity.getRiskLevel());
                data.put("riskDesc", riskIdentificationEntity.getRiskDesc());
                data.put("hazardFactors", riskIdentificationEntity.getHazardFactors());
                data.put("controlMeasures", riskIdentificationEntity.getControlMeasures());
                data.put("solveTime",riskIdentificationEntity.getSolveTime());
                data.put("planName",riskIdentificationEntity.getPlanName());
                data.put("dutyManager",riskIdentificationEntity.getDutyManager());
                data.put("manageLevel",riskIdentificationEntity.getManageLevel());
                data.put("technical",riskIdentificationEntity.getTechnical());
                data.put("supervision",riskIdentificationEntity.getSupervision());
                data.put("remark",riskIdentificationEntity.getRemark());
                data.put("isDel",riskIdentificationEntity.getIsDel());
                data.put("createName",riskIdentificationEntity.getCreateName());
                data.put("createBy",riskIdentificationEntity.getCreateBy());
                data.put("createDate",riskIdentificationEntity.getCreateDate()==null?"": DateUtils.date2Str(riskIdentificationEntity.getCreateDate(), DateUtils.date_sdf));
                data.put("updateName",riskIdentificationEntity.getUpdateName());
                data.put("updateBy",riskIdentificationEntity.getUpdateBy());
                data.put("updateDate",riskIdentificationEntity.getUpdateDate()==null?"": DateUtils.date2Str(riskIdentificationEntity.getUpdateDate(), DateUtils.date_sdf));
                data.put("type", riskIdentificationEntity.getType());
                data.put("quarter", riskIdentificationEntity.getQuarter());
                jsonArray.add(data);
            }
        }

        if (jsonArray.size() > 0){
            //上传
            String mineCode = ResourceUtil.getConfigByName("mine_code");
            String token = ResourceUtil.getConfigByName("token_group");
            String reportContent = jsonArray.toString();
            String monthRiskReport = ResourceUtil.getConfigByName("monthRiskReport_group");
            Map<String, Object> paramMap = new HashMap<>();

            /**
             * 加密过程
             * */
            String tempToken = "token=" + token + "&mineCode=" + mineCode;
            String ciphertext = null;
            try {
                ciphertext = AesUtil.encryptWithIV(tempToken, token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            paramMap.put("token", ciphertext);
            paramMap.put("mineCode", mineCode);
            paramMap.put("reportContent", reportContent);
            String response = null;
            LogUtil.info("monthRiskReport=" + monthRiskReport);
            LogUtil.info("jsonArray.size()=" + jsonArray.size() + ",reportContent="+reportContent.length());
            try {
                response = HttpClientUtils.post(monthRiskReport, paramMap, "UTF-8");
                LogUtil.info("上报接口调用成功返回：" + response);
            } catch (NetServiceException e) {
                j.setSuccess(false);
                j.setMsg("上报接口调用失败, 网络连接错误");
                LogUtil.error("上报接口调用异常",e);
                return j;
            }
            JSONObject jsonObject = JSONObject.fromObject(response);

            String retCode = jsonObject.getString("code");
            if("200".equals(retCode)){
                j.setMsg("上报成功");
                try{
                    String curUser;
                    try {
                        //定时任务无法获取session
                        curUser = ResourceUtil.getSessionUserName().getRealName();
                    } catch (Exception e) {
                        curUser = "定时任务";
                    }
                    systemService.executeSql("update t_b_month_risk_identification set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
                }catch (Exception e){
                    j.setMsg("上报成功但是本地数据库操作失败！");
                }
            }else{
                j.setMsg(jsonObject.optString("message"));
            }
        } else {
            j.setMsg("所选中记录不存在或状态已改变");
        }
        return j;
    }
}
