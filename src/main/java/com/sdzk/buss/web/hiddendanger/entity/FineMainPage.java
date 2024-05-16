package com.sdzk.buss.web.hiddendanger.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-4-1.
 */
public class FineMainPage implements java.io.Serializable{
    private List<TBFineInfoEntity> fineList = new ArrayList<TBFineInfoEntity>();

    public List<TBFineInfoEntity> getFineList() {
        return fineList;
    }

    public void setFineList(List<TBFineInfoEntity> fineList) {
        this.fineList = fineList;
    }
}
