package com.sdzk.buss.web.majorhiddendanger.service;

import com.alibaba.fastjson.JSONArray;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.hiddendangerhistory.entity.TBHiddenDangerHistoryEntity;
import com.sdzk.buss.web.hiddendangerhistory.service.TBHiddenDangerHistoryServiceI;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import com.sdzk.buss.web.rectprogressreport.entity.TBRectProgressReportEntity;
import com.sdzk.buss.web.rectprogressreport.service.TBRectProgressReportServiceI;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.HttpClientUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：张赛超
 * Created by Lenovo on 17-7-11.
 */
@Service("MajorDangerReportRPC")
public class MajorDangerReportRPC {
    @Autowired
    private SystemService systemService;
    @Autowired
    private TBHiddenDangerHistoryServiceI tbHiddenDangerHistoryService;
    @Autowired
    private TBRectProgressReportServiceI tbRectProgressReportService;
    @Autowired
    private TBMajorHiddenDangerServiceI tBMajorHiddenDangerService;

    private String dateToString(java.util.Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        if(date != null){
            str=sdf.format(date);
        }
        return str;
    }

    @Transactional
    public AjaxJson majorDangerReportToGroup(String ids){
        String json = null;
        String msg =  "重大隐患上报成功！";
        String code = null;
        AjaxJson j = new AjaxJson();
        Map<String,String> paramMap = new HashMap<>();

        String url = ResourceUtil.getConfigByName("majorHiddenDangerReport_group");

        try {
            /**
             * 获取本煤矿的煤矿名称和煤矿编码
             * */
            String token = ResourceUtil.getConfigByName("token_group");
            String mineCode = ResourceUtil.getConfigByName("mine_code");
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

            JSONArray reportContents = new JSONArray();

            for(String id:ids.split(",")){
                if (StringUtil.isEmpty(id)){
                    continue;
                }
                TBMajorHiddenDangerEntity tbMajorHiddenDangerEntity = systemService.getEntity(TBMajorHiddenDangerEntity.class, id);

                JSONObject fiveInfo = new JSONObject();
                fiveInfo.put("mhdGoalAch", tbMajorHiddenDangerEntity.getGoalAch());
                fiveInfo.put("mhdGoalAchDate", dateToString(tbMajorHiddenDangerEntity.getGoalAchDate()));
                fiveInfo.put("mhdIsGoalAchieve", tbMajorHiddenDangerEntity.getIsGoalAchieve());
                fiveInfo.put("mhdRespAch", tbMajorHiddenDangerEntity.getRespAch());
                fiveInfo.put("mhdRespAchDate", dateToString(tbMajorHiddenDangerEntity.getRespAchDate()));
                fiveInfo.put("mhdIsRespAchieve", tbMajorHiddenDangerEntity.getIsRespAchieve());
                fiveInfo.put("mhdMeasureAch", tbMajorHiddenDangerEntity.getMeasureAch());
                fiveInfo.put("mhdMeasureAchDate", dateToString(tbMajorHiddenDangerEntity.getMeasureAchDate()));
                fiveInfo.put("mhdIsMeasureAchieve", tbMajorHiddenDangerEntity.getIsMeasureAchieve());
                fiveInfo.put("mhdFundAch",tbMajorHiddenDangerEntity.getFundAch());
                fiveInfo.put("mhdFundAchDate", dateToString(tbMajorHiddenDangerEntity.getFundAchDate()));
                fiveInfo.put("mhdIsFundAchieve", tbMajorHiddenDangerEntity.getIsFundAchieve());
                fiveInfo.put("mhdPlanAch", tbMajorHiddenDangerEntity.getPlanAch());
                fiveInfo.put("mhdPlanAchDate", dateToString(tbMajorHiddenDangerEntity.getPlanAchDate()));
                fiveInfo.put("mhdIsPlanAchieve", tbMajorHiddenDangerEntity.getIsPlanAchieve());
                fiveInfo.put("mhdGovePlan", tbMajorHiddenDangerEntity.getGovePlan());
                fiveInfo.put("mhdGovePlanDate", dateToString(tbMajorHiddenDangerEntity.getGovePlanDate()));
                fiveInfo.put("mhdIsGovePlanAchieve", tbMajorHiddenDangerEntity.getIsGovePlanAchieve());

                JSONObject reportContent = new JSONObject();
                reportContent.put("id", id);
                //            reportContent.put("hdbiHdLocation", tbMajorHiddenDangerEntity.getHdLocation());
                //            根据id转成对应的地点名称
                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getHdLocation())){
                    reportContent.put("hdbiHdLocation", "");
                }else{
                    TBAddressInfoEntity hdLocation = systemService.getEntity(TBAddressInfoEntity.class, tbMajorHiddenDangerEntity.getHdLocation());
                    reportContent.put("hdbiHdLocation", hdLocation.getAddress());
                }
                reportContent.put("hdbiInveDate", dateToString(tbMajorHiddenDangerEntity.getInveDate()));
                reportContent.put("hdbiHdInfoSource", tbMajorHiddenDangerEntity.getHdInfoSource());
                reportContent.put("hdbiHdLevel", tbMajorHiddenDangerEntity.getHdLevel());
                reportContent.put("hdbiHdCate", tbMajorHiddenDangerEntity.getHdCate());
                reportContent.put("hdbiHdMajor", tbMajorHiddenDangerEntity.getHdMajor());
                reportContent.put("hdbiDesc", tbMajorHiddenDangerEntity.getHdDesc());
                reportContent.put("hdbiVerifyDate", dateToString(tbMajorHiddenDangerEntity.getVerifyDate()));
                reportContent.put("hdbiVerifyUnit", tbMajorHiddenDangerEntity.getVerifyUnit());
                reportContent.put("hdbiVerifyStatus", tbMajorHiddenDangerEntity.getVerifyStatus());
                reportContent.put("hdbiVerifyMan", tbMajorHiddenDangerEntity.getVerifyMan());
                //转换组织机构
                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getRectUnit())){
                    reportContent.put("hdbiRectUnit", "");
                }else{
                    TSDepart rectUnit = systemService.getEntity(TSDepart.class, tbMajorHiddenDangerEntity.getRectUnit());
                    reportContent.put("hdbiRectUnit", rectUnit.getDepartname());
                }
