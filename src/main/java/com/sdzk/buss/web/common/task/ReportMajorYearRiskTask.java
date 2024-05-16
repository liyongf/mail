package com.sdzk.buss.web.common.task;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.service.TBDangerSourceServiceI;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时上报重大年度风险数据
 */
@Service("reportMajorYearRiskTask")
public class ReportMajorYearRiskTask {
	
	@Autowired
	private TBDangerSourceServiceI tbDangerSourceService;

	public void run() {
		long start = System.currentTimeMillis();
		long count = 0;
		org.jeecgframework.core.util.LogUtil.info("===================定时上报重大年度风险数据开始===================");
		try {
			List<String> idList = tbDangerSourceService.findListbySql("select id from t_b_danger_source where ismajor='"+ Constants.IS_MAJORDangerSource_Y+"' and origin !=  '"+Constants.DANGER_SOURCE_ORIGIN_NOMAL+"' and is_delete='"+Constants.IS_DELETE_N+"'");
			if (idList != null && idList.size()>0) {
				count = idList.size();
				String ids = StringUtil.joinString(idList, ",");
				tbDangerSourceService.reportMajorYearRisk(ids);
			}
		} catch (Exception e) {
			org.jeecgframework.core.util.LogUtil.error("定时上报重大年度风险数据失败", e);
		}
		org.jeecgframework.core.util.LogUtil.info("===================定时上报重大年度风险数据结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		org.jeecgframework.core.util.LogUtil.info("定时上报重大年度风险数据总耗时"+times+"毫秒,共同步"+count+"条数据.");
	}
}
