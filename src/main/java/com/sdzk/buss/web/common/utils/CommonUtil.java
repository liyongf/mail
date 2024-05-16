package com.sdzk.buss.web.common.utils;

import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
public class CommonUtil {

    private CommonUtil(){
    }

    /**
     * 根据岗位名称获取ID
     * @param postName
     * @return
     */
    public static String getPostIdByName(String postName) {
        String postId="";
        if(StringUtil.isNotEmpty(postName)){
            HttpSession session = ContextHolderUtils.getSession();
            Map<String,String> postMap = (Map)session.getAttribute("postMap");
            postId = postMap.get(postName);
        }
        return postId;
    }

    /**
     * 根据作业活动名称获取ID
     * @param activityName
     * @return
     */
    public static String getActivityIdByName(String activityName) {
        String activityId="";
        if(StringUtil.isNotEmpty(activityName)){
            HttpSession session = ContextHolderUtils.getSession();
            Map<String,String> activityMap = (Map)session.getAttribute("activityMap");
            activityId = activityMap.get(activityName);
        }
        return activityId;
    }

    /**
     * 根据第一类危险源名称获取ID
     * @param hazardName
     * @return
     */
    public static String getHazardIdByName(String hazardName) {
        String hazardId="";
        if(StringUtil.isNotEmpty(hazardName)){
            HttpSession session = ContextHolderUtils.getSession();
            Map<String,String> hazardMap = (Map)session.getAttribute("hazardMap");
            hazardId = hazardMap.get(hazardName);
        }
        return hazardId;
    }

    /**
     *  判断集团版传递过来的id长度，此系统UUID生成id长度均为27位，如果超过27位，那么就是矿版上报过去的id，拼接了一个mineCode
     *  如果刚好27位（没超过27位）那么说明这个id是集团版的id或者其他的问题
     *  超过27位的id需要裁剪去前面的mineCode然后将其保存
     * */
    public static String cutMineCode(String id){
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String ret = id;

        if (id.length()>27){
            if (StringUtil.isNotEmpty(mineCode)){
                ret = id.substring(mineCode.length());
            } else {
                ret = id.substring(27);     //如果mindeCode为空，那么直接裁剪掉前面27位
            }
        }

        return ret;
    }

    /**
     * 处理对象的属性，得到JSONObject
     */
    public static HashMap dealObjToMap(Object object, SimpleDateFormat dateFormat) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap result = new HashMap();
        Class<?> aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field:fields) {
            String name = field.getName();
            if("serialVersionUID".equals(name)){
                continue;
            }
            // 拼接方法名
            String firstCharUpper = name.substring(0, 1).toUpperCase();
            String invokeMethodName = "get" + firstCharUpper + name.substring(1);
            // 获取方法
            Method pendingInvokeMethod = aClass.getMethod(invokeMethodName);
            //field.getGenericType().getTypeName()获取属性类型名称，例如java.lang.String
            Object invokeResult = pendingInvokeMethod.invoke(object);
            if (null==invokeResult){
                result.put(name, "");
            } else if (invokeResult instanceof Date&&null!=dateFormat) {
                String date = dateFormat.format((Date) invokeResult);
                result.put(name, date);
            }  else {
                result.put(name, invokeResult.toString());
            }
        }
        return result;
    }

}
