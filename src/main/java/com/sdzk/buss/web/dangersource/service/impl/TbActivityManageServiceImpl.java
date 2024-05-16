package com.sdzk.buss.web.dangersource.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdzk.buss.web.dangersource.service.TbActivityManageServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("tbActivityManageService")
@Transactional
public class TbActivityManageServiceImpl extends CommonServiceImpl implements TbActivityManageServiceI {
	
}