package com.sdzk.buss.web.wechattemplatemanagement.service;
import com.sdzk.buss.web.wechattemplatemanagement.entity.WechatTemplateManagementEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WechatTemplateManagementServiceI extends CommonService {
	
 	public void delete(WechatTemplateManagementEntity entity) throws Exception;
 	
 	public Serializable save(WechatTemplateManagementEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WechatTemplateManagementEntity entity) throws Exception;
 	
}
