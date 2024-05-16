package com.sddb.buss.riskdata.service.impl;

import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.buss.riskdata.service.HazardFactorsServiceI;
import com.sddb.common.Constants;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("hazardFactorsService")
@Transactional
public class HazardFactorsServiceImpl extends CommonServiceImpl implements HazardFactorsServiceI {

    @Autowired
    private SystemService systemService;

    @Override
    public void importDataSava(ExcelImportResult<HazardFactorsEntity> result) {

        List<HazardFactorsEntity> hazardFactorsEntityList = new ArrayList<>();
        for (int i = 0; i < result.getList().size(); i++) {
            HazardFactorsEntity hazardFactorsEntity = (HazardFactorsEntity)result.getList().get(i);
            hazardFactorsEntity.setIsDel(Constants.HAZARDFACTORS_IS_DEL_FALSE);
            hazardFactorsEntity.setMajor(DicUtil.getTypeCodeByName("major", hazardFactorsEntity.getMajorTemp()));
            hazardFactorsEntity.setRiskType(DicUtil.getTypeCodeByName("risk_type", hazardFactorsEntity.getRiskTypeTemp()));
            hazardFactorsEntity.setRiskLevel(DicUtil.getTypeCodeByName("factors_level", hazardFactorsEntity.getRiskLevelTemp()));
            hazardFactorsEntity.setFrom(Constants.HAZARDFACTORS_FROM_BASE);
            hazardFactorsEntity.setStatus(Constants.HAZARDFACTORS_STATUS_REVIEW);
            hazardFactorsEntityList.add(hazardFactorsEntity);
        }
        systemService.batchSave(hazardFactorsEntityList);
    }

    @Override
    public void getModularHazardRelList(HttpServletRequest request, DataGrid dataGrid) {
        String modularId = request.getParameter("modularId");
        String rel = request.getParameter("rel");
        String major = request.getParameter("major");
        String riskLevel = request.getParameter("riskLevel");
        String riskType = request.getParameter("riskType");
        List<Map<String,Object>> queryList = new ArrayList<>();
        if(StringUtil.isNotEmpty(modularId)){
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT harm.id,harm.risk_type, harm.post_name postName,harm.major, harm.riskLevel, harm.hazard_factors, harm.manage_measure,harm.create_date ");
            sb.append("FROM t_b_hazard_factors harm ");
            sb.append("WHERE harm.is_del = '0' AND harm.`status` = '3' AND ");
            if(StringUtil.isNotEmpty(major)){
                sb.append("harm.`major` = '"+major+"' AND ");
            }
            if(StringUtil.isNotEmpty(riskLevel)){
                sb.append("harm.`riskLevel` = '"+riskLevel+"' AND ");
            }
            if(StringUtil.isNotEmpty(riskType)){
                sb.append("harm.`risk_type` = '"+riskType+"' AND ");
            }
            if(StringUtil.isNotEmpty(rel) && rel.equals("noRel")){
                sb.append("harm.id not in(SELECT rel.hazard_id FROM t_b_hazard_module_rel rel WHERE rel.modular_id = '").append(modularId).append("' )  ");
            }else{
                sb.append("harm.id in (SELECT rel.hazard_id FROM t_b_hazard_module_rel rel WHERE rel.modular_id = '").append(modularId).append("' )  ");
            }
            sb.append(" ORDER BY create_date desc");
            queryList = this.commonDao.findForJdbc(sb.toString());
        }

        int currentPage = dataGrid.getPage();
        int pageSize = dataGrid.getRows();
        int endIndex = pageSize * currentPage;

        if(endIndex > queryList.size()){
            endIndex = queryList.size();
        }
        List<Map<String,Object>> resultList = queryList.subList(pageSize * (currentPage - 1), endIndex);
        dataGrid.setResults(resultList);
        dataGrid.setTotal(queryList.size());
    }

    @Override
    public AjaxJson hazardFactorsReportToGroup(String ids){
        AjaxJson j = new AjaxJson();
        JSONArray jsonArray = new JSONArray();
        for (String id : ids.split(",")) {
            JSONObject data = new JSONObject();
            HazardFactorsEntity hazardFactorsEntity = systemService.get(HazardFactorsEntity.class, id);
            if (hazardFactorsEntity != null){
                data.put("id", hazardFactorsEntity.getId());
                data.put("riskType", hazardFactorsEntity.getRiskType());
                data.put("major",hazardFactorsEntity.getMajor());
                data.put("hazardFactors", hazardFactorsEntity.getHazardFactors());
                data.put("manageMeasure", hazardFactorsEntity.getManageMeasure());
                data.put("postName", hazardFactorsEntity.getPostName());
                data.put("isDel", hazardFactorsEntity.getIsDel());
                data.put("from", hazardFactorsEntity.getFrom());
                data.put("riskLevel", hazardFactorsEntity.getRiskLevel());
                data.put("docSource", hazardFactorsEntity.getDocSource());
                data.put("sectionName", hazardFactorsEntity.getSectionName());
                data.put("activity", hazardFactorsEntity.getActivity());
                data.put("equipment", hazardFactorsEntity.getEquipment());
                jsonArray.add(data);
            }
        }

        if (jsonArray.size() > 0){
            //上传
            String mineCode = ResourceUtil.getConfigByName("mine_code");
            String token = ResourceUtil.getConfigByName("token_group");
            String reportContent = jsonArray.toString();
            String hazardFactorsReport = ResourceUtil.getConfigByName("hazardFactorsReport_group");
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
            LogUtil.info("hazardFactorsReport=" + hazardFactorsReport);
            LogUtil.info("jsonArray.size()=" + jsonArray.size() + ",reportContent="+reportContent.length());
            try {
                response = HttpClientUtils.post(hazardFactorsReport, paramMap, "UTF-8");
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
                    systemService.executeSql("update t_b_hazard_factors set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
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
