package com.bbyyxx2.myqrproject.ui.qrcode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bbyyxx2.myqrproject.databinding.FragmentQrcodeBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseFragment;

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
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textDashboard.setText(s);
            }
        });
    }
}