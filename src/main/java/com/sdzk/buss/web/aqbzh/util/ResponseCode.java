package com.sdzk.buss.web.aqbzh.util;

public enum ResponseCode {

    SUCCESS("200", "OK"),//成功
    ERROR("1", "ERROR"),//错误
    ADDSIGNERROR("110", "加号错误"),//文案加号错误
    NEED_LOGIN("10", " NEED_LOGIN"),//需要登入
    //请求参数错误
    REQUEST_ARGUMENTS_ERROR("003", "请求参数错误"),
    //未知错误
    OTHER_ERROR("400", "其他错误"),
    //未知错误
    UNAUTHORIZED("401", "未授权"),
    //未知错误
    LOCKING_USER_ERR("11", "账号已被锁定,请联系管理员"),
    USER_NEED_AUTHORITIES("201", "用户未登录"),
    USER_LOGIN_FAILED("202", "用户账号或密码错误"),
    USER_LOGIN_SUCCESS("203", "用户登录成功"),
    USER_NO_ACCESS("204", "用户无权访问"),
    USER_LOGOUT_SUCCESS("205", "用户退出成功"),
    USER_PHONE_SUCCESS("208", "此手机号不存在"),
    TOKEN_IS_BLACKLIST("206", "此token为黑名单"),
    LOGIN_IS_OVERDUE("207", "登录已失效"),
    ONLINE_COUNT_ONE("209", "当前账号已有一人登陆，是否覆盖"),
    ONLINE_COUNT_TOP("210", "当前账号登录人数已达上限"),
    INFO("100204", "请求受理成功，响应数据为空！"),
    UNAUTHENTIC("100401", "无权访问，当前是匿名访问，请先登录！"),
    SMSERROR("100909", "短信验证码已发送！"),
    SMS_CODE_ERROR("100910", "短信验证码错误！"),
    SMS_PHONE_ERROR("100911", "手机号错误！"),
    UPDATE_PASSWORD_ERROR("100912", "修改密码失败！"),
    LANDED("100913", "修改密码失败！"),
    SMS_PHONE_NULL("100914", "短信验证码为空！"),
    PWS_NULL("100915", "密码为空！"),
    NOTFOUND("100404", "服务器未找到资源"),

    EXCEPTION_NULL_POINTER("E1000", "数据异常"),
    EXCEPTION_CLASS_NOT_FOUND("E1001", "类找不到异常"),
    EXCEPTION_ARITHMETIC("E1002", "计算异常"),
    EXCEPTION_ARRAY_INDEX_OUT_OF_BOUNDS("E1003", "下标越界异常"),
    EXCEPTION_ILLEGAL_ARGUMENT("E1004", "非法参数异常"),
    EXCEPTION_CLASS_CAST("E1005", "类型转换异常"),

    FINANCE_PASSWORD_NULL("200000", "财务信息密码为空"),
    FINANCE_INFO_NULL("200001", "财务信息不存在"),
    FINANCE_PASSWORD_ERROR("200002", "财务密码错误"),
    FINANCE_ACCOUNT_EXIST("200003", "提现账户已存在");

    private final String code;
    private final String desc;

    /**
     * 根据code获取去value
     *
     * @param code
     * @return
     */
    public static String getValueByCode(String code) {
        for (ResponseCode responseCode : ResponseCode.values()) {
            if (code.equals(responseCode.getCode())) {
                return responseCode.getDesc();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ResponseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
