package com.sdzk.sys.hidedata.service;

import net.sf.json.JSONArray;
import org.jeecgframework.core.common.service.CommonService;

public interface HideDataServiceI extends CommonService {

    /**
     * 根据数据字典的typegroupcode从数据字典读取数据并返回一个包含typecode和typename的JSONArray数组
     *  request: String typegroupcode
     *  response: JSONArray [{typecode : typename},{......}]
     **/
    public JSONArray getDicArray(String typegroupcode);

}
