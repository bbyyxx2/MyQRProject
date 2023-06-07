package com.bbyyxx2.myqrproject.ui.qrcode;

import static com.huawei.hms.framework.common.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bbyyxx2.myqrproject.databinding.FragmentQrcodeBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseFragment;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;

import java.util.Objects;

public class QrcodeFragment extends BaseFragment<FragmentQrcodeBinding, QrcodeViewModel> {


    @Override
    protected FragmentQrcodeBinding inflateViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentQrcodeBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

        binding.tvIl.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etContent.setText("");
            }
        });

        binding.qrBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(Objects.requireNonNull(binding.etContent.getText()).toString())){
                    String content = binding.etContent.getText().toString();
                    int type = HmsScan.QRCODE_SCAN_TYPE;
                    int width = 300;
                    int height = 300;
                    HmsBuildBitmapOption options = new HmsBuildBitmapOption.Creator().setBitmapBackgroundColor(Color.WHITE).setBitmapColor(Color.BLACK).setBitmapMargin(3).create();
                    try {
                        // 如果未设置HmsBuildBitmapOption对象，生成二维码参数options置null
                        Bitmap qrBitmap = ScanUtil.buildBitmap(content, type, width, height, options);
                        binding.qrIv.setImageBitmap(qrBitmap);
                    } catch (WriterException e) {
                        Log.w("buildBitmap", e);
                    }
                }
            }
        });

        binding.etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE){
                    // 进行完成操作
                    binding.qrBt.performClick();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

    }
}