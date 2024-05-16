package com.sddb.buss.riskdata.service;

import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;

import javax.servlet.http.HttpServletRequest;

public interface HazardFactorsServiceI  {
    void importDataSava(ExcelImportResult<HazardFactorsEntity> result);
    public void getModularHazardRelList(HttpServletRequest request, DataGrid dataGrid);
    AjaxJson hazardFactorsReportToGroup(String ids);
}
