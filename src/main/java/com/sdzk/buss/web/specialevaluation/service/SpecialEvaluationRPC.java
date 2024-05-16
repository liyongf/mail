package com.sdzk.buss.web.specialevaluation.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.address.entity.TBAddressDepartRelEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.StaticDataMap;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerAddresstRelEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.dangersource.entity.TbActivityManageEntity;
import com.sdzk.buss.web.dangersource.entity.TbHazardManageEntity;
import com.sdzk.buss.web.specialevaluation.entity.TBSeDsRelationEntity;
import com.sdzk.buss.web.specialevaluation.entity.TBSpecialEvaluationEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import com.sdzk.buss.web.uploadthreads.UploadThread;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Author：张赛超
 * Created by Lenovo on 17-7-11.
 */
@Service("SpecialEvaluationRPC")
public class SpecialEvaluationRPC {
    @Autowired
    private SystemService systemService;
    @Autowired
    private TBSpecialEvaluationServiceI tBSpecialEvaluationService;

    private String dateToString(java.util.Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = "";
        if(date != null){
            str=sdf.format(date);
        }
        return str;
    }

    public AjaxJson mineReport(String ids,boolean isFromTask){

        String message = null;
        String code = null;
        AjaxJson j = new AjaxJson();

        Map paramMap = new HashMap<>();
        String json = null;

        message = "专项风险辨识更新成功";
        try {
            if (StringUtil.isNotEmpty(ids)){

                /**
                 * Author：张赛超
                 * */
                //TODO 煤监局上报接口

                String url = ResourceUtil.getConfigByName("specialEvaluateReport");
                try {
                    /**
                     * 获取本煤矿的煤矿名称和煤矿编码
                     * */
                    String token = ResourceUtil.getConfigByName("token");
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

                        TBSpecialEvaluationEntity tbSpecialEvaluationEntity = systemService.getEntity(TBSpecialEvaluationEntity.class, id);

                        reportContent.put("id", id);
                        reportContent.put("name", tbSpecialEvaluationEntity.getName());
                        //将辨识负责人从id转换成汉字
                        TSBaseUser leader = systemService.get(TSBaseUser.class, tbSpecialEvaluationEntity.getLeader() );
                        if (leader != null){
                            tbSpecialEvaluationEntity.setLeaderTemp(leader.getRealName());
                        }
                        reportContent.put("leader", tbSpecialEvaluationEntity.getLeaderTemp());
                        reportContent.put("leaderType", tbSpecialEvaluationEntity.getLeaderType());
                        reportContent.put("evalTime", dateToString(tbSpecialEvaluationEntity.getTime()));
                        //参与人从id转换成人名——开始
                        String participants = "";
                        StringBuffer sb2 = new StringBuffer();
                        for(String participant:tbSpecialEvaluationEntity.getParticipant().split(",")){
                            TSBaseUser tsBaseUser = systemService.getEntity(TSBaseUser.class, participant);
                            if(StringUtils.isNotBlank(sb2.toString())){
                                sb2.append(",");
                            }
                            if(tsBaseUser!=null){
                                sb2.append(tsBaseUser.getRealName());
                            }

                        }
                        reportContent.put("participants",sb2.toString());
                        //参与人从id转换成人名——结束
                        //根据id转成对应的地点名称——开始
                        if(StringUtil.isEmpty(tbSpecialEvaluationEntity.getLocation())){
                            reportContent.put("location", "");
                        }else{
                            TBAddressInfoEntity location = systemService.getEntity(TBAddressInfoEntity.class, tbSpecialEvaluationEntity.getLocation());
                            if (StringUtil.isNotEmpty(location)){
                                reportContent.put("location", location.getAddress());
                            }else{
                                reportContent.put("location", "");
                            }
                        }
                        //根据id转成对应的地点——结束
                        reportContent.put("remark", tbSpecialEvaluationEntity.getRemark());
                        reportContent.put("evalType", tbSpecialEvaluationEntity.getType());

                        /**
                         * 组装riskdatas
                         * reportContent.put("riskdatas", riskdatas);
                         * */

                        CriteriaQuery relationCq = new CriteriaQuery(TBSeDsRelationEntity.class);
                        relationCq.eq("sepcialEvaluationId", id);
                        relationCq.add();
                        List<TBSeDsRelationEntity> relations = systemService.getListByCriteriaQuery(relationCq, false);

                        JSONArray riskdatas = new JSONArray();

                        if (relations != null && relations.size() > 0){
                            for(TBSeDsRelationEntity tbSeDsRelationEntity : relations){
                                //获取对应的danger_source_id
                                TBDangerSourceEntity tbDangerSourceEntity = systemService.getEntity(TBDangerSourceEntity.class, tbSeDsRelationEntity.getDangerSourceId());

                                JSONObject riskdata = new JSONObject();
                                if(tbDangerSourceEntity == null){
                                    continue;
                                }

                                riskdata.put("id",tbDangerSourceEntity.getId());
                                riskdata.put("yeMhazardDesc",tbDangerSourceEntity.getYeMhazardDesc());
                                String yeProfession = tbDangerSourceEntity.getYeProfession();
                                String professionTemp = StaticDataMap.yeProfessionMap.get(yeProfession);
                                professionTemp = professionTemp == null?StaticDataMap.PROFESSION_OTHER:yeProfession;
                                riskdata.put("yeProfession",professionTemp);
                                String accident = tbDangerSourceEntity.getYeAccident();
                                String accidentTemp = "";
                                StringBuffer sb = new StringBuffer();
                                if(StringUtils.isNotBlank(accident)){
                                    String aa [] = accident.split(",");

                                    boolean isContainsOther = false;
                                    for(String str : aa){
                                        String bb =  StaticDataMap.yeAccidentMap.get(str);
                                        if(StringUtils.isBlank(bb)){
                                            bb = StaticDataMap.ACCIDENT_OTHER;
                                            isContainsOther = true;
                                            if(StringUtils.isNotBlank(sb.toString())){
                                                sb.append(",");
                                            }
                                            sb.append(bb);
                                        }else{
                                            bb = str;
                                        }
                                        if(!isContainsOther){
                                            if(StringUtils.isNotBlank(sb.toString())){
                                                sb.append(",");
                                            }
                                            sb.append(bb);
                                        }
                                    }
                                }
                                accidentTemp = sb.toString();
                                riskdata.put("yeAccident",accidentTemp);
                                riskdata.put("yeStandard",tbDangerSourceEntity.getYeStandard());
                                riskdata.put("yePossiblyHazard",tbDangerSourceEntity.getYePossiblyHazard());
                                riskdata.put("yeHazardCate",tbDangerSourceEntity.getYeHazardCate());
                                riskdata.put("yeRiskGrade",tbDangerSourceEntity.getYeRiskGrade());
                                riskdata.put("yeCaseNum",tbDangerSourceEntity.getYeCaseNum());
                                riskdata.put("yeRecognizeTime",DateUtils.date2Str(tbDangerSourceEntity.getYeRecognizeTime(), DateUtils.datetimeFormat));
                                riskdata.put("isMajor",tbDangerSourceEntity.getIsMajor());
                                riskdata.put("riskValue",tbDangerSourceEntity.getRiskValue());
                                riskdata.put("manageMeasure",tbDangerSourceEntity.getManageMeasure());
                                riskdata.put("LECRiskValue",tbDangerSourceEntity.getLecRiskValue() == null ?"":tbDangerSourceEntity.getLecRiskValue().toString());
                                riskdata.put("docSource",tbDangerSourceEntity.getDocSource());
                                riskdata.put("sectionName",tbDangerSourceEntity.getSectionName());
                                String postId = tbDangerSourceEntity.getPost().getId();
                                if(StringUtils.isNotBlank(postId)){
                                    TBPostManageEntity post = systemService.getEntity(TBPostManageEntity.class,postId);
                                    if(post != null){
                                        riskdata.put("dutyPost",post.getPostName());
                                    }else{
                                        riskdata.put("dutyPost","");
                                    }
                                }else{
                                    riskdata.put("dutyPost","");
                                }
                                String actId = tbDangerSourceEntity.getActivity().getId();
                                if(StringUtils.isNotBlank(actId)){
                                    TbActivityManageEntity act = systemService.getEntity(TbActivityManageEntity.class,actId);
                                    if(act != null){
                                        riskdata.put("activity",act.getActivityName());
                                    }else{
                                        riskdata.put("activity","");
                                    }
                                }else{
                                    riskdata.put("activity","");
                                }
                                String hazId = tbDangerSourceEntity.getHazard().getId();
                                if(StringUtils.isNotBlank(hazId)){
                                    TbHazardManageEntity haz = systemService.getEntity(TbHazardManageEntity.class,hazId);
                                    if(haz != null){
                                        riskdata.put("hazardManageName",haz.getHazardName());
                                    }else{
                                        riskdata.put("hazardManageName","");
                                    }
                                }else{
                                    riskdata.put("hazardManageName","");
                                }

                                String damageType = tbDangerSourceEntity.getDamageType();
                                String damageTypeTemp = StaticDataMap.damageTypeMap.get(damageType);
                                damageTypeTemp = damageTypeTemp == null?StaticDataMap.DAMAGETYPE_OTHER:damageType;
                                riskdata.put("damageType",damageTypeTemp);
                                riskdata.put("hiddenLevel",tbDangerSourceEntity.getHiddenLevel());
                                riskdata.put("yeCaseNum",tbDangerSourceEntity.getYeCaseNum());

                                riskdatas.add(riskdata);
                            }
                        }

                        /**
                         * 组装riskdatas结束
                         * */
                        reportContent.put("riskdatas", riskdatas);

                        reportContents.add(reportContent);
                    }
                    ////////////////////////////
                    // 上传风险点
                    String addressReportUrl = ResourceUtil.getConfigByName("addressReportUrl");
                    String addressReportContent = getAddressReportContent();
                    Map<String,String> addressParamMap = new HashMap<String,String>();
                    addressParamMap.put("token", ciphertext);
                    addressParamMap.put("mineCode", mineCode);
                    addressParamMap.put("reportContent", addressReportContent);
                    UploadThread addressThread = new UploadThread(addressReportUrl,"UTF-8",addressParamMap);
                    addressThread.start();
                    // 上传一类危险源
                    String hazardReportUrl = ResourceUtil.getConfigByName("hazardReportUrl");
                    String hazardReportContent = getHazardReportContent();
                    Map<String,String> hazardParamMap = new HashMap<String,String>();
                    hazardParamMap.put("token", ciphertext);
                    hazardParamMap.put("mineCode", mineCode);
                    hazardParamMap.put("reportContent", hazardReportContent);
                    UploadThread hazardThread = new UploadThread(hazardReportUrl,"UTF-8",hazardParamMap);
                    hazardThread.start();
                    // 上传风险点关联单位
                    String addressDepartRelUrl = ResourceUtil.getConfigByName("addressDepartRelUrl");
                    String addressDepartRelContent = getAddressDepartRelContent();
                    Map<String,String> addressDepartRelParamMap = new HashMap<String,String>();
                    addressDepartRelParamMap.put("token", ciphertext);
                    addressDepartRelParamMap.put("mineCode", mineCode);
                    addressDepartRelParamMap.put("reportContent", addressDepartRelContent);
                    UploadThread addressDepartRelThread = new UploadThread(addressDepartRelUrl,"UTF-8",addressDepartRelParamMap);
                    addressDepartRelThread.start();
                    // 上传风险点关联风险
                    String addressDangerRelUrl = ResourceUtil.getConfigByName("addressDangerRelUrl");
                    String addressDangerRelContent = getAddressDangerRelContent(ids);
                    Map<String,String> addressDangerRelParamMap = new HashMap<String,String>();
                    addressDangerRelParamMap.put("token", ciphertext);
                    addressDangerRelParamMap.put("mineCode", mineCode);
                    addressDangerRelParamMap.put("reportContent", addressDangerRelContent);
                    UploadThread addressDangerRelThread = new UploadThread(addressDangerRelUrl,"UTF-8",addressDangerRelParamMap);
                    addressDangerRelThread.start();
                    ////////////////////////////
                    System.out.println(reportContents.toString());
                    paramMap.put("reportContent", reportContents.toString());
                    json = HttpClientUtils.post(url, paramMap, "UTF-8");

                    try {
                        /**   这个是阿里巴巴包下的string转换成JSON的方法   */
                        JSONObject jsonObject = JSONObject.parseObject(json);

                        message = jsonObject.getString("message");
                        code = jsonObject.getString("code");

                        if("200".equals(code)){
                            for (String id: ids.split(",")) {
                                TBSpecialEvaluationEntity t = tBSpecialEvaluationService.get(TBSpecialEvaluationEntity.class, id);
                                if (t!=null && (Constants.SE_REPORT_STATUS_DEPART_REPORT.equals(t.getReportStatus()) || Constants.SE_REPORT_STATUS_PENDING_REPORT.equals(t.getReportStatus()))) {
                                    if(!isFromTask){
                                        t.setReportStatus(Constants.SE_REPORT_STATUS_MINE_REPORT);
                                        tBSpecialEvaluationService.saveOrUpdate(t);
                                        //更新相关危险源为矿上报到煤监局
                                        String sql = "update t_b_danger_source set report_status='" + Constants.DANGER_SOURCE_REPORT_REPORT + "' where id in " +
                                                "(select danger_source_id from t_b_se_ds_relation where sepcial_evaluation_id = '" + id + "')";
                                        systemService.updateBySqlString(sql);
                                    }

                                    systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
                                }
                            }
                        }

                    } catch (Exception e){
                        message = "专项风险辨识上报失败！";
                    }
                } catch (NetServiceException e) {
                    message = "专项风险辨识上报失败！";
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "专项风险辨识更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;

    }

    /**
     * 上传
     * @param idSb
     * @return
     */
    private String getAddressDangerRelContent(String idSb) {
        List<Object> sendList = new ArrayList<Object>();
        String[] idArray = idSb.split(",");
        CriteriaQuery cq = new CriteriaQuery(TBDangerAddresstRelEntity.class);
        try{
            cq.in("dangerId",idArray);
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        List<TBDangerAddresstRelEntity> list = systemService.getListByCriteriaQuery(cq,false);
        if(list != null && !list.isEmpty()){
            for(TBDangerAddresstRelEntity entity : list){
                Map<String,String> sendMap = new HashMap<>();

                sendMap.put("id",entity.getId());
                sendMap.put("darDangerId",entity.getDangerId());
                sendMap.put("darAddressId",entity.getAddressId());
                sendMap.put("darRiskLevel",entity.getRiskLevel());
                sendList.add(sendMap);
            }
        }
        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 查询所有风险点关联的组织机构
     * @return
     */
    private String getAddressDepartRelContent() {
        List<TBAddressDepartRelEntity> hazardList = systemService.getList(TBAddressDepartRelEntity.class);
        List<Object> sendList = new ArrayList<Object>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < hazardList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(hazardList.get(i).getId());
            TBAddressDepartRelEntity entity = hazardList.get(i);
            Map<String,String> sendMap = new HashMap<>();
            String departId = entity.getDepartId();
            if(StringUtils.isNotBlank(departId)){
                TSDepart depart = systemService.getEntity(TSDepart.class,departId);
                if(depart != null){
                    sendMap.put("adrDepartName",depart.getDepartname());
                }else{
                    sendMap.put("adrDepartName","");
                }
            }
            sendMap.put("id",entity.getId());
            sendMap.put("adrAddressId",entity.getAddressId());
            sendMap.put("adrDutyMan",entity.getDutyMan());
            sendList.add(sendMap);
        }

        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 查询所有一类危险源
     * @return
     */
    private String getHazardReportContent() {
        List<TbHazardManageEntity> hazardList = systemService.getList(TbHazardManageEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < hazardList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(hazardList.get(i).getId());
            TbHazardManageEntity entity = hazardList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("hmHazardName",entity.getHazardName());
            sendMap.put("hmHazardType",entity.getHazardType());
            sendMap.put("hmDamageType",entity.getDamageType());
            sendMap.put("hmProfessionType",entity.getProfessionType());
            sendMap.put("hmAccidentType",entity.getAccidentType());
            sendMap.put("isDelete",entity.getIsDelete());
            sendList.add(sendMap);
        }

        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 查询所有风险点
     * @return
     */
    private String getAddressReportContent() {
        List<TBAddressInfoEntity> addressList = systemService.getList(TBAddressInfoEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < addressList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(addressList.get(i).getId());
            TBAddressInfoEntity entity = addressList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("aiAddressName",entity.getAddress());
            sendMap.put("aiManageMan",entity.getManageMan());
            sendMap.put("aiManageMan",entity.getManageMan());
            sendMap.put("isDelete",entity.getIsDelete());
            sendList.add(sendMap);
        }

        return JSONHelper.toJSONString(sendList);
    }

    @Transactional
    public AjaxJson specialEvaluateReportToGroup(List<TBSpecialEvaluationEntity> list){

        String message = null;
        String code = null;
        AjaxJson j = new AjaxJson();
        Map paramMap = new HashMap<>();
        String json = null;
        StringBuffer ids = new StringBuffer();

        message = "专项风险辨识上报成功";
        if (list == null || list.size() == 0) {
            j.setMsg("请选择要上报的专项辨识风险");
            j.setSuccess(false);
            return j;
        }

        try {
            String url = ResourceUtil.getConfigByName("reportGroupRootUrl")+ResourceUtil.getConfigByName("specialEvaluateReportToGroup");
            String token = ResourceUtil.getConfigByName("token_group");
            String mineCode = ResourceUtil.getConfigByName("mine_code");
            /**
             * 加密token及mineCode
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
            /**
             * 初始化上报内容
             */
            for (TBSpecialEvaluationEntity tbSpecialEvaluationEntity : list) {
                JSONObject reportContent = new JSONObject();
                if (StringUtil.isNotEmpty(ids.toString())) {
                    ids.append(",");
                }
                ids.append(tbSpecialEvaluationEntity.getId());
                reportContent.put("id", tbSpecialEvaluationEntity.getId());
                reportContent.put("name", tbSpecialEvaluationEntity.getName());
                //获取辨识人名称
                TSBaseUser leader = systemService.get(TSBaseUser.class, tbSpecialEvaluationEntity.getLeader() );
                if (leader != null){
                    tbSpecialEvaluationEntity.setLeaderTemp(leader.getRealName());
                }
                reportContent.put("leader", tbSpecialEvaluationEntity.getLeaderTemp());
                //获取辨识人类型
                String leaderType = null;
                if (StringUtil.isNotEmpty(tbSpecialEvaluationEntity.getLeaderType())) {
                    leaderType = DicUtil.getTypeNameByCode("evaluationLeaderType", tbSpecialEvaluationEntity.getLeaderType());
                }
                reportContent.put("leaderType", leaderType);
                reportContent.put("evalTime", dateToString(tbSpecialEvaluationEntity.getTime()));
                //参与人从id转换成人名——开始
                StringBuffer participants = new StringBuffer();
                if (StringUtil.isNotEmpty(tbSpecialEvaluationEntity.getParticipant())) {
                    for(String participant:tbSpecialEvaluationEntity.getParticipant().split(",")){
                        TSBaseUser tsBaseUser = systemService.getEntity(TSBaseUser.class, participant);
                        if (tsBaseUser!=null) {
                            if (StringUtil.isNotEmpty(participants.toString())) {
                                participants.append(",");
                            }
                            participants.append(tsBaseUser.getRealName());
                        }
                    }
                }
                reportContent.put("participants",participants.toString());
                //参与人从id转换成人名——结束
                reportContent.put("location", tbSpecialEvaluationEntity.getLocation());
                reportContent.put("remark", tbSpecialEvaluationEntity.getRemark());
                reportContent.put("evalType", tbSpecialEvaluationEntity.getType());

                CriteriaQuery relationCq = new CriteriaQuery(TBSeDsRelationEntity.class);
                relationCq.eq("sepcialEvaluationId", tbSpecialEvaluationEntity.getId());
                relationCq.add();
                List<TBSeDsRelationEntity> relations = systemService.getListByCriteriaQuery(relationCq, false);

                JSONArray riskdatas = new JSONArray();

                if (relations != null && relations.size() > 0){
                    for(TBSeDsRelationEntity tbSeDsRelationEntity : relations){
                        //获取对应的danger_source_id
                        TBDangerSourceEntity tbDangerSourceEntity = systemService.getEntity(TBDangerSourceEntity.class, tbSeDsRelationEntity.getDangerSourceId());

                        JSONObject riskdata = new JSONObject();
                        if(tbDangerSourceEntity == null){
                            continue;
                        }
                        riskdata.put("id", tbDangerSourceEntity.getId());
                        riskdata.put("yeMhazardDesc", tbDangerSourceEntity.getYeMhazardDesc());
                        riskdata.put("yeProfession", tbDangerSourceEntity.getYeProfession());
                        riskdata.put("yeAccident", tbDangerSourceEntity.getYeAccident());
                        riskdata.put("yeStandard", tbDangerSourceEntity.getYeStandard());
                        riskdata.put("yePossiblyHazard", tbDangerSourceEntity.getYePossiblyHazard());
                        riskdata.put("yeProbability", tbDangerSourceEntity.getYeProbability());
                        riskdata.put("yeCost", tbDangerSourceEntity.getYeCost());
                        riskdata.put("yeHazardCate", tbDangerSourceEntity.getYeHazardCate());
                        riskdata.put("yeRiskGrade", tbDangerSourceEntity.getYeRiskGrade());
                        riskdata.put("yeCost", tbDangerSourceEntity.getYeCost());
                        riskdata.put("riskValue",tbDangerSourceEntity.getRiskValue());
                        //riskdata.put("yeCaseNum", tbDangerSourceEntity.getYeCaseNum());
                        riskdata.put("yeRecognizeTime", dateToString(tbDangerSourceEntity.getYeRecognizeTime()));
                        riskdata.put("isMajor", tbDangerSourceEntity.getIsMajor());
                        riskdata.put("origin", tbDangerSourceEntity.getOrigin());
                        riskdata.put("lecRiskPossibility", tbDangerSourceEntity.getLecRiskPossibility()==null?"":String.valueOf(tbDangerSourceEntity.getLecRiskPossibility()));
                        riskdata.put("lecRiskLoss", tbDangerSourceEntity.getLecRiskLoss()==null?"":String.valueOf(tbDangerSourceEntity.getLecRiskLoss()));
                        riskdata.put("lecExposure", tbDangerSourceEntity.getLecExposure()==null?"":String.valueOf(tbDangerSourceEntity.getLecExposure()));
                        riskdata.put("lecRiskValue", tbDangerSourceEntity.getLecRiskValue()==null?"":String.valueOf(tbDangerSourceEntity.getLecRiskValue()));
                        riskdata.put("manageMeasure", tbDangerSourceEntity.getManageMeasure());
                        riskdata.put("docSource", tbDangerSourceEntity.getDocSource());
                        riskdata.put("sectionName", tbDangerSourceEntity.getSectionName());
                        riskdata.put("dutyPost",tbDangerSourceEntity.getPost()==null ?"":tbDangerSourceEntity.getPost().getId());
                        riskdata.put("activity",tbDangerSourceEntity.getActivity() == null ? "":tbDangerSourceEntity.getActivity().getId());
                        riskdata.put("hazardManageId",tbDangerSourceEntity.getHazard()==null?"":tbDangerSourceEntity.getHazard().getId());
                        String damageType = tbDangerSourceEntity.getDamageType();
                        String damageTypeTemp = StaticDataMap.damageTypeMap.get(damageType);
                        damageTypeTemp = damageTypeTemp == null?StaticDataMap.DAMAGETYPE_OTHER:damageType;
                        riskdata.put("damageType",damageTypeTemp);
                        riskdata.put("hiddenLevel",tbDangerSourceEntity.getHiddenLevel());
                        riskdata.put("fineMoney",tbDangerSourceEntity.getFineMoney());

                        riskdatas.add(riskdata);
                    }
                }
                reportContent.put("riskdatas", riskdatas);
                reportContents.add(reportContent);

                // 上传风险点
                String addressReportUrl = ResourceUtil.getConfigByName("addressInfoReportUrl");
                String addressReportContent = getAddressReportGroupContent();
                Map<String,String> addressParamMap = new HashMap<String,String>();
                addressParamMap.put("token", ciphertext);
                addressParamMap.put("mineCode", mineCode);
                addressParamMap.put("reportContent", addressReportContent);
                UploadThread addressThread = new UploadThread(addressReportUrl,"UTF-8",addressParamMap);
                addressThread.start();
                // 上传一类危险源
                String hazardReportUrl = ResourceUtil.getConfigByName("hazardInfoReportUrl");
                String hazardReportContent = getHazardReportGroupContent();
                Map<String,String> hazardParamMap = new HashMap<String,String>();
                hazardParamMap.put("token", ciphertext);
                hazardParamMap.put("mineCode", mineCode);
                hazardParamMap.put("reportContent", hazardReportContent);
                UploadThread hazardThread = new UploadThread(hazardReportUrl,"UTF-8",hazardParamMap);
                hazardThread.start();
                // 上传风险点关联风险
                String addressDangerRelUrl = ResourceUtil.getConfigByName("addressDangerRelGroupUrl");
                String addressDangerRelContent = getAddressDangerRelGroupContent(ids);
                Map<String,String> addressDangerRelParamMap = new HashMap<String,String>();
                addressDangerRelParamMap.put("token", ciphertext);
                addressDangerRelParamMap.put("mineCode", mineCode);
                addressDangerRelParamMap.put("reportContent", addressDangerRelContent);
                UploadThread addressDangerRelThread = new UploadThread(addressDangerRelUrl,"UTF-8",addressDangerRelParamMap);
                addressDangerRelThread.start();
                // 上传作业活动
                String activityReportUrl = ResourceUtil.getConfigByName("activityInfoReportUrl");
                String activityReportContent = getActivityReportGroupContent();
                Map<String,String> activityParamMap = new HashMap<String,String>();
                activityParamMap.put("token", ciphertext);
                activityParamMap.put("mineCode", mineCode);
                activityParamMap.put("reportContent", activityReportContent);
                UploadThread activityThread = new UploadThread(activityReportUrl,"UTF-8",activityParamMap);
                activityThread.start();
                // 上传岗位
                String postReportUrl = ResourceUtil.getConfigByName("postInfoReportUrl");
                String postReportContent = getPostReportGroupContent();
                Map<String,String> postParamMap = new HashMap<String,String>();
                postParamMap.put("token", ciphertext);
                postParamMap.put("mineCode", mineCode);
                postParamMap.put("reportContent", postReportContent);
                UploadThread postThread = new UploadThread(postReportUrl,"UTF-8",postParamMap);
                postThread.start();


                paramMap.put("reportContent", reportContents.toString());
                json = HttpClientUtils.post(url, paramMap, "UTF-8");

                JSONObject jsonObject = JSONObject.parseObject(json);

                message = jsonObject.getString("message");
                code = jsonObject.getString("code");
                //TODO 更新本地上报状态
                if("200".equals(code)){
                    try {
                        String curUser;
                        try {
                            //定时任务无法获取session
                            curUser = ResourceUtil.getSessionUserName().getRealName();
                        } catch (Exception e) {
                            curUser = "定时任务";
                        }
                        String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                        if (StringUtil.isNotEmpty(ids.toString())) {
                            systemService.executeSql("update t_b_special_evaluation set report_group_man = '" + curUser + "' , report_group_time = '" + reportDate + "' , report_group_status = '1' where id in ('" + ids.toString().replace(",", "','") +"')");
                        }
                    } catch (Exception e) {
                        j.setMsg("上报成功，但本地数据库操作失败");
                        return j;
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.error("专项辨识上报到集团错误", e);
        }
        j.setMsg(message);
        return j;

    }

    /**
     * 组装危险源上报集团报文
     * @return
     */
    private String getHazardReportGroupContent() {
        List<TbHazardManageEntity> hazardList = systemService.getList(TbHazardManageEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < hazardList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(hazardList.get(i).getId());
            TbHazardManageEntity entity = hazardList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("hazardName",entity.getHazardName());
            sendMap.put("hazardType",entity.getHazardType());
            sendMap.put("damageType",entity.getDamageType());
            sendMap.put("professionType",entity.getProfessionType());
            sendMap.put("accidentType",entity.getAccidentType());
            sendMap.put("isDelete",entity.getIsDelete());
            sendList.add(sendMap);
        }

        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 组装风险点关联风险报文
     * @return
     */
    private String getAddressDangerRelGroupContent(StringBuffer idSb) {
        List<Object> sendList = new ArrayList<Object>();
        String[] idArray = idSb.toString().split(",");
        CriteriaQuery cq = new CriteriaQuery(TBDangerAddresstRelEntity.class);
        try{
            cq.in("dangerId",idArray);
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        List<TBDangerAddresstRelEntity> list = systemService.getListByCriteriaQuery(cq,false);
        if(list != null && !list.isEmpty()){
            for(TBDangerAddresstRelEntity entity : list){
                Map<String,String> sendMap = new HashMap<>();

                sendMap.put("id",entity.getId());
                sendMap.put("dangerId",entity.getDangerId());
                sendMap.put("addressId",entity.getAddressId());
                sendMap.put("riskLevel",entity.getRiskLevel());
                sendList.add(sendMap);
            }
        }
        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 组装风险点上报集团报文
     * @return
     */
    private String getAddressReportGroupContent() {
        List<TBAddressInfoEntity> addressList = systemService.getList(TBAddressInfoEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < addressList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(addressList.get(i).getId());
            TBAddressInfoEntity entity = addressList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("address",entity.getAddress());
            sendMap.put("lon",entity.getLon());
            sendMap.put("lat",entity.getLat());
            sendMap.put("isshow",entity.getIsshow());
            sendMap.put("manageMan",entity.getManageMan());
            sendMap.put("isDelete",entity.getIsDelete());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sendMap.put("createDate",entity.getCreateDate()==null?"":sdf.format(entity.getCreateDate()));
            sendList.add(sendMap);
        }

        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 组装作业活动上报集团报文
     * @return
     */
    private String getActivityReportGroupContent() {
        List<TbActivityManageEntity> activityList = systemService.getList(TbActivityManageEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < activityList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(activityList.get(i).getId());
            TbActivityManageEntity entity = activityList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("professionType",entity.getProfessionType());
            sendMap.put("activityName", entity.getActivityName());
            sendMap.put("isDelete",entity.getIsDelete());
            sendList.add(sendMap);
        }
        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 组装岗位上报集团报文
     * @return
     */
    private String getPostReportGroupContent() {
        List<TBPostManageEntity> postList = systemService.getList(TBPostManageEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < postList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(postList.get(i).getId());
            TBPostManageEntity entity = postList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("professionType",entity.getProfessionType());
            sendMap.put("postName",entity.getPostName());
            sendMap.put("isDelete",entity.getIsDelete());
            sendList.add(sendMap);
        }
        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 更新专项风险辨识和关联的危险源状态
     * @param id
     */
    public void updateReportStatus(String id) {
        String sql = "update t_b_special_evaluation set report_status='" + Constants.DANGER_SOURCE_REPORT_REPORT + "' where id = " +
                "'" + id + "'";
        systemService.updateBySqlString(sql);
        String updateDangerSourceSql = "update t_b_danger_source set report_status='" + Constants.DANGER_SOURCE_REPORT_REPORT + "' where id in " +
                "(select danger_source_id from t_b_se_ds_relation where sepcial_evaluation_id = '" + id + "')";
        systemService.updateBySqlString(updateDangerSourceSql);
    }
}
