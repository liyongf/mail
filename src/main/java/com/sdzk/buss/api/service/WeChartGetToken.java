package com.sdzk.buss.api.service;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

public interface WeChartGetToken {

    public  String  getAccessToken(String appid, String appsecret) throws ClientProtocolException, IOException;

    public String getToken();
}
