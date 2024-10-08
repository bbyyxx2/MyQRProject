package com.bbyyxx2.myqrproject.Util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class CommentUtil {

    /**
     * 获取当前应用的版本号，对应build.gradle中的versionName
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageInfo info = null;
        PackageManager pm = context.getPackageManager();
        try {
            info = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return info.versionName;

    }

    public static void copyToClipboard(Context context, String text){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = android.content.ClipData.newPlainText("text", text);
        cm.setPrimaryClip(clipData);

        T.showMsg("已复制到剪切板");
    }
}
