package com.liyf.boot.mail.util;

import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;

import java.util.HashMap;
import java.util.Map;

public class Cnpc {
    public static void main(String[] args) {
        String HkHost = "192.168.99.100";
        String HkAppKey = "26591636";
        String HkAppSecret = "Vjf3hLJtgmt7R1soskab";
        ArtemisConfig.host =HkHost; //平台（nginx）IP 和端口
        ArtemisConfig.appKey =HkAppKey; //合作方 key
        ArtemisConfig.appSecret =HkAppSecret;//合作方 Secret
        final String getSecurityApi = "/artemis" + "/api/video/v2/cameras/previewURLs";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getSecurityApi);
            }
        };
        //post请求
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        json.put("streamType",0);
        json.put("protocol","rtsp");
        json.put("transmode",1);
        json.put("expand","transcode=0");
        json.put("streamform","rtp");
        String body = json.toJSONString();
        com.alibaba.fastjson.JSONObject res= new com.alibaba.fastjson.JSONObject();
        try {
            String result = ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json",null);// post请求application/json类型参数
            if(result!=null){
                res=com.alibaba.fastjson.JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(res.toJSONString());
    }
}