//                reportContent.put("hdbiRectUnit", tbMajorHiddenDangerEntity.getRectUnit());
//                reportContent.put("hdbiRectMan", tbMajorHiddenDangerEntity.getRectMan());
                //转换人名
                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getRectMan())){
                    reportContent.put("hdbiRectMan", "");
                }else{
                    TSBaseUser acceptor = systemService.getEntity(TSBaseUser.class, tbMajorHiddenDangerEntity.getRectMan());
                    reportContent.put("hdbiRectMan", acceptor.getRealName());
                }
                reportContent.put("hdbiRectPeriod", dateToString(tbMajorHiddenDangerEntity.getRectPeriod()));
                reportContent.put("hdbiRectMeasures", tbMajorHiddenDangerEntity.getRectMeasures());
                reportContent.put("hdbiRectTagartDt", tbMajorHiddenDangerEntity.getRectTagartDt());
                //将id转换成对应的人名
                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getAcceptor())){
                    reportContent.put("hdbiAcceptor", "");
                }else{
                    TSBaseUser acceptor = systemService.getEntity(TSBaseUser.class, tbMajorHiddenDangerEntity.getAcceptor());
                    reportContent.put("hdbiAcceptor", acceptor.getRealName());
                }
                reportContent.put("hdbiAccepTime", dateToString(tbMajorHiddenDangerEntity.getAccepTime()));
                reportContent.put("hdbiAccepReport", tbMajorHiddenDangerEntity.getAccepReport());
                //将id转换成对应的人名
//                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getReviewer())){
//                    reportContent.put("hdbiReviewer", "");
//                }else{
//                    TSBaseUser reviewer = systemService.getEntity(TSBaseUser.class, tbMajorHiddenDangerEntity.getReviewer());
//                    reportContent.put("hdbiReviewer", reviewer.getRealName());
//                }
                reportContent.put("hdbiReviewer", tbMajorHiddenDangerEntity.getReviewer());
                reportContent.put("hdbiReviewTime", dateToString(tbMajorHiddenDangerEntity.getReviewTime()));
                reportContent.put("hdbiReviewReport", tbMajorHiddenDangerEntity.getReviewReport());
                reportContent.put("hdbiIsLsSub", tbMajorHiddenDangerEntity.getIsLsSub());
                reportContent.put("hdbiIsLsProv", tbMajorHiddenDangerEntity.getIsLsProv());
                reportContent.put("hdbiClStatus", tbMajorHiddenDangerEntity.getClStatus());
                reportContent.put("hdbiCancelDate", dateToString(tbMajorHiddenDangerEntity.getCancelDate()));
                reportContent.put("fiveInfo", fiveInfo);

                reportContent.put("hdbiReportStatus", tbMajorHiddenDangerEntity.getReportStatus());
                reportContent.put("hdbiReportDate", dateToString(tbMajorHiddenDangerEntity.getReportDate()));
