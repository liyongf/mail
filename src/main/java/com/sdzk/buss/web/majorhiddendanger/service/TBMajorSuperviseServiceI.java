package com.sdzk.buss.web.majorhiddendanger.service;

import com.sdzk.buss.web.majorhiddendanger.entity.SFListedSupervisionInfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

/**
 * Created by 张赛超 on 17-7-6.
 */
public interface TBMajorSuperviseServiceI extends CommonService {

    public void delete(SFListedSupervisionInfoEntity entity) throws Exception;

    public Serializable save(SFListedSupervisionInfoEntity entity) throws Exception;

    public void saveOrUpdate(SFListedSupervisionInfoEntity entity) throws Exception;

    public java.util.Map<String,String> synMajorSupervise();
    public java.util.Map<String,String> synCommonSupervise();
}
