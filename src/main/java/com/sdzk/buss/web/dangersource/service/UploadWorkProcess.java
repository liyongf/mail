package com.sdzk.buss.web.dangersource.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.dangersource.entity.TBWorkDangerRelEntity;
import com.sdzk.buss.web.dangersource.entity.TBWorkProcessManageEntity;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
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
 * Created by Lenovo on 17-9-1.
 * 张赛超
 */
@Service("UploadWorkProcess")
public class UploadWorkProcess {
    @Autowired
    private SystemService systemService;

    public AjaxJson uploadWorkProcess(String ids){

        String message = null;
        String code = null;
        AjaxJson j = new AjaxJson();

        Map paramMap = new HashMap<>();
        String json = null;

        message = "作业过程上报成功";
        try {
            if (StringUtil.isNotEmpty(ids)){
                /**
                 * Author：张赛超
                 * */
                //TODO 集团上报接口
                String url = ResourceUtil.getConfigByName("uploadWorkProcessUrl");

                try {
                    /**
                     * 获取本煤矿的煤矿名称和煤矿编码
                     * */
                    String mineCode = ResourceUtil.getConfigByName("mine_code");

//                    /**
//                     * 加密过程
//                     * */
//                    String tempToken = "token=" + token + "&mineCode=" + mineCode;
//                    String ciphertext = null;
//                    try {
//                        ciphertext = AesUtil.encryptWithIV(tempToken, token);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    paramMap.put("token", ciphertext);

                    paramMap.put("mineCode", mineCode);

                    JSONArray reportContents = new JSONArray();

                    for(String id:ids.split(",")){
                        JSONObject reportContent = new JSONObject();

                        TBWorkProcessManageEntity tbWorkProcessManageEntity = systemService.getEntity(TBWorkProcessManageEntity.class, id);
                        if(tbWorkProcessManageEntity != null){
                            reportContent.put("id", id);
                            reportContent.put("name", tbWorkProcessManageEntity.getName());
                            reportContent.put("major", tbWorkProcessManageEntity.getMajor());
                            reportContent.put("createDate", DateUtils.date2Str(tbWorkProcessManageEntity.getCreateDate(), DateUtils.date_sdf));
                        }
                        /**
                         * 组装riskdatas
                         * reportContent.put("riskdatas", riskdatas);
                         * */
                        JSONArray riskdatas = new JSONArray();

                        CriteriaQuery relCq = new CriteriaQuery(TBWorkDangerRelEntity.class);
                        relCq.eq("workprocess", tbWorkProcessManageEntity);
                        relCq.add();
                        List<TBWorkDangerRelEntity> workDangerRelEntityList = systemService.getListByCriteriaQuery(relCq, false);
                        if (workDangerRelEntityList != null && workDangerRelEntityList.size() > 0){
                            for (TBWorkDangerRelEntity tbWork : workDangerRelEntityList){
                                JSONObject riskdata = new JSONObject();

                                if(tbWork.getDanger() == null){
                                    continue;
                                }
                                riskdata.put("id", tbWork.getDanger().getId());
                                riskdata.put("yeMhazardDesc", tbWork.getDanger().getYeMhazardDesc());
//                                riskdata.put("yeProfession", tbWork.getDanger().getYeProfession());
                                String yeProfession = tbWork.getDanger().getYeProfession();
                                //专业分类
                                if(StringUtil.isNotEmpty(yeProfession)){
                                    String nameTemp = DicUtil.getTypeNameByCode("proCate_gradeControl",yeProfession);
                                    riskdata.put("yeProfession", nameTemp);
                                }else{
                                    riskdata.put("yeProfession", "");
                                }
                                //事故类型
                                String yeAccident = tbWork.getDanger().getYeAccident();
                                if(StringUtil.isNotEmpty(yeAccident)){
                                    String array[] = yeAccident.split(",");
                                    StringBuffer sb = new StringBuffer();
                                    for(String str : array){
                                        String nameTemp = DicUtil.getTypeNameByCode("accidentCate",str);
                                        if(StringUtil.isNotEmpty(sb.toString())){
                                            sb.append(",");
                                        }
                                        sb.append(nameTemp);
                                    }
                                    riskdata.put("yeAccident", sb.toString());
                                }else{
                                    riskdata.put("yeAccident", "");
                                }
                                riskdata.put("yeReference", tbWork.getDanger().getYeReference());
                                riskdata.put("yeLocation", tbWork.getDanger().getYeLocation());
                                riskdata.put("yeDistance", tbWork.getDanger().getYeDistance());
                                riskdata.put("yeSurrounding", tbWork.getDanger().getYeSurrounding());
                                riskdata.put("yeStandard", tbWork.getDanger().getYeStandard());
                                riskdata.put("yeMonitor", tbWork.getDanger().getYeMonitor());
                                riskdata.put("yeEmergency", tbWork.getDanger().getYeEmergency());
                                riskdata.put("yeResDepart", tbWork.getDanger().getYeResDepart());
                                riskdata.put("yePossiblyHazard", tbWork.getDanger().getYePossiblyHazard());
                                String yeProbability = tbWork.getDanger().getYeProbability();
                                if(StringUtil.isNotEmpty(yeProbability)){
                                    //取字典可能性
                                    String nameTemp = DicUtil.getTypeNameByCode("probability",yeProbability);
                                    riskdata.put("yeProbability", nameTemp);
                                }else{
                                    riskdata.put("yeProbability", "");
                                }

//                                riskdata.put("yeCost", tbWork.getDanger().getYeCost());
                                String yeCost = tbWork.getDanger().getYeCost();
                                if(StringUtil.isNotEmpty(yeCost)){
                                    String nameTemp = DicUtil.getTypeNameByCode("hazard_fxss",yeCost);
                                    riskdata.put("yeCost", nameTemp);
                                }else{
                                    riskdata.put("yeCost", "");
                                }
//                                riskdata.put("yeHazardCate", tbWork.getDanger().getYeHazardCate());
                                //隐患类型取字典值
                                String yeHazardCate = tbWork.getDanger().getYeHazardCate();
                                if(StringUtil.isNotEmpty(yeHazardCate)){
                                    String nameTemp = DicUtil.getTypeNameByCode("hazardCate",yeHazardCate);
                                    riskdata.put("yeHazardCate", nameTemp);
                                }else{
                                    riskdata.put("yeHazardCate", "");
                                }
                                //风险等级取字典值
                                String yeRiskGrade = tbWork.getDanger().getYeRiskGrade();
                                if(StringUtil.isNotEmpty(yeRiskGrade)){
                                    String nameTemp = DicUtil.getTypeNameByCode("riskLevel",yeRiskGrade);
                                    riskdata.put("yeRiskGrade", nameTemp);
                                }else{
                                    riskdata.put("yeRiskGrade", "");
                                }

                                riskdata.put("yeCaseNum", tbWork.getDanger().getYeCaseNum());
                                riskdata.put("isMajor", tbWork.getDanger().getIsMajor());
                                riskdata.put("yeRecognizeTime", DateUtils.date2Str(tbWork.getDanger().getYeRecognizeTime(), DateUtils.date_sdf));
                                Double lecRiskPossibility = tbWork.getDanger().getLecRiskPossibility();
                                if(lecRiskPossibility != null){
                                    String lecRiskPossibilityStr = lecRiskPossibility.toString();
                                    String nameTemp = DicUtil.getTypeNameByCode("lec_risk_probability",lecRiskPossibilityStr);
                                    riskdata.put("lecRiskPossibility", nameTemp);
                                }else{
                                    riskdata.put("lecRiskPossibility", null);
                                }

                                Double lecRiskLoss = tbWork.getDanger().getLecRiskLoss();
                                if(lecRiskLoss != null){
                                    String lecRiskLossStr = lecRiskLoss.toString();
                                    String nameTemp = DicUtil.getTypeNameByCode("lec_risk_loss",lecRiskLossStr);
                                    riskdata.put("lecRiskLoss", nameTemp);
                                }else{
                                    riskdata.put("lecRiskLoss", null);
                                }
                                Double lecExposure = tbWork.getDanger().getLecExposure();
                                if(lecExposure != null){
                                    String str = lecExposure.toString();
                                    String nameTemp = DicUtil.getTypeNameByCode("lec_exposure",str);
                                    riskdata.put("lecExposure", nameTemp);
                                }else{
                                    riskdata.put("lecExposure", null);
                                }
                                Double lecRiskValue = tbWork.getDanger().getLecRiskValue();
                                if (lecRiskValue != null) {
                                    riskdata.put("lecRiskValue", lecRiskValue.toString());
                                } else {
                                    riskdata.put("lecRiskValue", null);
                                }

                                riskdata.put("origin", tbWork.getDanger().getOrigin());
                                riskdata.put("riskValue",tbWork.getDanger().getRiskValue());

                                riskdatas.add(riskdata);
                            }
                        }
                        /**
                         * 组装riskdatas结束
                         * */
                        reportContent.put("riskdatas", riskdatas);

                        reportContents.add(reportContent);
                    }
                    System.out.println(reportContents.toString());
                    paramMap.put("reportContent", reportContents.toString());
                    json = HttpClientUtils.post(url, paramMap, "UTF-8");

                    net.sf.json.JSONObject object= net.sf.json.JSONObject.fromObject(json);  //创建JsonObject对象
                    code = object.getString("code");     //如果code=200，则数据正确可用
                    if("200".equals(code)){
                        message = "作业上报成功！";
                    }else{
                        message = object.getString("message");
                    }

                } catch (NetServiceException e) {
                    message = "作业过程上报失败！";
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "作业过程上报失败！";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
}

