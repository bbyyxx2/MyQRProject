package com.bbyyxx2.myqrproject.ui.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingViewModel extends ViewModel {

    private MutableLiveData<String> versionName;

    public SettingViewModel() {
        versionName = new MutableLiveData<>();
    }

    public LiveData<String> getVersionName() {
        return versionName;
    }

    public void setVersionName(String s){
        versionName.postValue(s);
    }
}