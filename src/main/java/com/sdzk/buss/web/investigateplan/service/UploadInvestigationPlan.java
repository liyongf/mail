package com.sdzk.buss.web.investigateplan.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.dangersource.entity.TBWorkDangerRelEntity;
import com.sdzk.buss.web.dangersource.entity.TBWorkProcessManageEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanEntity;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanRelEntity;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 张赛超
 * Created by Lenovo on 17-9-1.
 */
@Service("uploadInvestigationPlan")
public class UploadInvestigationPlan {
    @Autowired
    private SystemService systemService;

    @Transactional
    public AjaxJson uploadInvestigationPlan(String ids){

        String message = null;
        String code = null;
        AjaxJson j = new AjaxJson();

        Map paramMap = new HashMap<>();
        String json = null;

        message = "排查计划上报成功";
        try {
            if (StringUtil.isNotEmpty(ids)){
                //TODO 集团上报接口
                String url = ResourceUtil.getConfigByName("uploadInvestigationPlanUrl");

                try {
                    /**
                     * 获取本煤矿的煤矿名称和煤矿编码
                     * */
                    String token = ResourceUtil.getConfigByName("token_group");
                    String mineCode = ResourceUtil.getConfigByName("mine_code");
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

                    JSONArray reportContents = new JSONArray();

                    for(String id:ids.split(",")){
                        JSONObject reportContent = new JSONObject();

                        TBInvestigatePlanEntity tbInvestigatePlanEntity = systemService.getEntity(TBInvestigatePlanEntity.class, id);
                        if(tbInvestigatePlanEntity != null){
                            reportContent.put("id", id);
                            reportContent.put("startTime", DateUtils.date2Str(tbInvestigatePlanEntity.getStartTime(), DateUtils.date_sdf));
                            reportContent.put("endTime", DateUtils.date2Str(tbInvestigatePlanEntity.getEndTime(),DateUtils.date_sdf));
                            //风险点类型转换
                            reportContent.put("riskPointType", tbInvestigatePlanEntity.getRiskPointType());
                            reportContent.put("completeTime", DateUtils.date2Str(tbInvestigatePlanEntity.getCompleteTime(), DateUtils.date_sdf));
                            //转化受理单位
                            String acceptDepart = tbInvestigatePlanEntity.getAcceptDepart();
                            if(StringUtil.isNotEmpty(acceptDepart)){
                                List<TSDepart> tsDepartList = systemService.findByProperty(TSDepart.class, "id", acceptDepart);
                                if(tsDepartList != null & tsDepartList.size() > 0){
                                    reportContent.put("acceptDepart", tsDepartList.get(0).getDepartname());
                                }else{
                                    reportContent.put("acceptDepart", "");
                                }
                            }
                            //转化受理人
                            String acceptUser = tbInvestigatePlanEntity.getAcceptUser();
                            if(StringUtil.isNotEmpty(acceptUser)){
                                List<TSUser> tsUserList = systemService.findByProperty(TSUser.class, "id", acceptUser);
                                if(tsUserList != null && tsUserList.size() > 0){
                                    reportContent.put("acceptUser", tsUserList.get(0).getRealName());
                                }else{
                                    reportContent.put("acceptUser", "");
                                }
                            }

                            //获取关联的风险点和危险源信息
                            String sql;
                            StringBuffer riskPointIds = new StringBuffer();
                            StringBuffer riskPointName = new StringBuffer();
                            if (Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_LOCATION.equals(tbInvestigatePlanEntity.getRiskPointType())) {
                                sql = "select id, address name from t_b_address_info";
                            } else {
                                sql = "select id, ACTIVITY_NAME name from t_b_activity_manage";
                            }
                            List<Map<String, Object>> riskPointList = systemService.findForJdbc(sql+" where id in (select obj_id from t_b_investigate_plan_rel where plan_id='"+tbInvestigatePlanEntity.getId()+"' and rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT+"' and poit_type='"+tbInvestigatePlanEntity.getRiskPointType()+"')");
                            if (riskPointList != null && riskPointList.size() > 0) {
                                for (Map<String, Object> obj : riskPointList) {
                                    if (StringUtil.isNotEmpty(riskPointIds.toString())) {
                                        riskPointIds.append(",");
                                    }
                                    riskPointIds.append(obj.get("id"));
                                    if (StringUtil.isNotEmpty(riskPointName.toString())) {
                                        riskPointName.append("<br/>");
                                    }
                                    riskPointName.append(obj.get("name"));
                                }
                                tbInvestigatePlanEntity.setRiskPointIds(riskPointIds.toString());
                                tbInvestigatePlanEntity.setRiskPointName(riskPointName.toString());
                            }
                            //获取关联风险
                            StringBuffer riskIds = new StringBuffer();
                            StringBuffer riskName = new StringBuffer();
                            List<Map<String, Object>> riskList = systemService.findForJdbc("select id, ye_mhazard_desc name from t_b_danger_source where id in (select obj_id from t_b_investigate_plan_rel where plan_id='"+tbInvestigatePlanEntity.getId()+"' and rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISK+"')");
                            if (riskList != null && riskList.size() > 0) {
                                for (Map<String, Object> obj : riskList){
                                    if (StringUtil.isNotEmpty(riskIds.toString())) {
                                        riskIds.append(",");
                                    }
                                    riskIds.append(obj.get("id"));
                                    if (StringUtil.isNotEmpty(riskName.toString())) {
                                        riskName.append("<br/>");
                                    }
                                    riskName.append(obj.get("name"));
                                }
                                tbInvestigatePlanEntity.setRiskIds(riskIds.toString());
                                tbInvestigatePlanEntity.setRiskName(riskName.toString());
                            }

                            reportContent.put("riskPointName", tbInvestigatePlanEntity.getRiskPointName());
                            reportContent.put("dangerSourceName", tbInvestigatePlanEntity.getRiskName());
                            reportContent.put("acceptTime", DateUtils.date2Str(tbInvestigatePlanEntity.getAcceptTime(), DateUtils.date_sdf));
                            reportContent.put("investigateTime", DateUtils.date2Str(tbInvestigatePlanEntity.getInvestigateTime(), DateUtils.date_sdf));
                            reportContent.put("investigateDesc", tbInvestigatePlanEntity.getInvestigateDesc());
                            reportContent.put("status", tbInvestigatePlanEntity.getStatus());
                            reportContent.put("investigateType", tbInvestigatePlanEntity.getInvestigateType());
                            reportContent.put("rollBackReason", tbInvestigatePlanEntity.getRollBackReason());
                            reportContent.put("createName", tbInvestigatePlanEntity.getCreateName());
                            reportContent.put("createDate", DateUtils.date2Str(tbInvestigatePlanEntity.getCreateDate(), DateUtils.date_sdf));
                        }
                        /**
                         * 组装relDatas
                         * reportContent.put("relDatas", relDatas);
                         * */
                        JSONArray relDatas = new JSONArray();

                        CriteriaQuery relCq = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
                        relCq.eq("planId", id);
                        relCq.add();
                        List<TBInvestigatePlanRelEntity> tbInvestigatePlanRelEntityList = systemService.getListByCriteriaQuery(relCq, false);
                        if (tbInvestigatePlanRelEntityList != null && tbInvestigatePlanRelEntityList.size() > 0){
                            for (TBInvestigatePlanRelEntity tbInvPlanRel : tbInvestigatePlanRelEntityList){
                                JSONObject relData = new JSONObject();

                                relData.put("id", tbInvPlanRel.getId());
                                relData.put("planId", tbInvPlanRel.getPlanId());
                                relData.put("objId", tbInvPlanRel.getObjId());
                                relData.put("relType", tbInvPlanRel.getRelType());
                                relData.put("poitType", tbInvPlanRel.getPoitType());

                                relDatas.add(relData);
                            }
                        }
                        /**
                         * 组装relDatas结束
                         * */
                        reportContent.put("relDatas", relDatas);

                        /**
                         * 组装TBHiddenDangerHandle数据和TBHiddenDangerExam数据并上报
                         * */
                        StringBuffer hdhIds = new StringBuffer();
                        CriteriaQuery hdhCq = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
                        hdhCq.eq("planId", id);
                        hdhCq.eq("relType","3");
                        hdhCq.add();
                        List<TBInvestigatePlanRelEntity> relEntityList = systemService.getListByCriteriaQuery(hdhCq, false);
                        if(relEntityList!=null && relEntityList.size()>0){
                            for(TBInvestigatePlanRelEntity t : relEntityList){
                                if(!("".equals(hdhIds.toString())) && hdhIds!=null){
                                    hdhIds.append(",");
                                }
                                hdhIds.append(t.getObjId());
                            }
                        }
                        reportHiddenDangerToGroup(hdhIds.toString());
                        /**上报关联TBHiddenDangerHandle和TBHiddenDangerExam数据结束*/

                        reportContents.add(reportContent);
                    }
                    System.out.println(reportContents.toString());
                    paramMap.put("reportContent", reportContents.toString());
                    json = HttpClientUtils.post(url, paramMap, "UTF-8");

                    net.sf.json.JSONObject object= net.sf.json.JSONObject.fromObject(json);  //创建JsonObject对象
                    code = object.getString("code");     //如果code=200，则数据正确可用
                    if("200".equals(code)){
                        message = "排查上报成功！";
                        try {
                            String curUser;
                            try {
                                //定时任务无法获取session
                                curUser = ResourceUtil.getSessionUserName().getRealName();
                            } catch (Exception e) {
                                curUser = "定时任务";
                            }
                            systemService.executeSql("update t_b_investigate_plan set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
                        }catch (Exception e){
                            message = "上报成功但是本地数据库操作失败！";
                        }
                    }else{
                        message = object.getString("message");
                    }
                } catch (NetServiceException e) {
                    message = "排查计划上报失败！";
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "排查计划上报失败！";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    //上报与排查计划关联的危险源到集团
    public void reportHiddenDangerToGroup(String ids){

        if (StringUtil.isNotEmpty(ids)) {
            net.sf.json.JSONArray jsonArray = new net.sf.json.JSONArray();
            StringBuffer examIds = new StringBuffer();
            List<TSUser> userList = systemService.getList(TSUser.class);
            Map<String, String> userMap = new HashMap<>();
            if (userList != null && userList.size() > 0) {
                for (TSUser user : userList) {
                    userMap.put(user.getId(), user.getRealName());
                }
            }
            for (String id : ids.split(",")) {
                net.sf.json.JSONObject data = new net.sf.json.JSONObject();         //这里是包括 TBHiddenDangerHandleEntity 以及 TBHiddenDangerExamEntity 中的数据
                TBHiddenDangerHandleEntity handleEntity = systemService.get(TBHiddenDangerHandleEntity.class, id);
                if (handleEntity != null){
//                    if (StringUtil.isNotEmpty(examIds.toString())) {
//                        examIds.append(",");
//                    }
//                    examIds.append(handleEntity.getHiddenDanger().getId());
                    net.sf.json.JSONObject object = new net.sf.json.JSONObject();       //这里组装的是 TBHiddenDangerExamEntity 中的数据

                    /**组装TBHiddenDangerExamEntity    dangerExam**/
                    TBHiddenDangerExamEntity examEntity = handleEntity.getHiddenDanger();
                    object.put("id", handleEntity.getId());
                    if (StringUtil.isNotEmpty(examEntity.getAddress())) {
                        object.put("address", examEntity.getAddress().getId());
                    }
                    object.put("examDate", DateUtils.date2Str(examEntity.getExamDate(), DateUtils.date_sdf));
//                    if(StringUtils.isNotBlank(examEntity.getShift())){
//                        object.put("shift", DicUtil.getTypeNameByCode("workShift",examEntity.getShift()));
//                    }else{
//                        object.put("shift", "");
//                    }
                    object.put("shift", examEntity.getShift());

                    String fillCardManIds = examEntity.getFillCardManId();
                    if(fillCardManIds == null || fillCardManIds.trim().length() == 0){
                        object.put("fillCardMan", "");
                    }else{
                        StringBuffer manNames = new StringBuffer();
                        for (String manId : fillCardManIds.trim().split(",")){
                            if (StringUtils.isNoneBlank(manNames.toString())) {
                                manNames.append(",");
                            }
                            manNames.append(userMap.get(manId));
                        }
                        object.put("fillCardMan", manNames.toString());
                    }
                    TSDepart dutyUnit = examEntity.getDutyUnit();
                    if(dutyUnit == null){
                        object.put("dutyUnit", "");
                    }else{
                        object.put("dutyUnit", dutyUnit.getDepartname());
                    }
                    object.put("dutyMan", examEntity.getDutyMan());

                    TBDangerSourceEntity dangerSourceEntity = examEntity.getDangerId();
                    if(dangerSourceEntity == null){
                        object.put("dangerSourceName", "");
                    }else{
                        object.put("dangerSourceName", dangerSourceEntity.getYeMhazardDesc());
                    }
                    object.put("problemDesc", examEntity.getProblemDesc());
                    object.put("itemUserId", examEntity.getItemUserId());
                    object.put("itemId", examEntity.getItemId());
                    object.put("hiddenCategory", examEntity.getHiddenCategory());
                    object.put("hiddenNature", examEntity.getHiddenNature());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    if(examEntity.getBeginWellDate() == null){
                        object.put("beginWellDate","");
                    }else{
                        object.put("beginWellDate",sdf.format(examEntity.getBeginWellDate()));
                    }
                    if(examEntity.getEndWellDate() == null){
                        object.put("endWellDate","");
                    }else{
                        object.put("endWellDate",sdf.format(examEntity.getEndWellDate()));
                    }
                    object.put("dealType",examEntity.getDealType());
                    object.put("limitDate", DateUtils.date2Str(examEntity.getLimitDate(), DateUtils.date_sdf));
                    String limitShift = examEntity.getLimitShift();
                    String limitShiftTemp = DicUtil.getTypeNameByCode("workShift",limitShift);
                    object.put("limitShift",limitShiftTemp);
                    if(StringUtil.isNotEmpty(examEntity.getReviewMan())){
                        object.put("reviewMan", getNameById("t_s_base_user", "realname", examEntity.getReviewMan().getId()));
                    }else{
                        object.put("reviewMan", "");
                    }
                    object.put("examType",examEntity.getExamType());

                    String sjjcDept = examEntity.getSjjcDept();
                    object.put("sjjcDept",sjjcDept);
                    String sjjcCheckMan = examEntity.getSjjcCheckMan();
                    object.put("sjjcCheckMan",sjjcCheckMan);
                    String proType = examEntity.getProType();
                    if(StringUtils.isNotBlank(proType)){
                        object.put("proType",DicUtil.getTypeNameByCode("proType",proType));
                    }else{
                        object.put("proType","");
                    }
                    object.put("checkType",examEntity.getCheckType());
                    object.put("remark",examEntity.getRemark());
                    object.put("createName",examEntity.getCreateName());
                    object.put("createDate", sdf.format(examEntity.getCreateDate()));
                    object.put("isLsSub", examEntity.getIsLsSub());
                    object.put("isLsProv", examEntity.getIsLsProv());
                    object.put("reportName", examEntity.getReportName());
                    object.put("reportStatus", examEntity.getReportStatus());
                    if(examEntity.getDangerId() != null){
                        object.put("dangerId", examEntity.getDangerId().getId());
                    }else{
                        object.put("dangerId", null);
                    }
                    object.put("gzap", examEntity.getGzap());
                    object.put("hiddenNatureOriginal", examEntity.getHiddenNatureOriginal());
                    object.put("deductScores", examEntity.getDeductScores());
                    object.put("cancelDate", examEntity.getCancelDate());
                    object.put("hiddenType", examEntity.getHiddenType());
                    /**至此，hiddendangerexamentity中的数据组装完成**/

                    data.put("object",object );

                    data.put("id", handleEntity.getId());
                    if(handleEntity.getHiddenDanger() != null){
                        data.put("hiddenDangerId", handleEntity.getHiddenDanger().getId());
                    }else{
                        data.put("hiddenDangerId", null);
                    }
                    data.put("modifyDate",DateUtils.date2Str(handleEntity.getModifyDate(), DateUtils.datetimeFormat));
                    data.put("modifyShift", handleEntity.getModifyShift());
                    data.put("modifyMan", getNameById("t_s_base_user", "realname", handleEntity.getModifyMan()));
                    data.put("reviewDate", DateUtils.date2Str(handleEntity.getReviewDate(), DateUtils.datetimeFormat));
                    data.put("reviewShift", handleEntity.getReviewShift());
                    data.put("reviewMan", getNameById("t_s_base_user", "realname", handleEntity.getReviewMan()));
                    data.put("reviewResult", handleEntity.getReviewResult());
                    data.put("handlelStatus", handleEntity.getHandlelStatus());
                    data.put("rollBackRemark", handleEntity.getRollBackRemark());
                    data.put("createName", handleEntity.getCreateName());
                    data.put("createBy", handleEntity.getCreateBy());
                    data.put("createDate", DateUtils.date2Str(handleEntity.getCreateDate(), DateUtils.datetimeFormat));
                    data.put("rectMeasures", handleEntity.getRectMeasures());
                    data.put("reviewReport", handleEntity.getReviewReport());
                    data.put("verifyDate", handleEntity.getVerifyDate());
                    data.put("verifyUnit", handleEntity.getVerifyDate());
                    data.put("verifyStatus", handleEntity.getVerifyUnit());
                    data.put("verifyMan", handleEntity.getVerifyMan());
                    data.put("accepDate", handleEntity.getAccepDate());
                    data.put("acceptor", handleEntity.getAcceptor());
                    data.put("accepUnit", handleEntity.getAccepUnit());
                    data.put("accepReport", handleEntity.getAccepReport());

//                    String itemUserId =  examEntity.getItemUserId();
//                    if(StringUtils.isNotBlank(itemUserId)){
//                        String userIds [] = itemUserId.split(",");
//                        StringBuffer sb = new StringBuffer();
//                        for(String userId : userIds){
//                            TSUser user = systemService.getEntity(TSUser.class,userId);
//                            if(StringUtils.isNotBlank(sb.toString())){
//                                sb.append(",");
//                            }
//                            sb.append(user.getUserName());
//                        }
//                        object.put("itemUserName",sb.toString());
//                    }else{
//                        object.put("itemUserName","");
//                    }
                    jsonArray.add(data);
                }
            }

            if (jsonArray.size() > 0){
                //上传
                String mineCode = ResourceUtil.getConfigByName("mine_code");
                String token = ResourceUtil.getConfigByName("token_group");
                String reportContent = jsonArray.toString();
                String hiddenDangerReport = ResourceUtil.getConfigByName("hiddenDangerReport_group");
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
                try {
                    response = HttpClientUtils.post(hiddenDangerReport, paramMap, "UTF-8");
                } catch (NetServiceException e) {
                }
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(response);

                String retCode = jsonObject.getString("code");
                if("200".equals(retCode)){
                    try{
                        TSUser currUser = ResourceUtil.getSessionUserName();
                        systemService.executeSql("update t_b_hidden_danger_handle set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ currUser.getId() +"' where id in ('"+ids.replace(",","','")+"')");
                    }catch (Exception e){ }
                }
            }
        }
    }

    private String getNameById(String table,String field, String id) {
        List<String> name = systemService.findListbySql("select "+field+" from "+table+" where id = '"+id+"'");
        if (name != null && name.size() > 0) {
            return name.get(0);
        }
        return null;
    }


}
