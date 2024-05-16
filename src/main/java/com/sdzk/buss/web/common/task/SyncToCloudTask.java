package com.sdzk.buss.web.common.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.AesUtil;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 除操作日志上报外，均已改为切面，旧的方法不再使用
 */
@Service("syncToCloudTask")
public class SyncToCloudTask {
    @Autowired
    private SystemService systemService;

    private static final String rootUrl = ResourceUtil.getConfigByName("dataCloudUrl");
    private static final String enterpriseId = ResourceUtil.getConfigByName("enterpriseId");
    private static final String token = ResourceUtil.getConfigByName("token_cloud");

    public void dicTypeReport() {
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

        JSONArray reportContents = new JSONArray();

        String sql = "SELECT t.typecode, t.typename, g.typegroupcode, g.typegroupname FROM t_s_type t LEFT JOIN t_s_typegroup g ON t.typegroupid = g.ID ORDER BY g.typegroupcode";
        List<Map<String, Object>> dicTypeList = systemService.findForJdbc(sql);

        if (!dicTypeList.isEmpty() && dicTypeList.size() > 0) {
            for (Map<String, Object> map : dicTypeList) {
                JSONObject content = new JSONObject();
                content.put("typecode", map.get("typecode"));
                content.put("typename", map.get("typename"));
                content.put("typegroupcode", map.get("typegroupcode"));
                content.put("typegroupname", map.get("typegroupname"));

                reportContents.add(content);
            }
        }

        data.put("content", reportContents.toString());

        String url = rootUrl + ResourceUtil.getConfigByName("syncDicTypeToCloudUrl");
        try {
            HttpClientUtils.post(url, data, "UTF-8");
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    public void addressInfoReport() {
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

        JSONArray reportContents = new JSONArray();

        String sql = "SELECT t.id,t.address,t.lon,t.lat FROM t_b_address_info t WHERE t.is_delete=0";
        List<Map<String, Object>> dicTypeList = systemService.findForJdbc(sql);

        if (!dicTypeList.isEmpty() && dicTypeList.size() > 0) {
            for (Map<String, Object> map : dicTypeList) {
                JSONObject content = new JSONObject();
                content.put("oldId", map.get("id"));
                content.put("address", map.get("address"));
                content.put("longitude", map.get("lon"));
                content.put("latitude", map.get("lat"));
                content.put("description", "");

                reportContents.add(content);
            }
        }

        data.put("content", reportContents.toString());


        String url = rootUrl + ResourceUtil.getConfigByName("syncAddressToCloudUrl");
        try {
            HttpClientUtils.post(url, data, "UTF-8");
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    public void dangerSourceReport() {
        Map<String, String> data = new HashMap<>();

        String idSb = "";
        data.put("enterpriseId", enterpriseId);

        String tempToken = "token=" + token + "&enterpriseId=" + enterpriseId;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put("token", ciphertext);

        String sql = "SELECT ds.id oldId,DATE_FORMAT(ds.ye_recognize_time,'%Y-%m-%d') identifyTime,ds.ye_profession profession,\n" +
                "\thm.hazard_name dangerName,ds.damage_type injuryCategory,ds.ye_accident accidentType,ds.ye_possibly_hazard description,\n" +
                "\tds.ye_probability possibility,ds.ye_cost loss,ds.LEC_risk_possibility lecPossibility,\n" +
                "\tds.LEC_risk_loss lecLoss,ds.LEC_exposure lecExposure,ds.LEC_risk_value lecRiskValue,ds.ye_risk_grade riskLevel,\n" +
                "\tds.ye_hazard_cate riskType,act.activity_name jobActivity,ds.doc_source controlStandardFrom,\n" +
                "\tds.section_name chapterAndClause,ds.ye_standard standardContent,ds.manage_measure controlMeasure,\n" +
                "\tpst.post_name responsiblePost,ds.ye_mhazard_desc hiddenDangerDescribe,ds.fine_money referencePrice\t\n" +
                "FROM t_b_danger_source ds LEFT JOIN t_b_hazard_manage hm ON hm.id=ds.hazard_manage_id\n" +
                "LEFT JOIN t_b_activity_manage act ON act.id=ds.activity_id LEFT JOIN t_b_post_manage pst ON pst.id=ds.post_id\n" +
                "WHERE ds.cloud_report_status = 0";

        List<Map<String, Object>> resultList = systemService.findForJdbc(sql);
        List<String> idList = systemService.findListbySql("SELECT id FROM t_b_danger_source WHERE cloud_report_status = '0'");
        if (!idList.isEmpty() && idList.size() > 0) {
            idSb = StringUtils.join(idList, "','");
        }
        data.put("content", JSONHelper.toJSONString(resultList));


        String url = rootUrl + ResourceUtil.getConfigByName("syncDangerSourceToCloudUrl");
        try {
            String result = HttpClientUtils.post(url, data, "UTF-8");

            if (StringUtils.isNotBlank(result)) {
                net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                    String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(idSb)) {
                        systemService.updateBySqlString("update t_b_danger_source set cloud_report_date = '" + reportDate + "' , cloud_report_status = '1' where id in ('" + idSb + "')");
                    }
                }
            }
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    public void addressRiskRelReport() {
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

        String sql = "SELECT rel.id AS oldId, rel.danger_id AS dangerId, rel.address_id AS addressId, rel.risk_level AS riskLevel FROM t_b_danger_address_rel rel";
        List<Map<String, Object>> mapList = systemService.findForJdbc(sql);
        data.put("content", JSONHelper.toJSONString(mapList));

        String url = rootUrl + ResourceUtil.getConfigByName("syncAddressRiskRelToCloudUrl");
        try {
            HttpClientUtils.post(url, data, "UTF-8");
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    public void hiddenDangerReport() {
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

        //避免数据过多造成阻塞，每次上报2000条
        String sql = "SELECT hde.id oldId, DATE_FORMAT(hde.exam_date, '%Y-%m-%d') examDate, hde.exam_type examType, hde.deal_type dealType,\n" +
                "\t(CASE hde.shift WHEN 1 THEN '早班' WHEN 2 THEN '中班' WHEN 3 THEN '晚班' ELSE '' END) shift, hde.address addressId,\n" +
                "\taddr.address addressName, hde.duty_unit dutyUnitId, ddpt.departname dutyUnitName, NULL dutyManId, hde.duty_man dutyManName,\n" +
                "\thde.danger_id dangerSourceId, ds.ye_mhazard_desc dangerSourceDesc, hde.problem_desc problemDesc, hde.itemId itemId,\n" +
                "\thde.itemId itemName, hde.itemUserId itemUserId, hde.hidden_category hiddenCategory, hde.hidden_nature hiddenNature,\n" +
                "\tDATE_FORMAT(hde.begin_well_date, '%Y-%m-%d %T') beginWellTime, DATE_FORMAT(hde.end_well_date,'%Y-%m-%d %T') endWellTime,\n" +
                "  (CASE hde.limit_shift WHEN 1 THEN '早班' WHEN 2 THEN '中班' WHEN 3 THEN '晚班' ELSE '' END) limitShift,\n" +
                "\tDATE_FORMAT(hde.limit_date, '%Y-%m-%d') limitDate, hde.review_man appointReviewManId, ru.realname appointReviewManName,\n" +
                "\thde.sjjc_dept superiorDepart, hde.sjjc_check_man superiorCheckMan, hde.pro_type professionalType,\n" +
                "\thde.check_type checkType, hde.apply_delay extensionRequest, hde.remark remark, DATE_FORMAT(hdh.modify_date, '%Y-%m-%d') modifyDate,\n" +
                "\t(CASE hdh.modify_shift WHEN 1 THEN '早班' WHEN 2 THEN '中班' WHEN 3 THEN '晚班' ELSE '' END) modifyShift,\n" +
                "\thdh.modify_man modifyManName, hdh.roll_back_remark modifyRejectReason, DATE_FORMAT(hdh.review_date, '%Y-%m-%d') reviewDate,\n" +
                "\t(CASE hdh.review_shift WHEN 1 THEN '早班' WHEN 2 THEN '中班' WHEN 3 THEN '晚班' ELSE '' END) reviewShift,\n" +
                "\thdh.review_man reviewManName, hdh.review_result reviewResult, hdh.handlel_status handleStatus\n" +
                "FROM t_b_hidden_danger_exam hde LEFT JOIN t_b_hidden_danger_handle hdh ON hdh.hidden_danger_id = hde.id \n" +
                "LEFT JOIN t_b_address_info addr ON hde.address = addr.id LEFT JOIN t_s_depart ddpt ON hde.duty_unit = ddpt.ID\n" +
                "LEFT JOIN t_b_danger_source ds ON hde.danger_id = ds.id LEFT JOIN t_s_base_user ru ON hde.review_man = ru.id\n" +
                "WHERE (hde.cloud_report_status=0 or (hde.cloud_report_status=1 and (hde.update_date >= hde.cloud_report_date OR hdh.update_date >= hde.cloud_report_date)))\n" +
                "ORDER BY hde.exam_date DESC";
        List<Map<String, Object>> resultList = systemService.findForJdbc(sql);
        List<String> idList = new ArrayList<>();
        if (!resultList.isEmpty() && resultList.size() > 0) {
            for (Map<String, Object> map : resultList) {
                String id = (String) map.get("oldId");
                idList.add(id);

                String itemUserId = (String) map.get("itemUserId");
                if (StringUtils.isNotBlank(itemUserId)) {
                    String itemSql = "SELECT GROUP_CONCAT(DISTINCT realname) FROM t_s_base_user WHERE FIND_IN_SET(id,'" + itemUserId + "')";
                    List<String> tempList = systemService.findListbySql(itemSql);
                    if (!tempList.isEmpty() && tempList.size() > 0) {
                        String itemUserName = tempList.get(0);
                        map.put("itemUserName", itemUserName);
                    }
                }
            }
        }
        String ids = StringUtils.join(idList, "','");
        data.put("content", JSONHelper.toJSONString(resultList));

        String url = rootUrl + ResourceUtil.getConfigByName("syncHiddenDangerToCloudUrl");
        try {
            String result = HttpClientUtils.post(url, data, "UTF-8");
            if (StringUtils.isNotBlank(result)) {
                net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                    String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(ids)) {
                        systemService.updateBySqlString("update t_b_hidden_danger_exam set cloud_report_date = '" + reportDate + "' , cloud_report_status = '1' where id in ('" + ids + "')");
                    }
                }
            }
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    public void violationReport() {
        Map<String, String> data = new HashMap<>();

        String idSb = "";
        data.put("enterpriseId", enterpriseId);

        String tempToken = "token=" + token + "&enterpriseId=" + enterpriseId;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put("token", ciphertext);

        String sql = "SELECT\n" +
                "\tvio.id oldId,\n" +
                "\tDATE_FORMAT(vio.vio_date, '%Y-%m-%d') vioDate,\n" +
                "\ttp.typename shift,\n" +
                "\tvio.vio_address address,\n" +
                "\tvdpt.departname unit,\n" +
                "\tvio.vio_category classification,\n" +
                "\tvio.vio_qualitative qualitative,\n" +
                "\tvio.vio_level vioLevel,\n" +
                "\tvio.stop_people stopPeople,\n" +
                "\tsdpt.departname stopUnit,\n" +
                "\tvio.vio_fact_desc description,\n" +
                "\tvio.remark remark,\n" +
                "\tvio.vio_people people\n" +
                "FROM\n" +
                "\tt_b_three_violations vio\n" +
                "LEFT JOIN t_s_depart vdpt ON vdpt.id = vio.vio_units\n" +
                "LEFT JOIN t_s_depart sdpt ON sdpt.id = vio.find_units\n" +
                "LEFT JOIN t_s_type tp on tp.typecode=vio.shift\n" +
                "LEFT JOIN t_s_typegroup tg on tg.id=tp.typegroupid\n" +
                "WHERE\n" +
                "\tvio.cloud_report_status = '0'\n" +
                "AND tg.typegroupcode='workShift'";

        List<Map<String, Object>> resultList = systemService.findForJdbc(sql);
        List<String> idList = systemService.findListbySql("SELECT id FROM t_b_three_violations WHERE cloud_report_status = '0'");
        if (!idList.isEmpty() && idList.size() > 0) {
            idSb = StringUtils.join(idList, "','");
        }
        data.put("content", JSONHelper.toJSONString(resultList));


        String url = rootUrl + ResourceUtil.getConfigByName("syncViolationsToCloudUrl");
        try {
            String result = HttpClientUtils.post(url, data, "UTF-8");
            if (StringUtils.isNotBlank(result)) {
                net.sf.json.JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                    String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                    if (StringUtil.isNotEmpty(idSb)) {
                        systemService.updateBySqlString("update t_b_three_violations set cloud_report_date = '" + reportDate + "' , cloud_report_status = '1' where id in ('" + idSb + "')");
                    }
                }
            }
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    public void operationLogReport() {
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

        //这里不做上报状态的处理，每次上报前一天的日志
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());

        String sql = "SELECT lg.broswer, lg.logcontent logContent, lg.loglevel logLevel, lg.note ip, lg.operatetype operateType, lg.userid userId, bu.username userName, DATE_FORMAT(lg.operatetime,'%Y-%m-%d') operateTime";
        sql += " FROM t_s_log lg LEFT JOIN t_s_base_user bu on bu.id=lg.userid WHERE lg.operatetime LIKE '" + yesterday + "%'";

        List<Map<String, Object>> resultList = systemService.findForJdbc(sql);

        data.put("content", JSONHelper.toJSONString(resultList));

        String url = rootUrl + ResourceUtil.getConfigByName("syncOperationLogToCloudUrl");
        try {
            HttpClientUtils.post(url, data, "UTF-8");
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }
}
