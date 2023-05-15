package com.bbyyxx2.myqrproject.ui.base;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    public IFpermission iFpermission;

    interface IFpermission{
        void requestPermission();
        boolean callRequest();
    }
}