//                reportContent.put("hdbiDutyUnit", tbMajorHiddenDangerEntity.getDutyUnit());
                if(StringUtil.isNotEmpty(tbMajorHiddenDangerEntity.getDutyUnit())){
                    reportContent.put("hdbiDutyUnit", tbMajorHiddenDangerEntity.getDutyUnit().getDepartname());
                }else{
                    reportContent.put("hdbiDutyUnit", null);
                }
                reportContent.put("hdbiDutyMan", tbMajorHiddenDangerEntity.getDutyMan());
                if(StringUtil.isNotEmpty(tbMajorHiddenDangerEntity.getDangerId())){
                    reportContent.put("hdbiDangerSource", tbMajorHiddenDangerEntity.getDangerId().getYeMhazardDesc());
                }else{
                    reportContent.put("hdbiDangerSource", null);
                }
//                下面的字段暂时不使用，不需要上传
//                reportContent.put("hdbiKeyWords", tbMajorHiddenDangerEntity.getKeyWords());
//                reportContent.put("hdbiDangerId", tbMajorHiddenDangerEntity.getDangerId());
//                reportContent.put("hdbiHiddenType", tbMajorHiddenDangerEntity.getHiddenType());

                reportContents.add(reportContent);
            }
            paramMap.put("reportContent", reportContents.toString());

            json = HttpClientUtils.post(url, paramMap, "UTF-8");

            /**   这个是阿里巴巴包下的string转换成JSON的方法   */
            JSONObject jsonObject = JSONObject.parseObject(json);
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
                    systemService.executeSql("update t_b_major_hidden_danger set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
                }catch (Exception e){
                    j.setMsg("上报成功但是本地数据库操作失败！");
                }
            }else{
                j.setMsg(jsonObject.getString("message"));
            }

        } catch (NetServiceException e) {
            msg = "重大隐患上报失败！";
            e.printStackTrace();
        }
        j.setMsg(msg);
        return j;
    }

    public AjaxJson majorDangerReport(String ids,boolean isFromTask){
        String json = null;
        String msg = null;
        String code = null;
        AjaxJson j = new AjaxJson();
        Map<String,String> paramMap = new HashMap<>();

        String url = ResourceUtil.getConfigByName("majorHiddenDangerReport");

        try {
            /**
             * 获取本煤矿的煤矿名称和煤矿编码
             * */
            String token = ResourceUtil.getConfigByName("token");
            String mineCode = ResourceUtil.getConfigByName("mine_code");

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
            JSONArray reportContents = new JSONArray();

            for(String id:ids.split(",")){
                if (StringUtil.isEmpty(id)){
                    continue;
                }
                TBMajorHiddenDangerEntity tbMajorHiddenDangerEntity = systemService.getEntity(TBMajorHiddenDangerEntity.class, id);

                CriteriaQuery histCq = new CriteriaQuery(TBHiddenDangerHistoryEntity.class);
                histCq.eq("fkHiddenInfoId", id);
                histCq.add();
                List<TBHiddenDangerHistoryEntity> tbHiddenDangerHistoryList = tbHiddenDangerHistoryService.getListByCriteriaQuery(histCq, false);
                JSONArray histList = new JSONArray();
                if (tbHiddenDangerHistoryList != null && tbHiddenDangerHistoryList.size() > 0){
                    for(TBHiddenDangerHistoryEntity tbHiddenDangerHistoryEntity : tbHiddenDangerHistoryList){
                        JSONObject histInfo = new JSONObject();
                        histInfo.put("hdhDealTime", dateToString(tbHiddenDangerHistoryEntity.getDealTime()));
                        histInfo.put("hdhDealStep", tbHiddenDangerHistoryEntity.getDealStep());
                        histInfo.put("hdhDealDesc", tbHiddenDangerHistoryEntity.getDealDesc());
                        histInfo.put("hdhDealUserName", tbHiddenDangerHistoryEntity.getDealUserName());
                        histList.add(histInfo);
                    }
                }

                /**
                 * 冉哥给的方法，如何根据ID从数据库中查找数据
                 * */
                CriteriaQuery rectCq = new CriteriaQuery(TBRectProgressReportEntity.class);
                /**  声明查询语句，使根据对应的Entity生成对应的cq   */
                rectCq.eq("fkHiddenInfoId", id);
                rectCq.add();
                List<TBRectProgressReportEntity> tbRectProgressReportList = tbRectProgressReportService.getListByCriteriaQuery(rectCq, false);
               JSONArray reportList = new JSONArray();

                if (tbRectProgressReportList != null && tbRectProgressReportList.size() > 0) {
                    for (TBRectProgressReportEntity tbRectProgressReportEntity : tbRectProgressReportList) {
                        JSONObject rectReport = new JSONObject();
                        rectReport.put("rppReportDate", dateToString(tbRectProgressReportEntity.getReportDate()));
    //                    rectReport.put("rppReportPerson", tbRectProgressReportEntity.getReportPerson());
                        //将id转换成对应的人名
                        if(StringUtil.isEmpty(tbRectProgressReportEntity.getReportPerson())){
                            rectReport.put("rppReportPerson", "");
                        }else{
                            TSBaseUser reportPerson = systemService.getEntity(TSBaseUser.class, tbRectProgressReportEntity.getReportPerson());
                            rectReport.put("rppReportPerson", reportPerson.getRealName());
                        }
                        ////////////////////////////////////////////////////////////////
                        rectReport.put("rppReportDesc", tbRectProgressReportEntity.getReportDesc());
                        rectReport.put("rppIsComplete", tbRectProgressReportEntity.getIsComplete());
                        reportList.add(rectReport);
                    }
                }

                JSONObject fiveInfo = new JSONObject();
                fiveInfo.put("mhdGoalAch", tbMajorHiddenDangerEntity.getGoalAch());
                fiveInfo.put("mhdGoalAchDate", dateToString(tbMajorHiddenDangerEntity.getGoalAchDate()));
                fiveInfo.put("mhdIsGoalAchieve", tbMajorHiddenDangerEntity.getIsGoalAchieve());
                fiveInfo.put("mhdRespAch", tbMajorHiddenDangerEntity.getRespAch());
                fiveInfo.put("mhdRespAchDate", dateToString(tbMajorHiddenDangerEntity.getRespAchDate()));
                fiveInfo.put("mhdIsRespAchieve", tbMajorHiddenDangerEntity.getIsRespAchieve());
                fiveInfo.put("mhdMeasureAch", tbMajorHiddenDangerEntity.getMeasureAch());
                fiveInfo.put("mhdMeasureAchDate", dateToString(tbMajorHiddenDangerEntity.getMeasureAchDate()));
                fiveInfo.put("mhdIsMeasureAchieve", tbMajorHiddenDangerEntity.getIsMeasureAchieve());
                fiveInfo.put("mhdFundAch",tbMajorHiddenDangerEntity.getFundAch());
                fiveInfo.put("mhdFundAchDate", dateToString(tbMajorHiddenDangerEntity.getFundAchDate()));
                fiveInfo.put("mhdIsFundAchieve", tbMajorHiddenDangerEntity.getIsFundAchieve());
                fiveInfo.put("mhdPlanAch", tbMajorHiddenDangerEntity.getPlanAch());
                fiveInfo.put("mhdPlanAchDate", dateToString(tbMajorHiddenDangerEntity.getPlanAchDate()));
                fiveInfo.put("mhdIsPlanAchieve", tbMajorHiddenDangerEntity.getIsPlanAchieve());
                fiveInfo.put("mhdGovePlan", tbMajorHiddenDangerEntity.getGovePlan());
                fiveInfo.put("mhdGovePlanDate", dateToString(tbMajorHiddenDangerEntity.getGovePlanDate()));
                fiveInfo.put("mhdIsGovePlanAchieve", tbMajorHiddenDangerEntity.getIsGovePlanAchieve());

                JSONObject reportContent = new JSONObject();
                reportContent.put("id", id);
                // 根据id转成对应的地点名称
                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getHdLocation())){
                    reportContent.put("hdbiHdLocation", "");
                }else{
                    TBAddressInfoEntity hdLocation = systemService.getEntity(TBAddressInfoEntity.class, tbMajorHiddenDangerEntity.getHdLocation());
                    reportContent.put("hdbiHdLocation", hdLocation.getAddress());
                }

                reportContent.put("hdbiInveDate", dateToString(tbMajorHiddenDangerEntity.getInveDate()));
                reportContent.put("hdbiHdInfoSource", tbMajorHiddenDangerEntity.getHdInfoSource());
                reportContent.put("hdbiHdLevel", tbMajorHiddenDangerEntity.getHdLevel());
                reportContent.put("hdbiHdCate", tbMajorHiddenDangerEntity.getHdCate());
                reportContent.put("hdbiHdMajor", tbMajorHiddenDangerEntity.getHdMajor());
                reportContent.put("hdbiDesc", tbMajorHiddenDangerEntity.getHdDesc());
                reportContent.put("hdbiVerifyDate", dateToString(tbMajorHiddenDangerEntity.getVerifyDate()));
                reportContent.put("hdbiVerifyUnit", tbMajorHiddenDangerEntity.getVerifyUnit());
                reportContent.put("hdbiVerifyStatus", tbMajorHiddenDangerEntity.getVerifyStatus());
                //将id转换成对应的人名
                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getVerifyMan())){
                    reportContent.put("hdbiVerifyMan", "");
                }else{
                    TSBaseUser verifyMan = systemService.getEntity(TSBaseUser.class, tbMajorHiddenDangerEntity.getVerifyMan());
                    reportContent.put("hdbiVerifyMan", verifyMan.getRealName());
                }
                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getRectUnit())){
                    reportContent.put("hdbiRectMan", "");
                }else{
                    TSDepart rectUnit = systemService.getEntity(TSDepart.class, tbMajorHiddenDangerEntity.getRectUnit());
                    reportContent.put("hdbiRectMan", rectUnit.getDepartname());
                }
                //将id转换成对应的人名
                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getRectMan())){
                    reportContent.put("hdbiRectMan", "");
                }else{
                    TSBaseUser rectMan = systemService.getEntity(TSBaseUser.class, tbMajorHiddenDangerEntity.getRectMan());
                    reportContent.put("hdbiRectMan", rectMan.getRealName());
                }
                reportContent.put("hdbiRectPeriod", dateToString(tbMajorHiddenDangerEntity.getRectPeriod()));
                reportContent.put("hdbiRectMeasures", tbMajorHiddenDangerEntity.getRectMeasures());
                reportContent.put("hdbiRectTagartDt", tbMajorHiddenDangerEntity.getRectTagartDt());
                //将id转换成对应的人名
                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getAcceptor())){
                    reportContent.put("hdbiAcceptor", "");
                }else{
                    TSBaseUser acceptor = systemService.getEntity(TSBaseUser.class, tbMajorHiddenDangerEntity.getAcceptor());
                    reportContent.put("hdbiAcceptor", acceptor.getRealName());
                }
                reportContent.put("hdbiAccepTime", dateToString(tbMajorHiddenDangerEntity.getAccepTime()));
                reportContent.put("hdbiAccepReport", tbMajorHiddenDangerEntity.getAccepReport());
                //将id转换成对应的人名
                if(StringUtil.isEmpty(tbMajorHiddenDangerEntity.getReviewer())){
                    reportContent.put("hdbiReviewer", "");
                }else{
                    TSBaseUser reviewer = systemService.getEntity(TSBaseUser.class, tbMajorHiddenDangerEntity.getReviewer());
                    reportContent.put("hdbiReviewer", reviewer.getRealName());
                }
                reportContent.put("hdbiReviewTime", dateToString(tbMajorHiddenDangerEntity.getReviewTime()));
                reportContent.put("hdbiReviewReport", tbMajorHiddenDangerEntity.getReviewReport());
                reportContent.put("hdbiClStatus", tbMajorHiddenDangerEntity.getClStatus());
                reportContent.put("hdbiCancelDate", dateToString(tbMajorHiddenDangerEntity.getCancelDate()));
                reportContent.put("fiveInfo", fiveInfo);
                reportContent.put("histInfo", histList);
                reportContent.put("rectReport", reportList);
                reportContents.add(reportContent);
            }

            paramMap.put("reportContent", reportContents.toString());

            json = HttpClientUtils.post(url, paramMap, "UTF-8");

            /**   这个是阿里巴巴包下的string转换成JSON的方法   */
            JSONObject jsonObject = JSONObject.parseObject(json);

            msg = jsonObject.getString("message");
            System.out.println(msg);
            code = jsonObject.getString("code");

            if ("200".equals(code)){
                //更新数据库中重大隐患上报状态和时间 --hanxd
                if(!isFromTask){
                    Map<String,String> retMap = tBMajorHiddenDangerService.updateMajorDangerReport(ids);
                    if(null==retMap || !Constants.LOCAL_RESULT_CODE_SUCCESS.equals(retMap.get("code"))){
                        String subMsg = "更新本地数据库失败";
                        if(null!=retMap && retMap.containsKey("message")){
                            subMsg = retMap.get("message");
                        }
                        msg = "重大隐患上报失败:" + subMsg;
                    }
                }
            }
        } catch (NetServiceException e) {
            msg = "重大隐患上报失败！";
            e.printStackTrace();
        }

        j.setMsg(msg);
        return j;
    }

}
