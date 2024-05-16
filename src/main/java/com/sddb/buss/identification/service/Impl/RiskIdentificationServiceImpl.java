package com.sddb.buss.identification.service.Impl;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.identification.service.RiskIdentificationServiceI;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sdzk.buss.web.common.utils.AesUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Service("riskIdentificationService")
@Transactional
public class RiskIdentificationServiceImpl extends CommonServiceImpl implements RiskIdentificationServiceI {

    @Autowired
    private SystemService systemService;

    @Override
    public AjaxJson riskIdentificationReportToGroup(String ids){
        AjaxJson j = new AjaxJson();
        JSONArray jsonArray = new JSONArray();
        for (String id : ids.split(",")) {
            JSONObject data = new JSONObject();
            RiskIdentificationEntity riskIdentificationEntity = systemService.get(RiskIdentificationEntity.class, id);
            if (riskIdentificationEntity != null){
                data.put("id", riskIdentificationEntity.getId());
                data.put("identificationType", riskIdentificationEntity.getIdentificationType());
                data.put("address", StringUtil.isNotEmpty(riskIdentificationEntity.getAddress()) ? riskIdentificationEntity.getAddress().getId() : "");
                data.put("riskType", riskIdentificationEntity.getRiskType());
                data.put("riskDesc", riskIdentificationEntity.getRiskDesc());
                data.put("riskLevel", riskIdentificationEntity.getRiskLevel());
                data.put("manageLevel", riskIdentificationEntity.getManageLevel());
                data.put("dutyManager", riskIdentificationEntity.getDutyManager());
                data.put("identifiDate", DateUtils.date2Str(riskIdentificationEntity.getIdentifiDate(), DateUtils.date_sdf));
                data.put("expDate", DateUtils.date2Str(riskIdentificationEntity.getExpDate(), DateUtils.date_sdf));
                data.put("status", riskIdentificationEntity.getStatus());
                data.put("post",StringUtil.isNotEmpty(riskIdentificationEntity.getPost()) ? riskIdentificationEntity.getPost().getId() : "");
                data.put("specificType", riskIdentificationEntity.getSpecificType());
                data.put("specificName", riskIdentificationEntity.getSpecificName());
                data.put("isDel", riskIdentificationEntity.getIsDel());
                jsonArray.add(data);
            }
        }

        if (jsonArray.size() > 0){
            //上传
            String mineCode = ResourceUtil.getConfigByName("mine_code");
            String token = ResourceUtil.getConfigByName("token_group");
            String reportContent = jsonArray.toString();
            String riskIdentificationReport = ResourceUtil.getConfigByName("riskIdentificationReport_group");
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
            LogUtil.info("riskIdentificationReport=" + riskIdentificationReport);
            LogUtil.info("jsonArray.size()=" + jsonArray.size() + ",reportContent="+reportContent.length());
            try {
                response = HttpClientUtils.post(riskIdentificationReport, paramMap, "UTF-8");
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
                    systemService.executeSql("update t_b_risk_identification set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
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
