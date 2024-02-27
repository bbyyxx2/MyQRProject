package com.bbyyxx2.myqrproject.ui.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.bbyyxx2.dandelion.OkhttpUtil;
import com.bbyyxx2.dandelion.model.UpdateModel;
import com.bbyyxx2.myqrproject.R;
import com.bbyyxx2.myqrproject.Util.CommentUtil;
import com.bbyyxx2.myqrproject.Util.NetWorkUtil;
import com.bbyyxx2.myqrproject.Util.T;
import com.bbyyxx2.myqrproject.databinding.FragmentSettingBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseFragment;
import com.bbyyxx2.myqrproject.ui.base.Constant;
import com.bbyyxx2.myqrproject.ui.setting.adapter.SettingAdapter;
import com.bbyyxx2.myqrproject.ui.setting.model.SetData;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingFragment extends BaseFragment<FragmentSettingBinding, SettingViewModel> {

    private SettingAdapter adapter;
    private List<SetData> setDataList = new ArrayList<>();

    @Override
    public void initView() {
        viewModel.setVersionName("V" + CommentUtil.getVersionName(context));
        checkUpdate();

        setDataList.add(new SetData("scan", "保留扫码结果", null, Constant.LAST_SCAN_SWITCH, 1));
        setDataList.add(new SetData("qr", "保留生码结果", null, Constant.LAST_QR_CONTENT_SWITCH, 1));
        setDataList.add(new SetData("tts", "文字转语音功能", null, Constant.TTS_SWITCH, 1));
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
                switch (setDataList.get(pos).getLabel()){
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
        } else {
            T.showMsg("网络异常，请检查网络后重试！");
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