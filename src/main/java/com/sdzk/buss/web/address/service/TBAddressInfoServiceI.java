package com.sdzk.buss.web.address.service;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.Map;

public interface TBAddressInfoServiceI extends CommonService{
	
 	public void delete(TBAddressInfoEntity entity) throws Exception;
 	
 	public Serializable save(TBAddressInfoEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBAddressInfoEntity entity) throws Exception;

    void saveAddressDepartRel(String addressId, String departIds);

    public Map<String,Object> getDynamicLevel();//获取各个地点的动态等级

	AjaxJson tBAddressInfoReportToGroup(String ids);
}
