package com.sdzk.buss.web.tbdangersourceapost.service;
import com.sdzk.buss.web.tbdangersourceapost.entity.TBDangerSourceaPostEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBDangerSourceaPostServiceI extends CommonService{
	
 	public void delete(TBDangerSourceaPostEntity entity) throws Exception;
 	
 	public Serializable save(TBDangerSourceaPostEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBDangerSourceaPostEntity entity) throws Exception;
 	
}
