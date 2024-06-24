package com.bbyyxx2.myqrproject.Util;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {

    // 分钟
    public static int MINUTE_MILLIS = 1000 * 60;
    // 小时
    public static int HOUR_MILLIS = MINUTE_MILLIS * 60;
    // 天
    public static int DAY_MILLIS = HOUR_MILLIS * 24;
    // 年
    public static final long YEAR_MILLIS = 365 * DAY_MILLIS;


    /**
     * 格式化的 当前时间
     *
     * @return 字符串 yyyyMMdd_HHmmss
     */
    public static String getFormatCurrTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 格式化的 年-月-日
     *
     * @param timeMills
     * @return yyyy-MM-dd
     */
    public static String getFormat(long timeMills, String format) {
        Date dt = new Date(timeMills);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dt);
    }


    /**
     * 格式化的 年-月-日
     *
     * @param timeMills
     * @return yyyy-MM-dd
     */
    public static String getFormatYYYYMMDD(long timeMills) {
        Date dt = new Date(timeMills);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dt);
    }


    /**
     * 格式化的 月-日
     *
     * @param timeMills
     * @return
     */
    public static String getFormatMMdd(long timeMills) {
        Date dt = new Date(timeMills);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(dt);
    }


    /**
     * 格式化的 时:分
     *
     * @param timeMills
     * @return
     */
    public static String getFormatHHMM(long timeMills) {
        Date dt = new Date(timeMills);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(dt);
    }


    /**
     * ISO8601时间格式 格式化为 yyyy-MM-dd HH:mm:ss
     *
     * @param iso8601 输入的日志格式举例如下：
     *                <p>
     *                "2020-12-19T16:22:50.000Z"
     *                "2020-12-19T16:22:50.000+08:00"
     *                "2020-12-19T16:22:50Z"
     *                "2020-12-19T16:22:50+08:00"
     *                "2017-03-31T10:38:14.4723017Z"
     *                "2021-09-08T09:20:14.245292+08:00"
     * @return yyyy-MM-dd HH:mm:ss
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getFormatISO8601(String iso8601) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(iso8601);
        System.out.println("until: " + iso8601);
        String formatTime = offsetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("time: " + formatTime);
        return formatTime;
    }


    /**
     * 判断是不是同一天
     *
     * @param timeMillsA
     * @param timeMillsB
     * @return
     */
    public static boolean isSameDay(long timeMillsA, long timeMillsB) {
        Date dt = new Date(timeMillsA);
        Date nw = new Date(timeMillsB);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeStr = sdf.format(dt);
        String nowStr = sdf.format(nw);
        if (timeStr != null && nowStr != null
                && timeStr.equalsIgnoreCase(nowStr)) {
            return true;
        }
        return false;
    }


    /**
     * 判断是不是同一年
     *
     * @param timeMillsA
     * @param timeMillsB
     * @return
     */
    public static boolean isSameYear(long timeMillsA, long timeMillsB) {
        Date dt = new Date(timeMillsA);
        Date tmr = new Date(timeMillsB);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String timeStr = sdf.format(dt);
        String tmrStr = sdf.format(tmr);
        if (timeStr != null && tmrStr != null
                && timeStr.equalsIgnoreCase(tmrStr)) {
            return true;
        }
        return false;
    }


    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 基姆拉尔森计算公式根据日期判断星期几
     *
     * @param y
     * @param m
     * @param d
     * @return
     */
    public static String getWeekOfDate(int y, int m, int d) {
        String[] weekDaysName = {"日", "一", "二", "三", "四", "五", "六"};
        if (m == 1 || m == 2) {
            m += 12;
            y--;
        }
        int iWeek = (d + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7;
        switch (iWeek) {
            case 0:
                return weekDaysName[1];
            case 1:
                return weekDaysName[2];
            case 2:
                return weekDaysName[3];
            case 3:
                return weekDaysName[4];
            case 4:
                return weekDaysName[5];
            case 5:
                return weekDaysName[6];
            case 6:
                return weekDaysName[0];
            default:
                return "";
        }
    }
}

