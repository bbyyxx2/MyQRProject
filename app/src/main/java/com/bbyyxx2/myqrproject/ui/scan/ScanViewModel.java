package com.bbyyxx2.myqrproject.ui.scan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bbyyxx2.myqrproject.data.ScanRepository;

public class ScanViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final ScanRepository repository;

    public ScanViewModel() {
        mText = new MutableLiveData<>();
        // 在 ViewModel 中持有 Repository 实例
        repository = new ScanRepository();
    }

    public LiveData<String> getText() {
        return mText;
    }

    /**
     * 处理扫码结果：
     * 1. 更新 UI 状态 (mText)
     * 2. 调用 Repository 处理持久化逻辑
     * @param s 扫码内容
     */
    public void handleScanResult(String s){
        mText.setValue(s);
        repository.saveScanRecord(s);
    }
}