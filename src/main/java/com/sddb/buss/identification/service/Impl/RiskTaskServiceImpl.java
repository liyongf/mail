package com.sddb.buss.identification.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sddb.buss.identification.service.RiskTaskServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("riskTaskService")
@Transactional
public class RiskTaskServiceImpl extends CommonServiceImpl implements RiskTaskServiceI {
	
}