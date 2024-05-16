package com.sdzk.buss.web.yearreport.service.impl;

import com.sdzk.buss.web.yearreport.service.TBYearReportServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tBYearReportService")
@Transactional
public class TBYearReportServiceImpl extends CommonServiceImpl implements TBYearReportServiceI {
	
}