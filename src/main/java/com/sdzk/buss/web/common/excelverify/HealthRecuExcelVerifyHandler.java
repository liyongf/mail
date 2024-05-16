package com.sdzk.buss.web.common.excelverify;

import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

import com.sdzk.buss.web.health.entity.TBHealthRecuperateEntity;

/**
 * 职业健康疗养导入校验
 * @author Administrator
 *
 */
public class HealthRecuExcelVerifyHandler implements IExcelVerifyHandler<TBHealthRecuperateEntity> {

	@Override
	public ExcelVerifyHanlderResult verifyHandler(TBHealthRecuperateEntity obj) {
		StringBuilder builder = new StringBuilder();
		boolean success = true;
		
		/*if(StringUtil.isEmpty(obj.getFileNoTemp())){
			builder.append("档案号不能为空。");
		}*/
		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		}
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}



}
