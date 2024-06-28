package com.bbyyxx2.myqrproject.ui.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bbyyxx2.dandelion.OkhttpUtil;
import com.bbyyxx2.dandelion.model.UpdateModel;
import com.bbyyxx2.myqrproject.R;
import com.bbyyxx2.myqrproject.Util.CommentUtil;
import com.bbyyxx2.myqrproject.Util.NetWorkUtil;
import com.bbyyxx2.myqrproject.Util.T;
import com.bbyyxx2.myqrproject.databinding.FragmentSettingBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseFragment;
import com.bbyyxx2.myqrproject.ui.base.Constant;
import com.bbyyxx2.myqrproject.ui.base.MyKey;
import com.bbyyxx2.myqrproject.ui.setting.adapter.SettingAdapter;
import com.bbyyxx2.myqrproject.ui.setting.model.SetData;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends BaseFragment<FragmentSettingBinding, SettingViewModel> {

    private static final int REQUEST_CODE = 222;

    private SettingAdapter adapter;
    private List<SetData> setDataList = new ArrayList<>();

    @Override
    public void initView() {
        viewModel.setVersionName("V" + CommentUtil.getVersionName(context));
        checkUpdate();

        setDataList.add(new SetData("scan", "保留扫码结果", null, Constant.LAST_SCAN_SWITCH, 1));
        setDataList.add(new SetData("qr", "保留生码结果", null, Constant.LAST_QR_CONTENT_SWITCH, 1));
//        setDataList.add(new SetData("tts", "文字转语音功能", null, Constant.TTS_SWITCH, 1));
        setDataList.add(new SetData("version", "软件版本：V" + CommentUtil.getVersionName(context), null, "", 3));

        adapter = new SettingAdapter(context, setDataList);
        binding.settingsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.settingsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        adapter.setListener(new SettingAdapter.Listener() {
            @Override
            public void setOnClick(View v, int pos) {
                switch (setDataList.get(pos).getLabel()) {
                    case "scan":
                    case "qr":
                    case "tts":
                        SwitchMaterial aSwitch = v.findViewById(R.id.set_switch);
                        if (aSwitch != null)
                            aSwitch.setChecked(!aSwitch.isChecked());
                        break;
                    case "version":
                        checkUpdate();
                        break;
                }
            }

            @Override
            public void setOnLongClick(View v, int pos) {

            }
        });
    }

    private void checkUpdate() {
        if (NetWorkUtil.isNetConnection(context)) {
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
                            activity.runOnUiThread(() -> {
                                T.showMsg("更新请求异常：" + e.getMessage());
                            });
                        }

                        @Override
                        public void onResponse(int code, UpdateModel update) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (update != null && update.getData() != null && update.getData().isBuildHaveNewVersion()) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("发现更新");
                                        builder.setCancelable(false);
                                        builder.setMessage("发现" + update.getData().getBuildName() + "有新版本" + "\n"
                                                + "V" + update.getData().getBuildVersion() + "，备注:" + update.getData().getBuildUpdateDescription() + "\n"
                                                + "是否更新？");
                                        builder.setPositiveButton("普通下载", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                    if (!context.getPackageManager().canRequestPackageInstalls()) {
                                                        // 显示对话框请求权限
                                                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                                                        intent.setData(Uri.parse("package:" + context.getPackageName()));
                                                        intent.putExtra("data", update.getData());
                                                        startActivityForResult(intent, REQUEST_CODE);
                                                    } else {
                                                        getAppInstall2(update.getData());
                                                    }
                                                } else {
                                                    getAppInstall2(update.getData());
                                                }
                                            }
                                        });
                                        builder.setNegativeButton("浏览器下载", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                getAppInstall(update.getData().getBuildKey());
                                            }
                                        });
                                        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.create().show();
                                    } else {
                                        Toast.makeText(context, "当前为最新版！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onErrorCode(int code) {
                            activity.runOnUiThread(() -> {
                                T.showMsg("更新请求异常：code=" + code);
                            });
                        }
                    });
        } else {
            T.showMsg("网络异常，请检查网络后重试！");
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            UpdateModel.DataBean dataBean = (UpdateModel.DataBean) data.getSerializableExtra("data");
            getAppInstall2(dataBean);
        }
    }

    /**
     * 跳转浏览器的更新方式，依靠浏览器的下载机制
     *
     * @param buildKey
     */
    private void getAppInstall(String buildKey) {

        StringBuilder url = new StringBuilder("https://www.pgyer.com/apiv2/app/install?_api_key=" + MyKey._api_key);
        url.append("&buildKey=").append(buildKey);
        url.append("&buildPassword=").append(MyKey.buildPassword);

        Uri uri = Uri.parse(url.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void getAppInstall2(UpdateModel.DataBean dataBean) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MyQRProjectChannel1");
        builder.setContentTitle("下载中")
                .setSmallIcon(R.mipmap.app_icon2)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

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
                        notificationManager.notify(1, builder.build());
                        if (progress == 100) {
                            //不知道为啥，不好使
//                            builder.setContentTitle("下载完成")
//                                    .setProgress(0, 0, false);
//                            builder.setAutoCancel(true);
//                            notificationManager.notify(1, builder.build());
                            notificationManager.cancel(1);
                        }
                    }

                    @Override
                    public void onResponse(int code, File file) {
                        //通过file安装app
                        if (file != null) {
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

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        activity.runOnUiThread(() -> {
                            notificationManager.cancel(1);
                            T.showMsg("下载失败：" + e.getMessage());
                        });
                    }

                    @Override
                    public void onErrorCode(int code) {
                        activity.runOnUiThread(() -> {
                            notificationManager.cancel(1);
                            T.showMsg("下载异常：" + code);
                        });
                    }
                }, "");
    }
}