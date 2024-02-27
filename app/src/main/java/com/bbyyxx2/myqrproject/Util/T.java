package com.bbyyxx2.myqrproject.Util;

import android.widget.Toast;

import com.bbyyxx2.myqrproject.ui.base.MyApplication;

/**
 * toast吐司统一管理
 */
public class T {
    private static Toast toast;

    /**
     * 显示短期，防连续吐司
     * @param msg
     */
    public static void showMsg(String msg){
        if (toast == null){
            toast = Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 显示长期，防连续吐司
     * @param msg
     */
    public static void showLongMsg(String msg){
        if (toast == null){
            toast = Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }
}
