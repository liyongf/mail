package com.sdzk.buss.api.test;

import com.sdzk.buss.api.utils.Base64Util;
import com.sdzk.buss.web.common.Constants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.HttpClientUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MultipleFilesUploadTest {
    public static void main(String[] args) {
        String url = "http://localhost:8090/mobile/mobileHiddenDangerController/uploadMultipleFiles.do";
        Map<String,Object> param = new HashMap<>();

        Map<String,String> retMap =new HashMap<>();
        retMap.put("code", Constants.LOCAL_RESULT_CODE_SUCCESS);
        retMap.put("message", "文件上传成功");
        String result = null;

        try{
            param.put("hiddenId","402880ea5fb4a0e4015fb4a1bf410001");

            JSONArray fileContent = new JSONArray();

            String filePath = "D:\\code\\image\\a1.png";
            JSONObject jo = new JSONObject();
            File file = new File(filePath);
            jo.put("fileExtend", FileUtils.getExtend(file.getName()));
            String base64Code = Base64Util.encodeBase64File(filePath);
            jo.put("filebase64",base64Code);

            fileContent.add(jo);

            filePath = "D:\\code\\image\\a2.png";
            JSONObject jo1 = new JSONObject();
            File file1 = new File(filePath);
            jo1.put("fileExtend", FileUtils.getExtend(file1.getName()));
            String base64Code1 = Base64Util.encodeBase64File(filePath);
            jo1.put("filebase64",base64Code1);
            fileContent.add(jo1);

            param.put("fileContent",fileContent.toString());
            result = HttpClientUtils.post(url,param,"UTF-8");
        }catch (Exception e){
            retMap.put("code",Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message","文件上传失败："+e.getMessage());
        }

        if(result!=null){
            JSONObject resultJson = JSONObject.parseObject(result);
            String code = resultJson.getString("code");
            if(!code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)){//请求成功
                retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                retMap.put("message", "文件上传失败：" + resultJson.getString("message"));
            }
        }else {
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message", "文件上传失败");
        }

        System.out.println(retMap.get("message"));

    }
}
