package com.bbyyxx2.myqrproject.Util;

import android.util.Log;

import com.bbyyxx2.myqrproject.BuildConfig;

/**
 * Log类
 */
public class L {
    /**
     * 是否显示log
     */
    private static final boolean isLog = BuildConfig.DEBUG;

    private static final String TAG =  "MyQRProject-Log---";

    public static void e(String mes)
    {
        if (isLog) Log.e(TAG,mes);
    }

    public static void e(String tag, String mes)
    {
        if (isLog) Log.e(tag, mes);
    }

    public static void e(String mes,Throwable e)
    {
        if (isLog) Log.e(TAG,mes,e);
    }

    public static void e(String tag, String mes, Throwable e)
    {
        if (isLog) Log.e(tag, mes, e);
    }

    public static void d(String mes)
    {
        if (isLog) Log.d(TAG,mes);
    }

    public static void d(String tag, String mes)
    {
        if (isLog) Log.d(tag, mes);
    }

    public static void d(String mes,Throwable e)
    {
        if (isLog) Log.d(TAG,mes,e);
    }

    public static void d(String tag, String mes, Throwable e)
    {
        if (isLog) Log.d(tag, mes, e);
    }

    public static void w(String mes)
    {
        if (isLog) Log.w(TAG,mes);
    }

    public static void w(String tag, String mes)
    {
        if (isLog) Log.w(tag, mes);
    }

    public static void w(String mes,Throwable e)
    {
        if (isLog) Log.w(TAG,mes,e);
    }

    public static void w(String tag, String mes, Throwable e)
    {
        if (isLog) Log.w(tag, mes, e);
    }

    public static void i(String mes)
    {
        if (isLog) Log.i(TAG,mes);
    }

    public static void i(String tag, String mes)
    {
        if (isLog) Log.i(tag, mes);
    }

    public static void i(String mes,Throwable e)
    {
        if (isLog) Log.i(TAG,mes,e);
    }

    public static void i(String tag, String mes, Throwable e)
    {
        if (isLog) Log.i(tag, mes, e);
    }

    public static void v(String mes)
    {
        if (isLog) Log.v(TAG, mes);
    }

    public static void v(String tag, String mes)
    {
        if (isLog) Log.v(tag, mes);
    }
}
