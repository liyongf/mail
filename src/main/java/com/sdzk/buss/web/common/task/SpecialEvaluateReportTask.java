package com.sdzk.buss.web.common.task;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.specialevaluation.service.SpecialEvaluationRPC;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时上报专项风险数据
 */
@Service("specialEvaluateReportTask")
public class SpecialEvaluateReportTask {

	@Autowired
	private SystemService systemService;
	@Autowired
	private SpecialEvaluationRPC specialEvaluationRPC;

	public void run() {
		long start = System.currentTimeMillis();
		long count = 0;
		org.jeecgframework.core.util.LogUtil.info("===================定时上报专项风险数据开始===================");
		try {
			//TODO
			List<String> idList = systemService.findListbySql("select id from t_b_special_evaluation where report_status='"+Constants.SE_REPORT_STATUS_DEPART_REPORT+"' and is_delete != '"+Constants.IS_DELETE_Y+"'");
			if (idList != null && idList.size()>0) {
				count = idList.size();
				String ids = StringUtil.joinString(idList, ",");
				specialEvaluationRPC.mineReport(ids,true);
			}
		} catch (Exception e) {
			org.jeecgframework.core.util.LogUtil.error("定时上报专项风险数据失败", e);
		}
		org.jeecgframework.core.util.LogUtil.info("===================定时上报专项风险数据结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		org.jeecgframework.core.util.LogUtil.info("总耗时"+times+"毫秒,共同步"+count+"条数据.");
	}
}
