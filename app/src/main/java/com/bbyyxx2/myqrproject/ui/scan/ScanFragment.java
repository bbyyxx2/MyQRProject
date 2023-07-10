package com.bbyyxx2.myqrproject.ui.scan;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bbyyxx2.myqrproject.MainActivity;
import com.bbyyxx2.myqrproject.Util.MMKVUtil;
import com.bbyyxx2.myqrproject.databinding.FragmentScanBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseFragment;
import com.bbyyxx2.myqrproject.ui.base.Constant;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;

public class ScanFragment extends BaseFragment<FragmentScanBinding, ScanViewModel> implements MainActivity.IOnActivityResult {

    private static final int CAMERA_REQ_CODE = 111;
    private static final String[] permission = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    public void initView() {
        super.initView();

        if (MMKVUtil.getBoolean(Constant.LAST_SCAN_SWITCH, false)){
            binding.content.setText(MMKVUtil.getString(Constant.LAST_SCAN_CONTENT, ""));
        }
    }

    @Override
    public void initListener() {
        binding.scanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(activity, permission, CAMERA_REQ_CODE);
            }
        });

        ((MainActivity) activity).setiOnActivityResult(this);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.content.setText(s);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
        Log.e("test111","value=" + obj.originalValue);
        if (obj != null) {
            viewModel.setText(obj.originalValue);

            if (MMKVUtil.getBoolean(Constant.LAST_SCAN_SWITCH, false)){
                MMKVUtil.put(Constant.LAST_SCAN_CONTENT, obj.originalValue);
            }
        }
    }
}