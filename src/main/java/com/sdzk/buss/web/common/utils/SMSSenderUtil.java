package com.sdzk.buss.web.common.utils;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.sms.entity.TBSmsEntity;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * Created by hanshifeng on 17-9-20.
 */
public class SMSSenderUtil {

    private static final Logger logger = Logger.getLogger(SMSSenderUtil.class);

    /**
     * 发短信
     * @param content
     * @return
     */
    public static String sendSMS(String content,String mobiles) {
        String info = null;
        try{
            String url = ResourceUtil.getConfigByName("smsSendUrl");
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod(url);
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
            post.addParameter("userid", "501");
            post.addParameter("account", "hanshifeng");
            post.addParameter("password","a031511113");
            post.addParameter("mobile", mobiles);
            post.addParameter("content", content);
            post.addParameter("sendTime", "");
            post.addParameter("action", "send");
            post.addParameter("extno", "");
            httpclient.executeMethod(post);
            info = new String(post.getResponseBody(),"utf-8");
            System.out.println(info);
            return info;
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(ExceptionUtil.getExceptionMessage(e));
        }
        return null;
    }

    public static String sendSMS(String content, String mobile, String type) {
        String info = null;
        try{
            String mineManageRootUrl = ResourceUtil.getConfigByName("mineManageRootUrl");
            String sendSMSUrl = ResourceUtil.getConfigByName("sendSMSUrl");
            String url = mineManageRootUrl+sendSMSUrl;

            //获取集团名称和矿井名称
            String groupName = "集团";
            String mineName = "矿井";
            SystemService systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");
            String sql = "select value from t_b_config where name='groupName'";
            List<String> vList = systemService.findListbySql(sql);
            if(null!=vList&&vList.size()>0){
                groupName=vList.get(0);
            }
            sql = "select value from t_b_config where name='mineName'";
            vList = systemService.findListbySql(sql);
            if(null!=vList&&vList.size()>0){
                mineName=vList.get(0);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod(url);
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            post.addParameter("groupName", groupName);
            post.addParameter("mineName", mineName);
            post.addParameter("requestTime",sdf.format(new Date()));
            post.addParameter("type", type);
            post.addParameter("content", content);
            post.addParameter("phoneNumber", mobile);

            //添加本矿第一级组织机构名称
            sql = "select GROUP_CONCAT(departname) topDepartNames from t_s_depart where parentdepartid is null and delete_flag!='1'";
            List<String> topDepartNameList = systemService.findListbySql(sql);
            if(null!=topDepartNameList && topDepartNameList.size()>0){
                post.addParameter("topDepartNames", topDepartNameList.get(0));
            }

            //添加服务器设备信息（mac/ip/os）
            String serverInfo = "";
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            byte[] mac = ni.getHardwareAddress();
            String sIP = address.getHostAddress();
            String sMAC = "";
            for (int i = 0; i < mac.length; i++ ) {
                sMAC = sMAC + Integer.toHexString(mac[i] & 0xFF) + "-";
            }
            sMAC = sMAC.substring(0,sMAC.length()-1);
            serverInfo = serverInfo + "host/ip: "+sIP+",mac: "+sMAC;
            post.addParameter("serverInfo", serverInfo);

            httpclient.executeMethod(post);
            info = new String(post.getResponseBody(),"utf-8");
            if(!info.contains("请求成功")){
                TBSmsEntity tbSmsEntity = new TBSmsEntity();
                tbSmsEntity.setContent(content);
                tbSmsEntity.setType(type);
                tbSmsEntity.setMobilePhone(mobile);
                tbSmsEntity.setCreateDate(new Date());
                systemService.save(tbSmsEntity);
            }
            System.out.println(info);
            return info;
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(ExceptionUtil.getExceptionMessage(e));
        }
        return null;
    }
    public static void main(String[] args) {
        sendSMS("【安监部】通知：您有一条待整改的隐患，限期时间2017-9-21，请尽快处理！退订回T","18796260330",Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
    }
}
