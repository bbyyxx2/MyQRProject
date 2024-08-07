package com.bbyyxx2.myqrproject.ui.scan;

import static com.bbyyxx2.myqrproject.MainActivity.REQUEST_CODE_SCAN_ONE;

import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import com.bbyyxx2.database.database.AppDatabase;
import com.bbyyxx2.database.entities.ScanRecord;
import com.bbyyxx2.myqrproject.Util.MMKVUtil;
import com.bbyyxx2.myqrproject.Util.ThreadUtil;
import com.bbyyxx2.myqrproject.databinding.FragmentScanBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseFragment;
import com.bbyyxx2.myqrproject.ui.base.Constant;
import com.bbyyxx2.myqrproject.ui.history.HistoryActivity;
import com.bbyyxx2.myqrproject.ui.history.util.HistoryConstant;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;

public class ScanFragment extends BaseFragment<FragmentScanBinding, ScanViewModel> {

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

        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.content.setText(s);
            }
        });

        binding.history.setOnClickListener(v -> {
            startActivity(HistoryActivity.class, "type", HistoryConstant.SCAN_RECORD);
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
            Log.e("test111","value=" + obj.originalValue);
            if (obj != null) {
                viewModel.setText(obj.originalValue);

                if (MMKVUtil.getBoolean(Constant.LAST_SCAN_SWITCH, false)){
                    MMKVUtil.put(Constant.LAST_SCAN_CONTENT, obj.originalValue);
                    ThreadUtil.runInNewThread(() -> {
                        AppDatabase.instance.scanRecordDao().insertScanRecord(new ScanRecord(obj.originalValue));
                        return null;
                    });
                }
            }
        }
    }
}