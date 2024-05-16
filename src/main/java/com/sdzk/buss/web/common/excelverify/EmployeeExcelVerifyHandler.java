package com.sdzk.buss.web.common.excelverify;

import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

import com.sdzk.buss.web.health.entity.TBEmployeeInfoEntity;

public class EmployeeExcelVerifyHandler implements IExcelVerifyHandler<TBEmployeeInfoEntity> {

	@Override
	public ExcelVerifyHanlderResult verifyHandler(TBEmployeeInfoEntity obj) {
		StringBuilder builder = new StringBuilder();
		boolean success = true;
		if(StringUtil.isEmpty(obj.getFileNo())){
			builder.append("档案号不能为空。");
		}
		if(StringUtil.isEmpty(obj.getPostNumber())){
			builder.append("在岗编号不能为空。");
		}
		if(StringUtil.isEmpty(obj.getName())){
			builder.append("姓名不能为空。");
		}
		if(StringUtil.isEmpty(obj.getGender())){
			builder.append("性别不能为空。");
		}
		if(StringUtil.isEmpty(obj.getCardNumber())){
			builder.append("身份证号不能为空。");
		}
		if(StringUtil.isEmpty(obj.getPostCategory())){
			builder.append("岗位类别不能为空。");
		}
		if(StringUtil.isEmpty(obj.getPostStatus())){
			builder.append("岗位状态不能为空。");
		}
		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		}
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}



}
