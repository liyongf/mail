package com.sdzk.buss.web.uploadthreads;

import org.jeecgframework.core.util.HttpClientUtils;
import org.jeecgframework.web.cgform.exception.NetServiceException;

import java.util.Map;

/**
 * Created by Administrator on 17-9-25.
 */
public class UploadThread extends Thread {

    public String url;
    private String encode;
    private Map<String,String> data;

    public UploadThread(){

    }
    public UploadThread(String url,String encode,Map<String,String> data){
        this.url = url;
        this.encode = encode;
        this.data = data;
    }
    public void run(){
        try {
            HttpClientUtils.post(url, data, encode);
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }
}
