package com.bbyyxx2.myqrproject.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {

    /**
     * 判断当前是否有网络连接,但是如果该连接的网络无法上网，也会返回true
     * @param mContext
     * @return
     */
    public static boolean isNetConnection(Context mContext) {
        if (mContext!=null){
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) mContext.
                            getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            boolean connected = networkInfo.isConnected();
            if (networkInfo!=null&&connected){
                if (networkInfo.getState()== NetworkInfo.State.CONNECTED){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
}
