package com.sdzk.buss.api.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.api.service.WeChartGetToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.List;

@Service("weChartGetToken")
@Transactional
public class WeChartGetTokenImpl implements WeChartGetToken {

    @Autowired
    private SystemService systemService;
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    /***获取token方法二  已验证*/
    public JSONObject doGetStr(String url) throws ClientProtocolException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();//获取DefaultHttpClient请求
        HttpGet httpGet = new HttpGet(url);//HttpGet将使用Get方式发送请求URL
        JSONObject jsonObject = null;
        HttpResponse response = client.execute(httpGet);//使用HttpResponse接收client执行httpGet的结果
        HttpEntity entity = response.getEntity();//从response中获取结果，类型为HttpEntity
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");//HttpEntity转为字符串类型
            jsonObject = JSONObject.parseObject(result);//字符串类型转为JSON类型
        }
        return jsonObject;
    }

    public String getAccessToken(String appid, String appsecret) throws IOException {
        String accessToken = null;
        String url = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);//将URL中的两个参数替换掉
        JSONObject jsonObject = doGetStr(url);//使用刚刚写的doGet方法接收结果
        if (jsonObject != null) { //如果返回不为空，将返回结果封装进AccessToken实体类
            try {
                accessToken = (jsonObject.getString("access_token"));//取出access_token
//        token.setExpiresIn(jsonObject.getInt("expires_in"));//取出access_token的有效期
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
            }
        }
        return accessToken;
    }


    public String getToken() {
        String accesstoken = "";
        String sql = "select accessToken from t_b_token";
        List<String> tmpList = systemService.findListbySql(sql);
        if (!tmpList.isEmpty() && tmpList.size() > 0) {
            accesstoken = tmpList.get(0);
        } else {
            String appid = ResourceUtil.getConfigByName("appID");
            String appsecret = ResourceUtil.getConfigByName("appsecret");
            try {
                accesstoken = getAccessToken(appid, appsecret);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return accesstoken;
    }
}