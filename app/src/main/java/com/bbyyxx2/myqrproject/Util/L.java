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

    public static void e(String mes,Throwable e)
    {
        if (isLog) Log.e(TAG,mes,e);
    }

    public static void d(String mes)
    {
        if (isLog) Log.d(TAG,mes);
    }

    public static void d(String mes,Throwable e)
    {
        if (isLog) Log.d(TAG,mes,e);
    }

    public static void w(String mes)
    {
        if (isLog) Log.w(TAG,mes);
    }

    public static void w(String mes,Throwable e)
    {
        if (isLog) Log.w(TAG,mes,e);
    }

    public static void i(String mes)
    {
        if (isLog) Log.i(TAG,mes);
    }

    public static void i(String mes,Throwable e)
    {
        if (isLog) Log.i(TAG,mes,e);
    }
}
