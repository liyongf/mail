package com.sdzk.buss.web.sms.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdzk.buss.web.sms.service.TBSmsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("tBSmsService")
@Transactional
public class TBSmsServiceImpl extends CommonServiceImpl implements TBSmsServiceI {
	
}