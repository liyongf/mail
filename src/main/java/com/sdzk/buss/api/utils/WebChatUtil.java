package com.sdzk.buss.api.utils;

import com.alibaba.fastjson.JSONException;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.HttpClientUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;

import java.io.IOException;

/**
 * Created by xuran on 2018/5/3.
 */
public class WebChatUtil {

    public static String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={id}&corpsecret={secrect}";
    public static String access_token = null;
    private static final Logger logger = Logger.getLogger(WebChatUtil.class);
    private static String ERROR_CODE_0 = "0";
    private static int callCnt = 0;
    private static String ERROR_CODE_40014 = "40014";


    /***获取token方法二  已验证*/
    private static JSONObject doGetStr(String url) throws ClientProtocolException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();//获取DefaultHttpClient请求
        HttpGet httpGet = new HttpGet(url);//HttpGet将使用Get方式发送请求URL
        JSONObject jsonObject = null;
        HttpResponse response = client.execute(httpGet);//使用HttpResponse接收client执行httpGet的结果
        HttpEntity entity = response.getEntity();//从response中获取结果，类型为HttpEntity
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");//HttpEntity转为字符串类型
            jsonObject = JSONObject.fromObject(result);//字符串类型转为JSON类型
        }
        return jsonObject;
    }

    private static String getAccessToken(String appid, String appsecret) throws IOException {
        String url = access_token_url.replace("{id}", appid).replace("{secrect}", appsecret);//将URL中的两个参数替换掉
        JSONObject jsonObject = doGetStr(url);//使用刚刚写的doGet方法接收结果
        if (jsonObject != null) { //如果返回不为空，将返回结果封装进AccessToken实体类
            try {
                String errcode = jsonObject.getString("errcode");
                if (ERROR_CODE_0.equals(errcode)) {
                    access_token = jsonObject.getString("access_token");//取出access_token
                } else {
                    logger.error(jsonObject.getString("errmsg"));
                }
            } catch (JSONException e) {
                access_token = null;
                // 获取token失败
            }
        }
        return access_token;
    }

    public static String getToken() {
        if (access_token == null) {
            String appid = ResourceUtil.getConfigByName("CorpID");
            String appsecret = ResourceUtil.getConfigByName("corpsecret");
            try {
                access_token = getAccessToken(appid, appsecret);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return access_token;
    }

    public static JSONObject sendWeChatMessageToUser(String touser, String contents){
        JSONObject jo = sendWeChatMessage(touser, null, null, contents);
        String errcode = jo.getString("errcode");
        if (!ERROR_CODE_0.equals(errcode)){
            logger.error("微信信息发送失败，失败原因："+jo.getString("errmsg")+";发送内容为："+contents+"。");
        }
        callCnt = 0;
        return jo;
    }

    /**微信企业号推送消息*/
    public static JSONObject sendWeChatMessage(String touser, String toparty, String totag, String contents){
        JSONObject jo = new JSONObject();
        getToken();
        String url="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={ACCESS_TOKEN}";
        url = url.replace("{ACCESS_TOKEN}", access_token);
        String agentid = ResourceUtil.getConfigByName("agentid");
        JSONObject Jo=new JSONObject();
        Jo.put("touser",touser);
        if (StringUtil.isNotEmpty(toparty)) {
            Jo.put("toparty",toparty);
        }
        if (StringUtil.isNotEmpty(totag)) {
            Jo.put("totag",totag);
        }
        Jo.put("msgtype","text");
        Jo.put("agentid",agentid);
        JSONObject content =new JSONObject();
        content.put("content",contents);
        Jo.put("text",content);

        try {
            String returnData = HttpClientUtils.post(url, Jo.toString());
            jo = JSONObject.fromObject(returnData);
            String errcode = jo.getString("errcode");
            if (!ERROR_CODE_0.equals(errcode)) {//token过期时重新获取再次请求
                if (callCnt < 5) {
                    callCnt++;
                    logger.error("token过期，重新获取, 获取时间"+ DateUtils.getTimestamp());
                    access_token = null;
                    jo = sendWeChatMessage(touser, toparty, totag, contents);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return  jo;
    }
}
