package com.liyf.boot.mail;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    Logger logger= Logger.getLogger(SaticScheduleTask.class);
    @Autowired
    private Email email;
    @Value("${spring.mail.surl}")
    private String surl; //监控url

    @Scheduled(cron = "0 0/5 * * * ?")
    private void dayTasks() {
        task(true);
    }
    @Scheduled(cron = "0 0 0/1 * * ?")
    private void nightTasks() {
        task(false);

    }
    public void task(boolean day) {
        logger.info("执行静态定时任务时间: " + LocalDateTime.now());
        try {
            String format = "HH:mm:ss";
            Date nowTime = null;
            Date startTime = null;
            Date endTime = null;
            try {
                Date currentTime = new Date();
                String now = new SimpleDateFormat(format).format(currentTime);
                nowTime = new SimpleDateFormat(format).parse(now);
                startTime = new SimpleDateFormat(format).parse("08:00:00");
                endTime = new SimpleDateFormat(format).parse("12:00:00");
            } catch (ParseException e) {
                logger.error("日期转换异常",e);
            }

            if(isEffectiveDate(nowTime, startTime, endTime)&&!day){
                logger.info("任务没执行: "+LocalDateTime.now()+"是否白天"+day);
                return;
            }
            if(!isEffectiveDate(nowTime, startTime, endTime)&&day){
                logger.info("任务没执行: "+LocalDateTime.now()+"是否白天"+day);
                return;
            }

            URL url = new URL(surl);
            URLConnection rulConnection = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
            httpUrlConnection.setConnectTimeout(30000);
            httpUrlConnection.setReadTimeout(30000);
            httpUrlConnection.connect();
            String code = new Integer(httpUrlConnection.getResponseCode()).toString();
            String message = httpUrlConnection.getResponseMessage();
            System.out.println("getResponseCode code =" + code);
            System.out.println("getResponseMessage message =" + message);
            if (!code.startsWith("2")) {
                email.sendEmail();
                logger.info("ResponseCode is not begin with 2,code=" + code);
            }
            logger.info(LocalDateTime.now() + "连接" + surl + "正常");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            email.sendEmail();
        }
    }
    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author liyf
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
