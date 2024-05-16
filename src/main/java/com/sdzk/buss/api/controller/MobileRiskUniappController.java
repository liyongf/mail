package com.sdzk.buss.api.controller;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.common.Constants;
import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.EhcacheUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope("prototype")
@Controller
@RequestMapping("/mobileRiskUniappController")
public class MobileRiskUniappController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiServiceI apiService;

    private String sessionCache = "sessionCache";

    //重大风险清单列表
    @RequestMapping("/majorRiskList")
    @ResponseBody
    public ApiResultJson majorRiskList(HttpServletRequest request) {
        try {
            String sessionId = request.getParameter("sessionId");
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            TSUser user = (TSUser) EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            ArrayList<HashMap> result = new ArrayList<>();
            DataGrid dataGrid = new DataGrid();
//            dataGrid.setPage(Integer.parseInt(page));
//            dataGrid.setRows(Integer.parseInt(rows));
            CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
            cq.eq("status", Constants.RISK_IDENTIFI_STATUS_REVIEW);
            cq.eq("isDel", Constants.RISK_IS_DEL_FALSE);
            cq.eq("riskLevel", "1");
            cq.add(Restrictions.sqlRestriction("this_.id in (select id from  t_b_risk_identification  where  identifi_date <= now()  and (exp_date >= now() or exp_date is null))"));

            cq.setCurPage(Integer.parseInt(page));
            cq.setPageSize(Integer.parseInt(rows));
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
                List<RiskIdentificationEntity> list = dataGrid.getResults();
                for (RiskIdentificationEntity bean : list) {
                    List<RiskFactortsRel> relList = bean.getRelList();
                    if (relList == null) {
                        bean.setHazardFactortsNum("0");
                    }
                    bean.setHazardFactortsNum(relList.size() + "");
                }
            }
            List<RiskIdentificationEntity> objList = dataGrid.getResults();
            for (RiskIdentificationEntity obj : objList) {
                HashMap map = CommonUtil.dealObjToMap(obj,null);
                if (null != obj.getAddress() && null != obj.getAddress().getAddress()) {
                    map.put("address", obj.getAddress().getAddress());
                } else {
                    map.put("address", "");
                }
                if (StringUtils.isNotBlank(obj.getRiskType())) {
                    map.put("riskType", DicUtil.getTypeNameByCode("risk_type", obj.getRiskType()));
                } else {
                    map.put("riskType", "");
                }
                if (StringUtils.isNotBlank(obj.getRiskLevel())) {
                    map.put("riskLevel", DicUtil.getTypeNameByCode("riskLevel", obj.getRiskLevel()));
                } else {
                    map.put("riskLevel", "");
                }

                result.add(map);
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("重大风险清单查询错误", e);
            return new ApiResultJson(ApiResultJson.CODE_500, ApiResultJson.CODE_500_MSG, null);
        }
    }

    //重大风险清单详情
    @RequestMapping("/majorRiskDetail")
    @ResponseBody
    public ApiResultJson majorRiskDetail(HttpServletRequest request) {
        try {
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            TSUser user = (TSUser) EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            RiskIdentificationEntity obj = this.systemService.get(RiskIdentificationEntity.class, id);
            List<RiskFactortsRel> relList = obj.getRelList();
            if (relList == null) {
                obj.setHazardFactortsNum("0");
            }
            obj.setHazardFactortsNum(relList.size() + "");

            HashMap map = CommonUtil.dealObjToMap(obj,dateFormat);
            if (null != obj.getAddress() && null != obj.getAddress().getAddress()) {
                map.put("address", obj.getAddress().getAddress());
            } else {
                map.put("address", "");
            }
            if (StringUtils.isNotBlank(obj.getRiskType())) {
                map.put("riskType", DicUtil.getTypeNameByCode("risk_type", obj.getRiskType()));
            } else {
                map.put("riskType", "");
            }
            if (StringUtils.isNotBlank(obj.getRiskLevel())) {
                map.put("riskLevel", DicUtil.getTypeNameByCode("riskLevel", obj.getRiskLevel()));
            } else {
                map.put("riskLevel", "");
            }
            if (null!=obj.getPost()) {
                map.put("post", obj.getPost().getPostName());
            } else {
                map.put("post", "");
            }
            if (null!=obj.getManageLevel()) {
                map.put("manageLevel", DicUtil.getTypeNameByCode("identifi_mange_level", obj.getManageLevel()));
            } else {
                map.put("manageLevel", "");
            }
            if (null!=obj.getIdentificationType()) {
                map.put("identificationType", DicUtil.getTypeNameByCode("identifi_from", obj.getIdentificationType()));
            } else {
                map.put("identificationType", "");
            }

            return new ApiResultJson(map);
        } catch (Exception e) {
            LogUtil.error("重大风险详情查询错误", e);
            return new ApiResultJson(ApiResultJson.CODE_500, ApiResultJson.CODE_500_MSG, null);
        }
    }

    //重大风险清单-危害因素列表
    @RequestMapping("/factorList")
    @ResponseBody
    public ApiResultJson factorList(HttpServletRequest request) {
        try {
            String sessionId = request.getParameter("sessionId");
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            String riskId = request.getParameter("riskId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            TSUser user = (TSUser) EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            ArrayList<HashMap> result = new ArrayList<>();
            DataGrid dataGrid = new DataGrid();
//            dataGrid.setPage(Integer.parseInt(page));
//            dataGrid.setRows(Integer.parseInt(rows));
            CriteriaQuery cq = new CriteriaQuery(RiskFactortsRel.class, dataGrid);
            cq.eq("riskIdentificationEntity.id",riskId);
            cq.setCurPage(Integer.parseInt(page));
            cq.setPageSize(Integer.parseInt(rows));
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            List<RiskFactortsRel> objList = dataGrid.getResults();
            for (RiskFactortsRel obj : objList) {
                HashMap map = CommonUtil.dealObjToMap(obj,null);
                if (null != obj.getHazardFactorsEntity()){
                    if (null != obj.getHazardFactorsEntity().getRiskType()) {
                        map.put("riskType",DicUtil.getTypeNameByCode("risk_type", obj.getHazardFactorsEntity().getRiskType()) );
                    } else {
                        map.put("riskType", "");
                    }
                    if (null != obj.getHazardFactorsEntity().getMajor()) {
                        map.put("major",DicUtil.getTypeNameByCode("major", obj.getHazardFactorsEntity().getMajor()) );
                    } else {
                        map.put("major", "");
                    }
                    if (null != obj.getHazardFactorsEntity().getHazardFactors()) {
                        map.put("hazardFactors", obj.getHazardFactorsEntity().getHazardFactors() );
                    } else {
                        map.put("hazardFactors", "");
                    }

                }
                if (null != obj.getHfLevel()) {
                    map.put("hfLevel",DicUtil.getTypeNameByCode("factors_level", obj.getHfLevel()) );
                } else {
                    map.put("hfLevel", "");
                }

                result.add(map);
            }


            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("危害因素查询错误", e);
            return new ApiResultJson(ApiResultJson.CODE_500, ApiResultJson.CODE_500_MSG, null);
        }
    }

    //风险点列表
    @RequestMapping("/riskAddressList")
    @ResponseBody
    public ApiResultJson riskAddressList(HttpServletRequest request) {
        try {
            String sessionId = request.getParameter("sessionId");
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            TSUser user = (TSUser) EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            ArrayList<HashMap> result = new ArrayList<>();
            DataGrid dataGrid = new DataGrid();
//            dataGrid.setPage(Integer.parseInt(page));
//            dataGrid.setRows(Integer.parseInt(rows));
            CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
            cq.eq("isDelete", com.sdzk.buss.web.common.Constants.IS_DELETE_N);
            cq.setCurPage(Integer.parseInt(page));
            cq.setPageSize(Integer.parseInt(rows));
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            List<TBAddressInfoEntity> objList = dataGrid.getResults();
            setDangerSourceCountData(objList);
            for (TBAddressInfoEntity obj : objList) {
                HashMap map = CommonUtil.dealObjToMap(obj,null);
                if (StringUtils.isNotBlank(obj.getRiskType())) {
                    map.put("riskType", DicUtil.getTypeNameByCode("risk_type", obj.getRiskType()));
                } else {
                    map.put("riskType", "");
                }

                result.add(map);
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("风险点查询错误", e);
            return new ApiResultJson(ApiResultJson.CODE_500, ApiResultJson.CODE_500_MSG, null);
        }
    }

    private void setDangerSourceCountData(List<TBAddressInfoEntity> tBAddressInfos) {
        if (tBAddressInfos  != null && tBAddressInfos.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (TBAddressInfoEntity entity : tBAddressInfos ) {
                if (StringUtil.isNotEmpty(ids.toString())) {
                    ids.append(",");
                }
                ids.append("'").append(entity.getId()).append("'") ;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sql = "select count(1) total, address_id from t_b_risk_identification where address_id in ("+ids.toString()+")  and status = '"+ Constants.RISK_IDENTIFI_STATUS_REVIEW +"' and is_del = '"+ Constants.RISK_IS_DEL_FALSE +"' and identifi_date <= '"+sdf.format(new Date())+"'  and (exp_date >= '"+sdf.format(new Date())+"' or exp_date is null)  GROUP BY address_id";

            List<Map<String, Object>> result = systemService.findForJdbc(sql);
            Map<String, String> countMap = new HashMap<>();
            if (result != null && result.size() > 0) {
                for (Map<String, Object> obj : result) {
                    if (obj.get("total") != null && obj.get("address_id") != null) {
                        countMap.put(obj.get("address_id").toString(), obj.get("total").toString());
                    }
                }
            }
            for (TBAddressInfoEntity entity : tBAddressInfos ) {
                entity.setDangerSourceCount(countMap.get(entity.getId())!=null?countMap.get(entity.getId()):"0");
                if(StringUtil.isNotEmpty(entity.getInvestigationDate())&&StringUtil.isNotEmpty(entity.getStartDate())
                        &&StringUtil.isNotEmpty(entity.getEndDate())){
                    entity.setIsAll("0");
                }else{
                    entity.setIsAll("1");
                }
            }
        }
    }

    //风险点-风险数量列表
    @RequestMapping("/riskCountList")
    @ResponseBody
    public ApiResultJson riskCountList(HttpServletRequest request) {
        try {
            String sessionId = request.getParameter("sessionId");
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            String addressId = request.getParameter("addressId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            TSUser user = (TSUser) EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            ArrayList<HashMap> result = new ArrayList<>();
            DataGrid dataGrid = new DataGrid();
//            dataGrid.setPage(Integer.parseInt(page));
//            dataGrid.setRows(Integer.parseInt(rows));
            CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
            cq.eq("address.id",addressId);
            cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
            cq.eq("status",Constants.RISK_IDENTIFI_STATUS_REVIEW);
            cq.setCurPage(Integer.parseInt(page));
            cq.setPageSize(Integer.parseInt(rows));
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            List<RiskIdentificationEntity> objList = dataGrid.getResults();
            for (RiskIdentificationEntity obj : objList) {
                HashMap map = CommonUtil.dealObjToMap(obj,null);
                if (null != obj.getAddress() && null != obj.getAddress().getAddress()) {
                    map.put("address", obj.getAddress().getAddress());
                } else {
                    map.put("address", "");
                }
                if (StringUtils.isNotBlank(obj.getRiskType())) {
                    map.put("riskType", DicUtil.getTypeNameByCode("risk_type", obj.getRiskType()));
                } else {
                    map.put("riskType", "");
                }
                if (StringUtils.isNotBlank(obj.getRiskLevel())) {
                    map.put("riskLevel", DicUtil.getTypeNameByCode("riskLevel", obj.getRiskLevel()));
                } else {
                    map.put("riskLevel", "");
                }
                if (null!=obj.getManageLevel()) {
                    map.put("manageLevel", DicUtil.getTypeNameByCode("identifi_mange_level", obj.getManageLevel()));
                } else {
                    map.put("manageLevel", "");
                }

                result.add(map);
            }


            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("风险查询错误", e);
            return new ApiResultJson(ApiResultJson.CODE_500, ApiResultJson.CODE_500_MSG, null);
        }
    }

}
