package org.jeecgframework.web.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendangerhistory.entity.TBHiddenDangerHistoryEntity;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * Author：张赛超
 * Created by Lenovo on 17-7-12.
 */
@Service("updateDataDic")
public class UpdateDataDic {
    @Autowired
    private SystemService systemService;

    //检测空值，将空值转换为空字符串
    private static String nullToEmpty(String nte){
        if (nte == null || nte == "" || nte.equals("null") || nte == "null"){
            nte = "";
        }
        return nte;
    }

    /*
     * 这个方法没使用
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
    */

    //返回当前系统时间
    public static Date nowTime(){
        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
//        String nowTime = dateFormat.format(now);
//        return nowTime;
        return now;
    }



    public String save(String json, String msg){

//        List<TSTypegroup> dicGroupList = new ArrayList();
//        List<TSType> dicTypeList = new ArrayList();

        try {

            JSONObject object = JSONObject.parseObject(json);
            String code = "";
            if (object != null){
                code = object.getString("code");
                msg = object.getString("message");
            }

            if(code.equals("200")){
                JSONArray data = object.getJSONArray("data");   //得到data内容，转为json数组

                for (int i=0; i<data.size(); i++){

                    JSONObject oneData = (JSONObject) data.get(i);

                    String dicGroupCode = nullToEmpty(oneData.getString("typegroupcode"));
                    String dicGroupName = nullToEmpty(oneData.getString("typegroupname"));
                    /**
                     * 首先判断本地是否有此groupType
                     * */
                    CriteriaQuery typeGroupCq = new CriteriaQuery(TSTypegroup.class);
                    typeGroupCq.eq("typegroupcode", dicGroupCode);
                    typeGroupCq.add();
                    List<TSTypegroup> dicGroup = systemService.getListByCriteriaQuery(typeGroupCq, false);
                    //这个dicGroup将会返回一条typecode，我们需要获取它的id
                    if(dicGroup != null && dicGroup.size() > 0){        //如果此条groupType存在，那么将其删除重新添加
                        for(TSTypegroup tsTypegroup : dicGroup){        //删除t_s_type中的数据
                            String typeGroupId = tsTypegroup.getId();
                            String delTypeSql = "delete from t_s_type where typegroupid in ('" + typeGroupId + "')";
                            systemService.executeSql(delTypeSql);
                        }
                        //删除t_s_typegroup中的数据
                        String delTypeGroupSql = "delete from t_s_typegroup where typegroupcode in ('" + dicGroupCode + "')";
                        systemService.executeSql(delTypeGroupSql);
                    }

                    //插入数据
                    //上一步已经删除了存在的字典值，插入数据肯定没问题
                    //获取types的值并对其进行处理
                    TSTypegroup typegroupEntity = new TSTypegroup();
                    typegroupEntity.setTypegroupcode(dicGroupCode);
                    typegroupEntity.setTypegroupname(dicGroupName);
                    typegroupEntity.setCreateDate(nowTime());
                    typegroupEntity.setCreateName("管理员");
                    typegroupEntity.setOrigin(Constants.TYPE_GROUP_ORIGIN_MJJ);

                    List<TSType> typeEntityList = new ArrayList<TSType>();
                    JSONArray types = oneData.getJSONArray("types");
                    for (int j=0; j<types.size(); j++){
                        JSONObject oneType = (JSONObject) types.get(j);

                        TSType dicType = new TSType();
                        dicType.setTypecode(oneType.getString("typecode"));
                        dicType.setTypename(oneType.getString("typename"));
                        dicType.setTSTypegroup(typegroupEntity);
                        dicType.setCreateName("管理员");
                        dicType.setCreateDate(nowTime());

                        typeEntityList.add(dicType);
                    }
                    systemService.save(typegroupEntity);
                    systemService.batchSave(typeEntityList);
                }
            }

        }catch (JsonIOException e) {
            msg = "同步出错，请重新同步！";
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            msg = "同步出错，请重新同步！";
            e.printStackTrace();
        }

        return msg;
    }

}
/**
 * QQ：1228310398
 * */