package com.bbyyxx2.myqrproject.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public abstract class BaseFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    public VB binding;
    public Context context;
    public Activity activity;
    public VM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        viewModel = new ViewModelProvider(this).get( (Class<VM>) superClass.getActualTypeArguments()[1]);

        try {
            Class<VB> cls = (Class<VB>) superClass.getActualTypeArguments()[0];
            Method method = cls.getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE);
            method.setAccessible(true);
            binding = (VB) method.invoke(null, inflater,container, Boolean.FALSE);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        context = requireContext();
        activity = requireActivity();
        initView();
        initListener();
        return binding.getRoot();
    }
    public void initView(){

    }

    public void initListener() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //释放持有，防止泄露
        binding = null;
    }
}
