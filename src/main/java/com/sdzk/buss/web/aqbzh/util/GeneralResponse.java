package com.sdzk.buss.web.aqbzh.util;



import java.io.Serializable;


public class GeneralResponse<T> implements Serializable {

    /**
     * 响应代码
     */
    private String code;
    /**
     * 面向用户的消息
     */
    private String msg;
    /**
     * 返回给前端的数据
     */
    private T datas;

    private GeneralResponse(String code) {
        this.code = code;
    }

    private GeneralResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private GeneralResponse(String code, T datas) {
        this.code = code;
        this.datas = datas;
    }

    private GeneralResponse(String code, String msg, T datas) {
        this.code = code;
        this.msg = msg;
        this.datas = datas;
    }

    public boolean isSucess() {
        return this.code.equals(ResponseCode.SUCCESS.getCode());
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getDatas() {
        return datas;
    }

    public static <T> GeneralResponse createBySuccess() {
        return new GeneralResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> GeneralResponse createBySuccessMessage(String msg) {
        return new GeneralResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> GeneralResponse createBySuccess(T datas) {
        return new GeneralResponse<T>(ResponseCode.SUCCESS.getCode(), datas);
    }

    public static <T> GeneralResponse createBySuccess(String msg, T datas) {
        return new GeneralResponse<T>(ResponseCode.SUCCESS.getCode(), msg, datas);
    }

    public static <T> GeneralResponse createByError() {
        return new GeneralResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> GeneralResponse createByError(String msg) {
        return new GeneralResponse<T>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> GeneralResponse createByError(T datas) {
        return new GeneralResponse<T>(ResponseCode.ERROR.getCode(), datas);
    }

    public static <T> GeneralResponse createByError(String errorCode, String errormessage) {
        return new GeneralResponse<T>(errorCode, errormessage);
    }

    public static <T> GeneralResponse createByError(String errorCode, T datas) {
        return new GeneralResponse<T>(errorCode, datas);
    }

}
