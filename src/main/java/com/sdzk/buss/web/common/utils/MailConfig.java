package com.sdzk.buss.web.common.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by cuizhixiang on 2017/5/31.
 * 过去资源文件数据
 **/
@Component
public class MailConfig {
    private static final String PROPERTIES_DEFAULT = "mailConfig.properties";
    public static String host;
    public static Integer port;
    public static String timeout;
    public static String userName;
    public static String passWord;
    public static String sender;
    public static String receiver;
    public static String personal;
    public static String subject;
    public static Properties properties;
    static{
        init();
    }

    /**
     * 初始化
     */
    private static void init() {
        properties = new Properties();
        try{
            InputStream inputStream = MailConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_DEFAULT);
            properties.load(inputStream);
            inputStream.close();
            host = properties.getProperty("mailHost");
            port = Integer.parseInt(properties.getProperty("mailPort"));
            timeout = properties.getProperty("mailTimeout");
            userName = properties.getProperty("mailUsername");
            passWord = properties.getProperty("mailPassword");
            sender = properties.getProperty("sender");
            receiver = properties.getProperty("receiver");
            personal = "双防矿版管理系统";
            subject = "双防矿版管理系统-上传dwg文件,邮件通知处理.";
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
