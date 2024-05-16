package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.health.entity.TBHealthTrainEntity;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

/**
 * 职业安全卫生教育培训导入校验
 * @author Administrator
 *
 */
public class HealthTrainExcelVerifyHandler implements IExcelVerifyHandler<TBHealthTrainEntity> {

	@Override
	public ExcelVerifyHanlderResult verifyHandler(TBHealthTrainEntity obj) {
		StringBuilder builder = new StringBuilder();
		boolean success = true;
		
	/*	if(StringUtil.isEmpty(obj.getFileNoTemp())){
			builder.append("档案号不能为空。");
		}*/
		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		}
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}



}
