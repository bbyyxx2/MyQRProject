package com.bbyyxx2.myqrproject.ui.qrcode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;

import com.bbyyxx2.database.database.AppDatabase;
import com.bbyyxx2.database.entities.QRRecord;
import com.bbyyxx2.database.entities.ScanRecord;
import com.bbyyxx2.myqrproject.R;
import com.bbyyxx2.myqrproject.Util.L;
import com.bbyyxx2.myqrproject.Util.MMKVUtil;
import com.bbyyxx2.myqrproject.Util.T;
import com.bbyyxx2.myqrproject.Util.ThreadUtil;
import com.bbyyxx2.myqrproject.Util.TimeUtil;
import com.bbyyxx2.myqrproject.databinding.FragmentQrcodeBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseFragment;
import com.bbyyxx2.myqrproject.ui.base.Constant;
import com.bbyyxx2.myqrproject.ui.history.HistoryActivity;
import com.bbyyxx2.myqrproject.ui.history.util.HistoryConstant;
import com.bbyyxx2.myqrproject.ui.image.ShowImageActivity;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class QrcodeFragment extends BaseFragment<FragmentQrcodeBinding, QrcodeViewModel> {

    private PopupWindow popupDialog;

    @Override
    public void initView() {

        if (MMKVUtil.getInt(Constant.RED_SEEK_BAR, -1) != -1) {
            binding.redSeekbar.setProgress(MMKVUtil.getInt(Constant.RED_SEEK_BAR));
        }

        if (MMKVUtil.getInt(Constant.GREEN_SEEK_BAR, -1) != -1) {
            binding.greenSeekbar.setProgress(MMKVUtil.getInt(Constant.GREEN_SEEK_BAR));
        }

        if (MMKVUtil.getInt(Constant.BLUE_SEEK_BAR, -1) != -1) {
            binding.blueSeekbar.setProgress(MMKVUtil.getInt(Constant.BLUE_SEEK_BAR));
        }

        String lastContent = MMKVUtil.getString(Constant.LAST_QR_CONTENT, "");

        boolean switchLastQr = MMKVUtil.getBoolean(Constant.LAST_QR_CONTENT_SWITCH, false);

        if (switchLastQr && !TextUtils.isEmpty(lastContent)) {
            binding.etContent.setText(lastContent);
            createQr(lastContent);
        }

        binding.redEditText.setText(String.valueOf(binding.redSeekbar.getProgress()));
        binding.greenEditText.setText(String.valueOf(binding.greenSeekbar.getProgress()));
        binding.blueEditText.setText(String.valueOf(binding.blueSeekbar.getProgress()));
    }

    @Override
    public void initListener() {

        binding.tvIl.setEndIconOnClickListener(v -> binding.etContent.setText(""));

        binding.qrBt.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(Objects.requireNonNull(binding.etContent.getText()).toString())) {
                String content = binding.etContent.getText().toString();
                createQr(content);
                if (MMKVUtil.getBoolean(Constant.LAST_QR_CONTENT_SWITCH, false)) {
                    ThreadUtil.runInNewThread(() -> {
                        AppDatabase.instance.qrRecordDao().insertQRRecord(new QRRecord(content));
                        return null;
                    });
                }
            }
        });

        binding.etContent.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // 进行完成操作
                binding.qrBt.performClick();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });

        /*binding.qrIv.setOnClickListener(v -> {
            try { //容错
                if (popupDialog != null) {
                    popupDialog.dismiss();
                }
                View popView = getLayoutInflater().inflate(R.layout.dialog_image, null);


                //glide 加载图片
                ImageView iv_dialog_image = (ImageView) popView.findViewById(R.id.iv_dialog_image);
                //bt可要可不要，现在很少有app点开图片还有回退键，都是直接点击图片就消失
                iv_dialog_image.setOnClickListener(v1 -> {popupDialog.dismiss();});

                BitmapDrawable bmpDrawable = (BitmapDrawable) binding.qrIv.getDrawable();
                Bitmap bitmap = bmpDrawable.getBitmap();
                if (bitmap == null) {
                    return;
                }
                iv_dialog_image.setImageBitmap(bitmap);

                //Button bt =popView.findViewById(R.id.bt_dialog_image);
                //bt.setOnClickListener(v -> {popupDialog.dismiss();});
//            RequestOptions requestOptions = new RequestOptions();
//            requestOptions.placeholder(R.mipmap.image_load);

                //这里有两个坑，首先asBitmap在不同的版本位置不同，可能在with后面也可能在load才能点出来。
                //不能使用glide的 .placeholder()来设置占位图片，会导致自定义中获取到的图片大小永远是占位图大小，导致iv的初始大小缩放出现严重问题，导致图片显示不完全，iv大小异常等问题
//                Glide.with(mContext)
//                        .asBitmap()
//                        .load(url)
////                    .apply(requestOptions)
//                        .into(iv_dialog_image);

                popupDialog = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                //popupDialog.setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
                popupDialog.setOutsideTouchable(true);//外部点击消失
                //popView.setFocusableInTouchMode(true);
                popupDialog.setFocusable(true); // 这个很重要

                if (popupDialog != null && !popupDialog.isShowing()) {
                    popupDialog.setAnimationStyle(R.style.dialog_anim);
                    popupDialog.showAtLocation(getActivity().getWindow().getDecorView(),
                            Gravity.CENTER, 0, 0);
                    popupDialog.setFocusable(true);
                }
            } catch (Exception e) {
                //showAtLocation
            }
        });*/
        //点击放大，带共享元素动画
        binding.qrIv.setOnClickListener(v -> {
            Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v,"shareElement").toBundle();
            Intent intent = new Intent(activity, ShowImageActivity.class);
            intent.putExtra("content", binding.etContent.getText().toString());
            startActivity(intent, bundle);
        });
        // 长按保存图片
        binding.qrIv.setOnLongClickListener(v -> {
            if (((ImageView) v).getDrawable() == null) {
                return false;
            }
            if (((BitmapDrawable) ((ImageView) v).getDrawable()) == null) {
                return false;
            }
            if (((BitmapDrawable) ((ImageView) v).getDrawable()).getBitmap() == null) {
                return false;
            }

            PermissionX.init((FragmentActivity) activity)
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                            if (allGranted) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("提示");
                                builder.setMessage("是否保存图片");
                                builder.setCancelable(true);
                                builder.setPositiveButton("是", (dialog, which) -> saveImage(binding.qrIv));
                                builder.setNegativeButton("否", (dialog, which) -> dialog.dismiss());
                                builder.create().show();
                            } else {
                                Toast.makeText(context, deniedList + "权限被拒绝", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            return true;
        });

        binding.redSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        binding.greenSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        binding.blueSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);

        binding.redEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    int progress = Integer.parseInt(s.toString());
                    if (progress > 255){
                        binding.redEditText.setText("255");
                        binding.redSeekbar.setProgress(255);
                    } else {
                        binding.redSeekbar.setProgress(progress);
                    }
                }
            }
        });

        binding.greenEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    int progress = Integer.parseInt(s.toString());
                    if (progress > 255){
                        binding.greenEditText.setText("255");
                        binding.greenSeekbar.setProgress(255);
                    } else {
                        binding.greenSeekbar.setProgress(progress);
                    }
                }
            }
        });

        binding.blueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    int progress = Integer.parseInt(s.toString());
                    if (progress > 255){
                        binding.blueEditText.setText("255");
                        binding.blueSeekbar.setProgress(255);
                    } else {
                        binding.blueSeekbar.setProgress(progress);
                    }
                }
            }
        });

        binding.history.setOnClickListener(v -> {
            startActivity(HistoryActivity.class, "type", HistoryConstant.QR_RECORD);
        });
    }

    // 定义SeekBar控件的监听器
    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.red_seekbar:
                    MMKVUtil.put(Constant.RED_SEEK_BAR, progress);
                    if (fromUser)
                        binding.redEditText.setText(String.valueOf(progress));
                    break;
                case R.id.green_seekbar:
                    MMKVUtil.put(Constant.GREEN_SEEK_BAR, progress);
                    if (fromUser)
                        binding.greenEditText.setText(String.valueOf(progress));
                    break;
                case R.id.blue_seekbar:
                    MMKVUtil.put(Constant.BLUE_SEEK_BAR, progress);
                    if (fromUser)
                        binding.blueEditText.setText(String.valueOf(progress));
                    break;
            }

            if (!TextUtils.isEmpty(Objects.requireNonNull(binding.etContent.getText()).toString())) {

                String content = binding.etContent.getText().toString();
                createQr(content);
            }

