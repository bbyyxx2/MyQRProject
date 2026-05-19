package com.bbyyxx2.myqrproject.ui.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.bbyyxx2.myqrproject.R;
import com.bbyyxx2.myqrproject.Util.CommentUtil;
import com.bbyyxx2.myqrproject.Util.T;
import com.bbyyxx2.myqrproject.ui.base.Constant;

/**
 * 设置界面Fragment - 使用PreferenceFragmentCompat实现
 *
 * 功能包括：
 * 1. 保留扫码结果开关
 * 2. 保留生码结果开关
 * 3. 软件版本显示和更新检查
 *
 * 数据持久化方案：
 * - 使用自定义的 MMKVPreferenceDataStore 将所有设置项存储到 MMKV
 * - 统一项目的数据存储方式，避免 SharedPreferences 和 MMKV 混用
 * - PreferenceFragmentCompat 会自动从 MMKV 读取和保存数据
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private Context context;
    private Activity activity;

    // Preference 引用
    private SwitchPreferenceCompat scanSwitch;
    private SwitchPreferenceCompat qrSwitch;
    private Preference versionPreference;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = requireContext();
        activity = requireActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化Preference
        initPreferences();
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        // 设置自定义的 DataStore，将数据持久化到 MMKV 而非 SharedPreferences
        getPreferenceManager().setPreferenceDataStore(new MMKVPreferenceDataStore());

        // 加载 XML preference 文件
        setPreferencesFromResource(R.xml.setting_preference, rootKey);
    }

    /**
     * 初始化所有Preference及其事件监听
     */
    private void initPreferences() {
        // 获取Preference引用
        scanSwitch = findPreference(Constant.LAST_SCAN_SWITCH);
        qrSwitch = findPreference(Constant.LAST_QR_CONTENT_SWITCH);
        versionPreference = findPreference("pref_app_version");

        // 设置版本号
        if (versionPreference != null) {
            String versionName = "V" + CommentUtil.getVersionName(context);
            versionPreference.setSummary(versionName);

            // 点击版本号检查更新
            versionPreference.setOnPreferenceClickListener(preference -> {
                UpdateManager.checkUpdate(context, null);
                return true;
            });
        }

        // 扫码结果开关监听
        if (scanSwitch != null) {
            // 监听开关变化，仅用于 UI 提示
            scanSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean enabled = (Boolean) newValue;
                T.showMsg(enabled ? "已开启保留扫码结果" : "已关闭保留扫码结果");
                return true;
            });
        }

        // 生码结果开关监听
        if (qrSwitch != null) {
            // 监听开关变化，仅用于 UI 提示
            qrSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean enabled = (Boolean) newValue;
                T.showMsg(enabled ? "已开启保留生码结果" : "已关闭保留生码结果");
                return true;
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UpdateManager.handlePermissionResult(context, requestCode, resultCode, data);
    }
}
