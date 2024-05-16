package com.sdzk.buss.web.aqbzh.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";


    public final static SimpleDateFormat sdfTime = new SimpleDateFormat(STANDARD_FORMAT);

    private static SimpleDateFormat sdf_date_format = new SimpleDateFormat(YYYY_MM_DD);

    public static final ThreadLocal<DateFormat> dayFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(YYYY_MM_DD);
        }
    };

    public static final ThreadLocal<DateFormat> secondFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(STANDARD_FORMAT);
        }
    };

    /**
     * 获取当前时间的YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * 日期比较，如果s>=e 返回true 否则返回false
     *
     * @param s
     * @param e
     * @return
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return s.compareTo(e) > 0;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 获取某月第一天和最后一天
     *
     * @param month 0当月 整数为当月+数字后的月份  负数相反
     * @param type  first 为月中最小日期  last为月中最大日期
     * @return
     */
    public static String getFirstByMonth(Integer month, String type) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, month);
        String day = null;
        if (type != null && "first".equals(type)) {
            //获取当前月第一天
            c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            day = sdf_date_format.format(c.getTime());
        } else if (type != null && "last".equals(type)) {
            //获取当前月最后一天
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            day = sdf_date_format.format(c.getTime());
        }
        return day;
    }

    /**
     * @param year
     * @param month
     * @return 获得某年某月第一天
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year); //设置年份
        cal.set(Calendar.MONTH, month - 1);  //设置月份
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH); //获取某月最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);    //设置日历中月份的最小天数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //格式化日期
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /**
     * @param year
     * @param month
     * @return 获得某年某月最后一天
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year); //设置年份
        cal.set(Calendar.MONTH, month - 1); //设置月份
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);    //获取某月最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);   //设置日历中月份的最大天数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  //格式化日期
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 获取当前时间戳，单位秒
     *
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间的后i天
     *
     * @param i
     * @return
     */
    public static String getAddDay(int i) {
        String currentTime = getTime();
        GregorianCalendar gCal = new GregorianCalendar(
                Integer.parseInt(currentTime.substring(0, 4)),
                Integer.parseInt(currentTime.substring(5, 7)) - 1,
                Integer.parseInt(currentTime.substring(8, 10)));
        gCal.add(GregorianCalendar.DATE, i);
        return sdf_date_format.format(gCal.getTime());
    }

    /**
     * 获取当前时间的后i天
     * 精确到秒
     *
     * @param i
     * @return
     */
    public static String getAddDayTime(int i) {
        Date date = new Date(System.currentTimeMillis() + i * 24 * 60 * 60 * 1000);
        return sdfTime.format(date);
    }

    /**
     * 获取当前时间的+多少秒
     * 精确到秒
     *
     * @param i
     * @return
     */
    public static String getAddDaySecond(int i) {
        Date date = new Date(System.currentTimeMillis() + i * 1000);
        return sdfTime.format(date);
    }

    /**
     * 获取指定时间+多少秒后的时间
     * 精确到秒
     *
     * @param time 时间
     * @param i    追加秒数
     * @return
     */
    public static String getAddTimeSecond(String time, int i) {
        try {
            Date date = new Date(Long.valueOf(DateTimeUtil.dateToStamp(time)) + i * 1000);
            return sdfTime.format(date);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * 获取两个时间差的天、时、分、秒、毫秒数
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param format    时间格式 如：yyyy-MM-dd HH:mm:dd
     * @param type      毫秒-millisecond 秒-second 分-minute 时-hour 天-day（传英文）
     * @return
     */
    public static Long getTestTime(String beginTime, String endTime, String format, String type) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat(format);
            long arm1 = sim.parse(beginTime).getTime();//获取毫秒数
            long arm2 = sim.parse(endTime).getTime();//获取毫秒数
            long millisecond = arm2 - arm1;//计算毫秒
            long second = millisecond / 1000;//计算秒
            long minute = second / 60;//计算分
            long hour = minute / 60;//计算时
            long day = hour / 24;//计算天
            if (type != null) {
                if (type.equals("millisecond")) {
                    return millisecond;
                } else if (type.equals("second")) {
                    return second;
                } else if (type.equals("minute")) {
                    return minute;
                } else if (type.equals("hour")) {
                    return hour;
                } else if (type.equals("day")) {
                    return day;
                }
            }
        } catch (Exception e) {
            System.out.println("获取两个时间差的天、时、分、秒、毫秒失败" + e);
        }
        return null;
    }

    /**
     * 时间字符串 指定格式 时间
     *
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date strToDate(String dateTimeStr, String formatStr) {
        DateFormat df = new SimpleDateFormat(formatStr);
        try {
            return df.parse(dateTimeStr);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 时间 指定格式 时间字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
         SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
         String dateString = formatter.format(date);
        return dateString;
    }
    /**
     * 日期加上指定月月数
     *
     * @param olddate    需要加上指定月数的时间
     * @param recordDate 月数
     * @return
     */
    public static Date getNewDate(Date olddate, String recordDate) {
        Date date = olddate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String data = format.format(date);
        String dataStr[] = data.split("-");
        //年份
        int year = (Integer.parseInt(dataStr[1]) + Integer.parseInt(recordDate)) / 12;
        //月份
        int yue = (Integer.parseInt(dataStr[1]) + Integer.parseInt(recordDate)) % 12;
        String a = "";
        if (yue < 10) {
            if (yue < 1) {
                a = "12";
            } else {
                a = "0" + yue;
            }
        } else {
            a = yue + "";
        }
        dataStr[0] = String.valueOf(Integer.parseInt(dataStr[0]) + year);
        dataStr[1] = a;
        String newdata = dataStr[0] + "-" + dataStr[1] + "-" + dataStr[2];
        Date newDate = null;
        try {
            newDate = format.parse(newdata);
        } catch (ParseException e) {
        }
        return newDate;
    }

    /**
     * 获取周
     *
     * @param date 指定时间的 周数
     * @return
     */
    public static int getWeek(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY); //美国是以周日为每周的第一天 现把周一设成第一天
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取月份
     *
     * @param date 指定时间的 月份
     * @return
     */
    public static int getMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int m = calendar.get(Calendar.MONTH) + 1;
        return m;
    }

    /**
     * 获取季度
     *
     * @param date 指定时间的 季度
     * @return
     */
    public static String getQuarter(Date date) {
        if (date == null) {
            date = new Date();
        }
        String quarter = " ";
        int m = getMonth(date);
        if (m >= 1 && m <= 3) {
            quarter = "1";
        }
        if (m >= 4 && m <= 6) {
            quarter = "2";
        }
        if (m >= 7 && m <= 9) {
            quarter = "3";
        }
        if (m >= 10 && m <= 12) {
            quarter = "4";
        }
        return quarter;
    }

    /**
     * 获取属于上半年 还是下半年
     *
     * @param date 指定时间的 属于上半年 还是下半年
     * @return
     */
    public static String getOnceHalfYear(Date date) {
        if (date == null) {
            date = new Date();
        }
        String onceHalfYear = " ";
        int m = getMonth(date);
        if (m >= 1 && m <= 6) {
            onceHalfYear = "上半年";
        }
        if (m >= 7 && m <= 12) {
            onceHalfYear = "下半年";
        }
        return onceHalfYear;
    }



    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_FORMAT);
        Long timestamp = Long.parseLong(s) * 1000;
        String date = simpleDateFormat.format(new Date(timestamp));
        return date;
    }





    /**
     * 获得当前时间戳
     *
     * @return
     */
    public static String generateTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();
        return String.valueOf(time);
    }

    /**
     * @param inputJudgeDate 要判断是否在当天24h内的时间
     * @return boolean
     * @Description 是否为当天24h内
     */
    public static boolean isToday(Date inputJudgeDate) {
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Long paseBeginTime = null;
        Long paseEndTime = null;
        Long checkTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime).getTime();
            paseEndTime = dateFormat.parse(endTime).getTime();
            checkTime = inputJudgeDate.getTime();
            if (checkTime >= paseBeginTime && checkTime <= paseEndTime) {
                flag = true;
            }
        } catch (ParseException e) {
        }
        return flag;
    }

    /**
     * 时间 是否在 指定 范围内
     *
     * @param armtime   指定时间
     * @param beginTime 范围开始时间
     * @param endTime   范围结束时间
     * @param format    时间格式
     * @return
     * @throws ParseException
     */
    public static boolean dayTime(String armtime, String beginTime, String endTime, String format) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat(format);
            long arm0 = sim.parse(armtime).getTime();
            //把开始区间时间和结束区间时间转换为long类型。然后比较
            long arm1 = sim.parse(beginTime).getTime();
            long arm2 = sim.parse(endTime).getTime();
            if (arm0 >= arm1 && arm0 <= arm2) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
