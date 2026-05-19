package com.bbyyxx2.myqrproject.ui.setting;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;

import com.bbyyxx2.dandelion.OkhttpUtil;
import com.bbyyxx2.dandelion.model.UpdateModel;
import com.bbyyxx2.myqrproject.R;
import com.bbyyxx2.myqrproject.Util.CommentUtil;
import com.bbyyxx2.myqrproject.Util.NetWorkUtil;
import com.bbyyxx2.myqrproject.Util.T;
import com.bbyyxx2.myqrproject.ui.base.MyKey;

import java.io.File;

public class UpdateManager {

    public static final int REQUEST_CODE = 222;
    private static final String CHANNEL_ID = "MyQRProjectChannel1";
    private static final int NOTIFICATION_ID = 1;

    public interface OnCheckUpdateListener {
        void onNoUpdate();
        void onCheckError(String message);
    }

    public static void checkUpdate(Context context, OnCheckUpdateListener listener) {
        if (!NetWorkUtil.isNetConnection(context)) {
            T.showMsg("网络异常，请检查网络后重试！");
            if (listener != null) {
                listener.onCheckError("网络异常");
            }
            return;
        }

        OkhttpUtil.getInstance(context)
                .setUrl("app/check")
                .addBody("_api_key", MyKey._api_key)
                .addBody("appKey", MyKey.appKey)
                .addBody("buildVersion", CommentUtil.getVersionName(context))
                .setConvert(UpdateModel.class)
                .buildRequest("POST", new OkhttpUtil.OnRequest<UpdateModel>() {
                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(() -> {
                                T.showMsg("更新请求异常：" + e.getMessage());
                                if (listener != null) {
                                    listener.onCheckError(e.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onResponse(int code, UpdateModel update) {
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(() -> {
                                if (update != null && update.getData() != null && update.getData().isBuildHaveNewVersion()) {
                                    showUpdateDialog((Activity) context, update.getData());
                                } else {
                                    Toast.makeText(context, "当前为最新版！", Toast.LENGTH_SHORT).show();
                                    if (listener != null) {
                                        listener.onNoUpdate();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onErrorCode(int code) {
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(() -> {
                                T.showMsg("更新请求异常：code=" + code);
                                if (listener != null) {
                                    listener.onCheckError("code=" + code);
                                }
                            });
                        }
                    }
                });
    }

    public static void showUpdateDialog(Activity activity, UpdateModel.DataBean dataBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("发现更新");
        builder.setCancelable(false);
        builder.setMessage("发现" + dataBean.getBuildName() + "有新版本" + "\n"
                + "V" + dataBean.getBuildVersion() + "，备注:" + dataBean.getBuildUpdateDescription() + "\n"
                + "是否更新？");

        builder.setPositiveButton("普通下载", (dialog, which) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!activity.getPackageManager().canRequestPackageInstalls()) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    intent.putExtra("data", dataBean);
                    activity.startActivityForResult(intent, REQUEST_CODE);
                } else {
                    downloadAndInstall(activity, dataBean);
                }
            } else {
                downloadAndInstall(activity, dataBean);
            }
        });

        builder.setNegativeButton("浏览器下载", (dialog, which) -> {
            openBrowserDownload(activity, dataBean.getBuildKey());
        });

        builder.setNeutralButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create().show();
    }

    public static boolean handlePermissionResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && data != null) {
            UpdateModel.DataBean dataBean = (UpdateModel.DataBean) data.getSerializableExtra("data");
            if (dataBean != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (context.getPackageManager().canRequestPackageInstalls()) {
                    downloadAndInstall(context, dataBean);
                    return true;
                }
            } else if (dataBean != null) {
                downloadAndInstall(context, dataBean);
                return true;
            }
        }
        return false;
    }

    private static void openBrowserDownload(Context context, String buildKey) {
        StringBuilder url = new StringBuilder("https://www.pgyer.com/apiv2/app/install?_api_key=" + MyKey._api_key);
        url.append("&buildKey=").append(buildKey);
        url.append("&buildPassword=").append(MyKey.buildPassword);

        Uri uri = Uri.parse(url.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static void downloadAndInstall(Context context, UpdateModel.DataBean dataBean) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle("下载中")
                .setSmallIcon(R.mipmap.app_icon2)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        StringBuilder url = new StringBuilder("app/install");
        url.append("?_api_key=").append(MyKey._api_key);
        url.append("&buildKey=").append(dataBean.getBuildKey());
        url.append("&buildPassword=").append(MyKey.buildPassword);

        OkhttpUtil.getInstance(context)
                .setUrl(url.toString())
                .downloadFile(new OkhttpUtil.OnDownloadRequest() {
                    @Override
                    public void onProgress(int progress) {
                        builder.setProgress(100, progress, false);
                        notificationManager.notify(NOTIFICATION_ID, builder.build());
                        if (progress == 100) {
                            notificationManager.cancel(NOTIFICATION_ID);
                        }
                    }

                    @Override
                    public void onResponse(int code, File file) {
                        if (file != null) {
                            builder.setContentTitle("下载完成")
                                    .setContentText("点击安装")
                                    .setProgress(0, 0, false)
                                    .setAutoCancel(true);

                            Intent installIntent = new Intent(Intent.ACTION_VIEW);
                            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Uri contentUri = FileProvider.getUriForFile(context,
                                        context.getPackageName() + ".fileprovider", file);
                                installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                installIntent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                            } else {
                                Uri uri = Uri.fromFile(file);
                                installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                            }

                            PendingIntent pendingIntent = PendingIntent.getActivity(
                                    context, 0, installIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                            );
                            builder.setContentIntent(pendingIntent);
                            notificationManager.notify(NOTIFICATION_ID, builder.build());

                            installApk(context, file);
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(() -> {
                                notificationManager.cancel(NOTIFICATION_ID);
                                T.showMsg("下载失败：" + e.getMessage());
                            });
                        }
                    }

                    @Override
                    public void onErrorCode(int code) {
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(() -> {
                                notificationManager.cancel(NOTIFICATION_ID);
                                T.showMsg("下载异常：" + code);
                            });
                        }
                    }
                }, "");
    }

    public static void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(context,
                    context.getPackageName() + ".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }

        context.startActivity(intent);
    }
}
