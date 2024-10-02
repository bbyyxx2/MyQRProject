package com.bbyyxx2.myqrproject.ui.image;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bbyyxx2.myqrproject.Util.L;
import com.bbyyxx2.myqrproject.Util.T;
import com.bbyyxx2.myqrproject.databinding.ActivityShowImageBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseActivity;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;

/**
 * 用于展示图片的act，类似X或者微博微信点开图片的效果
 */
public class ShowImageActivity extends BaseActivity<ActivityShowImageBinding, ShowImageViewModel> {

    @Override
    public void initView() {
        super.initView();

        binding.toolbar.setTitle("预览");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String content = getIntent().getStringExtra("content");

        binding.imageView.post(new Runnable() {
            @Override
            public void run() {
                HmsBuildBitmapOption options = new HmsBuildBitmapOption.Creator().setBitmapBackgroundColor(Color.WHITE).setBitmapColor(Color.BLACK).setBitmapMargin(3).create();
                try {
                    // 如果未设置HmsBuildBitmapOption对象，生成二维码参数options置null
                    Bitmap qrBitmap = ScanUtil.buildBitmap(content, HmsScan.QRCODE_SCAN_TYPE, binding.imageView.getWidth(), binding.imageView.getWidth(), options);
                    binding.imageView.setImageBitmap(qrBitmap);
                } catch (WriterException e) {
                    L.w("buildBitmap", e);
                    T.showMsg("生成二维码异常：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // This ID represents the Home/Up button. In the case of this activity,
            // either showing the parent activity in a hierarchically structured app,
            // or exiting the app for top-level activities.
            ActivityCompat.finishAfterTransition(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
