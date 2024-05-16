package com.sdzk.buss.web.common.taskProvince.service.impl;

import com.sdzk.buss.web.common.taskProvince.service.GetDataService;
import com.sdzk.buss.web.common.utils.AesUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.HttpClientUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2019/1/3.
 */
@Component("getDataService")
public class getDataServiceImpl implements GetDataService {

    @Autowired
    private SystemService systemService;

    @Override
    public List<Map<String,Object>> getData(Map<String,String> columnNeed,String tableName,List<String> conditions){
        List<Map<String,Object>> result;
        StringBuffer buffer = new StringBuffer();
        buffer.append("select ");
        for(String columnName : columnNeed.keySet()){
            buffer.append(columnName+" "+columnNeed.get(columnName)+",\n");
        }
        buffer = new StringBuffer(buffer.substring(0,buffer.lastIndexOf(","))+"\n");
        buffer.append("from \n");
        buffer.append(tableName+"\n");
        buffer.append(" where 1=1\n");
        for(String condition : conditions){
            buffer.append("and "+condition+"\n");
        }
        result = systemService.findForJdbc(buffer.toString());
        return result;
    };

    @Override
    public AjaxJson postData(Object data,String reportUrl){
        AjaxJson j = new AjaxJson();
        //上传
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String token = ResourceUtil.getConfigByName("token");
        String reportContent = data.toString();
        Map<String, Object> paramMap = new HashMap<>();
        /**
         * 加密过程
         * */
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        paramMap.put("token", ciphertext);
        paramMap.put("mineCode", mineCode);
        paramMap.put("reportContent", reportContent);
        String response = null;
        try {
            response = HttpClientUtils.post(reportUrl, paramMap, "UTF-8");
        } catch (NetServiceException e) {
            j.setSuccess(false);
            j.setMsg("上报接口调用失败, 网络连接错误");
        }
        String retCode = null;
        if(StringUtil.isNotEmpty(response)){
            JSONObject jsonObject = JSONObject.fromObject(response);
            retCode = jsonObject.getString("code");
        }
        if(StringUtil.isEmpty(retCode)){
            retCode = "404";
        }
        j.setObj(retCode);
        return j;
    };

}
