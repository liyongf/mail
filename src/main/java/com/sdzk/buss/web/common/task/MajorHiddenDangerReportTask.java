package com.sdzk.buss.web.common.task;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendangerhistory.service.TBHiddenDangerHistoryServiceI;
import com.sdzk.buss.web.majorhiddendanger.service.MajorDangerReportRPC;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时上报重大隐患数据
 */
@Service("majorHiddenDangerReportTask")
public class MajorHiddenDangerReportTask {
	
	@Autowired
	private MajorDangerReportRPC majorDangerReportRPC;
	@Autowired
	private SystemService systemService;

	public void run() {
		long start = System.currentTimeMillis();
		long count = 0;
		org.jeecgframework.core.util.LogUtil.info("===================定时上报重大隐患数据开始===================");
		try {
			//TODO 获取需要上报的数据id
			List<String> idList = systemService.findListbySql("select id from t_b_major_hidden_danger where (report_status='"+ Constants.REPORT_STATUS_N+"' or report_status is null OR update_date > report_date )");
			if (idList != null && idList.size()>0) {
				String ids = StringUtil.joinString(idList, ",");
				count = idList.size();
				majorDangerReportRPC.majorDangerReport(ids,true);
			}
		} catch (Exception e) {
			org.jeecgframework.core.util.LogUtil.error("定时上报重大隐患数据失败", e);
		}
		org.jeecgframework.core.util.LogUtil.info("===================定时上报重大隐患数据结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		org.jeecgframework.core.util.LogUtil.info("定时上报重大隐患数据总耗时"+times+"毫秒,共同步"+count+"条数据.");
	}
}
