package com.sdzk.buss.web.dangersource.service.impl;

import com.sdzk.buss.web.dangersource.service.TBDocumentSourceServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tBDocumentSourceService")
@Transactional
public class TBDocumentSourceServiceImpl extends CommonServiceImpl implements TBDocumentSourceServiceI {

}