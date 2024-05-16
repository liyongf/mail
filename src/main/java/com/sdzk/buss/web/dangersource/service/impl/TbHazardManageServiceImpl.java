package com.sdzk.buss.web.dangersource.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdzk.buss.web.dangersource.service.TbHazardManageServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("tbHazardManageService")
@Transactional
public class TbHazardManageServiceImpl extends CommonServiceImpl implements TbHazardManageServiceI {
	
}