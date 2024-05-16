package com.sddb.buss.identification.service;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lenovo on 2019/1/8.
 */
public interface ReportServiceI {
    public CriteriaQuery queryRiskReportList(HttpServletRequest request, DataGrid dataGrid);
    public CriteriaQuery queryHazardFactorReportList(HttpServletRequest request, DataGrid dataGrid);
}
