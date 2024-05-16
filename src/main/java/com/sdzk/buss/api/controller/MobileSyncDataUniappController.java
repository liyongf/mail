package com.sdzk.buss.api.controller;

import com.sddb.common.Constants;
import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.EhcacheUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope("prototype")
@Controller
@RequestMapping("/mobileSyncDataUniappController")
public class MobileSyncDataUniappController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiServiceI apiService;

    private String sessionCache = "sessionCache";

    //缓存基础数据
    @RequestMapping("/syncBasicData")
    @ResponseBody
    public ApiResultJson syncBasicData(HttpServletRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String sessionId = request.getParameter("sessionId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isBlank(sessionId)) {
            return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
        }
        TSUser user = (TSUser) EhcacheUtil.get(sessionCache, sessionId);
        if (user == null) {
            return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
        }
        Map<String,Object> object = new HashMap();
        /**同步数据字典**/
        List<Map<String, Object>> dicList = apiService.mobileSyncDataDic();
        object.put("dicList",dicList);
        /**同步用户信息**/
        List<Map<String, Object>> userList = apiService.mobileSyncDataUser();
        object.put("userList",userList);
        /**同步部门信息**/
        List<Map<String, Object>> departList = apiService.mobileSyncDataDepart();
        object.put("departList",departList);
        /**同步用户部门关系信息**/
        List<Map<String, Object>> userOrgList = apiService.mobileSyncDataUserOrg();
        object.put("userOrgList",userOrgList);
        /**同步地点信息**/
       /* List<Map<String, Object>> locationList = apiService.mobileSyncDataLocation();
        object.put("locationList",locationList);*/
        //人员菜单
        String getAppFunctionsSql = "SELECT id,functioncode as menuNum from t_s_app_function WHERE id in ( SELECT functionid FROM t_s_app_role_function WHERE roleid in (SELECT roleid FROM t_s_role_user WHERE userid='"+user.getId()+"')) ORDER BY functioncode";
        List<Map<String, Object>> appFunctionsList = systemService.findForJdbc(getAppFunctionsSql);
        object.put("userMenuList",appFunctionsList);
        //重大风险点清单列表
        StringBuffer riskIdentificationIds = new StringBuffer();
        String majorRiskSql = " select \n" +
                "t1.id,t2.address,t1.risk_type riskType,t1.risk_level riskLevel,\n" +
                "(select count(1) from t_b_risk_factors_rel where risk_identification_id = t1.id)  hazardFactortsNum,\n" +
                "t3.post_name post,t1.risk_desc riskDesc,t1.manage_level manageLevel,t1.duty_manager dutyManager,DATE_FORMAT(t1.identifi_date,'%Y-%m-%d') identifiDate,DATE_FORMAT(t1.exp_date,'%Y-%m-%d') expDate,t1.identification_type identificationType\n" +
                "from t_b_risk_identification t1\n" +
                "left join t_b_address_info t2 on t1.address_id = t2.id\n" +
                "left join t_b_post_manage t3 on t1.post_id = t3.id " +
                "where status = '"+Constants.RISK_IDENTIFI_STATUS_REVIEW+"' and is_del = '"+Constants.RISK_IS_DEL_FALSE+"' and risk_level = '1' ";
        List<Map<String, Object>> majorRiskList = systemService.findForJdbc(majorRiskSql);
        for (Map<String, Object> map : majorRiskList) {
            if (null!=map.get("riskType")&&StringUtils.isNotBlank(map.get("riskType").toString())) {
                map.put("riskType", DicUtil.getTypeNameByCode("risk_type", map.get("riskType").toString()));
            } else {
                map.put("riskType", "");
            }
            if (null!=map.get("riskLevel")&&StringUtils.isNotBlank(map.get("riskLevel").toString())) {
                map.put("riskLevel", DicUtil.getTypeNameByCode("riskLevel", map.get("riskLevel").toString()));
            } else {
                map.put("riskLevel", "");
            }
            if (null!=map.get("post")&&StringUtils.isNotBlank(map.get("post").toString())) {
                map.put("post", map.get("post").toString());
            } else {
                map.put("post", "");
            }
            if (null!=map.get("manageLevel")&&StringUtils.isNotBlank(map.get("manageLevel").toString())) {
                map.put("manageLevel", DicUtil.getTypeNameByCode("identifi_mange_level", map.get("manageLevel").toString()));
            } else {
                map.put("manageLevel", "");
            }
            if (null!=map.get("identificationType")&&StringUtils.isNotBlank(map.get("identificationType").toString())) {
                map.put("identificationType", DicUtil.getTypeNameByCode("identifi_from", map.get("identificationType").toString()));
            } else {
                map.put("identificationType", "");
            }

            if (riskIdentificationIds.toString().length()==0){
                riskIdentificationIds.append("'"+map.get("id")+"'");
            }else {
                riskIdentificationIds.append(",'"+map.get("id")+"'");
            }
        }
        object.put("majorRiskList",majorRiskList);
        //重大风险点清单-危害因素列表
        List<Map<String, Object>> majorRiskFactorList = new ArrayList<>();
        if (riskIdentificationIds.toString().length()>0){
            String majorRiskFactorSql = " select \n" +
                    "t1.id,t2.risk_type riskType,t2.major,t2.post_name postName,t2.hazard_factors hazardFactors,t1.hfManageMeasure,t1.hfLevel,t1.risk_identification_id riskIdentificationId\n" +
                    "from t_b_risk_factors_rel t1\n" +
                    "left join t_b_hazard_factors t2 on t1.hazard_factors_id = t2.id" +
                    "   where risk_identification_id in ("+riskIdentificationIds.toString()+") ";
            majorRiskFactorList = systemService.findForJdbc(majorRiskFactorSql);
            for (Map<String, Object> map : majorRiskFactorList) {
                if (null!=map.get("riskType")&&StringUtils.isNotBlank(map.get("riskType").toString())) {
                    map.put("riskType",DicUtil.getTypeNameByCode("risk_type", map.get("riskType").toString()) );
                } else {
                    map.put("riskType", "");
                }
                if (null!=map.get("major")&&StringUtils.isNotBlank(map.get("major").toString())) {
                    map.put("major",DicUtil.getTypeNameByCode("major", map.get("major").toString()) );
                } else {
                    map.put("major", "");
                }
                if (null!=map.get("hazardFactors")&&StringUtils.isNotBlank(map.get("hazardFactors").toString())) {
                    map.put("hazardFactors", map.get("hazardFactors").toString() );
                } else {
                    map.put("hazardFactors", "");
                }
                if (null!=map.get("hfLevel")&&StringUtils.isNotBlank(map.get("hfLevel").toString())) {
                    map.put("hfLevel",DicUtil.getTypeNameByCode("factors_level", map.get("hfLevel").toString()) );
                } else {
                    map.put("hfLevel", "");
                }
            }
        }
        object.put("majorRiskFactorList",majorRiskFactorList);

        //风险点列表
        StringBuffer addressInfoIds = new StringBuffer();
        String addressInfoSql = " select t1.id,t1.address,(select count(1) from t_b_risk_identification where address_id = t1.id and status = '"+ Constants.RISK_IDENTIFI_STATUS_REVIEW +"' and is_del = '"+ Constants.RISK_IS_DEL_FALSE +"' and identifi_date <= '"+sdf.format(new Date())+"'  and (exp_date >= '"+sdf.format(new Date())+"' or exp_date is null)) dangerSourceCount,t1.manage_man manageMan " +
                " from t_b_address_info t1 where t1.is_delete = '0'  ";
        List<Map<String, Object>> addressInfoList = systemService.findForJdbc(addressInfoSql);
        object.put("addressInfoList",addressInfoList);

        if (CollectionUtils.isNotEmpty(addressInfoList)){
            for (Map<String, Object> map : addressInfoList) {
                if (addressInfoIds.toString().length()==0){
                    addressInfoIds.append("'"+map.get("id")+"'");
                }else {
                    addressInfoIds.append(",'"+map.get("id")+"'");
                }
            }
        }
        //风险点列表-风险数量列表
        String riskCountSql = " SELECT\n" +
                "\tid,\n" +
                "\trisk_type riskType,\n" +
                "\trisk_level riskLevel ,\n" +
                "\trisk_desc riskDesc,\n" +
                "\tmanage_level manageLevel,\n" +
                "\tduty_manager dutyManager,\n" +
                "\taddress_id addressId\n" +
                "FROM\n" +
                "\tt_b_risk_identification \n" +
                "WHERE\n" +
                "\tSTATUS = '" +Constants.RISK_IDENTIFI_STATUS_REVIEW+"' "+
                "\tAND is_del = '0'  and address_id in ("+addressInfoIds.toString()+")" +
                "  and identifi_date <= '"+sdf.format(new Date())+"'  and (exp_date >= '"+sdf.format(new Date())+"' or exp_date is null)  ";
        List<Map<String, Object>> riskCountList = systemService.findForJdbc(riskCountSql);
        for (Map<String, Object> map : riskCountList) {
            if (null!=map.get("riskType")&&StringUtils.isNotBlank(map.get("riskType").toString())) {
                map.put("riskType", DicUtil.getTypeNameByCode("risk_type", map.get("riskType").toString()));
            } else {
                map.put("riskType", "");
            }
            if (null!=map.get("riskLevel")&&StringUtils.isNotBlank(map.get("riskLevel").toString())) {
                map.put("riskLevel", DicUtil.getTypeNameByCode("riskLevel", map.get("riskLevel").toString()));
            } else {
                map.put("riskLevel", "");
            }
            if (null!=map.get("manageLevel")&&StringUtils.isNotBlank(map.get("manageLevel").toString())) {
                map.put("manageLevel", DicUtil.getTypeNameByCode("identifi_mange_level", map.get("manageLevel").toString()));
            } else {
                map.put("manageLevel", "");
            }
        }
        object.put("riskCountList",riskCountList);
        /** 隐患-我的任务列表 **/
        List<Map<String, Object>> hiddenTaskList = apiService.mobileSyncDataHiddenTask(user);
        object.put("hiddenTaskList",hiddenTaskList);
        /** 同步隐患-待上报隐患数据 **/
        List<Map<String, Object>> hiddenList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(hiddenTaskList)){
            StringBuffer examIds = new StringBuffer();
            for (Map<String, Object> map:hiddenTaskList) {
                examIds.append(examIds.toString().length()==0?"'"+map.get("id")+"'":",'"+map.get("id")+"'");
            }
            hiddenList = apiService.mobileSyncDataNotUploadHiddenTask(user,examIds.toString());
            for (Map<String, Object> map:hiddenList) {
                if (null!=map.get("infoSource")&& StringUtil.isNotEmpty(map.get("infoSource").toString())){
                    map.put("infoSourceName",DicUtil.getTypeNameByCode("manageType", map.get("infoSource").toString()));
                }
            }
        }
        object.put("hiddenList",hiddenList);

        return new ApiResultJson(object);
    }

}
