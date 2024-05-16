package com.sdzk.buss.web.system.entity;

/**
 * Created by Administrator on 17-9-23.
 */
public class LoginCountVO {

    //用户名
    private String userName;
    //真实姓名
    private String realName;
    //日期
    private String yemoday;
    //登录次数
    private String count;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getYemoday() {
        return yemoday;
    }

    public void setYemoday(String yemoday) {
        this.yemoday = yemoday;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
