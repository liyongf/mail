package com.sdzk.buss.api.controller;

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TbHazardManageEntity;
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
 * 查询危险源数据
 */
@Controller
@RequestMapping("/mobile/mobileHazardManageController")
public class MobileHazardManageController {
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
        String addressId = request.getParameter("addressId");//是否根据风险点关联查询危险源
        String hazardName = request.getParameter("hazardName");//是否根据危险源名称查询危险源
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
            CriteriaQuery cq = new CriteriaQuery(TbHazardManageEntity.class, dataGrid);
            cq.eq("isDelete",Constants.IS_DELETE_N);
            if(StringUtil.isNotEmpty(addressId)){
                cq.add(Restrictions.sqlRestriction(" this_.id in (select DISTINCT ds.hazard_manage_id from t_b_danger_address_rel address, t_b_danger_source ds where address.danger_id=ds.id and address.address_id='" + addressId + "')"));
            }
            if(StringUtil.isNotEmpty(hazardName)){
                cq.like("hazardName", "%"+hazardName+"%");
            }
            cq.add();
            List<TbHazardManageEntity> tempList = systemService.getListByCriteriaQuery(cq, true);
            for(int i = 0 ; i < tempList.size() ; i++) {
                TbHazardManageEntity hme = tempList.get(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id",hme.getId());
                map.put("hazardName",hme.getHazardName());
                map.put("hazardType",DicUtil.getTypeNameByCode("dangerSource_type", hme.getHazardType()));

                String dsNumStr = "select count(*) from t_b_danger_source ds where ds.audit_status='4' and ds.is_delete='0' and hazard_manage_id='" +hme.getId() + "'" ;
                Long dsNum = systemService.getCountForJdbc(dsNumStr);
                map.put("riskNum",dsNum+"");
                result.add(map);
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
                LogUtil.error("风险点列表查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


}