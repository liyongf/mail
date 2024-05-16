package com.sdzk.buss.api.test;

import com.sdzk.buss.api.utils.Base64Util;
import com.sdzk.buss.web.common.Constants;
import net.sf.json.JSONObject;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.HttpClientUtils;
import org.jeecgframework.core.util.JSONHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileUploadTest {
    public static void main(String[] args) {
        String url = "http://localhost:8181/sdzkmine/mobile/mobileHiddenDangerController/uploadFile.do";
        String filePath = "F://uploadfile//TEMP//960a304e251f95ca6a2e54a5c3177f3e660952ce.jpg";
        //String filePath = "F:\\uploadfile\\TEMP\\content-big-round-shade-show.zip";
        Map<String,Object> param = new HashMap<>();

        Map<String,String> retMap =new HashMap<>();
        retMap.put("code", Constants.LOCAL_RESULT_CODE_SUCCESS);
        retMap.put("message", "文件上传成功");
        String result = null;

        try{
            File file = new File(filePath);
            param.put("hiddenId","402880f7604322680160433e7e6a0009");
            param.put("fileExtend", FileUtils.getExtend(file.getName()));
            String base64Code = Base64Util.encodeBase64File(filePath);
            param.put("fileContent",base64Code);

            result = HttpClientUtils.post(url,param);
        }catch (Exception e){
            retMap.put("code",Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message","文件上传失败："+e.getMessage());
        }

        if(result!=null){
            JSONObject resultJson = JSONHelper.jsonstr2json(result);
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
