package com.bbyyxx2.myqrproject.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.ParameterizedType;

public abstract class BaseFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment {

    public VB binding;
    public Context context;
    public Activity activity;
    public VM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        viewModel = new ViewModelProvider(this).get( (Class<VM>) superClass.getActualTypeArguments()[1]);
        binding = inflateViewBinding(inflater, container);
        context = requireContext();
        activity = requireActivity();
        initView();
        initListener();
        return binding.getRoot();
    }

    /**
     * 一定要复写的，不然会报错，用于viewBinding的代码和页面关联。
     * 方法内只需要一行eg：return FragmentScanBinding.inflate(inflater, container, false);
     * @param inflater
     * @param container
     * @return
     */
    protected abstract VB inflateViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);
    protected abstract void initView();
    protected abstract void initListener();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
