package com.sddb.buss.identification.service;


import org.jeecgframework.core.common.model.json.AjaxJson;

/**
 * Created by lenovo on 2019/1/8.
 */
public interface RiskIdentificationServiceI {
    AjaxJson riskIdentificationReportToGroup(String ids);
}
