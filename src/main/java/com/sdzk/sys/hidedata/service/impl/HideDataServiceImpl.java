package com.sdzk.sys.hidedata.service.impl;

import com.sdzk.sys.childwindow.controller.TSChildWindowsController;
import com.sdzk.sys.hidedata.service.HideDataServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("hideDataService")
@Transactional
public class HideDataServiceImpl extends CommonServiceImpl implements HideDataServiceI {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TSChildWindowsController.class);

    @Autowired
    private SystemService systemService;


    /**
     * 根据数据字典的typegroupcode从数据字典读取数据并返回一个包含typecode和typename的JSONArray数组
     *  request: String typegroupcode
     *  response: JSONArray [{typecode : typename},{......}]
     **/
    @Override
    public JSONArray getDicArray(String typegroupcode) {
        JSONArray ja = new JSONArray();

        String sql = "select typecode,typename,is_hide,id from t_s_type where typegroupid in ( " +
                " select id from t_s_typegroup where typegroupcode = '"+typegroupcode+"' ) ORDER BY CAST(typecode AS SIGNED INTEGER) ASC";
        List<Object[]> list = systemService.findListbySql(sql);

        for (Object[] o : list){
            JSONObject jo = new JSONObject();
            jo.put("typecode", objToString(o[0]));
            jo.put("typename", objToString(o[1]));
            jo.put("isHide", objToString(o[2]));
            jo.put("id", objToString(o[3]));

            ja.add(jo);
        }
        return ja;
    }


    /**Object转String*/
    private Object objToString(Object obj){
        String ret = "";
        if (obj!=null){
            ret = obj.toString();
        }
        return ret;
    }









}
