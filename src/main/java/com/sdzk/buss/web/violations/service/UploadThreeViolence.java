package com.sdzk.buss.web.violations.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.dangersource.entity.TBWorkDangerRelEntity;
import com.sdzk.buss.web.dangersource.entity.TBWorkProcessManageEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 张赛超
 * Created by Lenovo on 17-9-1.
 */
@Service("UploadThreeViolent")
public class UploadThreeViolence {
    @Autowired
    private SystemService systemService;

    public AjaxJson uploadThreeViolence(String ids){

        String message = null;
        String code = null;
        AjaxJson j = new AjaxJson();

        Map paramMap = new HashMap<>();
        String json = null;

        message = "三违信息上报成功";
        try {
            if (StringUtil.isNotEmpty(ids)){
                /**
                 * Author：张赛超
                 * */
                //TODO 集团上报接口
                String url = ResourceUtil.getConfigByName("uploadThreeViolenceUrl");

                try {
                    /**
                     * 获取本煤矿的煤矿名称和煤矿编码
                     * */
                    String mineCode = ResourceUtil.getConfigByName("mine_code");

//                    /**
//                     * 加密过程
//                     * */
//                    String tempToken = "token=" + token + "&mineCode=" + mineCode;
//                    String ciphertext = null;
//                    try {
//                        ciphertext = AesUtil.encryptWithIV(tempToken, token);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    paramMap.put("token", ciphertext);

                    paramMap.put("mineCode", mineCode);

                    JSONArray reportContents = new JSONArray();

                    for(String id:ids.split(",")){
                        JSONObject reportContent = new JSONObject();

                        TBThreeViolationsEntity tbThreeViolationsEntity = systemService.getEntity(TBThreeViolationsEntity.class, id);
                        if(tbThreeViolationsEntity != null){
                            reportContent.put("id", id);
                            reportContent.put("vioDate", DateUtils.date2Str(tbThreeViolationsEntity.getVioDate(), DateUtils.date_sdf));
                            //转换班次
                            String shift = tbThreeViolationsEntity.getShift();
                            if(StringUtil.isNotEmpty(shift)){
                                reportContent.put("shift", DicUtil.getTypeNameByCode("workShift", tbThreeViolationsEntity.getShift()));
                            }else{
                                reportContent.put("shift", "");
                            }
                            reportContent.put("vioAddress", tbThreeViolationsEntity.getVioAddress());
                            //转化单位
                            String vioUnits = tbThreeViolationsEntity.getVioUnits();
                            if(StringUtil.isNotEmpty(vioUnits)){
                                List<TSDepart> tsDepartList = systemService.findByProperty(TSDepart.class, "id", vioUnits);
                                if(tsDepartList != null & tsDepartList.size() > 0){
                                    reportContent.put("vioUnits", tsDepartList.get(0).getDepartname());
                                }else{
                                    reportContent.put("vioUnits", "");
                                }
                            }
                            //转化三违级别
                            String vioLevel = tbThreeViolationsEntity.getVioLevel();
                            if(StringUtil.isNotEmpty(vioLevel)){
                                reportContent.put("vioLevel", DicUtil.getTypeNameByCode("vio_level", vioLevel));
                            }else{
                                reportContent.put("vioLevel", "");
                            }
                            reportContent.put("workType", tbThreeViolationsEntity.getWorkType());
                            String vioCategory = tbThreeViolationsEntity.getVioCategory();
                            if(StringUtil.isNotEmpty(vioCategory)){
                                reportContent.put("vioCategory", DicUtil.getTypeNameByCode("violaterule_wzfl", vioCategory));
                            }else{
                                reportContent.put("vioCategory", "");
                            }
                            //转化违章定性
                            String vioQualitative = tbThreeViolationsEntity.getVioQualitative();
                            if(StringUtil.isNotEmpty(vioQualitative)){
                                reportContent.put("vioQualitative", DicUtil.getTypeNameByCode("violaterule_wzdx", vioQualitative));
                            }else{
                                reportContent.put("vioQualitative", "");
                            }
//                            reportContent.put("stopPeople", tbThreeViolationsEntity.getStopPeople());
                            //转化制止人
//                            String stopPeople = tbThreeViolationsEntity.getStopPeople();
                            reportContent.put("stopPeople", tbThreeViolationsEntity.getStopPeople());
//                            if(StringUtil.isNotEmpty(stopPeople)){
//                                List<TSUser> tsUserList = systemService.findByProperty(TSUser.class, "id", stopPeople);
//                                if(tsUserList != null && tsUserList.size() > 0){
//                                    reportContent.put("stopPeople", tsUserList.get(0).getRealName());
//                                }else{
//                                    reportContent.put("stopPeople", "");
//                                }
//                            }
//                            reportContent.put("findUnits", tbThreeViolationsEntity.getFindUnits());
                            //转化查处单位
                            String findUnits = tbThreeViolationsEntity.getFindUnits();
                            if(StringUtil.isNotEmpty(findUnits)){
                                List<TSDepart> tsDepartList = systemService.findByProperty(TSDepart.class, "id", findUnits);
                                if(tsDepartList != null & tsDepartList.size() > 0){
                                    reportContent.put("findUnits", tsDepartList.get(0).getDepartname());
                                }else{
                                    reportContent.put("findUnits", "");
                                }
                            }
                            reportContent.put("vioFactDesc", tbThreeViolationsEntity.getVioFactDesc());
                            reportContent.put("remark", tbThreeViolationsEntity.getRemark());
                            reportContent.put("vioPeople", tbThreeViolationsEntity.getVioPeople());
                            //转化违章人员
//                            String vioPeople = tbThreeViolationsEntity.getVioPeople();
//                            if(StringUtil.isNotEmpty(vioPeople)){
//                                List<TSUser> tsUserList = systemService.findByProperty(TSUser.class, "id", vioPeople);
//                                if(tsUserList != null && tsUserList.size() > 0){
//                                    reportContent.put("vioPeople", tsUserList.get(0).getRealName());
//                                }else{
//                                    reportContent.put("vioPeople", "");
//                                }
//                            }
                            reportContent.put("workType", tbThreeViolationsEntity.getWorkType());
                        }

                        reportContents.add(reportContent);
                    }
                    System.out.println(reportContents.toString());
                    paramMap.put("reportContent", reportContents.toString());
                    json = HttpClientUtils.post(url, paramMap, "UTF-8");

                    net.sf.json.JSONObject object= net.sf.json.JSONObject.fromObject(json);  //创建JsonObject对象
                    code = object.getString("code");     //如果code=200，则数据正确可用
                    if("200".equals(code)){
                        message = "三违信息上报成功！";
                    }else{
                        message = object.getString("message");
                    }

                } catch (NetServiceException e) {
                    message = "三违信息上报失败！";
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "三违信息上报失败！";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;

    }
}
