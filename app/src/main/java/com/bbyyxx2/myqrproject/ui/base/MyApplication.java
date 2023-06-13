package com.bbyyxx2.myqrproject.ui.base;

import android.app.Application;
import android.content.Context;

import com.bbyyxx2.myqrproject.Util.MMKVUtil;
import com.tencent.mmkv.MMKV;

public class MyApplication extends Application {

    private static MMKVUtil mmkv;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();

        //MMKV初始化
        MMKV.initialize(this);
        //工具类初始化
        MMKVUtil.getInstance();
    }

    /**
     * 得到全局context
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
