package com.sdzk.buss.web.common.service.impl;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.FtpRemote;
import com.sdzk.buss.web.common.RiskTaskType;
import com.sdzk.buss.web.common.entity.FtpConnectInfo;
import com.sdzk.buss.web.common.service.ReportToProvinceService;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.common.utils.FtpUtil;
import com.sdzk.buss.web.gjj.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("ReportToProvinceService")
@Transactional
public class ReportToProvinceServiceImpl extends CommonServiceImpl implements ReportToProvinceService {
    @Autowired
    private SystemService systemService;

    /**
     * @param list 辨识任务
     * @return
     */
    @Override
    public AjaxJson reportRiskIdentTask(List<Map<String, Object>> list) {
        // 上传成功条数
        int successNum = 0;
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }

        String mineCode = ResourceUtil.getConfigByName("mine_code");
        // 需要上报的数据list
        JSONArray ja = new JSONArray();
        for (Map<String, Object> t : list) {
            if(t.get("fileId") != null){
                boolean b = uploadToFtp(t.get("fileId").toString(), mineCode, FtpRemote.RISK.getRemote(), t.get("riskIdentTaskCode").toString());
                if(!b){
                    continue;
                }
            }
            successNum++;
            ja.add(riskTaskToJson(t));
            ids.append(",'").append(t.get("riskIdentTaskCode").toString()).append("'");
        }
        if (ids.length() == 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = ja.toString();
        String token = ResourceUtil.getConfigByName("token");
        String url = ResourceUtil.getConfigByName("reportRiskIdentTaskToProvince");

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_risk_task_rel SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + successNum+"】条，失败【"+(list.size()-successNum)+"】条。");
        return j;
    }

    /**
     * 转换风险辨识数据
     *
     * @param e 风险辨识任务
     * @return map
     */
    public JSONObject riskTaskToJson(Map<String, Object> e) {
        JSONObject j = new JSONObject();
        j.put("riskIdentTaskCode", e.get("riskIdentTaskCode"));
        j.put("stateFlag", e.get("stateFlag"));
        // 辨识类型（年度1、专项2、其他3）risk_task_type
        if (e.get("type") != null) {
            j.put("type", convertTaskType(e.get("type").toString()));
            j.put("name", e.get("name"));
            j.put("startDate", e.get("startDate"));
            j.put("endDate", e.get("endDate"));
            j.put("officer", convertUserName(e.get("officer") == null ? "" : e.get("officer").toString()));
            j.put("participant", convertUserName(e.get("participant") == null ? "" : e.get("participant").toString()));
            j.put("fileName", "");
            j.put("dataTime", String.valueOf(e.get("dataTime") == null ? "" : e.get("dataTime")));
        }
        return j;
    }

    /**
     * 转换拼接用户名 多个用&拼接
     *
     * @param userId 用户id
     * @return 用户名
     */
    String convertUserName(String userId) {
        String[] ids = userId.split(",");
        StringBuilder name = new StringBuilder();
        for (String id : ids) {
            TSUser user = systemService.getEntity(TSUser.class, id);
            if (user != null) {
                name.append("&").append(user.getRealName().replaceAll(" ", ""));
            }else{
                //userId不为空但是传的是汉字
                if(StringUtils.isNoneBlank(userId)){
                    name.append("&").append(userId);
                }
            }
        }
        String str = name.toString();
        if (str.startsWith("&")) {
            str = str.substring(1);
        }
        return str;
    }

    /**
     * 根据用户id查询部门名称 多个用&拼接
     *
     * @param userId 用户id
     * @return 用户名
     */
    String convertDepartNameByUserId(String userId) {
        String[] ids = userId.split(",");
        StringBuilder name = new StringBuilder();
        for (String id : ids) {
            TSUser user = systemService.getEntity(TSUser.class, id);
            if (user != null) {
                if(user.getUserOrgList().size() > 0){ //过滤已删除的userId数据
                    name.append("&").append(user.getTSDepart().getDepartname().replaceAll(" ", ""));
                }
            }else{
                //userId存储的是汉字时，根据姓名查部门
                String sql = "select * from t_s_depart where id = (select org_id from t_s_user_org where user_id = '"+userId+"')";
                Map<String,Object> tsDepart = systemService.findOneForJdbc(sql);
                if (tsDepart != null) {
                    name.append("&").append(tsDepart.get("departname").toString().replaceAll(" ", ""));
                }
            }
        }
        String str = name.toString();
        if (str.startsWith("&")) {
            str = str.substring(1);
        }
        return str;
    }

    /**
     * 转换拼接部门名 多个用&拼接
     *
     * @param departId 部门id
     * @return 用户名
     */
    String convertDepartName(String departId) {
        String[] ids = departId.split(",");
        StringBuilder name = new StringBuilder();
        for (String id : ids) {
            TSDepart depart = systemService.getEntity(TSDepart.class, id);
            if (depart != null) {
                name.append("&").append(depart.getDepartname().replaceAll(" ", ""));
            }
        }
        String str = name.toString();
        if (str.startsWith("&")) {
            str = str.substring(1);
        }
        return str;
    }

    /**
     * 根据地点id查询地点名称 多个用&拼接
     *
     * @param addressId 地点id
     * @return 地点
     */
    String convertAddressName(String addressId) {
        String[] ids = addressId.split(",");
        StringBuilder name = new StringBuilder();
        for (String id : ids) {
            TBAddressInfoEntity addressInfo = systemService.getEntity(TBAddressInfoEntity.class, id);
            if (addressInfo != null) {
                name.append("&").append(addressInfo.getAddress().replaceAll(" ", ""));
            }
        }
        String str = name.toString();
        if (str.startsWith("&")) {
            str = str.substring(1);
        }
        return str;
    }

    @Override
    public AjaxJson reportPicture(List<SFPictureInfoEntity> list) {
        // 上传成功条数
        int successNum = 0;
        AjaxJson j = new AjaxJson();
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportPictureToProvince");

        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setMsg("上报失败");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (SFPictureInfoEntity t : list) {
            // 上报文件
            boolean b = uploadToFtp(t.getFileId(), mineCode, FtpRemote.PICTURE.getRemote(), t.getId().toString());
            if (b) {
                successNum++;
                reportList.add(reportPictureToJson(t));
                ids.append(",'").append(t.getId()).append("'");
            }
        }
        if (ids.length() == 0) {
            j.setMsg("上报失败,图形文件不存在（数量）:" + list.size());
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_picture_info SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + successNum+"】条，失败【"+(list.size()-successNum)+"】条。");
        return j;
    }

    /**
     * 转换 图形文件信息为map
     *
     * @param e 图形文件
     * @return map
     */
    public JSONObject reportPictureToJson(SFPictureInfoEntity e) {
        JSONObject j = new JSONObject();
        j.put("pictureCode", String.valueOf(e.getId()));
        j.put("stateFlag", e.getStateFlag());
        j.put("fileType", e.getFileType());
        j.put("fileName", e.getFileName());
        j.put("dataTime", formatDateTime(e.getCreateDate()));
        return j;
    }

    @Override
    public AjaxJson reportRiskControl(List<SfRiskControlEntity> list) {
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (SfRiskControlEntity t : list) {
            reportList.add(reportRiskToJson(t));
            ids.append(",'").append(t.getId()).append("'");

        }
        if (ids.length() == 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportRiskControlToProvince");

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_risk_control SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time=NOW() WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + list.size()+"】条。");
        return j;
    }

    @Override
    public AjaxJson reportRiskMeasure(List<SfRiskMeasureEntity> list) {
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (SfRiskMeasureEntity t : list) {
            reportList.add(reportRiskMeasureToJson(t));
            ids.append(",'").append(t.getId()).append("'");
        }
        if (ids.length() == 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportRiskMeasureToProvince");

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_risk_measure SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time=NOW() WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + list.size()+"】条。");
        return j;
    }


    /**
     * 转换 风险管控记录信息为map
     *
     * @param e 风险管控记录
     * @return map
     */
    public JSONObject reportRiskToJson(SfRiskControlEntity e) {
        JSONObject j = new JSONObject();
        j.put("riskControlCode", String.valueOf(e.getId()));
        j.put("stateFlag", e.getStateFlag());
        j.put("riskCode", e.getRiskCode());
        j.put("riskLevel", convertRiskLevel(e.getRiskLevel()));
        j.put("controlType", convertControlType(e.getControlType()));
        j.put("controlDate", formatDate(e.getControlDate()));
        j.put("controlDepart", e.getControlDepart());
        j.put("controlMan", e.getControlMan());
        j.put("implemented", e.getImplemented());
        j.put("unimplemented", e.getUnimplemented());
        j.put("dataTime", formatDateTime(e.getDataTime()));
        return j;
    }

    String convertControlType(String type) {
        String res = "";
        if (RiskTaskType.CONTROL_TYPE_1.getProvince().equals(type)) {
            res = RiskTaskType.CONTROL_TYPE_1.getCountry();
        } else if (RiskTaskType.CONTROL_TYPE_2.getProvince().equals(type)) {
            res = RiskTaskType.CONTROL_TYPE_2.getCountry();
        } else if (RiskTaskType.CONTROL_TYPE_3.getProvince().equals(type)) {
            res = RiskTaskType.CONTROL_TYPE_3.getCountry();
        } else if (RiskTaskType.CONTROL_TYPE_4.getProvince().equals(type)) {
            res = RiskTaskType.CONTROL_TYPE_4.getCountry();
        } else if (RiskTaskType.CONTROL_TYPE_5.getProvince().equals(type)) {
            res = RiskTaskType.CONTROL_TYPE_5.getCountry();
        } else if (RiskTaskType.CONTROL_TYPE_6.getProvince().equals(type)) {
            res = RiskTaskType.CONTROL_TYPE_6.getCountry();
        } else if (RiskTaskType.CONTROL_TYPE_7.getProvince().equals(type)) {
            res = RiskTaskType.CONTROL_TYPE_7.getCountry();
        } else if (RiskTaskType.CONTROL_TYPE_8.getProvince().equals(type)) {
            res = RiskTaskType.CONTROL_TYPE_8.getCountry();
        } else if (RiskTaskType.CONTROL_TYPE_9.getProvince().equals(type)) {
            res = RiskTaskType.CONTROL_TYPE_9.getCountry();
        } else {
            res = RiskTaskType.CONTROL_TYPE_1.getCountry();
        }
        return res;
    }

    /**
     * 转换 风险管控记录信息为map
     *
     * @param e 风险管控记录
     * @return map
     */
    public JSONObject reportRiskMeasureToJson(SfRiskMeasureEntity e) {
        JSONObject j = new JSONObject();
        j.put("riskMeasureCode", String.valueOf(e.getId()));
        j.put("stateFlag", e.getStateFlag());
        j.put("riskCode", e.getRiskCode());
        j.put("riskPointCode", e.getRiskPointCode());
        j.put("measures", e.getMeasures());
        j.put("post", e.getPost());
        j.put("depart", e.getDepart());
        j.put("dataTime", formatDateTime(e.getDateTime()));
        return j;
    }

    String convertTaskType(String type) {
        String res = "";
        if (RiskTaskType.T2.getProvince().equals(type)) {
            res = RiskTaskType.T2.getCountry();
        } else if (RiskTaskType.T3.getProvince().equals(type)) {
            res = RiskTaskType.T3.getCountry();
        } else {
            res = RiskTaskType.T4.getCountry();
        }
        return res;
    }


    @Override
    public AjaxJson reportLoginLog(List<TSLog> list, String userId) {
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        List<Object> reportList = new ArrayList<>();
        int size = list.size();

        for (int i = 0; i < size; i++) {
            TSLog t = list.get(i);
            ids.append(",'").append(t.getId()).append("'");
            TSLog t2 = null;
            if (i != size - 1) {
                t2 = list.get(i + 1);
            }
            reportList.add(loginLogToJson(t, t2, userId));
        }

        if (ids.length() == 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportLoginLogToProvince");

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE t_s_log SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + list.size()+"】条。");
        return j;
    }

    public JSONObject loginLogToJson(TSLog e, TSLog e2, String userId) {
        JSONObject j = new JSONObject();
        Date d1 = e.getOperatetime();
        Date d2 = null;
        if (e2 != null) {
            d2 = e2.getOperatetime();
        }
        TSUser tsUser = systemService.getEntity(TSUser.class, userId);

        if(tsUser.getUserOrgList().size() > 0){
            TSDepart tsDepart = tsUser.getUserOrgList().get(0).getTsDepart();
            j.put("organization", tsDepart.getDepartname());
        }else{
            j.put("organization", "");
        }
        j.put("userName", tsUser.getRealName());
        j.put("post", convertPostName(tsUser.getId()));
        j.put("loginTime", formatDateTime(e.getOperatetime()));
        j.put("dataTime", formatDateTime(e.getOperatetime()));
        j.put("onlineDuration", mathDuration(d1, d2));
        //默认新增
        j.put("stateFlag", Constants.GJJ_STATE_FLAG_1);
        return j;
    }


    String convertPostName(String userId) {
        String sql = "select b.post_name postName from t_b_post_user a left JOIN  t_b_post_manage b on a.post_id=b.id where a.user_id='" + userId + "'";
        List<Integer> listbySql = systemService.findListbySql(sql);
        if (listbySql != null && listbySql.size() > 0) {
            return String.valueOf(listbySql.get(0));
        }
        return "";
    }

    /**
     * @param d1 第一次登录时间
     * @param d2 第二次登录时间
     * @return
     */
    String mathDuration(Date d1, Date d2) {
        String str = "0.1";
        Random r = new Random();
        if (d2 == null) {
            // 最后一次登录 给洗一次登录时间赋值为当前时间
            d2 = new Date();
        }
        // 用后一次时间减去前一次登录时间
        long l = d2.getTime() - d1.getTime();
        // 如果相差超过2小时，取小于2的随机数
        if (l > 3600000 * 2) {
            str = String.valueOf(r.nextDouble() * 2);
        } else {
            // 不超过2小时取小于这个数的随机数
            str = String.valueOf(r.nextDouble() * ((double) l / 3600000));
        }
        return str;
    }

    @Override
    public AjaxJson reportReportInfo(List<SFReportInfoEntity> list) {
        // 上传成功条数
        int successNum = 0;
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportReportInfoToProvince");
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (SFReportInfoEntity t : list) {
            boolean b = uploadToFtp(t.getFileId(), mineCode, FtpRemote.REPORT.getRemote(), t.getId().toString());
            if (b) {
                successNum++;
                reportList.add(reportReportInfoToJson(t));
                ids.append(",'").append(t.getId()).append("'");
            }
        }
        if (ids.length() == 0) {
            j.setMsg("上报失败,图形文件不存在（数量）:" + list.size());
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_report_info SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + successNum+"】条，失败【"+(list.size()-successNum)+"】条。");
        return j;
    }

    /**
     * 转换 报告文件信息为map
     *
     * @param e 报告文件
     * @return map
     */
    public JSONObject reportReportInfoToJson(SFReportInfoEntity e) {
        JSONObject j = new JSONObject();
        j.put("reportCode", String.valueOf(e.getId()));
        j.put("stateFlag", e.getStateFlag());
        j.put("reportType", e.getReportType());
        j.put("upDate", formatDate(e.getUpDate()));
        j.put("reportName", e.getReportName());
        j.put("fileName", e.getFileName());
        j.put("dataTime", formatDateTime(e.getCreateDate()));
        return j;
    }


    @Override
    public AjaxJson sfArchiveInfo(List<SfArchiveInfoEntity> list) {
        // 上传成功条数
        int successNum = 0;
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportArchiveInfoToProvince");
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (SfArchiveInfoEntity t : list) {
            boolean b = uploadToFtp(t.getFileId(), mineCode, FtpRemote.ARCHIVE.getRemote(), t.getId().toString());
            if (b) {
                successNum++;
                reportList.add(sfArchiveInfoToJson(t));
                ids.append(",'").append(t.getId()).append("'");
            }
        }
        if (ids.length() == 0) {
            j.setMsg("上报失败,图形文件不存在（数量）:" + list.size());
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_archive_info SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + successNum+"】条，失败【"+(list.size()-successNum)+"】条。");
        return j;
    }

    /**
     * 转换 重大隐患档案信息为map
     *
     * @param e 重大隐患档案
     * @return map
     */
    public JSONObject sfArchiveInfoToJson(SfArchiveInfoEntity e) {
        JSONObject j = new JSONObject();
        j.put("archiveCode", String.valueOf(e.getId()));
        j.put("stateFlag", e.getStateFlag());
        j.put("hdCode", convertHdCode(e.getHdCode()));
        j.put("upDate", formatDate(e.getUpDate()));
        j.put("reportName", e.getReportName());
        j.put("fileName", e.getFileName());
        j.put("dataTime", formatDateTime(e.getCreateDate()));
        return j;
    }

    String convertHdCode(String code) {
        String sql = "SELECT id FROM sf_hidden_rel where is_delete='0' and hidden_id='" + code + "'";
        List<Integer> listbySql = systemService.findListbySql(sql);
        if (listbySql != null && listbySql.size() > 0) {
            return String.valueOf(listbySql.get(0));
        }
        return "";
    }

    @Override
    public AjaxJson sfPlanInfo(List<SfPlanInfoEntity> list) {
        // 上传成功条数
        int successNum = 0;
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportPlanInfoToProvince");
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (SfPlanInfoEntity t : list) {
            boolean b = uploadToFtp(t.getFileId(), mineCode, FtpRemote.PLAN.getRemote(), t.getId().toString());
            if (b) {
                successNum++;
                reportList.add(sfPlanInfoToJson(t));
                ids.append(",'").append(t.getId()).append("'");
            }
        }
        if (ids.length() == 0) {
            j.setMsg("上报失败,图形文件不存在（数量）:" + list.size());
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);
        String reportContent = JSONHelper.toJSONString(reportList);

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_plan_info SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + successNum+"】条，失败【"+(list.size()-successNum)+"】条。");
        return j;
    }

    /**
     * 转换 重大风险管控方案信息为map
     *
     * @param e 重大风险管控方案
     * @return map
     */
    public JSONObject sfPlanInfoToJson(SfPlanInfoEntity e) {
        JSONObject j = new JSONObject();
        j.put("planCode", String.valueOf(e.getId()));
        j.put("stateFlag", e.getStateFlag());
        j.put("riskCode", e.getRiskCode());
        j.put("upDate", formatDate(e.getUpDate()));
        j.put("reportName", e.getReportName());
        j.put("fileName", e.getFileName());
        j.put("dataTime", formatDateTime(e.getCreateDate()));
        return j;
    }

    @Override
    public AjaxJson sfSysFileInfo(List<SfSysFileInfoEntity> list) {
        // 上传成功条数
        int successNum = 0;
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportSysFileInfoToProvince");
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (SfSysFileInfoEntity t : list) {
            boolean b = uploadToFtp(t.getFileId(), mineCode, FtpRemote.sys.getRemote(), t.getId().toString());
            if (b) {
                successNum++;
                reportList.add(sfSysFileInfoToJson(t));
                ids.append(",'").append(t.getId()).append("'");
            }
        }
        if (ids.length() == 0) {
            j.setMsg("上报失败,图形文件不存在（数量）:" + list.size());
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_sys_file_info SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + successNum+"】条，失败【"+(list.size()-successNum)+"】条。");
        return j;
    }

    /**
     * 转换 双重预防工作制度信息为map
     *
     * @param e 双重预防工作制度
     * @return map
     */
    public JSONObject sfSysFileInfoToJson(SfSysFileInfoEntity e) {
        JSONObject j = new JSONObject();
        j.put("sysCode", String.valueOf(e.getId()));
        j.put("stateFlag", e.getStateFlag());
        j.put("upDate", formatDate(e.getUpDate()));
        j.put("sysName", e.getSysName());
        j.put("fileName", e.getFileName());
        j.put("dataTime", formatDateTime(e.getCreateDate()));
        return j;
    }

    @Override
    public AjaxJson sfTrainingInfo(List<SfTrainingInfoEntity> list) {
        // 上传成功条数
        int successNum = 0;
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportTrainingInfoToProvince");
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (SfTrainingInfoEntity t : list) {
            boolean b = uploadToFtp(t.getFileId(), mineCode, FtpRemote.TRAIN.getRemote(), t.getId().toString());
            if (b) {
                successNum++;
                reportList.add(sfTrainingInfoToJson(t));
                ids.append(",'").append(t.getId()).append("'");
            }
        }
        if (ids.length() == 0) {
            j.setMsg("上报失败,图形文件不存在（数量）:" + list.size());
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_training_info SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + successNum+"】条，失败【"+(list.size()-successNum)+"】条。");
        return j;
    }

    /**
     * 转换 培训档案信息为map
     *
     * @param e 培训档案
     * @return map
     */
    public JSONObject sfTrainingInfoToJson(SfTrainingInfoEntity e) {
        JSONObject j = new JSONObject();
        j.put("trainingCode", String.valueOf(e.getId()));
        j.put("stateFlag", e.getStateFlag());
        j.put("trainingType", e.getTrainingType());
        j.put("trainingDate", formatDate(e.getTrainingDate()));
        j.put("trainingTheme", e.getTrainingTheme());
        j.put("trainingPerson", convertUserName(e.getTrainingPerson()));
        j.put("trainingDepart", convertDepartName(e.getTrainingDepart()));
        j.put("fileName", e.getFileName());
        j.put("dataTime", formatDateTime(e.getCreateDate()));
        return j;
    }


    @Override
    public AjaxJson reportRiskPoint(List<TBAddressInfoEntity> list) {
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportRiskPointToProvince");
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (TBAddressInfoEntity t : list) {
            reportList.add(sfRiskPointToJson(t));
            ids.append(",'").append(t.getId()).append("'");
        }
        if (ids.length() == 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE t_b_address_info SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + list.size()+"】条。");
        return j;
    }

    /**
     * 转换 风险点信息为map
     *
     * @param e 风险点
     * @return map
     */
    public JSONObject sfRiskPointToJson(TBAddressInfoEntity e) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject j = new JSONObject();
        j.put("riskPointCode", String.valueOf(e.getId()));
        j.put("stateFlag", e.getStateFlag());
        j.put("name", e.getAddress());
        j.put("startDate", formatDate(e.getStartDate()));
        j.put("endDate", formatDate(e.getEndDate()));
        j.put("xCoordinate", e.getLat());
        j.put("yCoordinate", e.getLon());
        j.put("pictureCode", e.getPictureCode());
        j.put("dataTime", formatDateTime(e.getCreateDate()));
        return j;
    }

    @Override
    public AjaxJson reportRisk(List<RiskIdentificationEntity> list) {
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportRiskToProvince");
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (RiskIdentificationEntity t : list) {
            reportList.add(sfRiskToJson(t));
            ids.append(",'").append(t.getId()).append("'");
        }
        if (ids.length() == 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE t_b_risk_identification SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + list.size()+"】条。");
        return j;
    }

    /**
     * 转换 风险信息为map
     *
     * @param e 风险
     * @return map
     */
    public JSONObject sfRiskToJson(RiskIdentificationEntity e) {
        JSONObject j = new JSONObject();
        j.put("riskCode", String.valueOf(e.getId()));
        j.put("stateFlag", e.getStateFlag());
        j.put("riskIdentTaskCode", convertRiskIdentTaskCode(e.getRiskTaskId()));  //辨识任务编码 可选
        j.put("riskPointCode", e.getAddress().getId());
        j.put("idenObject", e.getAddress().getAddress());//辨识对象 必选
        j.put("checkProject", e.getAddress().getAddress());//检查项目 必选
        j.put("riskDesc", e.getRiskDesc());
        j.put("riskType", convertRiskType(e.getRiskType()));
        j.put("riskLevel", convertRiskLevel(e.getRiskLevel()));
        j.put("dataTime", formatDateTime(e.getCreateDate()));
        return j;
    }

    String convertRiskIdentTaskCode(String code) {
        String sql = "select id from sf_risk_task_rel where is_delete='0' and risk_task_id='" + code + "'";
        List<Integer> listbySql = systemService.findListbySql(sql);
        if (listbySql != null && listbySql.size() > 0) {
            return String.valueOf(listbySql.get(0));
        }
        return "";
    }

    String convertRiskType(String type) {
        String res = "";
        if (RiskTaskType.RISK_TYPE_1.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_1.getCountry();
        } else if (RiskTaskType.RISK_TYPE_2.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_2.getCountry();
        } else if (RiskTaskType.RISK_TYPE_3.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_3.getCountry();
        } else if (RiskTaskType.RISK_TYPE_4.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_4.getCountry();
        } else if (RiskTaskType.RISK_TYPE_5.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_5.getCountry();
        } else if (RiskTaskType.RISK_TYPE_6.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_6.getCountry();
        } else if (RiskTaskType.RISK_TYPE_7.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_7.getCountry();
        } else if (RiskTaskType.RISK_TYPE_8.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_8.getCountry();
        } else if (RiskTaskType.RISK_TYPE_9.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_9.getCountry();
        } else if (RiskTaskType.RISK_TYPE_10.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_10.getCountry();
        } else if (RiskTaskType.RISK_TYPE_11.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_11.getCountry();
        } else if (RiskTaskType.RISK_TYPE_12.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_12.getCountry();
        } else if (RiskTaskType.RISK_TYPE_13.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_13.getCountry();
        } else if (RiskTaskType.RISK_TYPE_14.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_14.getCountry();
        } else if (RiskTaskType.RISK_TYPE_15.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_15.getCountry();
        } else if (RiskTaskType.RISK_TYPE_16.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_16.getCountry();
        } else if (RiskTaskType.RISK_TYPE_17.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_17.getCountry();
        } else if (RiskTaskType.RISK_TYPE_18.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_18.getCountry();
        } else if (RiskTaskType.RISK_TYPE_19.getProvince().equals(type)) {
            res = RiskTaskType.RISK_TYPE_19.getCountry();
        } else {
            res = RiskTaskType.RISK_TYPE_10.getCountry();
        }
        return res;
    }

    String convertRiskLevel(String type) {
        String res = "";
        if (RiskTaskType.RISK_LEVEL_1.getProvince().equals(type)) {
            res = RiskTaskType.RISK_LEVEL_1.getCountry();
        } else if (RiskTaskType.RISK_LEVEL_2.getProvince().equals(type)) {
            res = RiskTaskType.RISK_LEVEL_2.getCountry();
        } else if (RiskTaskType.RISK_LEVEL_3.getProvince().equals(type)) {
            res = RiskTaskType.RISK_LEVEL_3.getCountry();
        } else if (RiskTaskType.RISK_LEVEL_4.getProvince().equals(type)) {
            res = RiskTaskType.RISK_LEVEL_4.getCountry();
        } else {
            res = RiskTaskType.RISK_LEVEL_4.getCountry();
        }
        return res;
    }

    @Override
    public AjaxJson reportThreeVio(List<Map<String, Object>> list) {
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportThreeVioToProvince");
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (Map<String, Object> t : list) {
            reportList.add(sfThreeVioToJson(t));
            ids.append(",'").append(t.get("vioCode")).append("'");
        }
        if (ids.length() == 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_vio_rel SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + list.size()+"】条。");
        return j;
    }

    /**
     * 转换 三违信息为map
     *
     * @param e 三违
     * @return map
     */
    public JSONObject sfThreeVioToJson(Map<String, Object> e) {
        JSONObject j = new JSONObject();
        j.put("vioCode", e.get("vioCode"));
        j.put("stateFlag", e.get("stateFlag"));
        j.put("vioDate", e.get("vioDate") == null ? "" : e.get("vioDate"));
        //与字典相同不转换
        j.put("vioShift", e.get("vioShift") == null ? "" : convertVioShift(e.get("vioShift").toString()));
        j.put("riskPointCode", e.get("riskPointCode") == null ? "" : e.get("riskPointCode"));
        j.put("vioUnit", e.get("vioUnit") == null ? "" : e.get("vioUnit"));
        j.put("vioPeople", e.get("vioPeople") == null ? "" : e.get("vioPeople"));
        //与字典相同不转换
        j.put("vioCategory", e.get("vioCategory") == null ? "" : e.get("vioCategory"));
        Object o = e.get("vioLevel");
        if (o == null) {
            o = e.get("vioQualitative");
            if (o == null) {
                // 三违等级和违章定性都是null 默认一般三违
                j.put("vioLevel", RiskTaskType.VIO_LEVEL_11.getCountry());
            } else {
                j.put("vioLevel", convertVioLevel2(o.toString()));
            }
        } else {
            j.put("vioLevel", convertVioLevel(o.toString()));
        }
        j.put("findUnit", e.get("findUnit") == null ? "" : e.get("findUnit"));
        j.put("stopPeople", e.get("stopPeople") == null ? "" : e.get("stopPeople"));
        j.put("vioDesc", e.get("vioDesc") == null ? "" : e.get("vioDesc"));
        j.put("vioResult", e.get("vioResult") == null ? "" : e.get("vioResult"));
        j.put("dataTime", String.valueOf(e.get("dataTime") == null ? "" : e.get("dataTime")));
        return j;
    }

    String convertVioLevel(String type) {
        String res = "";
        if (RiskTaskType.VIO_LEVEL_1.getProvince().equals(type)) {
            res = RiskTaskType.VIO_LEVEL_1.getCountry();
        } else if (RiskTaskType.VIO_LEVEL_2.getProvince().equals(type)) {
            res = RiskTaskType.VIO_LEVEL_2.getCountry();
        } else {
            res = RiskTaskType.VIO_LEVEL_3.getCountry();
        }
        return res;
    }

    /**
     * 有的矿没填三违等级，通过违章定性类确定三违等级
     *
     * @param type 类型编码
     * @return 国家局编码
     */
    String convertVioLevel2(String type) {
        String res = RiskTaskType.VIO_LEVEL_11.getCountry();
        if (RiskTaskType.VIO_LEVEL_14.getProvince().equals(type)) {
            res = RiskTaskType.VIO_LEVEL_14.getCountry();
        }
        return res;

    }

    String convertVioShift(String type) {
        String res = "";
        if (RiskTaskType.VIO_SHIFT_1.getProvince().equals(type)) {
            res = RiskTaskType.VIO_SHIFT_1.getCountry();
        } else if (RiskTaskType.VIO_SHIFT_2.getProvince().equals(type)) {
            res = RiskTaskType.VIO_SHIFT_2.getCountry();
        } else if (RiskTaskType.VIO_SHIFT_3.getProvince().equals(type)) {
            res = RiskTaskType.VIO_SHIFT_3.getCountry();
        } else if (RiskTaskType.VIO_SHIFT_4.getProvince().equals(type)) {
            res = RiskTaskType.VIO_SHIFT_4.getCountry();
        } else if (RiskTaskType.VIO_SHIFT_5.getProvince().equals(type)) {
            res = RiskTaskType.VIO_SHIFT_5.getCountry();
        } else if (RiskTaskType.VIO_SHIFT_6.getProvince().equals(type)) {
            res = RiskTaskType.VIO_SHIFT_6.getCountry();
        } else if (RiskTaskType.VIO_SHIFT_7.getProvince().equals(type)) {
            res = RiskTaskType.VIO_SHIFT_7.getCountry();
        } else {
            res = RiskTaskType.VIO_SHIFT_1.getCountry();
        }
        return res;
    }

    @Override
    public AjaxJson reportHidden(List<Map<String, Object>> list) {
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportHiddenToProvince");
        AjaxJson j = new AjaxJson();
        // 所有要上报的数据id，用于更新上报状态  ",'id1','id2'"
        StringBuilder ids = new StringBuilder();
        j.setMsg("上报成功");
        if (null == list || list.size() <= 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 需要上报的数据list
        List<Object> reportList = new ArrayList<>();
        for (Map<String, Object> t : list) {
            reportList.add(sfHiddenToJson(t));
            ids.append(",'").append(t.get("hdCode")).append("'");
        }
        if (ids.length() == 0) {
            j.setSuccess(false);
            j.setMsg("上报失败,数据为空");
            return j;
        }
        // 去掉开头的','   "'id1','id2'"
        String idStr = ids.substring(1);

        String reportContent = JSONHelper.toJSONString(reportList);

        // 加密token
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);

        String result = null;
        try {
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }

        //解析rpc返回的json
        if (result != null) {
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
            String code = resultJson.optString("code");
            //请求成功
            if (code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)) {
                // 更新上报状态
                String updateSql = "UPDATE sf_hidden_rel SET state_flag='" + Constants.GJJ_STATE_FLAG_0 + "',upload_time = '" + DateUtils.gettimestamp() + "' WHERE id in(" + idStr + ");";
                systemService.executeSql(updateSql);
            } else {
                j.setSuccess(false);
                j.setMsg("上报省局失败");
                return j;
            }
        } else {
            j.setSuccess(false);
            j.setMsg("上报省局失败");
            return j;
        }
        j.setMsg("上报成功【" + list.size()+"】条。");
        return j;
    }

    /**
     * 转换 隐患信息为map
     *
     * @param e 隐患
     * @return map
     */
    public JSONObject sfHiddenToJson(Map<String, Object> e) {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        JSONObject j = new JSONObject();
        j.put("hdCode", e.get("hdCode"));
        j.put("stateFlag", e.get("stateFlag"));
        j.put("riskMeasureCode", e.get("riskMeasureCode") == null ? "" : e.get("riskMeasureCode"));
        j.put("riskCode", e.get("riskCode") == null ? "" : e.get("riskCode"));
        j.put("inveDate", e.get("inveDate") == null ? "" : e.get("inveDate"));
        j.put("inveType", e.get("inveType") == null ? "" : convertInveType(e.get("inveType").toString()));
        j.put("inveMan", e.get("inveMan") == null ? "" : convertUserName(e.get("inveMan").toString()));
        j.put("hiddenAddress", e.get("hiddenAddress") == null ? "" : convertAddressName(e.get("hiddenAddress").toString()));
        j.put("riskPointCode", e.get("riskPointCode") == null ? "" : e.get("riskPointCode"));
        j.put("proType", e.get("proType") == null ? "" : convertProType(e.get("proType").toString()));
        j.put("hiddenLevel", e.get("hiddenLevel") == null ? "" : convertHiddenLevel(e.get("hiddenLevel").toString()));
        j.put("problemDesc", e.get("problemDesc") == null ? "" : e.get("problemDesc"));
        j.put("imgUrlList", e.get("imgUrlList") == null ? "" : e.get("imgUrlList"));
        //与字典相同不转换
        j.put("dealType", e.get("dealType") == null ? "" : e.get("dealType"));
        j.put("limitDate", e.get("limitDate") == null ? "" : e.get("limitDate"));
        j.put("status", e.get("status") == null ? "" : convertHiddenState(e.get("status").toString()));
        j.put("modifyDate", e.get("modifyDate") == null ? "" : e.get("modifyDate"));
        j.put("modifyUnit", e.get("modifyUnit") == null ? "" : convertDepartNameByUserId(e.get("modifyUnit").toString()));//转化id
        j.put("modifyMan", e.get("modifyMan") == null ? "" : convertUserName(e.get("modifyMan").toString()));
        j.put("modifyMeasures", e.get("modifyMeasures") == null ? "" : e.get("modifyMeasures"));
        j.put("modifyImgUrlList", e.get("modifyImgUrlList") == null ? "" : e.get("modifyImgUrlList"));
        j.put("reviewDate", e.get("reviewDate") == null ? "" : e.get("reviewDate"));
        j.put("reviewUnit", e.get("reviewUnit") == null ? "" : convertDepartNameByUserId(e.get("reviewUnit").toString()));
        j.put("reviewMan", e.get("reviewMan") == null ? "" : convertUserName(e.get("reviewMan").toString()));
        j.put("supervisionUnit", e.get("supervisionUnit") == null ? "" : convertDepartName(e.get("supervisionUnit").toString()));
        j.put("supervisor", e.get("supervisor") == null ? "" : convertUserName(e.get("supervisor").toString()));
        j.put("supervisionInfo", e.get("supervisionInfo") == null ? "" : e.get("supervisionInfo"));
        j.put("dataTime", String.valueOf(e.get("dataTime") == null ? "" : e.get("dataTime")));
        return j;
    }

    String convertHiddenLevel(String type) {
        String res = "";
        if (RiskTaskType.HIDDEN_LEVEL_1.getProvince().equals(type)) {
            res = RiskTaskType.HIDDEN_LEVEL_1.getCountry();
        } else if (RiskTaskType.HIDDEN_LEVEL_2.getProvince().equals(type)) {
            res = RiskTaskType.HIDDEN_LEVEL_2.getCountry();
        } else if (RiskTaskType.HIDDEN_LEVEL_3.getProvince().equals(type)) {
            res = RiskTaskType.HIDDEN_LEVEL_3.getCountry();
        } else if (RiskTaskType.HIDDEN_LEVEL_4.getProvince().equals(type)) {
            res = RiskTaskType.HIDDEN_LEVEL_4.getCountry();
        } else if (RiskTaskType.HIDDEN_LEVEL_5.getProvince().equals(type)) {
            res = RiskTaskType.HIDDEN_LEVEL_5.getCountry();
        } else if (RiskTaskType.HIDDEN_LEVEL_6.getProvince().equals(type)) {
            res = RiskTaskType.HIDDEN_LEVEL_6.getCountry();
        } else {
            res = RiskTaskType.HIDDEN_LEVEL_6.getCountry();
        }
        return res;
    }

    String convertInveType(String type) {
        String res = "";
        if (RiskTaskType.INVE_TYPE_1.getProvince().equals(type)) {
            res = RiskTaskType.INVE_TYPE_1.getCountry();
        } else if (RiskTaskType.INVE_TYPE_2.getProvince().equals(type)) {
            res = RiskTaskType.INVE_TYPE_2.getCountry();
        } else if (RiskTaskType.INVE_TYPE_3.getProvince().equals(type)) {
            res = RiskTaskType.INVE_TYPE_3.getCountry();
        } else if (RiskTaskType.INVE_TYPE_4.getProvince().equals(type)) {
            res = RiskTaskType.INVE_TYPE_4.getCountry();
        } else if (RiskTaskType.INVE_TYPE_5.getProvince().equals(type)) {
            res = RiskTaskType.INVE_TYPE_5.getCountry();
        } else if (RiskTaskType.INVE_TYPE_6.getProvince().equals(type)) {
            res = RiskTaskType.INVE_TYPE_6.getCountry();
        } else if (RiskTaskType.INVE_TYPE_7.getProvince().equals(type)) {
            res = RiskTaskType.INVE_TYPE_7.getCountry();
        } else if (RiskTaskType.INVE_TYPE_8.getProvince().equals(type)) {
            res = RiskTaskType.INVE_TYPE_8.getCountry();
        } else if (RiskTaskType.INVE_TYPE_9.getProvince().equals(type)) {
            res = RiskTaskType.INVE_TYPE_9.getCountry();
        } else {
            res = RiskTaskType.INVE_TYPE_9.getCountry();
        }
        return res;
    }

    String convertProType(String type) {
        String res = "";
        if (RiskTaskType.PRO_TYPE_1.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_1.getCountry();
        } else if (RiskTaskType.PRO_TYPE_2.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_2.getCountry();
        } else if (RiskTaskType.PRO_TYPE_3.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_3.getCountry();
        } else if (RiskTaskType.PRO_TYPE_4.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_4.getCountry();
        } else if (RiskTaskType.PRO_TYPE_5.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_5.getCountry();
        } else if (RiskTaskType.PRO_TYPE_6.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_6.getCountry();
        } else if (RiskTaskType.PRO_TYPE_7.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_7.getCountry();
        } else if (RiskTaskType.PRO_TYPE_8.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_8.getCountry();
        } else if (RiskTaskType.PRO_TYPE_9.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_9.getCountry();
        } else if (RiskTaskType.PRO_TYPE_10.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_10.getCountry();
        } else if (RiskTaskType.PRO_TYPE_11.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_11.getCountry();
        } else if (RiskTaskType.PRO_TYPE_12.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_12.getCountry();
        } else if (RiskTaskType.PRO_TYPE_13.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_13.getCountry();
        } else if (RiskTaskType.PRO_TYPE_14.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_14.getCountry();
        } else if (RiskTaskType.PRO_TYPE_15.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_15.getCountry();
        } else if (RiskTaskType.PRO_TYPE_16.getProvince().equals(type)) {
            res = RiskTaskType.PRO_TYPE_16.getCountry();
        } else {
            res = RiskTaskType.PRO_TYPE_16.getCountry();
        }
        return res;
    }

    String convertHiddenState(String type) {
        String res = "";
        if (RiskTaskType.HIDDEN_STATE_1.getProvince().equals(type)) {
            res = RiskTaskType.HIDDEN_STATE_1.getCountry();
        } else if (RiskTaskType.HIDDEN_STATE_3.getProvince().equals(type)) {
            res = RiskTaskType.HIDDEN_STATE_3.getCountry();
        } else if (RiskTaskType.HIDDEN_STATE_4.getProvince().equals(type)) {
            res = RiskTaskType.HIDDEN_STATE_4.getCountry();
        } else if (RiskTaskType.HIDDEN_STATE_5.getProvince().equals(type)) {
            res = RiskTaskType.HIDDEN_STATE_5.getCountry();
        } else {
            res = RiskTaskType.HIDDEN_STATE_1.getCountry();
        }
        return res;
    }

    /**
     * 通过ftp上传文件
     *
     * @param attId    文件id（t_s_attachment表）
     * @param mineCode 煤矿表编码
     * @param folder   不同业务存储文件的目录
     * @param mainId   待上报数据主键id
     * @return 是否上传成功
     */
    boolean uploadToFtp(String attId, String mineCode, String folder, String mainId) {
        boolean success = false;
        if (StringUtils.isNotBlank(attId)) {
            TSAttachment att = systemService.getEntity(TSAttachment.class, attId);
            if (att != null) {
                // 本地文件路径
                String filePath = ResourceUtil.getSysPath() + att.getRealpath();
                File file = new File(filePath);
                if (file.exists()) {
                    FtpConnectInfo f = new FtpConnectInfo(ResourceUtil.getConfigByName("ftpHost"), Integer.parseInt(ResourceUtil.getConfigByName("ftpPort")), ResourceUtil.getConfigByName("ftpUser"), ResourceUtil.getConfigByName("ftpPas"));
                    FtpUtil ftpUtil = new FtpUtil();
                    try {
                        if (ftpUtil.connect(f)) {
                            // ftp上报过去的文件路径 /ftpscyf/矿井编码/业务文件夹/数据主键/文件名.xxx
                            String ftpFilePath = FtpRemote.MAIN.getRemote() + mineCode + folder + mainId + "/";
                            String fileName = ftpFilePath + att.getAttachmenttitle() + "." + att.getExtend();
                            // 检查是否存在目录，不存在则创建
                            boolean directory = ftpUtil.createDirectory(ftpFilePath);
                            if (directory) {
                                success = ftpUtil.uploadFile(file, fileName);
                                LogUtil.info("~~~~~~~~~上传文件：【" + fileName + "】~" + success);
                            }
                            ftpUtil.disconnect();
                        }
                    } catch (IOException e) {
                        LogUtil.info("~~~~~~~~~上传文件错误：" + e);
                    }
                }
            }
        }
        return success;
    }


    public String formatDateTime(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String formatDate(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

}