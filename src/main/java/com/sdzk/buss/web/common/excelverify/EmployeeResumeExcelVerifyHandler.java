package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.health.entity.TBEmployeeInfoEntity;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

public class EmployeeResumeExcelVerifyHandler implements IExcelVerifyHandler<TBEmployeeInfoEntity> {

	@Override
	public ExcelVerifyHanlderResult verifyHandler(TBEmployeeInfoEntity obj) {
		StringBuilder builder = new StringBuilder();
		boolean success = true;
		/*if(StringUtil.isEmpty(obj.getFileNo())){
			builder.append("档案号不能为空。");
		}
		if(StringUtil.isEmpty(obj.getPostNumber())){
			builder.append("在岗编号不能为空。");
		}
		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		}*/
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}



}
