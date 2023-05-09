package com.liyf.boot.mail.util;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;

/**
 * @author: Seven.wk
 * @description: 辅助工具类
 * @create: 2018/07/04
 */
public class HttpUtils {
    /**
     * 向目的URL发送post请求
     * @param url       目的url
     * @param params    发送的参数
     * @return  ResultVO
     */
    public static String post(String url, HttpMethod method, JSONObject json) throws IOException {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10*1000);
        requestFactory.setReadTimeout(10*1000);
        RestTemplate client = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8);
        HttpEntity<String> requestEntity = new HttpEntity<String>(json.toString(), headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }
    public static  void  main(String args[]){
        try{
            //api url地址
            String url = "http://127.0.0.1:8081/json/data";
            //post请求
            HttpMethod method =HttpMethod.POST;
            JSONObject json = new JSONObject();
            json.put("name", "wangru");
            json.put("sex", "男");
            json.put("age", "27");
            json.put("address", "Jinan China");
            json.put("time", new Date());
            System.out.print("发送数据："+json.toString());
            //发送http请求并返回结果
            String result = HttpUtils.post(url,method,json);
            System.out.print("接收反馈："+result);
        }catch (Exception e){
        }
    }

}