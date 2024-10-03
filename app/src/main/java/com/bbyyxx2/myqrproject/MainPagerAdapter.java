package com.bbyyxx2.myqrproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bbyyxx2.myqrproject.ui.qrcode.QrcodeFragment;
import com.bbyyxx2.myqrproject.ui.scan.ScanFragment;
import com.bbyyxx2.myqrproject.ui.setting.SettingFragment;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public MainPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public MainPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ScanFragment();
            case 1:
                return new QrcodeFragment();
            case 2:
                return new SettingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
