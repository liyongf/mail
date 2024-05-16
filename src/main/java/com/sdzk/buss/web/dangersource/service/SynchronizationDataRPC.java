package com.sdzk.buss.web.dangersource.service;

import com.google.gson.*;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;

import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.json.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Lenovo on 17-7-8.
 * Author：张赛超
 */
@Service("synchronizationDataRPC")
public class SynchronizationDataRPC {
    @Autowired
    private SystemService systemService;

    //检测空值，将空值转换为空字符串
    private static String nullToEmpty(String nte){
        if (nte == null || nte == "" || nte.equals("null") || nte == "null"){
            nte = "";
        }
        return nte;
    }

    //将String型日期转换为Date型日期
    public static Date stringToDate(String std) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        if(std.equals("null") || StringUtil.isEmpty(std)){
            return date;
        }else{
            try {
                date = sdf.parse(std);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
    //计算风险值
    public static String calculateRiskValue(String num1, String num2){
        String value = "";
        if (num1.equals("")){
            return value;
        }
        if (num2.equals("")){
            return value;
        }
        value = String.valueOf(Integer.parseInt(num1) * Integer.parseInt(num2));
        return value;
    }


    public String save (String json, String msg) {

        List<TBDangerSourceEntity> entityList = new ArrayList<TBDangerSourceEntity>();

            try {
                JSONObject object= JSONObject.fromObject(json);  //创建JsonObject对象

                String code = object.getString("code");     //如果code=200，则数据正确可用

                if ("200".equals(code)){

                    JSONArray data = object.getJSONArray("data");    //得到为json的数组
                    for(int i=0;i<data.size();i++){
                        JSONObject dataArray = (JSONObject)data.get(i);     //再次将获得的data转换为JSON对象
                        /**
                         *这是示范代码，直接用nullToEmpty检测空值
                         *   vo.setName(nullToEmpty(data.optString("unit_name")));
                         */

                        TBDangerSourceEntity tBDangerSourceEntity = new TBDangerSourceEntity();
                        tBDangerSourceEntity.setId(nullToEmpty(dataArray.optString("id")));
                        tBDangerSourceEntity.setYeMhazardDesc(nullToEmpty(dataArray.optString("yeMhazardDesc")));
                        tBDangerSourceEntity.setYeProfession(nullToEmpty(dataArray.optString("yeProfession")));
                        tBDangerSourceEntity.setYeAccident(nullToEmpty(dataArray.optString("yeAccident")));
                        tBDangerSourceEntity.setYeReference(nullToEmpty(dataArray.optString("yeReference")));
                        tBDangerSourceEntity.setYeLocation(nullToEmpty(dataArray.optString("yeLocation")));
                        tBDangerSourceEntity.setYeDistance(nullToEmpty(dataArray.optString("yeDistance")));
                        tBDangerSourceEntity.setYeSurrounding(nullToEmpty(dataArray.optString("yeSurrounding")));
                        tBDangerSourceEntity.setYeStandard(nullToEmpty(dataArray.optString("yeStandard")));
                        tBDangerSourceEntity.setYeMonitor(nullToEmpty(dataArray.optString("yeMonitor")));
                        tBDangerSourceEntity.setYeEmergency(nullToEmpty(dataArray.optString("yeEmergency")));
                        tBDangerSourceEntity.setYePossiblyHazard(nullToEmpty(dataArray.optString("yePossiblyHazard")));
                        tBDangerSourceEntity.setYeProbability(nullToEmpty(dataArray.optString("yeProbability")));
                        tBDangerSourceEntity.setYeCost(nullToEmpty(dataArray.optString("yeCost")));
                        tBDangerSourceEntity.setYeHazardCate(nullToEmpty(dataArray.optString("yeHazardCate")));
                        tBDangerSourceEntity.setYeRiskGrade(nullToEmpty(dataArray.optString("yeRiskGrade")));
                        tBDangerSourceEntity.setYeCaseNum(nullToEmpty(dataArray.optString("yeCaseNum")));
                        tBDangerSourceEntity.setYeRecognizeTime(stringToDate(dataArray.optString("yeRecognizeTime")));
                        tBDangerSourceEntity.setManageMeasure(nullToEmpty(dataArray.optString("manageMeasure")));
                        tBDangerSourceEntity.setOrigin("1");
                        //计算风险值
                        String syeP = nullToEmpty(dataArray.optString("yeProbability"));     String syeC = nullToEmpty(dataArray.optString("yeCost"));
                        tBDangerSourceEntity.setRiskValue(calculateRiskValue(syeP, syeC));

                        entityList.add(tBDangerSourceEntity);
                    }
                }

                /**
                 * 下面这段代码可以自动根据data匹配对应的Entity中的值然后保存对应数据
                 * 也可以手动自己塞进去
                 * */
 //               TBDangerSourceEntity[] entitys = (TBDangerSourceEntity[]) JSONHelper.json2Array(object.getString("data"), TBDangerSourceEntity.class);
//                上面这行代码好用但是不灵活，不能随意修改
                /*******************************************************************/

                /**
                 * 每隔200条提交删除一次
                 * */
                if (entityList != null && entityList.size() > 0){
                    String sql = "delete from t_b_danger_source where id in ";
                    StringBuffer ids = new StringBuffer("('");
                    int i = 0;
                    for (TBDangerSourceEntity entity : entityList) {
                        i++;
                        ids.append(entity.getId()).append("','");
                        if (i%200==0){
//                            ids.deleteCharAt(ids.length() - 1);
                            ids.append("')");

                            systemService.executeSql(sql+ids.toString());
                            ids = new StringBuffer("('");
                        }
//                        systemService.executeSql("delete from t_b_danger_source where id = '" + entity.getId() + "'");
//                        systemService.save(entity);
                    }
//                    ids.deleteCharAt(ids.length() - 1);
                    ids.append("')");
                    systemService.executeSql(sql+ids.toString());

                    systemService.batchSave(entityList);
                    msg = "同步成功";
                }

                if("201".equals(code)){
                    msg = "数据已同步, 无需再次同步";
                }

            } catch (JsonIOException e) {
                msg = "同步出错，请重新同步！";
                e.printStackTrace();
            } catch (JsonSyntaxException e) {
                msg = "同步出错，请重新同步！";
                e.printStackTrace();
            }

            return msg;
        }
}
