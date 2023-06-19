package com.bbyyxx2.myqrproject.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public abstract class BaseActivity<VB extends ViewBinding, VM extends ViewModel> extends AppCompatActivity {

    public VB binding;
    public Context context;
    public Activity activity;
    public VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ParameterizedType是获取传递过来的泛型类
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        viewModel = new ViewModelProvider(this).get( (Class<VM>) superClass.getActualTypeArguments()[1]);

        try {
            Class<VB> cls = (Class<VB>) superClass.getActualTypeArguments()[0];
            Method method = cls.getMethod("inflate", LayoutInflater.class);
            method.setAccessible(true);
            binding = (VB) method.invoke(null, getLayoutInflater());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        setContentView(binding.getRoot());
        context = this;
        activity = this;
        initView();
        initListener();
    }

    public void initView() {

    }

    public void initListener(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放持有，防止泄露
        binding = null;
    }
}
