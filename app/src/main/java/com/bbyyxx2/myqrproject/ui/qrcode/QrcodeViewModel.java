package com.bbyyxx2.myqrproject.ui.qrcode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QrcodeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QrcodeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}