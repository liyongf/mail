package com.sdzk.buss.web.hiddendanger.service.impl;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleLogEntity;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleLogServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;


@Service("tBHiddenDangerHandleLogService")
@Transactional
public class TBHiddenDangerHandleLogServiceImpl extends CommonServiceImpl implements TBHiddenDangerHandleLogServiceI {


 	public void delete(TBHiddenDangerHandleLogEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TBHiddenDangerHandleLogEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}

 	public void saveOrUpdate(TBHiddenDangerHandleLogEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}

	/**
	 * 添加日志
	 */
	public void addLog(String examId, Short handleType, String handleContent) {

		TBHiddenDangerHandleLogEntity tBHiddenDangerHandleLog = new TBHiddenDangerHandleLogEntity();
		tBHiddenDangerHandleLog.setExamId(examId);
		tBHiddenDangerHandleLog.setHandleContent(handleContent);
		tBHiddenDangerHandleLog.setHandleType(handleType);
		tBHiddenDangerHandleLog.setOperatetime(new Date());
		tBHiddenDangerHandleLog.setTSUser(ResourceUtil.getSessionUserName());

		commonDao.save(tBHiddenDangerHandleLog);
	}

 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TBHiddenDangerHandleLogEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------

	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TBHiddenDangerHandleLogEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------

	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 删除操作增强业务
	 * @param
	 * @return
	 */
	private void doDelBus(TBHiddenDangerHandleLogEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------

	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}

}