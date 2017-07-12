package com.spade.mek.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Ayman Abouzeid on 6/12/17.
 */

public abstract class BaseFragment extends Fragment {

//    @Override
//    public void onStart() {
//        super.onStart();
//        initPresenter();
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    protected abstract void initPresenter();

    protected abstract void initViews();
}