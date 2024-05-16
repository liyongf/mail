package com.sdzk.buss.web.hiddendanger.service;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleLogEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBHiddenDangerHandleLogServiceI extends CommonService{
	
 	public void delete(TBHiddenDangerHandleLogEntity entity) throws Exception;
 	
 	public Serializable save(TBHiddenDangerHandleLogEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBHiddenDangerHandleLogEntity entity) throws Exception;

	/**
	 * 日志添加
	 * @param examId 隐患id
	 * @param handleType 操作类型
	 * @param handleContent 操作内容
	 * @param TUser 操作人
	 */
	public void addLog(String examId, Short handleType, String handleContent);
 	
}
