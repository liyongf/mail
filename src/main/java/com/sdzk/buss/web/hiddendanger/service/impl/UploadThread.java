package com.sdzk.buss.web.hiddendanger.service.impl;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.DataMapServiceI;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleStepEntity;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

@Service("uploadThread")
public class UploadThread extends Thread implements Runnable {
	private String ids;
	private TBHiddenDangerHandleServiceI tBHiddenDangerHandleService;
	public UploadThread(){

	}
	public UploadThread(String ids){
		this.ids = ids;
	}
	public void run(){
		try {
			tBHiddenDangerHandleService = (TBHiddenDangerHandleServiceI) ApplicationContextUtil.getContext().getBean("tBHiddenDangerHandleService");
			tBHiddenDangerHandleService.hiddenDangerReportToGroup(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}