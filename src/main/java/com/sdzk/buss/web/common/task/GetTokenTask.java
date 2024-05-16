package com.sdzk.buss.web.common.task;

import com.alibaba.fastjson.JSONArray;
import com.sdzk.buss.api.entity.WexinOpenId;
import com.sdzk.buss.api.service.WeChartGetToken;
import com.sdzk.buss.api.service.WexinOpenServiceI;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.HttpClientUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时从微信公众号获取token
 **/
@Service("getTokenTask")
public class GetTokenTask {
    // 第三方用户唯一凭证
    public static String appid = "";
    // 第三方用户唯一凭证密钥
    public static String appsecret = "";

    @Autowired
    private WeChartGetToken wegetToken;
    @Autowired
    private SystemService systemService;

    @Autowired
    private WeChartGetToken weChartGetToken;
    @Autowired
    private WexinOpenServiceI wexinOpenServiceI;


    public void saveToke() throws IOException {
        appid = ResourceUtil.getConfigByName("appID");
        appsecret = ResourceUtil.getConfigByName("appsecret");
        String accessToke = wegetToken.getAccessToken(appid, appsecret);

        //定时将token存入数据库中
        if (StringUtils.isNotBlank(accessToke)) {
            //执行sql保存 AccessToken到数据库
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowdate = dateFormat.format(date);

            String tokensql = "UPDATE t_b_token SET accessToken='" + accessToke + "' where id='123'";
            String timesql = "UPDATE t_b_token SET createTime='" + nowdate + "' where id='123';";
            systemService.updateBySqlString(tokensql);
            systemService.updateBySqlString(timesql);
        }
    }

    /**获取关注微信公众号用户列表*/
    public JSONObject getUserList(){

        String access_token= weChartGetToken.getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/user/get";

        JSONObject jo = new JSONObject();

        Map<String, String> params = new HashMap<>();
        params.put("access_token", access_token);
        params.put("next_openid", null);

        try {
            String returnData = HttpClientUtils.post(url, params, "UTF-8");
            jo = JSONObject.fromObject(returnData);
            net.sf.json.JSONArray openIds = jo.getJSONObject("data").getJSONArray("openid");
            WexinOpenId wexinOpenId ;
            List<String > list=new ArrayList<>();
            for(int i=0;i<openIds.size();i++){
                String opid= openIds.get(i).toString();
                if(StringUtils.isNotBlank(opid)){
                    list.add(openIds.get(i).toString());
                }
            }
            for( String openId :list){
                String sql="select count(1) from t_b_wexinopenid where openId='"+openId+"'";
                Long count = systemService.getCountForJdbc(sql);
                if(count==0){
                    wexinOpenId=new WexinOpenId();
                    wexinOpenId.setOpenId(openId);
                    wexinOpenId.setWexinStatus("0");
                    wexinOpenServiceI.save(wexinOpenId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;

    }
    /**根据关注用户openId获取用户详细信息   获取用户最大值为100   需要判断数据是否要分批处理*/
    public void postUserList() {
        String openIdsql = "select openId from t_b_WexinOpenId where nickname is NULL";
        List<String> dataList = systemService.findListbySql(openIdsql);

        if (null != dataList && dataList.size() > 0) {
            //限制条数，最大99
            int pointsDataLimit = 99;
            Integer size = dataList.size();
            //如果数据大于99，需要分批处理
            if (pointsDataLimit < size) {
                int part = size / pointsDataLimit;//分批条数
                //分批处理
                for (int i = 0; i < part; i++) {
                    List<String> listPage = dataList.subList(0, pointsDataLimit);
                    postList(listPage);
                    dataList.subList(0, pointsDataLimit).clear();
                }
                //分批之后剩下的数据
                if (!dataList.isEmpty()) {
                    postList(dataList);
                }
                //不用分批
            } else {
                postList(dataList);
            }

        }
    }

    public void postList(List <String> dataList){
        String access_token= weChartGetToken.getToken();
        JSONObject jo = new JSONObject();
        JSONArray userListJA = new JSONArray();

        for( String openId : dataList){
            JSONObject u=new JSONObject();
            u.put("openid",openId);
            u.put("lang","zh_CN");
            userListJA.add(u);
        }
        try {

            String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="+access_token;
            JSONObject userlist=new JSONObject();
            userlist.put("user_list",userListJA.toString());
            String returnData = HttpClientUtils.post(url, userlist.toString());
            String json = new String(returnData.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(json);
            jo = JSONObject.fromObject(json);
            net.sf.json.JSONArray arraylist=jo.getJSONArray("user_info_list");
            if(arraylist.size()>0) {
                for (int i = 0; i < arraylist.size(); i++) {
                    // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    JSONObject job = arraylist.getJSONObject(i);
                    // 得到 每个对象中的属性值
                    String openid= job.get("openid").toString();
                    String nickname= job.get("nickname").toString();
                    String sql="select count(1) from t_b_wexinopenid where nickname='"+nickname+"'";
                    Long count = systemService.getCountForJdbc(sql);
                    if(count==0){
                        String updatesql="update t_b_wexinopenid set nickname='"+nickname+"' where openId='"+openid+"'";
                        systemService.updateBySqlString(updatesql);
                    }
                }
            }

        }catch (Exception e){

            e.printStackTrace();
        }

    }


}
