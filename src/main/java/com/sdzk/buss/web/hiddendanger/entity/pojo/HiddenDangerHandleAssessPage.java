package com.sdzk.buss.web.hiddendanger.entity.pojo;

import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;

import java.util.List;

/**
 * Created by Administrator on 16-5-10.
 */
public class HiddenDangerHandleAssessPage {
    private List<TBHiddenDangerHandleEntity> issues;

    public List<TBHiddenDangerHandleEntity> getIssues() {
        return issues;
    }

    public void setIssues(List<TBHiddenDangerHandleEntity> issues) {
        this.issues = issues;
    }
}
