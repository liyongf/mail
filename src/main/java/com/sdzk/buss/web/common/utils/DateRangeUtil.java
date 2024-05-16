package com.sdzk.buss.web.common.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateRangeUtil {
    /**
     * 获取今天的时间范围
     *
     * @return 返回长度为2的字符串集合，如：[2017-11-03 00:00:00, 2017-11-03 24:00:00]
     */
    public static List<String> getTodayRange() {
        List<String> dateList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, 0);
        String today = dateFormat.format(calendar.getTime());
        dateList.add(today + " 00:00:00");
        dateList.add(today + " 24:00:00");
        return dateList;
    }

    /**
     * 获取昨天的时间范围
     *
     * @return 返回长度为2的字符串集合，如：[2017-11-02 00:00:00, 2017-11-02 24:00:00]
     */
    public static List<String> getYesterdayRange() {
        List<String> dateList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, -1);
        String yesterday = dateFormat.format(calendar.getTime());
        dateList.add(yesterday + " 00:00:00");
        dateList.add(yesterday + " 24:00:00");
        return dateList;
    }

    /**
     * 获取本周的时间范围
     *
     * @return 返回长度为2的字符串集合，如：[2017-10-30 00:00:00, 2017-11-05 24:00:00]
     */
    public static List<String> getCurrentWeekRange() {
        List<String> dateList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置周一为一周之内的第一天
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String monday = dateFormat.format(calendar.getTime()) + " 00:00:00";
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String sunday = dateFormat.format(calendar.getTime()) + " 24:00:00";
        dateList.add(monday);
        dateList.add(sunday);
        return dateList;
    }

    /**
     * 获取本周的时间范围(不带时分秒)
     *
     * @return 返回长度为2的字符串集合，如：[2017-10-30, 2017-11-05]
     */
    public static List<String> getCurrentWeekRangeNoTime() {
        List<String> dateList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置周一为一周之内的第一天
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String monday = dateFormat.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String sunday = dateFormat.format(calendar.getTime());
        dateList.add(monday);
        dateList.add(sunday);
        return dateList;
    }

    /**
     * 获取上周的时间范围
     * @return
     * @throws ParseException
     */
    public static List<String> getLastWeekRangeNoTime() throws ParseException{
        String dateTmp = getCurrentWeekRange().get(0);
        String endDate = offsetHours(dateTmp,-24*60);
        String startDate = offsetHours(endDate,-24*60*6);
        List<String> dateList = new ArrayList<>(2);
        dateList.add(startDate.substring(0,10));
        dateList.add(endDate.substring(0,10));
        return dateList;
    }

    /**
     * 获取本月的时间范围
     *
     * @return 返回长度为2的字符串集合，如：[2017-11-01 00:00:00, 2017-11-30 24:00:00]
     */
    public static List<String> getCurrentMonthRange() {
        List<String> dateList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDayOfMonth = dateFormat.format(calendar.getTime()) + " 00:00:00";
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        String lastDayOfMonth = dateFormat.format(calendar.getTime()) + " 24:00:00";
        dateList.add(firstDayOfMonth);
        dateList.add(lastDayOfMonth);
        return dateList;
    }

    /**
     * 获取本月的时间范围
     *
     * @return 返回长度为2的字符串集合，如：[2017-11-01, 2017-11-30]
     */
    public static List<String> getCurrentMonthRangeNoTime() {
        List<String> dateList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDayOfMonth = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        String lastDayOfMonth = dateFormat.format(calendar.getTime());
        dateList.add(firstDayOfMonth);
        dateList.add(lastDayOfMonth);
        return dateList;
    }

    /**
     * 获取上月的时间范围
     * @return
     * @throws ParseException
     */
    public static List<String> getLastMonthRangeNoTime() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String thisMonth = sdf.format(new Date());
        String lastMonth = offsetMonths(thisMonth,-1);

        int days = getDaysInMonth(lastMonth);

        String dateTmp = getCurrentMonthRange().get(0);
        String startDate = offsetHours(dateTmp,-24*60*days);
        String endDate = offsetHours(startDate,24*60*(days-1));
        List<String> dateList = new ArrayList<>(2);
        dateList.add(startDate.substring(0,10));
        dateList.add(endDate.substring(0,10));
        return dateList;
    }

    /**
     * 获取本年的时间范围
     *
     * @return 返回长度为2的字符串集合，如：[2017-01-01 00:00:00, 2017-12-31 24:00:00]
     */
    public static List<String> getCurrentYearRange() {
        List<String> dateList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.YEAR, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        String firstDayOfYear = dateFormat.format(calendar.getTime()) + " 00:00:00";
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        String lastDayOfYear = dateFormat.format(calendar.getTime()) + " 24:00:00";
        dateList.add(firstDayOfYear);
        dateList.add(lastDayOfYear);
        return dateList;
    }

    /**
     * 获取最近几天的时间范围
     *
     * @param lastFewDays 最近多少天
     * @return 返回长度为2的字符串集合，如：[2017-12-25 17:15:33, 2017-12-26 17:15:33]
     */
    public static List<String> getLastFewDaysRange(int lastFewDays) {
        List<String> dateList = new ArrayList<>(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String endTime = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, -lastFewDays);
        String startTime = dateFormat.format(calendar.getTime());
        dateList.add(startTime);
        dateList.add(endTime);
        return dateList;
    }

    /**
     * 获取当前时间
     *
     * @param pattern 指定返回当前时间的格式，例："yyyy-MM-dd HH:mm:ss"
     * @return 返回指定格式的当前时间，如："2018-01-25 10:14:30"
     */
    public static String getCurrentTime(String pattern) {
        String currentTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        currentTime = dateFormat.format(calendar.getTime());
        return currentTime;
    }

    /***
     * 将指定时间偏移几小时
     * @param time 指定时间，精确到分，例："2018-01-25 10:48"
     * @param offset 偏移量：负数代表减几个小时，正数代表加几个小时，例：1
     * @return 返回偏移后的时间，如："2018-01-25 11：48"
     * @throws ParseException
     */
    public static String offsetHours(String time, int offset) throws ParseException {
        String offsetHours = null;
        if (StringUtils.hasText(time)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(time));
            calendar.add(Calendar.MINUTE, offset);
            offsetHours = dateFormat.format(calendar.getTime());
        }
        return offsetHours;
    }

    /**
     * 将指定月份偏移几个月
     *
     * @param month  指定月份
     * @param offset 偏移量：负数代表上几个月，正数代表下几个月
     * @return 返回偏移后的月份，如：2018-01
     * @throws ParseException
     */
    public static String offsetMonths(String month, int offset) throws ParseException {
        String offsetMonth = null;
        if (StringUtils.hasText(month)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(month));
            calendar.add(Calendar.MONTH, offset);
            offsetMonth = dateFormat.format(calendar.getTime());
        }
        return offsetMonth;
    }

    /**
     * 获取指定日期是星期几（设置星期一为一周的开始）
     *
     * @param day 指定日期
     * @return 返回星期几，如：1
     * @throws ParseException
     */
    public static int getDayOfWeek(String day) throws ParseException {
        int dayOfWeek = 0;
        if (StringUtils.hasText(day)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(day));
            calendar.add(Calendar.DATE, -1); //在指定日期的基础上减一天，满足中国人的习惯
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        }
        return dayOfWeek;
    }

    /**
     * 获取指定月份有多少天
     *
     * @param month 指定月份
     * @return 返回天数，如：31
     * @throws ParseException
     */
    public static int getDaysInMonth(String month) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(month));
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static void main(String args[]) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String thisMonth = sdf.format(new Date());
        String lastMonth = offsetMonths(thisMonth,-1);

        int days = getDaysInMonth(lastMonth);

        String dateTmp = getCurrentMonthRange().get(0);
        String startDate = offsetHours(dateTmp,-24*60*days);
        String endDate = offsetHours(startDate,24*60*(days-1));

        System.out.println(startDate.substring(0,10));
        System.out.println(endDate.substring(0,10));
    }
}
