package com.steven.baselibrary.utils;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * TimeUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * yyyy-MM-dd
     */
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * yyyy年MM月dd日
     */
    public static final SimpleDateFormat DATE_FORMAT_CHINA = new SimpleDateFormat("yyyy年MM月dd日");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    public static String convertSecondToMin(String seconds) {
        int min = 0, sec = 0;
        min = Integer.valueOf(seconds) / 60;
        sec = Integer.valueOf(seconds) % 60;
        return (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
    }

    public static String formatTime(String pattern, long milli) {
        int m = (int) (milli / DateUtils.MINUTE_IN_MILLIS);
        int s = (int) ((milli / DateUtils.SECOND_IN_MILLIS) % 60);
        String mm = String.format(Locale.getDefault(), "%02d", m);
        String ss = String.format(Locale.getDefault(), "%02d", s);
        return pattern.replace("mm", mm).replace("ss", ss);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, int delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + delay * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     *
     * @param sdate
     * @param num
     * @return
     */
    public static String getWeek(String sdate, String num) {
        // 再转换为时间
        Date dd = TimeUtils.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num.equals("2")) // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num.equals("3")) // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num.equals("4")) // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num.equals("5")) // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num.equals("6")) // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num.equals("0")) // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 可以将类似2017-01-21转成2017年01月21日形式
     *
     * @param strDate the bare_field_name to set
     * @return the bare_field_name
     */
    public static String formatDate(String strDate, SimpleDateFormat dateFormat) {
        return dateFormat.format(strToDate(strDate));
    }

    /**
     * 比较两个日期的大小
     * <p>
     * 返回1，第一个参数大于第二个参数
     * 返回0，两个日期相等
     * 返回-1，第一个参数小于第二个
     * 日期格式：yyyy-MM-dd
     */
    public static int compare_date(String DATE1, String DATE2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                // System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                // System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String formatTimeFriendly(long getTime) {
        final long ONE_HOUR = 3600L;

        long nowTime = new Date().getTime() / 1000;
        SimpleDateFormat format = DATE_FORMAT_DATE;
        long todayTime = 0;
        try {
            todayTime = format.parse(getCurrentTimeInString(DATE_FORMAT_DATE)).getTime() / 1000;
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        long deltaToday = todayTime - getTime;//拿今日0点时间跟指定时间对比，小于0是今天
        long deltaNow = nowTime - getTime;//拿现在时间跟指定时间对比，判断是不是刚刚发布的

        Date date = new Date(getTime * 1000);

        if (deltaToday < 0) {//这是今天

            if (deltaNow < 60) {//小于1分钟
                return "刚刚更新";
            }

            if (deltaNow < ONE_HOUR) {//小于1小时
                return (long) Math.floor((deltaNow) * 1d / 60) + "分钟前更新";
            }

            return new SimpleDateFormat("今天 HH:mm更新").format(date);
        }

        if (deltaToday < 24L * ONE_HOUR) {//距离今天0点小于24小时，是昨天
            return new SimpleDateFormat("昨天 HH:mm更新").format(date);
        }

        if (deltaToday < 7 * 24L * ONE_HOUR) {
            long day = (deltaToday / (24L * ONE_HOUR) + 1);
            return day + "天前更新";
        }

        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

}