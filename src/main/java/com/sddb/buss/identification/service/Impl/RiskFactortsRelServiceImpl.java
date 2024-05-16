package com.sddb.buss.identification.service.Impl;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.identification.service.RiskFactortsRelServiceI;
import com.sddb.buss.identification.service.RiskIdentificationServiceI;
import com.sdzk.buss.web.common.utils.AesUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service("riskFactortsRelService")
@Transactional
public class RiskFactortsRelServiceImpl extends CommonServiceImpl implements RiskFactortsRelServiceI {

    @Autowired
    private SystemService systemService;

    @Override
    public AjaxJson riskFactortsRelReportToGroup(String ids){
        AjaxJson j = new AjaxJson();
        JSONArray jsonArray = new JSONArray();
        for (String id : ids.split(",")) {
            JSONObject data = new JSONObject();
            RiskFactortsRel riskFactortsRel = systemService.get(RiskFactortsRel.class, id);
            if (riskFactortsRel != null){
                data.put("id", riskFactortsRel.getId());
                data.put("hazardFactorsEntity", StringUtil.isNotEmpty(riskFactortsRel.getHazardFactorsEntity())?riskFactortsRel.getHazardFactorsEntity().getId():"");
                data.put("riskIdentificationEntity", StringUtil.isNotEmpty(riskFactortsRel.getRiskIdentificationEntity())?riskFactortsRel.getRiskIdentificationEntity().getId():"");
                data.put("hfLevel", riskFactortsRel.getHfLevel());
                data.put("hfManageMeasure", riskFactortsRel.getHfManageMeasure());
                data.put("manageDepart", StringUtil.isNotEmpty(riskFactortsRel.getManageDepart())?riskFactortsRel.getManageDepart().getDepartname():"");
                data.put("manageUser", StringUtil.isNotEmpty(riskFactortsRel.getManageUser())?riskFactortsRel.getManageUser().getRealName():"");
                jsonArray.add(data);
            }
        }

        if (jsonArray.size() > 0){
            //上传
            String mineCode = ResourceUtil.getConfigByName("mine_code");
            String token = ResourceUtil.getConfigByName("token_group");
            String reportContent = jsonArray.toString();
            String riskFactortsRelReport = ResourceUtil.getConfigByName("riskFactortsRelReport_group");
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
            LogUtil.info("riskFactortsRelReport=" + riskFactortsRelReport);
            LogUtil.info("jsonArray.size()=" + jsonArray.size() + ",reportContent="+reportContent.length());
            try {
                response = HttpClientUtils.post(riskFactortsRelReport, paramMap, "UTF-8");
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
                    systemService.executeSql("update t_b_risk_factors_rel set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
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
