package com.sdzk.buss.web.common.service;

import org.jeecgframework.core.common.service.CommonService;

import java.util.Map;

public interface DataMapServiceI extends CommonService {
    /**
     *  获取 userMap
     * */
    Map<String, String> getUserMap();

    /**
     *  获取 departMap
     * */
    Map<String, String> getDepartMap();

    /**
     *  获取 addressMap
     * */
    Map<String, String> getAddressMap();
}
