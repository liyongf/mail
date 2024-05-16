package com.sdzk.buss.api.controller;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.dsmanagerecord.entity.TBDsManageRecordEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @user hanxudong
 * 查询风险数据
 */
@Controller
@RequestMapping("/mobile/mobileDangerSourceController")
public class MobileDangerSourceController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiServiceI apiService;

    private String sessionCache="sessionCache";


    /**
     *
     * @param token
     * @param sessionId     用户当前登录的sessionid
     * @param page          当前页数
     * @param rows          查询行数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ApiResultJson list(String token, HttpServletRequest request, DataGrid dataGrid){
        //TODO TOKEN验证
        String isMajor = request.getParameter("isMajor");//是否查询重大风险
        String addressId = request.getParameter("addressId");//是否查询风险点关联的风险
        String hazardId = request.getParameter("hazardId");//是否查询风险点关联的风险
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            List<Map<String, Object>> result= new ArrayList<>();
            CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
            if(Constants.IS_MAJORDangerSource_Y.equals(isMajor)){
                cq.eq("isMajor",Constants.IS_MAJORDangerSource_Y);
                cq.notEq("origin", Constants.DANGER_SOURCE_ORIGIN_NOMAL);
            }
            cq.eq("isDelete",Constants.IS_DELETE_N);
            cq.eq("auditStatus",Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            if(StringUtil.isNotEmpty(addressId)){
                cq.add(Restrictions.sqlRestriction(" this_.id in (select address.danger_id from t_b_danger_address_rel address where address.address_id='"+addressId+"')"));
            }
            if(StringUtil.isNotEmpty(hazardId)){
                cq.eq("hazard.id",hazardId);
            }
            cq.add();
            //DataGridReturn dataGridReturn = systemService.getDataGridReturn(cq, true);
            List<TBDangerSourceEntity> tempList = systemService.getListByCriteriaQuery(cq, true);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(int i = 0 ; i < tempList.size() ; i++) {
                TBDangerSourceEntity dse = tempList.get(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id",dse.getId());
                map.put("yeRecognizeTime",sdf.format(dse.getYeRecognizeTime()));
                map.put("yeMhazardDesc",dse.getYeMhazardDesc());
                map.put("yeProfession",DicUtil.getTypeNameByCode("proCate_gradeControl", dse.getYeProfession()));
                map.put("hazardName",dse.getHazard().getHazardName());
                map.put("activityName",dse.getActivity().getActivityName());
                map.put("damageType",DicUtil.getTypeNameByCode("danger_Category", dse.getDamageType()));
                map.put("yePossiblyHazard",dse.getYePossiblyHazard());
                map.put("yeAccident",dse.getYeAccident());
                String sgxlStr = dse.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    map.put("yeAccident", sb.toString());
                }
                map.put("yeRiskGrade",DicUtil.getTypeNameByCode("riskLevel", dse.getYeRiskGrade()));
                map.put("yeHazardCate",DicUtil.getTypeNameByCode("hazardCate", dse.getYeHazardCate()));
                map.put("docSource",dse.getDocSource());
                map.put("sectionName",dse.getSectionName());
                map.put("yeStandard",dse.getYeStandard());
                map.put("manageMeasure",dse.getManageMeasure());
                map.put("postName",dse.getPost().getPostName());
                map.put("hiddenLevel",DicUtil.getTypeNameByCode("hiddenLevel", dse.getHiddenLevel()));
                if(StringUtil.isNotEmpty(dse.getFineMoney())){
                    map.put("fineMoney",dse.getFineMoney());
                }else
                    map.put("fineMoney","");
                if(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT.equals(dse.getAuditStatus()))
                    map.put("auditStatus","待上报");
                else if(Constants.DANGER_SOURCE_AUDITSTATUS_REVIEW.equals(dse.getAuditStatus()))
                    map.put("auditStatus","审核中");
                else if(Constants.DANGER_SOURCE_AUDITSTATUS_ROLLBANK.equals(dse.getAuditStatus()))
                    map.put("auditStatus","审核退回");
                else if(Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE.equals(dse.getAuditStatus()))
                    map.put("auditStatus","闭环");
                else map.put("auditStatus",dse.getAuditStatus());


                //如果是重大风险，则需要返回风险点和管控记录
                if(Constants.IS_MAJORDangerSource_Y.equals(isMajor)){
                    String dsAddressSql = "select address.id, address.address from t_b_danger_address_rel rel, t_b_address_info address where rel.address_id=address.id ";
                    dsAddressSql = dsAddressSql + " and rel.danger_id='" + dse.getId() + "'";
                    dsAddressSql = dsAddressSql + " and address.is_delete!='1'";
                    List<Map<String,Object>> addressList = systemService.findForJdbc(dsAddressSql);
                    /////
                    /*List<Map<String,Object>> tmpList = new ArrayList<>();
                    for(int k=0;k<addressList.size();k++){
                        tmpList.add(addressList.get(k));
                    }
                    int sizeList = addressList.size();
                    int k=0;
                    while(addressList.size()<200){
                        k=k%sizeList;
                        addressList.add(tmpList.get(k));
                        k++;
                    }
*/
                    /////
                    map.put("addressNum",addressList.size());
                    map.put("addresses",addressList);

                    List<TBDsManageRecordEntity> dsManageRecordList = systemService.findByProperty(TBDsManageRecordEntity.class, "dangerId", dse.getId());
                    List<Map<String,String>> dsManageRecordMapList = new ArrayList<>();
                    for(int j=0;j<dsManageRecordList.size();j++){
                        TBDsManageRecordEntity dsRecord = dsManageRecordList.get(j);
                        Map<String, String> dsRecordMap = new HashMap<String, String>();
                        dsRecordMap.put("id",dsRecord.getId());
                        dsRecordMap.put("controller","");
                        String controller = dsRecord.getController();
                        if(StringUtil.isNotEmpty(controller)){
                            TSUser controlerUser = systemService.getEntity(TSUser.class,controller);
                            if(null!=controlerUser){
                                dsRecordMap.put("controller",controlerUser.getRealName());
                            }
                        }
                        dsRecordMap.put("controlleDate",sdf.format(dsRecord.getControlleDate()));
                        dsRecordMap.put("workContent",dsRecord.getWorkContent());
                        dsRecordMap.put("achieveEffect",dsRecord.getAchieveEffect());
                        dsManageRecordMapList.add(dsRecordMap);
                    }
                    map.put("manageRecordNum", dsManageRecordMapList.size()+"");
                    map.put("manageRecords",dsManageRecordMapList);
                }

                result.add(map);
            }

            return new ApiResultJson(result);
        } catch (Exception e) {
            if(Constants.IS_MAJORDangerSource_Y.equals(isMajor))
                LogUtil.error("重大风险列表查询错误",e);
            else
                LogUtil.error("风险列表查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    @RequestMapping("/isMajorList")
    @ResponseBody
    public ApiResultJson isMajorList(String token, HttpServletRequest request, DataGrid dataGrid){
        try {
            String sessionId = request.getParameter("sessionId");
            String addressId = request.getParameter("addressId");
            //李楼的二维码扫描依据
            String ewmgn=ResourceUtil.getConfigByName("ewmgn");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            List<Map<String, Object>> result= new ArrayList<>();
            CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
            if("true".equals(ewmgn) && StringUtils.isNotBlank(addressId)){
                cq.createAlias("address","address");
                cq.eq("address.id",addressId);
            }else {
                cq.eq("riskLevel","1");
            }
            cq.eq("status","3");
            cq.eq("isDel","0");
            cq.add();
            if("true".equals(ewmgn) && StringUtils.isNotBlank(addressId)){
               cq.addOrder("riskLevel", SortDirection.asc);
            }
            List<RiskIdentificationEntity> tempList = systemService.getListByCriteriaQuery(cq, true);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(int i = 0 ; i < tempList.size() ; i++) {
                RiskIdentificationEntity riskIdentificationEntity = tempList.get(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id",riskIdentificationEntity.getId());
                map.put("address",riskIdentificationEntity.getAddress()==null?"":riskIdentificationEntity.getAddress().getAddress());
                map.put("postName",riskIdentificationEntity.getPost()==null?"":riskIdentificationEntity.getPost().getPostName());
                map.put("riskType",DicUtil.getTypeNameByCode("risk_type", riskIdentificationEntity.getRiskType()));
                map.put("riskDesc",riskIdentificationEntity.getRiskDesc());
                map.put("riskLevel",DicUtil.getTypeNameByCode("factors_level", riskIdentificationEntity.getRiskLevel()));
                map.put("manageLevel",DicUtil.getTypeNameByCode("identifi_mange_level", riskIdentificationEntity.getManageLevel()).equals("")?riskIdentificationEntity.getManageLevel():DicUtil.getTypeNameByCode("identifi_mange_level", riskIdentificationEntity.getManageLevel()));
                map.put("dutyManager",riskIdentificationEntity.getDutyManager());
                if(riskIdentificationEntity.getIdentifiDate()!=null){
                    map.put("identifiDate",sdf.format(riskIdentificationEntity.getIdentifiDate()));
                }else{
                    map.put("identifiDate","");
                }
                if(riskIdentificationEntity.getExpDate()!=null){
                    map.put("expDate",sdf.format(riskIdentificationEntity.getExpDate()));
                }else{
                    map.put("expDate","");
                }
                map.put("identificationType",DicUtil.getTypeNameByCode("identifi_from", riskIdentificationEntity.getIdentificationType()));
                if(StringUtil.isNotEmpty((String) map.get("id"))){
                    RiskIdentificationEntity bean =systemService.getEntity(RiskIdentificationEntity.class,(String) map.get("id"));
                    List<RiskFactortsRel> relList = bean.getRelList();
                    map.put("hazardFactortsNum", relList.size() + "");
                }
                String hazardFactortsSql = "SELECT hf.risk_type riskType, hf.major major, hf.post_name postName, hf.hazard_factors hazardFactors, rfr.hfManageMeasure hfManageMeasure, rfr.hfLevel hfLevel, IFNULL(d.departname,'') manageDepartName,IFNULL(b.realname,'')  manageUserName FROM t_b_risk_factors_rel rfr LEFT JOIN t_b_hazard_factors hf ON rfr.hazard_factors_id = hf.id LEFT JOIN t_s_depart d ON rfr.manage_depart = d.id LEFT JOIN t_s_base_user b ON rfr.manage_user = b.id WHERE rfr.risk_identification_id = '"+(String) map.get("id")+"'";
                List<Map<String,Object>> hazardFactortsList  = systemService.findForJdbc(hazardFactortsSql);
                if (hazardFactortsList != null && hazardFactortsList.size() > 0) {
                    for (Map<String, Object> temp: hazardFactortsList) {
                        temp.put("riskType", DicUtil.getTypeNameByCode("risk_type", (String) temp.get("riskType")));
                        temp.put("major", DicUtil.getTypeNameByCode("major", (String) temp.get("major")));
                        temp.put("hfLevel", DicUtil.getTypeNameByCode("factors_level", (String) temp.get("hfLevel")));
                    }
                }
                map.put("hazardFactorts",hazardFactortsList);
                result.add(map);
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("风险列表查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

}