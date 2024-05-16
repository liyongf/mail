package com.sdzk.sys.synctocloud.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.dangersource.entity.TbActivityManageEntity;
import com.sdzk.buss.web.dangersource.entity.TbHazardManageEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerApplyEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import com.sdzk.sys.synctocloud.service.SyncToCloudService;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("syncToCloudService")
@Transactional
public class SyncToCloudServiceImpl extends CommonServiceImpl implements SyncToCloudService {
    private static final String rootUrl = ResourceUtil.getConfigByName("dataCloudUrl");
    private static final String enterpriseId = ResourceUtil.getConfigByName("enterpriseId");
    private static final String token = ResourceUtil.getConfigByName("token_cloud");

    /**
     * 风险点同步
     *
     * @param addressInfo
     */
    @Override
    public void addressInfoReport(TBAddressInfoEntity addressInfo) {
        Map<String, String> data = initializeReportData();

        JSONArray ja = new JSONArray();
        JSONObject content = new JSONObject();
        content.put("oldId", addressInfo.getId());
        content.put("address", addressInfo.getAddress());
        content.put("longitude", addressInfo.getLon());
        content.put("latitude", addressInfo.getLat());
        content.put("description", addressInfo.getDescription());
        ja.add(content);

        data.put("content", JSONHelper.toJSONString(ja));

        String url = rootUrl + ResourceUtil.getConfigByName("syncAddressToCloudUrl");
        try {
            postData(data, url);
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * 风险同步
     *
     * @param dangerSource
     */
    @Override
    public void dangerSourceReport(TBDangerSourceEntity dangerSource) {
        Map<String, String> data = initializeReportData();

        JSONArray ja = new JSONArray();

        ja.add(dangerSourceToJSON(dangerSource));

        data.put("content", JSONHelper.toJSONString(ja));

        String url = rootUrl + ResourceUtil.getConfigByName("syncDangerSourceToCloudUrl");
        try {
            String result = postData(data, url);

            if(StringUtil.isNotEmpty(result)) {
                net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                    String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(dangerSource.getId())) {
                        updateBySqlString("update t_b_danger_source set cloud_report_date = '" + reportDate + "' , cloud_report_status = '1' where id = '" + dangerSource.getId() + "'");
                    }
                }
            }
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * 风险同步（批量）
     *
     * @param ids
     */
    @Override
    public void dangerSourceBatchReport(String ids) {
        Map<String, String> data = initializeReportData();

        JSONArray ja = new JSONArray();
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            TBDangerSourceEntity dangerSource = commonDao.getEntity(TBDangerSourceEntity.class, idArray[i]);
            if (dangerSource != null) {
                ja.add(dangerSourceToJSON(dangerSource));
            }
        }

        data.put("content", JSONHelper.toJSONString(ja));

        String url = rootUrl + ResourceUtil.getConfigByName("syncDangerSourceToCloudUrl");
        try {
            String result = postData(data, url);
            if (StringUtils.isNotBlank(result)) {
                net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                    String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                    updateBySqlString("update t_b_danger_source set cloud_report_date = '" + reportDate + "' , cloud_report_status = '1' where id in ('" + ids.replace(",", "','") + "')");
                }
            }
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addressRiskRelReport(String addressId) {
        String sql = "SELECT rel.id AS oldId, rel.danger_id AS dangerId, rel.address_id AS addressId, rel.risk_level AS riskLevel FROM t_b_danger_address_rel rel WHERE rel.address_id = '" + addressId + "'";
        List<Map<String, Object>> mapList = commonDao.findForJdbc(sql);
        if (!mapList.isEmpty() && mapList.size() > 0) {
            Map<String, String> data = initializeReportData();
            data.put("content", JSONHelper.toJSONString(mapList));

            String url = rootUrl + ResourceUtil.getConfigByName("syncAddressRiskRelToCloudUrl");
            try {
                postData(data, url);
            } catch (NetServiceException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void hiddenDangerReport(TBHiddenDangerExamEntity hiddenDanger) {
        Map<String, String> data = initializeReportData();

        JSONArray ja = new JSONArray();

        ja.add(hiddenDangerToJSON(hiddenDanger));

        data.put("content", JSONHelper.toJSONString(ja));

        String url = rootUrl + ResourceUtil.getConfigByName("syncHiddenDangerToCloudUrl");
        try {
            String result = postData(data, url);
            if (StringUtils.isNotBlank(result)) {
                net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                    String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(hiddenDanger.getId())) {
                        updateBySqlString("update t_b_hidden_danger_exam set cloud_report_date = '" + reportDate + "' , cloud_report_status = '1' where id = '" + hiddenDanger.getId() + "'");
                    }
                }
            }
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hiddenDangerBatchReport(String ids) {
        Map<String, String> data = initializeReportData();

        JSONArray ja = new JSONArray();
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            TBHiddenDangerExamEntity hiddenDanger = commonDao.getEntity(TBHiddenDangerExamEntity.class, idArray[i]);
            if (hiddenDanger != null) {
                ja.add(hiddenDangerToJSON(hiddenDanger));
            }
        }

        data.put("content", JSONHelper.toJSONString(ja));

        String url = rootUrl + ResourceUtil.getConfigByName("syncHiddenDangerToCloudUrl");
        try {
            String result = postData(data, url);
            if (StringUtils.isNotBlank(result)) {
                net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                    String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                    updateBySqlString("update t_b_hidden_danger_exam set cloud_report_date = '" + reportDate + "' , cloud_report_status = '1' where id in ('" + ids.replace(",", "','") + "')");

                }
            }
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void violationReport(TBThreeViolationsEntity violations) {
        Map<String, String> data = initializeReportData();

        JSONArray ja = new JSONArray();

        ja.add(threeViolationToJSON(violations));

        data.put("content", JSONHelper.toJSONString(ja));

        String url = rootUrl + ResourceUtil.getConfigByName("syncViolationsToCloudUrl");
        try {
            String result = postData(data, url);
            if (StringUtils.isNotBlank(result)) {
                net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                    String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(violations.getId())) {
                        updateBySqlString("update t_b_three_violations set cloud_report_date = '" + reportDate + "' , cloud_report_status = '1' where id = '" + violations.getId() + "'");
                    }
                }
            }
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void violationBatchReport(String ids) {
        Map<String, String> data = initializeReportData();

        JSONArray ja = new JSONArray();
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            TBThreeViolationsEntity violationsEntity = commonDao.getEntity(TBThreeViolationsEntity.class, idArray[i]);
            if (violationsEntity != null) {
                ja.add(threeViolationToJSON(violationsEntity));
            }
        }
        data.put("content", JSONHelper.toJSONString(ja));

        String url = rootUrl + ResourceUtil.getConfigByName("syncViolationsToCloudUrl");
        try {
            String result = postData(data, url);
            if (StringUtils.isNotBlank(result)) {
                net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                    String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(ids)) {
                        updateBySqlString("update t_b_three_violations set cloud_report_date = '" + reportDate + "' , cloud_report_status = '1' where id in ('" + ids.replace(",", "','") + "')");
                    }
                }
            }
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化报文
     *
     * @return
     */
    private Map<String, String> initializeReportData() {
        Map<String, String> data = new HashMap<>();

        data.put("enterpriseId", enterpriseId);

        String tempToken = "token=" + token + "&enterpriseId=" + enterpriseId;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put("token", ciphertext);

        return data;
    }

    /**
     * Post方式提交
     *
     * @param data
     * @param url
     * @return
     */
    private String postData(Map<String, String> data, String url) throws NetServiceException {
        return HttpClientUtils.post(url, data, "UTF-8");
    }

    private JSONObject dangerSourceToJSON(TBDangerSourceEntity dangerSource) {
        JSONObject jo = new JSONObject();
        jo.put("oldId", dangerSource.getId());
        jo.put("identifyTime", DateUtils.date2Str(dangerSource.getYeRecognizeTime(), DateUtils.date_sdf));
        jo.put("profession", dangerSource.getYeProfession());
        jo.put("injuryCategory", dangerSource.getDamageType());
        jo.put("accidentType", dangerSource.getYeAccident());
        jo.put("description", dangerSource.getYePossiblyHazard());
        jo.put("possibility", dangerSource.getYeProbability());
        jo.put("loss", dangerSource.getYeCost());
        jo.put("lecPossibility", dangerSource.getLecRiskPossibility());
        jo.put("lecLoss", dangerSource.getLecRiskLoss());
        jo.put("lecExposure", dangerSource.getLecExposure());
        jo.put("lecRiskValue", dangerSource.getLecRiskValue());
        jo.put("riskValue", dangerSource.getRiskValue());
        jo.put("riskLevel", dangerSource.getYeRiskGrade());
        jo.put("riskType", dangerSource.getYeHazardCate());
        jo.put("controlStandardFrom", dangerSource.getDocSource());
        jo.put("chapterAndClause", dangerSource.getSectionName());
        jo.put("standardContent", dangerSource.getYeStandard());
        jo.put("controlMeasure", dangerSource.getManageMeasure());
        jo.put("hiddenDangerDescribe", dangerSource.getYeMhazardDesc());
        jo.put("referencePrice", dangerSource.getFineMoney());

        if (dangerSource.getHazard() != null && StringUtils.isNotBlank(dangerSource.getHazard().getId())) {
            TbHazardManageEntity hazardManageEntity = commonDao.getEntity(TbHazardManageEntity.class, dangerSource.getHazard().getId());
            jo.put("dangerName", hazardManageEntity.getHazardName());
        }
        if (dangerSource.getActivity() != null && StringUtils.isNotBlank(dangerSource.getActivity().getId())) {
            TbActivityManageEntity activityManageEntity = commonDao.getEntity(TbActivityManageEntity.class, dangerSource.getActivity().getId());
            jo.put("jobActivity", activityManageEntity.getActivityName());
        }
        if (dangerSource.getPost() != null && StringUtils.isNotBlank(dangerSource.getPost().getId())) {
            TBPostManageEntity postManageEntity = commonDao.getEntity(TBPostManageEntity.class, dangerSource.getPost().getId());
            jo.put("responsiblePost", postManageEntity.getPostName());
        }
        return jo;
    }

    private JSONObject hiddenDangerToJSON(TBHiddenDangerExamEntity hiddenDanger) {
        TBHiddenDangerHandleEntity handleEntity = commonDao.getEntity(TBHiddenDangerHandleEntity.class, hiddenDanger.getHandleEntity().getId());

        JSONObject jo = new JSONObject();
        jo.put("oldId", hiddenDanger.getId());
        jo.put("examDate", DateUtils.date2Str(hiddenDanger.getExamDate(), DateUtils.date_sdf));
        jo.put("examType", hiddenDanger.getExamType());
        jo.put("dealType", hiddenDanger.getDealType());
        jo.put("hiddenType", hiddenDanger.getHiddenType());
        jo.put("shift", hiddenDanger.getShift());
        jo.put("dutyManName", hiddenDanger.getDutyMan());
        jo.put("problemDesc", hiddenDanger.getProblemDesc());
        jo.put("itemId", hiddenDanger.getItemId());
        jo.put("itemName", hiddenDanger.getItemId());
        jo.put("hiddenCategory", hiddenDanger.getHiddenCategory());
        jo.put("hiddenNature", hiddenDanger.getHiddenNature());
        jo.put("beginWellTime", DateUtils.date2Str(hiddenDanger.getBeginWellDate(), DateUtils.datetimeFormat));
        jo.put("endWellTime", DateUtils.date2Str(hiddenDanger.getEndWellDate(), DateUtils.datetimeFormat));

        if (hiddenDanger.getAddress() != null && StringUtils.isNotBlank(hiddenDanger.getAddress().getId())) {
            TBAddressInfoEntity addressInfoEntity = commonDao.getEntity(TBAddressInfoEntity.class, hiddenDanger.getAddress().getId());
            jo.put("addressId", addressInfoEntity.getId());
            jo.put("addressName", addressInfoEntity.getAddress());
        }

        if (hiddenDanger.getDutyUnit() != null && StringUtils.isNotBlank(hiddenDanger.getDutyUnit().getId())) {
            TSDepart depart = commonDao.getEntity(TSDepart.class, hiddenDanger.getDutyUnit().getId());
            jo.put("dutyUnitId", depart.getId());
            jo.put("dutyUnitName", depart.getDepartname());
        }

        if (hiddenDanger.getDangerId() != null && StringUtils.isNotBlank(hiddenDanger.getDangerId().getId())) {
            TBDangerSourceEntity dangerSourceEntity = commonDao.getEntity(TBDangerSourceEntity.class, hiddenDanger.getDangerId().getId());
            jo.put("dangerSourceId", dangerSourceEntity.getId());
            jo.put("dangerSourceDesc", dangerSourceEntity.getYeMhazardDesc());
        }

        if (StringUtils.isNotBlank(hiddenDanger.getItemUserId())) {
            String itemUserId = hiddenDanger.getItemUserId();
            jo.put("itemUserId", itemUserId);
            String itemSql = "SELECT GROUP_CONCAT(DISTINCT realname) FROM t_s_base_user WHERE FIND_IN_SET(id,'" + itemUserId + "')";
            List<String> tempList = commonDao.findListbySql(itemSql);
            if (!tempList.isEmpty() && tempList.size() > 0) {
                String itemUserName = tempList.get(0);
                jo.put("itemUserName", itemUserName);
            }
        }

        if (hiddenDanger.getReviewMan() != null && StringUtils.isNotBlank(hiddenDanger.getReviewMan().getId())) {
            TSUser user = commonDao.getEntity(TSUser.class, hiddenDanger.getReviewMan().getId());
            jo.put("appointReviewManId", user.getId());
            jo.put("appointReviewManName", user.getRealName());
        }

        if (hiddenDanger.getApplyEntity() != null && StringUtils.isNotBlank(hiddenDanger.getApplyEntity().getId())) {
            TBHiddenDangerApplyEntity applyEntity = commonDao.getEntity(TBHiddenDangerApplyEntity.class, hiddenDanger.getApplyEntity().getId());
            jo.put("extensionRequest", applyEntity.getApplyReason());
        }

        if (StringUtils.isNotBlank(handleEntity.getReviewMan())) {
            TSUser user = commonDao.getEntity(TSUser.class, handleEntity.getReviewMan());
            jo.put("reviewManId", user.getId());
            jo.put("reviewManName", user.getRealName());
        }

        if (StringUtils.isNotBlank(handleEntity.getModifyMan())) {
            TSUser user = commonDao.getEntity(TSUser.class, handleEntity.getModifyMan());
            if(StringUtil.isNotEmpty(user)){
                jo.put("modifyManId", user.getId());
                jo.put("modifyManName", user.getRealName());
            }else{
                jo.put("modifyManName", handleEntity.getModifyMan());
            }
        }

        jo.put("limitShift", hiddenDanger.getLimitShift());
        jo.put("limitDate", DateUtils.date2Str(hiddenDanger.getLimitDate(), DateUtils.date_sdf));
        jo.put("superiorDepart", hiddenDanger.getSjjcDept());
        jo.put("superiorCheckMan", hiddenDanger.getSjjcCheckMan());
        jo.put("professionalType", hiddenDanger.getProType());
        jo.put("checkType", hiddenDanger.getCheckType());
        jo.put("remark", hiddenDanger.getRemark());
        jo.put("modifyDate", DateUtils.date2Str(handleEntity.getModifyDate(), DateUtils.date_sdf));
        jo.put("modifyShift", handleEntity.getModifyShift());
        jo.put("modifyMeasures", handleEntity.getRectMeasures());
        jo.put("modifyRejectReason", handleEntity.getRollBackRemark());
        jo.put("reviewDate", DateUtils.date2Str(handleEntity.getReviewDate(), DateUtils.date_sdf));
        jo.put("reviewShift", handleEntity.getReviewShift());
        jo.put("reviewResult", handleEntity.getReviewResult());
        jo.put("handleStatus", handleEntity.getHandlelStatus());

        return jo;
    }

    private JSONObject threeViolationToJSON(TBThreeViolationsEntity violations) {
        JSONObject jo = new JSONObject();
        jo.put("oldId", violations.getId());
        jo.put("vioDate", DateUtils.date2Str(violations.getVioDate(), DateUtils.date_sdf));
        jo.put("shift", violations.getShift());
        jo.put("address", violations.getVioAddress());
        jo.put("classification", violations.getVioCategory());
        jo.put("qualitative", violations.getVioQualitative());
        jo.put("vioLevel", violations.getVioLevel());
        jo.put("stopPeople", violations.getStopPeople());
        jo.put("description", violations.getVioFactDesc());
        jo.put("remark", violations.getRemark());
        jo.put("people", violations.getVioPeople());
        jo.put("oldId", violations.getId());

        if (StringUtils.isNotBlank(violations.getVioUnits())) {
            TSDepart depart = commonDao.getEntity(TSDepart.class, violations.getVioUnits());
            if (depart != null) {
                jo.put("unit", depart.getDepartname());
            }
        }

        if (StringUtils.isNotBlank(violations.getFindUnits())) {
            TSDepart depart = commonDao.getEntity(TSDepart.class, violations.getFindUnits());
            if (depart != null) {
                jo.put("stopUnit", depart.getDepartname());
            }
        }

        return jo;
    }
}
