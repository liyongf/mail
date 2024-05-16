package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.health.entity.TBLaborProtectEntity;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

/**
 * 劳动防护用户导入校验
 * @author Administrator
 *
 */
public class LaborProtectExcelVerifyHandler implements IExcelVerifyHandler<TBLaborProtectEntity> {

	@Override
	public ExcelVerifyHanlderResult verifyHandler(TBLaborProtectEntity obj) {
		StringBuilder builder = new StringBuilder();
		boolean success = true;
		
		if(StringUtil.isEmpty(obj.getProtectEquipment())){
			builder.append("个人防护用品名称不能为空。");
		}
		if(StringUtil.isEmpty(obj.getAmount())){
			builder.append("数量不能为空。");
		}
		if(StringUtil.isEmpty(obj.getFileNoTemp())){
			builder.append("领取人档案号不能为空。");
		}
		if(obj.getReceiveDate()==null){
			builder.append("领取日期不能为空。");
		}
		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		}
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}



}
