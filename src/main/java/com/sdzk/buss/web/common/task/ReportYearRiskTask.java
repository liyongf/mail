package com.sdzk.buss.web.common.task;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.dangersource.service.TBDangerSourceServiceI;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 定时上报年度风险数据
 */
@Service("reportYearRiskTask")
public class ReportYearRiskTask {
	
	@Autowired
	private TBDangerSourceServiceI tbDangerSourceService;

	public void run() {
		long start = System.currentTimeMillis();
		long count = 0;
		org.jeecgframework.core.util.LogUtil.info("===================定时上报年度风险数据开始===================");
		try {

			List<String> idList = tbDangerSourceService.findListbySql("select id from t_b_danger_source where audit_status='"+ Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"' and report_status='"+Constants.DANGER_SOURCE_REPORT_UNREPORT+"' and origin =  '"+Constants.DANGER_SOURCE_ORIGIN_MINE+"' and is_delete='"+Constants.IS_DELETE_N+"'");
			if (idList != null && idList.size()>0) {
				count = idList.size();
				String ids = StringUtil.joinString(idList, ",");
				tbDangerSourceService.reportYearRisk(ids,true);
			}
		} catch (Exception e) {
			org.jeecgframework.core.util.LogUtil.error("定时上报年度风险数据失败", e);
		}
		org.jeecgframework.core.util.LogUtil.info("===================定时上报年度风险数据结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		org.jeecgframework.core.util.LogUtil.info("定时上报年度风险数据总耗时"+times+"毫秒,共同步"+count+"条数据.");
	}
}
