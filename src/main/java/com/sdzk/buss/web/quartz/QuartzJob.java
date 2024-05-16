package com.sdzk.buss.web.quartz;


import com.sdzk.buss.api.service.WeChartGetToken;
import com.sdzk.buss.api.utils.WebChatUtil;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.SMSSenderUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerAddresstRelEntity;
import com.sdzk.buss.web.hiddendanger.controller.TBHiddenDangerHandleController;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleLogServiceI;
import com.sdzk.buss.web.quartz.service.QrtzManagerServiceI;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.quartz.*;
import org.quartz.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 17-9-19.
 */
@Service
public class QuartzJob implements Job {

    @Autowired
    private SystemService systemService;

    @Autowired
    private WeChartGetToken weChartGetToken;
    @Autowired
    private QrtzManagerServiceI qrtzManagerServiceI;
    @Autowired
    private TBHiddenDangerHandleLogServiceI tBHiddenDangerHandleLogService;
    private static final Logger logger = Logger.getLogger(QuartzJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date runDate = new Date();
        String jobName = context.getJobDetail().getName();
        Trigger trigger = context.getTrigger();
        Scheduler scheduler = context.getScheduler();

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String hiddenDangerId = (String) dataMap.get("hiddenDangerId");
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class);
        try {
            cq.eq("hiddenDanger.id", hiddenDangerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        List<TBHiddenDangerHandleEntity> handleEntityList = systemService.getListByCriteriaQuery(cq, false);
        if (handleEntityList != null && !handleEntityList.isEmpty()) {
            TBHiddenDangerHandleEntity handleEntity = handleEntityList.get(0);
            TBHiddenDangerExamEntity hiddenDangerExamEntity = systemService.getEntity(TBHiddenDangerExamEntity.class, hiddenDangerId);
            //隐患状态
            String handlelStatus = handleEntity.getHandlelStatus();
            //1.判断状态是否是整改状态
            if (Constants.HANDELSTATUS_REPORT.equals(handlelStatus) || Constants.HANDELSTATUS_ROLLBACK_CHECK.equals(handlelStatus)) {
                //发送短信通知
                TSDepart tsDepart = hiddenDangerExamEntity.getDutyUnit();
                String pho1 = tsDepart.getPho1();
                String pho2 = tsDepart.getPho2();
                String pho3 = tsDepart.getPho3();
                //企业微信通知
                String wx1 = tsDepart.getWx1();
                String wx2 = tsDepart.getWx2();
                String wx3 = tsDepart.getWx3();


                //超期次数
                String limitNum = handleEntity.getLimitNum();
                Integer limitNumTemp = 0;
                if (StringUtils.isNotBlank(limitNum)) {
                    limitNumTemp = Integer.parseInt(limitNum.trim());
                }
                //计算整改周期（限期日期-创建日期）
                Date createDate = hiddenDangerExamEntity.getCreateDate();
                Date limitDate = hiddenDangerExamEntity.getLimitDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String limitDateStr = DateUtils.formatDate(hiddenDangerExamEntity.getLimitDate(), "yyyy-MM-dd");
                String dutyMan = hiddenDangerExamEntity.getDutyMan();
                String createName = hiddenDangerExamEntity.getCreateName();

                String tsDepartid = tsDepart.getId();
                //sql 待测试
                String sql = "select openid from t_b_WexinOpenId  where departId='" + tsDepartid + "'";
                List<String> openlist = systemService.findListbySql(sql);

                handleEntity.setLimitNum((limitNumTemp + 1) + "");
                systemService.saveOrUpdate(handleEntity);
                String hiddenLevelName = DicUtil.getTypeNameByCode("hiddenLevel", hiddenDangerExamEntity.getHiddenNature());
                if (limitNumTemp <= 1) {
                    //企业微信通知
                    String content = "【双防平台】通知：尊敬的双防用户，您好！" + createName + "发布的隐患未及时整改，限期日期为：" + limitDateStr + "，整改责任人为：" + dutyMan;
                    try {
                        if (wx1 != null && StringUtils.isNotBlank(wx1)) {
                            WebChatUtil.sendWeChatMessageToUser(wx1, content);
                        }
                        if (wx2 != null && StringUtils.isNotBlank(wx2)) {
                            WebChatUtil.sendWeChatMessageToUser(wx2, content);
                        }
                        String dutyman = hiddenDangerExamEntity.getDutyMan();
                        List<String> weChatPhones = systemService.findListbySql("select u.weChatPhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.realname='" + dutyman + "'");
                        if (weChatPhones != null && weChatPhones.size() > 0 && StringUtils.isNotBlank(weChatPhones.get(0))) {
                            WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0), content);
                        }
                        List<String> mobilePhone=systemService.findListbySql("select u.mobilePhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.realname='" + dutyman + "'");
                        if(mobilePhone != null && mobilePhone.size()>0 && StringUtils.isNotBlank(mobilePhone.get(0))){
                            SMSSenderUtil.sendSMS(content,mobilePhone.get(0), Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
                        }
                    } catch (Exception e) {
                        logger.error(ExceptionUtil.getExceptionMessage(e));
                    }
                    if (openlist.size() > 0) {
                        for (String openid : openlist) {
                            try {
                                postUpTemplate(openid, createName, hiddenLevelName, limitDateStr, dutyMan);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        //第一次升级,通知1、2
                        if (StringUtils.isNotBlank(pho1)) {
                            SMSSenderUtil.sendSMS("【双防平台】通知：尊敬的双防用户，您好！" + createName + "发布的隐患未及时整改，限期日期为：" + limitDateStr + "，整改责任人为：" + dutyMan, pho1, Constants.SMS_TYPE_HIDDEN_DANGER_EXPIRED);

                        }
                        if (StringUtils.isNotBlank(pho2)) {
                            SMSSenderUtil.sendSMS("【双防平台】通知：尊敬的双防用户，您好！" + createName + "发布的隐患未及时整改，限期日期为：" + limitDateStr + "，整改责任人为：" + dutyMan, pho2, Constants.SMS_TYPE_HIDDEN_DANGER_EXPIRED);
                        }
                    }
                } else if (limitNumTemp <= 3) {
                    //企业微信通知
                    String content = "【双防平台】通知：尊敬的双防用户，您好！" + createName + "发布的隐患未及时整改，限期日期为：" + limitDateStr + "，整改责任人为：" + dutyMan;
                    try {
                        if (wx1 != null && StringUtils.isNotBlank(wx1)) {
                            WebChatUtil.sendWeChatMessageToUser(wx1, content);
                        }
                        if (wx2 != null && StringUtils.isNotBlank(wx2)) {
                            WebChatUtil.sendWeChatMessageToUser(wx2, content);
                        }
                        if (wx3 != null && StringUtils.isNotBlank(wx3)) {
                            WebChatUtil.sendWeChatMessageToUser(wx3, content);
                        }
                        String dutyman = hiddenDangerExamEntity.getDutyMan();
                        List<String> weChatPhones = systemService.findListbySql("select u.weChatPhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.realname='" + dutyman + "'");
                        if (weChatPhones != null && weChatPhones.size() > 0 && StringUtils.isNotBlank(weChatPhones.get(0))) {
                            WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0), content);
                        }
                        List<String> mobilePhone=systemService.findListbySql("select u.mobilePhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.realname='" + dutyman + "'");
                        if(mobilePhone != null && mobilePhone.size()>0 && StringUtils.isNotBlank(mobilePhone.get(0))){
                            SMSSenderUtil.sendSMS(content,mobilePhone.get(0), Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
                        }
                    } catch (Exception e) {
                        logger.error(ExceptionUtil.getExceptionMessage(e));
                    }
                    if (openlist.size() > 0) {
                        for (String openid : openlist) {
                            try {
                                postUpTemplate(openid, createName, hiddenLevelName, limitDateStr, dutyMan);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        //多次升级通知1、2、3
                        if (StringUtils.isNotBlank(pho1)) {
                            SMSSenderUtil.sendSMS("【双防平台】通知：尊敬的双防用户，您好！" + createName + "发布的隐患未及时整改，限期日期为：" + limitDateStr + "，整改责任人为：" + dutyMan, pho1, Constants.SMS_TYPE_HIDDEN_DANGER_EXPIRED);
                        }
                        if (StringUtils.isNotBlank(pho2)) {
                            SMSSenderUtil.sendSMS("【双防平台】通知：尊敬的双防用户，您好！" + createName + "发布的隐患未及时整改，限期日期为：" + limitDateStr + "，整改责任人为：" + dutyMan, pho2, Constants.SMS_TYPE_HIDDEN_DANGER_EXPIRED);
                        }
                        if (StringUtils.isNotBlank(pho3)) {
                            SMSSenderUtil.sendSMS("【双防平台】通知：尊敬的双防用户，您好！" + createName + "发布的隐患未及时整改，限期日期为：" + limitDateStr + "，整改责任人为：" + dutyMan, pho3, Constants.SMS_TYPE_HIDDEN_DANGER_EXPIRED);
                        }
                    }
                }


                limitDateStr = limitDateStr + " 23:59:59";
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    limitDate = sdf.parse(limitDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long limitDateLong = limitDate.getTime();
                Long createDateLong = createDate.getTime();

                //整改周期
                Long zhouqi = limitDateLong - createDateLong;
                //计算下次升级时间
                Long nextTime = runDate.getTime() + zhouqi;
                Date nextDate = new Date(nextTime);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(nextDate);
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH) + 1;
                int day = calendar.get(calendar.DATE);
                int hour = calendar.get(calendar.HOUR_OF_DAY);
                int minute = calendar.get(calendar.MINUTE);
                int second = calendar.get(calendar.SECOND);

                StringBuffer sb = new StringBuffer();
                sb.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month).append(" ").append("? ").append(year);
                //添加下次升级任务
                qrtzManagerServiceI.modifyJobTime(scheduler, trigger.getName(), trigger.getGroup(), sb.toString());
            }
        }
    }

    /**
     * 隐患升级微信公众号发送模板
     */
    public JSONObject postUpTemplate(String openid, String createName, String hiddenLevelName, String limitDateStr, String dutyMan) throws IOException {
        JSONObject jo = new JSONObject();
        String access_token = "";
        String sql = "select accessToken from t_b_token";
        List<String> tmpList = systemService.findListbySql(sql);
        if (!tmpList.isEmpty() && tmpList.size() > 0) {
            access_token = tmpList.get(0);
        } else {
            String appid = ResourceUtil.getConfigByName("appID");
            String appsecret = ResourceUtil.getConfigByName("appsecret");
            access_token = weChartGetToken.getAccessToken(appid, appsecret);
        }
        String templateUp_id = ResourceUtil.getConfigByName("templateUp_id");
        JSONObject postJO = new JSONObject();
        JSONObject text = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject keyword1 = new JSONObject();
        JSONObject keyword2 = new JSONObject();

        JSONObject remark = new JSONObject();

//        openid="oHNqj1Az4-kNsxgpen5FT8CtvR0M";
//        createName="习近平";
//        limitDateStr="1997年";
//        dutyMan="薄熙来";

        postJO.put("touser", openid);
        postJO.put("template_id", templateUp_id);
        first.put("value", createName);
        first.put("color", "#007f80");
        keyword1.put("value", hiddenLevelName);
        keyword1.put("color", "#007f80");
        keyword2.put("value", limitDateStr);
        keyword2.put("color", "#007f80");
        remark.put("value", dutyMan);
        remark.put("color", "#007f80");

        text.put("first", first);
        text.put("keyword1", keyword1);
        text.put("keyword2", keyword2);
        text.put("remark", remark);
        postJO.put("data", text);

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;

        try {
            String returnData = HttpClientUtils.post(url, postJO.toString());
            jo = JSONObject.fromObject(returnData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }
}
