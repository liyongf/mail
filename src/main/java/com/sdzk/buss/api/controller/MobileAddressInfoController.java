package com.sdzk.buss.api.controller;

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.EhcacheUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @user hanxudong
 * 查询风险点数据
 */
@Controller
@RequestMapping("/mobile/mobileAddressInfoController")
public class MobileAddressInfoController {
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
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            List<Map<String, Object>> result=new ArrayList<>();
            CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
            cq.eq("isDelete",Constants.IS_DELETE_N);
            cq.add();
            List<TBAddressInfoEntity> tempList = systemService.getListByCriteriaQuery(cq, true);
            for(int i = 0 ; i < tempList.size() ; i++) {
                TBAddressInfoEntity aie = tempList.get(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id",aie.getId());
                map.put("address",aie.getAddress());

                //返回风险点上关联的风险级别，风险数量，危险源（数量，内容），责任单位（数量，内容）
                map.put("riskLevel","");
                String riskLevelSql = "select risk_level from t_b_danger_address_rel where address_id='"+aie.getId()+"' and risk_level is not null " + " and EXISTS(select * from t_b_danger_source ds where ds.id=danger_id and ds.is_delete='0' and ds.audit_status='4') ORDER BY risk_level asc limit 1";
                List<String> riskLevelList = systemService.findListbySql(riskLevelSql);
                if(!riskLevelList.isEmpty() && riskLevelList.size()>0){
                    String riskLevelName = DicUtil.getTypeNameByCode("riskLevel", riskLevelList.get(0));
                    if(StringUtil.isNotEmpty(riskLevelName)){
                        map.put("riskLevel",riskLevelName);
                    }
                }

                //风险数量
                String dsAddressSql = "select count(distinct ds.id) from t_b_danger_address_rel rel, t_b_danger_source ds\n" +
                        "where ds.id=rel.danger_id and ds.is_delete!='1' and ds.audit_status='4' and rel.address_id='" + aie.getId() + "'";
                Long riskNum = systemService.getCountForJdbc(dsAddressSql);
                map.put("riskNum",riskNum+"");

                //危险源（数量，内容）
                String hazardSql = "select DISTINCT hazard.id id,hazard.hazard_name hazardName,map.typename hazardType from t_b_danger_address_rel rel, t_b_danger_source ds\n" +
                        "left join t_b_hazard_manage hazard on ds.hazard_manage_id=hazard.id \n" +
                        "left join (\n" +
                        "\tselect t.typecode, t.typename from t_s_type t, t_s_typegroup g where t.typegroupid=g.id and g.typegroupcode='dangerSource_type'\n" +
                        ")map on hazard.hazard_type = map.typecode\n" +
                        "where ds.id=rel.danger_id and ds.is_delete!='1' and ds.audit_status='4'\n" +
                        "and rel.address_id='" + aie.getId() + "'";
                List<Map<String,Object>> hazardList = systemService.findForJdbc(hazardSql);
                map.put("hazardNum",hazardList.size()+"");
                map.put("hazards",hazardList);

                //责任单位（数量，内容）
                String hazardDeptSql = "select d.id id, d.departname departName, rel.duty_man dutyMan from t_b_address_depart_rel rel, t_s_depart d\n" +
                        "where rel.depart_id=d.ID and d.delete_flag='0'\n" +
                        "and rel.address_id='" + aie.getId() + "'";
                List<Map<String,Object>> hazardDeptList = systemService.findForJdbc(hazardDeptSql);
                map.put("departNum",hazardDeptList.size()+"");
                map.put("departs",hazardDeptList);

                result.add(map);
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
                LogUtil.error("风险点列表查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    /**
     *
     * @param token
     * @param sessionId     用户当前登录的sessionid
     * @param page          当前页数
     * @param rows          查询行数
     * @return
     */
    @RequestMapping("/addressList")
    @ResponseBody
    public ApiResultJson addressList(String token, HttpServletRequest request, DataGrid dataGrid){
        //TODO TOKEN验证
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            List<Map<String, Object>> result=new ArrayList<>();
            CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class, dataGrid);
            cq.eq("isDelete",Constants.IS_DELETE_N);
            cq.add();
            List<TBAddressInfoEntity> tempList = systemService.getListByCriteriaQuery(cq, true);
            for(int i = 0 ; i < tempList.size() ; i++) {
                TBAddressInfoEntity aie = tempList.get(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id",aie.getId());
                map.put("address",aie.getAddress());
                if(StringUtil.isNotEmpty(aie.getManageMan())){
                    map.put("manageMan",aie.getManageMan());
                }else{
                    map.put("manageMan","");
                }


                //风险数量
                String riskAddressSql = "SELECT count(id) FROM t_b_risk_identification WHERE address_id = '"+aie.getId()+"' and status = '3' and is_del = '0' ";
                Long riskNum = systemService.getCountForJdbc(riskAddressSql);
                map.put("riskNum",riskNum+"");

                String riskSql = "SELECT ri.risk_type riskType, ri.risk_desc riskDesc, ri.risk_level riskLevel, ri.manage_level manageLevel, ri.duty_manager dutyManager, DATE_FORMAT( ri.identifi_date, '%Y-%m-%d' ) identifiDate, DATE_FORMAT(ri.exp_date, '%Y-%m-%d') expDate, ri.identification_type identificationType FROM t_b_risk_identification ri WHERE address_id = '"+aie.getId()+"' AND STATUS = '3' and is_del = '0' ";
                List<Map<String,Object>> riskList  = systemService.findForJdbc(riskSql);
                if (riskList != null && riskList.size() > 0) {
                    for (Map<String, Object> temp: riskList) {
                        temp.put("riskType", DicUtil.getTypeNameByCode("risk_type", (String) temp.get("riskType")));
                        temp.put("riskLevel", DicUtil.getTypeNameByCode("factors_level", (String) temp.get("riskLevel")));
                        temp.put("manageLevel", DicUtil.getTypeNameByCode("identifi_mange_level", (String) temp.get("manageLevel")).equals("")?(String) temp.get("manageLevel"):DicUtil.getTypeNameByCode("identifi_mange_level", (String) temp.get("manageLevel")));
                        temp.put("identificationType", DicUtil.getTypeNameByCode("identifi_from", (String) temp.get("identificationType")));
                    }
                }
                map.put("riskList",riskList);
                result.add(map);
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("风险点列表查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


    /**
     *
     * @param token
     * @param sessionId     用户当前登录的sessionid
     * @return
     */
    @RequestMapping("/listAll")
    @ResponseBody
    public ApiResultJson listAll(String token, HttpServletRequest request, DataGrid dataGrid){
        //TODO TOKEN验证
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            List<Map<String, Object>> result=new ArrayList<>();
            CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
            cq.eq("isDelete",Constants.IS_DELETE_N);
            cq.add();
            List<TBAddressInfoEntity> tempList = systemService.getListByCriteriaQuery(cq, false);
            for(int i = 0 ; i < tempList.size() ; i++) {
                TBAddressInfoEntity aie = tempList.get(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id",aie.getId());
                map.put("address",aie.getAddress());
                result.add(map);
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("风险点全部列表查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

}