//            // 设置显示颜色的View控件的背景颜色
//            colorView.setBackgroundColor(color);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void createQr(String content) {
        int type = HmsScan.QRCODE_SCAN_TYPE;
        int width = 300;
        int height = 300;
        HmsBuildBitmapOption options = new HmsBuildBitmapOption.Creator().setBitmapBackgroundColor(Color.WHITE).setBitmapColor(getColor()).setBitmapMargin(3).create();
        try {
            // 如果未设置HmsBuildBitmapOption对象，生成二维码参数options置null
            Bitmap qrBitmap = ScanUtil.buildBitmap(content, type, width, height, options);
            binding.qrIv.setImageBitmap(qrBitmap);

            if (MMKVUtil.getBoolean(Constant.LAST_QR_CONTENT_SWITCH, false)) {
                MMKVUtil.put(Constant.LAST_QR_CONTENT, content);
            }
        } catch (WriterException e) {
            L.w("buildBitmap", e);
            T.showMsg("生成二维码异常：" + e.getMessage());
        }
    }

    private int getColor() {
        // 获取SeekBar控件的当前值
        int redValue = binding.redSeekbar.getProgress();
        int greenValue = binding.greenSeekbar.getProgress();
        int blueValue = binding.blueSeekbar.getProgress();

        // 将RGB颜色值合并成一个颜色值

        return Color.rgb(redValue, greenValue, blueValue);
    }

    private void saveImage(ImageView imageView) {
        BitmapDrawable bmpDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bmpDrawable.getBitmap();
        if (bitmap == null) {
            return;
        }
        String fileName = "QR" + TimeUtil.getFormatCurrTime() + ".png";
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, fileName, "");
        //如果是4.4及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            File mPhotoFile = new File(fileName);
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(mPhotoFile);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        } else {
            context.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://"
                            + Environment.getExternalStorageDirectory())));
        }
        T.showMsg("已保存到相册");
    }
}