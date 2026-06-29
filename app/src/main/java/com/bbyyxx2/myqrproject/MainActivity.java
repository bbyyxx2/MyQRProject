package com.bbyyxx2.myqrproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.bbyyxx2.myqrproject.databinding.ActivityMainBinding;
import com.bbyyxx2.myqrproject.ui.base.Constant;
import com.bbyyxx2.myqrproject.ui.base.BaseActivity2;
import com.bbyyxx2.myqrproject.Util.MMKVUtil;
import com.google.android.material.navigation.NavigationBarView;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

public class MainActivity extends BaseActivity2<ActivityMainBinding> {
    private MainPagerAdapter adapter;

    private static final int CAMERA_REQ_CODE = 111;
    public static final int REQUEST_CODE_SCAN_ONE = 0X01;
    private static final String[] permission = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    public void initView(){
        if (adapter == null){
            adapter = new MainPagerAdapter(this);
        }
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setUserInputEnabled(false);
        //现在一共有三个，为了触发Setting中的更新,改为全部懒加载
        binding.viewPager.setOffscreenPageLimit(2);

        binding.navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_scan:
                        binding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_qrcode:
                        binding.viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_setting:
                        binding.viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 判断"requestCode"是否为申请权限时设置请求码CAMERA_REQ_CODE，然后校验权限开启状态。
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQ_CODE && grantResults.length == permission.length && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            // 读取扫描类型设置
            int scanType = Integer.parseInt(MMKVUtil.getString(Constant.SCAN_TYPE, "0"));

            // 所有条形码类型(按位或组合)
            int allBarcodeTypes = HmsScan.CODE39_SCAN_TYPE | HmsScan.CODE93_SCAN_TYPE
                    | HmsScan.CODE128_SCAN_TYPE | HmsScan.EAN13_SCAN_TYPE
                    | HmsScan.EAN8_SCAN_TYPE | HmsScan.ITF14_SCAN_TYPE
                    | HmsScan.UPCCODE_A_SCAN_TYPE | HmsScan.UPCCODE_E_SCAN_TYPE
                    | HmsScan.CODABAR_SCAN_TYPE;

            // 根据设置选择扫描类型
            int hmsScanType;
            switch (scanType) {
                case 0:
                    // 仅二维码
                    hmsScanType = HmsScan.QRCODE_SCAN_TYPE;
                    break;
                case 1:
                    // 仅条形码
                    hmsScanType = allBarcodeTypes;
                    break;
                case 2:
                default:
                    // 全部(二维码+条形码)
                    hmsScanType = HmsScan.ALL_SCAN_TYPE;
                    break;
            }

            // 调用扫码接口，构建扫码能力
            ScanUtil.startScan(this, REQUEST_CODE_SCAN_ONE,
                    new HmsScanAnalyzerOptions.Creator()
                            .setHmsScanTypes(hmsScanType)
                            .create());
        }
    }
}