package com.sdzk.buss.api.model;

/**
 * Created by dell on 2017/7/8.
 * 对外接口返回信息
 */
public class ApiResultJson {
    private String message = "请求成功";// 提示信息
    private Object data = null;// 其他信息
    private String code = "200";
    public static final String CODE_200="200";
    public static final String CODE_201="201";
    public static final String CODE_202="202";//自定义返回信息
    public static final String CODE_400="400";
    public static final String CODE_401="401";
    public static final String CODE_403="403";
    public static final String CODE_500="500";
    public static final String CODE_200_MSG="请求成功";
    public static final String CODE_201_MSG="数据已同步, 无需再次同步";
    public static final String CODE_400_MSG="缺少请求参数";
    public static final String CODE_401_MSG="token校验失败，非法请求";
    public static final String CODE_403_MSG="用户未登录";
    public static final String CODE_500_MSG="服务器内部错误";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ApiResultJson(){
    }

    public ApiResultJson(String code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public ApiResultJson(Object data){
        this.data = data;
    }
}
