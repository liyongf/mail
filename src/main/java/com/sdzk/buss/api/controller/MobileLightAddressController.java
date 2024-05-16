package com.sdzk.buss.api.controller;

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.lightAddress.entity.TBAccessRecordEntity;
import com.sdzk.buss.web.lightAddress.entity.TBLightAddressEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.EhcacheUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @user hanxudong
 * 路径数据采集
 */
@Controller
@RequestMapping("/mobile/mobileLightAddressController")
public class MobileLightAddressController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiServiceI apiService;

    private String sessionCache="sessionCache";

    /**
     * 上报灯和风险点对应关系，下井检查途经数据接口
     * @param token
     * @param sessionId         用户当前登录的sessionid
     * @param reportContent
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ApiResultJson add(String token, String reportContent, HttpServletRequest request){
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
/*            String bussId = request.getParameter("bussId");
            if(StringUtil.isEmpty(bussId)){
                return new ApiResultJson(ApiResultJson.CODE_400_MSG,"缺少下井标识",null);
            }
            List<TBAccessRecordEntity> accessRecordList = this.systemService.findByProperty(TBAccessRecordEntity.class,"bussId",bussId);
            if(null==accessRecordList){
                return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
            }
            if(accessRecordList.size()>0){
                return new ApiResultJson(ApiResultJson.CODE_201,ApiResultJson.CODE_201_MSG,null);
            }*/

            JSONArray jsonArray = JSONArray.fromObject(reportContent);
            List<String> successIdsList = new ArrayList<String>();
            if (jsonArray !=null && jsonArray.size() > 0) {
                List<TBAccessRecordEntity> accessRecordEntityList = new ArrayList<>();
                List<TBLightAddressEntity> lightAddressEntityAddList = new ArrayList<>();
                List<TBLightAddressEntity> lightAddressEntityUpdateList = new ArrayList<>();
                for (int i=0; i<jsonArray.size(); i++) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    JSONObject object = jsonArray.getJSONObject(i);

                    //更新灯和风险点的对应关系
                    String lightId = object.getString("lightId");
                    String addressId = object.getString("addressId");
                    List<TBLightAddressEntity> lightAddressEntityListTmp = this.systemService.findByProperty(TBLightAddressEntity.class, "lightId", lightId);
                    if(null==lightAddressEntityListTmp || 0==lightAddressEntityListTmp.size()) {
                        TBLightAddressEntity lightAddressEntity = new TBLightAddressEntity();
                        lightAddressEntity.setLightId(lightId);
                        lightAddressEntity.setAddressId(addressId);
                        lightAddressEntity.setCreateBy(user.getUserName());
                        lightAddressEntity.setCreateName(user.getRealName());
                        lightAddressEntity.setCreateDate(new Date());
                        systemService.saveOrUpdate(lightAddressEntity);
                        //lightAddressEntityAddList.add(lightAddressEntity);
                    } else if(1==lightAddressEntityListTmp.size()) {
                        TBLightAddressEntity lightAddressEntity = lightAddressEntityListTmp.get(0);
                        if(!addressId.equals(lightAddressEntity.getAddressId())) {
                            lightAddressEntity.setAddressId(addressId);
                            lightAddressEntity.setUpdateBy(user.getUserName());
                            lightAddressEntity.setUpdateName(user.getRealName());
                            lightAddressEntity.setUpdateDate(new Date());
                            lightAddressEntityUpdateList.add(lightAddressEntity);
                        }
                    } else {
                        return new ApiResultJson(ApiResultJson.CODE_500_MSG,"灯标识数据错误",null);
                    }

                    //添加途经风险点
                    TBAccessRecordEntity accessRecordEntity = new TBAccessRecordEntity();
                    //accessRecordEntity.setBussId(bussId);
                    accessRecordEntity.setManId(user.getId());
                    accessRecordEntity.setAddressId(addressId);
                    accessRecordEntity.setArrivalTime(sdf.parse(object.getString("arrivalTime")));
                    accessRecordEntity.setLightId(lightId);
                    accessRecordEntity.setCreateBy(user.getUserName());
                    accessRecordEntity.setCreateName(user.getRealName());
                    accessRecordEntity.setCreateDate(new Date());
                    accessRecordEntityList.add(accessRecordEntity);

                }
                //systemService.batchSave(lightAddressEntityAddList);
                for(int k=0;k<lightAddressEntityUpdateList.size();k++){
                    systemService.saveOrUpdate(lightAddressEntityUpdateList.get(k));
                }
                systemService.batchSave(accessRecordEntityList);
            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, null);
        } catch (Exception e) {
            LogUtil.error("可见光探测路径上报错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    /**
     * 获取灯和风险点对应关系接口
     * @param token
     * @param sessionId         用户当前登录的sessionid
     * @param reportContent
     * @return
     */
    @RequestMapping("/getAddressByLight")
    @ResponseBody
    public ApiResultJson getAddressByLight(String token, String reportContent, HttpServletRequest request){
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            String lightId = request.getParameter("lightId");
            String sql = "select la.light_id lightId, la.address_id addressId, a.address addressName from t_b_light_address la, t_b_address_info a where la.address_id=a.id and a.is_delete!='1';";
            List<Map<String,Object>> lightAddressList = this.systemService.findForJdbc(sql);
            JSONObject joRet = new JSONObject();
            JSONArray ja = new JSONArray();
            if(null!=lightAddressList && lightAddressList.size()>0){
                for(int i=0;i<lightAddressList.size();i++){
                    JSONObject jo = new JSONObject();
                    jo.put("lightId", lightAddressList.get(i).get("lightId"));
                    jo.put("addressId", lightAddressList.get(i).get("addressId"));
                    jo.put("addressName", lightAddressList.get(i).get("addressName"));
                    ja.add(jo);
                    if(StringUtil.isNotEmpty(lightId) && lightId.equals(lightAddressList.get(i).get("lightId"))){
                        joRet.put("lightId",lightId);
                        joRet.put("addressId",lightAddressList.get(i).get("addressId"));
                        joRet.put("addressName",lightAddressList.get(i).get("addressName"));
                    }
                }
            }
            joRet.put("lightAddressRel",ja);
            return new ApiResultJson(joRet);
        } catch (Exception e) {
            LogUtil.error("获取灯和风险点对应关系错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


    /**
     * 获取轨迹动画页面
     * @param request
     * @return
     */
    @RequestMapping("track")
    public ModelAndView track(HttpServletRequest request) {
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ModelAndView("mobile/mobileLogin/login.do");
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ModelAndView("mobile/mobileLogin/login.do");
            }

        } catch (Exception e) {
            LogUtil.error("获取轨迹动画页面异常",e);
            return new ModelAndView("mobile/mobileLogin/login.do");
        }

        String supermapMineUrl = ResourceUtil.getConfigByName("supermapMineUrl");
        request.setAttribute("supermapMineUrl",supermapMineUrl);
        String supermapMineCenter = ResourceUtil.getConfigByName("supermapMineCenter");
        request.setAttribute("supermapMineCenter",supermapMineCenter);
        String supermapCountyUrl = ResourceUtil.getConfigByName("supermapCountyUrl");
        request.setAttribute("supermapCountyUrl",supermapCountyUrl);
        String supermapCountyCenter = ResourceUtil.getConfigByName("supermapCountyCenter");
        request.setAttribute("supermapCountyCenter",supermapCountyCenter);

        return new ModelAndView("com/sdzk/buss/web/lightAddress/tBLightAddressTrack");
    }


    /**
     * 获取全部地址
     *
     * @param req
     * @return
     */
    @RequestMapping(params = "getAllAddressList")
    @ResponseBody
    public AjaxJson getAllAddressList(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> resMap = new HashMap<String, Object>();
        CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
        cq.eq("isDelete", Constants.IS_DELETE_N);
        cq.eq("isshow",Constants.IS_SHOW_Y);
        cq.add();
        List<TBAddressInfoEntity> addressList = this.systemService.getListByCriteriaQuery(cq, false);
        resMap.put("addressList", addressList);
        j.setAttributes(resMap);
        return j;
    }

    /**
     * 获取定位信息
     *
     * @return
     */
    @RequestMapping(params = "getTrack")
    @ResponseBody
    public AjaxJson getTrack(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        JSONArray ja = new JSONArray();
        String sql = "select distinct r.man_id userId, u.realname userName from t_b_access_record r, t_s_base_user u where r.man_id=u.id";
        List<Map<String,Object>> userList = this.systemService.findForJdbc(sql);
        if(null!=userList && userList.size()>0){
            for(int i=0;i<userList.size();i++){
                String userId = (String)userList.get(i).get("userId");
                sql = "select r.address_id addressId, a.address addressName, a.lon lon, a.lat lat, DATE_FORMAT(r.arrival_time,'%Y-%m-%d %k:%i:%s') arrivalTime " +
                        "from t_b_access_record r, t_b_address_info a where r.address_id=a.id " +
                        "and r.man_id='"+userId+"' and a.is_delete!='1' and a.isShow='Y' order by r.arrival_time asc";
                List<Map<String,Object>> accessList = this.systemService.findForJdbc(sql);
                if(null!=accessList && accessList.size()>0){
                    JSONObject jo = new JSONObject();
                    jo.put("userId",userId);
                    jo.put("userName", userList.get(i).get("userName"));
                    jo.put("accessList",accessList);
                    ja.add(jo);
                }
            }
            j.setObj(ja);
        }
        return j;
    }

    /**
     * 根据检查人，检查地点，检查时间，查询当前隐患
     *
     * @return
     */
    @RequestMapping(params = "getHds")
    @ResponseBody
    public AjaxJson getHds(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String arrivalTime = req.getParameter("arrivalTime");
        String addressId = req.getParameter("addressId");
        String examDate = "";
        //SimpleDateFormat timeSdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        //SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtil.isNotEmpty(arrivalTime)){
            examDate = arrivalTime.substring(0,10);
        }

        String sql = "select hde.id hdId, d.departname dutyUnit, hde.duty_man dutyMan, hde.problem_desc hdDesc\n" +
                "from t_b_hidden_danger_exam hde, t_s_depart d \n" +
                "where hde.duty_unit=d.ID\n" +
                "and ( FIND_IN_SET('" + userId + "',hde.fill_card_manids) or hde.sjjc_check_man='" + userName + "') \n" +
                "and hde.address='" + addressId + "' \n" +
                "and DATE_FORMAT(hde.exam_date,'%Y-%m-%d')='" + examDate +"'";

        List<Map<String,Object>> hdList = this.systemService.findForJdbc(sql);
        j.setObj(hdList);
        return j;
    }

}