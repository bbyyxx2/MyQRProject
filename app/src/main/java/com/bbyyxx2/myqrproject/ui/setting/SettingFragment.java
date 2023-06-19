package com.bbyyxx2.myqrproject.ui.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.bbyyxx2.dandelion.OkhttpUtil;
import com.bbyyxx2.dandelion.model.UpdateModel;
import com.bbyyxx2.myqrproject.Util.CommentUtil;
import com.bbyyxx2.myqrproject.Util.NetWorkUtil;
import com.bbyyxx2.myqrproject.databinding.FragmentSettingBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Objects;

public class SettingFragment extends BaseFragment<FragmentSettingBinding, SettingViewModel> {

    @Override
    public void initView() {
        checkUpdate();
    }

    @Override
    public void initListener() {
        //声明观察
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textNotifications.setText(s);
            }
        });
        //获取版本号
        viewModel.setVersionName("V" + CommentUtil.getVersionName(context));
        //点击监听
        binding.textNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUpdate();
            }
        });
    }

    private void checkUpdate(){
        if (NetWorkUtil.isNetConnection(context)){
            OkhttpUtil.getInstance(context)
                    .setUrl("app/check")
                    .addBody("_api_key","89efd6870e572d850f2070111a9c8ee4")
                    .addBody("appKey","192b1f7aa331090c61bccfae3f3723f5")
                    .addBody("buildVersion",CommentUtil.getVersionName(context))
                    .buildRequest("POST", new OkhttpUtil.OnRequest() {
                        @Override
                        public void onFailure(Exception e) {

                        }

                        @Override
                        public void onResponse(int code, Object o) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    UpdateModel update = gson.fromJson(o.toString(), new TypeToken<UpdateModel>(){}.getType());
                                    if (update != null && update.getData() != null && update.getData().isBuildHaveNewVersion()) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("发现更新");
                                        builder.setCancelable(false);
                                        builder.setMessage("发现" + update.getData().getBuildName() + "有新版本" + "\n"
                                                + "V" + update.getData().getBuildVersion() + "，备注:" + update.getData().getBuildUpdateDescription() + "\n"
                                                + "是否更新？");
                                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                getAppInstall(update.getData().getBuildKey());
                                            }
                                        });
                                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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

                        }
                    });
        }
    }

    private void getAppInstall(String buildKey){

        StringBuilder url = new StringBuilder("https://www.pgyer.com/apiv2/app/install?_api_key=89efd6870e572d850f2070111a9c8ee4");
        url.append("&buildKey=").append(buildKey);
        url.append("&buildPassword=").append("bbyyxx2test");

        Uri uri = Uri.parse(url.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}