package com.sdzk.buss.web.riskalert.entity;

/**
 * Created by Administrator on 17-2-10.
 */
public class HiddenDangerVioVO {
    private String unitname;
    private String hiddennum;
    private String vionum;
    private String sumnum;
    private String addressname;
    private String checknum;

    public String getChecknum() {
        return checknum;
    }

    public void setChecknum(String checknum) {
        this.checknum = checknum;
    }

    public String getAddressname() {
        return addressname;
    }

    public void setAddressname(String addressname) {
        this.addressname = addressname;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getHiddennum() {
        return hiddennum;
    }

    public void setHiddennum(String hiddennum) {
        this.hiddennum = hiddennum;
    }

    public String getVionum() {
        return vionum;
    }

    public void setVionum(String vionum) {
        this.vionum = vionum;
    }

    public String getSumnum() {
        return sumnum;
    }

    public void setSumnum(String sumnum) {
        this.sumnum = sumnum;
    }
}
