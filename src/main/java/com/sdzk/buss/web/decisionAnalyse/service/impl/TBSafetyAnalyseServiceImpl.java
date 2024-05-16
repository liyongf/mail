package com.sdzk.buss.web.decisionAnalyse.service.impl;

import com.sdzk.buss.web.decisionAnalyse.service.TBSafetyAnalyseServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tBSafetyAnalyseService")
@Transactional
public class TBSafetyAnalyseServiceImpl extends CommonServiceImpl implements TBSafetyAnalyseServiceI{
}
